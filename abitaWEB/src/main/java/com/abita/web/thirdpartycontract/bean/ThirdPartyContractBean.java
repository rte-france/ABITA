package com.abita.web.thirdpartycontract.bean;

import com.abita.dto.CostCenterDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.HousingDTO;
import com.abita.dto.PaymentCycleDTO;
import com.abita.dto.ThirdPartyContractDTO;
import com.abita.dto.ThirdPartyDTO;
import com.abita.dto.ThirdPartyTerminationDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Backing bean de la page des gestion des contrats tiers
 * @author
 *
 */
public class ThirdPartyContractBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -8736982239437827259L;

  /**
   *
   * PROPRIETES
   *
   */

  /**
   * Le contrat tiers à créer/modifier
   */
  private ThirdPartyContractDTO thirdPartyContractDTO;

  /**
   * Liste des tiers
   */
  private List<ThirdPartyDTO> thirdPartyDTOs;

  /**
   * Liste des logements
   */
  private List<HousingDTO> housingDTOs;

  /**
   * Liste de tous les logements de l'appli
   */
  private List<HousingDTO> allHousingDTOs;

  /**
   * Liste des domaines d'activité
   */
  private List<FieldOfActivityDTO> fieldOfActivityDTOs;

  /**
   * Liste des centres coût
   */
  private List<CostCenterDTO> costCenterDTOs;

  /**
   * Liste des périodes de paiement
   */
  private List<PaymentCycleDTO> paymentCycleDTOs;

  /**
   * Liste des motifs de résiliation
   */
  private List<ThirdPartyTerminationDTO> thirdPartyTerminationDTOs;

  /** Sert pour la RG sur la notion terme échu / échoir */
  private boolean blnTerm;

  /** Boolean pour la modif pour le controle des required */
  private boolean blnTermRequired;

  /** Boolean pour savoir si le montant de la révision du loyer mensuel est obligatoire */
  private boolean revisedRentAmountMandatory;

  /** Boolean pour verfifer si le montant de la révision du loyer mensuel est obligatoire */
  private Boolean revisedRentAmountMandatoryCheck;

  /** Boolean pour savoir si lla date d'effet de la révision du loyer mensuel est obligatoire */
  private boolean revisedRentDateMandatory;

  /** Boolean pour verfifer si lla date d'effet de la révision du loyer mensuel est obligatoire */
  private Boolean revisedRentDateMandatoryCheck;

  /** Boolean pour savoir si le montant de la révision des charges prévisionnelles est obligatoire */
  private boolean revisedExpectedChargeCostAmountMandatory;

  /** Boolean pour verfifer si le montant de la révision des charges prévisionnelles est obligatoire */
  private Boolean revisedExpectedChargeCostAmountMandatoryCheck;

  /** Boolean pour savoir si la date d'effet de la révision des charges prévisionnelles est obligatoire  */
  private boolean revisedExpectedChargeCostDateMandatory;

  /** Boolean pour verfifer si la date d'effet de la révision des charges prévisionnelles est obligatoire  */
  private Boolean revisedExpectedChargeCostDateMandatoryCheck;

  /**
   *
   * GETTER/SETTER
   *
   */

  /**
   * Getter du contrat tiers à créer/modifier
   * @return the thirdPartyContractDTO
   */
  public ThirdPartyContractDTO getThirdPartyContractDTO() {
    return thirdPartyContractDTO;
  }

  /**
   * Setter du contrat tiers à créer/modifier
   * @param thirdPartyContractDTO the thirdPartyContractDTO to set
   */
  public void setThirdPartyContractDTO(ThirdPartyContractDTO thirdPartyContractDTO) {
    this.thirdPartyContractDTO = thirdPartyContractDTO;
  }

  /**
   * @return the thirdPartyDTOs
   */
  public List<ThirdPartyDTO> getThirdPartyDTOs() {
    return thirdPartyDTOs;
  }

  /**
   * @param thirdPartyDTOs the thirdPartyDTOs to set
   */
  public void setThirdPartyDTOs(List<ThirdPartyDTO> thirdPartyDTOs) {
    this.thirdPartyDTOs = thirdPartyDTOs;
  }

  /**
   * @return the housingDTOs
   */
  public List<HousingDTO> getHousingDTOs() {
    return housingDTOs;
  }

  /**
   * @param housingDTOs the housingDTOs to set
   */
  public void setHousingDTOs(List<HousingDTO> housingDTOs) {
    this.housingDTOs = housingDTOs;
  }

  /**
   * @return the fieldOfActivityDTOs
   */
  public List<FieldOfActivityDTO> getFieldOfActivityDTOs() {
    return fieldOfActivityDTOs;
  }

  /**
   * @param fieldOfActivityDTOs the fieldOfActivityDTOs to set
   */
  public void setFieldOfActivityDTOs(List<FieldOfActivityDTO> fieldOfActivityDTOs) {
    this.fieldOfActivityDTOs = fieldOfActivityDTOs;
  }

  /**
   * @return the costCenterDTOs
   */
  public List<CostCenterDTO> getCostCenterDTOs() {
    return costCenterDTOs;
  }

  /**
   * @param costCenterDTOs the costCenterDTOs to set
   */
  public void setCostCenterDTOs(List<CostCenterDTO> costCenterDTOs) {
    this.costCenterDTOs = costCenterDTOs;
  }

  /**
   * @return the paymentCycleDTOs
   */
  public List<PaymentCycleDTO> getPaymentCycleDTOs() {
    return paymentCycleDTOs;
  }

  /**
   * @param paymentCycleDTOs the paymentCycleDTOs to set
   */
  public void setPaymentCycleDTOs(List<PaymentCycleDTO> paymentCycleDTOs) {
    this.paymentCycleDTOs = paymentCycleDTOs;
  }

  /**
   * @return the terminationDTOs
   */
  public List<ThirdPartyTerminationDTO> getThirdPartyTerminationDTOs() {
    return thirdPartyTerminationDTOs;
  }

  /**
   * @param thirdPartyTerminationDTOs the thirdPartyTerminationDTOs to set
   */
  public void setThirdPartyTerminationDTOs(List<ThirdPartyTerminationDTO> thirdPartyTerminationDTOs) {
    this.thirdPartyTerminationDTOs = thirdPartyTerminationDTOs;
  }

  /**
   * @return the blnTerm
   */
  public boolean isBlnTerm() {
    return blnTerm;
  }

  /**
   * @param blnTerm the blnTerm to set
   */
  public void setBlnTerm(boolean blnTerm) {
    this.blnTerm = blnTerm;
  }

  /**
   * @return the blnTermRequired
   */
  public Boolean getBlnTermRequired() {
    return blnTermRequired;
  }

  /**
   * @param blnTermRequired the blnTermRequired to set
   */
  public void setBlnTermRequired(Boolean blnTermRequired) {
    this.blnTermRequired = blnTermRequired;
  }

  /**
   * @return the revisedRentAmountMandatory
   */
  public boolean isRevisedRentAmountMandatory() {
    return revisedRentAmountMandatory;
  }

  /**
   * @param revisedRentAmountMandatory the revisedRentAmountMandatory to set
   */
  public void setRevisedRentAmountMandatory(boolean revisedRentAmountMandatory) {
    this.revisedRentAmountMandatory = revisedRentAmountMandatory;
  }

  /**
   * @return the revisedRentAmountMandatoryCheck
   */
  public Boolean getRevisedRentAmountMandatoryCheck() {
    return revisedRentAmountMandatoryCheck;
  }

  /**
   * @param revisedRentAmountMandatoryCheck the revisedRentAmountMandatoryCheck to set
   */
  public void setRevisedRentAmountMandatoryCheck(Boolean revisedRentAmountMandatoryCheck) {
    this.revisedRentAmountMandatoryCheck = revisedRentAmountMandatoryCheck;
  }

  /**
   * @return the revisedRentDateMandatory
   */
  public boolean isRevisedRentDateMandatory() {
    return revisedRentDateMandatory;
  }

  /**
   * @param revisedRentDateMandatory the revisedRentDateMandatory to set
   */
  public void setRevisedRentDateMandatory(boolean revisedRentDateMandatory) {
    this.revisedRentDateMandatory = revisedRentDateMandatory;
  }

  /**
   * @return the revisedRentDateMandatoryCheck
   */
  public Boolean getRevisedRentDateMandatoryCheck() {
    return revisedRentDateMandatoryCheck;
  }

  /**
   * @param revisedRentDateMandatoryCheck the revisedRentDateMandatoryCheck to set
   */
  public void setRevisedRentDateMandatoryCheck(Boolean revisedRentDateMandatoryCheck) {
    this.revisedRentDateMandatoryCheck = revisedRentDateMandatoryCheck;
  }

  /**
   * @return the revisedExpectedChargeCostAmountMandatory
   */
  public boolean isRevisedExpectedChargeCostAmountMandatory() {
    return revisedExpectedChargeCostAmountMandatory;
  }

  /**
   * @param revisedExpectedChargeCostAmountMandatory the revisedExpectedChargeCostAmountMandatory to set
   */
  public void setRevisedExpectedChargeCostAmountMandatory(boolean revisedExpectedChargeCostAmountMandatory) {
    this.revisedExpectedChargeCostAmountMandatory = revisedExpectedChargeCostAmountMandatory;
  }

  /**
   * @return the revisedExpectedChargeCostAmountMandatoryCheck
   */
  public Boolean getRevisedExpectedChargeCostAmountMandatoryCheck() {
    return revisedExpectedChargeCostAmountMandatoryCheck;
  }

  /**
   * @param revisedExpectedChargeCostAmountMandatoryCheck the revisedExpectedChargeCostAmountMandatoryCheck to set
   */
  public void setRevisedExpectedChargeCostAmountMandatoryCheck(Boolean revisedExpectedChargeCostAmountMandatoryCheck) {
    this.revisedExpectedChargeCostAmountMandatoryCheck = revisedExpectedChargeCostAmountMandatoryCheck;
  }

  /**
   * @return the revisedExpectedChargeCostDateMandatory
   */
  public boolean isRevisedExpectedChargeCostDateMandatory() {
    return revisedExpectedChargeCostDateMandatory;
  }

  /**
   * @param revisedExpectedChargeCostDateMandatory the revisedExpectedChargeCostDateMandatory to set
   */
  public void setRevisedExpectedChargeCostDateMandatory(boolean revisedExpectedChargeCostDateMandatory) {
    this.revisedExpectedChargeCostDateMandatory = revisedExpectedChargeCostDateMandatory;
  }

  /**
   * @return the revisedExpectedChargeCostDateMandatoryCheck
   */
  public Boolean getRevisedExpectedChargeCostDateMandatoryCheck() {
    return revisedExpectedChargeCostDateMandatoryCheck;
  }

  /**
   * @param revisedExpectedChargeCostDateMandatoryCheck the revisedExpectedChargeCostDateMandatoryCheck to set
   */
  public void setRevisedExpectedChargeCostDateMandatoryCheck(Boolean revisedExpectedChargeCostDateMandatoryCheck) {
    this.revisedExpectedChargeCostDateMandatoryCheck = revisedExpectedChargeCostDateMandatoryCheck;
  }

  /**
   * @param blnTermRequired the blnTermRequired to set
   */
  public void setBlnTermRequired(boolean blnTermRequired) {
    this.blnTermRequired = blnTermRequired;
  }

  /**
   * @return the allHousingDTOs
   */
  public List<HousingDTO> getAllHousingDTOs() {
    return allHousingDTOs;
  }

  /**
   * @param allHousingDTOs the allHousingDTOs to set
   */
  public void setAllHousingDTOs(List<HousingDTO> allHousingDTOs) {
    this.allHousingDTOs = allHousingDTOs;
  }

}
