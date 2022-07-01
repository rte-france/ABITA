package com.abita.dao.batch.gcp;

/**
 * Un header pour une pièce comptable GCP
 * @author
 *
 */
public class GCPHeader {

  /** Type de ligne */
  private String typeLigne;
  /** Date de la pièce comptable */
  private String datePiece;
  /** Le type de pièce comptable */
  private String typePiece;
  /** Société */
  private String societe;
  /** Devise de la pièce comptable */
  private String devisePiece;
  /** Date comptable */
  private String dateComptable;
  /** Numéro de pièce comptable */
  private String numPieceComptable;
  /** Texte d'en-tête */
  private String texteEntete;
  /** La référence */
  private String reference;

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
   * @return the datePiece
   */
  public String getDatePiece() {
    return datePiece;
  }

  /**
   * @param datePiece the datePiece to set
   */
  public void setDatePiece(String datePiece) {
    this.datePiece = datePiece;
  }

  /**
   * @return the typePiece
   */
  public String getTypePiece() {
    return typePiece;
  }

  /**
   * @param typePiece the typePiece to set
   */
  public void setTypePiece(String typePiece) {
    this.typePiece = typePiece;
  }

  /**
   * @return the societe
   */
  public String getSociete() {
    return societe;
  }

  /**
   * @param societe the societe to set
   */
  public void setSociete(String societe) {
    this.societe = societe;
  }

  /**
   * @return the devisePiece
   */
  public String getDevisePiece() {
    return devisePiece;
  }

  /**
   * @param devisePiece the devisePiece to set
   */
  public void setDevisePiece(String devisePiece) {
    this.devisePiece = devisePiece;
  }

  /**
   * @return the dateComptable
   */
  public String getDateComptable() {
    return dateComptable;
  }

  /**
   * @param dateComptable the dateComptable to set
   */
  public void setDateComptable(String dateComptable) {
    this.dateComptable = dateComptable;
  }

  /**
   * @return the numPieceComptable
   */
  public String getNumPieceComptable() {
    return numPieceComptable;
  }

  /**
   * @param numPieceComptable the numPieceComptable to set
   */
  public void setNumPieceComptable(String numPieceComptable) {
    this.numPieceComptable = numPieceComptable;
  }

  /**
   * @return the texteEntete
   */
  public String getTexteEntete() {
    return texteEntete;
  }

  /**
   * @param texteEntete the texteEntete to set
   */
  public void setTexteEntete(String texteEntete) {
    this.texteEntete = texteEntete;
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @param reference the reference to set
   */
  public void setReference(String reference) {
    this.reference = reference;
  }

}
