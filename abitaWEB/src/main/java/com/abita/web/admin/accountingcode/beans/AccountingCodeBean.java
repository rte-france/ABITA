package com.abita.web.admin.accountingcode.beans;

import com.abita.dto.AccountingCodeDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Backing bean de la page de gestion des codes comptables
 * @author
 *
 */
public class AccountingCodeBean implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4518146771449315562L;

  /**
   * Objet de transfert des codes comptables
   */
  private AccountingCodeDTO accountingCode;

  /**
   * Liste des codes comptables
   */
  private List<AccountingCodeDTO> lstAccountingCode;

  /**
   * Getter de l'objet de transfert des codes comptables
   * @return the accountingCode
   */
  public AccountingCodeDTO getAccountingCode() {
    return accountingCode;
  }

  /**
   * Setter de l'objet de transfert des codes comptables
   * @param accountingCode the accountingCodeDTO to set
   */
  public void setAccountingCode(AccountingCodeDTO accountingCode) {
    this.accountingCode = accountingCode;
  }

  /**
   * Getter de la liste des codes comptables
   * @return the lstAccountingCode
   */
  public List<AccountingCodeDTO> getLstAccountingCode() {
    return lstAccountingCode;
  }

  /**
   * Setter de la liste des codes comptables
   * @param lstAccountingCode the lstAccountingCode to set
   */
  public void setLstAccountingCode(List<AccountingCodeDTO> lstAccountingCode) {
    this.lstAccountingCode = lstAccountingCode;
  }

}
