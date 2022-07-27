/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto;

import com.abita.util.dateutil.DateTimeUtils;
import com.abita.util.sort.DatatablePropertiesSorted;
import com.abita.dto.constant.DTOConstants;
import org.apache.commons.lang.BooleanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO des contrats occupant
 * Les objets de cette classes seront immutables (ils ne pourront être modifiés)
 * Pour modifier un objet, utiliser la methode update du service ContractServiceImpl
 *
 * La décision de rendre ces objets immutable a été prise car certains champs de la classe
 * sont calculés a partir d'autres champs selon des règles métier de calculs spécifiques.
 * Le détail de ces calculs est exporté dans la base de données a cause de la nécessite de pouvoir
 * faire des recherches mêmes sur les valeurs de ces champs calculés et de profiter des calculs
 * ensemblistes proposer par les SGBD.
 * Pour éviter de dupliquer la logique métier, il n'est pas prévu que la partie java implémente ces mêmes
 * règles de calculs. Cela implique que si l'on change une valeur de champs dont dépend un champ calculé,
 * pour que les valeurs de l'objet restent corhérentes, il est nécessaire de pousser les modifications
 * immédiatement en base de données puis de recharger l'objet depuis celle-ci.
 *
 * @author
 */
public final class ContractDTO implements Serializable {

  /** SerialID */
  private static final long serialVersionUID = 1947077027027096195L;

  /**
   * PROPERTIES
   */
  /** Id */
  private Long id;
  /** Référence Contrat occupant */
  private String contractReference;
  /** Contrat signé */
  private Boolean signature;
  /** Date de début de validité */
  private Date startValidityDate;
  /** Date de fin de validité */
  private Date endValidityDate;
  /** Loyer marché */
  private BigDecimal marketRentPrice;
  /** Loyer plafond */
  private BigDecimal rentPriceLimit;
  /** Loyer garage */
  private BigDecimal garageRent;
  /** Loyer jardin */
  private BigDecimal gardenRent;
  /** Surloyer */
  private BigDecimal extraRent;
  /** Charges prévisionnelles */
  private BigDecimal expectedChargeCost;
  /** Remboursement assurance */
  private BigDecimal insuranceReimbursement;
  /** Remboursement taxe habitation */
  private BigDecimal housingTaxReimbursement;
  /** Remboursement ordures ménagères */
  private BigDecimal garbageReimbursement;
  /** Apurement annuel charges locatives */
  private BigDecimal annualClearanceCharges;
  /** Facturation ordures ménagères */
  private BigDecimal garbageInvoicing;
  /** Facturation consommation eau */
  private BigDecimal waterInvoicing;
  /** Autres charges locatives (libellé) */
  private String otherInvoicingLabel;
  /** Autres charges locatives (montant) */
  private BigDecimal otherInvoicingAmount;
  /** Loyer prélevé cumulé fixe*/
  private BigDecimal plainAddedWithdrawnRent;
  /** Date de dernier cumule de loyer prélevé */
  private Date lastWithdrawnDate;
  /** Pécule de fin d’occupation */
  private Boolean terminationSavings;
  /** Montant du pécule de fin d'occupation fixe */
  private BigDecimal plainTerminationSavingAmount;
  /** Montant du pécule de fin d'occupation */
  private BigDecimal terminationSavingAmount;
  /** Date de dernier cumule de pécule de fin d’occupation */
  private Date lastSavingDate;
  /** Montant de la valeur locative foncière */
  private BigDecimal realEstateRentalValue;
  /** Logement */
  private HousingDTO housing;
  /** Occupant */
  private TenantDTO tenant;
  /** Typologie de loyer */
  private RentTypologyDTO rentTypology;
  /** Motif de résiliation */
  private TerminationDTO termination;
  /** Domaine d’activité */
  private FieldOfActivityDTO fieldOfActivity;
  /** Centre coût */
  private CostCenterDTO costCenter;
  /** Mode paiement */
  private PaymentMethodDTO paymentMethod;
  /** Coefficient n/N */
  private BigDecimal nNCoef;
  /** Abattement de précarité */
  private BigDecimal shortTermContractDiscount;
  /** Loyer écrêté */
  private BigDecimal lopRent;
  /** Nombre d'occupants */
  private Integer householdSize;
  /** Nombre de mois de rétroactivité à effectuer */
  private Integer retroactivitysMonths;
  /** Le loyer prélevé fixe */
  private BigDecimal fixedWithdrawnRentAmount;
  /** Règlement du pécule de fin d'occupation */
  private Boolean terminationSavingsPayment;
  /** Date de règlement du pécule de fin d'occupation */
  private Date terminationSavingsPaymentDate;
  /** Coeficient n/N si contrat clos (champ fixe) */
  private BigDecimal fixedNNCoef;
  /** Agence si contrat clos (champ fixe) */
  private AgencyDTO fixedAgency;
  /** Avantage en nature si contrat clos (champ fixe) */
  private BigDecimal fixedBenefit;
  /** Loyer écrêté si contrat clos (champ fixe) */
  private BigDecimal fixedLopRent;
  /** Loyer surface corrigée si contrat clos (champ fixe) */
  private BigDecimal fixedRevisedSurfaceAreaRent;
  /** Surface corrigée si contrat clos (champ fixe) */
  private BigDecimal fixedRevisedSurfaceArea;
  /** Contrat clos (utilisation des valeurs "fixed") */
  private Boolean closedContract;
   /** Date limite de validité de l'attestation d'assurance */
  private Date insuranceCertificateEndDate;

