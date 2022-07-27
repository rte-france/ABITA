/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.auditservice.constants;

import com.services.common.util.MessageSupport;

/**
 *
 * @author
 * @version 1.0
 */
public final class ConstantsActivityTracker {

	/**
	 * Private constructor
	 */
	private ConstantsActivityTracker() {
		super();
	}

	/**
	 * The parameter name for the action as for example HELLO, RECORD
	 */
	public static final String ACTION = "action";

	/**
	 * The parameter name for the timestamp when begins the action
	 */
	public static final String FROM = "from";

	/**
	 * The parameter name for the timestamp when the action is ended
	 */
	public static final String TO = "to";

	/**
	 * The parameter name for the application identifier
	 */
	public static final String IDAPP = "idApp";

	/**
	 * The parameter name for the user identifier
	 */
	public static final String IDUSER = "idUser";

	/**
	 * The parameter name for the component identifier
	 */
	public static final String IDCOMPONENT = "id";

	/**
	 * The parameter name for the transaction identifier.
	 */
	public static final String TRANSACTIONID = "idTr";

	/**
	 * User login, or "nni"
	 */
	public static final String USER_ID = "nni";

	/**
	 * User last name
	 */
	public static final String USER_LAST_NAME = "lname";

	/**
	 * User first name
	 */
	public static final String USER_FIRST_NAME = "fname";

	/**
	 * User area
	 */
	public static final String USER_AREA = "area";

	/**
	 * Optionnal parameters to log
	 */
	public static final String OPTIONAL_PARAMETERS = "optParams";

	/**
	 * The parameter value for the PING action
	 */
	public static final String ACTION_HELLO = "HELLO";

	/**
	 * The parameter value for the SAVING action
	 */
	public static final String ACTION_SAVE_DURATION = "SAVEDURATION";

	/**
	 * The parameter value for the SAVING action
	 */
	public static final String ACTION_COUNT = "COUNT";

	/**
	 * The parameter value for the SAVING action
	 */
	public static final String ACTION_USER_LOGGED = "USERLOGGED";

	/**
	 * The parameter value for the SAVING action
	 */
	public static final String ACTION_GET_DATA = "GETDATA";

	/**
	 * Attribute for the version of the the controller
	 */
	public static final String ATTRIBUTE_VERSION = "version";

	/**
	 * Attribute for the message of error
	 */
	public static final String ATTRIBUTE_ERROR_MESSAGE = "messageErreur";

	/**
	 * A message of error for a request without action
	 */
	public static final String MESSAGE_NO_ACTION =
	    MessageSupport.getMessage("com.services.auditservice.resource.auditservice", "message.no.action");

	/**
	 * A message if the to parameter is before the from parameter
	 */
	public static final String MESSAGE_BEFORE_END =
	    MessageSupport.getMessage("com.services.auditservice.resource.auditservice", "message.before.end");

	/**
	 * The &laquo; hello &raquo; view name for responding to a ping
	 */
	public static final String VIEW_NAME_HELLO = "hello";

	/**
	 * The parameter value for the PING action
	 */
	public static final String ACTION_HOME = "HOME";

	/**
	 * The &laquo; hello &raquo; view name for responding to a ping
	 */
	public static final String VIEW_NAME_HOME = "home";

	/**
	 * The name of the view if any error occurs
	 */
	public static final String VIEW_NAME_ERROR = "error";

	/**
	 * The &laquo; hello &raquo; view name if the controller successfully saved the activity.
	 */
	public static final String VIEW_NAME_SAVED = "saved";

	/**
	 * The &laquo; hello &raquo; view name if the controller successfully saved the activity.
	 */
	public static final String VIEW_NAME_GET_DATA = "data";
	/**
	 * The &laquo; hello &raquo; view name if the controller successfully saved the activity.
	 */
	public static final String PARAM_TRACKER = "tracker";
	/**
	 * The &laquo; hello &raquo; view name if the controller successfully saved the activity.
	 */
	public static final String PARAM_REPOSITORY = "repository";
	/**
	 * character used to split several optional parameters
	 */
	public static final String CHAR_OPT_PARAMETERS = ";";
	/**
	 * character used to split name / value in optional parameters
	 */
	public static final String CHAR_OPT_PARAMETERS_VALUES = "|";

	// Constants en "public" pour pouvoir y accÃ©der depuis de classes d'autres packages
	/** Nombre initial de parameters pour l'indicateur duration */
	public static final int INITIAL_NUMBER_PARAMETER_TRACK_DURATION = 6; // FFT 3563
	/** Nombre initial de parameters pour l'indicateur duration */
	public static final int INITIAL_NUMBER_PARAMETER_TRACK_CLICK_NUMBER = 4; //FFT 3565
	/** Nombre initial de parameters pour l'indicateur duration */
	public static final int INITIAL_NUMBER_PARAMETER_USER_LOGED = 6;
	// FFT 3562
	/** Nombre initial de parameters pour l'indicateur authorized */
	public static final int INITIAL_NUMBER_PARAMETER_TRACK_AUTHORIZED = 4;

	/**
	 * file system path delimiter
	 */
	/** System path delimiter */
	public static final String SYSTEM_PATH_DELIMITER = System.getProperty("file.separator");

}
