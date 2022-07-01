package com.abita.services.roomcategory;

import com.abita.dto.RoomCategoryDTO;
import com.abita.services.roomcategory.exceptions.RoomCategoryServiceException;

import java.util.List;

/**
 * Interface du service des catégories de local
 *
 * @author
 */
public interface IRoomCategoryService {

  /**
   * Permet de récupérer la liste de toutes les catégories de local
   *
   * @return une liste de toutes les catégories de local
   *
   * @throws RoomCategoryServiceException une RoomCategoryServiceException
   */
  List<RoomCategoryDTO> find() throws RoomCategoryServiceException;

  /**
   * Permet de sauvergarder la liste des catégories de local
   * @param lstRoomCategory une liste des catégories de local
   * @throws RoomCategoryServiceException une RoomCategoryServiceException
   */
  void saveListing(List<RoomCategoryDTO> lstRoomCategory) throws RoomCategoryServiceException;

}
