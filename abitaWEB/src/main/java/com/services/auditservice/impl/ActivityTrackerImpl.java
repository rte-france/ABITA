package com.services.auditservice.impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.dao.audit.impl.ActivityTrackerDataAccess;
import com.dto.EventDurationDTO;
import com.services.auditservice.ActivityTracker;
import com.services.auditservice.exception.DataAccessException;
import com.web.audit.data.AuthorizedUser;
import com.web.audit.data.Count;
import com.web.audit.data.Duration;
import com.web.audit.data.OptionalParameter;
import com.web.audit.data.UserLogged;

/**
 * Implementation file for the tracker service
 * @author
 *
 */
public class ActivityTrackerImpl implements ActivityTracker, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -8371282661751889978L;

	/**
	 * The Data access service
	 */
	private ActivityTrackerDataAccess dataAccess;

	/**
	 * Save log duration
	 * @param idApplication application's id
	 * @param eventDuration event duration
	 * @param siteVersion site's version
	 * @throws DataAccessException data access exception
	 */
	@Override
	public void saveDuration(final String idApplication, String siteVersion, EventDurationDTO eventDuration)
			throws DataAccessException {
		Duration duration = new Duration();
		duration.setApplicationId(idApplication);
		duration.setIdComponent(eventDuration.getIdElement());
		// FFT 3563 Suppression de l'id technique
		//duration.setIdTransaction(idTransaction);
		duration.setSiteVersion(siteVersion);
		// FFT 3563
		duration.setTime(eventDuration.getEndTime() - eventDuration.getStartTime());
		duration.setIdUser(eventDuration.getNniUser());
		duration.setOptionalParameters(eventDuration.getOptionalParameters());
		duration.setHour(Calendar.getInstance());
		dataAccess.saveDuration(duration);
	}

	@Override
	public void count(String idApplication, String idUser, String idComponent, List<OptionalParameter> optParams)
			throws DataAccessException {
		Count count = new Count();
		count.setApplicationId(idApplication);
		// FFT 3565
		count.setIdComponent(idComponent);
		count.setOptionalParameters(optParams);
		count.setIdUser(idUser);
		count.setHour(Calendar.getInstance());
		dataAccess.saveCount(count);
	}

	@Override
	public void userLogged(String applicationId, String userId, String area, List<OptionalParameter> optParams)
			throws DataAccessException {
		UserLogged user = new UserLogged();
		user.setApplicationId(applicationId);
		user.setIdUser(userId);
		user.setArea(area);
		user.setTime(GregorianCalendar.getInstance());
		user.setOptionalParameters(optParams);
		dataAccess.saveUser(user);
	}

	//FFT 3562
	@Override
	public void authorizedUser(String applicationId, String userId, String area, String profile)
			throws DataAccessException {
		AuthorizedUser authorizedUser = new AuthorizedUser();
		authorizedUser.setApplicationId(applicationId);
		authorizedUser.setIdUser(userId);
		authorizedUser.setArea(area);
		authorizedUser.setProfile(profile);
		dataAccess.saveAuthorizedUser(authorizedUser);
	}

	/**
	 * @param dataAccess the dataAccess to set
	 */
	public void setDataAccess(ActivityTrackerDataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

}
