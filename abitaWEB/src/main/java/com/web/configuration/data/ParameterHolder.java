/**
 * A. Erisay - 6 juil. 2011
 */
package com.web.configuration.data;

/**
 * @author
 * @param <T> parameter type
 */
public interface ParameterHolder<T> {

	/**
	* @return parameter value
	*/
	T getValue();

	/**
	* @param value parameter value
	*/
	void setValue(T value);

	/**
	* @return true if parameter is modified, false otherwise
	*/
	Boolean isModified();

	/**
	* @return parameter modified value
	*/
	String getModifiedValue();

}
