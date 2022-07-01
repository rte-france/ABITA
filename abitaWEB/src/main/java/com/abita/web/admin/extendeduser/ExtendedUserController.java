/**
 *
 */
package com.abita.web.admin.extendeduser;

import com.abita.services.agency.IAgencyService;
import com.abita.services.extendeduser.IExtendedUserService;
import com.abita.dto.AgencyDTO;
import com.abita.dto.ExtendedUserDTO;
import com.abita.dto.GroupDTO;
import com.abita.dto.unpersist.ExtendedUserCriteriaDTO;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.services.extendeduser.exception.ExtendedUserServiceException;
import com.abita.web.admin.extendeduser.beans.ExtendedUserBean;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.dto.Group;
import com.services.common.exception.NotFoundException;
import com.services.common.util.MessageSupport;
import com.web.common.constants.AccessConstants;
import com.web.common.data.StringSelectItem;
import com.web.userslist.constants.UserConstants;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.event.SelectEvent;

import javax.faces.application.FacesMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlleur en relation avec la page /pages/administration/referentiel/extendeduser.xhtml
 *
 * @author
 */
public class ExtendedUserController extends AbstractGenericController {

  /** SerialVersionUID */
  private static final long serialVersionUID = -8503897487525228836L;

  /** Bundle des acces. */
  private static final ResourceBundle ACCESSBUNDLE = ResourceBundle.getBundle(AccessConstants.ACCESS_BUNDLE);

  /** The resource bundle for the messages */
  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  /** Bean pour la gestion des utilisateurs étendus */
  private ExtendedUserBean extendedUserBean;

  /** Service gérant ImportFile */
  private transient IExtendedUserService extendedUserService;

