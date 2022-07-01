package com.abita.services.jobs.quittancement.impl;

import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.historyamount.IHistoryAmountService;
import com.abita.services.historyamount.exceptions.HistoryAmountServiceException;
import com.abita.services.historycontract.IHistoryContractServiceFacade;
import com.abita.services.historycontract.exceptions.HistoryContractServiceException;
import com.abita.services.jobs.quittancement.IQuittancementServiceFacade;
import com.abita.services.jobs.quittancement.constants.QuittancementConstants;
import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.contract.ContractUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.util.exceptions.UtilException;
import com.abita.util.ftp.FTPUtils;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.jcraft.jsch.Channel;
import com.abita.dto.ContractDTO;
import com.abita.dto.HistoryAmountDTO;
import com.abita.dto.HistoryContractDTO;
import com.abita.dto.HousingDTO;
import com.dto.UserDTO;
import com.services.paramservice.ParameterService;
import com.services.paramservice.constants.ParamServiceConstants;
import com.services.paramservice.exception.ParameterServiceException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.springframework.util.Assert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Classe d’implémentation des services façades de quittancement
 */
public class QuittancementServiceFacadeImpl implements IQuittancementServiceFacade {

  /**
   * Service des contrats occupants
   */
  private IContractServiceFacade contractServiceFacade;

  /**
   * Service parametreService
   */
  private ParameterService parameterService;

  /**
   * Service d'historisation des contrats occupants
   */
  private IHistoryContractServiceFacade historyContractServiceFacade;

  /**
   * Service d'historisation des montants
   */
  private IHistoryAmountService historyAmountService;

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(QuittancementServiceFacadeImpl.class);

  @Override
  public void generate() {
    try {
      List<ContractDTO> contracts = contractServiceFacade.findContractsInProgressAndToRegularizeWithoutInternalCompensation();

      // Récupération du répertoire où sont stockés les fichiers
      String tempPath = parameterService.getParameterValue(ParamServiceConstants.ACCESS_DOMAIN_KEY, QuittancementConstants.PATH_TEMP_QUITTANCEMENT_FOLDER_PARAMETER_KEY);
      String path = parameterService.getParameterValue(ParamServiceConstants.ACCESS_DOMAIN_KEY, QuittancementConstants.PATH_QUITTANCEMENT_FOLDER_PARAMETER_KEY);

      LocalDate generationDate = new LocalDate();
      List<InputStream> documents = generateContrats(contracts, tempPath, generationDate);

      // Nom du Fichier regroupé PDF
      String month = QuittancementConstants.DATE_FORMAT_MONTH.print(generationDate);

      String mergedFileName = MessageFormat.format(QuittancementConstants.MERGED_FILENAME, month, generationDate.getYear());
      File fileToZip = new File(tempPath + mergedFileName);

      // Nom du fichier zippé
      String mergedZippedFileName = MessageFormat.format(QuittancementConstants.ZIPPED_FILENAME, month, generationDate.getYear());
      File fileZipped = new File(path + mergedZippedFileName);

      // Flux de sortie pour le fichier PDF regroupé
      OutputStream mergedFile = new FileOutputStream(new File(tempPath + mergedFileName));

      // Regroupement des documents générés
      mergeDocuments(documents, mergedFile);

      // Zip et archivage
      zipAndSend(fileToZip);

      // Suppression des documents générés
      FileUtils.cleanDirectory(new File(tempPath));

      // Upload du fichier de quittancement compressé sur le FTP
      uploadOnFTP(fileZipped);


    } catch (ParameterServiceException e) {
      LOGGER.error("Une erreur est survenue lors de l’accès au répertoire", e);
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Erreur lors de la récupération des contrats", e);
    } catch (FileNotFoundException e) {
      LOGGER.error("Erreur lors de la recherche du dossier", e);
    } catch (DocumentException e) {
      LOGGER.error("Erreur lors du traitement du document", e);
    } catch (IOException e) {
      LOGGER.error("Erreur lors du traitement du fichier", e);
    } catch (UtilException e) {
      LOGGER.error("Erreur lors de l'envoi du document", e);
    }
  }

  /**
   * Permet de traiter la liste des contrats afin de générer les quittancements de loyer
   *
   * @param contracts      liste des contrat
   * @param tempPath       chemin temporaire
   * @param generationDate date de génération
   * @return liste des documents générés
   * @throws HistoryContractServiceException une HistoryContractServiceException
   * @throws DocumentException               une DocumentException
   * @throws IOException                     une IOException
   * @throws HistoryAmountServiceException   une HistoryAmountServiceException
   */
  private List<InputStream> generateContrats(List<ContractDTO> contracts, String tempPath, LocalDate generationDate) throws DocumentException, IOException {

    // Ensemble des documents générés
    List<InputStream> documents = new ArrayList<InputStream>();

    try {
      LocalDate firstGenerationDate = generationDate;
      // Si il y'a des mois à régulariser, on récupère la liste des mois historisés
      for (ContractDTO contractDTO : contracts) {
        List<HistoryContractDTO> historyContractDTOs = new ArrayList<HistoryContractDTO>();
        Map<YearMonth, HistoryAmountDTO> historizeAmountByYearMonth = new HashMap<YearMonth, HistoryAmountDTO>();

        firstGenerationDate = processContratsWithRetroactivityMonths(generationDate, firstGenerationDate, contractDTO, historyContractDTOs, historizeAmountByYearMonth);

        // Construction d’un fichier pour chaque contrat
        String fileName = MessageFormat.format(QuittancementConstants.FILENAME, generationDate.getMonthOfYear(), contractDTO.getContractReference(), generationDate.getYear());

        LocalDate firstDayDate;
        LocalDate lastDayDate;
        // En cas d'une cloture dans le passé, la date de début est la date de début du remboursement
        if (contractDTO.getRetroactivitysMonths() < 0 && contractDTO.getEndValidityDate() != null) {
          // on récupère les dates de remboursement en cas de modification de la date de fin dans le passé
          firstDayDate = new LocalDate(contractDTO.getEndValidityDate());
        } else {
          firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, firstGenerationDate);
        }
        // En cas de cloture dans le passé et si il n'y a pas de facturation ponctuelle la date de fin de quittancement est la date de fin du
        // remboursement
        if (contractDTO.getRetroactivitysMonths() < 0 && contractDTO.getEndValidityDate() != null && !ContractUtils.hasSporadicallyInvoicing(contractDTO)) {
          lastDayDate = new LocalDate(generationDate).minusMonths(1).dayOfMonth().withMaximumValue();
        } else if (contractDTO.getRetroactivitysMonths() > 0 && contractDTO.getEndValidityDate() != null
          && contractDTO.getEndValidityDate().before(DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(generationDate).toDate()) && !ContractUtils.hasSporadicallyInvoicing(contractDTO)) {
          lastDayDate = new LocalDate(contractDTO.getEndValidityDate());
        } else {
          lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);
        }

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        Document document = new Document();
        document.setPageSize(PageSize.A4);

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        PdfWriter writer = PdfWriter.getInstance(document, byteStream);

        HeaderFooter event = new HeaderFooter(firstGenerationDate, generationDate, contractDTO, historyContractDTOs);
        writer.setPageEvent(event);

        float llxHeader = document.left();
        float llyHeader = document.top(QuittancementConstants.SPACE_HEADER);
        float urxHeader = document.right();
        float uryHeader = document.top();
        writer.setBoxSize(QuittancementConstants.HEADER_BOX_NAME, new Rectangle(llxHeader, llyHeader, urxHeader, uryHeader));

        float llxFooter = document.left();
        float llyFooter = document.bottom(QuittancementConstants.MARGIN_BOTTOM_FOOTER);
        float urxFooter = document.right();
        float uryFooter = document.bottom(QuittancementConstants.SPACE_FOOTER);
        writer.setBoxSize(QuittancementConstants.FOOTER_BOX_NAME, new Rectangle(llxFooter, llyFooter, urxFooter, uryFooter));

        float llxPagination = document.left();
        float llyPagination = document.bottom();
        float urxPagination = document.right();
        float uryPagination = document.bottom(QuittancementConstants.MARGIN_BOTTOM_FOOTER);
        writer.setBoxSize(QuittancementConstants.PAGINATION_BOX_NAME, new Rectangle(llxPagination, llyPagination, urxPagination, uryPagination));

        document.setMargins(document.leftMargin(), document.rightMargin(), QuittancementConstants.MARGIN_TOP_BODY, uryFooter);

        document.open();

        HousingDTO housingDTO = contractDTO.getHousing();
        UserDTO managerDTO = housingDTO.getThirdpartyContractManager();
        boolean anyLineInserted = false;

        insertManager(document, managerDTO);
        insertObject(document, firstDay, lastDay);
        insertAddress(document, housingDTO);
        insertReference(document, contractDTO, housingDTO);
        anyLineInserted = insertInvoiceDetail(document, contractDTO, historyContractDTOs, historizeAmountByYearMonth, generationDate);

        document.close();

        PdfReader reader = new PdfReader(byteStream.toByteArray());
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(tempPath + fileName));

