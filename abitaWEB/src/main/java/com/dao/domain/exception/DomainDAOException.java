/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.domain.exception;

import com.dao.common.exception.GenericDAOException;

/**
 *
 * @author
 *
 */
public class DomainDAOException extends GenericDAOException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param e exception
	 */
	public DomainDAOException(Exception e) {
		super(e);
	}

	/**
	 * Constructor
	 */
	public DomainDAOException() {
		super();
	}

}
