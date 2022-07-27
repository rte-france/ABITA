/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto;

import com.web.audit.data.OptionalParameter;

import java.io.Serializable;
import java.util.List;

/**
 * Event duration
 * @author
 *
 */
public class EventDurationDTO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7634584786179155971L;

	/** User's NNI */
	private String nniUser;

	/** Element's id */
	private String idElement;

	/** start time */
	private long startTime;

	/** end time */
	private long endTime;

	/** Optional parameters */
	private List<OptionalParameter> optionalParameters;

	/**
	 * Constructor
	 * @param nniUser User's NNI
	 * @param idElement Element's id
	 * @param startTime start time
	 * @param endTime end time
	 * @param optionalParameters Optional parameters
	 */
	public EventDurationDTO(String nniUser, String idElement, long startTime, long endTime,
			List<OptionalParameter> optionalParameters) {
		super();
		this.nniUser = nniUser;
		this.idElement = idElement;
		this.startTime = startTime;
		this.endTime = endTime;
		this.optionalParameters = optionalParameters;
	}

	/**
	 * Constructor
	 */
	public EventDurationDTO() {
		super();
	}

	/**
	 * @return the nniUser
	 */
	public String getNniUser() {
		return nniUser;
	}

	/**
	 * @param nniUser the nniUser to set
	 */
	public void setNniUser(String nniUser) {
		this.nniUser = nniUser;
	}

	/**
	 * @return the idElement
	 */
	public String getIdElement() {
		return idElement;
	}

	/**
	 * @param idElement the idElement to set
	 */
	public void setIdElement(String idElement) {
		this.idElement = idElement;
	}

	/**
	 * @return the startTime
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public long getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the optionalParameters
	 */
	public List<OptionalParameter> getOptionalParameters() {
		return optionalParameters;
	}

	/**
	 * @param optionalParameters the optionalParameters to set
	 */
	public void setOptionalParameters(List<OptionalParameter> optionalParameters) {
		this.optionalParameters = optionalParameters;
	}

}
