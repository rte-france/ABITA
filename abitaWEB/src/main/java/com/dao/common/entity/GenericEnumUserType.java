/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.common.entity;

import org.hibernate.HibernateException;
import org.hibernate.type.NullableType;
import org.hibernate.type.TypeFactory;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe de gestion des types Enumeré
 * @author
 *
 */
public class GenericEnumUserType implements UserType, ParameterizedType {
	/**default identifier method name */
	private static final String DEFAULT_IDENTIFIER_METHOD_NAME = "toString";
	/** default valueOf method name*/
	private static final String DEFAULT_VALUE_OF_METHOD_NAME = "valueOf";
	/** enum class*/
	private Class<? extends Enum> enumClass;
	/** identifier type*/
	private Class<?> identifierType;
	/**identifier method*/
	private Method identifierMethod;
	/**valueOf method*/
	private Method valueOfMethod;
	/** type*/
	private NullableType type;
	/** sql Type */
	private int[] sqlTypes;

	@Override
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty("enumClass");
		try {
			enumClass = Class.forName(enumClassName).asSubclass(Enum.class);
		} catch (ClassNotFoundException classNotFoundException) {
			throw new HibernateException("Enum class not found", classNotFoundException);
		}

		String identifierMethodName = parameters.getProperty("identifierMethod", DEFAULT_IDENTIFIER_METHOD_NAME);

		try {
			identifierMethod = enumClass.getMethod(identifierMethodName, new Class[0]);
			identifierType = identifierMethod.getReturnType();
		} catch (Exception e) {
			throw new HibernateException("Failed to obtain identifier method", e);
		}

		type = (NullableType) TypeFactory.basic(identifierType.getName());

		if (type == null) {
			throw new HibernateException("Unsupported identifier type " + identifierType.getName());
		}

		sqlTypes = new int[] { type.sqlType() };

		String valueOfMethodName = parameters.getProperty("valueOfMethod", DEFAULT_VALUE_OF_METHOD_NAME);

		try {
			valueOfMethod = enumClass.getMethod(valueOfMethodName, new Class[] { identifierType });
		} catch (Exception e) {
			throw new HibernateException("Failed to obtain valueOf method", e);
		}
	}

	@Override
	public Class returnedClass() {
		return enumClass;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		Object identifier = type.get(rs, names[0]);
		if (rs.wasNull()) {
			return null;
		}

		try {
			Object obj = valueOfMethod.invoke(enumClass, new Object[] { identifier });
			return obj;
		} catch (Exception e) {
			throw new HibernateException("Exception while invoking valueOf method '" + valueOfMethod.getName()
					+ "' of " + "enumeration class '" + enumClass + "'", e);
		}
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		try {
			if (value == null) {
				st.setNull(index, type.sqlType());
			} else {
				Object identifier = identifierMethod.invoke(value, new Object[0]);
				type.set(st, identifier, index);
			}
		} catch (Exception e) {
			throw new HibernateException("Exception while invoking identifierMethod '" + identifierMethod.getName()
					+ "' of " + "enumeration class '" + enumClass + "'", e);
		}
	}

	@Override
	public int[] sqlTypes() {
		return sqlTypes;
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}
}
