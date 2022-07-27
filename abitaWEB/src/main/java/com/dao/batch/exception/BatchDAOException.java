/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.batch.exception;

import com.dao.common.exception.GenericDAOException;

/**
 * Exception du DAO BatchDAO
 * @author
 *
 */
public class BatchDAOException extends GenericDAOException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4283304471140173117L;

	/**
	 * Constructor.
	 */
	public BatchDAOException() {
		super();
	}

	/**
	 * Constructor.
	 * @param cause the cause
	 */
	public BatchDAOException(Exception cause) {
		super(cause);
	}

}
