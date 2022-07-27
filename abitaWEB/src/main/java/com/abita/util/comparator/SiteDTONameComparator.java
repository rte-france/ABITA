/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util.comparator;

import com.abita.dto.SiteDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Classe de comparaison entre deux SiteDTO afin de les trier par nom
 */
public class SiteDTONameComparator implements Comparator<SiteDTO>, Serializable {

  private static final long serialVersionUID = 1667634700691176196L;

  @Override
  public int compare(SiteDTO o1, SiteDTO o2) {
    return o1.getName().compareToIgnoreCase(o2.getName());
  }

}
