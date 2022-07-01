package com.abita.web.admin.agency;

import com.abita.services.agency.IAgencyService;
import com.abita.util.comparator.AgencyDTONameComparator;
import com.abita.dto.AgencyDTO;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.web.admin.agency.beans.AgencyBean;
import com.abita.web.shared.AbstractGenericController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.Collections;

/**
 * Contrôlleur de l’administration des agences
 */
public class AgencyController extends AbstractGenericController {

  /** SerialID */
  private static final long serialVersionUID = -7073940021817712299L;

  /** Données des agences */
  private AgencyBean agencyBean;

  /** Service des agences */
  private transient IAgencyService agencyService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(AgencyController.class);

  /**
   * Initialisation du controlleur afin de récupérer la liste des agences
   */
  @PostConstruct
  public void init() {
    initBeanValues();
    findAllAgencies();
  }

  /**
   * Récupère la liste agences
   */
  private void findAllAgencies() {
    try {
      agencyBean.setAgencies(agencyService.findAllAgencies());
    } catch (AgencyServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération des agences", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.agency.error.listing"));
    }
  }

  /**
   * Initialisation des données du bean
   */
  private void initBeanValues() {
    agencyBean.setAgency(new AgencyDTO());
  }

  /**
   * Ajoute l’agence à la liste
   * @param actionEvent : L'ActionEvent
   */
  public void addAgencyToList(ActionEvent actionEvent) {
    if (checkIfAgencyExists()) {
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.agency.error.not.unique"));
    } else {
      AgencyDTO agency = new AgencyDTO();
      // Génère un ID temporaire afin de pouvoir le supprimer si on a pas encore sauvegardé la valeur en BDD
      agency.setId(System.currentTimeMillis());
      agency.setName(agencyBean.getAgency().getName());
      agency.setRemovable(true);
      agencyBean.getAgencies().add(agency);
      Collections.sort(agencyBean.getAgencies(), new AgencyDTONameComparator());
    }
    initBeanValues();
  }

  /**
   * Sur modification, vérification de l’unicité d'une agence
   * @param event l’évènement qui déclenche l'action
   */
  public void onEdit(RowEditEvent event) {
    int nb = 0;

    String label = ((AgencyDTO) event.getObject()).getName();
    for (AgencyDTO agency : agencyBean.getAgencies()) {
      if (agency.getName().equalsIgnoreCase(label)) {
        nb++;
      }
    }

    if (nb > 1) {
      ((AgencyDTO) event.getObject()).setName("Copie de " + label);
      FacesContext.getCurrentInstance().addMessage(null, getWarningMessage("administration.referentiel.agency.warning.not.unique"));
    }
  }

  /**
   * Sauvegarde l'intégralité des données
   * @param actionEvent l’ActionEvent
   */
  public void saveData(ActionEvent actionEvent) {
    try {
      agencyService.save(agencyBean.getAgencies());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("administration.referentiel.agency.success"));
    } catch (AgencyServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde des agences", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    }
  }

  /**
   * Permet de savoir si l’agence existe déjà
   * @return true si existe, false autrement
   */
  private boolean checkIfAgencyExists() {
    boolean agencyExists = false;

    String name = agencyBean.getAgency().getName();
    for (AgencyDTO agency : agencyBean.getAgencies()) {
      if (agency.getName().equalsIgnoreCase(name)) {
        agencyExists = true;
        break;
      }
    }

    return agencyExists;
  }

  /**
   * @return the agencyBean
   */
  public AgencyBean getAgencyBean() {
    return agencyBean;
  }

  /**
   * @param agencyBean the agencyBean to set
   */
  public void setAgencyBean(AgencyBean agencyBean) {
    this.agencyBean = agencyBean;
  }

  /**
   * @param agencyService the agencyService to set
   */
  public void setAgencyService(IAgencyService agencyService) {
    this.agencyService = agencyService;
  }

}
