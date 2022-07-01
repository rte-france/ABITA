package com.abita.services.historybenefits.impl;

import com.abita.dao.historybenefits.entity.HistoryBenefitsEntity;
import com.abita.dao.historybenefits.exceptions.HistoryBenefitsDAOException;
import com.abita.services.historybenefits.IHistoryBenefitsService;
import com.abita.dao.historybenefits.IHistoryBenefitsDAO;
import com.abita.dto.HistoryBenefitsDTO;
import com.abita.services.historybenefits.exceptions.HistoryBenefitsServiceException;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;
import org.dozer.MappingException;

import java.util.ArrayList;
import java.util.List;

/**
 *  Classe d'implémentation d'historisation des avantages en nature
 * @author
 *
 */
public class HistoryBenefitsServiceImpl extends
  AbstractService<HistoryBenefitsEntity, HistoryBenefitsDTO, HistoryBenefitsDAOException, Long, HistoryBenefitsServiceException, IHistoryBenefitsDAO> implements
        IHistoryBenefitsService {

  /** DAO pour l’historisation des avantages en nature */
  private IHistoryBenefitsDAO historyBenefitsDAO;

  @Override
  public void historizeAllBenefits() throws HistoryBenefitsServiceException {
    try {
      historyBenefitsDAO.historizeAllBenefits();
    } catch (HistoryBenefitsDAOException e) {
      throw new HistoryBenefitsServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void deleteOldBenefits(int month, int year) throws HistoryBenefitsServiceException {
    try {
      historyBenefitsDAO.deleteOldBenefits(month, year);
    } catch (HistoryBenefitsDAOException e) {
      throw new HistoryBenefitsServiceException(e.getMessage(), e);
    }
  }

  @Override
  public List<HistoryBenefitsDTO> get(int month, int year) throws HistoryBenefitsServiceException {

    List<HistoryBenefitsDTO> lstDTO = new ArrayList<HistoryBenefitsDTO>();
    try {
      List<HistoryBenefitsEntity> lstEntity = historyBenefitsDAO.get(month, year);

      for (HistoryBenefitsEntity historyBenefitsEntity : SafetyUtils.emptyIfNull(lstEntity)) {
        lstDTO.add(mapper.map(historyBenefitsEntity, HistoryBenefitsDTO.class));
      }
    } catch (HistoryBenefitsDAOException e) {
      throw new HistoryBenefitsServiceException(e);
    } catch (MappingException e) {
      throw new HistoryBenefitsServiceException(e);
    }

    return lstDTO;
  }

  @Override
  public Long create(HistoryBenefitsDTO historyBenefitsDTO) throws HistoryBenefitsServiceException {
    return (Long) super.create(historyBenefitsDTO);
  }

  @Override
  protected IHistoryBenefitsDAO getSpecificIDAO() {
    return historyBenefitsDAO;
  }

  /**
   * @param historyBenefitsDAO the historyBenefitsDAO to set
   */
  public void setHistoryBenefitsDAO(IHistoryBenefitsDAO historyBenefitsDAO) {
    this.historyBenefitsDAO = historyBenefitsDAO;
  }
}
