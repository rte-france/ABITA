/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.web.logs.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.web.logs.impl.ActionLogsBean;
import com.web.logs.impl.SelectItemComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.event.SelectEvent;

import com.dto.ActionLogDTO;
import com.dto.ActionLogFilterDTO;
import com.dto.ActionLogMessageDTO;
import com.services.actionlogs.IActionLogService;
import com.services.actionlogs.exception.ActionLogServiceException;
import com.services.common.exception.TooManyResultsException;
import com.services.common.util.MessageSupport;
import com.services.common.util.SafetyUtils;
import com.web.logs.constants.ActionLogConstants;

/**
 * Contrôleur gérant les interactions avec la page des logs applicatifs
 *
 * @author
 */
public class ActionLogsController implements Serializable {
	/** LOGGER */
	private static final Log LOGGER = LogFactory.getLog(ActionLogsController.class);

	/**
	 * Le service permettant de récupérer les traces
	 */
	private IActionLogService actionLogService;

	/**
	 * Le bean associé
	 */
	private com.web.logs.impl.ActionLogsBean actionLogsBean;

	/**
	 * Serial id
	 */
	private static final long serialVersionUID = 6935331028483168809L;

	/**
	 * Constructeur par défaut
	 */
	public ActionLogsController() {
	}

	/**
	 * initialise les données du contrôleur, après son instanciation.
	 */
	@PostConstruct
	protected void init() {
		/* Initialisation de l'objet contenant les filtres avec des dates préféfinies */
		this.initFilters();
		/* Intialisation de la liste de filtrage des statuts */
		this.initStatusFilterOptions();

		this.getActionLogsBean().setSeverityFilterList(new ArrayList<SelectItem>());
	}

	/**
	 * Renvoie le bundle spécifique à l'application
	 * Cette méthode doit être surchargée pour renvoyer le bundle spécifique de l'application
	 * Si elle ne l'est pas, seuls les messages du framework seront utilisés
	 *
	 * Dans l'application modèle, cette méthode renvoie le PropertyResourceBundle injecté via jsf
	 *
	 * @return le bundle de l'application
	 */
	protected ResourceBundle getSpecificBundle() {
		return null;
	}

	/**
	 * Recherche une clé dans le bundle du framework puis dans celui de l'application.
	 * La méthode {@link #getSpecificBundleKeys()} permet de récupérer le bundle spécifique.
	 * Si la clé est présente dans les deux bundles, c'est la valeur de celle de l'application qui est retournée.
	 *
	 * @param key La clé du message à récupérer
	 *
	 * @return la clé si trouvée dans l'un des deux bundle.
	 */
	protected String getKey(final String key) {
		ResourceBundle specificBundle = this.getSpecificBundle();
		String value = null;

		if (specificBundle != null && specificBundle.containsKey(key)) {
			value = specificBundle.getString(key);
		}

		ResourceBundle bundle = ResourceBundle.getBundle(ActionLogConstants.BUNDLE_NAME);
		/* Clé inexistante dans le bundle de l'application */
		if (value == null && bundle.containsKey(key)) {
			value = bundle.getString(key);
		}

		return value;
	}

	/**
	 * Renvoie la liste des clés appartenant au bundle framework ou spécifique.
	 * Les clés du bundle framework sont écrasées.
	 *
	 * @return la liste des toutes les clés
	 */
	protected Set<String> getBundleKeys() {
		Set<String> keys = new HashSet<String>();

		ResourceBundle specificBundle = this.getSpecificBundle();
		if (specificBundle != null) {
			/* ajout des clés de l'application */
			keys.addAll(Collections.list(specificBundle.getKeys()));
		}

		ResourceBundle bundle = ResourceBundle.getBundle(ActionLogConstants.BUNDLE_NAME);
		/* ajout des clés du framework */
		keys.addAll(Collections.list(bundle.getKeys()));

		return keys;
	}

