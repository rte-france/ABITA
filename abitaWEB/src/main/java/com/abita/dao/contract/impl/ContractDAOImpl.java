package com.abita.dao.contract.impl;

import com.abita.dao.contract.constants.ContractConstants;
import com.abita.dao.contract.exceptions.ContractDAOException;
import com.abita.util.dao.DAOUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dao.contract.IContractDAO;
import com.abita.dao.contract.entity.ContractEntity;
import com.abita.dto.unpersist.ContractCriteriaDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe d'implémentation des DAO des contrats occupant
 *
 * @author
 */
public class ContractDAOImpl extends AbstractGenericEntityDAO<ContractEntity, ContractDAOException> implements IContractDAO {

  /** Nom du paramètre pour startDate */
  private static final String START_DATE_PARAMETER = "startDate";

  /** Nom du paramètre pour endDate */
  private static final String END_DATE_PARAMETER = "endDate";

  /** Message utilisé lors d’une erreur innatendue */
  private static final String UNEXPECTED_ERROR_MESSAGE = "Erreur inattendue : ";

  @Override
  public Long create(ContractEntity contract) throws ContractDAOException {
    Long id = (Long) super.create(contract);
    getSession().flush();
    ContractEntity newContract = get(id);
    contract.setLopRent(newContract.getComputedLopRent());
    update(contract);
    return id;
  }

  /**
  * serialVersionUID
  */
  private static final long serialVersionUID = -1273053419314164231L;

  /** Texte en cas d’erreur Hibernate */
  private static final String HIBERNATE_ERROR = "Erreur Hibernate ";

