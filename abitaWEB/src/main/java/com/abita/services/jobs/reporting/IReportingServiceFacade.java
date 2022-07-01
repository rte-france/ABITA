package com.abita.services.jobs.reporting;

import com.abita.services.jobs.reporting.exceptions.ReportingServiceFacadeException;
import com.dto.mail.MailSendStatus;
import com.services.mailservice.IMailService;

/**
 * Interface du service des reporting
 */
public interface IReportingServiceFacade {

  /**
   * Envoi du mail de reporting d’une pièce
   * @param type type de pièce
   * @return retourne le statut de l'envoi de mail
   * @throws ReportingServiceFacadeException si une erreur
   */
  MailSendStatus sendMail(String type) throws ReportingServiceFacadeException;

  /**
   * Permet d'instancier le service
   * @param mailService Le service de mail
   */
  void setMailService(IMailService mailService);
}
