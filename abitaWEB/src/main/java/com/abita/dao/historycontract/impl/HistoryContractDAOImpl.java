/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.historycontract.impl;

import com.abita.dao.contract.constants.ContractConstants;
import com.abita.dao.historycontract.exceptions.HistoryContractDAOException;
import com.abita.dao.historycontract.IHistoryContractDAO;
import com.abita.dao.historycontract.entity.HistoryContractEntity;
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

/**
 * Classe d'implémentation des DAO pour l'historisation des contrats occupant
 * @author
 *
 */
public class HistoryContractDAOImpl extends AbstractGenericEntityDAO<HistoryContractEntity, HistoryContractDAOException> implements IHistoryContractDAO {

  /** Source de données */
  private DataSource dataSource;

  /** Position du paramètre de l’année dans la requête */
  private static final int YEAR_PARAMETER_POSITION = 2;

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1290040730419913290L;

  /** logger de classe. */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistoryContractDAOImpl.class);

  @Override
  public HistoryContractEntity get(Long id, int month, int year) throws HistoryContractDAOException {
    Query query = getSession().getNamedQuery("findHistorizeContractByPrimaryKeys");
    query.setParameter("id", id, Hibernate.LONG);
    query.setParameter("month", month, Hibernate.INTEGER);
    query.setParameter("year", year, Hibernate.INTEGER);
    HistoryContractEntity historyContract = (HistoryContractEntity) query.uniqueResult();
    if (historyContract != null) {
      getSession().refresh(historyContract);
    }
    return historyContract;
  }

  @Override
  public void historizeAllContracts() throws HistoryContractDAOException {
    LocalDate now = new LocalDate();
    Integer month = now.getMonthOfYear();
    Integer year = now.getYear();

	try (Connection conn = dataSource.getConnection();
		 CallableStatement stmt = conn.prepareCall("{call HISTORIZE_CONTRACT(?,?)}");)
	{
		stmt.setInt(1, month);
		stmt.setInt(YEAR_PARAMETER_POSITION, year);
		stmt.execute();
	} catch (SQLException e) {
		log.error("Erreur inattendue : " + e);
		throw new HistoryContractDAOException(e);
	}
  }

  @Override
  public void deleteOldContracts(int month, int year) throws HistoryContractDAOException {
    Query query = getSession().getNamedQuery("deleteOldContracts");
    query.setParameter("month", month, Hibernate.INTEGER);
    query.setParameter("year", year, Hibernate.INTEGER);
    int countDeletedRows = query.executeUpdate();
    LOGGER.info(countDeletedRows + " contracts deleted");
  }

  @Override
  public void deleteAllTemporaryContractHistorisations(Long id) throws HistoryContractDAOException {
    Query query = getSession().getNamedQuery("deleteAllTemporaryContractHistorisations");
    query.setParameter("id", id, Hibernate.LONG);
    int countDeletedRows = query.executeUpdate();
    LOGGER.info(countDeletedRows + " contracts deleted");
  }

  /**
   * @param dataSource the dataSource to set
   */
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void deleteAllHistoryContractOfOneContract(Long id) throws HistoryContractDAOException {
    // On supprime les historisations
    Query query = getSession().getNamedQuery("deleteHistoryContract");
    query.setParameter(ContractConstants.ID_CONTRACT, id, Hibernate.LONG);
    query.executeUpdate();
  }

}
