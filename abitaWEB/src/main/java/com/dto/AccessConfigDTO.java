package com.dto;

import java.util.List;

/**
 * Classe représentant les droits d'accès
 *
 * @author
 */
public class AccessConfigDTO {
	/** la liste des pages configurées */
	private List<AccessConfigPageDTO> pages;

	/**
	 * @return the pages
	 */
	public List<AccessConfigPageDTO> getPages() {
		return pages;
	}

	/**
	 * @param pages the pages to set
	 */
	public void setPages(List<AccessConfigPageDTO> pages) {
		this.pages = pages;
	}
}
