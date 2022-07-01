package com.abita.dao.batch.tasklet;

import com.abita.services.historybenefits.IHistoryBenefitsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet qui permet l'historisation des avantages en nature
 * @author
 *
 */
public class HistorizeBenefitsTasklet implements Tasklet {

  /** Le service d'historisation des avantages en nature */
  private IHistoryBenefitsService historyBenefitsService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistorizeBenefitsTasklet.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    LOGGER.info("Historisation des données des avantages en nature pour la rétroactivité");
    historyBenefitsService.historizeAllBenefits();
    return RepeatStatus.FINISHED;
  }

  /**
   * @param historyBenefitsService the historyBenefitsService to set
   */
  public void setHistoryBenefitsService(IHistoryBenefitsService historyBenefitsService) {
    this.historyBenefitsService = historyBenefitsService;
  }

}
