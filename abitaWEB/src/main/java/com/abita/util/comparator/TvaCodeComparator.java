/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util.comparator;

import com.abita.dto.TvaCodeDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparateur d'objet TvaCode par le code TVA
 *
 * @author
 */
public class TvaCodeComparator implements Comparator<TvaCodeDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = -275048943652878163L;

  @Override
  public int compare(TvaCodeDTO o1, TvaCodeDTO o2) {
    return o1.getCode().compareTo(o2.getCode());
  }

}
