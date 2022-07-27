/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.interfaces.artesis;

import com.abita.dao.batch.constant.BatchConstants;
import com.abita.services.batch.RunScheduledBatch;
import com.abita.services.detailcron.IDetailCronService;
import com.abita.services.detailcron.exceptions.DetailCronServiceException;
import com.abita.dto.DetailCronDTO;
import com.abita.web.admin.interfaces.artesis.bean.InterfaceArtesisBean;
import com.abita.web.shared.AbstractGenericController;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.DateTime;
import org.primefaces.model.UploadedFile;

import javax.faces.context.FacesContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Controleur de la page de gestion manuelle des fichiers Artesis
 * @author
 *
 */
public class InterfaceArtesisController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = -1658358703815288200L;

  /** la liste des extensions de fichier autorisées */
  private static final String[] UPLOADED_FILE_ALLOWED_EXTENSIONS = new String[] {"txt"};

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(InterfaceArtesisController.class);

  /** 5Mo */
  private static final long UPLOADED_FILE_MAX_SIZE = 1024 * 1024 * 5;

  /** le bean associé */
  private InterfaceArtesisBean interfaceArtesisBean;

  /** Service lançant les jobs de création/lecture de fichiers */
  private transient RunScheduledBatch agentDataRunner;

  /** Service de gestion du paramètre du framework */
  private transient ParameterService parameterService;

  /** Service du details des cron */
  private transient IDetailCronService detailCronService;

  /** Nom de code pour l’erreur de copie */
  private static final String ERROR_COPY_CODE = "administration.interface.artesis.copy.failure";

  /**
   * Constructeur
   */
  public InterfaceArtesisController() {
    super();
  }

  /**
   * Méthode appelée pour chaque fichier du formulaire d'upload
   */
  public void onFileUpload() {
    if (check(interfaceArtesisBean.getUploadedFile())) {

      try {
        copyFile(FilenameUtils.getName(interfaceArtesisBean.getUploadedFile().getFileName()), interfaceArtesisBean.getUploadedFile().getInputstream());
      } catch (IOException e) {
        LOGGER.error(interfaceArtesisBean.getUploadedFile().getFileName() + " n'a pas pu être déplacé dans le répertoire de travail", e);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(ERROR_COPY_CODE, interfaceArtesisBean.getUploadedFile().getFileName()));
      }

      // lancement du job d'insertion des données agents
      try {
        boolean completed = agentDataRunner.launch();
        if (completed) {
          LOGGER.info(interfaceArtesisBean.getUploadedFile().getFileName() + " a bien été inséré");
          FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("administration.interface.artesis.upload.success"));
        } else {
          LOGGER.error("Une erreur s'est produite lors de l'insertion du fichier");
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.interface.artesis.upload.failure"));
        }

      } catch (org.springframework.batch.item.ItemStreamException e) {
        LOGGER.error("Une erreur s'est produite lors de l'insertion du fichier", e);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.interface.artesis.upload.failure"));
      }

    }
  }

  /**
  * Permet de savoir si la date actuelle entre deux générations
  * @return  oui si la date actuelle est entre deux générations
  */
  public boolean isBetweenGenerations() {
    boolean isBetweenGenerations = false;
    try {
      // On récupère la liste du detail des CRON
      DetailCronDTO detailCronDTO;
      detailCronDTO = detailCronService.getCronsOfCurrentMonth();

      DateTime now = new DateTime();

      if (detailCronService.getFirstCronOfTheMonth(detailCronDTO).isBefore(now) && detailCronService.getLastCronOfTheMonth(detailCronDTO).isAfter(now)) {
        isBetweenGenerations = true;
      }
    } catch (DetailCronServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la récupération du détail des CRON", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    }
    return isBetweenGenerations;

  }

  /**
   * Permet d’afficher le message d’erreur si la date actuelle est entre deux générations
   */
  public void isBetweenGenerationsErrorMessage() {
    try {
      // On récupère la liste du detail des CRON
      DetailCronDTO detailCronDTO;
      detailCronDTO = detailCronService.getCronsOfCurrentMonth();

      DateTime now = new DateTime();

      if (detailCronService.getFirstCronOfTheMonth(detailCronDTO).isBefore(now) && detailCronService.getLastCronOfTheMonth(detailCronDTO).isAfter(now)) {
        FacesContext.getCurrentInstance().addMessage(null,
          getErrorMessage("La lecture du fichier ARTESIS des données agents entre la génération des fichiers ARTESIS et GCP n'est pas autorisée"));
      }
    } catch (DetailCronServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la récupération du détail des CRON", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    }
  }

  /**
   * Permet de copier le fichier Artesis Données Agents
   * @param fileName Le nom du fichier
   * @param in Le flux d'entrée
   */
  private void copyFile(String fileName, InputStream in) {

    String destination = null;
    try {
      destination = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, BatchConstants.BATCH_ARTESIS_INBOUND_PATH);
    } catch (ParameterServiceException e) {
      LOGGER.error("Impossible de trouver le paramètre : " + BatchConstants.BATCH_PARAMETER_DOMAIN, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(ERROR_COPY_CODE));
    }
    OutputStream out = null;
    try {

      out = new FileOutputStream(new File(destination + fileName));

      int read = 0;
      byte[] bytes = new byte[(int) UPLOADED_FILE_MAX_SIZE];

      while ((read = in.read(bytes)) != -1) {
        out.write(bytes, 0, read);
      }

      in.close();
      out.flush();
      out.close();

      LOGGER.info(interfaceArtesisBean.getUploadedFile().getFileName() + " a bien été téléchargé sur le server");
    } catch (IOException e) {
      LOGGER.error("Impossible de trouver le copier le fichier " + interfaceArtesisBean.getUploadedFile().getFileName(), e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(ERROR_COPY_CODE));
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (IOException e) {
          LOGGER.error("Une erreur est survenue au cours de la fermeture de flux", e);
        }
      }
    }
  }

  /**
   * Vérifie que le fichier envoyé est correct :
   * - la taille est inférieure à la limite choisie
   * - l'extension est autorisée
   *
   * @param uploadedFile le fichier envoyé
   *
   * @return true si le fichier est correct
   */
  private boolean check(final UploadedFile uploadedFile) {

    // On log...
    // Si aucun fichier n'a été sélectionné
    if (uploadedFile != null) {
      // Si le fichier est vide...
      if (uploadedFile.getContents().length == 0) {
        LOGGER.warn("Le fichier est vide");
        FacesContext.getCurrentInstance().addMessage(null, getWarningMessage("administration.interface.artesis.empty.file", uploadedFile.getFileName()));
      }

      // Si le fichier dépasse la limite de taille...
      if (!isUploadFileSizeUnderLimit(uploadedFile.getSize())) {
        LOGGER.info("Fichier [" + uploadedFile.getFileName() + "] : taille maximale autorisée dépassée : [" + UPLOADED_FILE_MAX_SIZE + "]");
        FacesContext.getCurrentInstance().addMessage(null,
          getErrorMessage("administration.interface.artesis.upload.error.invalid_size", FileUtils.byteCountToDisplaySize(UPLOADED_FILE_MAX_SIZE)));
        // Si le fichier n'a pas une des bonnes extensions...
      } else if (!isUploadFileExtensionAllowed(uploadedFile.getFileName())) {
        LOGGER.info("Fichier [" + uploadedFile.getFileName() + "] : extension invalide");
        FacesContext.getCurrentInstance().addMessage(null,
          getErrorMessage("administration.interface.artesis.upload.error.invalid_extension", StringUtils.join(UPLOADED_FILE_ALLOWED_EXTENSIONS, ", ")));
      } else {
        return true;
      }
    } else {
      LOGGER.info("Aucun fichier sélectionné");
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.interface.artesis.no_file"));
    }
    return false;
  }

  /**
   * Vérifie que la taille du fichier envoyé est inférieure à la limite fixée
   * Limite : 5MB
   *
   * @param uploadedFileSize la taille du fichier envoyé
   *
   * @return true si la taille du fichier est valide
   */
  private boolean isUploadFileSizeUnderLimit(final long uploadedFileSize) {
    return uploadedFileSize < UPLOADED_FILE_MAX_SIZE;
  }

  /**
   * Véifie que l'extension du fichier est autorisée
   * Seuls les fichiers texte et les images sont acceptés.
   *
   * @param uploadedFileName le nom du fichier envoyé. Peut être le chemin complet selon le navigateur.
   *
   * @return true si l'extension est autorisée
   */
  private boolean isUploadFileExtensionAllowed(final String uploadedFileName) {
    final String ext = FilenameUtils.getExtension(uploadedFileName);
    boolean allowed = false;
    for (final String s : UPLOADED_FILE_ALLOWED_EXTENSIONS) {
      if (ext.equals(s)) {
        allowed = true;
        break;
      }
    }
    return allowed;
  }

  /**
   * Getter de {@link InterfaceArtesisController#bean}
   *
   * @return la valeur de la propriété
   */
  public InterfaceArtesisBean getInterfaceArtesisBean() {
    return interfaceArtesisBean;
  }

  /**
   * Setter de {@link InterfaceArtesisController#bean}
   *
   * @param interfaceArtesisBean la nouvelle valeur à affecter
   */
  public void setInterfaceArtesisBean(final InterfaceArtesisBean interfaceArtesisBean) {
    this.interfaceArtesisBean = interfaceArtesisBean;
  }

  /**
   * Renvoie la taille maximale du fichier à envoyer
   *
   * @return la taille en octets
   */
  public long getUploadFileSizeLimit() {
    return UPLOADED_FILE_MAX_SIZE;
  }

  /**
   * Renvoie la liste des extensions autorisées concaténées avec une virgule
   *
   * @return la liste des extensions séparées par des virgules
   */
  public String getAllowedExtensions() {
    return "/(\\.|\\/)(" + StringUtils.join(UPLOADED_FILE_ALLOWED_EXTENSIONS, '|') + ")$/";
  }

  /**
   * Setter du service de gestion du paramètre du framework
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * Setter du service lançant les jobs de création/lecture de fichiers
   * @param agentDataRunner the agentDataRunner to set
   */
  public void setAgentDataRunner(RunScheduledBatch agentDataRunner) {
    this.agentDataRunner = agentDataRunner;
  }

  /**
   * @param detailCronService the detailCronService to set
   */
  public void setDetailCronService(IDetailCronService detailCronService) {
    this.detailCronService = detailCronService;
  }

}