  /**
   * CALCULATED PROPERTIES
   */
  /** Loyer net agent */
  private BigDecimal netAgentRent;
  /** Loyer prélevé */
  private BigDecimal withdrawnRent;
  /** Surface corrigée */
  private BigDecimal revisedSurfaceArea;
  /** Loyer surface corrigée */
  private BigDecimal revisedSurfaceAreaRent;
  /** Avantage en nature */
  private BigDecimal benefit;
  /** Loyer prélevé cumulé */
  private BigDecimal addedWithdrawnRent;

  /**
   * Constructeur du contractDTO
   * @param builder Le builder qui permet de construire le DTO
   */
  private ContractDTO(ContractBuilder builder) {
    id = builder.id;
    contractReference = builder.contractReference;
    startValidityDate = builder.startValidityDate;
    endValidityDate = builder.endValidityDateValue;
    addedWithdrawnRent = builder.addedWithdrawnRentValue;
    plainAddedWithdrawnRent = builder.plainAddedWithdrawnRent;
    terminationSavings = builder.terminationSavings;
    plainTerminationSavingAmount = builder.plainTerminationSavingAmountValue;
    tenant = builder.tenant;
    housing = builder.housing;
    fieldOfActivity = builder.fieldOfActivity;
    costCenter = builder.costCenter;
    rentTypology = builder.rentTypology;
    paymentMethod = builder.paymentMethod;
    termination = builder.termination;
    signature = builder.signatureValue;
    endValidityDate = builder.endValidityDateValue;
    marketRentPrice = builder.marketRentPriceValue;
    garageRent = builder.garageRentValue;
    gardenRent = builder.gardenRentValue;
    extraRent = builder.extraRentValue;
    lastSavingDate = builder.lastSavingDateValue;
    lastWithdrawnDate = builder.lastWithdrawnDateValue;
    expectedChargeCost = builder.expectedChargeCostValue;
    rentPriceLimit = builder.rentPriceLimitValue;
    insuranceReimbursement = builder.insuranceReimbursementValue;
    housingTaxReimbursement = builder.housingTaxReimbursementValue;
    garbageReimbursement = builder.garbageReimbursementValue;
    annualClearanceCharges = builder.annualClearanceChargesValue;
    garbageInvoicing = builder.garbageInvoicingValue;
    waterInvoicing = builder.waterInvoicingValue;
    otherInvoicingLabel = builder.otherInvoicingLabelValue;
    otherInvoicingAmount = builder.otherInvoicingAmountValue;
    realEstateRentalValue = builder.realEstateRentalValueValue;
    revisedSurfaceArea = builder.revisedSurfaceAreaValue;
    revisedSurfaceAreaRent = builder.revisedSurfaceAreaRentValue;
    nNCoef = builder.nNCoefValue;
    shortTermContractDiscount = builder.shortTermContractDiscountValue;
    lopRent = builder.lopRentValue;
    netAgentRent = builder.netAgentRentValue;
    withdrawnRent = builder.withdrawnRentValue;
    terminationSavingAmount = builder.terminationSavingAmountValue;
    benefit = builder.benefitValue;
    householdSize = builder.householdSize;
    retroactivitysMonths = builder.retroactivitysMonthsValue;
    fixedWithdrawnRentAmount = builder.fixedWithdrawnRentAmountValue;
    terminationSavingsPayment = builder.terminationSavingsPaymentValue;
    terminationSavingsPaymentDate = builder.terminationSavingsPaymentDateValue;
    fixedNNCoef = builder.fixedNNCoefValue;
    fixedAgency = builder.fixedAgencyValue;
    fixedBenefit = builder.fixedBenefitValue;
    fixedLopRent = builder.fixedLopRentValue;
    fixedRevisedSurfaceAreaRent = builder.fixedRevisedSurfaceAreaRentValue;
    fixedRevisedSurfaceArea = builder.fixedRevisedSurfaceAreaValue;
    closedContract = builder.closedContractValue;
    insuranceCertificateEndDate = builder.insuranceCertificateEndDateValue;
  }

  /** Class du builder Contract */
  public static class ContractBuilder {
    // Mandatory Values
    /** Id */
    private final Long id;
    /** Référence Contrat occupant */
    private final String contractReference;
    /** Date de début de validité */
    private final Date startValidityDate;
    /** Loyer prélevé cumulé fixe*/
    private final BigDecimal plainAddedWithdrawnRent;
    /** Pécule de fin d’occupation */
    private final Boolean terminationSavings;
    /** Occupant */
    private final TenantDTO tenant;
    /** Logement */
    private final HousingDTO housing;
    /** Domaine d’activité */
    private final FieldOfActivityDTO fieldOfActivity;
    /** Centre coût */
    private final CostCenterDTO costCenter;
    /** Typologie de loyer */
    private final RentTypologyDTO rentTypology;
    /** Mode paiement */
    private final PaymentMethodDTO paymentMethod;
    /** Motif de résiliation */
    private final TerminationDTO termination;
    /** Nombre d'occupants */
    private final Integer householdSize;

