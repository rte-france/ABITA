/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.login.filters;

import com.dto.Group;
import com.dto.UserDTO;
import com.services.accessconfig.IAccessConfigService;
import com.services.common.constants.Constants;
import com.services.user.IUserService;
import com.services.user.exception.UserNotFoundException;
import com.services.user.exception.UserServiceException;
import com.web.common.util.PathPageConvertor;
import com.web.login.impl.ThreadUserHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * A security filter.
 * @author
 */
public class SecurityFilter implements Filter {

	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(SecurityFilter.class);

	/** SLASH_POSITION */
	private static final int SLASH_POSITION = 2;

	/** filterConfig */
	@SuppressWarnings("unused")
	private FilterConfig filterConfig = null;

	/** service d'accès du framework */
	private IUserService userService;

	/** accès à la configuration de sécurité du framework */
	private IAccessConfigService accessConfigService;

	@Override
	public void init(final FilterConfig filterConfigParam) throws ServletException {
		this.filterConfig = filterConfigParam;
	}

	@Override
	public void destroy() {
		this.filterConfig = null;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException,
	ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (this.isPage(httpRequest.getRequestURI()) && !this.isLoginPage(httpRequest)) {

			// No page but the login form will be cacheable by the browser:
			// http://stackoverflow.com/questions/10305718/avoid-back-button-on-jsfprimefaces-application
			httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			httpResponse.setDateHeader("Expires", 0); // Proxies.

			if (this.isSessionInvalid(httpRequest)) {
				this.goToLogin(httpRequest, httpResponse);
				return;
			} else {
				if (this.connected(httpRequest)) {
					// If the user is logged in, set the current thread context
					UserDTO connectedUser = this.getUser(httpRequest);
					ThreadUserHolder.set(connectedUser);
					if (!this.haveRight(httpRequest)) {
						httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
						return;
					}

					// Log de l'utilisateur connecté
					this.logConnectedUser(connectedUser, httpRequest);
				} else if (this.isResourceReacheableByAnonymous(httpRequest)) {
					LOGGER.debug("Anonymous user : access granted for resource \"" + httpRequest.getRequestURI() + "\"");
				} else {
					LOGGER.debug("user not connected : redirect to login page");
					this.goToLogin(httpRequest, httpResponse);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * logConnectedUser : appel au service de log de connexions distant
	 *
	 * @param connectedUser utilisateur connecté à  logger
	 * @param request requéte http
	 */
	private void logConnectedUser(final UserDTO connectedUser, final HttpServletRequest request) {
    LOGGER.debug("loggin the connected user \"" + connectedUser.getLogin() + "\"");
	}

	/**
	 * indique si l'utilisateur posséde les droits sur la cible
	 * @param httpRequest requéte http
	 * @return vrai si l'utilisateur posséde les droits sur la cible
	 */
	private boolean haveRight(final HttpServletRequest httpRequest) {
		UserDTO user = this.getUser(httpRequest);
		String pageName = this.getResourceId(httpRequest.getRequestURI(), httpRequest.getContextPath());

        return this.accessConfigService.hasUserAcessToPage(user, pageName);
	}

	/**
	 * Recupére l'utilisateur lié à la requête
	 * @param httpRequest requête
	 * @return utilisateur lié à la requête
	 */
	private UserDTO getUser(final HttpServletRequest httpRequest) {
		return (UserDTO) httpRequest.getSession(false).getAttribute(Constants.USER_KEY);
	}

	/**
	 * indique si un utilisateur est connecté
	 * @param httpRequest requéte
	 * @return vrai si un utilisateur est connecté
	 */
	private boolean connected(final HttpServletRequest httpRequest) {
		UserDTO user = this.getUser(httpRequest);
		return user != null;
	}

	/**
	 * Indique si la page cible est la page de connexion
	 * @param httpRequest requéte
	 * @return vrai si la page cible est la page de connexion
	 */
	private boolean isLoginPage(final HttpServletRequest httpRequest) {
		return this.loginURL(httpRequest).equals(httpRequest.getRequestURI());
	}

	/**
	 * Checks if the J2EE session is valid, the user presence is not checked here !
	 *
	 * @param httpRequest requéte
	 * @return vrai si la session est invalide
	 */
	private boolean isSessionInvalid(final HttpServletRequest httpRequest) {
		return httpRequest.getRequestedSessionId() == null || !httpRequest.isRequestedSessionIdValid();
	}

	/**
	 * Dirige vers la page de connexion
	 * @param httpRequest requéte
	 * @param httpResponse réponse
	 * @throws IOException I/O exception
	 */
	private void goToLogin(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse) throws IOException {

		httpResponse.sendRedirect(this.createLoginURL(httpRequest));
	}

	/**
	 * Défini l'URL de la page de connexion
	 * @param httpRequest requéte
	 * @return URL de la page de connexion
	 */
	private String loginURL(final HttpServletRequest httpRequest) {
		return String.valueOf(httpRequest.getContextPath() + "/pages/welcome/welcome.jsf");
	}

	/**
	 * Défini l'URL de la page de connexion
	 * @param httpRequest requéte
	 * @return URL de la page de connexion
	 */
	private String createLoginURL(final HttpServletRequest httpRequest) {
		String requestURI = httpRequest.getRequestURI();
		return this.loginURL(httpRequest) + "?page="
		+ requestURI.substring(requestURI.indexOf('/', SLASH_POSITION), requestURI.lastIndexOf('.'));
	}

	/**
	 * Resource named path.filename.extension.jsf
	 * @param contextPath context
	 * @return vrai si l'url indique une page jsf
	 */
	private boolean isPage(final String contextPath) {
		return contextPath.matches(".*/\\w+\\.jsf");
	}

	/**
	 * récupére l'identfiiant de la resource
	 * @param path chemin vers la resouce
	 * @param contextPath contexte
	 * @return identifiant
	 */
	private String getResourceId(final String path, final String contextPath) {
		return PathPageConvertor.encode(path, contextPath);
	}

	/**
	 * Tells if the current user is able to see the current resource
	 *
	 * @param request the request object
	 * @return boolean true if the user can show the resource
	 */
	private boolean isResourceReacheableByAnonymous(final HttpServletRequest request) {
		boolean hasAccessAnonymous = false;

		UserDTO anonymousUser;

		try {
			anonymousUser = this.userService.findByLogin(Group.ANONYMOUS.getIdentifier());
            String pageName = this.getResourceId(request.getRequestURI(), request.getContextPath());
            hasAccessAnonymous = this.accessConfigService.hasUserAcessToPage(anonymousUser, pageName);
		} catch (UserServiceException userServiceException) {
            LOGGER.error("An error has happend", userServiceException);
		} catch (UserNotFoundException e) {
            LOGGER.error("User Anonymous not found, maybe an error in the data base config has happend");
        }

        return hasAccessAnonymous;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(final IUserService userService) {
		this.userService = userService;
	}

	/**
	 * @param accessConfigService the accessConfigService to set
	 */
	public void setAccessConfigService(final IAccessConfigService accessConfigService) {
		this.accessConfigService = accessConfigService;
	}

}
