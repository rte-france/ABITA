package com.services.common.constants;

/**
 * Date and time constants
 * @author
 */
public final class DateTimeConstants {

	/**
	 * Private constructor
	 */
	private DateTimeConstants() {
		super();
	}

	/** FRENCH_DATE_FORMAT */
	public static final String FRENCH_DATE_FORMAT = "dd/MM/yyyy";

	/** HOUR_FORMAT */
	public static final String HOUR_FORMAT = "HH:mm:ss";

	/** Date format for saving the dates in the data files. */
	public static final String DATE_FORMAT_DATA_FILES = "ddMMyyyy HH:mm:ss";

	/** Date format for the file time stamps */
	public static final String TIME_STAMP_FILE_FORMAT = "yyyyMMdd";

	/** MILLISECONDS_IN_ONE_SECOND */
	public static final int MILLISECONDS_IN_ONE_SECOND = 1000;
}
