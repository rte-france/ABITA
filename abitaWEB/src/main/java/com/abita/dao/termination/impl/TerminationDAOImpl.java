/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.termination.impl;

import com.abita.dao.termination.exceptions.TerminationDAOException;
import com.abita.dao.termination.ITerminationDAO;
import com.abita.dao.termination.entity.TerminationEntity;
import com.dao.common.impl.AbstractGenericEntityDAO;

/**
 * Classe d'implémentation des résiliation de contrat
 *
 * @author
 */
public class TerminationDAOImpl extends AbstractGenericEntityDAO<TerminationEntity, TerminationDAOException> implements ITerminationDAO {

  /** serialVersionUID */
  private static final long serialVersionUID = 1052171540195554403L;
}
