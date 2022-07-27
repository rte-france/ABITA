/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.util;

import com.dao.common.entity.AbstractEntity;
import com.dto.AbstractDTO;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.dozer.loader.api.FieldsMappingOptions.hintA;
import static org.dozer.loader.api.FieldsMappingOptions.hintB;

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
 * @param <M> Le type de DTO attendu
 */
public class GenericDozerListWrapper<T extends AbstractEntity, M extends AbstractDTO> {

  /** Le type de l'entity */
  private Class<T> sourceClass;

  /** Le type de DTO */
  private Class<M> destClass;

  /**
   * Constructeur
   * @param t Le type de l'entity
   * @param m Le type de DTO
   */
  public GenericDozerListWrapper(Class<T> t, Class<M> m) {
    sourceClass = t;
    destClass = m;
  }

  /**
   * Methode effectuer le mapping des listes d'objets sources vers les listes d'objets destinations
   * @param mapper le mapper dozer déjà chargé
   * @param entities la liste des entités à mapper
   * @return la liste des DTO mappés
   */
  public List<M> map(Mapper mapper, List<T> entities) {

    // On créé une configuration de mapping à la volée pour indiquer a hibernate le type des objets contenu dans les listes des différents
    // wrappers
    BeanMappingBuilder builder = new BeanMappingBuilder() {

      @Override
      protected void configure() {
        mapping(EntityListWrapper.class, DTOListWrapper.class).fields("list", "list", hintA(sourceClass), hintB(destClass));
      }
    };

    List<String> mappingFiles = new ArrayList<String>();
    Mapper finalMapper = mapper;
    // Du coup : Pas besoin du fichier de mapping pour un mapp "ad hoc"
    finalMapper = new DozerBeanMapper(mappingFiles);

    ((DozerBeanMapper) finalMapper).addMapping(builder);
    EntityListWrapper<T> entityListWrapper = new EntityListWrapper<T>(entities);
    DTOListWrapper<M> dtoListWrapper = new DTOListWrapper<M>();
    finalMapper.map(entityListWrapper, dtoListWrapper);
    return dtoListWrapper.getList();
  }
}
