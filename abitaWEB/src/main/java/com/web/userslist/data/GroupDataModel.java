/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.userslist.data;

import com.dto.Group;

import java.io.Serializable;

/**
 * group data model
 * @author
 *
 */
public class GroupDataModel implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 5943111437174515277L;

    /** group name */
	private String name;

	/** group */
	private Group group;

	/** HASH_CODE_COMPLEMENT */
	private static final int HASH_CODE_COMPLEMENT = 32;

	/**
	 * default constructor.
	 */
	public GroupDataModel() {
		//
	}

	/**
	 * Adapter contructor
	 * @param group group
	 */
	public GroupDataModel(Group group) {
		this.name = group.getIdentifier();
		this.group = group;
	}

	/**
	 * getter name.
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * setter name.
	 * @param name group name
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (!(obj instanceof GroupDataModel)) {
			return false;
		}

		GroupDataModel compare = (GroupDataModel) obj;

		if (compare.name == null) {
			return this.name == null;
		}

		return compare.name.equals(this.name);
	}

	@Override
	public int hashCode() {
		int hash = 1;
		return hash * HASH_CODE_COMPLEMENT + name.hashCode();
	}

	/**
	 * getter group
	 * @return group
	 */
	public Group getGroup() {
		return group;
	}

	@Override
	public String toString() {
	    return this.name;
	}
}
