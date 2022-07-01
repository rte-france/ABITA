package com.abita.web.admin.interfaces.gcpdownloading.comparators;

import com.abita.dto.unpersist.GCPFileDTO;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Permet de trier les pièces comptables GCP par type et par date
 * @author
 *
 */
public class GCPFileDTOComparator implements Comparator<GCPFileDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = -6472543797413590426L;

  /** Une liste de comparateurs */
  private List<Comparator<GCPFileDTO>> listComparators;

  /**
   * Constructeur
   * @param comparators liste de comparteurs
   */
  public GCPFileDTOComparator(Comparator<GCPFileDTO>... comparators) {
    listComparators = Arrays.asList(comparators);
  }

  @Override
  public int compare(GCPFileDTO file1, GCPFileDTO file2) {
    for (Comparator<GCPFileDTO> comparator : listComparators) {
      int result = comparator.compare(file1, file2);
      if (result != 0) {
        return result;
      }
    }
    return 0;
  }

}
