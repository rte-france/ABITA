/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util.comparator;

import com.abita.dto.AgencyDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Classe de comparaison entre deux AgencyDTO afin de les trier par nom
 */
public class AgencyDTONameComparator implements Comparator<AgencyDTO>, Serializable {

  @Override
  public int compare(AgencyDTO o1, AgencyDTO o2) {
    return o1.getName().compareToIgnoreCase(o2.getName());
  }

}
