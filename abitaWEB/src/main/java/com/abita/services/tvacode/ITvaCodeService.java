/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.tvacode;

import com.abita.dto.TvaCodeDTO;
import com.abita.services.tvacode.exceptions.TvaCodeServiceException;

import java.util.List;

/**
 * Interface de service des codes TVA
 *
 * @author
 */
public interface ITvaCodeService {

  /**
   * Permet de récupérer la totalité des codes TVA
   * @return une liste de tous les codes TVA
   * @throws TvaCodeServiceException  une TvaCodeServiceException
   */
  List<TvaCodeDTO> findAllTvaCode() throws TvaCodeServiceException;

  /**
   * Permet de sauvergarder la liste des codes TVA
   * @param lstTvaCode : la liste des codes
   * @throws TvaCodeServiceException l'exception
   */
  void saveListing(List<TvaCodeDTO> lstTvaCode) throws TvaCodeServiceException;

}
