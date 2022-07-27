/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.interfaces.artesisdownloading.comparators;

import com.abita.dto.unpersist.ArtesisFileDTO;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Permet de trier les fichiers ARTESIS par type et par date
 * @author
 *
 */
public class ArtesisFileDTOComparator implements Comparator<ArtesisFileDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = 5452784976150562117L;

  /** La liste des differents comparateurs */
  private List<Comparator<ArtesisFileDTO>> listComparators;

  /**
   * Constructeur
   * @param comparators liste des comparateurs
   */
  public ArtesisFileDTOComparator(Comparator<ArtesisFileDTO>... comparators) {
    listComparators = Arrays.asList(comparators);
  }

  @Override
  public int compare(ArtesisFileDTO file1, ArtesisFileDTO file2) {
    for (Comparator<ArtesisFileDTO> comparator : listComparators) {
      int result = comparator.compare(file1, file2);
      if (result != 0) {
        return result;
      }
    }
    return 0;
  }

}
