package com.dao.domain;

import com.dao.common.IAbstractDAO;
import com.dao.domain.entity.Domain;
import com.dao.domain.exception.DomainDAOException;
import com.services.common.exception.NotFoundException;

/**
 * DomainDAO Interface
 * @author Administrateur
 *
 */
public interface IDomainDAO extends IAbstractDAO<Domain, DomainDAOException> {

	/**
	 * Find domain by name
	 * @param name domain name
	 * @return return domain by his name.
	 * @throws DomainDAOException if error occurs
	 * @throws NotFoundException Si le domaine n'existe pas
	 */
	Domain findDomainByName(String name) throws DomainDAOException, NotFoundException;

}
