/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 * A. Erisay - 1 juil. 2011
 */
package com.web.configuration.data;

import com.dto.parameter.ParameterDTO;
import com.web.configuration.data.AbstractParameterHolder;

import java.io.Serializable;

/**
 * The Class DoubleParameterHolder.
 *
 * @author
 */
public class DoubleParameterHolder extends AbstractParameterHolder<Double> implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -3120255268283167564L;

	/**
	 * Construct DoubleParameterHolder.
	 * @param parameter parameter
	 */
	public DoubleParameterHolder(final ParameterDTO parameter) {
		super(parameter);
	}

	/* (non-Javadoc)
	 * @see com.web.configuration.data.AbstractParameterHolder#getValue()
	 */
	@Override
	public Double getValue() {
		return Double.parseDouble(parameter.getValue());
	}

	/* (non-Javadoc)
	 * @see com.web.configuration.data.AbstractParameterHolder#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(final Double value) {
		if (!value.toString().equals(parameter.getValue())) {
			parameter.setValue(value.toString());
			modified = true;
		}
	}
}
