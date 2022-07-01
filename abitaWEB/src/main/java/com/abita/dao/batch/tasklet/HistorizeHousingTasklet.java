package com.abita.dao.batch.tasklet;

import com.abita.services.historyhousing.IHistoryHousingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet qui permet l'historisation des logements
 * @author
 *
 */
public class HistorizeHousingTasklet implements Tasklet {

  /** Le service d'historisation des logements */
  private IHistoryHousingService historyHousingService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistorizeHousingTasklet.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    LOGGER.info("Historisation des données des logements pour la rétroactivité");
    historyHousingService.historizeAllHousings();
    return RepeatStatus.FINISHED;
  }

  /**
   * @param historyHousingService the historyHousingService to set
   */
  public void setHistoryHousingService(IHistoryHousingService historyHousingService) {
    this.historyHousingService = historyHousingService;
  }

}
