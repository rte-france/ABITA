/**
 *
 */
package com.abita.dao.batch.gcp.fieldsetmapper;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;

/**
 * Permet de récupérer les informations lues dans la pièce comptable YL-ZN et d'instancier un objet BigDecimal avec le montant
 * @author
 *
 */
public class YlZnReadAndSendFieldSetMapper implements FieldSetMapper<BigDecimal> {

  @Override
  public BigDecimal mapFieldSet(FieldSet fieldSet) {
    if ("613250".equalsIgnoreCase(fieldSet.readString("account")) || "614110".equalsIgnoreCase(fieldSet.readString("account"))) {
      return new BigDecimal(fieldSet.readString("amount").replace(",", "."));
    } else {
      return new BigDecimal(0);
    }
  }
}
