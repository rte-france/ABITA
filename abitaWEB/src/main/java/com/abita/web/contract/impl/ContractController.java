package com.abita.web.contract.impl;

import com.abita.dto.*;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.costcenter.ICostCenterService;
import com.abita.services.detailcron.IDetailCronService;
import com.abita.services.extendeduser.IExtendedUserService;
import com.abita.services.fieldofactivity.IFieldOfActivityService;
import com.abita.services.historycontract.IHistoryContractServiceFacade;
import com.abita.services.housing.IHousingServiceFacade;
import com.abita.services.paymentmethod.IPaymentMethodService;
import com.abita.services.renttypology.IRentTypologyService;
import com.abita.services.tenant.ITenantServiceFacade;
import com.abita.services.termination.ITerminationService;
import com.abita.util.contract.ContractUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.web.contract.bean.ContractBean;
import com.abita.dto.unpersist.ContractInputDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.costcenter.exceptions.CostCenterServiceException;
import com.abita.services.detailcron.exceptions.DetailCronServiceException;
import com.abita.services.extendeduser.exception.ExtendedUserServiceException;
import com.abita.services.fieldofactivity.exceptions.FieldOfActivityServiceException;
import com.abita.services.historycontract.exceptions.HistoryContractServiceException;
import com.abita.services.housing.exceptions.HousingServiceFacadeException;
import com.abita.services.paymentmethod.exceptions.PaymentMethodServiceException;
import com.abita.services.renttypology.exceptions.RentTypologyServiceException;
import com.abita.services.tenant.exceptions.TenantServiceFacadeException;
import com.abita.services.termination.exceptions.TerminationServiceException;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.dto.Group;
import com.dto.UserDTO;
import com.services.common.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.*;
import org.primefaces.component.calendar.Calendar;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author
 */
public class ContractController extends AbstractGenericController {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -860519027443216772L;

  /**
   *
   * PROPRIETES
   *
   */

  /**
   * Backing bean de la gestion des contracts occupant
   */
  private ContractBean contractBean;

  /**
   * Service de contrats occupant
   */
  private transient IContractServiceFacade contractServiceFacade;

  /**
   * Service des occupants
   */
  private transient ITenantServiceFacade tenantServiceFacade;

  /**
   * Service des logements
   */
  private transient IHousingServiceFacade housingServiceFacade;

  /**
   * Service de centre coût
   */
  private transient ICostCenterService costCenterService;

  /**
   * Service de Motifs de résiliation
   */
  private transient ITerminationService terminationService;

  /**
   * Service de domaine d'activité
   */
  private transient IFieldOfActivityService fieldOfActivityService;

  /**
   * Service de methodes de paiement
   */
  private transient IPaymentMethodService paymentMethodService;

  /**
   * Service de typologies de loyer
   */
  private transient IRentTypologyService rentTypologyService;

  /**
   * Service du details des cron
   */
  private transient IDetailCronService detailCronService;

  /**
   * Service pour la gestion des utilisateurs
   */
  private transient IExtendedUserService extendedUserService;

  /**
   * Service d'historisation des contrats occupants
   */
  private transient IHistoryContractServiceFacade historyContractServiceFacade;

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

  /**
   * Message lors d’une erreur de récupération des logements
   */
  private static final String GET_HOUSINGS_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des logements";

  /**
   * Message lors d’une erreur de récupération des contrats occupants
   */
  private static final String GET_CONTRACTS_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des contrats occupants";

  /**
   * Message lors d’une erreur de récupération des occupants
   */
  private static final String GET_TENANTS_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des occupants";

  /**
   * Message lors d’une erreur de récupération des CRONS
   */
  private static final String GET_CRONS_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération du détail des CRON";

  /**
   * Message lors d’une erreur de récupération des typologies de loyer
   */
  private static final String GET_RENT_TYPOLOGIES_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des typologies de loyer";

  /**
   * Message lors d’une erreur de récupération des méthodes de paiement
   */
  private static final String GET_PAYMENT_METHODS_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des methodes de paiement";

  /**
   * Message lors d’une erreur de récupération des domaines d’æctivité
   */
  private static final String GET_FIELDS_OF_ACTIVITY_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des domaines d'activité";

  /**
   * Message lors d’une erreur de récupération des motifs de résiliation
   */
  private static final String GET_TERMINATIONS_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des motifs de resiliations";

  /**
   * Message lors d’une erreur de récupération des centres de coût
   */
  private static final String GET_COST_CENTERS_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des centres coût";

