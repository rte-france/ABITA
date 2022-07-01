package com.web.userslist.data;

import com.web.userslist.data.UserDataModel;
import org.primefaces.model.SelectableDataModel;

import javax.faces.model.ListDataModel;
import java.io.Serializable;
import java.util.List;

/**
 * Datamodel de la liste d'utilisateurs
 * @author
 *
 */
public class UsersListDataModel extends ListDataModel<com.web.userslist.data.UserDataModel> implements SelectableDataModel<com.web.userslist.data.UserDataModel>,
		Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -470891838900554953L;

	/**
	 * Constructeur sans parametre
	 */
	public UsersListDataModel() {
	}

	/**
	 * Constructeur
	 * @param users liste d'utilisateurs
	 */
	public UsersListDataModel(List<com.web.userslist.data.UserDataModel> users) {
		super(users);
	}

	@Override
	public com.web.userslist.data.UserDataModel getRowData(String rowKey) {
		List<com.web.userslist.data.UserDataModel> usersDataModel = (List<com.web.userslist.data.UserDataModel>) getWrappedData();
		Long id = Long.parseLong(rowKey);
		for (com.web.userslist.data.UserDataModel userDataModel : usersDataModel) {
			if (userDataModel.getId().equals(id)) {
				return userDataModel;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(UserDataModel userDataModel) {
		return userDataModel.getId();
	}

}
