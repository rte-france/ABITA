/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.common.impl;

import com.dao.common.IAbstractDAO;
import com.dao.common.entity.AbstractEntity;
import com.dao.common.exception.GenericDAOException;
import com.dto.AbstractDTO;
import com.services.common.exception.GenericServiceException;
import com.services.common.exception.NotFoundException;
import org.dozer.Mapper;
import org.dozer.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author
 *
 * @param <Entity> Entity class
 * @param <DAOException> DAO exception
 * @param <PK> primary key class
 * @param <ServiceException> service exception class
 * @param <IDAO> IDAO interface
 * @param <DTO> DTO class
 */
public abstract class AbstractService<Entity extends AbstractEntity, DTO extends AbstractDTO, DAOException extends GenericDAOException,
    PK extends Serializable, ServiceException extends GenericServiceException, IDAO extends IAbstractDAO<Entity, DAOException>> {

    /** index de récupération de l'exception de service dans les génériques de la classe de service */
    public static final int SERVICE_EXCEPTION_GENERICS_INDEX = 4;

    /** Mapper dozzer */
	protected Mapper mapper;

	/** logger */
	protected Logger logger = LoggerFactory.getLogger(getSpecificClassName());

	/**
	 * {@inheritDoc}
	 *
	 * @throws ServiceException
	 */
	public Serializable create(DTO o) throws ServiceException {
		try {
			return getSpecificIDAO().create(mapper.map(o, getSpecificEntityClass()));
		} catch (GenericDAOException aenericDaoException) {
			logger.error("Erreur survenue lors de la creation", aenericDaoException);
			throw createException(aenericDaoException);
		} catch (MappingException mappingException) {
			logger.error("Erreur survenue lors du mapping", mappingException);
			throw createException(mappingException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public DTO get(Long id) throws ServiceException {
		try {
			Entity entity = getSpecificIDAO().get(id);
			if (entity == null) {
				final ServiceException exception = this.createException(new NotFoundException(
						"Objet non existant pour l'identifiant : " + id));
				throw exception;
			}
			return mapper.map(entity, getSpecificDTOClass());
		} catch (final GenericDAOException genericDaoException) {
			logger.error("Erreur survenue lors de la recuperation", genericDaoException);
			throw createException(genericDaoException);
		} catch (MappingException mappingException) {
			logger.error("Erreur survenue lors du mapping", mappingException);
			throw createException(mappingException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<DTO> find() throws ServiceException {
		try {
			List<Entity> entites = getSpecificIDAO().find();
			List<DTO> dtos = new ArrayList<DTO>(entites.size());
			for (final Entity entity : entites) {
				dtos.add(mapper.map(entity, getSpecificDTOClass()));
			}
			return dtos;
		} catch (GenericDAOException genericDaoException) {
			logger.error("Erreur survenue lors de la recuperation", genericDaoException);
			throw createException(genericDaoException);
		} catch (MappingException mappingException) {
			logger.error("Erreur survenue lors du mapping", mappingException);
			throw createException(mappingException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void update(DTO o) throws ServiceException {
		try {
			getSpecificIDAO().update(mapper.map(o, getSpecificEntityClass()));
		} catch (GenericDAOException genericDaoException) {
			logger.error("Erreur survenue lors de la mise a jour", genericDaoException);
			throw createException(genericDaoException);
		} catch (MappingException mappingException) {
			logger.error("Erreur survenue lors du mapping", mappingException);
			throw createException(mappingException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveOrUpdate(DTO o) throws ServiceException {
		try {
			getSpecificIDAO().saveOrUpdate(mapper.map(o, getSpecificEntityClass()));
		} catch (GenericDAOException genericDaoException) {
			logger.error("Erreur survenue lors de la mise a jour", genericDaoException);
			throw createException(genericDaoException);
		} catch (MappingException mappingException) {
			logger.error("Erreur survenue lors du mapping", mappingException);
			throw createException(mappingException);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void delete(Long id) throws ServiceException {
		try {
			getSpecificIDAO().delete(id);
		} catch (GenericDAOException genericDaoException) {
			logger.error("Erreur survenue lors de la suppression", genericDaoException);
			throw createException(genericDaoException);
		}
	}

	/**
	 * Return the DAO to use
	 * @return IDAO
	 */
	protected abstract IDAO getSpecificIDAO();

	/**
	 * Return the Entity to map to.
	 *
	 * @return Class<T>
	 */
	@SuppressWarnings("unchecked")
	protected Class<Entity> getSpecificEntityClass() {
		return (Class<Entity>) this.getParameterType(0);
	}

	/**
	 * Renvoie le type du paramètre de la classe à la position spécifiée
	 *
	 * @param position la position du paramètre dans la déclaration
	 *
	 * @return la classe du paramètre
	 */
	protected Class<?> getParameterType(final int position) {
		final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<?>) superclass.getActualTypeArguments()[position];
	}

	/**
	 * Return the DTO to map to.
	 *
	 * @return Class<DTO>
	 */
	@SuppressWarnings("unchecked")
	protected Class<DTO> getSpecificDTOClass() {
		return (Class<DTO>) getParameterType(1);
	}

	/**
	 * Renvoie la classe de l'exception technique du service
	 *
	 * @return l'exception technique du service déclarée
	 */
	@SuppressWarnings("unchecked")
	protected Class<ServiceException> getSpecificServiceException() {
		return (Class<ServiceException>) getParameterType(SERVICE_EXCEPTION_GENERICS_INDEX);
	}

	/**
	 * Return the name of the specific class in use.
	 *
	 * @return String le nom de la classe
	 */
	protected String getSpecificClassName() {
		return this.getClass().getName();
	}

	/**
	 * Return the Exception to create.
	 *
	 * @param cause l'exception a intercepter et encapsuler
	 *
	 * @return ServiceException l'exception du service
	 */
	protected ServiceException createException(final Exception cause) {
		final Constructor<ServiceException> constructor;
		final ServiceException result;
		try {
			constructor = this.getSpecificServiceException().getConstructor(Throwable.class);
			result = constructor.newInstance(cause);
		} catch (final SecurityException securityException) {
			throw new IllegalArgumentException(securityException);
		} catch (final NoSuchMethodException noSuchMethodException) {
			throw new IllegalArgumentException(noSuchMethodException);
		} catch (final InstantiationException instantiationException) {
			throw new IllegalArgumentException(instantiationException);
		} catch (final IllegalAccessException illegalAccessException) {
			throw new IllegalArgumentException(illegalAccessException);
		} catch (final InvocationTargetException invocationTargetException) {
			throw new IllegalArgumentException(invocationTargetException);
		}
		return result;
	}

	/**
	 * @param mapper the mapper to set
	 */
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

}
