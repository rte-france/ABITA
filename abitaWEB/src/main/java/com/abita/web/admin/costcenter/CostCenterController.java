package com.abita.web.admin.costcenter;

import com.abita.services.costcenter.ICostCenterService;
import com.abita.util.comparator.CostCenterDTOLabelComparator;
import com.abita.dto.CostCenterDTO;
import com.abita.services.costcenter.exceptions.CostCenterServiceException;
import com.abita.web.admin.costcenter.beans.CostCenterBean;
import com.abita.web.shared.AbstractGenericController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.Collections;

/**
 * Controlleur en relation avec la page /pages/administration/referentiel/costcenter.xhtml
 *
 * @author
 */
public class CostCenterController extends AbstractGenericController {

  /** SerialVersionUID */
  private static final long serialVersionUID = 5039575391967375831L;

  /** Bean pour la supervision des flux de Convergence */
  private CostCenterBean costcenterBean;

  /** Service gérant les centres coût */
  private transient ICostCenterService costcenterService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(CostCenterController.class);

  /**
   * Initialisation du controlleur afin de récupérer la liste des centres coûts
   */
  @PostConstruct
  public void init() {
    findAllCostCenter();
  }

  /**
   * Ajoute le centre de coût à la liste
   * @param actionEvent : L'ActionEvent
   */
  public void addCostCenterToList(ActionEvent actionEvent) {
    if (checkIfCostCenterExists()) {
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.costcenter.error.not.unique"));
    } else {
      CostCenterDTO costcenter = new CostCenterDTO();
      // Génère un ID temporaire afin de pouvoir le supprimer si on a pas encore sauvegardé la valeur en BDD
      costcenter.setId(System.currentTimeMillis());
      costcenter.setLabel(costcenterBean.getCostcenter());
      costcenter.setRemovable(true);
      costcenterBean.getLstCostCenter().add(costcenter);
      Collections.sort(costcenterBean.getLstCostCenter(), new CostCenterDTOLabelComparator());
    }
    costcenterBean.setCostcenter(null);
  }

  /**
   * Sur modification, vérification de l'unicité d'un centre coût
   * @param event l'évènement qui déclenche l'action
   */
  public void onEdit(RowEditEvent event) {
    int nb = 0;

    String label = ((CostCenterDTO) event.getObject()).getLabel();
    for (CostCenterDTO costcenter : costcenterBean.getLstCostCenter()) {
      if (costcenter.getLabel().equalsIgnoreCase(label)) {
        nb++;
      }
    }

    if (nb > 1) {
      ((CostCenterDTO) event.getObject()).setLabel("Copie de " + label);
      FacesContext.getCurrentInstance().addMessage(null, getWarningMessage("administration.referentiel.costcenter.warning.not.unique"));
    }
  }

  /**
   * Sauvegarde en base de données l'intégralité des données
   * @param actionEvent : L'ActionEvent
   */
  public void saveData(ActionEvent actionEvent) {
    try {
      costcenterService.saveListing(costcenterBean.getLstCostCenter());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("administration.referentiel.costcenter.success"));
    } catch (CostCenterServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde des centres coûts", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    }
  }

  /**
   * Récupère la liste des centres coûts.
   */
  private void findAllCostCenter() {
    try {
      costcenterBean.setLstCostCenter(costcenterService.findAllCostCenter());
    } catch (CostCenterServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération des centres coûts", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.costcenter.error.listing"));
    }
  }

  /**
   * Méthode permettant de savoir si le centre de coût existe déjà
   * @return true si existe, false autrement
   */
  private boolean checkIfCostCenterExists() {
    boolean costcenterExists = false;

    String label = costcenterBean.getCostcenter();
    for (CostCenterDTO costcenter : costcenterBean.getLstCostCenter()) {
      if (costcenter.getLabel().equalsIgnoreCase(label)) {
        costcenterExists = true;
        break;
      }
    }

    return costcenterExists;
  }

  /**
   * @return the costcenterBean
   */
  public CostCenterBean getCostcenterBean() {
    return costcenterBean;
  }

  /**
   * @param costcenterBean the costcenterBean to set
   */
  public void setCostcenterBean(CostCenterBean costcenterBean) {
    this.costcenterBean = costcenterBean;
  }

  /**
   * @param costcenterService the costcenterService to set
   */
  public void setCostcenterService(ICostCenterService costcenterService) {
    this.costcenterService = costcenterService;
  }

}
