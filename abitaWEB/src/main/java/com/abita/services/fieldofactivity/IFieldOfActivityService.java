package com.abita.services.fieldofactivity;

import com.abita.dto.FieldOfActivityDTO;
import com.abita.services.fieldofactivity.exceptions.FieldOfActivityServiceException;

import java.util.List;

/**
 * Interface du service des domaines d'activité
 *
 * @author
 */
public interface IFieldOfActivityService {

  /**
   * Permet de récupérer la liste de tous les domaines d'activité avec l'information de si on peut les supprimer
   * @return une liste de tous les domaines d'activité
   * @throws FieldOfActivityServiceException une FieldOfActivityServiceException
   */
  List<FieldOfActivityDTO> findAllFieldOfActivity() throws FieldOfActivityServiceException;

  /**
   * permet de récupérer la liste de tous les domaines d'activité en fonction d'une agence
   * @param idAgency l'identifiant technique d'une agence
   * @return une liste de tous les domaines d'activité d'une agence
   * @throws FieldOfActivityServiceException une FieldOfActivityServiceException
   */
  List<FieldOfActivityDTO> findFieldOfActivityByAgency(Long idAgency) throws FieldOfActivityServiceException;

  /**
   * Permet de récupérer la liste de tous les domaines d'activité
   * @return une liste de tous les domaines d'activité
   * @throws FieldOfActivityServiceException une FieldOfActivityServiceException
   */
  List<FieldOfActivityDTO> find() throws FieldOfActivityServiceException;

  /**
   * Permet de créer un nouveau domaine d'activité en BDD
   * @param dto un domaine d'activité
   * @return l'identifiant du domaine d'activité
   * @throws FieldOfActivityServiceException une FieldOfActivityServiceException
   */
  Long create(FieldOfActivityDTO dto) throws FieldOfActivityServiceException;

  /**
   * Met à jour un domaine d'activité en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param dto un domaine d'activité
   * @throws FieldOfActivityServiceException une FieldOfActivityServiceException
   */
  void update(FieldOfActivityDTO dto) throws FieldOfActivityServiceException;

  /**
   * Supprime un domaine d'activité en BDD
   *
   * @param id l'identifiant du domaine d'activité à supprimer
   * @throws FieldOfActivityServiceException une FieldOfActivityServiceException
   */
  void delete(Long id) throws FieldOfActivityServiceException;

  /**
   * Méthode permettant de sauvegarder la liste des domaines d'activité depuis l'administration
   * @param lst : la liste des domaines d'activité
   * @throws FieldOfActivityServiceException l'exception
   */
  void saveListing(List<FieldOfActivityDTO> lst) throws FieldOfActivityServiceException;

}
