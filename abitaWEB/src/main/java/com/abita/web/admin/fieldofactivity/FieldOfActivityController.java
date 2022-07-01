/**
 *
 */
package com.abita.web.admin.fieldofactivity;

import com.abita.services.agency.IAgencyService;
import com.abita.services.fieldofactivity.IFieldOfActivityService;
import com.abita.util.comparator.FieldOfActivityDTOLabelComparator;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.services.fieldofactivity.exceptions.FieldOfActivityServiceException;
import com.abita.web.admin.fieldofactivity.beans.FieldOfActivityBean;
import com.abita.web.shared.AbstractGenericController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.Collections;

/**
 * Controlleur en relation avec la page /pages/administration/referentiel/fieldofactivity.xhtml
 *
 * @author
 */
public class FieldOfActivityController extends AbstractGenericController {

  /** SerialVersionUID */
  private static final long serialVersionUID = -2753311425420029318L;

  /** Bean des données des domaines d'activité */
  private FieldOfActivityBean fieldOfActivityBean;

  /** Service gérant les domaines d'activité */
  private transient IFieldOfActivityService fieldofactivityService;

  /** Service gérant les agences */
  private transient IAgencyService agencyService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(FieldOfActivityController.class);

  /**
   * Initialisation du controlleur afin de récupérer la liste des domaines d'activité
   */
  @PostConstruct
  public void init() {
    findAllFieldOfActivity();
    findAllAgencies();
  }

  /**
   * Ajoute le domaine d'activité à la liste
   * @param actionEvent : L'ActionEvent
   */
  public void addFieldOfActivityToList(ActionEvent actionEvent) {
    if (checkIfFieldOfActivityExists()) {
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.fieldofactivity.error.not.unique"));
    } else {
      FieldOfActivityDTO fieldOfActivity = new FieldOfActivityDTO();
      // Génère un ID temporaire afin de pouvoir le supprimer si on a pas encore sauvegardé la valeur en BDD
      fieldOfActivity.setId(System.currentTimeMillis());
      fieldOfActivity.setLabel(fieldOfActivityBean.getFieldOfActivity());
      fieldOfActivity.setGmr(fieldOfActivityBean.getGmr());
      fieldOfActivity.setAgency(fieldOfActivityBean.getAgency());
      fieldOfActivity.setRemovable(true);
      fieldOfActivityBean.getLstFieldOfActivity().add(fieldOfActivity);
      Collections.sort(fieldOfActivityBean.getLstFieldOfActivity(), new FieldOfActivityDTOLabelComparator());
    }
    fieldOfActivityBean.setFieldOfActivity(null);
    fieldOfActivityBean.setGmr(null);
    fieldOfActivityBean.setAgency(null);
  }

  /**
   * Sur modification, vérification de l'unicité d'un domaine d'activité
   * @param event l'évènement qui déclenche l'action
   */
  public void onEdit(RowEditEvent event) {
    int nb = 0;

    String label = ((FieldOfActivityDTO) event.getObject()).getLabel();
    for (FieldOfActivityDTO fieldofactivity : fieldOfActivityBean.getLstFieldOfActivity()) {
      if (fieldofactivity.getLabel().equalsIgnoreCase(label)) {
        nb++;
      }
    }

    if (nb > 1) {
      ((FieldOfActivityDTO) event.getObject()).setLabel("Copie de " + label);
      FacesContext.getCurrentInstance().addMessage(null, getWarningMessage("administration.referentiel.fieldofactivity.warning.not.unique"));
    }
  }

  /**
   * Sauvegarde en base de données l'intégralité des données
   * @param actionEvent : L'ActionEvent
   */
  public void saveData(ActionEvent actionEvent) {
    try {
      fieldofactivityService.saveListing(fieldOfActivityBean.getLstFieldOfActivity());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("administration.referentiel.fieldofactivity.success"));
    } catch (FieldOfActivityServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde des domaines d'activité", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    }
  }

  /**
   * Récupère la liste des domaines d'activité.
   */
  private void findAllFieldOfActivity() {
    try {
      fieldOfActivityBean.setLstFieldOfActivity(fieldofactivityService.findAllFieldOfActivity());
    } catch (FieldOfActivityServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération des domaines d'activité", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.fieldofactivity.error.listing"));
    }
  }

  /**
   * Récupère la liste des domaines d'activité.
   */
  private void findAllAgencies() {
    try {
      fieldOfActivityBean.setAgencies(agencyService.findAllAgencies());
    } catch (AgencyServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération des domaines d'activité", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.fieldofactivity.error.listing"));
    }
  }

  /**
   * Méthode permettant de savoir si le domaine d'activité existe déjà
   * @return true si existe, false autrement
   */
  private boolean checkIfFieldOfActivityExists() {
    boolean fieldofactivityExists = false;

    String label = fieldOfActivityBean.getFieldOfActivity();
    for (FieldOfActivityDTO fieldofactivity : fieldOfActivityBean.getLstFieldOfActivity()) {
      if (fieldofactivity.getLabel().equalsIgnoreCase(label)) {
        fieldofactivityExists = true;
        break;
      }
    }

    return fieldofactivityExists;
  }

  /**
   * @return the fieldofactivityBean
   */
  public FieldOfActivityBean getFieldOfActivityBean() {
    return fieldOfActivityBean;
  }

  /**
   * @param fieldOfActivityBean the fieldOfActivityBean to set
   */
  public void setFieldOfActivityBean(FieldOfActivityBean fieldOfActivityBean) {
    this.fieldOfActivityBean = fieldOfActivityBean;
  }

  /**
   * @param fieldOfActivityService the fieldOfActivityService to set
   */
  public void setFieldOfActivityService(IFieldOfActivityService fieldOfActivityService) {
    fieldofactivityService = fieldOfActivityService;
  }

  /**
   * @param agencyService the agencyService to set
   */
  public void setAgencyService(IAgencyService agencyService) {
    this.agencyService = agencyService;
  }

}