  /**
   * Message lors d’une erreur de sauvegarde du contrat occupant
   */
  private static final String SAVE_CONTRACT_ERROR_MESSAGE = "Une erreur est survenue lors de la sauvegarde du contrat occupant";

  /**
   * Message lors d’une erreur de suppression du contrat occupant
   */
  private static final String DELETE_ERROR_MESSAGE = "Une erreur est survenue lors de la suppression du contrat occupant";

  /**
   * Message lors d’une erreur de recherche des logements
   */
  private static final String SEARCH_HOUSING_ERROR_MESSAGE = "Une erreur est survenue lors de la recherche des logements";

  /**
   * Message lors d’une erreur de recherche des occupants
   */
  private static final String SEARCH_TENANTS_ERROR_MESSAGE = "Une erreur est survenue lors de la recherche des occupants";

  /**
   * Message lors d’une erreur de mise à jour du contrat occupant
   */
  private static final String UPDATE_CONTRACT_ERROR_MESSAGE = "Une erreur est survenue lors de la mise à jour du contrat occupant";

  /**
   * Message lors d’une tentative de création de contrat occupant pendant la phase de génération
   */
  private static final String CREATION_UNAVAILABLE_PERIOD_ERROR_MESSAGE = "La création d'un contrat occupant entre la génération des fichiers ARTESIS et GCP n'est pas autorisée";

  /**
   * Message lors d’une tentative de modification de contrat occupant pendant la phase de génération
   */
  private static final String MODIFICATION_UNAVAILABLE_PERIOD_ERROR_MESSAGE = "La modification d'un contrat occupant entre la génération des fichiers ARTESIS et GCP n'est pas autorisée";

  /**
   * Code pour l’erreur technique
   */
  private static final String TECHNICAL_ERROR_CODE = "technical.error";

  /**
   * Code pour l’erreur de sauvegarde
   */
  private static final String SAVE_ERROR_CODE = "contract.creation.save.error";

  /**
   * Code pour la suppression
   */
  private static final String DELETE_CODE = "contract.result.delete";

  /**
   * Code pour l’erreur de suppression
   */
  private static final String DELETE_ERROR_CODE = "contract.result.delete.error";

  /**
   * Code du paramètres de création
   */
  private static final String CREATE_PARAMETER = "create";

  /**
   * Code de navigation vers la page de sortie
   */
  private static final String EXIT_NAVIGATION_CODE = "EXIT_TENANT_CONTRACT";

  /**
   * Code de navigation vers la page de consultation
   */
  private static final String CONSULTATION_NAVIGATION_CODE = "GO_TO_CONSULTATION";

  /**
   * Code de navigation vers la page de modification
   */
  private static final String MODIFICATION_NAVIGATION_CODE = "GO_TO_MODIFICATION";

  /**
   * Code pour le composant de l’occupant
   */
  private static final String TENANT_COMPONENT = "frmCreateContract:tenant";

  /**
   * Code pour le composant du logement
   */
  private static final String HOUSING_COMPONENT = "frmCreateContract:housing";

  /**
   * Le nombre de minutes à ajouter afin d'être sur d'être apres tous les traitements
   */
  private static final int MINUTES_FOR_THE_END_OF_TREATMENT = 10;

