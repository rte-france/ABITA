package com.abita.services.costcenter;

import com.abita.dto.CostCenterDTO;
import com.abita.services.costcenter.exceptions.CostCenterServiceException;

import java.util.List;

/**
 * Interface du service des centres coûts
 *
 * @author
 */
public interface ICostCenterService {

  /**
   * Permet de récupérer la liste de tous les centres coûts avec l'information de si on peut les supprimer
   *
   * @return une liste de tous les centres coûts
   * @throws CostCenterServiceException une CostCenterServiceException
   */
  List<CostCenterDTO> findAllCostCenter() throws CostCenterServiceException;

  /**
   * Permet de récupérer la liste de tous les centres coûts
   *
   * @return une liste de tous les centres coûts
   *
   * @throws CostCenterServiceException une CostCenterServiceException
   */
  List<CostCenterDTO> find() throws CostCenterServiceException;

  /**
   * Permet de créer un nouveau contrat coût en BDD
   *
   * @param costcenterDTO un contrat coût
   * @return l'identifiant du contrat coût
   * @throws CostCenterServiceException une CostCenterServiceException
   */
  Long create(CostCenterDTO costcenterDTO) throws CostCenterServiceException;

  /**
   * Met à jour un contrat coût en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param costcenterDTO un contrat coût
   * @throws CostCenterServiceException une CostCenterServiceException
   */
  void update(CostCenterDTO costcenterDTO) throws CostCenterServiceException;

  /**
   * Supprime un contrat coût en BDD
   *
   * @param id l'identifiant du contrat coût à supprimer
   * @throws CostCenterServiceException une CostCenterServiceException
   */
  void delete(Long id) throws CostCenterServiceException;

  /**
   * Méthode permettant de sauvegarder la liste des centres de coût depuis l'administration
   * @param lstCostCenter : la liste des centres coûts
   * @throws CostCenterServiceException l'exception
   */
  void saveListing(List<CostCenterDTO> lstCostCenter) throws CostCenterServiceException;

}
