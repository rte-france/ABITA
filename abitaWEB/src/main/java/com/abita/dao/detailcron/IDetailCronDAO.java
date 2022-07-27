/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.detailcron;

import com.abita.dao.detailcron.entity.DetailCronEntity;
import com.abita.dao.detailcron.exceptions.DetailCronDAOException;
import com.dao.common.IAbstractDAO;

/**
 * Interface des DAO de l'administration des détails cron
 *
 * @author
 */
public interface IDetailCronDAO extends IAbstractDAO<DetailCronEntity, DetailCronDAOException> {

}
