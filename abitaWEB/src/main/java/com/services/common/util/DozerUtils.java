package com.services.common.util;

import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Utilitaire pour le mapping des collections
 *
 * @author
 */
public final class DozerUtils {

	/**
	 * Classe utilitaire => pas de constructeur public
	 */
	private DozerUtils() {
	}

	/**
	 * Mappe une liste en une autre.
	 *
	 * @param mapper le mapper
	 * @param source la liste source.
	 * @param <T> classe du type d'entrée
	 * @param <U> classe du type de sortie
	 * @param targetList le type de liste destination
	 * @param mappingId L'identifiant du mapping Dozer à utiliser
	 *
	 * @return la liste mappée.
	 */
	public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> targetList,
			final String mappingId) {
		final List<U> dest = new ArrayList<U>(source.size());
		for (T element : source) {
			if (element == null) {
				continue;
			}
			dest.add(mapper.map(element, targetList, mappingId));
		}
		return dest;
	}

	/**
	 * Mappe une liste en une autre.
	 *
	 * @see com.services.common.util.DozerUtils#map(Mapper, List, Class, String)
	 *
	 * @param mapper le mapper
	 * @param source la liste source.
	 * @param <T> classe du type d'entrée
	 * @param <U> classe du type de sortie
	 * @param targetList le type de liste destination
	 *
	 * @return la liste mappée.
	 */
	public static <T, U> List<U> map(final Mapper mapper, final List<T> source, final Class<U> targetList) {
		return map(mapper, source, targetList, null);
	}
}
