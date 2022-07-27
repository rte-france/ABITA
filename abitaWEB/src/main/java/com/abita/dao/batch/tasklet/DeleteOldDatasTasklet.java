/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.tasklet;

import com.abita.services.historybenefits.IHistoryBenefitsService;
import com.abita.services.historycontract.IHistoryContractServiceFacade;
import com.abita.services.historyhousing.IHistoryHousingService;
import com.abita.services.historytenant.IHistoryTenantService;
import com.abita.util.dateutil.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * Tasklet qui permet de supprimer les contrats historisés, les avantages en nature historisés, les occupants historisés et les logements historisés vieux de plus d'un an
 * @author
 *
 */
public class DeleteOldDatasTasklet implements Tasklet {

  /** Le service d'historisation des contrats occupants */
  private IHistoryBenefitsService historyBenefitsService;

  /** Le service d'historisation des occupants */
  private IHistoryTenantService historyTenantService;

  /** Le service d'historisation des logements */
  private IHistoryHousingService historyHousingService;

  /** Le service d'historisation des contrats occupants */
  private IHistoryContractServiceFacade historyContractServiceFacade;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeleteOldDatasTasklet.class);

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    LOGGER.info("Suppression des données en base vieilles de plus d'un an");
    LocalDate now = new LocalDate();
    LocalDate lastYear = now.minusMonths(DateTimeUtils.MONTHS_IN_A_YEAR + 1);
    historyContractServiceFacade.deleteOldContracts(lastYear.getMonthOfYear(), lastYear.getYear());
    historyBenefitsService.deleteOldBenefits(lastYear.getMonthOfYear(), lastYear.getYear());
    historyTenantService.deleteOldTenants(lastYear.getMonthOfYear(), lastYear.getYear());
    historyHousingService.deleteOldHousings(lastYear.getMonthOfYear(), lastYear.getYear());
    return RepeatStatus.FINISHED;
  }

  /**
   * @param historyBenefitsService the historyBenefitsService to set
   */
  public void setHistoryBenefitsService(IHistoryBenefitsService historyBenefitsService) {
    this.historyBenefitsService = historyBenefitsService;
  }

  /**
   * @param historyTenantService the historyTenantService to set
   */
  public void setHistoryTenantService(IHistoryTenantService historyTenantService) {
    this.historyTenantService = historyTenantService;
  }

  /**
   * @param historyHousingService the historyHousingService to set
   */
  public void setHistoryHousingService(IHistoryHousingService historyHousingService) {
    this.historyHousingService = historyHousingService;
  }

  /**
   * @param historyContractServiceFacade the historyContractServiceFacade to set
   */
  public void setHistoryContractServiceFacade(IHistoryContractServiceFacade historyContractServiceFacade) {
    this.historyContractServiceFacade = historyContractServiceFacade;
  }

}
