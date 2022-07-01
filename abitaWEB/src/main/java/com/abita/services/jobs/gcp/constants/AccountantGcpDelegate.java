package com.abita.services.jobs.gcp.constants;

import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dto.AccountingCodeDTO;
import com.abita.dto.ContractDTO;
import com.abita.dto.ThirdPartyContractDTO;
import com.abita.services.jobs.gcp.constants.GcpConstants.GcpType;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * Données des postes des pièces GCP
 */
public final class AccountantGcpDelegate {

  /**
   * Constructeur privé
   */
  private AccountantGcpDelegate() {
  }

  /**
   * Type de ligne
   * @return type de ligne
   */
  public static String getLineType() {
    return GcpConstants.TYPE_LINE_2;
  }

  /**
   * Compte
   * @param gcpType type de pièce
   * @param index ligne de poste
   * @param thirdPartyContractDTO contrat tiers
   * @param contractDTO contrat occupant
   * @param accountingCodeDTO code comptable
   * @return compte
   */
  public static String getAccount(GcpType gcpType, Integer index, ThirdPartyContractDTO thirdPartyContractDTO, ContractDTO contractDTO, AccountingCodeDTO accountingCodeDTO) {
    String account = "";

    if (GcpUtils.isYlType(gcpType)) {
      account = getAccountForYl(index, thirdPartyContractDTO, accountingCodeDTO);
    } else if (gcpType.equals(GcpType.ZN)) {
      account = accountingCodeDTO.getCode();
    } else if (GcpUtils.isNcType(gcpType)) {
      account = getAccountForNc(index, contractDTO, accountingCodeDTO);
    }

    return account;
  }

  /**
   * @param index type de pièce
   * @param thirdPartyContractDTO contrat tiers
   * @param accountingCodeDTO code comptable
   * @return compte
   */
  private static String getAccountForYl(Integer index, ThirdPartyContractDTO thirdPartyContractDTO, AccountingCodeDTO accountingCodeDTO) {
    String account = "";

    if (index == GcpConstants.LOYER_COMPTE_GENERAL || index == GcpConstants.CHARGES_COMPTE_GENERAL) {
      account = thirdPartyContractDTO.getThirdParty().getGcpReference();
    } else if (index == GcpConstants.LOYER_COMPTE_CHARGE || index == GcpConstants.CHARGES_COMPTE_LOCATIVES) {
      account = accountingCodeDTO.getCode();
    }

    return account;
  }

  /**
   * @param index type de pièce
   * @param contractDTO contrat occupant
   * @param accountingCodeDTO code comptable
   * @return compte
   */
  private static String getAccountForNc(Integer index, ContractDTO contractDTO, AccountingCodeDTO accountingCodeDTO) {
    String account = "";

    if (index == GcpConstants.RENT_SUPPLIER_LINE || index == GcpConstants.RENTAL_EXPENSES_SUPPLIER_LINE || index == GcpConstants.GARAGE_SUPPLIER_LINE) {
      if (GcpConstants.TYPE_TENANT_EMPLOYEE.equalsIgnoreCase(contractDTO.getTenant().getTypeTenant().getLabel())) {
        account = contractDTO.getTenant().getReference();
      } else {
        account = accountingCodeDTO.getCode();
      }
    } else if (index == GcpConstants.RENT_PRODUCT_LINE || index == GcpConstants.RENTAL_EXPENSES_PRODUCT_LINE || index == GcpConstants.GARAGE_PRODUCT_LINE) {
      account = accountingCodeDTO.getCode();
    }

    return account;
  }

  /**
   * Code clé GCS
   * @param gcpType type de pièce
   * @param index ligne de poste
   * @param contractDTO contrat occupant
   * @return code clé GCS
   */
  public static String getKeyCodeGCS(GcpType gcpType, Integer index, ContractDTO contractDTO) {
    String keyCodeGcs = "";

    if (GcpUtils.isNcType(gcpType)) {
      keyCodeGcs = getKeyCodeGcsForNc(index, contractDTO);
    }

    return keyCodeGcs;
  }

