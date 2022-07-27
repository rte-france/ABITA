/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util;

import com.dao.common.entity.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitaire de mapping groupé
 * Lors du mapping d'objet complexe, Dozer peut mettre en cache des objets qu'il a déjà converti si ceux-ci doivent être mappé plusieurs fois au cours du même appel .map()
 * Pour parvenir a profiter de cette optimisation, si un ensemble d'objet doivent être mappés, il peut être bénéfique de les encapsuler dans une classe "liste" et mapper cette liste
 * Cette classe participe à la mise en place de ce système de façon générique
 *
 *
 * @author
 *
 * @param <T> Le type d'Entity attendu
 */
public class EntityListWrapper<T extends AbstractEntity> {

  /** La liste à wrapper */
  private List<T> list;

  /** Constructeur */
  public EntityListWrapper() {
    this.list = new ArrayList<T>();
  }

  /**
   * Constructeur
   * @param list la liste a wrapper
   */
  public EntityListWrapper(List<T> list) {
    this.list = list;
  }

  /**
   * @return the list
   */
  public List<T> getList() {
    return list;
  }

  /**
   * @param list the list to set
   */
  public void setList(List<T> list) {
    this.list = list;
  }

}
