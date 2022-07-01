package com.abita.dao.contract.constants;

/**
 * Classe de constante des DAO des contrats occupant
 *
 * @author
 */
public final class ContractConstants {

  /**
   * Constructeur privé
   */
  private ContractConstants() {
  }

  /**
   * ID de l'occupant
   */
  public static final String ID_TENANT = "idTenant";

  /**
   * ID du logement
   */
  public static final String ID_HOUSING = "idHousing";

  /**
   * ID du contrat occupant
   */
  public static final String ID_CONTRACT = "idContract";

  /**
   * Date de sortie du logement
   */
  public static final String UNREGISTER_DATE = "unregisterDate";

  /** Requête de recherche des tiers sans critères
   * Attention! :
   * le 1=1 (condition toujours vraie) permet d'ajouter directement des clauses de recherche avec AND sans être obligé de tester les critères au préalable. */
  public static final String SEARCH_CONTRACT_WITHOUT_CRITERIA = "SELECT DISTINCT con FROM ContractEntity as con LEFT JOIN con.tenant as t INNER JOIN con.housing.agency AS agc WHERE 1 = 1";

  /** Critère de recherche par id. */
  public static final String SEARCH_CONTRACT_BY_ID = " and con.id = :id";

  /** Critère de recherche par la référence du logement. */
  public static final String SEARCH_CONTRACT_BY_HOUSING_REFERENCE = " and con.housing.id = :housingId";

  /** Critère de recherche par la vraie référence du logement. */
  public static final String SEARCH_CONTRACT_BY_REAL_HOUSING_REFERENCE = " and con.housing.reference = :housingReference";

  /** Critère de recherche par la référence de la typologie de loyer. */
  public static final String SEARCH_CONTRACT_BY_RENT_TYPOLOGY = " and con.rentTypology.id = :rentTypologyId";

  /** Critère de recherche par la référence du motif de résilliation. */
  public static final String SEARCH_CONTRACT_BY_TERMINATION = " and con.termination.id = :terminationId";

  /** Critère de recherche par le domaine d'activité. */
  public static final String SEARCH_CONTRACT_BY_FIELD_OF_ACTIVITY = " and con.fieldOfActivity.id = :fieldId";

  /** Critère de recherche par le centre de coût. */
  public static final String SEARCH_CONTRACT_BY_COST_CENTER = " and con.costCenter.id = :costCenterId";

  /** Critère de recherche par le moyen de paiement. */
  public static final String SEARCH_CONTRACT_BY_PAYMENT_METHOD = " and con.paymentMethod.id = :paymentMethodId";

  /** Critère de recherche par le contrat signé. */
  public static final String SEARCH_CONTRACT_BY_SIGNATURE = " and con.signature = :signature";

  /** Critère de recherche par la date de début de validité. */
  public static final String SEARCH_CONTRACT_BY_START_VALIDITY_DATE = " and con.startValidityDate = :startValidityDate";

  /** Critère de recherche par la date de fin de validité. */
  public static final String SEARCH_CONTRACT_BY_END_VALIDITY_DATE = " and con.endValidityDate = :endValidityDate";

  /** Critère de recherche par le loyer marché. */
  public static final String SEARCH_CONTRACT_BY_MARKET_RENT_PRICE = " and con.marketRentPrice = :marketRentPrice";

  /** Critère de recherche par le loyer plafond. */
  public static final String SEARCH_CONTRACT_BY_RENT_PRICE_LIMIT = " and con.rentPriceLimit = :rentPriceLimit";

  /** Critère de recherche par le loyer garage. */
  public static final String SEARCH_CONTRACT_BY_GARAGE_RENT = " and con.garageRent = :garageRent";

  /** Critère de recherche par le loyer jardin. */
  public static final String SEARCH_CONTRACT_BY_GARDEN_RENT = " and con.gardenRent = :gardenRent";

  /** Critère de recherche par le surloyer. */
  public static final String SEARCH_CONTRACT_BY_EXTRA_RENT = " and con.extraRent = :extraRent";

  /** Critère de recherche par les charges prévisionnelles. */
  public static final String SEARCH_CONTRACT_BY_EXPECTED_CHARGE_COST = " and con.expectedChargeCost = :expectedChargeCost";

  /** Critère de recherche Date limite de validité de l'attestation d'assurance postérieure ou égal à :date */
  public static final String SEARCH_CONTRACT_BY_INSURANCE_DATE_LOW = " and con.insuranceCertificateEndDate >= :insuranceCertificateEndDateLow";

  /** Critère de recherche Date limite de validité de l'attestation d'assurance antérieure ou égal à :date */
  public static final String SEARCH_CONTRACT_BY_INSURANCE_DATE_HIGH = " and con.insuranceCertificateEndDate <= :insuranceCertificateEndDateHigh";

  /** Critère de recherche par le loyer prélevé cumulé fixe. */
  public static final String SEARCH_CONTRACT_BY_PLAIN_ADDED_WITHDRAWN_RENT = " and con.addedWithdrawnRent = :plainAddedWithdrawnRent";

