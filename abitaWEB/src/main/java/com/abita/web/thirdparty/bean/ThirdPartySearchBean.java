package com.abita.web.thirdparty.bean;

import com.abita.dto.ThirdPartyDTO;
import com.abita.dto.unpersist.ThirdPartyCriteriaDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Backing bean de la recherche des tiers
 * @author
 *
 */
public class ThirdPartySearchBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 3448444964085518564L;

  /**
   *
   * PROPRIETES
   *
   */

  /**
   * Les critères de recherche pour les tiers
   */
  private ThirdPartyCriteriaDTO thirdPartyCriteria;

  /**
   * Liste de tiers récupérée de la recherche
   */
  private List<ThirdPartyDTO> thirdPartyList;

  /** Le tiers sélectionné */
  private ThirdPartyDTO thirdpartySelected;

  /**
   *
   * GETTER/SETTER
   *
   */

  /**
   * Getter des critères de recherche pour les tiers
   * @return the thirdPartyCriteria
   */
  public ThirdPartyCriteriaDTO getThirdPartyCriteria() {
    return thirdPartyCriteria;
  }

  /**
   * Setter des critères de recherche pour les tiers
   * @param thirdPartyCriteria the thirdPartyCriteria to set
   */
  public void setThirdPartyCriteria(ThirdPartyCriteriaDTO thirdPartyCriteria) {
    this.thirdPartyCriteria = thirdPartyCriteria;
  }

  /**
   * Getter de la liste de tiers récupérée de la recherche
   * @return the thirdPartyList
   */
  public List<ThirdPartyDTO> getThirdPartyList() {
    return thirdPartyList;
  }

  /**
   * Setter de la liste de tiers récupérée de la recherche
   * @param thirdPartyList the thirdPartyList to set
   */
  public void setThirdPartyList(List<ThirdPartyDTO> thirdPartyList) {
    this.thirdPartyList = thirdPartyList;
  }

  /**
   * @return the thirdpartySelected
   */
  public ThirdPartyDTO getThirdpartySelected() {
    return thirdpartySelected;
  }

  /**
   * @param thirdpartySelected the thirdpartySelected to set
   */
  public void setThirdpartySelected(ThirdPartyDTO thirdpartySelected) {
    this.thirdpartySelected = thirdpartySelected;
  }

}
