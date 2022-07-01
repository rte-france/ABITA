package com.abita.dao.batch.tasklet;

import com.abita.dao.batch.constant.BatchConstants;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FilenameFilter;
import java.text.MessageFormat;

/**
 * Permet de copier - coller les fichiers d'un dossier défini qui correspondent à un pattern bien spécifique. Les fichiers sont copié dans un dossier défini
 */
public class CopyPasteTasklet extends StepExecutionListenerSupport implements Tasklet, InitializingBean {

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(CopyPasteTasklet.class);

  /** Parameter service */
  private ParameterService parameterService;

  /** The files input dir */
  private String inputDirKey;

  /** The files input dir */
  private File inputDir;

  /** The out put directory */
  private String outputDirKey;

  /** The out put directory */
  private File outputDir;

  /** The input file name pattern */
  private String fileNamePattern;

  /** Indicate if the file should be copied or moved */
  private boolean cutInstead = false;

  /**
   * Define
   */
  private IFileRenamer renamer = new IFileRenamer() {

    @Override
    public String rename(String originalName) {
      return originalName;
    }
  };

  @Override
  public void afterPropertiesSet() {
    Assert.notNull(inputDirKey, "Key for input file path is mandatory for copy step");
    Assert.notNull(outputDirKey, "Key for file name is mandatory");
    String inputDirPath = null;
    String outputDirPath = null;
    try {
      inputDirPath = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, inputDirKey);
    } catch (ParameterServiceException e) {
      LOGGER.error(e.getMessage(), e);
      throw new AssertionError(MessageFormat.format(inputDirKey, "{0} param is mandatory in data base parameter table"));
    }
    try {
      outputDirPath = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, outputDirKey);
    } catch (ParameterServiceException e) {
      LOGGER.error(e.getMessage(), e);
      throw new AssertionError(MessageFormat.format(outputDirKey, "{0} param is mandatory in data base parameter table"));
    }

    inputDir = new File(inputDirPath);
    outputDir = new File(outputDirPath);
  }

  /**
   * Each file in the input dir corresponding to the given pattern is moved in the output dir.
   * <p/>
   * Thes task is used as an archiver.
   *
   * @param contribution Contribution à une execution de step
   * @param chunkContext Contexte du chunk (ensemble d'elements traités dans une transaction)
   * @return {@link RepeatStatus#FINISHED} if everything went fine. Else it will go out on exception.
   * @throws Exception une exception
   */
  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    Assert.notNull(inputDir, "Le répertoire source est requis.");
    Assert.notNull(outputDir, "Le répertoire de destination est requis.");
    Assert.notNull(fileNamePattern, "Le modèle de nom de fichier est requis.");

    FilenameFilter fileFilter = new RegexFileFilter(fileNamePattern);
    File[] files = inputDir.listFiles(fileFilter);
    LOGGER.info(MessageFormat.format("{0} file(s) found in directory {1} for job {2}", files != null ? files.length : 0, inputDir.getName(), chunkContext.getStepContext()
      .getJobName()));
    if (files != null && files.length > 0) {
      for (File file : files) {
        File newFile = new File(outputDir, renamer.rename(file.getName()));
        FileUtils.copyFile(file, newFile);
        if (cutInstead) {

          boolean deleteSuccessful = FileUtils.deleteQuietly(file);
          logDeleteResult(file, deleteSuccessful);

        }
        contribution.incrementFilterCount(1);
      }
      LOGGER.info(MessageFormat.format("{0} file(s) archived", files.length));
    }
    return RepeatStatus.FINISHED;
  }

  /**
   * Permet de journaliser le résultat de la suppression du fichier
   * @param file fichier à supprimer
   * @param deleteSuccessful résultat de la suppression
   */
  private void logDeleteResult(File file, boolean deleteSuccessful) {
    if (deleteSuccessful) {
      LOGGER.debug("Le fichier " + file.getAbsolutePath() + "a bien été supprimé après copie vers l'emplacement " + outputDir.getAbsolutePath() + ".");
    } else {
      LOGGER.error("Le fichier " + file.getAbsolutePath() + "n'a pas pu être supprimé après copie vers l'emplacement " + outputDir.getAbsolutePath() + ".");
    }
  }

  /**
   * @param parameterService the paramService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * @param inputDirKey the inputDirKey to set
   */
  public void setInputDirKey(String inputDirKey) {
    this.inputDirKey = inputDirKey;
  }

  /**
   * @param outputDirKey the outputDirKey to set
   */
  public void setOutputDirKey(String outputDirKey) {
    this.outputDirKey = outputDirKey;
  }

  /**
   * @param cutInstead the cutInstead to set
   */
  public void setCutInstead(boolean cutInstead) {
    this.cutInstead = cutInstead;
  }

  /**
   * @param renamer the renamer to set
   */
  public void setRenamer(IFileRenamer renamer) {
    this.renamer = renamer;
  }

  @Override
  public void beforeStep(StepExecution stepExecution) {
    JobExecution jobExecution = stepExecution.getJobExecution();
    ExecutionContext jobContext = jobExecution.getExecutionContext();
    fileNamePattern = (String) jobContext.get("fileNamePattern");
  }

}
