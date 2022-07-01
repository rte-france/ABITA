/**
 *
 */
package com.abita.web.converter;

import com.abita.dto.TenantDTO;
import com.dto.AbstractDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import java.util.Collection;

/**
 * Converter JSF dédié aux DTO occupant.
 *
 * Se base sur une liste de DTO de référence, à définir au niveau du composant
 * par un attribut nommé {@value #VALUES_ATTRIBUTE}.
 *
 * @author
 */
public class DTOTenantConverter implements Converter {

  /**
   * Le nom de l'attribut à définir au niveau du composant qui va utiliser
   * ce converter. L'attribut doit pointer vers une
   * {@link Collection}&lt;? extends {@link AbstractDTO}&gt.
   */
  public static final String VALUES_ATTRIBUTE = "converter-values";

  /** Le message d'erreur pour un champ vide */
  public static final String EMPTY_VALUE_MESSAGE = "emptyMessage";

  /** Le message d'erreur pour une reference inconnue */
  public static final String NO_EXIST_MESSAGE = "notExistMessage";

  /**
   * Se base sur la liste des valeurs fournies par l'attribut.
   *
   * @param context the context
   * @param component the component
   * @param value the value
   * @return the as object
   * {@value #VALUES_ATTRIBUTE} du composant pour retrouver le
   * DTO via sa reference.
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object getAsObject(FacesContext context, UIComponent component, String value) {

    final Collection<TenantDTO> values = (Collection<TenantDTO>) component.getAttributes().get(VALUES_ATTRIBUTE);
    final String emptyMessage = (String) component.getAttributes().get(EMPTY_VALUE_MESSAGE);
    final String noExistMessage = (String) component.getAttributes().get(NO_EXIST_MESSAGE);
    UIInput tenantInput = (UIInput) component;

    if (values != null) {
      if (!StringUtils.isBlank(value)) {
        for (TenantDTO dto : values) {
          if (value.equals(dto.getReference())) {
            return dto;
          }
        }
        // Message d'erreur pour le controle de surface si la référence n'existe pas en base de données
        tenantInput.setValid(false);
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        facesMessage.setSummary(noExistMessage);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        LoggerFactory.getLogger(DTOTenantConverter.class).warn("DTO not found '" + value + "'");
      } else {
        // Message d'erreur pour le controle de surface si le champ reference n'est pas rempli
        tenantInput.setValid(false);
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        facesMessage.setSummary(emptyMessage);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
      }
    } else {
      LoggerFactory.getLogger(DTOTenantConverter.class).warn("no values where search '" + value + "'");
    }
    return null;
  }

  /**
   * Retourne la reference du DTO.
   *
   * @param context the context
   * @param component the component
   * @param value the value
   * @return the as string
   */
  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    if (value instanceof AbstractDTO) {
      final TenantDTO dto = (TenantDTO) value;
      if (dto.getReference() != null) {
        return dto.getReference();
      }
    }
    return null;
  }

}