    // Optional values
    /** Date de dernier cumule de loyer prélevé */
    private Date lastWithdrawnDateValue;
    /** Date de dernier cumule de pécule de fin d’occupation */
    private Date lastSavingDateValue;
    /** Contrat signé */
    private Boolean signatureValue;
    /** Date de fin de validité */
    private Date endValidityDateValue;
    /** Loyer marché */
    private BigDecimal marketRentPriceValue;
    /** Loyer plafond */
    private BigDecimal rentPriceLimitValue;
    /** Loyer garage */
    private BigDecimal garageRentValue;
    /** Loyer jardin */
    private BigDecimal gardenRentValue;
    /** Surloyer */
    private BigDecimal extraRentValue;
    /** Charges prévisionnelles */
    private BigDecimal expectedChargeCostValue;
    /** Remboursement assurance */
    private BigDecimal insuranceReimbursementValue;
    /** Remboursement taxe habitation */
    private BigDecimal housingTaxReimbursementValue;
    /** Remboursement ordures ménagères */
    private BigDecimal garbageReimbursementValue;
    /** Apurement annuel charges locatives */
    private BigDecimal annualClearanceChargesValue;
    /** Facturation ordures ménagères */
    private BigDecimal garbageInvoicingValue;
    /** Facturation consommation eau */
    private BigDecimal waterInvoicingValue;
    /** Autre facturation libellé*/
    private String otherInvoicingLabelValue;
    /** Autre facturation montant */
    private BigDecimal otherInvoicingAmountValue;
    /** Montant de la valeur locative foncière */
    private BigDecimal realEstateRentalValueValue;
    /** Coefficient n/N */
    private BigDecimal nNCoefValue;
    /** Abattement de précarité */
    private BigDecimal shortTermContractDiscountValue;
    /** Loyer écrêté */
    private BigDecimal lopRentValue;
    /** Montant du pécule de fin d'occupation fixe */
    private BigDecimal plainTerminationSavingAmountValue;
    /** Nombre de mois de rétroactivité à effectuer */
    private Integer retroactivitysMonthsValue;
    /** Le loyer réellement prélevé */
    private BigDecimal fixedWithdrawnRentAmountValue;
    /** Règlement du pécule de fin d'occupation */
    private Boolean terminationSavingsPaymentValue;
    /** Date du règlement du pécule de fin d'occupation */
    private Date terminationSavingsPaymentDateValue;
    /** Coeficient n/N si contrat clos (champ fixe) */
    private BigDecimal fixedNNCoefValue;
    /** Agence si contrat clos (champ fixe) */
    private AgencyDTO fixedAgencyValue;
    /** Avantage en nature si contrat clos (champ fixe) */
    private BigDecimal fixedBenefitValue;
    /** Loyer écrêté si contrat clos (champ fixe) */
    private BigDecimal fixedLopRentValue;
    /** Loyer surface corrigée si contrat clos (champ fixe) */
    private BigDecimal fixedRevisedSurfaceAreaRentValue;
    /** Surface corrigée si contrat clos (champ fixe) */
    private BigDecimal fixedRevisedSurfaceAreaValue;
    /** Contrat clos (utilisation des valeurs "fixed") */
    private Boolean closedContractValue;
    /** Date limite de validité de l’attestation d’assurance */
    private Date insuranceCertificateEndDateValue;

    // Calculated values
    /** Loyer net agent */
    private BigDecimal netAgentRentValue;
    /** Loyer prélevé */
    private BigDecimal withdrawnRentValue;
    /** Surface corrigée */
    private BigDecimal revisedSurfaceAreaValue;
    /** Loyer surface corrigée */
    private BigDecimal revisedSurfaceAreaRentValue;
    /** Montant du pécule de fin d'occupation */
    private BigDecimal terminationSavingAmountValue;
    /** Avantage en nature */
    private BigDecimal benefitValue;
    /** Loyer prélevé cumulé */
    private BigDecimal addedWithdrawnRentValue;

    /**
     * constructeur du builder Contract
     * @param id identifiant d'un contrat occupant
     * @param contractReference référence du contrat
     * @param startValidityDate la date de validité
     * @param plainAddedWithdrawnRent Loyer prélevé cumulé fixe
     * @param terminationSavings pécule de fin d'occupation
     * @param tenant l'occupant
     * @param housing le logement
     * @param fieldOfActivity Domaine d’activité
     * @param costCenter le centre coût
     * @param rentTypology la typologie de loyer
     * @param paymentMethod la méthode de paiement
     * @param termination le motif de résiliation
     * @param householdSize le nombre d'occupants
     * @param retroactivitysMonths le nombre de mois de rétroactivité
     */
    public ContractBuilder(Long id, String contractReference, Date startValidityDate, BigDecimal plainAddedWithdrawnRent, Boolean terminationSavings, TenantDTO tenant,
      HousingDTO housing, FieldOfActivityDTO fieldOfActivity, CostCenterDTO costCenter, RentTypologyDTO rentTypology, PaymentMethodDTO paymentMethod, TerminationDTO termination,
      Integer householdSize, Integer retroactivitysMonths) {
      this.id = id;
      this.contractReference = contractReference;
      this.startValidityDate = DateTimeUtils.clone(startValidityDate);
      this.plainAddedWithdrawnRent = plainAddedWithdrawnRent;
      this.terminationSavings = terminationSavings;
      this.tenant = tenant;
      this.housing = housing;
      this.fieldOfActivity = fieldOfActivity;
      this.costCenter = costCenter;
      this.rentTypology = rentTypology;
      this.paymentMethod = paymentMethod;
      this.termination = termination;
      this.householdSize = householdSize;
      retroactivitysMonthsValue = retroactivitysMonths;
    }

    /**
     * Constructeur du builder de la date de dernier cumule de loyer prélevé
     * @param value la valeur
     * @return le loyer prélevé mise à jour
     */
    public ContractBuilder lastWithdrawnDate(Date value) {
      lastWithdrawnDateValue = DateTimeUtils.clone(value);
      return this;
    }

    /**
     * Constructeur du builder de la date de dernier cumule de pécule de fin d’occupation
     * @param value la valeur
     * @return la date de dernier cumule de pécule de fin d’occupation mis à jour
     */
    public ContractBuilder lastSavingDate(Date value) {
      lastSavingDateValue = DateTimeUtils.clone(value);
      return this;
    }

