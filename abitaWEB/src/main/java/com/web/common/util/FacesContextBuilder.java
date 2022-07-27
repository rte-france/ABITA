/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.common.util;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * FacesContextBuilder : classe à utiliser dans un filter, permet la récupération de l'objet FacesContext
 * dans un filter.
 * Voir
 * 		http://ocpsoft.com/java/jsf-java/jsf-20-extension-development-accessing-facescontext-in-a-filter/
 *
 * @author
 */
public class FacesContextBuilder {

    /**
     * @param request HTTP request
     * @param response HTTP response
     * @return faces context
     */
    public FacesContext getFacesContext(final ServletRequest request, final ServletResponse response) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            return facesContext;
        }

        FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder
                .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder
                .getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

        ServletContext servletContext = ((HttpServletRequest) request).getSession().getServletContext();
        facesContext = contextFactory.getFacesContext(servletContext, request, response, lifecycle);
        AbstractInnerFacesContext.setFacesContextAsCurrentInstance(facesContext);
        if (null == facesContext.getViewRoot()) {
            facesContext.setViewRoot(new UIViewRoot());
        }

        return facesContext;
    }

    /**
     * Inner faces context class
     * @author
     */
    private abstract static class AbstractInnerFacesContext extends FacesContext {
        /**
         * @param facesContext faces context to set
         */
        protected static void setFacesContextAsCurrentInstance(final FacesContext facesContext) {
            FacesContext.setCurrentInstance(facesContext);
        }
    }
}
