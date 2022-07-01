/**
 *
 * Fichier javascript pour la gestion des indicateurs.
 *
 */


/**
 *
 * Constantes
 *
 */
	/** Constante avec le libelle du cookie pour la mesure de la durée des appels */
	var COOKIE_LABEL_TIME_STAMP = 'cookieLabelTimeStamp';
	/** Constante avec le libelle du cookie pour la mesure de la durée des appels */
	var COOKIE_LABEL_ELEMENT_ID = 'cookieLabelElementId';
	/** Constante avec le libelle du cookie pour les stockage des paramètres optionnels à envoyer */
	var COOKIE_LABEL_OPT_PARAMETERS = 'cookieLabelOptParameters';


	/** Nombre de milisecondes par jour */
	//24*60*60*1000 = 86400000
	var MILISECONDS_PER_DAY=86400000;


/**
 *
 * Variables
 *
 */

	/** Identifiant de l'utilisateur crypté */
	var userIdToLog;
	/** Identifiant de l'élément à logger */
	var elementToLogId;
	/** Début de l'opération */
	var userEventTimeStampStart;
	/** Optional parameters for the counter action */
	var optParams;

	/** Gestion des évenements */

	/**
	 * Function fwkBALOnBeforeUnloadFunction(event) gestion du comportement global de l'évenement "onLoad"
	 * Si la durée d'affichage d'un écran entrainant le changement de page est à mesurer suite au click
	 * sur un bouton alors la variable elementToLogId aura été valorisée. Si c'est le cas il faudra alors
	 * attendre le chargement de la page suivante pour pouvoir mesurer la durée totale. Pour rendre disponible
	 * le moment de début à mesurer (ou le moment du click sur le bouton) un cookie avec le timestamp et un
	 * autre avec l'identifiant de l'élément à logger seront créés.
	 *
	 * @param event : évenement capturé
	 */
	function fwkBALOnBeforeUnloadFunction(event) {
		"use-strict";

		event = event || window.event;

		if (elementToLogId !== null && elementToLogId !== undefined && elementToLogId !== '') {
			// Un élément à loger a été clické, on stocke le timestamp dans userEventTimeStampStart
			// dans une cookie :
			createCookie(COOKIE_LABEL_TIME_STAMP, userEventTimeStampStart, 1);
			createCookie(COOKIE_LABEL_ELEMENT_ID, elementToLogId, 1);

			if (optParams) {
				createCookie(COOKIE_LABEL_OPT_PARAMETERS, JSON.stringify(optParams), 1);
			}

			// Reset de la variable à logger (mais comme on change de contexte cette valeur
			// ne sera jamais valorisé dans le onload car il est seulement valorisé lors d'un
			// événement utilisateur).
			elementToLogId='';
		}
	}


	/**
	 * fwkBALOnloadFuntion(event) gestion du comportement global de l'évenement "onLoad"
	 * Consultation de l'existence d'une cookie avec le nom COOKIE_LABEL_TIME_STAMP
	 * Si il existe il faut alors gérer la durée depuis que l'utilisateur a clické jusqu'au chargement de la page,
	 * pour cela il faudra calculer un nouveau time stamp indiquant le moment de finalisation du chargement, puis faire
	 * appel à la servlet qui stockera la différence entre ce timestamp et celui stocké dans la cookie. Ce cookie sera
	 * ensuite supprimé. Le nom de l'élément clické est stocké dans le cookie COOKIE_LABEL_ELEMENT_ID.
	 *
	 * @param event : évenement capturé
	 *
	 */
	function fwkBALOnloadFuntion(event) {
		"use-strict";

		event = event || window.event;

		var cookieTimeStamp=readCookie(COOKIE_LABEL_TIME_STAMP);
		var cookideElementToLogId=readCookie(COOKIE_LABEL_ELEMENT_ID);
		var cookieOptParameters=JSON.parse(readCookie(COOKIE_LABEL_OPT_PARAMETERS));
		eraseCookie(COOKIE_LABEL_TIME_STAMP);
		eraseCookie(COOKIE_LABEL_ELEMENT_ID);
		eraseCookie(COOKIE_LABEL_OPT_PARAMETERS);

		if (cookieTimeStamp!==undefined && cookieTimeStamp!=="") {
			// Appel à la servlet
			userEventTimeStampStart = cookieTimeStamp;
			elementToLogId = cookideElementToLogId;
			endCounterFwk(cookideElementToLogId, cookieOptParameters);
		}

	}

	/**
	 * attachFrameworkEventsManagement() attachement des gestionnaires des événements dans les pages
	 * des applications.
	 */
	function attachFrameworkEventsManagement() {
		"use-strict";

		if (window.addEventListener) {  // all browsers except IE before version 9
		  window.addEventListener ("beforeunload", fwkBALOnBeforeUnloadFunction, false);

        }
        else {
            if (window.attachEvent) {   // IE before version 9
                window.attachEvent ("onbeforeunload", fwkBALOnBeforeUnloadFunction);
            }
        }

	  if (window.addEventListener) {  // all browsers except IE before version 9
          window.addEventListener ("load", fwkBALOnloadFuntion, false);
      }
      else {
          if (window.attachEvent) {   // IE before version 9
              window.attachEvent ("onload", fwkBALOnloadFuntion);
          }
      }
	}


	/** *********************   Gestion de la durée des évenements ********************* */

	/**
	 *  startCounterFwk(elementId) : initialisation du compteur et stockage de la valeur dans la
	 *  variable globale 'userEventTimeStampStart'; l'identifiant de l'élément à logger sera aussi
	 *  stocké dans la variable globale 'elementToLogId'.
	 *
	 *  @param elementId : identifiant de l'élément dont la durée est à logger
	 */
	function startCounterFwk(userId, elementId, optParameters) {
		"use-strict";

		userIdToLog = userId;
		elementToLogId = elementId;
		optParams = optParameters;
		userEventTimeStampStart = new Date().getTime();
	}


	/**
	 * endCounterFwk(elementId) : finalisation de le durée d'un événement.
	 * Si un élément est à logger la variable 'elementToLogId' devra contenir la même valeur
	 * que le paramètre 'elementId' et la variable  'userEventTimeStampStart' devra être renseignée.
	 *
	 * @param elementId identifiant de l'élément à logger
	 */
	function endCounterFwk(elementId, optParameters) {
		"use-strict";

		if (elementToLogId === elementId &&
			userEventTimeStampStart !== undefined &&
			userEventTimeStampStart !== '' &&
			userEventTimeStampStart !== null) {
			callServletLogDuration(userEventTimeStampStart, elementId, optParameters);
			// Reset pour logger seulement si le bon élément est clické
			elementToLogId='';
			optParams = undefined;
		}
	}

	/**
	 * countClicksNumber(elementId) :
	 *
	 * @param elementId identifiant de l'élément dont le nombre de clicks est à logger
	 */
	function countClicksNumber(userId, elementId, optParameters) {
		"use-strict";

		userIdToLog = userId;
		elementToLogId = elementId;
		callServletLogClickNumber(userIdToLog, elementId, optParameters);
		// Reset pour logger
		elementToLogId='';
		optParams = undefined;
	}

	/**
	 * getContextRoot : récupération du context root de l'application
	 * un fichier nommé 'specificApplication.js' devra se trouver dans
	 * WebContent/resources/javascript/specificApplication.js
	 * @returns la chaine de caractère correspondant au context root de l'application
	 */
	function getContextRoot() {
		"use-strict";

		var contextRoot;
		if (window.APPLICATION_CONTEXT_ROOT !== undefined) {
			contextRoot = APPLICATION_CONTEXT_ROOT;
		}
		else {
			contextRoot = 'contextRootNonDefini';
		}
		return contextRoot;
	}


	/**
	 * callServletLogDuration() : appel à la servlet qui va stocker la durée de l'événement
	 *
	 * @param startTime début de l'opération
	 * @param elementId identifiant de l'élément à logger
	 */
	function callServletLogDuration(startTime, elementId, optParameters) {
		"use-strict";

		var endTime = new Date().getTime();

		// URL du type : /frameworkAuditWeb/fwkAudit?action=SAVEDURATION&idApp=123&id=456&idTr=789&from=50&to=75
		var url = window.location.protocol + "//" + location.host + "/" + getContextRoot() +"/frameworkAuditServlet/fwkAudit?action=SAVEDURATION";
		url += "&idUser=" + userIdToLog;
		url += "&id=" + elementId;
		url += "&from=" + escape(startTime);
		url += "&to=" + escape(endTime);

		url = appendOptionalParametersIfAny(url, optParameters);

		ajaxCallByUrl(url);
	}


	/**
	 * callServletLogClickNumber(elementId) : appel à la servlet qui va stocker le nombre de clicks
	 *
	 * @param elementId
	 */
	function callServletLogClickNumber(userIdToLog, elementId, optParameters) {
		"use-strict";

		var url = window.location.protocol + "//" + location.host + "/" + getContextRoot() +"/frameworkAuditServlet/fwkAudit?action=COUNT";
		url += "&idUser=" + userIdToLog;
		url += "&id=" + elementId;

		url = appendOptionalParametersIfAny(url, optParameters);
		ajaxCallByUrl(url);
	}


	/**
	 *
	 * appendOptionalParametersIfAny(url, optParameters)
	 *
	 * If optParameters is defined, this function parse the json object and append them as request
	 * get parameters :
	 * var optParameters = {lastname: 'Doeuf', firstname: 'John'};
	 * will be passed as [...URL...]&optParams=lastname|Doeuf;firstname|John
	 * @param url url to call for duration log. Optional attributes will be appended to this url.
	 * @optParameters the parameters represented by a json object.
	 */
	function appendOptionalParametersIfAny(url, optParameters) {
		"use-strict";

		if (optParameters) {
			url += "&optParams=";
			for ( var param in optParameters) {
				if (optParameters[param] !== "null" && optParameters[param] !== "null" && optParameters[param] !== "") {
					url += param + '|' + optParameters[param] + ';';
				} else {
					url += param + '|' + " " + ';';
				}
			}
		}
		return url;
	}



	/**
	 * ajaxCallByUrl(url) appel ajax Ã Â  l'url passÃ©e
	 *
	 * @param url url pour l'appel
	 */
	function ajaxCallByUrl(url) {
		"use-strict";

	   if (typeof XMLHttpRequest != "undefined") {
	       req = new XMLHttpRequest();
	   } else if (window.ActiveXObject) {
	       req = new ActiveXObject("Microsoft.XMLHTTP");
	   }
	   req.open("GET", url, true);
	   req.send(null);

	}







	// ****************************** COOKIES ****************************************************

	/**
	 * createCookie(name,value,days) : création d'une cookie
	 *
	 * @param name nom de la cookie
	 * @param value valeur
	 * @param days durée de la cookie en jours
	 */
	function createCookie(name,value,days) {
		"use-strict";

		var expires = "";
		if (days) {
			var date = new Date();
			// 24*60*60*1000 = 86400000
			date.setTime(date.getTime()+(days*MILISECONDS_PER_DAY));
			expires = "; expires="+date.toGMTString();
		}
		document.cookie = name+"="+escape(value)+expires+"; path=/";
	}
	/**
	 * readCookie(name) : lecture de la valeur de la cookie
	 *
	 * @param name nom de la cookie
	 * @returns valeur de la cookie, null si la cookie n'existe pas
	 */
	function readCookie(name) {
		"use-strict";

		var nameEQ = name + "=";
		var ca = document.cookie.split(';');
		var c = "";
		for(var i=0;i < ca.length;i++) {
			c = ca[i];
			while (c.charAt(0)===' ') c = c.substring(1,c.length);
			if (c.indexOf(nameEQ) === 0) return unescape(c.substring(nameEQ.length,c.length));
		}
		return null;
	}

	/**
	 * eraseCookie(name) : suppression de la cookie
	 *
	 * @param name nom de la cookie
	 */
	function eraseCookie(name) {
		"use-strict";

		createCookie(name,"",-1);
	}

	if (window.addEventListener) {  // all browsers except IE before version 9
         window.addEventListener ("load", attachFrameworkEventsManagement, false);
    }
    else if (window.attachEvent) {   // IE before version 9
         window.attachEvent ("onload", attachFrameworkEventsManagement);
    }
