package com.web.common.impl;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.services.common.constants.HeadersConstants;
import com.services.common.constants.MimeTypesConstants;
import com.web.common.exception.ExportFileException;
import com.web.common.impl.AbstractExportFileController;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Controlleur abstrait pour l'export de pdf
 * @author
 */
public abstract class AbstractExportPdfController extends AbstractExportFileController implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -6398751362270151550L;

	/** Le document pdf */
	protected Document document;

	/** Le contenu du fichier */
	private ByteArrayOutputStream byteArrayOutputStream;

	/**
	 * Constructeur.
	 * Initialise le document, le write et le tableau de données
	 *
	 * @throws DocumentException Si on tente d'affecter deux Writer au document
	 */
	public AbstractExportPdfController() throws DocumentException {
		byteArrayOutputStream = new ByteArrayOutputStream();
		document = new Document();
		PdfWriter.getInstance(document, byteArrayOutputStream);
	}

	/**
	 * {@inheritDoc}
	 * @see AbstractExportFileController#sendResponseFile()
	 */
	@Override
	public final void sendResponseFile() throws ExportFileException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.setContentType(MimeTypesConstants.CONTENT_TYPE_PDF);
		response.addHeader(HeadersConstants.CONTENT_DISPOSITION_HEADER,
				String.format(HeadersConstants.CONTENT_DISPOSITION_AS_ATTACHEMENT, getFileName()));

		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			response.setContentLength(byteArrayOutputStream.size());
			outputStream.write(byteArrayOutputStream.toByteArray());
			outputStream.flush();
			outputStream.close();
		} catch (IOException ioException) {
			throw new ExportFileException(ioException);
		} finally {
			context.responseComplete();
		}
	}

	/**
	 * Renvoie du nom du fichier à envoyer
	 *
	 * @return Le nom du fichier (avec l'extension)
	 */
	public abstract String getFileName();
}
