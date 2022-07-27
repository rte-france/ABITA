/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.housing.bean;

import com.abita.dto.*;
import com.abita.dto.*;
import com.abita.web.shared.Couverture;
import com.abita.web.shared.EnergyLabel;
import com.abita.web.shared.Facade;
import com.abita.web.shared.Finitions;
import com.abita.web.shared.FuturOfHousing;
import com.abita.web.shared.HousingStatus;
import com.abita.web.shared.Insulation;
import com.abita.web.shared.JoineryFrame;
import com.abita.web.shared.Plomberie;
import com.abita.web.shared.ReasonForExit;
import com.abita.web.shared.Sanitationstd;
import com.abita.web.shared.Vmc;
import com.abita.web.shared.Walls;
import com.dto.UserDTO;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Backing bean de la page des gestion des logements
 *
 * @author
 */
public class HousingBean implements Serializable {

  /** serialVersionUID */
  private static final long serialVersionUID = 6862969696510466889L;

  /** Sert pour la RG sur les champs Année et Prix liées au champ Avenir du logement */
  private boolean futureOfHousingBloc;


  /** Sert pour la RG sur le champ coût de la démolition */
  private boolean demolitionBloc;

  /** Sert pour la RG sur le champ coût de la rénovation */
  private boolean renovationBloc;


  /** Sert pour la RG sur le champ Mur */
  private boolean murBloc;

  /**
   * Le logement à créer/modifier
   */
  private HousingDTO housingDTO;

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
   * Liste des agences
   */
  private List<AgencyDTO> agencies;

  /**
   * Liste des assainissements
   */
  private List<SanitationDTO> lstSanitation;

  /**
   * Liste des types de chauffage
   */
  private List<HeatingTypeDTO> lstHeatingType;

  /**
   * Liste des climatisations
   */
  private List<AirconditionerDTO> lstAirconditioner;

  /**
   * Liste des sites
   */
  private List<SiteDTO> sites;

  /**
   * Liste des contrats occupants pour le logement
   */
  private List<ContractDTO> contractList;

  /**
   * Liste des contrats tiers pour le logement
   */
  private List<ThirdPartyContractDTO> thirdPartyContractList;

  private Map<String, Boolean> listFormBoolean;

  /**
   * @return the housingDTO
   */
  public HousingDTO getHousingDTO() {
    return housingDTO;
  }

