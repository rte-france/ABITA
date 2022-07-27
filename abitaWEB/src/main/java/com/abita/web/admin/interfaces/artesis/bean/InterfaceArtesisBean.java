/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.web.admin.interfaces.artesis.bean;

import org.primefaces.model.UploadedFile;

import java.io.Serializable;

/**
 * Backing bean de la page de gestion des codes comptables
 * @author
 *
 */
public class InterfaceArtesisBean implements Serializable {

  /** serialVersionUID */
  private static final long serialVersionUID = 3315181556239873342L;

  /** Objet représentant le fichier téléchargé */
  private transient UploadedFile uploadedFile;

  /**
   * Constructeur
   */
  public InterfaceArtesisBean() {
    super();
  }

  /**
   * Getter de l'objet représentant le fichier téléchargé
   * @return the uploadedFile
   */
  public UploadedFile getUploadedFile() {
    return uploadedFile;
  }

  /**
   * Setter de l'objet représentant le fichier téléchargé
   * @param uploadedFile the uploadedFile to set
   */
  public void setUploadedFile(UploadedFile uploadedFile) {
    this.uploadedFile = uploadedFile;
  }

}
