/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.quittancement.impl;

import com.abita.services.jobs.quittancement.constants.QuittancementConstants;
import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.contract.ContractUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.abita.dto.ContractDTO;
import com.abita.dto.HistoryContractDTO;
import com.abita.dto.HousingDTO;
import com.abita.dto.TenantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;

import java.text.MessageFormat;
import java.util.List;

/**
 * Classe d’aide à la génération du quittancement pour la gestion de l’en-tête et pied de page
 */
public class HeaderFooter extends PdfPageEventHelper {

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(HeaderFooter.class);

  /**
   * Contrat traité
   */
  private ContractDTO contractDTO;

  /**
   * Tenant traité
   */
  private TenantDTO tenantDTO;

  /**
   * Housing traité
   */
  private HousingDTO housingDTO;

  /**
   * Premier jour du mois du contrat manipulé
   */
  private LocalDate firstDayDate;

  /**
   * Dernier jour du mois du contrat manipulé
   */
  private LocalDate lastDayDate;

  /**
   * Liste des contrats occupants historisés
   */
  private List<HistoryContractDTO> historyContractDTOs;

  /**
   * @param firstGenerationDate date de la première génération
   * @param generationDate      date de génération
   * @param contractDTO         informations du contrat
   * @param historyContractDTOs historisations du contrat
   */
  public HeaderFooter(LocalDate firstGenerationDate, LocalDate generationDate, ContractDTO contractDTO, List<HistoryContractDTO> historyContractDTOs) {
    LocalDate startingDate;
    LocalDate endDate;
    // En cas d'une cloture dans le passé, la date de début est la date de début du remboursement
    if (contractDTO.getRetroactivitysMonths() < 0 && contractDTO.getEndValidityDate() != null) {
      // on récupère les dates de remboursement en cas de modification de la date de fin dans le passé
      startingDate = new LocalDate(contractDTO.getEndValidityDate());
    } else {
      startingDate = ContractUtils.getFirstDayOfMonth(contractDTO, firstGenerationDate);
    }
    // En cas de cloture dans le passé et si il n'y a pas de facturation ponctuelle la date de fin de quittancement est la date de fin du
    // remboursement
    if (contractDTO.getRetroactivitysMonths() < 0 && contractDTO.getEndValidityDate() != null && !ContractUtils.hasSporadicallyInvoicing(contractDTO)) {
      endDate = new LocalDate(generationDate).minusMonths(1).dayOfMonth().withMaximumValue();
    } else if (contractDTO.getRetroactivitysMonths() > 0 && contractDTO.getEndValidityDate() != null
      && contractDTO.getEndValidityDate().before(DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(generationDate).toDate()) && !ContractUtils.hasSporadicallyInvoicing(contractDTO)) {
      endDate = new LocalDate(contractDTO.getEndValidityDate());
    } else {
      endDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);
    }
    firstDayDate = startingDate;
    lastDayDate = endDate;
    this.contractDTO = contractDTO;
    tenantDTO = contractDTO.getTenant();
    housingDTO = contractDTO.getHousing();
    this.historyContractDTOs = historyContractDTOs;
  }

  @Override
  public void onEndPage(PdfWriter writer, Document document) {
    /* Header : début */
    Rectangle header = writer.getBoxSize(QuittancementConstants.HEADER_BOX_NAME);
    ColumnText contentHeader = new ColumnText(writer.getDirectContent());
    contentHeader.setSimpleColumn(header);

    insertSender(contentHeader);
    insertRecipient(contentHeader);
    insertDay(contentHeader, new LocalDate());
    /* Header : fin */

    /* Footer : début */
    Rectangle footer = writer.getBoxSize(QuittancementConstants.FOOTER_BOX_NAME);
    ColumnText contentFooter = new ColumnText(writer.getDirectContent());
    contentFooter.setSimpleColumn(footer);

    insertSummary(contentFooter);
    /* Footer : fin */

    try {
      contentHeader.go();
      contentFooter.go();
    } catch (DocumentException e) {
      LOGGER.error("Erreur lors du traitement du document", e);
    }
  }

  /**
   * @param content document en cours de traitement
   */
  private void insertSender(ColumnText content) {
    Paragraph sender = new Paragraph();
    sender.setFont(QuittancementConstants.ITEM_FONT);
    sender.add(QuittancementConstants.SENDER_COMPANY_NAME);
    sender.add(Chunk.NEWLINE);
    sender.add(QuittancementConstants.SENDER_PLACE);
    sender.add(Chunk.NEWLINE);
    sender.add(QuittancementConstants.SENDER_DEPARTMENT_NAME);
    sender.add(Chunk.NEWLINE);
    sender.add(QuittancementConstants.SENDER_ADRESS);
    sender.add(Chunk.NEWLINE);
    sender.add(QuittancementConstants.SENDER_POSTAL_CODE_TOWN);
    sender.add(Chunk.NEWLINE);
    sender.add(Chunk.NEWLINE);

    content.addElement(sender);
  }

  /**
   * @param content document en cours de traitement
   */
  private void insertRecipient(ColumnText content) {
    Paragraph recipient = new Paragraph();
    recipient.setFont(QuittancementConstants.ITEM_FONT);
    recipient.setSpacingBefore(QuittancementConstants.SPACING_SMALL_BETWEEN_ELEMENTS);
    recipient.setIndentationLeft(QuittancementConstants.INDENTATION_RIGHT);
    recipient.add(MessageFormat.format(QuittancementConstants.RECIPIENT_LASTNAME_FIRSTNAME, tenantDTO.getLastName()));
    recipient.add(Chunk.NEWLINE);
    if (null != tenantDTO.getAddress() && null != tenantDTO.getPostalCode() && null != tenantDTO.getCity()) {
      recipient.add(tenantDTO.getAddress().trim());
      recipient.add(Chunk.NEWLINE);
      recipient.add(MessageFormat.format(QuittancementConstants.RECIPIENT_POSTAL_CODE_TOWN, tenantDTO.getPostalCode(), tenantDTO.getCity()));
    } else {
      recipient.add(housingDTO.getAddress().trim());
      recipient.add(Chunk.NEWLINE);
      recipient.add(MessageFormat.format(QuittancementConstants.RECIPIENT_POSTAL_CODE_TOWN, housingDTO.getPostalCode(), housingDTO.getCity()));
    }
    recipient.add(Chunk.NEWLINE);
    content.addElement(recipient);
  }

  /**
   * @param content        document en cours de traitement
   * @param generationDate date de génération
   */
  private void insertDay(ColumnText content, LocalDate generationDate) {
    Paragraph day = new Paragraph();
    day.setFont(QuittancementConstants.ITEM_FONT);
    day.setSpacingBefore(QuittancementConstants.SPACING_BIG_BETWEEN_ELEMENTS);
    day.setIndentationLeft(QuittancementConstants.INDENTATION_RIGHT);
    day.add(MessageFormat.format(QuittancementConstants.CURRENT_DATE_AND_PLACE, QuittancementConstants.DATE_FORMAT_LONG.print(generationDate)));
    content.addElement(day);
  }

  /**
   * @param content document en cours de traitement
   */
  private void insertSummary(ColumnText content) {
    LocalDate generationDate = new LocalDate();
    String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
    String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

    Paragraph summary = new Paragraph();
    summary.setFont(QuittancementConstants.ITEM_FONT);
    summary.setIndentationLeft(QuittancementConstants.INDENTATION_RIGHT);
    summary.add(MessageFormat.format(QuittancementConstants.CONTRACT_REFERENCE, contractDTO.getContractReference()));
    summary.add(Chunk.NEWLINE);
    summary.add(MessageFormat.format(QuittancementConstants.HOUSING_REFERENCE, housingDTO.getReference()));
    summary.add(Chunk.NEWLINE);
    summary.add(MessageFormat.format(QuittancementConstants.TENANT_FIRSTNAME_LASTNAME, tenantDTO.getLastName()));
    summary.add(Chunk.NEWLINE);
    summary.add(MessageFormat.format(QuittancementConstants.QUITTANCEMENT_PERIOD, firstDay, lastDay));
    summary.add(Chunk.NEWLINE);
    summary.add(MessageFormat.format(QuittancementConstants.TOTAL_PRICE_FOOTER,
      BigDecimalUtils.formatFranceNumber(ContractUtils.withdrawnRentAmountWithAllInvoicingAndRegularization(contractDTO, historyContractDTOs, generationDate))));
    content.addElement(summary);
  }

}
