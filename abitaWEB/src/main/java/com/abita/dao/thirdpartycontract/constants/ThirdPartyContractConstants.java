package com.abita.dao.thirdpartycontract.constants;

/**
 * Classe de constante des DAO des contrats tiers
 *
 * @author
 */
public final class ThirdPartyContractConstants {

  /**
   * Constructeur privé
   */
  private ThirdPartyContractConstants() {
  }

  /**
   * ID du contrat tiers
   */
  public static final String ID_THIRDPARTY_CONTRACT = "thirdPartyContractId";

  /**
   * ID du logement
   */
  public static final String ID_HOUSING = "idHousing";

  /**
   * Terme échu / échoir
   */
  public static final String EXPIRY_DATE = "expiryDate";

  /**
   * Référence du contrat tiers
   */
  public static final String TPC_REFERENCE = "reference";

  /** Requête de recherche des tiers sans critères
   * Attention! :
   * le 1=1 (condition toujours vraie) permet d'ajouter directement des clauses de recherche avec AND sans être obligé de tester les critères au préalable. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_WITHOUT_CRITERIA = "SELECT DISTINCT tpc FROM ThirdPartyContractEntity tpc INNER JOIN tpc.housing.agency AS agc WHERE 1 = 1";

  /** Critère de recherche par référence (id). */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_REFERENCE = " and tpc.id = :id";

  /** Critère de recherche par référence. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_REAL_REFERENCE = " and tpc.reference = :reference";

  /** Critère de recherche par la référence du tiers. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_THIRDPARTY_REFERENCE = " and tpc.thirdParty.id = :thirdPartyId";

  /** Critère de recherche par la référence du logement. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_HOUSING_REFERENCE = " and tpc.housing.id = :housingId";

  /** Critère de recherche par la vraie référence du logement. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_REAL_HOUSING_REFERENCE = " and tpc.housing.reference = :housingReference";

  /** Critère de recherche par le domaine d'activité. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_FIELD_OF_ACTIVITY = " and tpc.fieldOfActivity.id = :fieldOfActivityId";

  /** Critère de recherche par le centre de coût. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_COST_CENTER = " and tpc.costCenter.id = :costCenterId";

  /** Critère de recherche par la durée du contrat. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_CONTRACT_PERIOD = " and tpc.contractPeriod like :contractPeriod";

  /** Critère de recherche par la durée du préavis. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_NOTICE_PERIOD = " and tpc.noticePeriod like :noticePeriod";

  /** Critère de recherche par la date de début de validité. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_START_VALIDITY = " and tpc.startValidity = :startValidity";

  /** Critère de recherche par la date de début de paiement fournisseur. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_START_SUPPLIER_PAYMENT_DATE = " and tpc.startSupplierPaymentDate = :startSupplierPaymentDate";

  /** Critère de recherche par le montant du loyer mensuel. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_RENT_AMOUNT = " and tpc.rentAmount like REPLACE(:rentAmount, '.', ',')";

  /** Critère de recherche par le montant des charges prévisionnelles mensuelles. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_EXPECTED_CHARGE_COST = " and tpc.expectedChargeCost like REPLACE(:expectedChargeCost, '.', ',')";

  /** Critère de recherche par le montant du dépot de garantie. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_GUARANTEED_DEPOSIT_AMOUNT = " and tpc.guaranteedDepositAmount like REPLACE(:guaranteedDepositAmount, '.', ',')";

  /** Critère de recherche par le montant des frais d'agence. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_REAL_ESTATE_AGENCY_FEE = " and tpc.realEstateAgencyFee like REPLACE(:realEstateAgencyFee, '.', ',')";

  /** Critère de recherche par la périodicité du paiement. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_PAYMENT_CYCLE = " and tpc.paymentCycle.id = :paymentCycleId";

  /** Critère de recherche par la charge ponctuelle. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_SPORADICALLY_INVOICING = " and tpc.sporadicallyInvoicing like REPLACE(:sporadicallyInvoicing, '.', ',')";

  /** Critère de recherche par la date de résiliation du contrat. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_CANCELLATION_DATE = " and tpc.cancellationDate = :cancellationDate";

  /** Critère de recherche par le motif de résiliation. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_THIRD_PARTY_TERMINATION = " and tpc.thirdPartyTermination.id = :thirdPartyTerminationId";

  /** Critère de recherche par le remboursement du dépot de garantie. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_GUARANTEED_DEPOSIT_REFUND = " and tpc.guaranteedDepositRefund = :guaranteedDepositRefund";

  /** Critère de recherche par le terme échu / échoir. */
  public static final String SEARCH_THIRDPARTY_CONTRACT_BY_EXPIRY_DATE = " and tpc.expiryDate = :expiryDate";

  /** Critère de recherche par agence */
  public static final String SEARCH_BY_AGENCY = " and ((tpc.closedThirdPartyContract = 0 and agc.id = :agency) or (tpc.closedThirdPartyContract = 1 and tpc.fixedAgency.id = :agency))";

  /** Ordre de tri par référence (id). */
  public static final String ORDER_BY_TENANT_REFERENCE = " order by tpc.id";

}
