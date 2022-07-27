/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.paramservice.impl;

import com.dao.domain.IDomainDAO;
import com.dao.domain.entity.Domain;
import com.dao.domain.exception.DomainDAOException;
import com.dto.Group;
import com.dto.parameter.DomainDTO;
import com.dto.parameter.ParameterDTO;
import com.services.access.constants.AccessServiceFacadeConstants;
import com.services.common.exception.NotFoundException;
import com.services.common.util.DozerUtils;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import com.web.common.constants.AccessConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A parameter service.
 * @author
 *
 */
public class ParameterServiceImpl implements ParameterService, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 9171933008078402455L;

	/** logger */
	private static final Log LOG = LogFactory.getLog(com.services.paramservice.impl.ParameterServiceImpl.class);

	/** configuration key */
	static final String CONFIG_KEY = "com.param.CONFIG_KEY";

	/** domains cache */
	private final Map<String, DomainDTO> cache = new ConcurrentHashMap<String, DomainDTO>();

	/** domain DAO */
	private IDomainDAO domainDAO;

	/** Le mappeur Entités <-> DTOs */
	private Mapper mapper;

	/**
	 * seeter of domain dao
	 * @param domainDAO domain DAO
	 */
	public void setDomainDAO(IDomainDAO domainDAO) {
		this.domainDAO = domainDAO;
	}

	/**
	 * Setter de {@link com.services.paramservice.impl.ParameterServiceImpl#mapper}
	 *
	 * @param mapper la nouvelle valeur à affecter
	 */
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<DomainDTO> getDomainList() throws ParameterServiceException {
		try {
			return DozerUtils.map(mapper, domainDAO.find(), DomainDTO.class);
		} catch (final DomainDAOException domainDaoException) {
			LOG.error(domainDaoException);
			throw new ParameterServiceException(domainDaoException);
		}
	}

	@Override
	public String getParameterValue(final String domainKey, final String paramKey) throws ParameterServiceException {
		// On regarde si le domaine est dans le cache
		DomainDTO d = cache.get(domainKey);
		if (d == null) {
			try {
				// récupération du domaine depuis la base
				final Domain domainEntity = domainDAO.findDomainByName(domainKey);
				d = mapper.map(domainEntity, DomainDTO.class);
			} catch (final DomainDAOException paramDaoException) {
				LOG.error(paramDaoException);
				throw new ParameterServiceException(paramDaoException);
			} catch (final NotFoundException notFoundException) {
				LOG.error("Le domaine '" + domainKey + "' n'existe pas");
				return null;
			}
			// mise en cache du domaine
			cache.put(domainKey, d);
		}

		final ParameterDTO p = d.getParameter(paramKey);
		if (p == null) {
			LOG.error("Le paramètre  '" + paramKey + "' du domaine '" + domainKey + "' n'existe pas");
			return null;
		}
		return p.getValue();
	}

	@Override
	public void setParameterValue(final String domain, final String param, final String value)
			throws ParameterServiceException {
		try {
			Domain domainEntity = domainDAO.findDomainByName(domain);
			final DomainDTO domainDTO = mapper.map(domainEntity, DomainDTO.class);

			ParameterDTO p = domainDTO.getParameter(param);
			if (p == null) {
				LOG.error("Le paramètre  '" + param + "' du domaine '" + domain + "' n'existe pas");
			} else {
				p.setValue(value);
				domainEntity = mapper.map(domainDTO, Domain.class);
				domainDAO.update(domainEntity);

				// on met à jour l'entrée dans le cache, si elle existe
                final DomainDTO domainCached = cache.get(domain);
                if (domainCached != null) {
                    domainCached.getParameter(param).setValue(value);
                }
			}
		} catch (final DomainDAOException domainDaoException) {
			LOG.error(domainDaoException);
			throw new ParameterServiceException(domainDaoException);
		} catch (final NotFoundException notFoundException) {
			LOG.error("unexpected error, failed to find domain '" + domain + "'");
		}
	}

	@Override
	public DomainDTO getDomainByName(final String domainName) throws NotFoundException, ParameterServiceException {
		// On regarde si le domaine est en cache
		DomainDTO d = cache.get(domainName);
		if (d == null) {
			try {
				// récupération depuis la base
				Domain domainEntity = domainDAO.findDomainByName(domainName);
				d = mapper.map(domainEntity, DomainDTO.class);
				// mise en cache
				cache.put(domainName, d);
			} catch (final DomainDAOException domainDaoException) {
				LOG.error(domainDaoException);
				throw new ParameterServiceException(domainDaoException);
			}
		}
		return d;
	}

    @Override
    public List<Group> getDefaultGroups() throws ParameterServiceException {
        // Recuperation de la chaine definissant la liste de groupes par defaut
        String defaultGroupsAsString;
        try {
            defaultGroupsAsString = this.getParameterValue(
                    com.services.paramservice.constants.ParamServiceConstants.ACCESS_DOMAIN_KEY,
                    AccessConstants.DEFAULT_GROUP_KEY);
        } catch (final ParameterServiceException parameterServiceException) {
            LOG.error(parameterServiceException.getMessage(), parameterServiceException);
            throw parameterServiceException;
        }

        String[] defaultGroups = null;
        if (defaultGroupsAsString != null) {
            // Transformation de la CdC en un tableau de noms de groupes
            defaultGroups = defaultGroupsAsString.split(AccessServiceFacadeConstants.DEFAULT_GROUPS_SEPARATOR);
        }

        // Recuperation des Groupe en fonction de leur noms
        final List<Group> groups = new ArrayList<Group>(defaultGroups == null ? 0 : defaultGroups.length);
        boolean hasErrors = false;

        if (defaultGroups != null) {
            boolean groupFound = false;
            for (String defaultGroup : defaultGroups) {
                groupFound = false;
                for (Group group : Group.values()) {
                    if (defaultGroup.equals(group.getIdentifier())) {
                        groups.add(group);
                        groupFound = true;
                        break;
                    }
                }
                if (!groupFound) {
                    LOG.error(defaultGroup + " isn't a valid group");
                    hasErrors = true;
                }
            }
        }

        if (hasErrors) {
            throw new ParameterServiceException("At least one group wasn't valid when retrieving default groups");
        }

        return groups;
    }

}
