package com.abita.dao.ncntaccountingdocnumber.impl;

import com.abita.dao.contract.constants.ContractConstants;
import com.abita.dao.ncntaccountingdocnumber.exceptions.NcNtAccountingDocNumberDAOException;
import com.abita.dao.ncntaccountingdocnumber.INcNtAccountingDocNumberDAO;
import com.abita.dao.ncntaccountingdocnumber.entity.NcNtAccountingDocNumberEntity;
import com.dao.common.impl.AbstractGenericEntityDAO;
import org.hibernate.Hibernate;
import org.hibernate.Query;

/**
 * Classe d'implémentation des services des informations nécessaires à la gestion du numéro de document des pièces comptables
 * @author
 *
 */
public class NcNtAccountingDocNumberDAOImpl extends AbstractGenericEntityDAO<NcNtAccountingDocNumberEntity, NcNtAccountingDocNumberDAOException> implements
  INcNtAccountingDocNumberDAO {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1442020282574784278L;

  @Override
  public Long getCurrentMaxValPieceNumber() throws NcNtAccountingDocNumberDAOException {
    Query query = getSession().getNamedQuery("getNcNtCurrentMaxValPieceNumber");
    return (Long) query.uniqueResult();
  }

  @Override
  public Long create(NcNtAccountingDocNumberEntity ncNtAccountingDocNumberEntity) throws NcNtAccountingDocNumberDAOException {

    Long id = (Long) super.create(ncNtAccountingDocNumberEntity);
    getSession().flush();
    return id;
  }

  @Override
  public void deleteAllNcNtAccountingDocNumberOfOneContract(Long id) throws NcNtAccountingDocNumberDAOException {
    // On supprime les historisations
    Query query = getSession().getNamedQuery("deleteNcNtAccountingDocNumber");
    query.setParameter(ContractConstants.ID_CONTRACT, id, Hibernate.LONG);
    query.executeUpdate();
  }

}
