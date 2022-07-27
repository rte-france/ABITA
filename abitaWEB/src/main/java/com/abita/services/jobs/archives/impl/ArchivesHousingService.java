/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.services.jobs.archives.impl;

import com.abita.dao.housing.entity.HousingEntity;
import com.abita.dao.housing.IHousingDAO;
import com.abita.web.shared.ConstantsWEB;
import com.abita.web.shared.Month;
import com.dao.common.exception.GenericDAOException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Classe d'implémentation pour la génération de l'archive des Logements
 *
 * @author
 */
public class ArchivesHousingService extends AbstractArchivesServiceImpl {

	/** Resource Bundle. */
	private static final String BUNDLE_NAME = "specificApplicationProperties";

	/** The resource bundle for the messages */
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  /**
   * Le DAO des Logements
   */
  private IHousingDAO housingDAO;

  @Override
  protected String defineFileName(int month, int year) {
    return ConstantsWEB.ARCHIVES_HOUSING_FILE + Month.getLabelByValue(month) + Integer.toString(year) + ConstantsWEB.ARCHIVES_EXTENSION_FILE;
  }

  @Override
  protected String defineSheetName() {
    return ConstantsWEB.ARCHIVES_HOUSING_SHEET;
  }

  @Override
  protected List<String> buildHeader() {
    List<String> lst = new ArrayList<String>();
    lst.add("Référence du logement");
    lst.add("Adresse");
    lst.add("Code Postal");
    lst.add("Commune");
    lst.add("Date d'entrée");
    lst.add("Date de sortie");
    lst.add("Motif de sortie");
    lst.add("Commentaire général");
    lst.add("Surface réelle");
    lst.add("Surface corrigée");
    lst.add("Propriété RTE");
    lst.add("Jardin");
    lst.add("Garage");
    lst.add("Statut logement");
    lst.add("Nombre de pièces");
    lst.add("Nature");
    lst.add("Noyau dur");
    lst.add("Année de construction");
    lst.add("Gestionnaire du logement");
    lst.add("Catégorie du local");
    lst.add("Agence");
    lst.add("Site");
    lst.add("Date de dernière maintenance DAAF");
    lst.add("Assainissement");
    lst.add("Type de chauffage");
    lst.add("Climatiseur");
    lst.add("OI Logement");
    lst.add("Code fournisseur");
    lst.add("Cuisine équipée");
    lst.add("Date cuisine");
    lst.add("Salle de bain");
    lst.add("Date salle de bain");
    lst.add("Gaz");
    lst.add("Date Gaz");
    lst.add("Anomalie Gaz");
    lst.add("DPE");
    lst.add("Date DPE");
    lst.add("Elect");
    lst.add("Date Elect");
    lst.add("Anomalie Elect");
    lst.add("Amiante");
    lst.add("Date Amiante");
    lst.add("Présence Amiante");
    lst.add("Plomb");
    lst.add("Date Plomb");
    lst.add("Anomalie Plomb");
    lst.add("Termites");
    lst.add("Date Termites");
    lst.add("Anomalie Termites");
    lst.add("ERP");
    lst.add("Date ERP");
    lst.add("Diagnostic Carrez");
    lst.add("Date Carrez");
    lst.add("M² (nombre)");
    lst.add("Avenir du logement");
    lst.add("Année");
    lst.add("Prix");
    lst.add("Date de la dernière visite");
    lst.add("Assainissment aux normes");
    lst.add("Façade");
    lst.add("Couverture");
    lst.add("Isolation");
    lst.add("Murs");
    lst.add("Menuiserie huisserie");
    lst.add("Finitions intérieurs");
    lst.add("VMC");
    lst.add("Plomberie / sanitaire");
    lst.add("Etiquette énergétique (DPE)");
    lst.add("Emission de GES");
    lst.add("Vente programmée en");
    lst.add("Démolition programmée en");
    lst.add("Coût de démolition");
    lst.add("Rénovation prévue en");
    lst.add("coût de la rénovation");



    return Collections.unmodifiableList(lst);
  }

  @Override
  protected void buildContent(HSSFSheet sheet) throws GenericDAOException {
    List<HousingEntity> lstHousing = housingDAO.find();

    int numberLine = ConstantsWEB.FIRST_LINE_ARCHIVES_CONTENT;
    SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWEB.PATTERN_DATE_MM_DD_YYYY);

