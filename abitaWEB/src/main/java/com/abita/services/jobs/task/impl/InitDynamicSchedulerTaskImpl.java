package com.abita.services.jobs.task.impl;

import com.abita.services.detailcron.IDetailCronService;
import com.abita.services.jobs.task.IInitDynamicSchedulerTask;

/**
 * Permet de lancer les batchs qui générent et lisent les fichiers GCP et ARTESIS au demarrage de l'application
 * @author
 *
 */
public class InitDynamicSchedulerTaskImpl implements IInitDynamicSchedulerTask {

  /** Le service qui gere les crons de GCP et ARTESIS */
  private IDetailCronService detailCronService;

  @Override
  public void process() {
    detailCronService.createCron();
  }

  /**
   * @param detailCronService the detailCronService to set
   */
  public void setDetailCronService(IDetailCronService detailCronService) {
    this.detailCronService = detailCronService;
  }

}