    /**
     * Constructeur du builder de contrat signé
     * @param value la valeur
     * @return le contrat signé
     */
    public ContractBuilder signature(Boolean value) {
      signatureValue = value;
      return this;
    }

    /**
     * Constructeur du builder de la date de fin de validité
     * @param value la valeur
     * @return la date de fin de validité mise à jour
     */
    public ContractBuilder endValidityDate(Date value) {
      endValidityDateValue = DateTimeUtils.clone(value);
      return this;
    }

    /**
     * Constructeur du builder du loyer marché
     * @param value la valeur
     * @return le loyer marché mis à jour
     */
    public ContractBuilder marketRentPrice(BigDecimal value) {
      marketRentPriceValue = value;
      return this;
    }

    /**
     * Constructeur du builder du loyer plafond
     * @param value la valeur
     * @return le loyer plafond mis à jour
     */
    public ContractBuilder rentPriceLimit(BigDecimal value) {
      rentPriceLimitValue = value;
      return this;
    }

    /**
     * Constructeur du builder du loyer garage
     * @param value la valeur
     * @return le loyer garage mis à jour
     */
    public ContractBuilder garageRent(BigDecimal value) {
      garageRentValue = value;
      return this;
    }

    /**
     * Constructeur du builder du loyer jardin
     * @param value la valeur
     * @return le loyer jardin mis à jour
     */
    public ContractBuilder gardenRent(BigDecimal value) {
      gardenRentValue = value;
      return this;
    }

    /**
     * Constructeur du builder du surloyer
     * @param value la valeur
     * @return le surloyer mis à jour
     */
    public ContractBuilder extraRent(BigDecimal value) {
      extraRentValue = value;
      return this;
    }

    /**
     * Constructeur du builder des charges prévisionnelles
     * @param value la valeur
     * @return les charges prévisionnelles misent à jour
     */
    public ContractBuilder expectedChargeCost(BigDecimal value) {
      expectedChargeCostValue = value;
      return this;
    }

    /**
     * Constructeur du builder du remboursement assurance
     * @param value la valeur
     * @return le remboursement assurance mis à jour
     */
    public ContractBuilder insuranceReimbursement(BigDecimal value) {
      insuranceReimbursementValue = value;
      return this;
    }

    /**
     * Constructeur du builder du remboursement de la taxe habitation
     * @param value la valeur
     * @return le remboursement de la taxe habitation mis à jour
     */
    public ContractBuilder housingTaxReimbursement(BigDecimal value) {
      housingTaxReimbursementValue = value;
      return this;
    }

    /**
     * Constructeur du builder du remboursement des ordures ménagères
     * @param value la valeur
     * @return le remboursement des ordures ménagères mis à jour
     */
    public ContractBuilder garbageReimbursement(BigDecimal value) {
      garbageReimbursementValue = value;
      return this;
    }

    /**
     * Constructeur du builder de l’apurement annuel des charges locatives
     * @param value la valeur
     * @return l’apurement annuel des charges locatives mis à jour
     */
    public ContractBuilder annualClearanceCharges(BigDecimal value) {
      annualClearanceChargesValue = value;
      return this;
    }

    /**
     * Constructeur du builder de la facturation ordures ménagères
     * @param value la valeur
     * @return la facturation ordures ménagères mise à jour
     */
    public ContractBuilder garbageInvoicing(BigDecimal value) {
      garbageInvoicingValue = value;
      return this;
    }

    /**
     * Constructeur du builder de la facturation consommation eau
     * @param value la valeur
     * @return la facturation consommation eau mise à jour
     */
    public ContractBuilder waterInvoicing(BigDecimal value) {
      waterInvoicingValue = value;
      return this;
    }

    /**
     * Constructeur du builder du libelle de la facturation autre
     * @param value la valeur
     * @return le libelle de la facturation autre mis à jour
     */
    public ContractBuilder otherInvoicingLabel(String value) {
      otherInvoicingLabelValue = value;
      return this;
    }

    /**
     * Constructeur du builder du montant de la facturation autre
     * @param value la valeur
     * @return le montant de la facturation autre
     */
    public ContractBuilder otherInvoicingAmount(BigDecimal value) {
      otherInvoicingAmountValue = value;
      return this;
    }

    /**
     * Constructeur du builder montant de la valeur locative foncière
     * @param value la valeur
     * @return la valeur locative foncière mise à jour
     */
    public ContractBuilder realEstateRentalValue(BigDecimal value) {
      realEstateRentalValueValue = value;
      return this;
    }

    /**
     * Constructeur du builder de la surface corrigée
     * @param value la valeur
     * @return la surface corrigée mise à jour
     */
    public ContractBuilder revisedSurfaceArea(BigDecimal value) {
      revisedSurfaceAreaValue = value;
      return this;
    }

    /**
     * Constructeur du builder du loyer surface corrigée
     * @param value la valeur
     * @return le loyer surface corrigée mis à jour
     */
    public ContractBuilder revisedSurfaceAreaRent(BigDecimal value) {
      revisedSurfaceAreaRentValue = value;
      return this;
    }

    /**
     * Constructeur du builder du coefficient n/N
     * @param value la valeur
     * @return le coefficient n/N mis à jour
     */
    public ContractBuilder nNCoef(BigDecimal value) {
      nNCoefValue = value;
      return this;
    }

    /**
     * Constructeur du builder d'abattement de précarité
     * @param value la valeur
     * @return l'abattement de précarité mis à jour
     */
    public ContractBuilder shortTermContractDiscount(BigDecimal value) {
      shortTermContractDiscountValue = value;
      return this;
    }

