/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.benefit.impl;

import com.abita.dao.benefit.IBenefitDAO;
import com.abita.dao.benefit.contants.BenefitConstants;
import com.abita.dao.benefit.entity.Benefit;
import com.abita.dao.benefit.entity.SalaryLevelEntity;
import com.abita.dao.benefit.exceptions.BenefitDAOException;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe d'implémentation des barèmes pour le calcul des avantages en nature
 * @author
 * @version 1.0
 */
public class BenefitDAOImpl extends AbstractGenericEntityDAO<SalaryLevelEntity, BenefitDAOException> implements IBenefitDAO {

  /** SerialID */
  private static final long serialVersionUID = -3255347302420699676L;

  @SuppressWarnings("unchecked")
  @Override
  public List<SalaryLevelEntity> findAllSortedSalaryLevel() throws BenefitDAOException {
    try {
      Query query = getSession().createQuery(BenefitConstants.FIND_ALL_SORTED_BENEFIT);
      return query.list();
    } catch (HibernateException e) {
      throw new BenefitDAOException(e.getMessage(), e);
    }

  }

  @Override
  public Benefit findBenefitBySalary(BigDecimal salary) throws BenefitDAOException {
    try {
      Query query = getSession().createQuery(BenefitConstants.FIND_BENEFIT_BY_SALARY);
      query.setParameter("salaryValue", salary, Hibernate.BIG_DECIMAL);
      query.setMaxResults(1);
      @SuppressWarnings("unchecked")
      List<SalaryLevelEntity> resultList = query.list();
      if (resultList == null || resultList.isEmpty()) {
        return null;
      }
      return resultList.get(0).getBenefit();
    } catch (HibernateException e) {
      throw new BenefitDAOException(e.getMessage(), e);
    }
  }
}
