/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.housing.constants;

/**
 * Classe de constante des DAO des logements
 *
 * @author
 */
public final class HousingConstants {

  /**
   * Constructeur privé
   */
  private HousingConstants() {
  }

  /**
   * ID du logement
   */
  public static final String ID_HOUSING = "idHousing";

  /** Requête de recherche des tiers sans critères
   * Attention! :
   * le 1=1 (condition toujours vraie) permet d'ajouter directement des clauses de recherche avec AND sans être obligé de tester les critères au préalable. */
  public static final String SEARCH_HOUSING_WITHOUT_CRITERIA = "SELECT DISTINCT h, c, san, ht, ac FROM ContractEntity as c RIGHT JOIN c.housing as h LEFT JOIN c.tenant as t LEFT JOIN c.fieldOfActivity as f INNER JOIN h.agency as agc LEFT JOIN h.rattachmentSite as sit LEFT OUTER JOIN h.heatingType as ht LEFT OUTER JOIN h.sanitation as san LEFT OUTER JOIN h.airconditioner as ac WHERE 1 = 1";

  /** Critère de recherche par référence (id). */
  public static final String SEARCH_HOUSING_BY_REFERENCE = " and h.id = :id";

  /** Critère de recherche par date d'entrée. */
  public static final String SEARCH_HOUSING_BY_REGISTER_DATE = " and h.registerDate = :registerDate";

  /** Critère de recherche par date de sortie. */
  public static final String SEARCH_HOUSING_BY_UNREGISTER_DATE = " and h.unregisterDate = :unregisterDate";

  /** Critère de recherche par date de sortie null */
  public static final String SEARCH_HOUSING_BY_UNREGISTER_DATE_NULL = " and h.unregisterDate IS NULL";

  /** Critère de recherche par motif de sortie */
  public static final String SEARCH_HOUSING_BY_REASON_FOR_EXIT = " and h.reasonForExit = :reasonForExit";

  /** Critère de recherche par Surface réelle */
  public static final String SEARCH_HOUSING_BY_REAL_SURFACE = " and h.realSurfaceArea like REPLACE(:realSurfaceArea, '.', ',')";

  /** Critère de recherche par Surface corrigée. */
  public static final String SEARCH_HOUSING_BY_REVISED_SURFACE = " and h.revisedSurfaceArea like REPLACE(:revisedSurfaceArea, '.', ',')";

  /** Critère de recherche par propriété. */
  public static final String SEARCH_HOUSING_BY_PROPERTY = " and h.property = :property";

  /** Critère de recherche par jardin. */
  public static final String SEARCH_HOUSING_BY_GARDEN = " and h.gardenAvailable = :gardenAvailable";

  /** Critère de recherche par garage. */
  public static final String SEARCH_HOUSING_BY_GARAGE = " and h.garageAvailable = :garageAvailable";

  /** Critère de recherche par cuisine equipée. */
  public static final String SEARCH_HOUSING_BY_EQUIPPED_KITCHEN = " and h.equippedKitchen = :equippedKitchen";

  /** Critère de recherche par date cuisine. */
  public static final String SEARCH_HOUSING_BY_KITCHEN_DATE = " and h.kitchenDate = :kitchenDate";

  /** Critère de recherche par date Plomb. */
  public static final String SEARCH_HOUSING_BY_LEAD_DATE = " and h.leadDate = :leadDate";

  /** Critère de recherche par date amiantes. */
  public static final String SEARCH_HOUSING_BY_ASBESTOS_DATE = " and h.asbestosDate = :asbestosDate";

  /** Critère de recherche par salle de bain. */
  public static final String SEARCH_HOUSING_BY_BATHROOM = " and h.bathroom = :bathroom";

  /** Critère de recherche par Termites Date. */
  public static final String SEARCH_HOUSING_BY_TERMITE_DATE = " and h.termiteDate = :termiteDate";

  /** Critère de recherche par ERNMT. */
  public static final String SEARCH_HOUSING_BY_ERNMT_DATE = " and h.ernmtDate = :ernmtDate";

  /** Critère de recherche par date salle de bain. */
  public static final String SEARCH_HOUSING_BY_BATHROOM_DATE = " and h.bathroomDate = :bathroomDate";

