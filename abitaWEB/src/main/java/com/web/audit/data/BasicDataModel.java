/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.audit.data;

import com.web.audit.data.OptionalParameter;

import java.io.Serializable;
import java.util.List;


/**
 * Basic model class with shared attributes.
 * @author
 */
public class BasicDataModel implements Serializable {

	/** serialVersionUID */
    private static final long serialVersionUID = 3064455642868620206L;

    /** Parametres optionnels */
	private List<OptionalParameter> optionalParameters;

	/** Identifiant de l'utilisateur */
	private String idUser;

	/** Identifiant de l'application */
	private String applicationId;

	/**
	 *
	 * @return optional parameters
	 */
	public List<OptionalParameter> getOptionalParameters() {
		return optionalParameters;
	}

	/**
	 *
	 * @param optionalParameters optional parameters to set
	 */
	public void setOptionalParameters(List<OptionalParameter> optionalParameters) {
		this.optionalParameters = optionalParameters;
	}

	/**
	 * @return the idUser
	 */
	public String getIdUser() {
		return idUser;
	}

	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

    /**
     * @return the applicationId
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

}
