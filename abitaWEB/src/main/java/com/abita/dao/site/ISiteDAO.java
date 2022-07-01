package com.abita.dao.site;

import com.abita.dao.site.entity.SiteEntity;
import com.abita.dao.site.exceptions.SiteDAOException;
import com.dao.common.IAbstractDAO;

import java.util.List;

/**
 * Interface du DAO des sites
 */
public interface ISiteDAO extends IAbstractDAO<SiteEntity, SiteDAOException> {

  /**
   * Permet de récupérer la liste de tous les sites ainsi que leur capacité à être supprimé ou non
   *
   * @return une liste de tous les sites
   * @throws SiteDAOException une SanitationDAOException
   */
  List<SiteEntity> findAllSites() throws SiteDAOException;

  /**
   * Permet de récupérer la liste des sites en fonction de l'agence choisie par l'utilisateur
   * @param agencyId l'id de l'agence choisie
   * @return la liste des sites rattachés à l'agence choisie
   * @throws SiteDAOException en cas d'exception hibernate
   */
  List<SiteEntity> findSitesByAgency(Long agencyId) throws SiteDAOException;
}
