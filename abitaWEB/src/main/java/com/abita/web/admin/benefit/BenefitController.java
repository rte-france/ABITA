/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.benefit;

import com.abita.services.benefit.IBenefitService;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.util.decorator.SalaryRangeDecorator;
import com.abita.util.exceptions.UtilException;
import com.abita.dto.BenefitDTO;
import com.abita.dto.SalaryLevelDTO;
import com.abita.services.benefit.exceptions.BenefitServiceException;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.web.admin.benefit.beans.BenefitBean;
import com.abita.web.shared.AbstractGenericController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * @author
 * @version 1.0
 */
public class BenefitController extends AbstractGenericController {

  /** SerialID */
  private static final long serialVersionUID = -8809743248762440717L;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(BenefitController.class);

  /** Bean pour les avantages en nature */
  private BenefitBean benefitBean;

  /** Service gérant les avantages en nature */
  private transient IBenefitService benefitService;

  /** Service pour la gestion des contrats occupant */
  private transient IContractServiceFacade contractServiceFacade;

  /**
   * Initialisation du controlleur afin de récupérer la liste des centres coûts
   */
  @PostConstruct
  public void init() {
    initBeanValues();
    findAllSorterSalaryRange();

  }

  /**
   *
   */
  private void findAllSorterSalaryRange() {
    try {
      benefitBean.setBenefitList(benefitService.findAllSortedSalaryRange());
    } catch (BenefitServiceException ex) {
      LOGGER.error("Une erreur est survenue lors des barèmes pour les avantages en nature", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.benefit.error.listing"));
    }
  }

  /**
   *
   */
  private void initBeanValues() {
    BenefitDTO emptyBenefitDTO = new BenefitDTO();
    SalaryLevelDTO emptySalaryLevelDTO = new SalaryLevelDTO();
    emptySalaryLevelDTO.setBenefit(emptyBenefitDTO);

    benefitBean.setEditedSalaryLevel(emptySalaryLevelDTO);
  }

  /**
   * Ajoute un barème a la liste des barème si les saisies utilisateur sont valides
   * @param actionEvent Event
   */
  public void addToList(ActionEvent actionEvent) {
    try {
      SalaryLevelDTO newSalaryLevelDTO = new SalaryLevelDTO();
      BenefitDTO benefitDTO = new BenefitDTO();

      benefitDTO.setBenefitForOneRoom(benefitBean.getEditedSalaryLevel().getBenefit().getBenefitForOneRoom());
      benefitDTO.setBenefitForManyRooms(benefitBean.getEditedSalaryLevel().getBenefit().getBenefitForManyRooms());
      newSalaryLevelDTO.setMinimumThreshold(benefitBean.getEditedSalaryLevel().getMinimumThreshold());
      newSalaryLevelDTO.setId(System.currentTimeMillis());
      newSalaryLevelDTO.setBenefit(benefitDTO);

      benefitBean.getBenefitList().sortedInsert(new SalaryRangeDecorator(newSalaryLevelDTO, benefitBean.getBenefitList()));

      // Une fois le barème ajouté, on réinitialise le bean pour l'ajout de nouveaux barèmes
      initBeanValues();

    } catch (UtilException.ThresholdAlreadyUsed e) {
      LOGGER.error(e.getMessage(), e);
      // Erreur dans les données saisies
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.benefit.error.same.threshold"));
    } catch (IllegalArgumentException e) {
      // Erreur grave
      LOGGER.error("Une erreur est survenue lors de l'ajout à la liste de barème pour avantage en nature ", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.benefit.error.internal"));
    }
  }

  /**
   * Sauvegarde en base de données l'intégralité des données
   * @param actionEvent : L'ActionEvent
   */
  public void saveData(ActionEvent actionEvent) {
    try {
      benefitService.saveListing(benefitBean.getBenefitList());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("administration.referentiel.benefit.success"));

      contractServiceFacade.updateContractsInProgress();
    } catch (BenefitServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde des barèmes pour les avantages en nature", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Une erreur est survenue lors de la mise à jour des contrats en cours", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    }
  }

  /**
   * Récupère le seuil supérieur limite par rapport au barème en paramètre (et sa position dans la liste)
   * @param reference barème de référence
   * @return Retourne le seuil supérieur limite adéquate, ou <code>""</code> si il n'y a pas de limite supérieure
   */
  public String getNextThreshold(SalaryRangeDecorator reference) {
    int index = benefitBean.getBenefitList().indexOf(reference);
    if (index >= 0 && index < benefitBean.getBenefitList().size() - 1) {
      return benefitBean.getBenefitList().get(++index).getMinimumThreshold().toPlainString();
    }
    return StringUtils.EMPTY;
  }

  /**
   * Récupère le seuil inférieur limite par rapport au barème en paramètre (et sa position dans la liste)
   * @param reference barème de référence
   * @return Retourne le seuil inférieur limite adéquate, ou <code>""</code> si il n'y a pas de limite inférieure
   */
  public String getPreviousThreshold(SalaryRangeDecorator reference) {
    int index = benefitBean.getBenefitList().indexOf(reference);
    if (index > 0 && index < benefitBean.getBenefitList().size()) {
      return benefitBean.getBenefitList().get(--index).getMinimumThreshold().toPlainString();
    }
    return StringUtils.EMPTY;
  }

  /**
   * Indique si l'index passé en paramètre est le premier de la liste
   * @param rowIndex index de la liste des barèmes
   * @return Renvoi <code>true</code> si l'index en paramètre permet de récupérer le premier élément de la liste
   */
  public boolean isFirstIndex(int rowIndex) {
    return benefitBean.getBenefitList().get(0).equals(benefitBean.getBenefitList().get(rowIndex));
  }

  /**
   * Indique si l'index passé en paramètre est le premier de la liste
   * @param rowIndex index de la liste des barèmes
   * @return Renvoi <code>true</code> si l'index en paramètre permet de récupérer le dernier élément de la liste
   */
  public boolean isLastIndex(int rowIndex) {
    return benefitBean.getBenefitList().size() - 1 == rowIndex;
  }

  /**
   * @return the benefitBean
   */
  public BenefitBean getBenefitBean() {
    return benefitBean;
  }

  /**
   * @param benefitBean the benefitBean to set
   */
  public void setBenefitBean(BenefitBean benefitBean) {
    this.benefitBean = benefitBean;
  }

  /**
   * @param benefitService the benefitService to set
   */
  public void setBenefitService(IBenefitService benefitService) {
    this.benefitService = benefitService;
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

}
