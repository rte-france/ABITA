/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.dao.historyamount.impl;

import com.abita.dao.contract.constants.ContractConstants;
import com.abita.dao.historyamount.exceptions.HistoryAmountDAOException;
import com.abita.dao.historyamount.IHistoryAmountDAO;
import com.abita.dao.historyamount.entity.HistoryAmountEntity;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.Hibernate;
import org.hibernate.Query;

/**
 * Classe d’implémentation des DAO de l’historisation des montants générés
 * @author
 *
 */
public class HistoryAmountDAOImpl extends AbstractGenericEntityDAO<HistoryAmountEntity, HistoryAmountDAOException> implements IHistoryAmountDAO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -5115003446313871603L;

  @Override
  public Long create(HistoryAmountEntity historyAmountDTO) throws HistoryAmountDAOException {
    Long id = (Long) super.create(historyAmountDTO);
    getSession().flush();
    return id;
  }

  @Override
  public void deleteAllHistoryAmountOfOneContract(Long id) throws HistoryAmountDAOException {
    // On supprime les historisations
    Query query = getSession().getNamedQuery("deleteHistoryAmount");
    query.setParameter(ContractConstants.ID_CONTRACT, id, Hibernate.LONG);
    query.executeUpdate();
  }

}
