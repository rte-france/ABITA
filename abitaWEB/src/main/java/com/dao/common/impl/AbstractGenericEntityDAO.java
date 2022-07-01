package com.dao.common.impl;

import com.dao.common.IAbstractDAO;
import com.dao.common.entity.AbstractEntity;
import com.dao.common.exception.GenericDAOException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.InstantiationException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 * Generic DAO for automatic mapping from entity objects to the data base. No dozer mapping is done in this class.
 *
 * @author
 *
 * @param <M> entity object with the information
 * @param <E> specific DAO exception
 */
public abstract class AbstractGenericEntityDAO<M extends AbstractEntity, E extends GenericDAOException> implements
		IAbstractDAO<M, E> {

	/** Log */
	protected final Logger log = LoggerFactory.getLogger(AbstractGenericEntityDAO.class);

	/**
	 * Factory of DAO.
	 */
	private SessionFactory sessionFactory;

	/**
	 * {@inheritDoc}
	 *
	 * @throws E
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Serializable create(M o) throws E {
		try {
			return getSession().save(o);
		} catch (HibernateException hibernateException) {
			log.info("Erreur Hibernate " + hibernateException);
			E hEx = createException(hibernateException);
			throw hEx;
		} catch (Exception e) {
			log.error("Erreur inattendue : " + e);
			E eEx = createException(e);
			throw eEx;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public M get(Serializable id) throws E {
		try {
			return (M) getSession().get(getSpecificEntityClass(), id);
		} catch (HibernateException hibernateException) {
			log.info("Erreur Hibernate " + hibernateException);
			E hEx = createException(hibernateException);
			throw hEx;
		} catch (Exception e) {
			log.error("Erreur inattendue : " + e);
			E eEx = createException(e);
			throw eEx;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<M> find() throws E {
		try {
			List<M> listRetour = new ArrayList<M>();
			Criteria crit = getSession().createCriteria(getSpecificEntityClass());
			@SuppressWarnings("unchecked")
			List<M> list = crit.list();
			listRetour.addAll(list);
			return listRetour;
		} catch (HibernateException hibernateException) {
			log.info("Erreur Hibernate " + hibernateException);
			E hEx = createException(hibernateException);
			throw hEx;
		} catch (Exception e) {
			log.error("Erreur inattendue : " + e);
			E eEx = createException(e);
			throw eEx;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public M update(M o) throws E {
		try {
			// If another object with the same id exists in the session it is evicted to
			// avoid an Hibernate double object exception
			getSession().evict(get(o.getId()));
			getSession().update(o);
			return o;
		} catch (HibernateException hibernateException) {
			log.info("Erreur Hibernate " + hibernateException);
			E hEx = createException(hibernateException);
			throw hEx;
		} catch (Exception e) {
			log.error("Erreur inattendue : " + e);
			E eEx = createException(e);
			throw eEx;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public M merge(M o) throws E {
		try {
			return (M) getSession().merge(o);
		} catch (HibernateException hibernateException) {
			log.error("Hibernate error during updating group", hibernateException);
			E eEx = createException(hibernateException);
			throw eEx;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void saveOrUpdate(M o) throws E {
		try {
			// If another object with the same id exists in the session it is evicted to
			// avoid an Hibernate double object exception
			if (o.getId() != null) {
				getSession().evict(get(o.getId()));
			}
			getSession().saveOrUpdate(o);
		} catch (HibernateException hibernateException) {
			log.info("Erreur Hibernate " + hibernateException);
			E hEx = createException(hibernateException);
			throw hEx;
		} catch (Exception e) {
			log.error("Erreur inattendue : " + e);
			E eEx = createException(e);
			throw eEx;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(Serializable id) throws E {
		try {
			M objectToDelete = get(id);
			if (objectToDelete != null) {
				getSession().delete(objectToDelete);
			}

		} catch (HibernateException hibernateException) {
			log.info("Erreur Hibernate " + hibernateException);
			E hEx = createException(hibernateException);
			throw hEx;
		} catch (Exception e) {
			log.error("Erreur inattendue : " + e);
			E eEx = createException(e);
			throw eEx;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<M> getListSortedBy(int start, int range) throws E {
		return getListSortedBy(start, range, null, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<M> getListSortedBy(int start, int range, String sortProperty, boolean ascending) throws E {

		// Récupération du nom de l'entité
		String entityName = getSpecificEntityClass().getSimpleName();

		// Construction de l'id de la namedQuery
		StringBuffer namedQuery = new StringBuffer();
		namedQuery.append("getAll");
		namedQuery.append(StringUtils.capitalize(entityName));

		if (sortProperty != null) {
			namedQuery.append("SortedBy");
			namedQuery.append(StringUtils.capitalize(sortProperty));
			if (ascending) {
				namedQuery.append("Ascending");
			} else {
				namedQuery.append("Descending");
			}
		}

		// Appel Hibernate avec namedQuery
		try {
			Session s = sessionFactory.getCurrentSession();
			Query query = s.getNamedQuery(namedQuery.toString());
			query.setFirstResult(start);
			query.setMaxResults(range);
			return query.list();
		} catch (HibernateException hibernateException) {
			log.error("Erreur inattendue : " + hibernateException);
			E eEx = createException(hibernateException);
			throw eEx;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCount() throws E {

		// Récupéraion du nom de l'entité
		String entityName = getSpecificEntityClass().getSimpleName();

		// Construction de l'id de la namedQuery
		StringBuffer namedQuery = new StringBuffer();
		namedQuery.append("get");
		namedQuery.append(entityName);
		namedQuery.append("Count");

		// Appel Hibernate avec namedQuery
		try {
			Session s = sessionFactory.getCurrentSession();
			Query query = s.getNamedQuery(namedQuery.toString());
			return ((Long) query.uniqueResult()).intValue();
		} catch (HibernateException hibernateException) {
			log.error("Erreur inattendue : " + hibernateException);
			E eEx = createException(hibernateException);
			throw eEx;
		}
	}

	/**
	 * Injection of session Factory.
	 *
	 * @param sessionFactory the session factory to use.
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * Return the Session
	 *
	 * @return Session
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * @return M
	 */
	public Class<M> m() {
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<M>) superclass.getActualTypeArguments()[0];
	}

	/**
	 * @return E
	 */
	public Class<E> e() {
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<E>) superclass.getActualTypeArguments()[1];
	}

	/**
	 * Return the name of the specific class in use.
	 *
	 * @return String
	 */
	protected String getSpecificClassName() {
		return m().getName();
	}

	/**
	 * Return the Name of the specific Exception class in use.
	 *
	 * @return Class<E>
	 */
	protected Class<E> getSpecificExceptionClass() {
		return e();
	}

	/**
	 * Return the Entity to map to.
	 *
	 * @return Class<T>
	 */
	protected Class<M> getSpecificEntityClass() {
		return m();
	}

	/**
	 * Return the Exception to create.
	 *
	 * @param e
	 *            Exception to catch
	 * @return E
	 */
	protected E createException(Exception e) {
		Constructor<E> constructor;
		E result;
		try {
			constructor = e().getConstructor(Exception.class);
			result = constructor.newInstance(e);
		} catch (SecurityException securityException) {
			throw new IllegalArgumentException(securityException);
		} catch (NoSuchMethodException noSuchMethodException) {
			throw new IllegalArgumentException(noSuchMethodException);
		} catch (InstantiationException instantiationException) {
			throw new IllegalArgumentException(instantiationException);
		} catch (IllegalAccessException illegalAccessException) {
			throw new IllegalArgumentException(illegalAccessException);
		} catch (InvocationTargetException invocationTargetException) {
			throw new IllegalArgumentException(invocationTargetException);
		}
		return result;
	}

	/**
	 * Return the Exception to create.
	 *
	 * @return E
	 */
	protected E createException() {
		E result;
		try {
			result = e().newInstance();
		} catch (InstantiationException instantiationException) {
			throw new IllegalArgumentException(instantiationException);
		} catch (IllegalAccessException illegalAccessException) {
			throw new IllegalArgumentException(illegalAccessException);
		}
		return result;
	}

}
