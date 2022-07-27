/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.dto.unpersist;

import com.abita.dto.ContractDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.TenantDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author
 * Objet des données agents
 */
public class AgentDataDTO implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1623607968596523387L;

  /** Occupant lu dans le fichier*/
  private TenantDTO tenant;

  /** Domaine d'activité lu dans le fichier*/
  private FieldOfActivityDTO fieldOfActivity;

  /** Liste des contrats occupants pour l'occupant lu dans le fichier */
  private List<ContractDTO> contractsByTenant;

  /** Salaire de l'agent à octobre N-1 contenu dans le fichier "Données agents" */
  private BigDecimal referenceGrossSalaryFromFile;

  /** Salaire échelon 35h contenu dans le fichier "Données agents" */
  private BigDecimal grossSalary35hFromFile;

  /**
   * @return the tenant
   */
  public TenantDTO getTenant() {
    return tenant;
  }

  /**
   * @param tenant the tenant to set
   */
  public void setTenant(TenantDTO tenant) {
    this.tenant = tenant;
  }

  /**
   * @return the fieldOfActivity
   */
  public FieldOfActivityDTO getFieldOfActivity() {
    return fieldOfActivity;
  }

  /**
   * @param fieldOfActivity the fieldOfActivity to set
   */
  public void setFieldOfActivity(FieldOfActivityDTO fieldOfActivity) {
    this.fieldOfActivity = fieldOfActivity;
  }

  /**
   * @return the contractsByTenant
   */
  public List<ContractDTO> getContractsByTenant() {
    return contractsByTenant;
  }

  /**
   * @param contractsByTenant the contractsByTenant to set
   */
  public void setContractsByTenant(List<ContractDTO> contractsByTenant) {
    this.contractsByTenant = contractsByTenant;
  }

  /**
   * Getter salaire de l'agent à octobre N-1 contenu dans le fichier "Données agents"
   * @return le salaire de l'agent à octobre N-1
   */
  public BigDecimal getReferenceGrossSalaryFromFile() {
    return referenceGrossSalaryFromFile;
  }

  /**
   * Setter salaire de l'agent à octobre N-1 contenu dans le fichier "Données agents"
   * @param referenceGrossSalaryFromFile le salaire de l'agent à octobre N-1 à enregistrer
   */
  public void setReferenceGrossSalaryFromFile(BigDecimal referenceGrossSalaryFromFile) {
    this.referenceGrossSalaryFromFile = referenceGrossSalaryFromFile;
  }


  /**
   * Getter salaire échelon contenu dans le fichier "Données agents"
   * @return le salaire échelon 35h
   */
  public BigDecimal getGrossSalary35hFromFile() {
    return grossSalary35hFromFile;
  }

  /**
   * Setter salaire échelon contenu dans le fichier "Données agents"
   * @param grossSalary35hFromFile le salaire échelon 35h à enregistrer
   */
  public void setGrossSalary35hFromFile(BigDecimal grossSalary35hFromFile) {
    this.grossSalary35hFromFile = grossSalary35hFromFile;
  }

}
