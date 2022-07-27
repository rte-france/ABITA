/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.gcp.writer;

import com.abita.dao.batch.constant.BatchConstants;
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
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.List;

/**
 * Permet l'écriture des pièces comptables à partir des fragments
 * @author
 *
 */
public class GCPAggregateWriter implements ItemStreamWriter<String>, InitializingBean {

  /** Delegue l'ecriture à un autre writer */
  private FlatFileItemWriter<String> delegate;

  /** la clé à rechercher en base pour trouver le nom du fichier */
  private String filenameKey;

  /** Le nom du fichier + sa sate de génération*/
  private String fileName;

  /** le nom du fichier */
  private String filePart;

  /** Le service de parametrage */
  private ParameterService parameterService;

  /** l'execution du step */
  private StepExecution stepExecution;

  /** Formatter de date ddMMyyyy */
  private DateTimeFormatter dateTightFormatter = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_DDMMYYYY);

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(GCPAggregateWriter.class);

  @Override
  public void afterPropertiesSet() {
    LOGGER.debug("Initialisation post-contruction du Writer.");
    Assert.notNull(filenameKey, "filenamekey is mandatory");
    try {
      fileName = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, filenameKey);
      Object[] paramsObject = new String[] {filePart, dateTightFormatter.print(new LocalDate())};
      fileName = MessageFormat.format(fileName, paramsObject);
    } catch (ParameterServiceException e) {
      LOGGER.error(e.getMessage(), e);
      throw new AssertionError(MessageFormat.format(filenameKey, "{0} param is mandatory"));
    }
  }

  @Override
  public void open(ExecutionContext executionContext) {
    Resource resource = new FileSystemResource(fileName);
    stepExecution.getExecutionContext().put("fileNamePattern", resource.getFilename());
    delegate.setResource(resource);
    delegate.open(executionContext);
  }

  @Override
  public void update(ExecutionContext executionContext) {
    delegate.update(executionContext);
  }

  @Override
  public void close() {
    delegate.close();
  }

  @Override
  public void write(List<? extends String> items) throws Exception {
    delegate.write(items);
  }

  /**
   * @param delegate the delegate to set
   */
  public void setDelegate(FlatFileItemWriter<String> delegate) {
    this.delegate = delegate;
  }

  /**
   * @param filenameKey the filenameKey to set
   */
  public void setFilenameKey(String filenameKey) {
    this.filenameKey = filenameKey;
  }

  /**
   * @param filePart the filePart to set
   */
  public void setFilePart(String filePart) {
    this.filePart = filePart;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * Savegarde où en est l'execution du step
   * @param execution L'execution du step
   */
  @BeforeStep
  public void saveStepExecution(StepExecution execution) {
    stepExecution = execution;
  }
}
