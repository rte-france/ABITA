/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.sort.DatatablePropertiesSorted;
import com.abita.dto.constant.DTOConstants;
import com.abita.web.shared.ConstantsWEB;
import com.dto.AbstractDTO;
import com.web.common.validators.NullableDigits;
import com.web.common.validators.NullableMin;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.math.BigDecimal;

/**
 * DTO de l'occupant
 *
 * @author
 */
public class TenantDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 8236116101131489627L;

  /** Référence */
  @NotBlank(message = "{tenant.creation.error.reference}")
  private String reference;

  /** Prénom */
  @NotBlank(message = "{tenant.creation.error.firstname}")
  private String firstName;

  /** Nom */
  @NotBlank(message = "{tenant.creation.error.name}")
  private String lastName;

  /** Adresse */
  private String address;

  /** Code postal */
  @Pattern(regexp = "^([0-9]{5})?$", message = "{tenant.creation.error.cp.wrong.format}")
  private String postalCode;

  /** Ville */
  private String city;

  /** Cadre */
  private Boolean managerial;

  /** Cadre à N-1 */
  private Boolean managerialLastYear;

  /** Nombre de personne dans le foyer */
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_3, fraction = ConstantsWEB.DECIMAL_PART_SIZE_NONE, message = "{tenant.creation.error.household.integer}")
  @Min(value = 0, message = "{tenant.creation.error.household.integer}")
  private Integer householdSize;

  /** Nombre de personne dans le foyer à N-1 */
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_3, fraction = ConstantsWEB.DECIMAL_PART_SIZE_NONE, message = "{tenant.creation.error.household.lastyear.integer}")
  @Min(value = 0, message = "{tenant.creation.error.household.lastyear.integer}")
  private Integer householdSizeLastYear;

  /**
   * Salaire actuel
   * Règles de validation : {@link #getActualSalaryAsString}
   */
  private BigDecimal actualSalary;

  /**
   * Salaire brut à N-1
   * Règles de validation : {@link #getReferenceGrossSalaryAsString}
   */
  private BigDecimal referenceGrossSalary;

  /** Téléphone */
  private String phone;

  /** Adresse mail */
  @Pattern(regexp = "^([_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))?$", message = "{tenant.creation.error.wrongformat.email}")
  private String mailAddress;

  /** Type d'occupant */
  @NotNull(message = "{tenant.creation.error.type}")
  private TypeTenantDTO typeTenant;

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
   * Renvoi l'adresse s'elle n'est pas vide ou null. Si c'est le cas renvoi une valeur plaçant celles-ci après les dernières valeurs.
   * @return the address
   */
  public String getSortedAddress() {
    return DatatablePropertiesSorted.getSortedString(address);

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
   * Renvoi le code postal s'il n'est pas vide ou null. Si c'est le cas renvoi une valeur plaçant celles-ci après les dernières valeurs.
   * @return the postalCode
   */
  public String getSortedPostalCode() {
    return DatatablePropertiesSorted.getSortedString(postalCode);
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
   * Renvoi la ville s'il n'est pas vide ou null. Si c'est le cas renvoi une valeur plaçant celles-ci après les dernières valeurs.
   * @return the city
   */
  public String getSortedCity() {
    return DatatablePropertiesSorted.getSortedString(city);
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the managerial
   */
  public Boolean getManagerial() {
    return managerial;
  }

  /**
   * Renvoi cadre s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers cadres.
   * @return Cadre
   */
  public String getSortedManagerial() {
    if (null == managerial) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(managerial.toString());
    }
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
   * Renvoi le cadre à n-1 s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers  cadre à n-1.
   * @return le cadre à n-1
   */
  public String getSortedManagerialLastYear() {
    if (null == managerialLastYear) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(managerialLastYear.toString());
    }
  }

  /**
   * @param managerialLastYear the managerialLastYear to set
   */
  public void setManagerialLastYear(Boolean managerialLastYear) {
    this.managerialLastYear = managerialLastYear;
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
   * Renvoi le salaire actuel formaté s'il n'est pas vide ou null. Si c'est le cas renvoi une valeur plaçant celles-ci après les dernières valeurs.
   * @return the guaranteedDepositAmount
   */
  public BigDecimal getSortedActualSalary() {
    return DatatablePropertiesSorted.getSortedBigDecimal(actualSalary);

  }

  /**
   * @param actualSalary the actualSalary to set
   */
  public void setActualSalary(BigDecimal actualSalary) {
    this.actualSalary = actualSalary;
  }

  /**
   * @return the actualSalary
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{tenant.creation.error.actualSalary}")
  @NullableMin(value = 0, message = "{tenant.creation.error.actualSalary}")
  public String getActualSalaryAsString() {
    return BigDecimalUtils.bigDecimalToString(actualSalary);
  }

  /**
   * @param actualSalaryString the actualSalary to set
   */
  public void setActualSalaryAsString(String actualSalaryString) {
    actualSalary = BigDecimalUtils.stringToBigDecimal(actualSalaryString);
  }

  /**
   * @return the referenceGrossSalary
   */
  public BigDecimal getReferenceGrossSalary() {
    return referenceGrossSalary;
  }

  /**
   * Renvoi le salaire à octobre N-1 formaté s'il n'est pas vide ou null. Si c'est le cas renvoi une valeur plaçant celles-ci après les dernières valeurs.
   * @return the guaranteedDepositAmount
   */
  public BigDecimal getSortedReferenceGrossSalary() {
    return DatatablePropertiesSorted.getSortedBigDecimal(referenceGrossSalary);

  }

  /**
   * @param referenceGrossSalary the referenceGrossSalary to set
   */
  public void setReferenceGrossSalary(BigDecimal referenceGrossSalary) {
    this.referenceGrossSalary = referenceGrossSalary;
  }

  /**
   * @return the referenceGrossSalary
   */
  @NullableDigits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{tenant.creation.error.referenceGrossSalary}")
  @NullableMin(value = 0, message = "{tenant.creation.error.referenceGrossSalary}")
  public String getReferenceGrossSalaryAsString() {
    return BigDecimalUtils.bigDecimalToString(referenceGrossSalary);
  }

  /**
   * @param referenceGrossSalaryString the referenceGrossSalary to set
   */
  public void setReferenceGrossSalaryAsString(String referenceGrossSalaryString) {
    referenceGrossSalary = BigDecimalUtils.stringToBigDecimal(referenceGrossSalaryString);
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * Renvoi le téléphone s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant ceux-ci après les z.
   * @return the phone
   */
  public String getSortedPhone() {

    return DatatablePropertiesSorted.getSortedString(phone);
  }

  public String getMailAddress() {
    return mailAddress;
  }

  public void setMailAddress(String mailAddress) {
    this.mailAddress = mailAddress;
  }

  /**
   * Renvoi l'adresse mail si elle n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les z.
   * @return the mailAddress
   */
  public String getSortedMailAddress() {
    return DatatablePropertiesSorted.getSortedString(mailAddress);
  }

  /**
   * @return the typeTenant
   */
  public TypeTenantDTO getTypeTenant() {
    return typeTenant;
  }

  /**
   * @param typeTenant the typeTenant to set
   */
  public void setTypeTenant(TypeTenantDTO typeTenant) {
    this.typeTenant = typeTenant;
  }

}
