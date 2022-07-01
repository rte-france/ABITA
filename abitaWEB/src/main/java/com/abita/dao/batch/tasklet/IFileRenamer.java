package com.abita.dao.batch.tasklet;

/**
 * Permet de renommer un fichier
 * @author
 *
 */
public interface IFileRenamer {

  /**
   * Methode qui sert a renommé un fichier à partir du nom de fichier original
   * @param originalName Nom de fichier original
   * @return Le nom du fichier modifié
   */
  String rename(String originalName);
}