package com.abita.services.benefit.impl;

import com.abita.dao.benefit.entity.Benefit;
import com.abita.dao.benefit.entity.SalaryLevelEntity;
import com.abita.dao.benefit.exceptions.BenefitDAOException;
import com.abita.services.benefit.IBenefitService;
import com.abita.services.benefit.exceptions.BenefitServiceException;
import com.abita.util.decorator.SalaryRangeDecorator;
import com.abita.dao.benefit.IBenefitDAO;
import com.abita.dto.BenefitDTO;
import com.abita.dto.SalaryLevelDTO;
import com.abita.util.SalaryRangeList;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'implémentation des barèmes des avantages en nature
 * @author
 * @version 1.0
 */
public class BenefitServiceImpl extends AbstractService<SalaryLevelEntity, SalaryLevelDTO, BenefitDAOException, Long, BenefitServiceException, IBenefitDAO> implements
        IBenefitService {

  /** DAO des barèmes des avantages en nature */
  private IBenefitDAO benefitDAO;

  @Override
  public SalaryRangeList findAllSortedSalaryRange() throws BenefitServiceException {
    List<SalaryLevelDTO> lstDTO = new ArrayList<SalaryLevelDTO>();
    SalaryRangeList lstSalaryRange = new SalaryRangeList();

    try {
      List<SalaryLevelEntity> lstEntity = benefitDAO.findAllSortedSalaryLevel();
      for (SalaryLevelEntity entity : SafetyUtils.emptyIfNull(lstEntity)) {
        lstDTO.add(mapper.map(entity, SalaryLevelDTO.class));
      }
    } catch (BenefitDAOException e) {
      throw new BenefitServiceException(e.getMessage(), e);
    }

    try {
      for (SalaryLevelDTO dto : lstDTO) {
        lstSalaryRange.add(new SalaryRangeDecorator(dto, lstSalaryRange));
      }
    } catch (IllegalArgumentException e) {
      throw new BenefitServiceException(e.getMessage(), e);
    }

    return lstSalaryRange;
  }

  @Override
  public BenefitDTO findBenefitBySalary(BigDecimal salary) throws BenefitServiceException {
    Benefit benefit;
    try {
      benefit = benefitDAO.findBenefitBySalary(salary);
    } catch (BenefitDAOException e) {
      throw new BenefitServiceException(e.getMessage(), e);
    }
    if (benefit == null) {
      throw new BenefitServiceException("Aucun barème pour avantage en nature trouvé pour la rémunération " + salary.toPlainString());
    }

    return mapper.map(benefit, BenefitDTO.class);

  }

  @Override
  public void saveListing(SalaryRangeList salaryRangeList) throws BenefitServiceException {
    List<SalaryLevelDTO> salaryLevelList = new ArrayList<SalaryLevelDTO>();
    for (SalaryRangeDecorator linkedSalary : salaryRangeList) {
      salaryLevelList.add(linkedSalary.getSalaryLevelDTO());
    }
    List<SalaryLevelDTO> lstOriginal = find();

    // Suppression
    for (SalaryLevelDTO salaryLevel : lstOriginal) {
      if (!salaryLevelList.contains(salaryLevel)) {
        logger.debug("Tentative de suppression du barème pour avantage en nautre avec l'id " + salaryLevel.getId());
        delete(salaryLevel.getId());
      }
    }

    // Ajout et modification
    for (SalaryLevelDTO salaryLevel : salaryLevelList) {
      if (lstOriginal.contains(salaryLevel)) {
        logger.debug("Tentative de mise à jour du barème pour avantage en nautre avec l'id " + salaryLevel.getId());
        update(salaryLevel);
      } else {
        logger.debug("Tentative d'ajout du barème pour avantage en nautre avec l'id " + salaryLevel.getId());
        create(salaryLevel);
      }
    }
  }

  @Override
  public Long create(SalaryLevelDTO salaryLevel) throws BenefitServiceException {
    return (Long) super.create(salaryLevel);
  }

  @Override
  protected IBenefitDAO getSpecificIDAO() {
    return benefitDAO;
  }

  /**
   * @param benefitDAO the benefitDAO to set
   */
  public void setBenefitDAO(IBenefitDAO benefitDAO) {
    this.benefitDAO = benefitDAO;
  }

}
