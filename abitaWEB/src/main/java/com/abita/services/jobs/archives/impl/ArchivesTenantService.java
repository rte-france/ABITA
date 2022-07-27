/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.archives.impl;

import com.abita.dao.tenant.entity.TenantEntity;
import com.abita.dao.tenant.ITenantDAO;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.Month;
import com.dao.common.exception.GenericDAOException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe d'implémentation pour la génération de l'archive des Occupants
 *
 * @author
 */
public class ArchivesTenantService extends AbstractArchivesServiceImpl {

  /**
  * Le DAO des Occupants
  */
  private ITenantDAO tenantDAO;

  @Override
  protected String defineFileName(int month, int year) {
    return ConstantsWEB.ARCHIVES_TENANT_FILE + Month.getLabelByValue(month) + Integer.toString(year) + ConstantsWEB.ARCHIVES_EXTENSION_FILE;
  }

  @Override
  protected String defineSheetName() {
    return ConstantsWEB.ARCHIVES_TENANT_SHEET;
  }

  @Override
  protected List<String> buildHeader() {
    List<String> lst = new ArrayList<String>();
    lst.add("Référence occupant");
    lst.add("Nom");
    lst.add("Prénom");
    lst.add("Cadre");
    lst.add("Cadre à octobre N-1");
    lst.add("Type d'occupant");
    lst.add("Nombre de personne constituant le foyer");
    lst.add("Nombre de personne constituant le foyer à octobre N-1");
    lst.add("Salaire brut");
    lst.add("Salaire brut à octobre N-1");
    lst.add("Téléphone");
    lst.add("Email");
    return Collections.unmodifiableList(lst);
  }

  @Override
  protected void buildContent(HSSFSheet sheet) throws GenericDAOException {
    List<TenantEntity> lstTenant = tenantDAO.find();

    int numberLine = ConstantsWEB.FIRST_LINE_ARCHIVES_CONTENT;
    for (TenantEntity tenant : lstTenant) {
      HSSFRow row = sheet.createRow(numberLine++);

      int numberCol = 0;

      // Référence occupant
      row.createCell(numberCol++).setCellValue(tenant.getReference());

      // Nom
      row.createCell(numberCol++).setCellValue(tenant.getLastName());

      // Prénom
      row.createCell(numberCol++).setCellValue(tenant.getFirstName());

      // Cadre
      if (tenant.getManagerial() != null) {
        if (tenant.getManagerial()) {
          row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
        } else {
          row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
        }
      }

      // Cadre à octobre N-1
      if (tenant.getManagerialLastYear() != null) {
        if (tenant.getManagerialLastYear()) {
          row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
        } else {
          row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
        }
      }

      // Type d'occupant
      row.createCell(numberCol++).setCellValue(tenant.getTypeTenant().getLabel());

      // Nombre de personne constituant le foyer
      if (tenant.getHouseholdSize() != null) {
        row.createCell(numberCol++).setCellValue(tenant.getHouseholdSize());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Nombre de personne constituant le foyer à octobre N-1
      if (tenant.getHouseholdSizeLastYear() != null) {
        row.createCell(numberCol++).setCellValue(tenant.getHouseholdSizeLastYear());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Salaire brut
      if (tenant.getActualSalary() != null) {
        row.createCell(numberCol++).setCellValue(tenant.getActualSalary().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Salaire brut à octobre N-1
      if (tenant.getReferenceGrossSalary() != null) {
        row.createCell(numberCol++).setCellValue(tenant.getReferenceGrossSalary().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Téléphone
      row.createCell(numberCol++).setCellValue(tenant.getPhone());

      // Email
      row.createCell(numberCol++).setCellValue(tenant.getMailAddress());
    }
  }

  /**
   * @param tenantDAO the tenantDAO to set
   */
  public void setTenantDAO(ITenantDAO tenantDAO) {
    this.tenantDAO = tenantDAO;
  }

}
