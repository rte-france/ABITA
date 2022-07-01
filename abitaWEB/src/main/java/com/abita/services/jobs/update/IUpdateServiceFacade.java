package com.abita.services.jobs.update;

import com.abita.services.jobs.update.exceptions.UpdateServiceFacadeException;

/**
 * Interface du service façade des jobs pour la mise à jour des données
 */
public interface IUpdateServiceFacade {

  /**
   * Permet de traiter les différentes mises à jour à effectuer sur les données des contrats tiers
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  void updateThirdPartyContractData() throws UpdateServiceFacadeException;

  /**
   * Permet de traiter les différentes mises à jour à effectuer sur les données des contrats occupants
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  void updateContractData() throws UpdateServiceFacadeException;

  /**
   * Permet de traiter les différentes mises à jour à effectuer sur les données des contrats
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  void updateData() throws UpdateServiceFacadeException;

  /**
   * Permet de traiter les différentes calculs des montants à effectuer sur les données des contrats
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  void updateCalculatedAmountContractData() throws UpdateServiceFacadeException;

  /**
   * Permet de traiter les différentes mises à jour à effectuer sur les données des contrats de rétroactivité
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  void updateContractDataRetroactivity() throws UpdateServiceFacadeException;

  /**
   * Permet de sauvegarder des champs lors de la cloture d'un contrat tiers
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  void saveDatasWhenClosedThirdPartyContract() throws UpdateServiceFacadeException;

  /**
   * Permet de sauvegarder des champs lors de la cloture d'un contrat occupant
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  void saveDatasWhenClosedContract() throws UpdateServiceFacadeException;
}
