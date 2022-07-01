package com.dao.audit.exception;

/**
 * The Class AuditException.
 */
public class AuditException extends Exception {

	/** serialUIDVersion for serialization. */
	private static final long serialVersionUID = -1312914927796926197L;

	/**
	 * Instantiates a new audit exception.
	 *
	 * @param e the e
	 */
	public AuditException(Exception e) {
		super(e);
	}
}
