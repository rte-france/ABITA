/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * @author
 *
 */
public final class RequestParameterHolder {

	/**
	 * Constructor
	 */
	private RequestParameterHolder() {
		super();
	}

	/**
	 * shortcut for request.setAttibute
	 * @param name attribute name
	 * @param value attribute value
	 */
	public static void setRequestParameter(String name, Object value) {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		req.setAttribute(name, value);
	}

	/**
	 * shortcut for request.getAttibute
     * @param name attribute name
	 * @return attribute value
	 */
	public static Object getRequestParameter(String name) {
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		return req.getAttribute(name);
	}

	/**
	 * shortcut for RequestParameterMap.put()
     * @param name attribute name
     * @param value attribute value
	 */
	public static void setRequestParameter2(String name, Object value) {
		Map<String, String> parameters = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		parameters.put(name, String.valueOf(value));
	}

	/**
	 * shortcut for RequestParameterMap.get()
     * @param name attribute name
	 * @return attribute value
	 */
	public static Object getRequestParameter2(String name) {
		Map<String, String> parameters = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		return parameters.get(name);
	}

}
