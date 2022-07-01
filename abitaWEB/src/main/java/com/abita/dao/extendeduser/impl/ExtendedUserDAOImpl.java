package com.abita.dao.extendeduser.impl;

import com.abita.util.dao.DAOUtils;
import com.abita.dao.extendeduser.IExtendedUserDAO;
import com.abita.dao.extendeduser.constants.ExtendedUserConstants;
import com.abita.dao.extendeduser.entity.ExtendedUser;
import com.abita.dao.extendeduser.exception.ExtendedUserDAOException;
import com.abita.dto.unpersist.ExtendedUserCriteriaDTO;
import com.dao.common.impl.AbstractGenericEntityDAO;
import com.dao.user.entity.User;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.List;

/**
 * Classe d'implémentation des utilisateurs étendus
 *
 * @author
 */
public class ExtendedUserDAOImpl extends AbstractGenericEntityDAO<ExtendedUser, ExtendedUserDAOException> implements IExtendedUserDAO {

  /** SerialID */
  private static final long serialVersionUID = -3066309064477741655L;

  @Override
  public List<User> findUsersThirdPartyContractManager() throws ExtendedUserDAOException {
    List<User> lstEntity = null;

    try {
      Query query = getSession().getNamedQuery("findUsersThirdPartyContractManager");
      lstEntity = query.list();
    } catch (HibernateException ex) {
      throw new ExtendedUserDAOException(ex.getMessage(), ex);
    }

    return lstEntity;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ExtendedUser> findByCriteria(ExtendedUserCriteriaDTO criteria) throws ExtendedUserDAOException {
    try {
      Query query = null;

      query = findRequestTuner(criteria);

      return query.list();

    } catch (HibernateException e) {
      log.info("Erreur Hibernate ", e);
      throw createException(e);
    } catch (Exception e) {
      log.error("Erreur inatendue ", e);
      throw createException(e);
    }
  }

  @Override
  public ExtendedUser get(Long id) throws ExtendedUserDAOException {
    Query query = getSession().getNamedQuery("findExtendedUserById");
    query.setParameter("id", id, Hibernate.LONG);
    return (ExtendedUser) query.uniqueResult();
  }

  /**
   * Permet de construire dynaiquement la requête de recherche
   * @param criteria les critères de recherche des tiers
   * @return la requête formatée
   */
  private Query findRequestTuner(ExtendedUserCriteriaDTO criteria) {

    String requete = generateQuery(criteria);

    Query query = getSession().createQuery(requete);

    parameterizeQuery(criteria, query);

    return query;
  }

  /**
   * Construit la requête dynamiquement en fonction des critères fournis
   * @param criteria Les critères
   * @return la requête générée
   */
  private String generateQuery(ExtendedUserCriteriaDTO criteria) {
    // Construction dynamique de la requête de recherche
    StringBuilder requete = new StringBuilder();

    // Requete de recherche de tous les tiers
    requete.append(ExtendedUserConstants.SEARCH_USER_WITHOUT_CRITERIA);

    if (!StringUtils.isEmpty(criteria.getLogin())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("eu.login", ":login"));
    }

    if (!StringUtils.isEmpty(criteria.getFirstName())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("eu.firstName", ":firstName"));
    }

    if (!StringUtils.isEmpty(criteria.getLastName())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("eu.lastName", ":lastName"));
    }

    if (!StringUtils.isEmpty(criteria.getEmail())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("eu.email", ":email"));
    }

    if (criteria.isActivated() != null) {
      requete.append(ExtendedUserConstants.SEARCH_USER_BY_ACTIVATION_STATUS);
    }

    if (criteria.isThirdPartyContractManager() != null) {
      requete.append(ExtendedUserConstants.SEARCH_USER_BY_TPC_MANAGER_STATUS);
    }

    if (criteria.getGroups() != null && !criteria.getGroups().isEmpty()) {
      requete.append(ExtendedUserConstants.SEARCH_USER_BY_GROUPS_START_CLAUSE);
      // Les groupes sont actuellement stocké sous la forme d'une concaténation de nom de groupe séparé par des virgules
      // Pour pouvoir trouver un utilisateur appartenant a un des groupes recherchés, il faut que le nom de ce groupe soit trouvé dans la
      // chaine
      // On rajoute donc autant d'opérateur de recherche "LIKE" qu'il y a de groupe différent à rechercher
      // Il n'y a pas moyen de chercher pour chaque groupe différent dans la même chaine sans expression régulière, et celles-ci ne sont pas
      // autorisées en HQL pour Oracle
      // Cette boucle n'est probablement pas très performante, mais avec l'architecture actuelle, c'est un mal nécessaire,
      // et acceptable (en Juin 2014, il n'y a que 7 groupes possibles)
      for (int i = 0; i < criteria.getGroups().size(); i++) {
        requete.append(ExtendedUserConstants.SEARCH_USER_BY_GROUPS_OR + i);
      }
      requete.append(ExtendedUserConstants.SEARCH_USER_BY_GROUPS_END_CLAUSE);
    }

    if (criteria.getAgency() != null) {
      requete.append(ExtendedUserConstants.SEARCH_USER_BY_AGENCY);
    }

    return requete.toString();
  }

  /**
   * Injecte les paramètres de requête provenant des critères sélectionnés
   * @param criteria les critères
   * @param query la requête paramétrée
   */
  private void parameterizeQuery(ExtendedUserCriteriaDTO criteria, Query query) {
    if (!StringUtils.isEmpty(criteria.getLogin())) {
      query.setParameter("login", "%" + criteria.getLogin() + "%", Hibernate.STRING);
    }

    // Ajout des paramètres sur chaque critère retenu
    if (!StringUtils.isEmpty(criteria.getFirstName())) {
      query.setParameter("firstName", "%" + criteria.getFirstName() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(criteria.getLastName())) {
      query.setParameter("lastName", "%" + criteria.getLastName() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(criteria.getEmail())) {
      query.setParameter("email", "%" + criteria.getEmail() + "%", Hibernate.STRING);
    }

    if (criteria.isActivated() != null) {
      query.setParameter("activated", criteria.isActivated(), Hibernate.BOOLEAN);
    }

    if (criteria.isThirdPartyContractManager() != null) {
      query.setParameter("isThirdPartyContractManager", criteria.isThirdPartyContractManager(), Hibernate.YES_NO);
    }

    if (criteria.getGroups() != null && !criteria.getGroups().isEmpty()) {
      // Les groupes sont actuellement stocké sous la forme d'une concaténation de nom de groupe séparé par des virgules
      // Pour pouvoir trouver un utilisateur appartenant a un des groupes recherchés, il faut que le nom de ce groupe soit trouvé dans la
      // chaine
      // On rajoute donc autant d'opérateur de recherche "LIKE" qu'il y a de groupe différent à rechercher
      // Il n'y a pas moyen de chercher pour chaque groupe différent dans la même chaine sans expression régulière, et celles-ci ne sont pas
      // autorisées en HQL pour Oracle
      // Cette boucle n'est probablement pas très performante, mais avec l'architecture actuelle, c'est un mal nécessaire,
      // et acceptable (en Juin 2014, il n'y a que 7 groupes possibles)
      for (int i = 0; i < criteria.getGroups().size(); i++) {
        query.setParameter("group" + i, "%" + criteria.getGroups().get(i) + "%", Hibernate.STRING);
      }
    }

    if (criteria.getAgency() != null) {
      query.setParameter("idAgency", criteria.getAgency().getId(), Hibernate.LONG);
    }
  }
}