    /**
     * Constructeur du builder du loyer écrêté
     * @param value la valeur
     * @return le loyer écrêté
     */
    public ContractBuilder lopRent(BigDecimal value) {
      lopRentValue = value;
      return this;
    }

    /**
     * Constructeur du builder loyer net agent
     * @param value la valeur
     * @return le loyer net agent mis à jour
     */
    public ContractBuilder netAgentRent(BigDecimal value) {
      netAgentRentValue = value;
      return this;
    }

    /**
     * Constructeur du builder du loyer prélevé
     * @param value la valeur
     * @return le loyer prélevé mis à jour
     */
    public ContractBuilder withdrawnRent(BigDecimal value) {
      withdrawnRentValue = value;
      return this;
    }

    /**
     * Constructeur du builder du montant du pécule de fin d'occupation fixe
     * @param value la valeur
     * @return le montant du pécule de fin d'occupation mis à jour
     */
    public ContractBuilder plainTerminationSavingAmount(BigDecimal value) {
      plainTerminationSavingAmountValue = value;
      return this;
    }

    /**
     * Constructeur du builder du montant du pécule de fin d'occupation
     * @param value la valeur
     * @return le montant du pécule de fin d'occupation mis à jour
     */
    public ContractBuilder terminationSavingAmount(BigDecimal value) {
      terminationSavingAmountValue = value;
      return this;
    }

    /**
     * Constructeur du builder d'avantage en nature
     * @param value la valeur
     * @return l'avantage en nature mis à jour
     */
    public ContractBuilder benefit(BigDecimal value) {
      benefitValue = value;
      return this;
    }

    /**
     * Constructeur du builder du loyer prélevé cumulé
     * @param value la valeur
     * @return le loyer prélevé cumulé mis à jour
     */
    public ContractBuilder addedWithdrawnRent(BigDecimal value) {
      addedWithdrawnRentValue = value;
      return this;
    }

    /**
     * Constructeur du builder du loyer réellement prélevé
     * @param value la valeur
     * @return le loyer prélevé fixe
     */
    public ContractBuilder fixedWithdrawnRent(BigDecimal value) {
      fixedWithdrawnRentAmountValue = value;
      return this;
    }

    /**
     * Constructeur du builder du loyer réellement prélevé
     * @param value la valeur
     * @return le loyer prélevé fixe
     */
    public ContractBuilder retroactivitysMonths(Integer value) {
      retroactivitysMonthsValue = value;
      return this;
    }

    /**
     * Constructeur du builder de règlement du pécule de fin d'occupation
     * @param value le règlement du pécule de fin d'occupation
     * @return le contrat avec une valeur pour le règlement du pécule de fin d'occupation
     */
    public ContractBuilder terminationSavingsPayment(Boolean value) {
      terminationSavingsPaymentValue = value;
      return this;
    }

    /**
     * Constructeur du builder pour la date du règlement du pécule de fin d'occupation
     * @param value la date du règlement du pécule de fin d'occupation
     * @return le contrat avec une valeur pour la date du règlement du pécule de fin d'occupation
     */
    public ContractBuilder terminationSavingsPaymentDate(Date value) {
      terminationSavingsPaymentDateValue = DateTimeUtils.clone(value);
      return this;
    }

    /**
     * Constructeur du builder pour le coeficient n/N si contrat clos (champ fixe)
     * @param value le coefficient n/N lors de la cloture du contrat
     * @return le contrat avec une valeur pour le coeficient n/N si contrat clos
     */
    public ContractBuilder fixedNNCoef(BigDecimal value) {
      fixedNNCoefValue = value;
      return this;
    }

    /**
     * Constructeur du builder pour l'agence si contrat clos (champ fixe)
     * @param value l'agence lors de la cloture du contrat
     * @return le contrat avec une valeur pour l'agence si contrat clos
     */
    public ContractBuilder fixedAgency(AgencyDTO value) {
      fixedAgencyValue = value;
      return this;
    }

    /**
     * Constructeur du builder pour les avantages en nature si contrat clos (champ fixe)
     * @param value la somme des avantages en nature lors de la cloture du contrat
     * @return le contrat avec une valeur pour les avantages en nature si contrat clos
     */
    public ContractBuilder fixedBenefit(BigDecimal value) {
      fixedBenefitValue = value;
      return this;
    }

    /**
     * Constructeur du builder pour le loyer écrêté si contrat clos (champ fixe)
     * @param value le loyer écrêté lors de la cloture du contrat
     * @return le contrat avec une valeur pour le loyer écrêté si contrat clos
     */
    public ContractBuilder fixedLopRent(BigDecimal value) {
      fixedLopRentValue = value;
      return this;
    }

    /**
     * Constructeur du builder pour le loyer surface corrigée si contrat clos (champ fixe)
     * @param value le loyer surface corrigée lors de la cloture du contrat
     * @return le contrat avec une valeur pour le loyer surface corrigée si contrat clos
     */
    public ContractBuilder fixedRevisedSurfaceAreaRent(BigDecimal value) {
      fixedRevisedSurfaceAreaRentValue = value;
      return this;
    }

    /**
     * Constructeur du builder pour la surface corrigée si contrat clos (champ fixe)
     * @param value la surface corrigée lors de la cloture du contrat
     * @return le contrat avec une valeur pour la surface corrigée si contrat clos
     */
    public ContractBuilder fixedRevisedSurfaceArea(BigDecimal value) {
      fixedRevisedSurfaceAreaValue = value;
      return this;
    }

