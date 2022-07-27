/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.domain.impl;

import com.dao.common.impl.AbstractGenericEntityDAO;
import com.dao.domain.IDomainDAO;
import com.dao.domain.entity.Domain;
import com.dao.domain.exception.DomainDAOException;
import com.services.common.exception.NotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.io.Serializable;

/**
 * A domain's DAO.
 * @author
 *
 */
public class DomainDAO extends AbstractGenericEntityDAO<Domain, DomainDAOException> implements Serializable, IDomainDAO {

	/** serialVersionUID */
	private static final long serialVersionUID = 4044027792839832962L;

	/** FIND_DOMAIN_BY_NAME */
	private static final String FIND_DOMAIN_BY_NAME = "findDomainByName";

	/** NAME */
	private static final String NAME = "name";

	/* (non-Javadoc)
	 * @see com.dao.domain.IDomainDAO#findDomainByName(java.lang.String)
	 */
	@Override
	public Domain findDomainByName(final String name) throws DomainDAOException, NotFoundException {
		try {
			Query query = getSession().getNamedQuery(FIND_DOMAIN_BY_NAME);
			query.setString(NAME, name);
			final Domain domain = (Domain) query.uniqueResult();
			if (domain == null) {
				throw new NotFoundException("Le domaine \"" + domain + "\" n'existe pas");
			}
			return domain;
		} catch (HibernateException hibernateException) {
			throw new DomainDAOException(hibernateException);
		}
	}
}
