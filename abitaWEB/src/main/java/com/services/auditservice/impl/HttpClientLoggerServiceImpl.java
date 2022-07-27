/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.auditservice.impl;

import com.dto.EventDurationDTO;
import com.dto.UserDTO;
import com.services.auditservice.IDistantLoggerService;
import com.web.audit.data.OptionalParameter;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.http.HTTPException;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * The Class HttpClientLoggerServiceImpl.
 *
 * @author
 */
public class HttpClientLoggerServiceImpl implements IDistantLoggerService, Serializable {

	/** Cookie id containing the session id. */
	private static final String JSESSIONID = "JSESSIONID";

	/** serialVersionUID. */
	private static final long serialVersionUID = -6295044527869160788L;

	/** Logger. */
	private static Log log = LogFactory.getLog(com.services.auditservice.impl.HttpClientLoggerServiceImpl.class);

	/** Context root de la servlet d'audit. */
	private String contextRoot;

	/** Le délai d'expiration pour le gestionnaire de connections */
	private static final int CONNECTION_MANAGER_TIMEOUT = 1000;

	/* (non-Javadoc)
	 * @see com.services.auditservice.IDistantLoggerService#logConnectedUser(javax.servlet.http.HttpServletRequest, com.dao.user.entity.User, java.util.List)
	 */
	@Override
	public void logConnectedUser(HttpServletRequest request, UserDTO connectedUser,
			List<OptionalParameter> optionalParameters) {
		// Exemple d'url
		// http://localhost:8080/frameworkAuditWeb/fwkAudit?action=USERLOGGED&idApp=123&nni=456&lname=hicauber&fname=mathieu&area=aquitaine

		if (connectedUser != null) {
			log.debug("Logging the user " + connectedUser.getLogin());
			StringBuffer strBuffer = new StringBuffer(getUrl(request));

			try {
				strBuffer.append("?action=USERLOGGED");
				strBuffer.append("&nni=");
				strBuffer.append(URLEncoder.encode(connectedUser.getLogin(), "UTF-8"));
				strBuffer.append("&area=");
				// No location log if the user has no location
				if (connectedUser.getLocation() != null) {
					strBuffer.append(URLEncoder.encode(connectedUser.getLocation(), "UTF-8"));
				}

				if (optionalParameters.size() > 0) {
					strBuffer.append("&optParams="
							+ URLEncoder.encode(processOptionalParameter(optionalParameters), "UTF-8"));
				}

			} catch (UnsupportedEncodingException unsupportedEncodingException) {
				log.error("Fatal protocol violation: " + unsupportedEncodingException.getMessage());
			}

			callUrl(strBuffer.toString(), request);
		}
	}

	/**
	 * Return the string representation of the map of optional parameters in an
	 * "url opt parameters " way.
	 *
	 * @param optionalParameters optionals parameters
	 * @return translated optional parameters
	 */
	private String processOptionalParameter(List<OptionalParameter> optionalParameters) {
		StringBuilder sb = new StringBuilder();
		for (OptionalParameter entry : optionalParameters) {
			if (entry != null && entry.getValue() != null) {
				sb.append(entry.getKey() + "|" + entry.getValue() + ";");
			}
		}
		return sb.toString();
	}

	/**
	 * Log events duration.
	 *
	 * @param request request
	 * @param eventDuration event duration
	 */
	@Override
	public void logEventDuration(HttpServletRequest request, EventDurationDTO eventDuration) {
		log.debug("Logging event duration");
		StringBuffer strBuffer = new StringBuffer(getUrl(request));

		try {

			strBuffer.append("?action=SAVEDURATION");
			strBuffer.append("&idUser=");
			strBuffer.append(URLEncoder.encode(eventDuration.getNniUser(), "UTF-8"));
			strBuffer.append("&id=");
			strBuffer.append(URLEncoder.encode(eventDuration.getIdElement(), "UTF-8"));
			//FFT 3563
			/*strBuffer.append("&idTr=");
			strBuffer.append(URLEncoder.encode("666", "UTF-8"));*/
			strBuffer.append("&from=");
			strBuffer.append(URLEncoder.encode(String.valueOf(eventDuration.getStartTime()), "UTF-8"));
			strBuffer.append("&to=");
			strBuffer.append(URLEncoder.encode(String.valueOf(eventDuration.getEndTime()), "UTF-8"));

			if (eventDuration.getOptionalParameters() != null && eventDuration.getOptionalParameters().size() > 0) {
				strBuffer.append("&optParams="
						+ URLEncoder.encode(processOptionalParameter(eventDuration.getOptionalParameters()), "UTF-8"));
			}

		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			log.error("Fatal protocol violation: " + unsupportedEncodingException.getMessage());
		}

		callUrl(strBuffer.toString(), request);
	}

