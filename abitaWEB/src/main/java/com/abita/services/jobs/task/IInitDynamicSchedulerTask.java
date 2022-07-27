/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.task;

/** Service de lancement des batchs */
public interface IInitDynamicSchedulerTask {

  /**
   * Methode qui permet de lancer un job
   */
  void process();
}
