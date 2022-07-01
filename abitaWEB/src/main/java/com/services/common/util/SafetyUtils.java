package com.services.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;


/**
 * Classe utilitaire pour simplifier le code Java et le rendre "safe"
 * @author
 */
public final class SafetyUtils {

	/** EMPTY_STRING_ARRAY */
	private static final String[] EMPTY_STRING_ARRAY = new String[] {};

    /** EMPTY_OBJECT_ARRAY */
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[] {};

	/**
	 * Constructeur
	 */
	private SafetyUtils() {
	}

	/**
	 * Methode helper qui permettra d'eviter les null check avant les foreach par exemple
	 * @param <T> type de l'element d'iteration
	 * @param iterable l'iteration
	 * @return une liste vide si la liste passee en parametre est null, la liste passee en parametre sinon
	 */
	public static <T> Iterable<T> emptyIfNull(Iterable<T> iterable) {
		return iterable == null ? Collections.<T> emptyList() : iterable;
	}

	/**
	 * Methode helper qui permettra d'eviter les null check avant les foreach par exemple
	 * @param iterable l'iteration
	 * @return un tableau vide si le tableau passe en parametre est null, le tableau passe en parametre sinon
	 */
	public static String[] emptyIfNull(String[] iterable) {
		return iterable == null ? EMPTY_STRING_ARRAY : iterable;
	}

    /**
     * Methode helper qui permettra d'eviter les null check avant les foreach par exemple
     * @param iterable l'iteration
     * @return un tableau vide si le tableau passe en parametre est null, le tableau passe en parametre sinon
     */
    public static Object[] emptyIfNull(Object[] iterable) {
        return iterable == null ? EMPTY_OBJECT_ARRAY : iterable;
    }

	/**
	 * @param collectionToCheck collection a tester
	 * @return true si la collection n'est pas vide, false sinon
	 */
	public static boolean isNotEmpty(Collection<?> collectionToCheck) {
	    return collectionToCheck != null && !collectionToCheck.isEmpty();
	}

	/**
	 * Méthode qui permet d'éviter le pattern if (.. != null) { ...close(); }
	 * @param resourceToClose la ressource à fermer
	 * @throws IOException I/O exception
	 */
	public static void close(Closeable resourceToClose) throws IOException {
	    if (resourceToClose != null) {
	        resourceToClose.close();
	    }
	}
}
