package com.abita.dao.batch.gcp.reader;

import com.abita.dao.batch.gcp.GCPEntryBlock;
import com.abita.services.jobs.gcp.IGcpThirdPartyServiceFacade;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.services.jobs.gcp.exceptions.GcpServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.util.Iterator;
import java.util.List;

/**
 * Permet de lire les informations afin d’écrire la pièce comptable ZN
 */
public class GCPZNEntryBlockReader implements ItemStreamReader<GCPEntryBlock> {

  /** Service des jobs pour GCP des tiers */
  private IGcpThirdPartyServiceFacade gcpThirdPartyServiceFacade;

  /** Iterator des blocks */
  private Iterator<GCPEntryBlock> gcpEntryBlocks;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(GCPZNEntryBlockReader.class);

  /** Date de génération */
  private LocalDate generationDate;

  @Override
  public GCPEntryBlock read() {
    if (gcpEntryBlocks != null && gcpEntryBlocks.hasNext()) {
      return gcpEntryBlocks.next();
    }
    return null;
  }

  @Override
  public void open(ExecutionContext executionContext) {
    LOGGER.debug("Récupération des données.");
    if (generationDate == null) {
      generationDate = new LocalDate();
    }
    try {
      List<GCPEntryBlock> gcpEntryBlockList = gcpThirdPartyServiceFacade.getZNEntryBlock(generationDate, DateTimeUtils.MONTHLY_CYCLE);
      if (generationDate.getMonthOfYear() % DateTimeUtils.MONTHS_IN_A_QUARTER == 0) {
        gcpEntryBlockList.addAll(gcpThirdPartyServiceFacade.getZNEntryBlock(generationDate, DateTimeUtils.QUARTERLY_CYCLE));
      }
      if (generationDate.getMonthOfYear() % DateTimeUtils.MONTHS_IN_A_YEAR == 0) {
        gcpEntryBlockList.addAll(gcpThirdPartyServiceFacade.getZNEntryBlock(generationDate, DateTimeUtils.ANNUAL_CYCLE));
      }
      if (gcpEntryBlockList != null) {
        gcpEntryBlocks = gcpEntryBlockList.iterator();
      }
    } catch (GcpServiceException e) {
      LOGGER.debug("Erreur lors de la récupération des blocks de lignes pour la pièce comptable ZN");
      throw new ItemStreamException(e);
    }
  }

  @Override
  public void update(ExecutionContext executionContext) {
    LOGGER.debug("Mise à jour des données.");

  }

  @Override
  public void close() {
    LOGGER.debug("Fermeture du lecteur.");

  }

  /**
   * @param gcpThirdPartyServiceFacade the gcpThirdPartyServiceFacade to set
   */
  public void setGcpThirdPartyServiceFacade(IGcpThirdPartyServiceFacade gcpThirdPartyServiceFacade) {
    this.gcpThirdPartyServiceFacade = gcpThirdPartyServiceFacade;
  }

  /**
   * @param generationDate the generationDate to set
   */
  public void setGenerationDate(LocalDate generationDate) {
    this.generationDate = generationDate;
  }

}
