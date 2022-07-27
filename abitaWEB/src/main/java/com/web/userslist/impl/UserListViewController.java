/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.userslist.impl;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dto.Group;
import com.dto.UserDTO;
import com.dto.UsersSearchDTO;
import com.services.access.constants.AccessServiceFacadeConstants;
import com.services.common.constants.Constants;
import com.services.common.exception.NotFoundException;
import com.services.common.util.MessageSupport;
import com.services.common.util.SafetyUtils;
import com.services.paramservice.ParameterService;
import com.services.paramservice.constants.ParamServiceConstants;
import com.services.paramservice.exception.ParameterServiceException;
import com.services.user.IUserService;
import com.services.user.exception.UserServiceException;
import com.web.common.constants.AccessConstants;
import com.web.common.data.ExportSheetData;
import com.web.common.data.StringSelectItem;
import com.web.common.exception.ExportFileException;
import com.web.common.impl.AbstractExportWorkBookController;
import com.web.common.util.ExportUtils;
import com.web.userslist.constants.UserConstants;
import com.web.userslist.data.GroupDataModel;
import com.web.userslist.data.GroupsListDataModel;
import com.web.userslist.data.UserDataModel;
import com.web.userslist.data.UserListViewBean;
import com.web.userslist.data.UsersListDataModel;

/**
 * A user list managed bean.
 * @author
 * @version 4.4.1.0
 *      [RM_MET_002] Trace de la durée de la liste des utilisateurs
 *      [RM_MET_003] Trace de la durée d'un export excel de la liste des utilisateurs
 *      RM_UTI_012	: Gestion des groupes par défaut
 */
public class UserListViewController extends AbstractExportWorkBookController implements Serializable {

  /** serialVersionUID */
  private static final long serialVersionUID = 3447112702668796616L;

  /** Bundle des acces. */
  private static final ResourceBundle ACCESSBUNDLE = ResourceBundle.getBundle(AccessConstants.ACCESS_BUNDLE);

  /** Error message export users */
  private static final String USER_EXPORT_FAILURE = "users_export_failure";

  /** Label column NNI */
  private static final String TITLE_NNI = "user.list.nni";

  /** Label column firstname */
  private static final String TITLE_FIRSTNAME = "user.list.first_name";

  /** Label column lastname */
  private static final String TITLE_LASTNAME = "user.list.last_name";

  /** Label column email */
  private static final String TITLE_EMAIL = "user.list.email";

  /** Label column phone */
  private static final String TITLE_PHONE = "user.list.phone";

  /** Label column fax */
  private static final String TITLE_FAX = "user.list.fax";

  /** Label column location */
  private static final String TITLE_LOCATION = "user.list.location";

  /** Label column activation */
  private static final String TITLE_ACTIVATION = "user.list.activation.label";

  /** Label column groups */
  private static final String TITLE_GROUPS = "user.groups";

  /** taille de userData */
  private static final int USER_DATA_LINE_SIZE = 10;

  /** Le service pour la gestion des utilisateurs */
  private IUserService userService;

  /** The parameter service. */
  private ParameterService parameterService;

  /**
   * The logger
   */
  private static final Log LOG = LogFactory.getLog(UserListViewController.class);

  /** The constant defining the name of the site name parameter */
  public static final String SITE_NAME = "siteName";

  /** Bean */
  private UserListViewBean userListViewBean;

  /**
   * Intialisation du contrôleur avant l'affichage du premier élément
   *
   * @param event l'événement
   */
  public void init(final ComponentSystemEvent event) {
    this.init();
  }