  /**
   * @param index ligne de poste
   * @param contractDTO contrat occupant
   * @return code clé GCS
   */
  private static String getKeyCodeGcsForNc(Integer index, ContractDTO contractDTO) {
    String keyCodeGcs = "";

    if (index == GcpConstants.RENT_SUPPLIER_LINE || index == GcpConstants.RENTAL_EXPENSES_SUPPLIER_LINE || index == GcpConstants.GARAGE_SUPPLIER_LINE) {
      if (GcpConstants.TYPE_TENANT_EMPLOYEE.equalsIgnoreCase(contractDTO.getTenant().getTypeTenant().getLabel())) {
        keyCodeGcs = "0";
      } else {
        keyCodeGcs = "";
      }
    }

    return keyCodeGcs;
  }

  /**
   * Clé de comptabilisation
   * @param gcpType type de pièce
   * @param inCycle vrai si c’est une pièce dans le cycle (seulement ZN)
   * @param index ligne de poste
   * @param thirdPartyContractDTO contrat tiers
   * @param contractDTO contrat occupant
   * @param accountingCodeDTO code comptable
   * @param price montant
   * @return clé de comptabilisation
   */
  public static String getKeyAccounting(GcpType gcpType, Boolean inCycle, Integer index, ThirdPartyContractDTO thirdPartyContractDTO, ContractDTO contractDTO,
    AccountingCodeDTO accountingCodeDTO, BigDecimal price) {
    String keyAccounting = "";

    if (gcpType.equals(GcpType.YL) || gcpType.equals(GcpType.YL_FP) || gcpType.equals(GcpType.YL_REVI)) {
      keyAccounting = getKeyAccountingForYlAndYlFp(gcpType, index, thirdPartyContractDTO, accountingCodeDTO, price);
    } else if (gcpType.equals(GcpType.YL_REGUL)) {
      keyAccounting = getKeyAccountingForYlRegul(gcpType, index, thirdPartyContractDTO, accountingCodeDTO, price);
    } else if (gcpType.equals(GcpType.ZN)) {
      keyAccounting = getKeyAccountingForZn(inCycle, index, price);
    } else if (GcpUtils.isNcType(gcpType)) {
      keyAccounting = getKeyAccountingForNc(gcpType, index, contractDTO, accountingCodeDTO, price);
    }

    return keyAccounting;
  }

  /**
   * @param gcpType type de pièce
   * @param index ligne de poste
   * @param thirdPartyContractDTO contrat tiers
   * @param accountingCodeDTO code comptable
   * @param price montant
   * @return clé de comptabilisation
   */
  private static String getKeyAccountingForYlAndYlFp(GcpType gcpType, Integer index, ThirdPartyContractDTO thirdPartyContractDTO, AccountingCodeDTO accountingCodeDTO,
    BigDecimal price) {
    String keyAccouting = "";
    String account = getAccount(gcpType, index, thirdPartyContractDTO, null, accountingCodeDTO);

    if (index == GcpConstants.LOYER_COMPTE_GENERAL || index == GcpConstants.CHARGES_COMPTE_GENERAL) {
      if (StringUtils.isNotEmpty(account)) {
        if (price.signum() > 0) {
          keyAccouting = GcpConstants.POSTING_KEY_31;
        } else {
          keyAccouting = GcpConstants.POSTING_KEY_21;
        }
      }
    } else if (index == GcpConstants.LOYER_COMPTE_CHARGE || index == GcpConstants.CHARGES_COMPTE_LOCATIVES) {
      if (price.signum() > 0) {
        keyAccouting = GcpConstants.POSTING_KEY_40;
      } else {
        keyAccouting = GcpConstants.POSTING_KEY_50;
      }
    }

    return keyAccouting;
  }

