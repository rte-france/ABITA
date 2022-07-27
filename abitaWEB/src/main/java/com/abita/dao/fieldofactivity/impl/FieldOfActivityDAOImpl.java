/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.fieldofactivity.impl;

import com.abita.dao.fieldofactivity.exceptions.FieldOfActivityDAOException;
import com.abita.dao.fieldofactivity.IFieldOfActivityDAO;
import com.abita.dao.fieldofactivity.entity.FieldOfActivityEntity;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'implémentation des domaines d'activité
 *
 * @author
 */
public class FieldOfActivityDAOImpl extends AbstractGenericEntityDAO<FieldOfActivityEntity, FieldOfActivityDAOException> implements IFieldOfActivityDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = 3050807829958922027L;

  /** Position du nombre de contrats dans le résultat de la requête */
  private static final int CONTRACT_RESULT_POSITION = 2;

  @Override
  @SuppressWarnings("unchecked")
  public List<FieldOfActivityEntity> findAllFieldOfActivity() throws FieldOfActivityDAOException {
    List<FieldOfActivityEntity> lstFieldOfActivity = new ArrayList<FieldOfActivityEntity>();

    try {
      Query query = getSession().getNamedQuery("findAllFieldOfActivity");
      List<Object[]> lstResult = query.list();

      for (Object[] result : lstResult) {
        FieldOfActivityEntity entity = (FieldOfActivityEntity) result[0];

        // Savoir si on peut supprimer le domaine d'activité
        int nbThirdPartyContract = (Integer) result[1];
        int nbContract = (Integer) result[CONTRACT_RESULT_POSITION];
        if (nbThirdPartyContract == 0 && nbContract == 0) {
          entity.setRemovable(true);
        }

        lstFieldOfActivity.add(entity);
      }
    } catch (HibernateException ex) {
      throw new FieldOfActivityDAOException(ex.getMessage(), ex);
    }

    return lstFieldOfActivity;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<FieldOfActivityEntity> findFieldOfActivityByAgency(Long idAgency) throws FieldOfActivityDAOException {
    Query query = getSession().getNamedQuery("findFieldOfActivityByAgency");
    query.setParameter("idAgency", idAgency, Hibernate.LONG);
    return query.list();
  }

  @Override
  public void updateAgencyOfContract(Long idAgency, Long idFieldOfActivity) throws FieldOfActivityDAOException {
    Query queryContract = getSession().getNamedQuery("updateHousingAgencyForContract");
    queryContract.setParameter("idAgency", idAgency, Hibernate.LONG);
    queryContract.setParameter("idFieldOfActivity", idFieldOfActivity, Hibernate.LONG);
    queryContract.executeUpdate();
  }

  @Override
  public void updateAgencyOfThirdPartyContract(Long idAgency, Long idFieldOfActivity) throws FieldOfActivityDAOException {
    Query queryThirdPartyContract = getSession().getNamedQuery("updateHousingAgencyForThirdPartyContract");
    queryThirdPartyContract.setParameter("idAgency", idAgency, Hibernate.LONG);
    queryThirdPartyContract.setParameter("idFieldOfActivity", idFieldOfActivity, Hibernate.LONG);
    queryThirdPartyContract.executeUpdate();
  }
}
