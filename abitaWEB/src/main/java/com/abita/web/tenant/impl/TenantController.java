/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.tenant.impl;

import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.historytenant.IHistoryTenantService;
import com.abita.services.tenant.ITenantServiceFacade;
import com.abita.services.typetenant.ITypeTenantService;
import com.abita.web.tenant.bean.TenantBean;
import com.abita.dto.TenantDTO;
import com.abita.dto.TypeTenantDTO;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.historytenant.exceptions.HistoryTenantServiceException;
import com.abita.services.tenant.exceptions.TenantServiceFacadeException;
import com.abita.services.typetenant.exceptions.TypeTenantServiceException;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import javax.annotation.PostConstruct;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import java.util.List;

/**
 * Classe controleur de la page des gestion des occupants
 *
 * @author
 */
public class TenantController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 9206615377262851587L;

  /** Backing bean de la gestion des occupants */
  private TenantBean tenantBean;

  /** Service pour la gestion des occupants */
  private transient ITenantServiceFacade tenantServiceFacade;

  /** Service pour l’historisation des occupants */
  private transient IHistoryTenantService historyTenantService;

  /** Service pour la gestion des occupants */
  private transient IContractServiceFacade contractServiceFacade;

  /** Service pour la gestion des types d'occupants */
  private transient ITypeTenantService typeTenantService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TenantController.class);

  /** Message lors d’une erreur de récupération des types d’occupant */
  private static final String GET_TYPES_TENANT_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération des types d'occupant";

  /** Message lors d’une erreur de récupération du logement */
  private static final String GET_HOUSINGS_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération de logement";

  /** Message lors d’une erreur de récupération de l’occupant */
  private static final String GET_TENANT_ERROR_MESSAGE = "Une erreur est survenue lors de la récupération de l'occupant";

  /** Message lors d’une erreur de mise à jour des contrats du logement */
  private static final String UPDATE_CONTRACTS_ERROR_MESSAGE = "Une erreur est survenue lors de la mise à jour des contrats du logement";

  /** Message lors d’une erreur de mise à jour de l’historisation de l’occupant */
  private static final String HISTORIZE_TENANT_ERROR_MESSAGE = "Une erreur est survenue lors de la mise à jour de l’historisation de l’occupant";

  /** Message lors d’une erreur de création de l’occupant */
  private static final String CREATE_TENANT_ERROR_MESSAGE = "Une erreur est survenue lors de la création de l'occupant";

  /** Message lors d’une erreur de suppresion de l’occupant */
  private static final String DELETE_TENANT_ERROR_MESSAGE = "Une erreur est survenue lors de la suppression de l'occupant";

  /** Message lors d’une erreur de vérification de la suppression de l’occupant */
  private static final String VERIFYING_DELETION_ERROR_MESSAGE = "Une erreur est survenue lors de la vérification de la suppression d'un occupant";

  /** Erreur si un utilisateur ouvre plusieurs onglets (Ecrasement des informations) */
  private static final String TECHNICAL_MULTIPLE_TABS_ERROR_CODE = "Impossible de sauvegarder. Plusieurs onglets ou fenêtres sont ouverts";

  /** Code pour l’erreur technique */
  private static final String TECHNICAL_ERROR = "technical.error";

  /** Code pour l’erreur de référence existante */
  private static final String EXIST_TENANT_ERROR_CODE = "tenant.creation.error.existing.reference";

  /** Code pour l’erreur de suppression */
  private static final String DELETE_TENANT_ERROR_CODE = "tenant.result.delete.error";

  /** Code pour la suppression */
  private static final String DELETE_TENANT_CODE = "tenant.result.delete";

  /** Code du paramètres de création */
  private static final String CREATE_PARAMETER = "create";

  /** Code de navigation vers la page de sortie */
  private static final String EXIT_NAVIGATION_CODE = "EXIT_TENANT";

  /** Code de navigation vers la page de consultation */
  private static final String CONSULTATION_NAVIGATION_CODE = "GO_TO_CONSULTATION";

  /** Code de navigation vers la page de modification */
  private static final String MODIFICATION_NAVIGATION_CODE = "GO_TO_MODIFICATION";

  /** Identifiant du champ caché dans la page de modification des occupants */
  private static final String MODIFICATION_HIDDEN_INPUT_ID = "frmUpdateTenant:checkId";

  /** Identifiant du champ caché dans la page de création des occupants */
  private static final String CREATE_HIDDEN_INPUT_ID = "frmCreateTenant:checkId";


  /**
   * Méthode d'initialisation des beans
   */
  @PostConstruct
  public void init() {
    // Détermine le mode : Création ou Modification
    String action = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(CREATE_PARAMETER);

    if (StringUtils.isEmpty(action)) {
      Long idTenant = null;

      if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_TENANT) != null) {
        idTenant = ((Number) FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_TENANT)).longValue();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().keep(ConstantsWEB.ID_TENANT);
      } else {
        // Cas de la suppression qui effectue un post dont on sait pas d'où il sort et qui pert la variable flash
        if (tenantBean.getTenantDTO().getId() != null) {
          idTenant = tenantBean.getTenantDTO().getId();
          FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_TENANT, idTenant);
        }
      }

      if (idTenant != null) {
        initTenant(idTenant);
      }

      // On récupére le champ  caché dans la page de modification
      HtmlInputHidden checkId = (HtmlInputHidden) FacesContext.getCurrentInstance().getViewRoot().findComponent(MODIFICATION_HIDDEN_INPUT_ID);
      if (checkId != null) {
        checkId.setValue(String.valueOf(tenantBean.getTenantDTO().getId()));
      }
    } else {
      tenantBean.setTenantDTO(new TenantDTO());

      // On récupére le champ  caché dans la page de création
      HtmlInputHidden checkId = (HtmlInputHidden) FacesContext.getCurrentInstance().getViewRoot().findComponent(CREATE_HIDDEN_INPUT_ID);
      if (checkId != null) {
        checkId.setValue(String.valueOf(tenantBean.getTenantDTO().getId()));
      }
    }
  }

  /**
   * Initialisation de l’occupant
   * @param idTenant identifiant de l’occupant
   */
  private void initTenant(Long idTenant) {
    try {
      tenantBean.setTenantDTO(tenantServiceFacade.get(idTenant));
      // On recupere la reference NNI pour pouvoir verifier si elle a été modifié
      if (ConstantsWEB.TENANT_TYPE_SALARIED.equals(tenantBean.getTenantDTO().getTypeTenant().getLabel())
        || ConstantsWEB.TENANT_TYPE_RETIRED.equals(tenantBean.getTenantDTO().getTypeTenant().getLabel())) {
        tenantBean.setReferenceNNI(tenantBean.getTenantDTO().getReference());
      }
      tenantBean.setTenantSalaried(tenantBean.getTenantDTO().getTypeTenant().getLabel().equals(ConstantsWEB.TENANT_TYPE_SALARIED));
      tenantBean.setTenantNotSalaried(tenantBean.getTenantDTO().getTypeTenant().getLabel().equals(ConstantsWEB.TENANT_TYPE_NOT_SALARIED));
      tenantBean.setContractList(contractServiceFacade.findContractByTenant(idTenant));
    } catch (TenantServiceFacadeException ex) {
      LOGGER.error(GET_TENANT_ERROR_MESSAGE, ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR));
    } catch (ContractServiceFacadeException e) {
      LOGGER.error(GET_HOUSINGS_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR));
    }
  }

  /**
   * Récupère la liste des types d'occupant
   * @return la liste des types d'occupant
   */
  public List<TypeTenantDTO> getAllTypeTenant() {
    if (tenantBean.getLstTypeTenant() == null || tenantBean.getLstTypeTenant().isEmpty()) {
      try {
        tenantBean.setLstTypeTenant(typeTenantService.find());
      } catch (TypeTenantServiceException ex) {
        LOGGER.error(GET_TYPES_TENANT_ERROR_MESSAGE, ex);
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR));
      }
    }
    return tenantBean.getLstTypeTenant();
  }

  /**
   * Permet de savoir si l'occupant est supprimable
   * @return true si l'occupant n'est pas référencé dans l'application, false autrement
   */
  public boolean isRemovable() {
    boolean isRemovable = true;

    try {
      isRemovable = tenantServiceFacade.isRemovable(tenantBean.getTenantDTO().getId());
    } catch (TenantServiceFacadeException e) {
      LOGGER.error(VERIFYING_DELETION_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR));
    }

    return isRemovable;
  }

  /**
   * Permet de sauvegarder un occupant
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
    if (checkId != null && checkId.getValue().equals(String.valueOf(tenantBean.getTenantDTO().getId()))) {

      String redirection = CONSULTATION_NAVIGATION_CODE;
      if (tenantBean.getTenantDTO().getId() != null) {
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
    return MODIFICATION_NAVIGATION_CODE;
  }

  /**
   * Permet de supprimer un occupant
   *
   * @return le résultat de l'action
   */
  public String deleteData() {
    try {
      tenantServiceFacade.delete(tenantBean.getTenantDTO().getId());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage(DELETE_TENANT_CODE));
      FacesContext.getCurrentInstance().getExternalContext().getFlash().remove(ConstantsWEB.ID_TENANT);
    } catch (TenantServiceFacadeException ex) {
      LOGGER.error(DELETE_TENANT_ERROR_MESSAGE, ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(DELETE_TENANT_ERROR_CODE, tenantBean.getTenantDTO().getId()));
    }

    return EXIT_NAVIGATION_CODE;
  }

  /**
   * Action du bouton « Annuler »
   * Comme on peut arriver de la modification, avant d'annuler, on doit supprimer l'état de modification
   *
   * @return le clé où aller
   */
  public String cancel() {
    if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get(ConstantsWEB.ID_TENANT) != null) {
      return CONSULTATION_NAVIGATION_CODE;
    }
    return EXIT_NAVIGATION_CODE;
  }

  /**
   * Effectue la sauvegarde de l'occupant
   * @return le résultat de l'action
   */
  private String saveData() {
    try {
      // On vérifie que l'occupant salarié n'existe pas par sa référence NNI
      if (null != tenantServiceFacade.findTenantByNNI(tenantBean.getTenantDTO().getReference())
        && (ConstantsWEB.TENANT_TYPE_SALARIED.equals(tenantBean.getTenantDTO().getTypeTenant().getLabel()) || ConstantsWEB.TENANT_TYPE_RETIRED.equals(tenantBean.getTenantDTO()
          .getTypeTenant().getLabel()))) {
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(EXIST_TENANT_ERROR_CODE, tenantBean.getTenantDTO().getReference()));
        tenantBean.setTenantDTO(tenantBean.getTenantDTO());
        return null;
      } else {
        long idTenant = tenantServiceFacade.create(tenantBean.getTenantDTO());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_TENANT, idTenant);
      }
    } catch (TenantServiceFacadeException ex) {
      LOGGER.error(CREATE_TENANT_ERROR_MESSAGE, ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR));
    }
    return CONSULTATION_NAVIGATION_CODE;
  }

  /**
   * Effectue la mise à jour de l'occupant
   * @return le résultat de l'action
   */
  private String updateData() {
    try {
      // On verifie si la reference NNi a changé
      if (tenantBean.getTenantDTO().getReference().equals(tenantBean.getReferenceNNI())
        && (ConstantsWEB.TENANT_TYPE_SALARIED.equals(tenantBean.getTenantDTO().getTypeTenant().getLabel()) || ConstantsWEB.TENANT_TYPE_RETIRED.equals(tenantBean.getTenantDTO()
          .getTypeTenant().getLabel()))) {
        tenantServiceFacade.update(tenantBean.getTenantDTO());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_TENANT, tenantBean.getTenantDTO().getId());

        historyTenantService.updateTemporaries(tenantBean.getTenantDTO());
        contractServiceFacade.updateContractsForTenant(tenantBean.getTenantDTO().getId());

        // On vérifie que l'occupant salarié n'existe pas par sa référence NNI
      } else if (null != tenantServiceFacade.findTenantByNNI(tenantBean.getTenantDTO().getReference())
        && (ConstantsWEB.TENANT_TYPE_SALARIED.equals(tenantBean.getTenantDTO().getTypeTenant().getLabel()) || ConstantsWEB.TENANT_TYPE_RETIRED.equals(tenantBean.getTenantDTO()
          .getTypeTenant().getLabel()))) {
        FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(EXIST_TENANT_ERROR_CODE, tenantBean.getTenantDTO().getReference()));
        tenantBean.setTenantDTO(tenantBean.getTenantDTO());
        return null;
      } else {
        tenantServiceFacade.update(tenantBean.getTenantDTO());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(ConstantsWEB.ID_TENANT, tenantBean.getTenantDTO().getId());

        historyTenantService.updateTemporaries(tenantBean.getTenantDTO());
        contractServiceFacade.updateContractsForTenant(tenantBean.getTenantDTO().getId());
      }
    } catch (TenantServiceFacadeException ex) {
      LOGGER.error(CREATE_TENANT_ERROR_MESSAGE, ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR));
    } catch (HistoryTenantServiceException e) {
      LOGGER.error(HISTORIZE_TENANT_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR));
    } catch (ContractServiceFacadeException e) {
      LOGGER.error(UPDATE_CONTRACTS_ERROR_MESSAGE, e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR));
    }
    return CONSULTATION_NAVIGATION_CODE;
  }

  /**
   * Recupere le texte de l'option selectionné dans le select « type »
   * et la compare avec la constante type occupant salarié ou type occupant retraité
   * @param event un evenement lors du changement de type salarié
   */
  public void setMandatoryFields(final AjaxBehaviorEvent event) {
    SelectOneMenu select = (SelectOneMenu) event.getComponent();
    TypeTenantDTO type = (TypeTenantDTO) select.getValue();
    tenantBean.setTenantSalaried(ConstantsWEB.TENANT_TYPE_SALARIED.equals(type.getLabel()));
    tenantBean.setTenantSalariedRequired(tenantBean.isTenantSalaried());
    tenantBean.setTenantNotSalaried(ConstantsWEB.TENANT_TYPE_NOT_SALARIED.equals(type.getLabel()));
    tenantBean.setTenantNotSalariedRequired(tenantBean.isTenantNotSalaried());
  }

  /**
   * Permet de savoir en modification si les champs sont obligatoires
   * @return true si obligatoire, false autrement
   */
  public boolean checkRequired() {
    if (tenantBean.getTenantSalariedRequired() == null) {
      tenantBean.setTenantSalariedRequired(tenantBean.isTenantSalaried());
    }
    return tenantBean.getTenantSalariedRequired();
  }

  /**
   * Permet de savoir en modification si les champs sont obligatoires
   * @return true si obligatoire, false autrement
   */
  public boolean checkRequiredNotSalaried() {
    if (tenantBean.getTenantNotSalariedRequired() == null) {
      tenantBean.setTenantNotSalariedRequired(tenantBean.isTenantNotSalaried());
    }
    return tenantBean.getTenantNotSalariedRequired();
  }

  /**
   * @return the tenantBean
   */
  public TenantBean getTenantBean() {
    return tenantBean;
  }

  /**
   * @param tenantBean the tenantBean to set
   */
  public void setTenantBean(TenantBean tenantBean) {
    this.tenantBean = tenantBean;
  }

  /**
   * @param historyTenantService the historyTenantService to set
   */
  public void setHistoryTenantService(IHistoryTenantService historyTenantService) {
    this.historyTenantService = historyTenantService;
  }

  /**
   * @param typeTenantService the typeTenantService to set
   */
  public void setTypeTenantService(ITypeTenantService typeTenantService) {
    this.typeTenantService = typeTenantService;
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

  public void setTenantServiceFacade(ITenantServiceFacade tenantServiceFacade) {
    this.tenantServiceFacade = tenantServiceFacade;
  }

}
