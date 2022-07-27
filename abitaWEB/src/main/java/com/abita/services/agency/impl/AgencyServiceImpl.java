/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.agency.impl;

import com.abita.dao.agency.entity.AgencyEntity;
import com.abita.dao.agency.exceptions.AgencyDAOException;
import com.abita.services.agency.IAgencyService;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.services.extendeduser.IExtendedUserService;
import com.abita.dao.agency.IAgencyDAO;
import com.abita.dto.AgencyDTO;
import com.abita.dto.ExtendedUserDTO;
import com.abita.services.extendeduser.exception.ExtendedUserServiceException;
import com.dto.Group;
import com.dto.UserDTO;
import com.services.common.impl.AbstractService;
import com.services.common.util.DozerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d’implémentation du service des agences
 */
public class AgencyServiceImpl extends AbstractService<AgencyEntity, AgencyDTO, AgencyDAOException, Long, AgencyServiceException, IAgencyDAO> implements IAgencyService {

  /** Service pour la gestion des utilisateurs */
  private transient IExtendedUserService extendedUserService;

  /** DAO des agences */
  private IAgencyDAO agencyDAO;

  @Override
  public List<AgencyDTO> findAllAgencies(UserDTO user) throws AgencyServiceException {
    try {
      List<AgencyDTO> agencies;

      // les administrateurs ont accès à toutes les agences
      if ((user.getGroups().contains(Group.ADMINISTRATEUR))){
        agencies = findAllAgencies();
      } else {
        ExtendedUserDTO extendedUser = extendedUserService.get(user.getId());
        agencies = extendedUser.getAgencies();
      }

      return agencies;
    } catch (ExtendedUserServiceException e) {
      throw new AgencyServiceException(e.getMessage(), e);
    }
  }

  @Override
  public List<AgencyDTO> findAllAgencies() throws AgencyServiceException {
    try {
      return DozerUtils.map(mapper, agencyDAO.findAllAgencies(), AgencyDTO.class);
    } catch (AgencyDAOException e) {
      throw new AgencyServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void save(List<AgencyDTO> agencies) throws AgencyServiceException {
    try {
      List<AgencyDTO> agenciesOriginal = find();
      List<ExtendedUserDTO> extendedUsers = new ArrayList<ExtendedUserDTO>();
      int numberOfAgencies = agenciesOriginal.size();

      for (ExtendedUserDTO extendedUser : extendedUserService.find()) {
        if (extendedUser.getAgencies().size() == numberOfAgencies) {
          extendedUsers.add(extendedUser);
        }
      }

      // Suppression
      for (AgencyDTO agency : agenciesOriginal) {
        if (!agencies.contains(agency)) {
          delete(agency.getId());
        }
      }

      // Ajout et modification
      for (AgencyDTO agency : agencies) {
        if (agenciesOriginal.contains(agency)) {
          update(agency);
        } else {
          create(agency, extendedUsers);
        }
      }
    } catch (ExtendedUserServiceException e) {
      throw new AgencyServiceException(e.getMessage(), e);
    }
  }

  @Override
  public Long create(AgencyDTO agency, List<ExtendedUserDTO> extendedUsers) throws AgencyServiceException {
    try {
      Long agencyId = (Long) super.create(agency);
      agency.setId(agencyId);

      for (ExtendedUserDTO extendedUser : extendedUsers) {
        extendedUser.getAgencies().add(agency);
        extendedUserService.update(extendedUser, "extendedUserLightUpdate");
      }

      return agencyId;
    } catch (ExtendedUserServiceException e) {
      throw new AgencyServiceException(e.getMessage(), e);
    }
  }

  @Override
  protected IAgencyDAO getSpecificIDAO() {
    return agencyDAO;
  }

  /**
   * @param agencyDAO the agencyDAO to set
   */
  public void setAgencyDAO(IAgencyDAO agencyDAO) {
    this.agencyDAO = agencyDAO;
  }

  /**
   * @param extendedUserService the extendedUserService to set
   */
  public void setExtendedUserService(IExtendedUserService extendedUserService) {
    this.extendedUserService = extendedUserService;
  }

}
