/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.archives;

import com.abita.util.textutil.TextUtils;
import com.abita.dto.unpersist.ArchivesFileDTO;
import com.abita.web.admin.archives.beans.ArchivesBean;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.Month;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Controlleur en relation avec la page /pages/administration/archives.xhtml
 *
 * @author
 */
public class ArchivesController extends AbstractGenericController {

  /** SerialVersionUID */
  private static final long serialVersionUID = -7339312193947573082L;

  /** Bean pour la gestion des archives */
  private ArchivesBean archivesBean;

  /** Service parametreService */
  private transient ParameterService parameterService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ArchivesController.class);

  /**
   * Initialisation du controlleur
   */
  @PostConstruct
  public void init() {
    // Initialisation des listes avec leurs valeurs par défaut
    LocalDate now = new LocalDate();
    archivesBean.setMonth(now.getMonthOfYear());
    archivesBean.setYear(now.getYear());
  }

  /**
   * Permet d'alimenter la liste de sélection du mois
   * @return liste de mois
   */
  public List<Month> getLstMonths() {
    if (archivesBean.getLstMonths() == null || archivesBean.getLstMonths().isEmpty()) {
      List<Month> lstMonth = Arrays.asList(Month.values());
      archivesBean.setLstMonths(lstMonth);
    }
    return archivesBean.getLstMonths();
  }

  /**
   * Lance la recherche au travers des dossiers pour afficher les fichiers correspondants
   */
  public void search() {
    findAllArchivesFile();
  }

  /**
   * Fonction permettant de récupérer les fichiers d'archivage
   */
  private void findAllArchivesFile() {
    archivesBean.resetLstArchivesFiles();

    try {
      String path = parameterService.getParameterValue(com.services.paramservice.constants.ParamServiceConstants.ACCESS_DOMAIN_KEY,
        ConstantsWEB.PATH_ARCHIVES_FOLDER_PARAMETER_KEY);
      NumberFormat nf = new DecimalFormat(ConstantsWEB.MONTH_ARCHIVES_FILENAME_PATTERN);
      String folderSearch = archivesBean.getYear().toString() + '-' + nf.format(archivesBean.getMonth());

      File folder = new File(path + folderSearch);

      File[] files = folder.listFiles();
      if (folder.exists() && files != null) {

        for (File file : files) {
          ArchivesFileDTO archivesFile = new ArchivesFileDTO();
          archivesFile.setName(file.getName());
          archivesFile.setMimeType(FacesContext.getCurrentInstance().getExternalContext().getMimeType(file.getAbsolutePath()));
          archivesFile.setSize(TextUtils.convertSize(file.length()));
          archivesFile.setFile(file);
          archivesBean.getLstArchivesFile().add(archivesFile);
        }
      }
    } catch (ParameterServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de l'accès au répertoire d'archivage", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.archives.error.access.folder"));
    }
  }

  /**
   * @return the archivesBean
   */
  public ArchivesBean getArchivesBean() {
    return archivesBean;
  }

  /**
   * @param archivesBean the archivesBean to set
   */
  public void setArchivesBean(ArchivesBean archivesBean) {
    this.archivesBean = archivesBean;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

}
