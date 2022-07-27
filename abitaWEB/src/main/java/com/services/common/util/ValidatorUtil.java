/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.common.util;

import com.services.common.util.MessageSupport;
import org.apache.commons.validator.GenericValidator;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities of validation
 * @author
 *
 */
public final class ValidatorUtil {

	/**
	 * Constructor
	 */
	private ValidatorUtil() {
	}

	/** word pattern */
	private static final Pattern WORD_PATTERN = Pattern.compile("[\\w_\\.']*");

	/** word list pattern */
	private static final Pattern WORD_LIST_PATTERN = Pattern.compile("[\\w ]*");

	/** empty string */
	private static final String EMPTY_STRING = "";

	/**
	 * validate a word list.
	 * @param s string to validate
	 * @param allowEmpty true allows empty string
	 * @return true if string matches a list of words, false otherwise
	 */
	public static boolean validateWordList(String s, boolean allowEmpty) {
		if (!allowEmpty && GenericValidator.isBlankOrNull(s)) {
			return false;
		}
		Matcher m = WORD_LIST_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * validate
	 *  ex : BLA_name.FOO.
	 * @param s string to validate
	 * @return true if string matches a word, false otherwise
	 */
	public static boolean validateWord(String s) {
		Matcher m = WORD_PATTERN.matcher(s);
		return m.matches();
	}

	/**
	 * validate url.
	 * @param s string to validate
	 * @return true if string matches a url, false otherwise
	 */
	public static boolean validateUrl(String s) {
		return GenericValidator.isUrl(s);
	}

	/**
	 * check and convert if string parameter are int.
	 * @param name log parameter
	 * @param value the value to check
	 * @return an integer.
	 */
	public static int checkIntegerParameter(String name, String value) {
		if (value == null || value.equals(EMPTY_STRING)) {
			String msg = "'" + name + "' is a 'nil' object";
			throw new IllegalArgumentException(msg);
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException numberFormatException) {
			String msg = "'" + name + "' is not an integer object '" + value + "'";
			throw new IllegalArgumentException(msg, numberFormatException);
		}
	}

	/**
	 * check and convert if string parameter are long.
	 * @param name log parameter
	 * @param value the value to check
	 * @return a long.
	 */
	public static Long checkLongParameter(String name, String value) {
		if (value == null || value.equals(EMPTY_STRING)) {
			String msg = "'" + name + "' is a 'nil' object";
			throw new IllegalArgumentException(msg);
		}
		try {
			return Long.decode(value);
		} catch (NumberFormatException numberFormatException) {
			String msg = "'" + name + "' is not a long object '" + value + "'";
			throw new IllegalArgumentException(msg, numberFormatException);
		}
	}

	/**
	 * Check if user are define.
	 * @param user a context parameter of user.
	 */
	public static void checkUserParameter(String user) {
		if (user == null || user.equals(EMPTY_STRING)) {
			String msg = "'user' is a 'nil' object";
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * Check if string are empty.
	 * @param name test string name
	 * @param value test string value
	 */
	public static void checkEmptyString(String name, String value) {
		if (value == null || value.equals(EMPTY_STRING)) {
			String msg = "'" + name + "' is a 'nil' object";
			throw new IllegalArgumentException(msg);
		}
	}

	/**
	 * Construit une exception de validation
	 * @param severity sévérité du message
	 * @param bundle bundle à utiliser
	 * @param messageId identifiant du message
	 * @throw ValidatorException
	 */
	public static void constructValidatorException(Severity severity, String bundle, String messageId) {
		FacesMessage message = MessageSupport.getMessage(severity, bundle, messageId, null);
		throw new ValidatorException(message);
	}

}
