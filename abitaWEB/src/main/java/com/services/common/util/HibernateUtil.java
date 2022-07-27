/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.common.util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.JDBCExceptionHelper;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author
 *
 */
public final class HibernateUtil {

	/** ERROR_CODE_FK_VIOLATION */
	private static final int ERROR_CODE_FK_VIOLATION = 2291;

	/** ERROR_CODE_CONSTRAINT_VIOLATION */
	private static final int ERROR_CODE_CONSTRAINT_VIOLATION = 2292;

	/** ERROR_CODE_NULLABILITY_CONSTRAINT */
	private static final int ERROR_CODE_NULLABILITY_CONSTRAINT = 1400;

	/** ERROR_CODE_DUPLICATE_ENTRY */
	private static final int ERROR_CODE_DUPLICATE_ENTRY = 1062;

	/** ERROR_CODE_ */
	private static final int ERROR_CODE_UNIQUE_CONSTRAINT_VIOLATED = 1;

	/**
	 * Private constructor
	 */
	private HibernateUtil() {
		super();
	}

	/**
	 * extract the contraint name part of an SQL exception.
	 * @param e sql exception
	 * @return constraint name
	 */
	public static String extractConstraintName(SQLException e) {
		int errorCode = JDBCExceptionHelper.extractErrorCode(e);
		if (errorCode == ERROR_CODE_UNIQUE_CONSTRAINT_VIOLATED || errorCode == ERROR_CODE_FK_VIOLATION
				|| errorCode == ERROR_CODE_CONSTRAINT_VIOLATION) {
			Pattern p = Pattern.compile(".*\\((.*)\\)");
			Matcher m = p.matcher(e.getMessage());
			if (m.find()) {
				return m.group(1);
			} else {
				return null;
			}
		} else if (errorCode == ERROR_CODE_NULLABILITY_CONSTRAINT) {
			// simple nullability constraint
			return null;
		} else {
			return null;
		}
	}

	/**
	 * extract the contraint's name of an SQL exception.
	 * @param e sql exception
	 * @return constraint name
	 */
	public static String extractConstraintName2(SQLException e) {
		int errorCode = JDBCExceptionHelper.extractErrorCode(e);
		if (errorCode == 1 || errorCode == ERROR_CODE_FK_VIOLATION || errorCode == ERROR_CODE_CONSTRAINT_VIOLATION) {
			Pattern p = Pattern.compile(".*\\(.*\\.(.*)\\)");
			Matcher m = p.matcher(e.getMessage());
			if (m.find()) {
				return m.group(1);
			} else {
				return null;
			}
		} else if (errorCode == ERROR_CODE_NULLABILITY_CONSTRAINT) {
			// simple nullability constraint
			return null;
		} else {
			return null;
		}
	}

	/**
	 * extract the contraint key of SQLException.
	 * @param e sql exception
	 * @return mysql constraint key
	 */
	public static int extractMySQLConstraintKey(SQLException e) {
		int errorCode = JDBCExceptionHelper.extractErrorCode(e);
		if (errorCode == ERROR_CODE_DUPLICATE_ENTRY) {
			Pattern p = Pattern.compile(".*key (.*)");
			Matcher m = p.matcher(e.getMessage());
			if (m.find()) {
				return Integer.valueOf(m.group(1));
			}
		}
		return StringUtils.INDEX_NOT_FOUND;
	}
}
