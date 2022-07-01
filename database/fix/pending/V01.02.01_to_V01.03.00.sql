UPDATE FWK_PARAMETER SET PRM_VALUE = '01.03.00' WHERE PRM_ID = 3041;

-- Modification des données des contrats occupants : valeur par défaut à 0 pour les valeurs locatives foncières non définies
UPDATE CONTRACT
SET CON_REAL_ESTATE_RENTAL_VALUE = 0
WHERE CON_REAL_ESTATE_RENTAL_VALUE IS NULL;
    
-- Modification de la table des contrats occupants : valeur obligatoire pour la valeur locative foncière
ALTER TABLE CONTRACT MODIFY CON_REAL_ESTATE_RENTAL_VALUE NOT NULL;

-- Modification des données des logements : valeur par défaut à « Maison » pour les natures non définies
UPDATE HOUSING
SET HNA_ID = 1
WHERE HNA_ID IS NULL;
    
-- Modification de la table des logements : valeur obligatoire pour la nature
ALTER TABLE HOUSING MODIFY HNA_ID NOT NULL;

-- Modification de la table des domaines d’activité : nouvelle colonne concernant le GMR
ALTER TABLE FIELD_OF_ACTIVITY ADD (
    FOA_GMR VARCHAR2(60 CHAR)
);

-- Création de la table pour les agences
CREATE TABLE AGENCY (
	AGC_ID NUMBER(19,0) NOT NULL,
	AGC_NAME VARCHAR2(50 CHAR) NOT NULL,
	CONSTRAINT PK_AGENCY PRIMARY KEY(AGC_ID) USING INDEX TABLESPACE TBS_ABITA_IDX
);

-- Création de la séquence pour la table des agences
CREATE SEQUENCE SEQ_AGENCY
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- Insertion d’une donnée d’agence
INSERT INTO AGENCY
VALUES (SEQ_AGENCY.nextval, 'Autre');

-- Modification de la table des logements : nouvelle colonne concernant l’agence
ALTER TABLE HOUSING ADD (
    AGC_ID NUMBER(6,0) DEFAULT 1 NOT NULL
);

-- Création de la table pour la relation entre les utilisateurs et les agences
CREATE TABLE ATTRIBUTE (
	USR_ID NUMBER(19,0) NOT NULL,
	AGC_ID NUMBER(19,0) NOT NULL,
	CONSTRAINT PK_ATT_USR PRIMARY KEY(USR_ID, AGC_ID) USING INDEX TABLESPACE TBS_ABITA_IDX,
	CONSTRAINT FK_ATT_AGC FOREIGN KEY (AGC_ID) REFERENCES AGENCY (AGC_ID) ON DELETE CASCADE,
	CONSTRAINT FK_ATT_USR FOREIGN KEY (USR_ID) REFERENCES USER_EXTENSION (USR_ID)
);

-- Modification de la table des logements : nouvelle contrainte de clé étrangère vers l’agence
ALTER TABLE HOUSING ADD CONSTRAINT FK_HOU_2_AGC FOREIGN KEY (AGC_ID) REFERENCES AGENCY (AGC_ID);

-- Modification de la table des contrats tiers : nouvelles colonnes pour les révisions
ALTER TABLE THIRD_PARTY_CONTRACT ADD (
    TPC_REVISED_RENT_AMOUNT NUMBER(10,3),
    TPC_REVISED_RENT_DATE DATE,
    TPC_REVISED_EXPECT_CHARGE_COST NUMBER(10,3),
    TPC_REVISED_EXPECT_CHARGE_DATE DATE
);

-- Création de la table d’historisation des révisions
CREATE TABLE REVISION_TPC (
	REV_TPC_ID NUMBER(19,0) NOT NULL,
    REV_TPC_AMOUNT NUMBER(10,3),
    REV_TPC_DATE DATE NOT NULL,
    REV_TPC_TYPE VARCHAR2(4 CHAR) NOT NULL,
	TPC_ID NUMBER(19,0) NOT NULL,
	CONSTRAINT PK_REVISION_TPC PRIMARY KEY(REV_TPC_ID) USING INDEX TABLESPACE TBS_ABITA_IDX,
	CONSTRAINT FK_REV_TPC_2_TPC FOREIGN KEY (TPC_ID) REFERENCES THIRD_PARTY_CONTRACT (TPC_ID)
);

CREATE SEQUENCE SEQ_REVISION_TPC
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- Modification de la table des contrats tiers : nouvelles colonnes pour les dates de dernieres révisions
ALTER TABLE THIRD_PARTY_CONTRACT ADD (
    TPC_LAST_REVISED_RENT_DATE DATE,
    TPC_LAST_REVISED_EXP_CHA_DATE DATE
);

CREATE UNIQUE INDEX IDX_UNIQUE_UID_ON_FWK_USER ON FWK_USER (USR_LOGICAL_ID);