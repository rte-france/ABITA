package com.abita.web.tenant.bean;

import com.abita.dto.ContractDTO;
import com.abita.dto.TenantDTO;
import com.abita.dto.TypeTenantDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Backing bean de la page des gestion des occupants
 *
 * @author
 */
public class TenantBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -7833416863808573649L;

  /**
   * L'occupant à créer/modifier
   */
  private TenantDTO tenantDTO;

  /**
   * La liste des types d'occupant
   */
  private List<TypeTenantDTO> lstTypeTenant;

  /**
   * Liste de logement occupé par l'occupant
   */
  private List<ContractDTO> contractList;

  /**
   * Boolean qui est egal à vrai si un occupant est un salarié ou retraité
   */
  private boolean tenantSalaried;

  /** Boolean pour la modif pour le controle des required */
  private Boolean tenantSalariedRequired;

  /**
   * Boolean qui est egal à vrai si un occupant est un salarié ou retraité
   */
  private boolean tenantNotSalaried;

  /** Boolean pour la modif pour le controle des required */
  private Boolean tenantNotSalariedRequired;

  /** La reference NNI qu'on utilise pour réinitialiser le controller*/
  private String referenceNNI;

  /**
   * @return the tenantDTO
   */
  public TenantDTO getTenantDTO() {
    return tenantDTO;
  }

  /**
   * @param tenantDTO the tenantDTO to set
   */
  public void setTenantDTO(TenantDTO tenantDTO) {
    this.tenantDTO = tenantDTO;
  }

  /**
   * @return the lstTypeTenant
   */
  public List<TypeTenantDTO> getLstTypeTenant() {
    return lstTypeTenant;
  }

  /**
   * @param lstTypeTenant the lstTypeTenant to set
   */
  public void setLstTypeTenant(List<TypeTenantDTO> lstTypeTenant) {
    this.lstTypeTenant = lstTypeTenant;
  }

  /**
   * @return the tenantSalaried
   */
  public boolean isTenantSalaried() {
    return tenantSalaried;
  }

  /**
   * @param tenantSalaried the tenantSalaried to set
   */
  public void setTenantSalaried(boolean tenantSalaried) {
    this.tenantSalaried = tenantSalaried;
  }

  /**
   * @return the tenantSalariedRequired
   */
  public Boolean getTenantSalariedRequired() {
    return tenantSalariedRequired;
  }

  /**
   * @param tenantSalariedRequired the tenantSalariedRequired to set
   */
  public void setTenantSalariedRequired(Boolean tenantSalariedRequired) {
    this.tenantSalariedRequired = tenantSalariedRequired;
  }

  /**
   * @return the contractList
   */
  public List<ContractDTO> getContractList() {
    return contractList;
  }

  /**
   * @param contractList the contractList to set
   */
  public void setContractList(List<ContractDTO> contractList) {
    this.contractList = contractList;
  }

  /**
   * @return the tenantNotSalaried
   */
  public boolean isTenantNotSalaried() {
    return tenantNotSalaried;
  }

  /**
   * @param tenantNotSalaried the tenantNotSalaried to set
   */
  public void setTenantNotSalaried(boolean tenantNotSalaried) {
    this.tenantNotSalaried = tenantNotSalaried;
  }

  /**
   * @return the tenantNotSalariedRequired
   */
  public Boolean getTenantNotSalariedRequired() {
    return tenantNotSalariedRequired;
  }

  /**
   * @param tenantNotSalariedRequired the tenantNotSalariedRequired to set
   */
  public void setTenantNotSalariedRequired(Boolean tenantNotSalariedRequired) {
    this.tenantNotSalariedRequired = tenantNotSalariedRequired;
  }

  /**
   * @return the referenceNNI
   */
  public String getReferenceNNI() {
    return referenceNNI;
  }

  /**
   * @param referenceNNI the referenceNNI to set
   */
  public void setReferenceNNI(String referenceNNI) {
    this.referenceNNI = referenceNNI;
  }

}
