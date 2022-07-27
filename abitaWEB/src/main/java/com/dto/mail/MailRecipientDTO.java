/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto.mail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class MailRecipientDTO.
 */
public class MailRecipientDTO implements Serializable {

	/**serialUID Version */
	private static final long serialVersionUID = 1628211898539994342L;

	/** The recipients. */
	private List<String> recipients;

	/**
	 * Instantiates a new mail recipient dto.
	 */
	public MailRecipientDTO() {
		recipients = new ArrayList<String>();
	}

	/**
	 * Adds the recipient.
	 *
	 * @param recipient the recipient
	 */
	public void addRecipient(String recipient) {
		recipients.add(recipient);
	}

	/**
	 * Gets the recipients.
	 *
	 * @return the recipients
	 */
	public List<String> getRecipients() {
		return recipients;
	}
}
