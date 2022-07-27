/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.services.historyamount.impl;

import com.abita.dao.historyamount.entity.HistoryAmountEntity;
import com.abita.dao.historyamount.exceptions.HistoryAmountDAOException;
import com.abita.services.historyamount.IHistoryAmountService;
import com.abita.services.historyamount.exceptions.HistoryAmountServiceException;
import com.abita.dao.historyamount.IHistoryAmountDAO;
import com.abita.dto.HistoryAmountDTO;
import com.services.common.impl.AbstractService;

/**
 * Classe d'implémentation d'historisation des montants générés
 * @author
 *
 */
public class HistoryAmountServiceImpl extends
  AbstractService<HistoryAmountEntity, HistoryAmountDTO, HistoryAmountDAOException, Long, HistoryAmountServiceException, IHistoryAmountDAO> implements IHistoryAmountService {

  /** DAO pour l’historisation des montants générés */
  private IHistoryAmountDAO historyAmountDAO;

  @Override
  public Long create(HistoryAmountDTO historyAmountDTO) throws HistoryAmountServiceException {
    return (Long) super.create(historyAmountDTO);
  }

  @Override
  public void deleteAllHistoryOfOneContract(Long id) throws HistoryAmountServiceException {
    try {
      historyAmountDAO.deleteAllHistoryAmountOfOneContract(id);
    } catch (HistoryAmountDAOException e) {
      throw new HistoryAmountServiceException(e.getMessage(), e);
    }
  }

  @Override
  protected IHistoryAmountDAO getSpecificIDAO() {
    return historyAmountDAO;
  }

  /**
   * @param historyAmountDAO the historyAmountDAO to set
   */
  public void setHistoryAmountDAO(IHistoryAmountDAO historyAmountDAO) {
    this.historyAmountDAO = historyAmountDAO;
  }

}
