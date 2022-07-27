/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.tvacode;

import com.abita.dao.tvacode.entity.TvaCodeEntity;
import com.abita.dao.tvacode.exceptions.TvaCodeDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface de DAO des codes TVA
 *
 * @author
 */
public interface ITvaCodeDAO extends IAbstractDAO<TvaCodeEntity, TvaCodeDAOException> {

}
