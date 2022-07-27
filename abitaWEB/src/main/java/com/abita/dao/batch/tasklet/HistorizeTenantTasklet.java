/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.tasklet;

import com.abita.services.historytenant.IHistoryTenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet qui permet l'historisation des occupants
 * @author
 *
 */
public class HistorizeTenantTasklet implements Tasklet {

  /** Le service d'historisation des occupants */
  private IHistoryTenantService historyTenantService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistorizeTenantTasklet.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    LOGGER.info("Historisation des données des occupants pour la rétroactivité");
    historyTenantService.historizeAllTenants();
    return RepeatStatus.FINISHED;
  }

  /**
   * @param historyTenantService the historyTenantService to set
   */
  public void setHistoryTenantService(IHistoryTenantService historyTenantService) {
    this.historyTenantService = historyTenantService;
  }

}
