/**
Copyright (C) 2020, RTE (http://www.rte-france.com)
SPDX-License-Identifier: Apache-2.0
*/

package com.abita.dao.accountingcode.entity;

import com.dao.common.entity.AbstractEntity;

/**
 * Classe Entité encapsulant l'objet code comptable
 * @author
 *
 */
public class AccountingCodeEntity extends AbstractEntity {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 6495250755813084102L;

    /** Libellé */
    private String label;

    /**
     * Code comptable
     */
    private String code;

    /**
     * Code technique du code comptable
     */
    private String technicalCode;

    /**
     * Getter du libellé
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setter du libellé
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter du code comptable
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter du code comptable
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the technicalCode
     */
    public String getTechnicalCode() {
        return technicalCode;
    }

    /**
     * @param technicalCode the technicalCode to set
     */
    public void setTechnicalCode(String technicalCode) {
        this.technicalCode = technicalCode;
    }

}
