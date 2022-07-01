package com.abita.services.jobs.gcp.constants;

import com.abita.services.accountingcode.constants.AccountingTechnicalCodeConstants;

/**
 * Constantes utilisées pour les générations de type GCP
 */
public final class GcpConstants {

  /**
   * Constructeur privé
   */
  private GcpConstants() {
  }

  /** Le type de la pièce comptable GCP YL */
  public static final String TYPE_PIECE_YL = "YL";

  /** Le type de la pièce comptable GCP ZN */
  public static final String TYPE_PIECE_ZN = "ZN";

  /** Le type de la pièce comptable GCP NT */
  public static final String TYPE_PIECE_NT = "NT";

  /** Le type de la pièce comptable GCP NC */
  public static final String TYPE_PIECE_NC = "NC";

  /** Le type de la pièce comptable GCP YL et ZN */
  public static final String TYPE_PIECE_YL_ZN = "YL_ZN";

  /** Texte d’en-tête pour les paiements des pièces */
  public static final String HEADER_TEXT_YL = "Ecrit. pér.: ";

  /** Texte d’en-tête pour les remboursements */
  public static final String HEADER_TEXT_ZN = "Régularisation ";

  /** Texte d’en-tête pour les pièces des occupants salarié */
  public static final String HEADER_TEXT_EMPLOYE = "Quittance";

  /** Centre coût de : MECALRTE */
  public static final String CENTER_COST_RTE_LABEL = "MECALRTE";

  /** Centre coût externe : MECALTIR */
  public static final String CENTER_COST_TIR_LABEL = "MECALTIR";

  /** Type de ligne 1 */
  public static final String TYPE_LINE_1 = "1";

  /** Type de ligne 2 */
  public static final String TYPE_LINE_2 = "2";

  /** Position de la ligne "Poste de compte fournisseur" pour les pièces ZN */
  public static final int LOYER_COMPTE_GENERAL = 1;

  /** Position de la ligne "Poste de compte de charge loyer" pour les pièces ZN */
  public static final int LOYER_COMPTE_CHARGE = 2;

  /** Position de la ligne "Poste de compte fournisseur" prévisionnel pour les pièces ZN */
  public static final int CHARGES_COMPTE_GENERAL = 3;

  /** Position de la ligne "Poste de charges prévisionnelles" pour les pièces ZN */
  public static final int CHARGES_COMPTE_LOCATIVES = 4;

  /** Position de la ligne « Loyer – Poste de compte fournisseur » */
  public static final int RENT_SUPPLIER_LINE = 1;

  /** Position de la ligne « Loyer – Poste de compte de produit » */
  public static final int RENT_PRODUCT_LINE = 2;

  /** Position de la ligne « Charges locatives – Poste de compte fournisseur » */
  public static final int RENTAL_EXPENSES_SUPPLIER_LINE = 3;

  /** Position de la ligne « Charges locatives – Poste de compte de produit » */
  public static final int RENTAL_EXPENSES_PRODUCT_LINE = 4;

  /** Position de la ligne « Loyer garage – Poste de compte fournisseur » */
  public static final int GARAGE_SUPPLIER_LINE = 5;

  /** Position de la ligne « Loyer garage – Poste de compte de produit » */
  public static final int GARAGE_PRODUCT_LINE = 6;

  /** Trigramme ABI */
  public static final String ABI_TRIGRAM = "ABI";

  /** Le nombre de mois concernés pour un contrat mensuel */
  public static final int MONTHLY_ITERATIONS = 2;

  /** Le nombre de mois concernés pour un contrat trimestriel */
  public static final int QUARTERLY_ITERATIONS = 4;

  /** Le nombre de mois concernés pour un contrat annuel*/
  public static final int ANNUAL_ITERATIONS = 13;

  /** Type d’occupant salarié */
  public static final String TYPE_TENANT_EMPLOYEE = "Salarié";

  /** Code TVA */
  public static final String TVA_CODE_CL = "CL";

  /** Clé de comptabilisation pour un débit en défaveur du fournisseur/occupant */
  public static final String POSTING_KEY_21 = "21";

  /** Clé de comptabilisation pour un débit en défaveur du fournisseur/occupant */
  public static final String POSTING_KEY_29 = "29";

