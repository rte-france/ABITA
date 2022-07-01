package com.abita.dto.constant;

/**
 * Classe de constantes des DTO
 * @author
 *
 */
public final class DTOConstants {

  /**
   * Constructeur privé
   */
  private DTOConstants() {
  }

  /**
   * Dernier caractère de la table ASCII étendue
   */
  private static final char END_CHARACTER_ASCII = (char) 255;

  /**
   * Nombre de parties qui composent le nom du fichier YL/ZN
   */
  public static final int GCP_YL_ZN_SIZE = 4;

  /**
   * Nombre de parties qui composent le nom des fichiers NT/NC
   */
  public static final int GCP_NT_NC_SIZE = 3;

  /**
   * Concaténation (5fois) du dernier charactère ASCII pour le tri des null dans une datatable
   */
  public static final String END_SORTED_STRING = new StringBuilder().append(END_CHARACTER_ASCII).append(END_CHARACTER_ASCII).append(END_CHARACTER_ASCII)
    .append(END_CHARACTER_ASCII).append(END_CHARACTER_ASCII).toString();

}
