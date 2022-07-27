/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.thirdpartytermination;

import com.abita.dao.thirdpartytermination.entity.ThirdPartyTerminationEntity;
import com.abita.dao.thirdpartytermination.exceptions.ThirdPartyTerminationDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface DAO des motifs de résiliations
 *
 * @author
 */
public interface IThirdPartyTerminationDAO extends IAbstractDAO<ThirdPartyTerminationEntity, ThirdPartyTerminationDAOException> {

}