    /**
     * Constructeur du builder pour cloturer le contrat
     * @param value vrai ou faux si le contrat est clos ou non
     * @return le contrat avec une valeur pour de cloture
     */
    public ContractBuilder closedContract(Boolean value) {
      closedContractValue = value;
      return this;
    }

    /**
     * Constructeur du builder pour la date de validité de l'attestation d'assurance
     * @param value la date en question
     * @return le contrat avec une valeur pour la date limite de validité de l'attestation d'assurance
     */
    public ContractBuilder insuranceCertificateEndDate(Date value) {
      insuranceCertificateEndDateValue = DateTimeUtils.clone(value);
      return this;
    }

    /**
     * Fonction qui renvoi un ContractDTO, Necessaire pour finir le builder
     * @return un contractDTO
     */
    public ContractDTO build() {
      return new ContractDTO(this);
    }

  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the signature
   */
  public Boolean getSignature() {
    return signature;
  }

  /**
   * Renvoi le contrat signé s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers contrats signés.
   * @return le contrat signé
   */
  public String getSortedSignature() {
    if (null == signature) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(signature.toString());
    }
  }

  /**
   * @return the startValidityDate
   */
  public Date getStartValidityDate() {
    return DateTimeUtils.clone(startValidityDate);
  }

  /**
   * @return the endValidityDate
   */
  public Date getEndValidityDate() {
    return DateTimeUtils.clone(endValidityDate);
  }

  /**
   * Renvoi la date formatée s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celles-ci après les dernières dates.
   * @return la date de fin de validité
   */
  public long getSortedEndValidityDate() {
    return DatatablePropertiesSorted.getSortedDate(endValidityDate);
  }

  /**
   * @return the marketRentPrice
   */
  public BigDecimal getMarketRentPrice() {
    return marketRentPrice;
  }

  /**
   * Renvoi le loyer marché s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers loyer marché.
   * @return le loyer marché
   */
  public BigDecimal getSortedMarketRentPrice() {
    return DatatablePropertiesSorted.getSortedBigDecimal(marketRentPrice);
  }

  /**
   * @return the rentPriceLimit
   */
  public BigDecimal getRentPriceLimit() {
    return rentPriceLimit;
  }

  /**
   * Renvoi le loyer plafond s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers loyer plafond.
   * @return le loyer plafond
   */
  public BigDecimal getSortedRentPriceLimit() {
    return DatatablePropertiesSorted.getSortedBigDecimal(rentPriceLimit);
  }

  /**
   * @return the garageRent
   */
  public BigDecimal getGarageRent() {
    return garageRent;
  }

  /**
   *  Renvoi le loyer garage s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers loyer garage.
   * @return le loyer garage
   */
  public BigDecimal getSortedGarageRent() {
    return DatatablePropertiesSorted.getSortedBigDecimal(garageRent);
  }

  /**
   * @return the gardenRent
   */
  public BigDecimal getGardenRent() {
    return gardenRent;
  }

  /**
   * Renvoi le loyer jardin s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers loyer jardin.
   * @return le loyer jardin
   */
  public BigDecimal getSortedGardenRent() {
    return DatatablePropertiesSorted.getSortedBigDecimal(gardenRent);
  }

  /**
   * @return the extraRent
   */
  public BigDecimal getExtraRent() {
    return extraRent;
  }

  /**
   * Renvoi le surloyer s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers surloyer.
   * @return le surloyer
   */
  public BigDecimal getSortedExtraRent() {
    return DatatablePropertiesSorted.getSortedBigDecimal(extraRent);
  }

  /**
   * @return the expectedChargeCost
   */
  public BigDecimal getExpectedChargeCost() {
    return expectedChargeCost;
  }

  /**
   * Renvoi les charges prévisionnelles s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celle-ci après les dernières charges prévisionnelles.
   * @return Les charges prévisionnelles
   */
  public BigDecimal getSortedExpectedChargeCost() {
    return DatatablePropertiesSorted.getSortedBigDecimal(expectedChargeCost);
  }

  /**
   * @return the annualClearanceCharges
   */
  public BigDecimal getAnnualClearanceCharges() {
    return annualClearanceCharges;
  }

  /**
   * Renvoi l’apurement annuel des charges locatives s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celle-ci après les dernièrs apurements annuel des charges.
   * @return l’apurement annuel des charges
   */
  public BigDecimal getSortedAnnualClearanceCharges() {
    return DatatablePropertiesSorted.getSortedBigDecimal(annualClearanceCharges);
  }

  /**
   * @return the addedWithdrawnRent
   */
  public BigDecimal getAddedWithdrawnRent() {
    return addedWithdrawnRent;
  }

  /**
   * @return the lastWithdrawnDate
   */
  public Date getLastWithdrawnDate() {
    return DateTimeUtils.clone(lastWithdrawnDate);
  }

  /**
   * @return the terminationSavings
   */
  public Boolean getTerminationSavings() {
    return terminationSavings;
  }

  /**
   * @return the terminationSavingAmount
   */
  public BigDecimal getTerminationSavingAmount() {
    return terminationSavingAmount;
  }

  /**
   * @return the lastSavingDate
   */
  public Date getLastSavingDate() {
    return DateTimeUtils.clone(lastSavingDate);
  }

  /**
   * @return the realEstateRentalValue
   */
  public BigDecimal getRealEstateRentalValue() {
    return realEstateRentalValue;
  }

  /**
   * Renvoi le montant de la valeur locative foncière s'il n'est pas vide ou null.
   * Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers montants de la valeur locative foncière.
   * @return Montant de la valeur locative foncière
   */
  public BigDecimal getSortedRealEstateRentalValue() {
    return DatatablePropertiesSorted.getSortedBigDecimal(realEstateRentalValue);
  }

