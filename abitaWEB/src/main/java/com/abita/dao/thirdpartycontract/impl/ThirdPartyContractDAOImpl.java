/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.thirdpartycontract.impl;

import com.abita.dao.thirdpartycontract.constants.ThirdPartyContractConstants;
import com.abita.dao.thirdpartycontract.entity.ThirdPartyContractEntity;
import com.abita.dao.thirdpartycontract.exceptions.ThirdPartyContractDAOException;
import com.abita.util.dao.DAOUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dao.thirdpartycontract.IThirdPartyContractDAO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.ThirdPartyContractCriteriaDTO;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

/**
 * Classe d'implémentation des DAO des contrats tiers
 *
 * @author
 */
public class ThirdPartyContractDAOImpl extends AbstractGenericEntityDAO<ThirdPartyContractEntity, ThirdPartyContractDAOException> implements IThirdPartyContractDAO {

  /**
  * serialVersionUID
  */
  private static final long serialVersionUID = -1273053419314164231L;

  /** Nom du paramètre contenant une date de fin */
  private static final String END_DATE_PARAMETER = "endDate";

  @SuppressWarnings("unchecked")
  @Override
  public List<ThirdPartyContractEntity> findContractByHousing(long idHousing) throws ThirdPartyContractDAOException {
    Query query = getSession().getNamedQuery("findThirdPartyContractByHousing");
    query.setParameter(ThirdPartyContractConstants.ID_HOUSING, idHousing, Hibernate.LONG);
    return query.list();
  }

