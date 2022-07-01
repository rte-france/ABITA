package com.abita.services.airconditioner.impl;

import com.abita.dao.airconditioner.entity.AirconditionerEntity;
import com.abita.dao.airconditioner.exceptions.AirconditionerDAOException;
import com.abita.services.airconditioner.IAirconditionerService;
import com.abita.dao.airconditioner.IAirconditionerDAO;
import com.abita.dto.AirconditionerDTO;
import com.abita.services.airconditioner.exceptions.AirconditionerServiceException;
import com.services.common.impl.AbstractService;

/**
 * Classe d’implémentation du service des climatisations
 */
public class AirconditionerServiceImpl extends AbstractService<AirconditionerEntity, AirconditionerDTO, AirconditionerDAOException, Long, AirconditionerServiceException, IAirconditionerDAO> implements IAirconditionerService {

  /** DAO des assainissements */
  private IAirconditionerDAO airconditionerDAO;

  @Override
  protected IAirconditionerDAO getSpecificIDAO() { return airconditionerDAO; }

  /**
   * @param airconditionerDAO the airconditionerDAO to set
   */
  public void setAirconditionerDAO(IAirconditionerDAO airconditionerDAO) {
    this.airconditionerDAO = airconditionerDAO;
  }
}
