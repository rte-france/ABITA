/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.interfaces.pdfquittancement.beans;

import com.abita.dto.unpersist.PDFQuittancementFileDTO;
import com.abita.web.shared.Month;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * Backing bean de la page de récupération des PDF de quittancement de loyer
 */
public class InterfacePDFQuittancementBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1140270964841678526L;

  /** Liste des mois */
  private List<Month> lstMonths;

  /** Mois de référence */
  private Integer month;

  /** Année de référence */
  private Integer year;

  /** Liste des fichiers */
  private List<PDFQuittancementFileDTO> lstPDFQuittancementFile;

  /**
   * Réiniatialise la liste des fichiers
   */
  public void resetLstPDFQuittancementFiles() {
    lstPDFQuittancementFile = new ArrayList<PDFQuittancementFileDTO>();
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
   * @return the lstPDFQuittancementFile
   */
  public List<PDFQuittancementFileDTO> getLstPDFQuittancementFile() {
    return lstPDFQuittancementFile;
  }

  /**
   * @param lstPDFQuittancementFile the lstPDFQuittancementFile to set
   */
  public void setLstPDFQuittancementFile(List<PDFQuittancementFileDTO> lstPDFQuittancementFile) {
    this.lstPDFQuittancementFile = lstPDFQuittancementFile;
  }

}
