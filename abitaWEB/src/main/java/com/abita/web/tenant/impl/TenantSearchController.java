package com.abita.web.tenant.impl;

import com.abita.services.agency.IAgencyService;
import com.abita.services.extendeduser.IExtendedUserService;
import com.abita.services.housingnature.IHousingNatureService;
import com.abita.services.roomcategory.IRoomCategoryService;
import com.abita.services.site.ISiteService;
import com.abita.services.tenant.ITenantServiceFacade;
import com.abita.services.typetenant.ITypeTenantService;
import com.abita.web.tenant.bean.TenantSearchBean;
import com.abita.dto.AgencyDTO;
import com.abita.dto.HousingNatureDTO;
import com.abita.dto.RoomCategoryDTO;
import com.abita.dto.TypeTenantDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.services.extendeduser.exception.ExtendedUserServiceException;
import com.abita.services.housingnature.exceptions.HousingNatureServiceException;
import com.abita.services.roomcategory.exceptions.RoomCategoryServiceException;
import com.abita.services.site.exception.SiteServiceException;
import com.abita.services.tenant.exceptions.TenantServiceFacadeException;
import com.abita.services.typetenant.exceptions.TypeTenantServiceException;
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
 * Controleur gérant la recherche des occupants
 * @author
 *
 */
