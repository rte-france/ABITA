/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.archives.impl;

import com.abita.dao.contract.entity.ContractEntity;
import com.abita.util.contract.ContractUtils;
import com.abita.dao.contract.IContractDAO;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.Month;
import com.dao.common.exception.GenericDAOException;
import com.services.common.util.StringUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe d'implémentation pour la génération de l'archive des Logements
 *
 * @author
 */
public class ArchivesContractService extends AbstractArchivesServiceImpl {

  /**
  * Le DAO des Contrats occupants
  */
  private IContractDAO contractDAO;

  @Override
  protected String defineFileName(int month, int year) {
    return ConstantsWEB.ARCHIVES_CONTRACT_FILE + Month.getLabelByValue(month) + Integer.toString(year) + ConstantsWEB.ARCHIVES_EXTENSION_FILE;
  }

  @Override
  protected String defineSheetName() {
    return ConstantsWEB.ARCHIVES_CONTRACT_SHEET;
  }

  @Override
  protected List<String> buildHeader() {
    List<String> lst = new ArrayList<String>();
    lst.add("Référence contrat occupant");
    lst.add("Référence occupant");
    lst.add("Référence du logement");
    lst.add("Domaine d'activité");
    lst.add("Agence");
    lst.add("Centre coût");
    lst.add("Typologie de loyer");
    lst.add("Mode paiement");
    lst.add("Contrat signé");
    lst.add("Date de début de validité");
    lst.add("Date de fin de validité");
    lst.add("Motif de résiliation");
    lst.add("Surface corrigée");
    lst.add("Loyer surface corrigée");
    lst.add("Nombre d'occupants");
    lst.add("Coefficient n/N");
    lst.add("Abattement de précarité");
    lst.add("Loyer marché");
    lst.add("Loyer plafond");
    lst.add("Loyer écrêté");
    lst.add("Loyer net agent");
    lst.add("Loyer garage");
    lst.add("Loyer jardin");
    lst.add("Surloyer");
    lst.add("Charges prévisionnelles");
    lst.add("Facturation ponctuelle");
    lst.add("Date limite de validité de l'attestation d'assurance");
    lst.add("Loyer prélevé");
    lst.add("Loyer prélevé cumulé sur l'année");
    lst.add("Pécule de fin d'occupation");
    lst.add("Règlement du pécule de fin d'occupation");
    lst.add("En date du");
    lst.add("Montant du pécule de fin d'occupation");
    lst.add("Avantage en nature");
    lst.add("Montant de la valeur locative foncière");

    return Collections.unmodifiableList(lst);
  }

  @Override
  protected void buildContent(HSSFSheet sheet) throws GenericDAOException {
    List<ContractEntity> lstContract = contractDAO.find();

    int numberLine = ConstantsWEB.FIRST_LINE_ARCHIVES_CONTENT;
    SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWEB.PATTERN_DATE_MM_DD_YYYY);

