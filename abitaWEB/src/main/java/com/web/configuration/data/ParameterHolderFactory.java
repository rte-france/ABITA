/**
 * A. Erisay - 1 juil. 2011
 */
package com.web.configuration.data;

import com.dto.parameter.ParameterDTO;
import com.web.configuration.data.AbstractParameterHolder;
import com.web.configuration.data.BooleanParameterHolder;
import com.web.configuration.data.DateParameterHolder;
import com.web.configuration.data.DoubleParameterHolder;
import com.web.configuration.data.IntegerParameterHolder;
import com.web.configuration.data.StringParameterHolder;

/**
 * @author
 *
 */
public abstract class ParameterHolderFactory {

	/**
	 * Private constructor
	 */
	private ParameterHolderFactory() {
		super();
	}

	/**
	 * @param parameter parameter to hold
	 * @return created parameter holder
	 */
	public static AbstractParameterHolder<?> create(ParameterDTO parameter) {
		if ("BOOLEAN".equals(parameter.getType())) {
			return new BooleanParameterHolder(parameter);
		}
		if ("DATE".equals(parameter.getType())) {
			return new DateParameterHolder(parameter);
		}
		if ("INTEGER".equals(parameter.getType())) {
			return new IntegerParameterHolder(parameter);
		}
		if ("DOUBLE".equals(parameter.getType())) {
			return new DoubleParameterHolder(parameter);
		}
		return new StringParameterHolder(parameter);
	}

}
