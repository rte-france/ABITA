/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.interfaces.gcpdownloading.comparators;

import com.abita.dto.unpersist.GCPFileDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparateur qui permet de faire le tri des dates sur
 * les pièces comptables GCP
 * @author
 *
 */
public class GCPFileDTODateComparator implements Comparator<GCPFileDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = 8452598747227849076L;

  @Override
  public int compare(GCPFileDTO file1, GCPFileDTO file2) {
    return file2.getLastUpdate().compareTo(file1.getLastUpdate());
  }
}
