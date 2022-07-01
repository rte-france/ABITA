package com.abita.dao.agency;

import com.abita.dao.agency.entity.AgencyEntity;
import com.abita.dao.agency.exceptions.AgencyDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface du DAO des agences
 */
public interface IAgencyDAO extends IAbstractDAO<AgencyEntity, AgencyDAOException> {

  /**
   * Permet de récupérer la liste de toutes les agences ainsi que leur nombre d’utilisation
   *
   * @return une liste de toutes les agences
   * @throws AgencyDAOException une SanitationDAOException
   */
  List<AgencyEntity> findAllAgencies() throws AgencyDAOException;
}
