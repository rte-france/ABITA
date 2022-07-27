/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.fieldofactivity.impl;

import com.abita.dao.fieldofactivity.entity.FieldOfActivityEntity;
import com.abita.dao.fieldofactivity.exceptions.FieldOfActivityDAOException;
import com.abita.services.fieldofactivity.IFieldOfActivityService;
import com.abita.dao.fieldofactivity.IFieldOfActivityDAO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.services.fieldofactivity.exceptions.FieldOfActivityServiceException;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'implémentation des domaines d'activité
 *
 * @author
 */
public class FieldOfActivityServiceImpl extends
  AbstractService<FieldOfActivityEntity, FieldOfActivityDTO, FieldOfActivityDAOException, Long, FieldOfActivityServiceException, IFieldOfActivityDAO> implements
        IFieldOfActivityService {

  /**
  * Le DAO des domaines d'activité
  */
  private IFieldOfActivityDAO fieldOfActivityDAO;

  @Override
  public List<FieldOfActivityDTO> findAllFieldOfActivity() throws FieldOfActivityServiceException {
    List<FieldOfActivityDTO> lstFieldOfActivityDTO = new ArrayList<FieldOfActivityDTO>();
    try {
      List<FieldOfActivityEntity> lstFieldOfActivityEntity = fieldOfActivityDAO.findAllFieldOfActivity();
      for (FieldOfActivityEntity fieldOfActivityEntity : SafetyUtils.emptyIfNull(lstFieldOfActivityEntity)) {
        lstFieldOfActivityDTO.add(mapper.map(fieldOfActivityEntity, FieldOfActivityDTO.class));
      }
    } catch (FieldOfActivityDAOException daoEx) {
      throw new FieldOfActivityServiceException(daoEx.getMessage(), daoEx);
    }
    return lstFieldOfActivityDTO;
  }

  @Override
  public List<FieldOfActivityDTO> findFieldOfActivityByAgency(Long idAgency) throws FieldOfActivityServiceException {
    List<FieldOfActivityDTO> lstFieldOfActivityDTO = new ArrayList<FieldOfActivityDTO>();
    try {
      List<FieldOfActivityEntity> lstFieldOfActivityEntity = fieldOfActivityDAO.findFieldOfActivityByAgency(idAgency);
      for (FieldOfActivityEntity fieldOfActivityEntity : SafetyUtils.emptyIfNull(lstFieldOfActivityEntity)) {
        lstFieldOfActivityDTO.add(mapper.map(fieldOfActivityEntity, FieldOfActivityDTO.class));
      }
    } catch (FieldOfActivityDAOException daoEx) {
      throw new FieldOfActivityServiceException(daoEx.getMessage(), daoEx);
    }
    return lstFieldOfActivityDTO;
  }

  @Override
  public void saveListing(List<FieldOfActivityDTO> lstFieldOfActivity) throws FieldOfActivityServiceException {
    List<FieldOfActivityDTO> lstFieldOfActivityOriginal = find();
    try {

      List<FieldOfActivityDTO> lstFieldOfActivityToAddOrModify = new ArrayList<FieldOfActivityDTO>();
      // Suppression et reperage des données modifiées
      for (FieldOfActivityDTO fieldOfActivity : lstFieldOfActivityOriginal) {
        if (!lstFieldOfActivity.contains(fieldOfActivity)) {
          delete(fieldOfActivity.getId());
        } else {
          findFieldOfActivityModified(lstFieldOfActivity, lstFieldOfActivityToAddOrModify, fieldOfActivity);
        }
      }

      for (FieldOfActivityDTO fieldOfActivityUpdated : lstFieldOfActivity) {
        if (!lstFieldOfActivityOriginal.contains(fieldOfActivityUpdated)) {
          lstFieldOfActivityToAddOrModify.add(fieldOfActivityUpdated);
        }
      }

      // Ajout et modification
      for (FieldOfActivityDTO fieldOfActivity : lstFieldOfActivityToAddOrModify) {
        if (lstFieldOfActivityOriginal.contains(fieldOfActivity)) {
          // On MAJ les agences des logements qui ont un contrat tiers avec un domaine d'activité modifié et pas de contrat occupant
          getSpecificIDAO().updateAgencyOfThirdPartyContract(fieldOfActivity.getAgency().getId(), fieldOfActivity.getId());
          // Une fois les contrats tiers MAJ, on MAJ les agences des logements qui ont un contrat occupant avec un domaine d'activité modifié
          getSpecificIDAO().updateAgencyOfContract(fieldOfActivity.getAgency().getId(), fieldOfActivity.getId());
          update(fieldOfActivity);
        } else {
          create(fieldOfActivity);
        }
      }
    } catch (FieldOfActivityDAOException daoEx) {
      throw new FieldOfActivityServiceException(daoEx.getMessage(), daoEx);
    }
  }

  private void findFieldOfActivityModified(List<FieldOfActivityDTO> lstFieldOfActivity, List<FieldOfActivityDTO> lstFieldOfActivityToAddOrModify, FieldOfActivityDTO fieldOfActivity) {

    for (FieldOfActivityDTO fieldOfActivityUpdated : lstFieldOfActivity) {

      if (fieldOfActivityUpdated.getLabel() != null && fieldOfActivityUpdated.getGmr() != null && fieldOfActivityUpdated.getAgency() != null) {
        boolean fieldOfActivityModified = fieldOfActivityUpdated.equals(fieldOfActivity) && (!fieldOfActivityUpdated.getLabel().equals(fieldOfActivity.getLabel())
          || !fieldOfActivityUpdated.getGmr().equals(fieldOfActivity.getGmr())
          || !fieldOfActivityUpdated.getAgency().equals(fieldOfActivity.getAgency()));

        if (fieldOfActivityModified) {
          lstFieldOfActivityToAddOrModify.add(fieldOfActivityUpdated);
        }
      }

    }
  }

  @Override
  public Long create(FieldOfActivityDTO fieldOfActivityDTO) throws FieldOfActivityServiceException {
    return (Long) super.create(fieldOfActivityDTO);
  }

  @Override
  protected IFieldOfActivityDAO getSpecificIDAO() {
    return fieldOfActivityDAO;
  }

  /**
   * @param fieldOfActivityDAO the fieldOfActivityDAO to set
   */
  public void setFieldOfActivityDAO(IFieldOfActivityDAO fieldOfActivityDAO) {
    this.fieldOfActivityDAO = fieldOfActivityDAO;
  }

}