        if (anyLineInserted) {
          documents.add(new FileInputStream(tempPath + fileName));
        }

        int n = reader.getNumberOfPages();

        for (int i = 1; i <= n; i++) {
          Rectangle footerPosition = writer.getBoxSize(QuittancementConstants.PAGINATION_BOX_NAME);
          ColumnText pagination = new ColumnText(stamper.getOverContent(i));
          pagination.setSimpleColumn(footerPosition);
          insertPageNumber(pagination, i, n);
          pagination.addText(new Phrase(Integer.toString(n)));

          pagination.go();
        }

        stamper.close();
        reader.close();

        for (Map.Entry<YearMonth, HistoryAmountDTO> entry : historizeAmountByYearMonth.entrySet()) {
          historyAmountService.create(entry.getValue());
        }
        firstGenerationDate = generationDate;
      }
    } catch (HistoryContractServiceException e) {
      LOGGER.error("Erreur lors de la récupération de l’historisation des contrats", e);
    } catch (HistoryAmountServiceException e) {
      LOGGER.error("Erreur lors de l'historisation des montants", e);
    }
    return documents;
  }

  /**
   * Permet de traiter les contracts avec des mois de rétroactivité
   *
   * @param generationDate             date de génération
   * @param firstGenerationDate        première date de génération
   * @param contractDTO                contrat à traiter
   * @param historyContractDTOs        historisations du contrat
   * @param historizeAmountByYearMonth montants historisés du contrat
   * @return date de première génération à prendre en compte
   * @throws HistoryContractServiceException une HistoryContractServiceException
   */
  private LocalDate processContratsWithRetroactivityMonths(LocalDate generationDate, LocalDate firstGenerationDate, ContractDTO contractDTO,
                                                           List<HistoryContractDTO> historyContractDTOs, Map<YearMonth, HistoryAmountDTO> historizeAmountByYearMonth) throws HistoryContractServiceException {
    LocalDate firstDate = firstGenerationDate;

    if (contractDTO.getRetroactivitysMonths() != 0) {
      int monthToRegularize = Math.abs(contractDTO.getRetroactivitysMonths());
      for (int i = 0; i < monthToRegularize; i++) {
        LocalDate regularizationDate = generationDate;
        regularizationDate = regularizationDate.minusMonths(i + 1);
        int month = regularizationDate.getMonthOfYear();
        int year = regularizationDate.getYear();
        HistoryContractDTO historyContractDTO = historyContractServiceFacade.get(contractDTO.getId(), month, year);
        YearMonth yearMonth = new YearMonth(year, month);
        if (historyContractDTO != null) {
          historyContractDTOs.add(historyContractDTO);
          HistoryAmountDTO historyAmountDTO = new HistoryAmountDTO();
          historyAmountDTO.setContractId(historyContractDTO.getContractId());
          historyAmountDTO.setMonthGeneration(generationDate.getMonthOfYear());
          historyAmountDTO.setYearGeneration(generationDate.getYear());
          historyAmountDTO.setMonthRetroactivity(month);
          historyAmountDTO.setYearRetroactivity(year);
          historyAmountDTO.setGenerationType(QuittancementConstants.TYPE_QUITTANCEMENT);
          historizeAmountByYearMonth.put(yearMonth, historyAmountDTO);
        }
        // On la formate en dd.MM.yyyy
        firstDate = regularizationDate;
      }
    }
    return firstDate;
  }

  /**
   * Permet de regrouper les différents fichiers issus de la génération
   *
   * @param documents    Liste des fichiers PDF à regrouper
   * @param outputStream Fichier issu du regroupement
   * @throws DocumentException
   * @throws IOException
   */
  private void mergeDocuments(List<InputStream> documents, OutputStream outputStream) throws DocumentException, IOException {
    Document document = new Document();
    document.setPageSize(PageSize.A4);
    PdfWriter writer = PdfWriter.getInstance(document, outputStream);
    document.open();
    PdfContentByte content = writer.getDirectContent();

    for (InputStream in : documents) {
      PdfReader reader = new PdfReader(in);
      for (int i = 1; i <= reader.getNumberOfPages(); i++) {
        document.newPage();
        PdfImportedPage page = writer.getImportedPage(reader, i);
        content.addTemplate(page, 0, 0);
      }
      in.close();
    }

    outputStream.flush();
    document.close();
    outputStream.close();
  }

  /**
   * @param document   document en cours de traitement
   * @param managerDTO informations du responsable du logement
   * @throws DocumentException erreur lors de l’ajout d’un élément au document
   */
  private void insertManager(Document document, UserDTO managerDTO) throws DocumentException {
    Paragraph manager = new Paragraph();
    manager.setFont(QuittancementConstants.ITEM_FONT);
    manager.add(MessageFormat.format(QuittancementConstants.MANAGER_LASTNAME_AND_FIRSTNAME, managerDTO.getFirstName(), managerDTO.getLastName()));
    if (null != managerDTO.getPhone()) {
      manager.add(Chunk.NEWLINE);
      manager.add(MessageFormat.format(QuittancementConstants.MANAGER_PHONE, managerDTO.getPhone()));
    }
    document.add(manager);
  }

  /**
   * @param document document en cours de traitement
   * @param firstDay date de début du contrat sur le mois en cours de traitement
   * @param lastDay  date de fin du contrat sur le mois en cours de traitement
   * @throws DocumentException erreur lors de l’ajout d’un élément au document
   */
  private void insertObject(Document document, String firstDay, String lastDay) throws DocumentException {
    Paragraph object = new Paragraph();
    object.setFont(QuittancementConstants.ITEM_FONT);
    object.setSpacingBefore(QuittancementConstants.SPACING_BETWEEN_ELEMENTS);
    object.add(MessageFormat.format(QuittancementConstants.OBJECT_SENTENCE, firstDay, lastDay));
    document.add(object);
  }

  /**
   * @param document   document en cours de traitement
   * @param housingDTO informations du lognement
   * @throws DocumentException erreur lors de l’ajout d’un élément au document
   */
  private void insertAddress(Document document, HousingDTO housingDTO) throws DocumentException {
    Paragraph address = new Paragraph();
    address.setFont(QuittancementConstants.ITEM_FONT);
    address.setSpacingBefore(QuittancementConstants.SPACING_BETWEEN_ELEMENTS);
    Chunk contractAddress = new Chunk();
    contractAddress.append(MessageFormat.format(QuittancementConstants.CONTRACT_ADDRESS, housingDTO.getAddress().trim(), housingDTO.getPostalCode(), housingDTO.getCity()));
    address.add(contractAddress);
    document.add(address);
  }

  /**
   * @param document    document en cours de traitement
   * @param contractDTO informations du contrat
   * @param housingDTO  informations du lognement
   * @throws DocumentException erreur lors de l’ajout d’un élément au document
   */
  private void insertReference(Document document, ContractDTO contractDTO, HousingDTO housingDTO) throws DocumentException {
    Paragraph reference = new Paragraph();
    reference.setFont(QuittancementConstants.ITEM_FONT);
    reference.add(QuittancementConstants.CONTRACT_REFERENCE_LABEL);
    Chunk contractReference = new Chunk();
    contractReference.setFont(QuittancementConstants.ITEM_FONT_STRONG);
    contractReference.append(contractDTO.getContractReference());
    reference.add(contractReference);
    reference.add(Chunk.NEWLINE);
    reference.add(QuittancementConstants.HOUSING_REFERENCE_LABEL);
    Chunk housingReference = new Chunk();
    housingReference.setFont(QuittancementConstants.ITEM_FONT_STRONG);
    housingReference.append(housingDTO.getReference());
    reference.add(housingReference);
    document.add(reference);
  }

  /**
   * @param document                   document en cours de traitement
   * @param contractDTO                information du contrat
   * @param historyContractDTOs        historisations du contrat
   * @param historizeAmountByYearMonth montants historisés
   * @param generationDate             date de génération
   * @return Si une ligne a été inséré
   * @throws DocumentException erreur lors de l’ajout d’un élément au document
   */
  private boolean insertInvoiceDetail(Document document, ContractDTO contractDTO, List<HistoryContractDTO> historyContractDTOs,
                                      Map<YearMonth, HistoryAmountDTO> historizeAmountByYearMonth, LocalDate generationDate) throws DocumentException {
    PdfPTable invoiceDetail = new PdfPTable(QuittancementConstants.TOTAL_COLUMN);
    invoiceDetail.setHeaderRows(1);
    invoiceDetail.setWidthPercentage(QuittancementConstants.TABLE_WIDTH);
    int[] tableWidths = {QuittancementConstants.TABLE_FIRST_WEIGHT, QuittancementConstants.TABLE_SECOND_WEIGHT};
    invoiceDetail.setWidths(tableWidths);
    invoiceDetail.setSpacingBefore(QuittancementConstants.SPACING_BETWEEN_ELEMENTS);
    invoiceDetail.setSpacingAfter(QuittancementConstants.SPACING_BETWEEN_ELEMENTS);
    boolean anyLineInserted = false;

    /* En-tête : début */
    PdfPCell headerLabel = new PdfPCell();
    headerLabel.setPadding(QuittancementConstants.TABLE_PADDING);
    headerLabel.setUseAscender(true);
    headerLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
    headerLabel.setBorder(Rectangle.BOTTOM);
    Phrase headerLabelContent = new Phrase();
    headerLabelContent.setFont(QuittancementConstants.ITEM_FONT_STRONG);
    headerLabelContent.add(QuittancementConstants.DETAIL_HEADER_LABEL);
    headerLabel.setPhrase(headerLabelContent);
    invoiceDetail.addCell(headerLabel);

    PdfPCell headerPrice = new PdfPCell();
    headerPrice.setPadding(QuittancementConstants.TABLE_PADDING);
    headerPrice.setUseAscender(true);
    headerPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
    headerPrice.setBorder(Rectangle.BOTTOM);
    Phrase headerPriceContent = new Phrase();
    headerPriceContent.setFont(QuittancementConstants.ITEM_FONT_STRONG);
    headerPriceContent.add(QuittancementConstants.DETAIL_HEADER_PRICE);
    headerPrice.setPhrase(headerPriceContent);
    invoiceDetail.addCell(headerPrice);
    /* En-tête : fin */

    // Loyer net agent
    anyLineInserted = insertInvoinceDetailsOfTheNetAgentRent(contractDTO, historyContractDTOs, historizeAmountByYearMonth, generationDate, invoiceDetail, anyLineInserted);

    // Loyer garage
    anyLineInserted = insertInvoinceDetailsOfTheGarageRent(contractDTO, historyContractDTOs, historizeAmountByYearMonth, generationDate, invoiceDetail, anyLineInserted);

    // Loyer jardin
    anyLineInserted = insertInvoinceDetailsOfTheGardenRent(contractDTO, historyContractDTOs, historizeAmountByYearMonth, generationDate, invoiceDetail, anyLineInserted);

    // Provision pour charges
    anyLineInserted = insertInvoinceDetailsOfTheExpectedChargeCost(contractDTO, historyContractDTOs, historizeAmountByYearMonth, generationDate, invoiceDetail, anyLineInserted);

    // Surloyer
    anyLineInserted = insertInvoinceDetailsOfTheExtraRent(contractDTO, historyContractDTOs, historizeAmountByYearMonth, generationDate, invoiceDetail, anyLineInserted);

    // Facturation consommation eau
    anyLineInserted = insertInvoinceDetailsOfTheWaterInvoicing(contractDTO, invoiceDetail, generationDate, anyLineInserted);

    // Facturation ordures ménagères
    anyLineInserted = insertInvoinceDetailsOfTheGarbageInvoicing(contractDTO, invoiceDetail, generationDate, anyLineInserted);

    // Remboursement assurance
    anyLineInserted = insertInvoinceDetailsOfTheInsuranceReimbursement(contractDTO, invoiceDetail, generationDate, anyLineInserted);

    // Remboursement taxe habitation
    anyLineInserted = insertInvoinceDetailsOfTheHousingTaxReimbursement(contractDTO, invoiceDetail, generationDate, anyLineInserted);

    // Remboursement ordures ménagères
    anyLineInserted = insertInvoinceDetailsOfTheGarbageReimbursement(contractDTO, invoiceDetail, generationDate, anyLineInserted);

    // Apurement annuel charges locatives
    anyLineInserted = insertInvoinceDetailsOfTheAnnualClearanceCharges(contractDTO, invoiceDetail, generationDate, anyLineInserted);

    // Autres charges locatives
    anyLineInserted = insertInvoinceDetailsOfTheOtherInvoicingLabel(contractDTO, invoiceDetail, generationDate, anyLineInserted);

    /* Total : début */
    PdfPCell totalLabel = new PdfPCell();
    totalLabel.setPadding(QuittancementConstants.TABLE_PADDING);
    totalLabel.setUseAscender(true);
    totalLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
    totalLabel.setBorder(Rectangle.TOP);
    Phrase totalLabelContent = new Phrase();
    totalLabelContent.setFont(QuittancementConstants.ITEM_FONT_STRONG);
    totalLabelContent.add(QuittancementConstants.DETAIL_TOTAL_FOOTER);
    totalLabel.setPhrase(totalLabelContent);
    invoiceDetail.addCell(totalLabel);

    PdfPCell totalPrice = new PdfPCell();
    totalPrice.setPadding(QuittancementConstants.TABLE_PADDING);
    totalPrice.setUseAscender(true);
    totalPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
    totalPrice.setBorder(Rectangle.TOP);
    Phrase totalPriceContent = new Phrase();
    totalPriceContent.setFont(QuittancementConstants.ITEM_FONT_STRONG);
    totalPriceContent.add(BigDecimalUtils.formatFranceNumber(ContractUtils.withdrawnRentAmountWithAllInvoicingAndRegularization(contractDTO, historyContractDTOs, generationDate)));
    totalPrice.setPhrase(totalPriceContent);
    invoiceDetail.addCell(totalPrice);
    /* Total : fin */

    document.add(invoiceDetail);

    return anyLineInserted;
  }

  /**
   * @param contractDTO                information du contrat
   * @param historyContractDTOs        historisations du contrat
   * @param historizeAmountByYearMonth montants historisés
   * @param generationDate             date de génération
   * @param invoiceDetail              tableau de détail
   * @param anyLineInserted            boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de loyer net agent a été inséré
   */
  private boolean insertInvoinceDetailsOfTheNetAgentRent(ContractDTO contractDTO, List<HistoryContractDTO> historyContractDTOs,
                                                         Map<YearMonth, HistoryAmountDTO> historizeAmountByYearMonth, LocalDate generationDate, PdfPTable invoiceDetail, boolean anyLineInserted) {
    /* Loyer net agent : début */
    BigDecimal netAgentRent = contractDTO.getNetAgentRent();
    netAgentRent = ContractUtils.getProrataOfAmount(netAgentRent, contractDTO, generationDate);
    boolean netAgentRentLineInserted = false;

    if (null != netAgentRent && netAgentRent.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() > 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_NET_AGENT_RENT_LABEL + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_NET_AGENT_RENT_LABEL);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, netAgentRent);
      netAgentRentLineInserted = true;
    }

    /* Loyer net agent : rétroactivité */
    for (HistoryContractDTO historyContractDTO : historyContractDTOs) {
      netAgentRentLineInserted = insertRegularizationLines(contractDTO, historyContractDTO, invoiceDetail, QuittancementConstants.DETAIL_NET_AGENT_RENT_LABEL,
        historyContractDTO.getNetAgentRent(), historizeAmountByYearMonth, netAgentRentLineInserted);
    }
    /* Loyer net agent : fin */

    return anyLineInserted || netAgentRentLineInserted;
  }

  /**
   * @param contractDTO                information du contrat
   * @param historyContractDTOs        historisations du contrat
   * @param historizeAmountByYearMonth montants historisés
   * @param generationDate             date de génération
   * @param invoiceDetail              tableau de détail
   * @param anyLineInserted            boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de loyer garage a été inséré
   */
  private boolean insertInvoinceDetailsOfTheGarageRent(ContractDTO contractDTO, List<HistoryContractDTO> historyContractDTOs,
                                                       Map<YearMonth, HistoryAmountDTO> historizeAmountByYearMonth, LocalDate generationDate, PdfPTable invoiceDetail, boolean anyLineInserted) {
    /* Loyer garage : début */
    BigDecimal garageRent = contractDTO.getGarageRent();
    garageRent = ContractUtils.getProrataOfAmount(garageRent, contractDTO, generationDate);
    boolean garageRentLineInserted = false;

    if (null != garageRent && garageRent.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() > 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_GARAGE_RENT_LABEL + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_GARAGE_RENT_LABEL);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, garageRent);
      garageRentLineInserted = true;
    }
    /* Loyer garage : rétroactivité */
    for (HistoryContractDTO historyContractDTO : historyContractDTOs) {
      garageRentLineInserted = insertRegularizationLines(contractDTO, historyContractDTO, invoiceDetail, QuittancementConstants.DETAIL_GARAGE_RENT_LABEL,
        historyContractDTO.getGarageRent(), historizeAmountByYearMonth, garageRentLineInserted);
    }
    /* Loyer garage : fin */

    return anyLineInserted || garageRentLineInserted;
  }

  /**
   * @param contractDTO                information du contrat
   * @param historyContractDTOs        historisations du contrat
   * @param historizeAmountByYearMonth montants historisés
   * @param generationDate             date de génération
   * @param invoiceDetail              tableau de détail
   * @param anyLineInserted            boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de loyer jardin a été inséré
   */
  private boolean insertInvoinceDetailsOfTheGardenRent(ContractDTO contractDTO, List<HistoryContractDTO> historyContractDTOs,
                                                       Map<YearMonth, HistoryAmountDTO> historizeAmountByYearMonth, LocalDate generationDate, PdfPTable invoiceDetail, boolean anyLineInserted) {
    /* Loyer jardin : début */
    BigDecimal gardenRent = ContractUtils.gardenRentAmount(contractDTO);
    gardenRent = ContractUtils.getProrataOfAmount(gardenRent, contractDTO, generationDate);
    boolean gardenRentLineInserted = false;

    if (null != gardenRent && gardenRent.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() > 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_GARDEN_RENT_LABEL + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_GARDEN_RENT_LABEL);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, gardenRent);
      gardenRentLineInserted = true;
    }
    /* Loyer jardin : rétroactivité */
    for (HistoryContractDTO historyContractDTO : historyContractDTOs) {
      gardenRent = ContractUtils.gardenRentAmount(historyContractDTO);
      gardenRentLineInserted = insertRegularizationLines(contractDTO, historyContractDTO, invoiceDetail, QuittancementConstants.DETAIL_GARDEN_RENT_LABEL, gardenRent,
        historizeAmountByYearMonth, gardenRentLineInserted);
    }
    /* Loyer jardin : fin */

    return anyLineInserted || gardenRentLineInserted;
  }

  /**
   * @param contractDTO                information du contrat
   * @param historyContractDTOs        historisations du contrat
   * @param historizeAmountByYearMonth montants historisés
   * @param generationDate             date de génération
   * @param invoiceDetail              tableau de détail
   * @param anyLineInserted            boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de charges prévisionnelles a été inséré
   */
  private boolean insertInvoinceDetailsOfTheExpectedChargeCost(ContractDTO contractDTO, List<HistoryContractDTO> historyContractDTOs,
                                                               Map<YearMonth, HistoryAmountDTO> historizeAmountByYearMonth, LocalDate generationDate, PdfPTable invoiceDetail, boolean anyLineInserted) {
    /* Provision pour charges : début */
    BigDecimal expectedChargeCost = contractDTO.getExpectedChargeCost();
    expectedChargeCost = ContractUtils.getProrataOfAmount(expectedChargeCost, contractDTO, generationDate);
    boolean expectedChargeCostLineInserted = false;

    if (null != expectedChargeCost && expectedChargeCost.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() > 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_EXPECTED_CHARGE_COST + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_EXPECTED_CHARGE_COST);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, expectedChargeCost);
      expectedChargeCostLineInserted = true;
    }
    /* Provision pour charges : rétroactivité */
    for (HistoryContractDTO historyContractDTO : historyContractDTOs) {
      expectedChargeCostLineInserted = insertRegularizationLines(contractDTO, historyContractDTO, invoiceDetail, QuittancementConstants.DETAIL_EXPECTED_CHARGE_COST,
        historyContractDTO.getExpectedChargeCost(), historizeAmountByYearMonth, expectedChargeCostLineInserted);
    }
    /* Provision pour charges : fin */

    return anyLineInserted || expectedChargeCostLineInserted;
  }

  /**
   * @param contractDTO                information du contrat
   * @param historyContractDTOs        historisations du contrat
   * @param historizeAmountByYearMonth montants historisés
   * @param generationDate             date de génération
   * @param invoiceDetail              tableau de détail
   * @param anyLineInserted            boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de surloyer a été inséré
   */
  private boolean insertInvoinceDetailsOfTheExtraRent(ContractDTO contractDTO, List<HistoryContractDTO> historyContractDTOs,
                                                      Map<YearMonth, HistoryAmountDTO> historizeAmountByYearMonth, LocalDate generationDate, PdfPTable invoiceDetail, boolean anyLineInserted) {
    /* Surloyer : début */
    BigDecimal extraRent = contractDTO.getExtraRent();
    extraRent = ContractUtils.getProrataOfAmount(extraRent, contractDTO, generationDate);
    boolean extraRentLineInserted = false;

    if (null != extraRent && extraRent.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() > 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_EXTRA_RENT + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_EXTRA_RENT);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, extraRent);
      extraRentLineInserted = true;
    }
    /* Surloyer : rétroactivité */
    for (HistoryContractDTO historyContractDTO : historyContractDTOs) {
      extraRentLineInserted = insertRegularizationLines(contractDTO, historyContractDTO, invoiceDetail, QuittancementConstants.DETAIL_EXTRA_RENT,
        historyContractDTO.getExtraRent(), historizeAmountByYearMonth, extraRentLineInserted);
    }
    /* Surloyer : fin */

    return anyLineInserted || extraRentLineInserted;
  }

  /**
   * @param contractDTO     information du contrat
   * @param invoiceDetail   tableau de détail
   * @param anyLineInserted boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de facturation consommation eau a été inséré
   */
  private boolean insertInvoinceDetailsOfTheWaterInvoicing(ContractDTO contractDTO, PdfPTable invoiceDetail, LocalDate generationDate, boolean anyLineInserted) {
    /* Facturation consommation eau : début */
    BigDecimal waterInvoicing = contractDTO.getWaterInvoicing();
    boolean waterInvoicingLineInserted = false;

    if (null != waterInvoicing && waterInvoicing.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() != 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_WATER_INVOICING + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_WATER_INVOICING);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, waterInvoicing);
      waterInvoicingLineInserted = true;
    }
    /* Facturation consommation eau : fin */

    return anyLineInserted || waterInvoicingLineInserted;
  }

  /**
   * @param contractDTO     information du contrat
   * @param invoiceDetail   tableau de détail
   * @param anyLineInserted boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de facturation ordures ménagères a été inséré
   */
  private boolean insertInvoinceDetailsOfTheGarbageInvoicing(ContractDTO contractDTO, PdfPTable invoiceDetail, LocalDate generationDate, boolean anyLineInserted) {
    /* Facturation ordures ménagères : début */
    BigDecimal garbageInvoicing = contractDTO.getGarbageInvoicing();
    boolean garbageInvoicingLineInserted = false;

    if (null != garbageInvoicing && garbageInvoicing.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() != 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_GARBAGE_INVOICING + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_GARBAGE_INVOICING);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, garbageInvoicing);
      garbageInvoicingLineInserted = true;
    }
    /* Facturation ordures ménagères : fin */

    return anyLineInserted || garbageInvoicingLineInserted;
  }

  /**
   * @param contractDTO     information du contrat
   * @param invoiceDetail   tableau de détail
   * @param anyLineInserted boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de remboursement assurance a été inséré
   */
  private boolean insertInvoinceDetailsOfTheInsuranceReimbursement(ContractDTO contractDTO, PdfPTable invoiceDetail, LocalDate generationDate, boolean anyLineInserted) {
    /* Remboursement assurance : début */
    BigDecimal insuranceReimbursement = contractDTO.getInsuranceReimbursement();
    boolean insuranceReimbursementLineInserted = false;

    if (null != insuranceReimbursement && insuranceReimbursement.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() != 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_INSURANCE_REIMBURSEMENT + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_INSURANCE_REIMBURSEMENT);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, insuranceReimbursement.negate());
      insuranceReimbursementLineInserted = true;
    }
    /* Remboursement assurance : fin */

    return anyLineInserted || insuranceReimbursementLineInserted;
  }

  /**
   * @param contractDTO     information du contrat
   * @param invoiceDetail   tableau de détail
   * @param anyLineInserted boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de remboursement taxe habitation a été inséré
   */
  private boolean insertInvoinceDetailsOfTheHousingTaxReimbursement(ContractDTO contractDTO, PdfPTable invoiceDetail, LocalDate generationDate, boolean anyLineInserted) {
    /* Remboursement taxe habitation : début */
    BigDecimal housingTaxReimbursement = contractDTO.getHousingTaxReimbursement();
    boolean housingTaxReimbursementLineInserted = false;

    if (null != housingTaxReimbursement && housingTaxReimbursement.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() != 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_HOUSING_TAX_REIMBURSEMENT + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_HOUSING_TAX_REIMBURSEMENT);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, housingTaxReimbursement.negate());
      housingTaxReimbursementLineInserted = true;
    }
    /* Remboursement taxe habitation : fin */

    return anyLineInserted || housingTaxReimbursementLineInserted;
  }

  /**
   * @param contractDTO     information du contrat
   * @param invoiceDetail   tableau de détail
   * @param anyLineInserted boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de remboursement ordures ménagères a été inséré
   */
  private boolean insertInvoinceDetailsOfTheGarbageReimbursement(ContractDTO contractDTO, PdfPTable invoiceDetail, LocalDate generationDate, boolean anyLineInserted) {
    /* Remboursement ordures ménagères : début */
    BigDecimal garbageReimbursement = contractDTO.getGarbageReimbursement();
    boolean garbageReimbursementLineInserted = false;

    if (null != garbageReimbursement && garbageReimbursement.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() != 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_GARBAGE_REIMBURSEMENT + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_GARBAGE_REIMBURSEMENT);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, garbageReimbursement.negate());
      garbageReimbursementLineInserted = true;
    }
    /* Remboursement ordures ménagères : fin */

    return anyLineInserted || garbageReimbursementLineInserted;
  }

  /**
   * @param contractDTO     information du contrat
   * @param invoiceDetail   tableau de détail
   * @param anyLineInserted boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de apurement annuel charges locatives a été inséré
   */
  private boolean insertInvoinceDetailsOfTheAnnualClearanceCharges(ContractDTO contractDTO, PdfPTable invoiceDetail, LocalDate generationDate, boolean anyLineInserted) {
    /* Apurement annuel charges locatives : début */
    BigDecimal annualClearanceCharges = contractDTO.getAnnualClearanceCharges();
    boolean annualClearanceChargesLineInserted = false;

    if (null != annualClearanceCharges && annualClearanceCharges.compareTo(BigDecimal.ZERO) != 0) {
      if (contractDTO.getRetroactivitysMonths() != 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail,
          QuittancementConstants.DETAIL_ANNUAL_CLEARANCE_CHARGES + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, QuittancementConstants.DETAIL_ANNUAL_CLEARANCE_CHARGES);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, annualClearanceCharges);
      annualClearanceChargesLineInserted = true;
    }
    /* Apurement annuel charges locatives : fin */

    return anyLineInserted || annualClearanceChargesLineInserted;
  }

  /**
   * @param contractDTO     information du contrat
   * @param invoiceDetail   tableau de détail
   * @param anyLineInserted boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de autres charges locatives a été inséré
   */
  private boolean insertInvoinceDetailsOfTheOtherInvoicingLabel(ContractDTO contractDTO, PdfPTable invoiceDetail, LocalDate generationDate, boolean anyLineInserted) {
    /* Autres charges locatives : début */
    String otherInvoicingLabel = contractDTO.getOtherInvoicingLabel();
    BigDecimal otherInvoicingAmount = contractDTO.getOtherInvoicingAmount();
    boolean otherInvoicingLineInserted = false;

    if (null != otherInvoicingAmount && otherInvoicingAmount.compareTo(BigDecimal.ZERO) != 0) {
      if (null == otherInvoicingLabel || StringUtils.isEmpty(otherInvoicingLabel)) {
        otherInvoicingLabel = QuittancementConstants.DETAIL_OTHER_INVOICING;
      }
      if (contractDTO.getRetroactivitysMonths() != 0) {
        LocalDate firstDayDate = ContractUtils.getFirstDayOfMonth(contractDTO, generationDate);
        LocalDate lastDayDate = ContractUtils.getLastDayOfMonth(contractDTO, generationDate);

        String firstDay = QuittancementConstants.DATE_FORMAT_SHORT.print(firstDayDate);
        String lastDay = QuittancementConstants.DATE_FORMAT_SHORT.print(lastDayDate);

        insertInvoiceDetailRowLabel(invoiceDetail, otherInvoicingLabel + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, firstDay, lastDay));
      } else {
        insertInvoiceDetailRowLabel(invoiceDetail, otherInvoicingLabel);
      }
      insertInvoiceDetailRowPrice(invoiceDetail, otherInvoicingAmount);
      otherInvoicingLineInserted = true;
    }
    /* Autres charges locatives : fin */

    return anyLineInserted || otherInvoicingLineInserted;
  }

  /**
   * Methode qui permet de rajouter les lignes rétroactivies
   *
   * @param contractDTO                un contrat occupant à réularisé
   * @param historyContractDTO         historisation du contrat
   * @param invoiceDetail              le detail des facturations
   * @param invoicingLabel             le label de la facturation
   * @param amount                     le montant de la facturation
   * @param historizeAmountByYearMonth montants historisés
   * @param invoiceLineInserted        boolean pour savoir si une ligne a déjà été inséré
   * @return true si une ligne de loyer net agent a été inséré
   */
  private boolean insertRegularizationLines(ContractDTO contractDTO, HistoryContractDTO historyContractDTO, PdfPTable invoiceDetail, String invoicingLabel, BigDecimal amount,
                                            Map<YearMonth, HistoryAmountDTO> historizeAmountByYearMonth, boolean invoiceLineInserted) {
    // On recupere l'historisation des montants du mois de rétroactivité
    YearMonth yearMonth = new YearMonth(historyContractDTO.getYear(), historyContractDTO.getMonth());
    HistoryAmountDTO historyAmountDTO = historizeAmountByYearMonth.get(yearMonth);
    boolean retroactivityInvoiceLineInsertedInserted = false;

    // On récupere la date au premier jour du mois rétroactif
    LocalDate startRegularizationDate = new LocalDate(historyContractDTO.getYear(), historyContractDTO.getMonth(), QuittancementConstants.FIRST_DAY_OF_MONTH);

    // On prend comme date de début de régularisation du mois le debut du mois ou la date de debut du contrat si elle est dans le mois
    String startRegularizationDateWithPattern;
    if (contractDTO.getRetroactivitysMonths() < 0 && contractDTO.getEndValidityDate() != null) {
      LocalDate endValidityDate = new LocalDate(contractDTO.getEndValidityDate());
      if (startRegularizationDate.isBefore(endValidityDate)) {
        // On la formate en dd.MM.yyyy
        startRegularizationDateWithPattern = QuittancementConstants.DATE_FORMAT_SHORT.print(endValidityDate);
      } else {
        // On la formate en dd.MM.yyyy
        startRegularizationDateWithPattern = QuittancementConstants.DATE_FORMAT_SHORT.print(startRegularizationDate);
      }
    } else {
      LocalDate startValidityDate = new LocalDate(contractDTO.getStartValidityDate());
      if (startRegularizationDate.isBefore(startValidityDate)) {
        // On la formate en dd.MM.yyyy
        startRegularizationDateWithPattern = QuittancementConstants.DATE_FORMAT_SHORT.print(startValidityDate);
      } else {
        // On la formate en dd.MM.yyyy

        startRegularizationDateWithPattern = QuittancementConstants.DATE_FORMAT_SHORT.print(startRegularizationDate);
      }
    }

    // On récupere la date au dernier jour du mois rétroactif
    LocalDate endRegularizationDate;

    if (contractDTO.getRetroactivitysMonths() > 0 && contractDTO.getEndValidityDate() != null
      && contractDTO.getEndValidityDate().before(DateTimeUtils.getMaximumTimeOfLastDayOfMonth(startRegularizationDate).toDate())) {
      endRegularizationDate = new LocalDate(contractDTO.getEndValidityDate());
    } else {
      endRegularizationDate = startRegularizationDate.dayOfMonth().withMaximumValue();
    }
    // On la formate en dd.MM.yyyy
    String endRegularizationDateWithPattern = QuittancementConstants.DATE_FORMAT_SHORT.print(endRegularizationDate);
    // On calcule le nouveau montant pour le mois de génération
    BigDecimal amountWithProrata = ContractUtils.getProrataOfAmount(amount, contractDTO, startRegularizationDate);
    // Si on est en modif, on retire l'ancien montant
    if (contractDTO.getRetroactivitysMonths() < 0) {
      amountWithProrata = amountWithProrata.subtract(ContractUtils.getProrataOfAmountForRegularization(amount, contractDTO.getStartValidityDate(),
        historyContractDTO.getEndValidityDate(), startRegularizationDate));
    }
    // On inscrit la ligne seulement si le montant est different de 0
    if (amountWithProrata.compareTo(BigDecimal.ZERO) != 0) {
      // Le label au format "raison : date de début - date de fin"
      insertInvoiceDetailRowLabel(invoiceDetail,
        invoicingLabel + MessageFormat.format(QuittancementConstants.PATTERN_REGULARIZATION, startRegularizationDateWithPattern, endRegularizationDateWithPattern));

      insertInvoiceDetailRowPrice(invoiceDetail, amountWithProrata);
      retroactivityInvoiceLineInsertedInserted = true;
    }

    // Historisation des montants
    String amountDetails = historyAmountDTO.getDetails();
    if (amountDetails == null) {
      amountDetails = MessageFormat.format(QuittancementConstants.PATTERN_HISTORY_AMOUNT_DETAIL, invoicingLabel, amountWithProrata);
    } else {
      amountDetails = amountDetails + MessageFormat.format(QuittancementConstants.PATTERN_HISTORY_AMOUNT_DETAIL, invoicingLabel, amountWithProrata);
    }
    historyAmountDTO.setDetails(amountDetails);

    return invoiceLineInserted || retroactivityInvoiceLineInsertedInserted;
  }

  /**
   * Permet d’insérer la cellule "Label" d’une ligne au tableau de détail
   *
   * @param invoiceDetail tableau de détail
   * @param label         nom du label
   */
  private void insertInvoiceDetailRowLabel(PdfPTable invoiceDetail, String label) {
    PdfPCell rowLabel = new PdfPCell();
    rowLabel.setPaddingTop(QuittancementConstants.TABLE_PADDING_BODY);
    rowLabel.setPaddingRight(QuittancementConstants.TABLE_PADDING);
    rowLabel.setPaddingBottom(QuittancementConstants.TABLE_PADDING_BODY);
    rowLabel.setPaddingLeft(QuittancementConstants.TABLE_PADDING);
    rowLabel.setUseAscender(true);
    rowLabel.setHorizontalAlignment(Element.ALIGN_LEFT);
    rowLabel.setBorder(Rectangle.NO_BORDER);
    Phrase rowLabelContent = new Phrase();
    rowLabelContent.setFont(QuittancementConstants.ITEM_FONT);
    rowLabelContent.add(label);
    rowLabel.setPhrase(rowLabelContent);
    invoiceDetail.addCell(rowLabel);
  }

  /**
   * Permet d’insérer la cellule "Prix" d’une ligne au tableau de détail
   *
   * @param invoiceDetail tableau de détail
   * @param price         valeur du prix
   */
  private void insertInvoiceDetailRowPrice(PdfPTable invoiceDetail, BigDecimal price) {
    PdfPCell rowPrice = new PdfPCell(new Paragraph());
    rowPrice.setPaddingTop(QuittancementConstants.TABLE_PADDING_BODY);
    rowPrice.setPaddingRight(QuittancementConstants.TABLE_PADDING);
    rowPrice.setPaddingBottom(QuittancementConstants.TABLE_PADDING_BODY);
    rowPrice.setPaddingLeft(QuittancementConstants.TABLE_PADDING);
    rowPrice.setUseAscender(true);
    rowPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
    rowPrice.setBorder(Rectangle.NO_BORDER);
    Phrase rowPriceContent = new Phrase();
    rowPriceContent.setFont(QuittancementConstants.ITEM_FONT);
    rowPriceContent.add(BigDecimalUtils.formatFranceNumber(price));
    rowPrice.setPhrase(rowPriceContent);
    invoiceDetail.addCell(rowPrice);
  }

  /**
   * @param content   document en cours de traitement
   * @param page      numéro de la page en cours
   * @param totalPage nombre total de page
   */
  private void insertPageNumber(ColumnText content, Integer page, Integer totalPage) {
    Paragraph pageNumber = new Paragraph();
    pageNumber.setFont(QuittancementConstants.ITEM_FONT);
    pageNumber.setAlignment(Element.ALIGN_RIGHT);
    pageNumber.add(page + "/" + totalPage);
    content.addElement(pageNumber);
  }

  /**
   * Permet de retrouver un pdf généré pour un mois donné
   *
   * @param date La date du pdf a trouver
   * @return Un pointeur vers le document
   */
  private File findQuittancementByYearMonth(YearMonth date) {
    return null;
  }

  @Override
  public void zipAndSend(YearMonth date) {
    File file = findQuittancementByYearMonth(date);
    zipAndSend(file);

  }

  @Override
  public File zipAndSend(File file) {
    Assert.notNull(file, "Aucun document à zipper");

    try {
      String tempPath = parameterService.getParameterValue(ParamServiceConstants.ACCESS_DOMAIN_KEY, QuittancementConstants.PATH_TEMP_QUITTANCEMENT_FOLDER_PARAMETER_KEY);
      File tempPathDirectory = new File(tempPath);

      String destinationPath = parameterService.getParameterValue(ParamServiceConstants.ACCESS_DOMAIN_KEY, QuittancementConstants.PATH_QUITTANCEMENT_FOLDER_PARAMETER_KEY);

      File destinationPathDirectory = new File(destinationPath);
      File zipFile = zip(file, tempPathDirectory);

      // Copie vers le dossier d'archive du zip
      copyPaste(zipFile, new File(destinationPathDirectory, FilenameUtils.removeExtension(file.getName()) + QuittancementConstants.ZIP_FILE_SUFFIX), false);

      return zipFile;

    } catch (ParameterServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la récupération des données en base de données", e);
    } catch (IOException e) {
      LOGGER.error("Une erreur est survenue lors de la préparation du document zippé", e);
    }
    return null;
  }

  /**
   * Copie ou déplace un fichier vers un nouvel emplacement
   *
   * @param fileToCopy      fichier à copier
   * @param destinationFile fichier de destination
   * @param cutInstead      oui si déplacement
   * @throws IOException une IOException
   */
  private void copyPaste(File fileToCopy, File destinationFile, boolean cutInstead) throws IOException {
    Assert.notNull(fileToCopy, "Le fichier source n'est pas défini");
    Assert.notNull(destinationFile, "Le fichier de destination n'est pas défini");

    LOGGER.info("Copie de fichier. Effectuer un déplacement à la place ? " + cutInstead);
    LOGGER.debug("Source : " + fileToCopy.getAbsolutePath());
    LOGGER.debug("Destination : " + destinationFile.getAbsolutePath());

    FileUtils.copyFile(fileToCopy, destinationFile);
    if (cutInstead) {

      boolean deleteSuccessful = FileUtils.deleteQuietly(fileToCopy);
      if (deleteSuccessful) {
        LOGGER.debug("Le fichier " + fileToCopy.getAbsolutePath() + "a bien été supprimé après copie vers l'emplacement " + destinationFile.getAbsolutePath() + ".");
      } else {
        LOGGER.error("Le fichier " + fileToCopy.getAbsolutePath() + "n'a pas pu être supprimé après copie vers l'emplacement " + destinationFile.getAbsolutePath() + ".");
      }
    }
  }

  /**
   * Permet de zipper un document
   *
   * @param fileToZip  Le fichier a zipper
   * @param tempFolder Le dossier temporaire où est construit le zip
   * @return Le fichier zip
   * @throws IOException une IOException
   */
  private File zip(File fileToZip, File tempFolder) throws IOException {
    Assert.notNull(fileToZip, "Le fichier à zipper n'est pas défini");
    Assert.notNull(tempFolder, "Le dossier temporaire de création de fichier zip n'est pas défini");

    LOGGER.info("Création du fichier zip avec le fichier " + fileToZip.getAbsolutePath());

    File tempFile;
    try {
      tempFile = File.createTempFile(QuittancementConstants.ZIP_TEMP_FILE_PREFIX, QuittancementConstants.ZIP_TEMP_FILE_SUFFIX, tempFolder);
    } catch (IOException e) {
      LOGGER.error("Impossible de créer le fichier zip temporaire", e);
      throw e;
    }

    OutputStream tempOS = null;
    ZipOutputStream zipFile = null;
    ZipEntry zipEntry = null;
    FileInputStream in = null;
    try {
      tempOS = new FileOutputStream(tempFile);
      zipFile = new ZipOutputStream(tempOS);

      zipEntry = new ZipEntry(fileToZip.getName());
      zipFile.putNextEntry(zipEntry);

      in = new FileInputStream(fileToZip);

      int len;
      byte[] buffer = new byte[QuittancementConstants.BUFFER_SIZE];
      while ((len = in.read(buffer)) > 0) {
        zipFile.write(buffer, 0, len);
      }
      return tempFile;
    } catch (FileNotFoundException e) {
      LOGGER.error("Fichier " + tempFile.getAbsolutePath() + " introuvable", e);
      throw e;
    } catch (IOException e) {
      LOGGER.error("Une erreur d'écriture est survenue au cours de la création du fichier zip", e);
      throw e;
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          LOGGER.error("Une erreur est survenue au cours de la fermeture de flux du fichier d'entrée", e);
        }
      }
      if (zipFile != null) {
        try {
          zipFile.close();
        } catch (IOException e) {
          LOGGER.error("Une erreur est survenue au cours de la fermeture de flux du fichier zip", e);
        }
      }
      if (tempOS != null) {
        try {
          tempOS.close();
        } catch (IOException e) {
          LOGGER.error("Une erreur est survenue au cours de la fermeture de flux du fichier de sortie", e);
        }
      }
    }

  }

  /**
   * Upload du fichier de quittancement compressé sur le ftp
   *
   * @param fileZipped le fichier de quittancement compressé
   * @throws UtilException
   */
  public void uploadOnFTP(File fileZipped) throws UtilException {
    LOGGER.info("DEBUT : Récupération du bundle");
    ResourceBundle ftpProperties = ResourceBundle.getBundle("ftp");
    LOGGER.info("FIN : Récupération du bundle");

    LOGGER.info("DEBUT : Connection au server SFTP");
    Channel channel = FTPUtils.connection(ftpProperties.getString("ftp.host"), ftpProperties.getString("ftp.port"), ftpProperties.getString("ftp.user"),
      ftpProperties.getString("ftp.password"), ftpProperties.getString("ftp.proxy.address"), ftpProperties.getString("ftp.proxy.port"));
    LOGGER.info("FIN : Connection au server SFTP");
    LOGGER.info("DEBUT : Upload du fichier sur le server SFTP");
    FTPUtils.uploadFile(channel, fileZipped, fileZipped.getName(), ftpProperties.getString("ftp.dir"));
    LOGGER.info("FIN : Upload du fichier sur le server SFTP");
    LOGGER.info("DEBUT : Deconnexion du server SFTP");
    FTPUtils.disconnect(channel);
    LOGGER.info("FIN : Deconnexion du server SFTP");
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * @param historyContractServiceFacade the historyContractServiceFacade to set
   */
  public void setHistoryContractServiceFacade(IHistoryContractServiceFacade historyContractServiceFacade) {
    this.historyContractServiceFacade = historyContractServiceFacade;
  }

  /**
   * @param historyAmountService the historyAmountService to set
   */
  public void setHistoryAmountService(IHistoryAmountService historyAmountService) {
    this.historyAmountService = historyAmountService;
  }

}
