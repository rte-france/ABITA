package com.abita.web.shared;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Classe de constantes communes pour le WEB
 *
 * @author
 */
public final class ConstantsWEB {

  /**
   * Constructeur privé
   */
  private ConstantsWEB() {
  }

  /**
   * Chemin d'accès au répertoire de l'archivage
   */
  public static final String PATH_ARCHIVES_FOLDER_PARAMETER_KEY = "pathArchivesFolder";

  /**
   * Extension des fichiers de l'archivage
   */
  public static final String ARCHIVES_EXTENSION_FILE = ".xls";

  /** Séparateur commun pour les fichiers CSV */
  public static final char SEPARATOR = ';';

  /** Mot déclarant une fin de fichier */
  public static final String END_OF_FILE = "<EOF>";

  /**
   * Nom du fichier d'archivage pour les Tiers
   */
  public static final String ARCHIVES_THIRD_PARTY_FILE = "Tiers_";

  /**
   * Nom de la feuille Excel des Tiers
   */
  public static final String ARCHIVES_THIRD_PARTY_SHEET = "Tiers";

  /**
   * Nom du fichier d'archivage pour les Logements
   */
  public static final String ARCHIVES_HOUSING_FILE = "Logement_";

  /**
   * Nom de la feuille Excel des Logements
   */
  public static final String ARCHIVES_HOUSING_SHEET = "Logement";

  /**
   * Numéroe de la premiere ligne pour le contenu du fichier d'archive
   */
  public static final int FIRST_LINE_ARCHIVES_CONTENT = 2;

  /**
   * Nom du fichier d'archivage pour les Contrats Tiers
   */
  public static final String ARCHIVES_THIRD_PARTY_CONTRACT_FILE = "Contrat Tiers_";

  /**
   * Nom de la feuille Excel des Contrats Tiers
   */
  public static final String ARCHIVES_THIRD_PARTY_CONTRACT_SHEET = "Contrat Tiers";

  /**
   * Nom du fichier d'archivage pour les occupants
   */
  public static final String ARCHIVES_TENANT_FILE = "Occupant_";

  /**
   * Nom de la feuille Excel des occupants
   */
  public static final String ARCHIVES_TENANT_SHEET = "Occupant";

  /**
   * Nom du fichier d'archivage pour les contrats occupants
   */
  public static final String ARCHIVES_CONTRACT_FILE = "Contrat Occupant_";

  /**
   * Nom de la feuille Excel des occupants
   */
  public static final String ARCHIVES_CONTRACT_SHEET = "Contrat Occupant";

  /**
   * Réponse Oui
   */
  public static final String ANSWER_YES = "Oui";

  /**
   * Réponse Non
   */
  public static final String ANSWER_NO = "Non";

  /**
   * Pattern date au format dd/MM/yyyy
   */
  public static final String PATTERN_DATE_MM_DD_YYYY = "dd/MM/yyyy";

  /**
   * Pattern Time au format HH:mm
   */
  public static final String PATTERN_TIME = "HH:mm";

  /** Variable flash permettant de passer de page en page l'ID du tiers */
  public static final String ID_THIRD_PARTY = "thirdpartyID";

  /** Variable flash permettant de passer de page en page l'ID du tiers */
  public static final String ID_THIRD_PARTY_CONTRACT = "thirdpartycontractID";

  /** Variable flash permettant de passer de page en page l'ID du logement */
  public static final String ID_HOUSING = "housingID";

  /** Variable flash permettant de passer de page en page l'ID de l'occupant  */
  public static final String ID_TENANT = "tenantID";

  /** Variable flash permettant de passer de page en page l'ID du contrat occupant */
  public static final String ID_CONTRACT = "contractID";

  /** label user actived */
  public static final String OPTION_YES = "information.yes";

  /** label user disactived */
  public static final String OPTION_NO = "information.no";

  /** label user disactived */
  public static final String OPTION_ANY = "information.all";

  /**
   * 3 petits points
   */
  public static final String ELLIPSIS = "...";

