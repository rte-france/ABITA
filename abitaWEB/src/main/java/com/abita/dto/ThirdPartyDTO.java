package com.abita.dto;

import com.abita.util.sort.DatatablePropertiesSorted;
import com.dto.AbstractDTO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * DTO tiers
 * @author
 *
 */
public class ThirdPartyDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 128880200175748441L;

  /** Référence GCP */
  private String gcpReference;

  /** Nom */
  @NotBlank(message = "{thirdparty.creation.error.name}")
  private String name;

  /** Adresse */
  @NotBlank(message = "{thirdparty.creation.error.adress}")
  private String address;

  /** Code postal */
  @NotBlank(message = "{thirdparty.creation.error.cp}")
  private String postalCode;

  /** Ville */
  @NotBlank(message = "{thirdparty.creation.error.city}")
  private String city;

  /** Téléphone */
  private String phone;

  /** Adresse mail */
  @Pattern(regexp = "^([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))?$", message = "{thirdparty.creation.wrongformat.email}")
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
   * Renvoi la reeference GCP s'elle n'est pas vide ou null. Si c'est le cas renvoi une valeur plaçant celles-ci après les dernières valeurs.
   * @return the gcpReference
   */
  public String getSortedGcpReference() {
    return DatatablePropertiesSorted.getSortedString(gcpReference);
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
   * Renvoi le téléphone s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant ceux-ci après les z.
   * @return the phone
   */
  public String getSortedPhone() {

    return DatatablePropertiesSorted.getSortedString(phone);
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
   * Renvoi l'adresse mail si elle n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les z.
   * @return the mailAddress
   */
  public String getSortedMailAddress() {
    return DatatablePropertiesSorted.getSortedString(mailAddress);
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
   * Renvoi le nom du bénéficiaire s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant ceux-ci après les z.
   * @return the beneficiaryName
   */
  public String getSortedBeneficiaryName() {
    return DatatablePropertiesSorted.getSortedString(beneficiaryName);
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
   * Renvoi l'adresse du bénéficiaire si elle n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les z.
   * @return the beneficiaryName
   */
  public String getSortedBeneficiaryAddress() {
    return DatatablePropertiesSorted.getSortedString(beneficiaryAddress);
  }

  /**
   * @param beneficiaryAddress the beneficiaryAddress to set
   */
  public void setBeneficiaryAddress(String beneficiaryAddress) {
    this.beneficiaryAddress = beneficiaryAddress;
  }

}
