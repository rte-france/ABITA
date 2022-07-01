package com.services.user;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.dto.UserDTO;
import com.dto.UsersSearchDTO;
import com.services.user.exception.UserNotFoundException;
import com.services.user.exception.UserServiceException;

/**
 * Interface pour la gestion des utilisateurs
 *
 * @author
 */
public interface IUserService {
	/**
	 * Créer l'utilisateur et renvoie son identifiant
	 *
	 * @param user l'utilisateur à créer
	 *
	 * @return l'identifiant généré
	 *
	 * @throws UserServiceException Si une erreur survient
	 */
	Serializable create(final UserDTO user) throws UserServiceException;

	/**
	 * Renvoie l'utilisateur à partir de l'identifiant spécifié.
	 * Renvoie une {@link com.services.common.exception.NotFoundException} encapsulée si l'utilisateur n'est pas trouvé.
	 *
	 * @param id l'identifiant
	 *
	 * @return l'utilisateur si trouvé
	 *
	 * @throws UserServiceException Si une erreur survient
	 */
	UserDTO get(final Long id) throws UserServiceException;

	/**
	 * Met à jour l'utilisateur
	 *
	 * @param user l'utilisateur à mettre à jour
	 *
	 * @throws UserServiceException Si une erreur survient
	 */
	void update(final UserDTO user) throws UserServiceException;

	/**
	 * Supprime l'utilisateur
	 *
	 * @param userId l'identifiant de l'utilisateur à supprimer
	 *
	 * @throws UserServiceException Si une erreur survient
	 */
	void delete(final Long userId) throws UserServiceException;

	/**
	 * Désactive l'utilsateur
	 *
	 * @param user l'utilisateur à désactiver
	 *
	 * @throws UserServiceException Si une erreur survient
	 */
	void disable(final UserDTO user) throws UserServiceException;

	/**
	 * Renvoie l'utilisateur à partir de l'identifiant de connexion spécifié
	 *
	 *
     * @param login l'identifiant de connexion
     *
     * @return l'utilisateur si trouvé
	 *
	 * @throws UserServiceException Si une erreur survient
     * @throws UserNotFoundException Si une erreur survient
	 */
	UserDTO findByLogin(final String login) throws UserServiceException, UserNotFoundException;

	/**
	 * Renvoie la liste de tous les utilisateurs
	 *
	 * @return la liste des utilisateurs, une liste vide si aucun utilisateur n'est présent
	 *
	 * @throws UserServiceException Si une erreur survient
	 */
	List<UserDTO> find() throws UserServiceException;

	/**
	 * Renvoie le nombre d'utilisateurs n'ayant pas les identifiants de connexion spécifiés
	 *
	 * @param filteredLogins Les identifiants à exclure
	 *
	 * @return le nombre d'utilisateurs
	 *
	 * @throws UserServiceException Si une erreur survient
	 */
	int getCount(final List<String> filteredLogins) throws UserServiceException;

	/**
	 * Renvoie la liste des utilisateurs correspondant au filtre de recherche
	 *
	 * @param usersSearch l'objet contenant les paramètres de filtrage
	 *
	 * @return la liste des utilisateurs qui correspondent au filtre
	 *
	 * @throws UserServiceException Si une erreur survient
	 */
	List<UserDTO> find(UsersSearchDTO usersSearch) throws UserServiceException;

    /**
     * Met à jour le thème de l'utilisateur connecté
     *
     * @param currentUser l'utilisateur en cours à mettre à jour
     * @param themeName le nom du thème à sélectionner
     * @return l'utilisateur avec son thème mis à jour
     * @throws UserServiceException Si une erreur survient
     */
    UserDTO updateUserTheme(UserDTO currentUser, String themeName) throws UserServiceException;

}
