package com.abita.dto.unpersist;

import com.abita.dto.AgencyDTO;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.web.common.constants.AccessConstants;
import com.web.common.data.StringSelectItem;
import com.web.userslist.constants.UserConstants;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Classe d'objet non persisté appellable depuis le controleur afin de passer les critères de recherche
 *
 * @author
 */
public class ExtendedUserCriteriaDTO implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 7097831999654077194L;

  /** Bundle des acces. */
  private static final ResourceBundle ACCESSBUNDLE = ResourceBundle.getBundle(AccessConstants.ACCESS_BUNDLE);

  /** The resource bundle for the messages */
  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(AbstractGenericController.BUNDLE_NAME);
  /**
   * NNI of user
   */
  private String login;
  /**
   * first name
   */
  private String firstName;
  /**
   * last name
   */
  private String lastName;
  /**
   * email of user
   */
  private String email;
  /**
   * enable or disable user connection.
   */
  private StringSelectItem activated;
  /**
   * enable or disable user contract manager.
   */
  private StringSelectItem thirdPartyContractManager;

  /** User groups */
  private List<String> groups;

  /** L'agence recherchée **/
  private AgencyDTO agency;

  /**
   * @return the login
   */
  public String getLogin() {
    return login;
  }

  /**
   * @param login the login to set
   */
  public void setLogin(String login) {
    this.login = login;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the activated
   */
  public StringSelectItem getActivated() {
    return activated;
  }

  /**
   * @param activated the activated to set
   */
  public void setActivated(StringSelectItem activated) {
    this.activated = activated;
  }

  /**
   * @return the thirdPartyContractManager
   */
  public StringSelectItem getThirdPartyContractManager() {
    return thirdPartyContractManager;
  }

  /**
   * @param thirdPartyContractManager the isThirdPartyContractManager to set
   */
  public void setThirdPartyContractManager(StringSelectItem thirdPartyContractManager) {
    this.thirdPartyContractManager = thirdPartyContractManager;
  }

  /**
   * @return the groups
   */
  public List<String> getGroups() {
    return groups;
  }

  /**
   * @param groups the groups to set
   */
  public void setGroups(List<String> groups) {
    this.groups = groups;
  }

  /**
   * Getter du booléen flagant si les utilisateurs recherchés doivent être activés(VRAI), désactivés(FAUX) ou n'importe quel état (null)
   * @return the activated
   */
  public Boolean isActivated() {
    Boolean result = null;
    if (null != activated && !activated.getValue().equals(ACCESSBUNDLE.getString(UserConstants.USER_LIST_ACTIVATION_ALL))) {
      if (activated.getValue().equals(ACCESSBUNDLE.getString(UserConstants.USER_LIST_ACTIVATION_ON))) {
        result = true;
      } else {
        result = false;
      }
    }
    return result;
  }

  /**
   * Getter du booléen flagant si les utilisateurs recherchés doivent être gestionnaire DIL (VRAI) ou non (FAUX) ou si ce critère n'est pas utilisé (null)
   * @return the activated
   */
  public Boolean isThirdPartyContractManager() {
    Boolean result = null;
    if (null != thirdPartyContractManager && !thirdPartyContractManager.getValue().equals(BUNDLE.getString(ConstantsWEB.OPTION_ANY))) {
      if (thirdPartyContractManager.getValue().equals(BUNDLE.getString(ConstantsWEB.OPTION_YES))) {
        result = true;
      } else {
        result = false;
      }
    }
    return result;
  }

  /**
   * @return the agency
   */
  public AgencyDTO getAgency() {
    return agency;
  }

  /**
   * @param agency the agency to set
   */
  public void setAgency(AgencyDTO agency) {
    this.agency = agency;
  }
}
