package com.services.amchartsservice;

import java.io.Serializable;

/**
 * Interface permettant de dissocier le processus de construction
 * d'un objet complexe de sa représentation interne (Design Pattern Monteur)
 * @author
 */
public interface IBuilder {

	/**
	 * Construction du flux
	 * @param serializableObject objet serializable passé lors de la construction
	 * @return le flux construit
	 */
	String build(Serializable serializableObject);
}
