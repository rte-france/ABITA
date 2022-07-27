/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.revisionthirdpartycontract.impl;

import com.abita.dao.revisionthirdpartycontract.entity.RevisionThirdPartyContractEntity;
import com.abita.dao.revisionthirdpartycontract.exceptions.RevisionThirdPartyContractDAOException;
import com.abita.services.revisionthirdpartycontract.IRevisionThirdPartyContractService;
import com.abita.services.revisionthirdpartycontract.exceptions.RevisionThirdPartyContractServiceException;
import com.abita.dao.revisionthirdpartycontract.IRevisionThirdPartyContractDAO;
import com.abita.dto.RevisionThirdPartyContractDTO;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d’implémentation des services des révisions des contrats tiers
 */
public class RevisionThirdPartyContractServiceImpl
  extends
  AbstractService<RevisionThirdPartyContractEntity, RevisionThirdPartyContractDTO, RevisionThirdPartyContractDAOException, Long, RevisionThirdPartyContractServiceException, IRevisionThirdPartyContractDAO>
  implements IRevisionThirdPartyContractService {

  private IRevisionThirdPartyContractDAO revisionThirdPartyContractDAO;

  @Override
  public Long create(RevisionThirdPartyContractDTO revisionThirdPartyContractDTO) throws RevisionThirdPartyContractServiceException {
    return (Long) super.create(revisionThirdPartyContractDTO);
  }

  @Override
  public List<RevisionThirdPartyContractDTO> findRevisionOfOneContract(Long tpcId) throws RevisionThirdPartyContractServiceException {
    try {
      List<RevisionThirdPartyContractDTO> revisionsThirdPartyContract = new ArrayList<RevisionThirdPartyContractDTO>();

      List<RevisionThirdPartyContractEntity> revisionsThirdPartyContractEntity = revisionThirdPartyContractDAO.findRevisionOfOneContract(tpcId);

      for (RevisionThirdPartyContractEntity revisionThirdPartyContractEntity : SafetyUtils.emptyIfNull(revisionsThirdPartyContractEntity)) {
        RevisionThirdPartyContractDTO revisionThirdPartyContractDTO = mapper.map(revisionThirdPartyContractEntity, RevisionThirdPartyContractDTO.class);
        revisionsThirdPartyContract.add(revisionThirdPartyContractDTO);
      }

      return revisionsThirdPartyContract;
    } catch (RevisionThirdPartyContractDAOException e) {
      throw new RevisionThirdPartyContractServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void deleteAllRevisionsOfOneContract(Long thirdPartyContractId) throws RevisionThirdPartyContractServiceException {
    try {
      revisionThirdPartyContractDAO.deleteAllRevisionsOfOneContract(thirdPartyContractId);
    } catch (RevisionThirdPartyContractDAOException e) {
      throw new RevisionThirdPartyContractServiceException(e.getMessage(), e);
    }

  }

  @Override
  protected IRevisionThirdPartyContractDAO getSpecificIDAO() {
    return revisionThirdPartyContractDAO;
  }

  /**
   * @param revisionThirdPartyContractDAO the revisionThirdPartyContractDAO to set
   */
  public void setRevisionThirdPartyContractDAO(IRevisionThirdPartyContractDAO revisionThirdPartyContractDAO) {
    this.revisionThirdPartyContractDAO = revisionThirdPartyContractDAO;
  }

}
