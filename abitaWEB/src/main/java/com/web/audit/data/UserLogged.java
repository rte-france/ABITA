package com.web.audit.data;

import com.web.audit.data.BasicDataModel;

import java.io.Serializable;
import java.util.Calendar;


/**
 * Logged user
 * @author
 *
 */
public class UserLogged extends BasicDataModel implements Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = 2095769403539324378L;

    /** area */
	private String area;

	/** Server time when it receives the message */
	private Calendar time;

	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}

	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}

	/**
	 * @return the time
	 */
	public Calendar getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Calendar time) {
		this.time = time;
	}

}
