/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.paymentcycle;

import com.abita.dao.paymentcycle.entity.PaymentCycleEntity;
import com.abita.dao.paymentcycle.exceptions.PaymentCycleDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO des périodicités de paiement
 *
 * @author
 */
public interface IPaymentCycleDAO extends IAbstractDAO<PaymentCycleEntity, PaymentCycleDAOException> {

}
