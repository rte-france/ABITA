/**
 *
 */
package com.services.auditservice.impl;

import com.dto.EventDurationDTO;
import com.dto.UserDTO;
import com.services.auditservice.IActivityRecordService;
import com.services.auditservice.IDistantLoggerService;
import com.web.audit.data.IOptionalParametersResolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * The Class DistantActivityRecordService.
 *
 * @author
 */
public class DistantActivityRecordService implements IActivityRecordService, Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -3551250072498911091L;

	/** Logger. */
	private static final Log LOG = LogFactory.getLog(com.services.auditservice.impl.DistantActivityRecordService.class);

	/** Distant logger. */
	private IDistantLoggerService distantLoggerService;

	/** Optional parameters resolver. */
	private IOptionalParametersResolver optionalParametersResolver;

	/* (non-Javadoc)
	 * @see com.services.auditservice.IActivityRecordService#logConnectedUser(javax.servlet.http.HttpServletRequest, com.dao.user.entity.User)
	 */
	@Override
	public void logConnectedUser(HttpServletRequest request, UserDTO connectedUser) {
		LOG.debug("logConnectedUser of " + connectedUser.getLogin());
		distantLoggerService.logConnectedUser(request, connectedUser,
				optionalParametersResolver.getOptionalParameters(connectedUser));
	}

	/* (non-Javadoc)
	 * @see com.services.auditservice.IActivityRecordService#logEventDuration(javax.servlet.http.HttpServletRequest, java.lang.String, java.lang.String, long, long, java.util.List)
	 */
	@Override
	public void logEventDuration(HttpServletRequest request, EventDurationDTO eventDuration) {
		LOG.debug("logEventDuration");
		distantLoggerService.logEventDuration(request, eventDuration);
	}

	/* (non-Javadoc)
	 * @see com.services.auditservice.IActivityRecordService#logElementClick()
	 */
	@Override
	public void logElementClick() {
		LOG.debug("logElementClick");
		distantLoggerService.logElementClick();
	}

	/**
	 * Sets the distant logger service.
	 *
	 * @param distantLoggerService the distantLoggerService to set
	 */
	public void setDistantLoggerService(IDistantLoggerService distantLoggerService) {
		this.distantLoggerService = distantLoggerService;
	}

	/**
	 * Gets the optional parameters resolver.
	 *
	 * @return the optionalParametersResolver
	 */
	public IOptionalParametersResolver getOptionalParametersResolver() {
		return optionalParametersResolver;
	}

	/**
	 * Sets the optional parameters resolver.
	 *
	 * @param optionalParametersResolver the optionalParametersResolver to set
	 */
	public void setOptionalParametersResolver(IOptionalParametersResolver optionalParametersResolver) {
		this.optionalParametersResolver = optionalParametersResolver;
	}

}
