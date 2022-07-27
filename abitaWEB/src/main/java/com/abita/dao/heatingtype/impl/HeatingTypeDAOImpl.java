/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.heatingtype.impl;

import com.abita.dao.heatingtype.entity.HeatingTypeEntity;
import com.abita.dao.heatingtype.exceptions.HeatingTypeDAOException;
import com.abita.dao.heatingtype.IHeatingTypeDAO;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d’implémentation du DAO des agences
 */
public class HeatingTypeDAOImpl extends AbstractGenericEntityDAO<HeatingTypeEntity, HeatingTypeDAOException> implements IHeatingTypeDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = -2236159852499416895L;

  @Override
  @SuppressWarnings("unchecked")
  public List<HeatingTypeEntity> findAllHeatingTypes() throws HeatingTypeDAOException {
    try {
      List<HeatingTypeEntity> sanitations = new ArrayList<HeatingTypeEntity>();

      Query query = getSession().getNamedQuery("findAllHeatingTypes");
      List<Object[]> lstResult = query.list();

      for (Object[] result : lstResult) {
        HeatingTypeEntity sanitation = (HeatingTypeEntity) result[0];

        sanitations.add(sanitation);
      }

      return sanitations;
    } catch (HibernateException ex) {
      throw new HeatingTypeDAOException(ex.getMessage(), ex);
    }
  }
}
