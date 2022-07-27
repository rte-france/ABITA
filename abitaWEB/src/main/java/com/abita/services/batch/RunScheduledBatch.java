/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.batch;

import com.abita.util.dateutil.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import java.util.Date;

/** Cette classe qui implémente Runnable permet de lancer les batchs */
public class RunScheduledBatch implements Runnable {

  /** logger de classe. */
  private static final Logger LOGGER = LoggerFactory.getLogger(RunScheduledBatch.class);

  /** Formatter de date MMyyyy */
  private DateTimeFormatter monthAndYearFormatter = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_MMYYYY);

  /** Le nom du job */
  private String label;

  /** Le job à lancer */
  private Job job;

  /** Permet de programmer un job */
  private JobLauncher jobLauncher;

  @Override
  public void run() {
    launch();
  }

  /**
   * Lance le job
   * @return vrai si le job s’est executé sans problème
   */
  public boolean launch() {
    try {
      String dateParam = new Date().toString();
      Long id = System.currentTimeMillis();
      LocalDate dateId = new LocalDate(id);

      JobParameters param = new JobParametersBuilder().addLong("id", id, true).addString("dateId", monthAndYearFormatter.print(dateId)).addString("date", dateParam)
        .toJobParameters();
      LOGGER.info("Exécution du job " + label);
      JobExecution execution;
      execution = jobLauncher.run(job, param);
      LOGGER.info("Fin d'exécution du job " + label + ". Statut : " + execution.getStatus());
      // On teste si le job a fait une erreur
      return !ExitStatus.FAILED.getExitCode().equals(execution.getExitStatus().getExitCode());
    } catch (Exception e) {
      LOGGER.info("Echec d'exécution du job " + label, e);
    }
    // On dit que le job a échoué s'il est sortie du try avant le if.
    return false;
  }

  /**
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @param job the job to set
   */
  public void setJob(Job job) {
    this.job = job;
  }

  /**
   * @param jobLauncher the jobLauncher to set
   */
  public void setJobLauncher(JobLauncher jobLauncher) {
    this.jobLauncher = jobLauncher;
  }

}
