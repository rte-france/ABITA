package com.abita.dao.revisionthirdpartycontract.impl;

import com.abita.dao.thirdpartycontract.constants.ThirdPartyContractConstants;
import com.abita.dao.revisionthirdpartycontract.IRevisionThirdPartyContractDAO;
import com.abita.dao.revisionthirdpartycontract.entity.RevisionThirdPartyContractEntity;
import com.abita.dao.revisionthirdpartycontract.exceptions.RevisionThirdPartyContractDAOException;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import java.util.List;

/**
 * Classe d’implémentation des DAO des révisions des contrats tiers
 */
public class RevisionThirdPartyContractDAOImpl extends AbstractGenericEntityDAO<RevisionThirdPartyContractEntity, RevisionThirdPartyContractDAOException> implements
  IRevisionThirdPartyContractDAO {

  /** SerialID */
  private static final long serialVersionUID = 7245882349739923636L;

  @SuppressWarnings("unchecked")
  @Override
  public List<RevisionThirdPartyContractEntity> findRevisionOfOneContract(Long tpcId) throws RevisionThirdPartyContractDAOException {
    Query query = getSession().getNamedQuery("findRevisionOfOneContract");
    query.setParameter("tpcId", tpcId, Hibernate.LONG);
    return query.list();
  }

  @Override
  public void deleteAllRevisionsOfOneContract(Long thirdPartyContractId) throws RevisionThirdPartyContractDAOException {
    // On supprime les historisations
    Query query = getSession().getNamedQuery("deleteAllRevisionsOfOneContract");
    query.setParameter(ThirdPartyContractConstants.ID_THIRDPARTY_CONTRACT, thirdPartyContractId, Hibernate.LONG);
    query.executeUpdate();
  }

}
