/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.detailcron;

import com.abita.services.detailcron.IDetailCronService;
import com.abita.util.comparator.DetailCronComparator;
import com.abita.dto.DetailCronDTO;
import com.abita.services.detailcron.exceptions.DetailCronServiceException;
import com.abita.web.admin.detailcron.bean.CronMonthBean;
import com.abita.web.admin.detailcron.bean.DetailCronBean;
import com.abita.web.shared.AbstractGenericController;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.Month;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Controlleur en relation avec la page /pages/administration/interfaces/detailcron.xhtml
 *
 * @author
 */
public class DetailCronController extends AbstractGenericController {

  /** SerialVersionUID */
  private static final long serialVersionUID = 5123854560821062387L;

  /** Bean pour la supervision des flux de Convergence */
  private DetailCronBean detailCronBean;

  /** Service gérant les crons */
  private transient IDetailCronService detailCronService;

  /** Le formateur pour l'heure */
  private SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWEB.PATTERN_TIME, Locale.FRENCH);

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(DetailCronController.class);

  /** Nom de code pour l’erreur technique */
  private static final String TECHNICAL_ERROR_CODE = "technical.error";

  /**
   * Initialisation du controlleur afin de récupérer le détail cron
   */
  @PostConstruct
  public void init() {
    try {
      List<DetailCronDTO> detailCrons = detailCronService.find();
      Collections.sort(detailCrons, new DetailCronComparator());
      detailCronBean.setCrons(detailCrons);
      loadData();
    } catch (DetailCronServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération du détail des CRON", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    } catch (ParseException ex) {
      LOGGER.error("Une erreur est survenue lors de la récupération du détail des CRON", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    }
  }

  /**
   * Permet de sauvegarder le paramétrage du CRON
   * @param actionEvent l'actionEvent
   */
  public void saveData(ActionEvent actionEvent) {
    try {
      generateData();
      detailCronService.update(detailCronBean.getCrons());
      FacesContext.getCurrentInstance().addMessage(null, getInfoMessage("adm.interface.detailcron.success"));
    } catch (DetailCronServiceException ex) {
      LOGGER.error("Une erreur est survenue lors de la sauvegarde des détails du CRON", ex);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage(TECHNICAL_ERROR_CODE));
    } catch (NullPointerException e) {
      LOGGER.error("Le temps d'attente minimum d'une minute avant la mise à jours des CRON n'a pas été respecté, le service n'est pas lancé", e);
      FacesContext.getCurrentInstance().addMessage(null, getErrorMessage("interface.detailcron.error.wait"));
    }
  }

  /**
   * Charge le Bean pour utilisation dans la vue
   * @throws ParseException l'exception
   */
  private void loadData() throws ParseException {
    List<CronMonthBean> cronsMonth = new ArrayList<CronMonthBean>();

    int month = 1;
    for (DetailCronDTO cron : detailCronBean.getCrons()) {
      CronMonthBean cronMonth = new CronMonthBean();

      String monthName = Month.getLabelByValue(month++);
      cronMonth.setMonthName(monthName);

      // GCP − YL/ZN
      cronMonth.setYlZnDay(getDay(cron.getYl()));
      cronMonth.setYlZnTime(getTime(cron.getYl()));

      // GCP − NC
      cronMonth.setNcDay(getDay(cron.getNc()));
      cronMonth.setNcTime(getTime(cron.getNc()));

      // GCP − NT
      cronMonth.setNtDay(getDay(cron.getNt()));
      cronMonth.setNtTime(getTime(cron.getNt()));

      // Artesis − NNI
      cronMonth.setNniDay(getDay(cron.getNni()));
      cronMonth.setNniTime(getTime(cron.getNni()));

      // Artesis − Données agent
      cronMonth.setUserdataDay(getDay(cron.getUserData()));
      cronMonth.setUserdataTime(getTime(cron.getUserData()));

      // Artesis − Retenue sur paie
      cronMonth.setSalaryretainedDay(getDay(cron.getSalaryRetained()));
      cronMonth.setSalaryretainedTime(getTime(cron.getSalaryRetained()));

      // Artesis − Avantage en nature
      cronMonth.setFringebenefitsDay(getDay(cron.getFringeBenefits()));
      cronMonth.setFringebenefitsTime(getTime(cron.getFringeBenefits()));

      cronsMonth.add(cronMonth);
    }

    detailCronBean.setCronsMonth(cronsMonth);
  }

  /**
   * Assemble les données pour le DTO
   */
  private void generateData() {

    for (int i = 0; i < detailCronBean.getCrons().size(); i++) {

      CronMonthBean cronMonth = detailCronBean.getCronsMonth().get(i);
      DetailCronDTO cron = detailCronBean.getCrons().get(i);

      // GCP − YL
      String yl = cronMonth.getYlZnDay() + " " + sdf.format(cronMonth.getYlZnTime());
      cron.setYl(yl);

      // GCP − ZN
      Calendar calZn = Calendar.getInstance();
      calZn.setTime(cronMonth.getYlZnTime());
      calZn.add(Calendar.MINUTE, 1);
      String zn = cronMonth.getYlZnDay() + " " + sdf.format(calZn.getTime());
      cron.setZn(zn);

      // GCP − NC
      String nc = cronMonth.getNcDay() + " " + sdf.format(cronMonth.getNcTime());
      cron.setNc(nc);

      // GCP − NT
      // l’ajout d’une minute à NT permet d’éviter les conflits entre NC et NT si leur déclenchement est en même temps
      if (cronMonth.getNcTime().equals(cronMonth.getNtTime()) && cronMonth.getNcDay().equalsIgnoreCase(cronMonth.getNtDay())) {
        Calendar calNt = Calendar.getInstance();
        calNt.setTime(cronMonth.getNtTime());
        calNt.add(Calendar.MINUTE, 1);
        String nt = cronMonth.getNtDay() + " " + sdf.format(calNt.getTime());
        cron.setNt(nt);
      } else {
        String nt = cronMonth.getNtDay() + " " + sdf.format(cronMonth.getNtTime());
        cron.setNt(nt);
      }

      // Artesis − NNI
      String nni = cronMonth.getNniDay() + " " + sdf.format(cronMonth.getNniTime());
      cron.setNni(nni);

      // Artesis − Données agent
      String userdata = cronMonth.getUserdataDay() + " " + sdf.format(cronMonth.getUserdataTime());
      cron.setUserData(userdata);

      // Artesis − Retenue sur paie
      String salaryretained = cronMonth.getSalaryretainedDay() + " " + sdf.format(cronMonth.getSalaryretainedTime());
      cron.setSalaryRetained(salaryretained);

      // Artesis − Avantage en nature
      String fringebenefits = cronMonth.getFringebenefitsDay() + " " + sdf.format(cronMonth.getFringebenefitsTime());
      cron.setFringeBenefits(fringebenefits);
    }
  }

  /**
   * Obtient le jour du cron
   * @param value la valeur dont on veut le jour
   * @return le jour
   */
  private String getDay(String value) {
    return value.split(" ")[0];
  }

  /**
   * Obtient l'heure de lancement du CRON
   * @param value : la chaine dont on veut extraire l'heure
   * @return l'heure
   * @throws ParseException l'exception
   */
  private Date getTime(String value) throws ParseException {
    return sdf.parse(value.split(" ")[1]);
  }

  /**
   * @return the detailCronBean
   */
  public DetailCronBean getDetailCronBean() {
    return detailCronBean;
  }

  /**
   * @param detailCronBean the detailCronBean to set
   */
  public void setDetailCronBean(DetailCronBean detailCronBean) {
    this.detailCronBean = detailCronBean;
  }

  /**
   * @param detailCronService the detailCronService to set
   */
  public void setDetailCronService(IDetailCronService detailCronService) {
    this.detailCronService = detailCronService;
  }

}
