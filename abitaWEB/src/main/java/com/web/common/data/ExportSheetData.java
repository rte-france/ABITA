package com.web.common.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Donn�e � exporter
 * @author
 *
 */
public class ExportSheetData implements Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = 910519507770104546L;

    /** title */
	private String title;

	/** headers */
	private List<String> headers;

	/** lines */
	private List<List<String>> lines;

	/**
	 * Constructeur
	 */
	public ExportSheetData() {
		headers = new ArrayList<String>();
		lines = new ArrayList<List<String>>();
	}

	/**
	 * Getter de title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter de title
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter de headers
	 * @return the headers
	 */
	public List<String> getHeaders() {
		return headers;
	}

	/**
	 * Setter de headers
	 * @param headers the headers to set
	 */
	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	/**
	 * Getter de lines
	 * @return the lines
	 */
	public List<List<String>> getLines() {
		return lines;
	}

	/**
	 * Setter de lines
	 * @param lines the lines to set
	 */
	public void setLines(List<List<String>> lines) {
		this.lines = lines;
	}
}
