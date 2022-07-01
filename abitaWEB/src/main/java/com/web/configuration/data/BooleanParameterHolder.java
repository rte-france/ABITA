/**
 * A. Erisay - 1 juil. 2011
 */
package com.web.configuration.data;

import com.dto.parameter.ParameterDTO;
import com.web.configuration.data.AbstractParameterHolder;

import java.io.Serializable;

/**
 * The Class BooleanParameterHolder.
 *
 * @author
 */
public class BooleanParameterHolder extends AbstractParameterHolder<Boolean> implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -8202817020168274363L;

	/**
	 * Construct BooleanParameterHolder.
	 * @param param parameter
	 */
	public BooleanParameterHolder(final ParameterDTO param) {
		super(param);
	}

	/* (non-Javadoc)
	 * @see com.web.configuration.data.AbstractParameterHolder#getValue()
	 */
	@Override
	public Boolean getValue() {
		return Boolean.parseBoolean(parameter.getValue());
	}

	/* (non-Javadoc)
	 * @see com.web.configuration.data.AbstractParameterHolder#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(final Boolean value) {
		Boolean oldvalue = Boolean.valueOf(parameter.getValue());

		if (!value.equals(oldvalue)) {
			parameter.setValue(value.toString());
			modified = true;
		}

	}

}
