package com.abita.dao.housing.impl;

import com.abita.dao.contract.entity.ContractEntity;
import com.abita.dao.housing.constants.HousingConstants;
import com.abita.dao.housing.exceptions.HousingDAOException;
import com.abita.dao.tenant.constants.TenantConstants;
import com.abita.util.dao.DAOUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dao.housing.IHousingDAO;
import com.abita.dao.housing.entity.HousingEntity;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.TenantCriteriaDTO;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.apache.commons.lang.BooleanUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe d'implémentation des DAO des logements
 *
 * @author
 */
public class HousingDAOImpl extends AbstractGenericEntityDAO<HousingEntity, HousingDAOException> implements IHousingDAO {

  /**
  * serialVersionUID
  */
  private static final long serialVersionUID = 7248844467836819556L;

  @Override
  public boolean isRemovable(long idHousing) throws HousingDAOException {
    Query query = getSession().getNamedQuery("isRemovableHousing");
    query.setParameter(HousingConstants.ID_HOUSING, idHousing, Hibernate.LONG);
    return query.list().isEmpty();
  }

  @Override
  public String generateReference() throws HousingDAOException {
    Query query = getSession().getNamedQuery("generateReference");
    return query.uniqueResult().toString();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<HousingEntity> findByCriteria(HousingCriteriaDTO housingCriteria, TenantCriteriaDTO tenantCriteria) throws HousingDAOException {
    try {
      Query query = findRequestTuner(housingCriteria, tenantCriteria);
      List<Object[]> queryResult = query.list();
      Map<Long, HousingEntity> housings = new HashMap<>();

      LocalDate now = new LocalDate();
      Date startDateTime = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(now).toDate();
      Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toDate();

      for (Object[] objectArray : queryResult) {
        HousingEntity housing = (HousingEntity) objectArray[0];
        ContractEntity contract = (ContractEntity) objectArray[1];

        housing = defineFieldOfActivityIfExist(startDateTime, endDateTime, housing, contract);

        housings.put(housing.getId(), housing);
      }
      return new ArrayList<>(housings.values());
    } catch (HibernateException e) {
      log.info("Erreur Hibernate ", e);
      throw createException(e);
    } catch (Exception e) {
      log.error("Erreur inatendue ", e);
      throw createException(e);
    }
  }

  /**
   * Permet de définir le domaine d’activité du logement à partir de son dernier contrat s’il est en cours
   * @param startDateTime premier jour du mois
   * @param endDateTime dernier jour du mois
   * @param housing logement à traiter
   * @param contract dernier contrat du logement
   * @return logement mis à jour si le dernier contrat est en cours
   */
  private HousingEntity defineFieldOfActivityIfExist(Date startDateTime, Date endDateTime, HousingEntity housing, ContractEntity contract) {
    if (contract != null) {
      Boolean startValidityDateBeforeEndDate = contract.getStartValidityDate().before(endDateTime) || contract.getStartValidityDate().equals(endDateTime);
      Boolean endValidityDateAfterStartDate = contract.getEndValidityDate() == null || startDateTime.before(contract.getEndValidityDate())
        || startDateTime.equals(contract.getEndValidityDate());

      if (startValidityDateBeforeEndDate && endValidityDateAfterStartDate) {
        housing.setFieldOfActivity(contract.getFieldOfActivity());
      }
    }
    return housing;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<HousingEntity> findHousingsByAgency(Long idAgency) throws HousingDAOException {
    Query query = getSession().getNamedQuery("findHousingsByAgency");
    query.setParameter("idAgency", idAgency, Hibernate.LONG);
    return query.list();
  }

  /**
   * Permet de construire dynaiquement la requête de recherche
   * @param housingCriteria critère de recherche par logement
   * @param tenantCriteria critère de recherche par occupant
   * @return une requete formatée
   */
  private Query findRequestTuner(HousingCriteriaDTO housingCriteria, TenantCriteriaDTO tenantCriteria) {
    String requete = buildRequete(housingCriteria, tenantCriteria);

    Query query = getSession().createQuery(requete);
    buildQuery(housingCriteria, tenantCriteria, query);

    return query;
  }

  /**
   * Fonction permettant de construire la requête
   * @param housingCriteria critère de recherche par occupants
   * @param tenantCriteria critère de recherche par logement
   * @return une requete
   */
  private String buildRequete(HousingCriteriaDTO housingCriteria, TenantCriteriaDTO tenantCriteria) {
    // Construction dynamique de la requête de recherche
    StringBuilder requete = new StringBuilder();

    // Requete de recherche de tous les logements
    requete.append(HousingConstants.SEARCH_HOUSING_WITHOUT_CRITERIA);

    appendHousingCriteria(housingCriteria, requete);
    appendTenantCriteria(tenantCriteria, requete);

    requete.append(HousingConstants.ORDER_BY_HOUSING_REFERENCE_AND_START_VALIDITY_DATE);

    return requete.toString();
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
    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getDateFuturOfHousing(), DAOUtils.requestWithoutCaseSensivity("h.dateFuturOfHousing", ":dateFuturOfHousing"));

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getDateProgrammedSale(), DAOUtils.requestWithoutCaseSensivity("h.dateProgrammedSale", ":dateProgrammedSale"));

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getDateDemolition(), DAOUtils.requestWithoutCaseSensivity("h.dateDemolition", ":dateDemolition"));

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getDateRenovation(), DAOUtils.requestWithoutCaseSensivity("h.dateRenovation", ":dateRenovation"));

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getCity(), DAOUtils.requestWithoutCaseSensivity("h.city", ":city"));

    if (null != housingCriteria.getRegisterDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_REGISTER_DATE);
    }

    if (null != housingCriteria.getUnregisterDate() && BooleanUtils.isFalse(housingCriteria.getExcludeHousingWithUnregisterDate())) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_UNREGISTER_DATE);
    }

    // Utilisation de la classe utils pour éviter le null (null = false)
    if (BooleanUtils.isTrue(housingCriteria.getExcludeHousingWithUnregisterDate())) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_UNREGISTER_DATE_NULL);
    }

    if (null != housingCriteria.getBathroomDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_BATHROOM_DATE);
    }

    if (null != housingCriteria.getGasDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_GAS_DATE);
    }

    if (null != housingCriteria.getDpeDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_DPE_DATE);
    }

    if (null != housingCriteria.getKitchenDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_KITCHEN_DATE);
    }

    if (null != housingCriteria.getLeadDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_LEAD_DATE);
    }

    if (null != housingCriteria.getAsbestosDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_ASBESTOS_DATE);
    }

    if (null != housingCriteria.getTermiteDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_TERMITE_DATE);
    }

    if (null != housingCriteria.getErnmtDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_ERNMT_DATE);
    }

    if (null != housingCriteria.getCarrezDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_CARREZ_DATE);
    }

    if (null != housingCriteria.getElectDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_ELECT_DATE);
    }

    if (null != housingCriteria.getReasonForExit()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_REASON_FOR_EXIT);
    }

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getRealSurfaceArea(), HousingConstants.SEARCH_HOUSING_BY_REAL_SURFACE);

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getRevisedSurfaceArea(), HousingConstants.SEARCH_HOUSING_BY_REVISED_SURFACE);

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getSquareMeter(), HousingConstants.SEARCH_HOUSING_BY_SQUARE_METER);


    if (null != housingCriteria.getProperty()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_PROPERTY);
    }

    if (null != housingCriteria.getGardenAvailable()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_GARDEN);
    }

    if (null != housingCriteria.getEquippedKitchen()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_EQUIPPED_KITCHEN);
    }

    if (null != housingCriteria.getBathroom()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_BATHROOM);
    }

    if (null != housingCriteria.getDpe()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_DPE);
    }

    if (null != housingCriteria.getGas()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_GAS);
    }

    if (null != housingCriteria.getElectAno()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_ELECT_ANO);
    }

    if (null != housingCriteria.getElect()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_ELECT);
    }

    if (null != housingCriteria.getGasAno()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_GAS_ANO);
    }

    if (null != housingCriteria.getAsbestos()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_ASBESTOS);
    }

    if (null != housingCriteria.getAsbestosAno()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_ASBESTOS_ANO);
    }

    if (null != housingCriteria.getLead()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_LEAD);
    }

    if (null != housingCriteria.getLeadAno()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_LEAD_ANO);
    }

    if (null != housingCriteria.getTermite()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_TERMITE);
    }

    if (null != housingCriteria.getTermiteAno()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_TERMITE_ANO);
    }

    if (null != housingCriteria.getAirconditioner()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_AIRCONDITIONER);
    }

    if (null != housingCriteria.getHeatingtype()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_HEATINGTYPE);
    }

    if (null != housingCriteria.getSanitation()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_SANITATION);
    }

    if (null != housingCriteria.getErnmt()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_ERNMT);
    }

    if (null != housingCriteria.getCarrez()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_CARREZ);
    }

    if (null != housingCriteria.getGarageAvailable()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_GARAGE);
    }

    if (null != housingCriteria.getHousingStatus()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_STATUS);
    }

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getEotp(), DAOUtils.requestWithoutCaseSensivity("h.eotp", ":eotp"));

    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getOiHousing(), DAOUtils.requestWithoutCaseSensivity("h.oiHousing", ":oiHousing"));

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

    if (null != housingCriteria.getFieldOfActivity()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_FIELD_OF_ACITIVITY);
    }

    if (null != housingCriteria.getAgency()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_AGENCY);
    }

    if (null != housingCriteria.getRattachmentSite()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_SITE);
    }

    if (null != housingCriteria.getLastDaafDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_LAST_DATE_DAAF);
    }
    if (null != housingCriteria.getFuturOfHousing()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_FUTUR_OF_HOUSING);
    }
    if (null != housingCriteria.getSanitationstd()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_SANITATION_STD);
    }
    if (null != housingCriteria.getFacade()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_FACADE);
    }
    if (null != housingCriteria.getCouverture()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_COUVERTURE);
    }
    if (null != housingCriteria.getInsulation()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_INSULATION);
    }
    if (null != housingCriteria.getWalls()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_WALLS);
    }
    if (null != housingCriteria.getJoineryFrame()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_JOINERYFRAME);
    }
    if (null != housingCriteria.getFinitions()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_FINITIONS);
    }
    if (null != housingCriteria.getVmc()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_VMC);
    }
    if (null != housingCriteria.getPlomberie()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_PLOMBERIE);
    }
    if (null != housingCriteria.getEnergyLabel()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_ENERGY_LABEL);
    }
    if (null != housingCriteria.getLastVisitDate()) {
      requete.append(HousingConstants.SEARCH_HOUSING_BY_LAST_VISIT_DATE);
    }
    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getFuturOfHousingPrice(), HousingConstants.SEARCH_HOUSING_BY_PRIX_FUTUR_OF_HOUSING);
    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getEmissionGes(), HousingConstants.SEARCH_HOUSING_BY_EMISSION_GES);
    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getCoutDemolition(), HousingConstants.SEARCH_HOUSING_BY_COUT_DEMOLITION);
    DAOUtils.appendNotEmptyCriteria(requete, housingCriteria.getCoutRenovation(), HousingConstants.SEARCH_HOUSING_BY_COUT_RENOVATION);
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
  }

  /**
   * Permet d’associer les paramètres à la requête
   * @param housingCriteria : les critères de recherche par logements
   * @param tenantCriteria : les critère de recherche par occupants
   * @param query : la requête HQL
   */
  private void buildQuery(HousingCriteriaDTO housingCriteria, TenantCriteriaDTO tenantCriteria, Query query) {
    setHousingParameter(housingCriteria, query);
    setTenantParameter(tenantCriteria, query);
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

    if (null != housingCriteria.getUnregisterDate() && BooleanUtils.isFalse(housingCriteria.getExcludeHousingWithUnregisterDate())) {
      query.setParameter("unregisterDate", housingCriteria.getUnregisterDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getKitchenDate()) {
      query.setParameter("kitchenDate", housingCriteria.getKitchenDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getBathroomDate()) {
      query.setParameter("bathroomDate", housingCriteria.getBathroomDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getGasDate()) {
      query.setParameter("gasDate", housingCriteria.getGasDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getDpeDate()) {
      query.setParameter("dpeDate", housingCriteria.getDpeDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getElectDate()) {
      query.setParameter("electDate", housingCriteria.getElectDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getAsbestosDate()) {
      query.setParameter("asbestosDate", housingCriteria.getAsbestosDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getLeadDate()) {
      query.setParameter("leadDate", housingCriteria.getLeadDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getTermiteDate()) {
      query.setParameter("termiteDate", housingCriteria.getTermiteDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getCarrezDate()) {
      query.setParameter("carrezDate", housingCriteria.getCarrezDate(), Hibernate.DATE);
    }

    if (null != housingCriteria.getErnmtDate()) {
      query.setParameter("ernmtDate", housingCriteria.getErnmtDate(), Hibernate.DATE);
    }

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getReasonForExit(), "reasonForExit", housingCriteria.getReasonForExit(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getRealSurfaceArea(), "realSurfaceArea", housingCriteria.getRealSurfaceArea(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getSquareMeter(), "squareMeter", housingCriteria.getSquareMeter(), Hibernate.STRING);

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

    if (null != housingCriteria.getTermiteAno()) {
      query.setParameter("termiteAno", housingCriteria.getTermiteAno(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getElectAno()) {
      query.setParameter("electAno", housingCriteria.getElectAno(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getAsbestosAno()) {
      query.setParameter("asbestosAno", housingCriteria.getAsbestosAno(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getLeadAno()) {
      query.setParameter("leadAno", housingCriteria.getLeadAno(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getEquippedKitchen()) {
      query.setParameter("equippedKitchen", housingCriteria.getEquippedKitchen(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getBathroom()) {
      query.setParameter("bathroom", housingCriteria.getBathroom(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getGas()) {
      query.setParameter("gas", housingCriteria.getGas(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getDpe()) {
      query.setParameter("dpe", housingCriteria.getDpe(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getElect()) {
      query.setParameter("elect", housingCriteria.getElect(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getGasAno()) {
      query.setParameter("gasAno", housingCriteria.getGasAno(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getAsbestos()) {
      query.setParameter("asbestos", housingCriteria.getAsbestos(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getLead()) {
      query.setParameter("lead", housingCriteria.getLead(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getTermite()) {
      query.setParameter("termite", housingCriteria.getTermite(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getErnmt()) {
      query.setParameter("ernmt", housingCriteria.getErnmt(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getCarrez()) {
      query.setParameter("carrez", housingCriteria.getCarrez(), Hibernate.BOOLEAN);
    }

    if (null != housingCriteria.getHousingStatus()) {
      query.setParameter("housingStatus", housingCriteria.getHousingStatus().name(), Hibernate.STRING);
    }

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getEotp(), "eotp", housingCriteria.getEotp(), Hibernate.STRING);


    DAOUtils.setNotEmptyParameter(query, housingCriteria.getOiHousing(), "oiHousing",  housingCriteria.getOiHousing(), Hibernate.STRING);


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

    if (null != housingCriteria.getFieldOfActivity()) {
      LocalDate now = new LocalDate();
      Date startDateTime = DateTimeUtils.getMinimumTimeOfFirstDayOfMonth(now).toDate();
      Date endDateTime = DateTimeUtils.getMaximumTimeOfLastDayOfMonth(now).toDate();
      query.setParameter("startDate", startDateTime, Hibernate.DATE);
      query.setParameter("endDate", endDateTime, Hibernate.DATE);
      query.setParameter("foa", housingCriteria.getFieldOfActivity().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getAgency()) {
      query.setParameter("agency", housingCriteria.getAgency().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getSanitation()) {
      query.setParameter("sanitation", housingCriteria.getSanitation().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getAirconditioner()) {
      query.setParameter("airconditioner", housingCriteria.getAirconditioner().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getHeatingtype()) {
      query.setParameter("heatingType", housingCriteria.getHeatingtype().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getRattachmentSite()) {
      query.setParameter("rattachmentSite", housingCriteria.getRattachmentSite().getId(), Hibernate.LONG);
    }

    if (null != housingCriteria.getLastDaafDate()) {
      query.setParameter("lastDaafDate", housingCriteria.getLastDaafDate(), Hibernate.DATE);
    }

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getFuturOfHousing(), "futurOfHousing", housingCriteria.getFuturOfHousing(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getSanitationstd(), "sanitationstd", housingCriteria.getSanitationstd(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getFacade(), "facade", housingCriteria.getFacade(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getCouverture(), "couverture", housingCriteria.getCouverture(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getInsulation(), "insulation", housingCriteria.getInsulation(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getWalls(), "walls", housingCriteria.getWalls(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getJoineryFrame(), "JoineryFrame", housingCriteria.getJoineryFrame(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getFinitions(), "finitions", housingCriteria.getFinitions(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getVmc(), "vmc", housingCriteria.getVmc(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getPlomberie(), "plomberie", housingCriteria.getPlomberie(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getEnergyLabel(), "energyLabel", housingCriteria.getEnergyLabel(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getDateFuturOfHousing(), "dateFuturOfHousing", housingCriteria.getDateFuturOfHousing(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getDateProgrammedSale(), "dateProgrammedSale", housingCriteria.getDateProgrammedSale(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getDateDemolition(), "dateDemolition", housingCriteria.getDateDemolition(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getDateRenovation(), "dateRenovation", housingCriteria.getDateRenovation(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getFuturOfHousingPrice(), "futurOfHousingPrice", housingCriteria.getFuturOfHousingPrice(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getEmissionGes(), "emissionGes", housingCriteria.getEmissionGes(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getCoutDemolition(), "coutDemolition", housingCriteria.getCoutDemolition(), Hibernate.STRING);

    DAOUtils.setNotEmptyParameter(query, housingCriteria.getCoutRenovation(), "coutRenovation", housingCriteria.getCoutRenovation(), Hibernate.STRING);

    if (null != housingCriteria.getLastVisitDate()) {
      query.setParameter("lastVisitDate", housingCriteria.getLastVisitDate(), Hibernate.DATE);
    }
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

    if (null != tenantCriteria.isEmployee()) {
      query.setParameter("employee", tenantCriteria.isEmployee(), Hibernate.BOOLEAN);
    }

    if (null != tenantCriteria.isManagerial()) {
      query.setParameter("managerial", tenantCriteria.isManagerial(), Hibernate.BOOLEAN);
    }

    if (null != tenantCriteria.isManagerialLastYear()) {
      query.setParameter("managerialLastYear", tenantCriteria.isManagerialLastYear(), Hibernate.BOOLEAN);
    }

    if (null != tenantCriteria.getHouseholdSize()) {
      query.setParameter("householdSize", tenantCriteria.getHouseholdSize(), Hibernate.INTEGER);
    }

    if (null != tenantCriteria.getHouseholdSizeLastYear()) {
      query.setParameter("householdSizeLastYear", tenantCriteria.getHouseholdSizeLastYear(), Hibernate.INTEGER);
    }

    if (null != tenantCriteria.getActualSalary()) {
      query.setParameter("actualSalary", tenantCriteria.getActualSalary(), Hibernate.BIG_DECIMAL);
    }

    if (null != tenantCriteria.getReferenceGrossSalary()) {
      query.setParameter("referenceGrossSalary", tenantCriteria.getReferenceGrossSalary(), Hibernate.BIG_DECIMAL);
    }

    if (null != tenantCriteria.getTypeTenant()) {
      query.setParameter("typeTenant", tenantCriteria.getTypeTenant().getId(), Hibernate.LONG);
    }
  }
}
