package com.abita.services.detailcron;

import com.abita.dto.DetailCronDTO;
import com.abita.services.detailcron.exceptions.DetailCronServiceException;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Interface du service des détails cron
 *
 * @author
 */
public interface IDetailCronService {

  /**
   * Permet de récupérer un détail cron par son identifiant en BDD
   * @param id l'identifiant du cron
   * @return Un DetailCron
   * @throws DetailCronServiceException une DetailCronServiceException
   */
  DetailCronDTO get(Long id) throws DetailCronServiceException;

  /**
   * Permet de récupérer la liste de tous les CRONS
   * @return la liste de tous les CRONS
   * @throws DetailCronServiceException une DetailCronServiceException
   */
  List<DetailCronDTO> find() throws DetailCronServiceException;

  /**
   * Met à jour les CRONS
   * @param crons liste de crons
   * @throws DetailCronServiceException une DetailCronServiceException
   */
  void update(List<DetailCronDTO> crons) throws DetailCronServiceException;

  /**
   * Permet de récupérer les informations des crons pour le mois en cours
   * @return les informations des crons
   */
  DetailCronDTO getCronsOfCurrentMonth();

  /**
   * Permet de récupérer les informations des crons pour le mois précédent
   * @return les informations des crons
   */
  DetailCronDTO getCronsOfLastMonth();

  /**
   * Permet de lancer les CRON, d'envoi et de reception des fichiers GCP et ARTESIS, parametrés en base de données
   */
  void createCron();

  /**
   * Permet de mettre à jour les CRON, d'envoi et de reception des fichiers GCP et ARTESIS, parametrés en base de données pour le mois en cours
   * @param currentDetailCronDTO les paramètres des CRON
   */
  void updateCron(DetailCronDTO currentDetailCronDTO);

  /**
   * Permet de mettre à jour les CRON, d'envoi et de reception des fichiers GCP et ARTESIS, automatiquement le 1er jour du mois
   */
  void updateAutomaticForANewMonth() throws DetailCronServiceException;

  /**
   * Permet de récuperer la date et l'heure du premier cron qui se lance dans le mois
   * @param detailCron les détails des CRON
   * @return lLa date et l'heure du premier CRON
   * @throws DetailCronServiceException une DetailCronServiceException
   */
  DateTime getFirstCronOfTheMonth(DetailCronDTO detailCron) throws DetailCronServiceException;

  /**
   * Permet de récuperer la date du dernier cron qui se lance dans le mois
   * @param detailCron les détails des CRON
   * @return la date et l'heure du dernier CRON
   * @throws DetailCronServiceException une DetailCronServiceException
   */
  DateTime getLastCronOfTheMonth(DetailCronDTO detailCron) throws DetailCronServiceException;

  /**
   * Permet de récuperer la date et l’heure du premier cron qui se lance dans le mois
   * @return date et heure du CRON GCP YL
   * @throws DetailCronServiceException une DetailCronServiceException
   */
  DateTime getYlCron() throws DetailCronServiceException;
}
