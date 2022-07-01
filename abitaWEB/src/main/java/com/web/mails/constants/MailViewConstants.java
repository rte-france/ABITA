package com.web.mails.constants;

/**
 * Classe de constantes pour la partie présentation des envois de mails
 *
 * @author
 */
public final class MailViewConstants {
	/**
	 * Constructeur privé
	 */
	private MailViewConstants() {
	}

	/** La classe CSS pour les mails envoyés */
	public static final String CSS_CLASS_SUCCESS = "success";

	/** La classe CSS pour les mails non envoyés */
	public static final String CSS_CLASS_FAILURE = "failure";

    /** La classe CSS pour les mails en cours d'envoi */
    public static final String CSS_CLASS_IN_PROGRESS = "inProgress";

	/** Code indiquant que le mail a été envoyé avec succès */
	public static final int SMTP_SUCCESS_CODE = 250;

	/** Le préfix dans le bundle pour les libellés des status */
	public static final String STATUS_BUNDLE_PREFIX = "mail.filter.status.";

	/** Statut à afficher lorsque le mail a été envoyé avec succès */
	public static final String STATUS_SUCCESS = "OK";

	/** Statut à afficher lorsqu'une erreur est survenue pendant l'envoi du mail */
	public static final String STATUS_FAILURE = "KO";
}
