package com.abita.util.comparator;

import com.abita.dto.DetailCronDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparateur d'objet DetailCron sur l'id
 *
 * @author
 */
public class DetailCronComparator implements Comparator<DetailCronDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = -275048943652878163L;

  @Override
  public int compare(DetailCronDTO o1, DetailCronDTO o2) {
    return o1.getId().compareTo(o2.getId());
  }

}
