/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.ldapservice.impl;

import com.services.ldapservice.ServiceInfoNodePath;

/**
 * @author rahmounepat
 */

/**
 * @author
 *
 */
public class ServiceInfoNodePathImpl implements ServiceInfoNodePath {

    /** entity code */
    private String code;

    /** entity name */
    private String name;

    /** service info child */
    private ServiceInfoNodePath serviceInfoNodePathChild;

    /** Creates a new instance of ServiceInfoImpl */
    public ServiceInfoNodePathImpl() {
        //
    }

   /**
    *
    * Construct ServiceInfoNodePathImpl.
    * @param code entity code
    * @param name entity name
    */
    public ServiceInfoNodePathImpl(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * @param code entity code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return entity code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param name entity name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return entity name
     */
    public String getName() {
        return name;
    }

    /**
     * @return entity full name
     */
    public String getFullName() {
        ServiceInfoNodePath child = getServiceInfoNodePathChild();
        if (child == null) {
            return name;
        } else {
            StringBuffer sb = new StringBuffer(name);
            sb.append(",");
            sb.append(child.getFullName());
            return sb.toString();
        }
    }

    /**
     * @return entity last name
     */
    public String getLastName() {
        ServiceInfoNodePath sinp = this;
        while (sinp.getServiceInfoNodePathChild() != null) {
            sinp = sinp.getServiceInfoNodePathChild();
        }
        return sinp.getName();
    }

    /**
     * @param serviceInfoNodePathChild service info child
     */
    public void setServiceInfoNodePathChild(ServiceInfoNodePath serviceInfoNodePathChild) {
        this.serviceInfoNodePathChild = serviceInfoNodePathChild;
    }

    /**
     * @return service info child
     */
    public ServiceInfoNodePath getServiceInfoNodePathChild() {
        return serviceInfoNodePathChild;
    }
}
