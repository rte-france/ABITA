package com.abita.dao.thirdparty;

import com.abita.dao.thirdparty.entity.ThirdPartyEntity;
import com.abita.dao.thirdparty.exceptions.ThirdPartyDAOException;
import com.abita.dto.unpersist.ThirdPartyCriteriaDTO;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface des DAO des tiers
 * @author
 *
 */
public interface IThirdPartyDAO extends IAbstractDAO<ThirdPartyEntity, ThirdPartyDAOException> {

  /**
   * Permet de vérifier si un tiers n'existe pas par sa référence GCP
   * @param gcpReference la référence GCP du tiers
   * @return un booléen flagant si le tiers existe (true) ou non (false)
   * @throws ThirdPartyDAOException une ThirdPartyDAOException
   */
  boolean isThirdPartyExist(String gcpReference) throws ThirdPartyDAOException;

  /**
   * Permet de récupérer la liste de tiers en BDD répondant aux critères choisis
   * @param criteria les critères de recherche
   * @return une liste de tous les tiers
   * @throws ThirdPartyDAOException une ThirdPartyDAOException
   */
  List<ThirdPartyEntity> findByCriteria(ThirdPartyCriteriaDTO criteria) throws ThirdPartyDAOException;

  /**
   * Permet de récupérer un tiers par sa référence GCP
   * @param thirdPartyToOpenId la référence GCP
   * @return un tiers
   * @throws ThirdPartyDAOException une ThirdPartyDAOException
   */
  ThirdPartyEntity getByGCPRef(String thirdPartyToOpenId) throws ThirdPartyDAOException;

  /**
   * Retourne la liste des tiers qui ont possédé le logement
   * @param idHousing l'id du logement
   * @return la liste des tiers
   * @throws ThirdPartyDAOException une ThirdPartyDAOException
   */
  List<ThirdPartyEntity> findThirdPartyByHousing(long idHousing) throws ThirdPartyDAOException;

  /**
   * Permet de savoir si le tiers est référencé ailleurs dans l'application
   * @param idThirdParty l'id du tiers que l'on recherche
   * @return true si utilisé et false sinon
   * @throws ThirdPartyDAOException l'exception
   */
  boolean isRemovable(long idThirdParty) throws ThirdPartyDAOException;

}
