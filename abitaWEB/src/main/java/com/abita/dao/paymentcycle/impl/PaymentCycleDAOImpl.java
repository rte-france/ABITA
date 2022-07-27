/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.paymentcycle.impl;

import com.abita.dao.paymentcycle.entity.PaymentCycleEntity;
import com.abita.dao.paymentcycle.exceptions.PaymentCycleDAOException;
import com.abita.dao.paymentcycle.IPaymentCycleDAO;
import com.dao.common.impl.AbstractGenericEntityDAO;

/**
 * Classe d'implémentation des natures
 *
 * @author
 */
public class PaymentCycleDAOImpl extends AbstractGenericEntityDAO<PaymentCycleEntity, PaymentCycleDAOException> implements IPaymentCycleDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = 5191491758997928961L;

}
