package com.abita.services.jobs.artesis.impl;

import com.abita.dao.batch.artesis.ArtesisBenefitsBlock;
import com.abita.dao.batch.artesis.ArtesisRetainedSalaryBlock;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.historyamount.IHistoryAmountService;
import com.abita.services.historyamount.exceptions.HistoryAmountServiceException;
import com.abita.services.historycontract.IHistoryContractServiceFacade;
import com.abita.services.historycontract.exceptions.HistoryContractServiceException;
import com.abita.services.jobs.artesis.IArtesisServiceFacade;
import com.abita.services.jobs.artesis.exceptions.ArtesisServiceException;
import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.comparator.ArtesisRetainedSalaryBlockComparator;
import com.abita.util.comparator.FinalArtesisRetainedSalaryBlockComparator;
import com.abita.util.contract.ContractUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.util.signum.SignumUtils;
import com.abita.dto.ContractDTO;
import com.abita.dto.HistoryAmountDTO;
import com.abita.dto.HistoryContractDTO;
import com.abita.services.jobs.artesis.constants.ArtesisConstants;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe d’implémentation des services façades des jobs pour Artesis
 */
public class ArtesisServiceFacadeImpl implements IArtesisServiceFacade {

  /** Service des contrats occupants */
  private IContractServiceFacade contractServiceFacade;

  /** Service d’historisation des contrats occupants */
  private IHistoryContractServiceFacade historyContractServiceFacade;

  /** Service d'historisation des montants */
  private IHistoryAmountService historyAmountService;

