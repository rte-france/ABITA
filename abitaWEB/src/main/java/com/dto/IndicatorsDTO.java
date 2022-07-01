package com.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Set of indicators
 * @author
 *
 */
public class IndicatorsDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7466773567670421953L;

	/** Indicator duration */
	private List<String[]> durations;

	/** Indicator actions */
	private List<String[]> counts;

	/** Indicator users */
	private List<String[]> users;

	/** Indicators user authorized */
	private List<String[]> authorized;

	/**
	 * Constructor
	 * @param durations Indicator duration
	 * @param counts Indicator actions
	 * @param users Indicator users
	 * @param authorized Indicators user authorized
	 */
	public IndicatorsDTO(List<String[]> durations, List<String[]> counts, List<String[]> users,
			List<String[]> authorized) {
		super();
		this.durations = durations;
		this.counts = counts;
		this.users = users;
		this.authorized = authorized;
	}

	/**
	 * Constructor
	 */
	public IndicatorsDTO() {
		super();
	}

	/**
	 * @return the durations
	 */
	public List<String[]> getDurations() {
		return durations;
	}

	/**
	 * @param durations the durations to set
	 */
	public void setDurations(List<String[]> durations) {
		this.durations = durations;
	}

	/**
	 * @return the counts
	 */
	public List<String[]> getCounts() {
		return counts;
	}

	/**
	 * @param counts the counts to set
	 */
	public void setCounts(List<String[]> counts) {
		this.counts = counts;
	}

	/**
	 * @return the users
	 */
	public List<String[]> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<String[]> users) {
		this.users = users;
	}

	/**
	 * @return the authorized
	 */
	public List<String[]> getAuthorized() {
		return authorized;
	}

	/**
	 * @param authorized the authorized to set
	 */
	public void setAuthorized(List<String[]> authorized) {
		this.authorized = authorized;
	}

}