public class TenantSearchController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 7415927206954651935L;

  /************************************
   * Propriétés
   ************************************/

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TenantSearchController.class);

  /** Backing bean de la recherche des occupants */
  private TenantSearchBean tenantSearchBean;

  /** Service des occupants */
  private transient ITenantServiceFacade tenantServiceFacade;

  /** Service des types d'occupant */
  private transient ITypeTenantService typeTenantService;

  /** Service pour la gestion des statuts logement */
  private transient IHousingNatureService housingNatureService;

  /** Service pour la gestion des gestionnaires de contrat tiers */
  private transient IExtendedUserService extendedUserService;

  /** Service pour la gestion des catégories de local */
  private transient IRoomCategoryService roomCategoryService;

  /** Service des agences */
  private transient IAgencyService agencyService;

  /** Service des sites */
  private transient ISiteService siteService;

  /** Nom de code pour l’erreur technique */
  private static final String TECHNICAL_ERROR_CODE = "technical.error";

  /** Identifiant technique non utilisé pour une agence */
  private static final Long UNUSED_ID = -1L;

  /************************************
   * Méthodes
   ************************************/

  /**
   * Méthode d'initialisation des beans
   */
  @PostConstruct
  public void init() {
    if (null == tenantSearchBean.getTenantCriteria()) {
      tenantSearchBean.setTenantCriteria(new TenantCriteriaDTO());
    }
    if (null == tenantSearchBean.getHousingCriteria()) {
      tenantSearchBean.setHousingCriteria(new HousingCriteriaDTO());
    }
  }

  /**
   * Récupère la liste des types d'occupant
   * @return la liste des types d'occupant
   */
  public List<TypeTenantDTO> getAllTypeTenant() {
    if (tenantSearchBean.getLstTypeTenant() == null || tenantSearchBean.getLstTypeTenant().isEmpty()) {
      try {
        tenantSearchBean.setLstTypeTenant(typeTenantService.find());
      } catch (TypeTenantServiceException e) {
        LOGGER.error("Une erreur est survenue lors de la récupération des types d'occupant", e);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return tenantSearchBean.getLstTypeTenant();
  }

  /**
   * Récupère la liste des statuts logement
   * @return la liste des statuts logement
   */
  public List<HousingStatus> getAllHousingStatus() {
    if (tenantSearchBean.getLstHousingStatus() == null || tenantSearchBean.getLstHousingStatus().isEmpty()) {
      tenantSearchBean.setLstHousingStatus(Arrays.asList(HousingStatus.values()));
    }
    return tenantSearchBean.getLstHousingStatus();
  }

  /**
   * Récupère la liste des nature
   * @return la liste des nature
   */
  public List<HousingNatureDTO> getAllHousingNature() {
    if (tenantSearchBean.getLstHousingNature() == null || tenantSearchBean.getLstHousingNature().isEmpty()) {
      try {
        tenantSearchBean.setLstHousingNature(housingNatureService.find());
      } catch (HousingNatureServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des natures", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return tenantSearchBean.getLstHousingNature();
  }

  /**
   * Récupère la liste des gestionnaires de contrat tiers
   * @return la liste des gestionnaires de contrat tiers
   */
  public List<UserDTO> getAllThirdpartyContractManager() {
    if (tenantSearchBean.getLstThirdpartyContractManager() == null || tenantSearchBean.getLstThirdpartyContractManager().isEmpty()) {
      try {
        tenantSearchBean.setLstThirdpartyContractManager(extendedUserService.findUsersThirdPartyContractManager());
      } catch (ExtendedUserServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des gestionnaires de contrat tiers", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return tenantSearchBean.getLstThirdpartyContractManager();
  }

  /**
   * Récupère la liste des catégories de local
   * @return la liste des catégories de local
   */
  public List<RoomCategoryDTO> getAllRoomCategory() {
    if (tenantSearchBean.getLstRoomCategory() == null || tenantSearchBean.getLstRoomCategory().isEmpty()) {
      try {
        tenantSearchBean.setLstRoomCategory(roomCategoryService.find());
      } catch (RoomCategoryServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des catégories de local", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return tenantSearchBean.getLstRoomCategory();
  }

  /**
   * Récupère la liste des agences disponibles pour l’utilisateur
   * @return la liste des agences disponibles pour l’utilisateur
   */
  public List<AgencyDTO> getAgencies() {
    if (tenantSearchBean.getAgencies() == null || tenantSearchBean.getAgencies().isEmpty()) {
      try {
        UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);

        List<AgencyDTO> agencies = agencyService.findAllAgencies(user);
        tenantSearchBean.setAgencies(agencies);

        if (!user.getGroups().contains(Group.ADMINISTRATEUR)) {
          setDefaultAgency();
          updateSiteListForCurrentAgency();
        }
      } catch (AgencyServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des agences", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return tenantSearchBean.getAgencies();
  }

  /**
   * Permet de définir par défaut la valeur du champ « Agence » du formulaire
   */
  private void setDefaultAgency() {
    if (tenantSearchBean.getAgencies().size() == 1 && tenantSearchBean.getHousingCriteria().getAgency() == null) {
      tenantSearchBean.getHousingCriteria().setAgency(tenantSearchBean.getAgencies().get(0));
    } else if (tenantSearchBean.getAgencies().isEmpty()) {
      AgencyDTO agency = new AgencyDTO();
      agency.setId(UNUSED_ID);
      tenantSearchBean.getHousingCriteria().setAgency(agency);
    }
  }

  /**
   * Mise à jour de la liste de sites en fonction de l'agence choisie par l'utilisateur
   */
  public void updateSiteListForCurrentAgency() {
    try {
      // à chaque changement d'agence, on réinitialise le site sélectionné précédemment.
      tenantSearchBean.getHousingCriteria().setRattachmentSite(null);
      if (tenantSearchBean.getHousingCriteria().getAgency() != null) {
        tenantSearchBean.setSites(siteService.findSitesByAgency(tenantSearchBean.getHousingCriteria().getAgency().getId()));
      } else {
        tenantSearchBean.setSites(null);
      }
    } catch (SiteServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la récupération des sites", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
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

      tenantSearchBean.setLstResultSearch(tenantServiceFacade.findByCriteria(tenantSearchBean.getTenantCriteria(), tenantSearchBean.getHousingCriteria()));
    } catch (TenantServiceFacadeException e) {
      LOGGER.error("Une erreur est survenue lors de la recherche des occupants", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * Matche les String avec leur équivalent booléen
   * A cause du parseur Apache EL qui distingue pas les primitifs des wrapper et qui retourne les null en false,
   * on passe par des variables tampon pour la gestion de ces booléens
   */
  private void defineBooleanValue() {
    if (StringUtils.isEmpty(tenantSearchBean.getEmployee())) {
      tenantSearchBean.getTenantCriteria().setEmployee(null);
    } else {
      tenantSearchBean.getTenantCriteria().setEmployee(Boolean.parseBoolean(tenantSearchBean.getEmployee()));
    }

    if (StringUtils.isEmpty(tenantSearchBean.getManagerial())) {
      tenantSearchBean.getTenantCriteria().setManagerial(null);
    } else {
      tenantSearchBean.getTenantCriteria().setManagerial(Boolean.parseBoolean(tenantSearchBean.getManagerial()));
    }

    if (StringUtils.isEmpty(tenantSearchBean.getManagerialLastYear())) {
      tenantSearchBean.getTenantCriteria().setManagerialLastYear(null);
    } else {
      tenantSearchBean.getTenantCriteria().setManagerialLastYear(Boolean.parseBoolean(tenantSearchBean.getManagerialLastYear()));
    }
    if (StringUtils.isEmpty(tenantSearchBean.getProperty())) {
      tenantSearchBean.getHousingCriteria().setProperty(null);
    } else {
      tenantSearchBean.getHousingCriteria().setProperty(Boolean.parseBoolean(tenantSearchBean.getProperty()));
    }

    if (StringUtils.isEmpty(tenantSearchBean.getGardenAvailable())) {
      tenantSearchBean.getHousingCriteria().setGardenAvailable(null);
    } else {
      tenantSearchBean.getHousingCriteria().setGardenAvailable(Boolean.parseBoolean(tenantSearchBean.getGardenAvailable()));
    }

    if (StringUtils.isEmpty(tenantSearchBean.getGarageAvailable())) {
      tenantSearchBean.getHousingCriteria().setGarageAvailable(null);
    } else {
      tenantSearchBean.getHousingCriteria().setGarageAvailable(Boolean.parseBoolean(tenantSearchBean.getGarageAvailable()));
    }

    if (StringUtils.isEmpty(tenantSearchBean.getCore())) {
      tenantSearchBean.getHousingCriteria().setCore(null);
    } else {
      tenantSearchBean.getHousingCriteria().setCore(Boolean.parseBoolean(tenantSearchBean.getCore()));
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
    long idTenant = Long.parseLong(event.getComponent().getAttributes().get("data_id").toString());
    FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_TENANT, idTenant);

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
    return "EXIT_TENANT";
  }

  /************************************
   * Getter et Setter
   ************************************/

  /**
   * Getter du backing bean de la recherche des occupants
   * @return the tenantSearchBean
   */
  public TenantSearchBean getTenantSearchBean() {
    return tenantSearchBean;
  }

  /**
   * Setter du backing bean de la recherche des occupants
   * @param tenantSearchBean the tenantSearchBean to set
   */
  public void setTenantSearchBean(TenantSearchBean tenantSearchBean) {
    this.tenantSearchBean = tenantSearchBean;
  }

  /**
   * Setter du service des types d'occupant
   * @param typeTenantService the typeTenantService to set
   */
  public void setTypeTenantService(ITypeTenantService typeTenantService) {
    this.typeTenantService = typeTenantService;
  }

  /**
   * Renvoi le template de la pagination
   * @return une pagination des résultats
   */
  public String getPaginationTemplate() {
    List<String> finalPaginationRowPerPageTemplate = new ArrayList<>(ConstantsWEB.DEFAULT_ROW_PER_PAGE);
    int maximumRowPerPage = Integer.parseInt(finalPaginationRowPerPageTemplate.get(finalPaginationRowPerPageTemplate.size() - 1));
    if (tenantSearchBean.getLstResultSearch() != null && tenantSearchBean.getLstResultSearch().size() > maximumRowPerPage) {
      finalPaginationRowPerPageTemplate.add(String.valueOf(tenantSearchBean.getLstResultSearch().size()));
    }
    return StringUtils.join(finalPaginationRowPerPageTemplate, ", ");
  }

  /**
   * @param housingNatureService the housingNatureService to set
   */
  public void setHousingNatureService(IHousingNatureService housingNatureService) {
    this.housingNatureService = housingNatureService;
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
   * @param tenantServiceFacade the tenantServiceFacade to set
   */
  public void setTenantServiceFacade(ITenantServiceFacade tenantServiceFacade) {
    this.tenantServiceFacade = tenantServiceFacade;
  }

  public void setAgencyService(IAgencyService agencyService) {
    this.agencyService = agencyService;
  }

  public void setSiteService(ISiteService siteService) {
    this.siteService = siteService;
  }
}
