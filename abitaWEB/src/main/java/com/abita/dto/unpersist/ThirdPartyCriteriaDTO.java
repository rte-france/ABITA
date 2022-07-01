package com.abita.dto.unpersist;

import com.abita.dto.AgencyDTO;

import java.io.Serializable;

/**
 * Classe d'objet non persisté appellable depuis le controleur afin de passer les critères de recherche
 * @author
 *
 */
public class ThirdPartyCriteriaDTO implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1137614810579299359L;

  /** Référence */
  private Long id;

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

  /** Agence lors de la cloture du contrat, null si le contrat est encore en cours */
  private AgencyDTO fixedAgencyId;

  /**
   * Getter de la référence
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Setter de la référence
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Getter
   * @return the gcpReference
   */
  public String getGcpReference() {
    return gcpReference;
  }

  /**
   * Setter
   * @param gcpReference the gcpReference to set
   */
  public void setGcpReference(String gcpReference) {
    this.gcpReference = gcpReference;
  }

  /**
   * Getter
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Setter
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Setter
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * Getter
   * @return the postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * Setter
   * @param postalCode the postalCode to set
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * Getter
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * Setter
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * Getter
   * @return the phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * Setter
   * @param phone the phone to set
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Getter
   * @return the mailAddress
   */
  public String getMailAddress() {
    return mailAddress;
  }

  /**
   * Setter
   * @param mailAddress the mailAddress to set
   */
  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }

  /**
   * Getter
   * @return the beneficiaryName
   */
  public String getBeneficiaryName() {
    return beneficiaryName;
  }

  /**
   * Setter
   * @param beneficiaryName the beneficiaryName to set
   */
  public void setBeneficiaryName(String beneficiaryName) {
    this.beneficiaryName = beneficiaryName;
  }

  /**
   * Getter
   * @return the beneficiaryAddress
   */
  public String getBeneficiaryAddress() {
    return beneficiaryAddress;
  }

  /**
   * Setter
   * @param beneficiaryAddress the beneficiaryAddress to set
   */
  public void setBeneficiaryAddress(String beneficiaryAddress) {
    this.beneficiaryAddress = beneficiaryAddress;
  }

  /**
   *
   * @return the fixedAgencyId
     */
  public AgencyDTO getFixedAgencyId() {
    return fixedAgencyId;
  }

  /**
   *
   * @param fixedAgencyId the fixedAgencyId to set
     */
  public void setFixedAgencyId(AgencyDTO fixedAgencyId) {
    this.fixedAgencyId = fixedAgencyId;
  }
}
