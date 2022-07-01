package com.abita.dao.batch.gcp.extractor;

import com.abita.dao.batch.gcp.GCPEntryLine;
import org.springframework.batch.item.file.transform.FieldExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Permet d'écrire une ligne de pièces comptable GCP
 * @author
 *
 */
public class GCPEntryLineExtractor implements FieldExtractor<GCPEntryLine> {

  @Override
  public Object[] extract(GCPEntryLine item) {
    List<String> result = new ArrayList<String>();

    result.add(item.getTypeLigne());
    result.add(item.getCompte());
    result.add(item.getCodeCleCGS());
    result.add(item.getCleComptabilisation());
    result.add(item.getMontantDevisePiece());
    result.add(item.getCodeTVA());
    result.add(item.getDomaineActivite());
    result.add(item.getDateBase());
    result.add(item.getConditionPaiement());
    result.add(item.getCentreCout());
    result.add(item.getZoneAffection());
    result.add(item.getTexte());

    return result.toArray();
  }
}
