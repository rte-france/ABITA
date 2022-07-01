package com.abita.dto;

import com.abita.web.shared.ConstantsWEB;
import com.dto.AbstractDTO;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * DTO des barèmes des avantages en nature
 * Cette classe se nomme "SalaryLevel" car elle ne porte que le seuil minimum du barème
 * @author
 * @version 1.0
 */
public class SalaryLevelDTO extends AbstractDTO {

  /** SerialID */
  private static final long serialVersionUID = 593998416270101014L;

  /** Le détail des avantages en nautre */
  private BenefitDTO benefit;

  /** Seuil minimum du barème */
  // Sans les annotations, le nom du champs indiqué dans la règle de validation est correct
  @NotNull(message = "{administration.referentiel.benefit.error.minimumthreshold.mandatory}")
  @Digits(integer = ConstantsWEB.INTEGER_PART_SIZE_10, fraction = ConstantsWEB.DECIMAL_PART_SIZE_2, message = "{administration.referentiel.benefit.error.minimumthreshold.format}")
  @Min(value = 0, message = "{administration.referentiel.benefit.error.minimumthreshold.format}")
  private BigDecimal minimumThreshold;

  /**
   * @return the benefit
   */
  public BenefitDTO getBenefit() {
    return benefit;
  }

  /**
   * @param benefit the benefit to set
   */
  public void setBenefit(BenefitDTO benefit) {
    this.benefit = benefit;
  }

  /**
   * @return the minimumThreshold
   */
  public BigDecimal getMinimumThreshold() {
    return minimumThreshold;
  }

  /**
   * @param minimumThreshold the minimumThreshold to set
   */
  public void setMinimumThreshold(BigDecimal minimumThreshold) {
    this.minimumThreshold = minimumThreshold;
  }
}
