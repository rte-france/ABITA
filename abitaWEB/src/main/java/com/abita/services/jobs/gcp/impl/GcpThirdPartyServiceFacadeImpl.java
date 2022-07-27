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
import com.abita.services.jobs.gcp.IGcpThirdPartyServiceFacade;
import com.abita.services.jobs.gcp.exceptions.GcpServiceException;
import com.abita.services.revisionthirdpartycontract.IRevisionThirdPartyContractService;
import com.abita.services.revisionthirdpartycontract.constants.RevisionThirdPartyContractConstants;
import com.abita.services.revisionthirdpartycontract.exceptions.RevisionThirdPartyContractServiceException;
import com.abita.services.thirdpartycontract.IThirdPartyContractServiceFacade;
import com.abita.services.thirdpartycontract.exceptions.ThirdPartyContractServiceException;
import com.abita.services.ylznaccountingdocnumber.IYlZnAccountingDocNumberService;
import com.abita.services.ylznaccountingdocnumber.exceptions.YlZnAccountingDocNumberServiceException;
import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.util.thirdpartycontract.ThirdPartyContractUtils;
import com.abita.util.ylznaccountingdocnumber.YlZnAccountingDocNumberUtils;
import com.abita.dto.AccountingCodeDTO;
import com.abita.dto.RevisionThirdPartyContractDTO;
import com.abita.dto.ThirdPartyContractDTO;
import com.abita.dto.YlZnAccountingDocNumberDTO;
import com.abita.services.jobs.gcp.constants.AccountantGcpDelegate;
import com.abita.services.jobs.gcp.constants.GcpConstants;
import com.abita.services.jobs.gcp.constants.GcpConstants.AccountingCodeYL;
import com.abita.services.jobs.gcp.constants.GcpConstants.AccountingCodeYLSporadicallyInvoicing;
import com.abita.services.jobs.gcp.constants.GcpConstants.AccountingCodeZN;
import com.abita.services.jobs.gcp.constants.GcpConstants.GcpType;
import com.abita.services.jobs.gcp.constants.HeaderGcpDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.YearMonth;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe d’implémentation des services façades des traitements GCP des tiers
 */
public class GcpThirdPartyServiceFacadeImpl implements IGcpThirdPartyServiceFacade {

  /** Service des code comptables */
  private IAccountingCodeService accountingCodeService;

  /** Service des contrats tiers */
  private IThirdPartyContractServiceFacade thirdPartyContractServiceFacade;

  /** Service des informations nécessaires à la gestion du numéro de document des pièces comptables YL-ZN */
  private IYlZnAccountingDocNumberService ylZnAccountingDocNumberService;

  /** Service des révisions des contrats tiers */
  private transient IRevisionThirdPartyContractService revisionThirdPartyContractService;

  /** Liste des comptes comptables */
  private Map<String, AccountingCodeDTO> accountingCodeMap = null;

  /** Texte en cas d’erreur des codes comptables */
  private static final String ACCOUNT_CODE_ERROR = "Les codes comptables n'ont pu être récupérés. Impossible de continuer";

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(GcpThirdPartyServiceFacadeImpl.class);

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
  public List<GCPEntryBlock> getYLEntryBlock(LocalDate generationDate, String paymentCycle) throws GcpServiceException {

    List<GCPEntryBlock> gcpEntryBlocks = new ArrayList<GCPEntryBlock>();

    try {
      initAccountingCodeMap();
      Assert.notNull(accountingCodeMap, ACCOUNT_CODE_ERROR);

      List<ThirdPartyContractDTO> thirdPartyContractDTOs = new ArrayList<ThirdPartyContractDTO>();
      // le cycle à traiter concerne le mois en cours pour les contrats échus
      LocalDate treatmentDateAtTheEnd = generationDate;
      LocalDate startExpiryDateCycle = DateTimeUtils.getFirstDayOfCycle(treatmentDateAtTheEnd, paymentCycle);
      LocalDate endExpiryDateCycle = DateTimeUtils.getLastDayOfCycle(treatmentDateAtTheEnd, paymentCycle);

      // récupération des contrats échus seulement lorsque le cycle est mensuel
      if (DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle)) {
        thirdPartyContractDTOs.addAll(thirdPartyContractServiceFacade.findThirdPartyContractPerCycle(startExpiryDateCycle, endExpiryDateCycle, paymentCycle, 1));
      }

      // le cycle à traiter concerne le mois suivant pour les contrats à échoir
      LocalDate treatmentDateInAdvance = generationDate.plusMonths(1);
      LocalDate startFallDueCycle = DateTimeUtils.getFirstDayOfCycle(treatmentDateInAdvance, paymentCycle);
      LocalDate endFallDueCycle = DateTimeUtils.getLastDayOfCycle(treatmentDateInAdvance, paymentCycle);

      // récupération des contrats à échoir
      thirdPartyContractDTOs.addAll(thirdPartyContractServiceFacade.findThirdPartyContractPerCycle(startFallDueCycle, endFallDueCycle, paymentCycle, 0));

      for (ThirdPartyContractDTO thirdPartyContractDTO : thirdPartyContractDTOs) {
        // si l’on traite les contrats mensuels et qu’il est échu, on récupère les dates du cycle échu
        // sinon l’on prend les dates à échoir
        LocalDate startCycle;
        LocalDate endCycle;
        if (DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle) && thirdPartyContractDTO.getExpiryDate()) {
          startCycle = startExpiryDateCycle;
          endCycle = endExpiryDateCycle;
        } else {
          startCycle = startFallDueCycle;
          endCycle = endFallDueCycle;
        }

        processYlEntryBlockForThirdPartyContract(generationDate, paymentCycle, gcpEntryBlocks, startCycle, endCycle, thirdPartyContractDTO);
      }

