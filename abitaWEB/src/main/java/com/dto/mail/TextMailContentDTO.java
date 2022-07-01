package com.dto.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

/**
 * Cette classe sert à représenter le contenu d'un mail texte
 * @author :
 * @since : 07/02/14 16:48
 */
public class TextMailContentDTO extends AbstractMailContentDTO {

    /**
     * serialVersionUID utile pour la sérisalisation
     */
    private static final long serialVersionUID = 505208566935900923L;

    /**
     * Constructeur pour permettre à l'utilisateur de créer un mail simplement
     * @param tos destinataires
     * @param ccs destinataires en copie
     * @param bccs destinataires en copie cachée
     * @param from expéditeur
     * @param subject sujet
     * @param message message
     */
    public TextMailContentDTO(
            final MailRecipientDTO tos,
            final MailRecipientDTO ccs,
            final MailRecipientDTO bccs,
            final String from,
            final String subject,
            final String message) {
        super(tos, ccs, bccs, from, subject, message, null);
    }

    /**
     * Retourne l'email du type associé
     * @return l'object email correctement instancié correspondant
     */
    @Override
    public Email createMail() {
        return new SimpleEmail();
    }
}
