package com.abita.services.historyreporting;

import com.abita.dto.HistoryReportingDTO;
import com.abita.services.historyreporting.exceptions.HistoryReportingServiceException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface du service d’historisation des reporting
 */
public interface IHistoryReportingService {

  /**
   * Permet de récupérer le reporting d’une pièce pour un mois et une année
   * @param type type de pièce
   * @param month le mois sélectionné
   * @param year l’année sélectionnée
   * @return le reporting historisé
   * @throws HistoryReportingServiceException une HistoryReportingServiceException
   */
  HistoryReportingDTO findHistoryReportingByTypeAndYearMonth(String type, int month, int year) throws HistoryReportingServiceException;

  /**
   * Permet d’enregistrer un reporting
   * @param type type de pièce
   * @param amounts liste des moutants
   * @throws HistoryReportingServiceException une HistoryReportingServiceException
   */
  void create(String type, List<BigDecimal> amounts) throws HistoryReportingServiceException;
}
