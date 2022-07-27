/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.ncntaccountingdocnumber;

import com.abita.dao.ncntaccountingdocnumber.entity.NcNtAccountingDocNumberEntity;
import com.abita.dao.ncntaccountingdocnumber.exceptions.NcNtAccountingDocNumberDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public interface INcNtAccountingDocNumberDAO extends IAbstractDAO<NcNtAccountingDocNumberEntity, NcNtAccountingDocNumberDAOException> {

  /**
   * Permet de récupérer le prochain numéro des pièces comptables
   * @return le prochain numéro des pièces comptables
   * @throws NcNtAccountingDocNumberDAOException une NcNtAccountingDocNumberDAOException
   */
  Long getCurrentMaxValPieceNumber() throws NcNtAccountingDocNumberDAOException;

  @Override
  Long create(NcNtAccountingDocNumberEntity ncNtAccountingDocNumberEntity) throws NcNtAccountingDocNumberDAOException;

  /**
   * Permet de supprimer les historisations des numéros comptables d'un contrat occupant
   * @param id identifiant du contrat occupant
   * @throws NcNtAccountingDocNumberDAOException une NcNtAccountingDocNumberDAOException;
   */
  void deleteAllNcNtAccountingDocNumberOfOneContract(Long id) throws NcNtAccountingDocNumberDAOException;

}
