/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.util.comparator;

import com.abita.dto.FieldOfActivityDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Classe de comparaison entre deux FieldOfActivityDTO afin de les trier par nom
 * @author
 *
 */
public class FieldOfActivityDTOLabelComparator implements Comparator<FieldOfActivityDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = -6948311017359387451L;

  @Override
  public int compare(FieldOfActivityDTO o1, FieldOfActivityDTO o2) {
    return o1.getLabel().compareToIgnoreCase(o2.getLabel());
  }

}
