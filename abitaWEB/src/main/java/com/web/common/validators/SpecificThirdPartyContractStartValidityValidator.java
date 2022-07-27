/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.web.common.validators;

import com.abita.dto.HousingDTO;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

/**
 * Valide la date de début du contrat
 *
 * @author
 */
public class SpecificThirdPartyContractStartValidityValidator implements Validator, Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -3291912803245385701L;

  /** Le nom de l'attribut indiquant le libellé du champ */
  private static final String ATTRIBUTE_LABEL = "label";

  /** Le nom de l'attribut indiquant le libellé du champ */
  private static final String ATTRIBUTE_SUMMARY_MESSAGE = "summaryMessage";

  /** Le nom de l'attribut indiquant le libellé du champ */
  private static final String DEFAULT_VALIDATION_MESSAGE = "Date de début de contrat antérieure à la date d''entrée du logement";

  /*
   * (non-Javadoc)
   *
   * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
   */
  @Override
  public void validate(FacesContext context, UIComponent component, Object value) {
    // On part du principe que "value" est déjà une date au format valide
    if (value != null) {
      Date startValidity = (Date) value;
      // Si une date de comparaison est fourni, on l'utilise
      Date otherDate = (Date) component.getAttributes().get("otherDate");
      if (otherDate == null) {
        // Sinon, on cherche un composant référençant un logement
        UIInput otherInput = (UIInput) component.getAttributes().get("housing");
        // On part du principe que le champ "housing" contient déjà l'id d'un logement valide
        HousingDTO otherValue = (HousingDTO) otherInput.getValue();
        if (otherValue != null) {
          otherDate = otherValue.getRegisterDate();
        }
      }
      // Si aucune date de comparaison n'a été trouvé, on ne fait pas la validation
      if (otherDate != null && startValidity.compareTo(otherDate) < 0) {
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
   * La méthode renvoie la valeur de l'attribut "label" si elle est définie et non nulle ou la valeur de l'identifiant client
   *
   * @param component le composant dont on veut le libellé
   *
   * @return le libellé à afficher
   */
  private String getSummaryMessage(final UIComponent component) {
    String msg = null;
    Object msgValue = component.getAttributes().get(ATTRIBUTE_SUMMARY_MESSAGE);
    if (msgValue != null) {
      msg = String.valueOf(msgValue);
    } else {
      msg = DEFAULT_VALIDATION_MESSAGE;
    }
    return msg;
  }
}