	/**
	 * Initialisation de la liste contenant les options de filtrage
	 */
	public void initStatusFilterOptions() {
		if (this.getActionLogsBean().getStatus() == null) {
			Set<String> keys = this.getBundleKeys();
			List<SelectItem> items = new ArrayList<SelectItem>();

			for (String k : keys) {
				if (k.startsWith(ActionLogConstants.STATUS_KEY_PREFIX)) {
					String label = this.getStatusLabel(k.substring(ActionLogConstants.STATUS_KEY_PREFIX.length()));
					items.add(new SelectItem(k.substring(ActionLogConstants.STATUS_KEY_PREFIX.length()), label));
				}
			}

			this.actionLogsBean.setStatus(items);
		}
	}

	/* *************************************************************************
	 * Tris
	 ************************************************************************ */

	/**
	 * Méthode de tri à appeler lors du tri de la colonne Status
	 * Tri basé sur le libellé affiché à l'écran et non sur la valeur interne
	 *
	 * @see String#compareTo(String)
	 *
	 * @param s1 Le premier statut
	 * @param s2 Le second statut
	 *
	 * @return le résultat de la comparaison
	 */
	public int onStatusColumnSort(final String s1, final String s2) {
		String statusLabel1 = this.getStatusLabel(s1);
		String statusLabel2 = this.getStatusLabel(s2);

		return statusLabel1.compareTo(statusLabel2);
	}

	/**
	 * Méthode appelé lors du tri de la colonne Severity dans la liste
	 * des messages
	 *
	 * @see String#compareTo(String)
	 *
	 * @param s1 Le premier état de gravité
	 * @param s2 Le second état de gravité
	 *
	 * @return le résultat de la comparaison
	 */
	public int onSeverityColumnSort(final String s1, final String s2) {
		String severityLabel1 = this.getSeverityLabel(s1);
		String severityLabel2 = this.getSeverityLabel(s2);

		return severityLabel1.compareTo(severityLabel2);
	}

	/* *************************************************************************
	 * Événements
	 ************************************************************************ */

