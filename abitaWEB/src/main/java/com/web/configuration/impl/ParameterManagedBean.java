package com.web.configuration.impl;

import com.dto.parameter.DomainDTO;
import com.services.common.constants.Constants;
import com.services.common.util.MessageSupport;
import com.services.common.util.SafetyUtils;
import com.services.paramservice.ParameterService;
import com.services.paramservice.exception.ParameterServiceException;
import com.web.common.constants.AccessConstants;
import com.web.configuration.data.MapParameterHolderFactory;
import com.web.configuration.data.ParameterHolder;
import com.web.configuration.impl.TabViewFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.component.tabview.TabView;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Managed bean of panel in /page/param/parameter.xhtml.
 * @author
 */
public class ParameterManagedBean implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -1221904061039263240L;

	/**
	 * The logger
	 */
	private static Log log = LogFactory.getLog(ParameterManagedBean.class);

	/**
	 * The parameter service
	 */
	private ParameterService parameterService;
	/**
	 * the tabbed pane
	 */
	private TabView panelTabbedPane;
	/**
	 * Tips to pass in EL syntax two STRING parameter to function.
	 */
	private Map<String, Map<String, ParameterHolder<?>>> parameterHolders = new HashMap<String, Map<String, ParameterHolder<?>>>();

	/** save parameters outcome */
	private String saveOutcome;

	/**
	 * Default contrutor.
	 */
	public ParameterManagedBean() {
		//
	}

	/**
	 * Injection of parameter Service.
	 * @param parameterService the parameter Service to set
	 */
	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	/**
	 * @return the panelTabbedPane
	 */
	public TabView getPanelTabbedPane() {
		return panelTabbedPane;
	}

	/**
	 * @param panelTabbedPane the panelTabbedPane to set
	 */
	public void setPanelTabbedPane(TabView panelTabbedPane) {
		this.panelTabbedPane = panelTabbedPane;
	}

	/**
	 * init managed bean method.
	 */
	@PostConstruct
	public void init() {
		try {
			List<DomainDTO> domains = parameterService.getDomainList();
			parameterHolders = MapParameterHolderFactory.create(domains);
			panelTabbedPane = TabViewFactory.createTabView(FacesContext.getCurrentInstance(), domains);
		} catch (ParameterServiceException parameterServiceException) {
			log.error(parameterServiceException);
			MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, AccessConstants.ACCESS_BUNDLE,
					AccessConstants.ERROR_FATAL);
		}
	}

	/**
	 * getter
	 * @return Map<String,Map<String,ParameterHolder>>
	 */
	public Map<String, Map<String, ParameterHolder<?>>> getParameterHolders() {
		return parameterHolders;
	}

	/**
	 * Save all parameters.
	 * @param event action event
	 */
	public void processSaveParameters(ActionEvent event) {
		Map<String, ParameterHolder<?>> dPMap;
		ParameterHolder<?> ph;
		for (String dName : SafetyUtils.emptyIfNull(parameterHolders.keySet())) {
			dPMap = parameterHolders.get(dName);
			for (String pName : SafetyUtils.emptyIfNull(dPMap.keySet())) {
				ph = dPMap.get(pName);
				if (ph.isModified()) {
					try {
						parameterService.setParameterValue(dName, pName, ph.getModifiedValue());
					} catch (ParameterServiceException parameterServiceException) {
						log.error("unexpected error", parameterServiceException);
						MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, "unexpected_exception");
						saveOutcome = Constants.FAILURE_OUTCOME;
					}
				}
			}
		}
		saveOutcome = "save";
	}

	/**
	 * @return save outcome
	 */
	public String getSaveOutcome() {
		return saveOutcome;
	}
}
