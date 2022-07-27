/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.interfaces.pdfquittancement;

import com.abita.util.textutil.TextUtils;
import com.abita.dto.unpersist.PDFQuittancementFileDTO;
import com.abita.services.jobs.quittancement.constants.QuittancementConstants;
import com.abita.web.admin.interfaces.pdfquittancement.beans.InterfacePDFQuittancementBean;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.Month;
import com.services.paramservice.ParameterService;
import com.services.paramservice.constants.ParamServiceConstants;
import com.services.paramservice.exception.ParameterServiceException;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author
 * Controlleur de la page d'archive des fichiers PDF des quittancements de loyer
 */
public class InterfacePDFQuittancementController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = -6699156407792492021L;

  /** Bean pour la gestion de l'interface des PDF de quittancement de loyer */
  private InterfacePDFQuittancementBean interfacePDFQuittancementBean;

  /** Service parametreService */
  private transient ParameterService parameterService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(InterfacePDFQuittancementController.class);

  /** Taille des mois au format MM */
  private static final int MONTH_LENGTH = 2;

  /**
   * Initialisation du controlleur
   */
  @PostConstruct
  public void init() {
    // Initialisation des listes avec leurs valeurs par défaut
    LocalDate now = new LocalDate();
    interfacePDFQuittancementBean.setMonth(now.getMonthOfYear());
    interfacePDFQuittancementBean.setYear(now.getYear());
  }

  /**
   * Permet d'alimenter la liste de sélection du mois
   * @return liste de mois
   */
  public List<Month> getLstMonths() {
    if (interfacePDFQuittancementBean.getLstMonths() == null || interfacePDFQuittancementBean.getLstMonths().size() == ConstantsWEB.DECIMAL_PART_SIZE_NONE) {
      List<Month> lstMonth = Arrays.asList(Month.values());
      interfacePDFQuittancementBean.setLstMonths(lstMonth);
    }
    return interfacePDFQuittancementBean.getLstMonths();
  }

  /**
   * Lance la recherche au travers des dossiers pour afficher les fichiers correspondants
   */
  public void search() {
    findAllPDFQuittancementFile();
  }

  /**
   * Fonction permettant de récupérer les PDF
   */
  private void findAllPDFQuittancementFile() {
    interfacePDFQuittancementBean.resetLstPDFQuittancementFiles();
    try {
      String path = parameterService.getParameterValue(ParamServiceConstants.ACCESS_DOMAIN_KEY, QuittancementConstants.PATH_QUITTANCEMENT_FOLDER_PARAMETER_KEY);
      // Les critères de selection du mois à afficher
      Integer monthFile = interfacePDFQuittancementBean.getMonth();
      String fileSearch = StringUtils.leftPad(monthFile.toString(), MONTH_LENGTH, "0") + "_" + interfacePDFQuittancementBean.getYear().toString();

      // On recupere le dossier avec les differents fichiers
      File folder = new File(path);

      if (folder.exists()) {
        findFiles(fileSearch, folder);

      }
    } catch (ParameterServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de l'accès au répertoire des quittancements de loyer", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.interface.pdfquittancement.error.access.folder"));
    }
  }

  /**
   * la recherche des fichiers sur le server
   *
   * @param fileSearch le fichier a chercher
   * @param folder     le dossier ou chercher les fichiers
   */
  private void findFiles(String fileSearch, File folder) {
    // On tri pour ne trouver que les fichiers qui contiennent la bonne date dans leur nom
    FileFilter fileFilter = new WildcardFileFilter("*" + fileSearch + "*");

    File[] files = folder.listFiles(fileFilter);

    if (files != null) {
      for (File file : files) {
        PDFQuittancementFileDTO pdfQuittancementFile = new PDFQuittancementFileDTO();
        pdfQuittancementFile.setName(file.getName());
        pdfQuittancementFile.setMimeType(FacesContext.getCurrentInstance().getExternalContext().getMimeType(file.getAbsolutePath()));
        pdfQuittancementFile.setSize(TextUtils.convertSize(file.length()));
        pdfQuittancementFile.setFile(file);
        pdfQuittancementFile.setLastUpdate(new Date(file.lastModified()));
        interfacePDFQuittancementBean.getLstPDFQuittancementFile().add(pdfQuittancementFile);
      }
    }
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * @return the interfacePDFQuittancementBean
   */
  public InterfacePDFQuittancementBean getInterfacePDFQuittancementBean() {
    return interfacePDFQuittancementBean;
  }

  /**
   * @param interfacePDFQuittancementBean the interfacePDFQuittancementBean to set
   */
  public void setInterfacePDFQuittancementBean(InterfacePDFQuittancementBean interfacePDFQuittancementBean) {
    this.interfacePDFQuittancementBean = interfacePDFQuittancementBean;
  }

}
