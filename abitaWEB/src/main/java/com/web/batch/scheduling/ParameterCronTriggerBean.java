/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.web.batch.scheduling;

import java.text.ParseException;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronExpression;
import org.quartz.JobDetail;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;

/**
 * @author
 *
 */
public class ParameterCronTriggerBean extends CronTriggerImpl implements BeanNameAware, InitializingBean  {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 3856044533908088330L;

  /**
   * The logger
   */
  private static final Log LOG = LogFactory.getLog(ParameterCronTriggerBean.class);

  /**
   * The parameter service
   */
  protected ParameterService parameterService;

  /**
   * The parameter key
   */
  protected String cronParameterKey;

  /**
   * The domain paramter
   */
  protected String parameterDomain;

  /**
   * The default CRON
   */
  protected String defaultCRON;

  /**
   * Job detail optionnel
   */
  private JobDetail jobDetail;

  /**
   * bean name
   */
  @Nullable
  private String beanName;

  /**
   * @param defaultCRON the defaultCRON to set
   */
  public void setDefaultCRON(String defaultCRON) {
    this.defaultCRON = defaultCRON;
  }

  /**
   * @param parameterDomain the parameterDomain to set
   */
  public void setParameterDomain(String parameterDomain) {
    this.parameterDomain = parameterDomain;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

  /**
   * @param cronParameterKey the cronParameterKey to set
   */
  public void setCronParameterKey(String cronParameterKey) {
    this.cronParameterKey = cronParameterKey;
  }

  /**
   * {@inheritDoc}
   * @see org.quartz.CronTrigger#getCronExpression()
   */
  public String getActualCronExpression() {
    String cron = null;
    try {
      cron = parameterService.getParameterValue(parameterDomain, cronParameterKey);
    } catch (ParameterServiceException parameterServiceException) {
      LOG.error("Erreur lors de la reccuperation du cron ", parameterServiceException);
    }
    if (cron == null) {
      //Pas de valeur en base
      cron = defaultCRON;
      LOG.info("Using default Cron Value");
    }
    if (!CronExpression.isValidExpression(cron)) {
      /* valeur en base incorrecte */
      cron = defaultCRON;
      LOG.warn("Bad Values for Cron parameters. Using default Cron Value");
    }
    return cron;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void afterPropertiesSet() throws ParseException {
    try {
      setCronExpression(getActualCronExpression());
      Assert.notNull(getCronExpression(), "Property 'cronExpression' is required");
      if (getName() == null) {
        this.setName(beanName);
      }
      if (jobDetail != null) {
        getJobDataMap().put("jobDetail", this.jobDetail);
      }
      if (getTimeZone() == null) {
        setTimeZone(TimeZone.getDefault());
      }
      if (jobDetail != null) {
        setJobKey(jobDetail.getKey());
      }
    } catch (ParseException parseException) {
      throw parseException;
    } catch (Exception e) {
      /* Obligé de catcher Exception car c'est le type indiqué par l'API sous-jascente */
      throw new ParseException(e.getMessage(),0);
    }
  }

  /**
   * Remet à jour le trigger.
   * @throws ParseException Exception levée lors du parsing du Cron
   */
  public void resetCronExpression() throws ParseException {
    String cron = null;
    try {
      cron = parameterService.getParameterValue(parameterDomain, cronParameterKey);
      this.setCronExpression(cron);
    } catch (ParameterServiceException parameterServiceException) {
      LOG.error("Erreur lors de la reccuperation du cron : cron non modifié !", parameterServiceException);
    }
  }

  /**
   * @return the cronParameterKey
   */
  public String getCronParameterKey() {
    return cronParameterKey;
  }

  /**
   * Getter jobDetail
   * @return jobDetail
   */
  public JobDetail getJobDetail() {
    return jobDetail;
  }
  /**
   * Setter jobDetail
   * @param jobDetail jobDetail
   */
  public void setJobDetail(JobDetail jobDetail) {
    this.jobDetail = jobDetail;
  }

  /**
   * Setter beanName
   */
  @Override
  public void setBeanName(String name) {
    beanName = name;
  }
}
