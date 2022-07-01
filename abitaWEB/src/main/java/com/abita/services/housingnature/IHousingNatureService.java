package com.abita.services.housingnature;

import com.abita.dto.HousingNatureDTO;
import com.abita.services.housingnature.exceptions.HousingNatureServiceException;

import java.util.List;

/**
 * Interface du service des natures
 *
 * @author
 */
public interface IHousingNatureService {

  /**
   * Permet de récupérer la liste de toutes les natures
   *
   * @return une liste de toutes les natures
   *
   * @throws HousingNatureServiceException une HousingNatureServiceException
   */
  List<HousingNatureDTO> find() throws HousingNatureServiceException;

}
