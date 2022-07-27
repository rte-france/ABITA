/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.batch.entity;

import com.dao.common.entity.AbstractEntity;
import com.dto.batch.BatchMessageSeverityLevel;

/**
 * Class containing a batch message.
 *
 * @author
 */
public class BatchMessage extends AbstractEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 333678884685455011L;

	/**
	 * Batch message content.
	 */
	private String message;

	/**
	 * User NNI.
	 */
	private String userLogin;

	/**
	 * The message's severity level
	 */
	private BatchMessageSeverityLevel level;

	/**
	 * Instantiates a new batch message.
	 */
	public BatchMessage() {
		super();
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * @return the userLogin
	 */
	public String getUserLogin() {
		return userLogin;
	}

	/**
	 * @param userLogin the userLogin to set
	 */
	public void setUserLogin(final String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * Getter of {@link BatchMessage#level}
	 *
	 * @return the current value of the property
	 */
	public BatchMessageSeverityLevel getLevel() {
		return level;
	}

	/**
	 * Setter of {@link BatchMessage#level}
	 *
	 * @param level the new value to assign
	 */
	public void setLevel(BatchMessageSeverityLevel level) {
		this.level = level;
	}
}
