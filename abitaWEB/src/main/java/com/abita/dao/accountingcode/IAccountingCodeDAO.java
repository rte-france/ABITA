/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.accountingcode;

import com.abita.dao.accountingcode.entity.AccountingCodeEntity;
import com.abita.dao.accountingcode.exceptions.AccountingCodeDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface de DAO des codes comptables
 * @author
 *
 */
public interface IAccountingCodeDAO extends IAbstractDAO<AccountingCodeEntity, AccountingCodeDAOException> {

}
