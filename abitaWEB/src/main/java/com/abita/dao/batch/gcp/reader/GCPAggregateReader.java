package com.abita.dao.batch.gcp.reader;

import com.abita.dao.batch.constant.BatchConstants;
import com.abita.util.dateutil.DateTimeUtils;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FilenameFilter;
import java.text.MessageFormat;

/**
 * Permet la lecture des fichiers necessaires à l'ecriture des pièce comptable GCP
 * @author
 *
 */
public class GCPAggregateReader implements ItemStreamReader<String> {

  /** Delegue la lecture à un autre reader */
  private MultiResourceItemReader<String> delegate;

  /** Le service de paramétrage */
  private ParameterService parameterService;

  /** le clé à rechercher en base pour trouver le nom du fichier */
  private String filenameKey;

  /** Le chemin des fichiers*/
  private String path;

  /** Identifiant de l'instance du job */
  private String jobInstanceId;

  /** Formatter de date MM */
  private DateTimeFormatter monthOnlyFormatter = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_MM);

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(GCPAggregateReader.class);

  @Override
  public String read() throws Exception {
    return delegate.read();
  }

  /**
   * Defini les resssources à lire
   * @param readPath Le chemin des ressources
   * @param pattern Le pattern des noms de fichiers à filtrer
   * @return Un tableau des resssources
   */
  private Resource[] filterFile(String readPath, String pattern) {
    FilenameFilter wildcardFileFilter = new RegexFileFilter(pattern);
    File file = new File(readPath);
    File[] files = file.listFiles(wildcardFileFilter);

    Resource[] resources = null;
    if (files != null) {
      resources = new Resource[files.length];

      for (int i = 0; i < resources.length; i++) {
        resources[i] = new FileSystemResource(files[i]);
      }
    } else {
      resources = new Resource[0];
    }
    return resources;
  }

  @Override
  public void open(ExecutionContext executionContext) {
    try {
      path = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, filenameKey);
    } catch (ParameterServiceException e) {
      LOGGER.error(e.getMessage(), e);
      throw new AssertionError(MessageFormat.format(filenameKey, "{0} param is mandatory"));
    }
    path = path.concat(monthOnlyFormatter.print(new LocalDate()));

    // pattern correspondant aux fichiers à récupérer
    String pattern = "(?:.+){2}_" + jobInstanceId + "_.+";

    Resource[] fileList = filterFile(path, pattern);
    delegate.setStrict(false);
    delegate.setResources(fileList);

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

  /**
   * @param delegate the delegate to set
   */
  public void setDelegate(MultiResourceItemReader<String> delegate) {
    this.delegate = delegate;
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
   * @param jobInstanceId the jobInstanceId to set
   */
  public void setJobInstanceId(String jobInstanceId) {
    this.jobInstanceId = jobInstanceId;
  }

  // @Afters
}
