package com.abita.web.thirdparty.impl;

import com.abita.services.thirdparty.IThirdPartyService;
import com.abita.web.thirdparty.bean.ThirdPartySearchBean;
import com.abita.dto.unpersist.ThirdPartyCriteriaDTO;
import com.abita.services.thirdparty.exceptions.ThirdPartyServiceException;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
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
 * Classe controleur de la page de recherche des tiers
 * @author
 *
 */
public class ThirdPartySearchController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 240402719577002673L;

  /**
   *
   * PROPRIETES
   *
   */

  /** Service des tiers */
  private transient IThirdPartyService thirdPartyService;

  /** Backing bean de la recherche des tiers */
  private ThirdPartySearchBean thirdPartySearchBean;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartySearchController.class);

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
    if (null == thirdPartySearchBean.getThirdPartyCriteria()) {
      thirdPartySearchBean.setThirdPartyCriteria(new ThirdPartyCriteriaDTO());
    }
  }

  /**
   * Permet de rechercher des tiers
   */
  public void searchData() {
    try {
      thirdPartySearchBean.setThirdPartyList(thirdPartyService.findByCriteria(thirdPartySearchBean.getThirdPartyCriteria()));
    } catch (ThirdPartyServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la recherche d'un tiers", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdparty.search.error"));
    }

  }

  /**
   * Action lancée après la création du fichier excel contenant les informations des tiers
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
    FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_THIRD_PARTY, idThirdParty);

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
    return "EXIT_THIRD_PARTY";
  }

  /**
   *
   * GETTER/SETTER
   *
   *
   * **/

  /**
   * Getter du backing bean de la recherche des tiers
   * @return the thirdPartySearchBean
   */
  public ThirdPartySearchBean getThirdPartySearchBean() {
    return thirdPartySearchBean;
  }

  /**
   * Setter du backing bean de la recherche des tiers
   * @param thirdPartySearchBean the thirdPartySearchBean to set
   */
  public void setThirdPartySearchBean(ThirdPartySearchBean thirdPartySearchBean) {
    this.thirdPartySearchBean = thirdPartySearchBean;
  }

  /**
   * Setter du service des tiers
   * @param thirdPartyService the thirdPartyService to set
   */
  public void setThirdPartyService(IThirdPartyService thirdPartyService) {
    this.thirdPartyService = thirdPartyService;
  }

  /**
   * Renvoi le template de la pagination
   * @return une pagination des résultats
   */
  public String getPaginationTemplate() {
    List<String> finalPaginationRowPerPageTemplate = new ArrayList<String>(ConstantsWEB.DEFAULT_ROW_PER_PAGE);
    int maximumRowPerPage = Integer.parseInt(finalPaginationRowPerPageTemplate.get(finalPaginationRowPerPageTemplate.size() - 1));
    if (thirdPartySearchBean.getThirdPartyList() != null && thirdPartySearchBean.getThirdPartyList().size() > maximumRowPerPage) {
      finalPaginationRowPerPageTemplate.add(String.valueOf(thirdPartySearchBean.getThirdPartyList().size()));
    }
    return StringUtils.join(finalPaginationRowPerPageTemplate, ", ");
  }
}
