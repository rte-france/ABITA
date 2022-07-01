package com.abita.dao.sanitation;

import com.abita.dao.sanitation.entity.SanitationEntity;
import com.abita.dao.sanitation.exceptions.SanitationDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface du DAO des agences
 */
public interface ISanitationDAO extends IAbstractDAO<SanitationEntity, SanitationDAOException> {

  /**
   * Permet de récupérer la liste de toutes les agences ainsi que leur nombre d’utilisation
   *
   * @return une liste de toutes les agences
   * @throws SanitationDAOException une SanitationDAOException
   */
  List<SanitationEntity> findAllSanitations() throws SanitationDAOException;
}
