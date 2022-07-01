package com.abita.dao.airconditioner.impl;

import com.abita.dao.airconditioner.exceptions.AirconditionerDAOException;
import com.abita.dao.airconditioner.IAirconditionerDAO;
import com.abita.dao.airconditioner.entity.AirconditionerEntity;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d’implémentation du DAO des climatisations
 */
public class AirconditionerDAOImpl extends AbstractGenericEntityDAO<AirconditionerEntity, AirconditionerDAOException> implements IAirconditionerDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = 341262198505261116L;

  @Override
  @SuppressWarnings("unchecked")
  public List<AirconditionerEntity> findAllAirconditioners() throws AirconditionerDAOException {
    try {
      List<AirconditionerEntity> airconditioners = new ArrayList<AirconditionerEntity>();

      Query query = getSession().getNamedQuery("findAllAirconditioners");
      List<Object[]> lstResult = query.list();

      for (Object[] result : lstResult) {
        AirconditionerEntity airconditioner = (AirconditionerEntity) result[0];

        airconditioners.add(airconditioner);
      }

      return airconditioners;
    } catch (HibernateException ex) {
      throw new AirconditionerDAOException(ex.getMessage(), ex);
    }
  }
}