	/**
	 * Méthode appelée lorsque l'utilisateur sélectionne une ligne du tableau
	 *
	 * RM_LOG_003
	 *
	 * @param event L'événement Primefaces associé
	 */
	public void selectLog(final SelectEvent event) {
		if (event.getObject() != null) {
			ActionLogDTO actionLog = (ActionLogDTO) event.getObject();
			this.getActionLogsBean().setSeverityFilterList(new ArrayList<SelectItem>());

			try {
				ActionLogDTO dto = this.actionLogService.get(actionLog.getId());
				this.actionLogsBean.setLogMessages(new ArrayList<ActionLogMessageDTO>(dto.getMessages()));
				this.updateSeverityQuickFilterOptions();
			} catch (ActionLogServiceException actionLogServiceException) {
				MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, ActionLogConstants.BUNDLE_NAME,
						ActionLogConstants.SELECT_ERROR);
			}
		}
	}

	/**
	 * Validation des filtres sélectionnés
	 *
	 * A minima, un filtre doit être indiqué
	 *
	 * @return true si les filtres sont valides
	 */
	protected boolean filtersValidation() {
		ActionLogFilterDTO filters = this.actionLogsBean.getFilters();
		if (filters.getFrom() == null && filters.getTo() == null
				&& com.services.common.util.StringUtils.isEmptyOrBlank(filters.getOrigin())
				&& ActionLogConstants.STATUS_ALL.equals(filters.getStatus())) {

			FacesMessage message = new FacesMessage(this.getKey(ActionLogConstants.FILTER_ERROR_IMPRECISE_FILTERS));
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, message);

			return false;
		}

		return true;
	}

	/**
	 * Appelée lorsque l'utilisateur demande le filtrage
	 */
	public void filterLogs() {
		if (this.filtersValidation()) {
			this.actionLogsBean.setStatusFilterList(new ArrayList<SelectItem>());
			this.actionLogsBean.setOriginFilterList(new ArrayList<SelectItem>());

			List<ActionLogDTO> dtos = null;
			try {
				dtos = this.actionLogService.find(this.actionLogsBean.getFilters());
			} catch (TooManyResultsException tooManyResultsException) {
				MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, ActionLogConstants.BUNDLE_NAME,
						ActionLogConstants.FILTER_ERROR_TOO_MANY_RESULTS);
				LOGGER.error("Le nombre d'objets retourné dépasse la limite fixée dans le fichier de configuration",
						tooManyResultsException);

			} catch (ActionLogServiceException actionLogServiceException) {
				MessageSupport.addMessage(FacesMessage.SEVERITY_ERROR, ActionLogConstants.BUNDLE_NAME,
						ActionLogConstants.FILTER_ERROR);
				LOGGER.error("Erreur pendant la récupération de la liste filtrée", actionLogServiceException);
			} finally {
				this.actionLogsBean.setLogs(dtos);
				this.updateOriginQuickFilterOptions();
				this.updateStatusQuickFilterOptions();
			}
		}
	}

	/**
	 * Conversion d'une map en liste
	 *
	 * @param <T> le type de la valeur stockée dans la map
	 *
	 * @param map La map d'entrée
	 *
	 * @return La liste
	 */
	protected <T> List<T> mapToList(final Map<String, T> map) {
		List<T> list = new ArrayList<T>();

		if (map != null) {
			for (final Entry<String, T> entry : map.entrySet()) {
				list.add(entry.getValue());
			}
		}

		return list;
	}

	/**
	 * Mise à jour de la liste de filtrage située dans la colonne du tableau
	 */
	protected void updateOriginQuickFilterOptions() {
		Map<String, SelectItem> map = new HashMap<String, SelectItem>();

		for (final ActionLogDTO actionLog : SafetyUtils.emptyIfNull(this.actionLogsBean.getLogs())) {
			if (!map.containsKey(actionLog.getOrigin())) {
				map.put(actionLog.getOrigin(), new SelectItem(actionLog.getOrigin(), actionLog.getOrigin()));
			}
		}

		// plus d'un élément, on ajoute l'élément de non sélection
		if (map.size() > 1) {
			String originFilterNone = this.getKey(ActionLogConstants.FILTER_ORIGIN_NONE);
			SelectItem noSelectionItem = new SelectItem(StringUtils.EMPTY, originFilterNone);
			noSelectionItem.setNoSelectionOption(true);
			map.put(originFilterNone, noSelectionItem);
		}

		this.actionLogsBean.setOriginFilterList(this.mapToListAndSort(map));
	}

	/**
	 * Mise à jour de la liste de filtrage située dans la colonne du tableau
	 */
	protected void updateStatusQuickFilterOptions() {
		Map<String, SelectItem> map = new HashMap<String, SelectItem>();

		for (final ActionLogDTO log : SafetyUtils.emptyIfNull(this.actionLogsBean.getLogs())) {
			String label = this.getStatusLabel(log.getStatus());
			if (!map.containsKey(label)) {
				map.put(label, new SelectItem(log.getStatus(), label));
			}
		}

		// plus d'un élément, on ajoute l'élément de non sélection
		if (map.size() > 1) {
			String statusFilterNone = this.getKey(ActionLogConstants.FILTER_STATUS_NONE);
			SelectItem noSelectionIem = new SelectItem(StringUtils.EMPTY, statusFilterNone);
			noSelectionIem.setNoSelectionOption(true);
			map.put(StringUtils.EMPTY, noSelectionIem);
		}

		this.actionLogsBean.setStatusFilterList(this.mapToListAndSort(map));
	}

	/**
	 * Converti la map en liste puis la tri
	 *
	 * @param map La map en entrée
	 *
	 * @return la liste triée en sortie
	 */
	protected List<SelectItem> mapToListAndSort(final Map<String, SelectItem> map) {
		List<SelectItem> list = this.mapToList(map);

		Collections.sort(list, new SelectItemComparator());

		return list;
	}

	/**
	 * Mise à jour de la liste de filtrage selon la sévérité du message
	 */
	protected void updateSeverityQuickFilterOptions() {
		Map<String, SelectItem> map = new HashMap<String, SelectItem>();

		if (this.actionLogsBean.getLogMessages() != null) {
			for (final ActionLogMessageDTO logMessage : this.actionLogsBean.getLogMessages()) {
				String label = this.getSeverityLabel(logMessage.getSeverity());
				if (!map.containsKey(label)) {
					map.put(label, new SelectItem(logMessage.getSeverity(), label));
				}
			}
		}

		// plus d'un élément, on ajoute l'élément de non sélection
		if (map.size() > 1) {
			String severityFilterNone = this.getKey(ActionLogConstants.FILTER_SEVERITY_NONE);
			SelectItem noSelectionIem = new SelectItem(StringUtils.EMPTY, severityFilterNone);
			noSelectionIem.setNoSelectionOption(true);
			map.put(StringUtils.EMPTY, noSelectionIem);
		}

		this.actionLogsBean.setSeverityFilterList(this.mapToListAndSort(map));
	}

	/**
	 * Initialisation de l'objet contenant les filtres
	 */
	protected void initFilters() {
		ActionLogFilterDTO filters = new ActionLogFilterDTO();
		this.getActionLogsBean().setFilters(filters);
	}

	/**
	 * Renvoie le nom de la classe CSS à affecter à l'élément afin de donner un style à la ligne
	 *
	 * @param status Le status du log en cours de traitement
	 *
	 * @return Le nom de la classe CSS associée
	 */
	public String getRowCssClass(final String status) {
		String key = this.getKeyNameForSatusCss(status);
		return this.getKey(key);
	}

	/**
	 * Renvoie le nom de la classe CSS à affecter à l'élément afin de donner un style à la ligne
	 *
	 * @param severity La gravité du message cours de traitement
	 *
	 * @return Le nom de la classe CSS associée
	 */
	public String getMessageRowCssClass(final String severity) {
		String key = this.getKeyNameForSeverityCss(severity);
		return this.getKey(key);
	}

	/**
	 * Renvoie le nom de la clé à utiliser pour récupérer la valeur de la classe CSS depuis le bundle
	 *
	 * @param status Le statut dont on veut récupérer la classe CSS
	 *
	 * @return le nom de la clé
	 */
	private String getKeyNameForSatusCss(final String status) {
		return ActionLogConstants.STATUS_CSS_KEY_PREFIX.concat(status).concat(ActionLogConstants.CSS_KEY_SUFFIX);
	}

	/**
	 * Renvoie le nom de la clé à utiliser pour récupérer la valeur de la classe CSS depuis le bundle
	 *
	 * @param severity La sévérité dont on veut récupérer la classe CSS
	 *
	 * @return le nom de la clé
	 */
	private String getKeyNameForSeverityCss(final String severity) {
		return ActionLogConstants.SEVERITY_CSS_KEY_PREFIX.concat(severity).concat(ActionLogConstants.CSS_KEY_SUFFIX);
	}

	/**
	 * Renvoie le libellé à afficher à partir du statut spécifié
	 *
	 * @param status Le statut dont on veut le libellé
	 *
	 * @return le message correspondant dans le fichier properties
	 */
	public String getStatusLabel(final String status) {
		String libelle = null;
		if (status != null) {
			String key = ActionLogConstants.STATUS_KEY_PREFIX.concat(status);
			libelle = this.getKey(key);
		}
		if (libelle == null) {
			libelle = StringUtils.EMPTY;
		}
		return libelle;
	}

	/**
	 * Renvoie le libellé à afficher à partir du statut spécifié
	 *
	 * @param severity La sévérité dont on veut le libellé
	 *
	 * @return le message correspondant dans le fichier properties
	 */
	public String getSeverityLabel(final String severity) {
		String libelle = null;
		if (severity != null) {
			String key = ActionLogConstants.SEVERITY_KEY_PREFIX.concat(severity);
			libelle = this.getKey(key);
		}
		if (libelle == null) {
			libelle = StringUtils.EMPTY;
		}
		return libelle;
	}

	/* *************************************************************************
	 * Getters / Setters
	 ************************************************************************ */

	/**
	 * @param actionLogService the actionLogService to set
	 */
	public void setActionLogService(final IActionLogService actionLogService) {
		this.actionLogService = actionLogService;
	}

	/**
	 * Getter de reportingBean
	 *
	 * @return the reportingBean
	 */
	public com.web.logs.impl.ActionLogsBean getActionLogsBean() {
		return this.actionLogsBean;
	}

	/**
	 * Setter de reportingBean
	 *
	 * @param actionLogsBean the reportingBean to set
	 */
	public void setActionLogsBean(final ActionLogsBean actionLogsBean) {
		this.actionLogsBean = actionLogsBean;
	}
}
