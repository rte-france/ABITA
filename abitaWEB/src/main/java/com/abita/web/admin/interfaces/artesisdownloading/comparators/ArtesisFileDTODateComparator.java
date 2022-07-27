/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.interfaces.artesisdownloading.comparators;

import com.abita.dto.unpersist.ArtesisFileDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparateur de dates afin de faire le tri sur la
 * page de téléchargement des fichier ARTESIS
 * @author
 *
 */
public class ArtesisFileDTODateComparator implements Comparator<ArtesisFileDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = 6648985892540959428L;

  @Override
  public int compare(ArtesisFileDTO file1, ArtesisFileDTO file2) {
    return file2.getLastUpdate().compareTo(file1.getLastUpdate());
  }
}
