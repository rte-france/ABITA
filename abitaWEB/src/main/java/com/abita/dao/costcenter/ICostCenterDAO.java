/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.costcenter;

import com.abita.dao.costcenter.entity.CostCenterEntity;
import com.abita.dao.costcenter.exceptions.CostCenterDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface des DAO de l'administration des centres coûts
 *
 * @author
 */
public interface ICostCenterDAO extends IAbstractDAO<CostCenterEntity, CostCenterDAOException> {

  /**
   * Permet de récupérer la liste de tous les centres coûts en BDD ainsi que leur nombre d'utilisation
   *
   * @return une liste de tous les centres coûts
   * @throws CostCenterDAOException une CostCenterDAOException
   */
  List<CostCenterEntity> findAllCostCenter() throws CostCenterDAOException;
}
