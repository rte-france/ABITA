package com.abita.web.admin.detailcron.bean;

import com.abita.dto.DetailCronDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Bean en relation avec la page /pages/administration/interfaces/detailcron.xhtml
 *
 * @author
 */
public class DetailCronBean implements Serializable {

  /** SerialVersionUID */
  private static final long serialVersionUID = 8937879006060578850L;

  /** Le DetailCronDTO */
  private List<DetailCronDTO> crons;

  /** Liste des données des mois de génération */
  private List<CronMonthBean> cronsMonth;

  /**
   * @return the crons
   */
  public List<DetailCronDTO> getCrons() {
    return crons;
  }

  /**
   * @param crons the crons to set
   */
  public void setCrons(List<DetailCronDTO> crons) {
    this.crons = crons;
  }

  /**
   * @return the cronsMonth
   */
  public List<CronMonthBean> getCronsMonth() {
    return cronsMonth;
  }

  /**
   * @param cronsMonth the cronsMonth to set
   */
  public void setCronsMonth(List<CronMonthBean> cronsMonth) {
    this.cronsMonth = cronsMonth;
  }

}
