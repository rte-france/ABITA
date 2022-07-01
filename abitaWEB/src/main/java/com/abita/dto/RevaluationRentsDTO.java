package com.abita.dto;

import com.abita.util.dateutil.DateTimeUtils;
import com.abita.web.shared.ConstantsWEB;
import com.dto.AbstractDTO;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO Revalorisation des loyers
 */
public class RevaluationRentsDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = -3121023908597783015L;

  /** Taux */
  @NotNull(message = "{referentiel.revaluationrents.error.mandatory.rent}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{referentiel.revaluationrents.error.format.rent}")
  private BigDecimal rate;

  /** Date de dernière mise à jour */
  private Date lastUpdated;

  /** Action */
  private Boolean action;

  /**
   * @return the rate
   */
  public BigDecimal getRate() {
    return rate;
  }

  /**
   * @param rate the rate to set
   */
  public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  /**
   * @return the lastUpdated
   */
  public Date getLastUpdated() {
    return DateTimeUtils.clone(lastUpdated);
  }

  /**
   * @param lastUpdated the lastUpdated to set
   */
  public void setLastUpdated(Date lastUpdated) {
    this.lastUpdated = DateTimeUtils.clone(lastUpdated);
  }

  /**
   * @return the action
   */
  public Boolean getAction() {
    return action;
  }

  /**
   * @param action the action to set
   */
  public void setAction(Boolean action) {
    this.action = action;
  }

}
