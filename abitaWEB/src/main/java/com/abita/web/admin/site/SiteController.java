/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.site;

import com.abita.services.agency.IAgencyService;
import com.abita.services.site.ISiteService;
import com.abita.util.comparator.SiteDTONameComparator;
import com.abita.dto.SiteDTO;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.services.site.exception.SiteServiceException;
import com.abita.web.admin.site.beans.SiteBean;
import com.abita.web.shared.AbstractGenericController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.util.Collections;

/**
 * Controller de l’administration des sites
 */
public class SiteController extends AbstractGenericController {

  private static final long serialVersionUID = -1321356971989961712L;

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(SiteController.class);

  /**
   * Données des sites
   */
  private SiteBean siteBean;

  /**
   * Service des site
   */
  private transient ISiteService siteService;

  /**
   * Service gérant les agences
   */
  private transient IAgencyService agencyService;

  /**
   * Initialisation du controller afin de récupérer la liste des sites et des agences
   */
  @PostConstruct
  public void init() {
    initBeanValues();
    findAllSites();
    findAllAgencies();
  }

  /**
   * Récupère la liste sites
   */
  private void findAllSites() {
    try {
      siteBean.setSites(siteService.findAllSites());
    } catch (SiteServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération des sites", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.site.error.listing"));
    }
  }

  /**
   * Récupère la liste des domaines d'activité.
   */
  private void findAllAgencies() {
    try {
      siteBean.setAgencies(agencyService.findAllAgencies());
    } catch (AgencyServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération des agences", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.agency.error.listing"));
    }
  }

  /**
   * Initialisation des données du bean
   */
  private void initBeanValues() {
    siteBean.setSite(new SiteDTO());
  }

  /**
   * Ajoute du site à la liste
   *
   * @param actionEvent : L'ActionEvent
   */
  public void addSiteToList(ActionEvent actionEvent) {
    if (checkIfSiteExists()) {
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.site.error.not.unique"));
    } else {
      SiteDTO site = new SiteDTO();
      // Génère un ID temporaire afin de pouvoir le supprimer si on a pas encore sauvegardé la valeur en BDD
      site.setId(System.currentTimeMillis());
      site.setName(siteBean.getSite().getName());
      site.setAgency(siteBean.getSite().getAgency());
      site.setRemovable(true);
      siteBean.getSites().add(site);
      Collections.sort(siteBean.getSites(), new SiteDTONameComparator());
    }
    initBeanValues();
  }

  /**
   * Sur modification, vérification de l’unicité d'un site
   *
   * @param event l’évènement qui déclenche l'action
   */
  public void onEdit(RowEditEvent event) {
    int nbDoublons = 0;
    String siteLabel = ((SiteDTO) event.getObject()).getName();
    for (SiteDTO site : siteBean.getSites()) {
      if (site.getName().equalsIgnoreCase(siteLabel)) {
        nbDoublons++;
      }
    }

    // si plus de deux éléments avec le même nom dans la liste, alors doublons !
    if (nbDoublons > 1) {
      ((SiteDTO) event.getObject()).setName("Copie de " + siteLabel);
      FacesContext.getCurrentInstance().addMessage(null, getWarningMessage("administration.referentiel.site.warning.not.unique"));
    }
  }

  /**
   * Sauvegarde l'intégralité des données
   *
   * @param actionEvent l’ActionEvent
   */
  public void saveData(ActionEvent actionEvent) {
    try {
      siteService.save(siteBean.getSites());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("administration.referentiel.site.success"));
    } catch (SiteServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde des sites", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    }
  }

  /**
   * Permet de savoir si le site existe déjà
   *
   * @return true si existe, false autrement
   */
  private boolean checkIfSiteExists() {
    boolean siteExists = false;

    String name = siteBean.getSite().getName();
    for (SiteDTO site : siteBean.getSites()) {
      if (site.getName().equalsIgnoreCase(name)) {
        siteExists = true;
        break;
      }
    }

    return siteExists;
  }

  public SiteBean getSiteBean() {
    return siteBean;
  }

  public void setSiteBean(SiteBean siteBean) {
    this.siteBean = siteBean;
  }

  public void setSiteService(ISiteService siteService) {
    this.siteService = siteService;
  }

  public void setAgencyService(IAgencyService agencyService) {
    this.agencyService = agencyService;
  }

}