  /**
   * @param housingDTO the housingDTO to set
   */
  public void setHousingDTO(HousingDTO housingDTO) {
    this.housingDTO = housingDTO;
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
   * @param thirdPartyContractList the thirdPartyContractList to set
   */
  public void setThirdPartyContractList(List<ThirdPartyContractDTO> thirdPartyContractList) {
    this.thirdPartyContractList = thirdPartyContractList;
  }

  /**
   * @return the thirdPartyContractList
   */
  public List<ThirdPartyContractDTO> getThirdPartyContractList() {
    return thirdPartyContractList;
  }

  public ReasonForExit[] getReasonForExitArray() {
    return ReasonForExit.values();
  }


  public EnergyLabel[] getEnergyLabelArray() {
    return EnergyLabel.values();
  }

  public FuturOfHousing[] getFuturOfHousingArray() {
    return FuturOfHousing.values();
  }


  public Insulation[] getInsulationArray() {
    return Insulation.values();
  }

  public JoineryFrame[] getJoineryFrameArray() {
    return JoineryFrame.values();
  }

  public Walls[] getWallsArray() {
    return Walls.values();
  }

  public Couverture[] getCouvertureArray() {
    return Couverture.values();
  }

  public Facade[] getFacadeArray() {
    return Facade.values();
  }

  public Finitions[] getFinitionsArray() {
    return Finitions.values();
  }

  public Plomberie[] getPlomberieArray() {
    return Plomberie.values();
  }

  public Sanitationstd[] getSanitationstdArray() {
    return Sanitationstd.values();
  }

  public Vmc[] getVmcArray() { return Vmc.values(); }

  /** Sert pour la RG sur la date: Avenir du logement*/
  private boolean futurOfHoussingDate;

  public boolean isFuturOfHoussingDate() {
    return futurOfHoussingDate;
  }

  public void setFuturOfHoussingDate(boolean futurOfHoussingDate) {
    this.futurOfHoussingDate = futurOfHoussingDate;
  }

  public List<SiteDTO> getSites() {
    return sites;
  }

  public void setSites(List<SiteDTO> sites) {
    this.sites = sites;
  }

  public List<SanitationDTO> getLstSanitation() {
    return lstSanitation;
  }

  public void setLstSanitation(List<SanitationDTO> lstSanitation) {
    this.lstSanitation = lstSanitation;
  }

  public List<HeatingTypeDTO> getLstHeatingType() {
    return lstHeatingType;
  }

  public void setLstHeatingType(List<HeatingTypeDTO> lstHeatingType) {
    this.lstHeatingType = lstHeatingType;
  }

  public List<AirconditionerDTO> getLstAirconditioner() {
    return lstAirconditioner;
  }

  public void setLstAirconditioner(List<AirconditionerDTO> lstAirconditioner) {
    this.lstAirconditioner = lstAirconditioner;
  }

  private void initListFormBoolean() {
    if(listFormBoolean == null) {
      listFormBoolean = new HashMap<String, Boolean>();
    }
  }

  /**
   * Permet d'affecter une valeur Boolean à un string (dans la liste listFormBoolean)
   *
   * @param label
   * @param val
   */
  public void setBooleanValueInList(String label, Boolean val) {
    this.initListFormBoolean();
    listFormBoolean.put(label, val);
  }

  /**
   * On récupère la valeur Boolean d'un string (de la liste listFormBoolean)
   *
   * @param label
   * @return
   */
  public boolean getBooleanValueInList(String label) {
    this.initListFormBoolean();

    if(listFormBoolean.containsKey(label)) {
      return listFormBoolean.get(label);
    }

    return false;
  }

  public boolean isFutureOfHousingBloc() {
    return futureOfHousingBloc;
  }

  public void setFutureOfHousingBloc(boolean futureOfHousingBloc) {
    this.futureOfHousingBloc = futureOfHousingBloc;
  }

  public boolean isDemolitionBloc() {
    return demolitionBloc;
  }

  public void setDemolitionBloc(boolean demolitionBloc) {
    this.demolitionBloc = demolitionBloc;
  }

  public boolean isRenovationBloc() {
    return renovationBloc;
  }

  public void setRenovationBloc(boolean renovationBloc) {
    this.renovationBloc = renovationBloc;
  }

  public boolean isMurBloc(){
    return murBloc;
  }

  public void setMurBloc(boolean murBloc) {
    this.murBloc = murBloc;
  }

  /**
   * On met à jour la liste des champs booléen qui impact l'affichage de certains champs
   */
  public void updateBooleanValueInListFromDTO() {
    if(this.housingDTO != null) {

      if(this.housingDTO.getEquippedKitchen() != null) {
        this.setBooleanValueInList("equippedKitchen", this.housingDTO.getEquippedKitchen());
      }

      if(this.housingDTO.getBathroom() != null) {
        this.setBooleanValueInList("Bathroom", this.housingDTO.getBathroom());
      }

      if(this.housingDTO.getGardenAvailable() != null) {
        this.setBooleanValueInList("gardenAvailable", this.housingDTO.getGardenAvailable());
      }

      if(this.housingDTO.getDpe() != null) {
        this.setBooleanValueInList("Dpe", this.housingDTO.getDpe());
      }

      if(this.housingDTO.getGas() != null) {
        this.setBooleanValueInList("Gas", this.housingDTO.getGas());
      }

      if(this.housingDTO.getElect() != null) {
        this.setBooleanValueInList("Elect", this.housingDTO.getElect());
      }

      if(this.housingDTO.getAsbestos() != null) {
        this.setBooleanValueInList("Asbestos", this.housingDTO.getAsbestos());
      }

      if(this.housingDTO.getLead() != null) {
        this.setBooleanValueInList("Lead", this.housingDTO.getLead());
      }

      if(this.housingDTO.getTermite() != null) {
        this.setBooleanValueInList("Termite", this.housingDTO.getTermite());
      }

      if(this.housingDTO.getErnmt() != null) {
        this.setBooleanValueInList("Ernmt", this.housingDTO.getErnmt());
      }

      if(this.housingDTO.getCarrez() != null) {
        this.setBooleanValueInList("Carrez", this.housingDTO.getCarrez());
      }

      if((this.housingDTO.getFuturOfHousing()!= null) && !this.housingDTO.getFuturOfHousing().equals(FuturOfHousing.MAINTENANCE)){
        this.setFutureOfHousingBloc(true);
      }else{
        this.setFutureOfHousingBloc(false);
      }

      if(this.housingDTO.getDateDemolition() != null){
        this.setDemolitionBloc(true);
      }else{
        this.setDemolitionBloc(false);
      }

      if(this.housingDTO.getDateRenovation() != null){
        this.setRenovationBloc(true);
      }else{
        this.setRenovationBloc(false);
      }

      if((this.getHousingDTO().getInsulation()!= null)&&(this.getHousingDTO().getInsulation().equals(Insulation.WALLS))) {
        this.setMurBloc(true);
      }else{
        this.setMurBloc(false);
      }

    }
  }


}
