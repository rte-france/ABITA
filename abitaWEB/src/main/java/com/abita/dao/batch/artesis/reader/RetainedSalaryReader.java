/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.artesis.reader;

import com.abita.dao.batch.artesis.ArtesisRetainedSalaryBlock;
import com.abita.services.jobs.artesis.IArtesisServiceFacade;
import com.abita.services.jobs.artesis.exceptions.ArtesisServiceException;
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
 * Classe qui permet de lire les informations en base de données afin d'écrire le fichier ARTESIS Retenue sur paie
 */
public class RetainedSalaryReader implements ItemReader<ArtesisRetainedSalaryBlock>, ItemStreamReader<ArtesisRetainedSalaryBlock> {

  /** Service des jobs pour Artesis */
  private IArtesisServiceFacade artesisServiceFacade;

  /** Iterator qui permet d'avancer dans la liste de résultats */
  private Iterator<ArtesisRetainedSalaryBlock> artesisRetainedSalaryBlocks;

  /** Date de génération */
  private LocalDate generationDate;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(RetainedSalaryReader.class);

  @Override
  public ArtesisRetainedSalaryBlock read() {
    if (artesisRetainedSalaryBlocks != null && artesisRetainedSalaryBlocks.hasNext()) {
      return artesisRetainedSalaryBlocks.next();
    }
    return null;
  }

  // Methode d'initialisation du ItemReader
  @Override
  public void open(ExecutionContext executionContext) {
    try {
      LOGGER.debug("Récupération des données.");

      if (generationDate == null) {
        generationDate = new LocalDate();
      }

      List<ArtesisRetainedSalaryBlock> artesisRetainedSalaryBlockList = artesisServiceFacade.getRetainedSalaryBlocks(generationDate);
      if (artesisRetainedSalaryBlockList != null) {
        artesisRetainedSalaryBlocks = artesisRetainedSalaryBlockList.iterator();
      }
    } catch (ArtesisServiceException e) {
      LOGGER.debug("Erreur lors de la récupération des lignes de retenue sur salaire.");
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
   * @param artesisServiceFacade the artesisServiceFacade to set
   */
  public void setArtesisServiceFacade(IArtesisServiceFacade artesisServiceFacade) {
    this.artesisServiceFacade = artesisServiceFacade;
  }

  /**
   * @param generationDate the generationDate to set
   */
  public void setGenerationDate(LocalDate generationDate) {
    this.generationDate = generationDate;
  }

}
