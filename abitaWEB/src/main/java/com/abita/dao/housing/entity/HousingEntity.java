/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.housing.entity;

import com.abita.dao.agency.entity.AgencyEntity;
import com.abita.dao.heatingtype.entity.HeatingTypeEntity;
import com.abita.dao.sanitation.entity.SanitationEntity;
import com.abita.dao.site.entity.SiteEntity;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dao.airconditioner.entity.AirconditionerEntity;
import com.abita.dao.fieldofactivity.entity.FieldOfActivityEntity;
import com.abita.dao.housingnature.entity.HousingNatureEntity;
import com.abita.dao.roomcategory.entity.RoomCategoryEntity;
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
import com.dao.common.entity.AbstractEntity;
import com.dao.user.entity.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Entité logement
 *
 * @author
 */
public class HousingEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = -8423244928162571656L;

  /** Référence */
  private String reference;

  /** Adresse */
  private String address;

  /** Code postal */
  private String postalCode;

  /** Commune */
  private String city;

  /** oiHousing */
  private String oiHousing;

  /** EOTP */
  private String eotp;

  /** Date d'entrée */
  private Date registerDate;

  /** Date de sortie */
  private Date unregisterDate;

  /** Date cuisine */
  private Date kitchenDate;

  /** Date salle de bain */
  private Date bathroomDate;

  /** Date dpe */
  private Date dpeDate;

  /** Date gas */
  private Date gasDate;

  /** Date ERNMT */
  private Date ernmtDate;

  /** Date Elect */
  private Date electDate;

  /** Date Carrez */
  private Date carrezDate;

  /** Date Lead */
  private Date leadDate;

  /** Date Amiante */
  private Date asbestosDate;

  /** Date Termites */
  private Date termiteDate;

  /** Motif de sortie */
  private ReasonForExit reasonForExit;

  /** Assainissement aux normes*/
  private Sanitationstd sanitationstd;

  /** Façade*/
  private Facade facade;

  /** couverture*/
  private Couverture couverture;

  /** Finitions intérieurs (sols, murs, plafonds …)*/
  private Finitions finitions;

  /** VMC*/
  private Vmc vmc;

  /** plomberie*/
  private Plomberie plomberie;


  /**
   * Etiquette énergétique (DPE)
   */
  private EnergyLabel energyLabel;

  /**
   * avenir du logement
   */
  private FuturOfHousing futurOfHousing;

  /**
   * Isolation
   */
  private Insulation insulation;

  /**
   * Menuiserie et huiserie
   */
  private JoineryFrame joineryFrame;

 /**
   * liste deroulant des Murs intérieur extérieur rien inconnu
   */
  private Walls walls;

  /** Commentaire général */
  private String generalComment;

  /** Surface réelle */
  private BigDecimal realSurfaceArea;

  /** Surface corrigée */
  private BigDecimal revisedSurfaceArea;

  /** Propriété */
  private boolean property;

  /** Jardin */
  private boolean gardenAvailable;

  /** Cuisine equipee */
  private Boolean equippedKitchen;

  /** salle de bain */
  private Boolean bathroom;

  /** DPE */
  private Boolean dpe;

  /** Gaz */
  private Boolean gas;

  /** Elect */
  private Boolean elect;

  /** Anomalie Elect */
  private Boolean electAno;

  /** Anomalie Plomb */
  private Boolean leadAno;

  /** Anomalie Gas */
  private Boolean gasAno;

  /** Amiante */
  private Boolean asbestos;

  /** Anomalie Amiantes */
  private Boolean asbestosAno;

  /** Plomb */
  private Boolean lead;

  /** Termites */
  private Boolean termite;

  /** Anomalie Termites */
  private Boolean termiteAno;

  /** ERNMT */
  private Boolean ernmt;

  /** Carrez */
  private Boolean carrez;

  /** Garage */
  private boolean garageAvailable;

  /** Statut du logement */
  private HousingStatus housingStatus;

  /** Nombre de pièces */
  private Integer roomCount;

  /** Metre carre */
  private Integer squareMeter;

  /** Noyau dur */
  private boolean core;

  /** Année de construction */
  private String year;

  /**
   * Date de dernière maintenance du DAAF (détecteur de fumée)
   */
  private Date lastDaafDate;

  /** Nature */
  private HousingNatureEntity housingNature;

  /** Utilisateur identifié comme DIL */
  private User thirdpartyContractManager;

  /** Categorie du local */
  private RoomCategoryEntity roomCategory;

  /** Domaine d'acitivité */
  private FieldOfActivityEntity fieldOfActivity;

  /** Agence */
  private AgencyEntity agency;

  /** Site de rattachement du logement */
  private SiteEntity rattachmentSite;

  /** Assainissement */
  private SanitationEntity sanitation;

  /** Type de chauffage */
  private HeatingTypeEntity heatingType;

  /** Climatisation */
  private AirconditionerEntity airconditioner;

  /** date de la vente ou de la demolition d'un logement */
  private String dateFuturOfHousing;

  /** Date Vente programmee  */
  private String dateProgrammedSale;

  /** Date demolition*/
  private String dateDemolition;

  /** Date de renovation */
  private String dateRenovation;

  /** cout demolition*/
  private BigDecimal coutDemolition;

  /** cout renovation */
  private BigDecimal coutRenovation;

  /** Emission GES */
  private BigDecimal emissionGes;

  /** Prix (Avenir du logement)   */
  private BigDecimal futurOfHousingPrice;

  /** dernière visite */
  private Date lastVisitDate;

  public FuturOfHousing getFuturOfHousing() {
    return futurOfHousing;
  }

  public void setFuturOfHousing(FuturOfHousing futurOfHousing) {
    this.futurOfHousing = futurOfHousing;
  }

  public Date getLastVisitDate() {
	  return DateTimeUtils.clone(lastVisitDate);
  }

  public void setLastVisitDate(Date lastVisitDate) {
	  this.lastVisitDate = DateTimeUtils.clone(lastVisitDate);
  }

  public Insulation getInsulation() {
    return insulation;
  }

  public void setInsulation(Insulation insulation) {
    this.insulation = insulation;
  }

  public Walls getWalls() {
    return walls;
  }

  public void setWalls(Walls walls) {
    this.walls = walls;
  }

  public JoineryFrame getJoineryFrame() {
    return joineryFrame;
  }

  public void setJoineryFrame(JoineryFrame joineryFrame) {
    this.joineryFrame = joineryFrame;
  }

  public EnergyLabel getEnergyLabel() {
    return energyLabel;
  }

  public void setEnergyLabel(EnergyLabel energyLabel) {
    this.energyLabel = energyLabel;
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

  public BigDecimal getCoutDemolition() {
    return coutDemolition;
  }

  public void setCoutDemolition(BigDecimal coutDemolition) {
    this.coutDemolition = coutDemolition;
  }

  public BigDecimal getFuturOfHousingPrice() {
    return futurOfHousingPrice;
  }

  public void setFuturOfHousingPrice(BigDecimal futurOfHousingPrice) {
    this.futurOfHousingPrice = futurOfHousingPrice;
  }

  public String getDateRenovation() {
    return dateRenovation;
  }

  public void setDateRenovation(String dateRenovation) {
    this.dateRenovation = dateRenovation;
  }

  public BigDecimal getCoutRenovation() {
    return coutRenovation;
  }

  public void setCoutRenovation(BigDecimal coutRenovation) {
    this.coutRenovation = coutRenovation;
  }

  public BigDecimal getEmissionGes() {
    return emissionGes;
  }

  public void setEmissionGes(BigDecimal emissionGes) {
    this.emissionGes = emissionGes;
  }

  public static long getSerialVersionUID() {
    return serialVersionUID;
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
   * @return the realSurfaceArea
   */
  public BigDecimal getRealSurfaceArea() {
    return realSurfaceArea;
  }

  /**
   * @param realSurfaceArea the realSurfaceArea to set
   */
  public void setRealSurfaceArea(BigDecimal realSurfaceArea) {
    this.realSurfaceArea = realSurfaceArea;
  }

  /**
   * @return the revisedSurfaceArea
   */
  public BigDecimal getRevisedSurfaceArea() {
    return revisedSurfaceArea;
  }

  /**
   * @param revisedSurfaceArea the revisedSurfaceArea to set
   */
  public void setRevisedSurfaceArea(BigDecimal revisedSurfaceArea) {
    this.revisedSurfaceArea = revisedSurfaceArea;
  }

  /**
   * @return the Property
   */
  public boolean isProperty() {
    return property;
  }

  /**
   * @param property the property to set
   */
  public void setProperty(boolean property) {
    this.property = property;
  }

  /**
   * @return the gardenAvailable
   */
  public boolean isGardenAvailable() {
    return gardenAvailable;
  }

  /**
   * @param gardenAvailable the gardenAvailable to set
   */
  public void setGardenAvailable(boolean gardenAvailable) {
    this.gardenAvailable = gardenAvailable;
  }

  /**
   * @return the garageAvailable
   */
  public boolean isGarageAvailable() {
    return garageAvailable;
  }

  /**
   * @param garageAvailable the garageAvailable to set
   */
  public void setGarageAvailable(boolean garageAvailable) {
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
  public Integer getRoomCount() {
    return roomCount;
  }

  /**
   * @param roomCount the roomCount to set
   */
  public void setRoomCount(Integer roomCount) {
    this.roomCount = roomCount;
  }

  /**
   * @return the core
   */
  public boolean isCore() {
    return core;
  }

  /**
   * @param core the core to set
   */
  public void setCore(boolean core) {
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

  /**
   * @return the housingNature
   */
  public HousingNatureEntity getHousingNature() {
    return housingNature;
  }

  /**
   * @param housingNature the housingNature to set
   */
  public void setHousingNature(HousingNatureEntity housingNature) {
    this.housingNature = housingNature;
  }

  /**
   * @return the thirdpartyContractManager
   */
  public User getThirdpartyContractManager() {
    return thirdpartyContractManager;
  }

  /**
   * @param thirdpartyContractManager the thirdpartyContractManager to set
   */
  public void setThirdpartyContractManager(User thirdpartyContractManager) {
    this.thirdpartyContractManager = thirdpartyContractManager;
  }

  /**
   * @return the roomCategory
   */
  public RoomCategoryEntity getRoomCategory() {
    return roomCategory;
  }

  /**
   * @param roomCategory the roomCategory to set
   */
  public void setRoomCategory(RoomCategoryEntity roomCategory) {
    this.roomCategory = roomCategory;
  }

  /**
   * @return the agency
   */
  public AgencyEntity getAgency() {
    return agency;
  }

  /**
   * @param agency the agency to set
   */
  public void setAgency(AgencyEntity agency) {
    this.agency = agency;
  }

  /**
   * @return the fieldOfActivity
   */
  public FieldOfActivityEntity getFieldOfActivity() {
    return fieldOfActivity;
  }

  /**
   * @param fieldOfActivity the fieldOfActivity to set
   */
  public void setFieldOfActivity(FieldOfActivityEntity fieldOfActivity) {
    this.fieldOfActivity = fieldOfActivity;
  }

  /**
   * @return the reasonForExit
   */
  public ReasonForExit getReasonForExit() {
    return reasonForExit;
  }

  /**
   * @param reasonForExit the reasonForExit to set
   */
  public void setReasonForExit(ReasonForExit reasonForExit) {
    this.reasonForExit = reasonForExit;
  }

  /**
   *
   * @return sanitationstd
   */
  public Sanitationstd getSanitationstd() {
    return sanitationstd;
  }

  /**
   * sanitationstd to set
   * @param sanitationstd
   */
  public void setSanitationstd(Sanitationstd sanitationstd) {
    this.sanitationstd = sanitationstd;
  }

  /**
   *
   * @return facade
   */
  public Facade getFacade() {
    return facade;
  }

  /**
   *
   * @param facade to set
   */
  public void setFacade(Facade facade) {
    this.facade = facade;
  }

  /**
   *
   * @return couverture
   */
  public Couverture getCouverture() {
    return couverture;
  }

  /**
   *
   * @param couverture to set
   */
  public void setCouverture(Couverture couverture) {
    this.couverture = couverture;
  }

  /**
   *
   * @return finitions
   */

  public Finitions getFinitions() {
    return finitions;
  }

  /**
   *
   * @param finitions to set
   */
  public void setFinitions(Finitions finitions) {
    this.finitions = finitions;
  }

  /**
   *
   * @return vmc
   */
  public Vmc getVmc() {
    return vmc;
  }

  /**
   *
   * @param vmc to set
   */
  public void setVmc(Vmc vmc) {
    this.vmc = vmc;
  }

  /**
   *
   * @return plomberie
   */
  public Plomberie getPlomberie() {
    return plomberie;
  }

  /**
   *
   * @param plomberie to set
   */
  public void setPlomberie(Plomberie plomberie) {
    this.plomberie = plomberie;
  }

  /**
   * @return the generalComment
   */
  public String getGeneralComment() {
    return generalComment;
  }

  /**
   * @param generalComment the generalComment to set
   */
  public void setGeneralComment(String generalComment) {
    this.generalComment = generalComment;
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
  public SiteEntity getRattachmentSite() {
    return rattachmentSite;
  }

  /**
   * @param site the site to set
   */
  public void setRattachmentSite(SiteEntity site) {
    this.rattachmentSite = site;
  }

  /**
   * @return the squareMeter
   */
  public Integer getSquareMeter() {
    return squareMeter;
  }

  /**
   * @param squareMeter the squareMeter to set
   */
  public void setSquareMeter(Integer squareMeter) {
    this.squareMeter = squareMeter;
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
   * @param dpeDate the dpeDate to set
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
   * @return the equippedKitchen
   */
  public Boolean isEquippedKitchen() {
    return equippedKitchen;
  }

  /**
   * @return the bathroom
   */
  public Boolean isBathroom() {
    return bathroom;
  }

  /**
   * @return the dpe
   */
  public Boolean isDpe() {
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
  public Boolean isGas() {
    return gas;
  }

  /**
   * @param gas the gas to set
   */
  public void setGas(Boolean gas) {
    this.gas = gas;
  }

  /**
   * @return the elect
   */
  public Boolean isElect() {
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
  public Boolean isElectAno() {
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
  public Boolean isGasAno() {
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
  public Boolean isAsbestos() {
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
  public Boolean isLead() {
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
  public Boolean isTermite() {
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
  public Boolean isErnmt() {
    return ernmt;
  }

  /**
   * @param ernmt the ernmt to set
   */
  public void setErnmt(Boolean ernmt) {
    this.ernmt = ernmt;
  }

  /**
   * @return the carrez
   */
  public Boolean isCarrez() {
    return carrez;
  }

  /**
   * @param carrez the carrez to set
   */
  public void setCarrez(Boolean carrez) {
    this.carrez = carrez;
  }

  /**
   * @return the sanitation
   */
  public SanitationEntity getSanitation() {
    return sanitation;
  }

  /**
   * @param sanitation the sanitation to set
   */
  public void setSanitation(SanitationEntity sanitation) {
    this.sanitation = sanitation;
  }

  /**
   * @return the heatingType
   */
  public HeatingTypeEntity getHeatingType() {
    return heatingType;
  }

  /**
   * @return the oiHousing
   */
  public String getOiHousing() {
    return oiHousing;
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
   * @return the asbestosAno
   */
  public Boolean isAsbestosAno() {
    return asbestosAno;
  }

  /**
   * @param asbestosAno the asbestosAno to set
   */
  public void setAsbestosAno(Boolean asbestosAno) {
    this.asbestosAno = asbestosAno;
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

  /**
   * @return the termiteAno
   */
  public Boolean isTermiteAno() {

    return termiteAno;
  }

  /**
   * @param termiteAno the termiteAno to set
   */
  public void setTermiteAno(Boolean termiteAno) {
    this.termiteAno = termiteAno;
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
   * @return the leadAno
   */
  public Boolean isLeadAno() {
    return leadAno;
  }

  /**
   * @param leadAno the leadAno to set
   */
  public void setLeadAno(Boolean leadAno) {
    this.leadAno = leadAno;
  }

  /**
   * @param eotp the eotp to set
   */
  public void setEotp(String eotp) {
    this.eotp = eotp;
  }

  /**
   * @param heatingType the heatingType to set
   */
  public void setHeatingType(HeatingTypeEntity heatingType) {
    this.heatingType = heatingType;
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
   * @return the airconditioner
   */

  public AirconditionerEntity getAirconditioner() {
    return airconditioner;
  }

  public Boolean getEquippedKitchen() {
    return equippedKitchen;
  }

  public Boolean getBathroom() {
    return bathroom;
  }

  public Boolean getDpe() {
    return dpe;
  }

  public Boolean getGas() {
    return gas;
  }

  public Boolean getElect() {
    return elect;
  }

  public Boolean getElectAno() {
    return electAno;
  }

  public Boolean getLeadAno() {
    return leadAno;
  }

  public Boolean getGasAno() {
    return gasAno;
  }

  public void setEquippedKitchen(Boolean equippedKitchen) {
    this.equippedKitchen = equippedKitchen;
  }

  public void setBathroom(Boolean bathroom) {
    this.bathroom = bathroom;
  }

  public Boolean getAsbestos() {
    return asbestos;
  }

  public Boolean getAsbestosAno() {
    return asbestosAno;
  }

  public Boolean getLead() {
    return lead;
  }

  public Boolean getTermite() {
    return termite;
  }

  public Boolean getTermiteAno() {
    return termiteAno;
  }

  public Boolean getErnmt() {
    return ernmt;
  }

  public Boolean getCarrez() {
    return carrez;
  }

  /**
   * @param airconditioner the airconditioner to set
   */

  public void setAirconditioner(AirconditionerEntity airconditioner) {
    this.airconditioner = airconditioner;
  }


}

