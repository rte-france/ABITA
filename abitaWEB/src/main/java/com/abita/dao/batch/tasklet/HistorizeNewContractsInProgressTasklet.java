package com.abita.dao.batch.tasklet;

import com.abita.services.historycontract.IHistoryContractServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet qui permet l’historisation des nouveaux contrats créés dans le passé
 */
public class HistorizeNewContractsInProgressTasklet implements Tasklet {

  /** Le service d’historisation des contrats occupants */
  private IHistoryContractServiceFacade historyContractServiceFacade;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistorizeContractTasklet.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    LOGGER.info("Historisation des données des nouveaux contrats occupants pour la rétroactivité");
    historyContractServiceFacade.historizeNewContracts();
    return RepeatStatus.FINISHED;
  }

  /**
   * @param historyContractServiceFacade the historyContractServiceFacade to set
   */
  public void setHistoryContractServiceFacade(IHistoryContractServiceFacade historyContractServiceFacade) {
    this.historyContractServiceFacade = historyContractServiceFacade;
  }

}
