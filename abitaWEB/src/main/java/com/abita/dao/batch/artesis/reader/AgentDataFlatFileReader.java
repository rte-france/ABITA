package com.abita.dao.batch.artesis.reader;

import com.abita.dao.batch.artesis.fieldsetmapper.AgentDataFieldSetMapper;
import com.abita.dao.batch.constant.BatchConstants;
import com.abita.dto.unpersist.AgentDataDTO;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.text.MessageFormat;

/**
 * Classe qui permet de lire le fichier Donnée agent
 */
public class AgentDataFlatFileReader extends FlatFileItemReader<AgentDataDTO> {

  /** Tokenizer qui permet de formater le texte lu dans le fichier */
  private FixedLengthTokenizer lineTokenizer;

  /** le clé à rechercher en base pour trouver le nom du fichier */
  private String filenameKey;

  /** Le nom du fichier */
  private String fileName;

  /** Le service de paramétrage */
  private ParameterService parameterService;

  /** L'execution du step */
  private StepExecution stepExecution;

  /** Message lors d’une erreur de lecture */
  private static final String READING_MESSAGE_ERROR = "Erreur lors de la lecture du fichier données agents";

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(AgentDataFlatFileReader.class);

  @Override
  public void afterPropertiesSet() {
    LOGGER.debug("Initialisation post-contruction du Reader .");
    Assert.notNull(filenameKey, "filenamekey is mandatory");
    try {
      fileName = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, filenameKey);

    } catch (ParameterServiceException e) {
      LOGGER.error(e.getMessage(), e);
      throw new AssertionError(MessageFormat.format(filenameKey, "{0} param is mandatory"));
    }
    setResource(new FileSystemResource(fileName));
  }

  @SuppressWarnings("unchecked")
  @Override
  public void open(ExecutionContext executionContext) {
    LOGGER.info("Agent Data - Set Parameter Reader...");

    DefaultLineMapper<AgentDataDTO> lineMapper = new DefaultLineMapper<AgentDataDTO>();
    lineMapper.setLineTokenizer(lineTokenizer);
    lineMapper.setFieldSetMapper(new AgentDataFieldSetMapper());
    super.setLineMapper(lineMapper);
    super.open(executionContext);
    Resource resource = new FileSystemResource(fileName);
    stepExecution.getExecutionContext().put("fileNamePattern", resource.getFilename());
  }

  @Override
  public AgentDataDTO read() {
    try {
      AgentDataDTO tenant = super.read();
      LOGGER.info("Agent Data - Reading...");
      if (tenant != null) {
        return tenant;
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
   * @param lineTokenizer the lineTokenizer to set
   */
  public void setLineTokenizer(FixedLengthTokenizer lineTokenizer) {
    this.lineTokenizer = lineTokenizer;
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
}
