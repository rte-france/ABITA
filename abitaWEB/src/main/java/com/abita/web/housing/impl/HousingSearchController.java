/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.housing.impl;

import com.abita.dto.*;
import com.abita.services.agency.IAgencyService;
import com.abita.services.airconditioner.IAirconditionerService;
import com.abita.services.extendeduser.IExtendedUserService;
import com.abita.services.fieldofactivity.IFieldOfActivityService;
import com.abita.services.heatingtype.IHeatingTypeService;
import com.abita.services.housing.IHousingServiceFacade;
import com.abita.services.housingnature.IHousingNatureService;
import com.abita.services.roomcategory.IRoomCategoryService;
import com.abita.services.sanitation.ISanitationService;
import com.abita.services.site.ISiteService;
import com.abita.services.typetenant.ITypeTenantService;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.services.airconditioner.exceptions.AirconditionerServiceException;
import com.abita.services.extendeduser.exception.ExtendedUserServiceException;
import com.abita.services.fieldofactivity.exceptions.FieldOfActivityServiceException;
import com.abita.services.heatingtype.exceptions.HeatingTypeServiceException;
import com.abita.services.housing.exceptions.HousingServiceFacadeException;
import com.abita.services.housingnature.exceptions.HousingNatureServiceException;
import com.abita.services.roomcategory.exceptions.RoomCategoryServiceException;
import com.abita.services.sanitation.exceptions.SanitationServiceException;
import com.abita.services.site.exception.SiteServiceException;
import com.abita.services.typetenant.exceptions.TypeTenantServiceException;
import com.abita.web.housing.bean.HousingSearchBean;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.HousingStatus;
import com.dto.Group;
import com.dto.UserDTO;
import com.services.common.constants.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import javax.annotation.PostConstruct;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe controleur de la page de recherche des logements
 *
 * @author
 */
