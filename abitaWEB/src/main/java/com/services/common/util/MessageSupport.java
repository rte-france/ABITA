package com.services.common.util;

import com.services.common.constants.Constants;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author
 *  Permet d'afficher des messages d'informations dans l'ihm. (Fin de traitement,Fin import de
 *         fichier,Envoi de mail reussi)
 */
public final class MessageSupport {

	/**
	 * Constructor
	 */
	private MessageSupport() {
		super();
	}

	/**
	 * Create faces message and Add him to faces Context.
	 * @param severity message severity
	 * @param bundle message bundle
	 * @param messageId message id
	 */
	public static void addMessage(Severity severity, String bundle, String messageId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage message = getMessage(severity, bundle, messageId, null);
		facesContext.addMessage(null, message);
	}

	/**
	 * Create faces message with parameter and add him to faces context.
	 * @param severity message severity
	 * @param bundle message bundle
	 * @param messageId message id
	 * @param params message params
	 */
	public static void addMessage(Severity severity, String bundle, String messageId, Object[] params) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage message = getMessage(severity, bundle, messageId, params);
		facesContext.addMessage(null, message);
	}

	/**
	 * Create faces message with common bundle and add himto faces context.
	 * @param severity message severity
	 * @param messageId message id
	 */
	public static void addMessage(Severity severity, String messageId) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage message = getMessage(severity, Constants.COMMON_BUNDLE, messageId, null);
		facesContext.addMessage(null, message);
	}

	/**
	 * Create faces message with parameters and common bundle and add him to faces context.
	 * @param severity message severity
	 * @param messageId message id
	 * @param params message params
	 */
	public static void addMessage(Severity severity, String messageId, Object[] params) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage message = getMessage(severity, Constants.COMMON_BUNDLE, messageId, params);
		facesContext.addMessage(null, message);
	}

	/**
	 * Create faces message with parameter
	 * @param severity message severity
	 * @param commonBundleKey message bundle
	 * @param messageId message id
	 * @param params message params
	 * @return faces message
	 */
	public static FacesMessage getMessage(Severity severity, String commonBundleKey, String messageId, Object[] params) {
		Locale locale = getLocale();
		String message = getMessage(commonBundleKey, messageId, params, locale);
		return new FacesMessage(severity, message, null);
	}

	/**
	 * Create faces message with parameter with common bundle.
	 * @param severity message severity
	 * @param messageId message id
	 * @param params message params
	 * @return faces message
	 */
	public static FacesMessage getMessage(Severity severity, String messageId, Object[] params) {
		Locale locale = getLocale();
		String message = getMessage(Constants.COMMON_BUNDLE, messageId, params, locale);
		return new FacesMessage(severity, message, null);
	}

	/**
	 * Create faces message with common bundle.
	 * @param severity message severity
	 * @param messageId message id
	 * @return faces message
	 */
	public static FacesMessage getMessage(Severity severity, String messageId) {
		Locale locale = getLocale();
		String message = getMessage(Constants.COMMON_BUNDLE, messageId, locale);
		return new FacesMessage(severity, message, null);
	}

	/**
	 * retrun message of bundle.
	 * @param bundle message bundle
	 * @param messageId message id
	 * @param locale message locale
	 * @return message or null
	 */
	public static String getMessagePattern(String bundle, String messageId, Locale locale) {
		ResourceBundle resource = ResourceBundle.getBundle(bundle, locale);
		String res = null;
		if (resource != null) {
			try {
				res = resource.getString(messageId);
			} catch (MissingResourceException missingResourceException) {
				res = null;
			}
		}

		return res;
	}

	/**
	 * @param bundle message bundle
	 * @param messageId message id
	 * @param locale message locale
	 * @return message string
	 */
	public static String getMessage(String bundle, String messageId, Locale locale) {
		ResourceBundle resource = ResourceBundle.getBundle(bundle, locale);
		String res = resource.getString(messageId);
		res = resource.getString(messageId);

		if (res == null) {
			res = messageId;
		}
		return res;
	}

	/**
	 * Create message with parameters.
	 * @param bundle message bundle
	 * @param messageId message id
	 * @param params message params
	 * @param locale message locale
	 * @return message string
	 */
	public static String getMessage(String bundle, String messageId, Object[] params, Locale locale) {
		String res;
		String pattern = getMessagePattern(bundle, messageId, locale);
		if (pattern == null) {
			res = messageId;
		} else {
			res = MessageFormat.format(pattern, params);
		}
		return res;
	}

	/**
	 * @param bundle message bundle
	 * @param messageId message id
	 * @return message string
	 */
	public static String getMessage(String bundle, String messageId) {
		Locale locale = getLocale();
		return getMessage(bundle, messageId, locale);
	}

	/**
	 * create message with param.
	 * @param bundle message bundle
	 * @param messageId message id
	 * @param params message params
	 * @return message string
	 */
	public static String getMessage(String bundle, String messageId, Object[] params) {
		Locale locale = getLocale();
		return getMessage(bundle, messageId, params, locale);
	}

	/**
	 * return the current locale.
	 * @return current locale
	 */
	public static Locale getLocale() {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		if (currentInstance == null) {
			return Locale.FRENCH;
		}
		UIViewRoot viewRoot = currentInstance.getViewRoot();
		Locale locale = viewRoot != null ? viewRoot.getLocale() : currentInstance.getApplication().getDefaultLocale();
		return locale;
	}

}
