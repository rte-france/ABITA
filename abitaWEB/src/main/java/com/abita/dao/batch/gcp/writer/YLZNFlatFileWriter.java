package com.abita.dao.batch.gcp.writer;

import com.abita.dao.batch.constant.BatchConstants;
import com.abita.dao.batch.gcp.GCPEntryBlock;
import com.abita.dao.batch.gcp.GCPHeader;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Permet l'ecriture des pièces comptables YL et ZN
 * @author
 *
 * @param <T>
 */
public class YLZNFlatFileWriter<T> extends FlatFileItemWriter<T> implements InitializingBean {
  /** Le format des headers dans le fichier */
  private String headerFormat;
  /** Le format des lignes dans le fichier */
  private String lineFormat;
  /** L'extracteur qui va permettre d'écrire les données que l'on veut pour le header*/
  private FieldExtractor<T> fieldExtractorHeader;
  /** L'extracteur qui va permettre d'écrire les données que l'on veut pour les lignes*/
  private FieldExtractor<T> fieldExtractorLine;
  /** la clé à rechercher en base pour trouver le nom du fichier */
  private String filenameKey;
  /** Le nom du fichier et sa date de génération*/
  private String fileName;
  /** Le service de paramétrage */
  private ParameterService parameterService;
  /** L'aggregateur pour le formatage des lignes */
  private FormatterLineAggregator formatterLineAggregator;
  /** L'execution du step */
  private StepExecution stepExecution;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(FlatFileWriter.class);

  @Override
  public void afterPropertiesSet() {
    LOGGER.debug("Initialisation post-contruction du Writer.");

    try {
      fileName = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, filenameKey);
    } catch (ParameterServiceException e) {
      LOGGER.error(e.getMessage(), e);
    }

    formatterLineAggregator = new FormatterLineAggregator();
    FileSystemResource fileSystemResource = new FileSystemResource(fileName);
    setResource(fileSystemResource);
  }

  @Override
  public void open(ExecutionContext executionContext) {
    super.open(executionContext);
    Resource resource = new FileSystemResource(fileName);
    stepExecution.getExecutionContext().put("fileNamePattern", resource.getFilename());
  }

  @Override
  public void write(List<? extends T> items) throws Exception {
    for (T t : items) {

      GCPEntryBlock entryBlock = (GCPEntryBlock) t;

      formatterLineAggregator.setFormat(headerFormat);
      formatterLineAggregator.setFieldExtractor(fieldExtractorHeader);
      setLineAggregator(formatterLineAggregator);

      List<GCPHeader> listHeader = new ArrayList<GCPHeader>();
      listHeader.add(entryBlock.getGcpHeader());
      super.write((List<? extends T>) listHeader);

      formatterLineAggregator.setFormat(lineFormat);
      formatterLineAggregator.setFieldExtractor(fieldExtractorLine);
      setLineAggregator(formatterLineAggregator);

      super.write((List<? extends T>) entryBlock.getGcpEntryLines());
    }
  }

  /**
   * @param lineFormat the lineFormat to set
   */
  public void setLineFormat(String lineFormat) {
    this.lineFormat = lineFormat;
  }

  /**
   * @param filenameKey the filenameKey to set
   */
  public void setFilenameKey(String filenameKey) {
    this.filenameKey = filenameKey;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * @param headerFormat the headerFormat to set
   */
  public void setHeaderFormat(String headerFormat) {
    this.headerFormat = headerFormat;
  }

  /**
   * @param fieldExtractorLine the fieldExtractorLine to set
   */
  public void setFieldExtractorLine(FieldExtractor<T> fieldExtractorLine) {
    this.fieldExtractorLine = fieldExtractorLine;
  }

  /**
   * @param fieldExtractorHeader the fieldExtractorHeader to set
   */
  public void setFieldExtractorHeader(FieldExtractor<T> fieldExtractorHeader) {
    this.fieldExtractorHeader = fieldExtractorHeader;
  }

  /**
   * @return the fileName
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Sauvegarde de l'état de l'execution du step
   * @param execution l'execution du step
   */
  @BeforeStep
  public void saveStepExecution(StepExecution execution) {
    this.stepExecution = execution;
  }
}
