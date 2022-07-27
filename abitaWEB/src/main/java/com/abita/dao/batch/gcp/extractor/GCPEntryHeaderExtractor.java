/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.gcp.extractor;

import com.abita.dao.batch.gcp.GCPHeader;
import org.springframework.batch.item.file.transform.FieldExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Permet d'ecrire l'en-tête des pièces comptables GCP
 * @author
 *
 */
public class GCPEntryHeaderExtractor implements FieldExtractor<GCPHeader> {

  @Override
  public Object[] extract(GCPHeader item) {
    List<String> result = new ArrayList<String>();

    result.add(item.getTypeLigne());
    result.add(item.getDatePiece());
    result.add(item.getTypePiece());
    result.add(item.getSociete());
    result.add(item.getDevisePiece());
    result.add(item.getDateComptable());
    result.add(item.getNumPieceComptable());
    result.add(item.getTexteEntete());
    result.add(item.getReference());

    return result.toArray();
  }

}
