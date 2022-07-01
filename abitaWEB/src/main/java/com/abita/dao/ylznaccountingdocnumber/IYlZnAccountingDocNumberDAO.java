package com.abita.dao.ylznaccountingdocnumber;

import com.abita.dao.ylznaccountingdocnumber.entity.YlZnAccountingDocNumberEntity;
import com.abita.dao.ylznaccountingdocnumber.exceptions.YlZnAccountingDocNumberDAOException;
import com.dao.common.IAbstractDAO;
import org.joda.time.YearMonth;

import java.util.List;

/**
 * Interface des DAO des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public interface IYlZnAccountingDocNumberDAO extends IAbstractDAO<YlZnAccountingDocNumberEntity, YlZnAccountingDocNumberDAOException> {

  /**
   * Permet de récupérer le prochain numéro des pièces comptables
   * @return le prochain numéro des pièces comptables
   * @throws YlZnAccountingDocNumberDAOException une YlZnAccountingDocNumberDAOException
   */
  Long getCurrentMaxValPieceNumber() throws YlZnAccountingDocNumberDAOException;

  /**
   * Permet de récupérer la pièce comptable de la dernière génération d’un contrat tiers d’un mois précis
   * @param thirdPartyContractId l’identifiant du contrat tier
   * @param yearMonth le couple mois/année de la génération
   * @return la pièce comptable
   * @throws YlZnAccountingDocNumberDAOException une YlZnAccountingDocNumberDAOException
   */
  YlZnAccountingDocNumberEntity findLastGenerationByThirdPartyContractIdAndYearMonth(Long thirdPartyContractId, YearMonth yearMonth) throws YlZnAccountingDocNumberDAOException;

  /**
   * Permet de récupérer la pièce comptable de la dernière génération d’un contrat tiers
   * @param thirdPartyContractId l’identifiant du contrat tier
   * @return la pièce comptable
   * @throws YlZnAccountingDocNumberDAOException une YlZnAccountingDocNumberDAOException
   */
  YlZnAccountingDocNumberEntity findLastGenerationByThirdPartyContractId(Long thirdPartyContractId) throws YlZnAccountingDocNumberDAOException;

  /**
   * Permet de récupérer les pièces comptables d’un contrat tiers à partir d’un mois précis
   * @param thirdPartyContractId l’identifiant du contrat tier
   * @param yearMonth le couple mois/année de la génération
   * @return les pièces comptables
   * @throws YlZnAccountingDocNumberDAOException une YlZnAccountingDocNumberDAOException
   */
  List<YlZnAccountingDocNumberEntity> findGenerationsByThirdPartyContractIdFromYearMonth(Long thirdPartyContractId, YearMonth yearMonth) throws YlZnAccountingDocNumberDAOException;

  @Override
  Long create(YlZnAccountingDocNumberEntity ylZnAccountingDocNumberEntity) throws YlZnAccountingDocNumberDAOException;

  /**
   * Permet de supprimer les historisations des numéros comptables d'un contrat tiers
   * @param id identifiant du contrat tiers
   * @throws YlZnAccountingDocNumberDAOException une YlZnAccountingDocNumberDAOException;
   */
  void deleteAllYlZnAccountingDocNumberOfOneContract(Long id) throws YlZnAccountingDocNumberDAOException;

}
