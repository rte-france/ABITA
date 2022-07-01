package com.abita.services.jobs.archives;

import com.abita.services.jobs.archives.exceptions.ArchivesServiceException;

/**
 * Interface du service des archives
 *
 * @author
 */
public interface IArchivesService {

  /**
   * Point d'entrée d'exécution du traitement
   * @throws ArchivesServiceException l'exception
   */
  void process() throws ArchivesServiceException;

}
