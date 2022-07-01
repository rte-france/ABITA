package com.services.common.constants;

/**
 * File mime types constants
 * @author
 */
public final class MimeTypesConstants {

	/**
	 * Private constructor
	 */
	private MimeTypesConstants() {
		super();
	}

	/** Excel 2007 content type */
	public static final String CONTENT_TYPE_EXCEL_2007 = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	/** CONTENT_TYPE_PDF */
	public static final String CONTENT_TYPE_PDF = "application/pdf";

	/** CONTENT_TYPE_XML */
	public static final String CONTENT_TYPE_XML = "text/xml";

	/** CHARSET_FOR_CONTENT */
	public static final String CHARSET_FOR_CONTENT = "; charset=%s";
}
