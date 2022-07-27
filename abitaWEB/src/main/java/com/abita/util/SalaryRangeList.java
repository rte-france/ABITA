/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util;

import com.abita.util.decorator.SalaryRangeDecorator;
import com.abita.util.exceptions.UtilException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Liste de barèmes d'avantage en nature
 * Cette classe a été créée afin de rajouter des contraintes sur une liste classique
 * Par exemple, la liste ne doit pas être vide,
 * on ne doit donc pas autoriser la suppression du dernier élément
 * @author
 * @version 1.0
 */
public class SalaryRangeList extends ArrayList<SalaryRangeDecorator> {

  /** SerialID */
  private static final long serialVersionUID = -2071281109327641942L;

  /**
   * Ajoute un barème à la liste en conservant l'ordre des barèmes selon leur seuil inférieur
   * @param salaryRangeDecorator Barème a ajouter
   * @throws UtilException.ThresholdAlreadyUsed Si le seuil minimum de <code>salaryRangeDecorator</code> est déjà utilisé par un autre élément
   */
  public void sortedInsert(SalaryRangeDecorator salaryRangeDecorator) throws UtilException.ThresholdAlreadyUsed {
    add(findInsertIndex(salaryRangeDecorator), salaryRangeDecorator);
  }

  /**
   *  Trouve la position dans la liste où serait inséré le SalaryRangeDecorator indiqué
   * @param salaryRangeDecorator le SalaryRangeDecorator
   * @return La position dans la liste
   * @throws UtilException.ThresholdAlreadyUsed Quand le SalaryRangeDecorator a le même seuil inférieur de barème qu'un autre SalaryRangeDecorator dans la liste
   */
  private int findInsertIndex(SalaryRangeDecorator salaryRangeDecorator) throws UtilException.ThresholdAlreadyUsed {
    return findInsertIndex(salaryRangeDecorator.getMinimumThreshold());
  }

  /**
   * Trouve la position dans la liste où serait inséré un SalaryRangeDecorator avec la valeur de seuil inférieur de barème indiqué
   * @param value La valeur de seuil inférieur de barème
   * @return La position dans la liste
   * @throws UtilException.ThresholdAlreadyUsed Quand le SalaryRangeDecorator a le même seuil inférieur qu'un autre de barème SalaryRangeDecorator dans la liste
   */
  public int findInsertIndex(BigDecimal value) throws UtilException.ThresholdAlreadyUsed {
    if (isEmpty()) {
      return 0;
    }
    // Sinon, on parcourt la liste à la recherche de la bonne position pour insérer
    Iterator<SalaryRangeDecorator> iterator = iterator();
    SalaryRangeDecorator referenceElement;
    while (iterator.hasNext()) {
      referenceElement = iterator.next();
      if (value.compareTo(referenceElement.getMinimumThreshold()) == 0) {
        // Si le seuil minimum est déjà utilisé par un autre interval, on ne peut pas ajouter l'élément
        throw new UtilException.ThresholdAlreadyUsed("Seuil minimum déjà utilisé dans cette liste de barème");
      }
      if (value.compareTo(referenceElement.getMinimumThreshold()) < 0) {
        // Dès que la position est trouvé, on insère et on interrompt la boucle
        return indexOf(referenceElement);
      }
    }
    // Si on a parcouru toute la liste sans trouver de position pour insertion, alors l'élément se place en fin de liste
    return size();
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.ArrayList#remove(java.lang.Object)
   */
  @Override
  public boolean remove(Object o) {
    if (isRemoveItemAllowed()) {
      return super.remove(o);
    }
    return false;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.util.ArrayList#remove(int)
   */
  @Override
  public SalaryRangeDecorator remove(int index) {
    if (isRemoveItemAllowed()) {
      return super.remove(index);
    }
    throw new IllegalStateException("Erreur interne. Il n'est fonctionnellement pas possible de supprimer le dernier barème de la liste.");
  }

  /**
   * Indique si la suppression du barème est autorisé
   * @return Retourne <code>true</code> si l'item a retirer n'est pas le seul item de la liste
   */
  private boolean isRemoveItemAllowed() {
    return size() > 1;
  }
}