  /** Critère de recherche par dpe. */
  public static final String SEARCH_HOUSING_BY_DPE = " and h.dpe = :dpe";

  /** Critère de recherche par date dpe. */
  public static final String SEARCH_HOUSING_BY_DPE_DATE = " and h.dpeDate = :dpeDate";

  /** Critère de recherche par gas. */
  public static final String SEARCH_HOUSING_BY_GAS = " and h.gas = :gas";

  /** Critère de recherche par date gaz. */
  public static final String SEARCH_HOUSING_BY_GAS_DATE = " and h.gasDate = :gasDate";

  /** Critère de recherche par elect. */
  public static final String SEARCH_HOUSING_BY_ELECT = " and h.elect = :elect";

  /** Critère de recherche par date elect. */
  public static final String SEARCH_HOUSING_BY_ELECT_DATE = " and h.electDate = :electDate";

  /** Critère de recherche par elect anomalie. */
  public static final String SEARCH_HOUSING_BY_ELECT_ANO = " and h.electAno = :electAno";

  /** Critère de recherche par gaz anomalie. */
  public static final String SEARCH_HOUSING_BY_GAS_ANO = " and h.gasAno = :gasAno";

  /** Critère de recherche par Amiantes. */
  public static final String SEARCH_HOUSING_BY_ASBESTOS = " and h.asbestos = :asbestos";

  /** Critère de recherche par Amiantes anomalie. */
  public static final String SEARCH_HOUSING_BY_ASBESTOS_ANO = " and h.asbestosAno = :asbestosAno";

  /** Critère de recherche par plomb. */
  public static final String SEARCH_HOUSING_BY_LEAD = " and h.lead = :lead";

  /** Critère de recherche par plomb anomalie. */
  public static final String SEARCH_HOUSING_BY_LEAD_ANO = " and h.leadAno = :leadAno";

  /** Critère de recherche par termites. */
  public static final String SEARCH_HOUSING_BY_TERMITE = " and h.termite = :termite";

  /** Critère de recherche par termites anomalie. */
  public static final String SEARCH_HOUSING_BY_TERMITE_ANO = " and h.termiteAno = :termiteAno";

  /** Critère de recherche par ernmt. V02.05.00 renommé en ERP */
  public static final String SEARCH_HOUSING_BY_ERNMT = " and h.ernmt = :ernmt";

  /** Critère de recherche par Climatisseur. */
  public static final String SEARCH_HOUSING_BY_AIRCONDITIONER = " and ac.id = :airconditioner";

  /** Critère de recherche par Type de chauffage. */
  public static final String SEARCH_HOUSING_BY_HEATINGTYPE = " and ht.id = :heatingType";

  /** Critère de recherche par Assainissement */
  public static final String SEARCH_HOUSING_BY_SANITATION = " and san.id = :sanitation";

  /** Critère de recherche par carrez. */
  public static final String SEARCH_HOUSING_BY_CARREZ = " and h.carrez = :carrez";

  /** Critère de recherche par date carrez . */
  public static final String SEARCH_HOUSING_BY_CARREZ_DATE = " and h.carrezDate = :carrezDate";

  /** Critère de recherche par statut. */
  public static final String SEARCH_HOUSING_BY_STATUS = " and h.housingStatus = :housingStatus";

  /** Critère de recherche par nombre de pièce. */
  public static final String SEARCH_HOUSING_BY_ROOM_COUNT = " and h.roomCount like :roomCount";

  /** Critère de recherche par metre carre. */
  public static final String SEARCH_HOUSING_BY_SQUARE_METER = " and h.squareMeter like :squareMeter";

  /** Critère de recherche par logement nature. */
  public static final String SEARCH_HOUSING_BY_NATURE = " and h.housingNature = :housingNature";

  /** Critère de recherche par core . */
  public static final String SEARCH_HOUSING_BY_CORE = " and h.core = :core";

  /** Critère de recherche par Année. */
  public static final String SEARCH_HOUSING_BY_YEAR = " and h.year = :year";

