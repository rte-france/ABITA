/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author
 *
 */
public class UserInfoDTO implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -7602732339875792342L;

	/** a user properties map (key, value) using String */
	private Map<String, String> properties = new HashMap<String, String>();
	/**
	 * mapping key for UID
	 */
	public static final String UID_KEY = "logicalId";
	/**
	 * mapping key for login
	 */
	public static final String LOGIN_KEY = "login";
	/**
	 * mapping key for firstName
	 */
	public static final String FIRST_NAME_KEY = "firstName";
	/**
	 * mapping key for lastName
	 */
	public static final String LAST_NAME_KEY = "lastName";
	/**
	 * mapping key for location
	 */
	public static final String LOCATION_KEY = "location";
	/**
	 * mapping key for email
	 */
	public static final String EMAIL_KEY = "email";
	/**
	 * mapping key for disabled
	 */
	public static final String DISABLED = "disabled";
	/**
	 * mapping key for expired
	 */
	public static final String EXPIRED = "expired";
	/**
	 * mapping key for phoneNumber
	 */
	public static final String PHONE_KEY = "phone";
	/**
	 * mapping key for faxNumber
	 */
	public static final String FAX_KEY = "fax";

	/**
	 * return mapped properties of user info.
	 * @return mapped properties of user info.
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

}
