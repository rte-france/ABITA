package com.abita.services.thirdpartytermination;

import com.abita.dto.ThirdPartyTerminationDTO;
import com.abita.services.thirdpartytermination.exceptions.ThirdPartyTerminationServiceException;

import java.util.List;

/**
 * Interface du service des motifs de résiliations
 *
 * @author
 */
public interface IThirdPartyTerminationService {

  /**
   * Permet de récupérer la liste de tous les motifs de résiliation
   *
   * @return une liste de tous les motifs de résiliation
   *
   * @throws ThirdPartyTerminationServiceException une ThirdPartyTerminationServiceException
   */
  List<ThirdPartyTerminationDTO> find() throws ThirdPartyTerminationServiceException;

}
