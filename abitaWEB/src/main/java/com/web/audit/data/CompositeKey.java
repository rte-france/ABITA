package com.web.audit.data;

import java.io.Serializable;

/**
 * Class used as a key for the Count HashMap when processing files
 * on the file system
 * @author
 *
 */
public class CompositeKey implements Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = 4482571134447463394L;

    /**
	 * constructor
	 * @param applicationId application's id
	 * @param componentId component's id
	 */
	public CompositeKey(String applicationId, String componentId) {
		this.applicationId = applicationId;
		this.componentId = componentId;
	}

	/**
	 * the application identifier
	 */
	private String applicationId;

	/**
	 * The component identifier
	 */
	private String componentId;

	/**
	 * For the hashmap to work
	 * @param obj object to compare
	 * @return true if objects are equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		CompositeKey receivedCount = (CompositeKey) obj;
		return receivedCount.applicationId.equals(this.applicationId)
				&& receivedCount.componentId.equals(this.componentId);
	}

	/**
	 * Need to override this method so the hashmap generate identical hash for
	 * equals objects
	 * @return hashcode of application's id
	 */
	@Override
	public int hashCode() {
		return applicationId.hashCode() ^ componentId.hashCode();
	}

	/**
	 * @return the applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the componentId
	 */
	public String getComponentId() {
		return componentId;
	}

	/**
	 * @param componentId the componentId to set
	 */
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

}
