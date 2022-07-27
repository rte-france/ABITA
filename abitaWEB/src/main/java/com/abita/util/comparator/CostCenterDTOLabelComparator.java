/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.util.comparator;

import com.abita.dto.CostCenterDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Classe de comparaison entre deux CostCenterDTO afin de les trier par nom
 * @author
 *
 */
public class CostCenterDTOLabelComparator implements Comparator<CostCenterDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = -7119005712003829172L;

  @Override
  public int compare(CostCenterDTO o1, CostCenterDTO o2) {
    return o1.getLabel().compareToIgnoreCase(o2.getLabel());
  }

}
