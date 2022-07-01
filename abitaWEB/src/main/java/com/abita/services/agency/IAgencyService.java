package com.abita.services.agency;

import com.abita.dto.AgencyDTO;
import com.abita.dto.ExtendedUserDTO;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.dto.UserDTO;

import java.util.List;

/**
 * Interface du service des agences
 */
public interface IAgencyService {

  /**
   * Permet de récupérer la liste de toutes les agences accessible à un utilisateur avec l’information de si on peut les supprimer
   * @param user l’utilisateur
   * @return une liste de toutes les agences
   * @throws AgencyServiceException une SanitationServiceException
   */
  List<AgencyDTO> findAllAgencies(UserDTO user) throws AgencyServiceException;

  /**
   * Permet de récupérer la liste de toutes les agences avec l’information de si on peut les supprimer
   * @return une liste de toutes les agences
   * @throws AgencyServiceException une SanitationServiceException
   */
  List<AgencyDTO> findAllAgencies() throws AgencyServiceException;

  /**
   * Permet de créer une agence et de l’ajouter aux utilisateurs concernés
   *
   * @param agency        l'agence à créer
   * @param extendedUsers liste des utilisateurs ayant toutes les agences
   * @return l'identifiant de l’agence
   * @throws AgencyServiceException une SanitationServiceException
   */
  Long create(AgencyDTO agency, List<ExtendedUserDTO> extendedUsers) throws AgencyServiceException;

  /**
   * Permet de mettre à jour une agence
   *
   * @param agency une agence
   * @throws AgencyServiceException une SanitationServiceException
   */
  void update(AgencyDTO agency) throws AgencyServiceException;

  /**
   * Permet de récupérer une agence par son identifiant
   * @param id l'identifiant de l'agence
   * @return l'agence
   * @throws AgencyServiceException une SanitationServiceException
   */
  AgencyDTO get(Long id) throws AgencyServiceException;

  /**
   * Permet de Supprimer une agence
   *
   * @param id l’identifiant de l’agence à supprimer
   * @throws AgencyServiceException une SanitationServiceException
   */
  void delete(Long id) throws AgencyServiceException;

  /**
   * Permet de sauvegarder une liste d’agences
   * @param agencies une liste d’agences
   * @throws AgencyServiceException une SanitationServiceException
   */
  void save(List<AgencyDTO> agencies) throws AgencyServiceException;

}
