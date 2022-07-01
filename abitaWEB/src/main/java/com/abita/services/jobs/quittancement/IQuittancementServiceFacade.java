package com.abita.services.jobs.quittancement;

import com.services.paramservice.ParameterService;
import org.joda.time.YearMonth;

import java.io.File;

/**
 * Interface du service façade des fichiers de quittancement
 */
public interface IQuittancementServiceFacade {

  /**
   * Permet de générer les quittancements de loyer
   */
  void generate();

  /**
   * Permet d'envoyer un fichier pdf vers le dossier de sortie après l'avoir zippé
   * @param file Le fichier a zipper
   * @return fichier compressé
   */
  File zipAndSend(File file);

  /**
   * Permet d'envoyer un fichier pdf vers le dossier de sortie après l'avoir zippé
   * en le recherchant a partir d'un mois de l'année
   * @param date La date pour laquelle rechercher le fichier a zipper
   */
  void zipAndSend(YearMonth date);

  /**
   * Permet de définir les paramètres des quittancements
   * @param parameterService paramètres
   */
  void setParameterService(ParameterService parameterService);
}
