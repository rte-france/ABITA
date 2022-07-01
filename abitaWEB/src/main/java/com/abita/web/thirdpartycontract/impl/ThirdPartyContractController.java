package com.abita.web.thirdpartycontract.impl;

import com.abita.dto.*;
import com.abita.services.costcenter.ICostCenterService;
import com.abita.services.extendeduser.IExtendedUserService;
import com.abita.services.fieldofactivity.IFieldOfActivityService;
import com.abita.services.housing.IHousingServiceFacade;
import com.abita.services.paymentcycle.IPaymentCycleService;
import com.abita.services.thirdparty.IThirdPartyService;
import com.abita.services.thirdpartycontract.IThirdPartyContractServiceFacade;
import com.abita.services.thirdpartytermination.IThirdPartyTerminationService;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.web.thirdpartycontract.bean.ThirdPartyContractBean;
import com.abita.dto.*;
import com.abita.services.costcenter.exceptions.CostCenterServiceException;
import com.abita.services.extendeduser.exception.ExtendedUserServiceException;
import com.abita.services.fieldofactivity.exceptions.FieldOfActivityServiceException;
import com.abita.services.housing.exceptions.HousingServiceFacadeException;
import com.abita.services.paymentcycle.exceptions.PaymentCycleServiceException;
import com.abita.services.thirdparty.exceptions.ThirdPartyServiceException;
import com.abita.services.thirdpartycontract.exceptions.ThirdPartyContractServiceException;
import com.abita.services.thirdpartytermination.exceptions.ThirdPartyTerminationServiceException;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.dto.Group;
import com.dto.UserDTO;
import com.services.common.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.DateSelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe controleur de la page des gestion des contracts tiers
 * @author
 *
 */
