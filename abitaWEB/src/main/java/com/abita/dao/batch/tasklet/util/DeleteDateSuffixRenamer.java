/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.tasklet.util;

import com.abita.dao.batch.tasklet.IFileRenamer;
import com.abita.util.dateutil.DateTimeUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Permet de supprimer une date à une chaîne de caractères
 */
public class DeleteDateSuffixRenamer implements IFileRenamer {

  /** Formatter de date ddMMyyyy */
  private DateTimeFormatter dateTightFormatter = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_DDMMYYYY);

  @Override
  public String rename(String originalName) {
    String suffix = "_" + dateTightFormatter.print(new LocalDate());
    StringBuilder newNameBuilder = new StringBuilder(originalName);
    int extensionIndex = StringUtils.lastIndexOf(originalName, ".");
    int suffixStartIndex = StringUtils.indexOf(originalName, suffix);
    if (extensionIndex >= 0) {
      newNameBuilder.delete(suffixStartIndex, extensionIndex);
    } else {
      newNameBuilder.delete(suffixStartIndex, originalName.length());
    }
    return newNameBuilder.toString();
  }
}
