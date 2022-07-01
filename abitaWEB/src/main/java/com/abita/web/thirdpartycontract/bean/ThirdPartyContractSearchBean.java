package com.abita.web.thirdpartycontract.bean;

import com.abita.dto.AgencyDTO;
import com.abita.dto.ThirdPartyContractDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.ThirdPartyContractCriteriaDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Backing bean de la recherche des contrat tiers
 * @author
 *
 */
public class ThirdPartyContractSearchBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -5185659534875135083L;

  /**
   *
   * PROPRIETES
   *
   */

  /**
   * Les critères de recherche pour les contrats tiers
   */
  private ThirdPartyContractCriteriaDTO thirdPartyContractCriteria;

  /**
   * Les critères de recherche pour les logements
   */
  private HousingCriteriaDTO housingCriteria;

  /**
   * Liste de contrats tiers récupérés de la recherche
   */
  private List<ThirdPartyContractDTO> thirdPartyContractList;

  /** Le contrat tiers sélectionné */
  private ThirdPartyContractDTO thirdPartyContractSelected;

  /** Remboursement du dépot de garantie */
  private String guaranteedDepositRefund;

  /** Remboursement du dépot de garantie */
  private String expiryDate;

  /**
   * Liste des agences
   */
  private List<AgencyDTO> agencies;

  /**
   *
   * GETTER/SETTER
   *
   */

  /**
   * Getter des critères de recherche pour les contrats tiers
   * @return the thirdPartyContractCriteria
   */
  public ThirdPartyContractCriteriaDTO getThirdPartyContractCriteria() {
    return thirdPartyContractCriteria;
  }

  /**
   * Setter des critères de recherche pour les contrats tiers
   * @param thirdPartyContractCriteria the thirdPartyContractCriteria to set
   */
  public void setThirdPartyContractCriteria(ThirdPartyContractCriteriaDTO thirdPartyContractCriteria) {
    this.thirdPartyContractCriteria = thirdPartyContractCriteria;
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
   * Getter de la liste de contrats tiers récupérés de la recherche
   * @return the thirdPartyContractList
   */
  public List<ThirdPartyContractDTO> getThirdPartyContractList() {
    return thirdPartyContractList;
  }

  /**
   * Setter de la liste de contrats tiers récupérés de la recherche
   * @param thirdPartyContractList the thirdPartyContractList to set
   */
  public void setThirdPartyContractList(List<ThirdPartyContractDTO> thirdPartyContractList) {
    this.thirdPartyContractList = thirdPartyContractList;
  }

  /**
   * @return the thirdPartyContractSelected
   */
  public ThirdPartyContractDTO getThirdPartyContractSelected() {
    return thirdPartyContractSelected;
  }

  /**
   * @param thirdPartyContractSelected the thirdPartyContractSelected to set
   */
  public void setThirdPartyContractSelected(ThirdPartyContractDTO thirdPartyContractSelected) {
    this.thirdPartyContractSelected = thirdPartyContractSelected;
  }

  /**
   * Getter du remboursement du dépot de garantie
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * @return the guaranteedDepositRefund
   */
  public String getGuaranteedDepositRefund() {
    return guaranteedDepositRefund;
  }

  /**
   * Setter du remboursement du dépot de garantie
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * @param guaranteedDepositRefund the guaranteedDepositRefund to set
   */
  public void setGuaranteedDepositRefund(String guaranteedDepositRefund) {
    this.guaranteedDepositRefund = guaranteedDepositRefund;
  }

  /**
   * @return the expiryDate
   */
  public String getExpiryDate() {
    return expiryDate;
  }

  /**
   * @param expiryDate the expiryDate to set
   */
  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  /**
   * @return the agencies
   */
  public List<AgencyDTO> getAgencies() {
    return agencies;
  }

  /**
   * @param agencies the agencies to set
   */
  public void setAgencies(List<AgencyDTO> agencies) {
    this.agencies = agencies;
  }

}
