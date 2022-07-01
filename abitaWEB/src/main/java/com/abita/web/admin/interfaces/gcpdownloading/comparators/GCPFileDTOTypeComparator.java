package com.abita.web.admin.interfaces.gcpdownloading.comparators;

import com.abita.dto.unpersist.GCPFileDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparateur qui permet de faire un tri par type pour les
 * pièces Comptables GCP
 * @author
 *
 */
public class GCPFileDTOTypeComparator implements Comparator<GCPFileDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = -1224282449619032276L;

  @Override
  public int compare(GCPFileDTO file1, GCPFileDTO file2) {
    return file1.getType().compareTo(file2.getType());
  }

}