  /** Service gérant les agences */
  private transient IAgencyService agencyService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedUserController.class);

  /**
   * Initialisation
   *
   * Récupération de la liste des goupes et des utilisateurs
   */
  public void init() {
    if (extendedUserBean.getGroupList() == null || extendedUserBean.getExtendedUserDTOList() == null) {
      try {
        prepareSearchForm();
        prepareExtendedUserPickList();

      } catch (ExtendedUserServiceException e) {
        LOGGER.error(e.getMessage(), e);
        MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE, AccessConstants.ERROR_FATAL);
      } catch (AgencyServiceException e) {
        LOGGER.error(e.getMessage(), e);
        MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE, AccessConstants.ERROR_FATAL);
      }
    }

  }

  /**
   * @throws AgencyServiceException
   *
   */
  private void prepareSearchForm() throws AgencyServiceException {
    // Init search form
    List<GroupDTO> groupList = createGroupList();
    extendedUserBean.setGroupList(groupList);

    List<AgencyDTO> agencyChoices = new ArrayList<AgencyDTO>();
    AgencyDTO agency = new AgencyDTO();
    agency.setId(ConstantsWEB.UNPERSIST_AGENCY_IDENTIFIER);
    agency.setName(ConstantsWEB.ALL_AGENCIES_VALUES);
    agencyChoices.add(agency);

    List<AgencyDTO> agencies = agencyService.findAllAgencies();
    agencyChoices.addAll(agencies);

    extendedUserBean.setAgencyList(agencies);
    extendedUserBean.setAgencyChoices(agencyChoices);

    List<StringSelectItem> activatedList = createActivatedList();
    extendedUserBean.setActivatedList(activatedList);
    List<StringSelectItem> tpcManagerOptionList = createThirdPartyContractManagerOptionList();
    extendedUserBean.setThirdPartyContractManagerOptionList(tpcManagerOptionList);
    ExtendedUserCriteriaDTO userCriteria = extendedUserBean.getExtendedUserCriteria();

    userCriteria.setActivated(null);
  }

  /**
   * Methode initialisant la liste des utilisateurs
   * @throws ExtendedUserServiceException une ExtendedUserServiceException
   */
  private void prepareExtendedUserPickList() throws ExtendedUserServiceException {
    if (extendedUserBean.getExtendedUserDTOList() == null || extendedUserBean.getExtendedUserDTOList().isEmpty()) {
      search();
    }
  }

  /**
   * Action de recherche des utilisateurs par critères
   */
  public void search() {

    try {
      ExtendedUserCriteriaDTO userCriteria = extendedUserBean.getExtendedUserCriteria();
      List<String> temporaryGroupList = userCriteria.getGroups();
      if (userCriteria.getGroups() == null || userCriteria.getGroups().contains("all") || userCriteria.getGroups().size() > extendedUserBean.getGroupList().size()
        || userCriteria.getGroups().isEmpty()) {
        List<String> defaultGroupList = new ArrayList<String>();
        for (GroupDTO groupDTO : extendedUserBean.getGroupList()) {
          defaultGroupList.add(groupDTO.getIdentifier());
        }
        userCriteria.setGroups(defaultGroupList);
      }

      List<ExtendedUserDTO> users = extendedUserService.findByCriteria(userCriteria);
      extendedUserBean.setExtendedUserDTOList(users);

      for (ExtendedUserDTO extendedUserDTO : users) {
        extendedUserDTO.setAllAgencies(extendedUserDTO.getAgencies().size() == extendedUserBean.getAgencyList().size());
      }

      userCriteria.setGroups(temporaryGroupList);

    } catch (ExtendedUserServiceException e) {
      LOGGER.error(e.getMessage(), e);
      MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE, AccessConstants.ERROR_FATAL);
    }

  }

  /**
   * update extended user
   */
  public void updateExtendedUser() {
    ExtendedUserDTO selectedUser;
    try {
      selectedUser = extendedUserBean.getSelectedUser();
      selectedUser.setGroups(null);
      List<AgencyDTO> agencyList = new ArrayList<AgencyDTO>();
      selectedUser.setAgencies(agencyList);
      for (GroupDTO group : extendedUserBean.getSelectedGroups()) {
        selectedUser.getGroupsAsList().add(Group.valueOf(group.getName()));
        if (Group.ADMINISTRATEUR.name().equals(group.getName())) {
          extendedUserBean.setSelectedAgency(null);
        }
      }
      if (extendedUserBean.getSelectedAgency() != null) {
        if (extendedUserBean.getSelectedAgency().getId() != -1L) {
          selectedUser.getAgencies().add(extendedUserBean.getSelectedAgency());
        } else if (extendedUserBean.getSelectedAgency().getId() == -1L) {
          selectedUser.getAgencies().addAll(extendedUserBean.getAgencyList());
        }
      }
      selectedUser.setIsThirdPartyContractManager(extendedUserBean.getIsThirdPartyContractManager());

      extendedUserService.update(selectedUser, "extendedUserLightUpdate");

      selectedUser.setAllAgencies(selectedUser.getAgencies().size() == extendedUserBean.getAgencyList().size());

      // After the user's groups update in data base no user should be selected
      // in the IHM, selectedUser.setId(null) does not work :'( this code does fine
      selectedUser = null;
      extendedUserBean.setSelectedGroups(null);
      extendedUserBean.setSelectedAgency(null);
    } catch (ExtendedUserServiceException e) {
      final Throwable rootCause = ExceptionUtils.getRootCause(e);
      if (rootCause instanceof NotFoundException) {
        LOGGER.error("not expected error", e);
      } else {
        MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE, AccessConstants.ERROR_FATAL);
      }
    }
  }

  /**
   * update extended user
   * @param event event
   */
  public void prepareUserEdition(SelectEvent event) {
    ExtendedUserDTO selectedUser = extendedUserBean.getSelectedUser();
    List<GroupDTO> selectedGroup = new ArrayList<GroupDTO>();
    for (Group group : selectedUser.getGroupsAsList()) {
      selectedGroup.add(new GroupDTO(group));
    }

    if (selectedUser.getAgencies().size() == extendedUserBean.getAgencyList().size()) {
      extendedUserBean.setSelectedAgency(extendedUserBean.getAgencyChoices().get(0));
    } else if (!selectedUser.getAgencies().isEmpty()) {
      extendedUserBean.setSelectedAgency(selectedUser.getAgencies().get(0));
    }

    extendedUserBean.setSelectedGroups(selectedGroup);

    extendedUserBean.setIsThirdPartyContractManager(selectedUser.getIsThirdPartyContractManager());
  }

  /**
   * Permet de recuperer une classe de style a appliquer dynamiquement a un utlisateur desactive ou non
   * @param rowIndex l'index de la ligne
   * @return le libelle d'une classe de style
   */
  public String getStyleClass(final Integer rowIndex) {
    String value = "";
    List<ExtendedUserDTO> users = extendedUserBean.getExtendedUserDTOList();
    if (!users.isEmpty() && !users.get(rowIndex).getActivated()) {
      value = UserConstants.STYLE_CLASSES_USER_DESACTIVATED;
    }
    return value;

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
   * Create list of users states with constants
   * @return list of users states
   */
  private List<StringSelectItem> createThirdPartyContractManagerOptionList() {
    final int tailleTypesActivations = 3;
    List<StringSelectItem> options = new ArrayList<StringSelectItem>(tailleTypesActivations);
    options.add(new StringSelectItem(BUNDLE.getString(ConstantsWEB.OPTION_ANY)));
    options.add(new StringSelectItem(BUNDLE.getString(ConstantsWEB.OPTION_YES)));
    options.add(new StringSelectItem(BUNDLE.getString(ConstantsWEB.OPTION_NO)));
    return options;
  }

  /**
   * Create list of groups
   * @return list of groups
   */
  private List<GroupDTO> createGroupList() {
    List<Group> groupList = Group.getChilds(Group.REGISTERED);
    List<GroupDTO> result = new ArrayList<GroupDTO>(groupList.size());
    for (Group group : groupList) {
      result.add(new GroupDTO(group));
    }
    return result;
  }

  /**
   * @param extendedUserBean the extendedUserBean to set
   */
  public void setExtendedUserBean(ExtendedUserBean extendedUserBean) {
    this.extendedUserBean = extendedUserBean;
  }

  /**
   * @param extendedUserService the extendedUserService to set
   */
  public void setExtendedUserService(IExtendedUserService extendedUserService) {
    this.extendedUserService = extendedUserService;
  }

  /**
   * @param agencyService the agencyService to set
   */
  public void setAgencyService(IAgencyService agencyService) {
    this.agencyService = agencyService;
  }

}
