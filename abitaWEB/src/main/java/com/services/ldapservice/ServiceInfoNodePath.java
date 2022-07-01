package com.services.ldapservice;

/**
 * @author rahmounepat
 */
public interface ServiceInfoNodePath {
	/**
	 * setter of Entity Code.
	 * @param code entity code
	 */
	void setCode(String code);

	/**
	 * getter of entity's Code.
	 * @return entity code
	 */
	String getCode();

	/**
	 * setter of entity's name
	 * @param name entity name
	 */
	void setName(String name);

	/**
	 * getter of entity's name
	 * @return entity name
	 */
	String getName();

	/**
	 * getter of entity's FullName
	 * @return entity full name
	 */
	String getFullName();

	/**
	 * getter of entity's  LastName
	 * @return entity full name
	 */
	String getLastName();

	/**
	 * setter of entity's child
	 * @param serviceInfoNodePathChild serviceInfoNodePathChild
	 */
	void setServiceInfoNodePathChild(com.services.ldapservice.ServiceInfoNodePath serviceInfoNodePathChild);

	/**
	 * getter of entity's child
	 * @return entity child
	 */
	com.services.ldapservice.ServiceInfoNodePath getServiceInfoNodePathChild();

}
