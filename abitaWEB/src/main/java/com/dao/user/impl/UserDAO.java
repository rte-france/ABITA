package com.dao.user.impl;

import com.dao.common.impl.AbstractGenericEntityDAO;
import com.dao.theme.entity.Theme;
import com.dao.user.IUserDAO;
import com.dao.user.entity.User;
import com.dao.user.exception.UserDAOException;
import com.dto.Group;
import com.dto.ThemeDTO;
import com.dto.UsersSearchDTO;
import com.services.common.util.SafetyUtils;
import com.services.common.util.StringUtils;
import com.services.user.exception.UserNotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link IUserDAO}
 *
 * @author
 */
public class UserDAO extends AbstractGenericEntityDAO<User, UserDAOException> implements IUserDAO {

	/** serialVersionUID */
	private static final long serialVersionUID = 1428441091109474279L;

	/** logger */
	private static final Log LOG = LogFactory.getLog(UserDAO.class);

	/** IMPOSSIBLELOGIN */
	private static final String IMPOSSIBLELOGIN = "__";

	/** FILTRED_LOGIN */
	private static final String FILTRED_LOGIN = "filtredLogin";

	/** FIND_USER_BY_LOGIN */
	private static final String FIND_USER_BY_LOGIN = "findByLogin";

	/** FIND_USER_BY_LOGICAL_ID */
	private static final String FIND_USER_BY_LOGICAL_ID = "findByLogicalId";

	/** GET_USER_COUNT */
	private static final String GET_USER_COUNT = "getUserCount";

	/** LOGIN */
	private static final String LOGIN = "login";

	/** LOGICAL_ID */
	private static final String LOGICAL_ID = "logicalId";

