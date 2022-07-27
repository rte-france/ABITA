/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.termination;

import com.abita.dao.termination.entity.TerminationEntity;
import com.abita.dao.termination.exceptions.TerminationDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO des résiliation de contrat
 *
 * @author
 */
public interface ITerminationDAO extends IAbstractDAO<TerminationEntity, TerminationDAOException> {

}
