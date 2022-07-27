/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.exception;

import com.web.common.exception.ManageExceptionHandler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Exception handler factory class
 * @author
 */
public class ManageExceptionHandlerFactory extends ExceptionHandlerFactory {

    /** parent exception handler factory */
	private ExceptionHandlerFactory parent;

	/**
	 * Constructor
	 * @param parent parent factory
	 */
	public ManageExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler result = parent.getExceptionHandler();
		result = new ManageExceptionHandler(result);
		return result;
	}
}
