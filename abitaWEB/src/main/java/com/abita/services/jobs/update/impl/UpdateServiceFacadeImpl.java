package com.abita.services.jobs.update.impl;

import com.abita.dao.batch.constant.BatchConstants;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.contract.exceptions.ContractServiceFacadeException;
import com.abita.services.jobs.update.IUpdateServiceFacade;
import com.abita.services.tenant.ITenantServiceFacade;
import com.abita.services.tenant.exceptions.TenantServiceFacadeException;
import com.abita.services.thirdpartycontract.IThirdPartyContractServiceFacade;
import com.abita.services.thirdpartycontract.exceptions.ThirdPartyContractServiceException;
import com.abita.dto.ContractDTO;
import com.abita.services.jobs.update.exceptions.UpdateServiceFacadeException;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * Classe d’implémentation des services façades des jobs pour la mise à jour des données
 */
public class UpdateServiceFacadeImpl implements IUpdateServiceFacade {

  /** Service des contrats occupants */
  private IContractServiceFacade contractServiceFacade;

  /** Service des contrats tiers */
  private IThirdPartyContractServiceFacade thirdPartyContractServiceFacade;

  /** Service des occupants */
  private ITenantServiceFacade tenantServiceFacade;

  /** Service des paramètre */
  private ParameterService parameterService;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(UpdateServiceFacadeImpl.class);

  @Override
  public void updateThirdPartyContractData() throws UpdateServiceFacadeException {

    // mise à jour la charge ponctuelle pour les contrats tiers
    updateSporadicallyInvoicingThirdPartyContract();

    // enregistrement des révisions actives
    saveActiveRevisionThirdPartyContract();

    // suppression des révisions actives
    resetActiveRevisionThirdPartyContract();
  }

  @Override
  public void updateContractData() throws UpdateServiceFacadeException {
    LocalDate now = new LocalDate();

    // remise à jour 0 des contrats candidats à la rétroactivité
    resetContractsToRegularize();

    // mise à jour des facturations ponctuelles pour les contrats occupants
    updateSporadicallyInvoicingContracts();

    if (now.getMonthOfYear() != DateTimeConstants.DECEMBER) {

      // mise à jour des loyers prévelés cumulés sur l’année (incrémentation)
      updateAddedWithdrawnRentContractsInProgress();
    }

    // mise à jour des pécule de fin d’occupation (incrémentation)
    updateTerminationSavingsContractsInProgress();

    // mise à jour des champs calculés des contrats
    updateCalculatedAmountContractData();

  }

  @Override
  public void updateData() throws UpdateServiceFacadeException {
    LocalDate now = new LocalDate();

    if (now.getMonthOfYear() == DateTimeConstants.JANUARY) {

      // mise à jour des loyers prévelés cumulés sur l’année (RAZ)
      updateResetAddedWithdrawnRentContracts();
    } else if (now.getMonthOfYear() == DateTimeConstants.OCTOBER) {

      // mise à jour du cadre à octobre N-1
      updateManagerialLastYear();

      // mise à jour du nombre de personne des foyers à octobre N-1
      updateHouseholdSizeLastYear();
    }

    // Nettoyage du dossier temporaires GCP. Suppression du mois précedent
    deleteOlderTempFolder();
  }

  @Override
  public void updateCalculatedAmountContractData() throws UpdateServiceFacadeException {
    // Mise à jour de tous les champs calculés
    updateCalculatedAmount();
  }

  /**
   * Permet de faire le ménage dans le dossier temp de GCP
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  public void deleteOlderTempFolder() throws UpdateServiceFacadeException {
    // on récupère le chemin du fichier temp en base de donnée
    try {
      String path = parameterService.getParameterValue(BatchConstants.BATCH_PARAMETER_DOMAIN, BatchConstants.BATCH_GCP_TEMP_PATH);

      // on verifie que le dossier existe
      File directory = new File(path);

      if (!directory.exists()) {
        LOGGER.warn("Nettoyage du fichier temporaire GCP : Le dossier n'existe pas");
      } else {
        deleteTempDirectory(directory);
      }
    } catch (ParameterServiceException e) {
      LOGGER.error("Erreur lors de la récupération du chemin", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de supprimer le dossier
   * @param directory dossier à traiter
   */
  private void deleteTempDirectory(File directory) {
    // on récupère la liste des dossiers
    String[] folders = directory.list();

    // on récupère le nom du dossier à supprimer
    LocalDate now = new LocalDate();
    int monthNumber = now.minusMonths(1).getMonthOfYear();
    NumberFormat nf = new DecimalFormat("00");
    String monthFolderName = nf.format(monthNumber);

    if (folders != null) {

      for (String tempFolder : folders) {
        // on sélectionne le dossier qu'on veut supprimer
        if (tempFolder.equals(monthFolderName)) {
          File folderDelete = new File(directory, tempFolder);
          // on appelle la méthode qui va supprimer le dossier et son contenu
          deleteAllFiles(folderDelete);
        }
      }
    }
  }

