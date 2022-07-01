package com.abita.services.accountingcode;

import com.abita.dto.AccountingCodeDTO;
import com.abita.services.accountingcode.exceptions.AccountingCodeServiceException;

import java.util.List;
import java.util.Map;

/**
 * Interface de service des codes comptables
 * @author
 *
 */
public interface IAccountingCodeService {

  /**
   * Permet de récupérer la totalité des codes comptables
   * @return une liste de tous les codes comptables
   * @throws AccountingCodeServiceException une AccountingCodeServiceException
   */
  List<AccountingCodeDTO> findAllAccountingCode() throws AccountingCodeServiceException;

  /**
   * Permet de sauvergarder la liste des codes comptables
   * @param lstAccountingCode une liste de code comptables à sauvegarder
   * @throws AccountingCodeServiceException une AccountingCodeServiceException
   */
  void saveListing(List<AccountingCodeDTO> lstAccountingCode) throws AccountingCodeServiceException;

  /**
   * Permet de générer une map des codes comptables en fonction des codes techniques attribués a chaque code comptable
   * @return Une map de codes comptables
   * @throws AccountingCodeServiceException une AccountingCodeServiceException
   */
  Map<String, AccountingCodeDTO> buildAccountingCodeMapping() throws AccountingCodeServiceException;

}
