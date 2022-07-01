package com.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Class used to represent the available filter values
 *
 * @author
 */
public class ActionLogFilterDTO implements Serializable {
	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = 4355803819519739646L;

	/** Date à partir de laquelle les logs doivent être cherchés */
	private Date from;

	/** Date jusqu'à laquelle les logs doivent être cherchés */
	private Date to;

	/** L'origine */
	private String origin;

	/** Le status */
	private String status;

	/**
	 * @return the from
	 */
	public Date getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(Date from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public Date getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(Date to) {
		this.to = to;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
