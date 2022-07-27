/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.gcp.impl;

import com.abita.dao.batch.gcp.GCPEntryBlock;
import com.abita.dao.batch.gcp.GCPEntryLine;
import com.abita.dao.batch.gcp.GCPHeader;
import com.abita.services.accountingcode.IAccountingCodeService;
import com.abita.services.accountingcode.exceptions.AccountingCodeServiceException;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.historyamount.IHistoryAmountService;
import com.abita.services.historyamount.exceptions.HistoryAmountServiceException;
import com.abita.services.historycontract.IHistoryContractServiceFacade;
import com.abita.services.historycontract.exceptions.HistoryContractServiceException;
import com.abita.services.jobs.artesis.constants.ArtesisConstants;
import com.abita.services.jobs.gcp.IGcpTenantServiceFacade;
import com.abita.services.jobs.gcp.exceptions.GcpServiceException;
import com.abita.services.ncntaccountingdocnumber.INcNtAccountingDocNumberService;
import com.abita.services.ncntaccountingdocnumber.exceptions.NcNtAccountingDocNumberServiceException;
import com.abita.services.thirdpartycontract.IThirdPartyContractServiceFacade;
import com.abita.services.thirdpartycontract.exceptions.ThirdPartyContractServiceException;
import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.contract.ContractUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.util.textutil.TextUtils;
import com.abita.dto.AccountingCodeDTO;
import com.abita.dto.ContractDTO;
import com.abita.dto.HistoryAmountDTO;
import com.abita.dto.HistoryContractDTO;
import com.abita.dto.HousingDTO;
import com.abita.dto.NcNtAccountingDocNumberDTO;
import com.abita.dto.ThirdPartyContractDTO;
import com.abita.services.jobs.gcp.constants.AccountantGcpDelegate;
import com.abita.services.jobs.gcp.constants.GcpConstants;
import com.abita.services.jobs.gcp.constants.GcpConstants.AccountingCodeNC;
import com.abita.services.jobs.gcp.constants.GcpConstants.AccountingCodeNT;
import com.abita.services.jobs.gcp.constants.GcpConstants.GcpType;
import com.abita.services.jobs.gcp.constants.GcpConstants.SporadicallyInvoicingType;
import com.abita.services.jobs.gcp.constants.HeaderGcpDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe d’implémentation des services façades des traitements GCP des occupants
 */
public class GcpTenantServiceFacadeImpl implements IGcpTenantServiceFacade {

  /** Service des code comptables */
  private IAccountingCodeService accountingCodeService;

  /** Service des contrats tiers */
  private IThirdPartyContractServiceFacade thirdPartyContractServiceFacade;

  /** Service des contrats occupants */
  private IContractServiceFacade contractServiceFacade;

  /** Service d'historisation des contrats occupants */
  private IHistoryContractServiceFacade historyContractServiceFacade;

  /** Service d'historisation des montants */
  private IHistoryAmountService historyAmountService;

  /** Service des informations nécessaires à la gestion du numéro de document des pièces comptables NC-NT */
  private INcNtAccountingDocNumberService ncNtAccountingDocNumberService;

