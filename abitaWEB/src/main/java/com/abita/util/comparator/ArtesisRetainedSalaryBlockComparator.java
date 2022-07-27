/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.util.comparator;

import com.abita.dao.batch.artesis.ArtesisRetainedSalaryBlock;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparateur d'objet des block d'Artesis en fonction de la reference du l'occupant
 * @author
 *
 */
public class ArtesisRetainedSalaryBlockComparator implements Comparator<ArtesisRetainedSalaryBlock>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = 4680210953755925105L;

  @Override
  public int compare(ArtesisRetainedSalaryBlock o1, ArtesisRetainedSalaryBlock o2) {
    return o1.getTenantReference().compareTo(o2.getTenantReference());
  }

}
