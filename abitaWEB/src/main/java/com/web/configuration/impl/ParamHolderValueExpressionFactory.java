package com.web.configuration.impl;

import com.web.common.util.ValueExpressionFactory;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import java.text.MessageFormat;

/**
 * A factory to make parameterHolders value expression.
 * @author
 *
 */
public abstract class ParamHolderValueExpressionFactory {

	/**
	 * Private constructor
	 */
	private ParamHolderValueExpressionFactory() {
		super();
	}

	/**
	 * the value of the value tag's property.
	 */
	public static final String METHODE = "#'{'parameter.parameterHolders[''{0}''][''{1}''].value'}'";

	/**
	 * Make valueExpression to call parameterHoldersconvert value.
	 * @param context faces context
	 * @param domainName the domain name.
	 * @param parameterName the parameter name.
	 * @param cl expected type
	 * @return A valueExpression.
	 */
	public static ValueExpression createValueExpression(FacesContext context, String domainName, String parameterName,
			Class<?> cl) {
		return ValueExpressionFactory.get(context, MessageFormat.format(METHODE, domainName, parameterName), cl);
	}
}
