package com.abita.services.historyhousing;

import com.abita.dto.HistoryHousingDTO;
import com.abita.dto.HousingDTO;
import com.abita.services.historyhousing.exceptions.HistoryHousingServiceException;

/**
 * Interface du service d'historisation des logements
 * @author
 *
 */
public interface IHistoryHousingService {

  /**
   * Permet de créer un nouveau logement d’historisation
   *
   * @param historyHousingDTO un logement
   * @return l’identifiant du logement
   * @throws HistoryHousingServiceException une HistoryHousingServiceException
   */
  Long create(HistoryHousingDTO historyHousingDTO) throws HistoryHousingServiceException;

  /**
   * Service qui permet l'historisation des logements
   * @throws HistoryHousingServiceException une HistoryHousingServiceException
   */
  void historizeAllHousings() throws HistoryHousingServiceException;

  /**
   * Service qui permet de supprimer les historisations des logements vieille d'un an
   * @param month le mois d'historisation
   * @param year l'année d'historisation
   * @throws HistoryHousingServiceException une HistoryHousingServiceException
   */
  void deleteOldHousings(int month, int year) throws HistoryHousingServiceException;

  /**
   * Service qui permet de récuperer un logement en fonction de son id, de son mois d’historisation et de son année
   * @param id l’identifiant du logement
   * @param month le mois d’historisation du logement
   * @param year l’année d’historisation du logement
   * @return l’historisation du contrat occupant
   * @throws HistoryHousingServiceException une HistoryHousingServiceException
   */
  HistoryHousingDTO get(Long id, int month, int year) throws HistoryHousingServiceException;

  /**
   * Service qui permet de récuperer le logement le plus récent en fonction du logement, de son mois d’historisation et de son année
   * @param housing le logement
   * @param month le mois d’historisation du logement
   * @param year l’année d’historisation du logement
   * @param temp Afin de savoir si l'historisation est temporaire
   * @return l’historisation du contrat occupant
   * @throws HistoryHousingServiceException une HistoryHousingServiceException
   */
  HistoryHousingDTO getLatest(HousingDTO housing, int month, int year, Boolean temp) throws HistoryHousingServiceException;

  /**
   * Service qui permet de récupérer une historisation de logement en fonction de son id
   * @param historyHousingId identifiant de l’historisation de logement
   * @return historisation de logement
   * @throws HistoryHousingServiceException une HistoryHousingServiceException
   */
  HistoryHousingDTO get(Long historyHousingId) throws HistoryHousingServiceException;

  /**
   * Service qui permet de mettre à jour les historisations temporaires d’un logement
   * @param housing logement
   * @throws HistoryHousingServiceException une HistoryHousingServiceException
   */
  void updateTemporaries(HousingDTO housing) throws HistoryHousingServiceException;

  /**
   * Service permettant de supprimer les historisations d'un logement
   * @param id identifiant du logement
   * @throws HistoryHousingServiceException une HistoryHousingServiceException
   */
  void deleteAllHistoryHousingOfOneHousing(Long id) throws HistoryHousingServiceException;
}
