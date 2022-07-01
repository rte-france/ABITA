/**
 *
 */
package com.abita.web.admin.interfaces.artesisdownloading.beans;

import com.abita.dto.unpersist.ArtesisFileDTO;
import com.abita.web.shared.Month;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 *
 */
public class InterfaceArtesisDownloadingBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -2019884802531220280L;

  /** Liste des mois */
  private List<Month> lstMonths;

  /** Mois de référence */
  private Integer month;

  /** Année de référence */
  private Integer year;

  /** Liste des fichiers */
  private List<ArtesisFileDTO> lstArtesisFile;

  /**
   * Réiniatialise la liste des fichiers
   */
  public void resetLstArtesisFiles() {
    lstArtesisFile = new ArrayList<ArtesisFileDTO>();
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
   * @return the lstArtesisFile
   */
  public List<ArtesisFileDTO> getLstArtesisFile() {
    return lstArtesisFile;
  }

  /**
   * @param lstArtesisFile the lstArtesisFile to set
   */
  public void setLstArtesisFile(List<ArtesisFileDTO> lstArtesisFile) {
    this.lstArtesisFile = lstArtesisFile;
  }

}
