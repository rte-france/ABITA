/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.roomcategory.impl;

import com.abita.dao.roomcategory.entity.RoomCategoryEntity;
import com.abita.dao.roomcategory.exceptions.RoomCategoryDAOException;
import com.abita.dao.roomcategory.IRoomCategoryDAO;
import com.dao.common.impl.AbstractGenericEntityDAO;

/**
 * Classe d'implémentation des catégories de local
 *
 * @author
 */
public class RoomCategoryDAOImpl extends AbstractGenericEntityDAO<RoomCategoryEntity, RoomCategoryDAOException> implements IRoomCategoryDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = -887824232668842734L;

}
