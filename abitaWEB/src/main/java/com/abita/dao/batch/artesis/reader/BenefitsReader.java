/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.artesis.reader;

import com.abita.dao.batch.artesis.ArtesisBenefitsBlock;
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
 * Permet la lecture des contrats occupant en base de données qui figureront dans le fichier avantages en nature
 */
public class BenefitsReader implements ItemReader<ArtesisBenefitsBlock>, ItemStreamReader<ArtesisBenefitsBlock> {

  /** Service des jobs pour Artesis */
  private IArtesisServiceFacade artesisServiceFacade;

  /** Iterator qui permet d'avancer dans la liste de résultats */
  private Iterator<ArtesisBenefitsBlock> artesisbenefitsBlocks;

  /** Date de génération */
  private LocalDate generationDate;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(BenefitsReader.class);

  @Override
  public ArtesisBenefitsBlock read() {
    if (artesisbenefitsBlocks != null && artesisbenefitsBlocks.hasNext()) {
      return artesisbenefitsBlocks.next();
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

      List<ArtesisBenefitsBlock> artesisBenefitsBlockList = artesisServiceFacade.getBenefitsBlocks(generationDate);
      if (artesisBenefitsBlockList != null) {
        artesisbenefitsBlocks = artesisBenefitsBlockList.iterator();
      }
    } catch (ArtesisServiceException e) {
      LOGGER.debug("Erreur lors de la récupération des lignes d'avantage en nature.");
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
