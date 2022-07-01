package com.abita.services.jobs.archives.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import com.abita.services.jobs.archives.IArchivesService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;

import com.abita.services.jobs.archives.exceptions.ArchivesServiceException;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.Month;
import com.dao.common.exception.GenericDAOException;
import com.services.common.util.SafetyUtils;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;

/**
 * Classe d'implémentation abstraite pour la génération des fichiers de l'archive
 *
 * @author
 */
public abstract class AbstractArchivesServiceImpl implements IArchivesService {

  /** Service parametreService */
  private ParameterService parameterService;

  /** logger de classe. */
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractArchivesServiceImpl.class);

  @Override
  public void process() throws ArchivesServiceException {
    try {
      // Récupération du répertoire où sont stockés les fichiers
      String path = parameterService.getParameterValue(com.services.paramservice.constants.ParamServiceConstants.ACCESS_DOMAIN_KEY,
        ConstantsWEB.PATH_ARCHIVES_FOLDER_PARAMETER_KEY);

      // Détermination du nom du dossier
      DateTime currentDate = DateTime.now();
      NumberFormat nf = new DecimalFormat(ConstantsWEB.MONTH_ARCHIVES_FILENAME_PATTERN);
      String folderSearch = Integer.toString(currentDate.getYear()) + '-' + nf.format(currentDate.getMonthOfYear());

      // Préparation du dossier où écrire
      String fullPath = generateFolderIfNotExists(path, folderSearch);

      // Définition du nom du fichier
      String fileName = defineFileName(currentDate.getMonthOfYear(), currentDate.getYear());

      // Ecriture du fichier
      write(fullPath + System.getProperty("file.separator") + fileName);
    } catch (ParameterServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de l'accès au répertoire d'archivage", ex);
      throw new ArchivesServiceException(ex.getMessage(), ex);
    } catch (IOException ex) {
      LOGGER.error("Une erreur est survenue lors de la manipulation des fichiers", ex);
      throw new ArchivesServiceException(ex.getMessage(), ex);
    }
  }

  /**
   * Vérifie si le répertoire où écrire les fichiers existe, le crée si besoin
   * @param parent : Le chemin d'accès au dossier parent
   * @param child : Le chemin d'accès au dossier enfant
   * @return le chemin
   * @throws IOException l'exception
   */
  private String generateFolderIfNotExists(String parent, String child) throws IOException {
    File folder = new File(parent, FilenameUtils.getName(child));
    if (!folder.exists()) {
      FileUtils.forceMkdir(folder);
    }
    return folder.getAbsolutePath();
  }

  /**
   * Ecrit le fichier à l'endroit désiré
   * @param fileWithPath : le chemin vers le fichier à écrire
   * @throws IOException l'exception
   * @throws ArchivesServiceException l'exception
   */
  private void write(String fileWithPath) throws IOException, ArchivesServiceException {
    FileOutputStream file = null;
    HSSFWorkbook wb = new HSSFWorkbook();

    try {
      // Génération du contenu
      generateContent(wb);

      // Ecriture du fichier
      file = new FileOutputStream(fileWithPath);
      wb.write(file);
    } catch (GenericDAOException ex) {
      throw new ArchivesServiceException(ex);
    } catch (FileNotFoundException ex) {
      throw new ArchivesServiceException(ex);
    } finally {
      if (file != null) {
        SafetyUtils.close(file);
      }
    }
  }

  /**
   * Génère le contenu du fichier Excel
   *
   * @param wb : le document Excel dans lequel on écrira
   * @throws GenericDAOException l'exception
   */
  private void generateContent(HSSFWorkbook wb) throws GenericDAOException {
    // Création de la feuille
    HSSFSheet sheet = wb.createSheet(defineSheetName());

    // Création de la ligne de l'horodatage
    HSSFRow rowDate = sheet.createRow(0);

    // Détermination du nom du dossier
    DateTime currentDate = DateTime.now();
    rowDate.createCell(0).setCellValue(Month.getLabelByValue(currentDate.getMonthOfYear()) + " " + Integer.toString(currentDate.getYear()));

    // Création de la ligne du header
    HSSFRow rowHeader = sheet.createRow(1);

    // Génération des colonnes de la 1ère ligne
    int numberCol = 0;
    for (String col : buildHeader()) {
      rowHeader.createCell(numberCol++).setCellValue(col);
    }

    // Génération des lignes suivantes contenant les données
    buildContent(sheet);
  }

  /**
   * Défini le nom du fichier
   * @param month : le mois
   * @param year : l'année
   * @return le nom du fichier
   */
  protected abstract String defineFileName(int month, int year);

  /**
   * Permet d'obtenir le nom de la feuille
   * @return le nom de la feuille
   */
  protected abstract String defineSheetName();

  /**
   * Permet d'obtenir la liste des colonnes
   * @return la 1ère ligne du fichier
   */
  protected abstract List<String> buildHeader();

  /**
   * Permet d'obtenir les données à mettre dans les colonnes
   *
   * @param sheet : la feuille dans laquelle écrire
   * @throws GenericDAOException l'exception
   */
  protected abstract void buildContent(HSSFSheet sheet) throws GenericDAOException;

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

}