	/**
	 * {@inheritDoc}
	 * @see IUserDAO#getCount(List)
	 */
	@Override
	public int getCount(List<String> filtredLogin) throws UserDAOException {
		try {
			if (filtredLogin == null || filtredLogin.isEmpty()) {
				filtredLogin = new ArrayList<String>(1);
			}
			final Query query = getSession().getNamedQuery(GET_USER_COUNT);
			filtredLogin.add(IMPOSSIBLELOGIN);
			query.setParameterList(FILTRED_LOGIN, filtredLogin);

			return ((Long) query.uniqueResult()).intValue();
		} catch (final HibernateException hibernateException) {
			LOG.error("Hibernate error during user count", hibernateException);
			throw new UserDAOException(hibernateException);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see IUserDAO#findByLogin(String)
	 */
	@Override
	public User findByLogin(final String login) throws UserDAOException, UserNotFoundException {
		try {
			final Query query = getSession().getNamedQuery(FIND_USER_BY_LOGIN);
			query.setParameter(LOGIN, login, Hibernate.STRING);
			final User user = (User) query.uniqueResult();
			if (user == null) {
				throw new UserNotFoundException("user with login '" + login + "' is not found");
			}
			user.setGroupsAsList(deserializeGroups(user));
			return user;
		} catch (final HibernateException hibernateException) {
			LOG.error("Hibernate error during user search", hibernateException);
			throw new UserDAOException(hibernateException);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see IUserDAO#findByLogicalId(String)
	 */
	@Override
	public User findByLogicalId(final String logicalId) throws UserDAOException, UserNotFoundException {
		try {
			final Query query = getSession().getNamedQuery(FIND_USER_BY_LOGICAL_ID);
			query.setParameter(LOGICAL_ID, logicalId, Hibernate.STRING);
			final User user = (User) query.uniqueResult();
			if (user == null) {
				throw new UserNotFoundException("user with logicalId '" + logicalId + "' is not found");
			}
			user.setGroupsAsList(deserializeGroups(user));
			return user;
		} catch (final HibernateException hibernateException) {
			LOG.error("Hibernate error during user search", hibernateException);
			throw new UserDAOException(hibernateException);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see IUserDAO#delete(Serializable)
	 */
	@Override
	public void delete(Serializable id) throws UserDAOException {
		try {
			final User user = (User) getSession().get(User.class, id);
			if (user != null) {
				getSession().delete(user);
			} else {
				throw new UserDAOException("user with id '" + id + "' is not found");
			}
			getSession().flush();
		} catch (final HibernateException hibernateException) {
			LOG.error("Hibernate error during user removing by PK", hibernateException);
			throw new UserDAOException(hibernateException);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see IUserDAO#getListSortedBy(int, int, String, boolean, List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getListSortedBy(final int start, final int range, final String sortProperty,
			final boolean ascending, List<String> filtredLogin) throws UserDAOException {

		if (filtredLogin == null) {
			filtredLogin = new ArrayList<String>(1);
		}
		filtredLogin.add(IMPOSSIBLELOGIN);
		// Récupération du nom de l'entité
		final String entityName = getSpecificEntityClass().getSimpleName();

		// Construction de l'id de la namedQuery
		final StringBuffer namedQuery = new StringBuffer();
		namedQuery.append("getAll");
		namedQuery.append(org.apache.commons.lang.StringUtils.capitalize(entityName));

		if (sortProperty != null) {
			namedQuery.append("SortedBy");
			namedQuery.append(org.apache.commons.lang.StringUtils.capitalize(sortProperty));
			if (ascending) {
				namedQuery.append("Ascending");
			} else {
				namedQuery.append("Descending");
			}
		}

		// Appel Hibernate avec namedQuery
		try {
			final Query query = getSession().getNamedQuery(namedQuery.toString());
			query.setFirstResult(start);
			query.setMaxResults(range);
			query.setParameterList(FILTRED_LOGIN, filtredLogin);
			List<User> users = query.list();
			for (User user : SafetyUtils.emptyIfNull(users)) {
				user.setGroupsAsList(deserializeGroups(user));
			}
			return users;
		} catch (final HibernateException hibernateException) {
			log.error("Erreur inattendue : " + hibernateException);
			final UserDAOException eEx = this.createException(hibernateException);
			throw eEx;
		}
	}

	/**
	 * {@inheritDoc}
	 * @see IUserDAO#updateImmediately(User)
	 */
	@Override
	public void updateImmediately(final User user) throws UserDAOException {
		try {
			if (user != null) {
				user.setGroups(serializeGroups(user));
			}
			final Session session = this.getSession();
			final User oldEntity = (User) session.get(this.getSpecificEntityClass(), user.getId());
			if (oldEntity != null) {
				session.evict(oldEntity);
			}
			session.update(user);
			session.flush();
		} catch (final HibernateException hibernateException) {
			LOG.error("Hibernate error during user updating", hibernateException);
			throw new UserDAOException(hibernateException);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see IUserDAO#getUsersList(UsersSearchDTO)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsersList(final UsersSearchDTO usersSearchDTO) throws UserDAOException {

		try {
			Criteria crit = getSession().createCriteria(User.class);

			// NNI
			if (!StringUtils.isEmptyOrBlank(usersSearchDTO.getNni())) {
				crit = crit.add(Restrictions.like("login", usersSearchDTO.getNni()));
			}

			// FirstName
			if (!StringUtils.isEmptyOrBlank(usersSearchDTO.getFirstName())) {
				crit = crit.add(Restrictions.like("firstName", usersSearchDTO.getFirstName()));
			}

			// lastName
			if (!StringUtils.isEmptyOrBlank(usersSearchDTO.getName())) {
				crit = crit.add(Restrictions.like("lastName", usersSearchDTO.getName()));
			}

			// email
			if (!StringUtils.isEmptyOrBlank(usersSearchDTO.getEmail())) {
				crit = crit.add(Restrictions.like("email", usersSearchDTO.getEmail()));
			}

			// phone
			if (!StringUtils.isEmptyOrBlank(usersSearchDTO.getPhone())) {
				crit = crit.add(Restrictions.like("phone", usersSearchDTO.getPhone()));
			}

			// email
			if (!StringUtils.isEmptyOrBlank(usersSearchDTO.getFax())) {
				crit = crit.add(Restrictions.like("fax", usersSearchDTO.getFax()));
			}

			// location
			if (!StringUtils.isEmptyOrBlank(usersSearchDTO.getLocation())) {
				crit = crit.add(Restrictions.like("location", usersSearchDTO.getLocation()));
			}

			// location
			if (null != usersSearchDTO.isActivated()) {
				crit = crit.add(Restrictions.like("activated", usersSearchDTO.isActivated()));
			}

			// Groups
			if (usersSearchDTO.getGroupsSelected() != null && !usersSearchDTO.getGroupsSelected().isEmpty()) {
				Disjunction orConditions = Restrictions.disjunction();
				for (String group : SafetyUtils.emptyIfNull(usersSearchDTO.getGroupsSelected())) {
					orConditions.add(Restrictions.like("groups", "%".concat(group).concat("%")));
				}
				crit = crit.add(orConditions);
			}

			crit = crit.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			final List<User> users = crit.list();
			for (User user : SafetyUtils.emptyIfNull(users)) {
				user.setGroupsAsList(deserializeGroups(user));
			}
			return users;
		} catch (final HibernateException hibernateException) {
			LOG.error("Hibernate error during search users by criteria", hibernateException);
			throw new UserDAOException(hibernateException);
		}

	}

	/**
	 * {@inheritDoc}
	 * @see IUserDAO#getRealUsersIdentifiedByLogin()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, User> getRealUsersIdentifiedByLogin() throws UserDAOException {
		try {
			final Criteria crit = getSession().createCriteria(getSpecificEntityClass());
			//On exclue l'utilisateur "Anonymous"
			crit.add(Restrictions.ne(LOGIN, Group.ANONYMOUS.getIdentifier()));
			final List<User> users = crit.list();
			for (User user : SafetyUtils.emptyIfNull(users)) {
				user.setGroupsAsList(deserializeGroups(user));
			}
			final Map<String, User> usersMap = new HashMap<String, User>(users.size());
			for (final User user : users) {
				usersMap.put(user.getLogin(), user);
			}
			return usersMap;
		} catch (final HibernateException hibernateException) {
			LOG.error("Hibernate error during search users by criteria", hibernateException);
			throw new UserDAOException(hibernateException);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws UserDAOException exception lors de la création de l'utilisateur
	 */
	@Override public void updateTheme(final Long id, final ThemeDTO theme) throws UserDAOException {
		try {
			User user = (User) getSession().get(User.class, id);
			user.setTheme((Theme) getSession().get(Theme.class, theme.getId()));
		} catch (final HibernateException hibernateException) {
			LOG.error("Hibernate error during search users by criteria", hibernateException);
			throw new UserDAOException(hibernateException);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws UserDAOException exception lors de la création de l'utilisateur
	 */
	@Override
	public Serializable create(User user) throws UserDAOException {
		if (user != null) {
			user.setGroups(serializeGroups(user));
		}
		return super.create(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User update(User user) throws UserDAOException {
		if (user != null) {
			user.setGroups(serializeGroups(user));
		}
		return super.update(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User merge(User user) throws UserDAOException {
		if (user != null) {
			user.setGroups(serializeGroups(user));
		}
		return super.merge(user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User get(Serializable id) throws UserDAOException {
		User user = super.get(id);
		user.setGroupsAsList(deserializeGroups(user));
		return user;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> find() throws UserDAOException {
		List<User> users = super.find();
		for (User user : SafetyUtils.emptyIfNull(users)) {
			user.setGroupsAsList(deserializeGroups(user));
		}
		return users;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getListSortedBy(int start, int range) throws UserDAOException {
		List<User> users = super.getListSortedBy(start, range);
		for (User user : SafetyUtils.emptyIfNull(users)) {
			user.setGroupsAsList(deserializeGroups(user));
		}
		return users;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getListSortedBy(int start, int range, String sortProperty, boolean ascending)
			throws UserDAOException {
		List<User> users = super.getListSortedBy(start, range, sortProperty, ascending);
		for (User user : SafetyUtils.emptyIfNull(users)) {
			user.setGroupsAsList(deserializeGroups(user));
		}
		return users;
	}

	/**
	 * Sérialisation du champ groupsAsList vers groups
	 * @param user le user sur lequel effectuer la sérialisation
	 * @return les groupes sérialisés sous forme de String
	 */
	private String serializeGroups(final User user) {
		String serializedGroups = null;
		if (user.getGroupsAsList() != null) {
			List<Group> groupsAsList = user.getGroupsAsList();
			int groupsSize = groupsAsList.size();
			if (groupsSize > 0) {
				String[] groupsIds = new String[groupsAsList.size()];
				int indexGroups = 0;
				for (Group group : groupsAsList) {
					groupsIds[indexGroups++] = group.getIdentifier();
				}
				serializedGroups = org.springframework.util.StringUtils.arrayToCommaDelimitedString(groupsIds);
			}
		}
		return serializedGroups;
	}

	/**
	 * Déserialisation de la colonne groups vers l'attribut groupsAsxxxxList
	 * @param user le user sur lequel effectuer la désérialisation
	 * @return les groupes désérialisés
	 */
	private List<Group> deserializeGroups(User user) {
		List<Group> deserializedGroups = new LinkedList<Group>();
		String userGroups = user.getGroups();
		if (userGroups != null) {
			String[] groupIds = org.springframework.util.StringUtils.commaDelimitedListToStringArray(userGroups);
			for (String groupIdentifier : groupIds) {
				for (Group group : Group.values()) {
					if (group.getIdentifier().equals(groupIdentifier)) {
						deserializedGroups.add(group);
						break;
					}
				}
			}
		}
		return deserializedGroups;
	}
}
