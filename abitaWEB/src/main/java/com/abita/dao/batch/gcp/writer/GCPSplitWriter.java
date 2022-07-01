package com.abita.dao.batch.gcp.writer;

import com.abita.dao.batch.gcp.GCPEntryBlock;
import com.abita.util.dateutil.DateTimeUtils;
import com.services.paramservice.ParameterService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 *
 */
public class GCPSplitWriter implements ItemStreamWriter<GCPEntryBlock> {

  /** Delegue l'ecriture à un autre Writer */
  private YLZNFlatFileWriter<GCPEntryBlock> delegate;

  /** Le contexte d'execution */
  private ExecutionContext executionContext;

  /** Liste des fichiers qui n'ont pas pu être écrit a cause d'une erreur */
  private List<String> errorFiles;

  /** Service de paramètrage */
  private ParameterService parameterService;

  /** Identifiant de l'instance du job */
  private String jobInstanceId;

  /** Formatter de date MMyyyy */
  private DateTimeFormatter monthAndYearFormatter = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_MMYYYY);

  /** Formatter de date MM */
  private DateTimeFormatter monthOnlyFormatter = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_MM);

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(GCPSplitWriter.class);

  /**
   * @return the parameterService
   */
  public ParameterService getParameterService() {
    return parameterService;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /** Constructeur */
  public GCPSplitWriter() {
    errorFiles = new ArrayList<String>();
  }

  @Override
  public void write(List<? extends GCPEntryBlock> items) throws Exception {
    List<GCPEntryBlock> toWrite = new ArrayList<GCPEntryBlock>();
    LocalDate currentMonth = items.get(0).getDateSent();
    String currentPaymentCycle = items.get(0).getPaymentCycle();
    String currentLineType = items.get(0).getGcpHeader().getTypePiece();
    LOGGER.debug("Traitement des données du mois : " + currentMonth);
    for (GCPEntryBlock block : items) {
      LocalDate dateSent = block.getDateSent();
      String paymentCycle = block.getPaymentCycle();
      if (dateSent.equals(currentMonth) && paymentCycle.equals(currentPaymentCycle)) {
        toWrite.add(block);
      } else {
        // Month changed
        writeFile(toWrite, currentMonth, currentPaymentCycle, currentLineType);
        currentMonth = dateSent;
        currentPaymentCycle = paymentCycle;
        LOGGER.debug("Traitement des données du mois : " + currentMonth);
        toWrite.clear();
        toWrite.add(block);
      }
    }
    // Write last month file
    writeFile(toWrite, currentMonth, currentPaymentCycle, currentLineType);
  }

  @Override
  public void open(ExecutionContext context) {
    executionContext = context;
  }

  /**
   * Ecriture d'un fichier
   * @param toWrite Elements à ecrire
   * @param currentMonth Le mois en cours
   * @param paymentCycle Le cycle de paiement
   * @param lineType Le type de ligne
   */
  private void writeFile(List<GCPEntryBlock> toWrite, LocalDate currentMonth, String paymentCycle, String lineType) {
    if (!toWrite.isEmpty()) {
      File outputFile = new File(buildFilePath(delegate.getFileName(), jobInstanceId, currentMonth, lineType, paymentCycle));

      FileSystemResource resource = new FileSystemResource(outputFile);
      delegate.setResource(resource);
      delegate.open(executionContext);
      try {
        delegate.write(toWrite);
      } catch (Exception e) {
        // Trace on error, keep the flow working.
        LOGGER.error("Impossible d'écrire le fichier : " + outputFile.getName(), e);
        errorFiles.add(outputFile.getName());
      } finally {
        delegate.close();
      }
    }
  }

  /**
   * Construit un chemin de fichier pour le fragment a écrire selon le format suivant :
   * folderPath/[monthSent]/[typePiece]_[dateSent]_[paymentCycle]_[jobId].txt
   * Exemple : GCP/09/ZN_092014_2_201406031548.txt
   * @param filePath Le chemin du fichier
   * @param jobId L'identifiant du job
   * @param dateSent La date d'envoi
   * @param blockType Le type de block
   * @param paymentCycle Le cycle de paiement
   * @return Un message avec le chemin du fichier et les paramètres du job
   */
  private String buildFilePath(String filePath, String jobId, LocalDate dateSent, String blockType, String paymentCycle) {
    Object[] paramsObject = new String[] {null, null, monthOnlyFormatter.print(dateSent), blockType, monthAndYearFormatter.print(dateSent), paymentCycle, jobId};
    return MessageFormat.format(filePath, paramsObject);
  }

  /**
   * @return the delegate
   */
  public YLZNFlatFileWriter<GCPEntryBlock> getDelegate() {
    return delegate;
  }

  /**
   * @param delegate the delegate to set
   */
  public void setDelegate(YLZNFlatFileWriter<GCPEntryBlock> delegate) {
    this.delegate = delegate;
  }

  /**
  * @param jobInstanceId the jobInstanceId to set
  */
  public void setJobInstanceId(String jobInstanceId) {
    this.jobInstanceId = jobInstanceId;
  }

  @Override
  public void update(ExecutionContext context) {
    LOGGER.info("Update");
  }

  @Override
  public void close() {
    if (!errorFiles.isEmpty()) {
      LOGGER.error("Le batch n'a pas pu écrire les fichiers suivants : " + StringUtils.join(errorFiles, ","));
    }
    errorFiles.clear();
  }

  /**
   * Savegarde où en est l'execution du step
   * @param execution L'execution du step
   */
  @BeforeStep
  public void saveStepExecution(StepExecution execution) {
    delegate.saveStepExecution(execution);
  }
}
