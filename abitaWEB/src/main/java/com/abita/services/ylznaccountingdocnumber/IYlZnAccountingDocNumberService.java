/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.ylznaccountingdocnumber;

import com.abita.dto.YlZnAccountingDocNumberDTO;
import com.abita.services.ylznaccountingdocnumber.exceptions.YlZnAccountingDocNumberServiceException;
import org.joda.time.YearMonth;

import java.util.List;

/**
 * Interface des services des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public interface IYlZnAccountingDocNumberService {

  /**
   * Permet de créer une information nécessaires à la gestion du numéro de document des pièces comptables
   * @param ylZnAccountingDocNumberDTO une information nécessaires à la gestion du numéro de document des pièces comptables
   * @return le numéro de pièce comptable formatée
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   */
  String createAccountingPieceNumber(YlZnAccountingDocNumberDTO ylZnAccountingDocNumberDTO) throws YlZnAccountingDocNumberServiceException;

  /**
   * Permet de récupérer la pièce comptable de la dernière génération d’un contrat tiers d’un mois précis
   * @param id l’identifiant du contrat tier
   * @param yearMonth le couple mois/année de la génération
   * @return la pièce comptable
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   */
  YlZnAccountingDocNumberDTO findLastGenerationByThirdPartyContractIdAndYearMonth(Long id, YearMonth yearMonth) throws YlZnAccountingDocNumberServiceException;

  /**
   * Permet de récupérer la pièce comptable de la dernière génération d’un contrat tiers
   * @param id l’identifiant du contrat tier
   * @return la pièce comptable
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   */
  YlZnAccountingDocNumberDTO findLastGenerationByThirdPartyContractId(Long id) throws YlZnAccountingDocNumberServiceException;

  /**
   * Permet de récupérer les pièces comptables d’un contrat tiers à partir d’un mois précis
   * @param id l’identifiant du contrat tier
   * @param yearMonth le couple mois/année de la génération
   * @return les pièces comptables
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   */
  List<YlZnAccountingDocNumberDTO> findGenerationsByThirdPartyContractIdFromYearMonth(Long id, YearMonth yearMonth) throws YlZnAccountingDocNumberServiceException;

  /**
   * Service permettant de supprimer les numéros comptables d'un contrat tiers
   * @param id identifiant du contrat tiers
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   */
  void deleteAllYlZnAccountingDocNumberOfOneContract(Long id) throws YlZnAccountingDocNumberServiceException;
}
