/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.ncntaccountingdocnumber;

import com.abita.dto.NcNtAccountingDocNumberDTO;
import com.abita.services.ncntaccountingdocnumber.exceptions.NcNtAccountingDocNumberServiceException;

/**
 * Interface des services des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public interface INcNtAccountingDocNumberService {

  /**
   * Permet de créer une information nécessaires à la gestion du numéro de document des pièces comptables
   * @param ncNtAccountingDocNumberDTO une information nécessaires à la gestion du numéro de document des pièces comptables
   * @return le numéro de pièce comptable formatée
   * @throws NcNtAccountingDocNumberServiceException une NcNtAccountingDocNumberServiceException
   */
  String createAccountingPieceNumber(NcNtAccountingDocNumberDTO ncNtAccountingDocNumberDTO) throws NcNtAccountingDocNumberServiceException;

  /**
   * Service permettant de supprimer les numéros comptables d'un contrat occupant
   * @param id identifiant du contrat occupant
   * @throws NcNtAccountingDocNumberServiceException une NcNtAccountingDocNumberServiceException
   */
  void deleteAllNcNtAccountingDocNumberOfOneContract(Long id) throws NcNtAccountingDocNumberServiceException;

}
