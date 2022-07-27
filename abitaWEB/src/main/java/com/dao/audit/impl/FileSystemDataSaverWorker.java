/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.dao.audit.impl;

import static com.services.auditservice.constants.ConstantsActivityTracker.SYSTEM_PATH_DELIMITER;
import static com.services.common.util.SafetyUtils.close;
import static com.services.common.util.SafetyUtils.isNotEmpty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVWriter;

import com.dto.IndicatorsDTO;
import com.services.common.constants.DateTimeConstants;
import com.services.common.util.DateUtils;

/**
 * Component in charge of synchronously writing data to the disk.
 */
@Component("fileSystemDataSaverWorker")
public class FileSystemDataSaverWorker implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -1879299905801332299L;

	/** The logger */
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Directory where files will be stored Default value : java io temp dir
	 * Regular value : retrieved from metrology.properties
	 * CFR: bizarrement, il est impossible de donner le nom d'une propriété Spring
	 * en utilisant le "placeholderPrefix" sans laisser uniquement l'accolade fermante...
	 */
	@Value("${metrology.path}")
	protected String fileStoragePath = System.getProperty("java.io.tmpdir") + SYSTEM_PATH_DELIMITER;

	/** Delimiter for CSV files */
	private static final char CSV_DELIMITER_CHAR = ';';

	/** Encoding for CSV file */
	private static final String ENCODE = "UTF-8";

	/** Directory command */
	private static final String COMMAND_DIR = "/bin/sh";

	/** Access command */
	private static final String COMMAND_ACCESS = "-c";

	/** Command to give rights */
	private static final String COMMAND_RIGHTS = "chmod -R g+x ";

	/**
	 * Encoding BOM UTF8
	 */
	private static final String BOM_UTF8 = "\uFEFF";

	/**
	 * This method write to the disk the content of the list passed as
	 * parameters. The method create one file per type of data per day, i.e. 3
	 * files per day.
	 *
	 * @param applicationId The application ID used in file path.
	 * @param useAlternativePath path to save log
	 * @param indicators set of indicators
	 * @throws IOException
	 *             if there's a problem while accessing the files on the disk.
	 */
	public void work(String applicationId, IndicatorsDTO indicators, boolean useAlternativePath) throws IOException {

		writeToFileIfNotEmpty(applicationId, indicators.getDurations(), "durations", useAlternativePath);
		writeToFileIfNotEmpty(applicationId, indicators.getCounts(), "actions", useAlternativePath);
		writeToFileIfNotEmpty(applicationId, indicators.getUsers(), "users", useAlternativePath);
		writeToFileIfNotEmpty(applicationId, indicators.getAuthorized(), "authorized", useAlternativePath);
	}

	/**
	 * Write data to adapted file if not empty
	 * @param applicationId application id
	 * @param dataToWrite data to write to file
	 * @param fileSuffix file suffix
	 * @param useAlternativePath true to use alternative path if default path not usable
	 * @throws IOException I/O exception
	 */
	private void writeToFileIfNotEmpty(String applicationId, List<String[]> dataToWrite, String fileSuffix,
			boolean useAlternativePath) throws IOException {

		if (isNotEmpty(dataToWrite)) {
			boolean dataLoged = false;

			Writer w = null;
			OutputStreamWriter outwritter = null;
			FileOutputStream foutstrem = null;
			CSVWriter writer = null;
			String timeStamp = DateUtils.getDateFormat(new GregorianCalendar().getTime(),
					DateTimeConstants.TIME_STAMP_FILE_FORMAT);

			String path = fileStoragePath.concat(applicationId).concat(SYSTEM_PATH_DELIMITER);
			log.debug("Saving file to : " + path);
			giveRightsOnPathIfExists(path);

			try {
				log.debug("Starting saving data to disk...");
				log.debug("Saving " + fileSuffix + " file");

				File f = new File(path + applicationId + "-" + timeStamp + "-" + fileSuffix + ".csv");
				boolean existFile = f.exists();

				foutstrem = new FileOutputStream(f, true);
				outwritter = new OutputStreamWriter(foutstrem, ENCODE);
				w = new BufferedWriter(outwritter);

				if (!existFile) {
					w.write(BOM_UTF8);
				}

				writer = new CSVWriter(w, CSV_DELIMITER_CHAR);

				writer.writeAll(dataToWrite);
				dataToWrite.clear();
				dataLoged = true;

			} catch (FileNotFoundException fileNotFoundException) {
				// if specified directory / file not found, we write the logs in
				// the java temp dir, to do
				// so a recursive call must be done with the flag
				// useAlternativePath set to 'true'
				if (!useAlternativePath) {
					fileStoragePath = System.getProperty("java.io.tmpdir") + SYSTEM_PATH_DELIMITER;
					// recursive call for using updated path
					this.writeToFileIfNotEmpty(applicationId, dataToWrite, fileSuffix, true);
				} else {
					throw fileNotFoundException;
				}
			} finally {
				close(writer);
				close(w);
				close(outwritter);
				close(foutstrem);
			}

			if (dataLoged) {
				log.debug("Data has been saved to disk");
			}
		}
	}

	/**
	 * Give rights on created path/path to create
	 * @param path the path where rights are to be given
	 */
	private void giveRightsOnPathIfExists(String path) {
		boolean existDir = new File(path).mkdir();
		if (existDir) {
			//on ajoute le droit d'execution pour le groupe
			try {
				String[] args = { COMMAND_DIR, COMMAND_ACCESS, COMMAND_RIGHTS + path };
				Runtime.getRuntime().exec(args);
			} catch (IOException ioException) {
				log.error(ioException.getMessage());
				log.error("Exception encountered while launching the command");
			}
		}
	}

}
