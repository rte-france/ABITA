package com.abita.dto;

import com.dto.AbstractDTO;

/**
 * DTO agence
 */
public class AgencyDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = -1475071614229727316L;

  /** Nom */
  private String name;

  /** Permet de savoir si l’agence peut être supprimable */
  private boolean removable;

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the removable
   */
  public boolean isRemovable() {
    return removable;
  }

  /**
   * @param removable the removable to set
   */
  public void setRemovable(boolean removable) {
    this.removable = removable;
  }
}
