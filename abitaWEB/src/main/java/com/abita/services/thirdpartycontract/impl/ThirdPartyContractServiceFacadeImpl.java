package com.abita.services.thirdpartycontract.impl;

import com.abita.dao.thirdpartycontract.entity.ThirdPartyContractEntity;
import com.abita.dao.thirdpartycontract.exceptions.ThirdPartyContractDAOException;
import com.abita.services.detailcron.IDetailCronService;
import com.abita.services.detailcron.exceptions.DetailCronServiceException;
import com.abita.services.housing.IHousingServiceFacade;
import com.abita.services.housing.exceptions.HousingServiceFacadeException;
import com.abita.services.revisionthirdpartycontract.IRevisionThirdPartyContractService;
import com.abita.services.revisionthirdpartycontract.constants.RevisionThirdPartyContractConstants;
import com.abita.services.revisionthirdpartycontract.exceptions.RevisionThirdPartyContractServiceException;
import com.abita.services.thirdpartycontract.IThirdPartyContractServiceFacade;
import com.abita.services.ylznaccountingdocnumber.IYlZnAccountingDocNumberService;
import com.abita.services.ylznaccountingdocnumber.exceptions.YlZnAccountingDocNumberServiceException;
import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.dateutil.DateTimeUtils;
import com.abita.util.thirdpartycontract.ThirdPartyContractUtils;
import com.abita.util.ylznaccountingdocnumber.YlZnAccountingDocNumberUtils;
import com.abita.dao.thirdpartycontract.IThirdPartyContractDAO;
import com.abita.dto.RevisionThirdPartyContractDTO;
import com.abita.dto.ThirdPartyContractDTO;
import com.abita.dto.YlZnAccountingDocNumberDTO;
import com.abita.dto.unpersist.HousingCriteriaDTO;
import com.abita.dto.unpersist.ThirdPartyContractCriteriaDTO;
import com.abita.services.thirdpartycontract.exceptions.ThirdPartyContractServiceException;
import com.abita.web.shared.ReasonForExit;
import com.services.common.impl.AbstractService;
import com.services.common.util.SafetyUtils;
import org.dozer.MappingException;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.YearMonth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe d'implémentation des services des contrats tiers
 *
 * @author
 */