public class ThirdPartyContractController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 2474479203060017755L;

  /** Code de navigation pour le retour à la consultation */
  private static final String CONSULTATION_NAVIGATION_CODE = "GO_TO_CONSULTATION";

  /** Nom du composant pour la date de résiliation du contrat */
  private static final String CANCELLATION_DATE_COMPONENT = ":frmCreateThirdParty:cancellation_date";

  /** Identifiant du champ caché dans la page de modification des contrats tiers */
  private static final String MODIFICATION_HIDDEN_INPUT_ID = "frmCreateThirdParty:checkId";

  /** Identifiant du champ caché dans la page de création des contrats tiers */
  private static final String CREATE_HIDDEN_INPUT_ID = "frmCreateThirdPartyContract:checkId";


  /**
   *
   * PROPRIETES
   *
   */

  /** Backing bean de la gestion des contracts tiers */
  private ThirdPartyContractBean thirdPartyContractBean;

  /** Service des contrats tiers */
  private transient IThirdPartyContractServiceFacade thirdPartyContractServiceFacade;

  /** Service des tiers */
  private transient IThirdPartyService thirdPartyService;

  /** Service des logements */
  private transient IHousingServiceFacade housingServiceFacade;

  /** Service des domaines d'activité */
  private transient IFieldOfActivityService fieldOfActivityService;

  /** Service des centres coût */
  private transient ICostCenterService costCenterService;

  /** Service des périodicité de paiement */
  private transient IPaymentCycleService paymentCycleService;

  /** Service pour la gestion des utilisateurs */
  private transient IExtendedUserService extendedUserService;

  /** Service des motifs de résiliation des tiers */
  private transient IThirdPartyTerminationService thirdPartyTerminationService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyContractController.class);

  /** Nom de code pour l’erreur technique */
  private static final String TECHNICAL_ERROR_CODE = "technical.error";

  /** Message lors d’une erreur de récupération des logement */
  private static final String GET_HOUSING_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des logements";

  /** Erreur si un utilisateur ouvre plusieurs onglets (Ecrasement des informations) */
  private static final String TECHNICAL_MULTIPLE_TABS_ERROR_CODE = "Impossible de sauvegarder. Plusieurs onglets ou fenêtres sont ouverts";


  /**
   *
   * METHODES
   *
   */

  /**
   * Méthode d'initialisation des beans
   */
  @PostConstruct
  public void init() {
    // Détermine le mode : Création ou Modification
    String action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("create");

    if (StringUtils.isEmpty(action)) {
      Long idThirdContractParty = null;

      if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_THIRD_PARTY_CONTRACT) != null) {
        idThirdContractParty = ((Number) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_THIRD_PARTY_CONTRACT)).longValue();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().keep(ConstantsWEB.ID_THIRD_PARTY_CONTRACT);
      } else {
        // Cas de la suppression qui effectue un post dont on sait pas d'où il sort et qui pert la variable flash
        if (thirdPartyContractBean.getThirdPartyContractDTO() != null && thirdPartyContractBean.getThirdPartyContractDTO().getId() != null) {
          idThirdContractParty = thirdPartyContractBean.getThirdPartyContractDTO().getId();
          FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_THIRD_PARTY_CONTRACT, idThirdContractParty);
        }
      }

      if (idThirdContractParty != null) {
        initThirdPartyContract(idThirdContractParty);
      }

      // On récupére le champ  caché dans la page de modification
      HtmlInputHidden checkId = (HtmlInputHidden) FacesContext.getCurrentInstance().getViewRoot().findComponent(MODIFICATION_HIDDEN_INPUT_ID);
      if (checkId != null) {
        checkId.setValue(String.valueOf(thirdPartyContractBean.getThirdPartyContractDTO().getId()));
      }
    } else {
      thirdPartyContractBean.setThirdPartyContractDTO(new ThirdPartyContractDTO());

      // On récupére le champ  caché dans la page de création
      HtmlInputHidden checkId = (HtmlInputHidden) FacesContext.getCurrentInstance().getViewRoot().findComponent(CREATE_HIDDEN_INPUT_ID);
      if (checkId != null) {
        checkId.setValue(String.valueOf(thirdPartyContractBean.getThirdPartyContractDTO().getId()));
      }
    }
  }

  /**
   * Initialisation du contrat tiers
   * @param idThirdContractParty identifiant du contrat tiers
   */
  private void initThirdPartyContract(Long idThirdContractParty) {
    try {
      thirdPartyContractBean.setThirdPartyContractDTO(thirdPartyContractServiceFacade.get(idThirdContractParty));
      // Si Périodicité paiment = mensuel
      if (thirdPartyContractBean.getThirdPartyContractDTO().getPaymentCycle().getLabel().equals(ConstantsWEB.MONTHLY_PAYMENT_CYCLE)) {
        thirdPartyContractBean.setBlnTerm(true);
      }
      if (null != thirdPartyContractBean.getThirdPartyContractDTO() && null != thirdPartyContractBean.getThirdPartyContractDTO().getRevisedRentDate()) {
        thirdPartyContractBean.setRevisedRentAmountMandatory(true);
      }
      if (null != thirdPartyContractBean.getThirdPartyContractDTO() && null != thirdPartyContractBean.getThirdPartyContractDTO().getRevisedRentAmount()) {
        thirdPartyContractBean.setRevisedRentDateMandatory(true);
      }
      if (null != thirdPartyContractBean.getThirdPartyContractDTO() && null != thirdPartyContractBean.getThirdPartyContractDTO().getRevisedExpectedChargeCostDate()) {
        thirdPartyContractBean.setRevisedExpectedChargeCostAmountMandatory(true);
      }
      if (null != thirdPartyContractBean.getThirdPartyContractDTO() && null != thirdPartyContractBean.getThirdPartyContractDTO().getRevisedExpectedChargeCostAmount()) {
        thirdPartyContractBean.setRevisedExpectedChargeCostDateMandatory(true);
      }
    } catch (ThirdPartyContractServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération du contrat tiers", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * Permet de supprimer un contrat occupant
   *
   * @return le résultat de l'action
   */
  public String deleteData() {
    try {
      thirdPartyContractServiceFacade.delete(thirdPartyContractBean.getThirdPartyContractDTO().getId());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("thirdpartycontract.result.delete"));
      FacesContext.getCurrentInstance().getExternalContext().getFlash().remove(ConstantsWEB.ID_THIRD_PARTY_CONTRACT);
    } catch (ThirdPartyContractServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la suppression du contrat tiers", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.result.delete.error", thirdPartyContractBean.getThirdPartyContractDTO().getId()));
    }
    return "EXIT_THIRD_PARTY_CONTRACT";
  }

  /**
   * Met à jour sur changement de la périodicité du paiement
   */
  public void updateTerm() {
    if (thirdPartyContractBean.getThirdPartyContractDTO().getPaymentCycle().getLabel().equals(ConstantsWEB.MONTHLY_PAYMENT_CYCLE)) {
      thirdPartyContractBean.setBlnTerm(true);
    } else {
      thirdPartyContractBean.setBlnTerm(false);
    }
  }

  /**
   * Gère la notion terme échu / échoir pour si obligatoire
   * @param event un evenement lors du changement de type salarié
   */
  public void setMandatoryFields(final AjaxBehaviorEvent event) {
    updateTerm();
    SelectOneMenu select = (SelectOneMenu) event.getComponent();
    PaymentCycleDTO type = (PaymentCycleDTO) select.getValue();
    thirdPartyContractBean.setBlnTermRequired(type.getLabel().equals(ConstantsWEB.MONTHLY_PAYMENT_CYCLE));
  }

  /**
   * Permet de savoir en modification si les champs sont obligatoires
   * @return true si obligatoire, false autrement
   */
  public boolean checkRequired() {
    if (thirdPartyContractBean.getBlnTermRequired() != null) {
      return thirdPartyContractBean.getBlnTermRequired();
    }
    return false;
  }

  /**
   * Permet de sauvegarder un contrat tiers
   *
   * @return la valeur de l'action
   */
  public String saveOrUpdateData() {
    UIInput checkId;
    if (FacesContext.getCurrentInstance().getViewRoot().findComponent(CREATE_HIDDEN_INPUT_ID) != null) {
      checkId = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(CREATE_HIDDEN_INPUT_ID);
    } else {
      checkId = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(MODIFICATION_HIDDEN_INPUT_ID);
    }
    if (checkId != null && checkId.getValue().equals(String.valueOf(thirdPartyContractBean.getThirdPartyContractDTO().getId()))) {

      if (thirdPartyContractBean.getThirdPartyContractDTO().getId() != null) {
        return updateData();
      } else {
        return saveData();
      }
    } else {

      LOGGER.error("PROBLEME D'OUVERTURE DE PLUSIEURS ONGLETS/FENETRES");
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_MULTIPLE_TABS_ERROR_CODE));
      return null;
    }
  }

  /**
   * Permet de renvoyer vers le mode Modification
   * @return la valeur de l'action
   */
  public String redirectUpdateData() {
    return "GO_TO_MODIFICATION";
  }

  /**
   * Action du bouton "Annuler"
   * Comme on peut arriver de la modification, avant d'annuler, on doit supprimer l'état de modification
   * @return le succès de l'opération d'annulation
   */
  public String cancel() {
    if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_THIRD_PARTY_CONTRACT) != null) {
      return CONSULTATION_NAVIGATION_CODE;
    }
    return "EXIT_THIRD_PARTY_CONTRACT";
  }

  /**
   * Effectue la sauvegarde du contrat tiers
   */
  private String saveData() {
    try {
      thirdPartyContractBean.getThirdPartyContractDTO().setReference(thirdPartyContractServiceFacade.generateReference());
      if (!thirdPartyContractBean.getThirdPartyContractDTO().getPaymentCycle().getLabel().equals(ConstantsWEB.MONTHLY_PAYMENT_CYCLE)) {
        thirdPartyContractBean.getThirdPartyContractDTO().setExpiryDate(null);
      }
      thirdPartyContractBean.getThirdPartyContractDTO().setClosedThirdPartyContract(false);
      long idThirdPartyContract = thirdPartyContractServiceFacade.create(thirdPartyContractBean.getThirdPartyContractDTO());
      FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_THIRD_PARTY_CONTRACT, idThirdPartyContract);
    } catch (ThirdPartyContractServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde du contrat tiers", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.creation.save.error"));
    }
    return CONSULTATION_NAVIGATION_CODE;
  }

  /**
   * Effectue la mise à jour du contrat tiers
   */
  private String updateData() {
    try {
      boolean anyError = false;

      ThirdPartyContractDTO thirdPartyContract = thirdPartyContractBean.getThirdPartyContractDTO();
      Date revisedRentDate = thirdPartyContract.getRevisedRentDate();
      Date startValidity = thirdPartyContract.getStartValidity();
      Date revisedExpectedChargeCostDate = thirdPartyContract.getRevisedExpectedChargeCostDate();
      Date cancellationDate = thirdPartyContract.getCancellationDate();
      PaymentCycleDTO paymentCycle = thirdPartyContract.getPaymentCycle();

      if (revisedRentDate != null && !startValidity.before(revisedRentDate)) {
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.error.effectdate.before.startvalidity"));
        Calendar calendar = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmCreateThirdParty:revisedRentDate_date");
        calendar.setValid(false);
        anyError = true;
      }

      if (revisedExpectedChargeCostDate != null && !startValidity.before(revisedExpectedChargeCostDate)) {
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.error.effectdate.before.startvalidity"));
        Calendar calendar = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmCreateThirdParty:revisedExpectedChargeCostDate_date");
        calendar.setValid(false);
        anyError = true;
      }

      LocalDate firstDayOfCycle = DateTimeUtils.getFirstDayOfCycle(LocalDate.now(), paymentCycle.getLabel());
      if (cancellationDate != null && cancellationDate.before(firstDayOfCycle.toDate()) && (revisedRentDate != null || revisedExpectedChargeCostDate != null)) {
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.error.cancellationdate.before.firstdayofcycle"));
        Calendar calendar = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent(CANCELLATION_DATE_COMPONENT);
        calendar.setValid(false);
        anyError = true;
      } else {

        if (cancellationDate != null && revisedRentDate != null && !cancellationDate.after(revisedRentDate)) {
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.error.effectdate.after.cancellationdate"));
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.error.cancellationdate.before.effectdate"));
          Calendar calendarRevision = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmCreateThirdParty:revisedRentDate_date");
          calendarRevision.setValid(false);
          Calendar calendarEnd = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent(CANCELLATION_DATE_COMPONENT);
          calendarEnd.setValid(false);
          anyError = true;
        }

        if (cancellationDate != null && revisedExpectedChargeCostDate != null && !cancellationDate.after(revisedExpectedChargeCostDate)) {
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.error.effectdate.after.cancellationdate"));
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.error.cancellationdate.before.effectdate"));
          Calendar calendarRevision = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent(":frmCreateThirdParty:revisedExpectedChargeCostDate_date");
          calendarRevision.setValid(false);
          Calendar calendarEnd = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent(CANCELLATION_DATE_COMPONENT);
          calendarEnd.setValid(false);
          anyError = true;
        }
      }


      if (!anyError) {

        thirdPartyContractServiceFacade.update(thirdPartyContract);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_THIRD_PARTY_CONTRACT, thirdPartyContract.getId());
        return CONSULTATION_NAVIGATION_CODE;
      } else {
        return "";
      }


    } catch (ThirdPartyContractServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la mise à jour du contrat tiers", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.creation.save.error"));
      return "";
    }
  }


  /**
   * Recupere le texte de la balise calendar "Date d'effet" des révisions des loyers mensuels afin de verifier
   * si elle est remplie. L'evenement en parametre est la selection d'une date.
   * @param event Event lorsque l'on selecte une date
   */
  public void isRevisedRentDateEmpty(DateSelectEvent event) {
    setRevisedRentAmountMandatory((Calendar) event.getComponent());
  }

  /**
   * Recupere le texte de la balise calendar "Date d'effet" des révisions des loyers mensuels afin de verifier
   * si elle est remplie. L'evenement en parametre est la selection d'une date.
   * @param event L'evenement est la modification d'une date de révision des loyers mensuels
   */
  public void isRevisedRentDateEmpty(AjaxBehaviorEvent event) {
    setRevisedRentAmountMandatory((Calendar) event.getComponent());
  }

  /**
   * mets les booleans de verifications des loyers mensuels vrai si le calendrier n'est pas vide
   * @param calendar le calendier de "date d'effet" de révision des loyers mensuels
   */
  private void setRevisedRentAmountMandatory(Calendar calendar) {
    Boolean mandatoryStatus = true;

    if (calendar.getSubmittedValue() != null) {
      mandatoryStatus = !calendar.getSubmittedValue().toString().isEmpty();
    }

    thirdPartyContractBean.setRevisedRentAmountMandatory(mandatoryStatus);
    thirdPartyContractBean.setRevisedRentAmountMandatoryCheck(thirdPartyContractBean.isRevisedRentAmountMandatory());
  }

  /**
   * Permet de savoir en modification si les champs des loyers mensuels est obligatoire
   * @return boolean de verification, vrai si il est obligatoire
   */
  public boolean checkRevisedRentAmountMandatory() {
    if (thirdPartyContractBean.getRevisedRentAmountMandatoryCheck() == null) {
      thirdPartyContractBean.setRevisedRentAmountMandatoryCheck(thirdPartyContractBean.isRevisedRentAmountMandatory());
    }
    return thirdPartyContractBean.getRevisedRentAmountMandatoryCheck();
  }

  /**
   * Recupere le texte de la balise calendar "Date d'effet" des révisions des charges prévisionnelles afin de verifier
   * si elle est remplie. L'evenement en parametre est la selection d'une date.
   * @param event Event lorsque l'on selecte une date
   */
  public void isRevisedExpectedChargeCostDateEmpty(DateSelectEvent event) {
    setRevisedExpectedChargeCostAmountMandatory((Calendar) event.getComponent());
  }

  /**
   * Recupere le texte de la balise calendar "Date d'effet" des révisions des charges prévisionnelles afin de verifier
   * si elle est remplie. L'evenement en parametre est la selection d'une date.
   * @param event L'evenement est la modification d'une date
   */
  public void isRevisedExpectedChargeCostDateEmpty(AjaxBehaviorEvent event) {
    setRevisedExpectedChargeCostAmountMandatory((Calendar) event.getComponent());
  }

  /**
   * mets les booleans de verifications du montant des charges prévisionnelles à vrai si le calendrier n'est pas vide
   * @param calendar le calendier de "date d'effet" des charges prévisionnelles
   */
  private void setRevisedExpectedChargeCostAmountMandatory(Calendar calendar) {
    Boolean mandatoryStatus = true;

    if (calendar.getSubmittedValue() != null) {
      mandatoryStatus = !calendar.getSubmittedValue().toString().isEmpty();
    }

    thirdPartyContractBean.setRevisedExpectedChargeCostAmountMandatory(mandatoryStatus);
    thirdPartyContractBean.setRevisedExpectedChargeCostAmountMandatoryCheck(thirdPartyContractBean.isRevisedExpectedChargeCostAmountMandatory());
  }

  /**
   * Permet de savoir en modification si les champs du montant des charges prévisionnelles sont obligatoires
   * @return boolean de verification, vrai si il est obligatoire
   */
  public boolean checkRevisedExpectedChargeCostAmountMandatory() {
    if (thirdPartyContractBean.getRevisedExpectedChargeCostAmountMandatoryCheck() == null) {
      thirdPartyContractBean.setRevisedExpectedChargeCostAmountMandatoryCheck(thirdPartyContractBean.isRevisedExpectedChargeCostAmountMandatory());
    }
    return thirdPartyContractBean.getRevisedExpectedChargeCostAmountMandatoryCheck();
  }

  /**
   * Recupere le texte de la balise calendar "montant de la révision du loyer mensuel" afin de verifier
   * si elle est remplie. L'evenement en parametre est la modification du montant
   * @param event L'evenement est la modification d'un champ
   */
  public void isRevisedRentAmountEmpty(AjaxBehaviorEvent event) {
    setRevisedRentDateMandatory((UIInput) event.getComponent());
  }

  /**
   * mets les booleans de verifications de la date du montant de la révision du loyuer mensuel vrai si le calendrier n'est pas vide
   * @param uIInput le champ du montant de la révision
   */
  private void setRevisedRentDateMandatory(UIInput uIInput) {
    thirdPartyContractBean.setRevisedRentDateMandatory(uIInput.getSubmittedValue() != null && !uIInput.getSubmittedValue().toString().isEmpty());
    thirdPartyContractBean.setRevisedRentDateMandatoryCheck(thirdPartyContractBean.isRevisedRentDateMandatory());
  }

  /**
   * Permet de savoir en modification si les champs de la date du montant de la révision du loyuer mensuel sont obligatoires
   * @return boolean de verification, vrai si il est obligatoire
   */
  public boolean checkRevisedRentDateMandatory() {
    if (thirdPartyContractBean.getRevisedRentDateMandatoryCheck() == null) {
      thirdPartyContractBean.setRevisedRentDateMandatoryCheck(thirdPartyContractBean.isRevisedRentDateMandatory());
    }
    return thirdPartyContractBean.getRevisedRentDateMandatoryCheck();
  }

  /**
   * Recupere le texte de la balise calendar "Montant de la révision des charges prévisionnelles" afin de verifier
   * si elle est remplie. L'evenement en parametre est la saisie d'un montnat
   * @param event L'evenement est la saisie d'un montnat
   */
  public void isRevisedExpectedChargeCostAmountEmpty(AjaxBehaviorEvent event) {
    setRevisedExpectedChargeCostDateMandatory((UIInput) event.getComponent());
  }

  /**
   * mets les booleans de verifications de la date d'effet du montant de la révision des charges prévisionnelles vrai si le calendrier n'est pas vide
   * @param uIInput le montant
   */
  private void setRevisedExpectedChargeCostDateMandatory(UIInput uIInput) {
    thirdPartyContractBean.setRevisedExpectedChargeCostDateMandatory(uIInput.getSubmittedValue() != null && !uIInput.getSubmittedValue().toString().isEmpty());
    thirdPartyContractBean.setRevisedExpectedChargeCostDateMandatoryCheck(thirdPartyContractBean.isRevisedExpectedChargeCostDateMandatory());
  }

  /**
   * Permet de savoir en modification si la date d'effet du montant de la révision des charges prévisionnelles est obligatoire
   * @return boolean de verification, vrai si il est obligatoire
   */
  public boolean checkRevisedExpectedChargeCostDateMandatory() {
    if (thirdPartyContractBean.getRevisedExpectedChargeCostDateMandatoryCheck() == null) {
      thirdPartyContractBean.setRevisedExpectedChargeCostDateMandatoryCheck(thirdPartyContractBean.isRevisedExpectedChargeCostDateMandatory());
    }
    return thirdPartyContractBean.getRevisedExpectedChargeCostDateMandatoryCheck();
  }

  /**
   * Récupère la liste des tiers
   * @return la liste des tiers
   */
  public List<ThirdPartyDTO> getAllThirdParty() {
    if (thirdPartyContractBean.getThirdPartyDTOs() == null || thirdPartyContractBean.getThirdPartyDTOs().isEmpty()) {
      try {
        thirdPartyContractBean.setThirdPartyDTOs(thirdPartyService.find());
      } catch (ThirdPartyServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des tiers", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return thirdPartyContractBean.getThirdPartyDTOs();
  }

  /**
   * Récupère la liste des logements
   * @return la liste des logements
   */
  public List<HousingDTO> getHousingsByAgencies() {
    if (thirdPartyContractBean.getHousingDTOs() == null || thirdPartyContractBean.getHousingDTOs().isEmpty()) {
      try {
        UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);

        if (user.getGroups().contains(Group.ADMINISTRATEUR)) {
          thirdPartyContractBean.setHousingDTOs(housingServiceFacade.find());
        } else {
          ExtendedUserDTO extendedUser = extendedUserService.get(user.getId());

          List<AgencyDTO> agencies = extendedUser.getAgencies();

          List<HousingDTO> housings = new ArrayList<HousingDTO>();

          for (AgencyDTO agency : agencies) {
            housings.addAll(housingServiceFacade.findHousingsByAgency(agency.getId()));
          }

          thirdPartyContractBean.setHousingDTOs(housings);
        }
      } catch (HousingServiceFacadeException ex) {
        LOGGER.error(GET_HOUSING_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      } catch (ExtendedUserServiceException ex) {
        LOGGER.error(GET_HOUSING_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return thirdPartyContractBean.getHousingDTOs();
  }

  /**
   * Récupère la liste des logements de toute l'application
   * @return la liste des logements de toutes l'application
   */
  public List<HousingDTO> getAllHousing() {
    if (thirdPartyContractBean.getAllHousingDTOs() == null || thirdPartyContractBean.getAllHousingDTOs().isEmpty()) {
      try {
        thirdPartyContractBean.setAllHousingDTOs(housingServiceFacade.find());

      } catch (HousingServiceFacadeException ex) {
        LOGGER.error(GET_HOUSING_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return thirdPartyContractBean.getAllHousingDTOs();
  }

  /**
   * Récupère la liste des domaines d'activité
   * @return la liste des domaines d'activité
   */
  public List<FieldOfActivityDTO> getAllFieldOfActivity() {
    if (thirdPartyContractBean.getFieldOfActivityDTOs() == null || thirdPartyContractBean.getFieldOfActivityDTOs().isEmpty()) {
      try {
        UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);

        if (user.getGroups().contains(Group.ADMINISTRATEUR)) {
          thirdPartyContractBean.setFieldOfActivityDTOs(fieldOfActivityService.find());
        } else {
          ExtendedUserDTO extendedUser = extendedUserService.get(user.getId());

          List<AgencyDTO> agencies = extendedUser.getAgencies();

          List<FieldOfActivityDTO> fieldsOfActivity = new ArrayList<FieldOfActivityDTO>();

          for (AgencyDTO agency : agencies) {
            fieldsOfActivity.addAll(fieldOfActivityService.findFieldOfActivityByAgency(agency.getId()));
          }

          thirdPartyContractBean.setFieldOfActivityDTOs(fieldsOfActivity);
        }

        // Domaine d’activité à R0ME par défaut dans le formulaire de création
        String action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("create");
        if (!StringUtils.isEmpty(action)) {
          defineDefaultFieldOfActivity(thirdPartyContractBean.getFieldOfActivityDTOs(), "R0ME");
        }

      } catch (FieldOfActivityServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des domaines d'activités", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      } catch (ExtendedUserServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des domaines d'activités", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return thirdPartyContractBean.getFieldOfActivityDTOs();
  }

  /**
   * Permet de définir le nom par défaut du domaine d’activité
   * @param fieldsOfActivity liste des domaines d’activités
   * @param label nom du domaine d’activité
   */
  private void defineDefaultFieldOfActivity(List<FieldOfActivityDTO> fieldsOfActivity, String label) {
    for (FieldOfActivityDTO fieldOfActivity : fieldsOfActivity) {
      if (fieldOfActivity.getLabel().equalsIgnoreCase(label)) {
        thirdPartyContractBean.getThirdPartyContractDTO().setFieldOfActivity(fieldOfActivity);
        break;
      }
    }
  }

  /**
   * Récupère la liste des centres de cout
   * @return la liste des centres de cout
   */
  public List<CostCenterDTO> getAllCostCenter() {
    if (thirdPartyContractBean.getCostCenterDTOs() == null || thirdPartyContractBean.getCostCenterDTOs().isEmpty()) {
      try {
        thirdPartyContractBean.setCostCenterDTOs(costCenterService.find());
      } catch (CostCenterServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des centres de cout", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return thirdPartyContractBean.getCostCenterDTOs();
  }

  /**
   * Récupère la liste des gestionnaires de contrat tiers
   * @return la liste des gestionnaires de contrat tiers
   */
  public List<PaymentCycleDTO> getAllPaymentCycle() {
    if (thirdPartyContractBean.getPaymentCycleDTOs() == null || thirdPartyContractBean.getPaymentCycleDTOs().isEmpty()) {
      try {
        thirdPartyContractBean.setPaymentCycleDTOs(paymentCycleService.find());
      } catch (PaymentCycleServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des cycles de paiement", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return thirdPartyContractBean.getPaymentCycleDTOs();
  }

  /**
   * Récupère la liste des motifs de résiliation
   * @return la liste des gestionnaires de contrat tiers
   */
  public List<ThirdPartyTerminationDTO> getAllThirdPartyTermination() {
    if (thirdPartyContractBean.getThirdPartyTerminationDTOs() == null || thirdPartyContractBean.getThirdPartyTerminationDTOs().isEmpty()) {
      try {
        thirdPartyContractBean.setThirdPartyTerminationDTOs(thirdPartyTerminationService.find());
      } catch (ThirdPartyTerminationServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des motifs de résiliation", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return thirdPartyContractBean.getThirdPartyTerminationDTOs();
  }

  /**
    *
    * GETTER/SETTER
    *
    *
    * **/

  /**
   * Getter du backing bean de la gestion des contracts tiers
   * @return the ThirdPartyContractBean
   */
  public ThirdPartyContractBean getThirdPartyContractBean() {
    return thirdPartyContractBean;
  }

  /**
   * Setter du backing bean de la gestion des contracts tiers
   * @param thirdPartyContractBean the thirdPartyContractBean to set
   */
  public void setThirdPartyContractBean(ThirdPartyContractBean thirdPartyContractBean) {
    this.thirdPartyContractBean = thirdPartyContractBean;
  }

  /**
   * @param thirdPartyContractServiceFacade the thirdPartyContractServiceFacade to set
   */
  public void setThirdPartyContractServiceFacade(IThirdPartyContractServiceFacade thirdPartyContractServiceFacade) {
    this.thirdPartyContractServiceFacade = thirdPartyContractServiceFacade;
  }

  /**
   * @param thirdPartyService the thirdPartyService to set
   */
  public void setThirdPartyService(IThirdPartyService thirdPartyService) {
    this.thirdPartyService = thirdPartyService;
  }

  /**
   * @param fieldOfActivityService the fieldOfActivityService to set
   */
  public void setFieldOfActivityService(IFieldOfActivityService fieldOfActivityService) {
    this.fieldOfActivityService = fieldOfActivityService;
  }

  /**
   * @param costCenterService the costCenterService to set
   */
  public void setCostCenterService(ICostCenterService costCenterService) {
    this.costCenterService = costCenterService;
  }

  /**
   * @param paymentCycleService the paymentCycleService to set
   */
  public void setPaymentCycleService(IPaymentCycleService paymentCycleService) {
    this.paymentCycleService = paymentCycleService;
  }

  /**
   * @param thirdPartyTerminationService the thirdPartyTerminationService to set
   */
  public void setThirdPartyTerminationService(IThirdPartyTerminationService thirdPartyTerminationService) {
    this.thirdPartyTerminationService = thirdPartyTerminationService;
  }

  /**
   * @param housingServiceFacade the housingServiceFacade to set
   */
  public void setHousingServiceFacade(IHousingServiceFacade housingServiceFacade) {
    this.housingServiceFacade = housingServiceFacade;
  }

  /**
   * @param extendedUserService the extendedUserService to set
   */
  public void setExtendedUserService(IExtendedUserService extendedUserService) {
    this.extendedUserService = extendedUserService;
  }
}
