package com.abita.dao.agency.impl;

import com.abita.dao.agency.exceptions.AgencyDAOException;
import com.abita.dao.agency.IAgencyDAO;
import com.abita.dao.agency.entity.AgencyEntity;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d’implémentation du DAO des agences
 */
public class AgencyDAOImpl extends AbstractGenericEntityDAO<AgencyEntity, AgencyDAOException> implements IAgencyDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = -7067950291720281513L;

  /** Position du nombre de domaine d'activité dans le résultat de la requête */
  private static final int FILED_OF_ACTIVITY_RESULT_POSITION = 2;

  @Override
  @SuppressWarnings("unchecked")
  public List<AgencyEntity> findAllAgencies() throws AgencyDAOException {
    try {
      List<AgencyEntity> agencies = new ArrayList<AgencyEntity>();

      Query query = getSession().getNamedQuery("findAllAgencies");
      List<Object[]> lstResult = query.list();

      for (Object[] result : lstResult) {
        AgencyEntity agency = (AgencyEntity) result[0];

        // Savoir si on peut supprimer l’agence
        int nbHousing = (Integer) result[1];
        int nbFieldOfActivity = (Integer) result[FILED_OF_ACTIVITY_RESULT_POSITION];
        if (nbHousing == 0 && nbFieldOfActivity == 0) {
          agency.setRemovable(true);
        }

        agencies.add(agency);
      }

      return agencies;
    } catch (HibernateException ex) {
      throw new AgencyDAOException(ex.getMessage(), ex);
    }
  }
}
