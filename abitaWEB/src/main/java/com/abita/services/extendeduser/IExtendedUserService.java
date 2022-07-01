package com.abita.services.extendeduser;

import com.abita.dto.ExtendedUserDTO;
import com.abita.dto.unpersist.ExtendedUserCriteriaDTO;
import com.abita.services.extendeduser.exception.ExtendedUserServiceException;
import com.dto.UserDTO;

import java.util.List;

/**
 * Interface du service des utilisateurs étendues
 *
 * @author
 */
public interface IExtendedUserService {

  /**
   * Permet de récupérer la liste de tous les utilisateurs étendus en BDD
   *
   * @return une liste de tous les utilisateurs étendus
   * @throws ExtendedUserServiceException une ExtendedUserServiceException
   */
  List<ExtendedUserDTO> find() throws ExtendedUserServiceException;

  /**
   * Permet de créer un nouvel utilisateur étendu en BDD
   *
   * @param entity un utilisateur étendu
   * @return l'identifiant du utilisateur étendu
   * @throws ExtendedUserServiceException une ExtendedUserServiceException
   */
  Long create(ExtendedUserDTO entity) throws ExtendedUserServiceException;

  /**
   * Met à jour un utilisateur étendu en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param entity un utilisateur étendu
   * @throws ExtendedUserServiceException une ExtendedUserServiceException
   */
  void update(ExtendedUserDTO entity) throws ExtendedUserServiceException;

  /**
   * Met à jour un utilisateur étendu en BDD avec un mapping dozer
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param entity un utilisateur étendu
   * @param mapId l'id d'un mapping dozer a utiliser
   * @throws ExtendedUserServiceException une ExtendedUserServiceException
   */
  void update(ExtendedUserDTO entity, String mapId) throws ExtendedUserServiceException;

  /**
   * Supprime un utilisateur étendu en BDD
   *
   * @param id l'identifiant de l'utilisateur étendu
   * @throws ExtendedUserServiceException une ExtendedUserServiceException
   */
  void delete(Long id) throws ExtendedUserServiceException;

  /**
   * Permet de récupérer une liste d'utilisateurs étendus en BDD répondant aux critères de recherche
   * @param criteria Bean contenant les critères de recherche
   * @return une liste d'utilisateurs étendus
   * @throws ExtendedUserServiceException une ExtendedUserServiceException
   */
  List<ExtendedUserDTO> findByCriteria(ExtendedUserCriteriaDTO criteria) throws ExtendedUserServiceException;

  /**
   * Permet de récupérer la liste des gestionnaires de contrat tiers
   * @return la liste des gestionnaires de contrat tiers
   * @throws ExtendedUserServiceException l'exception
   */
  List<UserDTO> findUsersThirdPartyContractManager() throws ExtendedUserServiceException;

  /**
   * Permet de récupérer un utilisateur étendu
   * @param id identifiant de l’utilisateur
   * @return un utilisateur étendu
   * @throws ExtendedUserServiceException l'exception
   */
  ExtendedUserDTO get(Long id) throws ExtendedUserServiceException;

}
