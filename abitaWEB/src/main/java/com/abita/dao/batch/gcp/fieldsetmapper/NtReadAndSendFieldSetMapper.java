/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.dao.batch.gcp.fieldsetmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;

/**
 * Permet de récupérer les informations lues dans la pièce comptable NT et d'instancier un objet BigDecimal avec le montant
 * @author
 *
 */
public class NtReadAndSendFieldSetMapper implements FieldSetMapper<BigDecimal> {

  @Override
  public BigDecimal mapFieldSet(FieldSet fieldSet) {
    if ("708301".equalsIgnoreCase(fieldSet.readString("account"))) {
      return new BigDecimal(fieldSet.readString("amount").replace(",", "."));
    } else {
      return new BigDecimal(0);
    }
  }
}
