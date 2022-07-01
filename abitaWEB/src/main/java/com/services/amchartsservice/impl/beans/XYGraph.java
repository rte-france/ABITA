package com.services.amchartsservice.impl.beans;

import com.services.amchartsservice.impl.beans.XYPoint;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XY Graph data model
 * @author
 */
public class XYGraph implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 3507558518447011410L;

	/** attributes */
	private HashMap<String, String> attributes;

	/** points */
	private List<XYPoint> points;

	/**
	 * @return the attributes
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = new HashMap<String, String>(attributes);
	}

	/**
	 * @return the points
	 */
	public List<XYPoint> getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(List<XYPoint> points) {
		this.points = points;
	}
}