  /**
   * @return the housing
   */
  public HousingDTO getHousing() {
    return housing;
  }

  /**
   * @return the tenant
   */
  public TenantDTO getTenant() {
    return tenant;
  }

  /**
   * @return the rentTypology
   */
  public RentTypologyDTO getRentTypology() {
    return rentTypology;
  }

  /**
   * @return the termination
   */
  public TerminationDTO getTermination() {
    return termination;
  }

  /**
   * Renvoi le label s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers labels.
   * @return le label
   */
  public String getSortedTerminationLabel() {
    if (null == termination) {
      return DTOConstants.END_SORTED_STRING;
    } else {
      return DatatablePropertiesSorted.getSortedString(termination.getLabel());
    }
  }

  /**
   * @return the fieldOfActivity
   */
  public FieldOfActivityDTO getFieldOfActivity() {
    return fieldOfActivity;
  }

  /**
   * @return the costCenter
   */
  public CostCenterDTO getCostCenter() {
    return costCenter;
  }

  /**
   * @return the paymentMethod
   */
  public PaymentMethodDTO getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * @return the revisedSurfaceArea
   */
  public BigDecimal getRevisedSurfaceArea() {
    return revisedSurfaceArea;
  }

  /**
   * @return the revisedSurfaceAreaRent
   */
  public BigDecimal getRevisedSurfaceAreaRent() {
    return revisedSurfaceAreaRent;
  }

  /**
   * @return the nNCoef
   */
  public BigDecimal getnNCoef() {
    return nNCoef;
  }

  /**
   * Renvoi le Coefficient n/N s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers Coefficient n/N.
   * @return the nNCoef
   */
  public BigDecimal getSortednNCoef() {
    return DatatablePropertiesSorted.getSortedBigDecimal(nNCoef);
  }

  /**
   * @return the netAgentRent
   */
  public BigDecimal getNetAgentRent() {
    return netAgentRent;
  }

  /**
   * Renvoi le loyer net agent s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers loyers net agent.
   * @return le loyer net agent
   */
  public BigDecimal getSortedNetAgentRent() {
    return DatatablePropertiesSorted.getSortedBigDecimal(netAgentRent);
  }

  /**
   * @return the shortTermContractDiscount
   */
  public BigDecimal getShortTermContractDiscount() {
    return shortTermContractDiscount;
  }

  /**
   * @return the lopRent
   */
  public BigDecimal getLopRent() {
    return lopRent;
  }

  /**
   * @return the withdrawnRent
   */
  public BigDecimal getWithdrawnRent() {
    return withdrawnRent;
  }

  /**
   *  Renvoi le loyer prélevé s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers loyers prélevé.
   * @return le loyer prélevé
   */
  public BigDecimal getSortedWithdrawnRent() {
    return DatatablePropertiesSorted.getSortedBigDecimal(withdrawnRent);
  }

  /**
   * @return the benefit
   */
  public BigDecimal getBenefit() {
    return benefit;
  }

  /**
   * Renvoi l'avantage en nature s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers avantages en nature.
   * @return l'avantage en nature
   */
  public BigDecimal getSortedBenefit() {
    if (BooleanUtils.isTrue(closedContract)) {
      return DatatablePropertiesSorted.getSortedBigDecimal(fixedBenefit);
    } else {
      return DatatablePropertiesSorted.getSortedBigDecimal(benefit);
    }
  }

  /**
   * @return the plainAddedWithdrawnRent
   */
  public BigDecimal getPlainAddedWithdrawnRent() {
    return plainAddedWithdrawnRent;
  }

  /**
   * @return the contractReference
   */
  public String getContractReference() {
    return contractReference;
  }

  /**
   * @return the plainTerminationSavingAmount
   */
  public BigDecimal getPlainTerminationSavingAmount() {
    return plainTerminationSavingAmount;
  }

  /**
   * Renvoi le montant du pécule s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celui-ci après les derniers montants du pécule.
   * @return le montant du pécule de fin
   */
  public BigDecimal getSortedTerminationSavingAmount() {
    return DatatablePropertiesSorted.getSortedBigDecimal(plainTerminationSavingAmount);
  }

  /**
   * @return the garbageInvoicing
   */
  public BigDecimal getGarbageInvoicing() {
    return garbageInvoicing;
  }

  /**
   * Renvoi la facturation des ordures ménagères si elle n’est pas vide ou null. Si c’est le cas renvoi une chaine plaçant celle-ci après les dernières facturations des ordures ménagères.
   * @return la facturation des ordures ménagères
   */
  public BigDecimal getSortedGarbageInvoicing() {
    return DatatablePropertiesSorted.getSortedBigDecimal(garbageInvoicing);
  }

  /**
   * @return the waterInvoicing
   */
  public BigDecimal getWaterInvoicing() {
    return waterInvoicing;
  }

  /**
   * Renvoi la facturation de la consommation d’eau si elle n’est pas vide ou null. Si c’est le cas renvoi une chaine plaçant celle-ci après les dernières facturations de la consommation d’eau.
   * @return la facturation de la consommation d’eau
   */
  public BigDecimal getSortedWaterInvoicing() {
    return DatatablePropertiesSorted.getSortedBigDecimal(waterInvoicing);
  }

  /**
   * @return the insuranceReimbursement
   */
  public BigDecimal getInsuranceReimbursement() {
    return insuranceReimbursement;
  }

