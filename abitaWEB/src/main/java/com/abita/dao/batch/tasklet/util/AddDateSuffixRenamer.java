package com.abita.dao.batch.tasklet.util;

import com.abita.dao.batch.tasklet.IFileRenamer;
import com.abita.util.dateutil.DateTimeUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Permet d'ajouter une date à une chaîne de caractères
 */
public class AddDateSuffixRenamer implements IFileRenamer {

  /** Formatter de date ddMMyyyy */
  private DateTimeFormatter dateTightFormatter = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_DDMMYYYY);

  @Override
  public String rename(String originalName) {
    String suffix = "_" + dateTightFormatter.print(new LocalDate());
    StringBuilder newNameBuilder = new StringBuilder(originalName);
    int extensionIndex = StringUtils.lastIndexOf(originalName, ".");
    if (extensionIndex >= 0) {
      newNameBuilder.insert(extensionIndex, suffix);
    } else {
      newNameBuilder.append(suffix);
    }
    return newNameBuilder.toString();
  }
}
