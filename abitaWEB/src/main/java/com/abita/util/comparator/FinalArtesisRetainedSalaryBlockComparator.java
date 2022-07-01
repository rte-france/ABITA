/**
 *
 */
package com.abita.util.comparator;

import com.abita.dao.batch.artesis.ArtesisRetainedSalaryBlock;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparateur d'objet des block d'Artesis en fonction de la reference du l'occupant et du code de rubrique de paie (URSSAF)
 * @author
 *
 */
public class FinalArtesisRetainedSalaryBlockComparator implements Comparator<ArtesisRetainedSalaryBlock>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = 4680210953755925105L;

  @Override
  public int compare(ArtesisRetainedSalaryBlock o1, ArtesisRetainedSalaryBlock o2) {
    int result = o1.getTenantReference().compareTo(o2.getTenantReference());
    if (result == 0) {
      result = o1.getPayCatgoryCode().compareTo(o2.getPayCatgoryCode());
    }
    return result;
  }
}
