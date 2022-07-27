/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.artesis.writer;

import com.abita.dao.batch.constant.BatchConstants;
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

import java.text.MessageFormat;

/**
 * Permet d'écrire les lignes dans les fichiers ARTESIS
 * @author
 *
 * @param <T> Type de données à écrire
 */
public class FlatFileWriter<T> extends FlatFileItemWriter<T> implements InitializingBean {

  /** Le format des lignes dans le fichier */
  private String format;

  /** L'extracteur qui va permettre d'écrire les données que l'on veut */
  private FieldExtractor<T> fieldExtractor;

  /** le clé à rechercher en base pour trouver le nom du fichier */
  private String filenameKey;

  /** Le nom du fichier et sa date de génération*/
  private String fileName;

  /** Le nom du fichier*/
  private String filePart;

  /** Le service de paramétrage */
  private ParameterService parameterService;

  /** L'execution du step */
  private StepExecution stepExecution;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(FlatFileWriter.class);

  @Override
  public void afterPropertiesSet() {
    LOGGER.debug("Initialisation post-contruction du Writer.");

    FormatterLineAggregator formatterLineAggregator = new FormatterLineAggregator();
    formatterLineAggregator.setFormat(format);
    formatterLineAggregator.setFieldExtractor(fieldExtractor);
    setLineAggregator(formatterLineAggregator);
  }

  @Override
  public void open(ExecutionContext executionContext) {
    try {
      fileName = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, filenameKey);
      Object[] paramsObject = new String[] {filePart};
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

  /**
   * @param format the format to set
   */
  public void setFormat(String format) {
    this.format = format;
  }

  /**
   * @param fieldExtractor the fieldExtractor to set
   */
  public void setFieldExtractor(FieldExtractor<T> fieldExtractor) {
    this.fieldExtractor = fieldExtractor;
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
   * @param filePart the filePart to set
   */
  public void setFilePart(String filePart) {
    this.filePart = filePart;
  }

  /**
   * Permet de sauvegarder où on en est dans l'écriture
   * @param execution un StepExecution
   */
  @BeforeStep
  public void saveStepExecution(StepExecution execution) {
    this.stepExecution = execution;
  }
}
