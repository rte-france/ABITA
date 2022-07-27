/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.batch.artesis.processor;

import com.abita.dao.batch.artesis.reader.AgentDataFlatFileReader;
import com.abita.services.contract.IContractServiceFacade;
import com.abita.services.tenant.ITenantServiceFacade;
import com.abita.dto.ContractDTO;
import com.abita.dto.TenantDTO;
import com.abita.dto.unpersist.AgentDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe qui permet d'ajouter les informations manquantes à la lecture afin de completer les informations de l'objet donnée agent.
 */
public class AgentDataProcessor implements ItemProcessor<AgentDataDTO, AgentDataDTO> {

  /** Service Occupant */
  private ITenantServiceFacade tenantServiceFacade;

  /** Service Contrat occupant */
  private IContractServiceFacade contractServiceFacade;

  /** LOGGER */
  private static final Logger LOGGER = LoggerFactory.getLogger(AgentDataFlatFileReader.class);

  @Override
  public AgentDataDTO process(AgentDataDTO item) throws Exception {
    TenantDTO tmpTenant = tenantServiceFacade.findTenantByNNI(item.getTenant().getReference());
    try {
      List<ContractDTO> contractsByTenant = contractServiceFacade.findContractByTenant(tmpTenant.getId());
      item.getTenant().setId(tmpTenant.getId());
      item.getTenant().setHouseholdSizeLastYear(tmpTenant.getHouseholdSizeLastYear());
      item.getTenant().setFirstName(tmpTenant.getFirstName());
      item.getTenant().setTypeTenant(tmpTenant.getTypeTenant());
      item.getTenant().setManagerialLastYear(tmpTenant.getManagerialLastYear());
      item.getTenant().setPhone(tmpTenant.getPhone());
      item.getTenant().setMailAddress(tmpTenant.getMailAddress());
      item.setContractsByTenant(contractsByTenant);

      /** V1.5.0 : Pour le salaire de l'agent à Octobre N-1, si la valeur contenue dans le fichier
       * référence vaut 0, alors on utilisera celle déjà existante en base.
       * Si celle en base vaut aussi 0, alors on utilisera le salaire échelon 35h du fichier.
       */
      if (BigDecimal.ZERO.compareTo(item.getReferenceGrossSalaryFromFile()) == 0) {

        if (BigDecimal.ZERO.compareTo(tmpTenant.getReferenceGrossSalary()) == 0) {

          item.getTenant().setReferenceGrossSalary(item.getGrossSalary35hFromFile());
        } else {

          item.getTenant().setReferenceGrossSalary(tmpTenant.getReferenceGrossSalary());
        }
      } else {

        item.getTenant().setReferenceGrossSalary(item.getReferenceGrossSalaryFromFile());
      }
      LOGGER.debug("Salaire d'octobre N-1 : " + item.getTenant().getReferenceGrossSalary());
      LOGGER.info("Agent Data - Modifing ... " + item.getTenant().getReference());
      return item;
    } catch (NullPointerException e) {
      LOGGER.error(e.getMessage(), e);
      LOGGER.info("Agent Data - Error : This NNI doesn't exist in database ... ");
      return null;
    } catch (org.hibernate.NonUniqueResultException e) {
      LOGGER.error(e.getMessage(), e);
      LOGGER.info("Agent Data - Error : There are two same NNI in database... " + item.getTenant().getReference());
      return null;
    }
  }

  /**
   * @param contractServiceFacade the contractServiceFacade to set
   */
  public void setContractServiceFacade(IContractServiceFacade contractServiceFacade) {
    this.contractServiceFacade = contractServiceFacade;
  }

  /**
   * @param tenantServiceFacade the tenantServiceFacade to set
   */
  public void setTenantServiceFacade(ITenantServiceFacade tenantServiceFacade) {
    this.tenantServiceFacade = tenantServiceFacade;
  }

}
