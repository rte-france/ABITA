package com.dao.theme.impl;

import com.dao.common.impl.AbstractGenericEntityDAO;
import com.dao.theme.IThemeDAO;
import com.dao.theme.constants.ThemeDAOQueryConstants;
import com.dao.theme.entity.Theme;
import com.dao.theme.exception.ThemeDAOException;
import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * Implementation of {@link IThemeDAO}
 *
 * @author
 */
public class ThemeDAOImpl extends AbstractGenericEntityDAO<Theme, ThemeDAOException> implements IThemeDAO {

	/** serialVersionUID */
	private static final long serialVersionUID = -4209572648275145018L;

	/* (non-Javadoc)
	 * @see com.dao.theme.IThemeDAO#findThemeByName(java.lang.String)
	 */
	@Override
	public Theme findByName(final String themeName) throws ThemeDAOException {
		try {
			Query query = this.getSession().getNamedQuery(ThemeDAOQueryConstants.QUERY_KEY_FIND_THEME_BY_NAME);
			query.setString(ThemeDAOQueryConstants.NAME, themeName);
			return (Theme) query.uniqueResult();
		} catch (final HibernateException hibernateException) {
			this.log.error("Hibernate error during the find theme by name query", hibernateException);
			throw new ThemeDAOException(hibernateException);
		}
	}
}
