package com.web.common.util;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 * Helper to create Value Expression
 * @author
 *
 */
public abstract class ValueExpressionFactory {

	/**
	 * Private constructor
	 */
	private ValueExpressionFactory() {
		super();
	}

	/**
	 * Construct ValueExpression for dynamic component
	 * @param fc the current instance of faces context.
	 * @param elValue String write in Expression's Language. Like in jsf TAG.
	 * @param cl expected type
	 * @return value expression
	 */
	public static ValueExpression get(FacesContext fc, String elValue, Class<?> cl) {
		ExpressionFactory ef = fc.getApplication().getExpressionFactory();
		ELContext elc = FacesContext.getCurrentInstance().getELContext();
		ValueExpression ve = ef.createValueExpression(elc, elValue, cl);
		return ve;
	}

    /**
     * Construct ValueExpression value for dynamic component
     * @param fc the current instance of faces context.
     * @param elValue String write in Expression's Language. Like in jsf TAG.
     * @param cl expected type
     * @return value expression value
     */
    public static Object getValue(FacesContext fc, String elValue, Class<?> cl) {
        ExpressionFactory ef = fc.getApplication().getExpressionFactory();
        ELContext elc = FacesContext.getCurrentInstance().getELContext();
        ValueExpression ve = ef.createValueExpression(elc, elValue, cl);
        return ve.getValue(elc);
    }
}
