/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.audit.data;

import com.dto.UserDTO;
import com.web.audit.data.OptionalParameter;

import java.util.List;

/**
 * Interface of optional parameters resolver
 * @author
 *
 */
public interface IOptionalParametersResolver {

	/**
	 * return the list of optional parameters
	 * @param connectedUser connected user
	 * @return optional parameters
	 */
	List<OptionalParameter> getOptionalParameters(UserDTO connectedUser);

}
