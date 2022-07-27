/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.quittancement.constants;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Classe des constantes utilisées pour les fichiers de quittancement
 */
public final class QuittancementConstants {

  /**
   * Constructeur privé
   */
  private QuittancementConstants() {
  }

  /**
   * Fichier zip temporaire
   */
  public static final String ZIP_TEMP_FILE_PREFIX = "Quittancement_TempFile";

  /**
   * Extension de fichier zip temporaire
   */
  public static final String ZIP_TEMP_FILE_SUFFIX = ".zip.tmp";

  /**
   * Extension de fichier zip
   */
  public static final String ZIP_FILE_SUFFIX = ".zip";

  /** Le domaine de paramètrage pour les batchs */
  public static final String QUITTANCEMENT_NAME = "quittancement";

  /** Le type de génération */
  public static final String TYPE_QUITTANCEMENT = "QUITTANCEMENT";

  /**
   * Chemin d'accès au répertoire des PDF des quittancements
   */
  public static final String PATH_QUITTANCEMENT_FOLDER_PARAMETER_KEY = "pathQuittancementFolder";

  /**
   * Chemin d'accès au répertoire temporaire des PDF des quittancements
   */
  public static final String PATH_TEMP_QUITTANCEMENT_FOLDER_PARAMETER_KEY = "pathTempQuittancementFolder";

  /**
   * Extension des fichiers des quittancements
   */
  public static final String QUITTANCEMENT_EXTENSION_FILE = ".pdf";

  /** Formatteur de le mois dans le fichier */
  public static final DateTimeFormatter DATE_FORMAT_MONTH = DateTimeFormat.forPattern("MM");

  /** Formatteur de date dd mmmm yyyy */
  public static final DateTimeFormatter DATE_FORMAT_LONG = DateTimeFormat.forPattern("dd MMMM yyyy").withLocale(Locale.FRENCH);

  /** Formatteur de date dd.MM.yyyy */
  public static final DateTimeFormatter DATE_FORMAT_SHORT = DateTimeFormat.forPattern("dd.MM.yyyy");

  /** Police de base des éléments */
  public static final Font ITEM_FONT = FontFactory.getFont(FontFactory.HELVETICA, 11);

