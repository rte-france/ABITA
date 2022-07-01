package com.abita.dao.batch.tasklet;

import com.abita.services.jobs.reporting.IReportingServiceFacade;
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
public class SendMailTasklet implements Tasklet {

  /**
   * Define
   */
  private IReportingServiceFacade reportingServiceFacade;

  /**
   * Type de pièce comptable
   */
  private String type;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(SendMailTasklet.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    LOGGER.error("Envoi de la pièce comptable par mail");
    reportingServiceFacade.sendMail(type);
    return RepeatStatus.FINISHED;
  }

  /**
   * @param reportingServiceFacade the reportingServiceFacade to set
   */
  public void setReportingServiceFacade(IReportingServiceFacade reportingServiceFacade) {
    this.reportingServiceFacade = reportingServiceFacade;
  }

  /**
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

}
