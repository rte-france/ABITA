/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.historytenant.impl;

import com.abita.dao.tenant.constants.TenantConstants;
import com.abita.dao.historytenant.IHistoryTenantDAO;
import com.abita.dao.historytenant.entity.HistoryTenantEntity;
import com.abita.dao.historytenant.exceptions.HistoryTenantDAOException;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.joda.time.LocalDate;

import javax.sql.DataSource;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe d’implémentation des DAO de l’historisation des occupants
 */
public class HistoryTenantDAOImpl extends AbstractGenericEntityDAO<HistoryTenantEntity, HistoryTenantDAOException> implements IHistoryTenantDAO {

  /** Source de données */
  private DataSource dataSource;

  /** Position du paramètre de l’année dans la requête */
  private static final int YEAR_PARAMETER_POSITION = 2;

  /** logger de classe. */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistoryTenantDAOImpl.class);

  /** Serial ID */
  private static final long serialVersionUID = 6518173009046331841L;

  @Override
  public HistoryTenantEntity get(Long id, int month, int year) throws HistoryTenantDAOException {
    Query query = getSession().getNamedQuery("findHistoryTenantByPrimaryKeys");
    query.setParameter("id", id, Hibernate.LONG);
    query.setParameter("month", month, Hibernate.INTEGER);
    query.setParameter("year", year, Hibernate.INTEGER);
    return (HistoryTenantEntity) query.uniqueResult();
  }

  @Override
  public HistoryTenantEntity getLatest(Long id, int month, int year) throws HistoryTenantDAOException {
    Query query = getSession().getNamedQuery("findLatestHistoryTenantByPrimaryKeys");
    query.setParameter("id", id, Hibernate.LONG);
    return (HistoryTenantEntity) query.setMaxResults(1).uniqueResult();
  }

  @Override
  public void historizeAllTenants() throws HistoryTenantDAOException {
    LocalDate now = new LocalDate();
    Integer month = now.getMonthOfYear();
    Integer year = now.getYear();

    try (Connection conn = dataSource.getConnection();
    	 CallableStatement stmt = conn.prepareCall("{call HISTORIZE_TENANT(?,?)}");)
	{
      stmt.setInt(1, month);
      stmt.setInt(YEAR_PARAMETER_POSITION, year);
      stmt.execute();
    } catch (SQLException e) {
      log.error("Erreur inattendue : " + e);
      throw new HistoryTenantDAOException(e);
    }
  }

  @Override
  public void deleteOldTenants(int month, int year) throws HistoryTenantDAOException {
    Query query = getSession().getNamedQuery("deleteOldTenants");
    query.setParameter("month", month, Hibernate.INTEGER);
    query.setParameter("year", year, Hibernate.INTEGER);
    int countDeletedRows = query.executeUpdate();
    LOGGER.info(countDeletedRows + " tenants deleted");
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<HistoryTenantEntity> getTemporaries(Long id) throws HistoryTenantDAOException {
    Query query = getSession().getNamedQuery("findTemporariesTenant");
    query.setParameter("id", id, Hibernate.LONG);
    return query.list();
  }

  /**
   * @param dataSource the dataSource to set
   */
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void deleteAllHistoryTenantOfOneTenant(Long id) throws HistoryTenantDAOException {
    // On supprime les historisations
    Query query = getSession().getNamedQuery("deleteHistoryTenant");
    query.setParameter(TenantConstants.ID_TENANT, id, Hibernate.LONG);
    query.executeUpdate();
  }

}
