/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.benefit;

import com.abita.dto.BenefitDTO;
import com.abita.dto.SalaryLevelDTO;
import com.abita.services.benefit.exceptions.BenefitServiceException;
import com.abita.util.SalaryRangeList;

import java.math.BigDecimal;

/**
 * Interface du service des barèmes pour avantage en nature
 * @author
 * @version 1.0
 */
public interface IBenefitService {

  /**
   * Permet de créer un nouveau barème pour avantage en nature en BDD
   * @param salaryLevel un barème pour avantage en nature
   * @return l'identifiant du barème pour avantage en nature
   * @throws BenefitServiceException une BenefitServiceException
   */
  Long create(SalaryLevelDTO salaryLevel) throws BenefitServiceException;

  /**
   * Supprime un barème pour avantage en nature en BDD
   * @param id l'identifiant du barème
   * @throws BenefitServiceException une BenefitServiceException
   */
  void delete(Long id) throws BenefitServiceException;

  /**
   * Permet de récupérer la liste de tous les barèmes pour avantage trié par ordre de barème
   * @return une liste de tous les barèmes pour avantage en nature
   * @throws BenefitServiceException une BenefitServiceException
   */
  SalaryRangeList findAllSortedSalaryRange() throws BenefitServiceException;

  /**
   * Permet de récupérer les avantages en nature liés à un barème
   * @param salary rémunération de référence pour trouver le barème et les avantages liés a ce barème
   * @return les avantages en nature
   * @throws BenefitServiceException une BenefitServiceException
   */
  BenefitDTO findBenefitBySalary(BigDecimal salary) throws BenefitServiceException;

  /**
   * Méthode permettant de sauvegarder la liste des barèmes pour avantage en nature
   * @param benefitList le liste des barèmes pour avantage en nature
   * @throws BenefitServiceException une BenefitServiceException
   */
  void saveListing(SalaryRangeList benefitList) throws BenefitServiceException;

  /**
   * Met à jour un barème pour avantage en nature en BDD.
   * @param benefit un barème pour avantage en nature
   * @throws BenefitServiceException une BenefitServiceException
   */
  void update(SalaryLevelDTO benefit) throws BenefitServiceException;

}
