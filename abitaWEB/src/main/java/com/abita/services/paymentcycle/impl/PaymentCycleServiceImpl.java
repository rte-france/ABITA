package com.abita.services.paymentcycle.impl;

import com.abita.dao.paymentcycle.entity.PaymentCycleEntity;
import com.abita.dao.paymentcycle.exceptions.PaymentCycleDAOException;
import com.abita.services.paymentcycle.IPaymentCycleService;
import com.abita.services.paymentcycle.exceptions.PaymentCycleServiceException;
import com.abita.dao.paymentcycle.IPaymentCycleDAO;
import com.abita.dto.PaymentCycleDTO;
import com.services.common.impl.AbstractService;

/**
 * Classe d'implémentation des périodicités de paiement
 *
 * @author
 */
public class PaymentCycleServiceImpl extends AbstractService<PaymentCycleEntity, PaymentCycleDTO, PaymentCycleDAOException, Long, PaymentCycleServiceException, IPaymentCycleDAO>
  implements IPaymentCycleService {

  /**
  * Le DAO des périodicités de paiement
  */
  private IPaymentCycleDAO paymentCycleDAO;

  @Override
  protected IPaymentCycleDAO getSpecificIDAO() {
    return paymentCycleDAO;
  }

  /**
   * @param paymentCycleDAO the paymentCycleDAO to set
   */
  public void setPaymentCycleDAO(IPaymentCycleDAO paymentCycleDAO) {
    this.paymentCycleDAO = paymentCycleDAO;
  }

}
