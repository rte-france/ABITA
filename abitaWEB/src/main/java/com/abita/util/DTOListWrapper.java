package com.abita.util;

import com.dto.AbstractDTO;

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
 * @param <M> Le type de DTO attendu
 */
public class DTOListWrapper<M extends AbstractDTO> {

  /** La liste à wrapper */
  private List<M> list = new ArrayList<M>();

  /**
   * @return the list
   */
  public List<M> getList() {
    return list;
  }

  /**
   * @param list the list to set
   */
  public void setList(List<M> list) {
    this.list = list;
  }

}
