package com.abita.util.comparator;

import com.abita.dto.AccountingCodeDTO;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Comparateur d'objet AccountingCode par le code comptable
 * @author
 *
 */
public class AccountingCodeComparator implements Comparator<AccountingCodeDTO>, Serializable {

  /** Serial ID */
  private static final long serialVersionUID = -8259705225774263154L;

  @Override
  public int compare(AccountingCodeDTO o1, AccountingCodeDTO o2) {
    return o1.getCode().compareTo(o2.getCode());
  }

}