    for (ContractEntity contract : lstContract) {
      HSSFRow row = sheet.createRow(numberLine++);

      // La valeur null en base équivaut à un contrat non cloturé
      boolean closedContract = BooleanUtils.isTrue(contract.getClosedContract());

      int numberCol = 0;

      // Référence contrat occupant
      row.createCell(numberCol++).setCellValue(contract.getContractReference());

      // Référence occupant
      row.createCell(numberCol++).setCellValue(contract.getTenant().getReference());

      // Référence logement
      row.createCell(numberCol++).setCellValue(contract.getHousing().getReference());

      // Domaine d'activité
      String fieldOfActivty = contract.getFieldOfActivity().getLabel() + " " + StringUtils.getNonNullValue(contract.getFieldOfActivity().getGmr());
      row.createCell(numberCol++).setCellValue(fieldOfActivty);

      // Agence, fixe si contrat clos
      if (closedContract && contract.getFixedAgency() != null) {
        row.createCell(numberCol++).setCellValue(contract.getFixedAgency().getName());
      } else {
        row.createCell(numberCol++).setCellValue(contract.getHousing().getAgency().getName());
      }

      // Centre coût
      row.createCell(numberCol++).setCellValue(contract.getCostCenter().getLabel());

      // Typologie de loyer
      row.createCell(numberCol++).setCellValue(contract.getRentTypology().getLabel());

      // Mode de paiement
      row.createCell(numberCol++).setCellValue(contract.getPaymentMethod().getLabel());

      // Contrat signé
      if (null != contract.getSignature()) {
        if (contract.getSignature()) {
          row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
        } else {
          row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
        }
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Date d'entrée
      row.createCell(numberCol++).setCellValue(sdf.format(contract.getStartValidityDate()));

      // Date de sortie
      if (contract.getEndValidityDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(contract.getEndValidityDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Motif de résiliation
      if (contract.getTermination() != null) {
        row.createCell(numberCol++).setCellValue(contract.getTermination().getLabel());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Surface corrigée, fixe si contrat clos
      if (closedContract && contract.getFixedRevisedSurfaceArea() != null) {
        row.createCell(numberCol++).setCellValue(contract.getFixedRevisedSurfaceArea().doubleValue());
      } else if (!closedContract && contract.getRevisedSurfaceArea() != null) {
        row.createCell(numberCol++).setCellValue(contract.getRevisedSurfaceArea().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Loyer surface corrigée, fixe si contrat clos
      if (closedContract && contract.getFixedRevisedSurfaceAreaRent() != null) {
        row.createCell(numberCol++).setCellValue(contract.getFixedRevisedSurfaceAreaRent().doubleValue());
      } else if (!closedContract && contract.getRevisedSurfaceAreaRent() != null) {
        row.createCell(numberCol++).setCellValue(contract.getRevisedSurfaceAreaRent().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Nombre d'occupants
      if (contract.getHouseholdSize() != null) {
        row.createCell(numberCol++).setCellValue(contract.getHouseholdSize());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Coefficient n/N, fixe si contrat clos
      if (closedContract && contract.getFixedNNCoef() != null) {
        row.createCell(numberCol++).setCellValue(contract.getFixedNNCoef().doubleValue());
      } else if (!closedContract && contract.getnNCoef() != null) {
        row.createCell(numberCol++).setCellValue(contract.getnNCoef().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Abattement de précarité
      if (contract.getShortTermContractDiscount() != null) {
        row.createCell(numberCol++).setCellValue(contract.getShortTermContractDiscount().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Loyer marché
      if (contract.getMarketRentPrice() != null) {
        row.createCell(numberCol++).setCellValue(contract.getMarketRentPrice().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Loyer plafond
      if (contract.getRentPriceLimit() != null) {
        row.createCell(numberCol++).setCellValue(contract.getRentPriceLimit().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Loyer écrêté, fixe si contrat clos
      if (closedContract && contract.getFixedLopRent() != null) {
        row.createCell(numberCol++).setCellValue(contract.getFixedLopRent().doubleValue());
      } else if (!closedContract && contract.getLopRent() != null) {
        row.createCell(numberCol++).setCellValue(contract.getLopRent().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Loyer net agent
      if (contract.getNetAgentRent() != null) {
        row.createCell(numberCol++).setCellValue(contract.getNetAgentRent().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Loyer garage
      if (contract.getGarageRent() != null) {
        row.createCell(numberCol++).setCellValue(contract.getGarageRent().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Loyer jardin
      if (contract.getGardenRent() != null) {
        row.createCell(numberCol++).setCellValue(contract.getGardenRent().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Loyer surloyer
      if (contract.getExtraRent() != null) {
        row.createCell(numberCol++).setCellValue(contract.getExtraRent().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Charges prévisionnelle
      if (contract.getExpectedChargeCost() != null) {
        row.createCell(numberCol++).setCellValue(contract.getExpectedChargeCost().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Facturation ponctuelle
      if (contract.getAnnualClearanceCharges() != null) {
        row.createCell(numberCol++).setCellValue(contract.getAnnualClearanceCharges().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Date limite de validité de l'attestation d'assurance
      if (contract.getInsuranceCertificateEndDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(contract.getInsuranceCertificateEndDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Loyer prélevé
      if (contract.getFixedWithdrawnRent() != null) {
        row.createCell(numberCol++).setCellValue(contract.getFixedWithdrawnRent().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      LocalDate generationDate = new LocalDate();
      // Loyer prélevé cumulé
      BigDecimal addedWithdrawnRent = contract.getPlainAddedWithdrawnRent();
      if (contract.getFixedWithdrawnRent() != null && contract.getRetroactivitysMonths() == 0) {
        addedWithdrawnRent = addedWithdrawnRent.add(contract.getFixedWithdrawnRent());
      } else {
        addedWithdrawnRent = addedWithdrawnRent.add(ContractUtils.withdrawnRentAmountWithAllInvoicing(contract, generationDate));
      }
      row.createCell(numberCol++).setCellValue(addedWithdrawnRent.doubleValue());

      // Pécule de fin d'occupation
      if (contract.getTerminationSavings()) {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      }

      // Règlement du pécule de fin d'occupation
      if (contract.getTerminationSavingsPayment()) {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      }

      // Date du règlement du pécule de fin d'occupation
      if (contract.getTerminationSavingsPaymentDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(contract.getTerminationSavingsPaymentDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Montant du pécule de fin d'occupation
      if (contract.getPlainTerminationSavingAmount() != null && contract.getTerminationSavings()) {
        BigDecimal terminationSavingsAmount = contract.getPlainTerminationSavingAmount();
        terminationSavingsAmount = terminationSavingsAmount.add(ContractUtils.terminationSavingMonth(contract, generationDate));
        row.createCell(numberCol++).setCellValue(terminationSavingsAmount.doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Avantage en nature, fixe si contrat clos
      if (closedContract && contract.getFixedBenefit() != null) {
        row.createCell(numberCol++).setCellValue(contract.getFixedBenefit().doubleValue());
      } else if (!closedContract && contract.getBenefit() != null) {
        row.createCell(numberCol++).setCellValue(contract.getBenefit().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Montant de la valeur locative foncière
      if (contract.getRealEstateRentalValue() != null) {
        row.createCell(numberCol++).setCellValue(contract.getRealEstateRentalValue().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }
    }
  }

  /**
   * @param contractDAO the contractDAO to set
   */
  public void setContractDAO(IContractDAO contractDAO) {
    this.contractDAO = contractDAO;
  }

}