      setYLZNAccountingPiecesNumbers(gcpEntryBlocks, generationDate);

    } catch (ThirdPartyContractServiceException e) {
      throw new GcpServiceException(e.getMessage(), e);
    }

    return gcpEntryBlocks;
  }

  /**
   * Permet de générer un bloc de ligne à insérer dans un fichier YL pour un contrat tiers
   * @param generationDate date de génération
   * @param paymentCycle cycle de paiement
   * @param gcpEntryBlocks ensemble des blocs de ligne
   * @param startCycle date de début du cycle
   * @param endCycle date de fin du cycle
   * @param thirdPartyContractDTO contrat tiers à traiter
   */
  private void processYlEntryBlockForThirdPartyContract(LocalDate generationDate, String paymentCycle, List<GCPEntryBlock> gcpEntryBlocks, LocalDate startCycle,
    LocalDate endCycle, ThirdPartyContractDTO thirdPartyContractDTO) {
    GCPEntryBlock entryBlock = new GCPEntryBlock();
    YlZnAccountingDocNumberDTO ylZnAccountingDocNumberDTO = new YlZnAccountingDocNumberDTO();
    entryBlock.setYlZnAccountingDocNumberDTO(ylZnAccountingDocNumberDTO);
    entryBlock.setDateSent(generationDate);
    entryBlock.setPaymentCycle(paymentCycle);
    entryBlock.setGcpType(GcpType.YL);
    entryBlock.setReference(thirdPartyContractDTO.getReference());

    // insertion des données de la ligne d’en-tête
    GCPHeader gcpHeader = new GCPHeader();
    gcpHeader.setTypeLigne(HeaderGcpDelegate.getLineType());
    gcpHeader.setDatePiece(HeaderGcpDelegate.getDateOfDocument(generationDate));
    gcpHeader.setTypePiece(HeaderGcpDelegate.getTypeOfDocument(GcpType.YL));
    gcpHeader.setSociete(HeaderGcpDelegate.getSociety());
    gcpHeader.setDevisePiece(HeaderGcpDelegate.getCurrency());
    gcpHeader.setDateComptable(HeaderGcpDelegate.getAccountingDate(generationDate));
    gcpHeader.setTexteEntete(HeaderGcpDelegate.getHeaderText(GcpType.YL, generationDate, null));
    gcpHeader.setReference(HeaderGcpDelegate.getReference(GcpType.YL, thirdPartyContractDTO, null));
    entryBlock.setGcpHeader(gcpHeader);

    List<GCPEntryLine> gcpEntryLines = new ArrayList<GCPEntryLine>();
    entryBlock.setGcpEntryLines(gcpEntryLines);

    for (AccountingCodeYL typePiece : AccountingCodeYL.values()) {
      AccountingCodeDTO accountingCodeDTO = accountingCodeMap.get(typePiece.getNumCompte());
      Integer index = typePiece.getIndex();

      // date de début du contrat
      LocalDate startValidity = new LocalDate(thirdPartyContractDTO.getStartValidity());
      // date de fin du contrat
      LocalDate endValidity = null;
      if (thirdPartyContractDTO.getCancellationDate() != null) {
        endValidity = new LocalDate(thirdPartyContractDTO.getCancellationDate());
      }

      Boolean revisionOfRentAmount = ThirdPartyContractUtils.hasRevisionOfRentAmount(thirdPartyContractDTO);
      Boolean revisionOfExpectedChargeCost = ThirdPartyContractUtils.hasRevisionOfExpectedChargeCost(thirdPartyContractDTO);

      BigDecimal priceWithoutRevision;
      if (typePiece.getIndex() == GcpConstants.LOYER_COMPTE_GENERAL || typePiece.getIndex() == GcpConstants.LOYER_COMPTE_CHARGE) {
        priceWithoutRevision = thirdPartyContractDTO.getRentAmount();

        if (revisionOfRentAmount) {
          priceWithoutRevision = thirdPartyContractDTO.getRevisedRentAmount();
        }
      } else {
        priceWithoutRevision = thirdPartyContractDTO.getExpectedChargeCost();

        if (revisionOfExpectedChargeCost) {
          priceWithoutRevision = thirdPartyContractDTO.getRevisedExpectedChargeCostAmount();
        }
      }

      // ratio du nombre de jours du contrat par rapport au nombre de jours du cycle
      double proportionDays = DateTimeUtils.getProportionOfDaysOnCycle(startCycle, endCycle, startValidity, endValidity);

      priceWithoutRevision = priceWithoutRevision.multiply(BigDecimal.valueOf(proportionDays));
      priceWithoutRevision = priceWithoutRevision.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

      BigDecimal price = priceWithoutRevision;

      // calcul du prix pour les contrats mensuels à terme échu avec une révision dans le mois en cours
      if (typePiece.getIndex() == GcpConstants.LOYER_COMPTE_GENERAL || typePiece.getIndex() == GcpConstants.LOYER_COMPTE_CHARGE) {
        if (revisionOfRentAmount && DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle) && thirdPartyContractDTO.getExpiryDate()) {
          LocalDate revisedRentDate = LocalDate.fromDateFields(thirdPartyContractDTO.getRevisedRentDate());
          BigDecimal revisedAmount = thirdPartyContractDTO.getRevisedRentAmount();
          BigDecimal initialRentAmount = thirdPartyContractDTO.getRentAmount();

          revisedAmount = revisedAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revisedRentDate.toDate(), endCycle.toDate(), generationDate));
          revisedAmount = revisedAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

          initialRentAmount = initialRentAmount.multiply(ThirdPartyContractUtils.getProportionsDays(startCycle.toDate(), revisedRentDate.minusDays(1).toDate(), generationDate));
          initialRentAmount = initialRentAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

          price = revisedAmount.add(initialRentAmount);
        }
      } else {
        if (revisionOfExpectedChargeCost && DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle) && thirdPartyContractDTO.getExpiryDate()) {
          LocalDate revisedExpectedChargeCostDate = LocalDate.fromDateFields(thirdPartyContractDTO.getRevisedExpectedChargeCostDate());
          BigDecimal expectedChargeCostAmount = thirdPartyContractDTO.getRevisedExpectedChargeCostAmount();
          BigDecimal initialExpectedChargeCostAmount = thirdPartyContractDTO.getExpectedChargeCost();

          expectedChargeCostAmount = expectedChargeCostAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revisedExpectedChargeCostDate.toDate(), endCycle.toDate(),
            generationDate));
          expectedChargeCostAmount = expectedChargeCostAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

          initialExpectedChargeCostAmount = initialExpectedChargeCostAmount.multiply(ThirdPartyContractUtils.getProportionsDays(startCycle.toDate(), revisedExpectedChargeCostDate
            .minusDays(1).toDate(), generationDate));
          initialExpectedChargeCostAmount = initialExpectedChargeCostAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

          price = expectedChargeCostAmount.add(initialExpectedChargeCostAmount);
        }
      }

      // insertion des données dans les lignes
      GCPEntryLine entryLine = new GCPEntryLine();
      entryLine.setTypeLigne(AccountantGcpDelegate.getLineType());
      entryLine.setCompte(AccountantGcpDelegate.getAccount(GcpType.YL, index, thirdPartyContractDTO, null, accountingCodeDTO));
      entryLine.setCodeCleCGS(AccountantGcpDelegate.getKeyCodeGCS(GcpType.YL, index, null));
      entryLine.setCleComptabilisation(AccountantGcpDelegate.getKeyAccounting(GcpType.YL, null, index, thirdPartyContractDTO, null, accountingCodeDTO, price));
      entryLine.setMontantDevisePiece(AccountantGcpDelegate.getAmount(price));
      entryLine.setCodeTVA(AccountantGcpDelegate.getValueAddedTaxCode(GcpType.YL, index));
      entryLine.setDomaineActivite(AccountantGcpDelegate.getActivityDomain(GcpType.YL, thirdPartyContractDTO, null));
      entryLine.setDateBase(AccountantGcpDelegate.getBaseDate(GcpType.YL, index, thirdPartyContractDTO, null, paymentCycle, generationDate));
      entryLine.setConditionPaiement(AccountantGcpDelegate.getConditionForPayment(GcpType.YL, index));
      entryLine.setCentreCout(AccountantGcpDelegate.getCostCenter(GcpType.YL, index, thirdPartyContractDTO, null));
      entryLine.setZoneAffection(AccountantGcpDelegate.getHeadquartersArea(GcpType.YL, index, thirdPartyContractDTO, null));
      entryLine.setTexte(AccountantGcpDelegate.getText(GcpType.YL, generationDate, thirdPartyContractDTO, null));

      // insertion des données pour l’historisation
      if (typePiece.getIndex() == GcpConstants.LOYER_COMPTE_GENERAL) {
        ylZnAccountingDocNumberDTO.setYlZnAdnRentAmount(priceWithoutRevision);
        ylZnAccountingDocNumberDTO.setYlZnAdnFinalRentAmount(priceWithoutRevision);
      } else if (typePiece.getIndex() == GcpConstants.CHARGES_COMPTE_GENERAL) {
        ylZnAccountingDocNumberDTO.setYlZnAdnExpectedChargeCost(priceWithoutRevision);
        ylZnAccountingDocNumberDTO.setYlZnAdnFinalExpectedChargeCost(priceWithoutRevision);
      }

      if (price.compareTo(BigDecimal.ZERO) != 0) {
        gcpEntryLines.add(entryLine);
      }
    }
    if (!entryBlock.getGcpEntryLines().isEmpty()) {
      gcpEntryBlocks.add(entryBlock);
    }
  }

  /**
   * Permet d'itérer sur tous les blocks afin d'insérer en dernier les numéros de pièces comptables
   * @param gcpEntryBlocks la liste des blocs de données
   * @param generationDate la date de génération
   */
  private void setYLZNAccountingPiecesNumbers(List<GCPEntryBlock> gcpEntryBlocks, LocalDate generationDate) {
    for (GCPEntryBlock gcpEntryBlock : gcpEntryBlocks) {
      gcpEntryBlock.getGcpHeader().setNumPieceComptable(createAccountingYlZnPieceNumber(gcpEntryBlock, generationDate));
    }
  }

  /**
   * Permet de créer et de renvoyer un objet référençant le numéro de la pièce comptable
   * @param gcpEntryBlock un bloc de données
   * @param generationDate la date de génération
   * @return le numéro de la pièce comptable issu de l'objet "YLZNAccountingPieceNumber"
   */
  private String createAccountingYlZnPieceNumber(GCPEntryBlock gcpEntryBlock, LocalDate generationDate) {
    String ylZnAccountingDocNumber = null;

    try {
      ThirdPartyContractDTO thirdPartyContractDTO = null;

      // On récupère le contrat tiers par sa référence
      thirdPartyContractDTO = thirdPartyContractServiceFacade.findByReference(gcpEntryBlock.getGcpHeader().getReference());

      YlZnAccountingDocNumberDTO ylZnAccountingDocNumberDTO;

      if (null == gcpEntryBlock.getYlZnAccountingDocNumberDTO()) {
        ylZnAccountingDocNumberDTO = new YlZnAccountingDocNumberDTO();
      } else {
        ylZnAccountingDocNumberDTO = gcpEntryBlock.getYlZnAccountingDocNumberDTO();
      }

      ylZnAccountingDocNumberDTO.setThirdPartyContract(thirdPartyContractDTO);
      ylZnAccountingDocNumberDTO.setYlZnAdnPieceDate(generationDate.toDate());
      ylZnAccountingDocNumberDTO.setYlZnAdnPieceType(gcpEntryBlock.getGcpType().toString());
      ylZnAccountingDocNumberDTO.setYlZnAdnCancellationDate(thirdPartyContractDTO.getCancellationDate());

      if (gcpEntryBlock.getGcpType() == GcpType.YL) {
        ylZnAccountingDocNumberDTO.setYlZnAdnMensualRentAmount(thirdPartyContractDTO.getRentAmount());
        ylZnAccountingDocNumberDTO.setYlZnAdnMensualExpectedChargeCost(thirdPartyContractDTO.getExpectedChargeCost());
        ylZnAccountingDocNumberDTO.setYlZnAdnCycleDate(ylZnAccountingDocNumberDTO.getYlZnAdnPieceDate());
      }

      // On crée l'objet référençant le numéro de la pièce comptable
      ylZnAccountingDocNumber = ylZnAccountingDocNumberService.createAccountingPieceNumber(ylZnAccountingDocNumberDTO);

    } catch (ThirdPartyContractServiceException e) {
      LOGGER.error("Erreur lors de la récupération du contrat tiers avec la référence " + gcpEntryBlock.getGcpHeader().getReference(), e);
    } catch (YlZnAccountingDocNumberServiceException e) {
      LOGGER.error("Erreur lors de la création de l’objet référençant le numéro de la pièce comptable", e);
    }

    return ylZnAccountingDocNumber;
  }

  @Override
  public List<GCPEntryBlock> getYLSporadicallyInvoicingEntryBlock(LocalDate generationDate) throws GcpServiceException {

    List<GCPEntryBlock> gcpEntryBlocks = new ArrayList<GCPEntryBlock>();

    try {
      initAccountingCodeMap();

      List<ThirdPartyContractDTO> thirdPartyContractDTOs = thirdPartyContractServiceFacade.findThirdPartyContractInProgressOrClose();

      for (ThirdPartyContractDTO thirdPartyContractDTO : thirdPartyContractDTOs) {
        processYlSporadicallyInvoicingEntryBlockForThirdPartyContract(generationDate, gcpEntryBlocks, thirdPartyContractDTO);
      }

      setYLZNAccountingPiecesNumbers(gcpEntryBlocks, generationDate);

    } catch (ThirdPartyContractServiceException e) {
      throw new GcpServiceException(e.getMessage(), e);
    }

    return gcpEntryBlocks;
  }

  /**
   * Permet de générer un bloc de ligne à insérer dans un fichier YL de charge ponctuelle pour un contrat tiers
   * @param generationDate date de génération
   * @param gcpEntryBlocks ensemble des blocs de ligne
   * @param thirdPartyContractDTO contrat tiers à traiter
   */
  private void processYlSporadicallyInvoicingEntryBlockForThirdPartyContract(LocalDate generationDate, List<GCPEntryBlock> gcpEntryBlocks,
    ThirdPartyContractDTO thirdPartyContractDTO) {
    GCPEntryBlock entryBlock = new GCPEntryBlock();
    entryBlock.setDateSent(generationDate);
    entryBlock.setPaymentCycle("FP");
    entryBlock.setGcpType(GcpType.YL_FP);

    // insertion des données de la ligne d’en-tête
    GCPHeader gcpHeader = new GCPHeader();
    gcpHeader.setTypeLigne(HeaderGcpDelegate.getLineType());
    gcpHeader.setDatePiece(HeaderGcpDelegate.getDateOfDocument(generationDate));
    gcpHeader.setTypePiece(HeaderGcpDelegate.getTypeOfDocument(GcpType.YL_FP));
    gcpHeader.setSociete(HeaderGcpDelegate.getSociety());
    gcpHeader.setDevisePiece(HeaderGcpDelegate.getCurrency());
    gcpHeader.setDateComptable(HeaderGcpDelegate.getAccountingDate(generationDate));
    gcpHeader.setTexteEntete(HeaderGcpDelegate.getHeaderText(GcpType.YL_FP, generationDate, null));
    gcpHeader.setReference(thirdPartyContractDTO.getReference());
    entryBlock.setGcpHeader(gcpHeader);

    List<GCPEntryLine> gcpEntryLines = new ArrayList<GCPEntryLine>();
    entryBlock.setGcpEntryLines(gcpEntryLines);

    for (AccountingCodeYLSporadicallyInvoicing typePiece : AccountingCodeYLSporadicallyInvoicing.values()) {
      AccountingCodeDTO accountingCodeDTO = accountingCodeMap.get(typePiece.getNumCompte());
      Integer index = typePiece.getIndex();

      BigDecimal price = new BigDecimal("0");
      if (null != thirdPartyContractDTO.getSporadicallyInvoicing()) {
        price = thirdPartyContractDTO.getSporadicallyInvoicing();
      }

      price = price.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

      // insertion des données dans les lignes
      GCPEntryLine entryLine = new GCPEntryLine();
      entryLine.setTypeLigne(AccountantGcpDelegate.getLineType());
      entryLine.setCompte(AccountantGcpDelegate.getAccount(GcpType.YL_FP, index, thirdPartyContractDTO, null, accountingCodeDTO));
      entryLine.setCodeCleCGS(AccountantGcpDelegate.getKeyCodeGCS(GcpType.YL_FP, index, null));
      entryLine.setCleComptabilisation(AccountantGcpDelegate.getKeyAccounting(GcpType.YL_FP, null, index, thirdPartyContractDTO, null, accountingCodeDTO, price));
      entryLine.setMontantDevisePiece(AccountantGcpDelegate.getAmount(price));
      entryLine.setCodeTVA(AccountantGcpDelegate.getValueAddedTaxCode(GcpType.YL_FP, index));
      entryLine.setDomaineActivite(AccountantGcpDelegate.getActivityDomain(GcpType.YL_FP, thirdPartyContractDTO, null));
      entryLine.setDateBase(AccountantGcpDelegate.getBaseDate(GcpType.YL_FP, index, thirdPartyContractDTO, null, null, generationDate));
      entryLine.setConditionPaiement(AccountantGcpDelegate.getConditionForPayment(GcpType.YL_FP, index));
      entryLine.setCentreCout(AccountantGcpDelegate.getCostCenter(GcpType.YL_FP, index, thirdPartyContractDTO, null));
      entryLine.setZoneAffection(AccountantGcpDelegate.getHeadquartersArea(GcpType.YL_FP, index, thirdPartyContractDTO, null));
      entryLine.setTexte(AccountantGcpDelegate.getText(GcpType.YL_FP, generationDate, thirdPartyContractDTO, null));

      if (price.compareTo(BigDecimal.ZERO) != 0) {
        gcpEntryLines.add(entryLine);
      }
    }
    if (!entryBlock.getGcpEntryLines().isEmpty()) {
      gcpEntryBlocks.add(entryBlock);
    }
  }

  @Override
  public List<GCPEntryBlock> getYLRegularizationBlock(LocalDate generationDate) throws GcpServiceException {

    List<GCPEntryBlock> gcpEntryBlocks = new ArrayList<GCPEntryBlock>();

    try {
      initAccountingCodeMap();
      Assert.notNull(accountingCodeMap, ACCOUNT_CODE_ERROR);

      List<ThirdPartyContractDTO> thirdPartyContractDTOs = thirdPartyContractServiceFacade.findThirdPartyContractToRegularize();

      for (ThirdPartyContractDTO thirdPartyContractDTO : thirdPartyContractDTOs) {
        processYlRegularizationBlockForThirdPartyContrat(generationDate, gcpEntryBlocks, thirdPartyContractDTO);
      }

      thirdPartyContractServiceFacade.resetThirdPartyContractToRegularize();

      setYLZNAccountingPiecesNumbers(gcpEntryBlocks, generationDate);

    } catch (ThirdPartyContractServiceException e) {
      throw new GcpServiceException(e.getMessage(), e);
    } catch (YlZnAccountingDocNumberServiceException e) {
      throw new GcpServiceException(e.getMessage(), e);
    } catch (RevisionThirdPartyContractServiceException e) {
      throw new GcpServiceException(e.getMessage(), e);
    }

    return gcpEntryBlocks;
  }

  /**
   * Permet de générer un bloc de ligne à insérer dans un fichier YL de régularisation pour un conrat
   * @param generationDate la date de génération
   * @param gcpEntryBlocks les lignes de régularisation
   * @param thirdPartyContractDTO le contrat à traiter
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   * @throws RevisionThirdPartyContractServiceException une RevisionThirdPartyContractServiceException
   */
  private void processYlRegularizationBlockForThirdPartyContrat(LocalDate generationDate, List<GCPEntryBlock> gcpEntryBlocks, ThirdPartyContractDTO thirdPartyContractDTO)
    throws YlZnAccountingDocNumberServiceException, RevisionThirdPartyContractServiceException {
    // date de traitement faisant partie du cycle
    LocalDate treatmentDate = generationDate;

    for (int i = 0; i < thirdPartyContractDTO.getYlRegularization(); i++) {
      // si l’on traite les contrats mensuels et qu’il est échu, on récupère les dates du cycle échu
      // sinon l’on prend les dates à échoir
      String cycle = thirdPartyContractDTO.getPaymentCycle().getLabel();
      Boolean expiryDate = thirdPartyContractDTO.getExpiryDate();
      LocalDate startRetroactivityCycle = DateTimeUtils.getFirstDayOfCycle(treatmentDate, cycle);
      LocalDate endRetroactivityCycle = DateTimeUtils.getLastDayOfCycle(treatmentDate, cycle);

      if (null != expiryDate && expiryDate) {
        startRetroactivityCycle = startRetroactivityCycle.minusMonths(1);
        endRetroactivityCycle = endRetroactivityCycle.minusMonths(1);
      }

      // récupération du cycle à régulariser
      YearMonth yearMonthOfCycle = DateTimeUtils.getYearMonthOfCycle(treatmentDate, cycle, expiryDate);

      if (null != expiryDate && expiryDate) {
        yearMonthOfCycle = yearMonthOfCycle.minusMonths(1);
      }

      // modification de la date de traitement pour le prochain remboursement du même contrat
      treatmentDate = getTreatmentDateOfNextRegularization(treatmentDate, cycle);

      // récupération de la dernière génération
      YlZnAccountingDocNumberDTO lastYlZnAccountingDocNumberDTO = ylZnAccountingDocNumberService.findLastGenerationByThirdPartyContractIdAndYearMonth(
        thirdPartyContractDTO.getId(), yearMonthOfCycle);
      if (!YlZnAccountingDocNumberUtils.verifyOlderGeneration(lastYlZnAccountingDocNumberDTO)) {
        continue;
      }

      // date de fin du contrat
      LocalDate endRetroactivityValidity = LocalDate.fromDateFields(thirdPartyContractDTO.getCancellationDate());

      // les révisions permettent de calculer le montant déjà payé pour le cycle
      List<RevisionThirdPartyContractDTO> revisionThirdPartyContracts = revisionThirdPartyContractService.findRevisionOfOneContract(thirdPartyContractDTO.getId());

      List<RevisionThirdPartyContractDTO> rentAmountRevisions = new ArrayList<RevisionThirdPartyContractDTO>();
      for (RevisionThirdPartyContractDTO revision : revisionThirdPartyContracts) {
        if (RevisionThirdPartyContractConstants.RENT_AMOUNT_REVISION_CODE.equals(revision.getType())) {
          rentAmountRevisions.add(revision);
        }
      }
      List<RevisionThirdPartyContractDTO> expectedChargeCostRevisions = new ArrayList<RevisionThirdPartyContractDTO>();
      for (RevisionThirdPartyContractDTO revision : revisionThirdPartyContracts) {
        if (RevisionThirdPartyContractConstants.EXPECTED_CHARGE_COST_REVISION_CODE.equals(revision.getType())) {
          expectedChargeCostRevisions.add(revision);
        }
      }

      LocalDate startTreatment = endRetroactivityValidity.dayOfMonth().withMinimumValue();
      if (startRetroactivityCycle.isAfter(startTreatment)) {
        startTreatment = startRetroactivityCycle;
      }

      // calcul du nombre de mois entre la date d’effet et la date d’aujourd’hui
      int months = Months.monthsBetween(startTreatment, endRetroactivityCycle).getMonths() + 1;

      BigDecimal totalRetroactivityRentAmount = BigDecimal.ZERO;
      BigDecimal totalRetroactivityExpectedChargeCost = BigDecimal.ZERO;

      for (int j = 0; j < months; j++) {

        LocalDate activeMonth = startTreatment.plusMonths(j);
        LocalDate startMonth = activeMonth.dayOfMonth().withMinimumValue();
        LocalDate endMonth = activeMonth.dayOfMonth().withMaximumValue();
        LocalDate startRegularization = endRetroactivityValidity.plusDays(1);
        LocalDate endRegularization = endMonth;

        if (startRegularization.isBefore(startMonth)) {
          startRegularization = startMonth;
        }

        // seule la révision la plus récente, antérieure à la date de régularisation, est nécessaire
        RevisionThirdPartyContractDTO mostRecentPreviousRentAmountRevision = null;
        List<RevisionThirdPartyContractDTO> rentAmountRevisionsOfMonth = new ArrayList<RevisionThirdPartyContractDTO>();
        for (RevisionThirdPartyContractDTO revision : rentAmountRevisions) {
          if (!revision.getDate().after(startRegularization.toDate())) {
            mostRecentPreviousRentAmountRevision = revision;
          } else if (!revision.getDate().after(endMonth.toDate())) {
            // On récupére les révisions du mois de révision
            rentAmountRevisionsOfMonth.add(revision);
          }
        }
        // supprime les révisions à ne pas traiter (déjà traitée dans le passé)
        int k = 0;
        List<RevisionThirdPartyContractDTO> rentAmountRevisionsToDelete = new ArrayList<RevisionThirdPartyContractDTO>();
        for (RevisionThirdPartyContractDTO revision : rentAmountRevisionsOfMonth) {
          int l = 0;
          for (RevisionThirdPartyContractDTO revision2 : rentAmountRevisionsOfMonth) {
            if (l < k && !revision2.getDate().before(revision.getDate())) {
              rentAmountRevisionsToDelete.add(revision2);
            }
            l += 1;
          }
          k += 1;
        }
        for (RevisionThirdPartyContractDTO revisionToDelete : rentAmountRevisionsToDelete) {
          rentAmountRevisionsOfMonth.remove(revisionToDelete);
        }

        RevisionThirdPartyContractDTO mostRecentPreviousExpectedChargeCostRevision = null;
        List<RevisionThirdPartyContractDTO> expectedChargeCostRevisionsOfMonth = new ArrayList<RevisionThirdPartyContractDTO>();
        for (RevisionThirdPartyContractDTO revision : expectedChargeCostRevisions) {
          if (!revision.getDate().after(startRegularization.toDate())) {
            mostRecentPreviousExpectedChargeCostRevision = revision;
          } else if (!revision.getDate().after(endMonth.toDate())) {
            // On récupére les révisions du mois de révision
            expectedChargeCostRevisionsOfMonth.add(revision);
          }
        }
        // supprime les révisions à ne pas traiter (déjà traitée dans le passé)
        int m = 0;
        List<RevisionThirdPartyContractDTO> expectedChargeCostRevisionsToDelete = new ArrayList<RevisionThirdPartyContractDTO>();
        for (RevisionThirdPartyContractDTO revision : expectedChargeCostRevisionsOfMonth) {
          int n = 0;
          for (RevisionThirdPartyContractDTO revision2 : expectedChargeCostRevisionsOfMonth) {
            if (n < m && !revision2.getDate().before(revision.getDate())) {
              expectedChargeCostRevisionsToDelete.add(revision2);
            }
            n += 1;
          }
          m += 1;
        }
        for (RevisionThirdPartyContractDTO revisionToDelete : expectedChargeCostRevisionsToDelete) {
          expectedChargeCostRevisionsOfMonth.remove(revisionToDelete);
        }

        // prorata du nombre de jours dans la partie à régulariser
        double retroactivityProportionDays = DateTimeUtils.getProportionOfDaysOnCycle(startMonth, endMonth, startRegularization, endRegularization);

        BigDecimal retroactivityRentAmount;
        if (mostRecentPreviousRentAmountRevision != null) {
          retroactivityRentAmount = mostRecentPreviousRentAmountRevision.getAmount();
        } else {
          retroactivityRentAmount = lastYlZnAccountingDocNumberDTO.getYlZnAdnMensualRentAmount();
        }

        if (rentAmountRevisionsOfMonth.isEmpty()) {
          retroactivityRentAmount = retroactivityRentAmount.multiply(BigDecimal.valueOf(retroactivityProportionDays));
          retroactivityRentAmount = retroactivityRentAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
        } else {
          BigDecimal previousFirstPartMonth = BigDecimal.ZERO;

          int o = 0;
          for (RevisionThirdPartyContractDTO revision : rentAmountRevisionsOfMonth) {
            LocalDate revisionDate = LocalDate.fromDateFields(revision.getDate());

            BigDecimal firstPartAmount;
            if (o == 0) {
              firstPartAmount = retroactivityRentAmount;
            } else {
              firstPartAmount = rentAmountRevisionsOfMonth.get(o - 1).getAmount();
            }

            if (o == 0) {
              firstPartAmount = firstPartAmount.multiply(ThirdPartyContractUtils.getProportionsDays(startRegularization.toDate(), revisionDate.minusDays(1).toDate(), activeMonth));
            } else {
              firstPartAmount = firstPartAmount.multiply(ThirdPartyContractUtils.getProportionsDays(rentAmountRevisionsOfMonth.get(o - 1).getDate(), revisionDate.minusDays(1)
                .toDate(), activeMonth));
            }
            firstPartAmount = firstPartAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

            BigDecimal revisedAmount = revision.getAmount();
            revisedAmount = revisedAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revision.getDate(), endMonth.toDate(), activeMonth));
            revisedAmount = revisedAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

            retroactivityRentAmount = firstPartAmount.add(revisedAmount).add(previousFirstPartMonth);

            previousFirstPartMonth = firstPartAmount;
            o += 1;
          }
        }

        BigDecimal retroactivityExpectedChargeCost;
        if (mostRecentPreviousExpectedChargeCostRevision != null) {
          retroactivityExpectedChargeCost = mostRecentPreviousExpectedChargeCostRevision.getAmount();
        } else {
          retroactivityExpectedChargeCost = lastYlZnAccountingDocNumberDTO.getYlZnAdnMensualExpectedChargeCost();
        }

        if (expectedChargeCostRevisionsOfMonth.isEmpty()) {
          retroactivityExpectedChargeCost = retroactivityExpectedChargeCost.multiply(BigDecimal.valueOf(retroactivityProportionDays));
          retroactivityExpectedChargeCost = retroactivityExpectedChargeCost.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
        } else {
          BigDecimal previousFirstPartMonth = BigDecimal.ZERO;

          int o = 0;
          for (RevisionThirdPartyContractDTO revision : expectedChargeCostRevisionsOfMonth) {
            LocalDate revisionDate = LocalDate.fromDateFields(revision.getDate());

            BigDecimal firstPartAmount;
            if (o == 0) {
              firstPartAmount = retroactivityRentAmount;
            } else {
              firstPartAmount = expectedChargeCostRevisionsOfMonth.get(o - 1).getAmount();
            }

            if (o == 0) {
              firstPartAmount = firstPartAmount.multiply(ThirdPartyContractUtils.getProportionsDays(startRegularization.toDate(), revisionDate.minusDays(1).toDate(), activeMonth));
            } else {
              firstPartAmount = firstPartAmount.multiply(ThirdPartyContractUtils.getProportionsDays(expectedChargeCostRevisionsOfMonth.get(o - 1).getDate(), revisionDate
                .minusDays(1).toDate(), activeMonth));
            }
            firstPartAmount = firstPartAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

            BigDecimal revisedAmount = revision.getAmount();
            revisedAmount = revisedAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revision.getDate(), endMonth.toDate(), activeMonth));
            revisedAmount = revisedAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

            retroactivityExpectedChargeCost = firstPartAmount.add(revisedAmount).add(previousFirstPartMonth);

            previousFirstPartMonth = firstPartAmount;
            o += 1;
          }
        }

        totalRetroactivityRentAmount = totalRetroactivityRentAmount.subtract(retroactivityRentAmount);
        totalRetroactivityExpectedChargeCost = totalRetroactivityExpectedChargeCost.subtract(retroactivityExpectedChargeCost);
      }

      GCPEntryBlock entryBlock = new GCPEntryBlock();
      YlZnAccountingDocNumberDTO ylZnAccountingDocNumberDTO = new YlZnAccountingDocNumberDTO();
      entryBlock.setYlZnAccountingDocNumberDTO(ylZnAccountingDocNumberDTO);
      entryBlock.setDateSent(generationDate);
      entryBlock.setPaymentCycle("Regul");
      entryBlock.setGcpType(GcpType.YL_REGUL);
      entryBlock.setReference(thirdPartyContractDTO.getReference());

      GCPHeader gcpHeader = new GCPHeader();
      gcpHeader.setTypeLigne(HeaderGcpDelegate.getLineType());
      gcpHeader.setDatePiece(HeaderGcpDelegate.getDateOfDocument(generationDate));
      gcpHeader.setTypePiece(HeaderGcpDelegate.getTypeOfDocument(GcpType.YL_REGUL));
      gcpHeader.setSociete(HeaderGcpDelegate.getSociety());
      gcpHeader.setDevisePiece(HeaderGcpDelegate.getCurrency());
      gcpHeader.setDateComptable(HeaderGcpDelegate.getAccountingDate(generationDate));
      gcpHeader.setTexteEntete(HeaderGcpDelegate.getHeaderText(GcpType.YL_REGUL, generationDate, null));
      gcpHeader.setReference(HeaderGcpDelegate.getReference(GcpType.YL_REGUL, thirdPartyContractDTO, null));
      entryBlock.setGcpHeader(gcpHeader);

      List<GCPEntryLine> gcpEntryLines = new ArrayList<GCPEntryLine>();
      entryBlock.setGcpEntryLines(gcpEntryLines);

      // informations du mois remboursé
      Date lastPieceDate = lastYlZnAccountingDocNumberDTO.getYlZnAdnPieceDate();
      BigDecimal lastMensualRentAmount = lastYlZnAccountingDocNumberDTO.getYlZnAdnMensualRentAmount();
      BigDecimal lastMensualExpectedChargeCost = lastYlZnAccountingDocNumberDTO.getYlZnAdnMensualExpectedChargeCost();

      ylZnAccountingDocNumberDTO.setYlZnAdnCycleDate(lastPieceDate);
      ylZnAccountingDocNumberDTO.setYlZnAdnMensualRentAmount(lastMensualRentAmount);
      ylZnAccountingDocNumberDTO.setYlZnAdnMensualExpectedChargeCost(lastMensualExpectedChargeCost);

      new LocalDate(thirdPartyContractDTO.getStartValidity());
      if (thirdPartyContractDTO.getCancellationDate() != null) {
        new LocalDate(thirdPartyContractDTO.getCancellationDate());
      }

      for (AccountingCodeYL typePiece : AccountingCodeYL.values()) {
        AccountingCodeDTO accountingCodeDTO = accountingCodeMap.get(typePiece.getNumCompte());
        Integer index = typePiece.getIndex();

        BigDecimal price = BigDecimal.ZERO;

        if (typePiece.getIndex() == GcpConstants.LOYER_COMPTE_GENERAL || typePiece.getIndex() == GcpConstants.LOYER_COMPTE_CHARGE) {
          ylZnAccountingDocNumberDTO.setYlZnAdnFinalRentAmount(totalRetroactivityRentAmount);
          price = totalRetroactivityRentAmount;
        } else if (typePiece.getIndex() == GcpConstants.CHARGES_COMPTE_GENERAL || typePiece.getIndex() == GcpConstants.CHARGES_COMPTE_LOCATIVES) {
          ylZnAccountingDocNumberDTO.setYlZnAdnFinalExpectedChargeCost(totalRetroactivityExpectedChargeCost);
          price = totalRetroactivityExpectedChargeCost;
        }

        // insertion des données dans les lignes
        GCPEntryLine entryLine = new GCPEntryLine();
        entryLine.setTypeLigne(AccountantGcpDelegate.getLineType());
        entryLine.setCompte(AccountantGcpDelegate.getAccount(GcpType.YL_REGUL, index, thirdPartyContractDTO, null, accountingCodeDTO));
        entryLine.setCodeCleCGS(AccountantGcpDelegate.getKeyCodeGCS(GcpType.YL_REGUL, index, null));
        entryLine.setCleComptabilisation(AccountantGcpDelegate.getKeyAccounting(GcpType.YL_REGUL, null, index, thirdPartyContractDTO, null, accountingCodeDTO, price));
        entryLine.setMontantDevisePiece(AccountantGcpDelegate.getAmount(price));
        entryLine.setCodeTVA(AccountantGcpDelegate.getValueAddedTaxCode(GcpType.YL_REGUL, index));
        entryLine.setDomaineActivite(AccountantGcpDelegate.getActivityDomain(GcpType.YL_REGUL, thirdPartyContractDTO, null));
        entryLine.setDateBase(AccountantGcpDelegate.getBaseDate(GcpType.YL_REGUL, index, thirdPartyContractDTO, null, null, generationDate));
        entryLine.setConditionPaiement(AccountantGcpDelegate.getConditionForPayment(GcpType.YL_REGUL, index));
        entryLine.setCentreCout(AccountantGcpDelegate.getCostCenter(GcpType.YL_REGUL, index, thirdPartyContractDTO, null));
        entryLine.setZoneAffection(AccountantGcpDelegate.getHeadquartersArea(GcpType.YL_REGUL, index, thirdPartyContractDTO, null));
        entryLine.setTexte(AccountantGcpDelegate.getText(GcpType.YL_REGUL, generationDate, thirdPartyContractDTO, null));

        if (price.compareTo(BigDecimal.ZERO) != 0) {
          gcpEntryLines.add(entryLine);
        }

      }
      if (!entryBlock.getGcpEntryLines().isEmpty()) {
        gcpEntryBlocks.add(entryBlock);
      }
    }
  }

  /**
   * Permet de récupérer la date de traitement pour le prochain remboursement
   * @param treatmentDate date de traitement
   * @param cycle cycle de paiement
   * @return prochaine date de traitement
   */
  private LocalDate getTreatmentDateOfNextRegularization(LocalDate treatmentDate, String cycle) {
    if (DateTimeUtils.ANNUAL_CYCLE.equals(cycle)) {
      return treatmentDate.minusMonths(DateTimeUtils.MONTHS_IN_A_YEAR);
    } else if (DateTimeUtils.QUARTERLY_CYCLE.equals(cycle)) {
      return treatmentDate.minusMonths(DateTimeUtils.MONTHS_IN_A_QUARTER);
    } else {
      return treatmentDate.minusMonths(DateTimeUtils.MONTHS_IN_A_MONTH);
    }
  }

  @Override
  public List<GCPEntryBlock> getYLRevisionBlock(LocalDate generationDate) throws GcpServiceException {

    List<GCPEntryBlock> gcpEntryBlocks = new ArrayList<GCPEntryBlock>();

    try {
      initAccountingCodeMap();
      Assert.notNull(accountingCodeMap, ACCOUNT_CODE_ERROR);

      List<ThirdPartyContractDTO> thirdPartyContractDTOs = thirdPartyContractServiceFacade.findThirdPartyContractWithActiveRevision();

      for (ThirdPartyContractDTO thirdPartyContractDTO : thirdPartyContractDTOs) {
        processYlRevisionBlockForThirdPartyContrat(generationDate, gcpEntryBlocks, thirdPartyContractDTO);
      }

      setYLZNAccountingPiecesNumbers(gcpEntryBlocks, generationDate);

    } catch (ThirdPartyContractServiceException e) {
      throw new GcpServiceException(e.getMessage(), e);
    } catch (YlZnAccountingDocNumberServiceException e) {
      throw new GcpServiceException(e.getMessage(), e);
    } catch (RevisionThirdPartyContractServiceException e) {
      throw new GcpServiceException(e.getMessage(), e);
    }

    return gcpEntryBlocks;
  }

  /**
   * Permet de générer un bloc de ligne à insérer dans un fichier YL de révision pour un conrat
   * @param generationDate la date de génération
   * @param gcpEntryBlocks les lignes de régularisation
   * @param thirdPartyContractDTO le contrat à traiter
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   * @throws RevisionThirdPartyContractServiceException une RevisionThirdPartyContractServiceException
   */
  private void processYlRevisionBlockForThirdPartyContrat(LocalDate generationDate, List<GCPEntryBlock> gcpEntryBlocks, ThirdPartyContractDTO thirdPartyContractDTO)
    throws YlZnAccountingDocNumberServiceException, RevisionThirdPartyContractServiceException {

    GCPEntryBlock entryBlock = new GCPEntryBlock();
    YlZnAccountingDocNumberDTO ylZnAccountingDocNumberDTO = new YlZnAccountingDocNumberDTO();
    entryBlock.setYlZnAccountingDocNumberDTO(ylZnAccountingDocNumberDTO);
    entryBlock.setDateSent(generationDate);
    entryBlock.setPaymentCycle("Revi");
    entryBlock.setGcpType(GcpType.YL_REVI);
    entryBlock.setReference(thirdPartyContractDTO.getReference());

    GCPHeader gcpHeader = new GCPHeader();
    gcpHeader.setTypeLigne(HeaderGcpDelegate.getLineType());
    gcpHeader.setDatePiece(HeaderGcpDelegate.getDateOfDocument(generationDate));
    gcpHeader.setTypePiece(HeaderGcpDelegate.getTypeOfDocument(GcpType.YL_REVI));
    gcpHeader.setSociete(HeaderGcpDelegate.getSociety());
    gcpHeader.setDevisePiece(HeaderGcpDelegate.getCurrency());
    gcpHeader.setDateComptable(HeaderGcpDelegate.getAccountingDate(generationDate));
    gcpHeader.setTexteEntete(HeaderGcpDelegate.getHeaderText(GcpType.YL_REVI, generationDate, null));
    gcpHeader.setReference(HeaderGcpDelegate.getReference(GcpType.YL_REVI, thirdPartyContractDTO, null));
    entryBlock.setGcpHeader(gcpHeader);

    List<GCPEntryLine> gcpEntryLines = new ArrayList<GCPEntryLine>();
    entryBlock.setGcpEntryLines(gcpEntryLines);

    for (AccountingCodeYL typePiece : AccountingCodeYL.values()) {
      AccountingCodeDTO accountingCodeDTO = accountingCodeMap.get(typePiece.getNumCompte());
      Integer index = typePiece.getIndex();

      BigDecimal price = BigDecimal.ZERO;

      if (ThirdPartyContractUtils.hasRevision(thirdPartyContractDTO)) {
        // prise en compte du cycle
        LocalDate now = LocalDate.now();
        String paymentCycle = thirdPartyContractDTO.getPaymentCycle().getLabel();
        Boolean expiryDate = thirdPartyContractDTO.getExpiryDate();

        LocalDate treatmentDateAtTheEnd = now;
        LocalDate endExpiryDateCycle = DateTimeUtils.getLastDayOfCycle(treatmentDateAtTheEnd, paymentCycle);

        // le cycle à traiter concerne le mois suivant pour les contrats à échoir
        LocalDate treatmentDateInAdvance = now.plusMonths(1);
        LocalDate endFallDueCycle = DateTimeUtils.getLastDayOfCycle(treatmentDateInAdvance, paymentCycle);

        LocalDate endCycle;

        if (DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle) && thirdPartyContractDTO.getExpiryDate()) {
          endCycle = endExpiryDateCycle;
        } else {
          endCycle = endFallDueCycle;
        }

        Boolean revisionOfRentAmount = ThirdPartyContractUtils.hasRevisionOfRentAmount(thirdPartyContractDTO);
        Boolean revisionOfExpectedChargeCost = ThirdPartyContractUtils.hasRevisionOfExpectedChargeCost(thirdPartyContractDTO);

        List<RevisionThirdPartyContractDTO> revisionThirdPartyContracts = revisionThirdPartyContractService.findRevisionOfOneContract(thirdPartyContractDTO.getId());

        LocalDate endCycleMonthOfRevision = DateTimeUtils.getLastDayOfCycle(now, paymentCycle);

        if (DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle) && thirdPartyContractDTO.getExpiryDate()) {
          endCycleMonthOfRevision = endCycleMonthOfRevision.minusMonths(1);
        }

        Boolean rentAccount = typePiece.getIndex() == GcpConstants.LOYER_COMPTE_GENERAL || typePiece.getIndex() == GcpConstants.LOYER_COMPTE_CHARGE;
        Boolean costAccount = typePiece.getIndex() == GcpConstants.CHARGES_COMPTE_GENERAL || typePiece.getIndex() == GcpConstants.CHARGES_COMPTE_LOCATIVES;
        if (rentAccount && revisionOfRentAmount && thirdPartyContractDTO.getRevisedRentDate() != null && !thirdPartyContractDTO.getRevisedRentDate().after(endCycle.toDate())) {
          price = calculatePriceForRevisionOfRentAmount(thirdPartyContractDTO, paymentCycle, expiryDate, revisionThirdPartyContracts, endCycleMonthOfRevision);
        } else if (costAccount && revisionOfExpectedChargeCost && thirdPartyContractDTO.getRevisedExpectedChargeCostDate() != null
          && !thirdPartyContractDTO.getRevisedExpectedChargeCostDate().after(endCycle.toDate())) {
          price = calculatePriceForRevisionOfExpectedChargeCost(thirdPartyContractDTO, paymentCycle, expiryDate, revisionThirdPartyContracts, endCycleMonthOfRevision);
        }
      }

      // insertion des données dans les lignes
      GCPEntryLine entryLine = new GCPEntryLine();
      entryLine.setTypeLigne(AccountantGcpDelegate.getLineType());
      entryLine.setCompte(AccountantGcpDelegate.getAccount(GcpType.YL_REVI, index, thirdPartyContractDTO, null, accountingCodeDTO));
      entryLine.setCodeCleCGS(AccountantGcpDelegate.getKeyCodeGCS(GcpType.YL_REVI, index, null));
      entryLine.setCleComptabilisation(AccountantGcpDelegate.getKeyAccounting(GcpType.YL_REVI, null, index, thirdPartyContractDTO, null, accountingCodeDTO, price));
      entryLine.setMontantDevisePiece(AccountantGcpDelegate.getAmount(price));
      entryLine.setCodeTVA(AccountantGcpDelegate.getValueAddedTaxCode(GcpType.YL_REVI, index));
      entryLine.setDomaineActivite(AccountantGcpDelegate.getActivityDomain(GcpType.YL_REVI, thirdPartyContractDTO, null));
      entryLine.setDateBase(AccountantGcpDelegate.getBaseDate(GcpType.YL_REVI, index, thirdPartyContractDTO, null, null, generationDate));
      entryLine.setConditionPaiement(AccountantGcpDelegate.getConditionForPayment(GcpType.YL_REVI, index));
      entryLine.setCentreCout(AccountantGcpDelegate.getCostCenter(GcpType.YL_REVI, index, thirdPartyContractDTO, null));
      entryLine.setZoneAffection(AccountantGcpDelegate.getHeadquartersArea(GcpType.YL_REVI, index, thirdPartyContractDTO, null));
      entryLine.setTexte(AccountantGcpDelegate.getText(GcpType.YL_REVI, generationDate, thirdPartyContractDTO, null));

      if (price.compareTo(BigDecimal.ZERO) != 0) {
        gcpEntryLines.add(entryLine);
      }
    }

    if (!entryBlock.getGcpEntryLines().isEmpty()) {
      gcpEntryBlocks.add(entryBlock);
    }
  }

  /**
   * Permet de calculer le montant de la révision du loyer mensuel
   * @param thirdPartyContractDTO contrat tiers à traiter
   * @param paymentCycle cycle de paiement
   * @param expiryDate si le contrat est échu
   * @param revisionThirdPartyContracts révisions du contrat tiers
   * @param endCycleMonthOfRevision date de fin du cycle de la révision
   * @return montant de la révision du loyer mensuel
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   */
  private BigDecimal calculatePriceForRevisionOfRentAmount(ThirdPartyContractDTO thirdPartyContractDTO, String paymentCycle, Boolean expiryDate,
    List<RevisionThirdPartyContractDTO> revisionThirdPartyContracts, LocalDate endCycleMonthOfRevision) throws YlZnAccountingDocNumberServiceException {
    BigDecimal price = BigDecimal.ZERO;

    LocalDate revisedRentDate = LocalDate.fromDateFields(thirdPartyContractDTO.getRevisedRentDate());

    // calcul du nombre de mois entre la date d’effet et la date d’aujourd’hui
    int months = Months.monthsBetween(revisedRentDate.dayOfMonth().withMinimumValue(), endCycleMonthOfRevision).getMonths() + 1;

    // boucle dans les mois entre la date d’effet et la date d’aujourd’hui
    for (int i = 0; i < months; i++) {

      LocalDate startDate = revisedRentDate;
      Boolean firstMonth = true;

      // Si on n'est pas dans le premier mois de révision
      if (i > 0) {
        revisedRentDate = revisedRentDate.plusMonths(1);
        startDate = revisedRentDate.dayOfMonth().withMinimumValue();
        firstMonth = false;
      }

      // récupération du cycle à régulariser
      YearMonth yearMonthOfCycle = DateTimeUtils.getYearMonthOfCycle(revisedRentDate, paymentCycle, expiryDate);

      if (null != expiryDate && expiryDate) {
        yearMonthOfCycle = yearMonthOfCycle.minusMonths(1);
      }

      // On récupère uniquement YL du cycle à régulariser
      YlZnAccountingDocNumberDTO lastYlZnAccountingDocNumberDTO = ylZnAccountingDocNumberService.findLastGenerationByThirdPartyContractIdAndYearMonth(
        thirdPartyContractDTO.getId(), yearMonthOfCycle);

      if (!YlZnAccountingDocNumberUtils.verifyOlderGeneration(lastYlZnAccountingDocNumberDTO)) {
        continue;
      }

      BigDecimal revisionRentAmount = ThirdPartyContractUtils.getRevisionRentAmount(thirdPartyContractDTO, revisionThirdPartyContracts, lastYlZnAccountingDocNumberDTO, startDate,
        firstMonth);
      price = price.add(revisionRentAmount);
    }
    return price;
  }

  /**
   * Permet de calculer le montant de la révision des charges prévisionnelles mensuelles
   * @param thirdPartyContractDTO contrat tiers à traiter
   * @param paymentCycle cycle de paiement
   * @param expiryDate si le contrat est échu
   * @param revisionThirdPartyContracts révisions du contrat tiers
   * @param endCycleMonthOfRevision date de fin du cycle de la révision
   * @return montant de la révision du loyer mensuel
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   */
  private BigDecimal calculatePriceForRevisionOfExpectedChargeCost(ThirdPartyContractDTO thirdPartyContractDTO, String paymentCycle, Boolean expiryDate,
    List<RevisionThirdPartyContractDTO> revisionThirdPartyContracts, LocalDate endCycleMonthOfRevision) throws YlZnAccountingDocNumberServiceException {
    BigDecimal price = BigDecimal.ZERO;

    LocalDate revisedExpectedChargeCostDate = LocalDate.fromDateFields(thirdPartyContractDTO.getRevisedExpectedChargeCostDate());

    // calcul du nombre de mois entre la date d’effet et la date d’aujourd’hui
    int months = Months.monthsBetween(revisedExpectedChargeCostDate.dayOfMonth().withMinimumValue(), endCycleMonthOfRevision).getMonths() + 1;

    // boucle dans les mois entre la date d’effet et la date d’aujourd’hui
    for (int i = 0; i < months; i++) {

      LocalDate startDate = revisedExpectedChargeCostDate;
      Boolean firstMonth = true;

      // Si on n'est pas dans le premier mois de révision
      if (i > 0) {
        revisedExpectedChargeCostDate = revisedExpectedChargeCostDate.plusMonths(1);
        startDate = revisedExpectedChargeCostDate.dayOfMonth().withMinimumValue();
        firstMonth = false;
      }

      // récupération du cycle à régulariser
      YearMonth yearMonthOfCycle = DateTimeUtils.getYearMonthOfCycle(revisedExpectedChargeCostDate, paymentCycle, expiryDate);

      if (null != expiryDate && expiryDate) {
        yearMonthOfCycle = yearMonthOfCycle.minusMonths(1);
      }

      // On récupère uniquement YL du cycle à régulariser
      YlZnAccountingDocNumberDTO lastYlZnAccountingDocNumberDTO = ylZnAccountingDocNumberService.findLastGenerationByThirdPartyContractIdAndYearMonth(
        thirdPartyContractDTO.getId(), yearMonthOfCycle);

      if (!YlZnAccountingDocNumberUtils.verifyOlderGeneration(lastYlZnAccountingDocNumberDTO)) {
        continue;
      }

      BigDecimal revisionExpectedChargeCost = ThirdPartyContractUtils.getRevisionExpectedChargeCostAmount(thirdPartyContractDTO, revisionThirdPartyContracts,
        lastYlZnAccountingDocNumberDTO, startDate, firstMonth);
      price = price.add(revisionExpectedChargeCost);
    }
    return price;
  }

  @Override
  public List<GCPEntryBlock> getZNEntryBlock(LocalDate generationDate, String paymentCycle) throws GcpServiceException {
    List<GCPEntryBlock> gcpEntryBlocks = new ArrayList<GCPEntryBlock>();

    try {
      initAccountingCodeMap();
      Assert.notNull(accountingCodeMap, ACCOUNT_CODE_ERROR);

      int iterations = 0;
      if (DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle)) {
        iterations = GcpConstants.MONTHLY_ITERATIONS;
      } else if (DateTimeUtils.ANNUAL_CYCLE.equals(paymentCycle)) {
        iterations = GcpConstants.ANNUAL_ITERATIONS;
      } else if (DateTimeUtils.QUARTERLY_CYCLE.equals(paymentCycle)) {
        iterations = GcpConstants.QUARTERLY_ITERATIONS;
      }

      Map<Integer, List<GCPEntryBlock>> gcpInterationsEntryBlocks = new HashMap<Integer, List<GCPEntryBlock>>();
      for (int i = 0; i < iterations; i++) {
        gcpInterationsEntryBlocks.put(i, new ArrayList<GCPEntryBlock>());
      }

      // le cycle à traiter concerne le mois suivant
      LocalDate treatmentDate = generationDate.plusMonths(1);
      LocalDate startCycle = DateTimeUtils.getFirstDayOfCycle(treatmentDate, paymentCycle);
      LocalDate endCycle = DateTimeUtils.getLastDayOfCycle(treatmentDate, paymentCycle);

      List<ThirdPartyContractDTO> thirdPartyContractDTOs = thirdPartyContractServiceFacade.findThirdPartyContractPerCycle(startCycle, endCycle, paymentCycle, 0);

      for (ThirdPartyContractDTO thirdPartyContractDTO : thirdPartyContractDTOs) {
        processZnEntryBlockForThirdPartyContract(generationDate, paymentCycle, iterations, gcpInterationsEntryBlocks, startCycle, endCycle, thirdPartyContractDTO);

      }

      for (int i = 0; i < iterations; i++) {
        gcpEntryBlocks.addAll(gcpInterationsEntryBlocks.get(i));
      }

      setYLZNAccountingPiecesNumbers(gcpEntryBlocks, generationDate);

    } catch (ThirdPartyContractServiceException e) {
      throw new GcpServiceException(e.getMessage(), e);
    }

    return gcpEntryBlocks;
  }

  /**
   * Permet de  le bloc de ligne à insérer dans un fichier ZN pour un contrat tiers
   * @param generationDate la date de génération
   * @param paymentCycle le cycle de paiement
   * @param iterations nombre d’itérations
   * @param gcpInterationsEntryBlocks l’ensemble des blocs de ligne
   * @param startCycle date de début du cycle
   * @param endCycle date de fin du cycle
   * @param thirdPartyContractDTO contrat tiers à traiter
   */
  private void processZnEntryBlockForThirdPartyContract(LocalDate generationDate, String paymentCycle, int iterations, Map<Integer, List<GCPEntryBlock>> gcpInterationsEntryBlocks,
    LocalDate startCycle, LocalDate endCycle, ThirdPartyContractDTO thirdPartyContractDTO) {
    for (int i = 0; i < iterations; i++) {
      LocalDate treatmentDate = generationDate.plusMonths(i);

      GCPEntryBlock entryBlock = new GCPEntryBlock();
      entryBlock.setDateSent(treatmentDate);
      entryBlock.setPaymentCycle(paymentCycle);
      entryBlock.setGcpType(GcpType.ZN);
      entryBlock.setReference(thirdPartyContractDTO.getReference());

      GCPHeader gcpHeader = new GCPHeader();
      gcpHeader.setTypeLigne(HeaderGcpDelegate.getLineType());
      gcpHeader.setDatePiece(HeaderGcpDelegate.getDateOfDocument(generationDate));
      gcpHeader.setTypePiece(HeaderGcpDelegate.getTypeOfDocument(GcpType.ZN));
      gcpHeader.setSociete(HeaderGcpDelegate.getSociety());
      gcpHeader.setDevisePiece(HeaderGcpDelegate.getCurrency());
      gcpHeader.setDateComptable(HeaderGcpDelegate.getAccountingDate(treatmentDate));
      gcpHeader.setTexteEntete(HeaderGcpDelegate.getHeaderText(GcpType.ZN, generationDate, null));
      gcpHeader.setReference(HeaderGcpDelegate.getReference(GcpType.ZN, thirdPartyContractDTO, null));
      entryBlock.setGcpHeader(gcpHeader);

      List<GCPEntryLine> gcpEntryLines = new ArrayList<GCPEntryLine>();
      entryBlock.setGcpEntryLines(gcpEntryLines);

      for (AccountingCodeZN typePiece : AccountingCodeZN.values()) {
        AccountingCodeDTO accountingCodeDTO = accountingCodeMap.get(typePiece.getNumCompte());
        Integer index = typePiece.getIndex();

        Boolean inCycle = false;
        if (i > 0) {
          inCycle = true;
        }

        // date de début du contrat
        LocalDate startValidity = new LocalDate(thirdPartyContractDTO.getStartValidity());
        // date de fin du contrat
        LocalDate endValidity = null;
        if (thirdPartyContractDTO.getCancellationDate() != null) {
          endValidity = new LocalDate(thirdPartyContractDTO.getCancellationDate());
        }

        LocalDate monthStartDate = startCycle;
        LocalDate monthEndDate = endCycle;

        if (i > 0) {
          // dates de traitement du mois
          monthStartDate = treatmentDate.dayOfMonth().withMinimumValue();
          monthEndDate = treatmentDate.dayOfMonth().withMaximumValue();

          // savoir si le mois du contrat est concerné par le mois de traitement
          int month = DateTimeUtils.getNumberOfMonth(monthStartDate, monthEndDate, startValidity, endValidity);
          if (month <= 0) {
            continue;
          }
        }

        BigDecimal price;
        // prix à payer si le contrat occupe toute la durée du cycle
        if (typePiece.getIndex() == GcpConstants.LOYER_COMPTE_GENERAL || typePiece.getIndex() == GcpConstants.LOYER_COMPTE_CHARGE) {
          price = thirdPartyContractDTO.getRentAmount();
        } else {
          price = thirdPartyContractDTO.getExpectedChargeCost();
        }

        double proportionDays = DateTimeUtils.getProportionOfDaysOnCycle(monthStartDate, monthEndDate, startValidity, endValidity);

        price = price.multiply(new BigDecimal(proportionDays));
        price = price.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).abs();

        // insertion des données dans les lignes
        GCPEntryLine entryLine = new GCPEntryLine();
        entryLine.setTypeLigne(AccountantGcpDelegate.getLineType());
        entryLine.setCompte(AccountantGcpDelegate.getAccount(GcpType.ZN, index, thirdPartyContractDTO, null, accountingCodeDTO));
        entryLine.setCodeCleCGS(AccountantGcpDelegate.getKeyCodeGCS(GcpType.ZN, index, null));
        entryLine.setCleComptabilisation(AccountantGcpDelegate.getKeyAccounting(GcpType.ZN, inCycle, index, thirdPartyContractDTO, null, accountingCodeDTO, price));
        entryLine.setMontantDevisePiece(AccountantGcpDelegate.getAmount(price));
        entryLine.setCodeTVA(AccountantGcpDelegate.getValueAddedTaxCode(GcpType.ZN, index));
        entryLine.setDomaineActivite(AccountantGcpDelegate.getActivityDomain(GcpType.ZN, thirdPartyContractDTO, null));
        entryLine.setDateBase(AccountantGcpDelegate.getBaseDate(GcpType.ZN, index, thirdPartyContractDTO, null, paymentCycle, generationDate));
        entryLine.setConditionPaiement(AccountantGcpDelegate.getConditionForPayment(GcpType.ZN, index));
        entryLine.setCentreCout(AccountantGcpDelegate.getCostCenter(GcpType.ZN, index, thirdPartyContractDTO, null));
        entryLine.setZoneAffection(AccountantGcpDelegate.getHeadquartersArea(GcpType.ZN, index, thirdPartyContractDTO, null));
        entryLine.setTexte(AccountantGcpDelegate.getText(GcpType.ZN, treatmentDate, thirdPartyContractDTO, null));

        if (price.compareTo(BigDecimal.ZERO) != 0) {
          gcpEntryLines.add(entryLine);
        }
      }

      if (!entryBlock.getGcpEntryLines().isEmpty()) {
        gcpInterationsEntryBlocks.get(i).add(entryBlock);
      }
    }
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
   * @param ylZnAccountingDocNumberService the ylZnAccountingDocNumberService to set
   */
  public void setYlZnAccountingDocNumberService(IYlZnAccountingDocNumberService ylZnAccountingDocNumberService) {
    this.ylZnAccountingDocNumberService = ylZnAccountingDocNumberService;
  }

  /**
   * @param revisionThirdPartyContractService the revisionThirdPartyContractService to set
   */
  public void setRevisionThirdPartyContractService(IRevisionThirdPartyContractService revisionThirdPartyContractService) {
    this.revisionThirdPartyContractService = revisionThirdPartyContractService;
  }

}