public class HousingSearchController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 3084761021335919985L;

  /** Backing bean de la gestion des logements */
  private HousingSearchBean housingSearchBean;

  /** Service pour la gestion des logements */
  private transient IHousingServiceFacade housingServiceFacade;

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

  /** Service des types d'occupant */
  private transient ITypeTenantService typeTenantService;

  /** Service de domaine d'activité */
  private transient IFieldOfActivityService fieldOfActivityService;

  /** Service des agences */
  private transient IAgencyService agencyService;

  /** Service des sites */
  private transient ISiteService siteService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HousingSearchController.class);

  /** Message lors d’une erreur de récupération des domaines d’æctivité */
  private static final String GET_FIELDS_OF_ACTIVITY_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des domaines d'activité";

  /** Nom de code pour l’erreur technique */
  private static final String TECHNICAL_ERROR_CODE = "technical.error";

  /** Identifiant technique non utilisé pour une agence */
  private static final Long UNUSED_ID = -1L;

  /**
   * Méthode d'initialisation des beans
   */
  @PostConstruct
  public void init() {
    if (null == housingSearchBean.getHousingCriteria()) {
      housingSearchBean.setHousingCriteria(new HousingCriteriaDTO());
    }
    if (null == housingSearchBean.getTenantCriteria()) {
      housingSearchBean.setTenantCriteria(new TenantCriteriaDTO());
    }
  }

  /**
   * Récupère la liste des statuts logement
   * @return la liste des statuts logement
   */
  public List<HousingStatus> getAllHousingStatus() {
    if (housingSearchBean.getLstHousingStatus() == null || housingSearchBean.getLstHousingStatus().isEmpty()) {
      housingSearchBean.setLstHousingStatus(Arrays.asList(HousingStatus.values()));
    }
    return housingSearchBean.getLstHousingStatus();
  }

  /**
   * Récupère la liste des nature
   * @return la liste des nature
   */
  public List<HousingNatureDTO> getAllHousingNature() {
    if (housingSearchBean.getLstHousingNature() == null || housingSearchBean.getLstHousingNature().isEmpty()) {
      try {
        housingSearchBean.setLstHousingNature(housingNatureService.find());
      } catch (HousingNatureServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des natures", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingSearchBean.getLstHousingNature();
  }

  /**
   * Récupère la liste des gestionnaires de contrat tiers
   * @return la liste des gestionnaires de contrat tiers
   */
  public List<UserDTO> getAllThirdpartyContractManager() {
    if (housingSearchBean.getLstThirdpartyContractManager() == null || housingSearchBean.getLstThirdpartyContractManager().isEmpty()) {
      try {
        housingSearchBean.setLstThirdpartyContractManager(extendedUserService.findUsersThirdPartyContractManager());
      } catch (ExtendedUserServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des gestionnaires de contrat tiers", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingSearchBean.getLstThirdpartyContractManager();
  }

  /**
   * Récupère la liste des catégories de local
   * @return la liste des catégories de local
   */
  public List<RoomCategoryDTO> getAllRoomCategory() {
    if (housingSearchBean.getLstRoomCategory() == null || housingSearchBean.getLstRoomCategory().isEmpty()) {
      try {
        housingSearchBean.setLstRoomCategory(roomCategoryService.find());
      } catch (RoomCategoryServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des catégories de local", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingSearchBean.getLstRoomCategory();
  }

  /**
   * Récupère la liste des domaines d'activité
   * @return la liste des domaines d'activité
   */
  public List<FieldOfActivityDTO> getAllFieldOfActivity() {
    if (housingSearchBean.getFieldOfActivityDTOs() == null || housingSearchBean.getFieldOfActivityDTOs().isEmpty()) {
      try {
        UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);

        if (user.getGroups().contains(Group.ADMINISTRATEUR)) {
          housingSearchBean.setFieldOfActivityDTOs(fieldOfActivityService.find());
        } else {
          ExtendedUserDTO extendedUser = extendedUserService.get(user.getId());

          List<AgencyDTO> agencies = extendedUser.getAgencies();

          List<FieldOfActivityDTO> fieldsOfActivity = new ArrayList<>();

          for (AgencyDTO agency : agencies) {
            fieldsOfActivity.addAll(fieldOfActivityService.findFieldOfActivityByAgency(agency.getId()));
          }

          housingSearchBean.setFieldOfActivityDTOs(fieldsOfActivity);
        }
      } catch (FieldOfActivityServiceException | ExtendedUserServiceException ex) {
        LOGGER.error(GET_FIELDS_OF_ACTIVITY_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingSearchBean.getFieldOfActivityDTOs();
  }

  /**
   * Récupère la liste des types d'occupant
   * @return la liste des types d'occupant
   */
  public List<TypeTenantDTO> getAllTypeTenant() {
    if (housingSearchBean.getLstTypeTenant() == null || housingSearchBean.getLstTypeTenant().isEmpty()) {
      try {
        housingSearchBean.setLstTypeTenant(typeTenantService.find());
      } catch (TypeTenantServiceException e) {
        LOGGER.error("Une erreur est survenue lors de la récupération des types d'occupant", e);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingSearchBean.getLstTypeTenant();
  }

  /**
   * Récupère la liste des agences disponibles pour l’utilisateur
   * @return la liste des agences disponibles pour l’utilisateur
   */
  public List<AgencyDTO> getAgencies() {
    if (housingSearchBean.getAgencies() == null || housingSearchBean.getAgencies().isEmpty()) {
      try {
        UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);

        List<AgencyDTO> agencies = agencyService.findAllAgencies(user);
        housingSearchBean.setAgencies(agencies);

        if (!user.getGroups().contains(Group.ADMINISTRATEUR)) {
          setDefaultAgency();
          updateSiteListForCurrentAgency();
        }
      } catch (AgencyServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des agences", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingSearchBean.getAgencies();
  }

  /**
   * Mise à jour de la liste de sites en fonction de l'agence choisie par l'utilisateur
   */
  public void updateSiteListForCurrentAgency() {
    try {
      // à chaque changement d'agence, on réinitialise le site sélectionné précédemment.
      housingSearchBean.getHousingCriteria().setRattachmentSite(null);
      if (housingSearchBean.getHousingCriteria().getAgency() != null) {
        housingSearchBean.setSites(siteService.findSitesByAgency(housingSearchBean.getHousingCriteria().getAgency().getId()));
      } else {
        housingSearchBean.setSites(null);
      }
    } catch (SiteServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la récupération des sites", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * Permet de définir par défaut la valeur du champ « Agence » du formulaire
   */
  private void setDefaultAgency() {
    if (housingSearchBean.getAgencies().size() == 1 && housingSearchBean.getHousingCriteria().getAgency() == null) {
      housingSearchBean.getHousingCriteria().setAgency(housingSearchBean.getAgencies().get(0));
    } else if (housingSearchBean.getAgencies().isEmpty()) {
      AgencyDTO agency = new AgencyDTO();
      agency.setId(UNUSED_ID);
      housingSearchBean.getHousingCriteria().setAgency(agency);
    }
  }

  /**
   * Récupère la liste des assainissement
   * @return la liste des assainissement
   */
  public List<SanitationDTO> getSanitations() {
    if (housingSearchBean.getSanitations() == null || housingSearchBean.getSanitations().isEmpty()) {
      try {
        housingSearchBean.setSanitations(sanitationService.find());
      } catch (SanitationServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des assainissement", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingSearchBean.getSanitations();
  }

  /**
   * Récupère la liste des Climatisseurs
   * @return la liste des Climatisseurs
   */
  public List<AirconditionerDTO> getAirconditioners() {
    if (housingSearchBean.getAirConditioners() == null || housingSearchBean.getAirConditioners().isEmpty()) {
      try {
        housingSearchBean.setAirConditioners(airconditionerService.find());
      } catch (AirconditionerServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération les climatisseurs", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingSearchBean.getAirConditioners();
  }

  /**
   * Récupère la liste des Types de Chauffages
   * @return la liste des Types de Chauffages
   */
  public List<HeatingTypeDTO> getHeatingTypes() {
    if (housingSearchBean.getHeatingTypes() == null || housingSearchBean.getHeatingTypes().isEmpty()) {
      try {
        housingSearchBean.setHeatingTypes(heatingTypeService.find());
      } catch (HeatingTypeServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des types de chauffages", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingSearchBean.getHeatingTypes();
  }

  /**
   * Lance la recherche
   * @param actionEvent : l'event
   */
  public void searchData(ActionEvent actionEvent) {
    try {
      defineBooleanValue();

      // les utilisateurs non administrateurs ne doivent voir que la liste des agences auxquelles ils sont rattachés
      UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);
      if (!user.getGroups().contains(Group.ADMINISTRATEUR)) {
        setDefaultAgency();
      }

      housingSearchBean.setLstResultSearch(housingServiceFacade.findByCriteria(housingSearchBean.getHousingCriteria(), housingSearchBean.getTenantCriteria()));
    } catch (HousingServiceFacadeException ex) {
      LOGGER.error("Une erreur est survenue lors de la recherche des logements", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * Matche les String avec leur équivalent booléen
   * A cause du parseur Apache EL qui distingue pas les primitifs des wrapper et qui retourne les null en false,
   * on passe par des variables tampon pour la gestion de ces booléens
   */
  private void defineBooleanValue() {
    if (StringUtils.isEmpty(housingSearchBean.getProperty())) {
      housingSearchBean.getHousingCriteria().setProperty(null);
    } else {
      housingSearchBean.getHousingCriteria().setProperty(Boolean.parseBoolean(housingSearchBean.getProperty()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getGardenAvailable())) {
      housingSearchBean.getHousingCriteria().setGardenAvailable(null);
    } else {
      housingSearchBean.getHousingCriteria().setGardenAvailable(Boolean.parseBoolean(housingSearchBean.getGardenAvailable()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getGarageAvailable())) {
      housingSearchBean.getHousingCriteria().setGarageAvailable(null);
    } else {
      housingSearchBean.getHousingCriteria().setGarageAvailable(Boolean.parseBoolean(housingSearchBean.getGarageAvailable()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getEquippedKitchen())) {
      housingSearchBean.getHousingCriteria().setEquippedKitchen(null);
    } else {
      housingSearchBean.getHousingCriteria().setEquippedKitchen(Boolean.parseBoolean(housingSearchBean.getEquippedKitchen()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getBathroom())) {
      housingSearchBean.getHousingCriteria().setBathroom(null);
    } else {
      housingSearchBean.getHousingCriteria().setBathroom(Boolean.parseBoolean(housingSearchBean.getBathroom()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getErnmt())) {
      housingSearchBean.getHousingCriteria().setErnmt(null);
    } else {
      housingSearchBean.getHousingCriteria().setErnmt(Boolean.parseBoolean(housingSearchBean.getErnmt()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getCarrez())) {
      housingSearchBean.getHousingCriteria().setCarrez(null);
    } else {
      housingSearchBean.getHousingCriteria().setCarrez(Boolean.parseBoolean(housingSearchBean.getCarrez()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getDpe())) {
      housingSearchBean.getHousingCriteria().setDpe(null);
    } else {
      housingSearchBean.getHousingCriteria().setDpe(Boolean.parseBoolean(housingSearchBean.getDpe()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getGas())) {
      housingSearchBean.getHousingCriteria().setGas(null);
    } else {
      housingSearchBean.getHousingCriteria().setGas(Boolean.parseBoolean(housingSearchBean.getGas()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getGasAno())) {
      housingSearchBean.getHousingCriteria().setGasAno(null);
    } else {
      housingSearchBean.getHousingCriteria().setGasAno(Boolean.parseBoolean(housingSearchBean.getGasAno()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getElectAno())) {
      housingSearchBean.getHousingCriteria().setElectAno(null);
    } else {
      housingSearchBean.getHousingCriteria().setElectAno(Boolean.parseBoolean(housingSearchBean.getElectAno()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getAsbestos())) {
      housingSearchBean.getHousingCriteria().setAsbestos(null);
    } else {
      housingSearchBean.getHousingCriteria().setAsbestos(Boolean.parseBoolean(housingSearchBean.getAsbestos()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getAsbestosAno())) {
      housingSearchBean.getHousingCriteria().setAsbestosAno(null);
    } else {
      housingSearchBean.getHousingCriteria().setAsbestosAno(Boolean.parseBoolean(housingSearchBean.getAsbestosAno()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getLead())) {
      housingSearchBean.getHousingCriteria().setLead(null);
    } else {
      housingSearchBean.getHousingCriteria().setLead(Boolean.parseBoolean(housingSearchBean.getLead()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getLeadAno())) {
      housingSearchBean.getHousingCriteria().setLeadAno(null);
    } else {
      housingSearchBean.getHousingCriteria().setLeadAno(Boolean.parseBoolean(housingSearchBean.getLeadAno()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getTermite())) {
      housingSearchBean.getHousingCriteria().setTermite(null);
    } else {
      housingSearchBean.getHousingCriteria().setTermite(Boolean.parseBoolean(housingSearchBean.getTermite()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getTermiteAno())) {
      housingSearchBean.getHousingCriteria().setTermiteAno(null);
    } else {
      housingSearchBean.getHousingCriteria().setTermiteAno(Boolean.parseBoolean(housingSearchBean.getTermiteAno()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getElect())) {
      housingSearchBean.getHousingCriteria().setElect(null);
    } else {
      housingSearchBean.getHousingCriteria().setElect(Boolean.parseBoolean(housingSearchBean.getElect()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getCore())) {
      housingSearchBean.getHousingCriteria().setCore(null);
    } else {
      housingSearchBean.getHousingCriteria().setCore(Boolean.parseBoolean(housingSearchBean.getCore()));
    }
    if (StringUtils.isEmpty(housingSearchBean.getEmployee())) {
      housingSearchBean.getTenantCriteria().setEmployee(null);
    } else {
      housingSearchBean.getTenantCriteria().setEmployee(Boolean.parseBoolean(housingSearchBean.getEmployee()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getManagerial())) {
      housingSearchBean.getTenantCriteria().setManagerial(null);
    } else {
      housingSearchBean.getTenantCriteria().setManagerial(Boolean.parseBoolean(housingSearchBean.getManagerial()));
    }

    if (StringUtils.isEmpty(housingSearchBean.getManagerialLastYear())) {
      housingSearchBean.getTenantCriteria().setManagerialLastYear(null);
    } else {
      housingSearchBean.getTenantCriteria().setManagerialLastYear(Boolean.parseBoolean(housingSearchBean.getManagerialLastYear()));
    }
  }

  /**
   * Action lancée après la création du fichier excel contenant les informations des logements
   * @param document le document excel
   */
  public void postProcessXLS(Object document) {
    HSSFWorkbook wb = (HSSFWorkbook) document;
    HSSFSheet sheet = wb.getSheetAt(0);
    HSSFRow header = sheet.getRow(0);

    HSSFCellStyle cellStyle = wb.createCellStyle();
    cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
    cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

    for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
      HSSFCell cell = header.getCell(i);

      cell.setCellStyle(cellStyle);
      sheet.autoSizeColumn(cell.getColumnIndex());
    }
  }

  /**
   * Sur clic de l'ID, on le récupère et on redirige vers la consultation
   * @param event : l'évènement
   */
  public void redirectConsult(ActionEvent event) {
    long idHousing = Long.parseLong(event.getComponent().getAttributes().get("data_id").toString());
    FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_HOUSING, idHousing);

    // On redirige vers la consultation
    ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
    configurableNavigationHandler.performNavigation("GO_TO_CONSULTATION");
  }

  /**
   * Action du bouton "Annuler"
   *
   * @return le clé où aller
   */
  public String cancel() {
    return "EXIT_HOUSING";
  }

  /**
   * @return the housingSearchBean
   */
  public HousingSearchBean getHousingSearchBean() {
    return housingSearchBean;
  }

  /**
   * @param housingSearchBean the housingSearchBean to set
   */
  public void setHousingSearchBean(HousingSearchBean housingSearchBean) {
    this.housingSearchBean = housingSearchBean;
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
   * Renvoi le template de la pagination
   * @return une pagination des résultats
   */
  public String getPaginationTemplate() {
    List<String> finalPaginationRowPerPageTemplate = new ArrayList<>(ConstantsWEB.DEFAULT_ROW_PER_PAGE);
    int maximumRowPerPage = Integer.parseInt(finalPaginationRowPerPageTemplate.get(finalPaginationRowPerPageTemplate.size() - 1));
    if (housingSearchBean.getLstResultSearch() != null && housingSearchBean.getLstResultSearch().size() > maximumRowPerPage) {
      finalPaginationRowPerPageTemplate.add(String.valueOf(housingSearchBean.getLstResultSearch().size()));
    }
    return StringUtils.join(finalPaginationRowPerPageTemplate, ", ");
  }

  /**
   * @param typeTenantService the typeTenantService to set
   */
  public void setTypeTenantService(ITypeTenantService typeTenantService) {
    this.typeTenantService = typeTenantService;
  }

  /**
   * @param housingServiceFacade the housingServiceFacade to set
   */
  public void setHousingServiceFacade(IHousingServiceFacade housingServiceFacade) {
    this.housingServiceFacade = housingServiceFacade;
  }

  public IHousingServiceFacade getHousingServiceFacade() {
    return housingServiceFacade;
  }

  public IHousingNatureService getHousingNatureService() {
    return housingNatureService;
  }

  public ISanitationService getSanitationService() {
    return sanitationService;
  }

  public IHeatingTypeService getHeatingTypeService() {
    return heatingTypeService;
  }

  public IAirconditionerService getAirconditionerService() {
    return airconditionerService;
  }

  public IExtendedUserService getExtendedUserService() {
    return extendedUserService;
  }

  public IRoomCategoryService getRoomCategoryService() {
    return roomCategoryService;
  }

  public ITypeTenantService getTypeTenantService() {
    return typeTenantService;
  }

  public IFieldOfActivityService getFieldOfActivityService() {
    return fieldOfActivityService;
  }

  public IAgencyService getAgencyService() {
    return agencyService;
  }

  public ISiteService getSiteService() {
    return siteService;
  }

  public static Logger getLOGGER() {
    return LOGGER;
  }

  public static String getGetFieldsOfActivityErrorMessage() {
    return GET_FIELDS_OF_ACTIVITY_ERROR_MESSAGE;
  }

  public static String getTechnicalErrorCode() {
    return TECHNICAL_ERROR_CODE;
  }

  public static Long getUnusedId() {
    return UNUSED_ID;
  }

  /**
   * @param fieldOfActivityService the fieldOfActivityService to set
   */
  public void setFieldOfActivityService(IFieldOfActivityService fieldOfActivityService) {
    this.fieldOfActivityService = fieldOfActivityService;
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

}
