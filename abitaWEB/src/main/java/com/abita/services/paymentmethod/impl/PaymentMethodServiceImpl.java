package com.abita.services.paymentmethod.impl;

import com.abita.dao.paymentmethod.entity.PaymentMethodEntity;
import com.abita.dao.paymentmethod.exceptions.PaymentMethodDAOException;
import com.abita.services.paymentmethod.IPaymentMethodService;
import com.abita.dao.paymentmethod.IPaymentMethodDAO;
import com.abita.dto.PaymentMethodDTO;
import com.abita.services.paymentmethod.exceptions.PaymentMethodServiceException;
import com.services.common.impl.AbstractService;

/**
 * Classe d'implémentation des methodes de paiement
 * @author
 *
 */
public class PaymentMethodServiceImpl extends
  AbstractService<PaymentMethodEntity, PaymentMethodDTO, PaymentMethodDAOException, Long, PaymentMethodServiceException, IPaymentMethodDAO> implements IPaymentMethodService {

  /**
  * Le DAO des methodes de paiement
  */
  private IPaymentMethodDAO paymentMethodDAO;

  @Override
  public Long create(PaymentMethodDTO paymentMethodDTO) throws PaymentMethodServiceException {
    return (Long) super.create(paymentMethodDTO);
  }

  @Override
  protected IPaymentMethodDAO getSpecificIDAO() {
    return paymentMethodDAO;
  }

  /**
   * @param paymentMethodDAO the paymentMethodDAO to set
   */
  public void setPaymentMethodDAO(IPaymentMethodDAO paymentMethodDAO) {
    this.paymentMethodDAO = paymentMethodDAO;
  }

}
