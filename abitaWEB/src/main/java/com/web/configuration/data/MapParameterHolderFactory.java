/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 * A. Erisay - 6 juin 2011
 */
package com.web.configuration.data;

import com.dto.parameter.DomainDTO;
import com.dto.parameter.ParameterDTO;
import com.web.configuration.data.ParameterHolder;
import com.web.configuration.data.ParameterHolderFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 *
 */
public abstract class MapParameterHolderFactory {

	/**
	 * Private constructor
	 */
	private MapParameterHolderFactory() {
		super();
	}

	/**
	 * @param domains domains list
	 * @return domains parameters map
	 */
	public static Map<String, Map<String, com.web.configuration.data.ParameterHolder<?>>> create(List<DomainDTO> domains) {
		Map<String, Map<String, com.web.configuration.data.ParameterHolder<?>>> parameterHolders = new HashMap<String, Map<String, com.web.configuration.data.ParameterHolder<?>>>();
		for (DomainDTO domain : domains) {
			for (ParameterDTO parameter : domain.getParameters().values()) {
				setParameterHolder(parameterHolders, domain, parameter);
			}
		}
		return parameterHolders;
	}

	/**
	 * @param parameterHolders parameter holders
	 * @param domain parameters domain
	 * @param parameter parameter to set
	 */
	private static void setParameterHolder(Map<String, Map<String, com.web.configuration.data.ParameterHolder<?>>> parameterHolders,
			DomainDTO domain, ParameterDTO parameter) {
		Map<String, com.web.configuration.data.ParameterHolder<?>> phs;
		if (parameterHolders.containsKey(domain.getName())) {
			phs = parameterHolders.get(domain.getName());
		} else {
			phs = new HashMap<String, ParameterHolder<?>>();
			parameterHolders.put(domain.getName(), phs);
		}
		phs.put(parameter.getName(), ParameterHolderFactory.create(parameter));
	}

}
