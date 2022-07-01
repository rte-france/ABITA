package com.abita.services.termination;

import com.abita.dto.TerminationDTO;
import com.abita.services.termination.exceptions.TerminationServiceException;

import java.util.List;

/**
 * Interface du service des motifs de résiliation
 *
 * @author
 */
public interface ITerminationService {

  /**
   * Permet de récupérer la liste de tous les motifs de résiliation
   *
   * @return une liste de tous les motifs de résiliation
   *
   * @throws TerminationServiceException une TerminationServiceException
   */
  List<TerminationDTO> find() throws TerminationServiceException;

  /**
   * Permet de créer un nouveau motif de résiliation  en BDD
   *
   * @param terminationDTO un motif de résiliation
   * @return l'identifiant du motif de résiliation
   * @throws TerminationServiceException une TerminationServiceException
   */
  Long create(TerminationDTO terminationDTO) throws TerminationServiceException;

  /**
   * Met à jour un motif de résiliation  en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param terminationDTO motif de résiliation
   * @throws TerminationServiceException une TerminationServiceException
   */
  void update(TerminationDTO terminationDTO) throws TerminationServiceException;

  /**
   * Supprime un motif de résiliation  en BDD
   *
   * @param id l'identifiant du motif de résiliation  à supprimer
   * @throws TerminationServiceException une TerminationServiceException
   */
  void delete(Long id) throws TerminationServiceException;

}
