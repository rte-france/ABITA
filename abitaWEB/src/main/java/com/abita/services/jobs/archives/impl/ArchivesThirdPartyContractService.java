package com.abita.services.jobs.archives.impl;

import com.abita.dao.thirdpartycontract.entity.ThirdPartyContractEntity;
import com.abita.dao.thirdpartycontract.IThirdPartyContractDAO;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.Month;
import com.dao.common.exception.GenericDAOException;
import com.services.common.util.StringUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe d'implémentation pour la génération de l'archive des contrats Tiers
 *
 * @author
 */
public class ArchivesThirdPartyContractService extends AbstractArchivesServiceImpl {

  /**
  * Le DAO des Contrat Tiers
  */
  private IThirdPartyContractDAO thirdPartyContractDAO;

  @Override
  protected String defineFileName(int month, int year) {
    return ConstantsWEB.ARCHIVES_THIRD_PARTY_CONTRACT_FILE + Month.getLabelByValue(month) + Integer.toString(year) + ConstantsWEB.ARCHIVES_EXTENSION_FILE;
  }

  @Override
  protected String defineSheetName() {
    return ConstantsWEB.ARCHIVES_THIRD_PARTY_CONTRACT_SHEET;
  }

  @Override
  protected List<String> buildHeader() {
    List<String> lst = new ArrayList<String>();
    lst.add("Référence du contrat tiers");
    lst.add("Référence du tiers");
    lst.add("Référence du logement");
    lst.add("Domaine d'activité");
    lst.add("Agence");
    lst.add("Centre coût");
    lst.add("Durée du contrat");
    lst.add("Durée du préavis");
    lst.add("Date de début de validité");
    lst.add("Date de début du paiement fournisseur");
    lst.add("Montant du loyer mensuel");
    lst.add("Montant de la révision du loyer mensuel");
    lst.add("Date d'effet");
    lst.add("Montant des charges prévisionnelles mensuelles");
    lst.add("Montant de la révision des charges prévisionnelles mensuelles");
    lst.add("Date d'effet");
    lst.add("Montant du dépôt de garantie");
    lst.add("Montant des frais d'agence");
    lst.add("Périodicité du paiement");
    lst.add("Charge ponctuelle");
    lst.add("Date de résiliation du contrat");
    lst.add("Motif de résiliation");
    lst.add("Remboursement du dépôt de garantie");
    lst.add("Terme");
    return Collections.unmodifiableList(lst);
  }

  @Override
  protected void buildContent(HSSFSheet sheet) throws GenericDAOException {
    List<ThirdPartyContractEntity> lstThirdPartyContract = thirdPartyContractDAO.find();

    int numberLine = ConstantsWEB.FIRST_LINE_ARCHIVES_CONTENT;
    SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWEB.PATTERN_DATE_MM_DD_YYYY);

