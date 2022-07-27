/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.reporting.impl;

import com.abita.services.historyreporting.IHistoryReportingService;
import com.abita.services.historyreporting.exceptions.HistoryReportingServiceException;
import com.abita.services.jobs.gcp.constants.GcpConstants;
import com.abita.services.jobs.reporting.IReportingServiceFacade;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dto.HistoryReportingDTO;
import com.abita.services.jobs.reporting.exceptions.ReportingServiceFacadeException;
import com.dto.mail.MailRecipientDTO;
import com.dto.mail.MailSendStatus;
import com.dto.mail.TextMailContentDTO;
import com.services.mailservice.IMailService;
import com.services.mailservice.exception.MailServiceException;
import com.services.paramservice.ParameterService;
import com.services.paramservice.constants.ParamServiceConstants;
import com.services.paramservice.exception.ParameterServiceException;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.joda.time.LocalDate;
import org.joda.time.YearMonth;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *  Classe d’implémentation des reporting
 */
public class ReportingServiceFacadeImpl implements IReportingServiceFacade {

  /** Service de mail */
  private IMailService mailService;

  /** Service d’historisation des reporting */
  private IHistoryReportingService historyReportingService;

  /** Service des paramètres */
  private ParameterService parameterService;

  /** Processus YL_ZN */
  private static final String YL_ZN_PROCESS = "Quittancement propriétaire (YL_ZN)";

  /** Processus NC */
  private static final String NC_PROCESS = "Quittancement salariés (NC)";

  /** Processus YL_ZN */
  private static final String NT_PROCESS = "Facturation ponctuelle (NT)";

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(ReportingServiceFacadeImpl.class);

  /** Propriétés du reporting */
  private ResourceBundle reportingProperties = ResourceBundle.getBundle("reporting");

  @Override
  public MailSendStatus sendMail(String type) throws ReportingServiceFacadeException {
    try {
      YearMonth now = new YearMonth();
      HistoryReportingDTO historyReportingDTO = historyReportingService.findHistoryReportingByTypeAndYearMonth(type, now.getMonthOfYear(), now.getYear());

      if (historyReportingDTO == null) {
        LOGGER.info("Aucun reporting pour la pi\u00E8ce " + type);
        historyReportingDTO = new HistoryReportingDTO();
        historyReportingDTO.setType(GcpConstants.TYPE_PIECE_NT);
        historyReportingDTO.setAmount(BigDecimal.ZERO);
      }

      MailRecipientDTO tos = makeRecipients();
      MailRecipientDTO ccs = makeRecipientsCopy();

      // L’expéditeur est celui configuré dans l’administration
      String from = parameterService.getParameterValue(ParamServiceConstants.GENERAL_DOMAIN_KEY, "mailSenderAddress");
      String subject = makeSubject(now, historyReportingDTO);
      String content = makeContent(now, historyReportingDTO);

      // Il n’y a pas de destinataire en copie ni en copie cachée
      TextMailContentDTO mailContent = new TextMailContentDTO(tos, ccs, null, from, subject, content);

      MailSendStatus status = mailService.sendMail(mailContent);
      LOGGER.info(status.toString());

      return status;
    } catch (HistoryReportingServiceException e) {
      throw new ReportingServiceFacadeException(e.getMessage(), e);
    } catch (MailServiceException e) {
      throw new ReportingServiceFacadeException(e.getMessage(), e);
    } catch (ParameterServiceException e) {
      throw new ReportingServiceFacadeException(e.getMessage(), e);
    } catch (UnsupportedEncodingException e) {
      throw new ReportingServiceFacadeException(e.getMessage(), e);
    }
  }

  /**
   * Permet de construire les données des destinataires
   * @return les destinataires
   */
  private MailRecipientDTO makeRecipients() {
    MailRecipientDTO tos = new MailRecipientDTO();

    String recipientsParameter = reportingProperties.getString("reporting.recipients");
    if (recipientsParameter != null) {
      tos.getRecipients().addAll(Arrays.asList(recipientsParameter.split(";")));
    }

    return tos;
  }

  /**
   * Permet de construire les données des destinataires en copie
   * @return les destinataires en copie
   */
  private MailRecipientDTO makeRecipientsCopy() {
    MailRecipientDTO ccs = new MailRecipientDTO();

    String recipientsCopyParameter = reportingProperties.getString("reporting.recipients.copy");
    if (recipientsCopyParameter != null) {
      ccs.getRecipients().addAll(Arrays.asList(recipientsCopyParameter.split(";")));
    }

    return ccs;
  }

  /**
   * Permet de construire les données du sujet
   * @param now date de traitement
   * @param historyReportingDTO reporting traité
   * @return le sujet
   */
  private String makeSubject(YearMonth now, HistoryReportingDTO historyReportingDTO) {
    String subject = "";

    String subjectParameter = reportingProperties.getString("reporting.subject");
    if (subjectParameter != null) {
      subject = MessageFormat.format(subjectParameter, historyReportingDTO.getType(), now.toString(DateTimeUtils.PATTERN_DATE_MMYYYY_UNDERSCORE));
    }

    return subject;
  }

  /**
   * Permet de construire les données du sujet
   * @param now date de traitement
   * @param historyReportingDTO  reporting traité
   * @return le contenu
   * @throws UnsupportedEncodingException
   */
  private String makeContent(YearMonth now, HistoryReportingDTO historyReportingDTO) throws UnsupportedEncodingException {
    String content = "";

    String contentParameter = reportingProperties.getString("reporting.content");
    if (contentParameter != null) {
      String month = now.monthOfYear().getAsText(Locale.FRENCH);
      LocalDate treatmentDate = new LocalDate();
      treatmentDate = treatmentDate.dayOfMonth().withMaximumValue();
      String process = "";
      if (GcpConstants.TYPE_PIECE_YL_ZN.equalsIgnoreCase(historyReportingDTO.getType())) {
        process = YL_ZN_PROCESS;
      }
      if (GcpConstants.TYPE_PIECE_NC.equalsIgnoreCase(historyReportingDTO.getType())) {
        process = NC_PROCESS;
      }
      if (GcpConstants.TYPE_PIECE_NT.equalsIgnoreCase(historyReportingDTO.getType())) {
        process = NT_PROCESS;
      }
      content = MessageFormat.format(contentParameter, historyReportingDTO.getType(), month, Integer.toString(now.getYear()),
        treatmentDate.toString(DateTimeUtils.PATTERN_DATE_DDMMYYYY_UNDERSCORE), process, historyReportingDTO.getAmount().toString());
    }

    LOGGER.info(content);

    return new String(content.getBytes(Charset.forName("UTF-8")), CharEncoding.ISO_8859_1);
  }

  /**
   * @param mailService the mailService to set
   */
  @Override
  public void setMailService(IMailService mailService) {
    this.mailService = mailService;
  }

  /**
   * @param historyReportingService the historyReportingService to set
   */
  public void setHistoryReportingService(IHistoryReportingService historyReportingService) {
    this.historyReportingService = historyReportingService;
  }

  /**
   * @param parameterService the parameterService to set
   */
  public void setParameterService(ParameterService parameterService) {
    this.parameterService = parameterService;
  }

}
