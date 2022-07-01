package com.abita.dao.batch.gcp;

import com.abita.dto.YlZnAccountingDocNumberDTO;
import com.abita.services.jobs.gcp.constants.GcpConstants.GcpType;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Un bloc pour une pièce comptable GCP
 * @author
 *
 */
public class GCPEntryBlock {

  /** La reference */
  private String reference;

  /** Date d'envoi */
  private LocalDate dateSent;

  /** Cycle de paiement */
  private String paymentCycle;

  /** Header des pièces comptables GCP */
  private GCPHeader gcpHeader;

  /** Liste de lignes */
  private List<GCPEntryLine> gcpEntryLines;

  /** Type de pièce */
  private GcpType gcpType;

  /** Informations pour l’historisation */
  private YlZnAccountingDocNumberDTO ylZnAccountingDocNumberDTO;

  /**
   * @return the gcpHeader
   */
  public GCPHeader getGcpHeader() {
    return gcpHeader;
  }

  /**
   * @param gcpHeader the gcpHeader to set
   */
  public void setGcpHeader(GCPHeader gcpHeader) {
    this.gcpHeader = gcpHeader;
  }

  /**
   * @return the gcpEntryLines
   */
  public List<GCPEntryLine> getGcpEntryLines() {
    return gcpEntryLines;
  }

  /**
   * @param gcpEntryLines the gcpEntryLines to set
   */
  public void setGcpEntryLines(List<GCPEntryLine> gcpEntryLines) {
    this.gcpEntryLines = gcpEntryLines;
  }

  /**
   * @return the reference
   */
  public String getReference() {
    return reference;
  }

  /**
   * @param reference the reference to set
   */
  public void setReference(String reference) {
    this.reference = reference;
  }

  /**
   * @param dateSent the dateSent to set
   */
  public void setDateSent(LocalDate dateSent) {
    this.dateSent = dateSent;
  }

  /**
   * @return the dateSent
   */
  public LocalDate getDateSent() {
    return dateSent;
  }

  /**
   * @return the paymentCycle
   */
  public String getPaymentCycle() {
    return paymentCycle;
  }

  /**
   * @param paymentCycle the paymentCycle to set
   */
  public void setPaymentCycle(String paymentCycle) {
    this.paymentCycle = paymentCycle;
  }

  /**
   * @return the gcpType
   */
  public GcpType getGcpType() {
    return gcpType;
  }

  /**
   * @param gcpType the gcpType to set
   */
  public void setGcpType(GcpType gcpType) {
    this.gcpType = gcpType;
  }

  /**
   * @return the ylZnAccountingDocNumberDTO
   */
  public YlZnAccountingDocNumberDTO getYlZnAccountingDocNumberDTO() {
    return ylZnAccountingDocNumberDTO;
  }

  /**
   * @param ylZnAccountingDocNumberDTO the ylZnAccountingDocNumberDTO to set
   */
  public void setYlZnAccountingDocNumberDTO(YlZnAccountingDocNumberDTO ylZnAccountingDocNumberDTO) {
    this.ylZnAccountingDocNumberDTO = ylZnAccountingDocNumberDTO;
  }

}
