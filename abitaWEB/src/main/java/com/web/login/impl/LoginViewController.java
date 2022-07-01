package com.web.login.impl;

import com.dto.UserDTO;
import com.services.access.IAccessServiceFacade;
import com.services.access.exception.AccessServiceFacadeException;
import com.services.authenticatorservice.exception.InvalidAccountStateException;
import com.services.authenticatorservice.exception.LoginException;
import com.services.common.constants.Constants;
import com.services.common.util.MessageSupport;
import com.web.common.constants.AccessConstants;
import com.web.login.impl.ThreadUserHolder;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * Contrôleur pour la gestion de la connexion
 *
 * @author
 */
public class LoginViewController implements Serializable {

	/** logger */
	private static final Log LOG = LogFactory.getLog(LoginViewController.class);
	/** serialVersionUID */
	private static final long serialVersionUID = -6841534452960600930L;

	/** Id of the message for a disabled account error. */
	private static final String LOGIN_USER_DISABLED_SMS_ID = "login.user.disabled";

	/** Id of the message for an error with the login or password. */
	private static final String LOGIN_FAILURE_SMS_ID = "login_failure";

	/** Form login. */
	private String login = null;

	/** Form password. */
	private String passwd = null;

	/** View Id of next page */
	private String page = DEFAULT_PAGE;

	/**
	 * the PAGES_WELCOME_WELCOME
	 */
	private static final String DEFAULT_PAGE = "/pages/welcome/welcome";

	/** Facade pour la gestion de la connexion et de l'utilisateur connecté */
	private IAccessServiceFacade accessServiceFacade;

	/**
	 * action of login.
	 * @return outcome for processed login
	 */
	public String processLogin() {
		try {
			UserDTO user = this.accessServiceFacade.login(this.getLogin(), this.getPasswd());
			if (user == null || user.getGroups() == null || user.getGroups().size() == 0) {
				MessageSupport.addMessage(FacesMessage.SEVERITY_WARN, AccessConstants.ACCESS_BUNDLE,
						"login_without_permission");
				return Constants.FAILURE_OUTCOME;
			}
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.USER_KEY, user);
			ThreadUserHolder.set(user);
			return Constants.SUCCESS_OUTCOME;
		} catch (AccessServiceFacadeException accessServiceFacadeException) {
            LOG.error(accessServiceFacadeException.getMessage());
			if (ExceptionUtils.getRootCause(accessServiceFacadeException) instanceof InvalidAccountStateException) {
                MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE,
                    LOGIN_USER_DISABLED_SMS_ID);
            } else {
                MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE, LOGIN_FAILURE_SMS_ID);
            }
			return Constants.FAILURE_OUTCOME;
		}
	}

	/**
	 * action of logout
	 * @return logout outcome
	 */
	public String processLogout() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(Constants.USER_KEY);
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
		return Constants.SUCCESS_OUTCOME;
	}

	/**
	 * @return the page
	 */
	public String getPage() {
		return this.page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(final String page) {
		if (page == null) {
			this.page = DEFAULT_PAGE;
		} else {
			this.page = page;
		}

	}

	/**
	 * getter of login input.
	 * @return login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * setter of login input.
	 * @param login login
	 */
	public void setLogin(final String login) {
		this.login = login;
	}

	/**
	 * getter of password input.
	 * @return password
	 */
	public String getPasswd() {
		return this.passwd;
	}

	/**
	 * setter of password input.
	 * @param passwd password
	 */
	public void setPasswd(final String passwd) {
		this.passwd = passwd;
	}

	/**
	 * @param accessServiceFacade the accessServiceFacade to set
	 */
	public void setAccessServiceFacade(final IAccessServiceFacade accessServiceFacade) {
		this.accessServiceFacade = accessServiceFacade;
	}

}
