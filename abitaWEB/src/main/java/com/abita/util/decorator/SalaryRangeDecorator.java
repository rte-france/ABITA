package com.abita.util.decorator;

import com.abita.dto.BenefitDTO;
import com.abita.dto.SalaryLevelDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO des barèmes des avantages en nature
 * Cette classe se nomme "SalaryRange" car elle fourni à la fois le seuil minimum et le seuil maximum du barème
 * @author
 * @version 1.0
 */
public class SalaryRangeDecorator extends SalaryLevelDTO {

  /** SerialID */
  private static final long serialVersionUID = 8442865924987524508L;

  /** Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(SalaryRangeDecorator.class);

  /** Liste contenant <code>this</code> */
  private List<SalaryRangeDecorator> salaryRangeList;

  /** Le barème pour avantages en nature décoré */
  private SalaryLevelDTO salaryLevelDTO;

  /**
   * Constructeur
   * @param salaryLevelDTO Le barème pour avantages en nature décoré
   * @param rangeList Liste contenant <code>this</code>
   */
  public SalaryRangeDecorator(SalaryLevelDTO salaryLevelDTO, List<SalaryRangeDecorator> rangeList) {
    if (salaryLevelDTO == null) {
      LOGGER.error("Erreur interne. La barème des avantages en nature n'est pas initialisé.");
      throw new IllegalArgumentException();
    }
    this.salaryLevelDTO = salaryLevelDTO;

    if (rangeList == null) {
      LOGGER.error("Erreur interne. La liste devant contenir les barèmes des avantages en nature n'est pas initialisée.");
      throw new IllegalArgumentException();
    }
    salaryRangeList = rangeList;
  }

  /**
   * La valeur max d'un barème est la valeur min du barème suivant
   * Récupère la valeur minimum du barème suivant
   * @return Retourne la valeur minimum du barème suivant, ou <code>null</code> s'il n'y a pas de barème suivant
   */
  public BigDecimal getMaximumThreshold() {

    int position = salaryRangeList.indexOf(this);
    try {
      if (position + 1 < salaryRangeList.size()) {
        return salaryRangeList.get(++position).getMinimumThreshold();
      }
    } catch (IndexOutOfBoundsException e) {
      // Il n'existe pas d'élément suivant
      LOGGER.error("Il n'existe pas d'élément suivant.", e);
    }
    return null;
  }

  /**
   * @return the salaryRangeLinkedList
   */
  public List<SalaryRangeDecorator> getSalaryRangeLinkedList() {
    return salaryRangeList;
  }

  /**
   * @return the salaryLevelDTO
   */
  public SalaryLevelDTO getSalaryLevelDTO() {
    return salaryLevelDTO;
  }

  @Override
  public Long getId() {
    return salaryLevelDTO.getId();
  }

  @Override
  public BenefitDTO getBenefit() {
    return salaryLevelDTO.getBenefit();
  }

  @Override
  public void setBenefit(BenefitDTO benefit) {
    salaryLevelDTO.setBenefit(benefit);
  }

  @Override
  public BigDecimal getMinimumThreshold() {
    return salaryLevelDTO.getMinimumThreshold();
  }

  @Override
  public void setMinimumThreshold(BigDecimal minimumThreshold) {
    salaryLevelDTO.setMinimumThreshold(minimumThreshold);
  }

}
