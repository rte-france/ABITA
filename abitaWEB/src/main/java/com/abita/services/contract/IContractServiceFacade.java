/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.contract;

import com.abita.dto.ContractDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.RevaluationRentsDTO;
import com.abita.dto.unpersist.ContractCriteriaDTO;
import com.abita.dto.unpersist.ContractInputDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

/**
 * Interface des services des contrats occupants
 *
 * @author
 */
public interface IContractServiceFacade {

  /**
   * Permet de créer un nouveau contrat occupants en BDD
   *
   * @param contractInputDTO un contrat occupants
   * @return l'identifiant du contrat occupants
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  Long create(ContractInputDTO contractInputDTO) throws ContractServiceFacadeException;

  /**
   * Permet de récupérer un contrat occupants par son identifiant en BDD
   *
   * @param id l'identifiant du contrat occupants
   * @return Un contrat occupants
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  ContractDTO get(Long id) throws ContractServiceFacadeException;

  /**
   * Met à jour un contrat occupants en BDD en fonction d'un contractInputDTO
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param contractInputDTO un contrat occupant modifié
   * @param actualContractDTO un contrat occupants
   * @return un contrat occupants modifié
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  ContractDTO update(ContractInputDTO contractInputDTO, ContractDTO actualContractDTO) throws ContractServiceFacadeException;

  /**
   * Service permettant de supprimer un contrat occupant
   * @param id identifiant du contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void delete(Long id) throws ContractServiceFacadeException;

  /**
   * Permet de récupérer la liste de tous les contrats occupants en BDD
   *
   * @return une liste de tous les contrats occupants
   *
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> find() throws ContractServiceFacadeException;

  /**
   * Permet de récupérer la liste des contrat occupants en BDD répondant aux critères choisis
   * @param contractCriteria critère de recherche des contrats occupants
   * @param tenantCriteria critère de recherche des occupants
   * @param housingCriteria critères de recherche sur les informations des logements
   * @return une liste de tous les contrats occupants
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findByCriteria(ContractCriteriaDTO contractCriteria, TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria)
    throws ContractServiceFacadeException;

  /**
   * Permet de mapper un ContractDTO en un ContractInputDTO
   *
   * @param contractDTO un ContractDTO
   *
   * @return un ContractInputDTO
   *
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  ContractInputDTO mapToInputDTO(ContractDTO contractDTO) throws ContractServiceFacadeException;

  /**
   * Transforme une liste de ContractDTO en une liste de ContractInputDTO à des fins d'affichage
   * @param contractDTO un contractDTO
   * @return un contractInputDTO
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractInputDTO> mapToInputDTO(List<ContractDTO> contractDTO) throws ContractServiceFacadeException;

  /**
   * Permet de vérifier si les dates de début et de fin de validité sont valide en fonction des identifiants des logements et des occupants
   * @param contractId id d'un contrat
   * @param dateStart date d'entrée de validité
   * @param dateEnd date de fin de validité
   * @param housingId id d'un logement
   * @param tenantId id d'un occupant
   * @return List<Integer> Collection du résultat des deux counts de la requete, équivalent au nbre de conflits avec l'occupant et le logement
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<Integer> validityContractDate(Long contractId, Date dateStart, Date dateEnd, Long housingId, Long tenantId) throws ContractServiceFacadeException;

  /**
   * Permet d'obtenir la liste des contrats pour un logement
   * @param idHousing identifiant du logement
   * @return liste de contract
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractByHousing(long idHousing) throws ContractServiceFacadeException;

  /**
   * Permet d'obtenir la liste des contrats pour un occupant
   * @param idTenant identifiant de l'occupant
   * @return Liste de contract
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractByTenant(long idTenant) throws ContractServiceFacadeException;

  /**
   * Permet de recuperer le nombre de contrat occupant qui ne sont pas fini avant la date de sortie du logement
   * @param idHousing identifiant du logement
   * @param unregisterDate la date de sortie
   * @return le nombre de contrat en conflit
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  Long unregisterDateAfterEndValidityDate(Long idHousing, Date unregisterDate) throws ContractServiceFacadeException;

  /**
   * Permet de récuperer la liste des contrats occupants en cours des occupants salariés logés.
   * @return La liste des contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractsInProgressOfSalariedTenant() throws ContractServiceFacadeException;

  /**
   * Permet de récuperer la liste des contrats occupants en cours des occupants salariés logés à régulariser
   * @return La liste des contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractsInProgressToRegularizeOfSalariedTenant() throws ContractServiceFacadeException;

  /**
   * Permet de récuperer la liste des contrats occupants actifs avec le type de paiement retenue sur paie des occupants salariés logés.
   * @return La liste des contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findActiveContractsWithRetainedSalary() throws ContractServiceFacadeException;

  /**
   * Permet de récuperer la liste des contrats occupants en cours sauf ceux avec une methode de paiement "Compensation interne"
   * @return La liste des contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractsInProgressWithoutInternalCompensation() throws ContractServiceFacadeException;

  /**
   * Permet de récuperer la liste des contrats occupants actifs (en cours ou FP) sauf ceux avec une methode de paiement « Compensation interne »
   * @return La liste des contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findActiveContractsWithoutInternalCompensation() throws ContractServiceFacadeException;

  /**
   * Permet de récuperer la liste des contrats occupants actifs (en cours ou FP) sauf ceux avec une methode de paiement « Compensation interne » ni rétroactivité en modification
   * @return La liste des contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findActiveContractsWithoutInternalCompensationAndWithoutRetroactivityInModification() throws ContractServiceFacadeException;

  /**
   * Permet de récuperer la liste des contrats occupants en cours et rétroactifs sauf ceux avec une methode de paiement « Compensation interne »
   * @return La liste des contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractsInProgressAndToRegularizeWithoutInternalCompensation() throws ContractServiceFacadeException;

  /**
   * Permet de récuperer la liste des contrats occupants en cours
   * @param date date de traitement
   * @return La liste des contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractsInProgress(LocalDate date) throws ContractServiceFacadeException;

  /**
   * Permet de récuperer la liste des contrats occupants en cours sans rétroactivité
   * @param date date de traitement
   * @return La liste des contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractsInProgressWithoutRetroactivity(LocalDate date) throws ContractServiceFacadeException;

  /**
   * Permet de mettre à jour les contrats occupants en cours
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void updateContractsInProgress() throws ContractServiceFacadeException;

  /**
   * Permet de mettre à jour les contrats occupants d’un logement
   * @param housingId identifiant du logement
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void updateContractsForHousing(Long housingId) throws ContractServiceFacadeException;

  /**
   * Permet de mettre à jour les contrats occupants d’un occupant
   * @param tenantId identifiant de l’occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void updateContractsForTenant(Long tenantId) throws ContractServiceFacadeException;

  /**
   * Permet de mettre à jour les facturations et remboursements (RAZ) des contrats occupants
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void updateSporadicallyInvoicingContracts() throws ContractServiceFacadeException;

  /**
   * Permet de mettre à jour le champ loyer prélevé cumulé (RAZ) des contrats occupants
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void updateResetAddedWithdrawnRentContracts() throws ContractServiceFacadeException;

  /**
   * Permet de mettre à jour le champ loyer prélevé cumulé (incrémentation) dans l'année des contrats occupants en cours
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void updateAddedWithdrawnRentContractsInProgress() throws ContractServiceFacadeException;

  /**
   * Permet de mettre à jour le champ pécule d'occupation (incrémentation) des contrats occupants en cours
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void updateTerminationSavingsContractsInProgress() throws ContractServiceFacadeException;

  /**
   * Permet de mettre à jour le loyer écreté tous les 1er janvier
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void updateLopRent() throws ContractServiceFacadeException;

  /**
   * Compte le nombre d'occupant qu'il y a eu dans un logement
   * @param idHousing identifiant du logement
   * @return Le nombre d'occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  Long countContractsInHousing(Long idHousing) throws ContractServiceFacadeException;

  /**
   * Permet de récupérer un contrat occupant par sa référence
   * @param reference la référence du contrat occupant
   * @return un contrat occupant
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  ContractDTO findByReference(String reference) throws ContractServiceFacadeException;

  /**
   * Permet d’appliquer le taux de revalorisation des loyers
   * @param revaluationRentsDTO Revalorisation à appliquer
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void applyRevaluationRent(RevaluationRentsDTO revaluationRentsDTO) throws ContractServiceFacadeException;

  /**
   * Permet de récupérer les contrats occupants à régulariser pour la rétroactivité
   * @return une liste de tous les contrats concernés
   * @throws ContractServiceFacadeException l’exception
   */
  List<ContractDTO> findContractsInProgressToRegularizeWithoutInternalCompensation() throws ContractServiceFacadeException;

