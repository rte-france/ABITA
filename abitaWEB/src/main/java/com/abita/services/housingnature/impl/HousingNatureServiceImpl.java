/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.housingnature.impl;

import com.abita.dao.housingnature.entity.HousingNatureEntity;
import com.abita.dao.housingnature.exceptions.HousingNatureDAOException;
import com.abita.services.housingnature.IHousingNatureService;
import com.abita.services.housingnature.exceptions.HousingNatureServiceException;
import com.abita.dao.housingnature.IHousingNatureDAO;
import com.abita.dto.HousingNatureDTO;
import com.services.common.impl.AbstractService;

/**
 * Classe d'implémentation des natures
 *
 * @author
 */
public class HousingNatureServiceImpl extends
  AbstractService<HousingNatureEntity, HousingNatureDTO, HousingNatureDAOException, Long, HousingNatureServiceException, IHousingNatureDAO> implements IHousingNatureService {

  /**
  * Le DAO des natures
  */
  private IHousingNatureDAO housingNatureDAO;

  @Override
  protected IHousingNatureDAO getSpecificIDAO() {
    return housingNatureDAO;
  }

  /**
   * @param housingNatureDAO the housingNatureDAO to set
   */
  public void setHousingNatureDAO(IHousingNatureDAO housingNatureDAO) {
    this.housingNatureDAO = housingNatureDAO;
  }

}