  /** Critère de recherche par la date du dernier cumule de loyer prélevé. */
  public static final String SEARCH_CONTRACT_BY_LAST_WITHDRAWN_DATE = " and con.lastWithdrawnDate = :lastWithdrawnDate";

  /** Critère de recherche par le pécule de fin d'occupation. */
  public static final String SEARCH_CONTRACT_BY_TERMINATION_SAVINGS = " and con.terminationSavings = :terminationSavings";

  /** Critère de recherche par le montant du pécule de fin d'occupation fixe. */
  public static final String SEARCH_CONTRACT_BY_PLAIN_TERMINATION_SAVING_AMOUNT = " and con.plainTerminationSavingAmount = :plainTerminationSavingAmount";

  /** Critère de recherche par le montant du pécule de fin d'occupation. */
  public static final String SEARCH_CONTRACT_BY_TERMINATION_SAVING_AMOUNT = " and cod.terminationSavingAmount = :terminationSavingAmount";

  /** Critère de recherche par la date du dernier cumule de pécule de fin d'occupation. */
  public static final String SEARCH_CONTRACT_BY_LAST_SAVING_DATE = " and con.lastSavingDate = :lastSavingDate";

  /** Critère de recherche par le montant de la valeur locative foncière. */
  public static final String SEARCH_CONTRACT_BY_REAL_ESTATE_RENTAL_VALUE = " and con.realEstateRentalValue = :realEstateRentalValue";

  /** Critère de recherche par la surface corrigée. */
  public static final String SEARCH_CONTRACT_BY_REVISED_SURFACE_AREA = " and ((con.closedContract = 0 and con.revisedSurfaceArea = :revisedSurfaceArea) or (con.closedContract = 1 and con.fixedRevisedSurfaceArea = :revisedSurfaceArea))";

  /** Critère de recherche par le loyer surface corrigée. */
  public static final String SEARCH_CONTRACT_BY_REVISED_SURFACE_AREA_RENT = " and ((con.closedContract = 0 and con.revisedSurfaceAreaRent = :revisedSurfaceAreaRent) or (con.closedContract = 1 and con.fixedRevisedSurfaceAreaRent = :revisedSurfaceAreaRent))";

  /** Critère de recherche par le nombre d'occupants */
  public static final String SEARCH_CONTRACT_BY_HOUSE_HOLD_SIZE = " and con.householdSize = :householdSize";

  /** Critère de recherche par le nNCoef n/N. */
  public static final String SEARCH_CONTRACT_BY_NNCOEF = " and ((con.closedContract = 0 and con.nNCoef = :nNCoef) or (con.closedContract = 1 and con.fixedNNCoef = :nNCoef))";

  /** Critère de recherche par le loyer net agent. */
  public static final String SEARCH_CONTRACT_BY_NET_AGENT_RENT = " and con.netAgentRent = :netAgentRent";

  /** Critère de recherche par l'abattement de précarité. */
  public static final String SEARCH_CONTRACT_BY_SHORT_TERM_CONTRACT_DISCOUNT = " and con.shortTermContractDiscount = :shortTermContractDiscount";

  /** Critère de recherche par le loyer écrêté. */
  public static final String SEARCH_CONTRACT_BY_LOP_RENT = " and ((con.closedContract = 0 and con.lopRent = :lopRent) or (con.closedContract = 1 and con.fixedLopRent = :lopRent))";

  /** Critère de recherche par le loyer prélevé. */
  public static final String SEARCH_CONTRACT_BY_WITHDRAWN_RENT = " and con.withdrawnRent = :withdrawnRent";

  /** Critère de recherche par le loyer prélevé cumulé. */
  public static final String SEARCH_CONTRACT_BY_ADDED_WITHDRAWN_RENT = " and cod.addedWithdrawnRent = :addedWithdrawnRent";

  /** Critère de recherche par l'avantage en nature. */
  public static final String SEARCH_CONTRACT_BY_BENEFIT = " and ((con.closedContract = 0 and con.benefit = :benefit) or (con.closedContract = 1 and con.fixedBenefit = :benefit))";

  /** Critère de recherche par le règlement du pécule de fin d'occupation. */
  public static final String SEARCH_CONTRACT_BY_TERMINATION_SAVINGS_PAYMENT = " and con.terminationSavingsPayment = :terminationSavingsPayment";

  /** Critère de recherche par la date de règlement du pécule de fin d'occupation. */
  public static final String SEARCH_CONTRACT_BY_TERMINATION_SAVINGS_PAYMENT_DATE = " and con.terminationSavingsPaymentDate = :terminationSavingsPaymentDate";

  /** Critère de recherche par agence */
  public static final String SEARCH_BY_AGENCY = " and ((con.closedContract = 0 and agc.id = :agency) or (con.closedContract = 1 and con.fixedAgency.id = :agency))";


  /** CONSTANTES OCCUPANTS */

  /** Ordre de tri par id. */
  public static final String ORDER_BY_CONTRACT_ID = " order by con.id";

}
