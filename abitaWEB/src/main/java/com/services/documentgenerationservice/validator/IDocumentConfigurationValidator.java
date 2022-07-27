/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.documentgenerationservice.validator;

import com.services.documentgenerationservice.data.DocumentConfiguration;

/**
 * Validating interface for document configurations
 *
 * @author
 *
 * @param <T> specific document configuration
 */
public interface IDocumentConfigurationValidator<T extends DocumentConfiguration> {

	/**
	 * Determines the validity of the given configuration
	 *
	 * @param documentConfiguration configuration to be validated
	 * @return true if the configuration is valid
	 */
	Boolean isValid(T documentConfiguration);

}
