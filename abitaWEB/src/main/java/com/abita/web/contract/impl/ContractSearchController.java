/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.contract.impl;

import com.abita.services.agency.IAgencyService;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.dto.AgencyDTO;
import com.abita.dto.unpersist.ContractCriteriaDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.web.contract.bean.ContractSearchBean;
import com.abita.web.housing.bean.HousingSearchBean;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.tenant.bean.TenantSearchBean;
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
import java.util.List;

/**
 * Classe controleur de la page de recherche des contrats occupants
 */
public class ContractSearchController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 1366827215803704358L;

  /**
   *
   * PROPRIETES
   *
   */

  /** Service des contrats occupant */
  private transient IContractServiceFacade contractServiceFacade;

  /** Backing bean de la recherche des contrats occupant */
  private ContractSearchBean contractSearchBean;

  /** Backing bean de la recherche des occupants */
  private TenantSearchBean tenantSearchBean;

  /** Backing bean de la recherche des logements */
  private HousingSearchBean housingSearchBean;

  /** Service des agences */
  private transient IAgencyService agencyService;

  /** Nom de code pour l’erreur technique */
  private static final String TECHNICAL_ERROR_CODE = "technical.error";

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ContractSearchController.class);

  /** Identifiant technique non utilisé pour une agence */
  private static final Long UNUSED_ID = -1L;

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
    if (null == contractSearchBean.getContractCriteria()) {
      contractSearchBean.setContractCriteria(new ContractCriteriaDTO());
    }
    if (null == tenantSearchBean.getTenantCriteria()) {
      tenantSearchBean.setTenantCriteria(new TenantCriteriaDTO());
    }
    if (null == housingSearchBean.getHousingCriteria()) {
      housingSearchBean.setHousingCriteria(new HousingCriteriaDTO());
    }
  }

  /**
   * Permet de rechercher des contrats occupants
   */
  public void searchData() {
    try {
      // les utilisateurs non administrateurs ne doivent voir que la liste des agences auxquelles ils sont rattachés
      UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);
      if (!user.getGroups().contains(Group.ADMINISTRATEUR)) {
        setDefaultAgency();
      }

      ContractCriteriaDTO contractCriteria = contractSearchBean.getContractCriteria();
      TenantCriteriaDTO tenantCriteria = tenantSearchBean.getTenantCriteria();
      HousingCriteriaDTO housingCriteria = housingSearchBean.getHousingCriteria();
      contractSearchBean.setContractList(contractServiceFacade.findByCriteria(contractCriteria, tenantCriteria, housingCriteria));
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Une erreur est survenue lors de la recherche d'un contrat occupant", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("contract.search.error"));
    }
  }

  /**
   * Action lancée après la création du fichier excel contenant les informations des contrats occupants
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
   * Sur clic de la référence contrat occupant, on récupère l'id et on redirige vers la consultation
   * @param event : l'évènement
   */
  public void redirectConsult(ActionEvent event) {
    long idContract = Long.parseLong(event.getComponent().getAttributes().get("data_id").toString());
    FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_CONTRACT, idContract);

    // On redirige vers la consultation
    ConfigurableNavigationHandler configurableNavigationHandler = (ConfigurableNavigationHandler) FacesContext.getCurrentInstance().getApplication().getNavigationHandler();
    configurableNavigationHandler.performNavigation("GO_TO_CONSULTATION");
  }

  /**
   * Renvoi le template de la pagination
   * @return une pagination des résultats
   */
  public String getPaginationTemplate() {
    List<String> finalPaginationRowPerPageTemplate = new ArrayList<String>(ConstantsWEB.DEFAULT_ROW_PER_PAGE);
    int maximumRowPerPage = Integer.parseInt(finalPaginationRowPerPageTemplate.get(finalPaginationRowPerPageTemplate.size() - 1));
    if (contractSearchBean.getContractList() != null && contractSearchBean.getContractList().size() > maximumRowPerPage) {
      finalPaginationRowPerPageTemplate.add(String.valueOf(contractSearchBean.getContractList().size()));
    }
    return StringUtils.join(finalPaginationRowPerPageTemplate, ", ");
  }

  /**
   * Action du bouton "Annuler"
   *
   * @return le clé où aller
   */
  public String cancel() {
    return "EXIT_TENANT_CONTRACT";
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
        }
      } catch (AgencyServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des agences", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return housingSearchBean.getAgencies();
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
   *
   * GETTER/SETTER
   *
   *
   * **/

  /** Getter du Service des contrats occupants
   * @return the contractServiceFacade
   */
  public IContractServiceFacade getContractServiceFacade() {
    return contractServiceFacade;
  }

  /** Setter du service des contrats occupants
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

  /**Getter du backing bean de la recherche des contrats occupants
   * @return the contractSearchBean
   */
  public ContractSearchBean getContractSearchBean() {
    return contractSearchBean;
  }

  /** Setter du backing bean de la recherche des contrats occupants
   * @param contractSearchBean the contractSearchBean to set
   */
  public void setContractSearchBean(ContractSearchBean contractSearchBean) {
    this.contractSearchBean = contractSearchBean;
  }

  /**
   * @return the tenantSearchBean
   */
  public TenantSearchBean getTenantSearchBean() {
    return tenantSearchBean;
  }

  /**
   * @param tenantSearchBean the tenantSearchBean to set
   */
  public void setTenantSearchBean(TenantSearchBean tenantSearchBean) {
    this.tenantSearchBean = tenantSearchBean;
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
   * @param agencyService the agencyService to set
   */
  public void setAgencyService(IAgencyService agencyService) {
    this.agencyService = agencyService;
  }

}
