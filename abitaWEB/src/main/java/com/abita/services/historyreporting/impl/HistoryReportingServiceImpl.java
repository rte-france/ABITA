/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.historyreporting.impl;

import com.abita.dao.historyreporting.entity.HistoryReportingEntity;
import com.abita.dao.historyreporting.exceptions.HistoryReportingDAOException;
import com.abita.services.historyreporting.IHistoryReportingService;
import com.abita.dao.historyreporting.IHistoryReportingDAO;
import com.abita.dto.HistoryReportingDTO;
import com.abita.services.historyreporting.exceptions.HistoryReportingServiceException;
import com.services.common.impl.AbstractService;
import org.joda.time.YearMonth;

import java.math.BigDecimal;
import java.util.List;

/**
 *  Classe d’implémentation d’historisation des reporting
 */
public class HistoryReportingServiceImpl extends
  AbstractService<HistoryReportingEntity, HistoryReportingDTO, HistoryReportingDAOException, Long, HistoryReportingServiceException, IHistoryReportingDAO> implements
        IHistoryReportingService {

  /** DAO pour l’historisation des reporting */
  private IHistoryReportingDAO historyReportingDAO;

  @Override
  public HistoryReportingDTO findHistoryReportingByTypeAndYearMonth(String type, int month, int year) throws HistoryReportingServiceException {
    HistoryReportingDTO historyReportingDTO = null;
    HistoryReportingEntity historyReportingEntity = null;
    try {
      historyReportingEntity = historyReportingDAO.findHistoryReportingByTypeAndYearMonth(type, month, year);
    } catch (HistoryReportingDAOException e) {
      throw new HistoryReportingServiceException(e.getMessage(), e);
    }
    if (historyReportingEntity != null) {
      historyReportingDTO = mapper.map(historyReportingEntity, HistoryReportingDTO.class);
    }
    return historyReportingDTO;
  }

  @Override
  public void create(String type, List<BigDecimal> amounts) throws HistoryReportingServiceException {
    HistoryReportingDTO historyReportingDTO = new HistoryReportingDTO();
    historyReportingDTO.setType(type);

    YearMonth now = new YearMonth();
    historyReportingDTO.setMonth(now.getMonthOfYear());
    historyReportingDTO.setYear(now.getYear());

    BigDecimal finalAmount = BigDecimal.ZERO;
    for (BigDecimal amount : amounts) {
      finalAmount = finalAmount.add(amount);
    }
    historyReportingDTO.setAmount(finalAmount);

    super.create(historyReportingDTO);
  }

  @Override
  protected IHistoryReportingDAO getSpecificIDAO() {
    return historyReportingDAO;
  }

  /**
   * @return the historyReportingDAO
   */
  public IHistoryReportingDAO getHistoryReportingDAO() {
    return historyReportingDAO;
  }

  /**
   * @param historyReportingDAO the historyReportingDAO to set
   */
  public void setHistoryReportingDAO(IHistoryReportingDAO historyReportingDAO) {
    this.historyReportingDAO = historyReportingDAO;
  }

}
