package com.abita.services.ncntaccountingdocnumber.impl;

import com.abita.dao.ncntaccountingdocnumber.entity.NcNtAccountingDocNumberEntity;
import com.abita.dao.ncntaccountingdocnumber.exceptions.NcNtAccountingDocNumberDAOException;
import com.abita.services.ncntaccountingdocnumber.INcNtAccountingDocNumberService;
import com.abita.dao.ncntaccountingdocnumber.INcNtAccountingDocNumberDAO;
import com.abita.dto.NcNtAccountingDocNumberDTO;
import com.abita.services.ncntaccountingdocnumber.exceptions.NcNtAccountingDocNumberServiceException;
import com.services.common.impl.AbstractService;
import org.dozer.MappingException;

/**
 * Classe d'implémentation des services des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public class NcNtAccountingDocNumberServiceImpl
  extends
  AbstractService<NcNtAccountingDocNumberEntity, NcNtAccountingDocNumberDTO, NcNtAccountingDocNumberDAOException, Long, NcNtAccountingDocNumberServiceException, INcNtAccountingDocNumberDAO>
  implements INcNtAccountingDocNumberService {

  /** Numéro de la première pi-ce comptable NT et NC */
  public static final Long ACCOUNTING_NT_NC_NUMBER = 11100000L;

  /**
  * Le DAO des informations nécessaires à la gestion du numéro de document des pièces comptables
  */
  private INcNtAccountingDocNumberDAO ncNtAccountingDocNumberDAO;

  @Override
  protected INcNtAccountingDocNumberDAO getSpecificIDAO() {
    return ncNtAccountingDocNumberDAO;
  }

  @Override
  public String createAccountingPieceNumber(NcNtAccountingDocNumberDTO ncNtAccountingDocNumberDTO) throws NcNtAccountingDocNumberServiceException {
    Long nextValPieceNumber = null;

    nextValPieceNumber = getNextValPieceNumber();

    ncNtAccountingDocNumberDTO.setNcNtAdnPieceNumber(nextValPieceNumber);

    try {
      ncNtAccountingDocNumberDAO.create(mapper.map(ncNtAccountingDocNumberDTO, NcNtAccountingDocNumberEntity.class));
    } catch (NcNtAccountingDocNumberDAOException e) {
      throw new NcNtAccountingDocNumberServiceException(e);
    } catch (MappingException e) {
      throw new NcNtAccountingDocNumberServiceException(e);
    }

    return "00".concat(String.valueOf(nextValPieceNumber));
  }

  /**
   * Permet de récupérer le prochain numéro de document des pièces comptables
   * @return le prochain numéro de document des pièces comptables
   * @throws NcNtAccountingDocNumberServiceException
   */
  private Long getNextValPieceNumber() throws NcNtAccountingDocNumberServiceException {
    Long ntNcAccountingDocNumber;

    try {
      ntNcAccountingDocNumber = ncNtAccountingDocNumberDAO.getCurrentMaxValPieceNumber();
    } catch (NcNtAccountingDocNumberDAOException e) {
      throw new NcNtAccountingDocNumberServiceException(e);
    }

    if (null == ntNcAccountingDocNumber) {
      return ACCOUNTING_NT_NC_NUMBER;
    }
    return ntNcAccountingDocNumber + 1L;
  }

  @Override
  public void deleteAllNcNtAccountingDocNumberOfOneContract(Long id) throws NcNtAccountingDocNumberServiceException {
    try {
      ncNtAccountingDocNumberDAO.deleteAllNcNtAccountingDocNumberOfOneContract(id);
    } catch (NcNtAccountingDocNumberDAOException e) {
      throw new NcNtAccountingDocNumberServiceException(e.getMessage(), e);
    }
  }

  /**
   * Setter du DAO des informations nécessaires à la gestion du numéro de document des pièces comptables
   * @param ncNtAccountingDocNumberDAO the ncNtAccountingDocNumberDAO to set
   */
  public void setNcNtAccountingDocNumberDAO(INcNtAccountingDocNumberDAO ncNtAccountingDocNumberDAO) {
    this.ncNtAccountingDocNumberDAO = ncNtAccountingDocNumberDAO;
  }

}
