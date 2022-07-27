/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.historybenefits.impl;

import com.abita.dao.historybenefits.entity.HistoryBenefitsEntity;
import com.abita.dao.historybenefits.exceptions.HistoryBenefitsDAOException;
import com.abita.dao.historybenefits.IHistoryBenefitsDAO;
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
 * Classe d’implémentation des DAO de l’historisation des avantages en nature
 */
public class HistoryBenefitsDAOImpl extends AbstractGenericEntityDAO<HistoryBenefitsEntity, HistoryBenefitsDAOException> implements IHistoryBenefitsDAO {

  /** Position du paramètre de l’année dans la requête */
  private static final int YEAR_PARAMETER_POSITION = 2;

  /** Source de données */
  private DataSource dataSource;

  /** Serial ID */
  private static final long serialVersionUID = -625792907093182775L;

  /** logger de classe. */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistoryBenefitsDAOImpl.class);

	@Override
	public void historizeAllBenefits() throws HistoryBenefitsDAOException {
		LocalDate now = new LocalDate();
		Integer month = now.getMonthOfYear();
		Integer year = now.getYear();

		try (Connection conn = dataSource.getConnection();
			 CallableStatement stmt = conn.prepareCall("{call HISTORIZE_BENEFITS(?,?)}");)
		{
			stmt.setInt(1, month);
			stmt.setInt(YEAR_PARAMETER_POSITION, year);
			stmt.execute();
		} catch (SQLException e) {
			log.error("Erreur inattendue : " + e);
			throw new HistoryBenefitsDAOException(e);
		}
	}

  @Override
  public void deleteOldBenefits(int month, int year) throws HistoryBenefitsDAOException {
    Query query = getSession().getNamedQuery("deleteOldBenefits");
    query.setParameter("month", month, Hibernate.INTEGER);
    query.setParameter("year", year, Hibernate.INTEGER);
    int countDeletedRows = query.executeUpdate();
    LOGGER.info(countDeletedRows + " benefits deleted");
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<HistoryBenefitsEntity> get(int month, int year) throws HistoryBenefitsDAOException {
    Query query = getSession().getNamedQuery("findHistoryBenefits");
    query.setParameter("month", month, Hibernate.INTEGER);
    query.setParameter("year", year, Hibernate.INTEGER);
    return query.list();
  }

  /**
   * @param dataSource the dataSource to set
   */
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

}
