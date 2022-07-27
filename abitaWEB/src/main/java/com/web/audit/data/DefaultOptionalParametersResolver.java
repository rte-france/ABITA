/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.audit.data;

import com.dto.UserDTO;
import com.web.audit.data.IOptionalParametersResolver;
import com.web.audit.data.OptionalParameter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation : returns an empty map.
 * Will be overridden if another implementation is defined in the project using the framework.
 * @author
 *
 */
public class DefaultOptionalParametersResolver implements IOptionalParametersResolver, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -1717803066062823656L;

	@Override
	public List<com.web.audit.data.OptionalParameter> getOptionalParameters(UserDTO user) {
		return new ArrayList<OptionalParameter>(0);
	}

}
