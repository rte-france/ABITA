/**
 * A. Erisay - 1 juil. 2011
 */
package com.web.configuration.data;

import com.dto.parameter.ParameterDTO;
import com.web.configuration.data.AbstractParameterHolder;

import java.io.Serializable;

/**
 * The Class StringParameterHolder.
 *
 * @author
 */
public class StringParameterHolder extends AbstractParameterHolder<String> implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1525620645684450528L;

	/**
	 * Construct StringParameterHolder.
	 * @param parameter parameter
	 */
	public StringParameterHolder(final ParameterDTO parameter) {
		super(parameter);
	}

	/* (non-Javadoc)
	 * @see com.web.configuration.data.AbstractParameterHolder#getValue()
	 */
	@Override
	public String getValue() {
		return parameter.getValue();
	}

	/* (non-Javadoc)
	 * @see com.web.configuration.data.AbstractParameterHolder#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(final String value) {
		if (!value.equals(parameter.getValue())) {
			parameter.setValue(value);
			modified = true;
		}
	}
}
