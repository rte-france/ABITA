/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.userslist.data;

import com.web.userslist.data.GroupDataModel;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;


/**
 * Modele de donnees de liste de groupes
 * @author
 *
 */
public class GroupsListDataModel extends ListDataModel<com.web.userslist.data.GroupDataModel> implements SelectableDataModel<com.web.userslist.data.GroupDataModel>, Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = 7946940941181613180L;

    /**
	 * Constructeur sans parametre
	 */
	public GroupsListDataModel() {
		super();
	}

	/**
	 * Constructeur avec parametre
	 * @param list liste de groupes
	 */
	public GroupsListDataModel(List<com.web.userslist.data.GroupDataModel> list) {
		super(list);
	}

	@Override
	public com.web.userslist.data.GroupDataModel getRowData(String rowKey) {
		List<com.web.userslist.data.GroupDataModel> groupsDataModel = (List<com.web.userslist.data.GroupDataModel>) getWrappedData();

		for (com.web.userslist.data.GroupDataModel groupDataModel : groupsDataModel) {
			if (groupDataModel.getName().equals(rowKey)) {
				return groupDataModel;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(GroupDataModel groupDataModel) {
		return groupDataModel.getName();
	}

}
