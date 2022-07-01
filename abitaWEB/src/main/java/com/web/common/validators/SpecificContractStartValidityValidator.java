/**
 *
 */
package com.web.common.validators;

import com.abita.dto.HousingDTO;
import com.abita.dto.TenantDTO;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.web.shared.ConstantsWEB;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.component.calendar.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 *
 */
public class SpecificContractStartValidityValidator implements Validator, Serializable {

  /** serialVersionUID */
  private static final long serialVersionUID = -2905301685062765153L;

  /** Le nom de l'attribut indiquant le libellé du champ */
  private static final String ATTRIBUTE_LABEL = "label";

  /** Le nom de l'attribut indiquant l'erreur de la date saisie en fonction de la date d'entrée dans le parc immo */
  private static final String ATTRIBUTE_SUMMARY_MESSAGE = "summaryMessage";

  /** Le nom de l'attribut indiquant l'erreur d'un logement deja occupé à cette date */
  private static final String ATTRIBUTE_HOUSING_MESSAGE = "housingMessage";

  /** Le message d'erreur par defaut en cas d'échec de la récupération  de l'attribut "summaryMessage" */
  private static final String DEFAULT_VALIDATION_SUMMARY_MESSAGE = "Le champ \"Date de d\u00E9but de validit\u00E9\" "
    + "doit \u00EAtre post\u00E9rieur ou \u00E9gal \u00E0 la date d\u2019entr\u00E9e du logement.";

  /** Le message d'erreur par defaut en cas d'échec de la récupération de l'attribut "housingMessage" */
  private static final String DEFAULT_VALIDATION_HOUSING_MESSAGE = "Ce logement est d\u00E9j\u00E0 occup\u00E9 par un occupant pour la plage de date saisie.";

  /** Le service des contrats occupants */
  private transient IContractServiceFacade contractServiceFacade;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(SpecificContractStartValidityValidator.class);

  /*
   * (non-Javadoc)
   *
   * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
   */
  @Override
  public void validate(FacesContext context, UIComponent component, Object value) {
    // On part du principe que "value" est déjà une date au format valide
    if (value != null) {
      Date startValidityDate = (Date) value;
      Date otherDate = null;
      // on cherche un composant référençant un logement
      UIOutput housingOutput = (UIOutput) component.getAttributes().get("housing");
      // On part du principe que le champ "housing" contient déjà l'id d'un logement valide
      HousingDTO housingValue = (HousingDTO) housingOutput.getValue();
      // on cherche un composant référençant un occupant
      UIOutput tenantOutput = (UIOutput) component.getAttributes().get("tenant");
      // On part du principe que le champ "occupant" contient déjà la réference d'un occupant valide
      TenantDTO tenantValue = (TenantDTO) tenantOutput.getValue();
      if (housingValue != null) {
        otherDate = housingValue.getRegisterDate();
      }
      // Si aucune date de comparaison n'a été trouvé, on ne fait pas la validation
      if (otherDate != null && startValidityDate.compareTo(otherDate) < 0) {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);

        String resumedMessage = MessageFormat.format(getSummaryMessage(component), ((UIInput) component).getSubmittedValue(), getComponentLabel(component));
        facesMessage.setSummary(resumedMessage);

        throw new ValidatorException(facesMessage);
      } else {
        // On récupere le composant Calandar
        Calendar endCal = (Calendar) component.getAttributes().get("endDate");
        if (null != endCal) {
          // On récupere sa valeur (Date)
          SimpleDateFormat formatDate = new SimpleDateFormat(ConstantsWEB.PATTERN_DATE_MM_DD_YYYY);
          Date endDate = null;
          try {
            // On verifie si le calendrier n'est pas vide
            if (StringUtils.isNotEmpty((String) endCal.getSubmittedValue())) {
              endDate = formatDate.parse((String) endCal.getSubmittedValue());
            }

          } catch (ParseException parseException) {
            LOGGER.error("Erreur survenue lors de la récupération de la date de fin", parseException);
          }
          List<FacesMessage> facesMessagesList = new ArrayList<FacesMessage>();
          try {
            // On recupere une liste d'integer du service
            Long contractId = (Long) component.getAttributes().get("idContrat");
            if (contractId == null) {
              contractId = -1L;
            }
            if (null != housingValue && null != tenantValue) {
              List<Integer> validityList = contractServiceFacade.validityContractDate(contractId, startValidityDate, endDate, housingValue.getId(), tenantValue.getId());
              if (!validityList.isEmpty()) {
                // Les valeurs récupérés sont le nombre de conflit pour les logements et les occupants
                int housingCount = validityList.get(0);

                // on teste si il y a eu un conflit et on vérifie si c'est à cause du logement, si oui on crée un message d'erreur
                if (housingCount != 0) {
                  FacesMessage facesMessage = new FacesMessage();
                  facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);

                  String housingMessage = MessageFormat.format(getHousingMessage(component), ((UIInput) component).getSubmittedValue(), getComponentLabel(component));
                  facesMessage.setSummary(housingMessage);
                  facesMessagesList.add(facesMessage);
                }
                // Si on a récupéré un message d'erreur.
                if (!facesMessagesList.isEmpty()) {
                  endCal.setValid(false);
                  throw new ValidatorException(facesMessagesList);
                }
              }

            }

          } catch (ContractServiceFacadeException e) {
            LOGGER.error("Erreur survenue lors de la recuperation du nombre de conflit pour les dates de validité", e);
          }
        }
      }

    }
    // On invalide le calendrier de fin de validité si il a été rempli et on ne fait pas la validation.
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
   * Renvoie le libellé à afficher dans le message d'erreur
   *
   * La méthode renvoie la valeur de l'attribut "housingMessage" si elle est définie et non nulle ou le message d'erreur par défaut
   *
   * @param component le composant dont on veut le message d'erreur housingMessage
   *
   * @return le message d'erreur à afficher
   */
  private String getHousingMessage(final UIComponent component) {
    String msg = null;
    Object msgValue = component.getAttributes().get(ATTRIBUTE_HOUSING_MESSAGE);
    if (msgValue != null) {
      msg = String.valueOf(msgValue);
    } else {
      msg = DEFAULT_VALIDATION_HOUSING_MESSAGE;
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
