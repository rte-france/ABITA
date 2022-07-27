/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.tenant.bean;

import com.abita.dto.AgencyDTO;
import com.abita.dto.HousingNatureDTO;
import com.abita.dto.RoomCategoryDTO;
import com.abita.dto.SiteDTO;
import com.abita.dto.TenantDTO;
import com.abita.dto.TypeTenantDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.web.shared.HousingStatus;
import com.dto.UserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Backing bean de la recherche des occupants
 * @author
 *
 */
public class TenantSearchBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1841687995377988645L;

  /************************************
   * Propriétés
   ************************************/

  /**
   * Critère de recherche de l'occupant
   */
  private TenantCriteriaDTO tenantCriteria;

  /**
   * Critère de recherche du logement
   */
  private HousingCriteriaDTO housingCriteria;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Salarié
   */
  private String employee;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Cadre
   */
  private String managerial;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Cadre à octobre N-1
   */
  private String managerialLastYear;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Propriété
   */
  private String property;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Jardin
   */
  private String gardenAvailable;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Garage
   */
  private String garageAvailable;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Noyau dur
   */
  private String core;

  /**
   * Liste des agences
   */
  private List<AgencyDTO> agencies;

  /**
   * Liste des sites
   */
  private List<SiteDTO> sites;

  /**
   * La liste des types d'occupant
   */
  private List<TypeTenantDTO> lstTypeTenant;

  /**
   * La liste des statuts
   */
  private List<HousingStatus> lstHousingStatus;

  /**
   * La liste des natures
   */
  private List<HousingNatureDTO> lstHousingNature;

  /**
   * La liste des gestionnaires de contrat tiers
   */
  private List<UserDTO> lstThirdpartyContractManager;

  /**
   * La liste des catégories de local
   */
  private List<RoomCategoryDTO> lstRoomCategory;

  /**
   * La liste des résultats de la recherche
   */
  private List<TenantDTO> lstResultSearch;

  /************************************
   * Getter et Setter
   ************************************/

  /**
   * Getter des critères de recherche du logement
   * @return the tenantCriteria
   */
  public TenantCriteriaDTO getTenantCriteria() {
    return tenantCriteria;
  }

  /**
   * Setter des critères de recherche du logement
   * @param tenantCriteria the tenantCriteria to set
   */
  public void setTenantCriteria(TenantCriteriaDTO tenantCriteria) {
    this.tenantCriteria = tenantCriteria;
  }

  /**
   * Getter du champ Salarié
   * @return the employee
   */
  public String getEmployee() {
    return employee;
  }

  /**
   * Setter du champ Salarié
   * @param employee the employee to set
   */
  public void setEmployee(String employee) {
    this.employee = employee;
  }

  /**
   * Getter du champ Cadre
   * @return the managerial
   */
  public String getManagerial() {
    return managerial;
  }

  /**
   * Setter du champ Cadre
   * @param managerial the managerial to set
   */
  public void setManagerial(String managerial) {
    this.managerial = managerial;
  }

  /**
   * Getter du champ Cadre à octobre N-1
   * @return the managerialLastYear
   */
  public String getManagerialLastYear() {
    return managerialLastYear;
  }

  /**
   * Setter du champ Cadre à octobre N-1
   * @param managerialLastYear the managerialLastYear to set
   */
  public void setManagerialLastYear(String managerialLastYear) {
    this.managerialLastYear = managerialLastYear;
  }

  /**
   * Getter de la liste des types d'occupant
   * @return the lstTypeTenant
   */
  public List<TypeTenantDTO> getLstTypeTenant() {
    return lstTypeTenant;
  }

  /**
   * Setter de la liste des types d'occupant
   * @param lstTypeTenant the lstTypeTenant to set
   */
  public void setLstTypeTenant(List<TypeTenantDTO> lstTypeTenant) {
    this.lstTypeTenant = lstTypeTenant;
  }

  /**
   * Getter de la liste des résultats de la recherche
   * @return the lstResultSearch
   */
  public List<TenantDTO> getLstResultSearch() {
    return lstResultSearch;
  }

  /**
   * Setter de la liste des résultats de la recherche
   * @param lstResultSearch the lstResultSearch to set
   */
  public void setLstResultSearch(List<TenantDTO> lstResultSearch) {
    this.lstResultSearch = lstResultSearch;
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
   * @return the property
   */
  public String getProperty() {
    return property;
  }

  /**
   * @param property the property to set
   */
  public void setProperty(String property) {
    this.property = property;
  }

  /**
   * @return the gardenAvailable
   */
  public String getGardenAvailable() {
    return gardenAvailable;
  }

  /**
   * @param gardenAvailable the gardenAvailable to set
   */
  public void setGardenAvailable(String gardenAvailable) {
    this.gardenAvailable = gardenAvailable;
  }

  /**
   * @return the garageAvailable
   */
  public String getGarageAvailable() {
    return garageAvailable;
  }

  /**
   * @param garageAvailable the garageAvailable to set
   */
  public void setGarageAvailable(String garageAvailable) {
    this.garageAvailable = garageAvailable;
  }

  /**
   * @return the core
   */
  public String getCore() {
    return core;
  }

  /**
   * @param core the core to set
   */
  public void setCore(String core) {
    this.core = core;
  }

  public List<AgencyDTO> getAgencies() {
    return agencies;
  }

  public void setAgencies(List<AgencyDTO> agencies) {
    this.agencies = agencies;
  }

  public List<SiteDTO> getSites() {
    return sites;
  }

  public void setSites(List<SiteDTO> sites) {
    this.sites = sites;
  }

  /**
   * @return the lstHousingStatus
   */
  public List<HousingStatus> getLstHousingStatus() {
    return lstHousingStatus;
  }

  /**
   * @param lstHousingStatus the lstHousingStatus to set
   */
  public void setLstHousingStatus(List<HousingStatus> lstHousingStatus) {
    this.lstHousingStatus = lstHousingStatus;
  }

  /**
   * @return the lstHousingNature
   */
  public List<HousingNatureDTO> getLstHousingNature() {
    return lstHousingNature;
  }

  /**
   * @param lstHousingNature the lstHousingNature to set
   */
  public void setLstHousingNature(List<HousingNatureDTO> lstHousingNature) {
    this.lstHousingNature = lstHousingNature;
  }

  /**
   * @return the lstThirdpartyContractManager
   */
  public List<UserDTO> getLstThirdpartyContractManager() {
    return lstThirdpartyContractManager;
  }

  /**
   * @param lstThirdpartyContractManager the lstThirdpartyContractManager to set
   */
  public void setLstThirdpartyContractManager(List<UserDTO> lstThirdpartyContractManager) {
    this.lstThirdpartyContractManager = lstThirdpartyContractManager;
  }

  /**
   * @return the lstRoomCategory
   */
  public List<RoomCategoryDTO> getLstRoomCategory() {
    return lstRoomCategory;
  }

  /**
   * @param lstRoomCategory the lstRoomCategory to set
   */
  public void setLstRoomCategory(List<RoomCategoryDTO> lstRoomCategory) {
    this.lstRoomCategory = lstRoomCategory;
  }

}