  /** Police de base des éléments mis en valeur  */
  public static final Font ITEM_FONT_STRONG = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD);

  /** Espace entre les différents éléments du document */
  public static final int SPACING_BETWEEN_ELEMENTS = 25;

  /** Espace (petit) entre les différents éléments du document */
  public static final int SPACING_SMALL_BETWEEN_ELEMENTS = 15;

  /** Espace (grand) entre les différents éléments du document */
  public static final int SPACING_BIG_BETWEEN_ELEMENTS = 35;

  /** Espace en hauteur pour le header */
  public static final float SPACE_HEADER = 225f;

  /** Espace en hauteur pour le footer */
  public static final float SPACE_FOOTER = 140f;

  /** Marge inférieure du footer */
  public static final float MARGIN_BOTTOM_FOOTER = 25f;

  /** Marge supérieure du contenu */
  public static final int MARGIN_TOP_BODY = 275;

  /** Valeur pour l’identation des éléments placés à droite */
  public static final int INDENTATION_RIGHT = 300;

  /** Nombre de colonnes du tableau */
  public static final int TOTAL_COLUMN = 2;

  /** Poids de la première colonne du tableau */
  public static final int TABLE_FIRST_WEIGHT = 3;

  /** Poids de la deuxième colonne du tableau */
  public static final int TABLE_SECOND_WEIGHT = 1;

  /** Largeur du tableau */
  public static final int TABLE_WIDTH = 100;

  /** Espacement entre les cellules */
  public static final int TABLE_PADDING = 8;

  /** Espacement pour les lignes du corps du tableau */
  public static final int TABLE_PADDING_BODY = 5;

  /** Nom du fichier */
  public static final String FILENAME = "{0}_{1}{2,number,#}.pdf";

  /** Taille du buffer */
  public static final int BUFFER_SIZE = 1024;

  /** Nom du conteneur de l'en-tête */
  public static final String HEADER_BOX_NAME = "header";

  /** Nom du conteneur du footer */
  public static final String FOOTER_BOX_NAME = "footer";

  /** Nom du conteneur de la pagination */
  public static final String PAGINATION_BOX_NAME = "pagination";

  /** Nom du Fichier regroupé PDF */
  public static final String MERGED_FILENAME = "quittancement_ABITA_{0}_{1,number,#}.pdf";

  /** Nom du fichier zippé */
  public static final String ZIPPED_FILENAME = "quittancement_ABITA_{0}_{1,number,#}.zip";

  /** Nom et Prenom du gestionnaire de logement */
  public static final String MANAGER_LASTNAME_AND_FIRSTNAME = "Affaire suivie par : {0} {1}";

  /** Telephone du gestionnaire de logement */
  public static final String MANAGER_PHONE = "Tél : {0}";

  /** Objet du document */
  public static final String OBJECT_SENTENCE = "Objet : décompte de loyer pour la période du {0} au {1}";

  /** Adresse des lieux loués */
  public static final String CONTRACT_ADDRESS = "Adresse des lieux loués : {0} {1} {2}";

  /** Référence contrat occupant */
  public static final String CONTRACT_REFERENCE_LABEL = "Référence contrat occupant : ";

  /** Référence logement */
  public static final String HOUSING_REFERENCE_LABEL = "Référence du logement : ";

  /** Libellé dans l'en-tête du detail */
  public static final String DETAIL_HEADER_LABEL = "Libellé";

  /** Prix à payer dans l'en-tête du detail */
  public static final String DETAIL_HEADER_PRICE = "A payer (en euros)";

  /** Libellé du Loyer net agent dans le detail */
  public static final String DETAIL_NET_AGENT_RENT_LABEL = "Loyer net agent";

  /** Libellé du loyer garage dans le detail */
  public static final String DETAIL_GARAGE_RENT_LABEL = "Loyer garage";

  /** Libellé du loyer jardin dans le detail */
  public static final String DETAIL_GARDEN_RENT_LABEL = "Loyer jardin";

  /** Libellé de la provision pour charges dans le detail */
  public static final String DETAIL_EXPECTED_CHARGE_COST = "Provision pour charges";

  /** Libellé du surloyer dans le detail */
  public static final String DETAIL_EXTRA_RENT = "Surloyer";

  /** Libellé de la facturation consommation eau */
  public static final String DETAIL_WATER_INVOICING = "Facturation consommation eau";

  /** Libellé de la facturation ordures ménagères */
  public static final String DETAIL_GARBAGE_INVOICING = "Facturation ordures ménagères";

  /** Libellé de la facturation taxe habitation */
  public static final String DETAIL_HOUSING_TAX_INVOICING = "Facturation taxe habitation";

  /** Libellé du remboursement assurance */
  public static final String DETAIL_INSURANCE_REIMBURSEMENT = "Remboursement assurance";

  /** Libellé du remboursement taxe habitation */
  public static final String DETAIL_HOUSING_TAX_REIMBURSEMENT = "Remboursement taxe habitation";

  /** Libellé du remboursement ordures ménagères */
  public static final String DETAIL_GARBAGE_REIMBURSEMENT = "Remboursement ordures ménagères";

  /** Libellé de l'apurement annuel des charges locatives */
  public static final String DETAIL_ANNUAL_CLEARANCE_CHARGES = "Apurement annuel charges locatives";

  /** Libellé de autres charges locatives */
  public static final String DETAIL_OTHER_INVOICING = "Autres charges locatives";

  /** Libellé total du pied du detail */
  public static final String DETAIL_TOTAL_FOOTER = "Total à régler";

  /** Libellé nom de l'émetteur */
  public static final String SENDER_COMPANY_NAME = "Nom de l'émetteur";

  /** Libellé lieu de l'émetteur */
  public static final String SENDER_PLACE = "lLeu de l'émetteur";

  /** Libellé nom du service de l'émetteur */
  public static final String SENDER_SERVICE_NAME = "Nom du service de l'émetteur";

  /** Libellé nom du departement de l'émetteur */
  public static final String SENDER_DEPARTMENT_NAME = "Nom du departement de l'émetteur";

  /** Libellé code de l'émetteur */
  public static final String SENDER_CODE = "Code de l'émetteur";

  /** Libellé code postal et ville de l'émetteur */
  public static final String SENDER_POSTAL_CODE_TOWN = "Code postal et ville de l'émetteur";

  /** Libellé de l'adresse de l'émetteur */
  public static final String SENDER_ADRESS = "Adresse de l'émetteur";

  /** Libellé nom et prénom du destinataire */
  public static final String RECIPIENT_LASTNAME_FIRSTNAME = "{0}";

  /** Libellé code postal et ville du destinataire */
  public static final String RECIPIENT_POSTAL_CODE_TOWN = "{0} {1}";

  /** libellé du lieu et de la date du jour */
  public static final String CURRENT_DATE_AND_PLACE = "Nom du lieu, le {0}";

  /** Référence contrat occupant */
  public static final String CONTRACT_REFERENCE = "Référence contrat occupant : {0}";

  /** Référence logement */
  public static final String HOUSING_REFERENCE = "Référence du logement : {0}";

  /** Nom et prénom de l'occupant */
  public static final String TENANT_FIRSTNAME_LASTNAME = "Occupant : {0}";

  /** Date de debut et de fin de décompte de loyer */
  public static final String QUITTANCEMENT_PERIOD = "Période : {0} au {1}";

  /** Prix total à payer */
  public static final String TOTAL_PRICE_FOOTER = "A payer : {0}";

  /** Pattern rétroactivité */
  public static final String PATTERN_REGULARIZATION = " : {0} - {1} ";

  /** Premier jour du mois */
  public static final int FIRST_DAY_OF_MONTH = 1;

  /** Pattern pour l'historisation des montants */
  public static final String PATTERN_HISTORY_AMOUNT_DETAIL = "{0}:{1};";
}
