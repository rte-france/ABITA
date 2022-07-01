/**
 * Correction d'un bug Primefaces 3.2
 *
 * 1 - Avoir des données
 * 2 - Effectuer un filtrage rapide depuis les colonnes
 * 3 - La ligne affichée est sélectionnable et les styles persos ne sont pas appliqués
 *
 * Correction :
 * Suite au filtrage, je :
 * - rajoute la classe 'ui-datatable-data-empty' sur le tbody pour appliquer les styles
 * - supprime les classes 'ui-datatable-data' et 'ui-widget-content' afin de ne pas rendre la
 * 	 ligne sélectionnable
 * - fais la chose inverse s'il y a des données
 *
 * @param component le composant javascript primefaces à mettre à jour
 */
function updateFilteredState(component) {
	if( typeof(component) !== 'undefined' ) {
		var tbody = component.tbody || jQuery(component.jqId).find('tbody');
		if( tbody.find('tr.ui-widget-content[role="row"]').length === 0 ) {
			tbody.addClass('ui-datatable-data-empty');
			tbody.removeClass('ui-datatable-data ui-widget-content');
		} else {
			tbody.removeClass('ui-datatable-data-empty');
			tbody.addClass('ui-datatable-data ui-widget-content');
		}
	}
}