  /**
   * @param gcpType type de pièce
   * @param index ligne de poste
   * @param thirdPartyContractDTO contrat tiers
   * @param accountingCodeDTO code comptable
   * @param price montant
   * @return clé de comptabilisation
   */
  private static String getKeyAccountingForYlRegul(GcpType gcpType, Integer index, ThirdPartyContractDTO thirdPartyContractDTO, AccountingCodeDTO accountingCodeDTO,
    BigDecimal price) {
    String keyAccouting = "";
    String account = getAccount(gcpType, index, thirdPartyContractDTO, null, accountingCodeDTO);

    if (index == GcpConstants.LOYER_COMPTE_GENERAL || index == GcpConstants.CHARGES_COMPTE_GENERAL) {
      if (StringUtils.isNotEmpty(account)) {
        keyAccouting = GcpConstants.POSTING_KEY_21;
      }
    } else if (index == GcpConstants.LOYER_COMPTE_CHARGE || index == GcpConstants.CHARGES_COMPTE_LOCATIVES) {
      keyAccouting = GcpConstants.POSTING_KEY_50;
    }

    return keyAccouting;
  }

  /**
   * @param inCycle vrai si c’est une pièce dans le cycle (seulement ZN)
   * @param index ligne de poste
   * @param price montant
   * @return clé de comptabilisation
   */
  private static String getKeyAccountingForZn(Boolean inCycle, Integer index, BigDecimal price) {
    String keyAccouting = "";

    if (inCycle) {
      keyAccouting = getKeyAccountForZnInCycle(index, price);
    } else {
      keyAccouting = getKeyAccountingForZnOffCycle(index, price);
    }

    return keyAccouting;
  }

  /**
   * @param index ligne de poste
   * @param price montant
   * @return clé de comptabilisation
   */
  private static String getKeyAccountForZnInCycle(Integer index, BigDecimal price) {
    String keyAccouting = "";

    if (index == GcpConstants.LOYER_COMPTE_GENERAL || index == GcpConstants.CHARGES_COMPTE_GENERAL) {
      if (price.signum() > 0) {
        keyAccouting = GcpConstants.POSTING_KEY_50;
      } else {
        keyAccouting = GcpConstants.POSTING_KEY_40;
      }
    } else if (index == GcpConstants.LOYER_COMPTE_CHARGE || index == GcpConstants.CHARGES_COMPTE_LOCATIVES) {
      if (price.signum() > 0) {
        keyAccouting = GcpConstants.POSTING_KEY_40;
      } else {
        keyAccouting = GcpConstants.POSTING_KEY_50;
      }
    }
    return keyAccouting;
  }

  /**
   * @param index ligne de poste
   * @param price montant
   * @return clé de comptabilisation
   */
  private static String getKeyAccountingForZnOffCycle(Integer index, BigDecimal price) {
    String keyAccouting = "";

    if (index == GcpConstants.LOYER_COMPTE_GENERAL || index == GcpConstants.CHARGES_COMPTE_GENERAL) {
      if (price.signum() > 0) {
        keyAccouting = GcpConstants.POSTING_KEY_40;
      } else {
        keyAccouting = GcpConstants.POSTING_KEY_50;
      }
    } else if (index == GcpConstants.LOYER_COMPTE_CHARGE || index == GcpConstants.CHARGES_COMPTE_LOCATIVES) {
      if (price.signum() > 0) {
        keyAccouting = GcpConstants.POSTING_KEY_50;
      } else {
        keyAccouting = GcpConstants.POSTING_KEY_40;
      }
    }
    return keyAccouting;
  }

