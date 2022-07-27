/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.abita.util.dateutil.DateTimeUtils;
import com.dto.AbstractDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Objet de transfert des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public class YlZnAccountingDocNumberDTO extends AbstractDTO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 11571151250590149L;

  /**
   * Numéro de la pièce comptable
   */
  private Long ylZnAdnPieceNumber;

  /**
   * Type de la pièce comptable (YL ou ZN)
   */
  private String ylZnAdnPieceType;

  /**
   * Date de génération de la pièce comptable
   */
  private Date ylZnAdnPieceDate;

  /** Date de génération du cycle concerné */
  private Date ylZnAdnCycleDate;

  /** Date de fin du contrat */
  private Date ylZnAdnCancellationDate;

  /** Montant du loyer mensuel */
  private BigDecimal ylZnAdnMensualRentAmount;

  /** Montant des charges prévisionnelles mensuelles */
  private BigDecimal ylZnAdnMensualExpectedChargeCost;

  /** Montant total du loyer du cycle */
  private BigDecimal ylZnAdnRentAmount;

  /** Montant total des charges prévisionnelles du cycle */
  private BigDecimal ylZnAdnExpectedChargeCost;

  /** Montant du loyer du cycle à payer */
  private BigDecimal ylZnAdnFinalRentAmount;

  /** Montant total des charges prévisionnelles du cycle à payer */
  private BigDecimal ylZnAdnFinalExpectedChargeCost;

  /**
   * Contrat tiers rattachés à la pièce comptable
   */
  private ThirdPartyContractDTO thirdPartyContract;

  /**
   * Getter du numéro de la pièce comptable
   * @return the ylZnAdnPieceNumber
   */
  public Long getYlZnAdnPieceNumber() {
    return ylZnAdnPieceNumber;
  }

  /**
   * Setter du numéro de la pièce comptable
   * @param ylZnAdnPieceNumber the ylZnAdnPieceNumber to set
   */
  public void setYlZnAdnPieceNumber(Long ylZnAdnPieceNumber) {
    this.ylZnAdnPieceNumber = ylZnAdnPieceNumber;
  }

  /**
   * Getter du type de la pièce comptable (YL ou ZN)
   * @return the ylZnAdnPieceType
   */
  public String getYlZnAdnPieceType() {
    return ylZnAdnPieceType;
  }

  /**
   * Setter du type de la pièce comptable (YL ou ZN)
   * @param ylZnAdnPieceType the ylZnAdnPieceType to set
   */
  public void setYlZnAdnPieceType(String ylZnAdnPieceType) {
    this.ylZnAdnPieceType = ylZnAdnPieceType;
  }

  /**
   * Getter de la date de génération de la pièce comptable
   * @return the ylZnAdnPieceDate
   */
  public Date getYlZnAdnPieceDate() {
    return DateTimeUtils.clone(ylZnAdnPieceDate);
  }

  /**
   * Setter de la date de génération de la pièce comptable
   * @param ylZnAdnPieceDate the ylZnAdnPieceDate to set
   */
  public void setYlZnAdnPieceDate(Date ylZnAdnPieceDate) {
    this.ylZnAdnPieceDate = DateTimeUtils.clone(ylZnAdnPieceDate);
  }

  /**
   * @return the ylZnAdnCycleDate
   */
  public Date getYlZnAdnCycleDate() {
    return DateTimeUtils.clone(ylZnAdnCycleDate);
  }

  /**
   * @param ylZnAdnCycleDate the ylZnAdnCycleDate to set
   */
  public void setYlZnAdnCycleDate(Date ylZnAdnCycleDate) {
    this.ylZnAdnCycleDate = DateTimeUtils.clone(ylZnAdnCycleDate);
  }

  /**
   * @return the ylZnAdnCancellationDate
   */
  public Date getYlZnAdnCancellationDate() {
    return DateTimeUtils.clone(ylZnAdnCancellationDate);
  }

  /**
   * @param ylZnAdnCancellationDate the ylZnAdnCancellationDate to set
   */
  public void setYlZnAdnCancellationDate(Date ylZnAdnCancellationDate) {
    this.ylZnAdnCancellationDate = DateTimeUtils.clone(ylZnAdnCancellationDate);
  }

  /**
   * @return the ylZnAdnMensualRentAmount
   */
  public BigDecimal getYlZnAdnMensualRentAmount() {
    return ylZnAdnMensualRentAmount;
  }

  /**
   * @param ylZnAdnMensualRentAmount the ylZnAdnMensualRentAmount to set
   */
  public void setYlZnAdnMensualRentAmount(BigDecimal ylZnAdnMensualRentAmount) {
    this.ylZnAdnMensualRentAmount = ylZnAdnMensualRentAmount;
  }

  /**
   * @return the ylZnAdnMensualExpectedChargeCost
   */
  public BigDecimal getYlZnAdnMensualExpectedChargeCost() {
    return ylZnAdnMensualExpectedChargeCost;
  }

  /**
   * @param ylZnAdnMensualExpectedChargeCost the ylZnAdnMensualExpectedChargeCost to set
   */
  public void setYlZnAdnMensualExpectedChargeCost(BigDecimal ylZnAdnMensualExpectedChargeCost) {
    this.ylZnAdnMensualExpectedChargeCost = ylZnAdnMensualExpectedChargeCost;
  }

  /**
   * @return the ylZnAdnRentAmount
   */
  public BigDecimal getYlZnAdnRentAmount() {
    return ylZnAdnRentAmount;
  }

  /**
   * @param ylZnAdnRentAmount the ylZnAdnRentAmount to set
   */
  public void setYlZnAdnRentAmount(BigDecimal ylZnAdnRentAmount) {
    this.ylZnAdnRentAmount = ylZnAdnRentAmount;
  }

  /**
   * @return the ylZnAdnExpectedChargeCost
   */
  public BigDecimal getYlZnAdnExpectedChargeCost() {
    return ylZnAdnExpectedChargeCost;
  }

  /**
   * @param ylZnAdnExpectedChargeCost the ylZnAdnExpectedChargeCost to set
   */
  public void setYlZnAdnExpectedChargeCost(BigDecimal ylZnAdnExpectedChargeCost) {
    this.ylZnAdnExpectedChargeCost = ylZnAdnExpectedChargeCost;
  }

  /**
   * @return the ylZnAdnFinalRentAmount
   */
  public BigDecimal getYlZnAdnFinalRentAmount() {
    return ylZnAdnFinalRentAmount;
  }

  /**
   * @param ylZnAdnFinalRentAmount the ylZnAdnFinalRentAmount to set
   */
  public void setYlZnAdnFinalRentAmount(BigDecimal ylZnAdnFinalRentAmount) {
    this.ylZnAdnFinalRentAmount = ylZnAdnFinalRentAmount;
  }

  /**
   * @return the ylZnAdnFinalExpectedChargeCost
   */
  public BigDecimal getYlZnAdnFinalExpectedChargeCost() {
    return ylZnAdnFinalExpectedChargeCost;
  }

  /**
   * @param ylZnAdnFinalExpectedChargeCost the ylZnAdnFinalExpectedChargeCost to set
   */
  public void setYlZnAdnFinalExpectedChargeCost(BigDecimal ylZnAdnFinalExpectedChargeCost) {
    this.ylZnAdnFinalExpectedChargeCost = ylZnAdnFinalExpectedChargeCost;
  }

  /**
   * Getter du contrat tiers rattachés à la pièce comptable
   * @return the thirdPartyContract
   */
  public ThirdPartyContractDTO getThirdPartyContract() {
    return thirdPartyContract;
  }

  /**
   * Setter du contrat tiers rattachés à la pièce comptable
   * @param thirdPartyContract the thirdPartyContract to set
   */
  public void setThirdPartyContract(ThirdPartyContractDTO thirdPartyContract) {
    this.thirdPartyContract = thirdPartyContract;
  }

}
