package com.web.common.exception;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.DefaultRequestContext;
import org.primefaces.context.RequestContext;
import org.primefaces.util.Constants;

import com.services.common.util.MessageSupport;
import com.web.common.constants.AccessConstants;

/**
 * Gestion des exceptions
 * @author
 *
 */
public class ManageExceptionHandler extends ExceptionHandlerWrapper {

	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(ManageExceptionHandler.class);

	/** Message d'erreur de d�connection */
	private static final String MSG_DISCONNECT = "disconnected";

	/** Gestionnaire d'exceptions */
	private ExceptionHandler wrapped;

	/**
	 * Constructeur
	 * @param wrapped gestionnaire d'exceptions
	 */
	public ManageExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		ExceptionQueuedEvent event;
		ExceptionQueuedEventContext context;
		Throwable t;
		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator(); i.hasNext();) {
			event = i.next();
			context = (ExceptionQueuedEventContext) event.getSource();
			t = context.getException();
			if (t instanceof ViewExpiredException) {
				// Cas de l'expiration de session
				viewExpiredException((ViewExpiredException) t, i);
			} else {
				// Cas de l'exception non traitee
				genericExceptionHandler(t, i);
				break;
			}
		}

		// At this point, the queue will not contain any ViewExpiredEvents.
		// Therefore, let the parent handle them.
		getWrapped().handle();
	}

	/**
	 * Redirige l'utilisateur vers la page souhaitee
	 * @param fc context
	 * @param redirectPage page cible
	 * @throws FacesException faces exception
	 */
	public void doRedirect(FacesContext fc, String redirectPage) throws FacesException {
		ExternalContext ec = fc.getExternalContext();

		try {
			// workaround for PrimeFaces
			new DefaultRequestContext();
			if (ec.getRequestParameterMap().containsKey(Constants.PARTIAL_PROCESS_PARAM)
					&& !"@all".equals(ec.getRequestParameterMap().get(Constants.PARTIAL_PROCESS_PARAM))) {
				fc.setViewRoot(new UIViewRoot());
			}

			// fix for renderer kit (Mojarra's and PrimeFaces's ajax redirect)
			if ((((DefaultRequestContext) RequestContext.getCurrentInstance()).isAjaxRequest() || fc
					.getPartialViewContext().isPartialRequest())
					&& fc.getResponseWriter() == null
					&& fc.getRenderKit() == null) {
				ServletResponse response = (ServletResponse) ec.getResponse();
				ServletRequest request = (ServletRequest) ec.getRequest();
				response.setCharacterEncoding(request.getCharacterEncoding());

				RenderKitFactory factory = (RenderKitFactory) FactoryFinder
						.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
				RenderKit renderKit = factory.getRenderKit(fc, fc.getApplication().getViewHandler()
						.calculateRenderKitId(fc));
				ResponseWriter responseWriter = renderKit.createResponseWriter(response.getWriter(), null,
						request.getCharacterEncoding());
				fc.setResponseWriter(responseWriter);
			}

			ec.redirect(ec.getRequestContextPath() + (redirectPage != null ? redirectPage : ""));
		} catch (IOException ioException) {
			LOGGER.error("Redirect to the specified page '" + redirectPage + "' failed");
			throw new FacesException(ioException);
		}
	}

	/**
	 * Gere la levee d'une exception de type ViewExpiredException
	 * @param vee exception levee
	 * @param i iterateur de l'exception traitee a supprimer
	 */
	private void viewExpiredException(ViewExpiredException vee, Iterator<ExceptionQueuedEvent> i) {
		String redirectPage = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) fc.getExternalContext().getSession(true);
		try {
			// Push some useful stuff to the request scope for use in
			// the page
			Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
			requestMap.put("sessionTimeOutMessage",
					MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE, MSG_DISCONNECT));

			MessageSupport.addMessage(FacesMessage.SEVERITY_WARN, AccessConstants.ACCESS_BUNDLE, MSG_DISCONNECT);

			session.setAttribute("currentViewId", vee.getViewId());
			LOGGER.debug("currentViewId to put = " + vee.getViewId());
			redirectPage = "/index.jsp";
		} finally {
			i.remove();
		}
		doRedirect(fc, redirectPage);
	}

	/**
	 * Gere la levee d'une exception generique : affichage d'un message d'erreur et log
	 * @param exception exception a gerer
	 * @param i iterateur de l'exception traitee a supprimer
	 */
	private void genericExceptionHandler(Throwable exception, Iterator<ExceptionQueuedEvent> i) {
		// Log de l'exception
		LOGGER.error(exception.getMessage());

		LOGGER.error(ExceptionUtils.getStackTrace(exception));

		// Suppression de l'exception
		i.remove();

		// Redirection vers la page d'erreur
		FacesContext fc = FacesContext.getCurrentInstance();
		String redirectPage = "/error.jsp";
		doRedirect(fc, redirectPage);
	}

}
