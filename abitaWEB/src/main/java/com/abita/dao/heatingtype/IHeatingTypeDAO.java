package com.abita.dao.heatingtype;

import com.abita.dao.heatingtype.entity.HeatingTypeEntity;
import com.abita.dao.heatingtype.exceptions.HeatingTypeDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface du DAO des agences
 */
public interface IHeatingTypeDAO extends IAbstractDAO<HeatingTypeEntity, HeatingTypeDAOException> {

  /**
   * Permet de récupérer la liste de toutes les agences ainsi que leur nombre d’utilisation
   *
   * @return une liste de toutes les agences
   * @throws HeatingTypeDAOException une HeatingTypeDAOException
   */
  List<HeatingTypeEntity> findAllHeatingTypes() throws HeatingTypeDAOException;
}
