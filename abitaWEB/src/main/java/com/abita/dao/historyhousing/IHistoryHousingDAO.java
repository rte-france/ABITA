/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.historyhousing;

import com.abita.dao.historyhousing.entity.HistoryHousingEntity;
import com.abita.dao.historyhousing.exceptions.HistoryHousingDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface des DAO pour l'historisation des logements
 * @author
 *
 */
public interface IHistoryHousingDAO extends IAbstractDAO<HistoryHousingEntity, HistoryHousingDAOException> {

  /**
   * DAO qui permet l'historisation des logements
   * @throws HistoryHousingDAOException une HistoryHousingDAOException
   */
  void historizeAllHousings() throws HistoryHousingDAOException;

  /**
   * DAO qui permet de supprimer l'historisation des logements vieille d'un an
   * @param month le mois à supprimer
   * @param year l'année à supprimer
   * @throws HistoryHousingDAOException une HistoryHousingDAOException
   */
  void deleteOldHousings(int month, int year) throws HistoryHousingDAOException;

  /**
   * DAO qui permet de récupérer l’historique d'un logement selon son id, son mois et son année d’historisation
   * @param id l’id du logement
   * @param month le mois d’historisation
   * @param year l’année d’historisation
   * @return l’archive du logement
   * @throws HistoryHousingDAOException une HistoryHousingDAOException
   */
  HistoryHousingEntity get(Long id, int month, int year) throws HistoryHousingDAOException;

  /**
   * DAO qui permet de récupérer l’historique le plus récent d'un logement selon son id, son mois et son année d’historisation
   * @param id l’id du logement
   * @param month le mois d’historisation
   * @param year l’année d’historisation
   * @return l’archive du logement
   * @throws HistoryHousingDAOException une HistoryHousingDAOException
   */
  HistoryHousingEntity getLatest(Long id, int month, int year) throws HistoryHousingDAOException;

  /**
   * Permet de récupérer les historisations temporaires d’un logement
   * @param id l’id du logement
   * @return historisations temporaires du logement
   * @throws HistoryHousingDAOException une HistoryHousingDAOException
   */
  List<HistoryHousingEntity> getTemporaries(Long id) throws HistoryHousingDAOException;

  /**
   * Permet de supprimer les historisations d'un logement
   * @param id identifiant du logement
   * @throws HistoryHousingDAOException une HistoryHousingDAOException;
   */
  void deleteAllHistoryHousingOfOneHousing(Long id) throws HistoryHousingDAOException;
}