  /** Clé de comptabilisation pour un débit en faveur du fournisseur/occupant */
  public static final String POSTING_KEY_31 = "31";

  /** Clé de comptabilisation pour un débit en défaveur/faveur du fournisseur/occupant */
  public static final String POSTING_KEY_40 = "40";

  /** Clé de comptabilisation pour un débit en faveur du fournisseur/occupant */
  public static final String POSTING_KEY_39 = "39";

  /** Clé de comptabilisation pour un débit en défaveur/faveur du fournisseur/occupant */
  public static final String POSTING_KEY_50 = "50";

  /** Enumération des types de pièces GCP */
  public static enum GcpType {
    /** Pièce YL */
    YL,
    /** Pièce YL de charge ponctuelle */
    YL_FP,
    /** Pièce YL de régularisation */
    YL_REGUL,
    /** Pièce YL de révision */
    YL_REVI,
    /** Pièce ZN */
    ZN,
    /** Pièce NC */
    NC,
    /** Pièce NC de régularisation */
    NC_REGUL
  }

  /** Le numero de la compagnie pour les pièces comptables GCP */
  public static final String COMPANY_CODE = "5906";

  /** Enumération des types de charge ponctuelle */
  public static enum SporadicallyInvoicingType {

    /** Facturation de la consommation d’eau */
    WATER_INVOICING(WATER_INVOICING_LABEL),

    /** Facturation des ordures ménagères */
    GARBAGE_INVOICING(GARBAGE_INVOICING_LABEL),

    /** Remboursement assurance */
    INSURANCE_REIMBURSEMENT(INSURANCE_REIMBURSEMENT_LABEL),

    /** Remboursement taxe habitation */
    HOUSING_TAX_REIMBURSEMENT(HOUSING_TAX_REIMBURSEMENT_LABEL),

    /** Remboursement taxe ordures ménagères */
    GARBAGE_REIMBURSEMENT(GARBAGE_REIMBURSEMENT_LABEL),

    /** Apurement annuel des charges */
    ANNUAL_CLEARANCE_CHARGES(ANNUAL_CLEARANCE_CHARGES_LABEL),

    /** Autre */
    OTHER_INVOICING(OTHER_INVOICING_LABEL);

    /** Texte associé au type */
    private String label;

    /**
     * Constructeur
     * @param label texte du type
     */
    private SporadicallyInvoicingType(String label) {
      this.label = label;
    }

    /**
     * @return the label
     */
    public String getLabel() {
      return label;
    }

  }

  /** Facturation de la consommation d’eau */
  public static final String WATER_INVOICING_LABEL = "Factu conso d'eau";

  /** Facturation des ordures ménagères */
  public static final String GARBAGE_INVOICING_LABEL = "Factu ordures menagères";

  /** Facturation de la taxe d’habitation */
  public static final String HOUSING_TAX_INVOICING_LABEL = "Factu taxe habitation";

  /** Remboursement assurance */
  public static final String INSURANCE_REIMBURSEMENT_LABEL = "Remboursement assurance";

  /** Remboursement taxe habitation */
  public static final String HOUSING_TAX_REIMBURSEMENT_LABEL = "Remb taxe habitation";

  /** Remboursement taxe ordures ménagères */
  public static final String GARBAGE_REIMBURSEMENT_LABEL = "Remb taxe ordure ménagère";

  /** Apurement annuel des charges */
  public static final String ANNUAL_CLEARANCE_CHARGES_LABEL = "Apurement des charges";

  /** Autre */
  public static final String OTHER_INVOICING_LABEL = "Autre";

  /** Libellé Loyer - Poste de compte fournisseur */
  public static final String LABEL_RENT_SUPPLIER_ACCOUNT = "Loyer - Poste de compte fournisseur";

  /** Libellé Loyer - Poste de compte de produit */
  public static final String LABEL_RENT_PRODUCT_ACCOUNT = "Loyer - Poste de compte de produit";

  /** Libellé Charges prévisionelles - Poste de compte fournisseur */
  public static final String LABEL_RENTAL_EXPENSE_SUPPLIER_ACCOUNT = "Charges - Poste de compte fournisseur";

  /** Libellé Loyer Poste de compte fournisseur */
  public static final String LABEL_RENTAL_EXPENSE_PRODUCT_ACCOUNT = "Charges - Poste de compte produit";

