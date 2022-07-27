/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.renttypology.impl;

import com.abita.dao.renttypology.entity.RentTypologyEntity;
import com.abita.dao.renttypology.exceptions.RentTypologyDAOException;
import com.abita.services.renttypology.IRentTypologyService;
import com.abita.services.renttypology.exceptions.RentTypologyServiceException;
import com.abita.dao.renttypology.IRentTypologyDAO;
import com.abita.dto.RentTypologyDTO;
import com.services.common.impl.AbstractService;

/**
 * Classe d'implémentation des typologies de loyer
 * @author
 *
 */
public class RentTypologyServiceImpl extends AbstractService<RentTypologyEntity, RentTypologyDTO, RentTypologyDAOException, Long, RentTypologyServiceException, IRentTypologyDAO>
  implements IRentTypologyService {

  /**
  * Le DAO des typologies de loyer
  */
  private IRentTypologyDAO rentTypologyDAO;

  @Override
  public Long create(RentTypologyDTO rentTypologyDTO) throws RentTypologyServiceException {
    return (Long) super.create(rentTypologyDTO);
  }

  @Override
  protected IRentTypologyDAO getSpecificIDAO() {
    return rentTypologyDAO;
  }

  /**
   * @param rentTypologyDAO the rentTypologyDAO to set
   */
  public void setRentTypologyDAO(IRentTypologyDAO rentTypologyDAO) {
    this.rentTypologyDAO = rentTypologyDAO;
  }

}
