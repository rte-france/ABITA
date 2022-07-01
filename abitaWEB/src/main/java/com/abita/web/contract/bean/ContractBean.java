/**
 *
 */
package com.abita.web.contract.bean;

import com.abita.dto.ContractDTO;
import com.abita.dto.CostCenterDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.HousingDTO;
import com.abita.dto.PaymentMethodDTO;
import com.abita.dto.RentTypologyDTO;
import com.abita.dto.TenantDTO;
import com.abita.dto.TerminationDTO;
import com.abita.dto.unpersist.ContractInputDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Backing bean de la page des gestion des contrats occupant
 * @author
 *
 */
public class ContractBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 795702595952870126L;

  /**
   *
   * PROPRIETES
   *
   */

  /**
   * Le contrat occupant crée par les saisies
   */
  private ContractInputDTO contractInputDTO;

  /**
   * Le contrat occupant
   */
  private ContractDTO contractDTO;

  /**
   * Liste des contrats occupant
   */
  private List<ContractInputDTO> contractDTOs;

  /**
   * Liste des occupants
   */
  private List<TenantDTO> tenantDTOs;

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
   * Liste des Typologies de loyer
   */
  private List<RentTypologyDTO> rentTypologyDTOs;

  /**
   * Liste des Modes de paiement
   */
  private List<PaymentMethodDTO> paymentMethodDTOs;

  /**
   * Liste des motifs de résiliation
   */
  private List<TerminationDTO> terminationDTOs;

  /**
   * Boolean pour savoir si le champ endValidityDate est vide
   */
  private boolean terminationMandatory;

  /**
   * Boolean pour la modif, permet de savoir si les champs sont obligatoire
   */
  private Boolean terminationMandatoryCheck;

  /**
   * DTO de recherche d'un logement
   */
  private HousingCriteriaDTO housingCriteria;

  /**
   * DTO de recherche d'un occupant
   */
  private TenantCriteriaDTO tenantCriteria;

  /**
   * La liste des résultats de la recherche des logements
   */
  private List<HousingDTO> lstHousingResultSearch;

  /**
   * Le logement sélectionné
   */
  private HousingDTO selectedHousing;

  /**
   * La liste des résultats de la recherche des logements
   */
  private List<TenantDTO> lstTenantResultSearch;

  /**
   * L'occupant sélectionné
   */
  private TenantDTO selectedTenant;

  /**
   *
   * GETTER/SETTER
   *
   */

  /**
   * @return the contractInputDTO
   */
  public ContractInputDTO getContractInputDTO() {
    return contractInputDTO;
  }

  /**
   * @param contractInputDTO the contractInputDTO to set
   */
  public void setContractInputDTO(ContractInputDTO contractInputDTO) {
    this.contractInputDTO = contractInputDTO;
  }

  /**
   * @return the tenantDTOs
   */
  public List<TenantDTO> getTenantDTOs() {
    return tenantDTOs;
  }

  /**
   * @param tenantDTOs the tenantDTOs to set
   */
  public void setTenantDTOs(List<TenantDTO> tenantDTOs) {
    this.tenantDTOs = tenantDTOs;
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
   * @return the rentTypologyDTOs
   */
  public List<RentTypologyDTO> getRentTypologyDTOs() {
    return rentTypologyDTOs;
  }

  /**
   * @param rentTypologyDTOs the rentTypologyDTOs to set
   */
  public void setRentTypologyDTOs(List<RentTypologyDTO> rentTypologyDTOs) {
    this.rentTypologyDTOs = rentTypologyDTOs;
  }

  /**
   * @return the paymentMethodDTOs
   */
  public List<PaymentMethodDTO> getPaymentMethodDTOs() {
    return paymentMethodDTOs;
  }

  /**
   * @param paymentMethodDTOs the paymentMethodDTOs to set
   */
  public void setPaymentMethodDTOs(List<PaymentMethodDTO> paymentMethodDTOs) {
    this.paymentMethodDTOs = paymentMethodDTOs;
  }

  /**
   * @return the contractDTOs
   */
  public List<ContractInputDTO> getContractDTOs() {
    return contractDTOs;
  }

  /**
   * @param contractDTOs the contractDTOs to set
   */
  public void setContractDTOs(List<ContractInputDTO> contractDTOs) {
    this.contractDTOs = contractDTOs;
  }

  /**
   * @return the terminationMandatory
   */
  public boolean isTerminationMandatory() {
    return terminationMandatory;
  }

  /**
   * @param terminationMandatory the terminationMandatory to set
   */
  public void setTerminationMandatory(boolean terminationMandatory) {
    this.terminationMandatory = terminationMandatory;
  }

  /**
   * @return the contractDTO
   */
  public ContractDTO getContractDTO() {
    return contractDTO;
  }

  /**
   * @param contractDTO the contractDTO to set
   */
  public void setContractDTO(ContractDTO contractDTO) {
    this.contractDTO = contractDTO;
  }

  /**
   * @return the terminationDTOs
   */
  public List<TerminationDTO> getTerminationDTOs() {
    return terminationDTOs;
  }

  /**
   * @param terminationDTOs the terminationDTOs to set
   */
  public void setTerminationDTOs(List<TerminationDTO> terminationDTOs) {
    this.terminationDTOs = terminationDTOs;
  }

  /**
   * @return the terminationMandatoryCheck
   */
  public Boolean getTerminationMandatoryCheck() {
    return terminationMandatoryCheck;
  }

  /**
   * @param terminationMandatoryCheck the terminationMandatoryCheck to set
   */
  public void setTerminationMandatoryCheck(Boolean terminationMandatoryCheck) {
    this.terminationMandatoryCheck = terminationMandatoryCheck;
  }

  /**
   * @return the tenantCriteria
   */
  public TenantCriteriaDTO getTenantCriteria() {
    return tenantCriteria;
  }

  /**
   * @param tenantCriteria the tenantCriteria to set
   */
  public void setTenantCriteria(TenantCriteriaDTO tenantCriteria) {
    this.tenantCriteria = tenantCriteria;
  }

  /**
   * @return the lstHousingResultSearch
   */
  public List<HousingDTO> getLstHousingResultSearch() {
    return lstHousingResultSearch;
  }

  /**
   * @param lstHousingResultSearch the lstHousingResultSearch to set
   */
  public void setLstHousingResultSearch(List<HousingDTO> lstHousingResultSearch) {
    this.lstHousingResultSearch = lstHousingResultSearch;
  }

  /**
   * @return the lstTenantResultSearch
   */
  public List<TenantDTO> getLstTenantResultSearch() {
    return lstTenantResultSearch;
  }

  /**
   * @param lstTenantResultSearch the lstTenantResultSearch to set
   */
  public void setLstTenantResultSearch(List<TenantDTO> lstTenantResultSearch) {
    this.lstTenantResultSearch = lstTenantResultSearch;
  }

  /**
   * @return the housingCriteria
   */
  public HousingCriteriaDTO getHousingCriteria() {
    return housingCriteria;
  }

  /**
   * @param housingCriteria the housingCriteria to set
   */
  public void setHousingCriteria(HousingCriteriaDTO housingCriteria) {
    this.housingCriteria = housingCriteria;
  }

  /**
   * @return the selectedHousing
   */
  public HousingDTO getSelectedHousing() {
    return selectedHousing;
  }

  /**
   * @param selectedHousing the selectedHousing to set
   */
  public void setSelectedHousing(HousingDTO selectedHousing) {
    this.selectedHousing = selectedHousing;
  }

  /**
   * @return the selectedTenant
   */
  public TenantDTO getSelectedTenant() {
    return selectedTenant;
  }

  /**
   * @param selectedTenant the selectedTenant to set
   */
  public void setSelectedTenant(TenantDTO selectedTenant) {
    this.selectedTenant = selectedTenant;
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