  /** Libellé Loyer Poste de compte fournisseur */
  public static final String LABEL_GARAGE_RENT_SUPPLIER_ACCOUNT = "Garage - Poste de compte fournisseur";

  /** Libellé Loyer Poste de compte fournisseur */
  public static final String LABEL_GARAGE_RENT_PRODUCT_ACCOUNT = "Garage - Poste de compte produit";

  /** Enumération des comptes comptables YL */
  public static enum AccountingCodeYL {
    /** 464220 YL : Loyer - Poste de compte fournisseur */
    A(1, "YL", AccountingTechnicalCodeConstants.YL_LOYER_POSTE_COMPTE_FOURNISSEUR),
    /** 613250 YL : Loyer - Poste de compte de charge loyer */
    B(2, "YL", AccountingTechnicalCodeConstants.YL_LOYER_POSTE_COMPTE_CHARGE_LOYER),
    /** 464220 YL : Charges prévisionnelles - Poste de compte fournisseur */
    C(3, "YL", AccountingTechnicalCodeConstants.YL_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_FOURNISSEUR),
    /** 614110 YL : Charges prévisionnelles - Poste de compte de charges prévisionnelles */
    D(4, "YL", AccountingTechnicalCodeConstants.YL_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_CHARGE_PREVISIONNELLES);

    /** Numéro d’index */
    private Integer index;

    /** Nom de la pièce */
    private String nomPiece;

    /** Numéro de compte */
    private String numCompte;

    /**
     * Constructeur privé de l’enumération des comptes comptables YL
     * @param index Numéro d’ndex
     * @param nomPiece Nom de la pièce
     * @param numCompte Numéro de compte
     */
    private AccountingCodeYL(Integer index, String nomPiece, String numCompte) {
      this.index = index;
      this.nomPiece = nomPiece;
      this.numCompte = numCompte;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
      return index;
    }

    /**
     * @return the nomPiece
     */
    public String getNomPiece() {
      return nomPiece;
    }

    /**
     * @return the numCompte
     */
    public String getNumCompte() {
      return numCompte;
    }
  }

  /** Enumération des comptes comptables YL de charge ponctuelle */
  public static enum AccountingCodeYLSporadicallyInvoicing {
    /** 464220 */
    A(1, "YL", AccountingTechnicalCodeConstants.YL_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_FOURNISSEUR),
    /** 614110 */
    B(2, "YL", AccountingTechnicalCodeConstants.YL_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_CHARGE_PREVISIONNELLES);

    /** Numéro d’index */
    private Integer index;

    /** Nom de la pièce */
    private String nomPiece;

    /** Numéro de compte */
    private String numCompte;

    /**
     * Constructeur privé de l’enumération des comptes comptables YL de charge ponctuelle
     * @param index Numéro d’index
     * @param nomPiece Nom de la pièce
     * @param numCompte Numéro de compte
     */
    private AccountingCodeYLSporadicallyInvoicing(Integer index, String nomPiece, String numCompte) {
      this.index = index;
      this.nomPiece = nomPiece;
      this.numCompte = numCompte;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
      return index;
    }

    /**
     * @return the nomPiece
     */
    public String getNomPiece() {
      return nomPiece;
    }

    /**
     * @return the numCompte
     */
    public String getNumCompte() {
      return numCompte;
    }

  }

  /** Eumération des comptes comptables ZN */
  public static enum AccountingCodeZN {
    /** 486230 ZN : Loyer - Poste de compte général */
    A(1, "ZN", AccountingTechnicalCodeConstants.ZN_LOYER_POSTE_COMPTE_GENERAL),
    /** 613250 ZN : Loyer - Poste de compte de charge */
    B(2, "ZN", AccountingTechnicalCodeConstants.ZN_LOYER_POSTE_COMPTE_CHARGE),
    /** 486230 ZN : Charges prévisionnelles - Poste de compte général */
    C(3, "ZN", AccountingTechnicalCodeConstants.ZN_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_GENERAL),
    /** 614110 ZN : Charges prévisionnelles - Poste de compte de charge locatives */
    D(4, "ZN", AccountingTechnicalCodeConstants.ZN_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_CHARGE_LOCATIVE);

    /** Numéro d’index */
    private Integer index;

