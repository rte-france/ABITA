/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.roomcategory.impl;

import com.abita.dao.roomcategory.entity.RoomCategoryEntity;
import com.abita.dao.roomcategory.exceptions.RoomCategoryDAOException;
import com.abita.services.roomcategory.IRoomCategoryService;
import com.abita.dao.roomcategory.IRoomCategoryDAO;
import com.abita.dto.RoomCategoryDTO;
import com.abita.services.roomcategory.exceptions.RoomCategoryServiceException;
import com.services.common.impl.AbstractService;

import java.util.List;

/**
 * Classe d'implémentation des catégories de local
 *
 * @author
 */
public class RoomCategoryServiceImpl extends AbstractService<RoomCategoryEntity, RoomCategoryDTO, RoomCategoryDAOException, Long, RoomCategoryServiceException, IRoomCategoryDAO>
  implements IRoomCategoryService {

  /**
  * Le DAO des catégories de local
  */
  private IRoomCategoryDAO roomCategoryDAO;

  @Override
  public void saveListing(List<RoomCategoryDTO> lstRoomCategory) throws RoomCategoryServiceException {

    List<RoomCategoryDTO> lstRoomCategoryOriginal = find();

    // Ajout et modification
    for (RoomCategoryDTO roomCategory : lstRoomCategory) {
      if (lstRoomCategoryOriginal.contains(roomCategory)) {
        update(roomCategory);
      }
    }

  }

  @Override
  protected IRoomCategoryDAO getSpecificIDAO() {
    return roomCategoryDAO;
  }

  /**
   * @param roomCategoryDAO the roomCategoryDAO to set
   */
  public void setRoomCategoryDAO(IRoomCategoryDAO roomCategoryDAO) {
    this.roomCategoryDAO = roomCategoryDAO;
  }

}
