/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.tenant.entity;

import com.abita.dao.typetenant.entity.TypeTenantEntity;
import com.dao.common.entity.AbstractEntity;

import java.math.BigDecimal;

/**
 * Entité occupant
 *
 * @author
 */
public class TenantEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -8787318716398754290L;

  /** Référence */
  private String reference;

  /** Prénom */
  private String firstName;

  /** Nom */
  private String lastName;

  /** Adresse */
  private String address;

  /** Code postal */
  private String postalCode;

  /** Ville */
  private String city;

  /** Cadre */
  private Boolean managerial;

  /** Cadre à N-1 */
  private Boolean managerialLastYear;

  /** Nombre de personne dans le foyer */
  private Integer householdSize;

  /** Nombre de personne dans le foyer à N-1 */
  private Integer householdSizeLastYear;

  /** Salaire brut */
  private BigDecimal actualSalary;

  /** Salaire brut à N-1 */
  private BigDecimal referenceGrossSalary;

  /** Téléphone */
  private String phone;

  /** Adresse mail */
  private String mailAddress;

  /** Type d'occupant */
  private TypeTenantEntity typeTenant;

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
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return the postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * @param postalCode the postalCode to set
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the householdSize
   */
  public Integer getHouseholdSize() {
    return householdSize;
  }

  /**
   * @param householdSize the householdSize to set
   */
  public void setHouseholdSize(Integer householdSize) {
    this.householdSize = householdSize;
  }

  /**
   * @return the householdSizeLastYear
   */
  public Integer getHouseholdSizeLastYear() {
    return householdSizeLastYear;
  }

  /**
   * @param householdSizeLastYear the householdSizeLastYear to set
   */
  public void setHouseholdSizeLastYear(Integer householdSizeLastYear) {
    this.householdSizeLastYear = householdSizeLastYear;
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


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getMailAddress() {
    return mailAddress;
  }

  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }

  /**
   * @return the typeTenant
   */
  public TypeTenantEntity getTypeTenant() {
    return typeTenant;
  }

  /**
   * @param typeTenant the typeTenant to set
   */
  public void setTypeTenant(TypeTenantEntity typeTenant) {
    this.typeTenant = typeTenant;
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
   * @return the managerialLastYear
   */
  public Boolean getManagerialLastYear() {
    return managerialLastYear;
  }

  /**
   * @param managerialLastYear the managerialLastYear to set
   */
  public void setManagerialLastYear(Boolean managerialLastYear) {
    this.managerialLastYear = managerialLastYear;
  }

}
