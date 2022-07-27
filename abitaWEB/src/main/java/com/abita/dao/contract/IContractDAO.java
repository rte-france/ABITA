/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.contract;

import com.abita.dao.contract.entity.ContractEntity;
import com.abita.dao.contract.exceptions.ContractDAOException;
import com.abita.dto.unpersist.ContractCriteriaDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.dao.common.IAbstractDAO;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

/**
 * Interface des DAO des contrats tiers
 *
 * @author
 */
public interface IContractDAO extends IAbstractDAO<ContractEntity, ContractDAOException> {

  /**
   * Permet de récupérer la liste des contrats tiers en BDD répondant aux critères choisis
   * @param contractCriteria critères de recherche des contrats occupants
   * @param tenantCriteria critères de recherche des occupants
   * @param housingCriteria critères de recherche sur les informations des logements
   * @return une liste de tous les contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findByCriteria(ContractCriteriaDTO contractCriteria, TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria) throws ContractDAOException;

  /**
   * permet de récupérer la liste du nombre de logements et d'occupants en conflit avec les dates saisies
   * @param contractId L'identifiant du contract occcupant
   * @param dateStart date de début de validité
   * @param dateEnd date de fin de validité
   * @param housingId L'identifiant du logement
   * @param tenantId L'identifaint de l'occupant
   * @return une liste de deux integers : nombre de conflits logements et nombre de conflits occupants
   * @throws ContractDAOException une ContractDAOException
   */
  List<Object[]> validityContractDate(Long contractId, Date dateStart, Date dateEnd, Long housingId, Long tenantId) throws ContractDAOException;

  /**
   * Retourne la liste des contrats pour un occupant
   * @param idTenant l'identifiant d'un occupant
   * @return une liste de contractEntity
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractByTenant(long idTenant) throws ContractDAOException;

  /**
   * Retourne la liste des contrats pour un logement
   * @param idHousing l'identifiant pour un logement
   * @return une liste de contractEntity
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractByHousing(long idHousing) throws ContractDAOException;

  /**
   * retourne le nombre de contrat conflit qui ne sont pas fini avant la date de sortie du logement
   * @param idHousing identifiant du logement
   * @param unregisterDate la date de sortie
   * @return le nombre de contrats en conflit
   * @throws ContractDAOException une ContractDAOException
   */
  Long unregisterDateAfterEndValidityDate(Long idHousing, Date unregisterDate) throws ContractDAOException;

  /**
   * Permet de récupérer la liste des contrats occupants en cours pour les occupants salariés logés
   * @return la liste de contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsInProgressOfSalariedTenant() throws ContractDAOException;

  /**
   * Permet de récupérer la liste des contrats occupants en cours à régulariser pour les occupants salariés logés
   * @return la liste de contrats occupants à régulariser
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsInProgressToRegularizeOfSalariedTenant() throws ContractDAOException;

  /**
   * Permet de récupérer la liste des contrats occupants avec le type de paiement retenue sur paie pour les occupants salariés logés
   * @return la liste de contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsWithRetainedSalary() throws ContractDAOException;

  /**
   * Permet de récupérer la liste des contrats occupants en cours sauf ceux avec une methode de paiement "Compensation interne"
   * @return la liste de contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsInProgressWithoutInternalCompensation() throws ContractDAOException;

  /**
   * Permet de récuperer la liste des contrats occupants en cours sauf ceux avec une methode de paiement "Compensation interne"
   * @return la liste de contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsInProgressAndToRegularizeWithoutInternalCompensation() throws ContractDAOException;

  /**
   * Permet de récupérer la liste des contrats occupants sauf ceux avec une methode de paiement "Compensation interne"
   * @return la liste de contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsWithoutInternalCompensation() throws ContractDAOException;

  /**
   * Permet de récupérer la liste des contrats occupants en cours
   * @param date date de traitement
   * @return la liste de contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsInProgress(LocalDate date) throws ContractDAOException;

  /**
   * Permet de récupérer la liste des contrats occupants en cours sans rétroactivité
   * @param date date de traitement
   * @return la liste de contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsInProgressWithoutRetroactivity(LocalDate date) throws ContractDAOException;

  /**
   * Compte le nombre d'occupant qu'il y a eu dans un logement
   * @param idHousing l’identifiant pour un logement
   * @return Le nombre d'occupant
   * @throws ContractDAOException une ContractDAOException
   */
  Long countContractsInHousing(Long idHousing) throws ContractDAOException;

  /**
   * Permet de récupérer un contrat occupant par sa référence
   * @param reference la référence du contrat occupant
   * @return un contrat occupant
   * @throws ContractDAOException une ContractDAOException
   */
  ContractEntity findByReference(String reference) throws ContractDAOException;

  /**
   * Permet de mettre à jour le champ des facturations ponctuelles des contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  void updateSporadicallyInvoicingContracts() throws ContractDAOException;

  /**
   * Permet de mettre à jour le champ loyer prélevé cumulé (RAZ) des contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  void updateResetAddedWithdrawnRentContracts() throws ContractDAOException;

  /**
   * Permet de mettre à jour le loyer écreté tous les 1er janvier
   * @throws ContractDAOException une ContractDAOException
   */
  void updateLopRent() throws ContractDAOException;

  /**
   * Permet de récupérer tous les contrats dont le loyer garage et jardin doit être revalorisé
   * @return la liste de contrats occupants
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsWithRentToUpgrade() throws ContractDAOException;

  /**
   * Permet de récupérer les contrats à régulariser pour la rétroactivité sans compensation interne
   * @return une liste de tous les contrats occupants concernés
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsInProgressToRegularizeWithoutInternalCompensation() throws ContractDAOException;

  /**
   * Permet de récupérer les contrats occupants à régulariser pour la génération du fichier ARTESIS Retenue sur paie
   * @return Une liste de tous les contrats occupants à régulariser avec le mode de paiement "Retenue sur paie"
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsToRegularizeWithRetainedSalary() throws ContractDAOException;

  /**
   * Permet de récupérer les contrats occupants à historiser
   * @return Une liste de tous les contrats occupants à historiser
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsToHistorize() throws ContractDAOException;

  /**
   * Permet de récupérer les contrats occupants de rétroactivité avec mises à jour à effectuer sur les données
   * @return Une liste de tous les contrats occupants à historiser
   * @throws ContractDAOException une ContractDAOException
   */
  List<ContractEntity> findContractsToUpdateDataRetroactivity() throws ContractDAOException;

  /**
   * Permet de remettre à 0 la liste des contrats à régulariser pour la rétroactivité
   * @throws ContractDAOException une ContractDAOException
   */
  void resetContractsToRegularize() throws ContractDAOException;

  /**
   * Permet de recuperer le domaine d'activité du contrat en cours sur le logement
   * @param idHousing l'identifiant du logement
   * @return le contrat occupant en cours sur le logement
   * @throws ContractDAOException une ContractDAOException
   */
  ContractEntity findCurrentContractOfOneHousing(Long idHousing) throws ContractDAOException;
}
