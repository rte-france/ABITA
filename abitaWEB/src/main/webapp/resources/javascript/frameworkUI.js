/**
 * Adds indexOf function to Array for IE8 and below
 */
if (!Array.prototype.indexOf)
{
  Array.prototype.indexOf = function(elt /*, from*/)
  {
    var len = this.length >>> 0;

    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;

    for (; from < len; from++)
    {
      if (from in this &&
          this[from] === elt)
        return from;
    }
    return -1;
  };
}

/**
 * Test si l'évènement corresponds à une touche numérique
 * Cette fonction n'est pas parfaite (check de si la touche shift du clavier est enfoncée par ex)... FIXME
 * @param event évènement à analyser
 * @returns {Boolean} vrai si l'évènement est une touche numérique, faux sinon
 * @param allowDecimal booléen indiquant si on veut allouer les décimaux
 */
function isNumerical(event, allowDecimal) {
	"use-strict";

	// Pour compatibilité IE
	var keyCode = event.which || event.keyCode;

    // Liste des chiffres en haut des lettres sur le clavier
	var authorizedKeysWithAlt = [48,49,50,51,52,53,54,55,56,57];

    // Liste des keycodes des chiffres du pavé numérique
    var authorizedKeys = [96,97,98,99,100,101,102,103,104,105];

    // Liste des keycodes des charactères de spération flottant (virgule, point, point)
    var decimalAuthorizeKeys = [188, 59, 190];

    // Liste des keycodes des flèches, ainsi que suppr, backspace, ...
    var others = [0, 8, 16, 37, 38, 39, 40, 46];

    return ((authorizedKeys.indexOf(keyCode) != -1)
        || (authorizedKeysWithAlt.indexOf(keyCode) != -1)
        || (others.indexOf(keyCode) != -1)
        || (allowDecimal && decimalAuthorizeKeys.indexOf(keyCode) != -1));
}
