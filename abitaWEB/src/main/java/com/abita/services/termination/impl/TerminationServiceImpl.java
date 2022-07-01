package com.abita.services.termination.impl;

import com.abita.dao.termination.entity.TerminationEntity;
import com.abita.dao.termination.exceptions.TerminationDAOException;
import com.abita.services.termination.ITerminationService;
import com.abita.dao.termination.ITerminationDAO;
import com.abita.dto.TerminationDTO;
import com.abita.services.termination.exceptions.TerminationServiceException;
import com.services.common.impl.AbstractService;

/**
 * Classe d'implémentation des motifs de résiliation
 *
 * @author
 */
public class TerminationServiceImpl extends AbstractService<TerminationEntity, TerminationDTO, TerminationDAOException, Long, TerminationServiceException, ITerminationDAO>
  implements ITerminationService {

  /**
  * Le DAO des motifs de résiliation
  */
  private ITerminationDAO terminationDAO;

  @Override
  public Long create(TerminationDTO terminationDTO) throws TerminationServiceException {
    return (Long) super.create(terminationDTO);
  }

  @Override
  protected ITerminationDAO getSpecificIDAO() {
    return terminationDAO;
  }

  /**
   * @param terminationDAO the terminationDAO to set
   */
  public void setTerminationDAO(ITerminationDAO terminationDAO) {
    this.terminationDAO = terminationDAO;
  }

}
