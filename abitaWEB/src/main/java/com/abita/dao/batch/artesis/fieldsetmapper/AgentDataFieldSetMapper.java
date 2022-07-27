/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

/**
 *
 */
package com.abita.dao.batch.artesis.fieldsetmapper;

import com.abita.dto.FieldOfActivityDTO;
import com.abita.dto.TenantDTO;
import com.abita.dto.unpersist.AgentDataDTO;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;

/**
 * Permet de récupérer les informations lues dans le fichier données agents et d'instancier un objet de donnée d'agent
 * @author
 *
 */
public class AgentDataFieldSetMapper implements FieldSetMapper<AgentDataDTO> {

  @Override
  public AgentDataDTO mapFieldSet(FieldSet fieldSet) {
    AgentDataDTO agentData = new AgentDataDTO();
    agentData.setTenant(new TenantDTO());
    agentData.setFieldOfActivity(new FieldOfActivityDTO());
    agentData.getTenant().setReference(fieldSet.readString("longNNI") + fieldSet.readString("shortNNI"));
    agentData.getTenant().setLastName(fieldSet.readString("lastName"));
    agentData.getFieldOfActivity().setLabel(fieldSet.readString("accountingDescriptor"));
    String managerial = fieldSet.readString("managerial");
    managerial = managerial.replaceAll("0", "");
    managerial = managerial.replaceAll("\\.", "");
    if ("3".equals(managerial)) {
      agentData.getTenant().setManagerial(true);
    } else {
      agentData.getTenant().setManagerial(false);
    }
    agentData.getTenant().setAddress(fieldSet.readString("adress"));
    agentData.getTenant().setPostalCode(fieldSet.readString("postalCode"));
    agentData.getTenant().setCity(fieldSet.readString("city"));

    agentData.setReferenceGrossSalaryFromFile(new BigDecimal(fieldSet.readString("referenceGrossSalary")));
    agentData.setGrossSalary35hFromFile(new BigDecimal(fieldSet.readString("grossSalary35hLevel1")));

    agentData.getTenant().setHouseholdSize(Integer.valueOf(fieldSet.readString("householdSize")));
    agentData.getTenant().setActualSalary(new BigDecimal(fieldSet.readString("actualSalary")));
    return agentData;
  }
}
