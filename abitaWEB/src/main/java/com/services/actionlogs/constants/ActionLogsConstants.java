/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.services.actionlogs.constants;

/**
 * Constantes du module ActionLogs
 * @author
 */
public final class ActionLogsConstants {

	/**
	 * Constructeur
	 */
	private ActionLogsConstants() {

	}

	/** nom du module */
	public static final String BUNDLE_NAME = "module_logs";

	/** nom du module de base dans le framework */
	public static final String BASE_BUNDLE_NAME = "com.services.actionlogs.resource.ActionLogs";

	/** clé manquante dans le bundle pour un point d'exécution */
	public static final String MISSING_KEY = "missing.key";

	/** statut manquant pour point d'exécution */
	public static final String MISSING_STATUS = "missing.status";

	/** suffixe de propriété de succès */
	public static final String SUCCESS = ".success";

	/** suffixe de propriété d'échec */
	public static final String FAILURE = ".failure";

	/** suffixe de propriété de libellé */
	public static final String LABEL_SUFFIX = ".label";

	/** suffixe de propriété de statut */
	public static final String STATUS_SUFFIX = ".status";

	/** suffixe de propriété nom d'utilisateur */
	public static final String USER_SUFFIX = ".user";

	/** suffixe de propriété d'origine */
	public static final String ORIGIN_SUFFIX = ".origin";

	/** Le nombre maximum d'objets à renvoyer */
	public static final String BUNDLE_MAX_RESULTS = "com.fwk.actionlogs.limits_results_count";

	/** Si la limite de résultats est active */
	public static final String BUNDLE_LIMIT_ENABLED = "com.fwk.actionlogs.limits_results_enabled";

	/** Si le service doit retourner des objets légers */
	public static final String BUNDLE_RETURN_LIGHTWEIGHT = "com.fwk.actionlogs.return_lightweight_objects";

	/** Le nom de la configuration de mapping qui permet de récupérer des objets légers */
	public static final String MAPPER_ID_LIGHT = "light";

	/** Valeur à envoyer quand la propriété n'est pas définie */
	public static final int NO_MAX_RESULT = -1;
}
