/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 * A. Erisay - 1 juil. 2011
 */
package com.web.configuration.data;

import com.dto.parameter.ParameterDTO;
import com.services.common.constants.DateTimeConstants;
import com.services.common.exception.UnexpectedException;
import com.services.common.util.MessageSupport;
import com.web.configuration.data.AbstractParameterHolder;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class DateParameterHolder.
 *
 * @author
 */
public class DateParameterHolder extends AbstractParameterHolder<Date> implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = -548908813371580142L;

	/** Format de date francaise. */
	private SimpleDateFormat formatter = new SimpleDateFormat(DateTimeConstants.FRENCH_DATE_FORMAT,
			MessageSupport.getLocale());

	/**
	 * Construct DateParameterHolder.
	 * @param parameter parameter
	 */
	public DateParameterHolder(ParameterDTO parameter) {
		super(parameter);

	}

	/* (non-Javadoc)
	 * @see com.web.configuration.data.AbstractParameterHolder#getValue()
	 */
	@Override
	public Date getValue() {
		try {
			return formatter.parse(parameter.getValue());
		} catch (ParseException parseException) {
			throw new UnexpectedException(parseException);
		}
	}

	/* (non-Javadoc)
	 * @see com.web.configuration.data.AbstractParameterHolder#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(final Date value) {
		if (!formatter.format(value).equals(parameter.getValue())) {
			parameter.setValue(formatter.format(value));
			modified = true;
		}
	}

}