  /**
   * Initialisation
   *
   * Création de l'objet {@link UsersSearchDTO} dans le bean
   * Récupération de la liste des goupes et des utilisateurs
   */
  private void init() {
    if (this.getUserListViewBean().getGroupsListDataModel() == null
      || this.getUserListViewBean().getGroups() == null || this.getUserListViewBean().getUsers() == null
      || this.getUserListViewBean().getUsersListDataModel() == null) {
      try {
        // [RM_MET_002] Indicateur durée de la liste des utilisateurs
        Calendar dateBegin = Calendar.getInstance();

        GroupDataModel[] groupsDataModel = this.convertGroup(Group.values());

        GroupsListDataModel groupsListDataModel = new GroupsListDataModel(Arrays.asList(groupsDataModel));
        this.getUserListViewBean().setGroupsListDataModel(groupsListDataModel);

        // Init search form
        this.getUserListViewBean().setGroups(this.createGroupSelect(groupsDataModel));
        List<StringSelectItem> activatedList = this.createActivatedList();
        this.userListViewBean.setActivatedList(activatedList);

        UsersSearchDTO usersSearch = this.getUserListViewBean().getUsersSearch();
        usersSearch.setActivatedSelected(activatedList.get(0));

        List<UserDataModel> users = this.convertUser(this.userService.find(usersSearch));
        this.getUserListViewBean().setUsers(users);
        this.getUserListViewBean().setUsersListDataModel(new UsersListDataModel(users));

        // [RM_MET_002] Fin indicateur de durée de la liste des utilisateurs
        Calendar dateEnd = Calendar.getInstance();
        // Récupération de l'utilisateur connecté
        UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
          .get(Constants.USER_KEY);
      } catch (UserServiceException userServiceException) {
        MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE,
          AccessConstants.ERROR_FATAL);
      }
    }

