/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.renttypology.impl;

import com.abita.dao.renttypology.IRentTypologyDAO;
import com.abita.dao.renttypology.entity.RentTypologyEntity;
import com.abita.dao.renttypology.exceptions.RentTypologyDAOException;
import com.dao.common.impl.AbstractGenericEntityDAO;

/**
 * Classe d'implémentation des typologies de loyer
 *
 * @author
 */
public class RentTypologyDAOImpl extends AbstractGenericEntityDAO<RentTypologyEntity, RentTypologyDAOException> implements IRentTypologyDAO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -666858031372637474L;

}
