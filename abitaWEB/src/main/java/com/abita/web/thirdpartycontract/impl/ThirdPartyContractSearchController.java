/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.thirdpartycontract.impl;

import com.abita.services.agency.IAgencyService;
import com.abita.services.thirdpartycontract.IThirdPartyContractServiceFacade;
import com.abita.web.thirdpartycontract.bean.ThirdPartyContractSearchBean;
import com.abita.dto.AgencyDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.ThirdPartyContractCriteriaDTO;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.services.thirdpartycontract.exceptions.ThirdPartyContractServiceException;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
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
 * Classe controleur de la page de recherche des contrats tiers
 */
public class ThirdPartyContractSearchController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 240402719577002673L;

  /** Service des agences */
  private transient IAgencyService agencyService;

  /** Nom de code pour l’erreur technique */
  private static final String TECHNICAL_ERROR_CODE = "technical.error";

  /** Identifiant technique non utilisé pour une agence */
  private static final Long UNUSED_ID = -1L;

  /**
   *
   * PROPRIETES
   *
   */

  /** Service des contrats tiers */
  private transient IThirdPartyContractServiceFacade thirdPartyContractServiceFacade;

  /** Backing bean de la recherche des contrats tiers */
  private ThirdPartyContractSearchBean thirdPartyContractSearchBean;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyContractSearchController.class);

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
    if (null == thirdPartyContractSearchBean.getThirdPartyContractCriteria()) {
      thirdPartyContractSearchBean.setThirdPartyContractCriteria(new ThirdPartyContractCriteriaDTO());
    }
    if (null == thirdPartyContractSearchBean.getHousingCriteria()) {
      thirdPartyContractSearchBean.setHousingCriteria(new HousingCriteriaDTO());
    }
  }

  /**
   * Permet de rechercher des contrats tiers
   */
  public void searchData() {
    try {
      defineBooleanValue();

      // les utilisateurs non administrateurs ne doivent voir que la liste des agences auxquelles ils sont rattachés
      UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);
      if (!user.getGroups().contains(Group.ADMINISTRATEUR)) {
        setDefaultAgency();
      }

      ThirdPartyContractCriteriaDTO thirdPartyContractCriteria = thirdPartyContractSearchBean.getThirdPartyContractCriteria();
      HousingCriteriaDTO housingCriteria = thirdPartyContractSearchBean.getHousingCriteria();

      thirdPartyContractSearchBean.setThirdPartyContractList(thirdPartyContractServiceFacade.findByCriteria(thirdPartyContractCriteria, housingCriteria));
    } catch (ThirdPartyContractServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la recherche d'un contrat tiers", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdpartycontract.search.error"));
    }
  }

  /**
   * Matche les String avec leur équivalent booléen
   * A cause du parseur Apache EL qui distingue pas les primitifs des wrapper et qui retourne les null en false,
   * on passe par des variables tampon pour la gestion de ces booléens
   */
  private void defineBooleanValue() {
    if (StringUtils.isEmpty(thirdPartyContractSearchBean.getGuaranteedDepositRefund())) {
      thirdPartyContractSearchBean.getThirdPartyContractCriteria().setGuaranteedDepositRefund(null);
    } else {
      thirdPartyContractSearchBean.getThirdPartyContractCriteria().setGuaranteedDepositRefund(Boolean.parseBoolean(thirdPartyContractSearchBean.getGuaranteedDepositRefund()));
    }

    if (StringUtils.isEmpty(thirdPartyContractSearchBean.getExpiryDate())) {
      thirdPartyContractSearchBean.getThirdPartyContractCriteria().setExpiryDate(null);
    } else {
      thirdPartyContractSearchBean.getThirdPartyContractCriteria().setExpiryDate(Boolean.parseBoolean(thirdPartyContractSearchBean.getExpiryDate()));
    }
  }

  /**
   * Action lancée après la création du fichier excel contenant les informations des contrats tiers
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
   * Sur clic de la référence GCP, on récupère l'id et on redirige vers la consultation
   * @param event : l'évènement
   */
  public void redirectConsult(ActionEvent event) {
    long idThirdParty = Long.parseLong(event.getComponent().getAttributes().get("data_id").toString());
    FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_THIRD_PARTY_CONTRACT, idThirdParty);

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
    return "EXIT_THIRD_PARTY_CONTRACT";
  }

  /**
   * Récupère la liste des agences disponibles pour l’utilisateur
   * @return la liste des agences disponibles pour l’utilisateur
   */
  public List<AgencyDTO> getAgencies() {
    if (thirdPartyContractSearchBean.getAgencies() == null || thirdPartyContractSearchBean.getAgencies().isEmpty()) {
      try {
        UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(Constants.USER_KEY);

        List<AgencyDTO> agencies = agencyService.findAllAgencies(user);
        thirdPartyContractSearchBean.setAgencies(agencies);

        if (!user.getGroups().contains(Group.ADMINISTRATEUR)) {
          setDefaultAgency();
        }
      } catch (AgencyServiceException ex) {
        LOGGER.error("Une erreur est survenue lors de la récupération des agences", ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
      }
    }
    return thirdPartyContractSearchBean.getAgencies();
  }

  /**
   * Permet de définir par défaut la valeur du champ « Agence » du formulaire
   */
  private void setDefaultAgency() {
    if (thirdPartyContractSearchBean.getAgencies().size() == 1 && thirdPartyContractSearchBean.getHousingCriteria().getAgency() == null) {
      thirdPartyContractSearchBean.getHousingCriteria().setAgency(thirdPartyContractSearchBean.getAgencies().get(0));
    } else if (thirdPartyContractSearchBean.getAgencies().isEmpty()) {
      AgencyDTO agency = new AgencyDTO();
      agency.setId(UNUSED_ID);
      thirdPartyContractSearchBean.getHousingCriteria().setAgency(agency);
    }
  }

  /**
   *
   * GETTER/SETTER
   *
   *
   * **/

  /**
   * Getter du backing bean de la recherche des contrats tiers
   * @return the thirdPartySearchBean
   */
  public ThirdPartyContractSearchBean getThirdPartyContractSearchBean() {
    return thirdPartyContractSearchBean;
  }

  /**
   * Setter du backing bean de la recherche des contrats tiers
   * @param thirdPartyContractSearchBean the thirdPartySearchBean to set
   */
  public void setThirdPartyContractSearchBean(ThirdPartyContractSearchBean thirdPartyContractSearchBean) {
    this.thirdPartyContractSearchBean = thirdPartyContractSearchBean;
  }

  /**
   * Setter du service des contrats tiers
   * @param thirdPartyContractServiceFacade the thirdPartyServiceFacade to set
   */
  public void setThirdPartyContractServiceFacade(IThirdPartyContractServiceFacade thirdPartyContractServiceFacade) {
    this.thirdPartyContractServiceFacade = thirdPartyContractServiceFacade;
  }

  /**
   * Renvoi le template de la pagination
   * @return une pagination des résultats
   */
  public String getPaginationTemplate() {
    List<String> finalPaginationRowPerPageTemplate = new ArrayList<String>(ConstantsWEB.DEFAULT_ROW_PER_PAGE);
    int maximumRowPerPage = Integer.parseInt(finalPaginationRowPerPageTemplate.get(finalPaginationRowPerPageTemplate.size() - 1));
    if (thirdPartyContractSearchBean.getThirdPartyContractList() != null && thirdPartyContractSearchBean.getThirdPartyContractList().size() > maximumRowPerPage) {
      finalPaginationRowPerPageTemplate.add(String.valueOf(thirdPartyContractSearchBean.getThirdPartyContractList().size()));
    }
    return StringUtils.join(finalPaginationRowPerPageTemplate, ", ");
  }

  /**
   * @param agencyService the agencyService to set
   */
  public void setAgencyService(IAgencyService agencyService) {
    this.agencyService = agencyService;
  }

}
