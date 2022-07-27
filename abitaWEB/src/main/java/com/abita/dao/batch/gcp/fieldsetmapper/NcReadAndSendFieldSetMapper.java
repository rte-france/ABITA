/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.gcp.fieldsetmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;

/**
 * Permet de récupérer les informations lues dans la pièce comptable NC et d'instancier un objet BigDecimal avec le montant
 * @author
 *
 */
public class NcReadAndSendFieldSetMapper implements FieldSetMapper<BigDecimal> {

  /** Nom du champ pour le compte */
  private static final String ACCOUNT_NAME = "account";

  @Override
  public BigDecimal mapFieldSet(FieldSet fieldSet) {
    if ("708300".equalsIgnoreCase(fieldSet.readString(ACCOUNT_NAME)) || "708301".equalsIgnoreCase(fieldSet.readString(ACCOUNT_NAME))
      || "708308".equalsIgnoreCase(fieldSet.readString(ACCOUNT_NAME))) {
      return new BigDecimal(fieldSet.readString("amount").replace(",", "."));
    } else {
      return new BigDecimal(0);
    }
  }
}
