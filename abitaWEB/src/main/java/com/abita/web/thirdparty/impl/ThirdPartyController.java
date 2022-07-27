/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.thirdparty.impl;

import com.abita.services.thirdparty.IThirdPartyService;
import com.abita.dto.ThirdPartyDTO;
import com.abita.services.thirdparty.exceptions.ThirdPartyServiceException;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.thirdparty.bean.ThirdPartyBean;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;

/**
 * Classe controleur de la page des gestion des tiers
 * @author
 *
 */
public class ThirdPartyController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = -8871261459079350765L;

  /**
   *
   * PROPRIETES
   *
   */

  /** Service des tiers */
  private transient IThirdPartyService thirdPartyService;

  /** Backing bean de la gestion des tiers */
  private ThirdPartyBean thirdPartyBean;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ThirdPartyController.class);

  /** Code de navigation pour le retour à la consultation */
  private static final String CONSULTATION_NAVIGATION_CODE = "GO_TO_CONSULTATION";

  /** Erreur si un utilisateur ouvre plusieurs onglets (Ecrasement des informations) */
  private static final String TECHNICAL_MULTIPLE_TABS_ERROR_CODE = "Impossible de sauvegarder. Plusieurs onglets ou fenêtres sont ouverts";

  /** Identifiant du champ caché dans la page de modification des tiers */
  private static final String MODIFICATION_HIDDEN_INPUT_ID = "frmUpdateThirdParty:checkId";

  /** Identifiant du champ caché dans la page de création des tiers */
  private static final String CREATE_HIDDEN_INPUT_ID = "frmCreateThirdParty:checkId";


  /**
   *
   * METHODES
   *
   */

  /**
   * Méthode d'initialisation des beans
   */
  @PostConstruct
  public void init() {
    // Détermine le mode : Création ou Modification
    String action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("create");

    if (StringUtils.isEmpty(action)) {
      Long idThirdParty = null;

      if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_THIRD_PARTY) != null) {
        idThirdParty = ((Number) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_THIRD_PARTY)).longValue();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().keep(ConstantsWEB.ID_THIRD_PARTY);
      } else {
        // Cas de la suppression qui effectue un post dont on sait pas d'où il sort et qui pert la variable flash
        if (thirdPartyBean.getThirdPartyDTO().getId() != null) {
          idThirdParty = thirdPartyBean.getThirdPartyDTO().getId();
          FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_THIRD_PARTY, idThirdParty);
        }
      }

      if (idThirdParty != null) {
        try {
          thirdPartyBean.setThirdPartyDTO(thirdPartyService.get(idThirdParty));
          thirdPartyBean.setReferenceGCP(thirdPartyBean.getThirdPartyDTO().getGcpReference());
        } catch (ThirdPartyServiceException ex) {
          LOGGER.error("Une erreur est survenue lors de la récupération du tiers", ex);
          FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
        }
      }

      // On récupére le champ  caché dans la page de modification
      HtmlInputHidden checkId = (HtmlInputHidden) FacesContext.getCurrentInstance().getViewRoot().findComponent(MODIFICATION_HIDDEN_INPUT_ID);
      if (checkId != null) {
        checkId.setValue(String.valueOf(thirdPartyBean.getThirdPartyDTO().getId()));
      }
    } else {
      thirdPartyBean.setThirdPartyDTO(new ThirdPartyDTO());

      // On récupére le champ  caché dans la page de création
      HtmlInputHidden checkId = (HtmlInputHidden) FacesContext.getCurrentInstance().getViewRoot().findComponent(CREATE_HIDDEN_INPUT_ID);
      if (checkId != null) {
        checkId.setValue(String.valueOf(thirdPartyBean.getThirdPartyDTO().getId()));
      }
    }
  }

  /**
   * Permet de savoir si le tiers est supprimable
   * @return true si le tiers n'est pas référencé dans l'application, false autrement
   */
  public boolean isRemovable() {
    boolean isRemovable = true;

    try {
      isRemovable = thirdPartyService.isRemovable(thirdPartyBean.getThirdPartyDTO().getId());
    } catch (ThirdPartyServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la vérification de la suppression d'un tiers", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    }

    return isRemovable;
  }

  /**
   * Permet de sauvegarder un tiers
   *
   * @return la valeur de l'action
   */
  public String saveOrUpdateData() {
    UIInput checkId;
    if (FacesContext.getCurrentInstance().getViewRoot().findComponent(CREATE_HIDDEN_INPUT_ID) != null) {
      checkId = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(CREATE_HIDDEN_INPUT_ID);
    } else {
      checkId = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(MODIFICATION_HIDDEN_INPUT_ID);
    }
    if (checkId != null && checkId.getValue().equals(String.valueOf(thirdPartyBean.getThirdPartyDTO().getId()))) {

      String redirection = CONSULTATION_NAVIGATION_CODE;
      if (thirdPartyBean.getThirdPartyDTO().getId() != null) {
        redirection = updateData();
      } else {
        redirection = saveData();
      }
      return redirection;
    } else {

      LOGGER.error("PROBLEME D'OUVERTURE DE PLUSIEURS ONGLETS/FENETRES");
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_MULTIPLE_TABS_ERROR_CODE));
      return null;
    }
  }


  /**
   * Permet de renvoyer vers le mode Modification
   * @return la valeur de l'action
   */
  public String redirectUpdateData() {
    return "GO_TO_MODIFICATION";
  }

  /**
   * Permet de supprimer un tiers
   * @return le résultat de l'action
   */
  public String deleteData() {
    try {
      thirdPartyService.delete(thirdPartyBean.getThirdPartyDTO().getId());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("thirdparty.result.delete"));
    } catch (ThirdPartyServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la suppression d'un tiers", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdparty.delete.error", thirdPartyBean.getThirdPartyDTO().getGcpReference()));
    }

    return "EXIT_THIRD_PARTY";
  }

  /**
   * Action du bouton "Annuler"
   * Comme on peut arriver de la modification, avant d'annuler, on doit supprimer l'état de modification
   * @return le succès de l'opération d'annulation
   */
  public String cancel() {
    if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_THIRD_PARTY) != null) {
      return CONSULTATION_NAVIGATION_CODE;
    }
    return "EXIT_THIRD_PARTY";
  }

  /**
   * Effectue la sauvegarde du tiers
   * @return la redirection
   */
  private String saveData() {
    try {
      long idThirdParty = thirdPartyService.create(thirdPartyBean.getThirdPartyDTO());
      FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_THIRD_PARTY, idThirdParty);
    } catch (ThirdPartyServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde d'un tiers", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdparty.creation.save.error"));
    }
    return CONSULTATION_NAVIGATION_CODE;
  }

  /**
   * Effectue la mise à jour du logement
   * @return la redirection
   */
  private String updateData() {
    try {
      thirdPartyService.update(thirdPartyBean.getThirdPartyDTO());
      FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_THIRD_PARTY, thirdPartyBean.getThirdPartyDTO().getId());
    } catch (ThirdPartyServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la mise à jour du tiers", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("thirdparty.creation.save.error"));
    }
    return CONSULTATION_NAVIGATION_CODE;
  }

  /**
   *
   * GETTER/SETTER
   *
   *
   * **/

  /**
   * Getter du backing bean de la gestion des tiers
   * @return the thirdPartyBean
   */
  public ThirdPartyBean getThirdPartyBean() {
    return thirdPartyBean;
  }

  /**
   * Setter du backing bean de la gestion des tiers
   * @param thirdPartyBean the thirdPartyBean to set
   */
  public void setThirdPartyBean(ThirdPartyBean thirdPartyBean) {
    this.thirdPartyBean = thirdPartyBean;
  }

  /**
   * @param thirdPartyService the thirdPartyService to set
   */
  public void setThirdPartyService(IThirdPartyService thirdPartyService) {
    this.thirdPartyService = thirdPartyService;
  }

}
