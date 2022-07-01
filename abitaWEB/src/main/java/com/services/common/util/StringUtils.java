package com.services.common.util;

import org.apache.commons.validator.GenericValidator;

/**
 * Classe utilitaire de gestion des String
 * @author
 *
 */
public final class StringUtils {

	/**
	 * Private constructor
	 */
	private StringUtils() {
	}

	/**
	 * Teste si une chaine est vide ou nulle
	 * @param chain la chaine à tester
	 * @return vrai si la chaine est vide ou nulle
	 */
	public static boolean isEmptyOrBlank(String chain) {
		return chain == null || chain.trim().length() == 0;
	}

	/**
	 * Converti un String en Integer
	 * @param value valeur en String
	 * @return valeur en Integer
	 */
	public static Integer string2Integer(String value) {
		Integer res = null;
		if (!com.services.common.util.StringUtils.isEmptyOrBlank(value)) {
			value = value.trim();
			if (GenericValidator.isInt(value)) {
				res = Integer.valueOf(value);
			}
		}
		return res;
	}

	/**
	 * Converti un String en un booléen
	 * @param value valeur à convertir
	 * @return le booléen
	 */
	public static Boolean string2Boolean(String value) {
		Boolean res = null;

		if (!com.services.common.util.StringUtils.isEmptyOrBlank(value)) {
			value = value.trim();
			res = new Boolean(value);
		}

		return res;
	}

	/**
	 * Truncate string, keeping first specified length first chars
	 *
	 * @param value the string to truncate
	 * @param length the length obtained
	 * @return the string truncated
	 */
	public static String truncateString(String value, int length) {
		String ret = value;
		if (ret != null && ret.length() > length) {
			ret = ret.substring(0, length);
		}
		return ret;
	}

	/**
	 * Gets the value, or blank if is null.
	 *
	 * @param value the value
	 * @return the non null value
	 */
	public static String getNonNullValue(String value) {
		String res = value;
		if (res == null) {
			res = "";
		}
		return res;

	}
}