    for (HousingEntity housing : lstHousing) {
      HSSFRow row = sheet.createRow(numberLine++);

      int numberCol = 0;

      // Référence logement
      row.createCell(numberCol++).setCellValue(housing.getReference());

      // Adresse, CP et ville
      row.createCell(numberCol++).setCellValue(housing.getAddress());
      row.createCell(numberCol++).setCellValue(housing.getPostalCode());
      row.createCell(numberCol++).setCellValue(housing.getCity());

      // Date d'entrée
      row.createCell(numberCol++).setCellValue(sdf.format(housing.getRegisterDate()));

      // Date de sortie
      if (housing.getUnregisterDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getUnregisterDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Motif de sortie
      if (housing.getReasonForExit() != null) {
        String reasonForExit = BUNDLE.getString(housing.getReasonForExit().getKey());
        row.createCell(numberCol++).setCellValue(reasonForExit);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Commentaire général
      row.createCell(numberCol++).setCellValue(housing.getGeneralComment());

      // Les surfaces
      row.createCell(numberCol++).setCellValue(housing.getRealSurfaceArea().doubleValue());
      row.createCell(numberCol++).setCellValue(housing.getRevisedSurfaceArea().doubleValue());

      // Propriété
      if (housing.isProperty()) {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      }

      // Jardin
      if (housing.isGardenAvailable()) {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      }

      // Garage
      if (housing.isGarageAvailable()) {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      }

      // Statut logement
      row.createCell(numberCol++).setCellValue(housing.getHousingStatus().getLabel());

      // Nombre de pièces
      row.createCell(numberCol++).setCellValue(housing.getRoomCount());

      // Nature
      if (housing.getHousingNature() != null) {
        row.createCell(numberCol++).setCellValue(housing.getHousingNature().getLabel());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Noyau dur
      if (housing.isCore()) {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      }

      // Année de construction
      row.createCell(numberCol++).setCellValue(housing.getYear());

      // Gestionnaire du logement
      String fullName = housing.getThirdpartyContractManager().getLastName() + " " + housing.getThirdpartyContractManager().getFirstName();
      row.createCell(numberCol++).setCellValue(fullName);

      // Catégorie local
      row.createCell(numberCol++).setCellValue(housing.getRoomCategory().getLabel());

      // Agence
      row.createCell(numberCol++).setCellValue(housing.getAgency().getName());

      // Site
      if (housing.getRattachmentSite() != null) {
        row.createCell(numberCol++).setCellValue(housing.getRattachmentSite().getName());
      } else {
        row.createCell(numberCol++).setCellValue("");
        }

      // Date de dernière maintenance DAAF
      if (housing.getLastDaafDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getLastDaafDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
        }

      // Assainissement
      if (housing.getSanitation() != null) {
      row.createCell(numberCol++).setCellValue(housing.getSanitation().getLabel());
      } else {
        row.createCell(numberCol++).setCellValue("");
        }
      // Type de chauffage
      if (housing.getHeatingType() != null) {
      row.createCell(numberCol++).setCellValue(housing.getHeatingType().getLabel());
      } else {
        row.createCell(numberCol++).setCellValue("");
        }

      // Climatiseur
      if (housing.getAirconditioner() != null) {
      row.createCell(numberCol++).setCellValue(housing.getAirconditioner().getLabel());
      } else {
        row.createCell(numberCol++).setCellValue("");
        }

      // OI Logement
      if (housing.getOiHousing() != null) {
      row.createCell(numberCol++).setCellValue(housing.getOiHousing());
      } else {
        row.createCell(numberCol++).setCellValue("");
        }

      // EOTP
      if (housing.getEotp() != null) {
      row.createCell(numberCol++).setCellValue(housing.getEotp());
      } else {
        row.createCell(numberCol++).setCellValue("");
        }

      // Cuisine Equipée
      if (housing.isEquippedKitchen()== null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isEquippedKitchen()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
        }
        else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Date Cuisine
      if (housing.getKitchenDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getKitchenDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Salle de Bain
      if (housing.isBathroom() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isBathroom()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Date Salle de Bain
      if (housing.getBathroomDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getBathroomDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // DPE
      if (housing.isDpe() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isDpe()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Date DPE
      if (housing.getDpeDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getDpeDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Gaz
      if (housing.isGas() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isGas()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Date Gaz
      if (housing.getGasDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getGasDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Anomalie Gaz
      if (housing.isGasAno() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isGasAno()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Elect
      if (housing.isElect() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isElect()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Date Elect
      if (housing.getElectDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getElectDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Anomalie Elect
      if (housing.isElectAno() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isElectAno()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Amiante
      if (housing.isAsbestos() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isAsbestos()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Date Amiante
      if (housing.getAsbestosDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getAsbestosDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Anomalie Amiante
      if (housing.isAsbestosAno() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isAsbestosAno()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Plomb
      if (housing.isLead() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isLead()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Date Amiante
      if (housing.getLeadDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getLeadDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Anomalie Plomb
      if (housing.isLeadAno() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isLeadAno()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Termites
      if (housing.isTermite() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isTermite()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Date Termites
      if (housing.getTermiteDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getTermiteDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Anomalie Termites
      if (housing.isTermiteAno() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isTermiteAno()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // ERNMT
      if (housing.isErnmt() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isErnmt()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Date ERNMT
      if (housing.getErnmtDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getErnmtDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // Diagnostic Carrez
      if (housing.isCarrez() == null) {
        row.createCell(numberCol++).setCellValue("");
      } else if (!housing.isCarrez()){
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_NO);
      } else {
        row.createCell(numberCol++).setCellValue(ConstantsWEB.ANSWER_YES);
      }

      // Date Diagnostic Carrez
      if (housing.getCarrezDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getCarrezDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      // M² (Nombre)
      if (housing.getSquareMeter() != null) {
      row.createCell(numberCol++).setCellValue(housing.getSquareMeter());
      } else {
      row.createCell(numberCol++).setCellValue("");
      }

      //Avenir du logement
      if (housing.getFuturOfHousing() != null) {
        String futurOfHousing = BUNDLE.getString(housing.getFuturOfHousing().getKey());
        row.createCell(numberCol++).setCellValue(futurOfHousing);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //Année
      row.createCell(numberCol++).setCellValue(housing.getDateFuturOfHousing());

     //prix
      if(housing.getFuturOfHousingPrice() != null){
        row.createCell(numberCol++).setCellValue(housing.getFuturOfHousingPrice().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //date de la dernière visite

      if (housing.getLastVisitDate() != null) {
        row.createCell(numberCol++).setCellValue(sdf.format(housing.getLastVisitDate()));
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //Assainissment aux normes
      if (housing.getSanitationstd() != null) {
        String sanitationstd = BUNDLE.getString(housing.getSanitationstd().getKey());
        row.createCell(numberCol++).setCellValue(sanitationstd);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //Façade
      if (housing.getFacade() != null) {
        String facade = BUNDLE.getString(housing.getFacade().getKey());
        row.createCell(numberCol++).setCellValue(facade);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //Couverture
      if (housing.getCouverture() != null) {
        String couverture = BUNDLE.getString(housing.getCouverture().getKey());
        row.createCell(numberCol++).setCellValue(couverture);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }



      //Isolation
      if (housing.getInsulation() != null) {
        String isolation = BUNDLE.getString(housing.getInsulation().getKey());
        row.createCell(numberCol++).setCellValue(isolation);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //Murs
      if (housing.getWalls() != null) {
        String walls = BUNDLE.getString(housing.getWalls().getKey());
        row.createCell(numberCol++).setCellValue(walls);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //Menuiserie huisserie
      if (housing.getJoineryFrame() != null) {
        String joineryFrame = BUNDLE.getString(housing.getJoineryFrame().getKey());
        row.createCell(numberCol++).setCellValue(joineryFrame);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //Finitions intérieurs
      if (housing.getFinitions() != null) {
        String finition = BUNDLE.getString(housing.getFinitions().getKey());
        row.createCell(numberCol++).setCellValue(finition);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //VMC
      if (housing.getVmc() != null) {
        String vmc = BUNDLE.getString(housing.getVmc().getKey());
        row.createCell(numberCol++).setCellValue(vmc);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //Plomberie / sanitaire
      if (housing.getPlomberie() != null) {
        String plomberie = BUNDLE.getString(housing.getPlomberie().getKey());
        row.createCell(numberCol++).setCellValue(plomberie);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //Etiquette énergétique (DPE)
      if (housing.getEnergyLabel() != null) {
        String energieLabel = BUNDLE.getString(housing.getEnergyLabel().getKey());
        row.createCell(numberCol++).setCellValue(energieLabel);
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //Emission GES

      if (housing.getEmissionGes() != null) {
        row.createCell(numberCol++).setCellValue(housing.getEmissionGes().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }


      //date vente: vente programmée en

      if (housing.getDateProgrammedSale() != null) {
        row.createCell(numberCol++).setCellValue(housing.getDateProgrammedSale());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //date démolition: démolition programmer en

      if (housing.getDateDemolition() != null) {
        row.createCell(numberCol++).setCellValue(housing.getDateDemolition());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }

      //coût démolition

      if (housing.getCoutDemolition() != null) {
        row.createCell(numberCol++).setCellValue(housing.getCoutDemolition().doubleValue());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }


      //date rénovation: rénovation prévue en

      if (housing.getDateRenovation() != null) {
        row.createCell(numberCol++).setCellValue(housing.getDateRenovation());
      } else {
        row.createCell(numberCol++).setCellValue("");
      }


      //coût de la rénovation

      if (housing.getCoutRenovation() != null) {
        row.createCell(numberCol).setCellValue(housing.getCoutRenovation().doubleValue()); // pas la peine d'appeler numCol++ car c'est la dernière instruction
      } else {
        row.createCell(numberCol).setCellValue(""); // pas la peine d'appeler numCol++ car c'est la dernière instruction
      }

    }
  }

  /**
   * @param housingDAO the housingDAO to set
   */
  public void setHousingDAO(IHousingDAO housingDAO) {
    this.housingDAO = housingDAO;
  }

}
