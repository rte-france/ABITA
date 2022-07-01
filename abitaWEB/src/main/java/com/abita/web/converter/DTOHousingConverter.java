/**
 *
 */
package com.abita.web.converter;

import com.abita.dto.HousingDTO;
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
 * Converter JSF dédié aux DTO logement.
 *
 * Se base sur une liste de DTO de référence, à définir au niveau du composant
 * par un attribut nommé {@value #VALUES_ATTRIBUTE}.
 *
 * @author
 */
public class DTOHousingConverter implements Converter {

  /**
   * Liste des logements par agence de l'utilisateur
   *
   * Le nom de l'attribut à définir au niveau du composant qui va utiliser
   * ce converter. L'attribut doit pointer vers une    *
   * {@link Collection}&lt;? extends {@link AbstractDTO}&gt.
   */
  public static final String VALUES_ATTRIBUTE = "converter-values";

  /**
   * Liste de tous les logements de l'application
   *
   * Le nom de l'attribut à définir au niveau du composant qui va utiliser
   * ce converter. L'attribut doit pointer vers une    *
   * {@link Collection}&lt;? extends {@link AbstractDTO}&gt.
   */
  public static final String ALL_VALUES_ATTRIBUTE = "converter-allvalues";

  /** Le message d'erreur pour un champ vide */
  public static final String EMPTY_VALUE_MESSAGE = "emptyMessage";

  /** Le message d'erreur pour une reference inconnue dans l'agence de l'utilisateur */
  public static final String AGENCY_MESSAGE = "agencyMessage";

  /** Le message d'erreur pour une reference inconnue dans l'application */
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

    final Collection<HousingDTO> values = (Collection<HousingDTO>) component.getAttributes().get(VALUES_ATTRIBUTE);
    final Collection<HousingDTO> allValues = (Collection<HousingDTO>) component.getAttributes().get(ALL_VALUES_ATTRIBUTE);
    final String emptyMessage = (String) component.getAttributes().get(EMPTY_VALUE_MESSAGE);
    final String agencyMessage = (String) component.getAttributes().get(AGENCY_MESSAGE);
    final String noExistMessage = (String) component.getAttributes().get(NO_EXIST_MESSAGE);
    UIInput housingInput = (UIInput) component;

    if (values != null) {
      if (!StringUtils.isBlank(value)) {
        for (HousingDTO dto : values) {
          if (value.equals(dto.getReference())) {
            return dto;
          }
        }

        boolean referenceFound = false;

        for (HousingDTO dto : allValues) {
          if (value.equals(dto.getReference())) {
            referenceFound = true;
          }
        }

        if (referenceFound) {
          // Message d'erreur pour le controle de surface pour savoir si la reference existe dans l'agence
          housingInput.setValid(false);
          FacesMessage facesMessage = new FacesMessage();
          facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
          facesMessage.setSummary(agencyMessage);
          FacesContext.getCurrentInstance().addMessage(null, facesMessage);
          LoggerFactory.getLogger(DTOHousingConverter.class).warn("DTO not found '" + value + "' in user's agencies");
        } else {
          // Message d'erreur pour le controle de surface pour savoir si la reference existe dans la base de donnée
          housingInput.setValid(false);
          FacesMessage facesMessage = new FacesMessage();
          facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
          facesMessage.setSummary(noExistMessage);
          FacesContext.getCurrentInstance().addMessage(null, facesMessage);
          LoggerFactory.getLogger(DTOHousingConverter.class).warn("DTO not found '" + value + "'");
        }
      } else {
        // Message d'erreur pour le controle de surface si le champ reference n'est pas rempli
        housingInput.setValid(false);
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        facesMessage.setSummary(emptyMessage);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
      }
    } else {
      LoggerFactory.getLogger(DTOHousingConverter.class).warn("no values where search '" + value + "'");
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
      final HousingDTO dto = (HousingDTO) value;
      if (dto.getReference() != null) {
        return dto.getReference();
      }
    }
    return null;
  }

}
