package com.abita.web.admin.accountingcode;

import com.abita.services.accountingcode.IAccountingCodeService;
import com.abita.util.comparator.AccountingCodeComparator;
import com.abita.dto.AccountingCodeDTO;
import com.abita.services.accountingcode.exceptions.AccountingCodeServiceException;
import com.abita.web.admin.accountingcode.beans.AccountingCodeBean;
import com.abita.web.shared.AbstractGenericController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.util.Collections;

/**
 * Classe controleur de la page de gestion des codes comptables
 * @author
 *
 */
public class AccountingCodeController extends AbstractGenericController {

  /** serialVersionUID */
  private static final long serialVersionUID = 7658029366408893793L;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(AccountingCodeController.class);

  /** Interface de services des codes comptables */
  private transient IAccountingCodeService accountingCodeService;

  /** Backing bean de la page de gestion des codes comptables */
  private AccountingCodeBean accountingCodeBean;

  /**
   * Méthode d'initialisation de la page
   */
  @PostConstruct
  public void init() {
    findAllAccountingCode();
    accountingCodeBean.setAccountingCode(new AccountingCodeDTO());
  }

  /**
   * Ajoute le le code comptable à la liste
   * @param actionEvent : L'ActionEvent
   */
  public void addAccountingCodeToList(ActionEvent actionEvent) {
    if (checkIfAccountingCode()) {
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.accountingcode.error.not.unique"));
    } else {
      AccountingCodeDTO accountingCodeDTO = new AccountingCodeDTO();
      // Génère un ID temporaire afin de pouvoir le supprimer si on a pas encore sauvegardé la valeur en BDD
      accountingCodeDTO.setId(System.currentTimeMillis());
      accountingCodeDTO.setCode(accountingCodeBean.getAccountingCode().getCode());
      accountingCodeDTO.setLabel(accountingCodeBean.getAccountingCode().getLabel());

      accountingCodeBean.getLstAccountingCode().add(accountingCodeDTO);
      Collections.sort(accountingCodeBean.getLstAccountingCode(), new AccountingCodeComparator());
    }
    accountingCodeBean.setAccountingCode(new AccountingCodeDTO());
  }

  /**
   * Sauvegarde en base de données l'intégralité des données
   * @param actionEvent : L'ActionEvent
   */
  public void saveData(ActionEvent actionEvent) {
    try {
      accountingCodeService.saveListing(accountingCodeBean.getLstAccountingCode());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("administration.referentiel.accountingcode.success"));
    } catch (AccountingCodeServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde des codes comptables", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("technical.error"));
    } catch (Exception ex) {
      // ERREUR POUR LE CAS D'UN CODE COMPTABLE DEJA EXISTANT
      LOGGER.error("Un code comptable est incorrect", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.accountingcode.error.save"));
    }
  }

  /**
   * Permet de récupérer la totalité des codes comptables
   */
  private void findAllAccountingCode() {
    try {
      accountingCodeBean.setLstAccountingCode(accountingCodeService.findAllAccountingCode());
    } catch (AccountingCodeServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération de la liste des codes comptables", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("administration.referentiel.accountingcode.error.listing"));
    }
  }

  /**
   * Méthode permettant de savoir si le libellé du code comptable existe déjà
   * @return true si existe, false autrement
   */
  private boolean checkIfAccountingCode() {
    boolean accountingCodeExists = false;

    String label = accountingCodeBean.getAccountingCode().getLabel();
    for (AccountingCodeDTO accountingCode : accountingCodeBean.getLstAccountingCode()) {
      if (accountingCode.getLabel().equals(label)) {
        accountingCodeExists = true;
        break;
      }
    }

    return accountingCodeExists;
  }

  /**
   * Setter de l'interface de services des codes comptables
   * @param accountingCodeService the accountingCodeService to set
   */
  public void setAccountingCodeService(IAccountingCodeService accountingCodeService) {
    this.accountingCodeService = accountingCodeService;
  }

  /**
   * Getter du backing bean de la page de gestion des codes comptables
   * @return the accountingCodeBean
   */
  public AccountingCodeBean getAccountingCodeBean() {
    return accountingCodeBean;
  }

  /**
   * Setter du backing bean de la page de gestion des codes comptables
   * @param accountingCodeBean the accountingCodeBean to set
   */
  public void setAccountingCodeBean(AccountingCodeBean accountingCodeBean) {
    this.accountingCodeBean = accountingCodeBean;
  }

}
