/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.tvacode.impl;

import com.abita.dao.tvacode.entity.TvaCodeEntity;
import com.abita.dao.tvacode.exceptions.TvaCodeDAOException;
import com.abita.services.tvacode.ITvaCodeService;
import com.abita.services.tvacode.exceptions.TvaCodeServiceException;
import com.abita.dao.tvacode.ITvaCodeDAO;
import com.abita.dto.TvaCodeDTO;
import com.services.common.impl.AbstractService;

import java.util.List;

/**
 * Implémentation du service des codes TVA
 *
 * @author
 */
public class TvaCodeServiceImpl extends AbstractService<TvaCodeEntity, TvaCodeDTO, TvaCodeDAOException, Long, TvaCodeServiceException, ITvaCodeDAO> implements ITvaCodeService {

  /**
   * Interface des DAO des codes TVA
   */
  private ITvaCodeDAO tvaCodeDAO;

  @Override
  protected ITvaCodeDAO getSpecificIDAO() {
    return tvaCodeDAO;
  }

  @Override
  public List<TvaCodeDTO> findAllTvaCode() throws TvaCodeServiceException {
    return super.find();
  }

  @Override
  public void saveListing(List<TvaCodeDTO> lstTvaCode) throws TvaCodeServiceException {
    List<TvaCodeDTO> lstTvaCodeOriginal = find();

    // Suppression
    for (TvaCodeDTO tvaCode : lstTvaCodeOriginal) {
      if (!lstTvaCode.contains(tvaCode)) {
        delete(tvaCode.getId());
      }
    }

    // Ajout et modification
    for (TvaCodeDTO tvaCode : lstTvaCode) {
      if (lstTvaCodeOriginal.contains(tvaCode)) {
        update(tvaCode);
      } else {
        create(tvaCode);
      }
    }
  }

  /**
   * @param tvaCodeDAO the tvaCodeDAO to set
   */
  public void setTvaCodeDAO(ITvaCodeDAO tvaCodeDAO) {
    this.tvaCodeDAO = tvaCodeDAO;
  }

}
