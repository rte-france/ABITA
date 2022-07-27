/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.archives.beans;

import com.abita.dto.unpersist.ArchivesFileDTO;
import com.abita.web.shared.Month;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bean en relation avec la page /pages/administration/archives/archives.xhtml
 *
 * @author
 */
public class ArchivesBean implements Serializable {

  /** SerialVersionUID */
  private static final long serialVersionUID = -2795266580940091098L;

  /** Mois de référence */
  private List<Month> lstMonths;

  /** Mois de référence */
  private Integer month;

  /** Année de référence */
  private Integer year;

  /** Liste des fichiers */
  private List<ArchivesFileDTO> lstArchivesFile;

  /**
   * Réiniatialise la liste des fichiers
   */
  public void resetLstArchivesFiles() {
    lstArchivesFile = new ArrayList<ArchivesFileDTO>();
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
   * @return the lstArchivesFile
   */
  public List<ArchivesFileDTO> getLstArchivesFile() {
    return lstArchivesFile;
  }

  /**
   * @param lstArchivesFile the lstArchivesFile to set
   */
  public void setLstArchivesFile(List<ArchivesFileDTO> lstArchivesFile) {
    this.lstArchivesFile = lstArchivesFile;
  }

}
