package com.abita.web.admin.fieldofactivity.beans;

import com.abita.dto.AgencyDTO;
import com.abita.dto.FieldOfActivityDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Bean en relation avec la page /pages/administration/referentiel/fieldofactivity.xhtml
 *
 * @author
 */
public class FieldOfActivityBean implements Serializable {

  /** SerialVersionUID */
  private static final long serialVersionUID = -232500146800151331L;

  /** Liste des domaines d'activité */
  private List<FieldOfActivityDTO> lstFieldOfActivity;

  /** Liste des agences */
  private List<AgencyDTO> agencies;

  /** Le domaine d'activité à ajouter */
  private String fieldOfActivity;

  /** GMR */
  private String gmr;

  /** Agence */
  private AgencyDTO agency;

  /**
   * @return the lstFieldOfActivity
   */
  public List<FieldOfActivityDTO> getLstFieldOfActivity() {
    return lstFieldOfActivity;
  }

  /**
   * @param lstFieldOfActivity the lstFieldOfActivity to set
   */
  public void setLstFieldOfActivity(List<FieldOfActivityDTO> lstFieldOfActivity) {
    this.lstFieldOfActivity = lstFieldOfActivity;
  }

  /**
   * @return the fieldofactivity
   */
  public String getFieldOfActivity() {
    return fieldOfActivity;
  }

  /**
   * @param fieldOfActivity the fieldOfActivity to set
   */
  public void setFieldOfActivity(String fieldOfActivity) {
    this.fieldOfActivity = fieldOfActivity;
  }

  /**
   * @return the gmr
   */
  public String getGmr() {
    return gmr;
  }

  /**
   * @param gmr the gmr to set
   */
  public void setGmr(String gmr) {
    this.gmr = gmr;
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

}
