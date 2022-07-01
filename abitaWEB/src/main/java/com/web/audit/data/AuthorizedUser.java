package com.web.audit.data;

import com.web.audit.data.BasicDataModel;

/**
 * Authorized user to application
 * @author
 *
 */
public class AuthorizedUser extends BasicDataModel {

	/** serialVersionUID */
    private static final long serialVersionUID = 4329239834237132656L;

    /** area */
	private String area;

	/** profile */
	private String profile;

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
	 *
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}

	/**
	 *
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

}
