package com.dao.mailreporting;

import com.dao.mailreporting.entity.MailReporting;
import com.dao.mailreporting.exception.MailReportingDAOException;
import com.dto.mail.MailSearchFilterDTO;
import com.services.common.exception.NotFoundException;

import java.util.List;

/**
 * Handle all the operations that can be made on the {@link MailReporting} entity
 *
 * @author
 */
public interface MailReportingDAO {
	/**
	 * Persist and return the entity's generated identifier.
	 * The entity must not be null.
	 *
	 * @param mailReportingEntity The entity to persist
	 *
	 * @return the generated identifier
	 *
	 * @throws MailReportingDAOException If an error occurs
	 */
	Long create(final MailReporting mailReportingEntity) throws MailReportingDAOException;

	/**
	 * Retrieve and return the entity identified with the specified identifier
	 * The identifier must not be null.
	 *
	 * @param id The identifier of the entity to retrieve
	 *
	 * @return The object
	 *
	 * @throws MailReportingDAOException If an error occurs
	 * @throws NotFoundException If the entity was not found
	 */
	MailReporting get(final Long id) throws MailReportingDAOException, NotFoundException;

	/**
	 * Update the entity and returns it's updated state
	 * The entity must not be null.
	 *
	 * @param mailReportingEntity The entity to update
	 *
	 * @return The updated entity
	 *
	 * @throws MailReportingDAOException If an error occurs
	 */
	MailReporting update(final MailReporting mailReportingEntity) throws MailReportingDAOException;

	/**
	 * Delete the specified entity.
	 * The entity or its identifier must not be null.
	 *
	 * @param mailReportingEntity The entity to delete
	 *
	 * @throws MailReportingDAOException If an error occurs
	 */
	void delete(final MailReporting mailReportingEntity) throws MailReportingDAOException;

	/**
	 * Retrieve and return all the entities
	 *
	 * @return the entities as a {@link List}
	 *
	 * @throws MailReportingDAOException If an error occurs
	 */
	List<MailReporting> find() throws MailReportingDAOException;

	/**
	 * Retrieve and return all the entities that match the specified filter.
	 * The filter can be of any type but must be type checked in the implementation.
	 *
	 * If the filter is null, all the entities should be returned.
	 *
	 * @param filter The filter to apply
	 *
	 * @return the filtered entities
	 *
	 * @throws MailReportingDAOException If an error occurs
	 */
	List<MailReporting> find(final MailSearchFilterDTO filter) throws MailReportingDAOException;

	/**
	 * Retrieve all the error messages
	 *
	 * @return the error messages
	 *
	 * @throws MailReportingDAOException If an error occurs
	 */
	List<String> getErrorMessages() throws MailReportingDAOException;

}
