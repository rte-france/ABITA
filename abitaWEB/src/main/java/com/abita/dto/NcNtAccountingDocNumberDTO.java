package com.abita.dto;

import com.abita.util.dateutil.DateTimeUtils;
import com.dto.AbstractDTO;

import java.util.Date;

/**
 * Objet de transfert des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public class NcNtAccountingDocNumberDTO extends AbstractDTO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 2273012788271146844L;

  /**
   * Numéro de la pièce comptable
   */
  private Long ncNtAdnPieceNumber;

  /**
   * Type de la pièce comptable (NC ou NT)
   */
  private String ncNtAdnPieceType;

  /**
   * Date de génération de la pièce comptable
   */
  private Date ncNtAdnPieceDate;

  /**
   * Contrat occupant rattachés à la pièce comptable
   */
  private ContractDTO contract;

  /**
   * Getter du numéro de la pièce comptable
   * @return the ncNtAdnPieceNumber
   */
  public Long getNcNtAdnPieceNumber() {
    return ncNtAdnPieceNumber;
  }

  /**
   * Setter du numéro de la pièce comptable
   * @param ncNtAdnPieceNumber the ncNtAdnPieceNumber to set
   */
  public void setNcNtAdnPieceNumber(Long ncNtAdnPieceNumber) {
    this.ncNtAdnPieceNumber = ncNtAdnPieceNumber;
  }

  /**
   * Getter du type de la pièce comptable (NC ou NT)
   * @return the ncNtAdnPieceType
   */
  public String getNcNtAdnPieceType() {
    return ncNtAdnPieceType;
  }

  /**
   * Setter du type de la pièce comptable (NC ou NT)
   * @param ncNtAdnPieceType the ncNtAdnPieceType to set
   */
  public void setNcNtAdnPieceType(String ncNtAdnPieceType) {
    this.ncNtAdnPieceType = ncNtAdnPieceType;
  }

  /**
   * Getter de la date de génération de la pièce comptable
   * @return the ncNtAdnPieceDate
   */
  public Date getNcNtAdnPieceDate() {
    return DateTimeUtils.clone(ncNtAdnPieceDate);
  }

  /**
   * Setter de la date de génération de la pièce comptable
   * @param ncNtAdnPieceDate the ncNtAdnPieceDate to set
   */
  public void setNcNtAdnPieceDate(Date ncNtAdnPieceDate) {
    this.ncNtAdnPieceDate = DateTimeUtils.clone(ncNtAdnPieceDate);
  }

  /**
   * Getter du contrat occupant rattachés à la pièce comptable
   * @return the contract
   */
  public ContractDTO getContract() {
    return contract;
  }

  /**
   * Setter du contrat occupant rattachés à la pièce comptable
   * @param contract the contract to set
   */
  public void setContract(ContractDTO contract) {
    this.contract = contract;
  }

}