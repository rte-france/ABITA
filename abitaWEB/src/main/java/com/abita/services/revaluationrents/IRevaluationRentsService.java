package com.abita.services.revaluationrents;

import com.abita.dto.RevaluationRentsDTO;
import com.abita.services.revaluationrents.exceptions.RevaluationRentsServiceException;

/**
 * Interface du service des revalorisations des loyers
 */
public interface IRevaluationRentsService {

  /**
   * Permet de récupérer une revalorisation de loyer par son identifiant en BDD
   * @param id l’identifiant de la revalorisation des loyers
   * @return Un RevaluationRentsDTO
   * @throws RevaluationRentsServiceException une RevaluationRentsServiceException
   */
  RevaluationRentsDTO get(Long id) throws RevaluationRentsServiceException;

  /**
   * Permet de mettre à jour une revalorisation de loyer en BDD.
   * @param revaluationRentsDTO une revalorisation de loyer
   * @throws RevaluationRentsServiceException une RevaluationRentsServiceException
   */
  void update(RevaluationRentsDTO revaluationRentsDTO) throws RevaluationRentsServiceException;
}
