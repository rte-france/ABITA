/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.extendeduser;

import com.abita.dao.extendeduser.entity.ExtendedUser;
import com.abita.dao.extendeduser.exception.ExtendedUserDAOException;
import com.abita.dto.unpersist.ExtendedUserCriteriaDTO;
import com.dao.common.IAbstractDAO;
import com.dao.user.entity.User;

import java.util.List;

/**
 * Interface des DAO de l'administration des utilisateurs étendus
 *
 * @author
 */
public interface IExtendedUserDAO extends IAbstractDAO<ExtendedUser, ExtendedUserDAOException> {

  /**
   * Permet de récupérer une liste d'utilisateurs étendus en BDD répondant aux critères de recherche
   * @param criteria Bean contenant les critères de recherche
   * @return une liste d'utilisateurs étendus
   * @throws ExtendedUserDAOException une ExtendedUserDAOException
   */
  List<ExtendedUser> findByCriteria(ExtendedUserCriteriaDTO criteria) throws ExtendedUserDAOException;

  /**
   * Permet de récupérer la liste des gestionnaires de contrat tiers
   * @return la liste des gestionnaires de contrat tiers
   * @throws ExtendedUserDAOException l'exception
   */
  List<User> findUsersThirdPartyContractManager() throws ExtendedUserDAOException;

  /**
   * Permet de récupérer un utilisateur étendu
   * @param id identifiant de l’utilisateur
   * @return un utilisateur étendu
   * @throws ExtendedUserDAOException une ExtendedUserDAOException
   */
  ExtendedUser get(Long id) throws ExtendedUserDAOException;
}
