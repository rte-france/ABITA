package com.abita.dao.batch.constant;

/**
 * Classe des constantes utilisées dans les batchs
 */
public final class BatchConstants {

  /**
   * Constructeur privé
   */
  private BatchConstants() {
  }

  /** Le domaine de paramètrage pour les batchs */
  public static final String BATCH_PARAMETER_DOMAIN = "batch";

  /** Le nom des fichiers temporaires pour les pièces comptables GCP */
  public static final String BATCH_GCP_TEMP_FILE_NAME = "gcpTempFileName";

  /** Le chemin du dossier des fichiers temporaires pour les pièces comptables GCP */
  public static final String BATCH_GCP_TEMP_PATH = "gcpTempPath";

  /** Le chemin du dossier des fichiers envoyés à GCP */
  public static final String BATCH_GCP_OUTBOUND_PATH = "gcpOutboundPath";

  /** Le chemin du dossier d'archive des pièces comptables GCP */
  public static final String BATCH_GCP_ARCHIVE_PATH = "gcpArchivePath";

  /** Le nom des fichiers temporaires pour les fichiers ARTESIS */
  public static final String BATCH_ARTESIS_TEMP_FILE_NAME = "artesisTempFileName";

  /** Le chemin du dossier pour les fichiers temporaires des fichiers ARTESIS */
  public static final String BATCH_ARTESIS_TEMP_PATH = "artesisTempPath";

  /** Le chemin du dossier des fichiers reçus par ARTESIS */
  public static final String BATCH_ARTESIS_INBOUND_PATH = "artesisInboundPath";

  /** Le chemin du dossier des fichiers envoyés à ARTESIS */
  public static final String BATCH_ARTESIS_OUTBOUND_PATH = "artesisOutboundPath";

  /** Le chemin du dossier pour l'archivage des fichiers ARTESIS */
  public static final String BATCH_ARTESIS_ARCHIVE_PATH = "artesisArchivePath";

  /** Le nom du fichier temporaire NNI */
  public static final String BATCH_ARTESIS_TEMP_NNI_FILE_NAME = "artesisNniTempFileName";

  /** Le nom des fragments de la pièce comptable YL-ZN */
  public static final String BATCH_GCP_YL_ZN_FRAGMENT_FILE_NAME = "ylAndZnFragmentFileName";

  /**  Le nom du fichier ARTESIS Données Agent*/
  public static final String BATCH_ARTESIS_AGENT_DATA_FILE_NAME = "agentDataFileName";
}
