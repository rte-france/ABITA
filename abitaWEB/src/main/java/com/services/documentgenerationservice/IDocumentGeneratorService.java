/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.documentgenerationservice;

import com.services.documentgenerationservice.data.DocumentConfiguration;
import com.services.documentgenerationservice.data.GeneratedDocument;
import com.services.documentgenerationservice.exception.DocumentGenerationException;
import com.services.documentgenerationservice.validator.IDocumentConfigurationValidator;

import java.io.IOException;

/**
 * Document generation service
 *
 * @author
 *
 * @param <D> specific object representing the generated document
 * @param <C> specific configuration for the implementation
 * @param <V> specific validating class for the configuration
 */
public interface IDocumentGeneratorService<D extends GeneratedDocument<?>, C extends DocumentConfiguration, V extends IDocumentConfigurationValidator<C>> {

	/**
	 * Generates a document based on the given configuration
	 *
	 * @param documentConfiguration configuration of the document
	 * @return an object representing the document (specified by the implementation)
	 * @throws DocumentGenerationException on error
	 */
	D generateDocument(C documentConfiguration) throws DocumentGenerationException;

	/**
	 * Writes the given document specified by the configuration in the given path
	 *
	 * @param documentConfiguration the document determined by its configuration to be written
	 * @param fullTargetPath where to write the file (must contain the name)
	 * @throws DocumentGenerationException on error
	 * @throws IOException I/O exception
	 */
	void writeDocument(C documentConfiguration, String fullTargetPath) throws DocumentGenerationException, IOException;

	/**
	 * Writes the given document
	 *
	 * @param generatedDocument the document object
	 * @param fullTargetPath where to write the file (must contain the name)
	 * @throws DocumentGenerationException on error
	 * @throws IOException I/O exception
	 */
	void writeDocument(D generatedDocument, String fullTargetPath) throws DocumentGenerationException, IOException;

	/**
	 * Determines if the configuration is valid
	 *
	 * @param configuration document configuration
	 * @return true if the configuration is valid, false ioc
	 */
	Boolean validateConfiguration(C configuration);
}
