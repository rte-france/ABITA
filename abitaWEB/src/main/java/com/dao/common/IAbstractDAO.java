package com.dao.common;

import com.dao.common.entity.AbstractEntity;
import com.dao.common.exception.GenericDAOException;

import java.io.Serializable;
import java.util.List;

/**
 * Interface Abstract Entity DAO
 * @author
 *
 * @param <T> Entity Type
 * @param <E> Exception Type
 */
public interface IAbstractDAO<T extends AbstractEntity, E extends GenericDAOException> extends Serializable {

	/**
	 * Persist the newInstance object into database
	 *
	 * @param newInstance T
	 * @return Serializable
	 * @throws E the corresponding exception
	 */
	Serializable create(T newInstance) throws E;

	/**
	 * Retrieve an object that was previously persisted to the database using
	 * the indicated id as primary key.
	 *
	 * @param id Serializable
	 * @return T
	 * @throws E the corresponding exception
	 */
	T get(Serializable id) throws E;

	/**
	 * Save changes made to a persistent object.
	 *
	 * @param transientObject T
	 * @return transientObject
	 * @throws E the corresponding exception
	 */
	T update(T transientObject) throws E;

	/**
     * Updates a persistent object by merging it.
     * @param transientObject T
     * @return transientObject
     * @throws E the corresponding exception
     */
    T merge(T transientObject) throws E;

	/**
	 * Save or update changes made to a persistent object.
	 *
	 * @param transientObject T
	 * @throws E the corresponding exception
	 */
	void saveOrUpdate(T transientObject) throws E;

	/**
	 * Remove an object from persistent storage in the database
	 *
	 * @param id the id of the object to delete T
	 * @throws E the corresponding exception
	 */
	void delete(Serializable id) throws E;

	/**
	 * Retrieve all the objects that was previously persisted to the database
	 * using the type
	 *
	 * @return List<M> the entities
	 *
	 * @throws E the corresponding exception
	 */
	List<T> find() throws E;

	/**
	 *
	 * @param start first row to retrieve
	 * @param range number max of results
	 *
	 * @return List<T> the entities
	 *
	 * @throws E the corresponding exception
	 */
	List<T> getListSortedBy(int start, int range) throws E;

	/**
	 *
	 * @param start first row to retrieve
	 * @param range number max of results
	 * @param sortProperty The property sorted by
	 * @param ascending type of sort (ascending or descending)
	 *
	 * @return List<T> the entities
	 *
	 * @throws E the corresponding exception
	 */
	List<T> getListSortedBy(int start, int range, String sortProperty, boolean ascending) throws E;

	/**
	 * return the number of entities
	 *
	 * @return number of entities
	 *
	 * @throws E the corresponding exception
	 */
	int getCount() throws E;
}
