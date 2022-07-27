/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.impl;

import com.services.common.constants.HeadersConstants;
import com.services.common.constants.MimeTypesConstants;
import com.web.common.exception.ExportFileException;
import com.web.common.impl.AbstractExportFileController;
import org.apache.poi.ss.usermodel.Workbook;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * Export de fichier specifique WorkBook
 * @author
 */
public abstract class AbstractExportWorkBookController extends AbstractExportFileController implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 2574705805541563219L;

	/** Workbook à exporter */
	protected Workbook workBook;

	/** Nom de fichier */
	protected String workBookName;

	@Override
	public void sendResponseFile() throws ExportFileException {

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.setContentType(MimeTypesConstants.CONTENT_TYPE_EXCEL_2007);
		response.addHeader(HeadersConstants.CONTENT_DISPOSITION_HEADER,
				String.format(HeadersConstants.CONTENT_DISPOSITION_AS_ATTACHEMENT, workBookName));

		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException ioException) {
			throw new ExportFileException(ioException);
		} finally {
			context.responseComplete();
		}
	}

}
