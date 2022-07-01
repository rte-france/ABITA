/**
 *
 */
package com.abita.dao.batch.gcp.writer;

import com.abita.services.historyreporting.IHistoryReportingService;
import com.abita.services.historyreporting.exceptions.HistoryReportingServiceException;
import com.abita.services.jobs.gcp.constants.GcpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.math.BigDecimal;
import java.util.List;

/**
 * Permet d'écrire en base de donnée la somme des montants d'une pièce comptable YL-ZN
 * @author
 *
 */
public class GCPYlZnReadAndSendWriter implements ItemWriter<BigDecimal> {

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(GCPYlZnReadAndSendWriter.class);

  /** Service d'historisation du montant total des pièces comptables */
  private IHistoryReportingService historyReportingService;

  @Override
  @SuppressWarnings("unchecked")
  public void write(List<? extends BigDecimal> items) throws HistoryReportingServiceException {
    LOGGER.info("File YL-ZN - Writing ...");
    try {
      historyReportingService.create(GcpConstants.TYPE_PIECE_YL_ZN, (List<BigDecimal>) items);
    } catch (HistoryReportingServiceException e) {
      LOGGER.error("Erreur lors de l'appel du service d'historisation des montants des pièces comptables");
      throw new HistoryReportingServiceException(e);
    }
  }

  /**
   * @param historyReportingService the historyReportingService to set
   */
  public void setHistoryReportingService(IHistoryReportingService historyReportingService) {
    this.historyReportingService = historyReportingService;
  }
}
