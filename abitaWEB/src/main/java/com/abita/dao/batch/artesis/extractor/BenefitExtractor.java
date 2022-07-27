/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.artesis.extractor;

import com.abita.dao.batch.artesis.ArtesisBenefitsBlock;
import org.springframework.batch.item.file.transform.FieldExtractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui sert à inserer les valeurs formattées que l'on désire écrire dans le fichier ARTESIS avantage en nature
 * @author
 *
 */
public class BenefitExtractor implements FieldExtractor<ArtesisBenefitsBlock> {

  @Override
  public Object[] extract(ArtesisBenefitsBlock item) {
    List<String> result = new ArrayList<String>();

    result.add(item.getOriginApplication());
    result.add(item.getNni());
    /** Société de gestion */
    result.add(item.getManagementCompany());
    /** Numéro de bail */
    result.add(item.getContractReference());
    /** Date de début */
    result.add(item.getStartDate());
    /** Date de fin */
    result.add(item.getEndDate());
    /** Période de paie */
    result.add(item.getPayPeriod());
    /** Date de début de bail */
    result.add(item.getStartValidityDate());
    /** Date de fin de bail ou Date de résiliation */
    result.add(item.getEndValidityDate());
    /** Indice logement imposé ou assigné */
    result.add(item.getHousingIndex());
    /** Nature du local */
    result.add(item.getHousingNature());
    /** Nombre de pièces */
    result.add(item.getRoomCount());
    /** Date de quittancement */
    result.add(item.getQuittancementDate());
    /** Année de la VLF */
    result.add(item.getRealEstateRentalValueYear());
    /** Origine de la VLF */
    result.add(item.getRealEstateRentalValueOrigin());
    /** Signe de la VLF */
    result.add(item.getRealEstateRentalValueSign());
    /** Valeur mensuel de la VLF */
    result.add(item.getRealEstateRentalValueAmount());
    /** Signe du forfait */
    result.add(item.getBenefitsSign());
    /** Forfait mensuel */
    result.add(item.getBenefitsAmount());
    /** Signe du montant loyer */
    result.add(item.getWithdrawnRentSign());
    /** Montant net du loyer mensuel */
    result.add(item.getWithdrawnRentAmount());
    /** Témoin de rappel */
    result.add(item.getEndOfLine());
    return result.toArray();
  }
}
