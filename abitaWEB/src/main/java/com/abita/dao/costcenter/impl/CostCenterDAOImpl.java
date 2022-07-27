/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.costcenter.impl;

import com.abita.dao.costcenter.entity.CostCenterEntity;
import com.abita.dao.costcenter.exceptions.CostCenterDAOException;
import com.abita.dao.costcenter.ICostCenterDAO;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'implémentation des centres coûts
 *
 * @author
 */
public class CostCenterDAOImpl extends AbstractGenericEntityDAO<CostCenterEntity, CostCenterDAOException> implements ICostCenterDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = -7067950291720281513L;

  @Override
  @SuppressWarnings("unchecked")
  public List<CostCenterEntity> findAllCostCenter() throws CostCenterDAOException {
    List<CostCenterEntity> lstCostCenter = new ArrayList<CostCenterEntity>();

    try {
      Query query = getSession().getNamedQuery("findAllCostCenter");
      List<Object[]> lstResult = query.list();

      for (Object[] result : lstResult) {
        CostCenterEntity costcenter = (CostCenterEntity) result[0];

        // Savoir si on peut supprimer le centre coût
        int nb = (Integer) result[1];
        if (nb == 0) {
          costcenter.setRemovable(true);
        }

        lstCostCenter.add(costcenter);
      }
    } catch (HibernateException ex) {
      throw new CostCenterDAOException(ex.getMessage(), ex);
    }

    return lstCostCenter;
  }
}
