package com.dao.audit.impl;

import static com.services.auditservice.constants.ConstantsActivityTracker.INITIAL_NUMBER_PARAMETER_TRACK_AUTHORIZED;
import static com.services.auditservice.constants.ConstantsActivityTracker.INITIAL_NUMBER_PARAMETER_TRACK_CLICK_NUMBER;
import static com.services.auditservice.constants.ConstantsActivityTracker.INITIAL_NUMBER_PARAMETER_TRACK_DURATION;
import static com.services.auditservice.constants.ConstantsActivityTracker.INITIAL_NUMBER_PARAMETER_USER_LOGED;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.dao.audit.exception.AuditException;
import com.dto.IndicatorsDTO;
import com.services.auditservice.exception.DataAccessException;
import com.services.common.constants.DateTimeConstants;
import com.services.common.util.MessageSupport;
import com.services.common.util.SafetyUtils;
import com.services.common.util.StringUtils;
import com.web.audit.data.AuthorizedUser;
import com.web.audit.data.Count;
import com.web.audit.data.Duration;
import com.web.audit.data.OptionalParameter;
import com.web.audit.data.UserLogged;

/**
 * File system implementation for the data access.
 * @author
 *
 */
@Repository
public class ActivityTrackerDataAccessFileSystem implements ActivityTrackerDataAccess, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2447824466302060672L;

	/** The logger */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * List containing the durations received from the client.
	 */
	private static List<String[]> durations = new ArrayList<String[]>();

	/**
	 * List containing the counts received from the client.
	 */
	private static List<String[]> counts = new ArrayList<String[]>();

	/**
	 * List containing the "user logged" informations received from the client.
	 */
	private static List<String[]> users = new ArrayList<String[]>();

	// FFT 3562
	/**
	 * List containing the "user authorized" informations received from the client.
	 */
	private static List<String[]> authorized = new ArrayList<String[]>();

	/**
	 * Id of the calling application.
	 */
	private String applicationId;

	/**
	 * The component in charge of saving the data to the disj
	 */
	@Autowired
	@Qualifier("fileSystemDataSaverWorker")
	private FileSystemDataSaverWorker worker;

	/**
	 * BUSINESS METHODS
	 */
	/**
	 * saveDuration() : adds the durations into the list which will
	 * be stocked into the file by the method saveToFileSystem()
	 *
	 * @param duration objet containing the durations to be logged
	 * @exception DataAccessException data access exception
	 */
	@Override
	public void saveDuration(Duration duration) throws DataAccessException {
		String[] durationArray = durationObjectToArray(duration);
		durations.add(durationArray);
	}

	/**
	 * saveCount() : adds the counts into the list which will
	 * be stocked into the file by the method saveToFileSystem()
	 *
	 * @param count objet containing the counts to be logged
	 * @exception DataAccessException data access exception
	 */
	@Override
	public void saveCount(Count count) throws DataAccessException {
		String[] countArray = countObjectToArray(count);
		counts.add(countArray);
	}

	/**
	 * saveUser() : adds the users into the list which will
	 * be stocked into the file by the method saveToFileSystem()
	 *
	 * @param userInformations objet containing the users to be logged
	 * @exception DataAccessException data access exception
	 */
	@Override
	public void saveUser(UserLogged userInformations) throws DataAccessException {
		String[] userArray = userObjectToArray(userInformations);
		users.add(userArray);
	}

	//FFT 3562
	/**
	 * saveUser() : adds the users into the list which will
	 * be stocked into the file by the method saveToFileSystem()
	 *
	 * @param authorizedUser objet containing the users to be logged
	 * @exception DataAccessException data access exception
	 */
	@Override
	public void saveAuthorizedUser(AuthorizedUser authorizedUser) throws DataAccessException {
		String[] authorizedUserArray = authorizedUserObjectToArray(authorizedUser);
		authorized.add(authorizedUserArray);
	}

	/**
	 * OTHER
	 */

	/**
	 * Convert a Duration object into an array of String
	 *
	 * @param duration the object to convert
	 * @return an array containing the duration attributes
	 */
	private String[] durationObjectToArray(Duration duration) {
		List<OptionalParameter> params = duration.getOptionalParameters();
		String[] array = createActivityTrackerArray(INITIAL_NUMBER_PARAMETER_TRACK_DURATION, params);
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimeConstants.HOUR_FORMAT, MessageSupport.getLocale());
		//		String[] array = new String[4 + (params == null ? 0 : params.size()) + (log.isDebugEnabled() ? 1 : 0)];
		int i = 0;
		array[i++] = duration.getApplicationId();
		// FFT 3563 ajout n? de version
		array[i++] = duration.getSiteVersion();
		// end FFT 3563
		array[i++] = duration.getIdUser();
		array[i++] = duration.getIdComponent();
		array[i++] = sdf.format(duration.getHour().getTime());
		array[i++] = String.valueOf(duration.getTime());
		// Param idTransaction non utilise pour l'instant, par contre il est toujours obligatoire cote javascript
		// Attention, pour la gestion du nombre de parametres il faut mettre a jour la constante
		//INITIAL_NUMBER_PARAMETER_TRACK_DURATION
		//		array[3] = duration.getIdTransaction();

		StringBuffer str = new StringBuffer();
		for (OptionalParameter entry : SafetyUtils.emptyIfNull(params)) {
			str.delete(0, str.length());
			str.append(entry.getKey()).append(":").append(entry.getValue());
			array[i++] = str.toString();
		}
		return array;
	}

	/**
	 * Convert a Count object into an array of String
	 *
	 * @param count the object to convert
	 * @return an array containing the count attributes
	 */
	private String[] countObjectToArray(Count count) {
		List<OptionalParameter> params = count.getOptionalParameters();
		String[] array = createActivityTrackerArray(INITIAL_NUMBER_PARAMETER_TRACK_CLICK_NUMBER, params);
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimeConstants.HOUR_FORMAT, MessageSupport.getLocale());
		int i = 0;
		array[i++] = count.getApplicationId();
		array[i++] = count.getIdUser();
		array[i++] = count.getIdComponent();
		// FFT 3565 Suppression de l'id technique
		//array[i++] = String.valueOf(count.getCount());
		// end FFT
		array[i++] = sdf.format(count.getHour().getTime());
		StringBuffer str = new StringBuffer();
		for (OptionalParameter entry : SafetyUtils.emptyIfNull(params)) {
			str.delete(0, str.length());
			str.append(entry.getKey()).append(":").append(entry.getValue());
			array[i++] = str.toString();
		}

		return array;
	}

	/**
	 * Convert a UserLogged object into an array of String
	 *
	 * @param user the object to convert
	 * @return an array containing the user attributes
	 */
	private String[] userObjectToArray(UserLogged user) {
		List<OptionalParameter> params = user.getOptionalParameters();
		SimpleDateFormat sdf = new SimpleDateFormat(DateTimeConstants.DATE_FORMAT_DATA_FILES,
				MessageSupport.getLocale());
		String[] array = createActivityTrackerArray(INITIAL_NUMBER_PARAMETER_USER_LOGED, params);
		//		String[] array = new String[6];
		int i = 0;
		array[i++] = user.getApplicationId();
		array[i++] = user.getIdUser();
		array[i++] = user.getArea();
		array[i++] = sdf.format(user.getTime().getTime());
		StringBuffer str = new StringBuffer();
		for (OptionalParameter entry : SafetyUtils.emptyIfNull(params)) {
			str.delete(0, str.length());
			str.append(entry.getKey()).append(":").append(entry.getValue());
			array[i++] = str.toString();
		}
		return array;
	}

	/**
	 * Transform AuthorizedUser to an array of String
	 * @param authorizedUser object to map
	 * @return Array of String
	 */
	//FFT 3562
	private String[] authorizedUserObjectToArray(AuthorizedUser authorizedUser) {
		List<OptionalParameter> params = authorizedUser.getOptionalParameters();
		String[] array = createActivityTrackerArray(INITIAL_NUMBER_PARAMETER_TRACK_AUTHORIZED, params);

		int i = 0;
		array[i++] = authorizedUser.getApplicationId();
		array[i++] = authorizedUser.getIdUser();
		array[i++] = authorizedUser.getArea();
		array[i++] = authorizedUser.getProfile();

		StringBuffer str = new StringBuffer();
		for (OptionalParameter entry : SafetyUtils.emptyIfNull(params)) {
			str.delete(0, str.length());
			str.append(entry.getKey()).append(":").append(entry.getValue());
			array[i++] = str.toString();
		}

		return array;
	}

	/**
	 * Create and initialize an array of Strings for the tracking. If DEBUG is enable the array will
	 * containt an aditional column with the timestamp.
	 *
	 * @param inialSize initial and fixed size for the indicator
	 * @param params map containing the dynamic parameters to be added to the file, il can be null
	 * @return String[] propertly intialized
	 */
	private String[] createActivityTrackerArray(int inialSize, List<OptionalParameter> params) {
		String[] array = new String[inialSize + (params == null ? 0 : params.size()) + (log.isDebugEnabled() ? 1 : 0)];

		if (log.isDebugEnabled()) {
			// Timestamp added if debug enabled
			array[array.length - 1] = "timeStamp:" + new Date().toString();
		}

		return array;
	}

	/**
	 * This scheduled task calls a worker in charge of writing the
	 * list in memory to the disk.
	 * CFR: bizarrement, il est impossible de donner le nom d'une propriété Spring
	 * en utilisant le "placeholderPrefix" sans laisser uniquement l'accolade fermante...
	 * The frequency is set in a cron expression in the metrology.properties file
	 * @throws AuditException exception when saving to filesystem
	 */
	@Scheduled(cron = "${metrology.tracker.frequency}")
	public void saveToFileSystem() throws AuditException {
		checkAndSetApplicationId();
		// write data to file system
		try {
			//FFT 3562 ajout de authorized
			IndicatorsDTO indicators = new IndicatorsDTO(durations, counts, users, authorized);
			worker.work(applicationId, indicators, false);
		} catch (IOException ioException) {
			log.error("\n\n=====================> Problem while accessing files for saving data : ", ioException);
			throw new AuditException(ioException);
		}

	}

	/**
	 * Is application ID set already ? if not, set it with in memory data.
	 */
	private void checkAndSetApplicationId() {
		if (StringUtils.isEmptyOrBlank(applicationId)) {
			if (durations.size() > 0) {
				applicationId = durations.get(0)[0];
			} else if (counts.size() > 0) {
				applicationId = counts.get(0)[0];
			} else if (users.size() > 0) {
				applicationId = users.get(0)[0];
			} else if (authorized.size() > 0) {
				// FFT
				applicationId = authorized.get(0)[0];
			}
		}
	}

	/**
	 * Call the saveTofileSystem method on object destruction.
	 * @throws Throwable throwable
	 */
	@Override
	protected void finalize() throws Throwable {
		saveToFileSystem();
		super.finalize();
	}

}
