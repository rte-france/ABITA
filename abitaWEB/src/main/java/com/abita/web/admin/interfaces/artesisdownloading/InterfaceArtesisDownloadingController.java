/**
 *
 */
package com.abita.web.admin.interfaces.artesisdownloading;

import com.abita.dao.batch.constant.BatchConstants;
import com.abita.util.textutil.TextUtils;
import com.abita.dto.unpersist.ArtesisFileDTO;
import com.abita.web.admin.interfaces.artesisdownloading.beans.InterfaceArtesisDownloadingBean;
import com.abita.web.admin.interfaces.artesisdownloading.comparators.ArtesisFileDTOComparator;
import com.abita.web.admin.interfaces.artesisdownloading.comparators.ArtesisFileDTODateComparator;
import com.abita.web.admin.interfaces.artesisdownloading.comparators.ArtesisFileDTOTypeComparator;
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
 * Controleur de la page de gestion de telechargement des fichiers Artesis
 */
public class InterfaceArtesisDownloadingController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = -3133414731692233394L;

  /** Service parametreService */
  private transient ParameterService parameterService;

  /** le bean associé */
  private InterfaceArtesisDownloadingBean interfaceArtesisDownloadingBean;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceArtesisDownloadingController.class);

  /** Taille des mois au format MM */
  private static final int MONTH_LENGTH = 2;

  /**
   * Initialisation du controlleur
   */
  @PostConstruct
  public void init() {
    // Initialisation des listes avec leurs valeurs par défaut
    LocalDate now = new LocalDate();
    interfaceArtesisDownloadingBean.setMonth(now.getMonthOfYear());
    interfaceArtesisDownloadingBean.setYear(now.getYear());
  }

  /**
   * Permet d'alimenter la liste de sélection du mois
   * @return liste de mois
   */
  public List<Month> getLstMonths() {
    if (interfaceArtesisDownloadingBean.getLstMonths() == null || interfaceArtesisDownloadingBean.getLstMonths().size() == ConstantsWEB.DECIMAL_PART_SIZE_NONE) {
      List<Month> lstMonth = Arrays.asList(Month.values());
      interfaceArtesisDownloadingBean.setLstMonths(lstMonth);
    }
    return interfaceArtesisDownloadingBean.getLstMonths();
  }

  /**
   * Lance la recherche au travers des dossiers pour afficher les fichiers correspondants
   */
  public void search() {
    findAllArtesisFile();
  }

  /**
   * Fonction permettant de récupérer les fichiers ARTESIS
   */
  private void findAllArtesisFile() {
    interfaceArtesisDownloadingBean.resetLstArtesisFiles();
    try {
      String path = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, BatchConstants.BATCH_ARTESIS_ARCHIVE_PATH);

      // Les critères de selection du mois à afficher
      Integer monthFile = interfaceArtesisDownloadingBean.getMonth();
      String fileSearch = StringUtils.leftPad(monthFile.toString(), MONTH_LENGTH, "0") + interfaceArtesisDownloadingBean.getYear().toString();

      // On recupere le dossier avec les differents fichiers
      File folder = new File(path);

      if (folder.exists()) {

        findFiles(fileSearch, folder);
      }
    } catch (ParameterServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de l'accès au répertoire ARTESIS", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.interface.gcp.error.access.folder"));
    }
  }

  /**
   * la recherche des fichiers sur le server
   * @param fileSearch le fichier a chercher
   * @param folder le dossier ou chercher les ficheirs
   */
  private void findFiles(String fileSearch, File folder) {
    // On tri pour ne trouver que les fichiers qui contiennent la bonne date dans leur nom
    FileFilter fileFilter = new WildcardFileFilter("*" + fileSearch + "*");

    File[] files = folder.listFiles(fileFilter);

    if (files != null) {

      for (File file : files) {
        ArtesisFileDTO artesisFileDTO = new ArtesisFileDTO();
        artesisFileDTO.setName(file.getName());
        artesisFileDTO.setMimeType(FacesContext.getCurrentInstance().getExternalContext().getMimeType(file.getAbsolutePath()));
        artesisFileDTO.setSize(TextUtils.convertSize(file.length()));
        artesisFileDTO.setFile(file);
        artesisFileDTO.setLastUpdate(new Date(file.lastModified()));
        interfaceArtesisDownloadingBean.getLstArtesisFile().add(artesisFileDTO);
      }
    }
    Collections.sort(interfaceArtesisDownloadingBean.getLstArtesisFile(), new ArtesisFileDTOComparator(new ArtesisFileDTOTypeComparator(), new ArtesisFileDTODateComparator()));
  }

  /**
   * @return the interfaceArtesisDownloadingBean
   */
  public InterfaceArtesisDownloadingBean getInterfaceArtesisDownloadingBean() {
    return interfaceArtesisDownloadingBean;
  }

  /**
   * @param interfaceArtesisDownloadingBean the interfaceArtesisDownloadingBean to set
   */
  public void setInterfaceArtesisDownloadingBean(InterfaceArtesisDownloadingBean interfaceArtesisDownloadingBean) {
    this.interfaceArtesisDownloadingBean = interfaceArtesisDownloadingBean;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

}