  @Override
  public ContractEntity get(Serializable id) throws ContractDAOException {
    try {
      ContractEntity entity = (ContractEntity) getSession().get(getSpecificEntityClass(), id);
      getSession().refresh(entity);
      return entity;
    } catch (HibernateException hibernateException) {
      log.error(HIBERNATE_ERROR + hibernateException);
      throw new ContractDAOException(hibernateException);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ContractEntity> find() throws ContractDAOException {
    try {
      List<ContractEntity> listRetour = new ArrayList<ContractEntity>();
      Criteria crit = getSession().createCriteria(getSpecificEntityClass());
      crit.setCacheMode(CacheMode.REFRESH);
      @SuppressWarnings("unchecked")
      List<ContractEntity> list = crit.list();
      listRetour.addAll(list);
      return listRetour;
    } catch (HibernateException hibernateException) {
      log.error(HIBERNATE_ERROR + hibernateException);
      throw new ContractDAOException(hibernateException);
    } catch (Exception e) {
      log.error(UNEXPECTED_ERROR_MESSAGE + e);
      throw new ContractDAOException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ContractEntity update(ContractEntity o) throws ContractDAOException {
    try {
      // If another object with the same id exists in the session it is evicted to
      // avoid an Hibernate double object exception
      getSession().evict(super.get(o.getId()));
      getSession().update(o);
      // Some object field are calculated on database side.
      // We need to refresh the object but also be sure the query had already been executed
      getSession().flush();
      getSession().refresh(o);
      return o;
    } catch (HibernateException hibernateException) {
      log.error(HIBERNATE_ERROR + hibernateException);
      throw new ContractDAOException(hibernateException);
    } catch (Exception e) {
      log.error(UNEXPECTED_ERROR_MESSAGE + e);
      throw new ContractDAOException(e);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ContractEntity> findContractByTenant(long idTenant) throws ContractDAOException {
    Query query = getSession().getNamedQuery("findContractByTenant");
    query.setParameter(ContractConstants.ID_TENANT, idTenant, Hibernate.LONG);
    return query.list();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ContractEntity> findContractByHousing(long idHousing) throws ContractDAOException {
    Query query = getSession().getNamedQuery("findContractByHousing");
    query.setParameter(ContractConstants.ID_HOUSING, idHousing, Hibernate.LONG);
    return query.list();
  }

  @Override
  public Long unregisterDateAfterEndValidityDate(Long idHousing, Date unregisterDate) throws ContractDAOException {
    Query query = getSession().getNamedQuery("findConflictUnregisterDateBeforeEndValidity");
    query.setParameter(ContractConstants.ID_HOUSING, idHousing, Hibernate.LONG);
    query.setParameter(ContractConstants.UNREGISTER_DATE, unregisterDate, Hibernate.DATE);
    return (Long) query.uniqueResult();

  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ContractEntity> findContractsInProgressOfSalariedTenant() throws ContractDAOException {
    LocalDate now = new LocalDate();
    Date startDateTime = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(now).toDate();
    Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toDate();

    Query query = getSession().getNamedQuery("findContractsInProgressOfSalariedTenant");
    query.setParameter(START_DATE_PARAMETER, startDateTime, Hibernate.DATE);
    query.setParameter(END_DATE_PARAMETER, endDateTime, Hibernate.DATE);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ContractEntity> findContractsInProgressToRegularizeOfSalariedTenant() throws ContractDAOException {
    Query query = getSession().getNamedQuery("findContractsInProgressToRegularizeOfSalariedTenant");
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ContractEntity> findContractsWithRetainedSalary() throws ContractDAOException {
    LocalDate now = new LocalDate();
    Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toDate();

    Query query = getSession().getNamedQuery("findContractsWithRetainedSalary");
    query.setParameter(END_DATE_PARAMETER, endDateTime, Hibernate.DATE);
    return query.list();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ContractEntity> findContractsInProgressWithoutInternalCompensation() throws ContractDAOException {
    LocalDate now = new LocalDate();
    Date startDateTime = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(now).toDate();
    Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toDate();

    Query query = getSession().getNamedQuery("findContractsInProgressWithoutInternalCompensation");
    query.setParameter(START_DATE_PARAMETER, startDateTime, Hibernate.DATE);
    query.setParameter(END_DATE_PARAMETER, endDateTime, Hibernate.DATE);
    return query.list();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ContractEntity> findContractsWithoutInternalCompensation() throws ContractDAOException {
    LocalDate now = new LocalDate();
    Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toDate();

    Query query = getSession().getNamedQuery("findContractsWithoutInternalCompensation");
    query.setParameter(END_DATE_PARAMETER, endDateTime, Hibernate.DATE);
    return query.list();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ContractEntity> findContractsInProgressAndToRegularizeWithoutInternalCompensation() throws ContractDAOException {
    Query query = getSession().getNamedQuery("findContractsInProgressAndToRegularizeWithoutInternalCompensation");
    return query.list();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ContractEntity> findContractsInProgress(LocalDate date) throws ContractDAOException {
    Date startDateTime = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(date).toDate();
    Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(date).toDate();

    Query query = getSession().getNamedQuery("findContractsInProgress");
    query.setParameter(START_DATE_PARAMETER, startDateTime, Hibernate.DATE);
    query.setParameter(END_DATE_PARAMETER, endDateTime, Hibernate.DATE);
    return query.list();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ContractEntity> findContractsInProgressWithoutRetroactivity(LocalDate date) throws ContractDAOException {
    Date startDateTime = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(date).toDate();
    Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(date).toDate();

    Query query = getSession().getNamedQuery("findContractsInProgressWithoutRetroactivity");
    query.setParameter(START_DATE_PARAMETER, startDateTime, Hibernate.DATE);
    query.setParameter(END_DATE_PARAMETER, endDateTime, Hibernate.DATE);
    return query.list();
  }

  @Override
  public Long countContractsInHousing(Long idHousing) throws ContractDAOException {
    Query query = getSession().getNamedQuery("countContractsInHousing");
    query.setParameter(ContractConstants.ID_HOUSING, idHousing, Hibernate.LONG);
    return (Long) query.uniqueResult();
  }

  @Override
  public void updateSporadicallyInvoicingContracts() throws ContractDAOException {
    Query query = getSession().getNamedQuery("updateSporadicallyInvoicingContracts");
    query.executeUpdate();
  }

  @Override
  public void updateResetAddedWithdrawnRentContracts() throws ContractDAOException {
    Query query = getSession().getNamedQuery("updateResetAddedWithdrawnRentContracts");
    query.executeUpdate();
  }

  @Override
  public void updateLopRent() throws ContractDAOException {
    LocalDate now = new LocalDate();
    Date startDateTime = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(now).toDate();
    Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toDate();

    Query query = getSession().getNamedQuery("updateLopRent");
    query.setParameter(START_DATE_PARAMETER, startDateTime, Hibernate.DATE);
    query.setParameter(END_DATE_PARAMETER, endDateTime, Hibernate.DATE);
    query.executeUpdate();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<ContractEntity> findContractsWithRentToUpgrade() throws ContractDAOException {
    LocalDate now = new LocalDate();
    Date minimumTime = DateTimeUtils.getMinimumTimeOfDayOfMonth(now).toDate();

    Query query = getSession().getNamedQuery("findContractsWithRentToUpgrade");
    query.setParameter("date", minimumTime, Hibernate.DATE);
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ContractEntity> findContractsInProgressToRegularizeWithoutInternalCompensation() throws ContractDAOException {
    Query query = getSession().getNamedQuery("findContractsInProgressToRegularizeWithoutInternalCompensation");
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ContractEntity> findContractsToRegularizeWithRetainedSalary() throws ContractDAOException {
    Query query = getSession().getNamedQuery("findContractsToRegularizeWithRetainedSalary");
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ContractEntity> findContractsToHistorize() throws ContractDAOException {
    Query query = getSession().getNamedQuery("findContractsToHistorize");
    return query.list();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ContractEntity> findContractsToUpdateDataRetroactivity() throws ContractDAOException {
    Query query = getSession().getNamedQuery("findContractsToUpdateDataRetroactivity");
    return query.list();
  }

  @Override
  public void resetContractsToRegularize() throws ContractDAOException {
    Query query = getSession().getNamedQuery("resetContractsToRegularize");
    query.executeUpdate();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ContractEntity> findByCriteria(ContractCriteriaDTO contractCriteria, TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria)
    throws ContractDAOException {
    try {
      Query query = findRequestTuner(contractCriteria, tenantCriteria, housingCriteria);
      return query.list();
    } catch (HibernateException e) {
      log.info(HIBERNATE_ERROR, e);
      throw createException(e);
    } catch (Exception e) {
      log.error("Erreur inatendue ", e);
      throw createException(e);
    }
  }

  /**
   * Permet de récupérer la liste du nombre de logements et d'occupants en conflit avec les dates saisies
   * @param contractId L'identifiant du contract occcupant
   * @param dateStart date de début de validité
   * @param dateEnd date de fin de validité
   * @param housingId L'identifiant du logement
   * @param tenantId L'identifaint de l'occupant
   * @return une liste de deux integers : nombre de conflits logements et nombre de conflits occupants
   * @throws ContractDAOException une ContractDAOException
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<Object[]> validityContractDate(Long contractId, Date dateStart, Date dateEnd, Long housingId, Long tenantId) throws ContractDAOException {
    List<Object[]> validityDateList;
    Date finalDateStart = dateStart;

    // Est-ce que la requête se fera sur une intervalle de temps ? oui par défaut
    boolean timeLap = true;

    if (finalDateStart == null || dateEnd == null) {
      // Si une des dates est nulle, la requ?te ne sera pas faite sur un intervale de temps
      timeLap = false;
    }
    if (!timeLap && finalDateStart == null) {
      // Si on n'est pas sur un interval de temps, s'assurer que dateDeb est rempli
      finalDateStart = dateEnd;
    }
    Query query = null;
    // On appelle la requete
    if (timeLap) {
      query = getSession().getNamedQuery("Contract.SqlComparatorStartAndEndDate");
    } else {
      query = getSession().getNamedQuery("Contract.SqlComparatorStartDate");
    }
    // Critère de recherche par la date de début de validité
    if (null != finalDateStart) {
      query.setParameter("dateBegin", finalDateStart, Hibernate.DATE);
    }
    // Critère de recherche par la date de fin de validité
    if (null != dateEnd) {
      query.setParameter("dateEnd", dateEnd, Hibernate.DATE);
    }
    // Critére de recherche par l'indentifiant du logement
    if (null != housingId) {
      query.setParameter("housingId", housingId, Hibernate.LONG);
    }
    // Critère de recherche par l'identifiant de l'occupant
    if (null != tenantId) {
      query.setParameter("tenantId", tenantId, Hibernate.LONG);
    }
    if (null != contractId) {
      query.setParameter("contractId", contractId, Hibernate.LONG);
    }

    validityDateList = query.list();

    return validityDateList;
  }

  /**
   * Permet de construire dynamiquement la requête de recherche>
   * @param criteriaContract les critères de recherche des contrats occupant
   * @param tenantCriteria les critères de recherche des occupants
   * @param housingCriteria critères de recherche sur les informations des logements
   * @return la requête formatée
   */
  private Query findRequestTuner(ContractCriteriaDTO criteriaContract, TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria) {
    String requete = buildRequete(criteriaContract, tenantCriteria, housingCriteria);

    Query query = getSession().createQuery(requete.toString());
    buildQuery(criteriaContract, tenantCriteria, housingCriteria, query);

    return query;
  }

  /**
   * Fonction permettant de construire la requête
   * @param contractCriteria : les critères de la recherche
   * @param tenantCriteria critère de recherche des occupants
   * @param housingCriteria critères de recherche sur les informations des logements
   * @return la requête
   */
  private String buildRequete(ContractCriteriaDTO contractCriteria, TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria) {
    StringBuilder requete = new StringBuilder();

    requete.append(ContractConstants.SEARCH_CONTRACT_WITHOUT_CRITERIA);

    if (null != contractCriteria.getId()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_ID);
    }

    DAOUtils.appendNotEmptyCriteria(requete, contractCriteria.getContractReference(), DAOUtils.requestWithoutCaseSensivity("con.contractReference", ":contractReference"));

    if (null != contractCriteria.getHousing()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_HOUSING_REFERENCE);
    }

    DAOUtils.appendNotEmptyCriteria(requete, contractCriteria.getHousingReference(), ContractConstants.SEARCH_CONTRACT_BY_REAL_HOUSING_REFERENCE);

    DAOUtils.appendNotEmptyCriteria(requete, contractCriteria.getTenantReference(), DAOUtils.requestWithoutCaseSensivity("con.tenant.reference", ":tenantRef"));

    DAOUtils.appendNotEmptyCriteria(requete, tenantCriteria.getFirstName(), DAOUtils.requestWithoutCaseSensivity("t.firstName", ":firstName"));

    DAOUtils.appendNotEmptyCriteria(requete, tenantCriteria.getLastName(), DAOUtils.requestWithoutCaseSensivity("t.lastName", ":lastName"));

    if (null != contractCriteria.getRentTypology() && !contractCriteria.getRentTypology().getId().equals(0L)) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_RENT_TYPOLOGY);
    }

    if (null != contractCriteria.getTermination() && !contractCriteria.getTermination().getId().equals(0L)) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_TERMINATION);
    }

    if (null != contractCriteria.getFieldOfActivity() && !contractCriteria.getFieldOfActivity().getId().equals(0L)) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_FIELD_OF_ACTIVITY);
    }

    if (null != contractCriteria.getCostCenter() && !contractCriteria.getCostCenter().getId().equals(0L)) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_COST_CENTER);
    }

    if (null != contractCriteria.getPaymentMethod() && !contractCriteria.getPaymentMethod().getId().equals(0L)) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_PAYMENT_METHOD);
    }

    if (null != contractCriteria.getSignature()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_SIGNATURE);
    }

    if (null != contractCriteria.getStartValidityDate()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_START_VALIDITY_DATE);
    }

    if (null != contractCriteria.getEndValidityDate()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_END_VALIDITY_DATE);
    }

    if (null != contractCriteria.getMarketRentPrice()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_MARKET_RENT_PRICE);
    }

    if (null != contractCriteria.getRentPriceLimit()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_RENT_PRICE_LIMIT);
    }

    if (null != contractCriteria.getGarageRent()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_GARAGE_RENT);
    }

    if (null != contractCriteria.getGardenRent()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_GARDEN_RENT);
    }

    if (null != contractCriteria.getExtraRent()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_EXTRA_RENT);
    }

    if (null != contractCriteria.getExpectedChargeCost()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_EXPECTED_CHARGE_COST);
    }

    if (null != contractCriteria.getInsuranceCertificateEndDateLow()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_INSURANCE_DATE_LOW);
    }

