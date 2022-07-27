/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * convertor class for path to resource
 * @author
 *
 */
public final class PathPageConvertor {

	/**
	 * Private constructor
	 */
	private PathPageConvertor() {
	}

	/** PATH_SEPARATOR_URL */
	private static final char PATH_SEPARATOR_URL = '/';

	/** PATH_SEPARATOR_FILE */
	private static final char PATH_SEPARATOR_FILE = '\\';

	/** RESOURCE_SEPARATOR */
	private static final char RESOURCE_SEPARATOR = '.';

	/**
	 * Transforme le path relatif de la page en identifiant de resource. le path peut etre soit un path sans le context
	 * (/pages/toto). Soit un chemin source contenant rootPath. Il est utilisé dans le SecurityFilter et dans l'ecran de
	 * gestion des droits.
	 * @param path chemin relatif ou chemin de system de fichier complet.
	 * @param rootPath la racine
	 * @return renvoi exemple: c:\src\WebContent\index.xhtml -> RESOURCE_SEPARATOR+index /index.jsf ->index
	 *         ->RESOURCE_SEPARATOR+index
	 */
	public static String encode(String path, String rootPath) {
		String suffix = relativePart(path, rootPath);

		// supression de l'extension
		suffix = org.springframework.util.StringUtils.stripFilenameExtension(suffix);

		// encodage
		StringBuilder stringBuilder = new StringBuilder();
		String replace = suffix.replace(PATH_SEPARATOR_URL, RESOURCE_SEPARATOR).replace(PATH_SEPARATOR_FILE,
				RESOURCE_SEPARATOR);
		replace = org.springframework.util.StringUtils.trimLeadingCharacter(replace, RESOURCE_SEPARATOR);
		stringBuilder.append(replace);

		return stringBuilder.toString();
	}

	/**
	 * tronque la chaine apres le mot WEBCONTENT ou ne fait rien.
	 * @param path url path
	 * @param rootPath root path
	 * @return relative part of url path
	 */
	public static String relativePart(String path, String rootPath) {
		String suffix;
		int index = path.indexOf(rootPath);
		if (index == StringUtils.INDEX_NOT_FOUND) {
			suffix = path;
		} else {
			suffix = path.substring(index + rootPath.length());
		}
		return suffix;
	}
}
