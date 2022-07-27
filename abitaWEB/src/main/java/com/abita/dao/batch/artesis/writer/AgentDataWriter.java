/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.artesis.writer;

import com.abita.dao.contract.entity.ContractEntity;
import com.abita.dao.fieldofactivity.entity.FieldOfActivityEntity;
import com.abita.dao.tenant.entity.TenantEntity;
import com.abita.services.agency.IAgencyService;
import com.abita.services.agency.exceptions.AgencyServiceException;
import com.abita.services.fieldofactivity.IFieldOfActivityService;
import com.abita.services.fieldofactivity.exceptions.FieldOfActivityServiceException;
import com.abita.dto.AgencyDTO;
import com.abita.dto.ContractDTO;
import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.unpersist.AgentDataDTO;
import com.abita.dto.unpersist.ContractInputDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dozer.Mapper;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Classe qui permet d'écrire en base de données les données agents
 * @author
 *
 */
public class AgentDataWriter implements ItemWriter<AgentDataDTO> {

  /** SessionFactory injecté */
  private SessionFactory sessionFactory;

  /** Mapper dozzer */
  private Mapper mapper;

  /** Service domaine d'activité */
  private IFieldOfActivityService fieldOfActivityService;

  /** Service agence */
  private IAgencyService agencyService;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(AgentDataWriter.class);

  /** Index */
  private int i = 0;

  /** Libellé pour la journalisation */
  private static final String AGENT_DATA_LOG_LABEL = "Agent Data - ";

  /**
   * Remplacement automatique du domaine d'activité
   * @param items une liste de données agents DTO
   * @throws FieldOfActivityServiceException une FieldOfActivityServiceException
   */
  @Override
  public void write(List<? extends AgentDataDTO> items) throws FieldOfActivityServiceException {
    i++;
    LOGGER.info("Agent Data - Writing... " + i);
    // ON RECUPERE LA SESSION HIBERNATE EN COURS
    Session session = sessionFactory.getCurrentSession();
    if (!items.isEmpty()) {
      processItems(items, session);
    }
    session.flush();
  }

  /**
   * Traite la liste des données agent
   * @param items liste des données agent
   * @param session session Hibernate
   * @throws FieldOfActivityServiceException une FieldOfActivityServiceException
   */
  private void processItems(List<? extends AgentDataDTO> items, Session session) throws FieldOfActivityServiceException {
    try {
      long saveOrUpdateTenantCount = 0;
      long saveOrUpdateFieldOfActivityCount = 0;
      long saveOrUpdateContractCount = 0;
      List<FieldOfActivityDTO> existingFieldsOfActivity = fieldOfActivityService.findAllFieldOfActivity();
      AgencyDTO defaultAgency = agencyService.get(1L);

      for (AgentDataDTO item : items) {
        if (!session.contains(item)) {
          // UPDATE DE L'OCCUPANT //
          TenantEntity tenantEntity = mapper.map(item.getTenant(), TenantEntity.class);
          // ON UPDATE L'ENTITE OCCUPANT
          session.saveOrUpdate(tenantEntity);
          saveOrUpdateTenantCount++;

          // ON RECHERCHE SI LE DOMAINE D'ACTIVITE EXISTE DEJA DANS L'APPLI SINON ON L'AJOUTE
          if (!compareFieldOfActivityLabel(existingFieldsOfActivity, item.getFieldOfActivity().getLabel())) {
            // On ajoute l'agence par defaut "Autre"
            item.getFieldOfActivity().setAgency(defaultAgency);
            // ON UPDATE LE DOMAINE D'ACTIVITE
            FieldOfActivityEntity fieldOfActivityEntity = mapper.map(item.getFieldOfActivity(), FieldOfActivityEntity.class);
            // ON UPDATE L'ENTITE DOMAINE D'ACTIVITE
            session.save(fieldOfActivityEntity);
            // ON RECUPERE L'ID DU NOUVEAU DOMAINE D'ACTIVITE
            item.getFieldOfActivity().setId(fieldOfActivityEntity.getId());
            existingFieldsOfActivity.add(item.getFieldOfActivity());
            saveOrUpdateFieldOfActivityCount++;

            // ON MET A JOUR LES CONTRATS OCCUPANT
            for (ContractDTO contract : item.getContractsByTenant()) {
              updateContractWithNewFieldOfActivity(contract, item.getFieldOfActivity(), session);
              saveOrUpdateContractCount++;
            }
          } else {
            // ON UPDATE LES CONTRATS OCCUPANT
            for (ContractDTO contract : item.getContractsByTenant()) {
              // ON RECHERCHE SI LE DOMAINE D'ACTIVITE EST LE MEME QUE CELUI DU CONTRAT SINON ON LE REMPLACE
              if (!compareFieldOfActivityLabel(contract.getFieldOfActivity().getLabel(), item.getFieldOfActivity().getLabel())) {
                // ON RECUPERE L'IDENTIFIANT EXISTANT EN BASE DU DOMAINE D'ACTIVITE LU DANS LE FICHIER
                for (FieldOfActivityDTO fieldOfActivity : existingFieldsOfActivity) {
                  if (fieldOfActivity.getLabel().equalsIgnoreCase(item.getFieldOfActivity().getLabel())) {
                    item.getFieldOfActivity().setId(fieldOfActivity.getId());
                  }
                }
                // ON MET A JOUR LE CONTRAT OCCUPANT
                updateContractWithNewFieldOfActivity(contract, item.getFieldOfActivity(), session);
                saveOrUpdateContractCount++;
              }
            }
          }
        }
      }

      LOGGER.info(AGENT_DATA_LOG_LABEL + saveOrUpdateTenantCount + " tenant entities updated.");
      LOGGER.info(AGENT_DATA_LOG_LABEL + saveOrUpdateFieldOfActivityCount + " field of activity entities added.");
      LOGGER.info(AGENT_DATA_LOG_LABEL + saveOrUpdateContractCount + " contracts entities updated.");
    } catch (AgencyServiceException e) {
      LOGGER.error("Erreur lors de la récupération de l'agence par défaut");
      throw new FieldOfActivityServiceException(e);
    }
  }