    if (null != contractCriteria.getInsuranceCertificateEndDateHigh()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_INSURANCE_DATE_HIGH);
    }

    if (null != contractCriteria.getPlainAddedWithdrawnRent()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_PLAIN_ADDED_WITHDRAWN_RENT);
    }

    if (null != contractCriteria.getLastWithdrawnDate()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_LAST_WITHDRAWN_DATE);
    }

    if (null != contractCriteria.getTerminationSavings()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_TERMINATION_SAVINGS);
    }

    if (null != contractCriteria.getTerminationSavingAmount()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_TERMINATION_SAVING_AMOUNT);
    }

    if (null != contractCriteria.getPlainTerminationSavingAmount()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_PLAIN_TERMINATION_SAVING_AMOUNT);
    }

    if (null != contractCriteria.getLastSavingDate()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_LAST_SAVING_DATE);
    }

    if (null != contractCriteria.getRealEstateRentalValue()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_REAL_ESTATE_RENTAL_VALUE);
    }

    if (null != contractCriteria.getRevisedSurfaceArea()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_REVISED_SURFACE_AREA);
    }

    if (null != contractCriteria.getRevisedSurfaceAreaRent()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_REVISED_SURFACE_AREA_RENT);
    }

    if (null != contractCriteria.getHouseholdSize()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_HOUSE_HOLD_SIZE);
    }

    if (null != contractCriteria.getnNCoef()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_NNCOEF);
    }

    if (null != contractCriteria.getNetAgentRent()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_NET_AGENT_RENT);
    }

    if (null != contractCriteria.getShortTermContractDiscount()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_SHORT_TERM_CONTRACT_DISCOUNT);
    }

    if (null != contractCriteria.getLopRent()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_LOP_RENT);
    }

    if (null != contractCriteria.getFixedWithdrawnRent()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_WITHDRAWN_RENT);
    }

    if (null != contractCriteria.getBenefit()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_BENEFIT);
    }

    if (null != contractCriteria.getAddedWithdrawnRent()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_ADDED_WITHDRAWN_RENT);
    }

    if (null != contractCriteria.getTerminationSavingsPayment()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_TERMINATION_SAVINGS_PAYMENT);
    }

    if (null != contractCriteria.getTerminationSavingsPaymentDate()) {
      requete.append(ContractConstants.SEARCH_CONTRACT_BY_TERMINATION_SAVINGS_PAYMENT_DATE);
    }

    if (null != housingCriteria.getAgency()) {
      requete.append(ContractConstants.SEARCH_BY_AGENCY);
    }

    requete.append(ContractConstants.ORDER_BY_CONTRACT_ID);

    return requete.toString();
  }

  /**
   * Permet d’associer les paramètres à la requête
   * @param criteriaContract : les critères de la recherche
   * @param tenantCriteria : les critères de la recherche des occupants
   * @param housingCriteriaDTO critères de recherche sur les informations des logements
   * @param query : la requête HQL
   */
  private void buildQuery(ContractCriteriaDTO criteriaContract, TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteriaDTO, Query query) {
    setLongParameter(criteriaContract, housingCriteriaDTO, query);
    setStringParameter(criteriaContract, tenantCriteria, query);
    setBooleanParameter(criteriaContract, query);
    setDateParameter(criteriaContract, query);
    setBigDecimalParameter(criteriaContract, query);
    setIntegerParameter(criteriaContract, query);
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des valeurs de type Long
   * @param criteriaContract critères de recherche des contrats
   * @param query requête HQL
   */
  private void setLongParameter(ContractCriteriaDTO criteriaContract, HousingCriteriaDTO housingCriteria, Query query) {
    if (null != criteriaContract.getId()) {
      query.setParameter("id", criteriaContract.getId(), Hibernate.LONG);
    }

    if (null != criteriaContract.getHousing()) {
      query.setParameter("housingId", criteriaContract.getHousing(), Hibernate.LONG);
    }

    if (null != criteriaContract.getRentTypology() && !criteriaContract.getRentTypology().getId().equals(0L)) {
      query.setParameter("rentTypologyId", criteriaContract.getRentTypology().getId(), Hibernate.LONG);
    }

    if (null != criteriaContract.getTermination() && !criteriaContract.getTermination().getId().equals(0L)) {
      query.setParameter("terminationId", criteriaContract.getTermination().getId(), Hibernate.LONG);
    }

    if (null != criteriaContract.getFieldOfActivity() && !criteriaContract.getFieldOfActivity().getId().equals(0L)) {
      query.setParameter("fieldId", criteriaContract.getFieldOfActivity().getId(), Hibernate.LONG);
    }

    if (null != criteriaContract.getCostCenter() && !criteriaContract.getCostCenter().getId().equals(0L)) {
      query.setParameter("costCenterId", criteriaContract.getCostCenter().getId(), Hibernate.LONG);
    }

    if (null != criteriaContract.getPaymentMethod() && !criteriaContract.getPaymentMethod().getId().equals(0L)) {
      query.setParameter("paymentMethodId", criteriaContract.getPaymentMethod().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getAgency()) {
      query.setParameter("agency", housingCriteria.getAgency().getId(), Hibernate.LONG);
    }
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des valeurs de type String
   * @param criteriaContract critères de recherche des contrats
   * @param tenantCriteria critères de recherche des occupants
   * @param query requête HQL
   */
  private void setStringParameter(ContractCriteriaDTO criteriaContract, TenantCriteriaDTO tenantCriteria, Query query) {
    DAOUtils.setNotEmptyParameter(query, criteriaContract.getContractReference(), "contractReference", criteriaContract.getContractReference(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, criteriaContract.getHousingReference(), "housingReference", criteriaContract.getHousingReference(), Hibernate.STRING);

    if (null != criteriaContract.getTenantReference()) {
      DAOUtils.setNotEmptyParameter(query, criteriaContract.getTenantReference(), "tenantRef", criteriaContract.getTenantReference(), Hibernate.STRING);
    }

    DAOUtils.setNotEmptyParameter(query, tenantCriteria.getFirstName(), "firstName", "%" + tenantCriteria.getFirstName() + "%", Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, tenantCriteria.getLastName(), "lastName", "%" + tenantCriteria.getLastName() + "%", Hibernate.STRING);
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des valeurs de type Boolean
   * @param criteriaContract critères de recherche des contrats
   * @param query requête HQL
   */
  private void setBooleanParameter(ContractCriteriaDTO criteriaContract, Query query) {
    if (null != criteriaContract.getSignature()) {
      query.setParameter("signature", criteriaContract.getSignature(), Hibernate.BOOLEAN);
    }

    if (null != criteriaContract.getTerminationSavings()) {
      query.setParameter("terminationSavings", criteriaContract.getTerminationSavings(), Hibernate.BOOLEAN);
    }

    if (null != criteriaContract.getTerminationSavingsPayment()) {
      query.setParameter("terminationSavingsPayment", criteriaContract.getTerminationSavingsPayment(), Hibernate.BOOLEAN);
    }
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des valeurs de type Date
   * @param criteriaContract critères de recherche des contrats
   * @param query requête HQL
   */
  private void setDateParameter(ContractCriteriaDTO criteriaContract, Query query) {
    if (null != criteriaContract.getStartValidityDate()) {
      query.setParameter("startValidityDate", criteriaContract.getStartValidityDate(), Hibernate.DATE);
    }

    if (null != criteriaContract.getEndValidityDate()) {
      query.setParameter("endValidityDate", criteriaContract.getEndValidityDate(), Hibernate.DATE);
    }

    if (null != criteriaContract.getLastWithdrawnDate()) {
      query.setParameter("lastWithdrawnDate", criteriaContract.getLastWithdrawnDate(), Hibernate.DATE);
    }

    if (null != criteriaContract.getLastSavingDate()) {
      query.setParameter("lastSavingDate", criteriaContract.getLastSavingDate(), Hibernate.DATE);
    }

    if (null != criteriaContract.getTerminationSavingsPaymentDate()) {
      query.setParameter("terminationSavingsPaymentDate", criteriaContract.getTerminationSavingsPaymentDate(), Hibernate.DATE);
    }

    if (null != criteriaContract.getInsuranceCertificateEndDateLow()) {
      query.setParameter("insuranceCertificateEndDateLow", criteriaContract.getInsuranceCertificateEndDateLow(), Hibernate.DATE);
    }

    if (null != criteriaContract.getInsuranceCertificateEndDateHigh()) {
      query.setParameter("insuranceCertificateEndDateHigh", criteriaContract.getInsuranceCertificateEndDateHigh(), Hibernate.DATE);
    }
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des valeurs de type Long
   * @param criteriaContract critères de recherche des contrats
   * @param query requête HQL
   */
  private void setIntegerParameter(ContractCriteriaDTO criteriaContract, Query query) {
    // Critère de recherche par nombre de personne dans le foyer
    if (null != criteriaContract.getHouseholdSize()) {
      query.setParameter("householdSize", criteriaContract.getHouseholdSize(), Hibernate.INTEGER);
    }
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des valeurs de type Long
   * @param criteriaContract critères de recherche des contrats
   * @param query requête HQL
   */
  private void setBigDecimalParameter(ContractCriteriaDTO criteriaContract, Query query) {
    if (null != criteriaContract.getMarketRentPrice()) {
      query.setParameter("marketRentPrice", criteriaContract.getMarketRentPrice(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getRentPriceLimit()) {
      query.setParameter("rentPriceLimit", criteriaContract.getRentPriceLimit(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getGarageRent()) {
      query.setParameter("garageRent", criteriaContract.getGarageRent(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getGardenRent()) {
      query.setParameter("gardenRent", criteriaContract.getGardenRent(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getExtraRent()) {
      query.setParameter("extraRent", criteriaContract.getExtraRent(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getExpectedChargeCost()) {
      query.setParameter("expectedChargeCost", criteriaContract.getExpectedChargeCost(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getPlainAddedWithdrawnRent()) {
      query.setParameter("plainAddedWithdrawnRent", criteriaContract.getPlainAddedWithdrawnRent(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getPlainTerminationSavingAmount()) {
      query.setParameter("plainTerminationSavingAmount", criteriaContract.getPlainTerminationSavingAmount(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getTerminationSavingAmount()) {
      query.setParameter("terminationSavingAmount", criteriaContract.getTerminationSavingAmount(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getRealEstateRentalValue()) {
      query.setParameter("realEstateRentalValue", criteriaContract.getRealEstateRentalValue(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getRevisedSurfaceArea()) {
      query.setParameter("revisedSurfaceArea", criteriaContract.getRevisedSurfaceArea(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getRevisedSurfaceAreaRent()) {
      query.setParameter("revisedSurfaceAreaRent", criteriaContract.getRevisedSurfaceAreaRent(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getnNCoef()) {
      query.setParameter("nNCoef", criteriaContract.getnNCoef(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getNetAgentRent()) {
      query.setParameter("netAgentRent", criteriaContract.getNetAgentRent(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getShortTermContractDiscount()) {
      query.setParameter("shortTermContractDiscount", criteriaContract.getShortTermContractDiscount(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getLopRent()) {
      query.setParameter("lopRent", criteriaContract.getLopRent(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getFixedWithdrawnRent()) {
      query.setParameter("withdrawnRent", criteriaContract.getFixedWithdrawnRent(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getBenefit()) {
      query.setParameter("benefit", criteriaContract.getBenefit(), Hibernate.BIG_DECIMAL);
    }

    if (null != criteriaContract.getAddedWithdrawnRent()) {
      query.setParameter("addedWithdrawnRent", criteriaContract.getAddedWithdrawnRent(), Hibernate.BIG_DECIMAL);
    }
  }

  @Override
  public ContractEntity findByReference(String reference) throws ContractDAOException {
    Query query = getSession().getNamedQuery("findByContractReference");
    query.setParameter("contractReference", reference, Hibernate.STRING);
    return (ContractEntity) query.uniqueResult();
  }

  @Override
  public ContractEntity findCurrentContractOfOneHousing(Long idHousing) throws ContractDAOException {
    LocalDate now = new LocalDate();
    Date startDateTime = now.toDate();
    Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toDate();

    Query query = getSession().getNamedQuery("findCurrentContractOfOneHousing");
    query.setParameter(ContractConstants.ID_HOUSING, idHousing, Hibernate.LONG);
    query.setParameter(START_DATE_PARAMETER, startDateTime, Hibernate.DATE);
    query.setParameter(END_DATE_PARAMETER, endDateTime, Hibernate.DATE);
    return (ContractEntity) query.uniqueResult();
  }

}
