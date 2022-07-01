package com.abita.web.admin.revaluationrents;

import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.revaluationrents.IRevaluationRentsService;
import com.abita.dto.RevaluationRentsDTO;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.revaluationrents.exceptions.RevaluationRentsServiceException;
import com.abita.web.admin.revaluationrents.beans.RevaluationRentsBean;
import com.abita.web.shared.AbstractGenericController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * Classe controleur de la page de revalorisation des loyers
 */
public class RevaluationRentsController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = -1757789891402466806L;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(RevaluationRentsController.class);

  /** Backing bean de la page de revalorisation des loyers */
  private RevaluationRentsBean revaluationRentsBean;

  /** Service pour les revalorisations de loyer */
  private transient IRevaluationRentsService revaluationRentsService;

  /** Service pour les contrats occupant */
  private transient IContractServiceFacade contractServiceFacade;

  /** Nom de code pour l’erreur technique */
  private static final String TECHNICAL_ERROR_CODE = "technical.error";

  /**
   * Initialisation du controlleur afin de récupérer la revalorisation des loyers
   */
  @PostConstruct
  public void init() {
    try {
      revaluationRentsBean.setRevaluationRentsDTO(revaluationRentsService.get(1L));
    } catch (RevaluationRentsServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération de la revalorisation des loyers", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * Sauvegarde en base de données l'intégralité des données
   * @param actionEvent : L'ActionEvent
   */
  public void saveData(ActionEvent actionEvent) {
    try {
      RevaluationRentsDTO revaluationRentsDTO = revaluationRentsBean.getRevaluationRentsDTO();
      revaluationRentsService.update(revaluationRentsDTO);
      contractServiceFacade.applyRevaluationRent(revaluationRentsDTO);
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("administration.referentiel.revaluationrents.success"));

      revaluationRentsBean.setRevaluationRentsDTO(revaluationRentsService.get(1L));
    } catch (RevaluationRentsServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la mise à jour du taux de revalorisation de loyers", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    } catch (ContractServiceFacadeException ex) {
      LOGGER.error("Une erreur est survenue lors de la mise à jour des loyers", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * @return the revaluationRentsBean
   */
  public RevaluationRentsBean getRevaluationRentsBean() {
    return revaluationRentsBean;
  }

  /**
   * @param revaluationRentsBean the revaluationRentsBean to set
   */
  public void setRevaluationRentsBean(RevaluationRentsBean revaluationRentsBean) {
    this.revaluationRentsBean = revaluationRentsBean;
  }

  /**
   * @return the revaluationRentsService
   */
  public IRevaluationRentsService getRevaluationRentsService() {
    return revaluationRentsService;
  }

  /**
   * @param revaluationRentsService the revaluationRentsService to set
   */
  public void setRevaluationRentsService(IRevaluationRentsService revaluationRentsService) {
    this.revaluationRentsService = revaluationRentsService;
  }

  /**
   * @return the contractServiceFacade
   */
  public IContractServiceFacade getContractServiceFacade() {
    return contractServiceFacade;
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

}
