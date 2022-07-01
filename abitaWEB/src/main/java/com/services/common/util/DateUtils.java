package com.services.common.util;

import com.services.common.constants.DateTimeConstants;
import com.services.common.exception.UnexpectedException;
import com.services.common.util.MessageSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe utilitaire concernant les dates
 * @author
 *
 */
public final class DateUtils {

	/**
	 * Private constructor
	 */
	private DateUtils() {
		super();
	}

	/**
	 * Converti une date au format français : dd/MM/yyyy
	 * @param date date à convertir
	 * @return chaine de caractère représentant la date au format dd/MM/yyyy
	 */
	public static String getDateFr(Date date) {
		String res = null;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(DateTimeConstants.FRENCH_DATE_FORMAT,
					MessageSupport.getLocale());
			res = formatter.format(date);
		} else {
			res = "";
		}
		return res;
	}

	/**
	 * Transforme la date dans le format donné
	 * @param date date
	 * @param format format
	 * @return date formattée
	 */
	public static String getDateFormat(Date date, String format) {
		String res = null;
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(format, MessageSupport.getLocale());
			res = formatter.format(date);
		} else {
			res = "";
		}
		return res;
	}

	/**
	 * Transforme la chaine de caractère au format dd/MM/yyyy en un objet Date
	 * @param value chaine de caractère au format dd/MM/yyyy
	 * @return Date
	 */
	public static Date getDateValue(String value) {
		SimpleDateFormat formatter = new SimpleDateFormat(DateTimeConstants.FRENCH_DATE_FORMAT,
				MessageSupport.getLocale());
		try {
			return formatter.parse(value);
		} catch (ParseException parseException) {
			throw new UnexpectedException(parseException);
		}
	}

	/**
	 * Récupère le numéro du jour du mois de la date (Le premier jour est à 1)
	 * @param date date à analyser
	 * @return le numéro du jour du mois
	 */
	public static int getDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Récupère le numéro du mois de la date (Janvier correspond à 0)
	 * @param date date à analyser
	 * @return le numéro du mois
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH);
	}

	/**
	 * Récupère l'année de la date
	 * @param date date à analyser
	 * @return l'année
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

}
