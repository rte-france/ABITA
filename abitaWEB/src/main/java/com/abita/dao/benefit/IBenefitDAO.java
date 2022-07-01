package com.abita.dao.benefit;

import com.abita.dao.benefit.entity.Benefit;
import com.abita.dao.benefit.entity.SalaryLevelEntity;
import com.abita.dao.benefit.exceptions.BenefitDAOException;
import com.dao.common.IAbstractDAO;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface des DAO de l'administration des barèmes pour le calcul des avantages
 * en nature
 * @author
 * @version 1.0
 */
public interface IBenefitDAO extends IAbstractDAO<SalaryLevelEntity, BenefitDAOException> {

  /**
   * Permet de récupèrer la liste de tous les barèmes en BDD
   * @return liste des barèmes et leurs avantages associés
   * @throws BenefitDAOException une BenefitDAOException
   */
  List<SalaryLevelEntity> findAllSortedSalaryLevel() throws BenefitDAOException;

  /**
   * Récupère les avantages en nature du barème dans lequel se trouve la rémunération indiquée
   * @param salary le salaire pour lequel on cherche les avantages en nature
   * @return Retourne les avantages correspondant au salaire indiqué, ou <code>null</code> si le salaire ne rentre dans aucun barème
   * @throws BenefitDAOException une BenefitDAOException
   */
  Benefit findBenefitBySalary(BigDecimal salary) throws BenefitDAOException;

}
