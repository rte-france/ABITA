/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.servlets;

import com.services.common.constants.MimeTypesConstants;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Permet de generer les flux xml setting et data pour amcharts.
 */
public class GenerateAmchartsXmlServlet extends HttpServlet {

    /** serialVersionUID */
	private static final long serialVersionUID = -2065904224830682423L;

	/** Clé encoding */
    public static final String ENCODING = "encoding";

    /** Clé données du graphique */
    public static final String DATA = "data";

    /** Clé configuration du graphique */
    public static final String SETTINGS = "settings";

    /** Encoding souhaité */
    private String encoding;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.encoding = config.getInitParameter(ENCODING);
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding(encoding);
        response.setContentType(MimeTypesConstants.CONTENT_TYPE_XML
        	+ String.format(MimeTypesConstants.CHARSET_FOR_CONTENT, encoding));
        PrintWriter out = response.getWriter();

        String dataCollection = request.getParameter("collection");
        String type = request.getParameter("type");

        if (DATA.equals(type)) {
            String dataFile = (String) request.getSession().getAttribute(DATA + dataCollection);
            out.println(dataFile);
        } else if (SETTINGS.equals(type)) {
            String settingsFile = (String) request.getSession().getAttribute(SETTINGS + dataCollection);
            out.println(settingsFile);
        }

    }

}
