package com.abita.dao.paymentmethod.impl;

import com.abita.dao.paymentmethod.entity.PaymentMethodEntity;
import com.abita.dao.paymentmethod.exceptions.PaymentMethodDAOException;
import com.abita.dao.paymentmethod.IPaymentMethodDAO;
import com.dao.common.impl.AbstractGenericEntityDAO;

/**
 *  Classe d'implémentation des méthodes de paiement
 * @author
 *
 */
public class PaymentMethodDAOImpl extends AbstractGenericEntityDAO<PaymentMethodEntity, PaymentMethodDAOException> implements IPaymentMethodDAO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -6445700278002478822L;

}
