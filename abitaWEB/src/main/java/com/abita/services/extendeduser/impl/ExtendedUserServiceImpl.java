package com.abita.services.extendeduser.impl;

import com.abita.dao.extendeduser.entity.ExtendedUser;
import com.abita.dao.extendeduser.exception.ExtendedUserDAOException;
import com.abita.services.extendeduser.IExtendedUserService;
import com.abita.services.extendeduser.exception.ExtendedUserServiceException;
import com.abita.dao.extendeduser.IExtendedUserDAO;
import com.abita.dto.ExtendedUserDTO;
import com.abita.dto.unpersist.ExtendedUserCriteriaDTO;
import com.abita.util.GenericDozerListWrapper;
import com.dao.common.exception.GenericDAOException;
import com.dao.user.entity.User;
import com.dto.UserDTO;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;
import org.dozer.MappingException;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'implémentation des utilisateurs étendus
 *
 * @author
 */
public class ExtendedUserServiceImpl extends AbstractService<ExtendedUser, ExtendedUserDTO, ExtendedUserDAOException, Long, ExtendedUserServiceException, IExtendedUserDAO>
  implements IExtendedUserService {

  /**
  * Le DAO des utilisateurs étendus
  */
  private IExtendedUserDAO extendedUserDAO;

  @Override
  public Long create(ExtendedUserDTO costcenterDTO) throws ExtendedUserServiceException {
    return (Long) super.create(costcenterDTO);
  }

  @Override
  public void update(ExtendedUserDTO dto, String mapId) throws ExtendedUserServiceException {
    try {
      ExtendedUser extendedUser = getSpecificIDAO().get(dto.getId());
      extendedUser.getAgencies().clear();
      mapper.map(dto, extendedUser, mapId);
      getSpecificIDAO().update(extendedUser);
    } catch (GenericDAOException genericDaoException) {
      logger.error("Erreur survenue lors de la mise a jour", genericDaoException);
      throw createException(genericDaoException);
    } catch (MappingException mappingException) {
      logger.error("Erreur survenue lors du mapping", mappingException);
      throw createException(mappingException);
    }
  }

  @Override
  public List<ExtendedUserDTO> findByCriteria(ExtendedUserCriteriaDTO criteria) throws ExtendedUserServiceException {
    try {
      List<ExtendedUser> lstEntity = extendedUserDAO.findByCriteria(criteria);

      GenericDozerListWrapper<ExtendedUser, ExtendedUserDTO> listMapper = new GenericDozerListWrapper<ExtendedUser, ExtendedUserDTO>(ExtendedUser.class, ExtendedUserDTO.class);

      return listMapper.map(mapper, lstEntity);
    } catch (ExtendedUserDAOException e) {
      throw new ExtendedUserServiceException(e);
    } catch (MappingException e) {
      throw new ExtendedUserServiceException(e);
    }
  }

  @Override
  public List<UserDTO> findUsersThirdPartyContractManager() throws ExtendedUserServiceException {
    List<UserDTO> lstDTO = new ArrayList<UserDTO>();

    try {
      List<User> lstEntity = extendedUserDAO.findUsersThirdPartyContractManager();

      for (User user : SafetyUtils.emptyIfNull(lstEntity)) {
        lstDTO.add(mapper.map(user, UserDTO.class));
      }
    } catch (ExtendedUserDAOException e) {
      throw new ExtendedUserServiceException(e);
    } catch (MappingException e) {
      throw new ExtendedUserServiceException(e);
    }

    return lstDTO;
  }

  @Override
  public ExtendedUserDTO get(Long id) throws ExtendedUserServiceException {
    try {
      ExtendedUser extendedUser = extendedUserDAO.get(id);
      return mapper.map(extendedUser, ExtendedUserDTO.class);
    } catch (ExtendedUserDAOException e) {
      throw new ExtendedUserServiceException(e);
    }
  }

  @Override
  protected IExtendedUserDAO getSpecificIDAO() {
    return extendedUserDAO;
  }

  /**
   * @param extendedUserDAO the extendedUserDAO to set
   */
  public void setExtendedUserDAO(IExtendedUserDAO extendedUserDAO) {
    this.extendedUserDAO = extendedUserDAO;
  }

}
