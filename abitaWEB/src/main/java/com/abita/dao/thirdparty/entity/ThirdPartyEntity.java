/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.thirdparty.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Entité tiers
 * @author
 *
 */
public class ThirdPartyEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = 128880200175748441L;

  /** Référence GCP */
  private String gcpReference;

  /** Nom */
  private String name;

  /** Adresse */
  private String address;

  /** Code postal */
  private String postalCode;

  /** Ville */
  private String city;

  /** Téléphone */
  private String phone;

  /** Adresse mail */
  private String mailAddress;

  /** Nom du bénéficiaire du paiement */
  private String beneficiaryName;

  /** Adresse du bénéficiaire du paiement */
  private String beneficiaryAddress;

  /**
   * @return the gcpReference
   */
  public String getGcpReference() {
    return gcpReference;
  }

  /**
   * @param gcpReference the gcpReference to set
   */
  public void setGcpReference(String gcpReference) {
    this.gcpReference = gcpReference;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
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
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @return the mailAddress
   */
  public String getMailAddress() {
    return mailAddress;
  }

  /**
   * @param mailAddress the mailAddress to set
   */
  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }

  /**
   * @return the beneficiaryName
   */
  public String getBeneficiaryName() {
    return beneficiaryName;
  }

  /**
   * @param beneficiaryName the beneficiaryName to set
   */
  public void setBeneficiaryName(String beneficiaryName) {
    this.beneficiaryName = beneficiaryName;
  }

  /**
   * @return the beneficiaryAddress
   */
  public String getBeneficiaryAddress() {
    return beneficiaryAddress;
  }

  /**
   * @param beneficiaryAddress the beneficiaryAddress to set
   */
  public void setBeneficiaryAddress(String beneficiaryAddress) {
    this.beneficiaryAddress = beneficiaryAddress;
  }

}
