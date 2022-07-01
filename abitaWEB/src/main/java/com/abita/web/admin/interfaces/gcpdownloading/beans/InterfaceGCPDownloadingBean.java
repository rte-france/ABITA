/**
 *
 */
package com.abita.web.admin.interfaces.gcpdownloading.beans;

import com.abita.dto.unpersist.GCPFileDTO;
import com.abita.web.shared.Month;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * Bean en relation avec la page /pages/interfaces/gcp.xhtml
 */
public class InterfaceGCPDownloadingBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 2615390355057947922L;

  /** Liste des mois */
  private List<Month> lstMonths;

  /** Mois de référence */
  private Integer month;

  /** Année de référence */
  private Integer year;

  /** Liste des fichiers */
  private List<GCPFileDTO> lstGCPFile;

  /**
   * Réiniatialise la liste des fichiers
   */
  public void resetLstGCPFiles() {
    lstGCPFile = new ArrayList<GCPFileDTO>();
  }

  /**
   * @return the lstMonths
   */
  public List<Month> getLstMonths() {
    return lstMonths;
  }

  /**
   * @param lstMonths the lstMonths to set
   */
  public void setLstMonths(List<Month> lstMonths) {
    this.lstMonths = lstMonths;
  }

  /**
   * @return the month
   */
  public Integer getMonth() {
    return month;
  }

  /**
   * @param month the month to set
   */
  public void setMonth(Integer month) {
    this.month = month;
  }

  /**
   * @return the year
   */
  public Integer getYear() {
    return year;
  }

  /**
   * @param year the year to set
   */
  public void setYear(Integer year) {
    this.year = year;
  }

  /**
   * @return the lstGCPFile
   */
  public List<GCPFileDTO> getLstGCPFile() {
    return lstGCPFile;
  }

  /**
   * @param lstGCPFile the lstGCPFile to set
   */
  public void setLstGCPFile(List<GCPFileDTO> lstGCPFile) {
    this.lstGCPFile = lstGCPFile;
  }

}