  /** Critère de recherche par gestionnaire de contrat tiers. */
  public static final String SEARCH_HOUSING_BY_THIRD_PARTY_CONTRACT_MANAGER = " and h.thirdpartyContractManager = :thirdpartyContractManager";

  /** Critère de recherche par catégorie de pièce. */
  public static final String SEARCH_HOUSING_BY_ROOM_CATEGORY = " and h.roomCategory = :roomCategory";

  /** Critère de recherche par domaine d’activité */
  public static final String SEARCH_HOUSING_BY_FIELD_OF_ACITIVITY = "  and f.id=:foa and c.startValidityDate <= :endDate and (c.endValidityDate >= :startDate OR c.endValidityDate IS NULL)";

  /** Critère de recherche par agence. */
  public static final String SEARCH_HOUSING_BY_AGENCY = " and agc.id = :agency";

  /** Critère de recherche par site */
  public static final String SEARCH_HOUSING_BY_SITE = " and sit.id = :rattachmentSite";

  /** Critère de recherche par date de dernière maintenance DAAF */
  public static final String SEARCH_HOUSING_BY_LAST_DATE_DAAF = " and h.lastDaafDate = :lastDaafDate";

  /** Ordre de tri par référence et date de début de validité du contrat */
  public static final String ORDER_BY_HOUSING_REFERENCE_AND_START_VALIDITY_DATE = " order by h.reference, c.startValidityDate";
  /** Critère de recherche par Avenir du logement */
  public static final String SEARCH_HOUSING_BY_FUTUR_OF_HOUSING = " and h.futurOfHousing = :futurOfHousing";
  /** Critère de recherche par sanitationstd */
  public static final String SEARCH_HOUSING_BY_SANITATION_STD = " and h.sanitationstd = :sanitationstd";
  /** Critère de recherche par Façade */
  public static final String SEARCH_HOUSING_BY_FACADE = " and h.facade = :facade";
  /** Critère de recherche par couverture */
  public static final String SEARCH_HOUSING_BY_COUVERTURE = " and h.couverture = :couverture";
  /** Critère de recherche par isolation */
  public static final String SEARCH_HOUSING_BY_INSULATION = " and h.insulation = :insulation";
  /** Critère de recherche par MURS */
  public static final String SEARCH_HOUSING_BY_WALLS= " and h.walls = :walls";
  /** Critère de recherche par minuiseries */
  public static final String SEARCH_HOUSING_BY_JOINERYFRAME = " and h.JoineryFrame = :JoineryFrame";
  /** Critère de recherche par finitions */
  public static final String SEARCH_HOUSING_BY_FINITIONS = " and h.finitions = :finitions";
  /** Critère de recherche par vmc */
  public static final String SEARCH_HOUSING_BY_VMC = " and h.vmc = :vmc";
  /** Critère de recherche par plomberie */
  public static final String SEARCH_HOUSING_BY_PLOMBERIE = " and h.plomberie = :plomberie";
  /** Critère de recherche par etiquette energetique */
  public static final String SEARCH_HOUSING_BY_ENERGY_LABEL = " and h.energyLabel = :energyLabel";
  /** Critère de recherche par Prix (Avenir du logement) */
  public static final String SEARCH_HOUSING_BY_PRIX_FUTUR_OF_HOUSING = " and h.futurOfHousingPrice like REPLACE(:futurOfHousingPrice, '.', ',')";
  /** Critère de recherche par dernière date de visite */
  public static final String SEARCH_HOUSING_BY_LAST_VISIT_DATE = " and h.lastVisitDate = :lastVisitDate";
  /** Critère de recherche par Emission de GES */
  public static final String SEARCH_HOUSING_BY_EMISSION_GES = " and h.emissionGes like REPLACE(:emissionGes, '.', ',')";
   /** Critère de recherche par coût de démolition */
  public static final String SEARCH_HOUSING_BY_COUT_DEMOLITION = " and h.coutDemolition like REPLACE(:coutDemolition, '.', ',')";
  /** Critère de recherche par coût de rénovation */
  public static final String SEARCH_HOUSING_BY_COUT_RENOVATION = " and h.coutRenovation like REPLACE(:coutRenovation, '.', ',')";
}
