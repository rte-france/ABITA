/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.historyhousing.impl;

import com.abita.dao.housing.constants.HousingConstants;
import com.abita.dao.historyhousing.IHistoryHousingDAO;
import com.abita.dao.historyhousing.entity.HistoryHousingEntity;
import com.abita.dao.historyhousing.exceptions.HistoryHousingDAOException;
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
 * Classe d'implémentation des DAO pour l'historisation des logements
 * @author
 *
 */
public class HistoryHousingDAOImpl extends AbstractGenericEntityDAO<HistoryHousingEntity, HistoryHousingDAOException> implements IHistoryHousingDAO {

  /** Source de données */
  private DataSource dataSource;

  /** Position du paramètre de l’année dans la requête */
  private static final int YEAR_PARAMETER_POSITION = 2;

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 1869079339146349976L;

  /** logger de classe. */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistoryHousingDAOImpl.class);

  @Override
  public HistoryHousingEntity get(Long id, int month, int year) throws HistoryHousingDAOException {
    Query query = getSession().getNamedQuery("findHistoryHousingByPrimaryKeys");
    query.setParameter("id", id, Hibernate.LONG);
    query.setParameter("month", month, Hibernate.INTEGER);
    query.setParameter("year", year, Hibernate.INTEGER);
    return (HistoryHousingEntity) query.uniqueResult();
  }

  @Override
  public HistoryHousingEntity getLatest(Long id, int month, int year) throws HistoryHousingDAOException {
    Query query = getSession().getNamedQuery("findLatestHistoryHousingByPrimaryKeys");
    query.setParameter("id", id, Hibernate.LONG);
    return (HistoryHousingEntity) query.setMaxResults(1).uniqueResult();
  }

  @Override
  public void historizeAllHousings() throws HistoryHousingDAOException {
    LocalDate now = new LocalDate();
    Integer month = now.getMonthOfYear();
    Integer year = now.getYear();

    try (Connection conn = dataSource.getConnection();
    	 CallableStatement stmt = conn.prepareCall("{call HISTORIZE_HOUSING(?,?)}");)
	{
      stmt.setInt(1, month);
      stmt.setInt(YEAR_PARAMETER_POSITION, year);
      stmt.execute();
    } catch (SQLException e) {
      log.error("Erreur inattendue : " + e);
      throw new HistoryHousingDAOException(e);
    }
  }

  @Override
  public void deleteOldHousings(int month, int year) throws HistoryHousingDAOException {
    Query query = getSession().getNamedQuery("deleteOldHousings");
    query.setParameter("month", month, Hibernate.INTEGER);
    query.setParameter("year", year, Hibernate.INTEGER);
    int countDeletedRows = query.executeUpdate();
    LOGGER.info(countDeletedRows + " housings deleted");
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<HistoryHousingEntity> getTemporaries(Long id) throws HistoryHousingDAOException {
    Query query = getSession().getNamedQuery("findTemporariesHousing");
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
  public void deleteAllHistoryHousingOfOneHousing(Long id) throws HistoryHousingDAOException {
    // On supprime les historisations
    Query query = getSession().getNamedQuery("deleteHistoryHousing");
    query.setParameter(HousingConstants.ID_HOUSING, id, Hibernate.LONG);
    query.executeUpdate();
  }

}
