package com.abita.dao.batch.gcp;

import java.math.BigDecimal;

/**
 * Une ligne pour une pièce comptable GCP
 * @author
 *
 */
public class GCPEntryLine {

  /** Le type de ligne */
  private String typeLigne;
  /** Compte */
  private String compte;
  /** Code clé de CGS */
  private String codeCleCGS;
  /** Clé de comptabilisation */
  private String cleComptabilisation;
  /** Montant devise pièce */
  private String montantDevisePiece;
  /** Montant devise pièce réel */
  private BigDecimal montantDevisePieceReel;
  /** Code TVA */
  private String codeTVA;
  /** Domaine d'activité */
  private String domaineActivite;
  /** Date de base */
  private String dateBase;
  /** Condition de paiement */
  private String conditionPaiement;
  /** Centre de coût */
  private String centreCout;
  /** Zone d'affection */
  private String zoneAffection;
  /** Texte */
  private String texte;

  /**
   * @return the typeLigne
   */
  public String getTypeLigne() {
    return typeLigne;
  }

  /**
   * @param typeLigne the typeLigne to set
   */
  public void setTypeLigne(String typeLigne) {
    this.typeLigne = typeLigne;
  }

  /**
   * @return the compte
   */
  public String getCompte() {
    return compte;
  }

  /**
   * @param compte the compte to set
   */
  public void setCompte(String compte) {
    this.compte = compte;
  }

  /**
   * @return the codeCleCGS
   */
  public String getCodeCleCGS() {
    return codeCleCGS;
  }

  /**
   * @param codeCleCGS the codeCleCGS to set
   */
  public void setCodeCleCGS(String codeCleCGS) {
    this.codeCleCGS = codeCleCGS;
  }

  /**
   * @return the cleComptabilisation
   */
  public String getCleComptabilisation() {
    return cleComptabilisation;
  }

  /**
   * @param cleComptabilisation the cleComptabilisation to set
   */
  public void setCleComptabilisation(String cleComptabilisation) {
    this.cleComptabilisation = cleComptabilisation;
  }

  /**
   * @return the montantDevisePiece
   */
  public String getMontantDevisePiece() {
    return montantDevisePiece;
  }

  /**
   * @param montantDevisePiece the montantDevisePiece to set
   */
  public void setMontantDevisePiece(String montantDevisePiece) {
    this.montantDevisePiece = montantDevisePiece;
  }

  /**
   * @return the codeTVA
   */
  public String getCodeTVA() {
    return codeTVA;
  }

  /**
   * @param codeTVA the codeTVA to set
   */
  public void setCodeTVA(String codeTVA) {
    this.codeTVA = codeTVA;
  }

  /**
   * @return the domaineActivite
   */
  public String getDomaineActivite() {
    return domaineActivite;
  }

  /**
   * @param domaineActivite the domaineActivite to set
   */
  public void setDomaineActivite(String domaineActivite) {
    this.domaineActivite = domaineActivite;
  }

  /**
   * @return the dateBase
   */
  public String getDateBase() {
    return dateBase;
  }

  /**
   * @param dateBase the dateBase to set
   */
  public void setDateBase(String dateBase) {
    this.dateBase = dateBase;
  }

  /**
   * @return the conditionPaiement
   */
  public String getConditionPaiement() {
    return conditionPaiement;
  }

  /**
   * @param conditionPaiement the conditionPaiement to set
   */
  public void setConditionPaiement(String conditionPaiement) {
    this.conditionPaiement = conditionPaiement;
  }

  /**
   * @return the centreCout
   */
  public String getCentreCout() {
    return centreCout;
  }

  /**
   * @param centreCout the centreCout to set
   */
  public void setCentreCout(String centreCout) {
    this.centreCout = centreCout;
  }

  /**
   * @return the zoneAffection
   */
  public String getZoneAffection() {
    return zoneAffection;
  }

  /**
   * @param zoneAffection the zoneAffection to set
   */
  public void setZoneAffection(String zoneAffection) {
    this.zoneAffection = zoneAffection;
  }

  /**
   * @return the texte
   */
  public String getTexte() {
    return texte;
  }

  /**
   * @param texte the texte to set
   */
  public void setTexte(String texte) {
    this.texte = texte;
  }

  /**
   * @return the montantDevisePieceReel
   */
  public BigDecimal getMontantDevisePieceReel() {
    return montantDevisePieceReel;
  }

  /**
   * @param montantDevisePieceReel the montantDevisePieceReel to set
   */
  public void setMontantDevisePieceReel(BigDecimal montantDevisePieceReel) {
    this.montantDevisePieceReel = montantDevisePieceReel;
  }
}
