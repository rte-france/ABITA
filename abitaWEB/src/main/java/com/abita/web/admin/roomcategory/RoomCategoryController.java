/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.roomcategory;

import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.roomcategory.IRoomCategoryService;
import com.abita.dto.RoomCategoryDTO;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.roomcategory.exceptions.RoomCategoryServiceException;
import com.abita.web.admin.roomcategory.beans.RoomCategoryBean;
import com.abita.web.shared.AbstractGenericController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * Classe controleur de la page de gestion des catégories de local
 * @author
 *
 */
public class RoomCategoryController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 7429953788349086081L;

  /** Backing bean de la page de gestion des catégories de local */
  private RoomCategoryBean roomCategoryBean;

  /** Interface de services des catégories de local */
  private transient IRoomCategoryService roomCategoryService;

  /** Service pour la gestion des contrats occupant */
  private transient IContractServiceFacade contractServiceFacade;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(RoomCategoryController.class);

  /**
   * Méthode d'initialisation de la page
   */
  @PostConstruct
  public void init() {
    findAllRoomCategroy();
    roomCategoryBean.setRoomCategory(new RoomCategoryDTO());
    for (RoomCategoryDTO roomCategory : roomCategoryBean.getLstRoomCategory()) {
      if ("Autre".equals(roomCategory.getLabel())) {
        roomCategory.setModifiable(false);
      } else {
        roomCategory.setModifiable(true);
      }
    }
  }

  /**
   * Sauvegarde en base de données l'intégralité des données
   * @param actionEvent : L'ActionEvent
   */
  public void saveData(ActionEvent actionEvent) {
    try {
      roomCategoryService.saveListing(roomCategoryBean.getLstRoomCategory());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("administration.referentiel.roomcategory.success"));

      contractServiceFacade.updateContractsInProgress();
    } catch (RoomCategoryServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde des catégories de local", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Une erreur est survenue lors de la mise à jour des contrats en cours", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    }
  }

  /**
   * Permet de récupérer la totalité des codes comptables
   */
  private void findAllRoomCategroy() {
    try {
      roomCategoryBean.setLstRoomCategory(roomCategoryService.find());
    } catch (RoomCategoryServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération de la liste des catégories de local", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.roomcategory.error.listing"));
    }
  }

  /**
   * @param roomCategoryService the roomCategoryService to set
   */
  public void setRoomCategoryService(IRoomCategoryService roomCategoryService) {
    this.roomCategoryService = roomCategoryService;
  }

  /**
   * @return the roomCategoryBean
   */
  public RoomCategoryBean getRoomCategoryBean() {
    return roomCategoryBean;
  }

  /**
   * @param roomCategoryBean the roomCategoryBean to set
   */
  public void setRoomCategoryBean(RoomCategoryBean roomCategoryBean) {
    this.roomCategoryBean = roomCategoryBean;
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }
}
