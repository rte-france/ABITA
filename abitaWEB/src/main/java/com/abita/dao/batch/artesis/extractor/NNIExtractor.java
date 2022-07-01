/**
 *
 */
package com.abita.dao.batch.artesis.extractor;

import com.abita.services.jobs.artesis.constants.ArtesisConstants;
import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.file.transform.FieldExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui sert à inserer les valeurs formattées que l'on désire écrire dans le fichier ARTESIS des NNI
 * @author
 *
 */
public class NNIExtractor implements FieldExtractor<String> {

  @Override
  public Object[] extract(String item) {
    List<String> result = new ArrayList<String>();

    result.add(StringUtils.left(StringUtils.leftPad(item, ArtesisConstants.REFERENCE_TENANT_LENGTH, " "), ArtesisConstants.REFERENCE_TENANT_LENGTH));

    return result.toArray();
  }

}
