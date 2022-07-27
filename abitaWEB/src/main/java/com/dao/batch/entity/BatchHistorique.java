/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.batch.entity;

import com.dao.common.entity.AbstractEntity;
import com.services.batchservice.constants.BatchStatut;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author
 *
 */
public class BatchHistorique extends AbstractEntity implements Serializable, Cloneable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 333678884685455011L;

	/**
	 * Le nom du batch.
	 */
	private String name;
	/**
	 * La date de lancement du batch
	 */
	private Date dateLancement;
	/**
	 * Statut du batch.
	 */
	private BatchStatut statut = BatchStatut.IN_PROGRESS;
	/**
	 * Messages associés au Batch.
	 */
	private Set<BatchMessage> messages;

	/**
	 * Constructor.
	 */
	public BatchHistorique() {
		super();
		messages = new HashSet<BatchMessage>();
		dateLancement = new Date();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the dateLancement
	 */
	public Date getDateLancement() {
		return dateLancement;
	}

	/**
	 * @param dateLancement the dateLancement to set
	 */
	public void setDateLancement(final Date dateLancement) {
		this.dateLancement = dateLancement;
	}

	/**
	 * @return the statut
	 */
	public BatchStatut getStatut() {
		return statut;
	}

	/**
	 * @param statut the statut to set
	 */
	public void setStatut(final BatchStatut statut) {
		this.statut = statut;
	}

	/**
	 * @return the messages
	 */
	public Set<BatchMessage> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(final Set<BatchMessage> messages) {
		this.messages = messages;
	}

	/**
	 * Adds the message.
	 *
	 * @param message the message
	 */
	public void addMessage(final BatchMessage message) {
		messages.add(message);
	}
}