  /**
   * Methode de suppression d'un fichier ou d'un dossier et de son contenu
   * @param file Le dossier/fichier à supprimer
   */
  private void deleteAllFiles(File file) {
    boolean successfullyDeleted = true;
    if (file != null) {
      successfullyDeleted = delete(file);

    }
    if (!successfullyDeleted) {
      LOGGER.error("Erreur lors de la suppression des dossiers et fichiers");
    }
  }

  private boolean delete(File file) {

    boolean successfullyDeleted = true;
    // on vérifie si c'est un dossier
    if (file.isDirectory()) {
      // on récupère la liste des fichiers dans le dossier
      String[] files = file.list();

      if (files != null) {

        for (String tempFile : files) {
          // on supprime les fichiers un par un
          File fileDelete = new File(file, tempFile);
          // on fait un appel récursif afin de verifier que le dossier ne contient pas d'autre dossier. Sinon on les vide avant.
          deleteAllFiles(fileDelete);
        }
        files = file.list();
        // on teste si le dossier est vide. Si oui alors on supprime le dossier
        if (files != null && files.length == 0) {
          successfullyDeleted = file.delete();
        }
      }
    } else {
      // on supprime le fichier
      successfullyDeleted = file.delete();
    }
    return successfullyDeleted;
  }

  /**
   * Permet de mettre à jour les montants calculés tous les 1er de chaque mois
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void updateCalculatedAmount() throws UpdateServiceFacadeException {
    try {
      contractServiceFacade.updateAllContracts();
      LOGGER.info("Lancement du service de mise a jour des montants calculés");
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Erreur lors de la mise à jour des loyers écrêtés", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de mettre les cadre à octobre N-1
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void updateManagerialLastYear() throws UpdateServiceFacadeException {
    try {
      tenantServiceFacade.updateManagerialLastYear();
      LOGGER.info("Lancement du service de mise a jour des des cadres à octobre N-1");
    } catch (TenantServiceFacadeException e) {
      LOGGER.error("Erreur lors de la mise à jour des cadres à octobre N-1", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de mettre le nombre de personne à octobre N-1
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void updateHouseholdSizeLastYear() throws UpdateServiceFacadeException {
    try {
      tenantServiceFacade.updateHouseholdSizeLastYear();
      LOGGER.info("Lancement du service de mise a jour du nombre de personnes à octobre N-1");
    } catch (TenantServiceFacadeException e) {
      LOGGER.error("Erreur lors de la mise à jour du nombre de personnes à octobre N-1", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de mettre à jour la facturation ponctuelle pour les contrats occupants
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void updateSporadicallyInvoicingContracts() throws UpdateServiceFacadeException {
    try {
      contractServiceFacade.updateSporadicallyInvoicingContracts();
      LOGGER.info("Lancement du service de remise à zéro de la facturation ponctuelle des contrats occupants");
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Erreur lors de la mise à jour des facturations ponctuelles des contrats occupants", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de remettre à zéro la charge ponctuelle des contrats tiers
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void updateSporadicallyInvoicingThirdPartyContract() throws UpdateServiceFacadeException {
    try {
      thirdPartyContractServiceFacade.updateSporadicallyInvoicingThirdPartyContract();
      LOGGER.info("Lancement du service de remise à zéro de la charge ponctuelle des contrats tiers");
    } catch (ThirdPartyContractServiceException e) {
      LOGGER.error("Erreur lors de la mise à jour des facturations ponctuelles des contrats tiers", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de mettre à jour le loyer prélevé cumulé (RAZ)
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void updateResetAddedWithdrawnRentContracts() throws UpdateServiceFacadeException {
    try {
      contractServiceFacade.updateResetAddedWithdrawnRentContracts();
      LOGGER.info("Lancement du service de remise à zéro des loyers prélevés cumulés sur l'année ");
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Erreur lors de la mise à jour des loyers prélevés cumulés sur l'année (RAZ)", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de mettre à jour le loyer prélevé cumulé (incrémentation)
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void updateAddedWithdrawnRentContractsInProgress() throws UpdateServiceFacadeException {
    try {
      contractServiceFacade.updateAddedWithdrawnRentContractsInProgress();
      LOGGER.info("Lancement du service de mise a jour des loyers prélevés cumulés");
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Erreur lors de la mise à jour des loyers prélevés cumulés sur l'année (incrémentation)", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de mettre à jour le pécule de fin d'occupation
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void updateTerminationSavingsContractsInProgress() throws UpdateServiceFacadeException {
    try {
      contractServiceFacade.updateTerminationSavingsContractsInProgress();
      LOGGER.info("Lancement du service de mise a jour des montants de pécule de fin d'occupation");
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Erreur lors de la mise à jour des pécules de fin d'occupation", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  @Override
  public void updateContractDataRetroactivity() throws UpdateServiceFacadeException {
    try {
      List<ContractDTO> contractList = contractServiceFacade.findContractsToUpdateDataRetroactivity();

      for (ContractDTO contractDTO : contractList) {
        contractServiceFacade.updateOneContractDataRetroactivity(contractDTO);
      }
      LOGGER.info("Lancement du service de mise a jour des montants des historisations");
    } catch (ContractServiceFacadeException e) {
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de remettre à zéro les contrats candidats à la rétroactivité
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void resetContractsToRegularize() throws UpdateServiceFacadeException {
    try {
      contractServiceFacade.resetContractsToRegularize();
      LOGGER.info("Lancement du service de remise à zéro les contrats candidats à la rétroactivité");
    } catch (ContractServiceFacadeException e) {
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de sauvegarder des champs lors de la cloture d'un contrat
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  public void saveDatasWhenClosedThirdPartyContract() throws UpdateServiceFacadeException {
    try {
      thirdPartyContractServiceFacade.saveDatasWhenClosedThirdPartyContract();
      LOGGER.info("Lancement du service de sauvegarde des données des contrats tiers clos");
    } catch (ThirdPartyContractServiceException e) {
      LOGGER.error("Erreur lors de la sauvegarde des données des contrats tiers clos", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de sauvegarder des champs lors de la cloture d'un contrat occupant
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  public void saveDatasWhenClosedContract() throws UpdateServiceFacadeException {
    try {
      contractServiceFacade.saveDatasWhenClosedContract();
      LOGGER.info("Lancement du service de sauvegarde des données des contrats occupant clos");
    } catch (ContractServiceFacadeException e) {
      LOGGER.error("Erreur lors de la sauvegarde des données des contrats occupant clos", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de sauvegarder les révisions actives des contrats tiers
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void saveActiveRevisionThirdPartyContract() throws UpdateServiceFacadeException {
    try {
      thirdPartyContractServiceFacade.saveActiveRevisionThirdPartyContract();
      LOGGER.info("Lancement du service de sauvegarde des révisions actives des contrats tiers");
    } catch (ThirdPartyContractServiceException e) {
      LOGGER.error("Erreur lors de la sauvegarde des révisions actives des contrats tiers", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * PPermet de remettre à 0 les révisions actives sur les contrats tiers
   * @throws UpdateServiceFacadeException une UpdateServiceFacadeException
   */
  private void resetActiveRevisionThirdPartyContract() throws UpdateServiceFacadeException {
    try {
      thirdPartyContractServiceFacade.resetActiveRevisionThirdPartyContract();
      LOGGER.info("Lancement du service de remise à zéro des révisions actives des contrats tiers");
    } catch (ThirdPartyContractServiceException e) {
      LOGGER.error("Erreur lors de la remise à zéro des révisions actives des contrats tiers", e);
      throw new UpdateServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

  /**
   * @param thirdPartyContractServiceFacade the thirdPartyContractServiceFacade to set
   */
  public void setThirdPartyContractServiceFacade(IThirdPartyContractServiceFacade thirdPartyContractServiceFacade) {
    this.thirdPartyContractServiceFacade = thirdPartyContractServiceFacade;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * @param tenantServiceFacade the tenantServiceFacade to set
   */
  public void setTenantServiceFacade(ITenantServiceFacade tenantServiceFacade) {
    this.tenantServiceFacade = tenantServiceFacade;
  }

}
