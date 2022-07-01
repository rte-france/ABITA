package com.abita.services.jobs.archives.impl;

import com.abita.dao.thirdparty.entity.ThirdPartyEntity;
import com.abita.dao.thirdparty.IThirdPartyDAO;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.Month;
import com.dao.common.exception.GenericDAOException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe d'implémentation pour la génération de l'archive des Tiers
 *
 * @author
 */
public class ArchivesThirdPartyService extends AbstractArchivesServiceImpl {

  /**
  * Le DAO des Tiers
  */
  private IThirdPartyDAO thirdPartyDAO;

  @Override
  protected String defineFileName(int month, int year) {
    return ConstantsWEB.ARCHIVES_THIRD_PARTY_FILE + Month.getLabelByValue(month) + Integer.toString(year) + ConstantsWEB.ARCHIVES_EXTENSION_FILE;
  }

  @Override
  protected String defineSheetName() {
    return ConstantsWEB.ARCHIVES_THIRD_PARTY_SHEET;
  }

  @Override
  protected List<String> buildHeader() {
    List<String> lst = new ArrayList<String>();
    lst.add("Référence");
    lst.add("Référence fournisseur GCP");
    lst.add("Nom");
    lst.add("Adresse");
    lst.add("Code Postal");
    lst.add("Ville");
    lst.add("Téléphone");
    lst.add("Email");
    lst.add("Bénéficiaire du paiement");
    lst.add("Adresse du bénéficiaire du paiement");
    return Collections.unmodifiableList(lst);
  }

  @Override
  protected void buildContent(HSSFSheet sheet) throws GenericDAOException {
    List<ThirdPartyEntity> lstThirdParty = thirdPartyDAO.find();

    int numberLine = ConstantsWEB.FIRST_LINE_ARCHIVES_CONTENT;
    for (ThirdPartyEntity thirdparty : lstThirdParty) {
      HSSFRow row = sheet.createRow(numberLine++);

      int numberCol = 0;
      row.createCell(numberCol++).setCellValue(thirdparty.getId());
      row.createCell(numberCol++).setCellValue(thirdparty.getGcpReference());
      row.createCell(numberCol++).setCellValue(thirdparty.getName());
      row.createCell(numberCol++).setCellValue(thirdparty.getAddress());
      row.createCell(numberCol++).setCellValue(thirdparty.getPostalCode());
      row.createCell(numberCol++).setCellValue(thirdparty.getCity());
      row.createCell(numberCol++).setCellValue(thirdparty.getPhone());
      row.createCell(numberCol++).setCellValue(thirdparty.getMailAddress());
      row.createCell(numberCol++).setCellValue(thirdparty.getBeneficiaryName());
      row.createCell(numberCol++).setCellValue(thirdparty.getBeneficiaryAddress());
    }
  }

  /**
   * @param thirdPartyDAO the thirdPartyDAO to set
   */
  public void setThirdPartyDAO(IThirdPartyDAO thirdPartyDAO) {
    this.thirdPartyDAO = thirdPartyDAO;
  }

}
