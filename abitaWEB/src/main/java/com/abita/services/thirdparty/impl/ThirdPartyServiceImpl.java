package com.abita.services.thirdparty.impl;

import com.abita.dao.thirdparty.entity.ThirdPartyEntity;
import com.abita.dao.thirdparty.exceptions.ThirdPartyDAOException;
import com.abita.services.thirdparty.IThirdPartyService;
import com.abita.services.thirdparty.exceptions.ThirdPartyServiceException;
import com.abita.dao.thirdparty.IThirdPartyDAO;
import com.abita.dto.ThirdPartyDTO;
import com.abita.dto.unpersist.ThirdPartyCriteriaDTO;
import com.abita.util.GenericDozerListWrapper;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;
import org.dozer.MappingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'implémentation des services des tiers
 * @author
 *
 */
public class ThirdPartyServiceImpl extends AbstractService<ThirdPartyEntity, ThirdPartyDTO, ThirdPartyDAOException, Long, ThirdPartyServiceException, IThirdPartyDAO> implements
        IThirdPartyService {

  /**
  * Le DAO des tiers
  */
  private IThirdPartyDAO thirdPartyDAO;

  @Override
  protected IThirdPartyDAO getSpecificIDAO() {
    return thirdPartyDAO;
  }

  @Override
  public Long create(ThirdPartyDTO thirdPartyDTO) throws ThirdPartyServiceException {
    return (Long) super.create(thirdPartyDTO);
  }

  /**
  * @param thirdPartyDAO the thirdPartyDAO to set
  */
  public void setThirdPartyDAO(IThirdPartyDAO thirdPartyDAO) {
    this.thirdPartyDAO = thirdPartyDAO;
  }

  @Override
  public boolean isThirdPartyExist(String gcpReference) throws ThirdPartyServiceException {
    try {
      return getSpecificIDAO().isThirdPartyExist(gcpReference);
    } catch (ThirdPartyDAOException e) {
      throw new ThirdPartyServiceException(e);
    }
  }

  @Override
  public List<ThirdPartyDTO> findByCriteria(ThirdPartyCriteriaDTO criteria) throws ThirdPartyServiceException {
    try {
      List<ThirdPartyEntity> lstEntity = thirdPartyDAO.findByCriteria(criteria);

      GenericDozerListWrapper<ThirdPartyEntity, ThirdPartyDTO> listMapper = new GenericDozerListWrapper<ThirdPartyEntity, ThirdPartyDTO>(ThirdPartyEntity.class,
        ThirdPartyDTO.class);

      return listMapper.map(mapper, lstEntity);
    } catch (ThirdPartyDAOException e) {
      throw new ThirdPartyServiceException(e);
    } catch (MappingException e) {
      throw new ThirdPartyServiceException(e);
    }
  }

  @Override
  public ThirdPartyDTO getByGCPRef(String thirdPartyToOpenId) throws ThirdPartyServiceException {
    try {
      return mapper.map(thirdPartyDAO.getByGCPRef(thirdPartyToOpenId), ThirdPartyDTO.class);
    } catch (MappingException e) {
      throw new ThirdPartyServiceException(e);
    } catch (ThirdPartyDAOException e) {
      throw new ThirdPartyServiceException(e);
    }
  }

  @Override
  public List<ThirdPartyDTO> findThirdPartyByHousing(long idHousing) throws ThirdPartyServiceException {
    List<ThirdPartyDTO> lstDTO = new ArrayList<ThirdPartyDTO>();

    try {
      List<ThirdPartyEntity> lstEntity = thirdPartyDAO.findThirdPartyByHousing(idHousing);

      for (ThirdPartyEntity thirdparty : SafetyUtils.emptyIfNull(lstEntity)) {
        lstDTO.add(mapper.map(thirdparty, ThirdPartyDTO.class));
      }
    } catch (MappingException e) {
      throw new ThirdPartyServiceException(e);
    } catch (ThirdPartyDAOException e) {
      throw new ThirdPartyServiceException(e);
    }

    return lstDTO;
  }

  @Override
  public boolean isRemovable(long idThirdParty) throws ThirdPartyServiceException {
    try {
      return getSpecificIDAO().isRemovable(idThirdParty);
    } catch (ThirdPartyDAOException e) {
      throw new ThirdPartyServiceException(e);
    }
  }

}
