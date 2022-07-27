/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.revisionthirdpartycontract;

import com.abita.dao.revisionthirdpartycontract.entity.RevisionThirdPartyContractEntity;
import com.abita.dao.revisionthirdpartycontract.exceptions.RevisionThirdPartyContractDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface des DAO des révisions des contrats tiers
 */
public interface IRevisionThirdPartyContractDAO extends IAbstractDAO<RevisionThirdPartyContractEntity, RevisionThirdPartyContractDAOException> {

  /**
   * Permet de récupérer la liste de toutes les révisions d’un contrat tiers
   *
   * @param tpcId l’identifiant du contrat tiers
   * @return une liste de toutes les révisions du contrat tiers
   * @throws RevisionThirdPartyContractDAOException une RevisionThirdPartyContractDAOException
   */
  List<RevisionThirdPartyContractEntity> findRevisionOfOneContract(Long tpcId) throws RevisionThirdPartyContractDAOException;

  /**
   * Permet de supprimer toutes les historisations de révision d'un contrat tiers
   * @param thirdPartyContractId L'identifiant technique d'un contrat tiers
   * @throws RevisionThirdPartyContractDAOException une RevisionThirdPartyContractDAOException
   */
  void deleteAllRevisionsOfOneContract(Long thirdPartyContractId) throws RevisionThirdPartyContractDAOException;
}