  /**
   * Nombre de caractère autorisé pour l'adresse tronquée
   */
  public static final int MAX_CHAR_EXPORT_DATATABLE = 45;

  /** label symbole infini */
  public static final String INFINITY_SYMBOL = "administration.referentiel.benefit.label.infinity";

  /** Liste des options par défaut pour le nombre de ligne de tableau par page de résultat */
  public static final List<String> DEFAULT_ROW_PER_PAGE = Collections.unmodifiableList(Arrays.asList("50", "100", "150"));

  /** Taille de la partie entière d'un nombre défini a 10 */
  public static final int INTEGER_PART_SIZE_10 = 10;

  /** Taille de la partie entière d'un nombre défini a 8 */
  public static final int INTEGER_PART_SIZE_8 = 8;

  /** Taille de la partie entière d'un nombre défini a 5 */
  public static final int INTEGER_PART_SIZE_5 = 5;

  /** Taille de la partie entière d'un nombre défini a 3 */
  public static final int INTEGER_PART_SIZE_3 = 3;

  /** Taille, en nombre de chiffre, de la partie décimal d'un nombre */
  public static final int DECIMAL_PART_SIZE_2 = 2;

  /** Taille, en nombre de chiffre, de la partie décimal d'un nombre */
  public static final int DECIMAL_PART_SIZE_NONE = 0;

  /** Valeur pour un occupant de type salarié */
  public static final String TENANT_TYPE_SALARIED = "Salarié";

  /** Valeur pour un occupant de type retraité */
  public static final String TENANT_TYPE_RETIRED = "Retraité";

  /** Valeur pour un occupant de type NonSalarié */
  public static final String TENANT_TYPE_NOT_SALARIED = "Non salarié";

  /** Taille du champ contrat référence lorsque le type de salarié est Salarié ou Retraité*/
  public static final int REFERENCE_TENANT_SALARIED_SIZE = 8;

  /** Taille du champ contrat référence lorsque le type de salarié est Non salarié*/
  public static final int REFERENCE_TENANT_NOT_SALARIED_SIZE = 15;

  /** Valeur de terminationSavingAmount lors de son initialisation */
  public static final String CONTRACT_TERMINATION_SAVING_AMOUNT_INIT = "0";

  /** Valeur pour l'id d'un nouveau contrat occupant lors de la validation de ses dates de validité. Eviter un champ null dans la requete */
  public static final Long CREATE_CONTRACT_ID_DATE_VALIDITY = 1L;

  /** Taille d'une ligne d'un fichier d'import ARTESIS */
  public static final int ARTESIS_FILE_LINE_LENGHT = 253;

  /** Unité de conversion Byte -> kByte */
  public static final int KILO_BYTE_SIZE = 1024;

  /** Valeur pour le mois précédent */
  public static final int NEXT_MONTH = 1;

  /** Partie de la reference occupant correspondant au lot */
  public static final String CONTRACT_REFERENCE_GROUP = ".000.";

  /** Pattern pour la partie du rang de l'occupant de la reference contrat occupant */
  public static final String CONTRACT_REFERENCE_TENANT_RANK_PATTERN = "000";

  /** Prefix de la partie reference logement pour la référence du contrat occupant */
  public static final String PREFIX_CONTRACT_REFERENCE = "0";

  /** Pattern pour la partie du rang de l'occupant de la reference contrat occupant */
  public static final String MONTH_ARCHIVES_FILENAME_PATTERN = "00";

  /** Terme échu */
  public static final String EXPIRY_DATE = "Echu";

  /** Terme échoir */
  public static final String FALL_DUE = "A échoir";

  /** Périodicité du paiement mensuel */
  public static final String MONTHLY_PAYMENT_CYCLE = "Mensuel";

  /** Valeur pour toutes les agences */
  public static final String ALL_AGENCIES_VALUES = "Toutes";


  /** Identifiant correspondant à toutes les agences */
  public static final Long UNPERSIST_AGENCY_IDENTIFIER = -1L;
}
