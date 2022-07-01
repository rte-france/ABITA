package com.abita.services.ylznaccountingdocnumber.impl;

import com.abita.dao.ylznaccountingdocnumber.entity.YlZnAccountingDocNumberEntity;
import com.abita.dao.ylznaccountingdocnumber.exceptions.YlZnAccountingDocNumberDAOException;
import com.abita.services.ylznaccountingdocnumber.IYlZnAccountingDocNumberService;
import com.abita.dao.ylznaccountingdocnumber.IYlZnAccountingDocNumberDAO;
import com.abita.dto.YlZnAccountingDocNumberDTO;
import com.abita.services.ylznaccountingdocnumber.exceptions.YlZnAccountingDocNumberServiceException;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;
import org.dozer.MappingException;
import org.joda.time.YearMonth;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'implémentation des services des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public class YlZnAccountingDocNumberServiceImpl
  extends
  AbstractService<YlZnAccountingDocNumberEntity, YlZnAccountingDocNumberDTO, YlZnAccountingDocNumberDAOException, Long, YlZnAccountingDocNumberServiceException, IYlZnAccountingDocNumberDAO>
  implements IYlZnAccountingDocNumberService {

  /** Numéro de la première pi-ce comptable YL et ZN */
  public static final Long ACCOUNTING_YL_ZN_NUMBER = 9100000L;

  /**
  * Le DAO des informations nécessaires à la gestion du numéro de document des pièces comptables
  */
  private IYlZnAccountingDocNumberDAO ylZnAccountingDocNumberDAO;

  @Override
  protected IYlZnAccountingDocNumberDAO getSpecificIDAO() {
    return ylZnAccountingDocNumberDAO;
  }

  /**
   * Setter du DAO des informations nécessaires à la gestion du numéro de document des pièces comptables
   * @param ylZnAccountingDocNumberDAO the ylZnAccountingDocNumberDAO to set
   */
  public void setYlZnAccountingDocNumberDAO(IYlZnAccountingDocNumberDAO ylZnAccountingDocNumberDAO) {
    this.ylZnAccountingDocNumberDAO = ylZnAccountingDocNumberDAO;
  }

  @Override
  public String createAccountingPieceNumber(YlZnAccountingDocNumberDTO ylZnAccountingDocNumberDTO) throws YlZnAccountingDocNumberServiceException {

    Long nextValPieceNumber = null;

    nextValPieceNumber = getNextValPieceNumber();

    ylZnAccountingDocNumberDTO.setYlZnAdnPieceNumber(nextValPieceNumber);
    try {
      ylZnAccountingDocNumberDAO.create(mapper.map(ylZnAccountingDocNumberDTO, YlZnAccountingDocNumberEntity.class));
    } catch (YlZnAccountingDocNumberDAOException e) {
      throw new YlZnAccountingDocNumberServiceException(e);
    } catch (MappingException e) {
      throw new YlZnAccountingDocNumberServiceException(e);
    }

    return "000".concat(String.valueOf(nextValPieceNumber));
  }

  /**
   * Permet de récupérer le prochain numéro de document des pièces comptables
   * @return le prochain numéro de document des pièces comptables
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   */
  private Long getNextValPieceNumber() throws YlZnAccountingDocNumberServiceException {
    Long ylZnAccountingDocNumber;
    try {
      ylZnAccountingDocNumber = ylZnAccountingDocNumberDAO.getCurrentMaxValPieceNumber();
    } catch (YlZnAccountingDocNumberDAOException e) {
      throw new YlZnAccountingDocNumberServiceException(e);
    }

    if (null == ylZnAccountingDocNumber) {
      return ACCOUNTING_YL_ZN_NUMBER;
    }
    return ylZnAccountingDocNumber + 1L;
  }

  @Override
  public Long create(YlZnAccountingDocNumberDTO ylZnAccountingDocNumberDTO) throws YlZnAccountingDocNumberServiceException {
    return (Long) super.create(ylZnAccountingDocNumberDTO);
  }

  @Override
  public YlZnAccountingDocNumberDTO findLastGenerationByThirdPartyContractIdAndYearMonth(Long id, YearMonth yearMonth) throws YlZnAccountingDocNumberServiceException {
    try {
      YlZnAccountingDocNumberEntity ylZnAccountingDocNumberEntity = ylZnAccountingDocNumberDAO.findLastGenerationByThirdPartyContractIdAndYearMonth(id, yearMonth);
      if (null != ylZnAccountingDocNumberEntity) {
        return mapper.map(ylZnAccountingDocNumberEntity, YlZnAccountingDocNumberDTO.class);
      }
      return null;
    } catch (YlZnAccountingDocNumberDAOException e) {
      throw new YlZnAccountingDocNumberServiceException(e);
    }
  }

  @Override
  public YlZnAccountingDocNumberDTO findLastGenerationByThirdPartyContractId(Long id) throws YlZnAccountingDocNumberServiceException {
    try {
      YlZnAccountingDocNumberEntity ylZnAccountingDocNumberEntity = ylZnAccountingDocNumberDAO.findLastGenerationByThirdPartyContractId(id);
      if (null != ylZnAccountingDocNumberEntity) {
        return mapper.map(ylZnAccountingDocNumberEntity, YlZnAccountingDocNumberDTO.class);
      }
      return null;
    } catch (YlZnAccountingDocNumberDAOException e) {
      throw new YlZnAccountingDocNumberServiceException(e);
    }
  }

  @Override
  public List<YlZnAccountingDocNumberDTO> findGenerationsByThirdPartyContractIdFromYearMonth(Long id, YearMonth yearMonth) throws YlZnAccountingDocNumberServiceException {
    List<YlZnAccountingDocNumberDTO> lstDTO = new ArrayList<YlZnAccountingDocNumberDTO>();
    try {
      List<YlZnAccountingDocNumberEntity> lstEntity = ylZnAccountingDocNumberDAO.findGenerationsByThirdPartyContractIdFromYearMonth(id, yearMonth);

      for (YlZnAccountingDocNumberEntity ylZnAccountingDocNumber : SafetyUtils.emptyIfNull(lstEntity)) {
        lstDTO.add(mapper.map(ylZnAccountingDocNumber, YlZnAccountingDocNumberDTO.class));
      }
    } catch (MappingException e) {
      throw new YlZnAccountingDocNumberServiceException(e);
    } catch (YlZnAccountingDocNumberDAOException e) {
      throw new YlZnAccountingDocNumberServiceException(e);
    }
    return lstDTO;
  }

  @Override
  public void deleteAllYlZnAccountingDocNumberOfOneContract(Long id) throws YlZnAccountingDocNumberServiceException {
    try {
      ylZnAccountingDocNumberDAO.deleteAllYlZnAccountingDocNumberOfOneContract(id);
    } catch (YlZnAccountingDocNumberDAOException e) {
      throw new YlZnAccountingDocNumberServiceException(e);
    }
  }
}
