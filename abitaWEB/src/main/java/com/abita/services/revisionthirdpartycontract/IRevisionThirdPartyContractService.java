package com.abita.services.revisionthirdpartycontract;

import com.abita.dto.RevisionThirdPartyContractDTO;
import com.abita.services.revisionthirdpartycontract.exceptions.RevisionThirdPartyContractServiceException;

import java.util.List;

/**
 * Interface du service des révisions des contrats tiers
 */
public interface IRevisionThirdPartyContractService {

  /**
   * Permet de récupérer la liste de toutes les révisions d’un contrat tiers
   *
   * @param tpcId l’identifiant du contrat tiers
   * @return une liste de toutes les révisions du contrat tiers
   *
   * @throws RevisionThirdPartyContractServiceException une RevisionThirdPartyContractServiceException
   */
  List<RevisionThirdPartyContractDTO> findRevisionOfOneContract(Long tpcId) throws RevisionThirdPartyContractServiceException;

  /**
   * Permet de créer une révision de contrat tiers
   *
   * @param revisionThirdPartyContractDTO identifiant du contrat tiers
   * @return l’identifiant de la révision
   * @throws RevisionThirdPartyContractServiceException une RevisionThirdPartyContractServiceException
   */
  Long create(RevisionThirdPartyContractDTO revisionThirdPartyContractDTO) throws RevisionThirdPartyContractServiceException;

  /**
   * Permet de mettre à jour une révision de contrat tiers
   *
   * @param revisionThirdPartyContractDTO une révision
   * @throws RevisionThirdPartyContractServiceException une RevisionThirdPartyContractServiceException
   */
  void update(RevisionThirdPartyContractDTO revisionThirdPartyContractDTO) throws RevisionThirdPartyContractServiceException;

  /**
   * Permet de supprimer toutes les historisations de révision d'un contrat tiers
   * @param thirdPartyContractId L'identifiant technique d'un contrat tiers
   * @throws RevisionThirdPartyContractServiceException une RevisionThirdPartyContractServiceException
   */
  void deleteAllRevisionsOfOneContract(Long thirdPartyContractId) throws RevisionThirdPartyContractServiceException;

}
