/** Identifiant de l’application */
var APPLICATION_ID = 'abita' ;

/** Contexte de l’application */
var APPLICATION_CONTEXT_ROOT = 'abita' ;

/**
 * Lors des recherches, en cas d’erreurs, on efface les résultats et on met le focus sur les messages
 */
function cleanResultAndShowErrors() {
	if (!$('#messages').is(':empty')) {
		$('#result').hide();
		$('html').animate({ scrollTop: $('#messages').offset().top }, 'slow');
	}
}

/**
 * Met à jour la borne inférieure lors de la validation d’un barème pour avantages en nature
 */
$(document).ready(function() {
	$('.ui-icon-check').live('click', function() {
		// Borne de l’avantage en nature précédent
		var min = $(this).parents('tr').prev().find('.lowLimit').text();
		var max = $(this).parents('tr').next().find('.lowLimit').text();

		// Valeur de celui que l'on change
		var $newValue = $(this).parents('tr').find('input').first().val();
		
		// Si c’est un nombre et qu’il est compris dans les bornes
		if (isNaN($newValue) === false && $newValue > min && $newValue < max) {
			
			// Si 2 chiffres max après la virgule
			$checkNumberAfterComma = $('#frmRefBenefit input:visible').first().val().split('.')[1];
			if ($checkNumberAfterComma === undefined || ($checkNumberAfterComma !== undefined && $checkNumberAfterComma.length <= 2)) {
				$(this).parents('tr').prev().find('.highLimit').text($newValue);
			}
		}
	});
});

/**
 * Annule la prise en compte des validations des données
 */
function resetButtonPressed() {
	var buttonPressed = $('#buttonPressedHidden');
	buttonPressed.val('0');
}

/**
 * Force la prise en compte des validations des données
 */
function checkButtonPressed() {
	var buttonPressed = $('#buttonPressedHidden');
	buttonPressed.val('1');
}

/**
 * Initialise la référence de l’occupant
 */
function initTenantRef() {
	var refNotSalaried = $('#refNotSalaried');
	var refHidden = $('#refHidden');
	if ($("#type option:selected").text() === "Non salarié") {
		refNotSalaried.text(($('#name').val() + $('#firstname').val()).substring(0,15));
		refHidden.val(($('#name').val() + $('#firstname').val()).substring(0,15));
	}
}

/**
 * Change le nombre de caractère du champs référence occupant selon le type occupant sélectionné
 */
function refTenantUpdate() {
	// recupere dans la variable refText le champ "ref"
	var refText = $('#ref');
	// teste si la 3eme option "non salarié" est selectionné
	if ($("#type option:selected").text() === "Non salarié") {
		initTenantRef();
	} else if ($("#type option:selected").text() === "Salarié" || $("#type option:selected").text() === "Retraité") {
		// efface la valeur du champ "ref" pour eviter que le nombre de caractère soit superieur a 8	
		refText.attr('maxlength','8');		
	}
}

/**
 * Affiche les messages d’alerte
 */
function alert() {
	var alert = $('#alert');
	var messages = $('#messages');
	var btnValidate = $('#btnValidate');
	var btnCancel = $('#btnCancel');
	
	// masque le message actif
	messages.hide();
	
	// affiche le nouveau message
	alert.show();
	
	// désactiver les boutons de validation et d’annulation
	btnValidate.addClass('ui-state-disabled');
	btnCancel.addClass('ui-state-disabled');
}
