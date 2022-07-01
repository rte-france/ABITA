package com.abita.util.dateutil;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.Months;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Locale;

/**
 * Classe utilitaire concernant le travail sur les dates
 * @author
 *
 */
public final class DateTimeUtils {

  /**
   * Constructeur privé
   */
  private DateTimeUtils() {
  }

  /** La longueur du nom d'un mois sur 3 caractères */
  private static final int CHAR_LENGTH_THREE = 3;

  /** La longueur du nom d'un mois sur 4 caractères */
  private static final int CHAR_LENGTH_FOUR = 4;

  /** Permet de faire des calculs sur les trimestres */
  private static final int QUARTER_LENGTH = 3;

  /** Nombre de mois dans une année */
  public static final int MONTHS_IN_A_YEAR = 12;

  /** Nombre de mois dans un trimestre */
  public static final int MONTHS_IN_A_QUARTER = 3;

  /** Nombre de mois dans un mois */
  public static final int MONTHS_IN_A_MONTH = 1;

  /** Cycle mensuel */
  public static final String MONTHLY_CYCLE = "Mensuel";

  /** Cycle trimestriel */
  public static final String QUARTERLY_CYCLE = "Trimestriel";

  /** Cycle annuel */
  public static final String ANNUAL_CYCLE = "Annuel";

  /** Pattern date au format yyyyMMdd */
  public static final String PATTERN_DATE_YYYYMMDD = "yyyyMMdd";

  /** Pattern date au format yyyyMM */
  public static final String PATTERN_DATE_YYYYMM = "yyyyMM";

  /** Pattern date au format yyyy-MM-dd */
  public static final String PATTERN_DATE_YYYYMMDD_DASH = "yyyy-MM-dd";

  /** Pattern date au format yyyy-MM */
  public static final String PATTERN_DATE_YYYYMM_DASH = "yyyy-MM";

  /** Pattern date au format yyyy */
  public static final String PATTERN_DATE_YYYY = "yyyy";

  /** Pattern date au format ddMMyyyy */
  public static final String PATTERN_DATE_DDMMYYYY = "ddMMyyyy";

  /** Pattern date au format dd.MM.yyyy */
  public static final String PATTERN_DATE_DDMMYYYY_DOTS = "dd.MM.yyyy";

  /** Pattern date au format MMyyyy */
  public static final String PATTERN_DATE_MMYYYY = "MMyyyy";

  /** Pattern date au format MM */
  public static final String PATTERN_DATE_MM = "MM";

  /** Pattern date au format MM/yyyy */
  public static final String PATTERN_DATE_MMYYYY_UNDERSCORE = "MM/yyyy";

  /** Pattern date au format MM/yyyy */
  public static final String PATTERN_DATE_DDMMYYYY_UNDERSCORE = "dd/MM/yyyy";

  /**
   * Permet de formater une date de début pour les fichiers générés
   * @param startValidityDate date de début de validité
   * @return une date de début
   */
  public static DateTime getStartValidityDateFromDate(Date startValidityDate) {
    DateTime now = new DateTime();
    YearMonth yearMonth = new YearMonth(now);
    return getStartValidityDateFromDate(startValidityDate, yearMonth);
  }

  /**
   * Permet de formater une date de début pour les fichiers générés
   * @param startValidityDate date de début de validité
   * @param yearMonth mois/année de traitement
   * @return une date de début
   */
  public static DateTime getStartValidityDateFromDate(Date startValidityDate, YearMonth yearMonth) {
    DateTime formatedStartValidityDate = new DateTime(startValidityDate);

    // Si pour la même année, le mois de la date du jour correspond au mois de la date de début de validité alors on retourne la date de
    // début de validité
    if (yearMonth.getYear() == formatedStartValidityDate.getYear() && yearMonth.getMonthOfYear() == formatedStartValidityDate.getMonthOfYear()) {
      return formatedStartValidityDate;
    }
    // Sinon on retourne la date au premier jour du mois de traitement
    return new DateTime(yearMonth.getYear(), yearMonth.getMonthOfYear(), 1, 0, 0);
  }

