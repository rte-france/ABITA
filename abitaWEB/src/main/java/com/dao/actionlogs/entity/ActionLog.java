package com.dao.actionlogs.entity;

import com.dao.common.entity.AbstractEntity;
import com.dao.user.entity.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe représentant la trace d'une action importante
 *
 * @author
 */
public class ActionLog extends AbstractEntity {
	/**
	 * Id
	 */
	private static final long serialVersionUID = 3231780517009446086L;

	/** La date de début de l'action */
	private Date date;

	/** Composant / fonction ayant initié l'action à tracer */
	private String origin;

	/** L'utilisateur responsable */
	private User user;

	/** Le status à la fin de l'opération */
	private String status;

	/**
	 * Intitulé / description succincte de l'action
	 */
	private String title;

	/** Les messages */
	private Set<ActionLogMessage> messages = new HashSet<ActionLogMessage>();

	/**
	 * Default constructor
	 */
	public ActionLog() {
	}

	/**
	 * @return the messages
	 */
	public Set<ActionLogMessage> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(Set<ActionLogMessage> messages) {
		this.messages = messages;
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
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
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
}
