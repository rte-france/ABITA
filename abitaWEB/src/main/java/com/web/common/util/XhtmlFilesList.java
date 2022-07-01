package com.web.common.util;

import com.web.common.util.XhtmlFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * A iterator on xhtml resource.
 * @author
 *
 */
public class XhtmlFilesList implements Iterator<File> {

	/**
	 * listFiles
	 */
	private List<File> listFiles = new ArrayList<File>();

	/**
	 *
	 * Construct XhtmlFilesList.
	 */
	public XhtmlFilesList() {
		WebApplicationContext requiredWebApplicationContext = FacesContextUtils
				.getRequiredWebApplicationContext(FacesContext.getCurrentInstance());
		ServletContext servletContext = requiredWebApplicationContext.getServletContext();
		String realPath = servletContext.getRealPath("");
		File file = new File(realPath);
		File[] listFiles2 = file.listFiles(new com.web.common.util.XhtmlFilter());
		listFiles.addAll(Arrays.asList(listFiles2));
	}

	@Override
	public boolean hasNext() {
		return listFiles.size() > 0;
	}

	@Override
	public File next() {
		File f = listFiles.get(0);
		listFiles.remove(0);

		if (f.isDirectory()) {
			listFiles.addAll(Arrays.asList(f.listFiles(new XhtmlFilter())));
		}
		return f;
	}

	@Override
	public void remove() {
		//
	}

}
