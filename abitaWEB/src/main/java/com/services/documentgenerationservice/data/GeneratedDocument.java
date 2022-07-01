package com.services.documentgenerationservice.data;

/**
 * Class representing the generated document
 *
 * @author
 *
 * @param <T> specific object representing the content of the document, no constraints are fixed for this type so any library model can be used
 */
public class GeneratedDocument<T> {

	/** Generated document */
	private T document;

	/**
	 * @return the document
	 */
	public T getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(T document) {
		this.document = document;
	}

}