  /** Formatteur de date sdfyyyyMM */
  private DateTimeFormatter sdfyyyyMM = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_YYYYMM);

  /** Formatteur de date sdfyyyyMMdd */
  private DateTimeFormatter sdfyyyyMMdd = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_YYYYMMDD);

  @Override
  public List<ArtesisRetainedSalaryBlock> getRetainedSalaryBlocks(LocalDate generationDate) throws ArtesisServiceException {
    List<ArtesisRetainedSalaryBlock> finalArtesisRetainedSalaryBlocks = new ArrayList<ArtesisRetainedSalaryBlock>();

    // ON RECUPERE LES CONTRATS QUI ONT UNE RETNUE SUR SALAIRE CLASSER PAR LEUR CATEGORIE DE CODE DE PAIE
    Map<String, List<ArtesisRetainedSalaryBlock>> artesisRetainedSalaryBlockByPayCode = getCurrentRetainedSalaryBlocks(generationDate);
    // ON RECUPERE LES CONTRATS QUI ONT BESOIN D'ETRE REGULARISER CLASSER PAR LEUR CATEGORIE DE CODE DE PAIE
    Map<String, List<ArtesisRetainedSalaryBlock>> artesisRetainedSalaryRegularizeBlockByPayCode = getRegularizationRetainedSalaryBlocks(generationDate);

    for (Map.Entry<String, List<ArtesisRetainedSalaryBlock>> artesisRetainedSalaryBlocksEntry : artesisRetainedSalaryBlockByPayCode.entrySet()) {
      List<ArtesisRetainedSalaryBlock> artesisRetainedSalaryBlocks = artesisRetainedSalaryBlocksEntry.getValue();

      // ON AJOUTE, A LA LISTE DES CONTRATS POUR CETTE CATEGORIE DE CODE DE PAIE, LES CONTRATS QUI ONT BESOIN D'ETRE REGULARISER
      artesisRetainedSalaryBlocks.addAll(artesisRetainedSalaryRegularizeBlockByPayCode.get(artesisRetainedSalaryBlocksEntry.getKey()));

      // ON TRI LA LISTE PAR LA REFERENCE DES OCCUPANTS AFIN DE POUVOIR MERGER LES DIFFERENTS CONTRATS D'UN OCCUPANT
      Collections.sort(artesisRetainedSalaryBlocks, new ArtesisRetainedSalaryBlockComparator());

      ArtesisRetainedSalaryBlock finalArtesisRetainedSalaryBlock = null;
      String activeReference = "";
      for (ArtesisRetainedSalaryBlock artesisRetainedSalaryBlock : artesisRetainedSalaryBlocks) {
        if (!activeReference.equals(artesisRetainedSalaryBlock.getTenantReference())) {
          finalArtesisRetainedSalaryBlock = new ArtesisRetainedSalaryBlock();
          finalArtesisRetainedSalaryBlocks.add(finalArtesisRetainedSalaryBlock);

          finalArtesisRetainedSalaryBlock.setTenantReference(artesisRetainedSalaryBlock.getTenantReference());
          finalArtesisRetainedSalaryBlock.setOriginApplication(ArtesisConstants.ORIGIN_APPLICATION_1_CODE);
          finalArtesisRetainedSalaryBlock.setPayArea("");
          finalArtesisRetainedSalaryBlock.setPayPeriod(sdfyyyyMM.print(generationDate.plusMonths(1)));
          finalArtesisRetainedSalaryBlock.setNni(StringUtils.left(
            StringUtils.leftPad(artesisRetainedSalaryBlock.getTenantReference(), ArtesisConstants.REFERENCE_TENANT_LENGTH, " "), ArtesisConstants.REFERENCE_TENANT_LENGTH));
          finalArtesisRetainedSalaryBlock.setRecordType(ArtesisConstants.RECORD_TYPE);
          finalArtesisRetainedSalaryBlock.setFreeZone("");
          finalArtesisRetainedSalaryBlock.setPayCatgoryCode(artesisRetainedSalaryBlock.getPayCatgoryCode());

          finalArtesisRetainedSalaryBlock.setEffectDate(sdfyyyyMMdd.print(generationDate.dayOfMonth().withMaximumValue()));
          finalArtesisRetainedSalaryBlock.setFreeZone2("");
        }

        if (artesisRetainedSalaryBlock.getRealAmount() != null) {
          if (activeReference.equals(artesisRetainedSalaryBlock.getTenantReference())) {
            finalArtesisRetainedSalaryBlock.setRealAmount(finalArtesisRetainedSalaryBlock.getRealAmount().add(artesisRetainedSalaryBlock.getRealAmount()));
          } else {
            finalArtesisRetainedSalaryBlock.setRealAmount(artesisRetainedSalaryBlock.getRealAmount());
          }

          finalArtesisRetainedSalaryBlock.setAmount(StringUtils.leftPad(finalArtesisRetainedSalaryBlock.getRealAmount().abs().movePointRight(BigDecimalUtils.SCALE_PRICE)
            .toPlainString(), ArtesisConstants.PRICE_LENGTH_ARTESIS, "0"));

          // Si on est sur une ligne de remboursement alors le signe est le même que celui du montant sinon il est inversé
          if (Integer.toString(ArtesisConstants.PAY_CODE_REIMBURSEMENT).equals(artesisRetainedSalaryBlock.getPayCatgoryCode())) {
            if (finalArtesisRetainedSalaryBlock.getRealAmount().compareTo(BigDecimal.ZERO) < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
              finalArtesisRetainedSalaryBlock.setSenseCode(SignumUtils.NEGATIVE_SIGN);
            } else {
              finalArtesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
            }
          } else {
            if (finalArtesisRetainedSalaryBlock.getRealAmount().compareTo(BigDecimal.ZERO) < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
              finalArtesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
            } else {
              finalArtesisRetainedSalaryBlock.setSenseCode(SignumUtils.NEGATIVE_SIGN);
            }
          }
        } else {
          finalArtesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
          finalArtesisRetainedSalaryBlock.setAmount("");
          finalArtesisRetainedSalaryBlock.setRealAmount(new BigDecimal(0));
        }

        activeReference = artesisRetainedSalaryBlock.getTenantReference();
      }
    }

    // ON TRI LA LISTE FINALE PAR LEUR REFERENCE DES OCCUPANTS PUIS PAR LEUR CATEGORIE DE CODE PAIE
    Collections.sort(finalArtesisRetainedSalaryBlocks, new FinalArtesisRetainedSalaryBlockComparator());

    List<ArtesisRetainedSalaryBlock> finalArtesisRetainedSalaryBlocksWithoutNil = new ArrayList<ArtesisRetainedSalaryBlock>();

    // On regarde le montant final de chaque ligne et on ajoute les lignes avec un montant différent zero à la liste de ligne qui sera dans
    // le fichier
    for (ArtesisRetainedSalaryBlock finalArtesisRetainedSalaryBlock : finalArtesisRetainedSalaryBlocks) {
      if (!BigDecimal.ZERO.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN).equals(
        finalArtesisRetainedSalaryBlock.getRealAmount().setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN))) {
        finalArtesisRetainedSalaryBlocksWithoutNil.add(finalArtesisRetainedSalaryBlock);
      }
    }

    return finalArtesisRetainedSalaryBlocksWithoutNil;
  }

  /**
   * Génere les lignes de générations pour le fichier ARTESIS de retenue sur paie pour les données en cours
   * @param generationDate la date de génération
   * @return les lignes de génération
   * @throws ArtesisServiceException une ArtesisServiceException
   */
  private Map<String, List<ArtesisRetainedSalaryBlock>> getCurrentRetainedSalaryBlocks(LocalDate generationDate) throws ArtesisServiceException {

    Map<String, List<ArtesisRetainedSalaryBlock>> artesisRetainedSalaryBlockByPayCode = new HashMap<String, List<ArtesisRetainedSalaryBlock>>();
    artesisRetainedSalaryBlockByPayCode.put(ArtesisConstants.PAY_CODE_NET_RENT.toString(), new ArrayList<ArtesisRetainedSalaryBlock>());
    artesisRetainedSalaryBlockByPayCode.put(ArtesisConstants.PAY_CODE_RENTAL_CHARGES.toString(), new ArrayList<ArtesisRetainedSalaryBlock>());
    artesisRetainedSalaryBlockByPayCode.put(ArtesisConstants.PAY_CODE_REIMBURSEMENT.toString(), new ArrayList<ArtesisRetainedSalaryBlock>());

    try {
      List<ContractDTO> contractDTOs = contractServiceFacade.findActiveContractsWithRetainedSalary();
      ArtesisRetainedSalaryBlock artesisRetainedSalaryBlock = null;
      String activeReference = "";

      for (ContractDTO contractDTO : contractDTOs) {
        for (int payCode = ArtesisConstants.PAY_CODE_NET_RENT; payCode <= ArtesisConstants.PAY_CODE_REIMBURSEMENT; payCode++) {
          List<ArtesisRetainedSalaryBlock> artesisRetainedSalaryBlocks = artesisRetainedSalaryBlockByPayCode.get(Integer.toString(payCode));
          if (!artesisRetainedSalaryBlocks.isEmpty()) {
            artesisRetainedSalaryBlock = artesisRetainedSalaryBlocks.get(artesisRetainedSalaryBlocks.size() - 1);
          }
          if (!activeReference.equals(contractDTO.getTenant().getReference())) {
            artesisRetainedSalaryBlock = new ArtesisRetainedSalaryBlock();
            artesisRetainedSalaryBlocks.add(artesisRetainedSalaryBlock);

            artesisRetainedSalaryBlock.setTenantReference(contractDTO.getTenant().getReference());
            artesisRetainedSalaryBlock.setOriginApplication(ArtesisConstants.ORIGIN_APPLICATION_1_CODE);
            artesisRetainedSalaryBlock.setPayArea("");
            artesisRetainedSalaryBlock.setPayPeriod(sdfyyyyMM.print(generationDate.plusMonths(1)));
            artesisRetainedSalaryBlock.setNni(StringUtils.left(StringUtils.leftPad(contractDTO.getTenant().getReference(), ArtesisConstants.REFERENCE_TENANT_LENGTH, " "),
              ArtesisConstants.REFERENCE_TENANT_LENGTH));
            artesisRetainedSalaryBlock.setRecordType(ArtesisConstants.RECORD_TYPE);
            artesisRetainedSalaryBlock.setFreeZone("");
            artesisRetainedSalaryBlock.setPayCatgoryCode(Integer.toString(payCode));

            artesisRetainedSalaryBlock.setEffectDate(sdfyyyyMMdd.print(generationDate.dayOfMonth().withMaximumValue()));
            artesisRetainedSalaryBlock.setFreeZone2("");
          }
          if (ArtesisConstants.PAY_CODE_NET_RENT.equals(payCode)) {
            if (contractDTO.getWithdrawnRent() != null) {
              // Calcul du montant de retenue sur paie (Loyer prélevé)
              BigDecimal withdrawnRent = ContractUtils.retainedSalaryNetRentAmount(contractDTO, generationDate);

              if (activeReference.equals(contractDTO.getTenant().getReference())) {
                artesisRetainedSalaryBlock.setRealAmount(artesisRetainedSalaryBlock.getRealAmount().add(withdrawnRent));
              } else {
                artesisRetainedSalaryBlock.setRealAmount(withdrawnRent);
              }

              artesisRetainedSalaryBlock.setAmount(StringUtils.leftPad(
                artesisRetainedSalaryBlock.getRealAmount().abs().movePointRight(BigDecimalUtils.SCALE_PRICE).toPlainString(), ArtesisConstants.PRICE_LENGTH_ARTESIS, "0"));

              if (artesisRetainedSalaryBlock.getRealAmount().compareTo(BigDecimal.ZERO) < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
                artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
              } else {
                artesisRetainedSalaryBlock.setSenseCode(SignumUtils.NEGATIVE_SIGN);
              }
            } else {
              artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
              artesisRetainedSalaryBlock.setAmount("");
              artesisRetainedSalaryBlock.setRealAmount(new BigDecimal(0));
            }
          }
          if (ArtesisConstants.PAY_CODE_RENTAL_CHARGES.equals(payCode)) {
            if (contractDTO.getWithdrawnRent() != null) {
              // Calcul du montant de retenue sur paie (Loyer prélevé)
              BigDecimal withdrawnRent = ContractUtils.retainedSalaryRentalChargesAmount(contractDTO, generationDate);

              if (activeReference.equals(contractDTO.getTenant().getReference())) {
                artesisRetainedSalaryBlock.setRealAmount(artesisRetainedSalaryBlock.getRealAmount().add(withdrawnRent));
              } else {
                artesisRetainedSalaryBlock.setRealAmount(withdrawnRent);
              }

              artesisRetainedSalaryBlock.setAmount(StringUtils.leftPad(
                artesisRetainedSalaryBlock.getRealAmount().abs().movePointRight(BigDecimalUtils.SCALE_PRICE).toPlainString(), ArtesisConstants.PRICE_LENGTH_ARTESIS, "0"));

              if (artesisRetainedSalaryBlock.getRealAmount().compareTo(BigDecimal.ZERO) < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
                artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
              } else {
                artesisRetainedSalaryBlock.setSenseCode(SignumUtils.NEGATIVE_SIGN);
              }
            } else {
              artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
              artesisRetainedSalaryBlock.setAmount("");
              artesisRetainedSalaryBlock.setRealAmount(new BigDecimal(0));
            }
          }
          if (ArtesisConstants.PAY_CODE_REIMBURSEMENT.equals(payCode)) {
            if (contractDTO.getWithdrawnRent() != null) {
              // Calcul du montant de retenue sur paie (Loyer prélevé)
              BigDecimal withdrawnRent = ContractUtils.retainedSalaryReimbursementAmount(contractDTO, generationDate);

              if (activeReference.equals(contractDTO.getTenant().getReference())) {
                artesisRetainedSalaryBlock.setRealAmount(artesisRetainedSalaryBlock.getRealAmount().add(withdrawnRent));
              } else {
                artesisRetainedSalaryBlock.setRealAmount(withdrawnRent);
              }

              artesisRetainedSalaryBlock.setAmount(StringUtils.leftPad(
                artesisRetainedSalaryBlock.getRealAmount().abs().movePointRight(BigDecimalUtils.SCALE_PRICE).toPlainString(), ArtesisConstants.PRICE_LENGTH_ARTESIS, "0"));

              if (artesisRetainedSalaryBlock.getRealAmount().compareTo(BigDecimal.ZERO) < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
                artesisRetainedSalaryBlock.setSenseCode(SignumUtils.NEGATIVE_SIGN);
              } else {
                artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
              }
            } else {
              artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
              artesisRetainedSalaryBlock.setAmount("");
              artesisRetainedSalaryBlock.setRealAmount(new BigDecimal(0));
            }

            activeReference = contractDTO.getTenant().getReference();
          }
        }
      }
    } catch (ContractServiceFacadeException e) {
      throw new ArtesisServiceException(e.getMessage(), e);
    }

    return artesisRetainedSalaryBlockByPayCode;
  }

  /**
   * Génére les lignes pour la retenue sur paie pour les contrats historisés à régularisé
   * @param generationDate la date de génération
   * @return les lignes pour la retenue sur paie
   * @throws ArtesisServiceException une ArtesisServiceException
   */
  private Map<String, List<ArtesisRetainedSalaryBlock>> getRegularizationRetainedSalaryBlocks(LocalDate generationDate) throws ArtesisServiceException {

    Map<String, List<ArtesisRetainedSalaryBlock>> artesisRetainedSalaryBlockByPayCode = new HashMap<String, List<ArtesisRetainedSalaryBlock>>();
    artesisRetainedSalaryBlockByPayCode.put(ArtesisConstants.PAY_CODE_NET_RENT.toString(), new ArrayList<ArtesisRetainedSalaryBlock>());
    artesisRetainedSalaryBlockByPayCode.put(ArtesisConstants.PAY_CODE_RENTAL_CHARGES.toString(), new ArrayList<ArtesisRetainedSalaryBlock>());
    artesisRetainedSalaryBlockByPayCode.put(ArtesisConstants.PAY_CODE_REIMBURSEMENT.toString(), new ArrayList<ArtesisRetainedSalaryBlock>());

    try {
      List<ContractDTO> contractDTOs = contractServiceFacade.findContractsToRegularizeWithRetainedSalary();

      ArtesisRetainedSalaryBlock artesisRetainedSalaryBlock = null;
      String activeReference = "";

      for (ContractDTO contractDTO : contractDTOs) {
        int numberOfRetroactivityMonths = contractDTO.getRetroactivitysMonths();
        int absoluteValueOfNumberOfRetroactivityMonths = Math.abs(numberOfRetroactivityMonths);

        for (int i = 0; i < absoluteValueOfNumberOfRetroactivityMonths; i++) {
          LocalDate regularizationDate = new LocalDate(generationDate);
          regularizationDate = regularizationDate.minusMonths(i + 1);
          HistoryContractDTO historyContractDTO = historyContractServiceFacade.get(contractDTO.getId(), regularizationDate.getMonthOfYear(), regularizationDate.getYear());

          if (historyContractDTO != null) {
            Map<String, BigDecimal> amountsByLabel = new HashMap<String, BigDecimal>();

            for (int payCode = ArtesisConstants.PAY_CODE_NET_RENT; payCode <= ArtesisConstants.PAY_CODE_REIMBURSEMENT; payCode++) {
              List<ArtesisRetainedSalaryBlock> artesisRetainedSalaryBlocks = artesisRetainedSalaryBlockByPayCode.get(Integer.toString(payCode));
              if (!artesisRetainedSalaryBlocks.isEmpty()) {
                artesisRetainedSalaryBlock = artesisRetainedSalaryBlocks.get(artesisRetainedSalaryBlocks.size() - 1);
              }

              if (!activeReference.equals(historyContractDTO.getTenant().getReference())) {
                artesisRetainedSalaryBlock = new ArtesisRetainedSalaryBlock();
                artesisRetainedSalaryBlocks.add(artesisRetainedSalaryBlock);

                artesisRetainedSalaryBlock.setTenantReference(historyContractDTO.getTenant().getReference());
                artesisRetainedSalaryBlock.setOriginApplication(ArtesisConstants.ORIGIN_APPLICATION_1_CODE);
                artesisRetainedSalaryBlock.setPayArea("");
                artesisRetainedSalaryBlock.setPayPeriod(sdfyyyyMM.print(generationDate.plusMonths(1)));
                artesisRetainedSalaryBlock.setNni(StringUtils.left(
                  StringUtils.leftPad(historyContractDTO.getTenant().getReference(), ArtesisConstants.REFERENCE_TENANT_LENGTH, " "), ArtesisConstants.REFERENCE_TENANT_LENGTH));
                artesisRetainedSalaryBlock.setRecordType(ArtesisConstants.RECORD_TYPE);
                artesisRetainedSalaryBlock.setFreeZone("");
                artesisRetainedSalaryBlock.setPayCatgoryCode(Integer.toString(payCode));

                artesisRetainedSalaryBlock.setEffectDate(sdfyyyyMMdd.print(generationDate.dayOfMonth().withMaximumValue()));
                artesisRetainedSalaryBlock.setFreeZone2("");
              }
              if (ArtesisConstants.PAY_CODE_NET_RENT.equals(payCode)) {
                if (historyContractDTO.getWithdrawnRent() != null) {
                  // Calcul du montant de retenue sur paie (Loyer prélevé)
                  BigDecimal withdrawnRent = ContractUtils.retainedSalaryNetRentAmountForRegularization(historyContractDTO, contractDTO.getStartValidityDate(),
                    contractDTO.getEndValidityDate(), regularizationDate);
                  if (numberOfRetroactivityMonths < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
                    withdrawnRent = withdrawnRent.subtract(ContractUtils.retainedSalaryNetRentAmountForRegularization(historyContractDTO, contractDTO.getStartValidityDate(),
                      historyContractDTO.getEndValidityDate(), regularizationDate));
                  }

                  // On l'ajoute a la map d'historisation du montant de la retenue sur paie (Loyer prélevé)
                  amountsByLabel.put(ArtesisConstants.RETAINED_SALARY_NET_RENT_LABEL, withdrawnRent);

                  // On additionne les montants si l'occupant est le meme que pour le précedent contrat
                  if (activeReference.equals(historyContractDTO.getTenant().getReference())) {
                    artesisRetainedSalaryBlock.setRealAmount(artesisRetainedSalaryBlock.getRealAmount().add(withdrawnRent));
                  } else {
                    artesisRetainedSalaryBlock.setRealAmount(withdrawnRent);
                  }

                  artesisRetainedSalaryBlock.setAmount(StringUtils.leftPad(artesisRetainedSalaryBlock.getRealAmount().abs().movePointRight(BigDecimalUtils.SCALE_PRICE)
                    .toPlainString(), ArtesisConstants.PRICE_LENGTH_ARTESIS, "0"));

                  if (artesisRetainedSalaryBlock.getRealAmount().compareTo(BigDecimal.ZERO) < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
                    artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
                  } else {
                    artesisRetainedSalaryBlock.setSenseCode(SignumUtils.NEGATIVE_SIGN);
                  }
                } else {
                  artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
                  artesisRetainedSalaryBlock.setAmount("");
                  artesisRetainedSalaryBlock.setRealAmount(new BigDecimal(0));
                }
              }
              if (ArtesisConstants.PAY_CODE_RENTAL_CHARGES.equals(payCode)) {
                if (historyContractDTO.getExpectedChargeCost() != null) {
                  // Calcul du montant de retenue sur paie (Charge prévisionelle)
                  BigDecimal expectedChargeCost = ContractUtils.retainedSalaryRentalChargesAmountForRegularization(historyContractDTO, contractDTO.getStartValidityDate(),
                    contractDTO.getEndValidityDate(), regularizationDate);
                  if (numberOfRetroactivityMonths < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
                    expectedChargeCost = expectedChargeCost.subtract(ContractUtils.retainedSalaryRentalChargesAmountForRegularization(historyContractDTO,
                      contractDTO.getStartValidityDate(), historyContractDTO.getEndValidityDate(), regularizationDate));
                  }

                  // On l'ajoute a la map d'historisation du montant de la retenue sur paie (Charge prévisionelle)
                  amountsByLabel.put(ArtesisConstants.RETAINED_SALARY_RENTAL_CHARGES_LABEL, expectedChargeCost);

                  // On additionne les montants si l'occupant est le meme que pour le précedent contrat
                  if (activeReference.equals(historyContractDTO.getTenant().getReference())) {
                    artesisRetainedSalaryBlock.setRealAmount(artesisRetainedSalaryBlock.getRealAmount().add(expectedChargeCost));
                  } else {
                    artesisRetainedSalaryBlock.setRealAmount(expectedChargeCost);
                  }

                  artesisRetainedSalaryBlock.setAmount(StringUtils.leftPad(artesisRetainedSalaryBlock.getRealAmount().abs().movePointRight(BigDecimalUtils.SCALE_PRICE)
                    .toPlainString(), ArtesisConstants.PRICE_LENGTH_ARTESIS, "0"));

                  if (artesisRetainedSalaryBlock.getRealAmount().compareTo(BigDecimal.ZERO) < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
                    artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
                  } else {
                    artesisRetainedSalaryBlock.setSenseCode(SignumUtils.NEGATIVE_SIGN);
                  }
                } else {
                  artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
                  artesisRetainedSalaryBlock.setAmount("");
                  artesisRetainedSalaryBlock.setRealAmount(new BigDecimal(0));
                }
              }
              if (ArtesisConstants.PAY_CODE_REIMBURSEMENT.equals(payCode)) {
                artesisRetainedSalaryBlock.setSenseCode(SignumUtils.POSITIVE_SIGN);
                artesisRetainedSalaryBlock.setAmount("0000000");
                artesisRetainedSalaryBlock.setRealAmount(new BigDecimal(0));

                if (!amountsByLabel.isEmpty()) {
                  // Historisation des montants de retenue sur salaire pour chaque contrat avec des valeurs retroactives
                  HistoryAmountDTO historyAmountDTO = historiseAmount(historyContractDTO, amountsByLabel, ArtesisConstants.TYPE_RETAINED_SALARY, generationDate, regularizationDate);
                  historyAmountService.create(historyAmountDTO);
                }

                activeReference = historyContractDTO.getTenant().getReference();
              }
            }
          }
        }
      }
    } catch (ContractServiceFacadeException e) {
      throw new ArtesisServiceException(e.getMessage(), e);
    } catch (HistoryContractServiceException e) {
      throw new ArtesisServiceException(e.getMessage(), e);
    } catch (HistoryAmountServiceException e) {
      throw new ArtesisServiceException(e.getMessage(), e);
    }

    return artesisRetainedSalaryBlockByPayCode;
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
   * Génere les lignes de générations pour le fichier ARTESIS d'avantage en nature pour les données en cours
   * @param generationDate la date de génération
   * @return les lignes de génération
   * @throws ArtesisServiceException une ArtesisServiceException
   */
  @Override
  public List<ArtesisBenefitsBlock> getBenefitsBlocks(LocalDate generationDate) throws ArtesisServiceException {
    List<ArtesisBenefitsBlock> artesisBenefitsBlocks = getCurrentBenefitsBlocks(generationDate);
    artesisBenefitsBlocks.addAll(getRegularizationBenefitsBlocks(generationDate));
    return artesisBenefitsBlocks;
  }

  /**
   * Génere les lignes de générations pour le fichier ARTESIS d'avantage en nature pour les données en cours
   * @param generationDate la date de génération
   * @return les lignes de génération
   * @throws ArtesisServiceException une ArtesisServiceException
   */
  private List<ArtesisBenefitsBlock> getCurrentBenefitsBlocks(LocalDate generationDate) throws ArtesisServiceException {

    List<ArtesisBenefitsBlock> artesisBenefitsBlocks = new ArrayList<ArtesisBenefitsBlock>();

    try {
      List<ContractDTO> contractDTOs = contractServiceFacade.findContractsInProgressOfSalariedTenant();

      ArtesisBenefitsBlock artesisBenefitsBlock = null;

      for (ContractDTO contractDTO : contractDTOs) {
        artesisBenefitsBlock = new ArtesisBenefitsBlock();
        artesisBenefitsBlocks.add(artesisBenefitsBlock);

        artesisBenefitsBlock.setOriginApplication(ArtesisConstants.ORIGIN_APPLICATION_2_CODE);
        // NNI + code période
        artesisBenefitsBlock.setNni(contractDTO.getTenant().getReference());
        artesisBenefitsBlock.setManagementCompany(ArtesisConstants.MANAGEMENT_COMPANY_CODE);
        artesisBenefitsBlock.setContractReference(String.valueOf(contractDTO.getContractReference()).replace(".", ""));
        artesisBenefitsBlock.setStartDate(DateTimeUtils.formatDateTimeToStringWithPattern(DateTimeUtils.getStartValidityDateFromDate(contractDTO.getStartValidityDate()),
          DateTimeUtils.PATTERN_DATE_YYYYMMDD_DASH));
        artesisBenefitsBlock.setEndDate(DateTimeUtils.formatDateTimeToStringWithPattern(DateTimeUtils.getEndValidityDateFromDate(contractDTO.getEndValidityDate()),
          DateTimeUtils.PATTERN_DATE_YYYYMMDD_DASH));
        artesisBenefitsBlock.setPayPeriod(DateTimeUtils.formatDateTimeToStringWithPattern(DateTimeUtils.nextMonthOfActuelDay(), DateTimeUtils.PATTERN_DATE_YYYYMM_DASH));
        artesisBenefitsBlock.setStartValidityDate(DateTimeUtils.formatDateTimeToStringWithPattern(new DateTime(contractDTO.getStartValidityDate()),
          DateTimeUtils.PATTERN_DATE_YYYYMMDD_DASH));
        if (contractDTO.getEndValidityDate() != null) {
          artesisBenefitsBlock.setEndValidityDate(DateTimeUtils.formatDateTimeToStringWithPattern(new DateTime(contractDTO.getEndValidityDate()),
            DateTimeUtils.PATTERN_DATE_YYYYMMDD_DASH));
        } else {
          artesisBenefitsBlock.setEndValidityDate("");
        }
        artesisBenefitsBlock.setHousingIndex(contractDTO.getRentTypology().getHousingIndex());
        if (null != contractDTO.getHousing().getHousingNature()) {
          artesisBenefitsBlock.setHousingNature(contractDTO.getHousing().getHousingNature().getNatureOfLocal());
        } else {
          artesisBenefitsBlock.setHousingNature("");
        }
        artesisBenefitsBlock.setRoomCount(StringUtils.leftPad(contractDTO.getHousing().getRoomCount().toString(), ArtesisConstants.NUMBER_ZERO_ON_EMPTY_ROOM_COUNT_FIELD, "0"));
        artesisBenefitsBlock.setQuittancementDate(DateTimeUtils.formatDateTimeToStringWithPattern(DateTime.now().dayOfMonth().withMaximumValue(),
          DateTimeUtils.PATTERN_DATE_YYYYMMDD_DASH));
        artesisBenefitsBlock.setRealEstateRentalValueYear(String.valueOf(DateTime.now().getYear()));
        artesisBenefitsBlock.setRealEstateRentalValueOrigin(ArtesisConstants.REAL_ESTATE_RENTAL_VALUE_ORIGIN);
        if (null != contractDTO.getRealEstateRentalValue()) {
          artesisBenefitsBlock.setRealEstateRentalValueSign(SignumUtils.getSignumByValue(contractDTO.getRealEstateRentalValue().signum()));
          BigDecimal realEstateRentalValue = contractDTO.getRealEstateRentalValue().divide(new BigDecimal(DateTimeUtils.MONTHS_IN_A_YEAR), BigDecimalUtils.SCALE_PRICE,
            BigDecimal.ROUND_HALF_EVEN);
          realEstateRentalValue = realEstateRentalValue.multiply(ContractUtils.getProportionsDays(contractDTO, generationDate));
          realEstateRentalValue = realEstateRentalValue.abs().setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
          artesisBenefitsBlock.setRealEstateRentalValueAmount(StringUtils.leftPad(realEstateRentalValue.movePointRight(BigDecimalUtils.SCALE_PRICE).toPlainString(),
            ArtesisConstants.NUMBER_ZERO_ON_EMPTY_MONETARY_FIELD, "0"));
        } else {
          artesisBenefitsBlock.setRealEstateRentalValueSign(SignumUtils.POSITIVE_SIGN);
          artesisBenefitsBlock.setRealEstateRentalValueAmount("");
        }
        if (null != contractDTO.getBenefit()) {
          artesisBenefitsBlock.setBenefitsSign(SignumUtils.getSignumByValue(contractDTO.getBenefit().signum()));
          BigDecimal benefit = contractDTO.getBenefit();
          benefit = benefit.multiply(ContractUtils.getProportionsDays(contractDTO, generationDate));
          benefit = benefit.abs().setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
          artesisBenefitsBlock.setBenefitsAmount(StringUtils.leftPad(benefit.movePointRight(BigDecimalUtils.SCALE_PRICE).toPlainString(),
            ArtesisConstants.NUMBER_ZERO_ON_EMPTY_MONETARY_FIELD, "0"));
        } else {
          artesisBenefitsBlock.setBenefitsSign(SignumUtils.POSITIVE_SIGN);
          artesisBenefitsBlock.setBenefitsAmount("");
        }
        if (null != contractDTO.getWithdrawnRent()) {
          BigDecimal mensualRent = ContractUtils.netMensualRentBenefit(contractDTO, new LocalDate());
          artesisBenefitsBlock.setWithdrawnRentSign(SignumUtils.getSignumByValue(mensualRent.signum()));
          mensualRent = mensualRent.abs();
          artesisBenefitsBlock.setWithdrawnRentAmount(StringUtils.leftPad(mensualRent.movePointRight(BigDecimalUtils.SCALE_PRICE).toPlainString(),
            ArtesisConstants.NUMBER_ZERO_ON_EMPTY_MONETARY_FIELD, "0"));
        } else {
          artesisBenefitsBlock.setWithdrawnRentSign(SignumUtils.POSITIVE_SIGN);
          artesisBenefitsBlock.setWithdrawnRentAmount("");
        }
        artesisBenefitsBlock.setEndOfLine("");

      }
    } catch (ContractServiceFacadeException e) {
      throw new ArtesisServiceException(e.getMessage(), e);
    }

    return artesisBenefitsBlocks;
  }

  /**
   * Génere les lignes de générations pour le fichier ARTESIS d'avantage en nature pour les données en cours
   * @param generationDate la date de génération
   * @return les lignes de génération
   * @throws ArtesisServiceException une ArtesisServiceException
   */
  private List<ArtesisBenefitsBlock> getRegularizationBenefitsBlocks(LocalDate generationDate) throws ArtesisServiceException {

    List<ArtesisBenefitsBlock> artesisBenefitsBlocks = new ArrayList<ArtesisBenefitsBlock>();

    try {
      List<ContractDTO> contractDTOs = contractServiceFacade.findContractsInProgressToRegularizeOfSalariedTenant();

      ArtesisBenefitsBlock artesisBenefitsBlock = null;

      for (ContractDTO contractDTO : contractDTOs) {
        int numberOfRetroactivityMonths = contractDTO.getRetroactivitysMonths();
        int absoluteValueOfNumberOfRetroactivityMonths = Math.abs(numberOfRetroactivityMonths);
        for (int i = 0; i < absoluteValueOfNumberOfRetroactivityMonths; i++) {

          LocalDate regularizationDate = new LocalDate(generationDate);
          regularizationDate = regularizationDate.minusMonths(i + 1);
          YearMonth yearMonth = new YearMonth(regularizationDate);

          HistoryContractDTO historyContractDTO = historyContractServiceFacade.get(contractDTO.getId(), regularizationDate.getMonthOfYear(), regularizationDate.getYear());

          if (historyContractDTO != null
            && (contractDTO.getRetroactivitysMonths() < 0 || contractDTO.getEndValidityDate() == null || !regularizationDate.isAfter(DateTimeUtils.getMaximumTimeOfLastDayOfMonth(
              new LocalDate(contractDTO.getEndValidityDate())).toLocalDate()))) {
            artesisBenefitsBlock = new ArtesisBenefitsBlock();
            artesisBenefitsBlocks.add(artesisBenefitsBlock);

            artesisBenefitsBlock.setOriginApplication(ArtesisConstants.ORIGIN_APPLICATION_2_CODE);
            // NNI + code période
            artesisBenefitsBlock.setNni(historyContractDTO.getTenant().getReference());
            artesisBenefitsBlock.setManagementCompany(ArtesisConstants.MANAGEMENT_COMPANY_CODE);
            artesisBenefitsBlock.setContractReference(String.valueOf(contractDTO.getContractReference()).replace(".", ""));
            artesisBenefitsBlock.setStartDate(DateTimeUtils.formatDateTimeToStringWithPattern(
              DateTimeUtils.getStartValidityDateFromDate(contractDTO.getStartValidityDate(), yearMonth), DateTimeUtils.PATTERN_DATE_YYYYMMDD_DASH));
            artesisBenefitsBlock.setEndDate(DateTimeUtils.formatDateTimeToStringWithPattern(DateTimeUtils.getEndValidityDateFromDate(contractDTO.getEndValidityDate(), yearMonth),
              DateTimeUtils.PATTERN_DATE_YYYYMMDD_DASH));
            artesisBenefitsBlock.setPayPeriod(DateTimeUtils.formatDateTimeToStringWithPattern(DateTimeUtils.nextMonthOfActuelDay(), DateTimeUtils.PATTERN_DATE_YYYYMM_DASH));
            artesisBenefitsBlock.setStartValidityDate(DateTimeUtils.formatDateTimeToStringWithPattern(new DateTime(contractDTO.getStartValidityDate()),
              DateTimeUtils.PATTERN_DATE_YYYYMMDD_DASH));
            if (contractDTO.getEndValidityDate() != null) {
              artesisBenefitsBlock.setEndValidityDate(DateTimeUtils.formatDateTimeToStringWithPattern(new DateTime(contractDTO.getEndValidityDate()),
                DateTimeUtils.PATTERN_DATE_YYYYMMDD_DASH));
            } else {
              artesisBenefitsBlock.setEndValidityDate("");
            }
            artesisBenefitsBlock.setHousingIndex(historyContractDTO.getRentTypologyHousingIndex());
            if (null != historyContractDTO.getHousing().getNatureOfLocal()) {
              artesisBenefitsBlock.setHousingNature(historyContractDTO.getHousing().getNatureOfLocal());
            } else {
              artesisBenefitsBlock.setHousingNature("");
            }
            artesisBenefitsBlock.setRoomCount(StringUtils.leftPad(historyContractDTO.getHousing().getRoomCount().toString(),
              ArtesisConstants.NUMBER_ZERO_ON_EMPTY_ROOM_COUNT_FIELD, "0"));
            artesisBenefitsBlock.setQuittancementDate(DateTimeUtils.formatDateTimeToStringWithPattern(DateTime.now().dayOfMonth().withMaximumValue(),
              DateTimeUtils.PATTERN_DATE_YYYYMMDD_DASH));
            artesisBenefitsBlock.setRealEstateRentalValueYear(String.valueOf(regularizationDate.getYear()));
            artesisBenefitsBlock.setRealEstateRentalValueOrigin(ArtesisConstants.REAL_ESTATE_RENTAL_VALUE_ORIGIN);

            // MAP POUR L'HISTORISATION DES MONTANTS
            Map<String, BigDecimal> amountsByLabel = new HashMap<String, BigDecimal>();

            if (null != historyContractDTO.getRealEstateRentalValue()) {
              // On divise la VLF par 12
              BigDecimal realEstateRentalValue = historyContractDTO.getRealEstateRentalValue().divide(new BigDecimal(DateTimeUtils.MONTHS_IN_A_YEAR), BigDecimalUtils.SCALE_PRICE,
                BigDecimal.ROUND_HALF_EVEN);
              // On fait le prorata
              realEstateRentalValue = realEstateRentalValue.multiply(ContractUtils.getProportionsDays(contractDTO.getStartValidityDate(), contractDTO.getEndValidityDate(),
                regularizationDate));
              // On arrondie
              realEstateRentalValue = realEstateRentalValue.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

              if (numberOfRetroactivityMonths < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
                // On divise la VLF que l'on a payé par 12
                BigDecimal realEstateRentalValueInModif = historyContractDTO.getRealEstateRentalValue().divide(new BigDecimal(DateTimeUtils.MONTHS_IN_A_YEAR),
                  BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
                // On fait le prorata
                realEstateRentalValueInModif = realEstateRentalValueInModif.multiply(ContractUtils.getProportionsDays(contractDTO.getStartValidityDate(),
                  historyContractDTO.getEndValidityDate(), regularizationDate));
                // On arrondie
                realEstateRentalValueInModif = realEstateRentalValueInModif.abs().setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
                // On soustrait le montant que l'on avait payé
                realEstateRentalValue = realEstateRentalValue.subtract(realEstateRentalValueInModif);
              }
              // On ajoute le montant à la liste à historiser
              amountsByLabel.put(ArtesisConstants.BENEFITS_REAL_ESTATE_RENTAL_VALUE_LABEL, realEstateRentalValue);

              artesisBenefitsBlock.setRealEstateRentalValueSign(SignumUtils.getSignumByValue(realEstateRentalValue.signum()));
              // on prend la valeur absolue
              realEstateRentalValue = realEstateRentalValue.abs();

              artesisBenefitsBlock.setRealEstateRentalValueAmount(StringUtils.leftPad(realEstateRentalValue.movePointRight(BigDecimalUtils.SCALE_PRICE).toPlainString(),
                ArtesisConstants.NUMBER_ZERO_ON_EMPTY_MONETARY_FIELD, "0"));

            } else {
              artesisBenefitsBlock.setRealEstateRentalValueSign(SignumUtils.POSITIVE_SIGN);
              artesisBenefitsBlock.setRealEstateRentalValueAmount("");
            }
            if (null != historyContractDTO.getBenefit()) {
              // On récupere la valeur d'avantages en nature
              BigDecimal benefit = historyContractDTO.getBenefit();
              // On fait le prorata
              benefit = benefit.multiply(ContractUtils.getProportionsDays(contractDTO.getStartValidityDate(), contractDTO.getEndValidityDate(), regularizationDate));
              // On arrondie
              benefit = benefit.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
              if (numberOfRetroactivityMonths < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
                // On récupere la valeur d'avantages en nature que l'on a payé
                BigDecimal benefitInModificaion = historyContractDTO.getBenefit();
                // On fait le prorata
                benefitInModificaion = benefitInModificaion.multiply(ContractUtils.getProportionsDays(contractDTO.getStartValidityDate(), historyContractDTO.getEndValidityDate(),
                  regularizationDate));
                // On arrondie
                benefitInModificaion = benefitInModificaion.abs().setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
                // On soustrait le montant que l'on avait payé
                benefit = benefit.subtract(benefitInModificaion);
              }
              // On ajoute le montant à la liste à historiser
              amountsByLabel.put(ArtesisConstants.BENEFITS_AMOUNT_LABEL, benefit);

              artesisBenefitsBlock.setBenefitsSign(SignumUtils.getSignumByValue(benefit.signum()));

              // On prend la valeur absolue
              benefit = benefit.abs();

              artesisBenefitsBlock.setBenefitsAmount(StringUtils.leftPad(benefit.movePointRight(BigDecimalUtils.SCALE_PRICE).toPlainString(),
                ArtesisConstants.NUMBER_ZERO_ON_EMPTY_MONETARY_FIELD, "0"));

            } else {
              artesisBenefitsBlock.setBenefitsSign(SignumUtils.POSITIVE_SIGN);
              artesisBenefitsBlock.setBenefitsAmount("");
            }
            if (null != historyContractDTO.getWithdrawnRent()) {
              // On calcule le loyer prélevé à payer
              BigDecimal mensualRent = ContractUtils.netMensualRentRegularizationBenefit(historyContractDTO, contractDTO.getStartValidityDate(), contractDTO.getEndValidityDate(),
                regularizationDate);

              if (numberOfRetroactivityMonths < BigDecimalUtils.POSITIVE_OR_NEGATIVE_COMPARE_WITH_ZERO) {
                // On calcule le loyer prélevé payé
                BigDecimal mensualRentInModif = ContractUtils.netMensualRentRegularizationBenefit(historyContractDTO, contractDTO.getStartValidityDate(),
                  historyContractDTO.getEndValidityDate(), regularizationDate);

                mensualRent = mensualRent.subtract(mensualRentInModif);
              }

              // On ajoute le montant à la liste à historiser
              amountsByLabel.put(ArtesisConstants.BENEFITS_WITHDRAWN_RENT_LABEL, mensualRent);

              artesisBenefitsBlock.setWithdrawnRentSign(SignumUtils.getSignumByValue(mensualRent.signum()));

              mensualRent = mensualRent.abs();

              artesisBenefitsBlock.setWithdrawnRentAmount(StringUtils.leftPad(mensualRent.movePointRight(BigDecimalUtils.SCALE_PRICE).toPlainString(),
                ArtesisConstants.NUMBER_ZERO_ON_EMPTY_MONETARY_FIELD, "0"));

            } else {
              artesisBenefitsBlock.setWithdrawnRentSign(SignumUtils.POSITIVE_SIGN);
              artesisBenefitsBlock.setWithdrawnRentAmount("");
            }
            artesisBenefitsBlock.setEndOfLine("");

            // On historise les montants de la map
            HistoryAmountDTO historyAmountDTO = historiseAmount(historyContractDTO, amountsByLabel, ArtesisConstants.TYPE_BENEFITS, generationDate, regularizationDate);
            historyAmountService.create(historyAmountDTO);
          }
        }
      }
    } catch (ContractServiceFacadeException e) {
      throw new ArtesisServiceException(e.getMessage(), e);
    } catch (HistoryContractServiceException e) {
      throw new ArtesisServiceException(e.getMessage(), e);
    } catch (HistoryAmountServiceException e) {
      throw new ArtesisServiceException(e.getMessage(), e);
    }

    return artesisBenefitsBlocks;
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
   * @param historyAmountService the historyAmountService to set
   */
  public void setHistoryAmountService(IHistoryAmountService historyAmountService) {
    this.historyAmountService = historyAmountService;
  }

}
