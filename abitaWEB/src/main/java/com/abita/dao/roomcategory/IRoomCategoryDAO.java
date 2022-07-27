/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.roomcategory;

import com.abita.dao.roomcategory.entity.RoomCategoryEntity;
import com.abita.dao.roomcategory.exceptions.RoomCategoryDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO des catégories du local
 *
 * @author
 */
public interface IRoomCategoryDAO extends IAbstractDAO<RoomCategoryEntity, RoomCategoryDAOException> {

}