  /**
   * Renvoi le remboursement assurance si elle n’est pas vide ou null. Si c’est le cas renvoi une chaine plaçant celle-ci après les dernièrs remboursements assurance.
   * @return le remboursement assurance
   */
  public BigDecimal getSortedInsuranceReimbursement() {
    return DatatablePropertiesSorted.getSortedBigDecimal(insuranceReimbursement);
  }

  /**
   * @return the housingTaxReimbursement
   */
  public BigDecimal getHousingTaxReimbursement() {
    return housingTaxReimbursement;
  }

  /**
   * Renvoi le remboursement taxe habitation si elle n’est pas vide ou null. Si c’est le cas renvoi une chaine plaçant celle-ci après les dernièrs remboursements taxe habitation.
   * @return le remboursement taxe habitation
   */
  public BigDecimal getSortedHousingTaxReimbursement() {
    return DatatablePropertiesSorted.getSortedBigDecimal(housingTaxReimbursement);
  }

  /**
   * @return the garbageReimbursement
   */
  public BigDecimal getGarbageReimbursement() {
    return garbageReimbursement;
  }

  /**
   * Renvoi le remboursement taxe ordures ménagères si elle n’est pas vide ou null. Si c’est le cas renvoi une chaine plaçant celle-ci après les dernièrs remboursements taxes ordures ménagères.
   * @return le remboursement taxe ordures ménagères
   */
  public BigDecimal getSortedGarbageReimbursement() {
    return DatatablePropertiesSorted.getSortedBigDecimal(garbageReimbursement);
  }

  /**
   * @return the otherInvoicingLabel
   */
  public String getOtherInvoicingLabel() {
    return otherInvoicingLabel;
  }

  /**
   * @return the otherInvoicingAmount
   */
  public BigDecimal getOtherInvoicingAmount() {
    return otherInvoicingAmount;
  }

  /**
   * Renvoi le montant de la facturation « Autre » s'il n'est pas vide ou null. Si c'est le cas renvoi une chaine plaçant celle-ci après les dernièrs montants de la facturation « Autre ».
   * @return le montant de la facturation « Autre »
   */
  public BigDecimal getSortedOtherInvoicingAmount() {
    return DatatablePropertiesSorted.getSortedBigDecimal(otherInvoicingAmount);
  }

  /**
   * @return the householdSize
   */
  public Integer getHouseholdSize() {
    return householdSize;
  }

  /**
   * @return the retroactivitysMonths
   */
  public Integer getRetroactivitysMonths() {
    return retroactivitysMonths;
  }

  /**
   * @return the fixedWithdrawnRentAmount
   */
  public BigDecimal getFixedWithdrawnRent() {
    return fixedWithdrawnRentAmount;
  }

  /**
   * @return the terminationSavingsPayment
   */
  public Boolean getTerminationSavingsPayment() {
    return terminationSavingsPayment;
  }

  /**
   * @return the terminationSavingsPayment
   */
  public Date getTerminationSavingsPaymentDate() {
    return DateTimeUtils.clone(terminationSavingsPaymentDate);
  }

  /**
   * @return the fixedNNCoef
   */
  public BigDecimal getFixedNNCoef() {
    return fixedNNCoef;
  }

  /**
   * @return the fixedAgency
   */
  public AgencyDTO getFixedAgency() {
    return fixedAgency;
  }

  /**
   * @return the fixedRevisedSurfaceArea
   */
  public BigDecimal getFixedRevisedSurfaceArea() {
    return fixedRevisedSurfaceArea;
  }

  /**
   * @return the fixedRevisedSurfaceAreaRent
   */
  public BigDecimal getFixedRevisedSurfaceAreaRent() {
    return fixedRevisedSurfaceAreaRent;
  }

  /**
   * @return the fixedLopRent
   */
  public BigDecimal getFixedLopRent() {
    return fixedLopRent;
  }

  /**
   * @return the fixedBenefit
   */
  public BigDecimal getFixedBenefit() {
    return fixedBenefit;
  }

  /**
   * @return the closedContract
   */
  public Boolean getClosedContract() {

    return closedContract;
  }

  /**
   * @return the insuranceCertificateEndDate
   */
  public Date getInsuranceCertificateEndDate() {
    return DateTimeUtils.clone(insuranceCertificateEndDate);
  }

  /**
   * Méthode de tri des dates de limite de validité de l’attestation d’assurance
   * @return un long représentant la date facilitant ainsi le tri
   */
  public long getSortedInsuranceCertificateEndDate() {
    return DatatablePropertiesSorted.getSortedDate(insuranceCertificateEndDate);
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur du coeficient n/N au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public BigDecimal getNNCoefDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedNNCoef;
    } else {
      return nNCoef;
    }
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur de l'agence au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public AgencyDTO getAgencyDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedAgency;
    } else {
      return housing.getAgency();
    }
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur de la surface corrigée au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public BigDecimal getRevisedSurfaceAreaDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedRevisedSurfaceArea;
    } else {
      return revisedSurfaceArea;
    }
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur du loyer surface corrigée au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public BigDecimal getRevisedSurfaceAreaRentDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedRevisedSurfaceAreaRent;
    } else {
      return revisedSurfaceAreaRent;
    }
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur du loyer écrêté au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public BigDecimal getLopRentDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedLopRent;
    } else {
      return lopRent;
    }
  }

  /**
   * Nécessaire pour l'affichage du résultat de recherche
   * @return la valeur des avantages en nature au moment de la cloture si contrat clos, la valeur actuelle sinon.
   */
  public BigDecimal getBenefitDisplay() {
    if (BooleanUtils.isTrue(closedContract)) {
      return fixedBenefit;
    } else {
      return benefit;
    }
  }
}
