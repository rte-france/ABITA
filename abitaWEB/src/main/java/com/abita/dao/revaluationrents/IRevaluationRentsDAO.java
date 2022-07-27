/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.revaluationrents;

import com.abita.dao.revaluationrents.entity.RevaluationRentsEntity;
import com.abita.dao.revaluationrents.exceptions.RevaluationRentsDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO des revalorisations des loyers
 */
public interface IRevaluationRentsDAO extends IAbstractDAO<RevaluationRentsEntity, RevaluationRentsDAOException> {

}
