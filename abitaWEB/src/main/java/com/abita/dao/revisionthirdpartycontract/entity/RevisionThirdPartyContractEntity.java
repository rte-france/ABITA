/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.revisionthirdpartycontract.entity;

import com.abita.util.dateutil.DateTimeUtils;
import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entité des révisions des contrats tiers
 */
public class RevisionThirdPartyContractEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = 5367703439592750125L;

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
   * @param amout the amount to set
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
