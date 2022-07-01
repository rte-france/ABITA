/**
 *
 */
package com.web.common.validators;

import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.HousingDTO;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * @author
 *
 */
public class SameAgencyValidator implements Validator, Serializable {

  /** serialVersionUID */
  private static final long serialVersionUID = -2905301685062765153L;

  /** Le nom de l'attribut indiquant le libellé du champ */
  private static final String ATTRIBUTE_LABEL = "label";

  /** Le nom de l'attribut indiquant l'erreur de la date saisie en fonction de la date d'entrée dans le parc immo */
  private static final String ATTRIBUTE_SUMMARY_MESSAGE = "summaryMessage";

  /** Le message d'erreur par defaut en cas d'échec de la récupération  de l'attribut "summaryMessage" */
  private static final String DEFAULT_VALIDATION_SUMMARY_MESSAGE = "Le domaine d\u2019activit\u00E9 n\u2019est pas rattach\u00E9 \u00E0 l\u2019agence du logement.";

  /*
   * (non-Javadoc)
   *
   * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
   */
  @Override
  public void validate(FacesContext context, UIComponent component, Object value) {
    if (value != null) {
      FieldOfActivityDTO fieldOfActivity = (FieldOfActivityDTO) value;
      // on cherche un composant référençant un logement
      UIOutput housingOutput = (UIOutput) component.getAttributes().get("housing");
      // On part du principe que le champ "housing" contient déjà l'id d'un logement valide
      HousingDTO housingValue = (HousingDTO) housingOutput.getValue();

      if (housingValue != null && !housingValue.getAgency().equals(fieldOfActivity.getAgency())) {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);

        String resumedMessage = MessageFormat.format(getSummaryMessage(component), ((UIInput) component).getSubmittedValue(), getComponentLabel(component));
        facesMessage.setSummary(resumedMessage);

        throw new ValidatorException(facesMessage);
      }
    }
  }

  /**
   * Renvoie le libellé à afficher dans le message d'erreur
   *
   * La méthode renvoie la valeur de l'attribut "label" si elle est définie et non nulle ou la valeur de l'identifiant client
   *
   * @param component le composant dont on veut le libellé
   *
   * @return le libellé à afficher
   */
  private String getComponentLabel(final UIComponent component) {
    String label = null;
    Object labelValue = component.getAttributes().get(ATTRIBUTE_LABEL);
    if (labelValue != null) {
      label = String.valueOf(labelValue);
    } else {
      label = component.getClientId();
    }
    return label;
  }

  /**
   * Renvoie le libellé à afficher dans le message d'erreur
   *
   * La méthode renvoie la valeur de l'attribut "summaryMessage" si elle est définie et non nulle ou le message d'erreur par défaut
   *
   * @param component le composant dont on veut le message d'erreur summaryMessage
   *
   * @return le message d'erreur à afficher
   */
  private String getSummaryMessage(final UIComponent component) {
    String msg = null;
    Object msgValue = component.getAttributes().get(ATTRIBUTE_SUMMARY_MESSAGE);
    if (msgValue != null) {
      msg = String.valueOf(msgValue);
    } else {
      msg = DEFAULT_VALIDATION_SUMMARY_MESSAGE;
    }
    return msg;
  }
}
