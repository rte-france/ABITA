/**
 *
 */
package com.abita.web.admin.roomcategory.beans;

import com.abita.dto.RoomCategoryDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Backing bean de la page de gestion des catégories de local
 * @author
 *
 */
public class RoomCategoryBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4192333801520290823L;

  /** Objet de transfert des catégories de local*/
  private RoomCategoryDTO roomCategory;

  /** Liste des catégories de local */
  private List<RoomCategoryDTO> lstRoomCategory;

  /**
   * @return the roomCategory
   */
  public RoomCategoryDTO getRoomCategory() {
    return roomCategory;
  }

  /**
   * @param roomCategory the roomCategory to set
   */
  public void setRoomCategory(RoomCategoryDTO roomCategory) {
    this.roomCategory = roomCategory;
  }

  /**
   * @return the lstRoomCategory
   */
  public List<RoomCategoryDTO> getLstRoomCategory() {
    return lstRoomCategory;
  }

  /**
   * @param lstRoomCategory the lstRoomCategory to set
   */
  public void setLstRoomCategory(List<RoomCategoryDTO> lstRoomCategory) {
    this.lstRoomCategory = lstRoomCategory;
  }

}