  /**
   * Permet de formater une date de fin pour les fichiers générés
   * @param endValidityDate date de fin de validité
   * @return une date de fin pour les fichiers générés
   */
  public static DateTime getEndValidityDateFromDate(Date endValidityDate) {
    DateTime now = new DateTime();
    YearMonth yearMonth = new YearMonth(now);
    return getEndValidityDateFromDate(endValidityDate, yearMonth);
  }

  /**
   * Permet de formater une date de fin pour les fichiers générés
   * @param endValidityDate date de fin de validité
   * @param yearMonth mois/année de traitement
   * @return une date de fin pour les fichiers générés
   */
  public static DateTime getEndValidityDateFromDate(Date endValidityDate, YearMonth yearMonth) {
    if (endValidityDate != null) {
      DateTime formatedEndValidityDate = new DateTime(endValidityDate);

      // Si pour la même année, le mois de la date du jour correspond au mois de la date de fin de validité alors on retourne la date de fin
      // de validité
      if (yearMonth.getYear() == formatedEndValidityDate.getYear() && yearMonth.getMonthOfYear() == formatedEndValidityDate.getMonthOfYear()) {
        return formatedEndValidityDate;
      }
    }
    // Sinon on retourne la date au dernier jour du mois de traitement
    return new DateTime(yearMonth.getYear(), yearMonth.getMonthOfYear(), 1, 0, 0).dayOfMonth().withMaximumValue();
  }

  /**
   * Permet de récupérer la date du prochaine mois par rapport à la date en cours
   * @return la date du prochaine mois par rapport à la date en cours
   */
  public static DateTime nextMonthOfActuelDay() {
    return DateTime.now().plusMonths(1);
  }

  /**
   * Permet de formatter une date (jodatime) en une chaine avec un pattern particulier
   * @param dateTime la date jodatime
   * @param pattern le pattern à appliquer
   * @return une chaine avec la date formattée
   */
  public static String formatDateTimeToStringWithPattern(DateTime dateTime, String pattern) {

    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(pattern);
    return dateTimeFormatter.print(dateTime);

  }

  /**
   * Permet de récupérer le nom du mois sur 3 caractères (JUN pour juin et JUL et juillet
   * @param date la date JodaTime à traiter
   * @return une chaîne avec seulement le mois
   */
  public static String getMonthWithThreeCharacters(LocalDate date) {
    if (DateTimeConstants.JUNE == date.getMonthOfYear()) {
      return "JUN";
    } else if (DateTimeConstants.JULY == date.getMonthOfYear()) {
      return "JUL";
    } else {
      return date.monthOfYear().getAsShortText(Locale.FRENCH).toUpperCase().substring(0, CHAR_LENGTH_THREE);
    }
  }

  /**
   * Permet de récupérer le nom du mois sur 4 caractères au maximum
   * @param date la date JodaTime à traiter
   * @return une chaîne avec seulement le mois
   */
  public static String getMonthWithFourCharacters(LocalDate date) {
    String month = date.monthOfYear().getAsText(Locale.FRENCH);
    if (month.length() >= CHAR_LENGTH_FOUR) {
      month = month.substring(0, CHAR_LENGTH_FOUR);
    }
    return month;
  }

