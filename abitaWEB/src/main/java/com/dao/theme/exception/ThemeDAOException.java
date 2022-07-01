package com.dao.theme.exception;

import com.dao.common.exception.GenericDAOException;

/**
 * @author
 *
 */
public class ThemeDAOException extends GenericDAOException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6237835184973730095L;

	/**
	 * Constructor
	 * @param e exception
	 */
	public ThemeDAOException(Exception e) {
		super(e);
	}

	/**
	 * Constructor
	 */
	public ThemeDAOException() {
		super();
	}
}
