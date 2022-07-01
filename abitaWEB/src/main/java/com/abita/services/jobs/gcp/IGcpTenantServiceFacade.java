package com.abita.services.jobs.gcp;

import com.abita.dao.batch.gcp.GCPEntryBlock;
import com.abita.services.jobs.gcp.exceptions.GcpServiceException;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Interface du service façade des traitements GCP des occupants
 */
public interface IGcpTenantServiceFacade {

  /**
   * Permet de générer le bloc de ligne à insérer dans un fichier NT
   * @param generationDate la date de génération
   * @return un bloc de ligne à insérer dans un fichier NT
   * @throws GcpServiceException une GcpServiceException
   */
  List<GCPEntryBlock> getNTEntryBlock(LocalDate generationDate) throws GcpServiceException;

  /**
   * Permet de générer le bloc de ligne à insérer dans un fichier NC
   * @param generationDate la date de génération
   * @return un bloc de ligne à insérer dans un fichier NC
   * @throws GcpServiceException une GcpServiceException
   */
  List<GCPEntryBlock> getNCEntryBlock(LocalDate generationDate) throws GcpServiceException;

  /**
   * Permet de générer le bloc de ligne à insérer dans un fichier NC de rétroactivité
   * @param generationDate la date de génération
   * @return un bloc de ligne à insérer dans un fichier NC
   * @throws GcpServiceException une GcpServiceException
   */
  List<GCPEntryBlock> getNCRegularizationEntryBlock(LocalDate generationDate) throws GcpServiceException;
}
