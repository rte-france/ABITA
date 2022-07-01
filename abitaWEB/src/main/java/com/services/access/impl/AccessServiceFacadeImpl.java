package com.services.access.impl;

import com.dto.UserDTO;
import com.services.access.IAccessServiceFacade;
import com.services.access.exception.AccessServiceFacadeException;
import com.services.auditservice.IActivityRecordService;
import com.services.authenticatorservice.AuthenticatorService;
import com.services.authenticatorservice.exception.*;
import com.services.common.exception.NotFoundException;
import com.services.paramservice.ParameterService;
import com.services.paramservice.constants.ParamServiceConstants;
import com.services.paramservice.exception.ParameterServiceException;
import com.services.user.IUserService;
import com.services.user.exception.UserNotFoundException;
import com.services.user.exception.UserServiceException;
import com.web.common.constants.AccessConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Implementation de {@link IAccessServiceFacade}
 *
 * @author
 */
public class AccessServiceFacadeImpl implements IAccessServiceFacade {

	/** Le loggueur */
	private static final Log LOG = LogFactory.getLog(com.services.access.impl.AccessServiceFacadeImpl.class);

	/** Le service pour la récupération des paramètres */
	private ParameterService parameterService;

	/** Le service pour l'authentification des utilisateurs */
	private AuthenticatorService authenticatorService;

	/** Le service de gestion des utilisateurs */
	private IUserService userService;

	/** Service pour le traçage des connexions */
	private IActivityRecordService recordService;

	@Override
	public UserDTO login(final String login, final String passwd) throws AccessServiceFacadeException {
		LOG.debug("login(\"" + login + "\", \"**********\")");
		try {
		  // the database user will be simply returned
      authenticateUser(login, passwd);
      return this.loginUserWithIdentifier(login);
		} catch (final UserServiceException userServiceException) {
			LOG.error(userServiceException.getMessage(), userServiceException);
			throw new AccessServiceFacadeException(userServiceException);
		} catch (UserNotFoundException e) {
            LOG.error(e.getMessage(), e);
            throw new AccessServiceFacadeException(e);
        }
    }

    /**
     * Login user with specific identifier
     * @param userIdentifier user identifier
     * @return user from DB (security disabled)
     * @throws UserServiceException Si une erreur survient
     * @throws UserNotFoundException Si l'utilisateur n'est pas trouvé
     */
    private UserDTO loginUserWithIdentifier(String userIdentifier) throws UserServiceException, UserNotFoundException {
        final UserDTO user = this.userService.findByLogin(userIdentifier);
        this.logConnectedUser(user);
        return user;
    }

    /**
     * Authenticate user with login and password.
     *
     * @param login user login
     * @param password user password
     * @return authenticated user info
     * @throws AccessServiceFacadeException exception on login
     */
    private void authenticateUser(final String login, final String password) throws AccessServiceFacadeException {
        try {
            this.authenticatorService.checkLogin(login, password);
        } catch (final InvalidLoginException invalidLoginException) {
            throw new AccessServiceFacadeException("user '" + login + "' is invalid", invalidLoginException);
        } catch (final InvalidPasswdException invalidPasswdException) {
            throw new AccessServiceFacadeException("user '" + login + "' has wrong passwd", invalidPasswdException);
        } catch (final PermissionException permissionException) {
            throw new AccessServiceFacadeException("User '" + login + "' has no permission to access the application",
                    permissionException);
        } catch (final InvalidAccountStateException invalidAccountStateException) {
            //	The exception must be thrown to show the message when the user is disabled.
            LOG.debug("Account de l'utilisateur \"" + login + "\" désactivé !!!");
            throw new AccessServiceFacadeException(invalidAccountStateException.getMessage(), invalidAccountStateException);
        } catch (final AuthenticatorServiceException authenticatorServiceException) {
            throw new AccessServiceFacadeException("Authentication failed", authenticatorServiceException);
        } catch (LoginException loginException) {
            throw new AccessServiceFacadeException(loginException.getMessage(), loginException);
        } catch (NotFoundException notFoundException) {
            throw new AccessServiceFacadeException(notFoundException.getMessage(), notFoundException);
        }
    }

    /**
     * Log the connected user.
     *
     * @param connectedUser user to be logged
     */
    private void logConnectedUser(final UserDTO connectedUser) {
        LOG.debug("loggin the connected user \"" + connectedUser.getLogin() + "\"");
        if (this.recordService != null) {
            final HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                    .getRequest();
            this.recordService.logConnectedUser(req, connectedUser);
        } else {
            LOG.debug("recordService NULL !!!!!!!!!!!!!!");
        }
    }

    /**
     * Checks if is enabled user update.
     *
     * @return true if user update is enabled, false otherwise
     * @throws ParameterServiceException paramater service exception
     */
    private boolean isEnabledUserUpdate() throws ParameterServiceException {
        return Boolean.parseBoolean(this.parameterService.getParameterValue(
                ParamServiceConstants.ACCESS_DOMAIN_KEY,
                AccessConstants.USER_UPDATE_KEY));
    }


    /**
     * @param parameterService the parameterService to set
     */
    public void setParameterService(final ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    /**
     * @param authenticatorService the authenticatorService to set
     */
    public void setAuthenticatorService(final AuthenticatorService authenticatorService) {
        this.authenticatorService = authenticatorService;
    }

    /**
     * @param userService the userService to set
     */
    public void setUserService(final IUserService userService) {
        this.userService = userService;
    }

    /**
     * @param recordService the recordService to set
     */
    public void setRecordService(final IActivityRecordService recordService) {
        this.recordService = recordService;
    }
}
