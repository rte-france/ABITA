package com.abita.dao.tenant.impl;

import com.abita.dao.tenant.constants.TenantConstants;
import com.abita.dao.tenant.entity.TenantEntity;
import com.abita.dao.tenant.exceptions.TenantDAOException;
import com.abita.util.dao.DAOUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dao.housing.constants.HousingConstants;
import com.abita.dao.tenant.ITenantDAO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Classe d'implémentation des DAO des occupants
 *
 * @author
 */
public class TenantDAOImpl extends AbstractGenericEntityDAO<TenantEntity, TenantDAOException> implements ITenantDAO {

  /**
  * serialVersionUID
  */
  private static final long serialVersionUID = -3141717048110486579L;

  @Override
  public void delete(Serializable id) throws TenantDAOException {
    // On supprime les historisations avant de supprimer l'occupant
    Query query = getSession().getNamedQuery("deleteHistoryTenant");
    query.setParameter(TenantConstants.ID_TENANT, id, Hibernate.LONG);
    query.executeUpdate();
    super.delete(id);
  }

  @Override
  public boolean isRemovable(long idTenant) throws TenantDAOException {
    Query query = getSession().getNamedQuery("isRemovableTenant");
    query.setParameter(TenantConstants.ID_TENANT, idTenant, Hibernate.LONG);
    return query.list().isEmpty();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<String> findNNI() throws TenantDAOException {
    LocalDate now = new LocalDate();
    Date startDateTime = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(now).toDate();

    Query query = getSession().getNamedQuery("findNNI");
    query.setParameter("startDate", startDateTime, Hibernate.DATE);
    return query.list();
  }

  @Override
  public TenantEntity findTenantByNNI(String nni) throws TenantDAOException {
    Query query = getSession().getNamedQuery("findTenantByNNI");
    query.setParameter(TenantConstants.REFERENCE_TENANT, nni, Hibernate.STRING);
    return (TenantEntity) query.uniqueResult();
  }

  @Override
  public void updateManagerialLastYear() throws TenantDAOException {
    Query query = getSession().getNamedQuery("updateManagerialLastYear");
    query.executeUpdate();
  }

  @Override
  public void updateHouseholdSizeLastYear() throws TenantDAOException {
    Query query = getSession().getNamedQuery("updateHouseholdSizeLastYear");
    query.executeUpdate();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<TenantEntity> findByCriteria(TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria) throws TenantDAOException {
    try {
      Query query = findRequestTuner(tenantCriteria, housingCriteria);
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
   *  Permet de construire dynamiquement la requête de recherche
   * @param tenantCriteria les critères de recherche des occupants
   * @param housingCriteria les critères de recherche des logements
   * @return la requête formatée
   */
  private Query findRequestTuner(TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria) {
    String requete = buildRequete(tenantCriteria, housingCriteria);

    Query query = getSession().createQuery(requete);
    buildQuery(tenantCriteria, housingCriteria, query);

    return query;
  }

  /**
   *  Fonction permettant de construire la requête
   * @param tenantCriteria les critères de la recherche occupant
   * @param housingCriteria les critères de la recherche logement
   * @return la requête
   */
  private String buildRequete(TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria) {
    // Construction dynamique de la requête de recherche
    StringBuilder requete = new StringBuilder();

    // Requete de recherche de tous les occupants
    requete.append(TenantConstants.SEARCH_TENANT_WITHOUT_CRITERIA);

    appendTenantCriteria(tenantCriteria, requete);
    appendHousingCriteria(housingCriteria, requete);

    requete.append(TenantConstants.ORDER_BY_TENANT_REFERENCE);

    return requete.toString();
  }

  /**
   * Permet de construire la requête au niveau des critères de l’occupant
   * @param tenantCriteria critères de l’occupant
   * @param requete requête en cours de construction
   */
  private void appendTenantCriteria(TenantCriteriaDTO tenantCriteria, StringBuilder requete) {
    DAOUtils.appendNotEmptyCriteria(requete, tenantCriteria.getReference(), DAOUtils.requestWithoutCaseSensivity("t.reference", ":reference"));

    DAOUtils.appendNotEmptyCriteria(requete, tenantCriteria.getFirstName(), DAOUtils.requestWithoutCaseSensivity("t.firstName", ":firstName"));

    DAOUtils.appendNotEmptyCriteria(requete, tenantCriteria.getLastName(), DAOUtils.requestWithoutCaseSensivity("t.lastName", ":lastName"));

    DAOUtils.appendNotEmptyCriteria(requete, tenantCriteria.getAddress(), DAOUtils.requestWithoutCaseSensivity("t.address", ":address"));

    DAOUtils.appendNotEmptyCriteria(requete, tenantCriteria.getPostalCode(), DAOUtils.requestWithoutCaseSensivity("t.postalCode", ":postalCode"));

    DAOUtils.appendNotEmptyCriteria(requete, tenantCriteria.getCity(), DAOUtils.requestWithoutCaseSensivity("t.city", ":city"));

    if (null != tenantCriteria.isEmployee()) {
      requete.append(TenantConstants.SEARCH_TENANT_BY_RTEEMPLOYEE);
    }

    if (null != tenantCriteria.isManagerial()) {
      requete.append(TenantConstants.SEARCH_TENANT_BY_MANAGERIAL);
    }

    if (null != tenantCriteria.isManagerialLastYear()) {
      requete.append(TenantConstants.SEARCH_TENANT_BY_MANAGERIAL_LAST_YEAR);
    }

    if (null != tenantCriteria.getHouseholdSize()) {
      requete.append(TenantConstants.SEARCH_TENANT_BY_HOUSE_HOLD_SIZE);
    }

    if (null != tenantCriteria.getHouseholdSizeLastYear()) {
      requete.append(TenantConstants.SEARCH_TENANT_BY_HOUSE_HOLD_SIZE_LAST_YEAR);
    }

    if (null != tenantCriteria.getActualSalary()) {
      requete.append(TenantConstants.SEARCH_TENANT_BY_ACTUAL_SALARY);
    }

    if (null != tenantCriteria.getReferenceGrossSalary()) {
      requete.append(TenantConstants.SEARCH_TENANT_BY_REFERENCE_GROSS_SALARY);
    }

    if (null != tenantCriteria.getTypeTenant()) {
      requete.append(TenantConstants.SEARCH_TENANT_BY_TYPE_TENANT);
    }

    if (!StringUtils.isEmpty(tenantCriteria.getPhone())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("t.phone", ":phone"));
    }

    if (!StringUtils.isEmpty(tenantCriteria.getMailAddress())) {
      requete.append(DAOUtils.requestWithoutCaseSensivity("t.mailAddress", ":mailAddress"));
    }
  }

  /**
   * Permet de construire la requête au niveau des critères du logement
   * @param housingCriteria critères du logement
   * @param requete requête en cours de construction
   */
  private void appendHousingCriteria(HousingCriteriaDTO housingCriteria, StringBuilder requete) {
    if (null != housingCriteria.getId()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_REFERENCE);
    }

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getReference(), DAOUtils.requestWithoutCaseSensivity("h.reference", ":reference"));

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getAddress(), DAOUtils.requestWithoutCaseSensivity("h.address", ":address"));

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getPostalCode(), DAOUtils.requestWithoutCaseSensivity("h.postalCode", ":postalCode"));

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getCity(), DAOUtils.requestWithoutCaseSensivity("h.city", ":city"));

    if (null != housingCriteria.getRegisterDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_REGISTER_DATE);

    }

    if (null != housingCriteria.getUnregisterDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_UNREGISTER_DATE);
    }

    if (null != housingCriteria.getReasonForExit()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_REASON_FOR_EXIT);
    }

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getRealSurfaceArea(), HousingConstants.SEARCH_HOUSING_BY_REAL_SURFACE);

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getRevisedSurfaceArea(), HousingConstants.SEARCH_HOUSING_BY_REVISED_SURFACE);

    if (null != housingCriteria.getProperty()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_PROPERTY);
    }

    if (null != housingCriteria.getGardenAvailable()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_GARDEN);
    }

    if (null != housingCriteria.getGarageAvailable()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_GARAGE);
    }

    if (null != housingCriteria.getHousingStatus()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_STATUS);
    }

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getRoomCount(), HousingConstants.SEARCH_HOUSING_BY_ROOM_COUNT);

    if (null != housingCriteria.getHousingNature()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_NATURE);
    }

    if (null != housingCriteria.getCore()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_CORE);
    }

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getYear(), HousingConstants.SEARCH_HOUSING_BY_YEAR);

    if (null != housingCriteria.getThirdpartyContractManager()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_THIRD_PARTY_CONTRACT_MANAGER);
    }

    if (null != housingCriteria.getRoomCategory()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_ROOM_CATEGORY);
    }

    if (null != housingCriteria.getAgency()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_AGENCY);
    }

    if (null != housingCriteria.getRattachmentSite()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_SITE);
    }
  }

  /**
   * Permet d'associer les paramètres à la requête
   * @param tenantCriteria les critères de la recherche occupant
   * @param housingCriteria les critères de la recherche logement
   * @param query la requête HQL
   */
  private void buildQuery(TenantCriteriaDTO tenantCriteria, HousingCriteriaDTO housingCriteria, Query query) {
    setTenantParameter(tenantCriteria, query);
    setHousingParameter(housingCriteria, query);
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des occupants
   * @param tenantCriteria critères de recherche des occupants
   * @param query requête HQL
   */
  private void setTenantParameter(TenantCriteriaDTO tenantCriteria, Query query) {
    DAOUtils.setNotEmptyParameter(query, tenantCriteria.getReference(), "reference", "%" + tenantCriteria.getReference() + "%", Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, tenantCriteria.getFirstName(), "firstName", "%" + tenantCriteria.getFirstName() + "%", Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, tenantCriteria.getLastName(), "lastName", "%" + tenantCriteria.getLastName() + "%", Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, tenantCriteria.getAddress(), "address", "%" + tenantCriteria.getAddress() + "%", Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, tenantCriteria.getPostalCode(), "postalCode", "%" + tenantCriteria.getPostalCode() + "%", Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, tenantCriteria.getCity(), "city", "%" + tenantCriteria.getCity() + "%", Hibernate.STRING);

    // Critère de recherche par le salarié employé ou non
    if (null != tenantCriteria.isEmployee()) {
      query.setParameter("employee", tenantCriteria.isEmployee(), Hibernate.BOOLEAN);
    }
    // Critère de recherche par le statut cadre ou non
    if (null != tenantCriteria.isManagerial()) {
      query.setParameter("managerial", tenantCriteria.isManagerial(), Hibernate.BOOLEAN);
    }
    // Critère de recherche par le statut cadre ou non
    if (null != tenantCriteria.isManagerialLastYear()) {
      query.setParameter("managerialLastYear", tenantCriteria.isManagerialLastYear(), Hibernate.BOOLEAN);
    }
    // Critère de recherche par nombre de personne dans le foyer
    if (null != tenantCriteria.getHouseholdSize()) {
      query.setParameter("householdSize", tenantCriteria.getHouseholdSize(), Hibernate.INTEGER);
    }
    // Critère de recherche par nombre de personne dans le foyer à octobre N-1
    if (null != tenantCriteria.getHouseholdSizeLastYear()) {
      query.setParameter("householdSizeLastYear", tenantCriteria.getHouseholdSizeLastYear(), Hibernate.INTEGER);
    }
    // Critère de recherche par le salaire
    if (null != tenantCriteria.getActualSalary()) {
      query.setParameter("actualSalary", tenantCriteria.getActualSalary(), Hibernate.BIG_DECIMAL);
    }
    // Critère de recherche par le salaire à octobre N-1
    if (null != tenantCriteria.getReferenceGrossSalary()) {
      query.setParameter("referenceGrossSalary", tenantCriteria.getReferenceGrossSalary(), Hibernate.BIG_DECIMAL);
    }
    // Critère de recherche par type d'occupant
    if (null != tenantCriteria.getTypeTenant()) {
      query.setParameter("typeTenant", tenantCriteria.getTypeTenant().getId(), Hibernate.LONG);
    }

    if (!StringUtils.isEmpty(tenantCriteria.getPhone())) {
      query.setParameter("phone", "%" + tenantCriteria.getPhone() + "%", Hibernate.STRING);
    }

    if (!StringUtils.isEmpty(tenantCriteria.getMailAddress())) {
      query.setParameter("mailAddress", "%" + tenantCriteria.getMailAddress() + "%", Hibernate.STRING);
    }
  }

  /**
   * Permet d’associer les paramètres à la requête au niveau des logements
   * @param housingCriteria critères de recherche des logements
   * @param query requête HQL
   */
  private void setHousingParameter(HousingCriteriaDTO housingCriteria, Query query) {
    if (null != housingCriteria.getId()) {
      query.setParameter("id", housingCriteria.getId(), Hibernate.LONG);
    }

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getReference(), "reference", "%" + housingCriteria.getReference() + "%", Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getAddress(), "address", "%" + housingCriteria.getAddress() + "%", Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getPostalCode(), "postalCode", "%" + housingCriteria.getPostalCode() + "%", Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getCity(), "city", "%" + housingCriteria.getCity() + "%", Hibernate.STRING);

    if (null != housingCriteria.getRegisterDate()) {
      query.setParameter("registerDate", housingCriteria.getRegisterDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getUnregisterDate()) {
      query.setParameter("unregisterDate", housingCriteria.getUnregisterDate(), Hibernate.DATE);
    }

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getReasonForExit(), "reasonForExit", housingCriteria.getReasonForExit(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getRealSurfaceArea(), "realSurfaceArea", housingCriteria.getRealSurfaceArea(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getRevisedSurfaceArea(), "revisedSurfaceArea", housingCriteria.getRevisedSurfaceArea(), Hibernate.STRING);

    if (null != housingCriteria.getProperty()) {
      query.setParameter("property", housingCriteria.getProperty(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getGardenAvailable()) {
      query.setParameter("gardenAvailable", housingCriteria.getGardenAvailable(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getGarageAvailable()) {
      query.setParameter("garageAvailable", housingCriteria.getGarageAvailable(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getHousingStatus()) {
      query.setParameter("housingStatus", housingCriteria.getHousingStatus().name(), Hibernate.STRING);
    }

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getRoomCount(), "roomCount", housingCriteria.getRoomCount(), Hibernate.STRING);

    if (null != housingCriteria.getHousingNature()) {
      query.setParameter("housingNature", housingCriteria.getHousingNature().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getCore()) {
      query.setParameter("core", housingCriteria.getCore(), Hibernate.BOOLEAN);
    }

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getYear(), "year", housingCriteria.getYear(), Hibernate.STRING);

    if (null != housingCriteria.getThirdpartyContractManager()) {
      query.setParameter("thirdpartyContractManager", housingCriteria.getThirdpartyContractManager().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getRoomCategory()) {
      query.setParameter("roomCategory", housingCriteria.getRoomCategory().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getAgency()) {
      query.setParameter("agency", housingCriteria.getAgency().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getRattachmentSite()) {
      query.setParameter("rattachmentSite", housingCriteria.getRattachmentSite().getId(), Hibernate.LONG);
    }

  }

}
