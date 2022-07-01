package com.abita.dao.historytenant;

import com.abita.dao.historytenant.entity.HistoryTenantEntity;
import com.abita.dao.historytenant.exceptions.HistoryTenantDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface des DAO pour l’historisation des occupants
 */
public interface IHistoryTenantDAO extends IAbstractDAO<HistoryTenantEntity, HistoryTenantDAOException> {

  /**
   * DAO qui permet l'historisation des occupants
   * @throws HistoryTenantDAOException une HistoryTenantDAOException
   */
  void historizeAllTenants() throws HistoryTenantDAOException;

  /**
   * DAO qui permet de supprimer l'historisation des occupants vieille d'un an
   * @param month le mois à supprimer
   * @param year l'année à supprimer
   * @throws HistoryTenantDAOException une HistoryTenantDAOException
   */
  void deleteOldTenants(int month, int year) throws HistoryTenantDAOException;

  /**
   * DAO qui permet de récupérer l’historique d’un occupant selon son id, son mois et son année d’historisation
   * @param id l’id de l’occupant
   * @param month le mois d’historisation
   * @param year l’année d’historisation
   * @return l’archive du logement
   * @throws HistoryTenantDAOException une HistoryTenantDAOException
   */
  HistoryTenantEntity get(Long id, int month, int year) throws HistoryTenantDAOException;

  /**
   * DAO qui permet de récupérer l’historique le plus récent d’un occupant selon son id, son mois et son année d’historisation
   * @param id l’id de l’occupant
   * @param month le mois d’historisation
   * @param year l’année d’historisation
   * @return l’archive du logement
   * @throws HistoryTenantDAOException une HistoryTenantDAOException
   */
  HistoryTenantEntity getLatest(Long id, int month, int year) throws HistoryTenantDAOException;

  /**
   * Permet de récupérer les historisations temporaires d’un occupant
   * @param id l’id de l’occupant
   * @return historisations temporaires de l’occupant
   * @throws HistoryTenantDAOException une HistoryTenantDAOException
   */
  List<HistoryTenantEntity> getTemporaries(Long id) throws HistoryTenantDAOException;

  /**
   * Permet de supprimer les historisations d'un occupant
   * @param id identifiant de l'occupant
   * @throws HistoryTenantDAOException une HistoryTenantDAOException;
   */
  void deleteAllHistoryTenantOfOneTenant(Long id) throws HistoryTenantDAOException;

}
