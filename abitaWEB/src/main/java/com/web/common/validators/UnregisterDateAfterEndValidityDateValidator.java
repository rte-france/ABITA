/**
 *
 */
package com.web.common.validators;

import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Validateur permettant de vérifier si la date de sortie de logement que l'on a saisi est
 * postérieur à la date de sortie du dernier occupant du logement
 *
 * @author
 *
 */
public class UnregisterDateAfterEndValidityDateValidator implements Validator, Serializable {

  /** serialVersionUID */
  private static final long serialVersionUID = 4428649168401914731L;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(UnregisterDateAfterEndValidityDateValidator.class);

  /** Le nom de l'attribut indiquant le libellé du champ */
  private static final String ATTRIBUTE_LABEL = "label";

  /** Le nom de l'attribut indiquant l'erreur de la date saisie en fonction de la date d'entrée dans le parc immo */
  private static final String ATTRIBUTE_SUMMARY_MESSAGE = "summaryMessage";

  /** Le message d'erreur par defaut en cas d'échec de la récupération  de l'attribut "summaryMessage" */
  private static final String DEFAULT_VALIDATION_SUMMARY_MESSAGE = "Le champ \"Date de sortie\" doit \u00EAtre post\u00E9rieur \u00E0 la date de sortie du dernier occupant du logement.";

  /** Le service des contrats occupants */
  private transient IContractServiceFacade contractServiceFacade;

  @Override
  public void validate(FacesContext context, UIComponent component, Object value) {
    if (null != value) {
      Date unregisterDate = (Date) value;
      // on cherche l'identifiant du logement
      Long idHousing = (Long) component.getAttributes().get("housing");
      try {
        Long conflicts = contractServiceFacade.unregisterDateAfterEndValidityDate(idHousing, unregisterDate);
        if (conflicts != 0) {
          FacesMessage facesMessage = new FacesMessage();
          facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);

          String resumedMessage = MessageFormat.format(getSummaryMessage(component), ((UIInput) component).getSubmittedValue(), getComponentLabel(component));
          facesMessage.setSummary(resumedMessage);
          throw new ValidatorException(facesMessage);
        }
      } catch (ContractServiceFacadeException e) {
        LOGGER.error("Erreur survenue lors de la recuperation du nombre de conflit pour la date de sortie", e);
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

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

}
