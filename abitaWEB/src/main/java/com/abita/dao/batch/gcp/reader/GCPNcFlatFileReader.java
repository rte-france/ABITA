/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.gcp.reader;

import com.abita.dao.batch.constant.BatchConstants;
import com.abita.dao.batch.gcp.fieldsetmapper.NcReadAndSendFieldSetMapper;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.YearMonth;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.PatternMatchingCompositeLineTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.File;
import java.io.FileFilter;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * Classe qui permet de lire la pièce comptable NC
 */
public class GCPNcFlatFileReader extends FlatFileItemReader<BigDecimal> {

  /** Tokenizer qui permet de formater le texte lu dans le fichier */
  private PatternMatchingCompositeLineTokenizer lineTokenizer;

  /** le clé à rechercher en base pour trouver le nom du fichier */
  private String filenameKey;

  /** Le nom du fichier */
  private String fileName;

  /** Le service de paramétrage */
  private ParameterService parameterService;

  /** L'execution du step */
  private StepExecution stepExecution;

  /** Message lors d’une erreur de lecture */
  private static final String READING_MESSAGE_ERROR = "Erreur lors de la lecture de la pièce comptable NC";

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(GCPNcFlatFileReader.class);

  @Override
  public void afterPropertiesSet() {
    LOGGER.debug("Initialisation post-contruction du Reader .");
    Assert.notNull(filenameKey, "filenamekey is mandatory");
    try {

      String folderPath = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, filenameKey);
      File folder = new File(folderPath);
      YearMonth currentYearMonth = new YearMonth();
      String currentDate = Integer.toString(currentYearMonth.getMonthOfYear()) + Integer.toString(currentYearMonth.getYear());
      FileFilter fileFilter = new WildcardFileFilter("ABITA_NC_" + "*" + currentDate + "*");
      File[] files = folder.listFiles(fileFilter);
      if (files != null && files.length > 0) {
        Arrays.sort(files);
        fileName = files[files.length - 1].toString();
      }
      LOGGER.info(fileName);
    } catch (ParameterServiceException e) {
      LOGGER.error(e.getMessage(), e);
      throw new AssertionError(MessageFormat.format(filenameKey, "{0} param is mandatory"));
    }
    setResource(new FileSystemResource(fileName));
  }

  @Override
  public void open(ExecutionContext executionContext) {
    LOGGER.info("File NC - Set Parameter Reader...");

    DefaultLineMapper<BigDecimal> lineMapper = new DefaultLineMapper<BigDecimal>();
    lineMapper.setLineTokenizer(lineTokenizer);
    lineMapper.setFieldSetMapper(new NcReadAndSendFieldSetMapper());
    super.setLineMapper(lineMapper);
    super.open(executionContext);
    Resource resource = new FileSystemResource(fileName);
    stepExecution.getExecutionContext().put("fileNamePattern", resource.getFilename());
  }

  @Override
  public BigDecimal read() {
    try {
      BigDecimal amount = super.read();
      if (amount != null) {
        return amount;
      }
    } catch (UnexpectedInputException e) {
      LOGGER.error(READING_MESSAGE_ERROR, e);
      throw new ItemStreamException(e);
    } catch (ParseException e) {
      LOGGER.error(READING_MESSAGE_ERROR, e);
      throw new ItemStreamException(e);
    } catch (Exception e) {
      LOGGER.error(READING_MESSAGE_ERROR, e);
      throw new ItemStreamException(e);
    }

    return null;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * @param filenameKey the filenameKey to set
   */
  public void setFilenameKey(String filenameKey) {
    this.filenameKey = filenameKey;
  }

  /**
   * Permet de sauvegarder où on en est dans l'écriture
   * @param execution un StepExecution
   */
  @BeforeStep
  public void saveStepExecution(StepExecution execution) {
    stepExecution = execution;
  }

  /**
   * @param lineTokenizer the lineTokenizer to set
   */
  public void setLineTokenizer(PatternMatchingCompositeLineTokenizer lineTokenizer) {
    this.lineTokenizer = lineTokenizer;
  }
}
