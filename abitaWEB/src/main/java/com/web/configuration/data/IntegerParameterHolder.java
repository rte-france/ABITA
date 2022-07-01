/**
 * A. Erisay - 1 juil. 2011
 */
package com.web.configuration.data;

import com.dto.parameter.ParameterDTO;
import com.web.configuration.data.AbstractParameterHolder;

import java.io.Serializable;

/**
 * The Class IntegerParameterHolder.
 *
 * @author
 */
public class IntegerParameterHolder extends AbstractParameterHolder<Integer> implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 6340461025791445289L;

	/**
	 * Construct IntegerParameterHolderTest.
	 * @param parameter parameter
	 */
	public IntegerParameterHolder(final ParameterDTO parameter) {
		super(parameter);
	}

	/* (non-Javadoc)
	 * @see com.web.configuration.data.AbstractParameterHolder#getValue()
	 */
	@Override
	public Integer getValue() {
		return Integer.parseInt(parameter.getValue());
	}

	/* (non-Javadoc)
	 * @see com.web.configuration.data.AbstractParameterHolder#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(final Integer value) {
		if (!value.toString().equals(parameter.getValue())) {
			parameter.setValue(value.toString());
			modified = true;
		}

	}
}
