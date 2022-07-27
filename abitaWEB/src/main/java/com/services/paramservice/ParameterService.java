/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.paramservice;

import com.dto.Group;
import com.dto.parameter.DomainDTO;
import com.services.common.exception.NotFoundException;
import com.services.paramservice.exception.ParameterServiceException;

import java.util.List;

/**
 * Interface du service de gestion des domaines et des paramètres associés
 *
 * @author
 * @author
 */
public interface ParameterService {

	/**
	 * Returns a parameter value
	 * @param domain the parameter domain
	 * @param param the parameter name
	 * @return the parameter value or null in case of failure
	 * @throws ParameterServiceException exception lors de la récupération du paramètre
	 */
	String getParameterValue(String domain, String param) throws ParameterServiceException;

	/**
	 * Sets a parameter value
	 * @param domain the parameter domain
	 * @param param the parameter name
	 * @param value the parameter value
	 * @throws ParameterServiceException exception lors de la valorisation du paramètre
	 */
	void setParameterValue(String domain, String param, String value) throws ParameterServiceException;

	/**
	 * Returns the domain list
	 * @return the domain list
	 * @throws ParameterServiceException exception lors de la récupération du domaine
	 */
	List<DomainDTO> getDomainList() throws ParameterServiceException;

	/**
	 * Returns a domain by name
	 * @param domain the domain name
	 * @return the domain
	 * @throws NotFoundException if the domain is not found
	 * @throws ParameterServiceException exception lors de la récupération du domaine
	 */
	DomainDTO getDomainByName(String domain) throws NotFoundException, ParameterServiceException;

    /**
     * Renvoie la liste des groupes qui sont automatiquement affectés aux nouveaux utilisateurs
     * Cette liste est configurée avec la clé defaultUserGroup dans la table PARAMETER.
     *
     * @return la liste des groupes par défaut
     *
     * @throws ParameterServiceException Si une erreur survient
     */
    List<Group> getDefaultGroups() throws ParameterServiceException;
}
