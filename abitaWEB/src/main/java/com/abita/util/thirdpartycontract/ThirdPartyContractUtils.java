package com.abita.util.thirdpartycontract;

import com.abita.dto.RevisionThirdPartyContractDTO;
import com.abita.dto.ThirdPartyContractDTO;
import com.abita.dto.YlZnAccountingDocNumberDTO;
import com.abita.util.bigdecimalutil.BigDecimalUtils;
import com.abita.util.dateutil.DateTimeUtils;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe utilitaire pour le calcul des montants des contrats tiers
 * @author
 *
 */
public final class ThirdPartyContractUtils {

  /**
   * Constructeur privé
   */
  private ThirdPartyContractUtils() {
  }

  /**
   * Récupère le prorata du nombre de jours par rapport au mois traité pour un contrat tiers
   * @param contractThird le contrat tiers à traiter
   * @return la valeur du prorata compris entre 0 et 1
   */
  public static BigDecimal getProportionsDays(ThirdPartyContractDTO contractThird) {
    return getProportionsDays(contractThird.getStartValidity(), contractThird.getCancellationDate());
  }

  /**
   * Récupère le prorata du nombre de jours par rapport au mois traité
   * @param startValidityDate date de début
   * @param endValidityDate date de fin
   * @return la valeur du prorata compris entre 0 et 1
   */
  public static BigDecimal getProportionsDays(Date startValidityDate, Date endValidityDate) {
    return getProportionsDays(startValidityDate, endValidityDate, LocalDate.now());
  }

  /**
   * Récupère le prorata du nombre de jours par rapport au mois traité
   * @param startValidityDate date de début
   * @param endValidityDate date de fin
   * @param treatmentDate date de traitement
   * @return la valeur du prorata compris entre 0 et 1
   */
  public static BigDecimal getProportionsDays(Date startValidityDate, Date endValidityDate, LocalDate treatmentDate) {
    // date de début du contrat
    LocalDate startValidity = new LocalDate(startValidityDate);
    // date de fin du contrat
    LocalDate endValidity = null;
    // mois à prendre en compte
    if (endValidityDate != null) {
      endValidity = new LocalDate(endValidityDate);
    }

    // date de début et date de fin du cycle
    LocalDate startCycle = DateTimeUtils.getFirstDayOfCycle(treatmentDate, DateTimeUtils.MONTHLY_CYCLE);
    LocalDate endCycle = DateTimeUtils.getLastDayOfCycle(treatmentDate, DateTimeUtils.MONTHLY_CYCLE);

    // ratio du nombre de jours du contrat par rapport au nombre de jours du cycle
    return new BigDecimal(DateTimeUtils.getProportionOfDaysOnCycle(startCycle, endCycle, startValidity, endValidity));
  }

  /**
   * Permet de savoir si un contrat tiers a une révision active sur le loyer mensuel
   * @param thirdPartyContract Contrat tiers
   * @return true si le contrat a une révision active sur le loyer mensuel
   */
  public static Boolean hasRevisionOfRentAmount(ThirdPartyContractDTO thirdPartyContract) {
    Boolean revisionOfRentAmount = false;
    if (thirdPartyContract.getRevisedRentAmount() != null && thirdPartyContract.getRevisedRentDate() != null) {
      revisionOfRentAmount = hasActiveRevision(thirdPartyContract.getRevisedRentDate());
    }
    return revisionOfRentAmount;
  }

  /**
   * Permet de savoir si un contrat tiers a une révision active sur les charges prévisionnelles
   * @param thirdPartyContract Contrat tiers
   * @return true si le contrat a une révision active sur les charges prévisionnelles
   */
  public static Boolean hasRevisionOfExpectedChargeCost(ThirdPartyContractDTO thirdPartyContract) {
    Boolean revisionOfExpectedChargeCost = false;
    if (thirdPartyContract.getRevisedExpectedChargeCostAmount() != null && thirdPartyContract.getRevisedExpectedChargeCostDate() != null) {
      revisionOfExpectedChargeCost = hasActiveRevision(thirdPartyContract.getRevisedExpectedChargeCostDate());
    }
    return revisionOfExpectedChargeCost;
  }

