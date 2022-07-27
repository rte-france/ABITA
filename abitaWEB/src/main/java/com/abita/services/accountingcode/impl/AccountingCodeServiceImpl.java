/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.accountingcode.impl;

import com.abita.dao.accountingcode.entity.AccountingCodeEntity;
import com.abita.dao.accountingcode.exceptions.AccountingCodeDAOException;
import com.abita.services.accountingcode.IAccountingCodeService;
import com.abita.services.accountingcode.exceptions.AccountingCodeServiceException;
import com.abita.dao.accountingcode.IAccountingCodeDAO;
import com.abita.dto.AccountingCodeDTO;
import com.services.common.impl.AbstractService;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe d'implémentation des services des code comptables
 *
 * @author
 */
public class AccountingCodeServiceImpl extends
  AbstractService<AccountingCodeEntity, AccountingCodeDTO, AccountingCodeDAOException, Long, AccountingCodeServiceException, IAccountingCodeDAO> implements IAccountingCodeService {

  /**
   * Interface des DAO des codes comptables
   */
  private IAccountingCodeDAO accountingCodeDAO;

  @Override
  protected IAccountingCodeDAO getSpecificIDAO() {
    return accountingCodeDAO;
  }

  @Override
  public List<AccountingCodeDTO> findAllAccountingCode() throws AccountingCodeServiceException {
    return super.find();
  }

  /**
   * Setter de l'interface des DAO des codes comptables
   * @param accountingCodeDAO the accountingCodeDAO to set
   */
  public void setAccountingCodeDAO(IAccountingCodeDAO accountingCodeDAO) {
    this.accountingCodeDAO = accountingCodeDAO;
  }

  @Override
  public void saveListing(List<AccountingCodeDTO> lstAccountingCode) throws AccountingCodeServiceException {

    List<AccountingCodeDTO> lstCostCenterOriginal = find();

    // Suppression
    for (AccountingCodeDTO accountingCode : lstCostCenterOriginal) {
      if (!lstAccountingCode.contains(accountingCode)) {
        delete(accountingCode.getId());
      }
    }

    // Ajout et modification
    for (AccountingCodeDTO accountingCode : lstAccountingCode) {
      if (lstCostCenterOriginal.contains(accountingCode)) {
        update(accountingCode);
      } else {
        create(accountingCode);
      }
    }

  }

  @Override
  public Map<String, AccountingCodeDTO> buildAccountingCodeMapping() throws AccountingCodeServiceException {
    Map<String, AccountingCodeDTO> mapResult = new HashMap<String, AccountingCodeDTO>();
    List<AccountingCodeDTO> accountingCodeDTOs = findAllAccountingCode();

    for (AccountingCodeDTO accountingCodeDTO : accountingCodeDTOs) {
      if (!StringUtils.isEmpty(accountingCodeDTO.getTechnicalCode())) {
        mapResult.put(accountingCodeDTO.getTechnicalCode(), accountingCodeDTO);
      }
    }
    return mapResult;
  }

}
