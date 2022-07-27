/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto.unpersist;

import com.abita.dto.*;
import com.abita.util.dateutil.DateTimeUtils;
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

import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe d'objet non persisté appellable depuis le controleur afin de passer les critères de recherche
 *
 * @author
 */
public class HousingCriteriaDTO implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1476604821423852754L;

  /** Référence interne */
  private Long id;

  /** Référence */
  private String reference;

  /** Adresse */
  private String address;

  /** Code postal */
  private String postalCode;

  /** Commune */
  private String city;

  /** Date d'entrée */
  private Date registerDate;

  /** Date de sortie */
  private Date unregisterDate;

  /** Exclusion des logements ayant une date de sortie renseignée */
  private Boolean excludeHousingWithUnregisterDate;

  /** Motif de sortie */
  private String reasonForExit;

  /** Surface réelle */
  private String realSurfaceArea;

  /** Surface corrigée */
  private String revisedSurfaceArea;

  /** Propriété */
  private Boolean property;

  /** Jardin */
  private Boolean gardenAvailable;

  /** Garage */
  private Boolean garageAvailable;

  /** Cuisine Equipée */
  private Boolean equippedKitchen;

  /** salle de bain */
  private Boolean bathroom;

  /** Date Cuisine */
  private Date kitchenDate;

  /** Date salle de bain */
  private Date bathroomDate;

  /** Date Plomb */
  private Date leadDate;

  /** Date Amiante */
  private Date asbestosDate;

  /** OI Logement */
  private String oiHousing;

  /** eotp */
  private String eotp;

  /** DPE */
  private Boolean dpe;

  /** Gas */
  private Boolean gas;

  /** Elect */
  private Boolean elect;

  /** Date DPE */
  private Date dpeDate;

  /** Date Gaz */
  private Date gasDate;

  /** Date Termites */
  private Date termiteDate;

  /** dernière visite du logement */
  private Date lastVisitDate;

  /** anomalie Elect */
  private Boolean electAno;

  /** Date Elect */
  private Date electDate;

  /** anomalie Gas */
  private Boolean gasAno;

  /** Amiantes */
  private Boolean asbestos;

  /** anomalie Amiantes */
  private Boolean asbestosAno;

  /** Plomb */
  private Boolean lead;

  /** anomalie Plomb */
  private Boolean leadAno;

  /** Termites */
  private Boolean termite;

  /** anomalie Termites */
  private Boolean termiteAno;

  /** ERNMT */
  private Boolean ernmt;

  /** Date ERNMT */
  private Date ernmtDate;

  /** Carrez */
  private Boolean carrez;

  /** DateCarrez */
  private Date carrezDate;

  /** Statut du logement */
  private HousingStatus housingStatus;

  /** Nombre de pièces */
  private String roomCount;

  /** Metre carre */
  private String squareMeter;

  /** Prix (Avenir du logement)   */
  private String futurOfHousingPrice;

  /**cout de la démolition   */
  private String coutDemolition;

  /**cout de la rénovation   */
  private String coutRenovation;

  /**Emission GES  */
  private String emissionGes;

  /** Noyau dur */
  private Boolean core;

  /** Année de construction */
  @Pattern(regexp = "^(?:[0-9]{4}||)$", message = "{housing.creation.error.year.size}")
  private String year;

  /**
   * Date l'avenir d'un logement
   */
  @Pattern(regexp = "^(?:[0-9]{4}||)$", message = "{housing.creation.error.dateFuturOfHousing.size}")
  private String dateFuturOfHousing;


  /** Année de vente programmer  */
  @Pattern(regexp = "^(?:[0-9]{4}||)$", message = "{housing.creation.error.dateProgrammedSale.size}")
  private String dateProgrammedSale;

  /** Année de démolition programmer */
  @Pattern(regexp = "^(?:[0-9]{4}||)$", message = "{housing.creation.error.dateDemolition.size}")
  private String dateDemolition;

  /** Année de la rénovation programmer */
  @Pattern(regexp = "^(?:[0-9]{4}||)$", message = "{housing.creation.error.dateRenovation.size}")
  private String dateRenovation;

  /** Date de derniere maintenance DAAF (détecteur de fumée) */
  private Date lastDaafDate;

  /** Nature */
  private HousingNatureDTO housingNature;

  /** Utilisateur identifié comme DIL */
  private UserDTO thirdpartyContractManager;

  /** Categorie du local */
  private RoomCategoryDTO roomCategory;

  /** Domaine d'activité du contrat occupant en cours */
  private FieldOfActivityDTO fieldOfActivity;

  /** Agence */
  private AgencyDTO agency;

  /** Agence */
  private SanitationDTO sanitation;

  /** Agence */
  private HeatingTypeDTO heatingtype;

  /** Climatisseur */
  private AirconditionerDTO airconditioner;


  /** Site */
  private SiteDTO rattachmentSite;

  /**
   * Etiquette énergétique (DPE)
   */
  private String energyLabel;

  /**
   * les listes deroulante Façade avec réponse bon état, mauvais état, inconnu
   */
  private String facade;
  /**
   * les listes deroulante Finitions avec réponse bon état, mauvais état, inconnu
   */
  private String finitions;
  /**
   * les listes deroulante Couverture avec réponse bon état, mauvais état, inconnu
   */
  private String couverture;

  private String vmc;

  private String plomberie;

  private String sanitationstd;
  /**
   * avenir du logement
   */
  private String futurOfHousing;

  /**
   * Isolation
   */
  private String insulation;

  /**
   * Menuiserie et huiserie
   */
  private String joineryFrame;

  /**
   * liste deroulant des Murs intérieur extérieur rien inconnu
   */
  private String walls;

  public String getEnergyLabel() {
    return energyLabel;
  }

  public void setEnergyLabel(String energyLabel) {
    this.energyLabel = energyLabel;
  }

  public String getFuturOfHousing() {
    return futurOfHousing;
  }

  public void setFuturOfHousing(String futurOfHousing) {
    this.futurOfHousing = futurOfHousing;
  }

  public String getInsulation() {
    return insulation;
  }

  public void setInsulation(String insulation) {
    this.insulation = insulation;
  }

  public String getJoineryFrame() {
    return joineryFrame;
  }

  public void setJoineryFrame(String joineryFrame) {
    this.joineryFrame = joineryFrame;
  }

  public String getWalls() {
    return walls;
  }

  public void setWalls(String walls) {
    this.walls = walls;
  }

  public String getFacade() {  return facade; }

  public void setFacade(String facade) {  this.facade = facade; }

  public String getFinitions() {return finitions; }

  public void setFinitions(String finitions) { this.finitions = finitions; }

  public String getCouverture() { return couverture; }

  public void setCouverture(String couverture) { this.couverture = couverture; }

  public String getVmc() {return vmc;}

  public void setVmc(String vmc) {  this.vmc = vmc;}

  public String getPlomberie() { return plomberie;}

  public void setPlomberie(String plomberie) { this.plomberie = plomberie; }

  public String getSanitationstd() { return sanitationstd; }

  public void setSanitationstd(String sanitationstd) { this.sanitationstd = sanitationstd; }

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

  public Sanitationstd[] getSanitationstdArray() { return Sanitationstd.values(); }

  public Plomberie[] getPlomberieArray() { return Plomberie.values(); }

  public Vmc[] getVmcArray() { return Vmc.values(); }

  public Facade[] getFacadeArray() { return Facade.values(); }

  public Finitions[] getFinitionsArray() { return Finitions.values(); }

  public Date getLastVisitDate() {
    return DateTimeUtils.clone(lastVisitDate);
  }

  public void setLastVisitDate(Date lastVisitDate) {
    this.lastVisitDate = DateTimeUtils.clone(lastVisitDate);
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @param reference the reference to set
   */
  public void setReference(String reference) {
    this.reference = reference;
  }

  /**
   * @return the address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address the address to set
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return the postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * @param postalCode the postalCode to set
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the registerDate
   */
  public Date getRegisterDate() {
    return DateTimeUtils.clone(registerDate);
  }

  /**
   * @param registerDate the registerDate to set
   */
  public void setRegisterDate(Date registerDate) {
    this.registerDate = DateTimeUtils.clone(registerDate);
  }

  /**
   * @return the oiHousing
   */
  public String getOiHousing() {
    return oiHousing;
  }

  /**
   * @param oiHousing the oiHousing to set
   */
  public void setOiHousing(String oiHousing) {
    this.oiHousing = oiHousing;
  }

  /**
   * @return the eotp
   */
  public String getEotp() {
    return eotp;
  }

  /**
   * @param eotp the eotp to set
   */
  public void setEotp(String eotp) {
    this.eotp = eotp;
  }

  /**
   * @return the unregisterDate
   */
  public Date getUnregisterDate() {
    return DateTimeUtils.clone(unregisterDate);
  }

  /**
   * @param unregisterDate the unregisterDate to set
   */
  public void setUnregisterDate(Date unregisterDate) {
    this.unregisterDate = DateTimeUtils.clone(unregisterDate);
  }

  /**
   * Liste des motifs de sortie récupérés depuis l'énumération et le fichier properties
   * @return un tableau contenant toutes les clés des motifs de sortie possibles
   */
  public ReasonForExit[] getReasonForExitArray() {
    return ReasonForExit.values();
  }

  /**
   * @return the realSurfaceArea
   */
  public String getRealSurfaceArea() {
    return realSurfaceArea;
  }

  /**
   * @param realSurfaceArea the realSurfaceArea to set
   */
  public void setRealSurfaceArea(String realSurfaceArea) {
    this.realSurfaceArea = realSurfaceArea;
  }

  /**
   * @return the revisedSurfaceArea
   */
  public String getRevisedSurfaceArea() {
    return revisedSurfaceArea;
  }

  /**
   * @param revisedSurfaceArea the revisedSurfaceArea to set
   */
  public void setRevisedSurfaceArea(String revisedSurfaceArea) {
    this.revisedSurfaceArea = revisedSurfaceArea;
  }

  /**
   * @return the property
   */
  public Boolean getProperty() {
    return property;
  }

  /**
   * @param property the property to set
   */
  public void setProperty(Boolean property) {
    this.property = property;
  }

  /**
   * @return the gardenAvailable
   */
  public Boolean getGardenAvailable() {
    return gardenAvailable;
  }

  /**
   * @param gardenAvailable the gardenAvailable to set
   */
  public void setGardenAvailable(Boolean gardenAvailable) {
    this.gardenAvailable = gardenAvailable;
  }

  /**
   * @return the garageAvailable
   */
  public Boolean getGarageAvailable() {
    return garageAvailable;
  }

  /**
   * @param garageAvailable the garageAvailable to set
   */
  public void setGarageAvailable(Boolean garageAvailable) {
    this.garageAvailable = garageAvailable;
  }

  /**
   * @return the housingStatus
   */
  public HousingStatus getHousingStatus() {
    return housingStatus;
  }

  /**
   * @param housingStatus the housingStatus to set
   */
  public void setHousingStatus(HousingStatus housingStatus) {
    this.housingStatus = housingStatus;
  }

  /**
   * @return the roomCount
   */
  public String getRoomCount() {
    return roomCount;
  }

  /**
   * @param roomCount the roomCount to set
   */
  public void setRoomCount(String roomCount) {
    this.roomCount = roomCount;
  }

  public String getFuturOfHousingPrice() {
    return futurOfHousingPrice;
  }

  public void setFuturOfHousingPrice(String futurOfHousingPrice) {
    this.futurOfHousingPrice = futurOfHousingPrice;
  }

  public String getCoutDemolition() {
    return coutDemolition;
  }

  public void setCoutDemolition(String coutDemolition) {
    this.coutDemolition = coutDemolition;
  }

  public String getCoutRenovation() {
    return coutRenovation;
  }

  public void setCoutRenovation(String coutRenovation) {
    this.coutRenovation = coutRenovation;
  }

  public String getEmissionGes() {
    return emissionGes;
  }

  public void setEmissionGes(String emissionGes) {
    this.emissionGes = emissionGes;
  }

  /**
   * @return the core
   */
  public Boolean getCore() {
    return core;
  }

  /**
   * @param core the core to set
   */
  public void setCore(Boolean core) {
    this.core = core;
  }

  /**
   * @return the year
   */
  public String getYear() {
    return year;
  }

  /**
   * @param year the year to set
   */
  public void setYear(String year) {
    this.year = year;
  }

  public String getDateFuturOfHousing() {
    return dateFuturOfHousing;
  }

  public void setDateFuturOfHousing(String dateFuturOfHousing) {
    this.dateFuturOfHousing = dateFuturOfHousing;
  }

  public String getDateProgrammedSale() {
    return dateProgrammedSale;
  }

  public void setDateProgrammedSale(String dateProgrammedSale) {
    this.dateProgrammedSale = dateProgrammedSale;
  }

  public String getDateDemolition() {
    return dateDemolition;
  }

  public void setDateDemolition(String dateDemolition) {
    this.dateDemolition = dateDemolition;
  }

  public String getDateRenovation() {
    return dateRenovation;
  }

  public void setDateRenovation(String dateRenovation) {
    this.dateRenovation = dateRenovation;
  }

  /**
   * @return the housingNature
   */
  public HousingNatureDTO getHousingNature() {
    return housingNature;
  }

  /**
   * @param housingNature the housingNature to set
   */
  public void setHousingNature(HousingNatureDTO housingNature) {
    this.housingNature = housingNature;
  }

  /**
   * @return the thirdpartyContractManager
   */
  public UserDTO getThirdpartyContractManager() {
    return thirdpartyContractManager;
  }

  /**
   * @param thirdpartyContractManager the thirdpartyContractManager to set
   */
  public void setThirdpartyContractManager(UserDTO thirdpartyContractManager) {
    this.thirdpartyContractManager = thirdpartyContractManager;
  }

  /**
   * @return the roomCategory
   */
  public RoomCategoryDTO getRoomCategory() {
    return roomCategory;
  }

  /**
   * @param roomCategory the roomCategory to set
   */
  public void setRoomCategory(RoomCategoryDTO roomCategory) {
    this.roomCategory = roomCategory;
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
   * @return the agency
   */
  public AgencyDTO getAgency() {
    return agency;
  }

  /**
   * @param agency the agency to set
   */
  public void setAgency(AgencyDTO agency) {
    this.agency = agency;
  }

  /**
   * @return the reasonForExit
   */
  public String getReasonForExit() {
    return reasonForExit;
  }

  /**
   * @param reasonForExit the reasonForExit to set
   */
  public void setReasonForExit(String reasonForExit) {
    this.reasonForExit = reasonForExit;
  }


  /**
   * @return the lastDaafDate
   */
  public Date getLastDaafDate() {
    return DateTimeUtils.clone(lastDaafDate);
  }

  /**
   * @param lastDaafDate the lastDaafDate to set
   */
  public void setLastDaafDate(Date lastDaafDate) {
    this.lastDaafDate = DateTimeUtils.clone(lastDaafDate);
  }

  /**
   * @return the rattachmentSite
   */
  public SiteDTO getRattachmentSite() {
    return rattachmentSite;
  }

  /**
   * @param rattachmentSite the rattachmentSite to set
   */
  public void setRattachmentSite(SiteDTO rattachmentSite) {
    this.rattachmentSite = rattachmentSite;
  }

  /**
   * @return the excludeHousingWithUnregisterDate
   */
  public Boolean getExcludeHousingWithUnregisterDate() {
    return excludeHousingWithUnregisterDate;
  }

  /**
   * @param excludeHousingWithUnregisterDate the excludeHousingWithUnregisterDate to set
   */
  public void setExcludeHousingWithUnregisterDate(Boolean excludeHousingWithUnregisterDate) {
    this.excludeHousingWithUnregisterDate = excludeHousingWithUnregisterDate;
  }

  /**
   * @return the squareMeter
   */
  public String getSquareMeter() {
    return squareMeter;
  }

  /**
   * @param squareMeter the squareMeter to set
   */
  public void setSquareMeter(String squareMeter) {
    this.squareMeter = squareMeter;
  }

  /**
   * @return the equippedKitchen
   */
  public Boolean getEquippedKitchen() {
    return equippedKitchen;
  }

  /**
   * @param equippedKitchen the equippedKitchen to set
   */
  public void setEquippedKitchen(Boolean equippedKitchen) {
    this.equippedKitchen = equippedKitchen;
  }

  /**
   * @return the bathroom
   */
  public Boolean getBathroom() {
    return bathroom;
  }

  /**
   * @param bathroom the bathroom to set
   */
  public void setBathroom(Boolean bathroom) {
    this.bathroom = bathroom;
  }

  /**
   * @return the dpe
   */
  public Boolean getDpe() {
    return dpe;
  }

  /**
   * @param dpe the dpe to set
   */
  public void setDpe(Boolean dpe) {
    this.dpe = dpe;
  }

  /**
   * @return the gas
   */
  public Boolean getGas() {
    return gas;
  }

  /**
   * @param gas the gas to set
   */
  public void setGas(Boolean gas) {
    this.gas = gas;
  }

  /**
   * @return the electDate
   */
  public Date getElectDate() {
    return DateTimeUtils.clone(electDate);
  }

  /**
   * @param electDate the electDate to set
   */
  public void setElectDate(Date electDate) {
    this.electDate = DateTimeUtils.clone(electDate);
  }

  /**
   * @return the leadDate
   */
  public Date getLeadDate() {

    return DateTimeUtils.clone(leadDate);
  }

  /**
   * @param leadDate the leadDate to set
   */
  public void setLeadDate(Date leadDate) {
    this.leadDate = DateTimeUtils.clone(leadDate);
  }

  /**
   * @return the asbestosDate
   */
  public Date getAsbestosDate() {
    return DateTimeUtils.clone(asbestosDate);
  }

  /**
   * @param asbestosDate the asbestosDate to set
   */
  public void setAsbestosDate(Date asbestosDate) {
    this.asbestosDate = DateTimeUtils.clone(asbestosDate);
  }

  /**
   * @return the elect
   */
  public Boolean getElect() {
    return elect;
  }

  /**
   * @param elect the elect to set
   */
  public void setElect(Boolean elect) {
    this.elect = elect;
  }

  /**
   * @return the electAno
   */
  public Boolean getElectAno() {
    return electAno;
  }

  /**
   * @param electAno the electAno to set
   */
  public void setElectAno(Boolean electAno) {
    this.electAno = electAno;
  }

  /**
   * @return the gasAno
   */
  public Boolean getGasAno() {
    return gasAno;
  }

  /**
   * @param gasAno the gasAno to set
   */
  public void setGasAno(Boolean gasAno) {
    this.gasAno = gasAno;
  }

  /**
   * @return the asbestos
   */
  public Boolean getAsbestos() {
    return asbestos;
  }

  /**
   * @param asbestos the asbestos to set
   */
  public void setAsbestos(Boolean asbestos) {
    this.asbestos = asbestos;
  }

  /**
   * @return the lead
   */
  public Boolean getLead() {
    return lead;
  }

  /**
   * @param lead the lead to set
   */
  public void setLead(Boolean lead) {
    this.lead = lead;
  }

  /**
   * @return the termite
   */
  public Boolean getTermite() {
    return termite;
  }

  /**
   * @param termite the termite to set
   */
  public void setTermite(Boolean termite) {
    this.termite = termite;
  }

  /**
   * @return the ernmt
   */
  public Boolean getErnmt() {
    return ernmt;
  }

  /**
   * @param ernmt the ernmt to set
   */
  public void setErnmt(Boolean ernmt) {
    this.ernmt = ernmt;
  }

  /**
   * @return the carrezDate
   */
  public Date getCarrezDate() {
    return DateTimeUtils.clone(carrezDate);
  }

  /**
   * @param carrezDate the carrezDate to set
   */
  public void setCarrezDate(Date carrezDate) {
    this.carrezDate = DateTimeUtils.clone(carrezDate);
  }

  /**
   * @return the carrez
   */
  public Boolean getCarrez() {
    return carrez;
  }

  /**
   * @return the kitchenDate
   */
  public Date getKitchenDate() {
    return DateTimeUtils.clone(kitchenDate);
  }

  /**
   * @param kitchenDate the kitchenDate to set
   */
  public void setKitchenDate(Date kitchenDate) {
    this.kitchenDate = DateTimeUtils.clone(kitchenDate);
  }

  /**
   * @return the bathroomDate
   */
  public Date getBathroomDate() {
    return DateTimeUtils.clone(bathroomDate);
  }

  /**
   * @param bathroomDate the bathroomDate to set
   */
  public void setBathroomDate(Date bathroomDate) {
    this.bathroomDate = DateTimeUtils.clone(bathroomDate);
  }

  /**
   * @return the dpeDate
   */
  public Date getDpeDate() {
    return DateTimeUtils.clone(dpeDate);
  }

  /**
   * @param dpeDate the v to set
   */
  public void setDpeDate(Date dpeDate) {
    this.dpeDate = DateTimeUtils.clone(dpeDate);
  }

  /**
   * @return the gasDate
   */
  public Date getGasDate() {
    return DateTimeUtils.clone(gasDate);
  }

  /**
   * @param gasDate the gasDate to set
   */
  public void setGasDate(Date gasDate) {
    this.gasDate = DateTimeUtils.clone(gasDate);
  }

  /**
   * @return the sanitation
   */
  public SanitationDTO getSanitation() {
    return sanitation;
  }

  /**
   * @param sanitation the sanitation to set
   */
  public void setSanitation(SanitationDTO sanitation) {
    this.sanitation = sanitation;
  }

  /**
   * @return the heatingtype
   */
  public HeatingTypeDTO getHeatingtype() {
    return heatingtype;
  }

  /**
   * @param heatingtype the heatingtype to set
   */
  public void setHeatingtype(HeatingTypeDTO heatingtype) {
    this.heatingtype = heatingtype;
  }

  /**
   * @return the airconditioner
   */
  public AirconditionerDTO getAirconditioner() {
    return airconditioner;
  }

  /**
   * @return the asbestosAno
   */
  public Boolean getAsbestosAno() {
    return asbestosAno;
  }

  /**
   * @param asbestosAno the asbestosAno to set
   */
  public void setAsbestosAno(Boolean asbestosAno) {
    this.asbestosAno = asbestosAno;
  }

  /**
   * @return the leadAno
   */
  public Boolean getLeadAno() {
    return leadAno;
  }

  /**
   * @param leadAno the leadAno to set
   */
  public void setLeadAno(Boolean leadAno) {
    this.leadAno = leadAno;
  }

  /**
   * @return the ernmtDate
   */
  public Date getErnmtDate() {
    return DateTimeUtils.clone(ernmtDate);
  }

  /**
   * @param ernmtDate the ernmtDate to set
   */
  public void setErnmtDate(Date ernmtDate) {
    this.ernmtDate = DateTimeUtils.clone(ernmtDate);
  }

  /**
   * @return the termiteDate
   */
  public Date getTermiteDate() {
    return DateTimeUtils.clone(termiteDate);
  }

  /**
   * @param termiteDate the termiteDate to set
   */
  public void setTermiteDate(Date termiteDate) {
    this.termiteDate = DateTimeUtils.clone(termiteDate);
  }

  public Boolean getTermiteAno() {

    return termiteAno;
  }

  /**
   * @param termiteAno the termiteAno to set
   */
  public void setTermiteAno(Boolean termiteAno) {
    this.termiteAno = termiteAno;
  }

  /**
   * @param airconditioner the airconditioner to set
   */
  public void setAirconditioner(AirconditionerDTO airconditioner) {
    this.airconditioner = airconditioner;
  }

  /**
   * @param carrez the carrez to set
   */
  public void setCarrez(Boolean carrez) {
    this.carrez = carrez;
  }
}
