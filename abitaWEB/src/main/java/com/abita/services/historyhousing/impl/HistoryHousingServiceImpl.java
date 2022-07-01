package com.abita.services.historyhousing.impl;

import com.abita.dao.historyhousing.entity.HistoryHousingEntity;
import com.abita.dao.historyhousing.exceptions.HistoryHousingDAOException;
import com.abita.services.historyhousing.IHistoryHousingService;
import com.abita.dao.historyhousing.IHistoryHousingDAO;
import com.abita.dto.HistoryHousingDTO;
import com.abita.dto.HousingDTO;
import com.abita.services.historyhousing.exceptions.HistoryHousingServiceException;
import com.services.common.impl.AbstractService;

import java.util.List;

/**
 *  Classe d'implémentation d'historisation des logements
 * @author
 *
 */
public class HistoryHousingServiceImpl extends
  AbstractService<HistoryHousingEntity, HistoryHousingDTO, HistoryHousingDAOException, Long, HistoryHousingServiceException, IHistoryHousingDAO> implements IHistoryHousingService {

  /** DAO pour l’historisation des logements */
  private IHistoryHousingDAO historyHousingDAO;

  @Override
  public Long create(HistoryHousingDTO historyHousingDTO) throws HistoryHousingServiceException {
    return (Long) super.create(historyHousingDTO);
  }

  @Override
  public HistoryHousingDTO get(Long id, int month, int year) throws HistoryHousingServiceException {
    HistoryHousingEntity historyHousing;
    try {
      historyHousing = historyHousingDAO.get(id, month, year);
    } catch (HistoryHousingDAOException e) {
      throw new HistoryHousingServiceException(e.getMessage(), e);
    }
    if (historyHousing == null) {
      return null;
    } else {
      return mapper.map(historyHousing, HistoryHousingDTO.class);
    }
  }

  @Override
  public HistoryHousingDTO getLatest(HousingDTO housing, int month, int year, Boolean temp) throws HistoryHousingServiceException {
    HistoryHousingEntity historyHousing;
    try {
      historyHousing = historyHousingDAO.getLatest(housing.getId(), month, year);
      // On renvoit rien si on est dans la vraie historisation et que l'historisation du mois en cours a planté
      if (historyHousing == null && !temp) {
        return null;
      }
    } catch (HistoryHousingDAOException e) {
      throw new HistoryHousingServiceException(e.getMessage(), e);
    }
    if (historyHousing == null) {
      HistoryHousingDTO historyHousingDTO = new HistoryHousingDTO();
      historyHousingDTO.setHousingId(housing.getId());
      if (null != housing.getHousingNature()) {
        historyHousingDTO.setNatureOfLocal(housing.getHousingNature().getNatureOfLocal());
      }
      historyHousingDTO.setFirst10SqrMeterPrice(housing.getRoomCategory().getFirst10SqrMeterPrice());
      historyHousingDTO.setNextSqrMeterPrice(housing.getRoomCategory().getNextSqrMeterPrice());
      historyHousingDTO.setRoomCount(housing.getRoomCount());
      historyHousingDTO.setRevisedSurfaceArea(housing.getRevisedSurfaceArea());
      return historyHousingDTO;
    } else {
      return mapper.map(historyHousing, HistoryHousingDTO.class);
    }
  }

  @Override
  public void historizeAllHousings() throws HistoryHousingServiceException {
    try {
      historyHousingDAO.historizeAllHousings();
    } catch (HistoryHousingDAOException e) {
      throw new HistoryHousingServiceException(e.getMessage(), e);
    }

  }

  @Override
  public void updateTemporaries(HousingDTO housingDTO) throws HistoryHousingServiceException {
    try {
      List<HistoryHousingEntity> historyHousingList = historyHousingDAO.getTemporaries(housingDTO.getId());

      for (HistoryHousingEntity historyHousingEntity : historyHousingList) {
        historyHousingEntity.setRevisedSurfaceArea(housingDTO.getRevisedSurfaceArea());
        historyHousingEntity.setRoomCount(housingDTO.getRoomCount());
        historyHousingEntity.setNatureOfLocal(housingDTO.getHousingNature().getNatureOfLocal());
        historyHousingEntity.setFirst10SqrMeterPrice(housingDTO.getRoomCategory().getFirst10SqrMeterPrice());
        historyHousingEntity.setNextSqrMeterPrice(housingDTO.getRoomCategory().getNextSqrMeterPrice());
        historyHousingDAO.update(historyHousingEntity);
      }

    } catch (HistoryHousingDAOException e) {
      throw new HistoryHousingServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void deleteOldHousings(int month, int year) throws HistoryHousingServiceException {
    try {
      historyHousingDAO.deleteOldHousings(month, year);
    } catch (HistoryHousingDAOException e) {
      throw new HistoryHousingServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void deleteAllHistoryHousingOfOneHousing(Long id) throws HistoryHousingServiceException {
    try {
      historyHousingDAO.deleteAllHistoryHousingOfOneHousing(id);
    } catch (HistoryHousingDAOException e) {
      throw new HistoryHousingServiceException(e.getMessage(), e);
    }
  }

  @Override
  protected IHistoryHousingDAO getSpecificIDAO() {
    return historyHousingDAO;
  }

  /**
   * @param historyHousingDAO the historyHousingDAO to set
   */
  public void setHistoryHousingDAO(IHistoryHousingDAO historyHousingDAO) {
    this.historyHousingDAO = historyHousingDAO;
  }

}
