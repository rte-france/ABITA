/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.tasklet;

import com.abita.services.jobs.quittancement.IQuittancementServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet qui permet la génération d'un pdf de quittancement
 * @author
 *
 */
public class QuittancementTasklet implements Tasklet {

  /**
   * Define
   */
  private IQuittancementServiceFacade quittancementServiceFacade;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(QuittancementTasklet.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    LOGGER.info("Génération du fichier PDF de quittancement");
    quittancementServiceFacade.generate();
    return RepeatStatus.FINISHED;
  }

  /**
   * @param quittancementServiceFacade the quittancementServiceFacade to set
   */
  public void setQuittancementServiceFacade(IQuittancementServiceFacade quittancementServiceFacade) {
    this.quittancementServiceFacade = quittancementServiceFacade;
  }

}
