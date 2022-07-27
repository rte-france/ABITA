/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.historyreporting.impl;

import com.abita.dao.historyreporting.entity.HistoryReportingEntity;
import com.abita.dao.historyreporting.exceptions.HistoryReportingDAOException;
import com.abita.dao.historyreporting.IHistoryReportingDAO;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import java.util.List;

/**
 * Classe d’implémentation des DAO pour l’historisation des reporting
 */
public class HistoryReportingDAOImpl extends AbstractGenericEntityDAO<HistoryReportingEntity, HistoryReportingDAOException> implements IHistoryReportingDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = 7588191937846032993L;

  @SuppressWarnings("unchecked")
  @Override
  public HistoryReportingEntity findHistoryReportingByTypeAndYearMonth(String type, int month, int year) throws HistoryReportingDAOException {
    HistoryReportingEntity historyReportingEntity = null;

    Query query = getSession().getNamedQuery("findHistoryReportingByTypeAndYearMonth");
    query.setParameter("type", type, Hibernate.STRING);
    query.setParameter("month", month, Hibernate.INTEGER);
    query.setParameter("year", year, Hibernate.INTEGER);
    List<HistoryReportingEntity> lstEntity = query.list();
    if (lstEntity != null && !lstEntity.isEmpty()) {
      historyReportingEntity = lstEntity.get(0);
    }
    return historyReportingEntity;
  }

}
