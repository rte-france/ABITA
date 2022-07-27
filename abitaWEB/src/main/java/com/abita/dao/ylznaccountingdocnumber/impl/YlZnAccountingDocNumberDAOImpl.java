/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.ylznaccountingdocnumber.impl;

import com.abita.util.dateutil.DateTimeUtils;
import com.abita.dao.thirdpartycontract.constants.ThirdPartyContractConstants;
import com.abita.dao.ylznaccountingdocnumber.IYlZnAccountingDocNumberDAO;
import com.abita.dao.ylznaccountingdocnumber.entity.YlZnAccountingDocNumberEntity;
import com.abita.dao.ylznaccountingdocnumber.exceptions.YlZnAccountingDocNumberDAOException;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.joda.time.YearMonth;

import java.util.Date;
import java.util.List;

/**
 * Classe d'implémentation des DAO des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public class YlZnAccountingDocNumberDAOImpl extends AbstractGenericEntityDAO<YlZnAccountingDocNumberEntity, YlZnAccountingDocNumberDAOException> implements
  IYlZnAccountingDocNumberDAO {

  /** Nom du paramètre pour thirdPartyContractId */
  private static final String THIRD_PARTY_CONTRACT_ID_PARAMETER = "thirdPartyContractId";
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -8405801871421915402L;

  @Override
  public Long getCurrentMaxValPieceNumber() throws YlZnAccountingDocNumberDAOException {
    Query query = getSession().getNamedQuery("getYlZnCurrentMaxValPieceNumber");
    return (Long) query.uniqueResult();
  }

  @Override
  public YlZnAccountingDocNumberEntity findLastGenerationByThirdPartyContractIdAndYearMonth(Long thirdPartyContractId, YearMonth yearMonth)
    throws YlZnAccountingDocNumberDAOException {

    int month = yearMonth.getMonthOfYear();
    int year = yearMonth.getYear();

    Query query = getSession().getNamedQuery("findLastGenerationByThirdPartyContractIdAndYearMonth");
    query.setParameter(THIRD_PARTY_CONTRACT_ID_PARAMETER, thirdPartyContractId, Hibernate.LONG);
    query.setParameter("month", month, Hibernate.INTEGER);
    query.setParameter("year", year, Hibernate.INTEGER);
    return (YlZnAccountingDocNumberEntity) query.uniqueResult();
  }

  @Override
  public YlZnAccountingDocNumberEntity findLastGenerationByThirdPartyContractId(Long thirdPartyContractId) throws YlZnAccountingDocNumberDAOException {

    Query query = getSession().getNamedQuery("findLastGenerationByThirdPartyContractId");
    query.setParameter(THIRD_PARTY_CONTRACT_ID_PARAMETER, thirdPartyContractId, Hibernate.LONG);
    return (YlZnAccountingDocNumberEntity) query.uniqueResult();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<YlZnAccountingDocNumberEntity> findGenerationsByThirdPartyContractIdFromYearMonth(Long thirdPartyContractId, YearMonth yearMonth)
    throws YlZnAccountingDocNumberDAOException {

    Date date = DateTimeUtils.getMinimumTimeOfDayOfMonth(yearMonth.toLocalDate(1)).toDate();

    Query query = getSession().getNamedQuery("findGenerationsByThirdPartyContractIdFromYearMonth");
    query.setParameter(THIRD_PARTY_CONTRACT_ID_PARAMETER, thirdPartyContractId, Hibernate.LONG);
    query.setParameter("date", date, Hibernate.DATE);
    return query.list();
  }

  @Override
  public Long create(YlZnAccountingDocNumberEntity ylZnAccountingDocNumberEntity) throws YlZnAccountingDocNumberDAOException {

    Long id = (Long) super.create(ylZnAccountingDocNumberEntity);
    getSession().flush();
    return id;
  }

  @Override
  public void deleteAllYlZnAccountingDocNumberOfOneContract(Long id) throws YlZnAccountingDocNumberDAOException {
    // On supprime les historisations
    Query query = getSession().getNamedQuery("deleteYlZnAccountingDocNumber");
    query.setParameter(ThirdPartyContractConstants.ID_THIRDPARTY_CONTRACT, id, Hibernate.LONG);
    query.executeUpdate();
  }
}
