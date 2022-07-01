package com.abita.services.jobs.gcp.constants;

import com.abita.util.dateutil.DateTimeUtils;
import com.abita.util.textutil.TextUtils;
import com.abita.dto.ContractDTO;
import com.abita.dto.ThirdPartyContractDTO;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Données de l’en-tête des pièces GCP
 */
public final class HeaderGcpDelegate {

  /**
   * Constructeur privé
   */
  private HeaderGcpDelegate() {
  }

  /**
   * Type de ligne
   * @return type de ligne
   */
  public static String getLineType() {
    return GcpConstants.TYPE_LINE_1;
  }

  /**
   * Date de pièce
   * @param generationDate date de génération
   * @return date de pièce
   */
  public static String getDateOfDocument(LocalDate generationDate) {
    return DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_DDMMYYYY).print(generationDate);
  }

  /**
   * Type de pièce
   * @param gcpType type de pièce
   * @return type de pièce
   */
  public static String getTypeOfDocument(GcpConstants.GcpType gcpType) {
    String typeOfDocument = "";

    if (GcpUtils.isYlType(gcpType)) {
      typeOfDocument = GcpConstants.TYPE_PIECE_YL;
    } else if (gcpType.equals(GcpConstants.GcpType.ZN)) {
      typeOfDocument = GcpConstants.TYPE_PIECE_ZN;
    } else if (GcpUtils.isNcType(gcpType)) {
      typeOfDocument = GcpConstants.TYPE_PIECE_NC;
    }

    return typeOfDocument;
  }

  /**
   * Société
   * @return société
   */
  public static String getSociety() {
    return GcpConstants.COMPANY_CODE;
  }

  /**
   * Devise pièce
   * @return devise pièce
   */
  public static String getCurrency() {
    return TextUtils.EURO_CURRENCY_SHORT_LABEL;
  }

  /**
   * Date comptable
   * @param generationDate date de génération
   * @return date comptable
   */
  public static String getAccountingDate(LocalDate generationDate) {
    LocalDate lastDay = generationDate.dayOfMonth().withMaximumValue();
    return DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_DDMMYYYY).print(lastDay);
  }

  /**
   * Texte d’en-tête
   * @param gcpType type de pièce
   * @param generationDate date de génération
   * @param baseLabel texte d’en-tête de base
   * @return texte d’en-tête
   */
  public static String getHeaderText(GcpConstants.GcpType gcpType, LocalDate generationDate, String baseLabel) {
    String headerText = "";

    if (GcpUtils.isYlType(gcpType)) {
      String pattern = "{0}{1} {2}";
      String text = GcpConstants.HEADER_TEXT_YL;
      String monthOfTreatment = DateTimeUtils.getMonthWithThreeCharacters(generationDate);
      String yearOfTreatment = DateTimeUtils.getShortYear(generationDate);
      headerText = MessageFormat.format(pattern, text, monthOfTreatment, yearOfTreatment);
    } else if (gcpType.equals(GcpConstants.GcpType.ZN)) {
      String pattern = "{0}{1}";
      String text = GcpConstants.HEADER_TEXT_ZN;
      String date = DateTimeFormat.forPattern(DateTimeUtils.PATTERN_DATE_DDMMYYYY_DOTS).print(generationDate);
      headerText = MessageFormat.format(pattern, text, date);
    } else if (GcpUtils.isNcType(gcpType)) {
      if (baseLabel.equals(GcpConstants.HEADER_TEXT_EMPLOYE)) {
        String pattern = "{0} {1} {2}";
        String monthOfTreatment = TextUtils.capitalize(generationDate.monthOfYear().getAsText(Locale.FRENCH));
        String yearOfTreatment = DateTimeUtils.getShortYear(generationDate);
        headerText = MessageFormat.format(pattern, baseLabel, monthOfTreatment, yearOfTreatment);
      } else {
        headerText = baseLabel;
      }
    }

    return headerText;
  }

  /**
   * Référence
   * @param gcpType type de pièce
   * @param thirdPartyContractDTO contrat tiers
   * @param contractDTO contrat occupant
   * @return référence
   */
  public static String getReference(GcpConstants.GcpType gcpType, ThirdPartyContractDTO thirdPartyContractDTO, ContractDTO contractDTO) {
    String reference = "";

    if (GcpUtils.isThirdPartyContractType(gcpType)) {
      reference = thirdPartyContractDTO.getReference();
    } else if (GcpUtils.isTenantContractType(gcpType)) {
      reference = contractDTO.getContractReference();
    }

    return reference;
  }

}