  /**
   * Permet de récupérer les contrats occupants à régulariser pour la génération du fichier ARTESIS Retenue sur paie
   * @return Une liste de tous les contrats occupants à régulariser avec le mode de paiement "Retenue sur paie"
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractsToRegularizeWithRetainedSalary() throws ContractServiceFacadeException;

  /**
   * Permet de récupérer les contrats occupants à historiser
   * @return une liste de tous les contrats occupants à régulariser
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractsToHistorize() throws ContractServiceFacadeException;

  /**
   * Permet de récupérer les contrats occupants de rétroactivité avec mises à jour à effectuer sur les données
   * @return une liste de tous les contrats occupants à régulariser
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  List<ContractDTO> findContractsToUpdateDataRetroactivity() throws ContractServiceFacadeException;

  /**
   * Permet de mettre à jour un contrat avec de la rétoactivité
   * @param contract un contrat rétroactif
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void updateOneContractDataRetroactivity(ContractDTO contract) throws ContractServiceFacadeException;

  /**
   * Permet de remettre à 0 la liste des contrats à régulariser pour la rétroactivité
   * @throws ContractServiceFacadeException l’exception
   */
  void resetContractsToRegularize() throws ContractServiceFacadeException;

  /**
   * Permet de mettre à jour tous les champs calculés des contrats
   * @throws ContractServiceFacadeException l’exception
   */
  void updateAllContracts() throws ContractServiceFacadeException;

  /**
   * Permet de recuperer le domaine d'activité du contrat en cours sur le logement
   * @param idHousing l’identifiant du logement
   * @return le domaine d'activité du contrat occupant en cours sur le logement
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  FieldOfActivityDTO findFieldOfActivityForOneHousing(Long idHousing) throws ContractServiceFacadeException;

  /**
   * Permet de sauvegarder des champs lors de la cloture d'un contrat tiers
   * @throws ContractServiceFacadeException une ContractServiceFacadeException
   */
  void saveDatasWhenClosedContract() throws ContractServiceFacadeException;
}
