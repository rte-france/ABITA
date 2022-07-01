package com.abita.services.paymentcycle;

import com.abita.dto.PaymentCycleDTO;
import com.abita.services.paymentcycle.exceptions.PaymentCycleServiceException;

import java.util.List;

/**
 * Interface du service des périodicités de paiement
 *
 * @author
 */
public interface IPaymentCycleService {

  /**
   * Permet de récupérer la liste de toutes les périodicités de paiement
   *
   * @return une liste de toutes les les périodicités de paiement
   *
   * @throws PaymentCycleServiceException une PaymentCycleServiceException
   */
  List<PaymentCycleDTO> find() throws PaymentCycleServiceException;

}