	/* (non-Javadoc)
	 * @see com.services.auditservice.IDistantLoggerService#logElementClick()
	 */
	@Override
	public void logElementClick() {
		// Nothing todo

	}

	/**
	 * callUrl(url, request) call the given url adding the JSESSIONID if passed in a cookie (with the id 'JSESSIONID').
	 *
	 * @param url to call for logging the action
	 * @param request received request containing the cookies
	 */
	private void callUrl(String url, HttpServletRequest request) {

		log.debug("call URL : " + url);
		String httpSessionIdCookie = null;
		for (Cookie cookie : request.getCookies()) {
			log.debug("cookie : " + cookie.getName() + " : " + cookie.getValue());
			if (JSESSIONID.equals(cookie.getName())) {
				httpSessionIdCookie = cookie.getValue();
			}
		}
		log.debug("JSESSIONID in cookie : " + httpSessionIdCookie);

		HttpClient httpClient = new HttpClient();
		DefaultHttpMethodRetryHandler retryhandler = new DefaultHttpMethodRetryHandler(1, false);
		httpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryhandler);
		Integer timeoutMs = new Integer(CONNECTION_MANAGER_TIMEOUT);
		httpClient.getParams().setConnectionManagerTimeout(timeoutMs);

		GetMethod method = null;

		try {
			method = new GetMethod(url);

			if (httpSessionIdCookie != null) {
				// Adding the JSESSIONID in the cookie for no new http session in the server
				log.debug("JSESSIONID cookie added to the request.");
				method.getParams().setCookiePolicy(org.apache.http.client.params.CookiePolicy.IGNORE_COOKIES);
				method.setRequestHeader("Cookie", JSESSIONID + "=" + httpSessionIdCookie);
			} else {
				log.error("No cookie with the JSESSIOND was founded, a new HTTP non applicative session will be probably created in server.");
				log.error("This error could provoque a PermGen exception if the sessions do not expire or if they are not manually invalidated.");
			}

			int statusCode = httpClient.executeMethod(method);

			if (statusCode != HttpStatus.SC_OK) {
				log.error("Method failed: " + method.getStatusLine());
				log.error("url : " + url);
			}

			// Read the response body.
			// TODO : correct warning
			byte[] responseBody = method.getResponseBody();

			// Deal with the response.
			// Use caution: ensure correct character encoding and is not binary
			// data
		} catch (HTTPException httpException) {
			log.error("Fatal protocol violation: " + httpException.getMessage());
			log.error(httpException);
		} catch (IOException ioException) {
			log.error("Fatal transport error: " + ioException.getMessage());
			log.error(ioException);
		} catch (RuntimeException runtimeException) {
			log.error("The metrology servlet is unavailable at the specified URI : " + url);
		} finally {
			// Release the connection.
			if (method != null) {
				method.releaseConnection();
			}
		}
	}

	/**
	 * getUrl() construit l'url d'appel à la servlet de log.
	 *
	 * @param request request
	 * @return String contenant l'url pour l'acces au serveur
	 */
	private String getUrl(HttpServletRequest request) {
		StringBuffer strBuffer = new StringBuffer();

		strBuffer.append(request.getScheme());
		strBuffer.append("://");
		strBuffer.append(request.getServerName());
		strBuffer.append(":");
		strBuffer.append(request.getServerPort());
		strBuffer.append("/");
		strBuffer.append(getContextRoot());
		strBuffer.append("/frameworkAuditServlet/");
		strBuffer.append("fwkAudit");

		return strBuffer.toString();
	}

	/**
	 * Gets the context root.
	 *
	 * @return the contextRoot
	 */
	public String getContextRoot() {
		return contextRoot;
	}

	/**
	 * Sets the context root.
	 *
	 * @param contextRoot the contextRoot to set
	 */
	public void setContextRoot(String contextRoot) {
		this.contextRoot = contextRoot;
	}

}
