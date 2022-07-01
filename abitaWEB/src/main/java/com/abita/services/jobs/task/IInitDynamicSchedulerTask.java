package com.abita.services.jobs.task;

/** Service de lancement des batchs */
public interface IInitDynamicSchedulerTask {

  /**
   * Methode qui permet de lancer un job
   */
  void process();
}
