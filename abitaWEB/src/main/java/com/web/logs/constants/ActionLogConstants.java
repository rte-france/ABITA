/**
 *
 */
package com.web.logs.constants;

/**
 * @author
 */
public final class ActionLogConstants {
	/**
	 * Constructor
	 */
	private ActionLogConstants() {

	}

	/** Service error while selecting a log */
	public static final String SELECT_ERROR = "fwk.logs.select.error";

	/** Service error while filtering logs */
	public static final String FILTER_ERROR = "fwk.logs.filter.error";

	/** Clé du message d'erreur à afficher lorsqu'il y a trop d'objets retournés par le filtre */
	public static final String FILTER_ERROR_TOO_MANY_RESULTS = "fwk.logs.filter.error.too_many_results";

	/** Nom du bundle de traduction */
	public static final String BUNDLE_NAME = "com.web.logs.resource.logs";

	/** Préfixe de la clé du fichier de propriétés pour la classe CSS d'un status */
	public static final String STATUS_CSS_KEY_PREFIX = "fwk.logs.table.column.status.";

	/** Suffixe de la clé du fichier de propriétés pour la classe CSS d'un status */
	public static final String CSS_KEY_SUFFIX = ".css.class";

	/** Préfixe de la clé du fichier de propriétés pour la classe CSS de la sévérité d'un message */
	public static final String SEVERITY_CSS_KEY_PREFIX = "fwk.logs.table.column.severity.";

	/** Préfixe de la clé du fichier de propriétés pour le libellé d'un status */
	public static final String STATUS_KEY_PREFIX = "com.fwk.logs.status.";

	/** Clé du fichier de propriétés pour le libellé de filtrage rapide des origines */
	public static final String FILTER_ORIGIN_NONE = "fwk.logs.table.column.origin.filter.none";

	/** Clé du fichier de propriétés pour le libellé de filtrage rapide des status */
	public static final String FILTER_STATUS_NONE = "fwk.logs.table.column.status.filter.none";

	/** Clé du fichier de propriétés pour le libellé de filtrage rapide de la gravité */
	public static final String FILTER_SEVERITY_NONE = "fwk.logs.table.column.severity.filter.none";

	/** Préfixe de la clé du fichier de propriétés pour le libellé de la sévérité */
	public static final String SEVERITY_KEY_PREFIX = "com.fwk.logs.severity.";

	/** La chaîne indiquant que tous les statuts doivent être retournés par le filtre */
	public static final String STATUS_ALL = "all";

	/** La clé du message indiquant qu'au moins un filtre doit être indiqué */
	public static final String FILTER_ERROR_IMPRECISE_FILTERS = "fwk.logs.filter.error.imprecise_filters";
}
