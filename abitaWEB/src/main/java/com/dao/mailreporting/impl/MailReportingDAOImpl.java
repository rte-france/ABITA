/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.mailreporting.impl;

import com.dao.common.impl.AbstractGenericEntityDAO;
import com.dao.mailreporting.MailReportingDAO;
import com.dao.mailreporting.constants.MailReportingDAOConstants;
import com.dao.mailreporting.entity.MailReporting;
import com.dao.mailreporting.exception.MailReportingDAOException;
import com.dto.mail.MailSearchFilterDTO;
import com.dto.mail.MailSendStatus;
import com.services.common.exception.NotFoundException;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Implementation of {@link MailReportingDAO}
 *
 * @author
 */
public class MailReportingDAOImpl extends AbstractGenericEntityDAO<MailReporting, MailReportingDAOException> implements
        MailReportingDAO {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 6376335217781745355L;

	/* (non-Javadoc)
	 * @see com.dao.mailreporting.MailReportingDAO#get(java.lang.Long)
	 */
	@Override
	public MailReporting get(final Long id) throws MailReportingDAOException, NotFoundException {
		if (id == null) {
			throw new NullArgumentException("L'identifiant ne doit pas être nul");
		}
		final MailReporting entity = super.get(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.dao.mailreporting.MailReportingDAO#delete(com.dao.mailreporting.entity.MailReporting)
	 */
	@Override
	public void delete(final MailReporting mailReportingEntity) throws MailReportingDAOException {
		if (mailReportingEntity == null || mailReportingEntity.getId() == null) {
			throw new IllegalArgumentException("L'entité et son identifiant ne doivent pas être nuls");
		}
		super.delete(mailReportingEntity.getId());
	}

	/* (non-Javadoc)
	 * @see com.dao.mailreporting.MailReportingDAO#find(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MailReporting> find(final MailSearchFilterDTO filter) throws MailReportingDAOException {
		final Criteria criteria = this.buildCriteriaFromFilter(filter);
		if (criteria == null) {
			return this.find();
		}

		try {
			return criteria.list();
		} catch (HibernateException hibernateException) {
			log.error("Erreur pendant le filtrage des envois de mail");
			throw new MailReportingDAOException(hibernateException);
		}
	}

	/**
	 * Creates and returns a criteria from the specified filter.
	 * If the filter is not of the good type, null is returned.
	 *
	 * @param filter The object used to filter
	 *
	 * @return the criteria or null
	 */
	private Criteria buildCriteriaFromFilter(MailSearchFilterDTO filter) {
		if (filter == null) {
			return null;
		}

		final Criteria criteria = this.getSession().createCriteria(getSpecificEntityClass());

		if (filter.getSentAfter() != null) {
			criteria.add(Restrictions.ge(MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_DATE_SENT,
					filter.getSentAfter()));
		}

		if (filter.getSentBefore() != null) {
			criteria.add(Restrictions.lt(MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_DATE_SENT,
					filter.getSentBefore()));
		}

		if (!StringUtils.isBlank(filter.getSender())) {
			criteria.add(Restrictions.ilike(MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_SENDER,
					filter.getSender(), MatchMode.ANYWHERE));
		}

		if (!StringUtils.isBlank(filter.getSubject())) {
			criteria.add(Restrictions.ilike(MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_SUBJECT,
					filter.getSubject(), MatchMode.ANYWHERE));
		}

		if (filter.getStatus() != MailSendStatus.ALL) {
            criteria.add(Restrictions.eq(MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_STATUS, filter.getStatus().getValue()));
		}

        if (!StringUtils.isBlank(filter.getErrorMessage())) {
            criteria.add(Restrictions.ilike(MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_ERROR_MESSAGE,
                    filter.getErrorMessage(), MatchMode.ANYWHERE));
        }

		if (!StringUtils.isBlank(filter.getRecipients())) {
			criteria.add(Restrictions.ilike(MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_RECIPIENTS,
					filter.getRecipients(), MatchMode.ANYWHERE));
		}

		if (!StringUtils.isBlank(filter.getOtherRecipients())) {
			criteria.add(Restrictions.or(Restrictions.ilike(
					MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_COPY_RECIPIENTS,
					filter.getOtherRecipients(), MatchMode.ANYWHERE), Restrictions.ilike(
					MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_BLIND_COPY_RECIPIENTS,
					filter.getOtherRecipients(), MatchMode.ANYWHERE)));
		}

		if (filter.getAttachments()) {
			criteria.add(Restrictions.gt(MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_ATTACHMENTS, 0));
		}

        criteria.addOrder(Order.desc(MailReportingDAOConstants.MAIL_REPORTING_PROPERTY_DATE_SENT));

		return criteria;
	}

	/* (non-Javadoc)
	 * @see com.dao.common.impl.AbstractGenericEntityDAO#create(com.dao.common.entity.AbstractEntity)
	 */
	@Override
	public Long create(final MailReporting mailReportingEntity) throws MailReportingDAOException {
		if (mailReportingEntity == null) {
			throw new NullArgumentException("L'entité ne doit pas être nulle");
		}
		return (Long) super.create(mailReportingEntity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getErrorMessages() throws MailReportingDAOException {
		try {
			return this.getSession().getNamedQuery(MailReportingDAOConstants.QUERY_GET_ERROR_MESSAGES).list();
		} catch (HibernateException hibernateException) {
			log.error("Erreur pendant la récupération de la liste des codes d'erreur");
			throw new MailReportingDAOException(hibernateException);
		}
	}
}
