/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.util;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

/**
 * Exclude WebContent\WEB-INF AND WebContent\META-INF Accepte
 * @author admlocal
 */
public class XhtmlFilter implements FileFilter {

	@Override
	public boolean accept(File pathname) {

		String parentName = pathname.getParentFile().getName();
		String name = pathname.getName();
		boolean isHiddenFile = name.startsWith(".");
		boolean isXhtmlFile = pathname.isFile() && (isHiddenFile || !name.endsWith(".xhtml"));
		boolean isDirectories = pathname.isDirectory();

		final List<String> forbiddenPathNames =
		    Arrays.asList(new String[] {"includes", "images", "WEB-INF", "classes", "lib", "META-INF"});
		boolean excludeDirectories = "WebContent".equals(parentName)
			&& (isDirectories && (isHiddenFile || forbiddenPathNames.contains(name) || isXhtmlFile));
		return !excludeDirectories && !(isDirectories && isHiddenFile) && !isXhtmlFile;
	}
}
