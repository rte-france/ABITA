/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.validators;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.component.calendar.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Validateur permettant de vérifier qu'une date saisie est inférieure à celle d'un autre champ
 *
 * @author
 */
public class BeforeDateValidator implements Validator, Serializable {
  /**
   * Identifiant unique de serialisation
   */
  private static final long serialVersionUID = 1195087546348119375L;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(BeforeDateValidator.class);

  /** Le nom de l'attribut indiquant le libellé du champ */
  private static final String ATTRIBUTE_LABEL = "label";

  /** Le résumé du message à afficher en cas d'erreur */
  private String summaryMessage;

  /** Le détail du message à afficher en cas d'erreur */
  private String detailMessage;

  /** Le composant ayant l'autre date */
  private String boundComponentId;

  /**
   * Le paramètre value est déjà converti en date (la propriété de destination doit évidemment être de type {@link Date}
   * Les composants utilisés doivent être des p:calendar.
   *
   * La méthode récupère la valeur soumise par le calendrier (13/11/2013) et le pattern afin de récupérer l'objet {@link Date} correspondant
   *
   * @param context le context actuel
   * @param component le composant sur lequel le validateur est positionné
   * @param value la valeur convertie du composant
   */
  @Override
  public void validate(FacesContext context, UIComponent component, Object value) {
    if (context == null) {
      throw new IllegalArgumentException("Le context ne peut être indéfini");
    }

    if (component == null) {
      throw new IllegalArgumentException("Ce validateur doit être associé à un composant");
    }

    if (value != null) {
      if (!(value instanceof Date)) {
        throw new IllegalArgumentException("Ce validateur ne peut être utilisé qu'avec des dates");
      }

      // recherche de l'élément dans le NamingContainer actuel
      UIComponent targetComponent = component.findComponent(boundComponentId);
      if (targetComponent == null) {
        // si non trouvé, recherche de l'identifiant unique dans la vue
        targetComponent = context.getViewRoot().findComponent(boundComponentId);
      }

      if (targetComponent == null) {
        LOGGER.warn("Le composant d'id '" + boundComponentId + "' n'a pas été trouvé");
      } else if (targetComponent instanceof Calendar) {
        Calendar targetCalendar = (Calendar) targetComponent;

        if (!StringUtils.isBlank((String) targetCalendar.getSubmittedValue())) {
          // il faut aller chercher la valeur soumise et la convertir en date
          // en utilisant le pattern
          Date targetDate = null;
          try {
            targetDate = new SimpleDateFormat(targetCalendar.calculatePattern(), context.getViewRoot().getLocale()).parse((String) targetCalendar.getSubmittedValue());

            if (targetDate.before((Date) value)) {
              FacesMessage facesMessage = new FacesMessage();
              facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);

              String resumedMessage = MessageFormat.format(summaryMessage, ((UIInput) component).getSubmittedValue(), getComponentLabel(component),
                targetCalendar.getSubmittedValue(), getComponentLabel(targetCalendar));
              facesMessage.setSummary(resumedMessage);
              targetCalendar.setValid(false);

              if (detailMessage != null) {
                String detailedMessage = MessageFormat.format(detailMessage, ((UIInput) component).getSubmittedValue(), getComponentLabel(component),
                  targetCalendar.getSubmittedValue(), getComponentLabel(targetCalendar));
                facesMessage.setDetail(detailedMessage);
              }

              throw new ValidatorException(facesMessage);
            }
          } catch (ParseException parseException) {
            LOGGER.error("La date soumise '" + targetCalendar.getSubmittedValue() + "' n'est pas au format spécifié '" + targetCalendar.getSubmittedValue() + "'", parseException);
          }
        }
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
   * @param summaryMessage the summaryMessage to set
   */
  public void setSummaryMessage(String summaryMessage) {
    this.summaryMessage = summaryMessage;
  }

  /**
   * @param detailMessage the detailMessage to set
   */
  public void setDetailMessage(String detailMessage) {
    this.detailMessage = detailMessage;
  }

  /**
   * @param boundComponentId the boundComponentId to set
   */
  public void setBoundComponentId(String boundComponentId) {
    this.boundComponentId = boundComponentId;
  }
}
