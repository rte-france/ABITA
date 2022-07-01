package com.abita.dto.unpersist;

import java.io.Serializable;

/**
 * Classe d'objet non persisté appellable depuis le controleur afin de passer les critères de recherche
 *
 * @author
 */
public class SanitationCriteriaDTO implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1476604821423852754L;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  /** Référence interne */
  private Long id;

  /** Nom */
  private String label;

  /**
   * @return the label
   */
  public String getLabel() {
    return label;
  }

  /**
   * @param label the label to set
   */
  public void setLabel(String label) {
    this.label = label;
  }
}
