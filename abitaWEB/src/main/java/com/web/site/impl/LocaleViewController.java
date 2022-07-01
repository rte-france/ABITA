package com.web.site.impl;

import com.services.common.constants.Constants;
import com.services.paramservice.ParameterService;
import com.web.common.util.RequestParameterHolder;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Locale;

/**
 * @author
 * local controller
 */
public class LocaleViewController implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 6308512030878433379L;

    /** current locale */
    private Locale currentLocale;

    /** locale id */
    private String localeId;

    /** parameter service */
    @SuppressWarnings("unused")
    private ParameterService parameterService;

    /**
     * Construct LocaleViewController.
     */
    public LocaleViewController() {
        FacesContext.getCurrentInstance().getApplication().setDefaultLocale(Locale.FRENCH);
        currentLocale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
    }
    /**
     * Injection of parameter Service
     * @param parameterService parameter service
     */
    public void setParameterService(ParameterService parameterService) {
        this.parameterService = parameterService;
    }
    /**
     * setter of locale
     * @param localeId locale id
     */
    public void setLocaleId(String localeId) {
        this.localeId = localeId;
    }
    /**
     * getter of local switch enable.
     * @return true if locale switch is enabled, false otherwise
     */
    public boolean getLocaleSwitchEnabled() {
        Boolean b = Boolean.FALSE;
        return b.booleanValue();
    }
    /**
     * getter if locale id.
     * @return locale id
     */
    public String getLocaleId() {
        return localeId;
    }
    /**
     * setter of current locale.
     * @param currentLocale current locale
     */
    public void setCurrentLocale(Locale currentLocale) {
        this.currentLocale = currentLocale;
    }
    /**
     * getter of current locale.
     * @return current locale
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }
    /**
     * Action to change locale.
     * @return process change locale outcome
     */
    public String processChangeLocale() {

        String localeIdentifier = (String) RequestParameterHolder.getRequestParameter2("localeId");

        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIViewRoot view = facesContext.getViewRoot();

        currentLocale = new Locale(localeIdentifier);
        view.setLocale(currentLocale);
        facesContext.getExternalContext().getSessionMap().put(Constants.CURRENT_LOCALE, currentLocale);
        return "main";
    }
}
