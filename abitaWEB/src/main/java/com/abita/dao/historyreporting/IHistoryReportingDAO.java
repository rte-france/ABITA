/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.historyreporting;

import com.abita.dao.historyreporting.entity.HistoryReportingEntity;
import com.abita.dao.historyreporting.exceptions.HistoryReportingDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO pour l’historisation des reporting
 */
public interface IHistoryReportingDAO extends IAbstractDAO<HistoryReportingEntity, HistoryReportingDAOException> {

  /**
   * Permet de récupérer le reporting d’une pièce pour un mois et une année
   * @param type type de pièce
   * @param month le mois sélectionné
   * @param year l’année sélectionnée
   * @return le reporting historisé
   * @throws HistoryReportingDAOException une HistoryReportingDAOException
   */
  HistoryReportingEntity findHistoryReportingByTypeAndYearMonth(String type, int month, int year) throws HistoryReportingDAOException;

}
