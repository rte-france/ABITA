/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto.mail;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.MultiPartEmail;

/**
 * Cette classe sert à représenter le contenu d'un mail texte avec des pièces jointes
 * @author :
 * @since : 07/02/14 16:48
 */
public class TextWithAttachmentsMailContentDTO extends AbstractMailContentDTO {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6178908436545244914L;

    /**
     * Constructeur pour permettre à l'utilisateur de créer un mail simplement
     * @param tos destinataires
     * @param ccs destinataires en copie
     * @param bccs destinataires en copie cachée
     * @param from expéditeur
     * @param subject sujet
     * @param message message
     * @param attachments pièces jointes
     */
    public TextWithAttachmentsMailContentDTO(
            final MailRecipientDTO tos,
            final MailRecipientDTO ccs,
            final MailRecipientDTO bccs,
            final String from,
            final String subject,
            final String message,
            final MailAttachmentDTO[] attachments) {
        super(tos, ccs, bccs, from, subject, message, attachments);
    }

    /**
     * Retourne l'email du type associé
     * @return l'object email correctement instancié correspondant
     */
    @Override
    public Email createMail() {
        return new MultiPartEmail();
    }
}
