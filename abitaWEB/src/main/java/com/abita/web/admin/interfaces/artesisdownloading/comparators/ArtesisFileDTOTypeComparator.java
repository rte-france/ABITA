/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.interfaces.artesisdownloading.comparators;

import com.abita.dto.unpersist.ArtesisFileDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Dans les pages de telechargement des pieces comptables GCP
 * et des fichiers ARTESIS, permet de faire un tri
 * Sur les dates et les types
 * @author
 *
 */
public class ArtesisFileDTOTypeComparator implements Comparator<ArtesisFileDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = 5519367067782585532L;

  @Override
  public int compare(ArtesisFileDTO file1, ArtesisFileDTO file2) {
    return file1.getType().compareTo(file2.getType());
  }

}