    /** Nom de la pièce */
    private String nomPiece;

    /** Numero de compte */
    private String numCompte;

    /**
     * Constructeur privé de l’enumération des comptes comptables ZN
     * @param index Numéro d’index
     * @param nomPiece Nom de la pièce
     * @param numCompte Numero de compte
     */
    private AccountingCodeZN(Integer index, String nomPiece, String numCompte) {
      this.index = index;
      this.nomPiece = nomPiece;
      this.numCompte = numCompte;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
      return index;
    }

    /**
     * @return the nomPiece
     */
    public String getNomPiece() {
      return nomPiece;
    }

    /**
     * @return the numCompte
     */
    public String getNumCompte() {
      return numCompte;
    }
  }

  /** Eumération des comptes comptables NC */
  public static enum AccountingCodeNC {
    /** 464210 NC : Loyer - Poste de compte fournisseur */
    A(1, "NC", AccountingTechnicalCodeConstants.NC_LOYER_POSTE_COMPTE_FOURNISSEUR),
    /** 708300 NC : Loyer - Poste de compte de produit */
    B(2, "NC", AccountingTechnicalCodeConstants.NC_LOYER_POSTE_COMPTE_PRODUIT),
    /** 464210 NC : Charges locatives - Poste de compte fournisseur */
    C(3, "NC", AccountingTechnicalCodeConstants.NC_CHARGES_LOCATIVES_POSTE_COMPTE_FOURNISSEUR),
    /** 708301 NC : Charges locatives - Poste de compte de produit */
    D(4, "NC", AccountingTechnicalCodeConstants.NC_CHARGES_LOCATIVES_POSTE_COMPTE_PRODUIT),
    /** 464210 NC : Loyer garage - Poste de compte fournisseur */
    E(5, "NC", AccountingTechnicalCodeConstants.NC_LOYER_GARAGE_POSTE_COMPTE_FOURNISSEUR),
    /** 708308 NC : Loyer garage - Poste de compte de produit */
    F(6, "NC", AccountingTechnicalCodeConstants.NC_LOYER_GARAGE_POSTE_COMPTE_PRODUIT);

    /** Numéro d’index */
    private Integer index;

    /** Nom de la pièce */
    private String nomPiece;

    /** Numéro de compte */
    private String numCompte;

    /**
     * Le constructeur privé de l’éumération des comptes comptables NT
     * @param index Numéro d’index
     * @param nomPiece Nom de la pièce
     * @param numCompte Numéro de compte
     */
    private AccountingCodeNC(Integer index, String nomPiece, String numCompte) {
      this.index = index;
      this.nomPiece = nomPiece;
      this.numCompte = numCompte;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
      return index;
    }

    /**
     * @return the nomPiece
     */
    public String getNomPiece() {
      return nomPiece;
    }

    /**
     * @return the numCompte
     */
    public String getNumCompte() {
      return numCompte;
    }
  }

  /** Eumération des comptes comptables NT */
  public static enum AccountingCodeNT {
    /**
     * Valeur originale attendue en base
     * 464210 NT : Loyer - Poste de compte fournisseur
     * 708301 NT : Loyer - Poste de compte de produit
     */
    A(1, "NT", AccountingTechnicalCodeConstants.NT_LOYER_POSTE_COMPTE_FOURNISSEUR), B(2, "NT", AccountingTechnicalCodeConstants.NT_LOYER_POSTE_COMPTE_PRODUIT);

    /** Numéro d’index */
    private Integer index;

    /** Nom de la pièce */
    private String nomPiece;

    /** Numéro de compte */
    private String numCompte;

    /**
     * Le constructeur privé de l’éumération des comptes comptables NT
     * @param index Numéro d’index
     * @param nomPiece Nom de la pièce
     * @param numCompte Numéro de compte
     */
    private AccountingCodeNT(Integer index, String nomPiece, String numCompte) {
      this.index = index;
      this.nomPiece = nomPiece;
      this.numCompte = numCompte;
    }

    /**
     * @return the index
     */
    public Integer getIndex() {
      return index;
    }

    /**
     * @return the nomPiece
     */
    public String getNomPiece() {
      return nomPiece;
    }

    /**
     * @return the numCompte
     */
    public String getNumCompte() {
      return numCompte;
    }
  }
}
