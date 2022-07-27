/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.validators;

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
 * Validateur permettant de vérifier si la date de dernière maintenance DAAF est postérieure ou égale à la date
 * d'entrée dans le logement et antérieure ou égale à la date de sortie du dernier occupant du logement
 *
 * @author
 */
public class LastDaafDateBetweenRegisterAndUnregisterDate implements Validator, Serializable {

  private static final long serialVersionUID = -4566328399718218113L;

  /**
   * Le nom de l'attribut indiquant le libellé du champ
   */
  private static final String ATTRIBUTE_LABEL = "label";

  /**
   * Le nom de l'attribut contenant le message d'erreur à afficher
   */
  private static final String ATTRIBUTE_SUMMARY_MESSAGE = "summaryMessage";

  /**
   * Le message d'erreur par defaut en cas d'échec de la récupération  de l'attribut "summaryMessage"
   */
  private static final String DEFAULT_VALIDATION_SUMMARY_MESSAGE = "Le champ \"Date de derni\u00E8re maintenance DAAF\" doit \u00EAtre post\u00E9rieur ou \u00E9gal au champ \"Date d''entr\u00E9e\" et ant\u00E9rieur ou \u00E9gal au champ \"Date de sortie\".";

  /**
   * Le nom de l'attribut contenant la date d'entrée dans le logement
   */
  private static final String ATTRIBUTE_REGISTER_DATE = "registerDate";
  /**
   * Le nom de l'attribut contenant la date de sortie du logment
   */
  private static final String ATTRIBUTE_UNREGISTER_DATE = "unregisterDate";

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) {
    if (context == null) {
      throw new IllegalArgumentException("Le context ne peut être indéfini");
    }

    if (component == null) {
      throw new IllegalArgumentException("Ce validateur doit être associé à un composant");
    }

    if (null != value) {
      Date lastDaafDate = (Date) value;
      Date registerDate = (Date) component.getAttributes().get(ATTRIBUTE_REGISTER_DATE);
      Date unregisterDate = (Date) component.getAttributes().get(ATTRIBUTE_UNREGISTER_DATE);

      // si la date de sortie n'est pas renseignée, on vérifie uniquement que la date DAAF soit postérieure à la date d'entrée
      if ((registerDate != null && lastDaafDate.before(registerDate)) || (unregisterDate != null && lastDaafDate.after(unregisterDate))) {
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
   * <p>
   * La méthode renvoie la valeur de l'attribut "label" si elle est définie et non nulle ou la valeur de l'identifiant client
   *
   * @param component le composant dont on veut le libellé
   * @return le libellé à afficher
   */
  private String getComponentLabel(final UIComponent component) {
    String label;
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
   * <p>
   * La méthode renvoie la valeur de l'attribut "summaryMessage" si elle est définie et non nulle ou le message d'erreur par défaut
   *
   * @param component le composant dont on veut le message d'erreur summaryMessage
   * @return le message d'erreur à afficher
   */
  private String getSummaryMessage(final UIComponent component) {
    String msg;
    Object msgValue = component.getAttributes().get(ATTRIBUTE_SUMMARY_MESSAGE);
    if (msgValue != null) {
      msg = String.valueOf(msgValue);
    } else {
      msg = DEFAULT_VALIDATION_SUMMARY_MESSAGE;
    }
    return msg;
  }
}
