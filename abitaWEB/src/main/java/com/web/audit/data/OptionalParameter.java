package com.web.audit.data;

import java.io.Serializable;

/**
 * Bean représentant un paramètre optionnel
 * @author
 *
 */
public class OptionalParameter implements Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = 4547965768254936939L;

    /** Clé du paramètre */
	private String key;

	/** Valeur du paramètre */
	private String value;

	/**
	 * constructor
	 * @param key key
	 * @param value value
	 */
	public OptionalParameter(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
