package com.abita.dao.detailcron.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Entité détail cron
 *
 * @author
 */
public class DetailCronEntity extends AbstractEntity {

  /** SerialID */
  private static final long serialVersionUID = 4931534463826856920L;

  /** GCP YL */
  private String yl;

  /** GCP ZN */
  private String zn;

  /** GCP NC */
  private String nc;

  /** GCP NT */
  private String nt;

  /** ARTESIS NNI */
  private String nni;

  /** ARTESIS Données utilisateurs */
  private String userData;

  /** ARTESIS Retenu sur salaire */
  private String salaryRetained;

  /** ARTESIS Avantage en nature */
  private String fringeBenefits;

  /**
   * @return the yl
   */
  public String getYl() {
    return yl;
  }

  /**
   * @param yl the yl to set
   */
  public void setYl(String yl) {
    this.yl = yl;
  }

  /**
   * @return the zn
   */
  public String getZn() {
    return zn;
  }

  /**
   * @param zn the zn to set
   */
  public void setZn(String zn) {
    this.zn = zn;
  }

  /**
   * @return the nc
   */
  public String getNc() {
    return nc;
  }

  /**
   * @param nc the nc to set
   */
  public void setNc(String nc) {
    this.nc = nc;
  }

  /**
   * @return the nt
   */
  public String getNt() {
    return nt;
  }

  /**
   * @param nt the nt to set
   */
  public void setNt(String nt) {
    this.nt = nt;
  }

  /**
   * @return the nni
   */
  public String getNni() {
    return nni;
  }

  /**
   * @param nni the nni to set
   */
  public void setNni(String nni) {
    this.nni = nni;
  }

  /**
   * @return the userData
   */
  public String getUserData() {
    return userData;
  }

  /**
   * @param userData the userData to set
   */
  public void setUserData(String userData) {
    this.userData = userData;
  }

  /**
   * @return the salaryRetained
   */
  public String getSalaryRetained() {
    return salaryRetained;
  }

  /**
   * @param salaryRetained the salaryRetained to set
   */
  public void setSalaryRetained(String salaryRetained) {
    this.salaryRetained = salaryRetained;
  }

  /**
   * @return the fringeBenefits
   */
  public String getFringeBenefits() {
    return fringeBenefits;
  }

  /**
   * @param fringeBenefits the fringeBenefits to set
   */
  public void setFringeBenefits(String fringeBenefits) {
    this.fringeBenefits = fringeBenefits;
  }

}
