package com.web.audit.data;

import com.web.audit.data.BasicDataModel;

import java.io.Serializable;
import java.util.Calendar;


/**
 * Data model for an action duration
 * @author
 *
 */
public class Duration extends BasicDataModel implements Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = -8381569264624353744L;

    /** component's id */
	private String idComponent;

	/** numero de version */
	// FFT 3563 ajout numero de version
	private String siteVersion;

	// FFT 3563 Suppression de l'id technique
	//private String idTransaction;

	/** time */
	private long time;

	/** Hour */
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
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
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

	/**
	 * @return siteVersion
	 */
	public String getSiteVersion() {
		return siteVersion;
	}

	/**
	 * @param siteVersion the siteVersion to set
	 */
	public void setSiteVersion(String siteVersion) {
		this.siteVersion = siteVersion;
	}

}
