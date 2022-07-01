/**
 *
 */
package com.abita.dao.batch.artesis.extractor;

import com.abita.dao.batch.artesis.ArtesisRetainedSalaryBlock;
import org.springframework.batch.item.file.transform.FieldExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui sert à inserer les valeurs formattées que l'on désire écrire dans le fichier ARTESIS retenue sur paie
 * @author
 *
 */
public class RetainedSalaryExtractor implements FieldExtractor<ArtesisRetainedSalaryBlock> {

  @Override
  public Object[] extract(ArtesisRetainedSalaryBlock item) {
    List<String> result = new ArrayList<String>();

    result.add(item.getOriginApplication());
    result.add(item.getPayArea());
    result.add(item.getPayPeriod());
    result.add(item.getNni());
    result.add(item.getRecordType());
    result.add(item.getFreeZone());
    result.add(item.getPayCatgoryCode());
    result.add(item.getSenseCode());
    result.add(item.getAmount());
    result.add(item.getEffectDate());
    result.add(item.getFreeZone2());

    return result.toArray();
  }

}