    if (this.getUserListViewBean().getDefaultGroupsId() == null) {
      List<String> defaultGroupsId = new ArrayList<String>();
      try {
        List<Group> defaultGroups = this.parameterService.getDefaultGroups();
        for (final Group group : defaultGroups) {
          defaultGroupsId.add(group.getIdentifier());
        }
      } catch (final ParameterServiceException parameterServiceException) {
        LOG.error("Erreur pendant la récupération des groupes par défaut", parameterServiceException);
        MessageSupport.addMessage(FacesMessage.SEVERITY_WARN, AccessConstants.ACCESS_BUNDLE,
          AccessConstants.WARN_INIT_DEFAULT_GROUPS);
      }
      this.getUserListViewBean().setDefaultGroupsId(defaultGroupsId);
    }
  }

  /**
   * Create groups select
   * @param groupsToSelect groups data model
   * @return groups select
   */
  private List<SelectItem> createGroupSelect(final GroupDataModel[] groupsToSelect) {
    List<SelectItem> res = new ArrayList<SelectItem>(groupsToSelect.length);
    for (GroupDataModel groupDataModel : groupsToSelect) {
      res.add(new SelectItem(groupDataModel.getName(), groupDataModel.getName()));
    }
    return res;
  }

  /**
   * Convert list of users to list of UserDataModel
   * @param data list to convert
   * @return convert result
   */
  private List<UserDataModel> convertUser(final List<UserDTO> data) {
    List<UserDataModel> res = new ArrayList<UserDataModel>(data == null ? 0 : data.size());
    UserDataModel userDataModel = null;
    GroupDataModel[] convertGroup;
    for (UserDTO user : SafetyUtils.emptyIfNull(data)) {
      userDataModel = new UserDataModel(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName(),
        user.getEmail(), user.getPhone(), user.getFax(), user.getLocation(), user.getActivated());
      convertGroup = this.convertGroup(user.getGroups().toArray(new Group[user.getGroups().size()]));
      userDataModel.setGroups(convertGroup);
      res.add(userDataModel);
    }

    return res;
  }

  /**
   * Convert collection of Group to array of GroupDataModel
   *
   * @param data collection to convert
   * @return array
   */
  private GroupDataModel[] convertGroup(final Group[] data) {
    GroupDataModel[] res;
    if (data.length > 0) {
      res = new GroupDataModel[data.length];

      int i = 0;
      for (Group grp : data) {
        res[i++] = new GroupDataModel(grp);
      }
    } else {
      res = new GroupDataModel[] {};
    }
    return res;
  }

  /**
   * update user's group.
   * @param event event
   */
  public void updateGroupUser(final ActionEvent event) {
    UserDTO userDTO;
    try {
      UserDataModel selectedUser = this.getUserListViewBean().getSelectedUser();
      userDTO = this.userService.get(selectedUser.getId());
      userDTO.getGroups().clear();
      GroupDataModel groupDataModel = null;
      for (int i = 0; i < selectedUser.getGroups().length; i++) {
        groupDataModel = selectedUser.getGroups()[i];
        userDTO.getGroups().add(groupDataModel.getGroup());
      }
      this.userService.update(userDTO);
      // After the user's groups update in data base no user should be selected
      // in the IHM, selectedUser.setId(null) does not work :'( this code does fine
      selectedUser = null;
    } catch (final UserServiceException userServiceException) {
      final Throwable rootCause = ExceptionUtils.getRootCause(userServiceException);
      if (rootCause instanceof NotFoundException) {
        LOG.error("not expected error", userServiceException);
      } else {
        MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE,
          AccessConstants.ERROR_FATAL);
      }
    }
  }

  /**
   * [RM_MET_003] Trace de la durée d'un export excel de la liste des utilisateurs
   * @throws ExportFileException exception lors de l'export
   */
  @Override
  public void exportFile() throws ExportFileException {
    ExportSheetData exportSheetData = this.getUsersData();
    exportSheetData.setTitle("Sheet0");
    List<ExportSheetData> export = new ArrayList<ExportSheetData>(1);
    export.add(exportSheetData);

    String siteName = null;
    try {
      siteName = this.parameterService.getParameterValue(
        ParamServiceConstants.GENERAL_DOMAIN_KEY, SITE_NAME);
    } catch (ParameterServiceException parameterServiceException) {
      LOG.debug("Exception ok");
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    this.workBookName = siteName + "_" + sdf.format(new Date()) + "-users.xlsx";
    try {
      this.workBook = ExportUtils.export(export);
    } catch (IOException ioException) {
      LOG.error(ioException);
      MessageSupport.addMessage(FacesMessage.SEVERITY_FATAL, AccessConstants.ACCESS_BUNDLE, USER_EXPORT_FAILURE);
      throw new ExportFileException(ioException);
    }
  }

  /**
   * Create list of users states with constants
   * @return list of users states
   */
  private List<StringSelectItem> createActivatedList() {
    final int tailleTypesActivations = 3;
    List<StringSelectItem> activations = new ArrayList<StringSelectItem>(tailleTypesActivations);
    activations.add(new StringSelectItem(ACCESSBUNDLE.getString(UserConstants.USER_LIST_ACTIVATION_ALL)));
    activations.add(new StringSelectItem(ACCESSBUNDLE.getString(UserConstants.USER_LIST_ACTIVATION_OFF)));
    activations.add(new StringSelectItem(ACCESSBUNDLE.getString(UserConstants.USER_LIST_ACTIVATION_ON)));
    return activations;
  }

  /**
   * wrapper for messages of report.
   * @author
   *
   */
  public class Message {

    /** Message */
    private String message;

    /**
     *
     * Construct Message.
     * @param message message
     */
    public Message(final String message) {
      this.message = message;
    }

    /**
     * getter of message
     * @return message
     */
    public String getMessage() {
      return this.message;
    }
  }

  /**
   * formating user data to export
   * @return formating data
   */
  private ExportSheetData getUsersData() {

    ExportSheetData exportSheetData = new ExportSheetData();

    // construction du header
    exportSheetData.getHeaders().add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE, TITLE_NNI));
    exportSheetData.getHeaders().add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE, TITLE_FIRSTNAME));
    exportSheetData.getHeaders().add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE, TITLE_LASTNAME));
    exportSheetData.getHeaders().add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE, TITLE_EMAIL));
    exportSheetData.getHeaders().add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE, TITLE_PHONE));
    exportSheetData.getHeaders().add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE, TITLE_FAX));
    exportSheetData.getHeaders().add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE, TITLE_LOCATION));
    exportSheetData.getHeaders().add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE, TITLE_ACTIVATION));
    exportSheetData.getHeaders().add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE, TITLE_GROUPS));

    // Construction des lignes
    List<String> userData = null;
    for (UserDataModel user : this.getUserListViewBean().getUsers()) {
      userData = new ArrayList<String>(USER_DATA_LINE_SIZE);
      userData.add(user.getLogin());
      userData.add(user.getFirstName());
      userData.add(user.getLastName());
      userData.add(user.getEmail());
      userData.add(user.getPhone());
      userData.add(user.getFax());
      userData.add(user.getLocation());
      if (user.getActivated()) {
        userData.add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE,
          UserConstants.USER_LIST_ACTIVATION_ON));
      } else {
        userData.add(MessageSupport.getMessage(AccessConstants.ACCESS_BUNDLE,
          UserConstants.USER_LIST_ACTIVATION_OFF));
      }
      userData.add(user.getGroupsDisplay());

      exportSheetData.getLines().add(userData);
    }

    return exportSheetData;
  }

  /**
   * Action de recherche des utilisateurs par critères
   */
  public void search() {

    try {
      // [RM_MET_002] Indicateur durée de la liste des utilisateurs
      Calendar dateBegin = Calendar.getInstance();

      UsersSearchDTO usersSearch = this.getUserListViewBean().getUsersSearch();
      if (usersSearch.getGroupsSelected().contains("-1")
        || usersSearch.getGroupsSelected().size() > this.getUserListViewBean().getGroups().size()) {
        usersSearch.setGroupsSelected(null);
      }

      List<UserDataModel> users = this.convertUser(this.userService.find(usersSearch));
      this.getUserListViewBean().setUsers(users);
      this.getUserListViewBean().setUsersListDataModel(new UsersListDataModel(users));

      // [RM_MET_002] Fin indicateur de durée de la liste des utilisateurs
      Calendar dateEnd = Calendar.getInstance();
      // Récupération de l'utilisateur connecté
      UserDTO user = (UserDTO) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
        .get(Constants.USER_KEY);
    } catch (final UserServiceException userServiceException) {
      MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE,
        AccessConstants.ERROR_FATAL);
    }

  }

  /**
   * Enregistrement du groupe par défaut
   */
  public void saveDefaultGroups() {
    try {
      String defaultGroups = "";
      for (final String defaultgroup : this.getUserListViewBean().getDefaultGroupsId()) {
        Group defaultGroup = null;
        for (Group group : Group.values()) {
          if (defaultgroup.equals(group.getIdentifier())) {
            defaultGroup = group;
            break;
          }
        }
        if (!defaultGroups.isEmpty()) {
          defaultGroups = defaultGroups.concat(AccessServiceFacadeConstants.DEFAULT_GROUPS_SEPARATOR);
        }
        defaultGroups = defaultGroups.concat(defaultGroup.getIdentifier());
      }

      this.parameterService.setParameterValue(ParamServiceConstants.ACCESS_DOMAIN_KEY,
        AccessConstants.DEFAULT_GROUP_KEY, defaultGroups);
      MessageSupport.addMessage(FacesMessage.SEVERITY_INFO, AccessConstants.ACCESS_BUNDLE,
        AccessConstants.INFO_DEFAULT_GROUP_SAVE);
    } catch (ParameterServiceException parameterServiceException) {
      MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE,
        AccessConstants.ERROR_DEFAULT_GROUP_SAVE);
    }
  }

  /**
   * Permet de recuperer une classe de style a appliquer dynamiquement a un utlisateur desactive ou non
   * @param rowIndex l'index de la ligne
   * @return le libelle d'une classe de style
   */
  public String getStyleClass(final Integer rowIndex) {
    String value = "";
    List<UserDataModel> users = this.getUserListViewBean().getUsers();
    if (!users.isEmpty() && !users.get(rowIndex).getActivated()) {
      value = UserConstants.STYLE_CLASSES_USER_DESACTIVATED;
    }
    return value;

  }

  /**
   * Sets the parameter service.
   *
   * @param parameterService the new parameter service
   */
  public void setParameterService(final ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * @return the userListViewBean
   */
  public UserListViewBean getUserListViewBean() {
    return this.userListViewBean;
  }

  /**
   * @param userListViewBean the userListViewBean to set
   */
  public void setUserListViewBean(final UserListViewBean userListViewBean) {
    this.userListViewBean = userListViewBean;
  }

  /**
   * @param userService the userService to set
   */
  public void setUserService(final IUserService userService) {
    this.userService = userService;
  }
}
