/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.thirdpartytermination.impl;

import com.abita.dao.thirdpartytermination.entity.ThirdPartyTerminationEntity;
import com.abita.dao.thirdpartytermination.exceptions.ThirdPartyTerminationDAOException;
import com.abita.services.thirdpartytermination.IThirdPartyTerminationService;
import com.abita.dao.thirdpartytermination.IThirdPartyTerminationDAO;
import com.abita.dto.ThirdPartyTerminationDTO;
import com.abita.services.thirdpartytermination.exceptions.ThirdPartyTerminationServiceException;
import com.services.common.impl.AbstractService;

/**
 * Classe d'implémentation des motifs de résiliations
 *
 * @author
 */
public class ThirdPartyTerminationServiceImpl extends
  AbstractService<ThirdPartyTerminationEntity, ThirdPartyTerminationDTO, ThirdPartyTerminationDAOException, Long, ThirdPartyTerminationServiceException, IThirdPartyTerminationDAO>
  implements IThirdPartyTerminationService {

  /**
  * Le DAO des motifs de résiliation
  */
  private IThirdPartyTerminationDAO thirdPartyTerminationDAO;

  @Override
  protected IThirdPartyTerminationDAO getSpecificIDAO() {
    return thirdPartyTerminationDAO;
  }

  /**
   * @param thirdPartyTerminationDAO the thirdPartyTerminationDAO to set
   */
  public void setThirdPartyTerminationDAO(IThirdPartyTerminationDAO thirdPartyTerminationDAO) {
    this.thirdPartyTerminationDAO = thirdPartyTerminationDAO;
  }

}
