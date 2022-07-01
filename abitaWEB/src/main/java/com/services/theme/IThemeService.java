package com.services.theme;

import com.dto.ThemeDTO;
import com.services.theme.exception.ThemeServiceException;

import java.util.Map;

/**
 * Interface pour la gestion des thèmes
 *
 * @author
 */
public interface IThemeService {
	/**
	 * Renvoie la liste des thèmes sous forme d'une map.
	 * Les clés correspondent au nom technique et les valeurs au nom fonctionnel.
	 *
	 * @return les thèmes
	 *
	 * @throws ThemeServiceException Si une erreur survient
	 */
	Map<String, String> getThemes() throws ThemeServiceException;

	/**
	 * Renvoie le thème à partir du nom indiqué
	 *
	 * @param name le nom de thème à chercher
	 *
	 * @return le thème si trouvé
	 *
	 * @throws ThemeServiceException Si une erreur survient
	 */
	ThemeDTO getByName(final String name) throws ThemeServiceException;
}
