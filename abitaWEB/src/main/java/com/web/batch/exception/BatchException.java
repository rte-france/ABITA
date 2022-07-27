/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.batch.exception;

/**
 * The Class BatchException.
 */
public class BatchException extends Exception {

	/** serialUIDVersion for serialization. */
	private static final long serialVersionUID = 7780013570588576615L;

	/**
	 * Instantiates a new batch exception.
	 *
	 * @param e the e
	 */
	public BatchException(Exception e) {
		super(e);
	}
}
