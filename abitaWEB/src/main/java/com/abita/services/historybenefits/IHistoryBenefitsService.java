package com.abita.services.historybenefits;

import com.abita.dto.HistoryBenefitsDTO;
import com.abita.services.historybenefits.exceptions.HistoryBenefitsServiceException;

import java.util.List;

/**
 * Interface du service d'historisation des avantages en nature
 * @author
 *
 */
public interface IHistoryBenefitsService {

  /**
   * Service qui permet l'historisation des avantages en nature
   * @throws HistoryBenefitsServiceException une HistoryBenefitsServiceException
   */
  void historizeAllBenefits() throws HistoryBenefitsServiceException;

  /**
   * Service qui permet de supprimer les historisations des avantages en nature vieille d'un an
   * @param month le mois à supprimer
   * @param year l'année à supprimer
   * @throws HistoryBenefitsServiceException une HistoryBenefitsServiceException
   */
  void deleteOldBenefits(int month, int year) throws HistoryBenefitsServiceException;

  /**
   * Service qui permet de récuperer les avantares en nature en fonction du mois d’historisation et de l’année
   * @param month le mois d’historisation des avantares en nature
   * @param year l’année d’historisation des avantares en nature
   * @return les historisations des avantares en nature
   * @throws HistoryBenefitsServiceException une HistoryBenefitsServiceException
   */
  List<HistoryBenefitsDTO> get(int month, int year) throws HistoryBenefitsServiceException;

  /**
   * Permet de créer les historisation des avantages en nature
   *
   * @param historyBenefitsDTO un avantage
   * @return l’identifiant de l’avantage de nature
   * @throws HistoryBenefitsServiceException une HistoryBenefitsServiceException
   */
  Long create(HistoryBenefitsDTO historyBenefitsDTO) throws HistoryBenefitsServiceException;
}
