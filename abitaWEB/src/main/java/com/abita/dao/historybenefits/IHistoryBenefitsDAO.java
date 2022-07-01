package com.abita.dao.historybenefits;

import com.abita.dao.historybenefits.entity.HistoryBenefitsEntity;
import com.abita.dao.historybenefits.exceptions.HistoryBenefitsDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface des DAO pour l’historisation des avantages en nature
 */
public interface IHistoryBenefitsDAO extends IAbstractDAO<HistoryBenefitsEntity, HistoryBenefitsDAOException> {

  /**
   * DAO qui permet l'historisation des avantages en nature
   * @throws HistoryBenefitsDAOException une HistoryBenefitsDAOException
   */
  void historizeAllBenefits() throws HistoryBenefitsDAOException;

  /**
   * DAO qui permet de supprimer l'historisation des avantages en nature vieille d'un an
   * @param month le mois à supprimer
   * @param year l'année à supprimer
   * @throws HistoryBenefitsDAOException une HistoryBenefitsDAOException
   */
  void deleteOldBenefits(int month, int year) throws HistoryBenefitsDAOException;

  /**
   * DAO qui permet de récupérer les avantages en nature d’un mois et année d’historisation
   * @param month le mois d’historisation
   * @param year l'année d’historisation
   * @return les avantages en nature historisés
   * @throws HistoryBenefitsDAOException une HistoryBenefitsDAOException
   */
  List<HistoryBenefitsEntity> get(int month, int year) throws HistoryBenefitsDAOException;
}
