/**
 *
 */
package com.dao.batch.impl;

import com.dao.batch.IBatchDAO;
import com.dao.batch.entity.BatchHistorique;
import com.dao.batch.exception.BatchDAOException;
import com.dao.common.impl.AbstractGenericEntityDAO;
import com.services.batchservice.constants.BatchStatut;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author
 *
 */
public class BatchDAO extends AbstractGenericEntityDAO<BatchHistorique, BatchDAOException> implements Serializable,
		IBatchDAO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4294748944581735242L;

	/** logger */
	private static final Log LOG = LogFactory.getLog(BatchDAO.class);

	/* (non-Javadoc)
	 * @see com.dao.batch.IBatchDAO#findBatchHistoriqueByName(java.lang.String, int)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<BatchHistorique> findBatchHistoriqueByName(String name, int maxHisto) throws BatchDAOException {
		List<BatchHistorique> result;
		try {
			Criteria criteria = getSession().createCriteria(BatchHistorique.class);
			criteria.add(Restrictions.eq("name", name)).addOrder(Order.desc("dateLancement"));
			//Force le lazy à false de la collection.
			criteria.setFetchMode("messages", FetchMode.JOIN);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			// pas de maxResults avec le FetchMode.JOIN
			result = criteria.list();
			return result.subList(0, Math.min(result.size(), maxHisto));
		} catch (HibernateException hibernateException) {
			LOG.error("Hibernate error during getBatchHistoriqueByName", hibernateException);
			throw new BatchDAOException(hibernateException);
		}
	}

	/* (non-Javadoc)
	 * @see com.dao.batch.IBatchDAO#findBatchInProgress(java.lang.String, java.util.Date)
	 */
	@Override
	public List<BatchHistorique> findBatchInProgress(String batchName, Date minDate) throws BatchDAOException {
		try {
			Criteria criteria = getSession().createCriteria(BatchHistorique.class);
			criteria.add(Restrictions.eq("name", batchName)).add(Restrictions.gt("dateLancement", minDate))
					.add(Restrictions.eq("statut", BatchStatut.IN_PROGRESS)).addOrder(Order.desc("dateLancement"));
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			return criteria.list();
		} catch (HibernateException hibernateException) {
			LOG.error("Hibernate error during getBatchHistoriqueByName", hibernateException);
			throw new BatchDAOException(hibernateException);
		}
	}
}
