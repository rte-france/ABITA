/**
 *
 */
package com.abita.web.admin.detailcron.bean;

import com.abita.util.dateutil.DateTimeUtils;

import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * Bean pour les données des CRON d’un mois
 */
public class CronMonthBean implements Serializable {

  /**SerialVersionUID */
  private static final long serialVersionUID = 6925957555207921600L;

  /** Mois de génération */
  private String monthName;

  /** Jour de YL - ZN */
  private String ylZnDay;

  /** Heure de YL - ZN */
  @NotNull(message = "{interface.detailcron.gcp.yl.error}")
  private Date ylZnTime;

  /** Jour de NC */
  private String ncDay;

  /** Heure de NC */
  @NotNull(message = "{interface.detailcron.gcp.nc.error}")
  private Date ncTime;

  /** Jour de NT */
  private String ntDay;

  /** Heure de NT */
  @NotNull(message = "{interface.detailcron.gcp.nt.error}")
  private Date ntTime;

  /** Jour de NNI */
  private String nniDay;

  /** Heure de NNI */
  @NotNull(message = "{interface.detailcron.artesis.nni.error}")
  private Date nniTime;

  /** Jour de Donnée agent */
  private String userdataDay;

  /** Heure de Donnée agent */
  @NotNull(message = "{interface.detailcron.artesis.userdata.error}")
  private Date userdataTime;

  /** Jour de Retenue sur paie */
  private String salaryretainedDay;

  /** Heure de Retenue sur paie */
  @NotNull(message = "{interface.detailcron.artesis.salaryretained.error}")
  private Date salaryretainedTime;

  /** Jour de Avantage en nature */
  private String fringebenefitsDay;

  /** Heure de Avantage en nature */
  @NotNull(message = "{interface.detailcron.artesis.fringebenefits.error}")
  private Date fringebenefitsTime;

  /**
   * @return the monthName
   */
  public String getMonthName() {
    return monthName;
  }

  /**
   * @param monthName the monthName to set
   */
  public void setMonthName(String monthName) {
    this.monthName = monthName;
  }

  /**
   * @return the ylZnDay
   */
  public String getYlZnDay() {
    return ylZnDay;
  }

  /**
   * @param ylZnDay the ylZnDay to set
   */
  public void setYlZnDay(String ylZnDay) {
    this.ylZnDay = ylZnDay;
  }

  /**
   * @return the ylZnTime
   */
  public Date getYlZnTime() {
    return DateTimeUtils.clone(ylZnTime);
  }

  /**
   * @param ylZnTime the ylZnTime to set
   */
  public void setYlZnTime(Date ylZnTime) {
    this.ylZnTime = DateTimeUtils.clone(ylZnTime);
  }

  /**
   * @return the ncDay
   */
  public String getNcDay() {
    return ncDay;
  }

  /**
   * @param ncDay the ncDay to set
   */
  public void setNcDay(String ncDay) {
    this.ncDay = ncDay;
  }

  /**
   * @return the ncTime
   */
  public Date getNcTime() {
    return DateTimeUtils.clone(ncTime);
  }

  /**
   * @param ncTime the ncTime to set
   */
  public void setNcTime(Date ncTime) {
    this.ncTime = DateTimeUtils.clone(ncTime);
  }

  /**
   * @return the ntDay
   */
  public String getNtDay() {
    return ntDay;
  }

  /**
   * @param ntDay the ntDay to set
   */
  public void setNtDay(String ntDay) {
    this.ntDay = ntDay;
  }

  /**
   * @return the ntTime
   */
  public Date getNtTime() {
    return DateTimeUtils.clone(ntTime);
  }

  /**
   * @param ntTime the ntTime to set
   */
  public void setNtTime(Date ntTime) {
    this.ntTime = DateTimeUtils.clone(ntTime);
  }

  /**
   * @return the nniDay
   */
  public String getNniDay() {
    return nniDay;
  }

  /**
   * @param nniDay the nniDay to set
   */
  public void setNniDay(String nniDay) {
    this.nniDay = nniDay;
  }

  /**
   * @return the nniTime
   */
  public Date getNniTime() {
    return DateTimeUtils.clone(nniTime);
  }

  /**
   * @param nniTime the nniTime to set
   */
  public void setNniTime(Date nniTime) {
    this.nniTime = DateTimeUtils.clone(nniTime);
  }

  /**
   * @return the userdataDay
   */
  public String getUserdataDay() {
    return userdataDay;
  }

  /**
   * @param userdataDay the userdataDay to set
   */
  public void setUserdataDay(String userdataDay) {
    this.userdataDay = userdataDay;
  }

  /**
   * @return the userdataTime
   */
  public Date getUserdataTime() {
    return DateTimeUtils.clone(userdataTime);
  }

  /**
   * @param userdataTime the userdataTime to set
   */
  public void setUserdataTime(Date userdataTime) {
    this.userdataTime = DateTimeUtils.clone(userdataTime);
  }

  /**
   * @return the salaryretainedDay
   */
  public String getSalaryretainedDay() {
    return salaryretainedDay;
  }

  /**
   * @param salaryretainedDay the salaryretainedDay to set
   */
  public void setSalaryretainedDay(String salaryretainedDay) {
    this.salaryretainedDay = salaryretainedDay;
  }

  /**
   * @return the salaryretainedTime
   */
  public Date getSalaryretainedTime() {
    return DateTimeUtils.clone(salaryretainedTime);
  }

  /**
   * @param salaryretainedTime the salaryretainedTime to set
   */
  public void setSalaryretainedTime(Date salaryretainedTime) {
    this.salaryretainedTime = DateTimeUtils.clone(salaryretainedTime);
  }

  /**
   * @return the fringebenefitsDay
   */
  public String getFringebenefitsDay() {
    return fringebenefitsDay;
  }

  /**
   * @param fringebenefitsDay the fringebenefitsDay to set
   */
  public void setFringebenefitsDay(String fringebenefitsDay) {
    this.fringebenefitsDay = fringebenefitsDay;
  }

  /**
   * @return the fringebenefitsTime
   */
  public Date getFringebenefitsTime() {
    return DateTimeUtils.clone(fringebenefitsTime);
  }

  /**
   * @param fringebenefitsTime the fringebenefitsTime to set
   */
  public void setFringebenefitsTime(Date fringebenefitsTime) {
    this.fringebenefitsTime = DateTimeUtils.clone(fringebenefitsTime);
  }

}