    for (ThirdPartyContractEntity thirdPartyContract : lstThirdPartyContract) {
      HSSFRow row = sheet.createRow(numberLine++);

      int numberCol = 0;

      // Référence du contrat tiers
      row.createCell(numberCol++).setCellValue(thirdPartyContract.getReference());

      // Référence du tiers
      row.createCell(numberCol++).setCellValue(thirdPartyContract.getThirdParty().getId());

      // Référence du logement
      row.createCell(numberCol++).setCellValue(thirdPartyContract.getHousing().getReference());

      // Domaine d'activité
      String fieldOfActivity = thirdPartyContract.getFieldOfActivity().getLabel() + " " + StringUtils.getNonNullValue(thirdPartyContract.getFieldOfActivity().getGmr());
      row.createCell(numberCol++).setCellValue(fieldOfActivity);

      // La valeur null en base équivaut à un contrat non cloturé
      boolean closedThirdPartyContract = BooleanUtils.isTrue(thirdPartyContract.getClosedThirdPartyContract());

      // Agence
      if (closedThirdPartyContract && thirdPartyContract.getFixedAgency() != null) {
        row.createCell(numberCol++).setCellValue(thirdPartyContract.getFixedAgency().getName());
      } else {
        row.createCell(numberCol++).setCellValue(thirdPartyContract.getHousing().getAgency().getName());
      }

      // Centre coût
      row.createCell(numberCol++).setCellValue(thirdPartyContract.getCostCenter().getLabel());

      // Durée du contrat
      row.createCell(numberCol++).setCellValue(thirdPartyContract.getContractPeriod());

      // Durée du préavis
      if (thirdPartyContract.getNoticePeriod() != null) {
        row.createCell(numberCol++).setCellValue(thirdPartyContract.getNoticePeriod());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Date de début de validité
      row.createCell(numberCol++).setCellValue(sdf.format(thirdPartyContract.getStartValidity()));

      // Date de début du paiement fournisseur
      row.createCell(numberCol++).setCellValue(sdf.format(thirdPartyContract.getStartSupplierPaymentDate()));

      // Montant du loyer mensuel
      row.createCell(numberCol++).setCellValue(thirdPartyContract.getRentAmount().doubleValue());

      // Montant de la révision du loyer mensuel
      if (thirdPartyContract.getRevisedRentAmount() != null) {
        row.createCell(numberCol++).setCellValue(thirdPartyContract.getRentAmount().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Date d'effet de la révision du loyer mensuel
      if (thirdPartyContract.getRevisedRentDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(thirdPartyContract.getRevisedRentDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Montant des charges prévisionnelles mensuelles
      row.createCell(numberCol++).setCellValue(thirdPartyContract.getExpectedChargeCost().doubleValue());

      // Montant de la révision des charges prévisionnelles mensuelles
      if (thirdPartyContract.getRevisedExpectedChargeCostAmount() != null) {
        row.createCell(numberCol++).setCellValue(thirdPartyContract.getRevisedExpectedChargeCostAmount().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Date d'effet de la révision des charges prévisionnelles mensuelles
      if (thirdPartyContract.getRevisedExpectedChargeCostDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(thirdPartyContract.getRevisedExpectedChargeCostDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Montant du dépôt de garantie
      if (thirdPartyContract.getGuaranteedDepositAmount() != null) {
        row.createCell(numberCol++).setCellValue(thirdPartyContract.getGuaranteedDepositAmount().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Montant des frais d'agence
      if (thirdPartyContract.getRealEstateAgencyFee() != null) {
        row.createCell(numberCol++).setCellValue(thirdPartyContract.getRealEstateAgencyFee().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Périodicité du paiement
      row.createCell(numberCol++).setCellValue(thirdPartyContract.getPaymentCycle().getLabel());

      // Charge ponctuelle
      if (thirdPartyContract.getSporadicallyInvoicing() != null) {
        row.createCell(numberCol++).setCellValue(thirdPartyContract.getSporadicallyInvoicing().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Date de résiliation du contrat
      if (thirdPartyContract.getCancellationDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(thirdPartyContract.getCancellationDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Motif de résiliation
      if (thirdPartyContract.getThirdPartyTermination() != null) {
        row.createCell(numberCol++).setCellValue(thirdPartyContract.getThirdPartyTermination().getLabel());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Remboursement du dépôt de garantie
      if (thirdPartyContract.getGuaranteedDepositRefund() != null && thirdPartyContract.getGuaranteedDepositRefund()) {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      }

      // Terme échu / échoir
      if (thirdPartyContract.getExpiryDate() != null && thirdPartyContract.getExpiryDate()) {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.EXPIRY_DATE);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.FALL_DUE);
      }
    }
  }

  /**
   * @param thirdPartyContractDAO the thirdPartyContractDAO to set
   */
  public void setThirdPartyContractDAO(IThirdPartyContractDAO thirdPartyContractDAO) {
    this.thirdPartyContractDAO = thirdPartyContractDAO;
  }

}
