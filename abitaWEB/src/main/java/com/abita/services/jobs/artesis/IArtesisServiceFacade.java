/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.artesis;

import com.abita.dao.batch.artesis.ArtesisBenefitsBlock;
import com.abita.dao.batch.artesis.ArtesisRetainedSalaryBlock;
import com.abita.services.jobs.artesis.exceptions.ArtesisServiceException;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Interface du service façade des jobs pour Artesis
 */
public interface IArtesisServiceFacade {

  /**
   * Permet de générer les lignes à insérer dans le fichier de retenue sur paie
   * @param generationDate la date de génération
   * @return les lignes à insérer dans le fichier
   * @throws ArtesisServiceException une ArtesisServiceException
   */
  List<ArtesisRetainedSalaryBlock> getRetainedSalaryBlocks(LocalDate generationDate) throws ArtesisServiceException;

  /**
   * Permet de générer les lignes à insérer dans le fichier des avantages en nature
   * @param generationDate la date de génération
   * @return les lignes à insérer dans le fichier
   * @throws ArtesisServiceException une ArtesisServiceException
   */
  List<ArtesisBenefitsBlock> getBenefitsBlocks(LocalDate generationDate) throws ArtesisServiceException;
}
