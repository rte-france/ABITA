package com.abita.services.heatingtype.impl;

import com.abita.dao.heatingtype.entity.HeatingTypeEntity;
import com.abita.dao.heatingtype.exceptions.HeatingTypeDAOException;
import com.abita.services.heatingtype.IHeatingTypeService;
import com.abita.services.heatingtype.exceptions.HeatingTypeServiceException;
import com.abita.dao.heatingtype.IHeatingTypeDAO;
import com.abita.dto.HeatingTypeDTO;
import com.services.common.impl.AbstractService;

/**
 * Classe d’implémentation du service des types de chauffage
 */
public class HeatingTypeServiceImpl extends AbstractService<HeatingTypeEntity, HeatingTypeDTO, HeatingTypeDAOException, Long, HeatingTypeServiceException, IHeatingTypeDAO> implements IHeatingTypeService {

  /** DAO des assainissements */
  private IHeatingTypeDAO heatingTypeDAO;

  @Override
  protected IHeatingTypeDAO getSpecificIDAO() { return heatingTypeDAO; }

  /**
   * @param heatingTypeDAO the heatingTypeDAO to set
   */
  public void setHeatingTypeDAO(IHeatingTypeDAO heatingTypeDAO) {
    this.heatingTypeDAO = heatingTypeDAO;
  }
}
