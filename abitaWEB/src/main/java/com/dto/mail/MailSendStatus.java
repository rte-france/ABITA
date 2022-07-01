package com.dto.mail;

/**
 * Created with IntelliJ IDEA.
 * @author
 * Date: 07/02/14
 * Time: 17:29
 * This class is the result of a mail send
 */
public enum MailSendStatus {
    /** Tous */
    ALL(1),
    /** Les mails envoyés */
    SENT_OK(2),
    /** Les mails dont l'envoi a échoué */
    ERROR(3),
    /** Les mails dont l'envoi est en cours */
    IN_PROGRESS(4);

    /** La valeur associée au statut */
    private Integer value;

    /**
     * Constructeur privé
     *
     * @param value la valeur interne
     */
    private MailSendStatus(Integer value) {
        this.value = value;
    }

    /**
     * function toString overloaded in order to simplify display
     * @return the name of the property, lower cased
     */
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

    /**
     * @return the value
     */
    public Integer getValue() {
        return value;
    }

}
