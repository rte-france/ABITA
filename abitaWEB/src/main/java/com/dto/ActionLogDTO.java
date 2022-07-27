/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Objet retourné par les services et affiché côté présentation
 *
 * @author
 */
public class ActionLogDTO extends AbstractDTO {

	/**
	 * Generated serial id
	 */
	private static final long serialVersionUID = -2351662744984350290L;

	/** La date de début de l'action */
	private Date date;

	/** Composant / fonction ayant initié l'action à tracer */
	private String origin;

	/** L'utilisateur responsable */
	private UserDTO user;

	/** Le status à la fin de l'opération */
	private String status;

	/**
	 * Intitulé / description succincte de l'action
	 */
	private String title;

	/** Les messages */
	private Set<ActionLogMessageDTO> messages = new HashSet<ActionLogMessageDTO>();

	/**
	 * Default constructor
	 */
	public ActionLogDTO() {
		super();
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the user
	 */
	public UserDTO getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserDTO user) {
		this.user = user;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the messages
	 */
	public Set<ActionLogMessageDTO> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(Set<ActionLogMessageDTO> messages) {
		this.messages = messages;
	}
}
