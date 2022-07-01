package com.abita.services.jobs.artesis.constants;

/**
 * Constantes utilisées pour les générations de type Artesis
 */
public final class ArtesisConstants {

  /**
   * Constructeur privé
   */
  private ArtesisConstants() {
  }

  /** Société de gestion */
  public static final String MANAGEMENT_COMPANY_CODE = "5906";

  /** Nombre de chiffres à afficher dans le montant du fichier de retenu sur paie */
  public static final int PRICE_LENGTH_ARTESIS = 7;

  /** Nombre de caractères à afficher pour la référence de l’occupant */
  public static final int REFERENCE_TENANT_LENGTH = 8;

  /** Code de l’application 1 d’origine */
  public static final String ORIGIN_APPLICATION_1_CODE = "L1";

  /** Code de l’application 2 d’origine */
  public static final String ORIGIN_APPLICATION_2_CODE = "L2";

  /** Type d’enregistrement */
  public static final String RECORD_TYPE = "M";

  /** Code rubrique de paie du loyer net de charge*/
  public static final Integer PAY_CODE_NET_RENT = 290;

  /** Code rubrique de paie des charges locatives*/
  public static final Integer PAY_CODE_RENTAL_CHARGES = 291;

  /** Code rubrique de paie de remboursement*/
  public static final Integer PAY_CODE_REIMBURSEMENT = 292;

  /** Origine de la VLF */
  public static final String REAL_ESTATE_RENTAL_VALUE_ORIGIN = "VL";

  /** Nombre de zero à ajouter pour un champ monnétaire vide */
  public static final int NUMBER_ZERO_ON_EMPTY_MONETARY_FIELD = 7;

  /** Nombre de zero à ajouter pour le champ nombre de piecce si il est vide */
  public static final int NUMBER_ZERO_ON_EMPTY_ROOM_COUNT_FIELD = 2;

  /** Le type de génération pour le fichier de retenue sur salaire*/
  public static final String TYPE_RETAINED_SALARY = "RETAINED";

  /** Le type de génération pour le fichier des avantages en nature*/
  public static final String TYPE_BENEFITS = "BENEFITS";

  /** Libellé du montant de la retenue sur paie pour le code 290 : loyer net de charge */
  public static final String RETAINED_SALARY_NET_RENT_LABEL = "Loyer net de charge";

  /** Libellé du montant de la retenue sur paie pour le code 291 : charges locatives et charges liées au logement  */
  public static final String RETAINED_SALARY_RENTAL_CHARGES_LABEL = "Charges locatives et charges liées au logement";

  /** Libellé du montant de la VLF dans le fichier des avantages en nature */
  public static final String BENEFITS_REAL_ESTATE_RENTAL_VALUE_LABEL = "Valeur mensuel de la VLF";

  /** Libellé du montant du forfait mensuel dans le fichier des avantages en nature */
  public static final String BENEFITS_AMOUNT_LABEL = "Forfait mensuel";

  /** Libellé du montant net du loyer mensuel dans le fichier des avantages en nature*/
  public static final String BENEFITS_WITHDRAWN_RENT_LABEL = "Montant net du loyer mensuel";

  /** Pattern pour l'historisation des montants */
  public static final String PATTERN_HISTORY_AMOUNT_DETAIL = "{0}:{1};";

}
