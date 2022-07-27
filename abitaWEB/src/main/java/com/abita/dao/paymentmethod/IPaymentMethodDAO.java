/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.paymentmethod;

import com.abita.dao.paymentmethod.entity.PaymentMethodEntity;
import com.abita.dao.paymentmethod.exceptions.PaymentMethodDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO des méthodes de paiement
 * @author
 *
 */
public interface IPaymentMethodDAO extends IAbstractDAO<PaymentMethodEntity, PaymentMethodDAOException> {

}
