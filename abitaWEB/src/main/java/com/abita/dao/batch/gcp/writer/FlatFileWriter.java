/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.gcp.writer;

import com.abita.dao.batch.constant.BatchConstants;
import com.abita.dao.batch.gcp.GCPEntryBlock;
import com.abita.dao.batch.gcp.GCPHeader;
import com.abita.util.dateutil.DateTimeUtils;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.FormatterLineAggregator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet d'ecrire les pièces comptables GCP
 * @author
 *
 * @param <T>
 */
public class FlatFileWriter<T> extends FlatFileItemWriter<T> implements InitializingBean {

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

  /** Le nom du fichier*/
  private String filePart;

  /** Le service de paramétrage */
  private ParameterService parameterService;

  /** L'aggregateur pour le formatage des lignes */
  private FormatterLineAggregator formatterLineAggregator;

  /** L'execution du step */
  private StepExecution stepExecution;

  /** Formatter de date ddMMyyyy */
  private DateTimeFormatter dateTightFormatter = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_DDMMYYYY);

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(FlatFileWriter.class);

  @Override
  public void afterPropertiesSet() {
    LOGGER.debug("Initialisation post-contruction du Writer.");
    formatterLineAggregator = new FormatterLineAggregator();
  }

  @Override
  public void open(ExecutionContext executionContext) {
    try {
      fileName = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, filenameKey);
      Object[] paramsObject = new String[] {filePart, dateTightFormatter.print(new LocalDate())};
      fileName = MessageFormat.format(fileName, paramsObject);
      LOGGER.info("Nouveau fichier : " + fileName);
    } catch (ParameterServiceException e) {
      LOGGER.error(e.getMessage(), e);
      throw new AssertionError(MessageFormat.format(filenameKey, "{0} param is mandatory"));
    }
    setResource(new FileSystemResource(fileName));

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
   * @param filePart the filePart to set
   */
  public void setFilePart(String filePart) {
    this.filePart = filePart;
  }

  /**
   * Permet de sauvegarder le contexte d'execution
   * @param execution Le contexte d'execution
   */
  @BeforeStep
  public void saveStepExecution(StepExecution execution) {
    this.stepExecution = execution;
  }
}
