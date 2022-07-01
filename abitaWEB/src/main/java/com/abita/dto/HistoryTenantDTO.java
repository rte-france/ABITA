package com.abita.dto;

import com.dto.AbstractDTO;

import java.math.BigDecimal;

/**
 *  DTO des historisations des occupants
 * @author
 *
 */
public class HistoryTenantDTO extends AbstractDTO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1333818163377985051L;

  /** Identifiant */
  private Long tenantId;

  /** Mois d’historisation */
  private Integer month;

  /** Année d’historisation */
  private Integer year;

  /** Référence */
  private String reference;

  /** Cadre */
  private Boolean managerial;

  /** Label du type d’occupant */
  private String typeTenantHeaderLabel;

  /** Salaire brut */
  private BigDecimal actualSalary;

  /** Salaire brut à N-1 */
  private BigDecimal referenceGrossSalary;

  /** Historisation temporaire */
  private Boolean temp;

  /**
   * @return the tenantId
   */
  public Long getTenantId() {
    return tenantId;
  }

  /**
   * @param tenantId the tenantId to set
   */
  public void setTenantId(Long tenantId) {
    this.tenantId = tenantId;
  }

  /**
   * @return the month
   */
  public Integer getMonth() {
    return month;
  }

  /**
   * @param month the month to set
   */
  public void setMonth(Integer month) {
    this.month = month;
  }

  /**
   * @return the year
   */
  public Integer getYear() {
    return year;
  }

  /**
   * @param year the year to set
   */
  public void setYear(Integer year) {
    this.year = year;
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

  /**
   * @return the managerial
   */
  public Boolean getManagerial() {
    return managerial;
  }

  /**
   * @param managerial the managerial to set
   */
  public void setManagerial(Boolean managerial) {
    this.managerial = managerial;
  }

  /**
   * @return the typeTenantHeaderLabel
   */
  public String getTypeTenantHeaderLabel() {
    return typeTenantHeaderLabel;
  }

  /**
   * @param typeTenantHeaderLabel the typeTenantHeaderLabel to set
   */
  public void setTypeTenantHeaderLabel(String typeTenantHeaderLabel) {
    this.typeTenantHeaderLabel = typeTenantHeaderLabel;
  }

  /**
   * @return the actualSalary
   */
  public BigDecimal getActualSalary() {
    return actualSalary;
  }

  /**
   * @param actualSalary the actualSalary to set
   */
  public void setActualSalary(BigDecimal actualSalary) {
    this.actualSalary = actualSalary;
  }

  /**
   * @return the referenceGrossSalary
   */
  public BigDecimal getReferenceGrossSalary() {
    return referenceGrossSalary;
  }

  /**
   * @param referenceGrossSalary the referenceGrossSalary to set
   */
  public void setReferenceGrossSalary(BigDecimal referenceGrossSalary) {
    this.referenceGrossSalary = referenceGrossSalary;
  }

  /**
   * @return the temp
   */
  public Boolean getTemp() {
    return temp;
  }

  /**
   * @param temp the temp to set
   */
  public void setTemp(Boolean temp) {
    this.temp = temp;
  }
}
