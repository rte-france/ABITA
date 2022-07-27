/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.detailcron.impl;

import com.abita.dao.detailcron.entity.DetailCronEntity;
import com.abita.dao.detailcron.exceptions.DetailCronDAOException;
import com.abita.services.detailcron.IDetailCronService;
import com.abita.services.detailcron.exceptions.DetailCronServiceException;
import com.abita.util.cron.CronUtils;
import com.abita.dao.detailcron.IDetailCronDAO;
import com.abita.dto.DetailCronDTO;
import com.services.common.impl.AbstractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * Classe d'implémentation des services des détails cron
 *
 * @author
 */
public class DetailCronServiceImpl extends AbstractService<DetailCronEntity, DetailCronDTO, DetailCronDAOException, Long, DetailCronServiceException, IDetailCronDAO> implements
        IDetailCronService, ApplicationContextAware {

  /**
  * Le DAO des détails cron
  */
  private IDetailCronDAO detailCronDAO;

  /** Une instance d'ApplicationContext */
  private ApplicationContext applicationContext;

  /** Le batch de traitement des pièces comptables YL */
  private Runnable ylRunner;

  /** Le batch de traitement des pièces comptables NC */
  private Runnable ncRunner;

  /** Le batch de traitement des pièces comptables NT */
  private Runnable ntRunner;

  /** Le batch de traitement du fichier ARTESIS NNI */
  private Runnable nniRunner;

  /** Le batch de traitement du fichier ARTESIS Données Agent */
  private Runnable agentDataRunner;

  /** Le batch de traitement du fichier ARTESIS Retenue sur paie */
  private Runnable retainedSalaryAndQuittancementRunner;

  /** Le batch de traitement du fichier ARTESIS Avantage en nature */
  private Runnable benefitsRunner;

  /** Le batch de traitement de suppression de l'historisation vieille d'un an */
  private Runnable deleteOldDatasRunner;

  /** Le batch de traitement de l’historisations des nouveaux contrats en cours */
  private Runnable historizeNewContractsInProgressRunner;

  /** Le batch des calculs sur les contrats de rétroactivité */
  private Runnable updateContractDataRetroactivityRunner;

  /** Le batch de traitement d'historisation des contrats occupant */
  private Runnable historizeContractRunner;

  /** Le batch de traitement d'historisation des contrats occupant */
  private Runnable historizeHousingRunner;

  /** Le batch de traitement d'historisation des contrats occupant */
  private Runnable historizeTenantRunner;

  /** Le batch de traitement d'historisation des contrats occupant */
  private Runnable historizeBenefitsRunner;

  /** La tache de traitement des pièces comptables YL dans le futur */
  private ScheduledFuture<?> scheduledFutureYl;

  /** La tache de traitement des pièces comptables NC dans le futur */
  private ScheduledFuture<?> scheduledFutureNc;

  /** La tache de traitement des pièces comptables NT dans le futur */
  private ScheduledFuture<?> scheduledFutureNt;

  /** La tache de traitement du fichier ARTESIS NNI dans le futur */
  private ScheduledFuture<?> scheduledFutureNni;

  /** La tache de traitement du fichier ARTESIS Données Agent dans le futur */
  private ScheduledFuture<?> scheduledFutureAgentData;

  /** La tache de traitement du fichier ARTESIS Retenue sur paie dans le futur */
  private ScheduledFuture<?> scheduledFutureRetainedSalary;

  /** La tache de traitement du fichier ARTESIS Avantage en nature dans le futur */
  private ScheduledFuture<?> scheduledFutureBenefits;

  /** La tache de traitement d'historisation des avantages en nature dans le futur */
  private ScheduledFuture<?> scheduledFutureDeleteOldData;

  /** La tache dans le futur de traitement de l’historisations des nouveaux contrats en cours */
  private ScheduledFuture<?> scheduledFutureHistorizeNewContractsInProgress;

  /** La tache dans le futur des calculs sur les contrats de rétroactivité */
  private ScheduledFuture<?> scheduledFutureUpdateContractDataRetroactivity;

  /** La tache de traitement d'historisation des contrats occupant dans le futur */
  private ScheduledFuture<?> scheduledFutureHistoryContract;

  /** La tache de traitement d'historisation des logements dans le futur */
  private ScheduledFuture<?> scheduledFutureHistoryHousing;

  /** La tache de traitement d'historisation des occupants dans le futur */
  private ScheduledFuture<?> scheduledFutureHistoryTenant;

  /** La tache de traitement d'historisation des avantages en nature dans le futur */
  private ScheduledFuture<?> scheduledFutureHistoryBenefits;

  /** Le batch des traitements des tiers */
  private Runnable thirdPartyTreatmentsRunner;

  /** Le tache dans le futur des traitements des tiers */
  private ScheduledFuture<?> scheduledFutureThirdPartyTreatments;

  /** Le batch des traitements des occupants */
  private Runnable tenantTreatmentsRunner;

  /** Le tache dans le futur des traitements des occupants */
  private ScheduledFuture<?> scheduledFutureTenantTreatments;

  /** logger de classe. */
  private static final Logger LOGGER = LoggerFactory.getLogger(DetailCronServiceImpl.class);

  /** Délai pour la première génération */
  private static final int FIRST_TIME_FRAME = 60;

  /** Délai pour la deuxième génération */
  private static final int SECOND_TIME_FRAME = 75;

  /** Délai pour la troisième génération */
  private static final int THIRD_TIME_FRAME = 90;

  /** Délai pour la quatrième génération */
  private static final int FOURTH_TIME_FRAME = 105;

  /** Délai pour la cinquième génération */
  private static final int FIFTH_TIME_FRAME = 120;

  /** Délai pour la sixième génération */
  private static final int SIXTH_TIME_FRAME = 135;

  /** Délai pour la septième génération */
  private static final int SEVENTH_TIME_FRAME = 150;

  @Override
  public DetailCronDTO getCronsOfCurrentMonth() {
    DetailCronDTO detailCron = new DetailCronDTO();
    try {
      LocalDate now = LocalDate.now();
      Long month = Long.valueOf(now.getMonthOfYear());

      detailCron = get(month);

    } catch (DetailCronServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la récupération du détail des CRON", e);
    }
    return detailCron;
  }

  @Override
  public DetailCronDTO getCronsOfLastMonth() {
    DetailCronDTO detailCron = new DetailCronDTO();
    try {
      LocalDate now = LocalDate.now();
      now = now.minusMonths(1);
      Long month = Long.valueOf(now.getMonthOfYear());

      detailCron = get(month);

    } catch (DetailCronServiceException e) {
      LOGGER.error("Une erreur est survenue lors de la récupération du détail des CRON", e);
    }
    return detailCron;
  }

  @Override
  public void update(List<DetailCronDTO> crons) throws DetailCronServiceException {
    LOGGER.info("Mise à jour des batchs (manuel)");

    // On récupère dans la base le cron avant de le modifier afin de mettre à jour uniquement les crons modifiés
    DetailCronDTO currentDetailCronDTO = getCronsOfCurrentMonth();

    for (DetailCronDTO cron : crons) {
      update(cron);
    }

    updateCron(currentDetailCronDTO);
  }

  @Override
  public void updateAutomaticForANewMonth() throws DetailCronServiceException {
    LOGGER.info("Mise à jour des batchs (automatique)");

    // On récupère dans la base le cron du mois précédent avant de le modifier afin de mettre à jour uniquement les crons modifiés
    DetailCronDTO currentDetailCronDTO = getCronsOfLastMonth();

    updateCron(currentDetailCronDTO);
  }

  @Override
  public void updateCron(DetailCronDTO oldDetailCronDTO) {
    // On récupère les informations modifiées des crons dans la bases de données
    DetailCronDTO updatedDetailCron = getCronsOfCurrentMonth();

    // On récupère le plannificateur
    ThreadPoolTaskScheduler scheduler = applicationContext.getBean(ThreadPoolTaskScheduler.class);

    // on compare les crons pour trouver ceux modifier afin de les arreter plus de les relancer
    if (!updatedDetailCron.getYl().equals(oldDetailCronDTO.getYl())) {
      scheduledFutureYl.cancel(true);
      scheduledFutureYl = scheduler.schedule(ylRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getYl())));

      scheduledFutureThirdPartyTreatments.cancel(true);
      scheduledFutureThirdPartyTreatments = scheduler.schedule(thirdPartyTreatmentsRunner,
        new CronTrigger(CronUtils.detailCronExpression(SEVENTH_TIME_FRAME, updatedDetailCron.getZn())));
    }
    if (!updatedDetailCron.getNc().equals(oldDetailCronDTO.getNc())) {
      scheduledFutureNc.cancel(true);
      scheduledFutureNc = scheduler.schedule(ncRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getNc())));
    }
    if (!updatedDetailCron.getNt().equals(oldDetailCronDTO.getNt())) {
      scheduledFutureNt.cancel(true);
      scheduledFutureNt = scheduler.schedule(ntRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getNt())));
    }
    if (!updatedDetailCron.getNni().equals(oldDetailCronDTO.getNni())) {
      scheduledFutureNni.cancel(true);
      scheduledFutureNni = scheduler.schedule(nniRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getNni())));
    }
    if (!updatedDetailCron.getUserData().equals(oldDetailCronDTO.getUserData())) {
      scheduledFutureAgentData.cancel(true);
      scheduledFutureAgentData = scheduler.schedule(agentDataRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getUserData())));

      scheduledFutureDeleteOldData.cancel(true);
      scheduledFutureDeleteOldData = scheduler.schedule(deleteOldDatasRunner, new CronTrigger(CronUtils.detailCronExpression(FIRST_TIME_FRAME, updatedDetailCron.getUserData())));

      scheduledFutureHistoryTenant.cancel(true);
      scheduledFutureHistoryTenant = scheduler.schedule(historizeTenantRunner, new CronTrigger(CronUtils.detailCronExpression(SECOND_TIME_FRAME, updatedDetailCron.getUserData())));

      scheduledFutureHistoryHousing.cancel(true);
      scheduledFutureHistoryHousing = scheduler
        .schedule(historizeHousingRunner, new CronTrigger(CronUtils.detailCronExpression(THIRD_TIME_FRAME, updatedDetailCron.getUserData())));

      scheduledFutureHistoryBenefits.cancel(true);
      scheduledFutureHistoryBenefits = scheduler.schedule(historizeBenefitsRunner,
        new CronTrigger(CronUtils.detailCronExpression(FOURTH_TIME_FRAME, updatedDetailCron.getUserData())));

      scheduledFutureHistorizeNewContractsInProgress.cancel(true);
      scheduledFutureHistorizeNewContractsInProgress = scheduler.schedule(historizeNewContractsInProgressRunner,
        new CronTrigger(CronUtils.detailCronExpression(FIFTH_TIME_FRAME, updatedDetailCron.getUserData())));

      scheduledFutureHistoryContract.cancel(true);
      scheduledFutureHistoryContract = scheduler.schedule(historizeContractRunner,
        new CronTrigger(CronUtils.detailCronExpression(SIXTH_TIME_FRAME, updatedDetailCron.getUserData())));

      scheduledFutureUpdateContractDataRetroactivity.cancel(true);
      scheduledFutureUpdateContractDataRetroactivity = scheduler.schedule(updateContractDataRetroactivityRunner,
        new CronTrigger(CronUtils.detailCronExpression(SEVENTH_TIME_FRAME, updatedDetailCron.getUserData())));

    }
    if (!updatedDetailCron.getSalaryRetained().equals(oldDetailCronDTO.getSalaryRetained())) {
      scheduledFutureRetainedSalary.cancel(true);
      scheduledFutureRetainedSalary = scheduler.schedule(retainedSalaryAndQuittancementRunner,
        new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getSalaryRetained())));
    }
    if (!updatedDetailCron.getFringeBenefits().equals(oldDetailCronDTO.getFringeBenefits())) {
      scheduledFutureBenefits.cancel(true);
      scheduledFutureBenefits = scheduler.schedule(benefitsRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getFringeBenefits())));
    }

    scheduledFutureTenantTreatments.cancel(true);
    scheduledFutureTenantTreatments = scheduler.schedule(tenantTreatmentsRunner, new CronTrigger(getLastStringOfCronOfTheMonth(updatedDetailCron)));
  }

  @Override
  public void createCron() {
    LOGGER.info("Création des batch.");
    // On récupère les informations modifiées des crons dans la bases de données
    DetailCronDTO updatedDetailCron = getCronsOfCurrentMonth();

    // On récupère le plannificateur
    ThreadPoolTaskScheduler scheduler = applicationContext.getBean(ThreadPoolTaskScheduler.class);

    // On planifie les taches avec leurs déclencheurs et on initialise les déclencheurs avec les valeurs récupérées dans la base de données
    scheduledFutureYl = scheduler.schedule(ylRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getYl())));
    scheduledFutureThirdPartyTreatments = scheduler.schedule(thirdPartyTreatmentsRunner,
      new CronTrigger(CronUtils.detailCronExpression(SEVENTH_TIME_FRAME, updatedDetailCron.getZn())));

    scheduledFutureNc = scheduler.schedule(ncRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getNc())));

    scheduledFutureNt = scheduler.schedule(ntRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getNt())));

    scheduledFutureNni = scheduler.schedule(nniRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getNni())));

    scheduledFutureAgentData = scheduler.schedule(agentDataRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getUserData())));

    scheduledFutureDeleteOldData = scheduler.schedule(deleteOldDatasRunner, new CronTrigger(CronUtils.detailCronExpression(FIRST_TIME_FRAME, updatedDetailCron.getUserData())));

    scheduledFutureHistoryTenant = scheduler.schedule(historizeTenantRunner, new CronTrigger(CronUtils.detailCronExpression(SECOND_TIME_FRAME, updatedDetailCron.getUserData())));

    scheduledFutureHistoryHousing = scheduler.schedule(historizeHousingRunner, new CronTrigger(CronUtils.detailCronExpression(THIRD_TIME_FRAME, updatedDetailCron.getUserData())));

    scheduledFutureHistoryBenefits = scheduler.schedule(historizeBenefitsRunner,
      new CronTrigger(CronUtils.detailCronExpression(FOURTH_TIME_FRAME, updatedDetailCron.getUserData())));

    scheduledFutureHistorizeNewContractsInProgress = scheduler.schedule(historizeNewContractsInProgressRunner,
      new CronTrigger(CronUtils.detailCronExpression(FIFTH_TIME_FRAME, updatedDetailCron.getUserData())));

    scheduledFutureHistoryContract = scheduler
      .schedule(historizeContractRunner, new CronTrigger(CronUtils.detailCronExpression(SIXTH_TIME_FRAME, updatedDetailCron.getUserData())));

    scheduledFutureUpdateContractDataRetroactivity = scheduler.schedule(updateContractDataRetroactivityRunner,
      new CronTrigger(CronUtils.detailCronExpression(SEVENTH_TIME_FRAME, updatedDetailCron.getUserData())));

    scheduledFutureRetainedSalary = scheduler.schedule(retainedSalaryAndQuittancementRunner,
      new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getSalaryRetained())));

    scheduledFutureBenefits = scheduler.schedule(benefitsRunner, new CronTrigger(CronUtils.detailCronExpression(0, updatedDetailCron.getFringeBenefits())));

    scheduledFutureTenantTreatments = scheduler.schedule(tenantTreatmentsRunner, new CronTrigger(getLastStringOfCronOfTheMonth(updatedDetailCron)));
  }

  @Override
  public DateTime getYlCron() throws DetailCronServiceException {
    DetailCronDTO detailCron = getCronsOfCurrentMonth();
    return CronUtils.getDateTimeFromDetailCronInfo(detailCron.getYl());
  }

  @Override
  public DateTime getFirstCronOfTheMonth(DetailCronDTO detailCron) {
    List<DateTime> fileTransferInfoList = new ArrayList<DateTime>();
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getUserData()));
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getFringeBenefits()));
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getSalaryRetained()));
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getNc()));
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getNt()));
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getNni()));
    Collections.sort(fileTransferInfoList);
    return fileTransferInfoList.get(0);
  }

  @Override
  public DateTime getLastCronOfTheMonth(DetailCronDTO detailCron) {
    List<DateTime> fileTransferInfoList = new ArrayList<DateTime>();
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getUserData()));
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getFringeBenefits()));
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getSalaryRetained()));
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getNc()));
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getNt()));
    fileTransferInfoList.add(CronUtils.getDateTimeFromDetailCronInfo(detailCron.getNni()));
    Collections.sort(fileTransferInfoList);
    return fileTransferInfoList.get(fileTransferInfoList.size() - 1);
  }

  /**
   * Permet de récupérer la date en expression CRON du dernier cron qui se lance dans le mois
   * @param detailCron les détails des CRON
   * @return la date et l'heure du dernier CRON
   */
  private String getLastStringOfCronOfTheMonth(DetailCronDTO detailCron) {
    DateTime lastDateTime = getLastCronOfTheMonth(detailCron);
    lastDateTime = lastDateTime.plusSeconds(SEVENTH_TIME_FRAME);
    return CronUtils.getCronExpression(lastDateTime);
  }

  @Override
  protected IDetailCronDAO getSpecificIDAO() {
    return detailCronDAO;
  }

  /**
   * @param detailCronDAO the detailCronDAO to set
   */
  public void setDetailCronDAO(IDetailCronDAO detailCronDAO) {
    this.detailCronDAO = detailCronDAO;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  /**
   * @param ylRunner the ylRunner to set
   */
  public void setYlRunner(Runnable ylRunner) {
    this.ylRunner = ylRunner;
  }

  /**
   * @param thirdPartyTreatmentsRunner the thirdPartyTreatmentsRunner to set
   */
  public void setThirdPartyTreatmentsRunner(Runnable thirdPartyTreatmentsRunner) {
    this.thirdPartyTreatmentsRunner = thirdPartyTreatmentsRunner;
  }

  /**
   * @param tenantTreatmentsRunner the tenantTreatmentsRunner to set
   */
  public void setTenantTreatmentsRunner(Runnable tenantTreatmentsRunner) {
    this.tenantTreatmentsRunner = tenantTreatmentsRunner;
  }

  /**
   * @param ncRunner the ncRunner to set
   */
  public void setNcRunner(Runnable ncRunner) {
    this.ncRunner = ncRunner;
  }

  /**
   * @param ntRunner the ntRunner to set
   */
  public void setNtRunner(Runnable ntRunner) {
    this.ntRunner = ntRunner;
  }

  /**
   * @param nniRunner the nniRunner to set
   */
  public void setNniRunner(Runnable nniRunner) {
    this.nniRunner = nniRunner;
  }

  /**
   * @param agentDataRunner the agentDataRunner to set
   */
  public void setAgentDataRunner(Runnable agentDataRunner) {
    this.agentDataRunner = agentDataRunner;
  }

  /**
   * @param benefitsRunner the benefitsRunner to set
   */
  public void setBenefitsRunner(Runnable benefitsRunner) {
    this.benefitsRunner = benefitsRunner;
  }

  /**
   * @param retainedSalaryAndQuittancementRunner the retainedSalaryAndQuittancementRunner to set
   */
  public void setRetainedSalaryAndQuittancementRunner(Runnable retainedSalaryAndQuittancementRunner) {
    this.retainedSalaryAndQuittancementRunner = retainedSalaryAndQuittancementRunner;
  }

  /**
   * @param historizeNewContractsInProgressRunner the historizeNewContractsInProgressRunner to set
   */
  public void setHistorizeNewContractsInProgressRunner(Runnable historizeNewContractsInProgressRunner) {
    this.historizeNewContractsInProgressRunner = historizeNewContractsInProgressRunner;
  }

  /**
   * @param historizeContractRunner the historizeContractRunner to set
   */
  public void setHistorizeContractRunner(Runnable historizeContractRunner) {
    this.historizeContractRunner = historizeContractRunner;
  }

  /**
   * @param historizeHousingRunner the historizeHousingRunner to set
   */
  public void setHistorizeHousingRunner(Runnable historizeHousingRunner) {
    this.historizeHousingRunner = historizeHousingRunner;
  }

  /**
   * @param historizeTenantRunner the historizeTenantRunner to set
   */
  public void setHistorizeTenantRunner(Runnable historizeTenantRunner) {
    this.historizeTenantRunner = historizeTenantRunner;
  }

  /**
   * @param historizeBenefitsRunner the historizeBenefitsRunner to set
   */
  public void setHistorizeBenefitsRunner(Runnable historizeBenefitsRunner) {
    this.historizeBenefitsRunner = historizeBenefitsRunner;
  }

  /**
   * @param deleteOldDatasRunner the deleteOldDatasRunner to set
   */
  public void setDeleteOldDatasRunner(Runnable deleteOldDatasRunner) {
    this.deleteOldDatasRunner = deleteOldDatasRunner;
  }

  /**
   * @param updateContractDataRetroactivityRunner the updateContractDataRetroactivityRunner to set
   */
  public void setUpdateContractDataRetroactivityRunner(Runnable updateContractDataRetroactivityRunner) {
    this.updateContractDataRetroactivityRunner = updateContractDataRetroactivityRunner;
  }

}
