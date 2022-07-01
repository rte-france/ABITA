/**
 *
 */
package com.abita.dao.batch.artesis.reader;

import com.abita.services.tenant.ITenantServiceFacade;
import com.abita.services.tenant.exceptions.TenantServiceFacadeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.util.Iterator;
import java.util.List;

/**
 * Permet la lecture des occupant en base de données qui figueront dans le fichier NNI
 * @author
 *
 */
public class NNIReader implements ItemStreamReader<String> {

  /** Service des occupants */
  private ITenantServiceFacade tenantServiceFacade;

  /** Iterator de NNI */
  private Iterator<String> nnis;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(NNIReader.class);

  @Override
  public void open(ExecutionContext executionContext) {
    LOGGER.debug("Récupération des données.");
    try {
      List<String> nniList = tenantServiceFacade.findNNI();
      if (nniList != null) {
        nnis = nniList.iterator();
      }
    } catch (TenantServiceFacadeException e) {
      LOGGER.debug("Erreur lors de la récupération des lignes du fichier des NNI.");
      throw new ItemStreamException(e);
    }

  }

  @Override
  public String read() {
    if (nnis != null && nnis.hasNext()) {
      return nnis.next();
    }
    return null;
  }

  @Override
  public void update(ExecutionContext executionContext) {
    LOGGER.debug("Mise à jour des données.");

  }

  @Override
  public void close() {
    LOGGER.debug("Fermeture du lecteur.");

  }

  /**
   * @param tenantServiceFacade the tenantServiceFacade to set
   */
  public void setTenantServiceFacade(ITenantServiceFacade tenantServiceFacade) {
    this.tenantServiceFacade = tenantServiceFacade;
  }

}
