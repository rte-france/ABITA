/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.audit.data;

import com.web.audit.data.BasicDataModel;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Data model for a Count action.
 * A count is the number of time a specific component has been clicked in an application
 * @author
 *
 */
public class Count extends BasicDataModel implements Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = 5925398167237079638L;

    /**
	 * The identifier of the clicked component.
	 */
	private String idComponent;

	/**
	 * Number of times this component has been clicked.
	 */
	private long count;

	/** Heure de l'action */
	private Calendar hour;

	/**
	 * @return the idComponent
	 */
	public String getIdComponent() {
		return idComponent;
	}

	/**
	 * @param idComponent the idComponent to set
	 */
	public void setIdComponent(String idComponent) {
		this.idComponent = idComponent;
	}

	/**
	 * @return the count
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * @return the hour
	 */
	public Calendar getHour() {
		return hour;
	}

	/**
	 * @param hour the hour to set
	 */
	public void setHour(Calendar hour) {
		this.hour = hour;
	}

}
