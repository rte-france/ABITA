/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.contract.bean;

import com.abita.dto.ContractDTO;
import com.abita.dto.unpersist.ContractCriteriaDTO;
import com.abita.dto.unpersist.ContractInputDTO;

import java.io.Serializable;
import java.util.List;

/**Backing bean de la recherche des contrat occupant
 * @author
 *
 */
public class ContractSearchBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 2384305525353439935L;

  /**
   *
   * PROPRIETES
   *
   */

  /**
   * Les critères de recherche pour les contrats occupant
   */
  private ContractCriteriaDTO contractCriteria;

  /**
   * Liste de contrats occupants récupérés de la recherche
   */
  private List<ContractDTO> contractList;

  /** Le contrat occupant sélectionné */
  private ContractInputDTO contractSelected;

  /** Contrat signé */
  private String signature;

  /** Pécule de fin d'occupation */
  private String terminationSavings;

  /** Règlement du pécule de fin d'occupation */
  private String terminationSavingsPayment;

  /** Date du règlement du pécule de fin d'occupation */
  private String terminationSavingsPaymentDate;

  /**
   *
   * GETTER/SETTER
   *
   */

  /**Getter des critères de recherche pour les contrats occupants
   * @return the contractCriteria
   */
  public ContractCriteriaDTO getContractCriteria() {
    return contractCriteria;
  }

  /**Setter des critères de recherche pour les contrats occupants
   * @param contractCriteria the contractCriteria to set
   */
  public void setContractCriteria(ContractCriteriaDTO contractCriteria) {
    this.contractCriteria = contractCriteria;
  }

  /**Getter de la liste de contrats occupants récupérés de la recherche
   * @return the contractList
   */
  public List<ContractDTO> getContractList() {
    return contractList;
  }

  /**Setter de la liste de contrats occupants récupérés de la recherche
   * @param contractList the contractList to set
   */
  public void setContractList(List<ContractDTO> contractList) {
    this.contractList = contractList;
  }

  /**Getter du contrat occupant selectionné
   * @return the contractSelected
   */
  public ContractInputDTO getContractSelected() {
    return contractSelected;
  }

  /**Setter du contrat occupant selectionné
   * @param contractSelected the contractSelected to set
   */
  public void setContractSelected(ContractInputDTO contractSelected) {
    this.contractSelected = contractSelected;
  }

  /**Getter du contrat signé
   *
   * @return the signature
   */
  public String getSignature() {
    return signature;
  }

  /**Setter du contrat signé
   * @param signature the signature to set
   */
  public void setSignature(String signature) {
    this.signature = signature;
  }

  /**Getter du pécule de fin d'occupation
   * @return the terminationSavings
   */
  public String getTerminationSavings() {
    return terminationSavings;
  }

  /**Setter du pécule de fin d'occupation
   * @param terminationSavings the terminationSavings to set
   */
  public void setTerminationSavings(String terminationSavings) {
    this.terminationSavings = terminationSavings;
  }

  /** Getter du règlement du pécule de fin d'occupation
   * @return the terminationSavingsPayment
   */
  public String getTerminationSavingsPayment() {
    return terminationSavingsPayment;
  }

  /** Setter du règlement du pécule de fin d'occupation
   * @param terminationSavingsPayment the terminationSavings to set
   */
  public void setTerminationSavingsPayment(String terminationSavingsPayment) {
    this.terminationSavingsPayment = terminationSavingsPayment;
  }

  /** Getter de la date du règlement du pécule de fin d'occupation
   * @return the terminationSavingsPaymentDate
   */
  public String getTerminationSavingsPaymentDate() {
    return terminationSavingsPaymentDate;
  }

  /** Setter de la date du règlement du pécule de fin d'occupation
   * @param terminationSavingsPaymentDate the terminationSavings to set
   */
  public void setTerminationSavingsPaymentDate(String terminationSavingsPaymentDate) {
    this.terminationSavingsPaymentDate = terminationSavingsPaymentDate;
  }

}
