package com.abita.util.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe helper concernant l'import des fichiers Artesis
 * @author
 *
 */
public final class ArtesisHelper {

  /**
   * Constructeur privé
   */
  private ArtesisHelper() {
  }

  /**
   * Permet de parser le fichier d'import Artesis
   * @param lines les lignes du fichier
   * @return la map avec les informations splitées
   */
  public static Map<String, Map<String, String>> parseArtesisFile(List<String> lines) {

    Map<String, Map<String, String>> splitMap = new HashMap<String, Map<String, String>>();

    // Pou chaque ligne du fichier on split juste les infos nécessaires
    for (String line : lines) {
      splitArtesisLine(line, splitMap);
    }
    return splitMap;
  }

  /**
   * Permet de splitter une ligne du fichier d'import Artesis suivant un enum contenant les informations à splitter
   * @param line la ligne à splitter
   * @param artesisSplitMap la map à alimenter
   */
  private static void splitArtesisLine(String line, Map<String, Map<String, String>> artesisSplitMap) {

    Map<String, String> valueMap = new HashMap<String, String>();

    // NNI de l'occupant dont les infos doivent être mise à jour
    String tenantNNI = line.substring(ArtesisIndexEnum.SHORTNNI.getBeginIndex(), ArtesisIndexEnum.SHORTNNI.getLenght());

    // Pour chaque valeur de l'enum...
    for (ArtesisIndexEnum value : ArtesisIndexEnum.values()) {

      // On associe l'identifiant de l'information à mettre à jour (clé de la map) à sa valeur splitée...
      valueMap.put(value.getIdentifier(), line.substring(value.getBeginIndex(), value.getLenght()));

      // ...au NNI de l'occupant
      artesisSplitMap.put(tenantNNI, valueMap);

    }

  }
}