  /** Formateur de date ddMMyyyy */
  private DateTimeFormatter dateTightFormatter = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_DDMMYYYY);

  /** Liste des comptes comptables */
  private Map<String, AccountingCodeDTO> accountingCodeMap = null;

  /** Texte en cas d’erreur des codes comptables */
  private static final String ACCOUNT_CODE_ERROR = "Les codes comptables n'ont pu être récupérés. Impossible de continuer";

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(GcpTenantServiceFacadeImpl.class);

  /**
   * Initialisation des codes comptables
   */
  private void initAccountingCodeMap() {
    try {
      if (accountingCodeMap == null) {
        accountingCodeMap = accountingCodeService.buildAccountingCodeMapping();
      }
    } catch (AccountingCodeServiceException e) {
      LOGGER.error("Erreur lors de l'initialisation de la liste des code comptables", e);
    }
  }

  @Override
  public List<GCPEntryBlock> getNTEntryBlock(LocalDate generationDate) {

    List<GCPEntryBlock> gcpNTEntryBlocks = new ArrayList<GCPEntryBlock>();

    try {
      initAccountingCodeMap();
      Assert.notNull(accountingCodeMap, ACCOUNT_CODE_ERROR);

      List<ContractDTO> contractList = null;

      contractList = contractServiceFacade.findActiveContractsWithoutInternalCompensation();

      for (ContractDTO contractDTO : contractList) {
        if (BigDecimalUtils.returnZeroFromBigDecimalIfNull(contractDTO.getAnnualClearanceCharges()).compareTo(BigDecimal.ZERO) != 0) {
          SporadicallyInvoicingType type = SporadicallyInvoicingType.ANNUAL_CLEARANCE_CHARGES;
          BigDecimal amount = contractDTO.getAnnualClearanceCharges();
          gcpNTEntryBlocks.add(processNTLines(contractDTO, generationDate, type, amount));
        }
        if (BigDecimalUtils.returnZeroFromBigDecimalIfNull(contractDTO.getOtherInvoicingAmount()).compareTo(BigDecimal.ZERO) != 0) {
          SporadicallyInvoicingType type = SporadicallyInvoicingType.OTHER_INVOICING;
          BigDecimal amount = contractDTO.getOtherInvoicingAmount();
          gcpNTEntryBlocks.add(processNTLines(contractDTO, generationDate, type, amount));
        }
        if (BigDecimalUtils.returnZeroFromBigDecimalIfNull(contractDTO.getWaterInvoicing()).compareTo(BigDecimal.ZERO) != 0) {
          SporadicallyInvoicingType type = SporadicallyInvoicingType.WATER_INVOICING;
          BigDecimal amount = contractDTO.getWaterInvoicing();
          gcpNTEntryBlocks.add(processNTLines(contractDTO, generationDate, type, amount));
        }
        if (BigDecimalUtils.returnZeroFromBigDecimalIfNull(contractDTO.getGarbageInvoicing()).compareTo(BigDecimal.ZERO) != 0) {
          SporadicallyInvoicingType type = SporadicallyInvoicingType.GARBAGE_INVOICING;
          BigDecimal amount = contractDTO.getGarbageInvoicing();
          gcpNTEntryBlocks.add(processNTLines(contractDTO, generationDate, type, amount));
        }
      }
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Erreur lors de la récupération des contrats occupants actifs", e);
    }

    return gcpNTEntryBlocks;
  }

  /**
   * Permet de formater un block pour un contrat occupant d’une pièce NT
   * @param contractDTO le contrat occupant
   * @param generationDate la date de génération
   * @param type le type de facturation ponctuelle
   * @param amount montant à insérer
   * @return le block à insérer
   */
  private GCPEntryBlock processNTLines(ContractDTO contractDTO, LocalDate generationDate, SporadicallyInvoicingType type, BigDecimal amount) {
    GCPEntryBlock entryNTBlock = new GCPEntryBlock();
    entryNTBlock.setReference(contractDTO.getContractReference());

    GCPHeader gcpNTHeader = fillNTHeader(contractDTO, generationDate, type);
    gcpNTHeader.setTypePiece(GcpConstants.TYPE_PIECE_NT);
    entryNTBlock.setGcpHeader(gcpNTHeader);

    List<GCPEntryLine> gcpEntryLines = new ArrayList<GCPEntryLine>();
    gcpEntryLines.add(formatSupplierAccountNtEntryLine(contractDTO, generationDate, amount));
    gcpEntryLines.add(formatProductAccountNtEntryLine(contractDTO, generationDate, amount));
    entryNTBlock.setGcpEntryLines(gcpEntryLines);

    setNtNcAccountingPiecesNumbers(entryNTBlock, generationDate);

    return entryNTBlock;
  }

  /**
   * Permet d'itérer sur tous les blocks afin d'insérer en dernier les numéros de pièces comptables
   * @param entryNTBlock la liste des blocs de données
   * @param generationDate la date de génération
   */
  private void setNtNcAccountingPiecesNumbers(GCPEntryBlock entryNTBlock, LocalDate generationDate) {
    entryNTBlock.getGcpHeader().setNumPieceComptable(createAccountingNtNcPieceNumber(entryNTBlock, generationDate));

  }

  /**
   * Permet de créer et de renvoyer un objet référençant le numéro de la pièce comptable
   * @param gcpEntryBlock un bloc de données
   * @param generationDate la date de génération
   * @return le numéro de la pièce comptable issu de l'objet "YLZNAccountingPieceNumber"
   */
  private String createAccountingNtNcPieceNumber(GCPEntryBlock gcpEntryBlock, LocalDate generationDate) {

    String ntNcAccountingDocNumber = null;

    try {
      ContractDTO contractDTO = null;
      // On récupère le contrat tiers par sa référence

      contractDTO = contractServiceFacade.findByReference(gcpEntryBlock.getGcpHeader().getReference());

      NcNtAccountingDocNumberDTO ncNtAccountingDocNumberDTO = new NcNtAccountingDocNumberDTO();

      ncNtAccountingDocNumberDTO.setContract(contractDTO);
      ncNtAccountingDocNumberDTO.setNcNtAdnPieceDate(generationDate.toDate());
      ncNtAccountingDocNumberDTO.setNcNtAdnPieceType(gcpEntryBlock.getGcpHeader().getTypePiece());

      // On crée l'objet référençant le numéro de la pièce comptable

      ntNcAccountingDocNumber = ncNtAccountingDocNumberService.createAccountingPieceNumber(ncNtAccountingDocNumberDTO);
    } catch (NcNtAccountingDocNumberServiceException e) {
      LOGGER.error("Erreur lors de la création des informations lors de la génération d'une pièce comptable NC-NT", e);
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Erreur lors de la récupération du contrat occupant par la référence : " + gcpEntryBlock.getGcpHeader().getReference(), e);
    }

    return ntNcAccountingDocNumber;

  }

  /**
   * Permet de définir l’en-tête de la pièce NT
   * @param contractDTO un contrat occupant
   * @param generationDate la date de génération
   * @param type le type de facturation ponctuelle
   * @return l’en-tête de la pièce NT
   */
  private GCPHeader fillNTHeader(ContractDTO contractDTO, LocalDate generationDate, SporadicallyInvoicingType type) {
    GCPHeader gcpNTHeader;
    gcpNTHeader = new GCPHeader();
    gcpNTHeader.setTypeLigne(GcpConstants.TYPE_LINE_1);
    gcpNTHeader.setDatePiece(dateTightFormatter.print(generationDate));

    gcpNTHeader.setSociete(GcpConstants.COMPANY_CODE);
    gcpNTHeader.setDevisePiece(TextUtils.EURO_CURRENCY_SHORT_LABEL);
    gcpNTHeader.setDateComptable(dateTightFormatter.print(generationDate));

    if (type.equals(SporadicallyInvoicingType.OTHER_INVOICING)) {
      gcpNTHeader.setTexteEntete(contractDTO.getOtherInvoicingLabel());
    } else {
      gcpNTHeader.setTexteEntete(type.getLabel());
    }

    gcpNTHeader.setReference(contractDTO.getContractReference());
    return gcpNTHeader;
  }

  /**
   * Permet de formater une ligne de compte fourniseur pour un contrat occupant pour une pièce NT
   * @param contractDTO le contrat occupant
   * @param generationDate la date de génération
   * @param amount montant à insérer
   * @return la ligne à insérer
   */
  private GCPEntryLine formatSupplierAccountNtEntryLine(ContractDTO contractDTO, LocalDate generationDate, BigDecimal amount) {

    GCPEntryLine ntEntryLine = new GCPEntryLine();

    BigDecimal montantPiece = BigDecimalUtils.returnZeroFromBigDecimalIfNull(amount);

    ntEntryLine.setMontantDevisePieceReel(montantPiece);

    montantPiece = montantPiece.abs();

    ntEntryLine.setMontantDevisePiece(BigDecimalUtils.formatFranceNumber(montantPiece));

    commonSupplierAccountNtEntryLine(contractDTO, ntEntryLine, generationDate);

    return ntEntryLine;
  }

  /**
   * Permet d’insérer les données communes aux pièces NT et NC concernant les comptes fournisseurs
   * @param contractDTO le contrat occupant
   * @param entryLine la ligne de données
   * @param generationDate la date de génération
   */
  private void commonSupplierAccountNtEntryLine(ContractDTO contractDTO, GCPEntryLine entryLine, LocalDate generationDate) {

    // informations communes à toutes les pièces NT
    entryLine.setTypeLigne(GcpConstants.TYPE_LINE_2);

    if (GcpConstants.TYPE_TENANT_EMPLOYEE.equalsIgnoreCase(contractDTO.getTenant().getTypeTenant().getLabel())) {
      entryLine.setCompte(contractDTO.getTenant().getReference());
      entryLine.setDateBase(DateTimeUtils.formatDateTimeToStringWithPattern(generationDate.toDateTimeAtCurrentTime(), DateTimeUtils.PATTERN_DATE_DDMMYYYY));
      entryLine.setCodeCleCGS("0");
      // Cas débit ou crédit (correction pour le cas spécial d’un salarié)
      if (entryLine.getMontantDevisePieceReel().signum() < 0) {
        // Si la valeur de la facturation ponctuelle est négative, il s'agit d'un paiement en faveur de l'occupant (crédit)
        entryLine.setCleComptabilisation(GcpConstants.POSTING_KEY_39);
      } else {
        // Sinon, il s'agit d'un paiement en faveur de (débit)
        entryLine.setCleComptabilisation(GcpConstants.POSTING_KEY_29);
      }
    } else {
      AccountingCodeDTO accountingCodeDTO = accountingCodeMap.get(AccountingCodeNC.A.getNumCompte());
      entryLine.setCompte(accountingCodeDTO.getCode());
      entryLine.setDateBase("");
      entryLine.setCodeCleCGS("");
      // Cas débit ou crédit
      if (entryLine.getMontantDevisePieceReel().signum() < 0) {
        // Si la valeur de la facturation ponctuelle est négative, il s’agit d'un paiement en faveur de l’occupant (crédit)
        entryLine.setCleComptabilisation(GcpConstants.POSTING_KEY_50);
      } else {
        // Sinon, il s'agit d'un paiement en faveur de (débit)
        entryLine.setCleComptabilisation(GcpConstants.POSTING_KEY_40);
      }
    }

    commonAllSupplierAccount(contractDTO, entryLine, generationDate);
  }

  /**
   * Permet d'insérer les données communes à tous les comptes fournisseur
   * @param contractDTO le contrat occupant
   * @param ntEntryLine la ligne généré
   * @param generationDate la date de génération
   */
  private void commonAllSupplierAccount(ContractDTO contractDTO, GCPEntryLine ntEntryLine, LocalDate generationDate) {
    ntEntryLine.setCodeTVA("");
    ntEntryLine.setDomaineActivite(contractDTO.getFieldOfActivity().getLabel());
    ntEntryLine.setConditionPaiement("");
    ntEntryLine.setCentreCout("");
    ntEntryLine.setZoneAffection(contractDTO.getTenant().getReference());
    ntEntryLine.setTexte(GcpConstants.ABI_TRIGRAM
      .concat(DateTimeUtils.formatDateTimeToStringWithPattern(generationDate.toDateTimeAtCurrentTime(), DateTimeUtils.PATTERN_DATE_MMYYYY))
      .concat(DateTimeUtils.getMonthWithFourCharacters(generationDate)).concat(getThirdPartyNameByHousingId(contractDTO.getHousing())));
  }

  /**
   * Permet de formater une ligne de compte produit pour un contrat occupant pour une pièce NT
   * @param contractDTO le contrat occupant
   * @param generationDate la date de génération
   * @param amount montant à insérer
   * @return la ligne à insérer
   */
  private GCPEntryLine formatProductAccountNtEntryLine(ContractDTO contractDTO, LocalDate generationDate, BigDecimal amount) {

    GCPEntryLine ntEntryLine = new GCPEntryLine();

    AccountingCodeDTO accountingCodeDTO = accountingCodeMap.get(AccountingCodeNT.B.getNumCompte());
    ntEntryLine.setCompte(accountingCodeDTO.getCode());

    BigDecimal montantPiece = BigDecimalUtils.returnZeroFromBigDecimalIfNull(amount);

    ntEntryLine.setMontantDevisePieceReel(montantPiece);

    montantPiece = montantPiece.abs();

    ntEntryLine.setMontantDevisePiece(BigDecimalUtils.formatFranceNumber(montantPiece));

    commonAllProductAccount(contractDTO, ntEntryLine, generationDate);

    return ntEntryLine;
  }

  /**
   * Permet d’insérer les données communes aux lignes des comtes produits pour NC et NT
   * @param contractDTO le contrat occupant
   * @param entryLine la ligne à créer
   * @param generationDate la date de génération
   */
  private void commonAllProductAccount(ContractDTO contractDTO, GCPEntryLine entryLine, LocalDate generationDate) {

    entryLine.setTypeLigne(GcpConstants.TYPE_LINE_2);
    entryLine.setCodeCleCGS("");

    // Cas débit ou crédit (correction pour le cas spécial d’un salarié)
    if (entryLine.getMontantDevisePieceReel().signum() < 0) {
      // Si la valeur de la facturation ponctuelle est négative, il s'agit d'un paiement en faveur de l'occupant (crédit)
      entryLine.setCleComptabilisation(GcpConstants.POSTING_KEY_40);
    } else {
      // Sinon, il s'agit d’un paiement en faveur de (débit)
      entryLine.setCleComptabilisation(GcpConstants.POSTING_KEY_50);
    }

    entryLine.setCodeTVA(GcpConstants.TVA_CODE_CL);
    entryLine.setDomaineActivite(contractDTO.getFieldOfActivity().getLabel());
    entryLine.setDateBase("");
    entryLine.setConditionPaiement("");
    if (contractDTO.getHousing().getProperty()) {
      entryLine.setCentreCout(GcpConstants.CENTER_COST_RTE_LABEL);
    } else {
      entryLine.setCentreCout(GcpConstants.CENTER_COST_TIR_LABEL);
    }
    entryLine.setZoneAffection(contractDTO.getContractReference());

    entryLine.setTexte(GcpConstants.ABI_TRIGRAM
      .concat(DateTimeUtils.formatDateTimeToStringWithPattern(generationDate.toDateTimeAtCurrentTime(), DateTimeUtils.PATTERN_DATE_MMYYYY))
      .concat(DateTimeUtils.getMonthWithFourCharacters(generationDate)).concat(getThirdPartyNameByHousingId(contractDTO.getHousing())));

  }

  /**
   * Permet de formatter le nom du tiers par le numéro de logement
   * @param housing le logement
   * @return le nom du tiers par le numéro de logement
   */
  private String getThirdPartyNameByHousingId(HousingDTO housing) {

    ThirdPartyContractDTO thirdPartyContractDTO = null;
    try {
      // un propriétaire valide (date de la fin du contrat tiers non nulle)
      thirdPartyContractDTO = thirdPartyContractServiceFacade.findLastThirdPartyContractByHousing(housing.getId());
    } catch (ThirdPartyContractServiceException e) {
      LOGGER.error("Erreur lors de la récupération du contrat tiers dont l'identifiant est : " + housing.getId(), e);
    }

    if (null != thirdPartyContractDTO) {
      return thirdPartyContractDTO.getThirdParty().getName();
    }

    return "RTE";
  }

  @Override
  public List<GCPEntryBlock> getNCEntryBlock(LocalDate generationDate) throws GcpServiceException {

    List<GCPEntryBlock> gcpEntryBlocks = new ArrayList<GCPEntryBlock>();

    try {
      initAccountingCodeMap();
      Assert.notNull(accountingCodeMap, ACCOUNT_CODE_ERROR);

      List<ContractDTO> contractDTOs = contractServiceFacade.findContractsInProgressWithoutInternalCompensation();

      for (ContractDTO contractDTO : contractDTOs) {
        GCPEntryBlock entryBlock = new GCPEntryBlock();
        entryBlock.setReference(contractDTO.getContractReference());

        List<GCPEntryLine> gcpEntryLines = new ArrayList<GCPEntryLine>();
        entryBlock.setGcpEntryLines(gcpEntryLines);

        // insertion des données de la ligne d’en-tête
        GCPHeader gcpHeader = new GCPHeader();
        gcpHeader.setTypeLigne(HeaderGcpDelegate.getLineType());
        gcpHeader.setDatePiece(HeaderGcpDelegate.getDateOfDocument(generationDate));
        gcpHeader.setTypePiece(HeaderGcpDelegate.getTypeOfDocument(GcpType.NC));
        gcpHeader.setSociete(HeaderGcpDelegate.getSociety());
        gcpHeader.setDevisePiece(HeaderGcpDelegate.getCurrency());
        gcpHeader.setDateComptable(HeaderGcpDelegate.getAccountingDate(generationDate));
        gcpHeader.setTexteEntete(HeaderGcpDelegate.getHeaderText(GcpType.NC, generationDate, contractDTO.getTenant().getTypeTenant().getNtHeaderLabel()));
        gcpHeader.setReference(HeaderGcpDelegate.getReference(GcpType.NC, null, contractDTO));
        entryBlock.setGcpHeader(gcpHeader);

        for (AccountingCodeNC line : AccountingCodeNC.values()) {
          processNcForOneLine(generationDate, contractDTO, gcpEntryLines, line);
        }

        if (!gcpEntryLines.isEmpty()) {
          setNtNcAccountingPiecesNumbers(entryBlock, generationDate);
          gcpEntryBlocks.add(entryBlock);
        }
      }
    } catch (ContractServiceFacadeException e) {
      throw new GcpServiceException(e.getMessage(), e);
    }

    return gcpEntryBlocks;
  }

  /**
   * Permet de générer un bloc de ligne à insérer dans un fichier NC pour une ligne d’un contrat
   * @param generationDate date de génération
   * @param contractDTO contrat occupant
   * @param gcpEntryLines lignes de la génération
   * @param line ligne en cours de traitement
   */
  private void processNcForOneLine(LocalDate generationDate, ContractDTO contractDTO, List<GCPEntryLine> gcpEntryLines, AccountingCodeNC line) {
    AccountingCodeDTO accountingCodeDTO = accountingCodeMap.get(line.getNumCompte());
    Integer index = line.getIndex();
    BigDecimal price = calculatePriceWhenNoHistory(contractDTO, line, generationDate);

    // insertion des données dans les lignes
    GCPEntryLine entryLine = new GCPEntryLine();
    entryLine.setTypeLigne(AccountantGcpDelegate.getLineType());
    entryLine.setCompte(AccountantGcpDelegate.getAccount(GcpType.NC, index, null, contractDTO, accountingCodeDTO));
    entryLine.setCodeCleCGS(AccountantGcpDelegate.getKeyCodeGCS(GcpType.NC, index, contractDTO));
    entryLine.setCleComptabilisation(AccountantGcpDelegate.getKeyAccounting(GcpType.NC, null, index, null, contractDTO, accountingCodeDTO, price));
    entryLine.setMontantDevisePiece(AccountantGcpDelegate.getAmount(price));
    entryLine.setCodeTVA(AccountantGcpDelegate.getValueAddedTaxCode(GcpType.NC, index));
    entryLine.setDomaineActivite(AccountantGcpDelegate.getActivityDomain(GcpType.NC, null, contractDTO));
    entryLine.setDateBase(AccountantGcpDelegate.getBaseDate(GcpType.NC, index, null, contractDTO, null, generationDate));
    entryLine.setConditionPaiement(AccountantGcpDelegate.getConditionForPayment(GcpType.NC, index));
    entryLine.setCentreCout(AccountantGcpDelegate.getCostCenter(GcpType.NC, index, null, contractDTO));
    entryLine.setZoneAffection(AccountantGcpDelegate.getHeadquartersArea(GcpType.NC, index, null, contractDTO));
    entryLine.setTexte(AccountantGcpDelegate.getText(GcpType.NC, generationDate, null, getThirdPartyNameByHousingId(contractDTO.getHousing())));

    if (price.compareTo(BigDecimal.ZERO) != 0) {
      gcpEntryLines.add(entryLine);
    }
  }

  @Override
  public List<GCPEntryBlock> getNCRegularizationEntryBlock(LocalDate generationDate) throws GcpServiceException {

    List<GCPEntryBlock> gcpEntryBlocks = new ArrayList<GCPEntryBlock>();

    try {
      initAccountingCodeMap();
      Assert.notNull(accountingCodeMap, ACCOUNT_CODE_ERROR);

      List<ContractDTO> contractList = contractServiceFacade.findContractsInProgressToRegularizeWithoutInternalCompensation();

      for (ContractDTO contractDTO : contractList) {
        processNCRegularizationEntryBlock(generationDate, gcpEntryBlocks, contractDTO);
      }

    } catch (ContractServiceFacadeException e) {
      throw new GcpServiceException(e.getMessage(), e);
    }

    return gcpEntryBlocks;
  }

  /**
   * Permet de générer un bloc de ligne à insérer dans un fichier NC de régularisation pour la rétroactivité pour un conrat
   * @param generationDate la date de génération
   * @param gcpEntryBlocks les lignes de régularisation
   * @param contractDTO le contrat à traiter
   */
  private void processNCRegularizationEntryBlock(LocalDate generationDate, List<GCPEntryBlock> gcpEntryBlocks, ContractDTO contractDTO) {
    try {
      int numberOfRetroactivityMonths = contractDTO.getRetroactivitysMonths();
      int absoluteValueOfNumberOfRetroactivityMonths = Math.abs(numberOfRetroactivityMonths);
      for (int i = 0; i < absoluteValueOfNumberOfRetroactivityMonths; i++) {
        LocalDate regularizationDate = generationDate;
        regularizationDate = regularizationDate.minusMonths(i + 1);

        HistoryContractDTO historyContractDTO = historyContractServiceFacade.get(contractDTO.getId(), regularizationDate.getMonthOfYear(), regularizationDate.getYear());

        // Map qui va récupérer les montants en fonction de leurs labels
        Map<String, BigDecimal> amountsByLabel = new HashMap<String, BigDecimal>();

        if (historyContractDTO == null) {
          continue;
        }

        GCPEntryBlock entryBlock = new GCPEntryBlock();
        entryBlock.setReference(contractDTO.getContractReference());

        List<GCPEntryLine> gcpEntryLines = new ArrayList<GCPEntryLine>();
        entryBlock.setGcpEntryLines(gcpEntryLines);

        // insertion des données de la ligne d’en-tête
        GCPHeader gcpHeader = new GCPHeader();
        gcpHeader.setTypeLigne(HeaderGcpDelegate.getLineType());
        gcpHeader.setDatePiece(HeaderGcpDelegate.getDateOfDocument(generationDate));
        gcpHeader.setTypePiece(HeaderGcpDelegate.getTypeOfDocument(GcpType.NC));
        gcpHeader.setSociete(HeaderGcpDelegate.getSociety());
        gcpHeader.setDevisePiece(HeaderGcpDelegate.getCurrency());
        gcpHeader.setDateComptable(HeaderGcpDelegate.getAccountingDate(generationDate));
        gcpHeader.setTexteEntete(HeaderGcpDelegate.getHeaderText(GcpType.NC, generationDate, historyContractDTO.getTenant().getTypeTenantHeaderLabel()));
        gcpHeader.setReference(HeaderGcpDelegate.getReference(GcpType.NC, null, contractDTO));
        entryBlock.setGcpHeader(gcpHeader);

        for (AccountingCodeNC line : AccountingCodeNC.values()) {
          processNcRegularizationForOneLine(contractDTO, numberOfRetroactivityMonths, regularizationDate, historyContractDTO, gcpEntryLines, line, amountsByLabel);
        }

        if (!gcpEntryLines.isEmpty()) {
          setNtNcAccountingPiecesNumbers(entryBlock, generationDate);
          gcpEntryBlocks.add(entryBlock);

          HistoryAmountDTO historyAmountDTO = historiseAmount(historyContractDTO, amountsByLabel, GcpConstants.TYPE_PIECE_NC, generationDate, regularizationDate);
          historyAmountService.create(historyAmountDTO);
        }
      }
    } catch (HistoryContractServiceException e) {
      LOGGER.error("Erreur lors de la récupération des données historisées.", e);
    } catch (HistoryAmountServiceException e) {
      LOGGER.error("Erreur lors de l'historisation des montants.", e);
    }
  }

  /**
   * Permet de générer un bloc de ligne à insérer dans un fichier NC de régularisation pour la rétroactivité pour une ligne d’un contrat
   * @param contractDTO contrat occupant
   * @param numberOfRetroactivityMonths nombre de mois de rétroactivité
   * @param regularizationDate date de régularisation
   * @param historyContractDTO contrat historisé du contrat occupant
   * @param gcpEntryLines lignes de la génération
   * @param line ligne en cours de traitement
   * @param amountsByLabel montants et libellés générés
   */
  private void processNcRegularizationForOneLine(ContractDTO contractDTO, int numberOfRetroactivityMonths, LocalDate regularizationDate, HistoryContractDTO historyContractDTO,
    List<GCPEntryLine> gcpEntryLines, AccountingCodeNC line, Map<String, BigDecimal> amountsByLabel) {
    AccountingCodeDTO accountingCodeDTO = accountingCodeMap.get(line.getNumCompte());
    Integer index = line.getIndex();

    // calcul du prix
    BigDecimal price = calculatePriceNcRegularization(contractDTO, numberOfRetroactivityMonths, regularizationDate, historyContractDTO, line);

    // insertion des données dans les lignes
    GCPEntryLine entryLine = new GCPEntryLine();
    entryLine.setTypeLigne(AccountantGcpDelegate.getLineType());
    entryLine.setCompte(AccountantGcpDelegate.getAccount(GcpType.NC_REGUL, index, null, contractDTO, accountingCodeDTO));
    entryLine.setCodeCleCGS(AccountantGcpDelegate.getKeyCodeGCS(GcpType.NC_REGUL, index, contractDTO));
    entryLine.setCleComptabilisation(AccountantGcpDelegate.getKeyAccounting(GcpType.NC_REGUL, null, index, null, contractDTO, accountingCodeDTO, price));
    entryLine.setMontantDevisePiece(AccountantGcpDelegate.getAmount(price));
    entryLine.setCodeTVA(AccountantGcpDelegate.getValueAddedTaxCode(GcpType.NC_REGUL, index));
    entryLine.setDomaineActivite(AccountantGcpDelegate.getActivityDomain(GcpType.NC_REGUL, null, contractDTO));
    entryLine.setDateBase(AccountantGcpDelegate.getBaseDate(GcpType.NC_REGUL, index, null, contractDTO, null, regularizationDate));
    entryLine.setConditionPaiement(AccountantGcpDelegate.getConditionForPayment(GcpType.NC_REGUL, index));
    entryLine.setCentreCout(AccountantGcpDelegate.getCostCenter(GcpType.NC_REGUL, index, null, contractDTO));
    entryLine.setZoneAffection(AccountantGcpDelegate.getHeadquartersArea(GcpType.NC_REGUL, index, null, contractDTO));
    entryLine.setTexte(AccountantGcpDelegate.getText(GcpType.NC_REGUL, regularizationDate, null, getThirdPartyNameByHousingId(contractDTO.getHousing())));

    if (price.compareTo(BigDecimal.ZERO) != 0) {
      gcpEntryLines.add(entryLine);

      String label = "";
      if (line.equals(AccountingCodeNC.A)) {
        label = GcpConstants.LABEL_RENT_SUPPLIER_ACCOUNT;
      } else if (line.equals(AccountingCodeNC.B)) {
        label = GcpConstants.LABEL_RENT_PRODUCT_ACCOUNT;
      } else if (line.equals(AccountingCodeNC.C)) {
        label = GcpConstants.LABEL_RENTAL_EXPENSE_SUPPLIER_ACCOUNT;
      } else if (line.equals(AccountingCodeNC.D)) {
        label = GcpConstants.LABEL_RENTAL_EXPENSE_PRODUCT_ACCOUNT;
      } else if (line.equals(AccountingCodeNC.E)) {
        label = GcpConstants.LABEL_GARAGE_RENT_SUPPLIER_ACCOUNT;
      } else if (line.equals(AccountingCodeNC.F)) {
        label = GcpConstants.LABEL_GARAGE_RENT_PRODUCT_ACCOUNT;
      }
      amountsByLabel.put(label, price);
    }
  }

  /**
   * Permet de calculer le prix de la pièce NC de rétroactivité
   * @param contractDTO contrat occupant
   * @param numberOfRetroactivityMonths nombre de mois de rétroactivité
   * @param regularizationDate date du mois de rétroactivité
   * @param historyContractDTO historisation du contrat occupant
   * @param line ligne en cours
   * @return prix de la pièce
   */
  private BigDecimal calculatePriceNcRegularization(ContractDTO contractDTO, int numberOfRetroactivityMonths, LocalDate regularizationDate, HistoryContractDTO historyContractDTO,
    AccountingCodeNC line) {
    BigDecimal price;

    if (historyContractDTO == null) {
      price = calculatePriceWhenNoHistory(contractDTO, line, null);
    } else {
      price = calculatePriceWithHistorization(contractDTO, numberOfRetroactivityMonths, regularizationDate, historyContractDTO, line);
    }
    return price;
  }

  /**
   * Permet de calculer le prix de la pièce NC de rétroactivité sans historisation
   * @param contractDTO contrat occupant
   * @param line ligne en cours
   * @param generationDate date de génération
   * @return prix de la pièce
   */
  private BigDecimal calculatePriceWhenNoHistory(ContractDTO contractDTO, AccountingCodeNC line, LocalDate generationDate) {
    BigDecimal price = new BigDecimal("0");

    if (line.equals(AccountingCodeNC.A) || line.equals(AccountingCodeNC.B)) {
      price = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contractDTO.getNetAgentRent());
      price = price.add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(contractDTO.getExtraRent()));
      if (generationDate != null) {
        price = ContractUtils.getProrataOfAmount(price, contractDTO, generationDate);
      }
    } else if (line.equals(AccountingCodeNC.C) || line.equals(AccountingCodeNC.D)) {
      price = BigDecimalUtils.returnZeroFromBigDecimalIfNull(contractDTO.getExpectedChargeCost());
      if (generationDate != null) {
        price = ContractUtils.getProrataOfAmount(price, contractDTO, generationDate);
      }
    } else if (line.equals(AccountingCodeNC.E) || line.equals(AccountingCodeNC.F)) {
      BigDecimal garageRent = contractDTO.getGarageRent();
      if (generationDate != null) {
        garageRent = ContractUtils.getProrataOfAmount(garageRent, contractDTO, generationDate);
      }

      BigDecimal gardenRent = ContractUtils.gardenRentAmount(contractDTO);
      if (generationDate != null) {
        gardenRent = ContractUtils.getProrataOfAmount(gardenRent, contractDTO, generationDate);
      }

      price = price.add(garageRent).add(gardenRent);
    }

    return price;
  }

  /**
   * Permet de calculer le prix de la pièce NC de rétroactivité par rapport à une historisation
   * @param contractDTO contrat occupant
   * @param numberOfRetroactivityMonths nombre de mois de rétroactivité
   * @param regularizationDate date de rétroactivité
   * @param historyContractDTO historisation du contrat tiers
   * @param line ligne en cours
   * @return prix de la pièce
   */
  private BigDecimal calculatePriceWithHistorization(ContractDTO contractDTO, int numberOfRetroactivityMonths, LocalDate regularizationDate, HistoryContractDTO historyContractDTO,
    AccountingCodeNC line) {
    BigDecimal price;
    BigDecimal newPrice = new BigDecimal("0");

    if (line.equals(AccountingCodeNC.A) || line.equals(AccountingCodeNC.B)) {
      newPrice = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getNetAgentRent());
      newPrice = newPrice.add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getExtraRent()));
      newPrice = ContractUtils.getProrataOfAmount(newPrice, contractDTO, regularizationDate);
    } else if (line.equals(AccountingCodeNC.C) || line.equals(AccountingCodeNC.D)) {
      newPrice = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getExpectedChargeCost());
      newPrice = ContractUtils.getProrataOfAmount(newPrice, contractDTO, regularizationDate);
    } else if (line.equals(AccountingCodeNC.E) || line.equals(AccountingCodeNC.F)) {
      BigDecimal newGardenRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getGardenRent());
      newGardenRent = newGardenRent.subtract(BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getGardenRent())
        .multiply(BigDecimal.valueOf(ContractUtils.GARDEN_RENT_DISCOUNT)).movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION));
      newGardenRent = newGardenRent.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
      newGardenRent = ContractUtils.getProrataOfAmount(newGardenRent, contractDTO, regularizationDate);

      BigDecimal newGarageRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getGarageRent());
      newGarageRent = ContractUtils.getProrataOfAmount(newGarageRent, contractDTO, regularizationDate);

      newPrice = newPrice.add(newGardenRent).add(newGarageRent);
    }

    price = newPrice;

    // en cas de création, seul le prix sur les montants historisés sont calculés
    if (numberOfRetroactivityMonths < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
      BigDecimal oldPrice = new BigDecimal("0");

      if (line.equals(AccountingCodeNC.A) || line.equals(AccountingCodeNC.B)) {
        oldPrice = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getNetAgentRent());
        oldPrice = oldPrice.add(BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getExtraRent()));
        oldPrice = ContractUtils.getProrataOfAmount(oldPrice, contractDTO, historyContractDTO, regularizationDate);
      } else if (line.equals(AccountingCodeNC.C) || line.equals(AccountingCodeNC.D)) {
        oldPrice = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getExpectedChargeCost());
        oldPrice = ContractUtils.getProrataOfAmount(oldPrice, contractDTO, historyContractDTO, regularizationDate);
      } else if (line.equals(AccountingCodeNC.E) || line.equals(AccountingCodeNC.F)) {
        BigDecimal oldGardenRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getGardenRent());
        oldGardenRent = oldGardenRent.subtract(BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getGardenRent())
          .multiply(BigDecimal.valueOf(ContractUtils.GARDEN_RENT_DISCOUNT)).movePointLeft(BigDecimalUtils.PERCENTAGE_DIVISION));
        oldGardenRent = oldGardenRent.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
        oldGardenRent = ContractUtils.getProrataOfAmount(oldGardenRent, contractDTO, historyContractDTO, regularizationDate);

        BigDecimal oldGarageRent = BigDecimalUtils.returnZeroFromBigDecimalIfNull(historyContractDTO.getGarageRent());
        oldGarageRent = ContractUtils.getProrataOfAmount(oldGarageRent, contractDTO, historyContractDTO, regularizationDate);

        oldPrice = oldPrice.add(oldGardenRent).add(oldGarageRent);
      }

      price = newPrice.subtract(oldPrice);
    }
    return price;
  }

  /**
   * Permet de setter les valeurs du DTO d'historisation des montants
   * @param historyContractDTO un DTO d'historisation des contrats
   * @param amountsByLabel une map des montants par leurs libellés
   * @param generationType le type de génération
   * @param generationDate la date de génération
   * @param retroactivityDate la date régularisée
   * @return un DTO d'historisation des montants
   */
  private HistoryAmountDTO historiseAmount(HistoryContractDTO historyContractDTO, Map<String, BigDecimal> amountsByLabel, String generationType, LocalDate generationDate,
    LocalDate retroactivityDate) {
    HistoryAmountDTO historyAmountDTO = new HistoryAmountDTO();
    historyAmountDTO.setContractId(historyContractDTO.getContractId());
    historyAmountDTO.setGenerationType(generationType);
    historyAmountDTO.setMonthGeneration(generationDate.getMonthOfYear());
    historyAmountDTO.setYearGeneration(generationDate.getYear());
    historyAmountDTO.setMonthRetroactivity(retroactivityDate.getMonthOfYear());
    historyAmountDTO.setYearRetroactivity(retroactivityDate.getYear());
    StringBuilder detailsBuilder = new StringBuilder();
    for (Map.Entry<String, BigDecimal> entry : amountsByLabel.entrySet()) {
      detailsBuilder.append(MessageFormat.format(ArtesisConstants.PATTERN_HISTORY_AMOUNT_DETAIL, entry.getKey(), entry.getValue()));
    }
    historyAmountDTO.setDetails(detailsBuilder.toString());
    return historyAmountDTO;
  }

  /**
   * @param accountingCodeService the accountingCodeService to set
   */
  public void setAccountingCodeService(IAccountingCodeService accountingCodeService) {
    this.accountingCodeService = accountingCodeService;
  }

  /**
   * @param thirdPartyContractServiceFacade the thirdPartyContractServiceFacade to set
   */
  public void setThirdPartyContractServiceFacade(IThirdPartyContractServiceFacade thirdPartyContractServiceFacade) {
    this.thirdPartyContractServiceFacade = thirdPartyContractServiceFacade;
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

  /**
   * @param historyContractServiceFacade the historyContractServiceFacade to set
   */
  public void setHistoryContractServiceFacade(IHistoryContractServiceFacade historyContractServiceFacade) {
    this.historyContractServiceFacade = historyContractServiceFacade;
  }

  /**
   * @param ncNtAccountingDocNumberService the ncNtAccountingDocNumberService to set
   */
  public void setNcNtAccountingDocNumberService(INcNtAccountingDocNumberService ncNtAccountingDocNumberService) {
    this.ncNtAccountingDocNumberService = ncNtAccountingDocNumberService;
  }

  /**
   * @param historyAmountService the historyAmountService to set
   */
  public void setHistoryAmountService(IHistoryAmountService historyAmountService) {
    this.historyAmountService = historyAmountService;
  }

}