  /**
   * Permet de récupérer l’année sur seulement 2 caractères
   * @param date la date JodaTime à traiter
   * @return une chaîne avec seulement l’année
   */
  public static String getShortYear(LocalDate date) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yy");
    return dateTimeFormatter.print(date);
  }

  /**
   * Permet de récupérer le ratio du nombre de jours par rapport à un cycle
   * @param startCycleDate date de début du cycle
   * @param endCycleDate date de fin du du cycle
   * @param startValidityDate date de début traitée
   * @param endValidityDate date de fin traitée
   * @return le ratio du nombre de jours dans le cycle
   */
  public static float getProportionOfDaysOnCycle(LocalDate startCycleDate, LocalDate endCycleDate, LocalDate startValidityDate, LocalDate endValidityDate) {

    int months = getNumberOfMonth(startCycleDate, endCycleDate, startValidityDate, endValidityDate);

    float prorata = 0;

    for (int i = 0; i < months; i++) {
      LocalDate monthDate = startCycleDate.plusMonths(i);
      LocalDate monthStartDate = monthDate.dayOfMonth().withMinimumValue();
      LocalDate monthEndDate = monthDate.dayOfMonth().withMaximumValue();

      LocalDate monthStartValidity = startValidityDate;
      LocalDate monthEndValidity = endValidityDate;

      // on s’assure que la date de début traitée concerne le mois traité
      if (startValidityDate.isBefore(monthStartDate)) {
        monthStartValidity = monthStartDate;
      }

      // on s’assure que la date de fin traitée concerne le mois traité
      if (endValidityDate == null || endValidityDate.isAfter(monthEndDate)) {
        monthEndValidity = monthEndDate;
      }

      // nombre de jours entre la date de début et la date de fin traitée
      int daysValidity = Days.daysBetween(monthStartValidity, monthEndValidity).getDays() + 1;

      // nombre de jours du cycle
      int daysCycle = Days.daysBetween(monthStartDate, monthEndDate).getDays() + 1;

      // proportion du nombre de nombre par rapport au mois traité
      prorata += (float) daysValidity / daysCycle;
    }

    return prorata;
  }

  /**
   * Permet de récupérer la première date d’un trimestre
   * @param date la date JodaTime à traiter
   * @return la date JodaTime traitée
   */
  public static LocalDate getFirstDayOfQuarterlyCycle(LocalDate date) {
    return date.withDayOfMonth(1).withMonthOfYear((date.getMonthOfYear() - 1) / QUARTER_LENGTH * QUARTER_LENGTH + 1);
  }

  /**
   * Permet de récupérer la dernière date d’un trimestre
   * @param date la date JodaTime à traiter
   * @return la date JodaTime traitée
   */
  public static LocalDate getLastDayOfQuarterlyCycle(LocalDate date) {
    return getFirstDayOfQuarterlyCycle(date).plusMonths(QUARTER_LENGTH).minusDays(1);
  }

  /**
   * Permet de récupérer la première date d’un cycle
   * @param date la date JodaTime à traiter
   * @param cycle le cycle à prendre en compte
   * @return la date JodaTime traitée
   */
  public static LocalDate getFirstDayOfCycle(LocalDate date, String cycle) {
    LocalDate firstDay = new LocalDate();
    if (MONTHLY_CYCLE.equals(cycle)) {
      firstDay = date.dayOfMonth().withMinimumValue();
    } else if (QUARTERLY_CYCLE.equals(cycle)) {
      firstDay = getFirstDayOfQuarterlyCycle(date);
    } else if (ANNUAL_CYCLE.equals(cycle)) {
      firstDay = date.dayOfYear().withMinimumValue();
    }
    return firstDay;
  }

  /**
   * Permet de récupérer la dernière date d’un cycle
   * @param date la date JodaTime à traiter
   * @param cycle le cycle à prendre en compte
   * @return la date JodaTime traitée
   */
  public static LocalDate getLastDayOfCycle(LocalDate date, String cycle) {
    LocalDate firstDay = new LocalDate();
    if (MONTHLY_CYCLE.equals(cycle)) {
      firstDay = date.dayOfMonth().withMaximumValue();
    } else if (QUARTERLY_CYCLE.equals(cycle)) {
      firstDay = getLastDayOfQuarterlyCycle(date);
    } else if (ANNUAL_CYCLE.equals(cycle)) {
      firstDay = date.dayOfYear().withMaximumValue();
    }
    return firstDay;
  }

  /**
   * Permet de récupérer le nombre de mois impacté par une date de début et de fin dans un cycle
   * @param startCycleDate date de début du cycle
   * @param endCycleDate date de fin du du cycle
   * @param startValidityDate date de début traitée
   * @param endValidityDate date de fin traitée
   * @return le nombre de mois dans le cycle
   */
  public static int getNumberOfMonth(LocalDate startCycleDate, LocalDate endCycleDate, LocalDate startValidityDate, LocalDate endValidityDate) {
    LocalDate startValidity = startValidityDate;
    LocalDate endValidity = endValidityDate;

    // on s’assure que la date de début traitée concerne le cycle
    if (startValidityDate.isBefore(startCycleDate)) {
      startValidity = startCycleDate;
    }

    // on s’assure que la date de fin traitée concerne le cycle
    if (endValidityDate == null || endValidityDate.isAfter(endCycleDate)) {
      endValidity = endCycleDate;
    }

    LocalDate roundStartValidity = startValidity.monthOfYear().roundFloorCopy();
    LocalDate roundEndValidity = endValidity.monthOfYear().roundCeilingCopy();

    if (endValidity.getDayOfMonth() == 1) {
      roundEndValidity = roundEndValidity.plusMonths(1);
    }

    return Months.monthsBetween(roundStartValidity, roundEndValidity).getMonths();
  }

  /**
   * Permet de récupérer le premier jour du mois avec les heures, les minutes, les secondes et les millisecondes au minimum
   * @param date date à traiter
   * @return date avec les informations de temps à jour
   */
  public static LocalDateTime getMinimumTimeOfFirstDayOfMonth(LocalDate date) {
    LocalDateTime dateTime = date.toLocalDateTime(new LocalTime());

    return dateTime.dayOfMonth().withMinimumValue().hourOfDay().withMinimumValue().minuteOfHour().withMinimumValue().secondOfMinute().withMinimumValue().millisOfSecond()
      .withMinimumValue();
  }

  /**
   * Permet de récupérer le premier jour du mois avec les heures, les minutes, les secondes et les millisecondes au minimum
   * @param yearMonth mois/année à traiter
   * @return date avec les informations de temps à jour
   */
  public static LocalDateTime getMinimumTimeOfFirstDayOfMonth(YearMonth yearMonth) {
    return getMinimumTimeOfFirstDayOfMonth(yearMonth.toLocalDate(1));
  }

  /**
   * Permet de récupérer le jour avec les heures, les minutes, les secondes et les millisecondes au minimum
   * @param date date à traiter
   * @return date avec les informations de temps à jour
   */
  public static LocalDateTime getMinimumTimeOfDayOfMonth(LocalDate date) {
    LocalDateTime dateTime = date.toLocalDateTime(new LocalTime());

    return dateTime.hourOfDay().withMinimumValue().minuteOfHour().withMinimumValue().secondOfMinute().withMinimumValue().millisOfSecond().withMinimumValue();
  }

  /**
   * Permet de récupérer le dernier jour du mois avec les heures, les minutes, les secondes et les millisecondes au maximum
   * @param date date à traiter
   * @return date avec les informations de temps à jour
   */
  public static LocalDateTime getMaximumTimeOfLastDayOfMonth(LocalDate date) {
    LocalDateTime dateTime = date.toLocalDateTime(new LocalTime());

    return dateTime.dayOfMonth().withMaximumValue().hourOfDay().withMaximumValue().minuteOfHour().withMaximumValue().secondOfMinute().withMaximumValue().millisOfSecond()
      .withMaximumValue();
  }

  /**
   * Permet de récupérer le couple mois/année du cycle
   * @param date date de repère
   * @param cycle le cycle à prendre en compte
   * @param expiryDate paiement échu ou à échoir
   * @return nombre de mois
   */
  public static YearMonth getYearMonthOfCycle(LocalDate date, String cycle, Boolean expiryDate) {
    LocalDate firstDayOfCycle = getFirstDayOfCycle(date, cycle);
    if (null == expiryDate || !expiryDate) {
      firstDayOfCycle = firstDayOfCycle.minusMonths(1);
    }
    Date finalDate = firstDayOfCycle.toDate();
    return YearMonth.fromDateFields(finalDate);
  }

  /**
   * Clone une date source pour éviter les problèmes de mutabilité
   * @param source date source
   * @return clone
   */
  public static Date clone(Date source) {
    Date result = null;

    if (source != null) {
      result = new Date(source.getTime());
    }

    return result;
  }

}
