package com.abita.services.jobs.gcp;

import com.abita.dao.batch.gcp.GCPEntryBlock;
import com.abita.services.jobs.gcp.exceptions.GcpServiceException;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Interface du service façade des traitements GCP des tiers
 */
public interface IGcpThirdPartyServiceFacade {

  /**
   * Permet de générer le bloc de ligne à insérer dans un fichier YL
   * @param generationDate la date de génération
   * @param paymentCycle le cycle de paiement
   * @return un bloc de ligne à insérer dans un fichier YL
   * @throws GcpServiceException une GcpServiceException
   */
  List<GCPEntryBlock> getYLEntryBlock(LocalDate generationDate, String paymentCycle) throws GcpServiceException;

  /**
   * Permet de générer le bloc de ligne à insérer dans un fichier YL de charge ponctuelle
   * @param generationDate la date de génération
   * @return un bloc de ligne à insérer dans un fichier YL
   * @throws GcpServiceException une GcpServiceException
   */
  List<GCPEntryBlock> getYLSporadicallyInvoicingEntryBlock(LocalDate generationDate) throws GcpServiceException;

  /**
   * Permet de générer le bloc de ligne à insérer dans un fichier YL de régularisation
   * @param generationDate la date de génération
   * @return un bloc de ligne à insérer dans un fichier YL
   * @throws GcpServiceException une GcpServiceException
   */
  List<GCPEntryBlock> getYLRegularizationBlock(LocalDate generationDate) throws GcpServiceException;

  /**
   * Permet de générer le bloc de ligne à insérer dans un fichier YL de révision
   * @param generationDate la date de génération
   * @return un bloc de ligne à insérer dans un fichier YL
   * @throws GcpServiceException une GcpServiceException
   */
  List<GCPEntryBlock> getYLRevisionBlock(LocalDate generationDate) throws GcpServiceException;

  /**
   * Permet de générer le bloc de ligne à insérer dans un fichier ZN
   * @param generationDate la date de génération
   * @param paymentCycle le cycle de paiement
   * @return un bloc de ligne à insérer dans un fichier ZN
   * @throws GcpServiceException une GcpServiceException
   */
  List<GCPEntryBlock> getZNEntryBlock(LocalDate generationDate, String paymentCycle) throws GcpServiceException;

}
