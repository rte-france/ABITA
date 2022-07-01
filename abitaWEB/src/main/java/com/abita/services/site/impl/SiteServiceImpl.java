package com.abita.services.site.impl;

import com.abita.dao.site.entity.SiteEntity;
import com.abita.dao.site.exceptions.SiteDAOException;
import com.abita.services.site.ISiteService;
import com.abita.services.site.exception.SiteServiceException;
import com.abita.dao.site.ISiteDAO;
import com.abita.dto.SiteDTO;
import com.services.common.impl.AbstractService;
import com.services.common.util.DozerUtils;

import java.util.List;

/**
 * Classe d’implémentation du service des sites
 */
public class SiteServiceImpl extends AbstractService<SiteEntity, SiteDTO, SiteDAOException, Long, SiteServiceException, ISiteDAO> implements ISiteService {

  /**
   * DAO des sites
   */
  private ISiteDAO siteDAO;

  @Override
  public List<SiteDTO> findAllSites() throws SiteServiceException {
    try {
      return DozerUtils.map(mapper, siteDAO.findAllSites(), SiteDTO.class);
    } catch (SiteDAOException e) {
      throw new SiteServiceException(e.getMessage(), e);
    }
  }

  @Override
  public List<SiteDTO> findSitesByAgency(Long agencyId) throws SiteServiceException {
    try {
      return DozerUtils.map(mapper, siteDAO.findSitesByAgency(agencyId), SiteDTO.class);
    } catch (SiteDAOException e) {
      throw new SiteServiceException(e.getMessage(), e);
    }
  }

  @Override
  public void save(List<SiteDTO> siteDTOs) throws SiteServiceException {
    List<SiteDTO> sitesOriginal = find();

    // Suppression
    for (SiteDTO site : sitesOriginal) {
      if (!siteDTOs.contains(site)) {
        delete(site.getId());
      }
    }

    // Ajout et modification
    for (SiteDTO siteDTO : siteDTOs) {
      if (sitesOriginal.contains(siteDTO)) {
        update(siteDTO);
      } else {
        create(siteDTO);
      }
    }
  }

  @Override
  public Long create(SiteDTO site) throws SiteServiceException {
    Long siteId = (Long) super.create(site);
    site.setId(siteId);
    return siteId;
  }

  @Override
  protected ISiteDAO getSpecificIDAO() {
    return siteDAO;
  }

  public void setSiteDAO(ISiteDAO siteDao) {
    this.siteDAO = siteDao;
  }

}