public class ThirdPartyContractServiceFacadeImpl extends
  AbstractService<ThirdPartyContractEntity, ThirdPartyContractDTO, ThirdPartyContractDAOException, Long, ThirdPartyContractServiceException, IThirdPartyContractDAO> implements
        IThirdPartyContractServiceFacade {

  private static final String ERREUR_SURVENUE_MAJ_LOGEMENT_ASSOCIE = "Une erreur est survenue lors de la mise à jour du logement associé";
  /**
  * Le DAO des contrats tiers
  */
  private IThirdPartyContractDAO thirdPartyContractDAO;

  /** Service des informations nécessaires à la gestion du numéro de document des pièces comptables YL-ZN */
  private IYlZnAccountingDocNumberService ylZnAccountingDocNumberService;

  /** Service des logements */
  private transient IHousingServiceFacade housingServiceFacade;

  /** Service du détails des cron */
  private transient IDetailCronService detailCronService;

  /** Service des révisions des contrats tiers */
  private transient IRevisionThirdPartyContractService revisionThirdPartyContractService;

  @Override
  protected IThirdPartyContractDAO getSpecificIDAO() {

    return thirdPartyContractDAO;
  }

  @Override
  public Long create(ThirdPartyContractDTO thirdPartyContractDTO) throws ThirdPartyContractServiceException {
    thirdPartyContractDTO.setYlRegularization(0);
    return (Long) super.create(thirdPartyContractDTO);
  }

  @Override
  public void update(ThirdPartyContractDTO thirdPartyContractDTO) throws ThirdPartyContractServiceException {
    // récupération des générations
    try {
      YlZnAccountingDocNumberDTO lastYlZnAccountingDocNumberDTOs = ylZnAccountingDocNumberService.findLastGenerationByThirdPartyContractId(thirdPartyContractDTO.getId());
      if (lastYlZnAccountingDocNumberDTOs != null) {

        // le remboursement est déclenché seulement quand le contrat n’était pas déjà clos
        if (thirdPartyContractDTO.getCancellationDate() != null && lastYlZnAccountingDocNumberDTOs.getYlZnAdnCancellationDate() == null
          || thirdPartyContractDTO.getCancellationDate() != null && lastYlZnAccountingDocNumberDTOs.getYlZnAdnCancellationDate().after(new Date())) {

          LocalDate cancellationDate = new LocalDate(thirdPartyContractDTO.getCancellationDate());
          String paymentCycle = thirdPartyContractDTO.getPaymentCycle().getLabel();
          Boolean expiryDate = thirdPartyContractDTO.getExpiryDate();

          // récupération du mois de génération YL du cycle de la date de fin de validité
          YearMonth yearMonthOfTreatment = DateTimeUtils.getYearMonthOfCycle(cancellationDate, paymentCycle, expiryDate);

          // récupération de toutes les générations à partir de ce mois
          List<YlZnAccountingDocNumberDTO> ylZnAccountingDocNumberDTOs = ylZnAccountingDocNumberService.findGenerationsByThirdPartyContractIdFromYearMonth(
            thirdPartyContractDTO.getId(), yearMonthOfTreatment);

          // le nombre de remboursement est égal au nombre de générations
          Integer numberOfRegularizations = ylZnAccountingDocNumberDTOs.size();
          thirdPartyContractDTO.setYlRegularization(numberOfRegularizations);

        } else {
          thirdPartyContractDTO.setYlRegularization(0);
        }
      }
    } catch (YlZnAccountingDocNumberServiceException e) {
      logger.error("Une erreur est survenue lors de la mise à jour de la régularisation du contrat", e);
    }
    super.update(thirdPartyContractDTO);

    // une fois le contrat tiers enregistré, on met à jour le logement si une date de résiliation du contrat a été renseignée
    if (thirdPartyContractDTO.getCancellationDate() != null) {
      // mise à jour de la date de sortie du logement avec la date de résiliation du contrat
      thirdPartyContractDTO.getHousing().setUnregisterDate(thirdPartyContractDTO.getCancellationDate());
      // mise à jour du motif de sortie à "Résiliation"
      thirdPartyContractDTO.getHousing().setReasonForExit(ReasonForExit.TERMINATION);
      try {
        housingServiceFacade.update(thirdPartyContractDTO.getHousing());
      } catch (HousingServiceFacadeException e) {
        logger.error(ERREUR_SURVENUE_MAJ_LOGEMENT_ASSOCIE, e);
        throw new ThirdPartyContractServiceException(ERREUR_SURVENUE_MAJ_LOGEMENT_ASSOCIE, e);
      }
    }
  }

  @Override
  public void delete(Long id) throws ThirdPartyContractServiceException {
    try {
      ylZnAccountingDocNumberService.deleteAllYlZnAccountingDocNumberOfOneContract(id);
      revisionThirdPartyContractService.deleteAllRevisionsOfOneContract(id);
      thirdPartyContractDAO.delete(id);
    } catch (YlZnAccountingDocNumberServiceException e) {
      throw new ThirdPartyContractServiceException(e);
    } catch (ThirdPartyContractDAOException e) {
      throw new ThirdPartyContractServiceException(e);
    } catch (RevisionThirdPartyContractServiceException e) {
      throw new ThirdPartyContractServiceException(e);
    }
  }

  @Override
  public ThirdPartyContractDTO get(Long id) throws ThirdPartyContractServiceException {
    ThirdPartyContractDTO contractDTO = super.get(id);
    contractDTO.setRentToCharge(calculateRentToPay(contractDTO));
    return contractDTO;
  }

  @Override
  public List<ThirdPartyContractDTO> find() throws ThirdPartyContractServiceException {
    List<ThirdPartyContractDTO> lstDTO = super.find();

    for (ThirdPartyContractDTO contractDTO : SafetyUtils.emptyIfNull(lstDTO)) {
      contractDTO.setRentToCharge(calculateRentToPay(contractDTO));
    }
    return lstDTO;
  }

  @Override
  public List<ThirdPartyContractDTO> findByCriteria(ThirdPartyContractCriteriaDTO criteria, HousingCriteriaDTO housingCriteria) throws ThirdPartyContractServiceException {

    List<ThirdPartyContractDTO> lstDTO = new ArrayList<ThirdPartyContractDTO>();
    try {
      List<ThirdPartyContractEntity> lstEntity = thirdPartyContractDAO.findByCriteria(criteria, housingCriteria);

      for (ThirdPartyContractEntity thirdPartyContract : SafetyUtils.emptyIfNull(lstEntity)) {
        ThirdPartyContractDTO contractDTO = mapper.map(thirdPartyContract, ThirdPartyContractDTO.class);
        contractDTO.setRentToCharge(calculateRentToPay(contractDTO));
        lstDTO.add(contractDTO);
      }
    } catch (ThirdPartyContractDAOException e) {
      throw new ThirdPartyContractServiceException(e);
    } catch (MappingException e) {
      throw new ThirdPartyContractServiceException(e);
    }

    return lstDTO;
  }

  @Override
  public List<ThirdPartyContractDTO> findThirdPartyContractByHousing(long idHousing) throws ThirdPartyContractServiceException {
    List<ThirdPartyContractDTO> lstDTO = new ArrayList<ThirdPartyContractDTO>();

    try {
      List<ThirdPartyContractEntity> lstEntity = thirdPartyContractDAO.findContractByHousing(idHousing);

      for (ThirdPartyContractEntity thirdPartyContract : SafetyUtils.emptyIfNull(lstEntity)) {
        lstDTO.add(mapper.map(thirdPartyContract, ThirdPartyContractDTO.class));
      }
    } catch (MappingException e) {
      throw new ThirdPartyContractServiceException(e);
    } catch (ThirdPartyContractDAOException e) {
      throw new ThirdPartyContractServiceException(e);
    }

    return lstDTO;
  }

  @Override
  public String generateReference() throws ThirdPartyContractServiceException {
    try {
      return getSpecificIDAO().generateReference();
    } catch (ThirdPartyContractDAOException e) {
      throw new ThirdPartyContractServiceException(e);
    }
  }

  @Override
  public ThirdPartyContractDTO findLastThirdPartyContractByHousing(Long id) throws ThirdPartyContractServiceException {
    ThirdPartyContractEntity thirdPartyContractEntity;
    ThirdPartyContractDTO thirdPartyContractDTO = null;
    try {
      thirdPartyContractEntity = getSpecificIDAO().findLastThirdPartyContractByHousing(id);
      if (null != thirdPartyContractEntity) {
        thirdPartyContractDTO = mapper.map(thirdPartyContractEntity, ThirdPartyContractDTO.class);
      }
    } catch (ThirdPartyContractDAOException e) {
      throw new ThirdPartyContractServiceException(e);
    }
    return thirdPartyContractDTO;

  }

  /**
   * @param thirdPartyContractDAO the thirdPartyContractDAO to set
   */
  public void setThirdPartyContractDAO(IThirdPartyContractDAO thirdPartyContractDAO) {
    this.thirdPartyContractDAO = thirdPartyContractDAO;
  }

  @Override
  public List<ThirdPartyContractDTO> findThirdPartyContractPerCycle(LocalDate startDate, LocalDate endDate, String paymentCycle, Integer expiryDate)
    throws ThirdPartyContractServiceException {
    List<ThirdPartyContractDTO> lstDTO = new ArrayList<ThirdPartyContractDTO>();

    try {
      List<ThirdPartyContractEntity> lstEntity = thirdPartyContractDAO.findThirdPartyContractPerCycle(startDate, endDate, paymentCycle, expiryDate);

      for (ThirdPartyContractEntity thirdPartyContract : SafetyUtils.emptyIfNull(lstEntity)) {
        // les dates de début de validité et de paiement fournisseur sont à vérifier
        // pour savoir si le contrat doit être pris en compte
        LocalDate startValidity = new LocalDate(thirdPartyContract.getStartValidity());
        LocalDate startSupplier = new LocalDate(thirdPartyContract.getStartSupplierPaymentDate());

        // on s’assure que la date de début de validité ne soit pas antérieure au cycle
        if (startValidity.isBefore(startDate)) {
          startValidity = startDate;
        }
        // on s’assure que la date de début de paiement fournisseur ne soit pas antérieure au cycle
        if (startSupplier.isBefore(startDate)) {
          startSupplier = startDate;
        }

        // en position la date de début de paiment fournisseur au dernier jour du mois,
        // le mois en cours et les mois précédents sont pris en compte dans la prochaine condition
        startValidity = startValidity.dayOfMonth().withMaximumValue();
        startSupplier = startSupplier.dayOfMonth().withMinimumValue();

        // la date de début de validité doit commencer avant la date de paiement fournisseur
        if (startSupplier.isBefore(startValidity)) {
          lstDTO.add(mapper.map(thirdPartyContract, ThirdPartyContractDTO.class));
        }
      }
    } catch (MappingException e) {
      throw new ThirdPartyContractServiceException(e);
    } catch (ThirdPartyContractDAOException e) {
      throw new ThirdPartyContractServiceException(e);
    }

    return lstDTO;
  }

  @Override
  public List<ThirdPartyContractDTO> findThirdPartyContractInProgressOrClose() throws ThirdPartyContractServiceException {
    List<ThirdPartyContractDTO> contracts = new ArrayList<ThirdPartyContractDTO>();

    try {
      for (ThirdPartyContractEntity contract : thirdPartyContractDAO.findThirdPartyContractInProgressOrClose()) {
        contracts.add(mapper.map(contract, ThirdPartyContractDTO.class));
      }
    } catch (ThirdPartyContractDAOException e) {
      logger.error("Erreur survenue lors de la recuperation des contrats tiers en cours", e);
      throw new ThirdPartyContractServiceException(e);
    }
    return contracts;
  }

  @Override
  public List<ThirdPartyContractDTO> findThirdPartyContractToRegularize() throws ThirdPartyContractServiceException {
    List<ThirdPartyContractDTO> contracts = new ArrayList<ThirdPartyContractDTO>();

    try {
      for (ThirdPartyContractEntity contract : thirdPartyContractDAO.findThirdPartyContractToRegularize()) {
        contracts.add(mapper.map(contract, ThirdPartyContractDTO.class));
      }
    } catch (ThirdPartyContractDAOException e) {
      logger.error("Erreur survenue lors de la recuperation des contrats tiers à régulariser", e);
      throw new ThirdPartyContractServiceException(e);
    }
    return contracts;
  }

  @Override
  public void resetThirdPartyContractToRegularize() throws ThirdPartyContractServiceException {
    try {
      thirdPartyContractDAO.resetThirdPartyContractToRegularize();
    } catch (ThirdPartyContractDAOException e) {
      throw new ThirdPartyContractServiceException(e);
    }
  }

  @Override
  public void updateSporadicallyInvoicingThirdPartyContract() throws ThirdPartyContractServiceException {
    try {
      thirdPartyContractDAO.updateSporadicallyInvoicingThirdPartyContract();
    } catch (ThirdPartyContractDAOException e) {
      throw new ThirdPartyContractServiceException(e);
    }
  }

  @Override
  public ThirdPartyContractDTO findByReference(String reference) throws ThirdPartyContractServiceException {
    ThirdPartyContractEntity thirdPartyContractEntity;
    try {
      thirdPartyContractEntity = thirdPartyContractDAO.findByReference(reference);
    } catch (ThirdPartyContractDAOException e) {
      throw new ThirdPartyContractServiceException(e);
    }
    return mapper.map(thirdPartyContractEntity, ThirdPartyContractDTO.class);
  }

  @Override
  public List<ThirdPartyContractDTO> findThirdPartyContractWithActiveRevision() throws ThirdPartyContractServiceException {
    List<ThirdPartyContractDTO> thirdPartyContractstWithActiveRevision = new ArrayList<ThirdPartyContractDTO>();

    List<ThirdPartyContractDTO> thirdPartyContracts = find();

    for (ThirdPartyContractDTO thirdPartyContract : thirdPartyContracts) {
      if (ThirdPartyContractUtils.hasRevision(thirdPartyContract)) {
        thirdPartyContractstWithActiveRevision.add(thirdPartyContract);
      }
    }

    return thirdPartyContractstWithActiveRevision;
  }

  @Override
  public void resetActiveRevisionThirdPartyContract() throws ThirdPartyContractServiceException {
    List<ThirdPartyContractDTO> thirdPartyContractsWithActiveRevision = findThirdPartyContractWithActiveRevision();

    for (ThirdPartyContractDTO thirdPartyContractWithActiveRevision : thirdPartyContractsWithActiveRevision) {

      if (ThirdPartyContractUtils.hasRevisionOfRentAmount(thirdPartyContractWithActiveRevision)) {
        BigDecimal revisedRentAmount = thirdPartyContractWithActiveRevision.getRevisedRentAmount();
        Date revisedRentDate = thirdPartyContractWithActiveRevision.getRevisedRentDate();
        thirdPartyContractWithActiveRevision.setRentAmount(revisedRentAmount);
        thirdPartyContractWithActiveRevision.setLastRevisedRent(revisedRentDate);
      }

      if (ThirdPartyContractUtils.hasRevisionOfExpectedChargeCost(thirdPartyContractWithActiveRevision)) {
        BigDecimal revisedExpectedChargeCostAmount = thirdPartyContractWithActiveRevision.getRevisedExpectedChargeCostAmount();
        Date revisedExpectedChargeCostDate = thirdPartyContractWithActiveRevision.getRevisedExpectedChargeCostDate();
        thirdPartyContractWithActiveRevision.setExpectedChargeCost(revisedExpectedChargeCostAmount);
        thirdPartyContractWithActiveRevision.setLastRevisedExpectedChargeCost(revisedExpectedChargeCostDate);
      }

      thirdPartyContractWithActiveRevision.setRevisedRentAmount(null);
      thirdPartyContractWithActiveRevision.setRevisedRentDate(null);
      thirdPartyContractWithActiveRevision.setRevisedExpectedChargeCostAmount(null);
      thirdPartyContractWithActiveRevision.setRevisedExpectedChargeCostDate(null);

      saveOrUpdate(thirdPartyContractWithActiveRevision);
    }
  }

  @Override
  public void saveActiveRevisionThirdPartyContract() throws ThirdPartyContractServiceException {
    try {
      List<ThirdPartyContractDTO> thirdPartyContractsWithActiveRevision = findThirdPartyContractWithActiveRevision();

      for (ThirdPartyContractDTO thirdPartyContractWithActiveRevision : thirdPartyContractsWithActiveRevision) {

        Boolean revisionOfRentAmount = ThirdPartyContractUtils.hasRevisionOfRentAmount(thirdPartyContractWithActiveRevision);
        Boolean revisionOfExpectedChargeCost = ThirdPartyContractUtils.hasRevisionOfExpectedChargeCost(thirdPartyContractWithActiveRevision);

        if (revisionOfRentAmount) {
          RevisionThirdPartyContractDTO revisionThirdPartyContractDTO = new RevisionThirdPartyContractDTO();
          revisionThirdPartyContractDTO.setThirdPartyContractId(thirdPartyContractWithActiveRevision.getId());
          revisionThirdPartyContractDTO.setAmount(thirdPartyContractWithActiveRevision.getRevisedRentAmount());
          revisionThirdPartyContractDTO.setDate(thirdPartyContractWithActiveRevision.getRevisedRentDate());
          revisionThirdPartyContractDTO.setType(RevisionThirdPartyContractConstants.RENT_AMOUNT_REVISION_CODE);
          revisionThirdPartyContractService.create(revisionThirdPartyContractDTO);
          thirdPartyContractWithActiveRevision.setLastRevisedRent(thirdPartyContractWithActiveRevision.getRevisedRentDate());
          saveOrUpdate(thirdPartyContractWithActiveRevision);
        }

        if (revisionOfExpectedChargeCost) {
          RevisionThirdPartyContractDTO revisionThirdPartyContractDTO = new RevisionThirdPartyContractDTO();
          revisionThirdPartyContractDTO.setThirdPartyContractId(thirdPartyContractWithActiveRevision.getId());
          revisionThirdPartyContractDTO.setAmount(thirdPartyContractWithActiveRevision.getRevisedExpectedChargeCostAmount());
          revisionThirdPartyContractDTO.setDate(thirdPartyContractWithActiveRevision.getRevisedExpectedChargeCostDate());
          revisionThirdPartyContractDTO.setType(RevisionThirdPartyContractConstants.EXPECTED_CHARGE_COST_REVISION_CODE);
          revisionThirdPartyContractService.create(revisionThirdPartyContractDTO);
          thirdPartyContractWithActiveRevision.setLastRevisedExpectedChargeCost(thirdPartyContractWithActiveRevision.getRevisedExpectedChargeCostDate());
          saveOrUpdate(thirdPartyContractWithActiveRevision);
        }

      }

    } catch (RevisionThirdPartyContractServiceException e) {
      throw new ThirdPartyContractServiceException(e);
    }
  }

  @Override
  public void saveDatasWhenClosedThirdPartyContract() throws ThirdPartyContractServiceException {

    List<ThirdPartyContractDTO> thirdPartyContracts = findThirdPartyContractInProgressOrClose();

    for (ThirdPartyContractDTO thirdPartyContract : thirdPartyContracts) {

      LocalDate endOfCycle;

      if (thirdPartyContract.getExpiryDate() != null && thirdPartyContract.getExpiryDate()) {

        endOfCycle = DateTimeUtils.getLastDayOfCycle(LocalDate.now(), thirdPartyContract.getPaymentCycle().getLabel());
      } else {

        // le cycle à traiter concerne le mois suivant pour les contrats à échoir
        LocalDate treatmentDateInAdvance = LocalDate.now().plusMonths(1);
        endOfCycle = DateTimeUtils.getLastDayOfCycle(treatmentDateInAdvance, thirdPartyContract.getPaymentCycle().getLabel());
      }

      if (thrirdPartyContractAlreadyClosed(thirdPartyContract) &&
        thrirdPartyContractIsClosed(thirdPartyContract, endOfCycle)) {

        thirdPartyContract.setClosedThirdPartyContract(true);
        thirdPartyContract.setFixedAgency(thirdPartyContract.getHousing().getAgency());

        update(thirdPartyContract
        );
      }
    }
  }

  /**
   * Permet de verifier si un contrat tiers est deja clos
   * @param thirdPartyContract le contrat tiers à tester
   * @return true si le contrat est déjà clos
     */
  private boolean thrirdPartyContractAlreadyClosed (ThirdPartyContractDTO thirdPartyContract) {
    return !thirdPartyContract.getClosedThirdPartyContract();
  }

  /**
   * permet de vérifier si un contrat devient clos
   * @param thirdPartyContract le contrat clos
   * @param endOfCycle la fin du cycle
   * @return true si le contrat devient clos
     */
  private boolean thrirdPartyContractIsClosed (ThirdPartyContractDTO thirdPartyContract, LocalDate endOfCycle) {
    return thirdPartyContract.getCancellationDate() != null && (thirdPartyContract.getCancellationDate().before(endOfCycle.toDate())
      || thirdPartyContract.getCancellationDate().equals(endOfCycle.toDate()));
  }

  /**
   * @param ylZnAccountingDocNumberService the ylZnAccountingDocNumberService to set
   */
  public void setYlZnAccountingDocNumberService(IYlZnAccountingDocNumberService ylZnAccountingDocNumberService) {
    this.ylZnAccountingDocNumberService = ylZnAccountingDocNumberService;
  }

  /**
   * Calcul du loyer à payer au prorata du nombre de jours dans le mois
   * @param thirdPartyContractDTO le contrat tiers à traiter
   * @return le montant du loyer à payer
   * @throws ThirdPartyContractServiceException  l’exception
   */
  private BigDecimal calculateRentToPay(ThirdPartyContractDTO thirdPartyContractDTO) throws ThirdPartyContractServiceException {

    BigDecimal rentToCharge = BigDecimal.ZERO;

    try {

      // prise en compte du cycle
      LocalDate now = LocalDate.now();
      String paymentCycle = thirdPartyContractDTO.getPaymentCycle().getLabel();

      LocalDate treatmentDateAtTheEnd = now;
      LocalDate startExpiryDateCycle = DateTimeUtils.getFirstDayOfCycle(treatmentDateAtTheEnd, paymentCycle);
      LocalDate endExpiryDateCycle = DateTimeUtils.getLastDayOfCycle(treatmentDateAtTheEnd, paymentCycle);

      // le cycle à traiter concerne le mois suivant pour les contrats à échoir
      LocalDate treatmentDateInAdvance = now.plusMonths(1);
      LocalDate startFallDueCycle = DateTimeUtils.getFirstDayOfCycle(treatmentDateInAdvance, paymentCycle);
      LocalDate endFallDueCycle = DateTimeUtils.getLastDayOfCycle(treatmentDateInAdvance, paymentCycle);

      LocalDate startCycle;
      LocalDate endCycle;

      if (DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle) && thirdPartyContractDTO.getExpiryDate()) {
        startCycle = startExpiryDateCycle;
        endCycle = endExpiryDateCycle;
      } else {
        startCycle = startFallDueCycle;
        endCycle = endFallDueCycle;
      }

      Boolean annualContractConcerned = now.getMonthOfYear() % DateTimeUtils.MONTHS_IN_A_YEAR == 0 && DateTimeUtils.ANNUAL_CYCLE.equals(paymentCycle);
      Boolean quarterlyContractConcerned = now.getMonthOfYear() % DateTimeUtils.MONTHS_IN_A_QUARTER == 0 && DateTimeUtils.QUARTERLY_CYCLE.equals(paymentCycle);
      Boolean monthlyContractConcerned = DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle);

      LocalDate treatmentDate = now;
      DateTime nowDateTime = DateTime.now();

      if (annualContractConcerned || quarterlyContractConcerned || monthlyContractConcerned) {
        // date de début du contrat
        LocalDate startValidity = LocalDate.fromDateFields(thirdPartyContractDTO.getStartValidity());
        // date de fin du contrat
        LocalDate endValidity = null;
        if (thirdPartyContractDTO.getCancellationDate() != null) {
          endValidity = LocalDate.fromDateFields(thirdPartyContractDTO.getCancellationDate());
        }

        // ratio du nombre de jours du contrat par rapport au nombre de jours du cycle
        double proportionDays = DateTimeUtils.getProportionOfDaysOnCycle(startCycle, endCycle, startValidity, endValidity);

        BigDecimal rentAmount = thirdPartyContractDTO.getRentAmount();
        Boolean revisionOfRentAmount = ThirdPartyContractUtils.hasRevisionOfRentAmount(thirdPartyContractDTO);

        if (revisionOfRentAmount) {
          rentAmount = thirdPartyContractDTO.getRevisedRentAmount();
        }

        if (revisionOfRentAmount && DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle) && thirdPartyContractDTO.getExpiryDate()
          && !thirdPartyContractDTO.getRevisedRentDate().before(startCycle.toDate())) {
          LocalDate revisedRentDate = LocalDate.fromDateFields(thirdPartyContractDTO.getRevisedRentDate());
          BigDecimal revisedAmount = thirdPartyContractDTO.getRevisedRentAmount();
          BigDecimal initialRentAmount = thirdPartyContractDTO.getRentAmount();

          revisedAmount = revisedAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revisedRentDate.toDate(), endCycle.toDate(), treatmentDate));
          revisedAmount = revisedAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

          initialRentAmount = initialRentAmount.multiply(ThirdPartyContractUtils.getProportionsDays(startCycle.toDate(), revisedRentDate.minusDays(1).toDate(), treatmentDate));
          initialRentAmount = initialRentAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

          rentAmount = revisedAmount.add(initialRentAmount);
        } else {
          rentAmount = rentAmount.multiply(BigDecimal.valueOf(proportionDays));
          rentAmount = rentAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
        }

        BigDecimal expectedChargeCost = thirdPartyContractDTO.getExpectedChargeCost();
        Boolean revisionOfExpectedChargeCost = ThirdPartyContractUtils.hasRevisionOfExpectedChargeCost(thirdPartyContractDTO);

        if (revisionOfExpectedChargeCost) {
          expectedChargeCost = thirdPartyContractDTO.getRevisedExpectedChargeCostAmount();
        }

        if (revisionOfExpectedChargeCost && DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle) && thirdPartyContractDTO.getExpiryDate()
          && !thirdPartyContractDTO.getRevisedExpectedChargeCostDate().before(startCycle.toDate())) {
          LocalDate revisedExpectedChargeCostDate = LocalDate.fromDateFields(thirdPartyContractDTO.getRevisedExpectedChargeCostDate());
          BigDecimal expectedChargeCostAmount = thirdPartyContractDTO.getRevisedExpectedChargeCostAmount();
          BigDecimal initialExpectedChargeCostAmount = thirdPartyContractDTO.getExpectedChargeCost();

          expectedChargeCostAmount = expectedChargeCostAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revisedExpectedChargeCostDate.toDate(), endCycle.toDate(),
            treatmentDate));
          expectedChargeCostAmount = expectedChargeCostAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

          initialExpectedChargeCostAmount = initialExpectedChargeCostAmount.multiply(ThirdPartyContractUtils.getProportionsDays(startCycle.toDate(), revisedExpectedChargeCostDate
            .minusDays(1).toDate(), treatmentDate));
          initialExpectedChargeCostAmount = initialExpectedChargeCostAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

          expectedChargeCost = expectedChargeCostAmount.add(initialExpectedChargeCostAmount);
        } else {
          expectedChargeCost = expectedChargeCost.multiply(BigDecimal.valueOf(proportionDays));
          expectedChargeCost = expectedChargeCost.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
        }
        rentToCharge = rentToCharge.add(rentAmount);
        rentToCharge = rentToCharge.add(expectedChargeCost);

        // si la génération du cycle est déjà passée, le mois en cours doit être pris en compte dans le calcul
        DateTime ylDateTime = detailCronService.getYlCron();
        if (nowDateTime.isAfter(ylDateTime)) {
          treatmentDate = treatmentDate.plusMonths(1);
        }
      }

      if (null != thirdPartyContractDTO.getSporadicallyInvoicing()) {
        rentToCharge = rentToCharge.add(thirdPartyContractDTO.getSporadicallyInvoicing());
        rentToCharge = rentToCharge.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
      }

      Boolean expiryDate = thirdPartyContractDTO.getExpiryDate();

      for (int i = 0; i < thirdPartyContractDTO.getYlRegularization(); i++) {
        // si l’on traite les contrats mensuels et qu’il est échu, on récupère les dates du cycle échu
        // sinon l’on prend les dates à échoir
        LocalDate startRetroactivityCycle = DateTimeUtils.getFirstDayOfCycle(treatmentDate, paymentCycle);
        LocalDate endRetroactivityCycle = DateTimeUtils.getLastDayOfCycle(treatmentDate, paymentCycle);

        if (null != expiryDate && expiryDate) {
          startRetroactivityCycle = startRetroactivityCycle.minusMonths(1);
          endRetroactivityCycle = endRetroactivityCycle.minusMonths(1);
        }

        // récupération du cycle à régulariser
        YearMonth yearMonthOfCycle = DateTimeUtils.getYearMonthOfCycle(treatmentDate, paymentCycle, expiryDate);

        if (null != expiryDate && expiryDate) {
          yearMonthOfCycle = yearMonthOfCycle.minusMonths(1);
        }

        // modification de la date de traitement pour le prochain remboursement du même contrat
        treatmentDate = getTreatmentDateOfNextRegularization(treatmentDate, paymentCycle);

        // récupération de la dernière génération
        YlZnAccountingDocNumberDTO lastYlZnAccountingDocNumberDTO;
        lastYlZnAccountingDocNumberDTO = ylZnAccountingDocNumberService.findLastGenerationByThirdPartyContractIdAndYearMonth(thirdPartyContractDTO.getId(), yearMonthOfCycle);

        if (!YlZnAccountingDocNumberUtils.verifyOlderGeneration(lastYlZnAccountingDocNumberDTO)) {
          continue;
        }

        // date de fin du contrat
        LocalDate endRetroactivityValidity = LocalDate.fromDateFields(thirdPartyContractDTO.getCancellationDate());

        // les révisions permettent de calculer le montant déjà payé pour le cycle
        List<RevisionThirdPartyContractDTO> revisionThirdPartyContracts = revisionThirdPartyContractService.findRevisionOfOneContract(thirdPartyContractDTO.getId());

        List<RevisionThirdPartyContractDTO> rentAmountRevisions = new ArrayList<RevisionThirdPartyContractDTO>();
        for (RevisionThirdPartyContractDTO revision : revisionThirdPartyContracts) {
          if (RevisionThirdPartyContractConstants.RENT_AMOUNT_REVISION_CODE.equals(revision.getType())) {
            rentAmountRevisions.add(revision);
          }
        }
        List<RevisionThirdPartyContractDTO> expectedChargeCostRevisions = new ArrayList<RevisionThirdPartyContractDTO>();
        for (RevisionThirdPartyContractDTO revision : revisionThirdPartyContracts) {
          if (RevisionThirdPartyContractConstants.EXPECTED_CHARGE_COST_REVISION_CODE.equals(revision.getType())) {
            expectedChargeCostRevisions.add(revision);
          }
        }

        LocalDate startTreatment = endRetroactivityValidity.dayOfMonth().withMinimumValue();
        if (startRetroactivityCycle.isAfter(startTreatment)) {
          startTreatment = startRetroactivityCycle;
        }

        // calcul du nombre de mois entre la date d’effet et la date d’aujourd’hui
        int months = Months.monthsBetween(startTreatment, endRetroactivityCycle).getMonths() + 1;

        for (int j = 0; j < months; j++) {

          LocalDate activeMonth = startTreatment.plusMonths(j);
          LocalDate startMonth = activeMonth.dayOfMonth().withMinimumValue();
          LocalDate endMonth = activeMonth.dayOfMonth().withMaximumValue();
          LocalDate startRegularization = endRetroactivityValidity.plusDays(1);
          LocalDate endRegularization = endMonth;

          if (startRegularization.isBefore(startMonth)) {
            startRegularization = startMonth;
          }

          // seule la révision la plus récente, antérieure à la date de régularisation, est nécessaire
          RevisionThirdPartyContractDTO mostRecentPreviousRentAmountRevision = null;
          List<RevisionThirdPartyContractDTO> rentAmountRevisionsOfMonth = new ArrayList<RevisionThirdPartyContractDTO>();
          for (RevisionThirdPartyContractDTO revision : rentAmountRevisions) {
            if (!revision.getDate().after(startRegularization.toDate())) {
              mostRecentPreviousRentAmountRevision = revision;
            } else if (!revision.getDate().after(endMonth.toDate())) {
              // On récupére les révisions du mois de révision
              rentAmountRevisionsOfMonth.add(revision);
            }
          }
          // supprime les révisions à ne pas traiter (déjà traitée dans le passé)
          int k = 0;
          List<RevisionThirdPartyContractDTO> rentAmountRevisionsToDelete = new ArrayList<RevisionThirdPartyContractDTO>();
          for (RevisionThirdPartyContractDTO revision : rentAmountRevisionsOfMonth) {
            int l = 0;
            for (RevisionThirdPartyContractDTO revision2 : rentAmountRevisionsOfMonth) {
              if (l < k && !revision2.getDate().before(revision.getDate())) {
                rentAmountRevisionsToDelete.add(revision2);
              }
              l += 1;
            }
            k += 1;
          }
          for (RevisionThirdPartyContractDTO revisionToDelete : rentAmountRevisionsToDelete) {
            rentAmountRevisionsOfMonth.remove(revisionToDelete);
          }

          RevisionThirdPartyContractDTO mostRecentPreviousExpectedChargeCostRevision = null;
          List<RevisionThirdPartyContractDTO> expectedChargeCostRevisionsOfMonth = new ArrayList<RevisionThirdPartyContractDTO>();
          for (RevisionThirdPartyContractDTO revision : expectedChargeCostRevisions) {
            if (!revision.getDate().after(startRegularization.toDate())) {
              mostRecentPreviousExpectedChargeCostRevision = revision;
            } else if (!revision.getDate().after(endMonth.toDate())) {
              // On récupére les révisions du mois de révision
              expectedChargeCostRevisionsOfMonth.add(revision);
            }
          }
          // supprime les révisions à ne pas traiter (déjà traitée dans le passé)
          int m = 0;
          List<RevisionThirdPartyContractDTO> expectedChargeCostRevisionsToDelete = new ArrayList<RevisionThirdPartyContractDTO>();
          for (RevisionThirdPartyContractDTO revision : expectedChargeCostRevisionsOfMonth) {
            int n = 0;
            for (RevisionThirdPartyContractDTO revision2 : expectedChargeCostRevisionsOfMonth) {
              if (n < m && !revision2.getDate().before(revision.getDate())) {
                expectedChargeCostRevisionsToDelete.add(revision2);
              }
              n += 1;
            }
            m += 1;
          }
          for (RevisionThirdPartyContractDTO revisionToDelete : expectedChargeCostRevisionsToDelete) {
            expectedChargeCostRevisionsOfMonth.remove(revisionToDelete);
          }

          // prorata du nombre de jours dans la partie à régulariser
          double retroactivityProportionDays = DateTimeUtils.getProportionOfDaysOnCycle(startMonth, endMonth, startRegularization, endRegularization);

          BigDecimal retroactivityRentAmount;
          if (mostRecentPreviousRentAmountRevision != null) {
            retroactivityRentAmount = mostRecentPreviousRentAmountRevision.getAmount();
          } else {
            retroactivityRentAmount = lastYlZnAccountingDocNumberDTO.getYlZnAdnMensualRentAmount();
          }

          if (rentAmountRevisionsOfMonth.isEmpty()) {
            retroactivityRentAmount = retroactivityRentAmount.multiply(BigDecimal.valueOf(retroactivityProportionDays));
            retroactivityRentAmount = retroactivityRentAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
          } else {
            BigDecimal previousFirstPartMonth = BigDecimal.ZERO;

            int o = 0;
            for (RevisionThirdPartyContractDTO revision : rentAmountRevisionsOfMonth) {
              LocalDate revisionDate = LocalDate.fromDateFields(revision.getDate());

              BigDecimal firstPartAmount;
              if (o == 0) {
                firstPartAmount = retroactivityRentAmount;
              } else {
                firstPartAmount = rentAmountRevisionsOfMonth.get(o - 1).getAmount();
              }

              if (o == 0) {
                firstPartAmount = firstPartAmount
                  .multiply(ThirdPartyContractUtils.getProportionsDays(startRegularization.toDate(), revisionDate.minusDays(1).toDate(), activeMonth));
              } else {
                firstPartAmount = firstPartAmount.multiply(ThirdPartyContractUtils.getProportionsDays(rentAmountRevisionsOfMonth.get(o - 1).getDate(), revisionDate.minusDays(1)
                  .toDate(), activeMonth));
              }
              firstPartAmount = firstPartAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

              BigDecimal revisedAmount = revision.getAmount();
              revisedAmount = revisedAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revision.getDate(), endMonth.toDate(), activeMonth));
              revisedAmount = revisedAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

              retroactivityRentAmount = firstPartAmount.add(revisedAmount).add(previousFirstPartMonth);

              previousFirstPartMonth = firstPartAmount;
              o += 1;
            }
          }

          BigDecimal retroactivityExpectedChargeCost;
          if (mostRecentPreviousExpectedChargeCostRevision != null) {
            retroactivityExpectedChargeCost = mostRecentPreviousExpectedChargeCostRevision.getAmount();
          } else {
            retroactivityExpectedChargeCost = lastYlZnAccountingDocNumberDTO.getYlZnAdnMensualExpectedChargeCost();
          }

          if (expectedChargeCostRevisionsOfMonth.isEmpty()) {
            retroactivityExpectedChargeCost = retroactivityExpectedChargeCost.multiply(BigDecimal.valueOf(retroactivityProportionDays));
            retroactivityExpectedChargeCost = retroactivityExpectedChargeCost.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);
          } else {
            BigDecimal previousFirstPartMonth = BigDecimal.ZERO;

            int o = 0;
            for (RevisionThirdPartyContractDTO revision : expectedChargeCostRevisionsOfMonth) {
              LocalDate revisionDate = LocalDate.fromDateFields(revision.getDate());

              BigDecimal firstPartAmount;
              if (o == 0) {
                firstPartAmount = retroactivityRentAmount;
              } else {
                firstPartAmount = expectedChargeCostRevisionsOfMonth.get(o - 1).getAmount();
              }

              if (o == 0) {
                firstPartAmount = firstPartAmount
                  .multiply(ThirdPartyContractUtils.getProportionsDays(startRegularization.toDate(), revisionDate.minusDays(1).toDate(), activeMonth));
              } else {
                firstPartAmount = firstPartAmount.multiply(ThirdPartyContractUtils.getProportionsDays(expectedChargeCostRevisionsOfMonth.get(o - 1).getDate(), revisionDate
                  .minusDays(1).toDate(), activeMonth));
              }
              firstPartAmount = firstPartAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

              BigDecimal revisedAmount = revision.getAmount();
              revisedAmount = revisedAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revision.getDate(), endMonth.toDate(), activeMonth));
              revisedAmount = revisedAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

              retroactivityExpectedChargeCost = firstPartAmount.add(revisedAmount).add(previousFirstPartMonth);

              previousFirstPartMonth = firstPartAmount;
              o += 1;
            }
          }

          rentToCharge = rentToCharge.subtract(retroactivityRentAmount);
          rentToCharge = rentToCharge.subtract(retroactivityExpectedChargeCost);
        }
      }

      Boolean activeRevision = ThirdPartyContractUtils.hasRevision(thirdPartyContractDTO);
      if (activeRevision) {
        Boolean existingRevisionOfRentAmount = ThirdPartyContractUtils.hasRevisionOfRentAmount(thirdPartyContractDTO);
        Boolean existingRevisionOfExpectedChargeCost = ThirdPartyContractUtils.hasRevisionOfExpectedChargeCost(thirdPartyContractDTO);

        List<RevisionThirdPartyContractDTO> revisionThirdPartyContracts = revisionThirdPartyContractService.findRevisionOfOneContract(thirdPartyContractDTO.getId());

        LocalDate endCycleMonthOfRevision = DateTimeUtils.getLastDayOfCycle(now, paymentCycle);

        if (DateTimeUtils.MONTHLY_CYCLE.equals(paymentCycle) && thirdPartyContractDTO.getExpiryDate()) {
          endCycleMonthOfRevision = endCycleMonthOfRevision.minusMonths(1);
        }

        if (existingRevisionOfRentAmount && thirdPartyContractDTO.getRevisedRentDate() != null
          && !thirdPartyContractDTO.getRevisedRentDate().after(endCycleMonthOfRevision.toDate())) {
          rentToCharge = rentToCharge.add(calculateRevisionOfRentAmount(thirdPartyContractDTO, revisionThirdPartyContracts, endCycleMonthOfRevision));
        }

        if (existingRevisionOfExpectedChargeCost && thirdPartyContractDTO.getRevisedExpectedChargeCostDate() != null
          && !thirdPartyContractDTO.getRevisedExpectedChargeCostDate().after(endCycleMonthOfRevision.toDate())) {
          rentToCharge = rentToCharge.add(calculateRevisionOfExpectedChargeCost(thirdPartyContractDTO, revisionThirdPartyContracts, endCycleMonthOfRevision));
        }
      }
    } catch (YlZnAccountingDocNumberServiceException e) {
      throw new ThirdPartyContractServiceException(e.getMessage(), e);
    } catch (DetailCronServiceException e) {
      throw new ThirdPartyContractServiceException(e.getMessage(), e);
    } catch (RevisionThirdPartyContractServiceException e) {
      throw new ThirdPartyContractServiceException(e.getMessage(), e);
    }

    return rentToCharge;
  }

  /**
   * Permet de calculer le montant de la révision pour le loyer mensuel
   * @param thirdPartyContractDTO contrat tier à traiter
   * @param revisionThirdPartyContracts révisions du contrat tier
   * @param endCycleMonthOfRevision date de dernier jour du cycle à prendre en compte
   * @return montant de la révision du loyer mensuel
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   */
  private BigDecimal calculateRevisionOfRentAmount(ThirdPartyContractDTO thirdPartyContractDTO, List<RevisionThirdPartyContractDTO> revisionThirdPartyContracts,
    LocalDate endCycleMonthOfRevision) throws YlZnAccountingDocNumberServiceException {
    BigDecimal revisionOfRentAmount = BigDecimal.ZERO;

    String paymentCycle = thirdPartyContractDTO.getPaymentCycle().getLabel();
    Boolean expiryDate = thirdPartyContractDTO.getExpiryDate();

    LocalDate revisedRentDate = LocalDate.fromDateFields(thirdPartyContractDTO.getRevisedRentDate());

    // calcul du nombre de mois entre la date d’effet et la date d’aujourd’hui
    int months = Months.monthsBetween(revisedRentDate.dayOfMonth().withMinimumValue(), endCycleMonthOfRevision).getMonths() + 1;

    // boucle dans les mois entre la date d’effet et la date d’aujourd’hui
    for (int i = 0; i < months; i++) {

      LocalDate startDate = revisedRentDate;
      Boolean firstMonth = true;

      // Si on n'est pas dans le premier mois de révision
      if (i > 0) {
        revisedRentDate = revisedRentDate.plusMonths(1);
        startDate = revisedRentDate.dayOfMonth().withMinimumValue();
        firstMonth = false;
      }

      // récupération du cycle à régulariser
      YearMonth yearMonthOfCycle = DateTimeUtils.getYearMonthOfCycle(revisedRentDate, paymentCycle, expiryDate);

      if (null != expiryDate && expiryDate) {
        yearMonthOfCycle = yearMonthOfCycle.minusMonths(1);
      }

      // On récupère uniquement YL du cycle à régulariser
      YlZnAccountingDocNumberDTO lastYlZnAccountingDocNumberDTO = ylZnAccountingDocNumberService.findLastGenerationByThirdPartyContractIdAndYearMonth(
        thirdPartyContractDTO.getId(), yearMonthOfCycle);

      if (!YlZnAccountingDocNumberUtils.verifyOlderGeneration(lastYlZnAccountingDocNumberDTO)) {
        continue;
      }

      List<RevisionThirdPartyContractDTO> rentAmountRevisions = new ArrayList<RevisionThirdPartyContractDTO>();
      for (RevisionThirdPartyContractDTO revision : revisionThirdPartyContracts) {
        if (RevisionThirdPartyContractConstants.RENT_AMOUNT_REVISION_CODE.equals(revision.getType())) {
          rentAmountRevisions.add(revision);
        }
      }

      BigDecimal revisionAmount = ThirdPartyContractUtils.getRevisionRentAmount(thirdPartyContractDTO, rentAmountRevisions, lastYlZnAccountingDocNumberDTO, startDate, firstMonth);
      revisionOfRentAmount = revisionOfRentAmount.add(revisionAmount);
    }
    return revisionOfRentAmount;
  }

  /**
   * Permet de calculer le montant de la révision pour les charges prévisionnelles mensuelles
   * @param thirdPartyContractDTO contrat tier à traiter
   * @param revisionThirdPartyContracts révisions du contrat tier
   * @param endCycleMonthOfRevision date de dernier jour du cycle à prendre en compte
   * @return montant de la révision des charges prévisionnelles mensuelles
   * @throws YlZnAccountingDocNumberServiceException une YlZnAccountingDocNumberServiceException
   */
  private BigDecimal calculateRevisionOfExpectedChargeCost(ThirdPartyContractDTO thirdPartyContractDTO, List<RevisionThirdPartyContractDTO> revisionThirdPartyContracts,
    LocalDate endCycleMonthOfRevision) throws YlZnAccountingDocNumberServiceException {
    BigDecimal revisionOfExpectedChargeCost = BigDecimal.ZERO;

    String paymentCycle = thirdPartyContractDTO.getPaymentCycle().getLabel();
    Boolean expiryDate = thirdPartyContractDTO.getExpiryDate();

    LocalDate revisedExpectedChargeCostDate = LocalDate.fromDateFields(thirdPartyContractDTO.getRevisedExpectedChargeCostDate());

    // calcul du nombre de mois entre la date d’effet et la date d’aujourd’hui
    int months = Months.monthsBetween(revisedExpectedChargeCostDate.dayOfMonth().withMinimumValue(), endCycleMonthOfRevision).getMonths() + 1;

    // boucle dans les mois entre la date d’effet et la date d’aujourd’hui
    for (int i = 0; i < months; i++) {

      LocalDate startDate = revisedExpectedChargeCostDate;
      Boolean firstMonth = true;

      // Si on n'est pas dans le premier mois de révision
      if (i > 0) {
        revisedExpectedChargeCostDate = revisedExpectedChargeCostDate.plusMonths(1);
        startDate = revisedExpectedChargeCostDate.dayOfMonth().withMinimumValue();
        firstMonth = false;
      }

      // récupération du cycle à régulariser
      YearMonth yearMonthOfCycle = DateTimeUtils.getYearMonthOfCycle(revisedExpectedChargeCostDate, paymentCycle, expiryDate);

      if (null != expiryDate && expiryDate) {
        yearMonthOfCycle = yearMonthOfCycle.minusMonths(1);
      }

      // On récupère uniquement YL du cycle à régulariser
      YlZnAccountingDocNumberDTO lastYlZnAccountingDocNumberDTO = ylZnAccountingDocNumberService.findLastGenerationByThirdPartyContractIdAndYearMonth(
        thirdPartyContractDTO.getId(), yearMonthOfCycle);

      if (!YlZnAccountingDocNumberUtils.verifyOlderGeneration(lastYlZnAccountingDocNumberDTO)) {
        continue;
      }

      List<RevisionThirdPartyContractDTO> expectedChargeCostRevisions = new ArrayList<RevisionThirdPartyContractDTO>();
      for (RevisionThirdPartyContractDTO revision : revisionThirdPartyContracts) {
        if (RevisionThirdPartyContractConstants.EXPECTED_CHARGE_COST_REVISION_CODE.equals(revision.getType())) {
          expectedChargeCostRevisions.add(revision);
        }
      }

      BigDecimal revisionAmount = ThirdPartyContractUtils.getRevisionExpectedChargeCostAmount(thirdPartyContractDTO, expectedChargeCostRevisions, lastYlZnAccountingDocNumberDTO,
        startDate, firstMonth);
      revisionOfExpectedChargeCost = revisionOfExpectedChargeCost.add(revisionAmount);
    }
    return revisionOfExpectedChargeCost;
  }

  /**
   * Permet de récupérer la date de traitement pour le prochain remboursement
   * @param treatmentDate date de traitement
   * @param cycle cycle de paiement
   * @return prochaine date de traitement
   */
  private LocalDate getTreatmentDateOfNextRegularization(LocalDate treatmentDate, String cycle) {
    if (DateTimeUtils.ANNUAL_CYCLE.equals(cycle)) {
      return treatmentDate.minusMonths(DateTimeUtils.MONTHS_IN_A_YEAR);
    } else if (DateTimeUtils.QUARTERLY_CYCLE.equals(cycle)) {
      return treatmentDate.minusMonths(DateTimeUtils.MONTHS_IN_A_QUARTER);
    } else {
      return treatmentDate.minusMonths(DateTimeUtils.MONTHS_IN_A_MONTH);
    }
  }

  /**
   * @param detailCronService the detailCronService to set
   */
  public void setDetailCronService(IDetailCronService detailCronService) {
    this.detailCronService = detailCronService;
  }

  /**
   * @param revisionThirdPartyContractService the revisionThirdPartyContractService to set
   */
  public void setRevisionThirdPartyContractService(IRevisionThirdPartyContractService revisionThirdPartyContractService) {
    this.revisionThirdPartyContractService = revisionThirdPartyContractService;
  }

  public void setHousingServiceFacade(IHousingServiceFacade housingServiceFacade) {
    this.housingServiceFacade = housingServiceFacade;
  }

}