  @Override
  public String generateReference() throws ThirdPartyContractDAOException {
    Query query = getSession().getNamedQuery("generateReferenceThirdPartyContract");
    return query.uniqueResult().toString();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ThirdPartyContractEntity> findByCriteria(ThirdPartyContractCriteriaDTO criteria, HousingCriteriaDTO housingCriteria) throws ThirdPartyContractDAOException {
    try {
      Query query = findRequestTuner(criteria, housingCriteria);
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
   * Permet de construire dynamiquement la requête de recherche
   * @param criteria les critères de recherche des contrats tiers
   * @param housingCriteria critères de recherche sur les informations des logements
   * @return la requête formatée
   */
  private Query findRequestTuner(ThirdPartyContractCriteriaDTO criteria, HousingCriteriaDTO housingCriteria) {
    String requete = buildRequete(criteria, housingCriteria);

    Query query = getSession().createQuery(requete.toString());
    buildQuery(criteria, housingCriteria, query);

    return query;
  }

  /**
   * Fonction permettant de construire la requête
   * @param criteria : les critères de la recherche
   * @param housingCriteria critères de recherche sur les informations des logements
   * @return la requête
   */
  private String buildRequete(ThirdPartyContractCriteriaDTO criteria, HousingCriteriaDTO housingCriteria) {
    // Construction dynamique de la requête de recherche
    StringBuilder requete = new StringBuilder();

    // Requete de recherche de tous les contrats tiers
    requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_WITHOUT_CRITERIA);

    if (null != criteria.getId()) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_REFERENCE);
    }

    DAOUtils.appendNotEmptyCriteria(requete, criteria.getReference(), ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_REAL_REFERENCE);

    if (null != criteria.getThirdParty()) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_THIRDPARTY_REFERENCE);
    }

    if (null != criteria.getHousing()) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_HOUSING_REFERENCE);
    }

    DAOUtils.appendNotEmptyCriteria(requete, criteria.getHousingReference(), ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_REAL_HOUSING_REFERENCE);

    if (null != criteria.getFieldOfActivity() && !criteria.getFieldOfActivity().getId().equals(0L)) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_FIELD_OF_ACTIVITY);
    }

    if (null != criteria.getCostCenter() && !criteria.getCostCenter().getId().equals(0L)) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_COST_CENTER);
    }

    DAOUtils.appendNotEmptyCriteria(requete, criteria.getContractPeriod(), ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_CONTRACT_PERIOD);

    DAOUtils.appendNotEmptyCriteria(requete, criteria.getNoticePeriod(), ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_NOTICE_PERIOD);

    if (null != criteria.getStartValidity()) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_START_VALIDITY);
    }

    if (null != criteria.getStartSupplierPaymentDate()) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_START_SUPPLIER_PAYMENT_DATE);
    }

    DAOUtils.appendNotEmptyCriteria(requete, criteria.getRentAmount(), ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_RENT_AMOUNT);

    DAOUtils.appendNotEmptyCriteria(requete, criteria.getExpectedChargeCost(), ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_EXPECTED_CHARGE_COST);

    DAOUtils.appendNotEmptyCriteria(requete, criteria.getGuaranteedDepositAmount(), ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_GUARANTEED_DEPOSIT_AMOUNT);

    DAOUtils.appendNotEmptyCriteria(requete, criteria.getRealEstateAgencyFee(), ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_REAL_ESTATE_AGENCY_FEE);

    if (null != criteria.getPaymentCycle() && !criteria.getPaymentCycle().getId().equals(0L)) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_PAYMENT_CYCLE);
    }

    DAOUtils.appendNotEmptyCriteria(requete, criteria.getSporadicallyInvoicing(), ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_SPORADICALLY_INVOICING);

    if (null != criteria.getCancellationDate()) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_CANCELLATION_DATE);
    }

    if (null != criteria.getThirdPartyTermination() && !criteria.getThirdPartyTermination().getId().equals(0L)) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_THIRD_PARTY_TERMINATION);
    }

    if (null != criteria.isGuaranteedDepositRefund()) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_GUARANTEED_DEPOSIT_REFUND);
    }

    if (null != criteria.getExpiryDate()) {
      requete.append(ThirdPartyContractConstants.SEARCH_THIRDPARTY_CONTRACT_BY_EXPIRY_DATE);
    }

    if (null != housingCriteria.getAgency()) {
      requete.append(ThirdPartyContractConstants.SEARCH_BY_AGENCY);
    }

    requete.append(ThirdPartyContractConstants.ORDER_BY_TENANT_REFERENCE);

    return requete.toString();
  }

  /**
   * Permet d'associer les paramètres à la requête
   * @param criteria : les critères de la recherche
   * @param housingCriteria critères de recherche sur les informations des logements
   * @param query : la requête HQL
   */
  private void buildQuery(ThirdPartyContractCriteriaDTO criteria, HousingCriteriaDTO housingCriteria, Query query) {
    setLongParameter(criteria, housingCriteria, query);
    setStringParameter(criteria, query);
    setDateParameter(criteria, query);
    setBooleanParameter(criteria, query);
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des valeurs de type Long
   * @param criteria critères de recherche des contrats tiers
   * @param housingCriteria critères de recherche sur les informations des logements
   * @param query requête HQL
   */
  private void setLongParameter(ThirdPartyContractCriteriaDTO criteria, HousingCriteriaDTO housingCriteria, Query query) {
    if (null != criteria.getId()) {
      query.setParameter("id", criteria.getId(), Hibernate.LONG);
    }

    if (null != criteria.getThirdParty()) {
      query.setParameter("thirdPartyId", criteria.getThirdParty(), Hibernate.LONG);
    }

    if (null != criteria.getHousing()) {
      query.setParameter("housingId", criteria.getHousing(), Hibernate.LONG);
    }

    if (null != criteria.getFieldOfActivity() && !criteria.getFieldOfActivity().getId().equals(0L)) {
      query.setParameter("fieldOfActivityId", criteria.getFieldOfActivity().getId(), Hibernate.LONG);
    }

    if (null != criteria.getCostCenter() && !criteria.getCostCenter().getId().equals(0L)) {
      query.setParameter("costCenterId", criteria.getCostCenter().getId(), Hibernate.LONG);
    }

    if (null != criteria.getPaymentCycle() && !criteria.getPaymentCycle().getId().equals(0L)) {
      query.setParameter("paymentCycleId", criteria.getPaymentCycle().getId(), Hibernate.LONG);
    }

    if (null != criteria.getThirdPartyTermination() && !criteria.getThirdPartyTermination().getId().equals(0L)) {
      query.setParameter("thirdPartyTerminationId", criteria.getThirdPartyTermination().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getAgency()) {
      query.setParameter("agency", housingCriteria.getAgency().getId(), Hibernate.LONG);
    }
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des valeurs de type String
   * @param criteria critères de recherche des contrats tiers
   * @param query requête HQL
   */
  private void setStringParameter(ThirdPartyContractCriteriaDTO criteria, Query query) {
    DAOUtils.setNotEmptyParameter(query, criteria.getReference(), "reference", criteria.getReference(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, criteria.getHousingReference(), "housingReference", criteria.getHousingReference(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, criteria.getContractPeriod(), "contractPeriod", criteria.getContractPeriod(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, criteria.getNoticePeriod(), "noticePeriod", criteria.getNoticePeriod(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, criteria.getRentAmount(), "rentAmount", criteria.getRentAmount(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, criteria.getExpectedChargeCost(), "expectedChargeCost", criteria.getExpectedChargeCost(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, criteria.getGuaranteedDepositAmount(), "guaranteedDepositAmount", criteria.getGuaranteedDepositAmount(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, criteria.getRealEstateAgencyFee(), "realEstateAgencyFee", criteria.getRealEstateAgencyFee(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, criteria.getSporadicallyInvoicing(), "sporadicallyInvoicing", criteria.getSporadicallyInvoicing(), Hibernate.STRING);
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des valeurs de type Date
   * @param criteria critères de recherche des contrats tiers
   * @param query requête HQL
   */
  private void setDateParameter(ThirdPartyContractCriteriaDTO criteria, Query query) {
    if (null != criteria.getStartValidity()) {
      query.setParameter("startValidity", criteria.getStartValidity(), Hibernate.DATE);
    }

    if (null != criteria.getStartSupplierPaymentDate()) {
      query.setParameter("startSupplierPaymentDate", criteria.getStartSupplierPaymentDate(), Hibernate.DATE);
    }

    if (null != criteria.getCancellationDate()) {
      query.setParameter("cancellationDate", criteria.getCancellationDate(), Hibernate.DATE);
    }
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des valeurs de type Boolean
   * @param criteria critères de recherche des contrats tiers
   * @param query requête HQL
   */
  private void setBooleanParameter(ThirdPartyContractCriteriaDTO criteria, Query query) {
    if (null != criteria.isGuaranteedDepositRefund()) {
      query.setParameter("guaranteedDepositRefund", criteria.isGuaranteedDepositRefund(), Hibernate.BOOLEAN);
    }

    if (null != criteria.getExpiryDate()) {
      query.setParameter(ThirdPartyContractConstants.EXPIRY_DATE, criteria.getExpiryDate(), Hibernate.BOOLEAN);
    }
  }

  @Override
  public ThirdPartyContractEntity findLastThirdPartyContractByHousing(Long id) throws ThirdPartyContractDAOException {
    try {
      Query query = getSession().getNamedQuery("findLastThirdPartyContractByHousing");
      query.setParameter(ThirdPartyContractConstants.ID_HOUSING, id, Hibernate.LONG);
      return (ThirdPartyContractEntity) query.setMaxResults(1).uniqueResult();
    } catch (HibernateException e) {
      log.info("Erreur Hibernate " + id, e);
      throw createException(e);
    } catch (Exception e) {
      log.error("Erreur inatendue " + id, e);
      throw createException(e);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ThirdPartyContractEntity> findThirdPartyContractPerCycle(LocalDate startDate, LocalDate endDate, String paymentCycle, Integer expiryDate)
    throws ThirdPartyContractDAOException {
    Date startDateTime = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(startDate).toDate();
    Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(endDate).toDate();

    Query query = getSession().getNamedQuery("findThirdPartyContractPerCycle");
    query.setParameter("startDate", startDateTime, Hibernate.DATE);
    query.setParameter(END_DATE_PARAMETER, endDateTime, Hibernate.DATE);
    query.setParameter("paymentCycleLabel", paymentCycle, Hibernate.STRING);
    query.setParameter(ThirdPartyContractConstants.EXPIRY_DATE, expiryDate, Hibernate.INTEGER);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ThirdPartyContractEntity> findThirdPartyContractInProgressOrClose() throws ThirdPartyContractDAOException {
    LocalDate now = new LocalDate();
    Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toDate();

    Query query = getSession().getNamedQuery("findThirdPartyContractInProgressOrClose");
    query.setParameter(END_DATE_PARAMETER, endDateTime, Hibernate.DATE);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ThirdPartyContractEntity> findThirdPartyContractToRegularize() throws ThirdPartyContractDAOException {
    Query query = getSession().getNamedQuery("findThirdPartyContractToRegularize");
    return query.list();
  }

  @Override
  public void resetThirdPartyContractToRegularize() throws ThirdPartyContractDAOException {
    Query query = getSession().getNamedQuery("resetThirdPartyContractToRegularize");
    query.executeUpdate();
  }

  @Override
  public void updateSporadicallyInvoicingThirdPartyContract() throws ThirdPartyContractDAOException {
    Query query = getSession().getNamedQuery("updateSporadicallyInvoicingThirdPartyContract");
    query.executeUpdate();
  }

  @Override
  public ThirdPartyContractEntity findByReference(String reference) throws ThirdPartyContractDAOException {
    Query query = getSession().getNamedQuery("findByReference");
    query.setParameter(ThirdPartyContractConstants.TPC_REFERENCE, reference, Hibernate.STRING);
    return (ThirdPartyContractEntity) query.uniqueResult();
  }

}
