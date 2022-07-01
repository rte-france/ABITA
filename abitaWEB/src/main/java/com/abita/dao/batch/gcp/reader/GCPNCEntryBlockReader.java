package com.abita.dao.batch.gcp.reader;

import com.abita.dao.batch.gcp.GCPEntryBlock;
import com.abita.services.jobs.gcp.IGcpTenantServiceFacade;
import com.abita.services.jobs.gcp.exceptions.GcpServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.util.Iterator;
import java.util.List;

/**
 * Permet de lire les informations afin d’écrire la pièce comptable NC
 */
public class GCPNCEntryBlockReader implements ItemReader<GCPEntryBlock>, ItemStreamReader<GCPEntryBlock> {

  /** Service des jobs pour GCP des occupants */
  private IGcpTenantServiceFacade gcpTenantServiceFacade;

  /** Iterator des blocks */
  private Iterator<GCPEntryBlock> gcpEntryBlocks;

  /** Date de génération */
  private LocalDate generationDate;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(GCPNCEntryBlockReader.class);

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
      List<GCPEntryBlock> gcpEntryBlockList = gcpTenantServiceFacade.getNCEntryBlock(generationDate);
      gcpEntryBlockList.addAll(gcpTenantServiceFacade.getNCRegularizationEntryBlock(generationDate));
      gcpEntryBlocks = gcpEntryBlockList.iterator();
    } catch (GcpServiceException e) {
      LOGGER.debug("Erreur lors de la récupération des blocks de lignes pour la pièce comptable NC");
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
   * @param gcpTenantServiceFacade the gcpTenantServiceFacade to set
   */
  public void setGcpTenantServiceFacade(IGcpTenantServiceFacade gcpTenantServiceFacade) {
    this.gcpTenantServiceFacade = gcpTenantServiceFacade;
  }

  /**
   * @param generationDate the generationDate to set
   */
  public void setGenerationDate(LocalDate generationDate) {
    this.generationDate = generationDate;
  }

}
