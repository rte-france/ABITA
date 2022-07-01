package com.abita.services.thirdpartycontract;

import com.abita.dto.ThirdPartyContractDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.ThirdPartyContractCriteriaDTO;
import com.abita.services.thirdpartycontract.exceptions.ThirdPartyContractServiceException;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Interface des services des contrats tiers
 *
 * @author
 */
public interface IThirdPartyContractServiceFacade {

  /**
   * Permet de créer un nouveau contrat tiers en BDD
   *
   * @param thirdPartyContractDTO un contrat tiers
   * @return l'identifiant du contrat tiers
   * @throws ThirdPartyContractServiceException une ThirdPartyContractServiceException
   */
  Long create(ThirdPartyContractDTO thirdPartyContractDTO) throws ThirdPartyContractServiceException;

  /**
   * Permet de récupérer un contrat tiers par son identifiant en BDD
   *
   * @param id l'identifiant du contrat tiers
   * @return Un contrat tiers
   * @throws ThirdPartyContractServiceException une ThirdPartyContractServiceException
   */
  ThirdPartyContractDTO get(Long id) throws ThirdPartyContractServiceException;

  /**
   * Met à jour un contrat tiers en BDD.
   * Attention : Ne pas faire un create puis un merge dans la même méthode.
   * @param thirdPartyContractDTO un contrat tiers
   * @throws ThirdPartyContractServiceException une ThirdPartyContractServiceException
   */
  void update(ThirdPartyContractDTO thirdPartyContractDTO) throws ThirdPartyContractServiceException;

  /**
   * Permet de récupérer la liste de tous les contrat tiers en BDD
   *
   * @return une liste de tous les contrats tiers
   *
   * @throws ThirdPartyContractServiceException une ThirdPartyContractServiceException
   */
  List<ThirdPartyContractDTO> find() throws ThirdPartyContractServiceException;

  /**
   * Permet de récupérer la liste des contrat tiers en BDD répondant aux critères choisis
   * @param criteria les critères de recherche
   * @param housingCriteria critères de recherche sur les informations des logements
   *
   * @return une liste de tous les contrats tiers
   *
   * @throws ThirdPartyContractServiceException une ThirdPartyContractServiceException
   */
  List<ThirdPartyContractDTO> findByCriteria(ThirdPartyContractCriteriaDTO criteria, HousingCriteriaDTO housingCriteria) throws ThirdPartyContractServiceException;

  /**
   * Permet d'obtenir la liste des contrats pour un logement
   * @param idHousing identifiant du logement
   * @return liste de contract
   * @throws ThirdPartyContractServiceException une ThirdPartyContractServiceException
   */
  List<ThirdPartyContractDTO> findThirdPartyContractByHousing(long idHousing) throws ThirdPartyContractServiceException;

  /**
   * Génère la référence du contrat tiers
   * @return la référence du contrat tiers
   * @throws ThirdPartyContractServiceException l'exception
   */
  String generateReference() throws ThirdPartyContractServiceException;

  /**
   * Permet de retrouver le dernier contrat en cours de validité
   * @param id identifiant du logement
   * @return le dernier contrat en cours de validité
   * @throws ThirdPartyContractServiceException l'exception
   */
  ThirdPartyContractDTO findLastThirdPartyContractByHousing(Long id) throws ThirdPartyContractServiceException;

  /**
   * Permet de récupérer les contrats tiers en cours ou clos
   * @return une liste de tous les contrats tiers concernés
   * @throws ThirdPartyContractServiceException l’exception
   */
  List<ThirdPartyContractDTO> findThirdPartyContractInProgressOrClose() throws ThirdPartyContractServiceException;

  /**
   * Permet de récupérer les contrats appartenant à un cycle en particulier
   * @param startDate date du début du cycle
   * @param endDate date de fin du cycle
   * @param paymentCycle nom du cycle
   * @param expiryDate terme échu ou non
   * @return une liste de tous les contrats tiers concernés
   * @throws ThirdPartyContractServiceException l’exception
   */
  List<ThirdPartyContractDTO> findThirdPartyContractPerCycle(LocalDate startDate, LocalDate endDate, String paymentCycle, Integer expiryDate)
    throws ThirdPartyContractServiceException;

  /**
   * Permet de récupérer les contrats tiers à régulariser
   * @return une liste de tous les contrats tiers concernés
   * @throws ThirdPartyContractServiceException l’exception
   */
  List<ThirdPartyContractDTO> findThirdPartyContractToRegularize() throws ThirdPartyContractServiceException;

  /**
   * Permet de remettre à 0 la liste des contrats à régulariser
   * @throws ThirdPartyContractServiceException l’exception
   */
  void resetThirdPartyContractToRegularize() throws ThirdPartyContractServiceException;

  /**
   * Permet de mettre à jour la charge ponctuelle des contrats tiers
   * @throws ThirdPartyContractServiceException une ThirdPartyContractServiceException
   */
  void updateSporadicallyInvoicingThirdPartyContract() throws ThirdPartyContractServiceException;

  /**
   * Permet de récupérer un contrat tiers par sa référence
   * @param reference la référence du contrat tiers
   * @return un contrat tiers
   * @throws ThirdPartyContractServiceException une ThirdPartyContractServiceException
   */
  ThirdPartyContractDTO findByReference(String reference) throws ThirdPartyContractServiceException;

  /**
   * Service permettant de supprimer un contrat tiers
   * @param id identifiant du contrat tiers
   * @throws ThirdPartyContractServiceException une ThirdPartyContractServiceException
   */
  void delete(Long id) throws ThirdPartyContractServiceException;

  /**
   * Permet de récupérer tous les contrats avec une révision active
   * @return une liste de tous les contrats avec une révision active
   * @throws ThirdPartyContractServiceException l’exception
   */
  List<ThirdPartyContractDTO> findThirdPartyContractWithActiveRevision() throws ThirdPartyContractServiceException;

  /**
   * Permet de remettre à 0 les révisions actives sur les contrats tiers
   * @throws ThirdPartyContractServiceException l’exception
   */
  void resetActiveRevisionThirdPartyContract() throws ThirdPartyContractServiceException;

  /**
   * Permet de sauvegarder les révisions actives des contrats tiers
   * @throws ThirdPartyContractServiceException l’exception
   */
  void saveActiveRevisionThirdPartyContract() throws ThirdPartyContractServiceException;

  /**
   * Permet de sauvegarder des champs lors de la cloture d'un contrat
   * @throws ThirdPartyContractServiceException l'exception
   */
  void saveDatasWhenClosedThirdPartyContract() throws ThirdPartyContractServiceException;


}
