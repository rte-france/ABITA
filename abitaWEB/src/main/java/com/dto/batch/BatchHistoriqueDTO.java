/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto.batch;

import com.dto.AbstractDTO;
import com.services.batchservice.constants.BatchStatut;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Permet de tracer l'exécution d'un traitement
 *
 * @author
 */
public class BatchHistoriqueDTO extends AbstractDTO {
	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = 5374176806651371070L;

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
	private List<BatchMessageDTO> messages;

	/**
	 * Constructeur
	 */
	public BatchHistoriqueDTO() {
		super();
		this.messages = new ArrayList<BatchMessageDTO>();
		this.dateLancement = new Date();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
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
		return this.dateLancement;
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
		return this.statut;
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
	public List<BatchMessageDTO> getMessages() {
		return this.messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(final List<BatchMessageDTO> messages) {
		this.messages = messages;
	}

	/**
	 * Ajout du messages à la liste interne
	 *
	 * @param batchMessage le message à ajouter
	 */
	public void addMessage(final BatchMessageDTO batchMessage) {
		this.messages.add(batchMessage);
	}
}
