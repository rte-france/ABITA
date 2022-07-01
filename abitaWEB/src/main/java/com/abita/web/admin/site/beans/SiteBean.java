package com.abita.web.admin.site.beans;

import com.abita.dto.AgencyDTO;
import com.abita.dto.SiteDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Données des sites
 */
public class SiteBean implements Serializable {

  private static final long serialVersionUID = 2006165717038423504L;

  /**
   * Liste des sites
   */
  private List<SiteDTO> sites;

  /**
   * Le site à ajouter
   */
  private SiteDTO site;

  /**
   * Liste des agences pour la liste déroulante
   */
  private List<AgencyDTO> agencies;

  public List<SiteDTO> getSites() {
    return sites;
  }

  public void setSites(List<SiteDTO> sites) {
    this.sites = sites;
  }

  public SiteDTO getSite() {
    return site;
  }

  public void setSite(SiteDTO site) {
    this.site = site;
  }

  public List<AgencyDTO> getAgencies() {
    return agencies;
  }

  public void setAgencies(List<AgencyDTO> agencies) {
    this.agencies = agencies;
  }

}
