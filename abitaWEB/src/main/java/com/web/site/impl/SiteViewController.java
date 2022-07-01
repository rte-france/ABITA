package com.web.site.impl;

import com.dto.UserDTO;
import com.services.common.constants.Constants;
import com.services.common.exception.NotFoundException;
import com.services.common.util.MessageSupport;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import com.services.theme.IThemeService;
import com.services.user.IUserService;
import com.services.user.exception.UserServiceException;
import com.web.login.impl.AccessSupport;
import com.web.login.impl.ThreadUserHolder;
import com.web.site.constants.SiteConstants;
import com.web.site.data.ThemeUserBean;
import com.web.site.impl.ThemeSwitcherBean;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controleur du template du site /WebContent/templates/site/site.xhtml
 */
public class SiteViewController implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 623088047412198022L;

	/** logger */
	private static final Log LOG = LogFactory.getLog(SiteViewController.class);

	/** Bundle de messages. */
	private static final ResourceBundle CUSTOMBUNDLE = ResourceBundle.getBundle(SiteConstants.CUSTOM_BUNDLE);

	/** parametreService */
	private ParameterService parameterService;

	/**
	 * Le service pour la récupération des thèmes disponibles
	 */
	private IThemeService themeService;

	/**
	 * Backing bean du theme rattache a l'utilisateur
	 */
	private ThemeUserBean themeUserBean;

	/** Façade pour la gestion des accès et de l'utilisateur connecté */
	private IUserService userService;

	/**
	 * Construct SiteViewController.
	 */
	public SiteViewController() {
		super();
	}

	/**
	 * getter of contextPath.
	 * @return request context path
	 */
	public String getContextPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}

	/**
	 * getter of date serveur.
	 * @return server date
	 */
	public Date getDateServeur() {
		return new Date();
	}

	/**
	 * getter of site name.
	 * @return site name
	 */
	public String getSiteName() {
		try {
			return this.parameterService.getParameterValue(
					com.services.paramservice.constants.ParamServiceConstants.GENERAL_DOMAIN_KEY,
					SiteConstants.SITE_NAME_PARAMETER_KEY);
		} catch (ParameterServiceException parameterServiceException) {
			MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, "unexpected_exception");
			return null;
		}
	}

	/**
	 * getter of sub site name.
	 * @return site sub name
	 */
	public String getSiteSubName() {
		try {
			return this.parameterService.getParameterValue(
					com.services.paramservice.constants.ParamServiceConstants.GENERAL_DOMAIN_KEY,
					SiteConstants.SITE_SUB_NAME_PARAMETER_KEY);
		} catch (ParameterServiceException parameterServiceException) {
			MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, "unexpected_exception");
			return null;
		}
	}

	/**
	 * getter of version.
	 * @return site version
	 */
	public String getSiteVersion() {
		try {
			return this.parameterService.getParameterValue(
					com.services.paramservice.constants.ParamServiceConstants.GENERAL_DOMAIN_KEY,
					SiteConstants.SITE_VERSION_PARAMETER_KEY);
		} catch (ParameterServiceException parameterServiceException) {
			MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, "unexpected_exception");
			return null;
		}
	}

	/**
	 * getter of timeout Retourne un timeout de 0 si le timeout n'est pas defini
	 * @return timeout
	 */
	public int getTimeout() {
		try {
			String timeoutValue = this.parameterService.getParameterValue(
					com.services.paramservice.constants.ParamServiceConstants.GENERAL_DOMAIN_KEY,
					SiteConstants.SITE_TIMEOUT);
			int res = 0;
			if (timeoutValue != null) {
				res = Integer.parseInt(timeoutValue);
			}
			return res;
		} catch (ParameterServiceException parameterServiceException) {
			MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, "unexpected_exception");
			return 0;
		}
	}

	/**
	 * Permet de recuperer la liste des themes disponibles pour l'utilisateur
	 * @return une liste de themes
	 */
	public Map<String, String> getThemes() {

		com.web.site.impl.ThemeSwitcherBean themeSwitcherBean = ThemeSwitcherBean.INSTANCE;
		return themeSwitcherBean.getTheme(this.themeService);
	}

	/**
	 * Permet de recuperer le theme attache a l'utilisateur
	 * S'il n'existe pas, alors on prend celui defini par defaut
	 * @return le theme de l'utilisateur ou celui par defaut s'il n'existe pas
	 */
	public String getUserTheme() {
		String theme = null;
		UserDTO user = AccessSupport.getCurrentUser();
		if (user != null && user.getTheme() != null) {
			theme = user.getTheme().getName();
		} else {
			theme = ResourceBundle.getBundle(Constants.COMMON_BUNDLE).getString(SiteConstants.DEFAULTTHEME);
		}
		this.themeUserBean.setUserThemeName(theme);
		return this.themeUserBean.getUserThemeName();
	}

	/**
	 * Permet de mettre a jour un theme pour l'uilisateur
	 */
	public void saveTheme() {
		try {
            UserDTO currentUser = AccessSupport.getCurrentUser();
			this.userService.updateUserTheme(currentUser, this.themeUserBean.getUserThemeName());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(Constants.USER_KEY, currentUser);
            ThreadUserHolder.set(currentUser);
		} catch (final UserServiceException userServiceException) {
            LOG.error(userServiceException);
            if (ExceptionUtils.getRootCause(userServiceException) instanceof NotFoundException) {
                MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, Constants.COMMON_BUNDLE,
                        SiteConstants.ERROR_MESSAGES_UPDATE_THEME_NOT_FOUND);
            } else {
                MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, Constants.COMMON_BUNDLE,
                        SiteConstants.ERROR_MESSAGES_UPDATE_THEME);
            }
		}
	}

	/**
	 * Permet de mettre a jour un theme pour l'uilisateur -> restauration de la valeur sauvegardee
	 */
	public void cancelTheme() {
		this.themeUserBean.setUserThemeName(this.getUserTheme());
	}

	/**
	 * Permet de retranscrire la date de livraison en une chaine lisible
	 * @return une chaine avec une date formatee
	 */
	public String getConvertDeliveryDate() {

		final int beginDay = 6;
		final int endDay = 8;
		final int beginMonth = 4;
		final int beginYear = 0;

		String deliveryDate = CUSTOMBUNDLE.getString(SiteConstants.APPLICATION_DELIVERY_DATE);
		String deliveryDateFormated = deliveryDate.substring(beginDay, endDay) + "/"
				+ deliveryDate.substring(beginMonth, beginDay) + "/" + deliveryDate.substring(beginYear, beginMonth);

		return deliveryDateFormated;

	}

	/**
	 * setter of parameter service.
	 * @param parameterService the parameterService
	 */
	public void setParameterService(final ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	/**
	 * Setter du backing bean du theme rattache a l'utilisateur
	 * @param themeUserBean the themeUserBean to set
	 */
	public void setThemeUserBean(final ThemeUserBean themeUserBean) {
		this.themeUserBean = themeUserBean;
	}

	/**
	 * Listener du timeout de session
	 */
	public void timeoutListener() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
		    .remove(Constants.USER_KEY);
		((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
	}

	/**
	 * @param themeService the themeService to set
	 */
	public void setThemeService(final IThemeService themeService) {
		this.themeService = themeService;
	}

    /**
     * @param userService the userService to set
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