  /**
   * Permet de comparer les labels d'une liste de domaine d'activité avec le label lu dans le fichier
   * @param existingFieldsOfActivity la liste de FieldOfActivityDTO
   * @param fieldOfActivityLabel le label à comparer
   * @return true s'il y'a une correspondance
   */
  public boolean compareFieldOfActivityLabel(List<FieldOfActivityDTO> existingFieldsOfActivity, String fieldOfActivityLabel) {
    boolean exist = false;
    for (FieldOfActivityDTO fieldOfActivity : existingFieldsOfActivity) {
      if (fieldOfActivity.getLabel().equalsIgnoreCase(fieldOfActivityLabel)) {
        exist = true;
        return exist;
      }
    }
    return exist;
  }

  /**
   * Permet de comparer le label de domaine d'acitivté d'un contrat occupant avec le label lu dans le fichier
   * @param contractFieldsOfActivity Le label du domaine d'activité du contrat occupant
   * @param fieldOfActivityLabel le label de domaine d'activité à comparer
   * @return true s'il y'a une correspondance
   */
  public boolean compareFieldOfActivityLabel(String contractFieldsOfActivity, String fieldOfActivityLabel) {
    boolean exist = false;
    if (contractFieldsOfActivity.equalsIgnoreCase(fieldOfActivityLabel)) {
      exist = true;
      return exist;
    }

    return exist;
  }

  /**
   * Permet de faire un update du domaine d'activité d'un contrat occupant
   * @param contract Le contrat occupant à update
   * @param fieldOfActivity Le domaine d'activité à ajouter pour le contrat
   * @param session la session hibernate
   */
  public void updateContractWithNewFieldOfActivity(ContractDTO contract, FieldOfActivityDTO fieldOfActivity, Session session) {
    ContractInputDTO contractInput = mapper.map(contract, ContractInputDTO.class);
    contractInput.setFieldOfActivity(fieldOfActivity);
    ContractEntity contratEntity = mapper.map(contractInput, ContractEntity.class);

    // ON UPDATE L'ENTITE CONTRAT OCCUPANT
    session.saveOrUpdate(contratEntity);
  }

  /**
   * @param sessionFactory the sessionFactory to set
   */
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  /**
   * @param mapper the mapper to set
   */
  public void setMapper(Mapper mapper) {
    this.mapper = mapper;
  }

  /**
   * @param fieldOfActivityService the fieldOfActivityService to set
   */
  public void setFieldOfActivityService(IFieldOfActivityService fieldOfActivityService) {
    this.fieldOfActivityService = fieldOfActivityService;
  }

  /**
   * @param agencyService the agencyService to set
   */
  public void setAgencyService(IAgencyService agencyService) {
    this.agencyService = agencyService;
  }
}
