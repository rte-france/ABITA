/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dto.unpersist;

import com.abita.util.dateutil.DateTimeUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * Classe DTO servant à alimenter les PDF de quittancement de loyer
 */
public class PDFQuittancementFileDTO implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -6042580016288590349L;

  /** Nom du fichier */
  private String name;

  /** Type Mime du fichier */
  private String mimeType;

  /** Taille du fichier */
  private String size;

  /** Date de dernière modification */
  private Date lastUpdate;

  /** Le fichier sous forme téléchargeable */
  private File file;

  /** Première partie du nom du fichier */
  private static final int FIRST_PART_OF_NAME = 0;

  /**
   * @return the type
   */
  public String getType() {
    String[] explode = name.split("_");
    return explode[FIRST_PART_OF_NAME];
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the mimeType
   */
  public String getMimeType() {
    return mimeType;
  }

  /**
   * @param mimeType the mimeType to set
   */
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * @return the size
   */
  public String getSize() {
    return size;
  }

  /**
   * @param size the size to set
   */
  public void setSize(String size) {
    this.size = size;
  }

  /**
   * @return the lastUpdate
   */
  public Date getLastUpdate() {
    return DateTimeUtils.clone(lastUpdate);
  }

  /**
   * @param lastUpdate the lastUpdate to set
   */
  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = DateTimeUtils.clone(lastUpdate);
  }

  /**
   * Permet d'obtenir le fichier à télécharger
   * @return le fichier à télécharger
   * @throws IOException l'exception
   */
  public StreamedContent getFile() throws IOException {
    return new DefaultStreamedContent(new FileInputStream(file), mimeType, name);
  }

  /**
   * @param file the file to set
   */
  public void setFile(File file) {
    this.file = file;
  }

}
