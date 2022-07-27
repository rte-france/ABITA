/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.housing.impl;

import com.abita.dto.*;
import com.abita.services.agency.IAgencyService;
import com.abita.services.airconditioner.IAirconditionerService;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.extendeduser.IExtendedUserService;
import com.abita.services.heatingtype.IHeatingTypeService;
import com.abita.services.historyhousing.IHistoryHousingService;
import com.abita.services.housing.IHousingServiceFacade;
import com.abita.services.housingnature.IHousingNatureService;
import com.abita.services.roomcategory.IRoomCategoryService;
import com.abita.services.sanitation.ISanitationService;
import com.abita.services.site.ISiteService;
import com.abita.services.thirdpartycontract.IThirdPartyContractServiceFacade;
import com.abita.dto.*;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.services.airconditioner.exceptions.AirconditionerServiceException;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.extendeduser.exception.ExtendedUserServiceException;
import com.abita.services.heatingtype.exceptions.HeatingTypeServiceException;
import com.abita.services.historyhousing.exceptions.HistoryHousingServiceException;
import com.abita.services.housing.exceptions.HousingServiceFacadeException;
import com.abita.services.housingnature.exceptions.HousingNatureServiceException;
import com.abita.services.roomcategory.exceptions.RoomCategoryServiceException;
import com.abita.services.sanitation.exceptions.SanitationServiceException;
import com.abita.services.site.exception.SiteServiceException;
import com.abita.services.thirdpartycontract.exceptions.ThirdPartyContractServiceException;
import com.abita.web.housing.bean.HousingBean;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.FuturOfHousing;
import com.abita.web.shared.HousingStatus;
import com.abita.web.shared.Insulation;
import com.dto.Group;
import com.dto.UserDTO;
import com.services.common.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.List;

/**
 * Classe controleur de la page des gestion des logements
 *
 * @author
 */