  /**
   * @param gcpType type de pièce
   * @param index ligne de poste
   * @param contractDTO contrat occupant
   * @param accountingCodeDTO code comptable
   * @param price montant
   * @return clé de comptabilisation
   */
  private static String getKeyAccountingForNc(GcpType gcpType, Integer index, ContractDTO contractDTO, AccountingCodeDTO accountingCodeDTO, BigDecimal price) {
    String keyAccouting = "";

    if (index == GcpConstants.RENT_SUPPLIER_LINE || index == GcpConstants.RENTAL_EXPENSES_SUPPLIER_LINE || index == GcpConstants.GARAGE_SUPPLIER_LINE) {
      if (GcpConstants.TYPE_TENANT_EMPLOYEE.equalsIgnoreCase(contractDTO.getTenant().getTypeTenant().getLabel())) {
        if (price.signum() > 0) {
          keyAccouting = GcpConstants.POSTING_KEY_29;
        } else {
          keyAccouting = GcpConstants.POSTING_KEY_39;
        }
      } else {
        if (price.signum() > 0) {
          keyAccouting = GcpConstants.POSTING_KEY_40;
        } else {
          keyAccouting = GcpConstants.POSTING_KEY_50;
        }
      }
    } else if (index == GcpConstants.RENT_PRODUCT_LINE || index == GcpConstants.RENTAL_EXPENSES_PRODUCT_LINE || index == GcpConstants.GARAGE_PRODUCT_LINE) {
      if (price.signum() > 0) {
        keyAccouting = GcpConstants.POSTING_KEY_50;
      } else {
        keyAccouting = GcpConstants.POSTING_KEY_40;
      }
    }

    return keyAccouting;
  }

  /**
   * Montant devise pièce
   * @param price montant
   * @return Montant devise pièce
   */
  public static String getAmount(BigDecimal price) {
    return BigDecimalUtils.formatFranceNumber(price.abs());
  }

  /**
   * Code TVA
   * @param gcpType type de pièce
   * @param index ligne de poste
   * @return code TVA
   */
  public static String getValueAddedTaxCode(GcpType gcpType, Integer index) {
    String valueAddedTaxCode = "";

    if (GcpUtils.isThirdPartyContractType(gcpType)) {
      if (index == GcpConstants.LOYER_COMPTE_CHARGE || index == GcpConstants.CHARGES_COMPTE_LOCATIVES) {
        valueAddedTaxCode = "V0";
      }
    } else if (GcpUtils.isTenantContractType(gcpType)) {
      valueAddedTaxCode = getValueAddedTaxCodeForNc(index);
    }

    return valueAddedTaxCode;
  }

  /**
   * @param index ligne de poste
   * @return code TVA
   */
  private static String getValueAddedTaxCodeForNc(Integer index) {
    String valueAddedTaxCode = "";

    if (index == GcpConstants.RENT_PRODUCT_LINE || index == GcpConstants.RENTAL_EXPENSES_PRODUCT_LINE || index == GcpConstants.GARAGE_PRODUCT_LINE) {
      valueAddedTaxCode = GcpConstants.TVA_CODE_CL;
    }

    return valueAddedTaxCode;
  }

  /**
   * Domaine d’activité
   * @param gcpType type de pièce
   * @param thirdPartyContractDTO contrat tiers
   * @param contractDTO contrat occupant
   * @return domaine d’activité
   */
  public static String getActivityDomain(GcpType gcpType, ThirdPartyContractDTO thirdPartyContractDTO, ContractDTO contractDTO) {
    String activityDomain = "";

    if (GcpUtils.isThirdPartyContractType(gcpType)) {
      activityDomain = thirdPartyContractDTO.getFieldOfActivity().getLabel();
    } else if (GcpUtils.isTenantContractType(gcpType)) {
      activityDomain = contractDTO.getFieldOfActivity().getLabel();
    }

    return activityDomain;
  }

