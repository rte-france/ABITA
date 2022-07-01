package com.abita.web.shared;

import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Controller Générique gérant les messages d'erreurs
 *
 * @author
 */
public abstract class AbstractGenericController implements Serializable {

  /** SerialVersionUID */
  private static final long serialVersionUID = -3228136270325345375L;

  /** Resource Bundle. */
  public static final String BUNDLE_NAME = "specificApplicationProperties";

  /** The resource bundle for the messages */
  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  /**
   * Builds and returns a new {@link FacesMessage} with the specified arguments. The message summary is taken from the resource bundle.
   * @param key : The message key in the bundle
   * @param severity : The message severity. Default to {@link FacesMessage#SEVERITY_INFO}
   * @param params : The optional arguments list
   * @return FacesMessage : a new {@link FacesMessage} built with the specified arguments
   */
  protected FacesMessage getMessage(String key, Severity severity, Object... params) {
    if (key == null) {
      return null;
    }

    final FacesMessage fMessage = new FacesMessage();
    if (severity != null) {
      fMessage.setSeverity(severity);
    }

    if (!BUNDLE.containsKey(key)) {
      fMessage.setSummary(key);
    } else {
      final String message = BUNDLE.getString(key);
      // aucun paramÃ¨tre, on renvoie la chaine
      if (params == null || params.length == 0) {
        fMessage.setSummary(message);
      } else {
        fMessage.setSummary(MessageFormat.format(message, params));
      }
    }

    return fMessage;
  }

  /**
   * @see GCMBaseController#getMessage(String, Severity, Object...)
   * @param key : The message key in the bundle
   * @param params : The optional arguments list
   * @return FacesMessage : a new {@link FacesMessage} built with the specified arguments
   */
  protected final FacesMessage getInfoMessage(final String key, final Object... params) {
    return getMessage(key, FacesMessage.SEVERITY_INFO, params);
  }

  /**
   * @see GCMBaseController#getMessage(String, Severity, Object...)
   * @param key : The message key in the bundle
   * @param params : The optional arguments list
   * @return FacesMessage a new {@link FacesMessage} built with the specified arguments
   */
  protected final FacesMessage getErrorMessage(final String key, final Object... params) {
    return getMessage(key, FacesMessage.SEVERITY_ERROR, params);
  }

  /**
   * @see GCMBaseController#getMessage(String, Severity, Object...)
   * @param key : The message key in the bundle
   * @param params : The optional arguments list
   * @return FacesMessage a new {@link FacesMessage} built with the specified arguments
   */
  protected final FacesMessage getWarningMessage(final String key, final Object... params) {
    return getMessage(key, FacesMessage.SEVERITY_WARN, params);
  }

  /**
   * Tells the client that the validation failed so it does not close the dialog
   * @param errorMessageId : the error message id in the jsp
   * @param errorMessage : the error message
   */
  public void sendErrorMessage(String errorMessageId, String errorMessage) {
    RequestContext context = RequestContext.getCurrentInstance();
    context.addCallbackParam("validationFailed", true);
    FacesContext.getCurrentInstance().addMessage(errorMessageId, getErrorMessage(errorMessage));
  }

}
