/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.tasklet;

import com.abita.services.historycontract.IHistoryContractServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet qui permet l'historisation des contrats occupants
 * @author
 *
 */
public class HistorizeContractTasklet implements Tasklet {

  /** Le service d'historisation des contrats occupants */
  private IHistoryContractServiceFacade historyContractServiceFacade;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistorizeContractTasklet.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    LOGGER.info("Historisation des données des contrats occupants pour la rétroactivité");
    historyContractServiceFacade.historizeAllContracts();
    return RepeatStatus.FINISHED;
  }

  /**
   * @param historyContractServiceFacade the historyContractServiceFacade to set
   */
  public void setHistoryContractServiceFacade(IHistoryContractServiceFacade historyContractServiceFacade) {
    this.historyContractServiceFacade = historyContractServiceFacade;
  }
}
