package com.abita.services.sanitation;


import com.abita.dto.SanitationDTO;
import com.abita.services.sanitation.exceptions.SanitationServiceException;

import java.util.List;

/**
 * Interface du service des assainissements
 */
public interface ISanitationService {

  /**
   * Permet de récupérer la liste de tous les assainissements
   *
   * @return une liste de tous les assainissements
   *
   * @throws SanitationServiceException une SanitationServiceException
   */
  List<SanitationDTO> find() throws SanitationServiceException;

  /**
   * Permet de récupérer la liste de toutes les assainissement
   * @return une liste de toutes les assainissement
   * @throws SanitationServiceException une SanitationServiceException
   */
  List<SanitationDTO> findAllSanitations() throws SanitationServiceException;
}
