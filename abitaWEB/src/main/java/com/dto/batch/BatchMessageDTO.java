package com.dto.batch;

import com.dto.AbstractDTO;

/**
 * Message relevé lors d'un gros traitement
 *
 * @author
 */
public class BatchMessageDTO extends AbstractDTO {
	/**
	 * Identifiant unique de serialisation
	 */
	private static final long serialVersionUID = -7048652829016869916L;

	/**
	 * Le contenu du message
	 */
	private String message;

	/**
	 * Le login de l'utilisateur
	 */
	private String userLogin;

	/**
	 * Le niveau de sévérité du message
	 */
	private BatchMessageSeverityLevel level;

	/**
	 * Constructeur
	 */
	public BatchMessageDTO() {
		super();
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
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
		return this.userLogin;
	}

	/**
	 * @param userLogin the userLogin to set
	 */
	public void setUserLogin(final String userLogin) {
		this.userLogin = userLogin;
	}

	/**
	 * Getter de {@link BatchMessageDTO#level}
	 *
	 * @return la valeur de la propriété
	 */
	public BatchMessageSeverityLevel getLevel() {
		return level;
	}

	/**
	 * Setter de {@link BatchMessageDTO#level}
	 *
	 * @param level la nouvelle valeur à affecter
	 */
	public void setLevel(BatchMessageSeverityLevel level) {
		this.level = level;
	}
}
