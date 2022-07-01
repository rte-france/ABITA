package com.abita.dao.fieldofactivity.entity;

import com.abita.dao.agency.entity.AgencyEntity;
import com.dao.common.entity.AbstractEntity;

/**
 * Entité domaine d'activité
 * @author
 *
 */
public class FieldOfActivityEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = 9174785348799697002L;

  /** Libellé */
  private String label;

  /** GMR */
  private String gmr;

  /** Agence */
  private AgencyEntity agency;

  /** Permet de savoir si le domaine d'activité peut être supprimé */
  private boolean removable;

  /**
   * Getter du libellé
   *
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * Setter du libellé
   *
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @return the gmr
   */
  public String getGmr() {
    return gmr;
  }

  /**
   * @param gmr the gmr to set
   */
  public void setGmr(String gmr) {
    this.gmr = gmr;
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

  /**
   * @return the agency
   */
  public AgencyEntity getAgency() {
    return agency;
  }

  /**
   * @param agency the agency to set
   */
  public void setAgency(AgencyEntity agency) {
    this.agency = agency;
  }

}
