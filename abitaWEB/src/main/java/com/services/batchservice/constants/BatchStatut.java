/**
 *
 */
package com.services.batchservice.constants;

/**
 * Défini les statuts des Batch
 * @author
 *
 */
public enum BatchStatut {

    /** Batch terminé sans erreur*/
    OK,
    /** Batch en cours de traitement*/
    IN_PROGRESS,
    /** Batch terminé avec erreurs*/
    KO
}
