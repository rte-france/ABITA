/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.sanitation.impl;

import com.abita.dao.sanitation.entity.SanitationEntity;
import com.abita.dao.sanitation.exceptions.SanitationDAOException;
import com.abita.services.sanitation.ISanitationService;
import com.abita.dao.sanitation.ISanitationDAO;
import com.abita.dto.SanitationDTO;
import com.abita.services.sanitation.exceptions.SanitationServiceException;
import com.services.common.impl.AbstractService;
import com.services.common.util.DozerUtils;

import java.util.List;

/**
 * Classe d’implémentation du service des assainissements
 */
public class SanitationServiceImpl extends AbstractService<SanitationEntity, SanitationDTO, SanitationDAOException, Long, SanitationServiceException, ISanitationDAO> implements ISanitationService {

  /** DAO des assainissements */
  private ISanitationDAO sanitationDAO;

  @Override
  protected ISanitationDAO getSpecificIDAO() { return sanitationDAO; }

  /**
   * @param sanitationDAO the sanitationDAO to set
   */
  public void setSanitationDAO(ISanitationDAO sanitationDAO) {
    this.sanitationDAO = sanitationDAO;
  }

  @Override
  public List<SanitationDTO> findAllSanitations() throws SanitationServiceException {
    try {
      return DozerUtils.map(mapper, sanitationDAO.findAllSanitations(), SanitationDTO.class);
    } catch (SanitationDAOException e) {
      throw new SanitationServiceException(e.getMessage(), e);
    }
  }
}
