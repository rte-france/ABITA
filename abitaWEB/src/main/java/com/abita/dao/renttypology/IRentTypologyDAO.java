/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.renttypology;

import com.abita.dao.renttypology.entity.RentTypologyEntity;
import com.abita.dao.renttypology.exceptions.RentTypologyDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO des typologies de loyer
 *
 * @author
 */
public interface IRentTypologyDAO extends IAbstractDAO<RentTypologyEntity, RentTypologyDAOException> {

}
