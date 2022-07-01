package com.services.actionlogs.aspects;

import com.dto.ActionLogDTO;
import com.dto.UserDTO;
import com.services.actionlogs.IActionLogService;
import com.services.actionlogs.constants.ActionLogsConstants;
import com.services.actionlogs.exception.ActionLogServiceException;
import com.services.common.util.MessageSupport;
import com.web.login.impl.AccessSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;

import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;

/**
 * Aspect de Log du framework
 * @author
 */
public class LoggingAspect {

	/** logger de classe */
	private static final Log LOGGER = LogFactory.getLog(com.services.actionlogs.aspects.LoggingAspect.class);

	/** composant de trace applicatives */
	private IActionLogService actionLogsService;

	/**
	 * Méthode gérant un cas passant (ex. : appel de service sans exception)
	 * @param joinPoint le point d'exécution
	 * @throws ActionLogServiceException exception lors de l'exécution du code AOP
	 */
	public void methodAfterReturning(JoinPoint joinPoint) throws ActionLogServiceException {
		createLogForJoinPointWithStatus(joinPoint, ActionLogsConstants.SUCCESS);
	}

	/**
	 * Méthode gérant un cas d'exception (ex. : appel de service avec levée d'exception)
	 * @param joinPoint le point d'exécution
	 * @throws ActionLogServiceException exception lors de l'exécution du code AOP
	 */
	public void methodAfterThrowing(JoinPoint joinPoint) throws ActionLogServiceException {
		createLogForJoinPointWithStatus(joinPoint, ActionLogsConstants.FAILURE);
	}

	/**
	 * Algorithme générique de création de log pour un point d'exécution
	 * @param joinPoint le point d'exécution
	 * @param actionStatus le statut de l'action
	 * @throws ActionLogServiceException exception lors de l'exécution du code AOP
	 */
	private void createLogForJoinPointWithStatus(JoinPoint joinPoint, String actionStatus)
			throws ActionLogServiceException {

		final Log logger = LogFactory.getLog(joinPoint.getTarget().getClass().getName());
		try {
			ActionLogDTO actionLogDTO = mapJoinPointWithStatusToActionLogDTO(joinPoint, actionStatus);
			actionLogsService.create(actionLogDTO);
		} catch (ActionLogServiceException actionLogsServiceException) {
			logger.error(actionLogsServiceException.getMessage(), actionLogsServiceException);
			throw actionLogsServiceException;
		}
	}

	/**
	 * Mapping des caractéristiques d'un point d'exécution et d'un statut vers un objet ActionLog
	 * @param joinPoint le point d'exécution
	 * @param actionStatus le statut
	 * @return l'objet ActionLogDTO pour tracer l'exécution
	 */
	private ActionLogDTO mapJoinPointWithStatusToActionLogDTO(JoinPoint joinPoint, String actionStatus) {
		ActionLogDTO actionLogDTO = new ActionLogDTO();
		String label = null;
		try {
			label = buildTargetMessageFromJoinPoint(joinPoint, actionStatus, ActionLogsConstants.LABEL_SUFFIX);
		} catch (MissingResourceException missingResourceException) {
			label = MessageSupport.getMessage(
					ActionLogsConstants.BASE_BUNDLE_NAME,
					ActionLogsConstants.MISSING_KEY,
					new String[] {joinPoint.getTarget().getClass().getName().concat(".")
							.concat(joinPoint.getSignature().getName()).concat(actionStatus)
							.concat(ActionLogsConstants.LABEL_SUFFIX) }, Locale.FRENCH);
		}
		actionLogDTO.setTitle(label);
		String status = null;
		try {
			status = buildTargetMessageFromJoinPoint(joinPoint, actionStatus, ActionLogsConstants.STATUS_SUFFIX);
		} catch (MissingResourceException missingResourceException) {
			status = MessageSupport.getMessage(
					ActionLogsConstants.BASE_BUNDLE_NAME,
					ActionLogsConstants.MISSING_KEY,
					new String[] {joinPoint.getTarget().getClass().getName().concat(".")
							.concat(joinPoint.getSignature().getName()).concat(actionStatus)
							.concat(ActionLogsConstants.STATUS_SUFFIX) }, Locale.FRENCH);
		}
		actionLogDTO.setStatus(status);
		String user = null;
		// par défaut, la trace de l'utilisateur initiateur de l'action est activée
		boolean isUserLogged = true;
		try {
			user = buildTargetMessageFromJoinPoint(joinPoint, actionStatus, ActionLogsConstants.USER_SUFFIX);
			isUserLogged = Boolean.valueOf(user);
		} catch (MissingResourceException missingResourceException) {
			isUserLogged = true;
		}
		if (isUserLogged) {
			UserDTO userToTrace = AccessSupport.getCurrentUser();
			actionLogDTO.setUser(userToTrace);
		}
		String origin = null;
		try {
			origin = buildTargetMessageFromJoinPoint(joinPoint, actionStatus, ActionLogsConstants.ORIGIN_SUFFIX);
		} catch (MissingResourceException missingResourceException) {
			origin = joinPoint.getTarget().getClass().getSimpleName();
		}
		actionLogDTO.setOrigin(origin);
		actionLogDTO.setDate(new Date());
		return actionLogDTO;
	}

	/**
	 * Constitution d'un message pour une classe sur laquelle on se greffe, un statut d'action et une donnée particulière
	 * @param joinPoint le point d'exécution sur lequel on se greffe
	 * @param actionStatus le statut de l'action
	 * @param messageData la donnée à récupérer
	 * @return le message récupéré du bundle du module
	 */
	private String buildTargetMessageFromJoinPoint(JoinPoint joinPoint, String actionStatus, String messageData) {
		String message = null;
		final String key = joinPoint.getTarget().getClass().getName().concat(".")
				.concat(joinPoint.getSignature().getName()).concat(actionStatus).concat(messageData);
		try {
			message = MessageSupport.getMessage(ActionLogsConstants.BUNDLE_NAME, key, Locale.FRENCH);
		} catch (MissingResourceException missingResourceException) {
			LOGGER.error(missingResourceException.getMessage(), missingResourceException);
			throw missingResourceException;
		}
		return message;
	}

	/**
	 * @param actionLogsService the actionLogsService to set
	 */
	public void setActionLogsService(IActionLogService actionLogsService) {
		this.actionLogsService = actionLogsService;
	}
}