  /**
   * Méthode d'initialisation des beans
   */
  @PostConstruct
  public void init() {
    // Détermine le mode : Création ou Modification
    String action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(CREATE_PARAMETER);

    if (StringUtils.isEmpty(action)) {
      Long idContract = null;

      if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_CONTRACT) != null) {
        idContract = ((Number) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_CONTRACT)).longValue();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().keep(ConstantsWEB.ID_CONTRACT);
      } else {
        // Cas de la suppression qui effectue un post dont on sait pas d'où il sort et qui pert la variable flash
        if (contractBean.getContractInputDTO() != null && contractBean.getContractInputDTO().getId() != null) {
          idContract = contractBean.getContractInputDTO().getId();
          FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_CONTRACT, idContract);
        }
      }
      if (idContract != null) {
        initContract(idContract);
      }
    } else {
      contractBean.setContractInputDTO(new ContractInputDTO());
      contractBean.getContractInputDTO().setTerminationSavingsPayment(Boolean.FALSE);
      if (null == contractBean.getHousingCriteria()) {
        contractBean.setHousingCriteria(new HousingCriteriaDTO());
      }
      if (null == contractBean.getTenantCriteria()) {
        contractBean.setTenantCriteria(new TenantCriteriaDTO());
      }
    }
  }

  /**
   * Initialise le contrat occupant
   *
   * @param idContract identifiant du contrat occupant
   */
  private void initContract(Long idContract) {
    try {
      contractBean.setContractDTO(contractServiceFacade.get(idContract));
      contractBean.setContractInputDTO(contractServiceFacade.mapToInputDTO(contractBean.getContractDTO()));
      if (null != contractBean.getContractInputDTO() && null != contractBean.getContractInputDTO().getEndValidityDate()) {
        contractBean.setTerminationMandatory(true);
      }

      LocalDateTime generationDate = LocalDateTime.now();
      DetailCronDTO detailCronDTO = detailCronService.getCronsOfCurrentMonth();

      if (detailCronService.getLastCronOfTheMonth(detailCronDTO).isBefore(generationDate.toDateTime())) {
        generationDate = generationDate.plusMonths(1);
      }

      // On ajoute au loyer prélevé cumulé le loyer prélevé du mois en cours
      BigDecimal addedWithdrawnRent = contractBean.getContractInputDTO().getPlainAddedWithdrawnRent();
      if (contractBean.getContractInputDTO().getNetAgentRent() != null || contractBean.getContractInputDTO().getExtraRent() != null) {
        addedWithdrawnRent = addedWithdrawnRent.add(ContractUtils.addedWithdrawnRentWithProrata(contractBean.getContractDTO(), generationDate.toLocalDate()));
      }
      contractBean.getContractInputDTO().setPlainTempAddedWithdrawnRent(addedWithdrawnRent);
      // On ajoute le pécule de fin d'occupation du mois en cours
      if (contractBean.getContractInputDTO().getTerminationSavings()) {
        BigDecimal terminationSavingsAmount = contractBean.getContractInputDTO().getPlainTerminationSavingAmount();
        terminationSavingsAmount = terminationSavingsAmount.add(ContractUtils.terminationSavingMonth(contractBean.getContractInputDTO(), generationDate.toLocalDate()));
        contractBean.getContractInputDTO().setPlainTempTerminationSavingAmount(terminationSavingsAmount);
      } else {
        contractBean.getContractInputDTO().setPlainTempTerminationSavingAmount(BigDecimal.ZERO);
      }
    } catch (ContractServiceFacadeException ex) {
      LOGGER.error(GET_CONTRACTS_ERROR_MESSAGE, ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    } catch (DetailCronServiceException ex) {
      LOGGER.error(GET_CONTRACTS_ERROR_MESSAGE, ex);
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
      contractServiceFacade.delete(contractBean.getContractInputDTO().getId());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage(DELETE_CODE));
      FacesContext.getCurrentInstance().getExternalContext().getFlash().remove(ConstantsWEB.ID_CONTRACT);
    } catch (ContractServiceFacadeException ex) {
      LOGGER.error(DELETE_ERROR_MESSAGE, ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(DELETE_ERROR_CODE, contractBean.getContractInputDTO().getId()));
    }
    return EXIT_NAVIGATION_CODE;
  }

  /**
   * Lance la recherche des logements
   *
   * @param actionEvent : l'event
   */
  public void searchHousingData(ActionEvent actionEvent) {
    try {
      // On récupére l'utilisateur
      contractBean.setTenantCriteria(new TenantCriteriaDTO());
      UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);
      // SI il est utilisateur, on récupère toutes les logements
      if (user.getGroups().contains(Group.ADMINISTRATEUR)) {
        contractBean.setLstHousingResultSearch(housingServiceFacade.findByCriteria(contractBean.getHousingCriteria(), contractBean.getTenantCriteria()));
      } else {
        ExtendedUserDTO extendedUser = extendedUserService.get(user.getId());
        // On récupére les agences de l'utilisateur
        List<AgencyDTO> agencies = extendedUser.getAgencies();

        List<HousingDTO> housings = new ArrayList<HousingDTO>();

        // On récupére les logements selon leur agence
        for (AgencyDTO agency : agencies) {
          contractBean.getHousingCriteria().setAgency(agency);
          housings.addAll(housingServiceFacade.findByCriteria(contractBean.getHousingCriteria(), contractBean.getTenantCriteria()));
        }
        contractBean.setLstHousingResultSearch(housings);
      }
    } catch (HousingServiceFacadeException ex) {
      LOGGER.error(SEARCH_HOUSING_ERROR_MESSAGE, ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    } catch (ExtendedUserServiceException e) {
      LOGGER.error(SEARCH_HOUSING_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * Permet de valider la recherche du logement
   */
  public void validateHousingSearch() {
    // On remet à zéro l'inputText de la réferene des logements
    UIInput housing = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(HOUSING_COMPONENT);
    housing.setValue(null);
    housing.setSubmittedValue(null);
    housing.setLocalValueSet(false);
    housing.setValid(true);
    // On set la nouvelle valeur
    contractBean.getContractInputDTO().setHousing(contractBean.getSelectedHousing());
  }

  /**
   * Permet d'annuler la recherche du logement
   */
  public void cancelHousingSearch() {
    contractBean.setHousingCriteria(new HousingCriteriaDTO());
    contractBean.setLstHousingResultSearch(null);
    contractBean.setSelectedHousing(null);
  }

  /**
   * Lance la recherche des occupants
   *
   * @param actionEvent : l'event
   */
  public void searchTenantData(ActionEvent actionEvent) {
    try {
      contractBean.setHousingCriteria(new HousingCriteriaDTO());
      contractBean.setLstTenantResultSearch(tenantServiceFacade.findByCriteria(contractBean.getTenantCriteria(), contractBean.getHousingCriteria()));
    } catch (TenantServiceFacadeException ex) {
      LOGGER.error(SEARCH_TENANTS_ERROR_MESSAGE, ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * Permet de valider la recherche du logement
   */
  public void validateTenantSearch() {
    // On remet à zéro l'inputText de la réferene des occupants
    UIInput tenant = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(TENANT_COMPONENT);
    tenant.setValue(null);
    tenant.setSubmittedValue(null);
    tenant.setLocalValueSet(false);
    tenant.setValid(true);
    // On set la nouvelle valeur
    contractBean.getContractInputDTO().setTenant(contractBean.getSelectedTenant());
  }

  /**
   * Permet d'annuler la recherche du logement
   */
  public void cancelTenantSearch() {
    contractBean.setTenantCriteria(new TenantCriteriaDTO());
    contractBean.setLstTenantResultSearch(null);
    contractBean.setSelectedTenant(null);
  }

  /**
   * Permet de savoir si la date est déjà passée
   *
   * @return oui si date déjà passée
   */
  public boolean isStartValidityDateAlreadyPast() {
    boolean isAlreadyPast = false;
    if (contractBean.getContractInputDTO().getStartValidityDate() != null && contractBean.getContractInputDTO().getStartValidityDate().before(new LocalDate().toDate())) {
      isAlreadyPast = true;
    }
    return isAlreadyPast;
  }

  /**
   * Permet de sauvegarder un contrat occupant
   *
   * @return la valeur de l'action
   */
  public String saveOrUpdateData() {

    if (contractBean.getContractInputDTO().getId() != null) {
      updateData();
    } else {
      saveData();
    }

    return CONSULTATION_NAVIGATION_CODE;
  }

  /**
   * Permet de renvoyer vers le mode Modification
   *
   * @return la valeur de l'action
   */
  public String redirectUpdateData() {
    return MODIFICATION_NAVIGATION_CODE;
  }

  /**
   * Permet de savoir si la date actuelle entre deux générations
   *
   * @return oui si la date actuelle est entre deux générations
   */
  public boolean isBetweenGenerations() {
    boolean isBetweenGenerations = false;
    try {
      // On récupère la liste du detail des CRON
      DetailCronDTO detailCronDTO;
      detailCronDTO = detailCronService.getCronsOfCurrentMonth();

      DateTime now = new DateTime();
      DateTime lastCron = detailCronService.getLastCronOfTheMonth(detailCronDTO);
      lastCron = lastCron.plusMinutes(MINUTES_FOR_THE_END_OF_TREATMENT);

      if (detailCronService.getFirstCronOfTheMonth(detailCronDTO).isBefore(now) && lastCron.isAfter(now)) {
        isBetweenGenerations = true;
      }
    } catch (DetailCronServiceException e) {
      LOGGER.error(GET_CRONS_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
    return isBetweenGenerations;

  }

  /**
   * Permet d’afficher le message d’erreur si la date actuelle est entre deux générations
   */
  public void isBetweenGenerationsErrorMessage() {
    try {
      // On récupère la liste du detail des CRON
      DetailCronDTO detailCronDTO;
      detailCronDTO = detailCronService.getCronsOfCurrentMonth();

      DateTime now = new DateTime();
      DateTime lastCron = detailCronService.getLastCronOfTheMonth(detailCronDTO);
      lastCron = lastCron.plusMinutes(MINUTES_FOR_THE_END_OF_TREATMENT);
/*
      if (detailCronService.getFirstCronOfTheMonth(detailCronDTO).isBefore(now) && lastCron.isAfter(now)) {
        // Détermine le mode : Création ou Modification
        String action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(CREATE_PARAMETER);

        if (StringUtils.isEmpty(action)) {
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(MODIFICATION_UNAVAILABLE_PERIOD_ERROR_MESSAGE));
        } else {
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(CREATION_UNAVAILABLE_PERIOD_ERROR_MESSAGE));
        }
      }

 */
    } catch (DetailCronServiceException e) {
      LOGGER.error(GET_CRONS_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * Effectue la sauvegarde du contrat occupant
   */
  private void saveData() {
    try {
      contractBean.getContractInputDTO().setClosedContract(false);
      long idContract = contractServiceFacade.create(contractBean.getContractInputDTO());
      FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_CONTRACT, idContract);
    } catch (ContractServiceFacadeException e) {
      LOGGER.error(SAVE_CONTRACT_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(SAVE_ERROR_CODE));
    }
  }

  /**
   * Effectue la mise a jour du contrat occupant
   */
  private void updateData() {
    try {

      contractServiceFacade.update(contractBean.getContractInputDTO(), contractBean.getContractDTO());
      FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_CONTRACT, contractBean.getContractInputDTO().getId());

      checkRegularisationInModification(contractBean.getContractInputDTO().getId());

    } catch (ContractServiceFacadeException e) {
      LOGGER.error(UPDATE_CONTRACT_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(SAVE_ERROR_CODE));
    }
  }

  /**
   * Permet de verifier si le contrat est rétroactif lors d'une modification
   *
   * @param idContract l'identifiant du contrat occupant
   */
  private void checkRegularisationInModification(long idContract) {
    try {
      ContractDTO contractDTO = contractServiceFacade.get(idContract);
      ContractInputDTO contractInputDTO = contractServiceFacade.mapToInputDTO(contractDTO);

      if (contractDTO.getRetroactivitysMonths() <= 0) {
        // On récupère la liste du detail des CRON
        DetailCronDTO detailCronDTO = detailCronService.getCronsOfCurrentMonth();

        // La date du jour
        LocalDateTime now = new LocalDateTime();

        // On récupére l'historisation du mois précedent
        HistoryContractDTO historyContractDTO = historyContractServiceFacade.get(idContract, now.minusMonths(1).getMonthOfYear(), now.minusMonths(1).getYear());

        // Le dernier CRON
        LocalDateTime lastCron = detailCronService.getLastCronOfTheMonth(detailCronDTO).toLocalDateTime();

        // On verifie si la génération du mois en cours est passée
        boolean generationIsAlreadyPast = now.isAfter(lastCron);

        Boolean activeRetroactivity = isActiveRetroactivity(historyContractDTO, generationIsAlreadyPast);

        // On test pour savoir si le contrat a les caractéristiques nécessaires pour être rétroactif
        if (activeRetroactivity) {
          Integer months = modificationHistorizeDatas(idContract, generationIsAlreadyPast, contractInputDTO);
          contractInputDTO.setRetroactivitysMonths(months);
        } else {
          contractInputDTO.setRetroactivitysMonths(0);
        }
      } else {
        // On remplace toutes les historisations pour pouvoir recalculer les montants
        historyContractServiceFacade.replaceAllTemporaryContractHistorisations(contractDTO);
      }
      contractServiceFacade.update(contractInputDTO, contractDTO);
    } catch (DetailCronServiceException e) {
      LOGGER.error(SAVE_CONTRACT_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(SAVE_ERROR_CODE));
    } catch (HistoryContractServiceException e) {
      LOGGER.error(SAVE_CONTRACT_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(SAVE_ERROR_CODE));
    } catch (ContractServiceFacadeException e) {
      LOGGER.error(SAVE_CONTRACT_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(SAVE_ERROR_CODE));
    }
  }

  private boolean isActiveRetroactivity(HistoryContractDTO historyContractDTO, Boolean generationIsAlreadyPast) {
    Boolean activeRetroactivity = false;

    // La date du jour
    LocalDate now = LocalDate.now();
    // Le 1er jour du mois en cours
    Date firstDayOfMonth = com.abita.util.dateutil.DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(now).toDate();
    // Le dernier jour du mois en cours
    Date lastDayOfMonth = com.abita.util.dateutil.DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toDate();

    Date endValidityDate = contractBean.getContractInputDTO().getEndValidityDate();

    if (historyContractDTO != null && endValidityDate != null) {

      Boolean endValidityOfHistorizationInMonth = historyContractDTO.getEndValidityDate() == null || firstDayOfMonth.before(historyContractDTO.getEndValidityDate());

      Boolean endValidityBeforeEndValidityOfHistorization = historyContractDTO.getEndValidityDate() == null || endValidityDate.before(historyContractDTO.getEndValidityDate());

      Boolean retroactivityWithoutGenerationAlreadyPast = !generationIsAlreadyPast && endValidityOfHistorizationInMonth && endValidityDate.before(firstDayOfMonth)
        && endValidityBeforeEndValidityOfHistorization;

      Boolean retroactivityWithGenerationAlreadyPast = generationIsAlreadyPast && endValidityOfHistorizationInMonth && endValidityBeforeEndValidityOfHistorization
        && endValidityDate.before(lastDayOfMonth);

      activeRetroactivity = retroactivityWithoutGenerationAlreadyPast || retroactivityWithGenerationAlreadyPast;
    }

    return activeRetroactivity;
  }

  /**
   * Permet de savoir combien de mois sont rétroactifs en modification
   *
   * @param idContract              l'identifiant du contrat occupant
   * @param generationIsAlreadyPast flag pour savoir si la génération des fichiers est passée
   * @param contractInputDTO        contrat
   *                                return nombre de mois rétroactifs
   */
  private Integer modificationHistorizeDatas(long idContract, boolean generationIsAlreadyPast, ContractInputDTO contractInputDTO) {
    // On recupere la nouvelle date de fin du contrat
    LocalDate newEndValidityDate = new LocalDate(contractInputDTO.getEndValidityDate());

    // On calcule la difference de mois entre deux dates
    Months months = Months.monthsBetween(new YearMonth(), new YearMonth(newEndValidityDate));

    if (generationIsAlreadyPast) {
      months = months.minus(1);
    }

    if (months.getMonths() > com.abita.util.dateutil.DateTimeUtils.MONTHS_IN_A_YEAR) {
      months = Months.months(com.abita.util.dateutil.DateTimeUtils.MONTHS_IN_A_YEAR);
    }

    if (months.getMonths() < -com.abita.util.dateutil.DateTimeUtils.MONTHS_IN_A_YEAR) {
      months = Months.months(-DateTimeUtils.MONTHS_IN_A_YEAR);
    }

    return months.getMonths();
  }

  /**
   * Permet de retourner en arrière
   *
   * @return Le chemin de retour
   */
  public String cancel() {
    if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_CONTRACT) != null) {
      return CONSULTATION_NAVIGATION_CODE;
    }
    return EXIT_NAVIGATION_CODE;
  }

  /**
   * Recupere le texte de la balise calendar « endValidityDate » afin de verifier
   * si elle est remplie. L'evenement en parametre est la selection d'une date.
   *
   * @param event L'evenement est la modification d'une date
   */
  public void isEmpty(AjaxBehaviorEvent event) {
    setTerminationMandatory((Calendar) event.getComponent());
  }

  /**
   * mets les booleans de verifications vrai si le calendrier n'est pas vide
   *
   * @param calendar le calendier de « endValidityDate »
   */
  private void setTerminationMandatory(Calendar calendar) {
    contractBean.setTerminationMandatory(null != calendar.getValue());
    contractBean.setTerminationMandatoryCheck(contractBean.isTerminationMandatory());
  }

  /**
   * Permet de savoir en modification si les champs sont obligatoires
   *
   * @return boolean de verification, vrai si il est obligatoire
   */
  public boolean checkTerminationMandatory() {
    if (contractBean.getTerminationMandatoryCheck() == null) {
      contractBean.setTerminationMandatoryCheck(contractBean.isTerminationMandatory());
    }
    return contractBean.getTerminationMandatoryCheck();
  }

  /**
   * Récupère la liste des occupants
   *
   * @return la liste des occupants
   */
  public List<TenantDTO> getAllTenant() {
    if (contractBean.getTenantDTOs() == null || contractBean.getTenantDTOs().isEmpty()) {
      try {
        contractBean.setTenantDTOs(tenantServiceFacade.find());
      } catch (TenantServiceFacadeException ex) {
        LOGGER.error(GET_TENANTS_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return contractBean.getTenantDTOs();
  }

  /**
   * Récupère la liste des logements
   *
   * @return la liste des logements
   */
  public List<HousingDTO> getHousingsByAgencies() {
    if (contractBean.getHousingDTOs() == null || contractBean.getHousingDTOs().isEmpty()) {
      try {
        UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);

        if (user.getGroups().contains(Group.ADMINISTRATEUR)) {
          contractBean.setHousingDTOs(housingServiceFacade.find());
        } else {
          ExtendedUserDTO extendedUser = extendedUserService.get(user.getId());

          List<AgencyDTO> agencies = extendedUser.getAgencies();

          List<HousingDTO> housings = new ArrayList<HousingDTO>();

          for (AgencyDTO agency : agencies) {
            housings.addAll(housingServiceFacade.findHousingsByAgency(agency.getId()));
          }

          contractBean.setHousingDTOs(housings);
        }
      } catch (HousingServiceFacadeException ex) {
        LOGGER.error(GET_HOUSINGS_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      } catch (ExtendedUserServiceException ex) {
        LOGGER.error(GET_HOUSINGS_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return contractBean.getHousingDTOs();
  }

  /**
   * Récupère la liste des logements
   *
   * @return la liste des logements
   */
  public List<HousingDTO> getAllHousing() {
    if (contractBean.getAllHousingDTOs() == null || contractBean.getAllHousingDTOs().isEmpty()) {
      try {
        contractBean.setAllHousingDTOs(housingServiceFacade.find());
      } catch (HousingServiceFacadeException ex) {
        LOGGER.error(GET_HOUSINGS_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return contractBean.getAllHousingDTOs();
  }

  /**
   * Récupère la liste des centres coût
   *
   * @return la liste des centres coût
   */
  public List<CostCenterDTO> getAllCostCenter() {
    if (contractBean.getCostCenterDTOs() == null || contractBean.getCostCenterDTOs().isEmpty()) {
      try {
        contractBean.setCostCenterDTOs(costCenterService.find());
      } catch (CostCenterServiceException ex) {
        LOGGER.error(GET_COST_CENTERS_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return contractBean.getCostCenterDTOs();
  }

  /**
   * Récupère la liste des motifs de resiliations
   *
   * @return la liste des motifs de resiliations
   */
  public List<TerminationDTO> getAllTermination() {
    if (contractBean.getTerminationDTOs() == null || contractBean.getTerminationDTOs().isEmpty()) {
      try {
        contractBean.setTerminationDTOs(terminationService.find());
      } catch (TerminationServiceException ex) {
        LOGGER.error(GET_TERMINATIONS_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return contractBean.getTerminationDTOs();
  }

  /**
   * Récupère la liste des domaines d'activité
   *
   * @return la liste des domaines d'activité
   */
  public List<FieldOfActivityDTO> getAllFieldOfActivity() {
    if (contractBean.getFieldOfActivityDTOs() == null || contractBean.getFieldOfActivityDTOs().isEmpty()) {
      try {
        UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);

        if (user.getGroups().contains(Group.ADMINISTRATEUR)) {
          contractBean.setFieldOfActivityDTOs(fieldOfActivityService.find());
        } else {
          ExtendedUserDTO extendedUser = extendedUserService.get(user.getId());

          List<AgencyDTO> agencies = extendedUser.getAgencies();

          List<FieldOfActivityDTO> fieldsOfActivity = new ArrayList<FieldOfActivityDTO>();

          for (AgencyDTO agency : agencies) {
            fieldsOfActivity.addAll(fieldOfActivityService.findFieldOfActivityByAgency(agency.getId()));
          }

          contractBean.setFieldOfActivityDTOs(fieldsOfActivity);
        }
      } catch (FieldOfActivityServiceException ex) {
        LOGGER.error(GET_FIELDS_OF_ACTIVITY_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      } catch (ExtendedUserServiceException ex) {
        LOGGER.error(GET_FIELDS_OF_ACTIVITY_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return contractBean.getFieldOfActivityDTOs();
  }

  /**
   * Récupère la liste des methodes de paiement
   *
   * @return la liste des methodes de paiement
   */
  public List<PaymentMethodDTO> getAllPaymentMethod() {
    if (contractBean.getPaymentMethodDTOs() == null || contractBean.getPaymentMethodDTOs().isEmpty()) {
      try {
        contractBean.setPaymentMethodDTOs(paymentMethodService.find());
      } catch (PaymentMethodServiceException ex) {
        LOGGER.error(GET_PAYMENT_METHODS_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return contractBean.getPaymentMethodDTOs();
  }

  /**
   * Récupère la liste des typologies de loyer
   *
   * @return la liste des typologies de loyer
   */
  public List<RentTypologyDTO> getAllRentTypology() {
    if (contractBean.getRentTypologyDTOs() == null || contractBean.getRentTypologyDTOs().isEmpty()) {
      try {
        contractBean.setRentTypologyDTOs(rentTypologyService.find());
      } catch (RentTypologyServiceException ex) {
        LOGGER.error(GET_RENT_TYPOLOGIES_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return contractBean.getRentTypologyDTOs();
  }

  /**
   * Le champ TerminationSavingsPaymentDate doit être réinitialiser si "Règlement du pécule de fin d'occupation" vaut "Non"
   */
  public void clearTerminationSavingsPaymentDate() {
    // Si Règlement du pécule de fin d'occupation = Non
    if (!contractBean.getContractInputDTO().getTerminationSavingsPayment()) {
      contractBean.getContractInputDTO().setTerminationSavingsPaymentDate(null);

    }
  }

  /**
   * @return the contractBean
   */
  public ContractBean getContractBean() {
    return contractBean;
  }

  /**
   * @param contractBean the contractBean to set
   */
  public void setContractBean(ContractBean contractBean) {
    this.contractBean = contractBean;
  }

  /**
   * @return the contractServiceFacade
   */
  public IContractServiceFacade getContractServiceFacade() {
    return contractServiceFacade;
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

  /**
   * @return the costCenterService
   */
  public ICostCenterService getCostCenterService() {
    return costCenterService;
  }

  /**
   * @param costCenterService the costCenterService to set
   */
  public void setCostCenterService(ICostCenterService costCenterService) {
    this.costCenterService = costCenterService;
  }

  /**
   * @return the terminationService
   */
  public ITerminationService getTerminationService() {
    return terminationService;
  }

  /**
   * @param terminationService the terminationService to set
   */
  public void setTerminationService(ITerminationService terminationService) {
    this.terminationService = terminationService;
  }

  /**
   * @return the paymentMethodService
   */
  public IPaymentMethodService getPaymentMethodService() {
    return paymentMethodService;
  }

  /**
   * @param paymentMethodService the paymentMethodService to set
   */
  public void setPaymentMethodService(IPaymentMethodService paymentMethodService) {
    this.paymentMethodService = paymentMethodService;
  }

  /**
   * @return the rentTypologyService
   */
  public IRentTypologyService getRentTypologyService() {
    return rentTypologyService;
  }

  /**
   * @param rentTypologyService the rentTypologyService to set
   */
  public void setRentTypologyService(IRentTypologyService rentTypologyService) {
    this.rentTypologyService = rentTypologyService;
  }

  /**
   * @param fieldOfActivityService the fieldOfActivityService to set
   */
  public void setFieldOfActivityService(IFieldOfActivityService fieldOfActivityService) {
    this.fieldOfActivityService = fieldOfActivityService;
  }

  /**
   * @param detailCronService the detailCronService to set
   */
  public void setDetailCronService(IDetailCronService detailCronService) {
    this.detailCronService = detailCronService;
  }

  /**
   * @param historyContractServiceFacade the historyContractServiceFacade to set
   */
  public void setHistoryContractServiceFacade(IHistoryContractServiceFacade historyContractServiceFacade) {
    this.historyContractServiceFacade = historyContractServiceFacade;
  }

  /**
   * @param housingServiceFacade the housingServiceFacade to set
   */
  public void setHousingServiceFacade(IHousingServiceFacade housingServiceFacade) {
    this.housingServiceFacade = housingServiceFacade;
  }

  /**
   * @param tenantServiceFacade the tenantServiceFacade to set
   */
  public void setTenantServiceFacade(ITenantServiceFacade tenantServiceFacade) {
    this.tenantServiceFacade = tenantServiceFacade;
  }

  /**
   * @param extendedUserService the extendedUserService to set
   */
  public void setExtendedUserService(IExtendedUserService extendedUserService) {
    this.extendedUserService = extendedUserService;
  }

}
