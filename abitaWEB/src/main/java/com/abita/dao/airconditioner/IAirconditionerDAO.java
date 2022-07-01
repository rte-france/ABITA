package com.abita.dao.airconditioner;

import com.abita.dao.airconditioner.entity.AirconditionerEntity;
import com.abita.dao.airconditioner.exceptions.AirconditionerDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface du DAO des climatisations
 */
public interface IAirconditionerDAO extends IAbstractDAO<AirconditionerEntity, AirconditionerDAOException> {

  /**
   * Permet de récupérer la liste de toutes les agences ainsi que leur nombre d’utilisation
   *
   * @return une liste de toutes les agences
   * @throws AirconditionerDAOException une AirconditionerDAOException
   */
  List<AirconditionerEntity> findAllAirconditioners() throws AirconditionerDAOException;
}
