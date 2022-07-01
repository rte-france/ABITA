package com.web.audit.impl;

import com.dto.EventDurationDTO;
import com.dto.Group;
import com.dto.UserDTO;
import com.services.auditservice.ActivityTracker;
import com.services.auditservice.exception.DataAccessException;
import com.services.common.util.SafetyUtils;
import com.services.common.util.SecurityUtil;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import com.services.user.IUserService;
import com.services.user.exception.UserServiceException;
import com.web.audit.data.OptionalParameter;
import com.web.site.constants.SiteConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.services.auditservice.constants.ConstantsActivityTracker.*;

/**
 * Servlet implementation class ActivityTrackerServlet invoked for tracking the enduser's activity.
 *
 * @author
 * @version 1.0
 */
@Controller
@RequestMapping("/fwkAudit")
@SessionAttributes("activity")
public class ActivityTrackerController implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -5138735589228686382L;

	/** logger */
	private static final Log LOG = LogFactory.getLog(ActivityTrackerController.class);

	/** parameterService */
	@Resource(name = "parameterService")
	private ParameterService parameterService;

	/** Le service pour la récupération de la liste des utilisateurs */
	@Resource(name = "userService")
	private IUserService userService;

	/**
	 * The activity service interface
	 */
	@Autowired
	private ActivityTracker activityTracker;

	/** Servlet VERSION. This version attribute has nothing related to the MAVEN version.  */
	private static final String VERSION = "1.0";

	/** Application's unknown */
	private static final String UNKNOWN_APPLICATION = "UNKNOW_APPLICATION";

	/** NB_ELEMENT_PER_PAIRVALUE */
	private static final int NB_ELEMENT_PER_PAIRVALUE = 2;

	/** Site's name */
	private String siteName;

	/** Site's version */
	private String siteVersion;

	/**
	 * an empty call
	 * @param model the data model
	 * @return error
	 */
	@RequestMapping(method = RequestMethod.GET, params = "!action")
	public String empty(final Model model) {
		return this.error(model, new Exception(MESSAGE_NO_ACTION));
	}

	/**
	 * save duration
	 * @param model request method (get or post)
	 * @param idComponent component's id
	 * @param from start time
	 * @param idUser user's id
	 * @param to end time
	 * @param optParameters optional parameters
	 * @return target view
	 */
	@RequestMapping(method = RequestMethod.GET, params = "action=" + ACTION_SAVE_DURATION)
	public String save(final Model model,
			@RequestParam(IDCOMPONENT) final String idComponent,
			// FFT 3563 Suppression de l'id technique
			/* final @RequestParam(TRANSACTIONID) String transactionId, */
			@RequestParam(FROM) final long from, @RequestParam(IDUSER) final String idUser,
			@RequestParam(TO) final long to,
			@RequestParam(value = OPTIONAL_PARAMETERS, required = false) final String optParameters) {

		// Parameters validation
		if (from > to) {
			return this.error(model, new Exception(MESSAGE_BEFORE_END));
		}

		List<OptionalParameter> optParametersAsList = null;
		if (optParameters != null) {
			optParametersAsList = this.processOptParametersIntoList(optParameters);
		}

		final String idApplication = this.getSiteName();
		try {
			final String idUserSHA1 = SecurityUtil.encode(idUser, SecurityUtil.SECRET_KEY);
			final EventDurationDTO eventDuration = new EventDurationDTO(idUserSHA1, idComponent, from, to,
					optParametersAsList);
			this.activityTracker.saveDuration(idApplication, this.getSiteVersion(), eventDuration);
		} catch (final DataAccessException dataAccessException) {
			LOG.error(dataAccessException);
		}

		return VIEW_NAME_SAVED;
	}

	/**
	 * save count indicator
	 * @param model request method (get or post)
	 * @param idComponent component's id
	 * @param idUser user's id
	 * @param optParameters optional parameters
	 * @return target view
	 */
	@RequestMapping(method = RequestMethod.GET, params = "action=" + ACTION_COUNT)
	public String count(final Model model, @RequestParam(value = IDUSER) final String idUser,
			@RequestParam(value = IDCOMPONENT) final String idComponent,
			@RequestParam(value = OPTIONAL_PARAMETERS, required = false) final String optParameters) {

		List<OptionalParameter> optParametersAsList = null;
		if (optParameters != null) {
			optParametersAsList = this.processOptParametersIntoList(optParameters);
		}

		final String idApplication = this.getSiteName();

		try {
			final String idUserSHA1 = SecurityUtil.encode(idUser, SecurityUtil.SECRET_KEY);
			this.activityTracker.count(idApplication, idUserSHA1, idComponent, optParametersAsList);
		} catch (final DataAccessException dataAccessException) {
			LOG.error(dataAccessException);
		}

		return VIEW_NAME_SAVED;
	}

	/**
	 * save user logged indicator
	 * @param model  request method (get or post)
	 * @param userId user's id
	 * @param userArea user's area
	 * @param optParameters optional parameters
	 * @return target view
	 */
	@RequestMapping(method = RequestMethod.GET, params = "action=" + ACTION_USER_LOGGED)
	public String userLogged(final Model model, @RequestParam(USER_ID) final String userId,
			@RequestParam(USER_AREA) final String userArea,
			@RequestParam(value = OPTIONAL_PARAMETERS, required = false) final String optParameters) {

		List<OptionalParameter> optParametersAsList = null;
		if (optParameters != null) {
			optParametersAsList = this.processOptParametersIntoList(optParameters);
		}

		final String idApplication = this.getSiteName();

		try {
			final String idUserSHA1 = SecurityUtil.encode(userId, SecurityUtil.SECRET_KEY);
			this.activityTracker.userLogged(idApplication, idUserSHA1, userArea, optParametersAsList);
		} catch (final DataAccessException dataAccessException) {
			LOG.error(dataAccessException);
		}
		return VIEW_NAME_SAVED;
	}

	/**
	 * get date
	 * @param model request method (get or post)
	 * @param appId application's id
	 * @return target view
	 */
	@RequestMapping(method = RequestMethod.GET, params = "action=" + ACTION_GET_DATA)
	public String getData(final Model model, @RequestParam(IDAPP) final String appId) {

		model.addAttribute("attributeName", new Object()); // new Object ==>
		// n'importe quel
		// objet métier
		return VIEW_NAME_GET_DATA;
	}

	/**
	 * Servlet test
	 * @param model request method (get or post)
	 * @return target view
	 */
	@RequestMapping(method = RequestMethod.GET, params = "action=" + ACTION_HELLO)
	public String hello(final Model model) {
		model.addAttribute("version", VERSION);
		return VIEW_NAME_HELLO;
	}

	/**
	 * This method is in charge of handling the exception thrown by the
	 * treatment
	 *
	 * @param model model
	 * @param e the exception thrown
	 * @return target view
	 */
	private String error(final Model model, final Exception e) {
		model.addAttribute(ATTRIBUTE_ERROR_MESSAGE, e.getMessage());
		return VIEW_NAME_ERROR;
	}

	/**
	 * returns a map
	 * @param optParameters optional parameters
	 * @return optional parameters
	 */
	private List<OptionalParameter> processOptParametersIntoList(final String optParameters) {
		final List<OptionalParameter> params = new ArrayList<OptionalParameter>();
		final String[] paramsSplitted = optParameters.split(CHAR_OPT_PARAMETERS);
		String[] pairValueSplitted;
		for (String pair : paramsSplitted) {
			if (pair.endsWith("|")) {
				pair = pair.concat("null");
			}
			pairValueSplitted = pair.split("\\" + CHAR_OPT_PARAMETERS_VALUES);
			if (pairValueSplitted != null && pairValueSplitted.length >= NB_ELEMENT_PER_PAIRVALUE) {
				params.add(new OptionalParameter(pairValueSplitted[0], pairValueSplitted[1]));
			}
		}
		return params;
	}

	// FFT 3562
	/**
	 * Methode cron appel? toutes les heures. Recherche la liste des
	 * utilisateurs habilit?s, pour alimenter le fichier authorized.csv Dans le
	 * fichier authorized.csv on en loggue que les utilisateurs Activ?, et les
	 * utilisateurs qui appartiennent ? un groupe autre que admin et annymous.
	 * CFR: bizarrement, il est impossible de donner le nom d'une propriété Spring
	 * en utilisant le "placeholderPrefix" sans laisser uniquement l'accolade fermante...
	 */
	@Scheduled(cron = "${metrology.tracker.lessfrequency}")
	public void authorizedUser() {
		try {
			final List<UserDTO> users = this.userService.find();
			Group userGroup;
			String idUserSHA1;
			for (final UserDTO user : users) {
				if (user.getActivated()) {
					userGroup = null;
					idUserSHA1 = SecurityUtil.encode(user.getLogin(), SecurityUtil.SECRET_KEY);
					//Parcours la list des groupes de l'utilisateurs.
					for (final Group group : SafetyUtils.emptyIfNull(user.getGroups())) {
						if (group != Group.ADMINISTRATEUR && group != Group.ANONYMOUS) {
							userGroup = group;
						}
					}
					if (userGroup != null) {
						this.activityTracker.authorizedUser(this.getSiteName(), idUserSHA1, user.getLocation(), userGroup.toString());
					}
				}
			}

		} catch (final DataAccessException dataAccessException) {
			LOG.error(dataAccessException);
		} catch (final UserServiceException userServiceException) {
			LOG.error(userServiceException);
		}
	}

	/**
	 * @param activityTracker
	 *            the activityTracker to set
	 */
	public void setActivityTracker(final ActivityTracker activityTracker) {
		this.activityTracker = activityTracker;
	}

	/**
	 * @return the siteName
	 */
	private String getSiteName() {

		if (this.siteName == null) {
			try {
				final String siteNameWithEscapes = this.parameterService.getParameterValue(
						com.services.paramservice.constants.ParamServiceConstants.GENERAL_DOMAIN_KEY,
						SiteConstants.SITE_NAME_PARAMETER_KEY);

				// Escapes management
				if (siteNameWithEscapes != null) {
					this.siteName = siteNameWithEscapes.trim();
					if (this.siteName.length() == 0) {
						this.siteName = UNKNOWN_APPLICATION;
					}
				}
			} catch (final ParameterServiceException parameterServiceException) {
				LOG.error(parameterServiceException);
			}
		}

		return this.siteName;
	}

	/**
	 * @return the siteVersion
	 */
	private String getSiteVersion() {

		if (this.siteVersion == null) {
			try {
				this.siteVersion = this.parameterService.getParameterValue(
						com.services.paramservice.constants.ParamServiceConstants.GENERAL_DOMAIN_KEY,
						SiteConstants.SITE_VERSION_PARAMETER_KEY);
			} catch (final ParameterServiceException parameterServiceException) {
				LOG.error(parameterServiceException);
			}
		}
		return this.siteVersion;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(final IUserService userService) {
		this.userService = userService;
	}
}
