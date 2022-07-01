package com.abita.dto;

import com.abita.util.dateutil.DateTimeUtils;
import com.dto.AbstractDTO;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO révision des contrats tiers
 */
public class RevisionThirdPartyContractDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 3057998998390027376L;

  /** Montant */
  private BigDecimal amount;

  /** Date d’effet */
  private Date date;

  /** Type de révision */
  private String type;

  /** Identifiant du contrat tiers */
  private Long thirdPartyContractId;

  /**
   * @return the amount
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * @param amount the amount to set
   */
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  /**
   * @return the date
   */
  public Date getDate() {
    return DateTimeUtils.clone(date);
  }

  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = DateTimeUtils.clone(date);
  }

  /**
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return the thirdPartyContractId
   */
  public Long getThirdPartyContractId() {
    return thirdPartyContractId;
  }

  /**
   * @param thirdPartyContractId the thirdPartyContractId to set
   */
  public void setThirdPartyContractId(Long thirdPartyContractId) {
    this.thirdPartyContractId = thirdPartyContractId;
  }

}
