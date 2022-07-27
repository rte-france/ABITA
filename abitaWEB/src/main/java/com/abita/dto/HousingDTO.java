/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.abita.util.dateutil.DateTimeUtils;
import com.abita.util.sort.DatatablePropertiesSorted;
import com.abita.dto.constant.DTOConstants;
import com.abita.web.shared.ConstantsWEB;
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
import com.dto.AbstractDTO;
import com.dto.UserDTO;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * DTO Logement
 *
 * @author
 */
public class HousingDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 3909553230243176130L;

  /** Référence */
  private String reference;

  /** Adresse */
  @NotBlank(message = "{housing.creation.error.adress}")
  private String address;

  /** Code postal */
  @NotBlank(message = "{housing.creation.error.cp}")
  @Pattern(regexp = "[0-9]{5}", message = "{housing.creation.error.cp.wrong.format}")
  private String postalCode;

  /** Commune */
  @NotBlank(message = "{housing.creation.error.city}")
  private String city;

  /** Date d'entrée */
  @NotNull(message = "{housing.creation.error.date.in}")
  private Date registerDate;

  /** Date de sortie */
  private Date unregisterDate;



  /** Date:avenir de logement */
  private Date futurHousingDate;

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the kitchenDate
   */
  public long getSortedkitchenDate() {
    return DatatablePropertiesSorted.getSortedDate(kitchenDate);
  }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the bathroomDate
   */
  public long getSortedbathroomDate() {
    return DatatablePropertiesSorted.getSortedDate(bathroomDate);
  }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the dpeDate
   */
  public long getSorteddpeDate() {
    return DatatablePropertiesSorted.getSortedDate(dpeDate);
  }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the electDate
   */
  public long getSortedelectDate() { return DatatablePropertiesSorted.getSortedDate(electDate); }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the asbestosDate
   */
  public long getSortedasbestosDate() {
    return DatatablePropertiesSorted.getSortedDate(asbestosDate);
  }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the carrezDate
   */
  public long getSortedcarrezDate() {
    return DatatablePropertiesSorted.getSortedDate(carrezDate);
  }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the termiteDate
   */
  public long getSortedtermiteDate() {
    return DatatablePropertiesSorted.getSortedDate(termiteDate);
  }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the ernmtDate
   */
  public long getSortedernmtDate() {
    return DatatablePropertiesSorted.getSortedDate(ernmtDate);
  }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the leadDate
   */
  public long getSortedleadDate() {
    return DatatablePropertiesSorted.getSortedDate(leadDate);
  }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the gasDate
   */
  public long getSortedgasDate() {
    return DatatablePropertiesSorted.getSortedDate(gasDate);
  }

  /**
   * Motif de sortie, obligatoire si une date de sortie a été saisie
   */
  private ReasonForExit reasonForExit;


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
  /**
   * liste deroulant couverture
   */
  private Couverture couverture;
  /**
   * liste deroulant Façade
   */
  private Facade facade;

  /**
   * liste deroulant Finitions
   */
  private Finitions finitions;

  /**
   * liste deroulant Plomberie
   */
  private Plomberie plomberie;


  /**
   * liste deroulant Assainissement aux normes
   */
  private Sanitationstd sanitationstd;

  /**
   * liste deroulant VMC
   */
  private Vmc vmc;

  /**
   * liste deroulant Etiquette énergétique (DPE)
   */
  public EnergyLabel getEnergyLabel() {
    return energyLabel;
  }

  public void setEnergyLabel(EnergyLabel energyLabel) {
    this.energyLabel = energyLabel;
  }

  public FuturOfHousing getFuturOfHousing() {
    return futurOfHousing;
  }

  public void setFuturOfHousing(FuturOfHousing futurOfHousing) {
    this.futurOfHousing = futurOfHousing;
  }

  public Insulation getInsulation() {
    return insulation;
  }

  public void setInsulation(Insulation insulation) {
    this.insulation = insulation;
  }

  public JoineryFrame getJoineryFrame() {
    return joineryFrame;
  }

  public void setJoineryFrame(JoineryFrame joineryFrame) {
    this.joineryFrame = joineryFrame;
  }

  public Walls getWalls() {
    return walls;
  }

  public void setWalls(Walls walls) {
    this.walls = walls;
  }

  public Couverture getCouverture() {
    return couverture;
  }

  public void setCouverture(Couverture couverture) {
    this.couverture = couverture;
  }

  public Facade getFacade() {
    return facade;
  }

  public void setFacade(Facade facade) {
    this.facade = facade;
  }

  public Finitions getFinitions() {
    return finitions;
  }

  public void setFinitions(Finitions finitions) {
    this.finitions = finitions;
  }

  public Plomberie getPlomberie() {
    return plomberie;
  }

  public void setPlomberie(Plomberie plomberie) {
    this.plomberie = plomberie;
  }

  public Sanitationstd getSanitationstd() {
    return sanitationstd;
  }

  public void setSanitationstd(Sanitationstd sanitationstd) {
    this.sanitationstd = sanitationstd;
  }

  public Vmc getVmc() {
    return vmc;
  }

  public void setVmc(Vmc vmc) {
    this.vmc = vmc;
  }

  /**
   * Commentaire général du logement
   */
  private String generalComment;

  /** Surface réelle */
  @NotNull(message = "{housing.creation.error.real.surface}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{housing.creation.wrong.decimal.realsurface}")
  private BigDecimal realSurfaceArea;

  /** Surface corrigée */
  @NotNull(message = "{housing.creation.error.fixed.surface}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_10, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{housing.creation.wrong.decimal.revisedsurface}")
  private BigDecimal revisedSurfaceArea;

  /** Propriété */
  @NotNull(message = "{housing.creation.error.property}")
  private Boolean property;

  /** Jardin */
  @NotNull(message = "{housing.creation.error.garden}")
  private Boolean gardenAvailable;

  /** Garage */
  @NotNull(message = "{housing.creation.error.garage}")
  private Boolean garageAvailable;

  /** Cuisine Equipée */
  private Boolean equippedKitchen;

  /** Date cuisine */
  private Date kitchenDate;

  /** salle de bain */
  private Boolean bathroom;

  /** Date salle de bain */
  private Date bathroomDate;

  /** DPE */
  @NotNull(message = "{housing.creation.error.dpe}")
  private Boolean dpe;

  /** Gaz */
  @NotNull(message = "{housing.creation.error.gas}")
  private Boolean gas;

  /** Elect */
  @NotNull(message = "{housing.creation.error.elect}")
  private Boolean elect;

  /** Date DPE */
  private Date dpeDate;

  /** Date gaz */
  private Date gasDate;

  /** Date elect */
  private Date electDate;

  /** dernière visite du logement */
  private Date lastVisitDate;

  /**
   * Date l'avenir d'un logement
   */
  @NotBlank(message ="{housing.creation.error.date.futurOfHousing}")
  @Pattern(regexp = "^(?:[0-9]{4}||)$", message = "{housing.creation.error.dateFuturOfHousing.size}")
  private String dateFuturOfHousing;


  /** Année de vente programmer  */
  @Pattern(regexp = "^(?:[0-9]{4}||)$", message = "{housing.creation.error.dateProgrammedSale.size}")
  private String dateProgrammedSale;

  /** Année de la démolition programmer */
  @Pattern(regexp = "^(?:[0-9]{4}||)$", message = "{housing.creation.error.dateDemolition.size}")
  private String dateDemolition;

  /** Année de la rénovation programmer */
  @Pattern(regexp = "^(?:[0-9]{4}||)$", message = "{housing.creation.error.dateRenovation.size}")
  private String dateRenovation;


  public String getDateRenovation() {
    return dateRenovation;
  }

  public void setDateRenovation(String dateRenovation) {
    this.dateRenovation = dateRenovation;
  }

  public String getDateFuturOfHousing() {
    return dateFuturOfHousing;
  }

  public void setDateFuturOfHousing(String dateFuturOfHousing) {
    this.dateFuturOfHousing = dateFuturOfHousing;
  }

  public Date getLastVisitDate() {
	  return DateTimeUtils.clone(lastVisitDate);
  }

  public void setLastVisitDate(Date lastVisitDate) {
	  this.lastVisitDate = DateTimeUtils.clone(lastVisitDate);
  }

  /**
   * Récupère la date au format long afin de faciliter le tri.
   * @return la date lastVisit en long
   */
  public long getSortedLastVisitDate() {
    return DatatablePropertiesSorted.getSortedDate(lastVisitDate);
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

  /** Anomalie Elect */
  private Boolean electAno;

  /** Anomalie Gas */
  private Boolean gasAno;

  /** Amiante */
  @NotNull(message = "{housing.creation.error.asbestos}")
  private Boolean asbestos;

  /** Plomb */
  @NotNull(message = "{housing.creation.error.lead}")
  private Boolean lead;

  /** Termites */
  @NotNull(message = "{housing.creation.error.termite}")
  private Boolean termite;

  /** Date amiante */
  private Date asbestosDate;

  /** Date plomb */
  private Date leadDate;

  /** Date termites */
  private Date termiteDate;

  /** Anomalie amiante */
  private Boolean asbestosAno;

  /** Anomalie plomb */
  private Boolean leadAno;

  /** Anomalie termites */
  private Boolean termiteAno;

  /** Date ERNMT */
  private Date ernmtDate;

  /** Date carrez */
  private Date carrezDate;

  /** ERNMT */
  @NotNull(message = "{housing.creation.error.ernmt}")
  private Boolean ernmt;

  /** Carrez */
  @NotNull(message = "{housing.creation.error.carrez}")
  private Boolean carrez;

  /** Statut du logement */
  @NotNull(message = "{housing.creation.error.status}")
  private HousingStatus housingStatus;

  /** Nombre de pièces */
  @NotNull(message = "{housing.creation.error.room.number}")
  private Integer roomCount;

  /** Metre Carre */
  @NotNull(message = "{housing.creation.error.square.meter}")
  private Integer squareMeter;

  /** Noyau dur */
  @NotNull(message = "{housing.creation.error.core}")
  private Boolean core;

  /** Année de construction */
  @Pattern(regexp = "^(?:[0-9]{4}||)$", message = "{housing.creation.error.year.size}")
  private String year;

  /** Prix (Avenir du logement)   */
  @NotNull(message = "{housing.creation.error.prix}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_10, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{housing.creation.wrong.decimal.futurOfHousingPrice}")
  private BigDecimal futurOfHousingPrice;


  /**cout de la démolition   */
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_NONE, message = "{housing.creation.wrong.decimal.coutDemolition}")
  private BigDecimal coutDemolition;

  /**cout de la rénovation   */
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_8, fraction = ConstantsWEB.DECIMAL_PART_SIZE_NONE, message = "{housing.creation.wrong.decimal.coutRenovation}")
  private BigDecimal coutRenovation;

  /**Emission GES  */
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_10, fraction = ConstantsWEB.DECIMAL_PART_SIZE_NONE, message = "{housing.creation.wrong.decimal.emissionGes}")
  private BigDecimal emissionGes;



  public BigDecimal getCoutRenovation() {
    return coutRenovation;
  }

  public void setCoutRenovation(BigDecimal coutRenovation) {
    this.coutRenovation = coutRenovation;
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



  public BigDecimal getEmissionGes() {
    return emissionGes;
  }

  public void setEmissionGes(BigDecimal emissionGes) {
    this.emissionGes = emissionGes;
  }

  /**
   * Date de derniere maintenance DAAF (détecteur de fumée)
   */
  private Date lastDaafDate;

  /** Nature */
  @NotNull(message = "{housing.creation.error.nature}")
  private HousingNatureDTO housingNature;

  /** Utilisateur identifié comme DIL */
  @NotNull(message = "{housing.creation.error.manager}")
  private UserDTO thirdpartyContractManager;

  /** Categorie du local */
  @NotNull(message = "{housing.creation.error.category}")
  private RoomCategoryDTO roomCategory;

  /** Domaine d'activité du contrat occupant en cours */
  private FieldOfActivityDTO fieldOfActivity;

  /** Agence */
  @NotNull(message = "{housing.creation.error.agency}")
  private AgencyDTO agency;

  /** Site */
  @NotNull(message = "{housing.creation.error.rattachmentSite}")
  private SiteDTO rattachmentSite;

  /** Sanitation */
  @NotNull(message = "{housing.creation.error.sanitation}")
  private SanitationDTO sanitation;

  /** HeatingType */
  @NotNull(message = "{housing.creation.error.heatingType}")
  private HeatingTypeDTO heatingType;

  /** Airconditioner */
  @NotNull(message = "{housing.creation.error.airconditioner}")
  private AirconditionerDTO airconditioner;

  /** OI Logement */
  @NotBlank(message = "{housing.creation.error.oiHousing}")
  private String oiHousing;

  /** EOTP */
  private String eotp;

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
   * Retourne l'adresse tronquée
   * @return l'adresse tronquée
   */
  public String shortAddress() {
    if (address.length() > ConstantsWEB.MAX_CHAR_EXPORT_DATATABLE) {
      return address.substring(0, ConstantsWEB.MAX_CHAR_EXPORT_DATATABLE) + ConstantsWEB.ELLIPSIS;
    }
    return address;
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

  public Date getFuturHousingDate() {
    return DateTimeUtils.clone(futurHousingDate);
  }

  public void setFuturHousingDate(Date futurHousingDate) {
    this.futurHousingDate = DateTimeUtils.clone(futurHousingDate);
  }

  /**
   * @return the unregisterDate
   */
  public Date getUnregisterDate() {
    return DateTimeUtils.clone(unregisterDate);
  }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return the unregisterDate
   */
  public long getSortedUnregisterDate() {
    return DatatablePropertiesSorted.getSortedDate(unregisterDate);
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
   * @param squareMeter the squareMeter to set
   */
  public void setSquareMeter(Integer squareMeter) {
    this.squareMeter = squareMeter;
  }

  /**
   * Méthode permettant de renvoyer un paramètre pour le tri dans une datatable

   * @return le paramètre si non null ou non vide, sinon renvoi un bigDecimal avec la valeur max d'un Double
   */

  public Integer getSortedSquareMeter() {
    if (null == squareMeter) {
      return Integer.MAX_VALUE;
    } else {
      return squareMeter;
    }
  }

  /**
   * @return the squareMeter
   */
  public Integer getSquareMeter() {
    return squareMeter;
  }

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
   * Renvoi l'année si elle n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les z.
   * @return the year
   */
  public String getSortedYear() {

    return DatatablePropertiesSorted.getSortedString(year);
  }


  /**
   * Renvoi l'année si elle n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les z.
   * @return the year
   */
  public String getSortedDateFuturOfHousing() {

    return DatatablePropertiesSorted.getSortedString(dateFuturOfHousing);
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
  public HousingNatureDTO getHousingNature() {
    return housingNature;
  }

  /**
   * Renvoi le label s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant ceux-ci après les z.
   *
   * @return the label
   */
  public String getSortedHousingNature() {
    if (null == housingNature) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(housingNature.getLabel());
    }
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
   * Getter pour le tri des domaines d'activité avec null
   * @return le label du domaine d'activité
   */
  public String getSortedFieldOfActivity() {
    if (null == fieldOfActivity) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(fieldOfActivity.getLabel());
    }
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
   * @return the sanitationDTO
   */
  public SanitationDTO getSanitation() {
    return sanitation;
  }

  /**
   * Getter pour le tri des sanitationDTO avec null
   * @return le label du sanitationDTO
   */
  public String getSortedSanitation() {
    if (null == sanitation) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(sanitation.getLabel());
    }
  }

  /**
   * @param sanitation the sanitation to set
   */
  public void setSanitation(SanitationDTO sanitation) {
    this.sanitation = sanitation;
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
   * Récupère la date au format long afin de faciliter le tri.
   * @return la date daaf en long
   */
  public long getSortedLastDaafDate() {
    return DatatablePropertiesSorted.getSortedDate(lastDaafDate);
  }

  /**
   * @return the rattachmentSite
   */
  public SiteDTO getRattachmentSite() {
    return rattachmentSite;
  }

  /**
   * Tri des sites de rattachements par nom.
   * Si pas de site de rattachement, alors retourne le dernier caractère ASCII
   * @return le site transformé pour le tri
   */
  public String getSortedRattachmentSite() {
    if (null == rattachmentSite) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(rattachmentSite.getName());
    }
  }

  /**
   * @param rattachmentSite the rattachmentSite to set
   */
  public void setRattachmentSite(SiteDTO rattachmentSite) {
    this.rattachmentSite = rattachmentSite;
  }

  /**
   * @return the equippedKitchen
   */
  public Boolean getEquippedKitchen() {
    return equippedKitchen;
  }

  /**
   * Getter pour le tri des sanitationDTO avec null
   * @return le label du sanitationDTO
   */
  public String getSortedEquippedKitchen() {
    if (null == equippedKitchen) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(equippedKitchen);
    }
  }

  /**
   * @param equippedKitchen the equippedKitchen to set
   */
  public void setEquippedKitchen(Boolean equippedKitchen) {
    this.equippedKitchen = equippedKitchen;
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
   * @return the bathroom
   */
  public Boolean getBathroom() {
    return bathroom;
  }

  /**
   * Getter pour le tri des bathroom avec null
   * @return le label du bathroom
   */
  public String getSortedBathroom() {
    if (null == bathroom) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(bathroom);
    }
  }

  /**
   * @param bathroom the bathroom to set
   */
  public void setBathroom(Boolean bathroom) {
    this.bathroom = bathroom;
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
   * @return the dpe
   */
  public Boolean getDpe() {
    return dpe;
  }

  /**
   * Getter pour le tri des dpe avec null
   * @return le label du dpe
   */
  public String getSortedDpe() {
    if (null == dpe) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(dpe);
    }
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
   * Getter pour le tri des gas avec null
   * @return le label du gas
   */
  public String getSortedGas() {
    if (null == gas) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(gas);
    }
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
  public Boolean getElect() {
    return elect;
  }

  /**
   * Getter pour le tri des elect avec null
   * @return le label du elect
   */
  public String getSortedElect() {
    if (null == elect) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(elect);
    }
  }

  /**
   * @param elect the elect to set
   */
  public void setElect(Boolean elect) {
    this.elect = elect;
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
   * @return the electAno
   */
  public Boolean getElectAno() {
    return electAno;
  }

  /**
   * Getter pour le tri des electAno avec null
   * @return le label du electAno
   */
  public String getSortedElectAno() {
    if (null == electAno) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(electAno);
    }
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
   * Getter pour le tri des gasAno avec null
   * @return le label du gasAno
   */
  public String getSortedGasAno() {
    if (null == gasAno) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(gasAno);
    }
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
   * Getter pour le tri des asbestos avec null
   * @return le label du asbestos
   */
  public String getSortedAsbestos() {
    if (null == asbestos) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(asbestos);
    }
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
   * Getter pour le tri des lead avec null
   * @return le label du lead
   */
  public String getSortedLead() {
    if (null == lead) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(lead);
    }
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
   * Getter pour le tri des termite avec null
   * @return le label du termite
   */
  public String getSortedTermite() {
    if (null == termite) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(termite);
    }
  }

  /**
   * @param termite the termite to set
   */
  public void setTermite(Boolean termite) {
    this.termite = termite;
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
   * @return the asbestosAno
   */
  public Boolean getAsbestosAno() {
    return asbestosAno;
  }

  /**
   * Getter pour le tri des asbestosAno avec null
   * @return le label du asbestosAno
   */
  public String getSortedAsbestosAno() {
    if (null == asbestosAno) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(asbestosAno);
    }
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
   * Getter pour le tri des leadAno avec null
   * @return le label du leadAno
   */
  public String getSortedLeadAno() {
    if (null == leadAno) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(leadAno);
    }
  }

  /**
   * @param leadAno the leadAno to set
   */
  public void setLeadAno(Boolean leadAno) {
    this.leadAno = leadAno;
  }

  /**
   * @return the termiteAno
   */
  public Boolean getTermiteAno() {
    return termiteAno;
  }

  /**
   * Getter pour le tri des termiteAno avec null
   * @return le label du termiteAno
   */
  public String getSortedTermiteAno() {
    if (null == termiteAno) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(termiteAno);
    }
  }

  /**
   * @param termiteAno the termiteAno to set
   */
  public void setTermiteAno(Boolean termiteAno) {
    this.termiteAno = termiteAno;
  }

  /**
   * @return the ernmt
   */
  public Boolean getErnmt() {
    return ernmt;
  }

  /**
   * Getter pour le tri des ernmt avec null
   * @return le label du ernmt
   */
  public String getSortedErnmt() {
    if (null == ernmt) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(ernmt);
    }
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
  public Boolean getCarrez() {
    return carrez;
  }

  /**
   * Getter pour le tri des carrez avec null
   * @return le label du carrez
   */
  public String getSortedCarrez() {
    if (null == carrez) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedBoolean(carrez);
    }
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
   * @return the oiHousing
   */
  public String getOiHousing() {
    return oiHousing;
  }

  /**
   * Getter pour le tri des oiHousing avec null
   * @return le label du oiHousing
   */
  public String getSortedOiHousing() {
    if (null == oiHousing) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(oiHousing);
    }
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
   * Getter pour le tri des eotp avec null
   * @return le label du eotp
   */
  public String getSortedEotp() {
    if (null == eotp) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(eotp);
    }
  }

  /**
   * @return the heatingType
   */
  public HeatingTypeDTO getHeatingType() {
    return heatingType;
  }

  /**
   * Getter pour le tri des heatingType avec null
   * @return le label du heatingType
   */
  public String getSortedHeatingType() {
    if (null == heatingType) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(heatingType.getLabel());
    }
  }

  /**
   * @param heatingType the heatingType to set
   */
  public void setHeatingType(HeatingTypeDTO heatingType) {
    this.heatingType = heatingType;
  }

  /**
   * @return the airconditioner
   */
  public AirconditionerDTO getAirconditioner() {
    return airconditioner;
  }

  /**
   * Getter pour le tri des airconditioner avec null
   * @return le label du airconditioner
   */
  public String getSortedAirConditioner() {
    if (null == airconditioner) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(airconditioner.getLabel());
    }
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    HousingDTO that = (HousingDTO) o;
    return reference.equals(that.reference) && address.equals(that.address) && postalCode.equals(that.postalCode) && city.equals(that.city);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), reference, address, postalCode, city);
  }
}
