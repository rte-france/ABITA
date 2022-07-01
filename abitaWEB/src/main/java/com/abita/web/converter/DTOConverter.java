package com.abita.web.converter;

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
 * Converter JSF dédié aux DTO.
 *
 * Se base sur une liste de DTO de référence, à définir au niveau du composant
 * par un attribut nommé {@value #VALUES_ATTRIBUTE}.
 *
 * @author
 */
public class DTOConverter implements Converter {

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
   * DTO via son ID.
   */
  @Override
  @SuppressWarnings("unchecked")
  public Object getAsObject(FacesContext context, UIComponent component, String value) {

    final Collection<? extends AbstractDTO> values = (Collection<? extends AbstractDTO>) component.getAttributes().get(VALUES_ATTRIBUTE);
    final String emptyMessage = (String) component.getAttributes().get(EMPTY_VALUE_MESSAGE);
    final String noExistMessage = (String) component.getAttributes().get(NO_EXIST_MESSAGE);
    UIInput dtoInput = (UIInput) component;

    if (values != null) {
      if (!StringUtils.isBlank(value)) {

        try {
          final Long id = Long.valueOf(value);
          for (AbstractDTO dto : values) {
            if (id.equals(dto.getId())) {
              return dto;
            }
          }

          if (StringUtils.isNotEmpty(NO_EXIST_MESSAGE)) {
            // Message d'erreur pour le controle de surface si la référence n'existe pas en base de données
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesMessage.setSummary(noExistMessage);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

            dtoInput.setValid(false);
          }
          LoggerFactory.getLogger(DTOConverter.class).warn("DTO not found '" + value + "'");

        } catch (NumberFormatException e) {
          if (StringUtils.isNotEmpty(NO_EXIST_MESSAGE)) {
            // Message d'erreur pour le controle de surface si la référence n'existe pas en base de données
            FacesMessage facesMessage = new FacesMessage();
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
            facesMessage.setSummary(noExistMessage);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);

            dtoInput.setValid(false);
          }

          LoggerFactory.getLogger(DTOConverter.class).warn("Invalid DTO id'" + value + "'");
        }
      } else {
        if (StringUtils.isNotEmpty(EMPTY_VALUE_MESSAGE)) {
          // Message d'erreur pour le controle de surface si le champ reference n'est pas rempli
          FacesMessage facesMessage = new FacesMessage();
          facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
          facesMessage.setSummary(emptyMessage);
          FacesContext.getCurrentInstance().addMessage(null, facesMessage);

          dtoInput.setValid(false);
        }
      }
    } else {
      LoggerFactory.getLogger(DTOConverter.class).warn("no values where search '" + value + "'");
    }
    return null;
  }

  /**
   * Retourne l'ID du DTO.
   *
   * @param context the context
   * @param component the component
   * @param value the value
   * @return the as string
   */
  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    if (value instanceof AbstractDTO) {
      final AbstractDTO dto = (AbstractDTO) value;
      if (dto.getId() != null) {
        return dto.getId().toString();
      }
    }
    return null;
  }

}
