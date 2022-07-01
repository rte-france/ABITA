package com.abita.services.contract;

import com.abita.dao.contract.entity.ContractEntity;
import com.abita.services.contract.exceptions.ContractCalculationsServiceFacadeException;
import org.joda.time.LocalDate;

/**
 * Interface des services de calcul des contrats occupants
 */
public interface IContractCalculationsServiceFacade {

  /**
   * Permet de calculer le pécule de fin d'occupation d’un contrat occupant
   * @param contractEntity entité de contrat occupant
   * @param generationDate date de génération
   * @return pécule de fin d'occupation
   * @throws ContractCalculationsServiceFacadeException une ContractCalculationsServiceFacadeException
   */
  ContractEntity calculateActuallyTerminationSavingAmount(ContractEntity contractEntity, LocalDate generationDate) throws ContractCalculationsServiceFacadeException;

  /**
   * Permet de calculer du loyer prélevé cumulé d’un contrat occupant
   * @param contractEntity entité de contrat occupant
   * @param generationDate date de génération
   * @return loyer prélevé cumulé
   * @throws ContractCalculationsServiceFacadeException une ContractCalculationsServiceFacadeException
   */
  ContractEntity calculateActuallyAddedWithdrawnRent(ContractEntity contractEntity, LocalDate generationDate) throws ContractCalculationsServiceFacadeException;

  /**
   * Permet de calculer du loyer prélevé d’un contrat occupant
   * @param contractEntity entité de contrat occupant
   * @param generationDate date de génération
   * @return loyer prélevé
   * @throws ContractCalculationsServiceFacadeException une ContractCalculationsServiceFacadeException
   */
  ContractEntity calculateActuallyWithdrawnRent(ContractEntity contractEntity, LocalDate generationDate) throws ContractCalculationsServiceFacadeException;

}
