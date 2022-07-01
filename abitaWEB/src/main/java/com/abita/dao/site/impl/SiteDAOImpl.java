package com.abita.dao.site.impl;

import com.abita.dao.site.exceptions.SiteDAOException;
import com.abita.dao.site.ISiteDAO;
import com.abita.dao.site.entity.SiteEntity;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe d’implémentation du DAO des sites
 */
public class SiteDAOImpl extends AbstractGenericEntityDAO<SiteEntity, SiteDAOException> implements ISiteDAO {

  private static final long serialVersionUID = 682834639989754660L;
  /**
   * Position du site dans le résultat de la requête
   */
  private static final int INDEX_OF_SITE = 0;
  /**
   * Position du nombre de logements dans le résultat de la requête
   */
  private static final int INDEX_OF_NB_HOUSING = 1;

  @Override
  public List<SiteEntity> findAllSites() throws SiteDAOException {
    try {
      List<SiteEntity> sites = new ArrayList<>();

      Query query = getSession().getNamedQuery("findAllSites");
      List<Object[]> lstResult = query.list();

      for (Object[] result : lstResult) {
        SiteEntity site = (SiteEntity) result[INDEX_OF_SITE];

        // il n'est pas possible de supprimer un site dans le cas où ce
        // dernier est rattaché à un logement
        int nbHousing = (Integer) result[INDEX_OF_NB_HOUSING];
        if (nbHousing == 0) {
          site.setRemovable(true);
        }
        sites.add(site);
      }
      return sites;
    } catch (HibernateException ex) {
      throw new SiteDAOException(ex.getMessage(), ex);
    }
  }

  @Override
  public List<SiteEntity> findSitesByAgency(Long agencyId) throws SiteDAOException {
    try {
      Query query = getSession().getNamedQuery("findSitesByAgency");
      query.setParameter("idAgency", agencyId, Hibernate.LONG);
      return query.list();
    } catch (HibernateException ex) {
      throw new SiteDAOException(ex);
    }
  }
}
