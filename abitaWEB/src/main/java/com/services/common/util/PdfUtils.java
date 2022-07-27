/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.common.util;


import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.jfree.chart.JFreeChart;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;


/**
 * Pdf util methods for iText
 * @author
 */
public final class PdfUtils {

	/** STANDARD_DOC_MARGIN */
	private static final float STANDARD_DOC_MARGIN = 20.0F;

	/**
	 * Private constructor
	 */
	private PdfUtils() {
		super();
	}

    /**
     * builds a standard row cell
     * @param content row text content
     * @param borderColor border color
     * @return standard row cell
     */
    public static PdfPCell buildRowCell(final String content, final BaseColor borderColor) {
        final PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorderColor(borderColor);
        return cell;
    }

    /**
     * @param document pdf document
     * @return real document height, minus margins
     */
    public static int getRealDocumentHeight(Document document) {
    	return (int) (document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin());
    }

    /**
     * @param document pdf document
     * @return real document width, minus margins
     */
    public static int getRealDocumentWidth(Document document) {
    	return (int) (document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin());
    }

    /**
     * Sets up document margins, document orientation, opens it and adds a new page
     * @param document document to set up
     * @param landscapeOrientation true if landscape orientation, false otherwise
     */
    public static void setUpAndOpenDocument(Document document, boolean landscapeOrientation) {
        document.setMargins(STANDARD_DOC_MARGIN, STANDARD_DOC_MARGIN, STANDARD_DOC_MARGIN, STANDARD_DOC_MARGIN);
        if (landscapeOrientation) {
        	Rectangle landscape = PageSize.A4.rotate();
        	document.setPageSize(landscape);
        } else {
        	document.setPageSize(PageSize.A4);
        }
        document.open();
        document.newPage();
    }

    /**
     * Draws a chart into a buffered image and adds it to PDF document
     * @param chart the chart
     * @param width chart width
     * @param height chart height
     * @param document document
     * @throws IOException I/O exception
     * @throws DocumentException document exception
     */
    public static void drawChartAndAddIt(JFreeChart chart, int width, int height, Document document) throws IOException, DocumentException {
    	BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = img.createGraphics();
        chart.draw(graphics, new Rectangle2D.Double(0, 0, width, height));
        Image pdfImage = Image.getInstance(img, null);
        document.add(pdfImage);
    }
}
