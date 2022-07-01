package com.services.common.util;

import com.services.common.exception.XMLException;
import com.services.common.util.ClasspathResolver;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * XML Util to read or write io.
 * @author
 *
 */
public final class XMLUtil {

	/**
	 * Private constructor
	 */
	private XMLUtil() {
		super();
	}

	/**
	 * Reads an XML file and creates the root element
	 * @param url the url of the xml file to be read
	 * @return the root element
	 * @throws XMLException exception on creating root element
	 */
	public static Element create(URL url) throws XMLException {
		if (url == null) {
			throw new IllegalArgumentException("url is a 'nil' object");
		}
		return createDocument(url, false).getRootElement();
	}

	/**
	 * Reads an XML file and creates the root element
	 * @param validate validate the xml document
	 * @param url the url of the xml file to be read
	 * @return the root element
	 * @throws XMLException exception on creating root element
	 */
	public static Element create(URL url, boolean validate) throws XMLException {
		if (url == null) {
			throw new IllegalArgumentException("url is a 'nil' object");
		}
		return createDocument(url, validate).getRootElement();
	}

	/**
	 * Reads an InputStream and creates the root element
	 * @param is input stream
	 * @return the root element
	 * @throws XMLException exception on creating root element
	 */
	public static Element create(InputStream is) throws XMLException {
		return createDocument(is, false).getRootElement();
	}

	/**
	 * Reads an InputStream and creates the root element
	 * @param is input stream
	 * @param validate validate the xml document
	 * @return the root element
	 * @throws XMLException exception on creating root element
	 */
	public static Element create(InputStream is, boolean validate) throws XMLException {
		return createDocument(is, validate).getRootElement();
	}

	/**
	 * @param url XML url
	 * @param validate true to validate XML
	 * @return created XML document from url
	 * @throws XMLException XML exception
	 */
	private static Document createDocument(URL url, boolean validate) throws XMLException {
		SAXBuilder builder = null;
		Document document = null;
		try {
			builder = new SAXBuilder(validate);
			builder.setExpandEntities(true);
			if (validate) {
				builder.setEntityResolver(new ClasspathResolver());
			}
			document = builder.build(url.openStream());
		} catch (JDOMException jDomException) {
			throw new XMLException(jDomException);
		} catch (IOException ioException) {
			throw new XMLException(ioException);
		}
		return document;
	}

    /**
     * @param is XML input stream
     * @param validate true to validate XML
     * @return created XML document from url
     * @throws XMLException XML exception
     */
	private static Document createDocument(InputStream is, boolean validate) throws XMLException {
		SAXBuilder builder = null;
		Document document = null;
		try {
			builder = new SAXBuilder(validate);
			builder.setExpandEntities(true);
			if (validate) {
				builder.setEntityResolver(new ClasspathResolver());
			}
			document = builder.build(is);
		} catch (JDOMException jDomException) {
			throw new XMLException(jDomException);
		} catch (IOException ioException) {
			throw new XMLException(ioException);
		}
		return document;
	}

}
