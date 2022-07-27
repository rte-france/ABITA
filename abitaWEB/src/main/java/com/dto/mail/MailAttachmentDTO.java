/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dto.mail;


/**
 * Mail attachment
 * @author
 *
 */
public class MailAttachmentDTO {

	/** Size */
	private long size;

	/** File name */
	private String fileName;

	/** Content */
	private byte[] contents;

	/**
	 * @return the contents
	 */
	public byte[] getContents() {
		return this.contents;
	}

	/**
	 * Filename getter
	 * @return filename
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the size
	 */
	public long getSize() {
		return this.size;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(final byte[] contents) {
		this.contents = contents;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(final long size) {
		this.size = size;
	}
}
