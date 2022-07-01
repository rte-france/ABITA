package com.abita.dao.extendeduser.entity;

import com.abita.dao.agency.entity.AgencyEntity;
import com.dao.user.entity.User;

import java.util.Set;

/**
 * Classe d'extension de User
 * Permet d'ajouter des propriétés aux utilisateurs, en dehors des propriétés définies par le Framework
 *
 * @author
 */
public class ExtendedUser extends User {

  /** SerialID */
  private static final long serialVersionUID = -4663242113166475378L;

  /** Défini si un utilisateur est un gestionnaire de contrat tiers */
  private Boolean isThirdPartyContractManager;

  /** Agence de l'utilisateur */
  private Set<AgencyEntity> agencies;

  /**
   * @return the isThirdPartyContractManager
   */
  public Boolean getIsThirdPartyContractManager() {
    return isThirdPartyContractManager;
  }

  /**
   * @param isThirdPartyContractManager the isThirdPartyContractManager to set
   */
  public void setIsThirdPartyContractManager(Boolean isThirdPartyContractManager) {
    this.isThirdPartyContractManager = isThirdPartyContractManager;
  }

  /**
   * @return the agencies
   */
  public Set<AgencyEntity> getAgencies() {
    return agencies;
  }

  /**
   * @param agencies the agencies to set
   */
  public void setAgencies(Set<AgencyEntity> agencies) {
    this.agencies = agencies;
  }
}
