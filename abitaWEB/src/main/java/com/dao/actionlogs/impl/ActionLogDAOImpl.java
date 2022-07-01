package com.dao.actionlogs.impl;

import com.dao.actionlogs.IActionLogDAO;
import com.dao.actionlogs.entity.ActionLog;
import com.dao.actionlogs.exception.ActionLogDAOException;
import com.dao.common.entity.AbstractEntity;
import com.dao.common.impl.AbstractGenericEntityDAO;
import com.dto.ActionLogFilterDTO;
import com.services.common.util.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

/**
 * Implementation of {@link IActionLogDAO}
 *
 * Implements the common operations of the data layer from the service layer
 *
 * Exigences liées :
 * -	RM_LOG_002 : affichage, filtrage
 * -	RM_LOG_003 : récupération des informations d'un log
 *
 * @author
 */
public class ActionLogDAOImpl extends AbstractGenericEntityDAO<ActionLog, ActionLogDAOException> implements
		IActionLogDAO {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 8950435815191068934L;

	/* (non-Javadoc)
	 * @see com.dao.actionlogs.entity.IActionLogDAO#create(com.dao.actionlogs.entity.ActionLog)
	 */
	@Override
	public Long create(ActionLog actionLog) throws ActionLogDAOException {
		return (Long) super.create(actionLog);
	}

	/* (non-Javadoc)
	 * @see com.dao.actionlogs.entity.IActionLogDAO#update(com.dao.actionlogs.entity.ActionLog)
	 */
	@Override
	public ActionLog update(ActionLog actionLog) throws ActionLogDAOException {
		ActionLog mergedActionLog = null;
		try {
			mergedActionLog = (ActionLog) getSession().merge(actionLog);
			getSession().flush();
		} catch (HibernateException hibernateException) {
			throw new ActionLogDAOException(hibernateException.getMessage(), hibernateException);
		}
		return mergedActionLog;
	}

	/* (non-Javadoc)
	 * @see com.dao.actionlogs.entity.IActionLogDAO#get(java.lang.Long)
	 */
	@Override
	public ActionLog get(Serializable id) throws ActionLogDAOException {
		return super.get(id);
	}

	/* (non-Javadoc)
	 * @see com.dao.actionlogs.entity.IActionLogDAO#find()
	 */
	@Override
	public List<ActionLog> find() throws ActionLogDAOException {
		return super.find();
	}

	/* (non-Javadoc)
	 * @see com.dao.actionlogs.entity.IActionLogDAO#find(com.services.reporting.ActionLogFilterDTO)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<ActionLog> find(Object filters) throws ActionLogDAOException {
		if (filters == null) {
			log.warn("Null argument, default to find() method");
			return this.find();
		}

		Criteria criteria = buildCriteriaFromFilter(filters, getSpecificEntityClass());
		criteria.addOrder(Order.asc("date"));
		try {
			return criteria.list();
		} catch (HibernateException hibernateException) {
			log.error("Error while retrieving filtered objects", hibernateException);
			ActionLogDAOException actionLogDAOException = this.createException(hibernateException);
			throw actionLogDAOException;
		}
	}

	@Override
	public int getCount(Object filters) throws ActionLogDAOException {
		if (filters == null) {
			return this.getCount();
		}

		try {
			Criteria criteria = buildCriteriaFromFilter(filters, getSpecificEntityClass());
			criteria.setProjection(Projections.rowCount());
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);

			Object result = criteria.uniqueResult();
			if (result != null) {
				return ((Integer) result).intValue();
			}
			return 0;
		} catch (HibernateException hibernateException) {
			ActionLogDAOException alde = this.createException(hibernateException);
			log.error("Error while retrieving number of filtered entities");
			throw alde;
		}
	}

	/**
	 * Build a criteria from the specified filter
	 *
	 * @param filters The filter to convert to a request
	 *
	 * @param clazz The destination class
	 *
	 * @return The criteria
	 */
	protected Criteria buildCriteriaFromFilter(Object filters, Class<? extends AbstractEntity> clazz) {
		Criteria criteria = getSession().createCriteria(clazz);

		if (filters instanceof ActionLogFilterDTO) {
			ActionLogFilterDTO actionLogFilter = (ActionLogFilterDTO) filters;
			if (actionLogFilter.getFrom() != null) {
				criteria.add(Restrictions.ge("date", actionLogFilter.getFrom()));
			}
			if (actionLogFilter.getTo() != null) {
				criteria.add(Restrictions.le("date", actionLogFilter.getTo()));
			}
			if (!StringUtils.isEmptyOrBlank(actionLogFilter.getOrigin())) {
				criteria.add(Restrictions.ilike("origin", "%" + actionLogFilter.getOrigin() + "%"));
			}
			if (!StringUtils.isEmptyOrBlank(actionLogFilter.getStatus())) {
				criteria.add(Restrictions.eq("status", actionLogFilter.getStatus()));
			}
		}

		return criteria;
	}
}
