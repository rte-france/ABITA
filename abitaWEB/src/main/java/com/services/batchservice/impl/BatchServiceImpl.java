/**
 *
 */
package com.services.batchservice.impl;

import com.dao.batch.IBatchDAO;
import com.dao.batch.entity.BatchHistorique;
import com.dao.batch.exception.BatchDAOException;
import com.dto.batch.BatchHistoriqueDTO;
import com.services.batchservice.BatchService;
import com.services.batchservice.exception.BatchServiceException;
import com.services.common.util.DozerUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dozer.Mapper;

import java.util.Date;
import java.util.List;

/**
 * @author
 *
 */
public class BatchServiceImpl implements BatchService {

	/** The batch dao. */
	private IBatchDAO batchDAO;

	/** The Constant LOG. */
	private static final Log LOG = LogFactory.getLog(com.services.batchservice.impl.BatchServiceImpl.class);

	/** Le mapper Entités <-> DTOs*/
	private Mapper mapper;

	/**
	 * @param batchDAO the batchDAO to set
	 */
	public void setBatchDAO(final IBatchDAO batchDAO) {
		this.batchDAO = batchDAO;
	}

	/**
	 * @param mapper the mapper to set
	 */
	public void setMapper(final Mapper mapper) {
		this.mapper = mapper;
	}

	/**
	 * {@inheritDoc}
	 * @see BatchService#findLastBatchHistorique(String)
	 */
	@Override
	public List<BatchHistoriqueDTO> findLastBatchHistorique(final String batchName) throws BatchServiceException {
		LOG.debug("Recherche de l'historique du batch :" + batchName);
		try {
			final List<BatchHistorique> batchs = this.batchDAO.findBatchHistoriqueByName(batchName, 1);
			return DozerUtils.map(this.mapper, batchs, BatchHistoriqueDTO.class);
		} catch (BatchDAOException batchDaoException) {
			throw new BatchServiceException(batchDaoException);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see BatchService#findBatchHistorique(String, int)
	 */
	@Override
	public List<BatchHistoriqueDTO> findBatchHistorique(final String batchName, final int nbHisto) throws BatchServiceException {
		LOG.debug("Recherche de " + nbHisto + " historiques du batch :" + batchName);
		try {
			final List<BatchHistorique> batchs = this.batchDAO.findBatchHistoriqueByName(batchName, nbHisto);
			return DozerUtils.map(this.mapper, batchs, BatchHistoriqueDTO.class);
		} catch (BatchDAOException batchDaoException) {
			throw new BatchServiceException(batchDaoException);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see BatchService#createBatch(BatchHistoriqueDTO)
	 */
	@Override
	public BatchHistoriqueDTO createBatch(final BatchHistoriqueDTO batch) throws BatchServiceException {
		LOG.debug("creation de  batch " + batch.getName());
		try {
			final BatchHistorique batchEntity = this.mapper.map(batch, BatchHistorique.class);
			final Long id = (Long) this.batchDAO.create(batchEntity);
			batch.setId(id);
			return batch;
		} catch (BatchDAOException batchDaoException) {
			throw new BatchServiceException(batchDaoException);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see BatchService#updateBatch(BatchHistoriqueDTO)
	 */
	@Override
	public BatchHistoriqueDTO updateBatch(final BatchHistoriqueDTO batch) throws BatchServiceException {
		try {
			BatchHistorique batchEntity = this.mapper.map(batch, BatchHistorique.class);
			batchEntity = this.batchDAO.update(batchEntity);
			return this.mapper.map(batchEntity, BatchHistoriqueDTO.class);
		} catch (BatchDAOException batchDaoException) {
			throw new BatchServiceException(batchDaoException);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see BatchService#findBatchInProgress(String, Date)
	 */
	@Override
	public List<BatchHistoriqueDTO> findBatchInProgress(final String batchName, final Date minDate) throws BatchServiceException {
		try {
			final List<BatchHistorique> batchs = this.batchDAO.findBatchInProgress(batchName, minDate);
			return DozerUtils.map(this.mapper, batchs, BatchHistoriqueDTO.class);
		} catch (BatchDAOException batchDaoException) {
			throw new BatchServiceException(batchDaoException);
		}
	}
}
