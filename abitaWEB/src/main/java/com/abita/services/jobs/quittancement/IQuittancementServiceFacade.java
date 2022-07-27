/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.quittancement;

import com.services.paramservice.ParameterService;
import org.joda.time.YearMonth;

import java.io.File;

/**
 * Interface du service façade des fichiers de quittancement
 */
public interface IQuittancementServiceFacade {

  /**
   * Permet de générer les quittancements de loyer
   */
  void generate();

  /**
   * Permet de définir les paramètres des quittancements
   * @param parameterService paramètres
   */
  void setParameterService(ParameterService parameterService);
}
