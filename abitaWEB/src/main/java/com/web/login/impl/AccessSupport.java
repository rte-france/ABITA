package com.web.login.impl;

import com.dto.UserDTO;
import com.services.common.constants.Constants;
import com.web.login.impl.ThreadUserHolder;

import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * An access. support
 * @author
 *
 */
public final class AccessSupport {

	/**
	 * Constructor
	 */
	private AccessSupport() {
		super();
	}

	/**
	 * @return the current user or null if the there is not current user
	 */
	public static UserDTO getCurrentUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (facesContext != null) {
			Map<?, ?> sessionMap = facesContext.getExternalContext().getSessionMap();
			return (UserDTO) sessionMap.get(Constants.USER_KEY);
		} else {
			return ThreadUserHolder.get();
		}
	}
}
