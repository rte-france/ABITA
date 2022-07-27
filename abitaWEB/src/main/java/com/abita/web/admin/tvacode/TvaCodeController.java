/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.tvacode;

import com.abita.services.tvacode.ITvaCodeService;
import com.abita.util.comparator.TvaCodeComparator;
import com.abita.dto.TvaCodeDTO;
import com.abita.services.tvacode.exceptions.TvaCodeServiceException;
import com.abita.web.admin.tvacode.beans.TvaCodeBean;
import com.abita.web.shared.AbstractGenericController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.Collections;

/**
 * Classe controleur de la page de gestion des codes TVA
 *
 * @author
 */
public class TvaCodeController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 2824380877781501162L;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TvaCodeController.class);

  /** Interface de services des codes TVA */
  private transient ITvaCodeService tvaCodeService;

  /** Backing bean de la page de gestion des codes TVA */
  private TvaCodeBean tvaCodeBean;

  /**
   * Méthode d'initialisation de la page
   */
  @PostConstruct
  public void init() {
    findAllTvaCode();
    tvaCodeBean.setTvaCode(new TvaCodeDTO());
  }

  /**
   * Ajoute le le code TVA à la liste
   *
   * @param actionEvent : L'ActionEvent
   */
  public void addTvaCodeToList(ActionEvent actionEvent) {
    if (checkIfTvaCode()) {
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.tvacode.error.not.unique"));
    } else {
      TvaCodeDTO tvaCodeDTO = new TvaCodeDTO();
      // Génère un ID temporaire afin de pouvoir le supprimer si on a pas encore sauvegardé la valeur en BDD
      tvaCodeDTO.setId(System.currentTimeMillis());
      tvaCodeDTO.setCode(tvaCodeBean.getTvaCode().getCode());
      tvaCodeDTO.setLabel(tvaCodeBean.getTvaCode().getLabel());

      tvaCodeBean.getLstTvaCode().add(tvaCodeDTO);
      Collections.sort(tvaCodeBean.getLstTvaCode(), new TvaCodeComparator());
    }
    tvaCodeBean.setTvaCode(new TvaCodeDTO());
  }

  /**
   * Sauvegarde en base de données l'intégralité des données
   * @param actionEvent : L'ActionEvent
   */
  public void saveData(ActionEvent actionEvent) {
    try {
      tvaCodeService.saveListing(tvaCodeBean.getLstTvaCode());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("administration.referentiel.tvacode.success"));
    } catch (TvaCodeServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde des codes TVA", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    } catch (Exception ex) {
      // ERREUR POUR LE CAS D'UN CODE TVA DEJA EXISTANT
      LOGGER.error("Un code TVA est incorrect", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.tvacode.error.save"));
    }
  }

  /**
   * Permet de récupérer la totalité des codes TVA
   */
  private void findAllTvaCode() {
    try {
      tvaCodeBean.setLstTvaCode(tvaCodeService.findAllTvaCode());
    } catch (TvaCodeServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération de la liste des codes TVA", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.tvacode.error.listing"));
    }
  }

  /**
   * Méthode permettant de savoir si le code TVA existe déjà
   *
   * @return true si existe, false autrement
   */
  private boolean checkIfTvaCode() {
    boolean tvaCodeExists = false;

    String code = tvaCodeBean.getTvaCode().getCode();
    for (TvaCodeDTO tvaCode : tvaCodeBean.getLstTvaCode()) {
      if (tvaCode.getCode().equals(code)) {
        tvaCodeExists = true;
        break;
      }
    }

    return tvaCodeExists;
  }

  /**
   * @return the tvaCodeBean
   */
  public TvaCodeBean getTvaCodeBean() {
    return tvaCodeBean;
  }

  /**
   * @param tvaCodeBean the tvaCodeBean to set
   */
  public void setTvaCodeBean(TvaCodeBean tvaCodeBean) {
    this.tvaCodeBean = tvaCodeBean;
  }

  /**
   * @param tvaCodeService the tvaCodeService to set
   */
  public void setTvaCodeService(ITvaCodeService tvaCodeService) {
    this.tvaCodeService = tvaCodeService;
  }

}
