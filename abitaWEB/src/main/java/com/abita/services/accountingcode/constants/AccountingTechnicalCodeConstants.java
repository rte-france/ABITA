/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.accountingcode.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Classe des constantes utilisées pour les comptes comptables des fichiers GCP
 */
public final class AccountingTechnicalCodeConstants {

  /**
   * Constructeur privé
   */
  private AccountingTechnicalCodeConstants() {
  }

  /** Code technique pour YL : Loyer - Poste de compte fournisseur */
  public static final String YL_LOYER_POSTE_COMPTE_FOURNISSEUR = "YLLOYPCFOU";
  /** Code technique pour YL : Loyer - Poste de compte de charge loyer */
  public static final String YL_LOYER_POSTE_COMPTE_CHARGE_LOYER = "YLLOYPCCLO";
  /** Code technique pour YL : Charges prévisionnelles - Poste de compte fournisseur */
  public static final String YL_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_FOURNISSEUR = "YLCHPPCFOU";
  /** Code technique pour YL : Charges prévisionnelles - Poste de compte de charges prévisionnelles */
  public static final String YL_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_CHARGE_PREVISIONNELLES = "YLCHPPCCHP";
  /** Code technique pour YL : Charges prévisionnelles (charge ponctuelle) - Poste de compte fournisseur */
  public static final String YL_CHARCHES_PREVISIONNELLES_FACTURATION_PONCTUELLE_POSTE_COMPTE_FOURNISSEUR = "YLCFPPCFOU";
  /** Code technique pour YL : Charges prévisionnelles (charge ponctuelle) - Poste de compte de charges prévisionnelles */
  public static final String YL_CHARCHES_PREVISIONNELLES_FACTURATION_PONCTUELLE_POSTE_COMPTE_CHARGE_PREVISIONNELLES = "YLCFPPCCHP";
  /** Code technique pour ZN : Loyer - Poste de compte général */
  public static final String ZN_LOYER_POSTE_COMPTE_GENERAL = "ZNLOYPCGEN";
  /** Code technique pour ZN : Loyer - Poste de compte de charge */
  public static final String ZN_LOYER_POSTE_COMPTE_CHARGE = "ZNLOYPCCHP";
  /** Code technique pour ZN : Charges prévisionnelles - Poste de compte général */
  public static final String ZN_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_GENERAL = "ZNCHPPCGEN";
  /** Code technique pour ZN : Charges prévisionnelles - Poste de compte de charge locatives */
  public static final String ZN_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_CHARGE_LOCATIVE = "ZNCHPPCCLO";
  /** Code technique pour NC : Loyer - Poste de compte fournisseur */
  public static final String NC_LOYER_POSTE_COMPTE_FOURNISSEUR = "NCLOYPCFOU";
  /** Code technique pour NC : Loyer - Poste de compte de produit */
  public static final String NC_LOYER_POSTE_COMPTE_PRODUIT = "NCLOYPCPRO";
  /** Code technique pour NC : Charges locatives - Poste de compte fournisseur */
  public static final String NC_CHARGES_LOCATIVES_POSTE_COMPTE_FOURNISSEUR = "NCCLOPCFOU";
  /** Code technique pour NC : Charges locatives - Poste de compte de produit */
  public static final String NC_CHARGES_LOCATIVES_POSTE_COMPTE_PRODUIT = "NCCLOPCPRO";
  /** Code technique pour NC : Loyer garage - Poste de compte fournisseur */
  public static final String NC_LOYER_GARAGE_POSTE_COMPTE_FOURNISSEUR = "NCLGAPCFOU";
  /** Code technique pour NC : Loyer garage - Poste de compte de produit */
  public static final String NC_LOYER_GARAGE_POSTE_COMPTE_PRODUIT = "NCLGAPCPRO";
  /** Code technique pour NT : Loyer - Poste de compte fournisseur */
  public static final String NT_LOYER_POSTE_COMPTE_FOURNISSEUR = "NTLOYPCFOU";
  /** Code technique pour NT : Loyer - Poste de compte de produit */
  public static final String NT_LOYER_POSTE_COMPTE_PRODUIT = "NTLOYPCPRO";

  /** Liste de codes techniques pour les pièces YL (hors charge ponctuelle) */
  public static final List<String> YL_TECHNICAL_CODE_LIST = Arrays.asList(YL_LOYER_POSTE_COMPTE_FOURNISSEUR, YL_LOYER_POSTE_COMPTE_CHARGE_LOYER,
    YL_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_FOURNISSEUR, YL_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_CHARGE_PREVISIONNELLES);

  /** Liste de codes techniques pour les pièces YL pour charge ponctuelle */
  public static final List<String> YL_SPORADICALLY_INVOICING_TECHNICAL_CODE_LIST = Arrays.asList(YL_CHARCHES_PREVISIONNELLES_FACTURATION_PONCTUELLE_POSTE_COMPTE_FOURNISSEUR,
    YL_CHARCHES_PREVISIONNELLES_FACTURATION_PONCTUELLE_POSTE_COMPTE_CHARGE_PREVISIONNELLES);

  /** Liste de codes techniques pour les pièces ZN */
  public static final List<String> ZN_TECHNICAL_CODE_LIST = Arrays.asList(ZN_LOYER_POSTE_COMPTE_GENERAL, ZN_LOYER_POSTE_COMPTE_CHARGE,
    ZN_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_GENERAL, ZN_CHARCHES_PREVISIONNELLES_POSTE_COMPTE_CHARGE_LOCATIVE);

  /** Liste de codes techniques pour les pièces NC */
  public static final List<String> NC_TECHNICAL_CODE_LIST = Arrays.asList(NC_LOYER_POSTE_COMPTE_FOURNISSEUR, NC_LOYER_POSTE_COMPTE_PRODUIT,
    NC_CHARGES_LOCATIVES_POSTE_COMPTE_FOURNISSEUR, NC_CHARGES_LOCATIVES_POSTE_COMPTE_PRODUIT, NC_LOYER_GARAGE_POSTE_COMPTE_FOURNISSEUR, NC_LOYER_GARAGE_POSTE_COMPTE_PRODUIT);

  /** Liste de codes techniques pour les pièces NT */
  public static final List<String> NT_TECHNICAL_CODE_LIST = Arrays.asList(NT_LOYER_POSTE_COMPTE_FOURNISSEUR, NT_LOYER_POSTE_COMPTE_PRODUIT);

}