  /**
   * Permet de savoir si la date de révision est active (mois en cours ou dans le passé)
   * @param revisedDate
   * @return
   */
  private static Boolean hasActiveRevision(Date revisedDate) {
    Boolean activeRevision = false;

    LocalDate endCycleMonthOfRevision = LocalDate.now().dayOfMonth().withMaximumValue();
    if (!revisedDate.after(endCycleMonthOfRevision.toDate())) {
      activeRevision = true;
    }

    return activeRevision;
  }

  /**
   * Permet de savoir si un contrat tiers a une révision active
   * @param thirdPartyContract Contrat tiers
   * @return true si le contrat a une révision active
   */
  public static Boolean hasRevision(ThirdPartyContractDTO thirdPartyContract) {
    return hasRevisionOfRentAmount(thirdPartyContract) || hasRevisionOfExpectedChargeCost(thirdPartyContract);
  }

  /**
   * Permet de savoir si un contrat tiers a une regularisation active
   * @param thirdPartyContract Contrat tiers
   * @return true si le contrat a une regularisation active
   */
  public static Boolean hasRegularization(ThirdPartyContractDTO thirdPartyContract) {
    return thirdPartyContract.getYlRegularization() > 0;
  }

  /**
   * Permet de calculer le montant à payer d’un mois pour le loyer mensuel en prenant en compte les différentes révisions
   * @param thirdPartyContract contrat tiers
   * @param revisions liste des révisions
   * @param ylZnAccountingDocNumber pièce compte initiale
   * @param treatmentDate date de traitement
   * @param firstMonth vrai si l’on traite le premier mois de révision
   * @return montant à payer d’un mois en prenant en compte les différentes révisions
   */
  public static BigDecimal getRevisionRentAmount(ThirdPartyContractDTO thirdPartyContract, List<RevisionThirdPartyContractDTO> revisions,
    YlZnAccountingDocNumberDTO ylZnAccountingDocNumber, LocalDate treatmentDate, Boolean firstMonth) {

    LocalDate firstDay = treatmentDate.dayOfMonth().withMinimumValue();
    LocalDate lastDay = treatmentDate.dayOfMonth().withMaximumValue();
    List<RevisionThirdPartyContractDTO> revisionsOfMonth = new ArrayList<RevisionThirdPartyContractDTO>();

    RevisionThirdPartyContractDTO mostRecentPreviousRevision = new RevisionThirdPartyContractDTO();
    for (RevisionThirdPartyContractDTO revision : revisions) {
      if (firstMonth) {
        if (!revision.getDate().after(thirdPartyContract.getRevisedRentDate())) {
          // On récupére la révision la plus récente dans le passé
          mostRecentPreviousRevision = revision;
        } else if (!revision.getDate().after(lastDay.toDate())) {
          // On récupére les révisions du mois de révision
          revisionsOfMonth.add(revision);
        }
      } else {
        if (!revision.getDate().after(firstDay.toDate())) {
          // On récupére la révision la plus récente dans le passé (triés par ID DESC)
          mostRecentPreviousRevision = revision;
        } else if (!revision.getDate().after(lastDay.toDate())) {
          // On récupére les révisions du mois de révision
          revisionsOfMonth.add(revision);
        }
      }
    }

    // supprime les révisions à ne pas traiter (déjà traitée dans le passé)
    int i = 0;
    List<RevisionThirdPartyContractDTO> revisionsToDelete = new ArrayList<RevisionThirdPartyContractDTO>();
    for (RevisionThirdPartyContractDTO revision : revisionsOfMonth) {
      int j = 0;
      for (RevisionThirdPartyContractDTO revision2 : revisionsOfMonth) {
        if (j < i && !revision2.getDate().before(revision.getDate())) {
          revisionsToDelete.add(revision2);
        }
        j += 1;
      }
      i += 1;
    }

    for (RevisionThirdPartyContractDTO revisionToDelete : revisionsToDelete) {
      revisionsOfMonth.remove(revisionToDelete);
    }

    Date endDate = treatmentDate.dayOfMonth().withMaximumValue().toDate();

    if (thirdPartyContract.getCancellationDate() != null && endDate.after(thirdPartyContract.getCancellationDate())) {
      endDate = thirdPartyContract.getCancellationDate();
    }

    BigDecimal initialAmount = BigDecimal.ZERO;
    BigDecimal finalAmount = BigDecimal.ZERO;

    if (revisions.isEmpty() || mostRecentPreviousRevision.getAmount() == null) {
      initialAmount = ylZnAccountingDocNumber.getYlZnAdnMensualRentAmount();
    } else {
      initialAmount = mostRecentPreviousRevision.getAmount();
    }

    if (revisionsOfMonth.isEmpty()) {
      BigDecimal revisedAmount = thirdPartyContract.getRevisedRentAmount();
      revisedAmount = revisedAmount.multiply(ThirdPartyContractUtils.getProportionsDays(treatmentDate.toDate(), endDate, treatmentDate));
      revisedAmount = revisedAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

      initialAmount = initialAmount.multiply(ThirdPartyContractUtils.getProportionsDays(treatmentDate.toDate(), endDate, treatmentDate));
      initialAmount = initialAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

      finalAmount = revisedAmount.subtract(initialAmount);
    } else {
      initialAmount = initialAmount.multiply(ThirdPartyContractUtils.getProportionsDays(treatmentDate.toDate(), endDate, treatmentDate));
      initialAmount = initialAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

      BigDecimal paidAmount = initialAmount;
      BigDecimal previousFirstPartMonth = BigDecimal.ZERO;

      int k = 0;
      for (RevisionThirdPartyContractDTO revision : revisionsOfMonth) {
        LocalDate revisionDate = LocalDate.fromDateFields(revision.getDate());

        BigDecimal firstPartAmount;
        if (k == 0) {
          firstPartAmount = paidAmount;
        } else {
          firstPartAmount = revisionsOfMonth.get(k - 1).getAmount();
        }

        if (k == 0) {
          firstPartAmount = firstPartAmount.multiply(ThirdPartyContractUtils.getProportionsDays(treatmentDate.toDate(), revisionDate.minusDays(1).toDate(), treatmentDate));
        } else {
          firstPartAmount = firstPartAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revisionsOfMonth.get(k - 1).getDate(), revisionDate.minusDays(1).toDate(),
            treatmentDate));
        }
        firstPartAmount = firstPartAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

        BigDecimal revisedAmount = revision.getAmount();
        revisedAmount = revisedAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revision.getDate(), endDate, treatmentDate));
        revisedAmount = revisedAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

        paidAmount = firstPartAmount.add(revisedAmount).add(previousFirstPartMonth);
        previousFirstPartMonth = firstPartAmount;
        k += 1;
      }

      BigDecimal currentRevisedAmount = thirdPartyContract.getRevisedRentAmount();
      finalAmount = currentRevisedAmount.subtract(paidAmount);
    }

    return finalAmount;
  }

  /**
   * Permet de calculer le montant à payer d’un mois pour les charges prévisionnelles mensuelles en prenant en compte les différentes révisions
   * @param thirdPartyContract contrat tiers
   * @param revisions liste des révisions
   * @param ylZnAccountingDocNumber pièce compte initiale
   * @param treatmentDate date de traitement
   * @param firstMonth vrai si l’on traite le premier mois de révision
   * @return montant à payer d’un mois en prenant en compte les différentes révisions
   */
  public static BigDecimal getRevisionExpectedChargeCostAmount(ThirdPartyContractDTO thirdPartyContract, List<RevisionThirdPartyContractDTO> revisions,
    YlZnAccountingDocNumberDTO ylZnAccountingDocNumber, LocalDate treatmentDate, Boolean firstMonth) {

    LocalDate firstDay = treatmentDate.dayOfMonth().withMinimumValue();
    LocalDate lastDay = treatmentDate.dayOfMonth().withMaximumValue();
    List<RevisionThirdPartyContractDTO> revisionsOfMonth = new ArrayList<RevisionThirdPartyContractDTO>();

    RevisionThirdPartyContractDTO mostRecentPreviousRevision = new RevisionThirdPartyContractDTO();
    for (RevisionThirdPartyContractDTO revision : revisions) {
      if (firstMonth) {
        if (!revision.getDate().after(thirdPartyContract.getRevisedExpectedChargeCostDate())) {
          // On récupére la révision la plus récente dans le passé
          mostRecentPreviousRevision = revision;
        } else if (!revision.getDate().after(lastDay.toDate())) {
          // On récupére les révisions du mois de révision
          revisionsOfMonth.add(revision);
        }
      } else {
        if (!revision.getDate().after(firstDay.toDate())) {
          // On récupére la révision la plus récente dans le passé (triés par ID DESC)
          mostRecentPreviousRevision = revision;
        } else if (!revision.getDate().after(lastDay.toDate())) {
          // On récupére les révisions du mois de révision
          revisionsOfMonth.add(revision);
        }
      }
    }

    // supprime les révisions à ne pas traiter (déjà traitée dans le passé)
    int i = 0;
    List<RevisionThirdPartyContractDTO> revisionsToDelete = new ArrayList<RevisionThirdPartyContractDTO>();
    for (RevisionThirdPartyContractDTO revision : revisionsOfMonth) {
      int j = 0;
      for (RevisionThirdPartyContractDTO revision2 : revisionsOfMonth) {
        if (j < i && !revision2.getDate().before(revision.getDate())) {
          revisionsToDelete.add(revision2);
        }
        j += 1;
      }
      i += 1;
    }

    for (RevisionThirdPartyContractDTO revisionToDelete : revisionsToDelete) {
      revisionsOfMonth.remove(revisionToDelete);
    }

    Date endDate = treatmentDate.dayOfMonth().withMaximumValue().toDate();

    if (thirdPartyContract.getCancellationDate() != null && endDate.after(thirdPartyContract.getCancellationDate())) {
      endDate = thirdPartyContract.getCancellationDate();
    }

    BigDecimal initialAmount = BigDecimal.ZERO;
    BigDecimal finalAmount = BigDecimal.ZERO;

    if (revisions.isEmpty() || mostRecentPreviousRevision.getAmount() == null) {
      initialAmount = ylZnAccountingDocNumber.getYlZnAdnMensualExpectedChargeCost();
    } else {
      initialAmount = mostRecentPreviousRevision.getAmount();
    }

    if (revisionsOfMonth.isEmpty()) {
      BigDecimal revisedAmount = thirdPartyContract.getRevisedExpectedChargeCostAmount();
      revisedAmount = revisedAmount.multiply(ThirdPartyContractUtils.getProportionsDays(treatmentDate.toDate(), endDate, treatmentDate));
      revisedAmount = revisedAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

      initialAmount = initialAmount.multiply(ThirdPartyContractUtils.getProportionsDays(treatmentDate.toDate(), endDate, treatmentDate));
      initialAmount = initialAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

      finalAmount = revisedAmount.subtract(initialAmount);
    } else {
      initialAmount = initialAmount.multiply(ThirdPartyContractUtils.getProportionsDays(treatmentDate.toDate(), endDate, treatmentDate));
      initialAmount = initialAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

      BigDecimal paidAmount = initialAmount;
      BigDecimal previousFirstPartMonth = BigDecimal.ZERO;

      int k = 0;
      for (RevisionThirdPartyContractDTO revision : revisionsOfMonth) {
        LocalDate revisionDate = LocalDate.fromDateFields(revision.getDate());

        BigDecimal firstPartAmount;
        if (k == 0) {
          firstPartAmount = paidAmount;
        } else {
          firstPartAmount = revisionsOfMonth.get(k - 1).getAmount();
        }

        if (k == 0) {
          firstPartAmount = firstPartAmount.multiply(ThirdPartyContractUtils.getProportionsDays(treatmentDate.toDate(), revisionDate.minusDays(1).toDate(), treatmentDate));
        } else {
          firstPartAmount = firstPartAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revisionsOfMonth.get(k - 1).getDate(), revisionDate.minusDays(1).toDate(),
            treatmentDate));
        }
        firstPartAmount = firstPartAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

        BigDecimal revisedAmount = revision.getAmount();
        revisedAmount = revisedAmount.multiply(ThirdPartyContractUtils.getProportionsDays(revision.getDate(), endDate, treatmentDate));
        revisedAmount = revisedAmount.setScale(BigDecimalUtils.SCALE_PRICE, BigDecimal.ROUND_HALF_EVEN);

        paidAmount = firstPartAmount.add(revisedAmount).add(previousFirstPartMonth);
        previousFirstPartMonth = firstPartAmount;
        k += 1;
      }

      BigDecimal currentRevisedAmount = thirdPartyContract.getRevisedExpectedChargeCostAmount();
      finalAmount = currentRevisedAmount.subtract(paidAmount);
    }

    return finalAmount;
  }
}
