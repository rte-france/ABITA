package com.web.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.services.common.util.StringUtils;
import com.web.common.data.ExportSheetData;

/**
 * Méthodes utilitaires pour les exports
 * @author
 */
public final class ExportUtils {

	/**
	 * exporte les données dans un fichier excel
	 * @param exportSheetsData données à exporter
	 * @return {@link Workbook}
	 * @throws IOException I/O exception
	 */
	public static Workbook export(List<ExportSheetData> exportSheetsData) throws IOException {

		Workbook wb = new XSSFWorkbook();
		Sheet sheet = null;
		Font headerFont = null;
		CellStyle headerStyle = null;
		Row row = null;
		Cell cell = null;
		RichTextString value = null;
		int idColumn;
		for (ExportSheetData exportSheetData : exportSheetsData) {
			sheet = wb.createSheet(exportSheetData.getTitle());

			idColumn = 0;

			// En tête
			headerFont = wb.createFont();
			headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
			headerStyle = wb.createCellStyle();
			headerStyle.setFont(headerFont);
			row = sheet.createRow(0);
			value = null;
			for (String data : exportSheetData.getHeaders()) {
				cell = row.createCell(idColumn);
				value = new XSSFRichTextString(data);
				cell.setCellValue(value);
				cell.setCellStyle(headerStyle);
				idColumn++;
			}

			// Lignes
			int nbLines = 1;
			for (List<String> line : exportSheetData.getLines()) {
				idColumn = 0;
				row = sheet.createRow(nbLines);
				for (String data : line) {
					if (!StringUtils.isEmptyOrBlank(data)) {
						value = new XSSFRichTextString(data);
						row.createCell(idColumn).setCellValue(value);
					}
					idColumn++;
				}
				nbLines++;
			}
		}

		return wb;
	}

	/**
	 * Exporte les données dans un fichier existant
	 * @param exportSheetsData liste des onglets
	 * @param inputStream fichier d'entrée
	 * @throws IOException I/O exception
	 * @throws InvalidFormatException invalid format exception
	 * @return {@link Workbook}
	 */
	public static Workbook export(List<ExportSheetData> exportSheetsData, InputStream inputStream)
			throws IOException, InvalidFormatException {

		Workbook wb = WorkbookFactory.create(inputStream);
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		int idSheet = 0;
		int idColumn = 0;
		for (ExportSheetData exportSheetData : exportSheetsData) {
			sheet = wb.getSheetAt(idSheet);

			idColumn = 0;

			// En tête
			row = sheet.getRow(0);
			cell = null;
			for (String data : exportSheetData.getHeaders()) {
				if (data != null) {
					cell = row.getCell(idColumn);
					cell.setCellValue(data);
					idColumn++;
				}
			}

			// Lignes
			int nbLines = 1;
			for (List<String> line : exportSheetData.getLines()) {
				idColumn = 0;
				row = sheet.getRow(nbLines);
				if (row == null) {
					row = sheet.createRow(nbLines);
				}
				for (String data : line) {
					if (!StringUtils.isEmptyOrBlank(data)) {
						cell = row.getCell(idColumn);
						if (cell == null) {
							cell = row.createCell(idColumn);
						}
						cell.setCellValue(data);
					}
					idColumn++;
				}
				nbLines++;
			}
			idSheet++;
		}

		return wb;
	}

	/**
	 * Constructeur
	 */
	private ExportUtils() {
	}

}
