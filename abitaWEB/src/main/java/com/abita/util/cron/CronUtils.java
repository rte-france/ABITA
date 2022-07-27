/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util.cron;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe utilitaire sur les CRON
 */
public final class CronUtils {

  /** La position des minutes dans les resultats trouvés de l'expresssion régulière */
  private static final int POSITION_MINUTE = 3;

  /** La position des heures dans les resultats trouvés de l'expression régulière */
  private static final int POSITION_HOUR = 2;

  /** La position des jours dans les resultats trouvés de l'expression régulière */
  private static final int POSITION_DAY = 1;

  /**
   * Constructeur privé
   */
  private CronUtils() {
  }

  /**
   * Formate la Cron expression à partir du chaine de caractère au format dd HH:mm
   * @param secondesToAdd décalage en secondes
   * @param fileTransferInfo fileTransferInfo une chaine de caractère au format dd HH:mm
   * @return la Cron expression
   */
  public static String detailCronExpression(Integer secondesToAdd, String fileTransferInfo) {
    DateTime newCurrentDate = getDateTimeFromDetailCronInfo(fileTransferInfo);
    // On ajoute le nombre de minute parametré
    newCurrentDate = newCurrentDate.plusSeconds(secondesToAdd);
    return getCronExpression(newCurrentDate);
  }

  /**
   * Permet de récupérer l’expression cron à partir d’un DateTime
   * @param dateTime dateTime à transformer
   * @return l’expression cron
   */
  public static String getCronExpression(DateTime dateTime) {
    String day = dateTime.dayOfMonth().getAsString();
    String hour = dateTime.hourOfDay().getAsString();
    String minute = dateTime.minuteOfHour().getAsString();
    String second = dateTime.secondOfMinute().getAsString();

    return second + " " + minute + " " + hour + " " + day + " * ?";
  }

  /**
   * Permet de récupérer la date et l’heure d’une expression CRON
   * @param cronInfo expression CRON
   * @return la date et l’heure
   */
  public static DateTime getDateTimeFromDetailCronInfo(String cronInfo) {
    MutableDateTime now = new MutableDateTime();

    Matcher match = applyRegex(cronInfo);
    match.matches();
    String day = match.group(POSITION_DAY);
    String hour = match.group(POSITION_HOUR);
    String minute = match.group(POSITION_MINUTE);

    // On set la date
    now.setDayOfMonth(Integer.parseInt(day));
    now.setHourOfDay(Integer.parseInt(hour));
    now.setMinuteOfHour(Integer.parseInt(minute));

    return now.toDateTime();
  }

  /**
   * Applique l'expression régulière sur une chaine de caractère dd HH:mm
   * @param fileTransferInfo la chaine de caractère dd HH:mm
   * @return Le resultat de l'expression régulière
   */
  public static Matcher applyRegex(String fileTransferInfo) {
    // La regex pour capturer 3 sous-chaines = jour, heure, minutes
    String regex = "(.+) (.+):(.+)";
    Pattern pattern = Pattern.compile(regex);
    // On applique la regex sur les infos d'un des fichiers contenus dans le DTO
    return pattern.matcher(fileTransferInfo);
  }
}