  /**
   * Date de base
   * @param gcpType type de pièce
   * @param index ligne de poste
   * @param thirdPartyContractDTO contrat tiers
   * @param contractDTO contrat occupant
   * @param paymentCycle cycle de paiement
   * @param generationDate date de génération
   * @return date de base
   */
  public static String getBaseDate(GcpType gcpType, Integer index, ThirdPartyContractDTO thirdPartyContractDTO, ContractDTO contractDTO, String paymentCycle,
    LocalDate generationDate) {
    LocalDate baseDate = null;

    if (gcpType.equals(GcpType.YL)) {
      baseDate = getBaseDateForYl(index, thirdPartyContractDTO, paymentCycle, generationDate);
    } else if (gcpType.equals(GcpType.YL_FP) || gcpType.equals(GcpType.YL_REVI)) {
      baseDate = getBaseDateForYlFp(index, generationDate);
    } else if (gcpType.equals(GcpType.YL_REGUL)) {
      baseDate = getBaseDateForYlRegul(index, generationDate);
    } else if (GcpUtils.isNcType(gcpType)) {
      baseDate = getBaseDateForNc(index, contractDTO, generationDate);
    }

    if (baseDate != null) {
      return DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_DDMMYYYY).print(baseDate);
    } else {
      return "";
    }
  }

  /**
   * @param index ligne de poste
   * @param thirdPartyContractDTO contrat tiers
   * @param paymentCycle cycle de paiement
   * @param generationDate date de génération
   * @return date de base
   */
  private static LocalDate getBaseDateForYl(Integer index, ThirdPartyContractDTO thirdPartyContractDTO, String paymentCycle, LocalDate generationDate) {
    LocalDate baseDate = null;

    if (index == GcpConstants.LOYER_COMPTE_GENERAL || index == GcpConstants.CHARGES_COMPTE_GENERAL) {
      // dernier jour calendrier du mois de génération pour les contrats à terme échu
      // premier jour calendrier du mois comptable suivant pour les contrats à terme à échoir
      if (DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle) && thirdPartyContractDTO.getExpiryDate()) {
        baseDate = generationDate.dayOfMonth().withMaximumValue();
      } else {
        baseDate = generationDate.plusMonths(1).dayOfMonth().withMinimumValue();
      }
    }

    return baseDate;
  }

  /**
   * @param index ligne de poste
   * @param generationDate date de génération
   * @return date de base
   */
  private static LocalDate getBaseDateForYlFp(Integer index, LocalDate generationDate) {
    LocalDate baseDate = null;

    if (index == GcpConstants.LOYER_COMPTE_GENERAL || index == GcpConstants.CHARGES_COMPTE_GENERAL) {
      baseDate = generationDate.dayOfMonth().withMinimumValue();
    }

    return baseDate;
  }

  /**
   * @param index ligne de poste
   * @param generationDate date de génération
   * @return date de base
   */
  private static LocalDate getBaseDateForYlRegul(Integer index, LocalDate generationDate) {
    LocalDate baseDate = null;

    if (index == GcpConstants.LOYER_COMPTE_GENERAL || index == GcpConstants.CHARGES_COMPTE_GENERAL) {
      baseDate = generationDate.plusMonths(1).dayOfMonth().withMinimumValue();
    }

    return baseDate;
  }

  /**
   * @param index ligne de poste
   * @param contractDTO contract occupant
   * @param generationDate date de génération
   * @return date de base
   */
  private static LocalDate getBaseDateForNc(Integer index, ContractDTO contractDTO, LocalDate generationDate) {
    LocalDate baseDate = null;

    if (index == GcpConstants.RENT_SUPPLIER_LINE || index == GcpConstants.RENTAL_EXPENSES_SUPPLIER_LINE || index == GcpConstants.GARAGE_SUPPLIER_LINE) {
      if (GcpConstants.TYPE_TENANT_EMPLOYEE.equalsIgnoreCase(contractDTO.getTenant().getTypeTenant().getLabel())) {
        baseDate = generationDate.dayOfMonth().withMaximumValue();
      } else {
        baseDate = null;
      }
    }

    return baseDate;
  }

  /**
   * Condition de paiement
   * @param gcpType type de pièce
   * @param index ligne de poste
   * @return condition de paiement
   */
  public static String getConditionForPayment(GcpType gcpType, Integer index) {
    String conditionForPayment = "";

    if (GcpUtils.isYlType(gcpType)) {
      conditionForPayment = getConditionForPaymentForYl(index);
    }

    return conditionForPayment;
  }

  /**
   * @param index ligne de poste
   * @return condition de paiement
   */
  private static String getConditionForPaymentForYl(Integer index) {
    String conditionForPayment = "";

    if (index == GcpConstants.LOYER_COMPTE_GENERAL || index == GcpConstants.CHARGES_COMPTE_GENERAL) {
      conditionForPayment = "0001";
    }

    return conditionForPayment;
  }

  /**
   * Centre de coûts
   * @param gcpType type de pièce
   * @param index ligne de poste
   * @param thirdPartyContractDTO contrat tiers
   * @param contractDTO contrat occupant
   * @return centre de coûts
   */
  public static String getCostCenter(GcpType gcpType, Integer index, ThirdPartyContractDTO thirdPartyContractDTO, ContractDTO contractDTO) {
    String costCenter = "";

    if (GcpUtils.isThirdPartyContractType(gcpType)) {
      if (index == GcpConstants.LOYER_COMPTE_CHARGE || index == GcpConstants.CHARGES_COMPTE_LOCATIVES) {
        costCenter = thirdPartyContractDTO.getCostCenter().getLabel();
      }
    } else if (GcpUtils.isTenantContractType(gcpType)) {
      costCenter = getCostCenterForNc(index, contractDTO);
    }

    return costCenter;
  }

  /**
   * @param index ligne de poste
   * @param contractDTO contrat occupant
   * @return centre de coût
   */
  private static String getCostCenterForNc(Integer index, ContractDTO contractDTO) {
    String costCenter = "";

    if (index == GcpConstants.RENT_PRODUCT_LINE || index == GcpConstants.RENTAL_EXPENSES_PRODUCT_LINE || index == GcpConstants.GARAGE_PRODUCT_LINE) {
      if (contractDTO.getHousing().getProperty()) {
        costCenter = GcpConstants.CENTER_COST_RTE_LABEL;
      } else {
        costCenter = GcpConstants.CENTER_COST_TIR_LABEL;
      }
    }

    return costCenter;
  }

  /**
   * Zone d’affectation
   * @param gcpType type de pièce
   * @param index ligne de poste
   * @param thirdPartyContractDTO contrat tiers
   * @param contractDTO contrat occupant
   * @return zone d’affectation
   */
  public static String getHeadquartersArea(GcpType gcpType, Integer index, ThirdPartyContractDTO thirdPartyContractDTO, ContractDTO contractDTO) {
    String headquartersArea = "";

    if (GcpUtils.isThirdPartyContractType(gcpType)) {
      headquartersArea = thirdPartyContractDTO.getThirdParty().getGcpReference();
    } else if (GcpUtils.isTenantContractType(gcpType)) {
      if (index == GcpConstants.RENT_SUPPLIER_LINE || index == GcpConstants.RENTAL_EXPENSES_SUPPLIER_LINE || index == GcpConstants.GARAGE_SUPPLIER_LINE) {
        headquartersArea = contractDTO.getTenant().getReference();
      } else if (index == GcpConstants.RENT_PRODUCT_LINE || index == GcpConstants.RENTAL_EXPENSES_PRODUCT_LINE || index == GcpConstants.GARAGE_PRODUCT_LINE) {
        headquartersArea = contractDTO.getContractReference();
      }
    }

    return headquartersArea;
  }

  /**
   * Texte
   * @param gcpType type de pièce
   * @param generationDate date de génération
   * @param thirdPartyContractDTO contrat tiers
   * @param thirdParty nom du propriétaire
   * @return texte
   */
  public static String getText(GcpType gcpType, LocalDate generationDate, ThirdPartyContractDTO thirdPartyContractDTO, String thirdParty) {
    String pattern = "{0}{1}{2}{3}";
    String monthYearOfTreatment = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_MMYYYY).print(generationDate);
    String monthOfTreatment = DateTimeUtils.getMonthWithFourCharacters(generationDate);

    String name = "";
    if (GcpUtils.isThirdPartyContractType(gcpType)) {
      name = thirdPartyContractDTO.getThirdParty().getName().toUpperCase();
    } else if (GcpUtils.isTenantContractType(gcpType)) {
      name = thirdParty;
    }
    return MessageFormat.format(pattern, GcpConstants.ABI_TRIGRAM, monthYearOfTreatment, monthOfTreatment, name);
  }
}
