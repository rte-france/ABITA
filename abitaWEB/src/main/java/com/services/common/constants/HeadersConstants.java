package com.services.common.constants;

/**
 * HTTP Headers constants
 * @author
 */
public final class HeadersConstants {

	/**
	 * Private constructor
	 */
	private HeadersConstants() {
		super();
	}

	/** CONTENT_DISPOSITION_HEADER */
	public static final String CONTENT_DISPOSITION_HEADER = "Content-disposition";

	/** CONTENT_DISPOSITION_AS_ATTACHEMENT */
	public static final String CONTENT_DISPOSITION_AS_ATTACHEMENT = "attachment; filename=\"%s\"";
}