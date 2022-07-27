/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.thirdparty.impl;

import com.abita.dao.thirdparty.constants.ThirdPartyConstants;
import com.abita.dao.thirdparty.exceptions.ThirdPartyDAOException;
import com.abita.util.dao.DAOUtils;
import com.abita.dao.thirdparty.IThirdPartyDAO;
import com.abita.dao.thirdparty.entity.ThirdPartyEntity;
import com.abita.dto.unpersist.ThirdPartyCriteriaDTO;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.List;

/**
 * Classe d'implémentation des DAO des tiers
 * @author
 *
 */
public class ThirdPartyDAOImpl extends AbstractGenericEntityDAO<ThirdPartyEntity, ThirdPartyDAOException> implements IThirdPartyDAO {

  /**
  * serialVersionUID
  */
  private static final long serialVersionUID = -1433109461905493605L;

  /**
   * Named query permettant de ramener un tiers par la référence GCP
   */
  private static final String FIND_THIRDPARTY_BY_GCPREF = "findThirdPartyByGCPRef";

  /**
   * Named query permettant de ramener un tiers par son ID
   */
  private static final String IS_REMOVABLE = "isRemovable";

  @Override
  public boolean isThirdPartyExist(String gcpReference) throws ThirdPartyDAOException {
    Query query = getSession().getNamedQuery(FIND_THIRDPARTY_BY_GCPREF);
    query.setParameter(ThirdPartyConstants.GCPREF, gcpReference, Hibernate.STRING);
    return !query.list().isEmpty();
  }

  @Override
  public boolean isRemovable(long idThirdParty) throws ThirdPartyDAOException {
    Query query = getSession().getNamedQuery(IS_REMOVABLE);
    query.setParameter(ThirdPartyConstants.ID_THIRD_PARTY, idThirdParty, Hibernate.LONG);
    return query.list().isEmpty();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ThirdPartyEntity> findByCriteria(ThirdPartyCriteriaDTO criteria) throws ThirdPartyDAOException {
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

  /**
   * Permet de construire dynaiquement la requête de recherche
   * @param criteria les critères de recherche des tiers
   * @return la requête formatée
   */
  private Query findRequestTuner(ThirdPartyCriteriaDTO criteria) {

    String requete = generateQuery(criteria);

    Query query;
    query = getSession().createQuery(requete.toString());

    parameterizeQuery(criteria, query);

    return query;
  }

  /**
   * Construit la requête dynamiquement en fonction des critères fournis
   * @param criteria Les critères
   * @return la requête générée
   */
  private String generateQuery(ThirdPartyCriteriaDTO criteria) {
    // Construction dynamique de la requête de recherche
    StringBuilder requete = new StringBuilder();

    // Requete de recherche de tous les tiers
    requete.append(ThirdPartyConstants.SEARCH_THIRD_PARTY_WITHOUT_CRITERIA);

    // Critère de recherche par la référence (id)
    if (null != criteria.getId()) {
      requete.append(ThirdPartyConstants.SEARCH_THIRD_PARTY_BY_REFERENCE);
    }

    // Critère de recherche par la référence GCP
    if (!StringUtils.isEmpty(criteria.getGcpReference())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("tp.gcpReference", ":gcpReference"));
    }

    if (!StringUtils.isEmpty(criteria.getName())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("tp.name", ":name"));
    }

    if (!StringUtils.isEmpty(criteria.getAddress())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("tp.address", ":address"));
    }

    if (!StringUtils.isEmpty(criteria.getPostalCode())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("tp.postalCode", ":postalCode"));
    }

    if (!StringUtils.isEmpty(criteria.getCity())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("tp.city", ":city"));
    }

    if (!StringUtils.isEmpty(criteria.getPhone())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("tp.phone", ":phone"));
    }

    if (!StringUtils.isEmpty(criteria.getMailAddress())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("tp.mailAddress", ":mailAddress"));
    }

    if (!StringUtils.isEmpty(criteria.getBeneficiaryName())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("tp.beneficiaryName", ":beneficiaryName"));
    }

    if (!StringUtils.isEmpty(criteria.getBeneficiaryAddress())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("tp.beneficiaryAddress", ":beneficiaryAddress"));
    }

    requete.append(ThirdPartyConstants.ORDER_BY_THIRD_PARTY_REFERENCE);

    return requete.toString();
  }

  /**
   * Injecte les paramètres de requête provenant des critères sélectionnés
   * @param criteria les critères
   * @param query la requête paramétrée
   */
  private void parameterizeQuery(ThirdPartyCriteriaDTO criteria, Query query) {
    // Critère de recherche par la référence (id)
    if (null != criteria.getId()) {
      query.setParameter("id", criteria.getId(), Hibernate.LONG);
    }

    // Ajout des paramètres sur chaque critère retenu
    if (!StringUtils.isEmpty(criteria.getGcpReference())) {
      query.setParameter("gcpReference", "%" + criteria.getGcpReference() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(criteria.getName())) {
      query.setParameter("name", "%" + criteria.getName() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(criteria.getAddress())) {
      query.setParameter("address", "%" + criteria.getAddress() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(criteria.getPostalCode())) {
      query.setParameter("postalCode", "%" + criteria.getPostalCode() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(criteria.getCity())) {
      query.setParameter("city", "%" + criteria.getCity() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(criteria.getPhone())) {
      query.setParameter("phone", "%" + criteria.getPhone() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(criteria.getMailAddress())) {
      query.setParameter("mailAddress", "%" + criteria.getMailAddress() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(criteria.getBeneficiaryName())) {
      query.setParameter("beneficiaryName", "%" + criteria.getBeneficiaryName() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(criteria.getBeneficiaryAddress())) {
      query.setParameter("beneficiaryAddress", "%" + criteria.getBeneficiaryAddress() + "%", Hibernate.STRING);
    }
  }

  @Override
  public ThirdPartyEntity getByGCPRef(String thirdPartyToOpenId) throws ThirdPartyDAOException {
    Query query = getSession().getNamedQuery(FIND_THIRDPARTY_BY_GCPREF);
    query.setParameter(ThirdPartyConstants.GCPREF, thirdPartyToOpenId, Hibernate.STRING);
    return (ThirdPartyEntity) query.uniqueResult();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ThirdPartyEntity> findThirdPartyByHousing(long idHousing) throws ThirdPartyDAOException {
    Query query = getSession().getNamedQuery("findThirdPartyByHousing");
    query.setParameter(ThirdPartyConstants.ID_HOUSING, idHousing, Hibernate.LONG);
    return query.list();
  }
}
