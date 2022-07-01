package com.abita.services.paymentmethod;

import com.abita.dto.PaymentMethodDTO;
import com.abita.services.paymentmethod.exceptions.PaymentMethodServiceException;

import java.util.List;

/**
 * Interface du service des methodes de paiement
 * @author
 *
 */
public interface IPaymentMethodService {

  /**
   * Permet de récupérer la liste de toutes les methodes de paiement
   *
   * @return une liste de toutes les methodes de paiement
   *
   * @throws PaymentMethodException une PaymentMethodServiceException
   */
  List<PaymentMethodDTO> find() throws PaymentMethodServiceException;

  /**
   * Permet de créer une nouveau methode de paiement  en BDD
   *
   * @param paymentMethodDTO une methode de paiement
   * @return l'identifiant de la methode de paiement
   * @throws PaymentMethodServiceException une PaymentMethodServiceException
   */
  Long create(PaymentMethodDTO paymentMethodDTO) throws PaymentMethodServiceException;

  /**
   * Met à jour une methode de paiement  en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param paymentMethodDTO methode de paiement
   * @throws PaymentMethodServiceException une PaymentMethodServiceException
   */
  void update(PaymentMethodDTO paymentMethodDTO) throws PaymentMethodServiceException;

  /**
   * Supprime une methode de paiement  en BDD
   *
   * @param id l'identifiant de la methode de paiement  à supprimer
   * @throws PaymentMethodServiceException une PaymentMethodServiceException
   */
  void delete(Long id) throws PaymentMethodServiceException;

}
