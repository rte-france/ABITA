package com.services.documentgenerationservice.data;

import java.io.InputStream;

/**
 * Class representing the configuration of the document. It will be represented by its path or an input stream, if both of them are
 * filled in the input stream will have priority.
 *
 * @author
 */
public class DocumentConfiguration {

	/** Path to the file */
	private String path;

	/** Input Stream of the template */
	private InputStream inputStream;

	/**
	 * Default constructor
	 */
	public DocumentConfiguration() {
		super();
	}

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
