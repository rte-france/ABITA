/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.quittancement.impl;

import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.historyamount.IHistoryAmountService;
import com.abita.services.historycontract.IHistoryContractServiceFacade;
import com.abita.services.jobs.quittancement.IQuittancementServiceFacade;
import com.services.paramservice.ParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe d’implémentation des services façades de quittancement
 */
public class QuittancementServiceFacadeImpl implements IQuittancementServiceFacade {

  /**
   * Service des contrats occupants
   */
  private IContractServiceFacade contractServiceFacade;

  /**
   * Service parametreService
   */
  private ParameterService parameterService;

  /**
   * Service d'historisation des contrats occupants
   */
  private IHistoryContractServiceFacade historyContractServiceFacade;

  /**
   * Service d'historisation des montants
   */
  private IHistoryAmountService historyAmountService;

  /**
   * Logger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(QuittancementServiceFacadeImpl.class);

  @Override
  public void generate() {

  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * @param historyContractServiceFacade the historyContractServiceFacade to set
   */
  public void setHistoryContractServiceFacade(IHistoryContractServiceFacade historyContractServiceFacade) {
    this.historyContractServiceFacade = historyContractServiceFacade;
  }

  /**
   * @param historyAmountService the historyAmountService to set
   */
  public void setHistoryAmountService(IHistoryAmountService historyAmountService) {
    this.historyAmountService = historyAmountService;
  }

}

