/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.sanitation.impl;

import com.abita.dao.sanitation.ISanitationDAO;
import com.abita.dao.sanitation.entity.SanitationEntity;
import com.abita.dao.sanitation.exceptions.SanitationDAOException;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d’implémentation du DAO des assainissements
 */
public class SanitationDAOImpl extends AbstractGenericEntityDAO<SanitationEntity, SanitationDAOException> implements ISanitationDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = -2408511600273690201L;

  @Override
  @SuppressWarnings("unchecked")
  public List<SanitationEntity> findAllSanitations() throws SanitationDAOException {
    try {
      List<SanitationEntity> sanitations = new ArrayList<SanitationEntity>();

      Query query = getSession().getNamedQuery("findAllSanitations");
      List<Object[]> lstResult = query.list();

      for (Object[] result : lstResult) {
        SanitationEntity sanitation = (SanitationEntity) result[0];

        sanitations.add(sanitation);
      }

      return sanitations;
    } catch (HibernateException ex) {
      throw new SanitationDAOException(ex.getMessage(), ex);
    }
  }
}
