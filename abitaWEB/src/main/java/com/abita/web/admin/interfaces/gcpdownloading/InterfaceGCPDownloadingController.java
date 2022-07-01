package com.abita.web.admin.interfaces.gcpdownloading;

import com.abita.dao.batch.constant.BatchConstants;
import com.abita.util.textutil.TextUtils;
import com.abita.dto.unpersist.GCPFileDTO;
import com.abita.web.admin.interfaces.gcpdownloading.beans.InterfaceGCPDownloadingBean;
import com.abita.web.admin.interfaces.gcpdownloading.comparators.GCPFileDTOComparator;
import com.abita.web.admin.interfaces.gcpdownloading.comparators.GCPFileDTODateComparator;
import com.abita.web.admin.interfaces.gcpdownloading.comparators.GCPFileDTOTypeComparator;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.Month;
import com.services.paramservice.ParameterService;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author
 *         Controlleur en relation avec la page /pages/interfaces/gcp.xhtml
 */
public class InterfaceGCPDownloadingController extends AbstractGenericController {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -8252620109594348050L;

  /**
   * Bean pour la gestion de l'interface GCP
   */
  private InterfaceGCPDownloadingBean interfaceGCPDownloadingBean;

  /**
   * Service parametreService
   */
  private transient ParameterService parameterService;

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceGCPDownloadingController.class);

  /**
   * Taille des mois au format MM
   */
  private static final int MONTH_LENGTH = 2;

  /**
   * Initialisation du controlleur
   */
  @PostConstruct
  public void init() {
    // Initialisation des listes avec leurs valeurs par défaut
    LocalDate now = new LocalDate();
    interfaceGCPDownloadingBean.setMonth(now.getMonthOfYear());
    interfaceGCPDownloadingBean.setYear(now.getYear());
  }

  /**
   * Permet d'alimenter la liste de sélection du mois
   *
   * @return liste de mois
   */
  public List<Month> getLstMonths() {
    if (interfaceGCPDownloadingBean.getLstMonths() == null || interfaceGCPDownloadingBean.getLstMonths().size() == ConstantsWEB.DECIMAL_PART_SIZE_NONE) {
      List<Month> lstMonth = Arrays.asList(Month.values());
      interfaceGCPDownloadingBean.setLstMonths(lstMonth);
    }
    return interfaceGCPDownloadingBean.getLstMonths();
  }

  /**
   * Lance la recherche au travers des dossiers pour afficher les fichiers correspondants
   */
  public void search() {
    findAllGCPFile();
  }

  /**
   * Fonction permettant de récupérer les pièces comptables GCP
   */
  private void findAllGCPFile() {
    interfaceGCPDownloadingBean.resetLstGCPFiles();
    try {
      String path = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, BatchConstants.BATCH_GCP_ARCHIVE_PATH);
      // Les critères de selection du mois à afficher
      Integer monthFile = interfaceGCPDownloadingBean.getMonth();
      String fileSearch = StringUtils.leftPad(monthFile.toString(), MONTH_LENGTH, "0") + interfaceGCPDownloadingBean.getYear().toString();

      // On recupere le dossier avec les differents fichiers
      File folder = new File(path);

      if (folder.exists()) {

        findFiles(fileSearch, folder);
      }
    } catch (ParameterServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de l'accès au répertoire GCP", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.interface.gcp.error.access.folder"));
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
        GCPFileDTO gcpFile = new GCPFileDTO();
        gcpFile.setName(file.getName());
        gcpFile.setMimeType(FacesContext.getCurrentInstance().getExternalContext().getMimeType(file.getAbsolutePath()));
        gcpFile.setSize(TextUtils.convertSize(file.length()));
        gcpFile.setFile(file);
        gcpFile.setLastUpdate(new Date(file.lastModified()));
        interfaceGCPDownloadingBean.getLstGCPFile().add(gcpFile);
      }
    }

    Collections.sort(interfaceGCPDownloadingBean.getLstGCPFile(), new GCPFileDTOComparator(new GCPFileDTOTypeComparator(), new GCPFileDTODateComparator()));

  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * @return the interfaceGCPDownloadingBean
   */
  public InterfaceGCPDownloadingBean getInterfaceGCPDownloadingBean() {
    return interfaceGCPDownloadingBean;
  }

  /**
   * @param interfaceGCPDownloadingBean the interfaceGCPDownloadingBean to set
   */
  public void setInterfaceGCPDownloadingBean(InterfaceGCPDownloadingBean interfaceGCPDownloadingBean) {
    this.interfaceGCPDownloadingBean = interfaceGCPDownloadingBean;
  }

}
