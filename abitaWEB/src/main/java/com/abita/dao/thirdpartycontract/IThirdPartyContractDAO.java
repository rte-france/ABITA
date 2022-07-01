package com.abita.dao.thirdpartycontract;

import com.abita.dao.thirdpartycontract.entity.ThirdPartyContractEntity;
import com.abita.dao.thirdpartycontract.exceptions.ThirdPartyContractDAOException;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.ThirdPartyContractCriteriaDTO;
import com.dao.common.IAbstractDAO;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Interface des DAO des contrats tiers
 *
 * @author
 */
public interface IThirdPartyContractDAO extends IAbstractDAO<ThirdPartyContractEntity, ThirdPartyContractDAOException> {

  /**
   * Permet de récupérer la liste des contrats tiers en BDD répondant aux critères choisis
   * @param criteria les critères de recherche
   * @param housingCriteria critères de recherche sur les informations des logements
   * @return une liste de tous les contrats tiers
   * @throws ThirdPartyContractDAOException une ThirdPartyContractDAOException
   */
  List<ThirdPartyContractEntity> findByCriteria(ThirdPartyContractCriteriaDTO criteria, HousingCriteriaDTO housingCriteria) throws ThirdPartyContractDAOException;

  /**
   * Retourne la liste des contrats pour un logement
   * @param idHousing l'identifiant pour un logement
   * @return une liste de thirdPartyContractEntity
   * @throws ThirdPartyContractDAOException une ThirdPartyContractDAOException
   */
  List<ThirdPartyContractEntity> findContractByHousing(long idHousing) throws ThirdPartyContractDAOException;

  /**
   * Génère la référence du contrat tiers
   * @return la référence du contrat tiers
   * @throws ThirdPartyContractDAOException l'exception
   */
  String generateReference() throws ThirdPartyContractDAOException;

  /**
   * Permet de retrouver le dernier contrat en cours de validité
   * @param id identifiant du logement
   * @return le dernier contrat en cours de validité
   * @throws ThirdPartyContractDAOException une ThirdPartyContractDAOException
   */
  ThirdPartyContractEntity findLastThirdPartyContractByHousing(Long id) throws ThirdPartyContractDAOException;

  /**
   * Permet de récupérer les contrats appartenant à un cycle en particulier
   * @param startDate date du début du cycle
   * @param endDate date de fin du cycle
   * @param paymentCycle nom du cycle
   * @param expiryDate terme échu ou non
   * @return une liste de tous les contrats tiers concernés
   * @throws ThirdPartyContractDAOException une ThirdPartyContractDAOException
   */
  List<ThirdPartyContractEntity> findThirdPartyContractPerCycle(LocalDate startDate, LocalDate endDate, String paymentCycle, Integer expiryDate)
    throws ThirdPartyContractDAOException;

  /**
   * Permet de récupérer les contrats en cours ou clos
   * @return une liste de tous les contrats tiers concernés
   * @throws ThirdPartyContractDAOException une ThirdPartyContractDAOException
   */
  List<ThirdPartyContractEntity> findThirdPartyContractInProgressOrClose() throws ThirdPartyContractDAOException;

  /**
   * Permet de récupérer les contrats à régulariser
   * @return une liste de tous les contrats tiers concernés
   * @throws ThirdPartyContractDAOException une ThirdPartyContractDAOException
   */
  List<ThirdPartyContractEntity> findThirdPartyContractToRegularize() throws ThirdPartyContractDAOException;

  /**
   * Permet de remettre à 0 la liste des contrats à régulariser
   * @throws ThirdPartyContractDAOException une ThirdPartyContractDAOException
   */
  void resetThirdPartyContractToRegularize() throws ThirdPartyContractDAOException;

  /**
   * Permet de mettre à jour la charge ponctuelle des contrats tiers
   * @throws ThirdPartyContractDAOException une ThirdPartyContractDAOException
   */
  void updateSporadicallyInvoicingThirdPartyContract() throws ThirdPartyContractDAOException;

  /**
   * Permet de récupérer un contrat tiers par sa référence
   * @param reference la référence du contrat tiers
   * @return un contrat tiers
   * @throws ThirdPartyContractDAOException une ThirdPartyContractDAOException
   */
  ThirdPartyContractEntity findByReference(String reference) throws ThirdPartyContractDAOException;
}
