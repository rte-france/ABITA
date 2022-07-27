/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 * A. Erisay - 30 juin 2011
 */
package com.web.configuration.data;

import com.dto.parameter.ParameterDTO;
import com.web.configuration.data.ParameterHolder;

import java.io.Serializable;

/**
 * @author
 * @param <E> parameter type
 */
public abstract class AbstractParameterHolder<E> implements ParameterHolder<E>, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 7328552793819878359L;

	/** parameter to hold */
	protected final ParameterDTO parameter;

	/** parameter modification */
	protected Boolean modified = Boolean.FALSE;

	/**
	 * Construct AbstractParameterHolder.
	 * @param param parameter to hold
	 */
	public AbstractParameterHolder(final ParameterDTO param) {
		this.parameter = param;
	}

	/**
	 * @return parameter value
	 */
	@Override
	public abstract E getValue();

	/**
	 * @param value parameter value to set
	 */
	@Override
	public abstract void setValue(final E value);

	/**
	 * getter of modified.
	 * @return true if parameter is modified, false otherwise
	 */
	@Override
	public Boolean isModified() {
		return modified;
	}

	/**
	 * setter of modified.
	 * @param modified true if parameter is modified, false otherwise
	 */
	public void setModified(final boolean modified) {
		this.modified = modified;
	}

	/**
	 * @return modified parameter value
	 */
	@Override
	public String getModifiedValue() {
		return parameter.getValue();
	}
}