public class HousingController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 2096947954367538384L;

  /** Message d'erreur si exception lors de la récupération des sites */
  private static final String ERREUR_SURVENUE_RECUPERATION_SITES = "Une erreur est survenue lors de la récupération des sites";

  /** Paramètre de la requête permettant d'indentifier le logement en mode création */
  private static final String CREATION_MODE_PARAMETER = "create";

  /** Redirection de l'utilisateur vers la page de consultation du logement */
  private static final String REDIRECT_TO_CONSULTATION = "GO_TO_CONSULTATION";

  /** Redirection de l'utilisateur vers l'accueil */
  private static final String REDIRECT_TO_EXIT_HOUSING = "EXIT_HOUSING";

  /** Backing bean de la gestion des logements */
  private HousingBean housingBean;

  /** Service pour la gestion des logements */
  private transient IHousingServiceFacade housingServiceFacade;

  /** Service pour l’historisation des logements */
  private transient IHistoryHousingService historyHousingService;

  /** Service pour la gestion des contrats occupant */
  private transient IContractServiceFacade contractServiceFacade;

  /** Service pour la gestion des contrats tiers */
  private transient IThirdPartyContractServiceFacade thirdPartyContractServiceFacade;

  /** Service pour la gestion des statuts logement */
  private transient IHousingNatureService housingNatureService;

  /** Service pour la gestion des assainissements */
  private transient ISanitationService sanitationService;

  /** Service pour la gestion des types de chauffage */
  private transient IHeatingTypeService heatingTypeService;

  /** Service pour la gestion des climatisations */
  private transient IAirconditionerService airconditionerService;

  /** Service pour la gestion des gestionnaires de contrat tiers */
  private transient IExtendedUserService extendedUserService;

  /** Service pour la gestion des catégories de local */
  private transient IRoomCategoryService roomCategoryService;

  /** Service des agences */
  private transient IAgencyService agencyService;

  /** Service des sites */
  private transient ISiteService siteService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HousingController.class);

  /** Erreur si un utilisateur ouvre plusieurs onglets (Ecrasement des informations) */
  private static final String TECHNICAL_MULTIPLE_TABS_ERROR_CODE = "Impossible de sauvegarder. Plusieurs onglets ou fenêtres sont ouverts";

  /** Nom de code pour l’erreur technique */
  private static final String TECHNICAL_ERROR_CODE = "technical.error";

  /** Identifiant du champ caché dans la page de modification des logements */
  private static final String MODIFICATION_HIDDEN_INPUT_ID = "frmUpdateHousing:checkId";

  /** Identifiant du champ caché dans la page de création des logements */
  private static final String CREATE_HIDDEN_INPUT_ID = "frmCreateHousing:checkId";

  /**
   * Méthode d'initialisation des beans
   */
  @PostConstruct
  public void init() {

    // Détermine le mode : Création ou Modification
    String action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(CREATION_MODE_PARAMETER);

    if(housingBean != null) {
        housingBean.updateBooleanValueInListFromDTO();
      }

    if (StringUtils.isEmpty(action)) {
      Long idHousing = null;

      if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_HOUSING) != null) {
        idHousing = ((Number) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_HOUSING)).longValue();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().keep(ConstantsWEB.ID_HOUSING);
      } else {
        // Cas de la suppression qui effectue un post dont on sait pas d'où il sort et qui pert la variable flash
        if (null != housingBean.getHousingDTO() && null != housingBean.getHousingDTO().getId()) {
          idHousing = housingBean.getHousingDTO().getId();
          FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_HOUSING, idHousing);
        }
      }
      /** Justification de cette immondice (Test isAjaxRequest()) :
       * Le chargement de la liste des sites dans la méthode INIT doit avoir lieu uniquement lors du premier affichage du logement en modification.
       * La liste des sites est chargée dynamiquement en fonction de l'agence choisi par l'utilisateur.
       * Cependant, 2 autres cas se présentent :
       *  - Si une erreur survient lors de l'envoi du formulaire, on passe aussi par cette méthode. A ce moment là, il ne faut pas
       * recharger la liste des sites en fonction de l'agence stockée en base de données mais en fonction de l'agence choisie par l'utilisateur.
       *  - Si l'utlisateur change d'agence, on effectue un appel AJAX à la méthode {@link #updateSiteListForCurrentAgency()}. La méthode INIT
       *  étant de type post-construct, il ne faut donc par réinitialiser les valeurs du bean mais conserver celles soumises par l'utilisateur.
       *
       *  Il en est de même pour la liste déroulante "Motif de sortie". Celle-ci est activée/désactivée en fonction de la date de sortie.
       */
      if (idHousing != null && !FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
        try {
          housingBean.setHousingDTO(housingServiceFacade.get(idHousing));
          housingBean.setContractList(contractServiceFacade.findContractByHousing(idHousing));
          housingBean.setThirdPartyContractList(thirdPartyContractServiceFacade.findThirdPartyContractByHousing(idHousing));
          housingBean.setSites(siteService.findSitesByAgency(housingBean.getHousingDTO().getAgency().getId()));
          housingBean.updateBooleanValueInListFromDTO();
        } catch (HousingServiceFacadeException ex) {
          LOGGER.error("Une erreur est survenue lors de la récupération du logement", ex);
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
        } catch (ContractServiceFacadeException e) {
          LOGGER.error("Une erreur est survenue lors de la récupération des contrats occupants", e);
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
        } catch (ThirdPartyContractServiceException e) {
          LOGGER.error("Une erreur est survenue lors de la récupération des contrats tiers", e);
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
        } catch (SiteServiceException e) {
          LOGGER.error(ERREUR_SURVENUE_RECUPERATION_SITES, e);
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
        }
      }

      // On récupére le champ  caché dans la page de modification
      HtmlInputHidden checkId = (HtmlInputHidden) FacesContext.getCurrentInstance().getViewRoot().findComponent(MODIFICATION_HIDDEN_INPUT_ID);
      if (checkId != null) {
        checkId.setValue(String.valueOf(housingBean.getHousingDTO().getId()));
      }
    } else {
      housingBean.setHousingDTO(new HousingDTO());
      housingBean.getHousingDTO().setHousingStatus(HousingStatus.EMPTY);

      // On récupére le champ  caché dans la page de création
      HtmlInputHidden checkId = (HtmlInputHidden) FacesContext.getCurrentInstance().getViewRoot().findComponent(CREATE_HIDDEN_INPUT_ID);
      if (checkId != null) {
        checkId.setValue(String.valueOf(housingBean.getHousingDTO().getId()));
      }
    }
  }

  /**
   * Récupère la liste des statuts logement
   * @return la liste des statuts logement
   */
  public List<HousingStatus> getAllHousingStatus() {
    if (housingBean.getLstHousingStatus() == null || housingBean.getLstHousingStatus().isEmpty()) {
      housingBean.setLstHousingStatus(Arrays.asList(HousingStatus.values()));
    }
    return housingBean.getLstHousingStatus();
  }

  /**
   * Récupère la liste des nature
   * @return la liste des nature
   */
  public List<HousingNatureDTO> getAllHousingNature() {
    if (housingBean.getLstHousingNature() == null || housingBean.getLstHousingNature().isEmpty()) {
      try {
        housingBean.setLstHousingNature(housingNatureService.find());
      } catch (HousingNatureServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des natures", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingBean.getLstHousingNature();
  }

  /**
   * Récupère la liste des assainissements
   * @return la liste des assainissements
   */
  public List<SanitationDTO> getAllSanitation() {
    if (housingBean.getLstSanitation() == null || housingBean.getLstSanitation().isEmpty()) {
      try {
        housingBean.setLstSanitation(sanitationService.find());
      } catch (SanitationServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des assainissement", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingBean.getLstSanitation();
  }

  /**
   * Récupère la liste des types de chauffage
   * @return la liste des types de chauffage
   */
  public List<HeatingTypeDTO> getAllHeatingType() {
    if (housingBean.getLstHeatingType() == null || housingBean.getLstHeatingType().isEmpty()) {
      try {
        housingBean.setLstHeatingType(heatingTypeService.find());
      } catch (HeatingTypeServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des types de chauffage", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingBean.getLstHeatingType();
  }

  /**
   * Récupère la liste des climatisations
   * @return la liste des climatisations
   */
  public List<AirconditionerDTO> getAllAirconditioner() {
    if (housingBean.getLstAirconditioner() == null || housingBean.getLstAirconditioner().isEmpty()) {
      try {
        housingBean.setLstAirconditioner(airconditionerService.find());
      } catch (AirconditionerServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des climatisations", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingBean.getLstAirconditioner();
  }

  /**
   * Récupère la liste des gestionnaires de contrat tiers
   * @return la liste des gestionnaires de contrat tiers
   */
  public List<UserDTO> getAllThirdpartyContractManager() {
    if (housingBean.getLstThirdpartyContractManager() == null || housingBean.getLstThirdpartyContractManager().isEmpty()) {
      try {
        housingBean.setLstThirdpartyContractManager(extendedUserService.findUsersThirdPartyContractManager());
      } catch (ExtendedUserServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des gestionnaires de contrat tiers", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingBean.getLstThirdpartyContractManager();
  }

  /**
   * Récupère la liste des catégories de local
   * @return la liste des catégories de local
   */
  public List<RoomCategoryDTO> getAllRoomCategory() {
    if (housingBean.getLstRoomCategory() == null || housingBean.getLstRoomCategory().isEmpty()) {
      try {
        housingBean.setLstRoomCategory(roomCategoryService.find());
      } catch (RoomCategoryServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des catégories de local", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingBean.getLstRoomCategory();
  }

  /**
   * Récupère la liste des agences disponibles pour l’utilisateur
   * @return la liste des agences disponibles pour l’utilisateur
   */
  public List<AgencyDTO> getAgencies() {
    if (housingBean.getAgencies() == null || housingBean.getAgencies().isEmpty()) {
      try {
        UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);

        List<AgencyDTO> agencies = agencyService.findAllAgencies(user);
        housingBean.setAgencies(agencies);

        if ((!user.getGroups().contains(Group.ADMINISTRATEUR))) {
          setDefaultAgency();
        }
      } catch (AgencyServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des agences", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingBean.getAgencies();
  }

  /**
   * Mise à jour de la liste de sites en fonction de l'agence choisie par l'utilisateur
   */
  public void updateSiteListForCurrentAgency() {
    try {
      // à chaque changement d'agence, on réinitialise le site sélectionné précédemment.
      housingBean.getHousingDTO().setRattachmentSite(null);
      if (housingBean.getHousingDTO().getAgency() != null) {
        housingBean.setSites(siteService.findSitesByAgency(housingBean.getHousingDTO().getAgency().getId()));
      } else {
        housingBean.setSites(null);
      }
    } catch (SiteServiceException e) {
      LOGGER.error(ERREUR_SURVENUE_RECUPERATION_SITES, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * On vide le champs "Motif de sortie" si le champ "Date de sortie" est vidé
   */
  public void resetReasonForExit() {
    if (housingBean.getHousingDTO().getUnregisterDate() == null) {
      housingBean.getHousingDTO().setReasonForExit(null);
    }
  }
  /**
   * si le champs "Avenir du logement" est à Maintien, on retourne vrai pour l'option disable du champ "Année:Avenir du logement"
   */
	public void resetFuturOfHousingDate() {
	if((housingBean.getHousingDTO().getFuturOfHousing() == null)
				|| (housingBean.getHousingDTO().getFuturOfHousing().equals(FuturOfHousing.DEMOLITION) || (housingBean.getHousingDTO().getFuturOfHousing().equals(FuturOfHousing.SALE)))){
    housingBean.setFutureOfHousingBloc(true);
    housingBean.getHousingDTO().setFuturOfHousingPrice(housingBean.getHousingDTO().getFuturOfHousingPrice());
    housingBean.getHousingDTO().setDateFuturOfHousing(housingBean.getHousingDTO().getDateFuturOfHousing());
  }else{
    housingBean.setFutureOfHousingBloc(false);
    housingBean.getHousingDTO().setFuturOfHousingPrice(null);
    housingBean.getHousingDTO().setDateFuturOfHousing("");
  }

	}

  /**
   * si le champs "Isolation" est à couverture, on retourne vrai pour l'option disable du champ "Année:Avenir du logement"
   */
  public void resetWalls() {
  if ((housingBean.getHousingDTO().getInsulation()== null) || (housingBean.getHousingDTO().getInsulation().equals(Insulation.COVER) ||(housingBean.getHousingDTO().getInsulation().equals(Insulation.UNKNOWN)||
      (housingBean.getHousingDTO().getInsulation().equals(""))))){
    housingBean.setMurBloc(false);
    housingBean.getHousingDTO().setWalls(null);
  }else{
    housingBean.setMurBloc(true);
  }
  }


  /**
   * si le champs démolition programmé en est remplis on affiche le champ coût de démolition
   */
	public void resetDemolition() {
		housingBean.setDemolitionBloc(StringUtils.isNotEmpty(housingBean.getHousingDTO().getDateDemolition()));
	}

  /**
   * si le champs rénovation programmé en est remplis on affiche le champ coût de rénovation
   */
	public void resetRenovation() {
		housingBean.setRenovationBloc(StringUtils.isNotEmpty(housingBean.getHousingDTO().getDateRenovation()));
 	}

  /**
   * Permet de définir par défaut la valeur du champ « Agence » du formulaire de création
   */
  private void setDefaultAgency() {
    String action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(CREATION_MODE_PARAMETER);
    if (!StringUtils.isEmpty(action) && housingBean.getAgencies().size() == 1) {
      housingBean.getHousingDTO().setAgency(housingBean.getAgencies().get(0));
      updateSiteListForCurrentAgency();
    }
  }

  /**
   * Permet de savoir si le logement est supprimable
   * @return true si le logement n'est pas référencé dans l'application, false autrement
   */
  public boolean isRemovable() {
    boolean isRemovable = true;

    try {
      isRemovable = housingServiceFacade.isRemovable(housingBean.getHousingDTO().getId());
    } catch (HousingServiceFacadeException e) {
      LOGGER.error("Une erreur est survenue lors de la vérification de la suppression d'un logement", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }

    return isRemovable;
  }

  /**
   * Permet de sauvegarder un logement
   *
   * @return la valeur de l'action
   */
  public String saveOrUpdateData() {
    UIInput checkId;
    UIInput reasonForExit;

    // en création

    if (FacesContext.getCurrentInstance().getViewRoot().findComponent(CREATE_HIDDEN_INPUT_ID) != null) {
      checkId = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(CREATE_HIDDEN_INPUT_ID);
      // on récupère le champ motif de sortie en création
      reasonForExit = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmCreateHousing:reason_for_exit");
      // en modification
    } else {
      checkId = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(MODIFICATION_HIDDEN_INPUT_ID);
      // on récupère le champ motif de sortie en modification
      reasonForExit = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent("frmUpdateHousing:reason_for_exit");
    }

    if (reasonForExit != null && !checkReasonForExit()) {
      reasonForExit.setValid(false);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("housing.error.reason.exit.mandatory"));
      return null;
    }

    if (checkId != null && checkId.getValue().equals(String.valueOf(housingBean.getHousingDTO().getId()))) {

      if (housingBean.getHousingDTO().getId() != null) {
        updateData();
      } else {
        saveData();
      }
      return REDIRECT_TO_CONSULTATION;
    } else {

      LOGGER.error("PROBLEME D'OUVERTURE DE PLUSIEURS ONGLETS/FENETRES");
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_MULTIPLE_TABS_ERROR_CODE));
      return null;
    }
  }

  /**
   * Vérifie si la date de sortie est renseignée que le motif de sortie est lui aussi bien renseigné.
   * @return true si formulaire valide
   */
  private boolean checkReasonForExit() {
    return !(housingBean.getHousingDTO().getUnregisterDate() != null && housingBean.getHousingDTO().getReasonForExit() == null);
  }

  /**
   * Permet de renvoyer vers le mode Modification
   * @return la valeur de l'action
   */
  public String redirectUpdateData() {
    return "GO_TO_MODIFICATION";
  }

  /**
   * Permet de supprimer un logement
   *
   * @return le résultat de l'action
   */
  public String deleteData() {
    try {
      housingServiceFacade.delete(housingBean.getHousingDTO().getId());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("housing.result.delete"));
    } catch (HousingServiceFacadeException ex) {
      LOGGER.error("Une erreur est survenue lors de la suppression du logement", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("housing.result.delete.error", housingBean.getHousingDTO().getId()));
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      FacesMessage msg = new FacesMessage("Impossible de supprimer un logement en relation avec un tiers.");
      msg.setSeverity(FacesMessage.SEVERITY_INFO);
      FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    return REDIRECT_TO_EXIT_HOUSING;
  }

  /**
   * Action du bouton "Annuler"
   * Comme on peut arriver de la modification, avant d'annuler, on doit supprimer l'état de modification
   *
   * @return le clé où aller
   */
  public String cancel() {
    if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_HOUSING) != null) {
      return REDIRECT_TO_CONSULTATION;
    }
    return REDIRECT_TO_EXIT_HOUSING;
  }

  /**
   * Effectue la sauvegarde du logement
   */
  private void saveData() {
    try {
      housingBean.getHousingDTO().setReference(housingServiceFacade.generateReference());
      long idHousing = housingServiceFacade.create(housingBean.getHousingDTO());
      FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_HOUSING, idHousing);
    } catch (HousingServiceFacadeException ex) {
      LOGGER.error("Une erreur est survenue lors de la création du logement", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * Effectue la mise à jour du logement
   */
  private void updateData() {
    try {
      housingServiceFacade.update(housingBean.getHousingDTO());
      FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_HOUSING, housingBean.getHousingDTO().getId());

      historyHousingService.updateTemporaries(housingBean.getHousingDTO());
      contractServiceFacade.updateContractsForHousing(housingBean.getHousingDTO().getId());
    } catch (HousingServiceFacadeException ex) {
      LOGGER.error("Une erreur est survenue lors de la mise à jour du logement", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    } catch (HistoryHousingServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la mise à jour de l’historisation du logement", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Une erreur est survenue lors de la mise à jour des contrats du logement", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * @return the housingBean
   */
  public HousingBean getHousingBean() {
    return housingBean;
  }

  /**
   * @param housingBean the housingBean to set
   */
  public void setHousingBean(HousingBean housingBean) {
    this.housingBean = housingBean;
  }

  /**
   * @param historyHousingService the historyHousingService to set
   */
  public void setHistoryHousingService(IHistoryHousingService historyHousingService) {
    this.historyHousingService = historyHousingService;
  }

  /**
   * @param housingNatureService the housingNatureService to set
   */
  public void setHousingNatureService(IHousingNatureService housingNatureService) {
    this.housingNatureService = housingNatureService;
  }

  /**
   * @param sanitationService the sanitationService to set
   */
  public void setSanitationService(ISanitationService sanitationService) {
    this.sanitationService = sanitationService;
  }

  /**
   * @param airconditionerService the airconditionerService to set
   */
  public void setAirconditionerService(IAirconditionerService airconditionerService) {
    this.airconditionerService = airconditionerService;
  }

  /**
   * @param heatingTypeService the heatingTypeService to set
   */
  public void setHeatingTypeService(IHeatingTypeService heatingTypeService) {
    this.heatingTypeService = heatingTypeService;
  }

  /**
   * @param extendedUserService the extendedUserService to set
   */
  public void setExtendedUserService(IExtendedUserService extendedUserService) {
    this.extendedUserService = extendedUserService;
  }

  /**
   * @param roomCategoryService the roomCategoryService to set
   */
  public void setRoomCategoryService(IRoomCategoryService roomCategoryService) {
    this.roomCategoryService = roomCategoryService;
  }

  /**
   * @param agencyService the agencyService to set
   */
  public void setAgencyService(IAgencyService agencyService) {
    this.agencyService = agencyService;
  }

  /**
   * @param siteService the siteService to set
   */
  public void setSiteService(ISiteService siteService) {
    this.siteService = siteService;
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

  /**
   * @param thirdPartyContractServiceFacade the thirdPartyContractServiceFacade to set
   */
  public void setThirdPartyContractServiceFacade(IThirdPartyContractServiceFacade thirdPartyContractServiceFacade) {
    this.thirdPartyContractServiceFacade = thirdPartyContractServiceFacade;
  }

  public void setHousingServiceFacade(IHousingServiceFacade housingServiceFacade) {
    this.housingServiceFacade = housingServiceFacade;
  }

    /**
   * Permet d'affecter une valeur Boolean à un string (dans la liste listFormBoolean)
   *
   * @param label
   * @param val
   */
  public void setBooleanValueInList(String label, boolean val) {
    housingBean.setBooleanValueInList(label, val);
  }

  /**
   * On récupère la valeur Boolean d'un string (de la liste listFormBoolean)
   *
   * @param label
   * @return
   */
  public boolean getBooleanValueInList(String label) {
    return housingBean.getBooleanValueInList(label);
  }
}
