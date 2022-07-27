/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.housing.bean;

import com.abita.dto.*;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.web.shared.HousingStatus;
import com.dto.UserDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Backing bean de la page de recherche des logements
 *
 * @author
 */
public class HousingSearchBean implements Serializable {

  /** serialVersionUID */
  private static final long serialVersionUID = -6400032483668625234L;

  /**
   * Critère de recherche du logement
   */
  private HousingCriteriaDTO housingCriteria;

  /**
   * Critère de recherche du logement
   */
  private TenantCriteriaDTO tenantCriteria;

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
   * Liste des domaines d'activité
   */
  private List<FieldOfActivityDTO> fieldOfActivityDTOs;

  /**
   * Liste des agences
   */
  private List<AgencyDTO> agencies;

  /**
   * Liste des climatisseurs
   */
  private List<AirconditionerDTO> airConditioners;

     /* Liste des types de chauffages
   */
  private List<HeatingTypeDTO> heatingTypes;

     /* Liste des assainissement
   */
  private List<SanitationDTO> sanitations;

  /**
   * Liste des sites
   */
  private List<SiteDTO> sites;

  /**
   * La liste des résultats de la recherche
   */
  private List<HousingDTO> lstResultSearch;

  /** Le logement sélectionné */
  private HousingDTO housingSelected;

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
   * Champ Cuisine Equipée
   */
  private String equippedKitchen;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Salle de Bain
   */
  private String bathroom;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ DPE
   */
  private String dpe;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Gaz
   */
  private String gas;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Amiantes
   */
  private String asbestos;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Elect
   */
  private String elect;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Plomb
   */
  private String lead;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Termites
   */
  private String termite;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Anomalie Electrique
   */
  private String electAno;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Anomalie Gaz
   */
  private String gasAno;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Anomalie Termite
   */
  private String termiteAno;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Anomalie Plomb
   */
  private String leadAno;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ Anomalie Amiantes
   */
  private String asbestosAno;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ ernmt
   */
  private String ernmt;

  /**
   * Bug sur champ Boolean, fait pas la différence entre NULL et false
   * On passe par une variable tampon pour gêrer tout ça
   * Champ carrez
   */
  private String carrez;

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
   * La liste des types d'occupant
   */
  private List<TypeTenantDTO> lstTypeTenant;

  /**
   * @return the housingCriteria
   */
  public HousingCriteriaDTO getHousingCriteria() {
    return housingCriteria;
  }

  /**
   * @param housingCriteria the housingCriteriaDTO to set
   */
  public void setHousingCriteria(HousingCriteriaDTO housingCriteria) {
    this.housingCriteria = housingCriteria;
  }

  /**
   * @return the lstHousingStatus
   */
  public List<HousingStatus> getLstHousingStatus() {
    return lstHousingStatus;
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

  /**
   * @return the lstResultSearch
   */
  public List<HousingDTO> getLstResultSearch() {
    return lstResultSearch;
  }

  /**
   * @param lstResultSearch the lstResultSearch to set
   */
  public void setLstResultSearch(List<HousingDTO> lstResultSearch) {
    this.lstResultSearch = lstResultSearch;
  }

  /**
   * @return the housingSelected
   */
  public HousingDTO getHousingSelected() {
    return housingSelected;
  }

  /**
   * @param housingSelected the housingSelected to set
   */
  public void setHousingSelected(HousingDTO housingSelected) {
    this.housingSelected = housingSelected;
  }

  /**
   * @return the employee
   */
  public String getEmployee() {
    return employee;
  }

  /**
   * @param employee the employee to set
   */
  public void setEmployee(String employee) {
    this.employee = employee;
  }

  /**
   * @return the managerial
   */
  public String getManagerial() {
    return managerial;
  }

  /**
   * @param managerial the managerial to set
   */
  public void setManagerial(String managerial) {
    this.managerial = managerial;
  }

  /**
   * @return the managerialLastYear
   */
  public String getManagerialLastYear() {
    return managerialLastYear;
  }

  /**
   * @param managerialLastYear the managerialLastYear to set
   */
  public void setManagerialLastYear(String managerialLastYear) {
    this.managerialLastYear = managerialLastYear;
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
   * @return the sanitations
   */
  public List<SanitationDTO> getSanitations() {
    return sanitations;
  }

  /**
   * @param sanitations the sanitations to set
   */
  public void setSanitations(List<SanitationDTO> sanitations) {
    this.sanitations = sanitations;
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

  public String getEquippedKitchen() {
    return equippedKitchen;
  }

  public void setEquippedKitchen(String equippedKitchen) {
    this.equippedKitchen = equippedKitchen;
  }

  public String getBathroom() {
    return bathroom;
  }

  public void setBathroom(String bathroom) {
    this.bathroom = bathroom;
  }

  public String getDpe() {
    return dpe;
  }

  public void setDpe(String dpe) {
    this.dpe = dpe;
  }

  public String getGas() {
    return gas;
  }

  public void setGas(String gas) {
    this.gas = gas;
  }

  public String getElectAno() {
    return electAno;
  }

  public void setElectAno(String electAno) {
    this.electAno = electAno;
  }

  public String getGasAno() {
    return gasAno;
  }

  public void setGasAno(String gasAno) {
    this.gasAno = gasAno;
  }

  public List<SiteDTO> getSites() {
    return sites;
  }

  public void setSites(List<SiteDTO> sites) {
    this.sites = sites;
  }

  public String getErnmt() {
    return ernmt;
  }

  public void setErnmt(String ernmt) {
    this.ernmt = ernmt;
  }

  public String getCarrez() {
    return carrez;
  }

  public void setCarrez(String carrez) {
    this.carrez = carrez;
  }

  public String getAsbestos() {
    return asbestos;
  }

  public void setAsbestos(String asbestos) {
    this.asbestos = asbestos;
  }

  public String getLead() {
    return lead;
  }

  public void setLead(String lead) {
    this.lead = lead;
  }

  public String getTermite() {
    return termite;
  }

  public List<AirconditionerDTO> getAirConditioners() {
    return airConditioners;
  }

  public String getElect() {
    return elect;
  }

  public void setElect(String elect) {
    this.elect = elect;
  }

  public String getTermiteAno() {
    return termiteAno;
  }

  public void setTermiteAno(String termiteAno) {
    this.termiteAno = termiteAno;
  }

  public String getLeadAno() {
    return leadAno;
  }

  public void setLeadAno(String leadAno) {
    this.leadAno = leadAno;
  }

  public String getAsbestosAno() {
    return asbestosAno;
  }

  public void setAsbestosAno(String asbestosAno) {
    this.asbestosAno = asbestosAno;
  }

  public void setAirConditioners(List<AirconditionerDTO> airConditioner) {
    this.airConditioners = airConditioner;
  }

  public List<HeatingTypeDTO> getHeatingTypes() {
    return heatingTypes;
  }

  public void setHeatingTypes(List<HeatingTypeDTO> heatingType) {
    this.heatingTypes = heatingType;
  }

  public void setTermite(String termite) {
    this.termite = termite;
  }


}
