package com.services.accessconfig;

import com.dto.Group;
import com.dto.UserDTO;

/**
 * Composant de gestion des droits d'accès pour un utilisateur
 * @author
 */
public interface IAccessConfigService {

    /**
     * Permet de déterminer si un utilisateur a accès à une page donnée
     * @param user l'utilisateur à tester
     * @param pageName la page
     * @return true si l'utilisateur a les droits d'accès à la page, false sinon
     */
    boolean hasUserAcessToPage(UserDTO user, String pageName);

    /**
     * Permet de déterminer si un utilisateur dispose d'un groupe donné
     * @param user l'utilisateur
     * @param group le groupe à tester
     * @return true si l'utilisateur a le groupe, false sinon
     */
    boolean isUserHavingGroup(UserDTO user, Group group);
}
