UPDATE FWK_PARAMETER SET PRM_VALUE = '01.01.00' WHERE PRM_ID = 3041;

-- Nouveaux paramètres de l’application : chemins pour l’écriture des fichiers PDF de quittancement
INSERT INTO FWK_PARAMETER VALUES (3301,1,'pathQuittancementFolder','Path Quittancement Folder','Path to the quittancement folder','/applis/abita/archives/quittancement/','/applis/abita/archives/quittancement/',1001,9,1,1,'STRING');
INSERT INTO FWK_PARAMETER VALUES (3302,1,'pathTempQuittancementFolder','Temp Path Quittancement Folder','Path to the temp quittancement folder','/applis/abita/temp/quittancement/','/applis/abita/temp/quittancement/',1001,10,1,1,'STRING');

-- Modification de la table contrat : ajout des nouveaux champs de facturation ponctuelle
ALTER TABLE CONTRACT ADD (
	CON_GARBAGE_INVOICING NUMBER(10,2),
	CON_WATER_INVOICING NUMBER(10,2),
	CON_HOUSING_TAX_INVOICING NUMBER(10,2),
	CON_INSURANCE_REIMBURSEMENT NUMBER(10,2),
	CON_HOUSING_TAX_REIMBURSEMENT NUMBER(10,2),
	CON_GARBAGE_REIMBURSEMENT NUMBER(10,2),
	CON_OTHER_INVOICING_LABEL VARCHAR2(50 CHAR),
	CON_OTHER_INVOICING_AMOUNT NUMBER(10,2)
);

-- Modification de la table contrat : remplacement de la facturation ponctuelle par l’apurement annuel des charges
ALTER TABLE CONTRACT
RENAME COLUMN CON_SPORADICALLY_INVOICING
TO CON_ANNUAL_CLEARANCE_CHARGES;

-- Modification de la vue du calcul du loyer prélevé : prise en compte des nouveaux champs de la facturation ponctuelle
CREATE OR REPLACE VIEW VUE_WITHDRAWN_RENT_CALC (CON_ID, WITHDRAWN_RENT) AS
      SELECT C.CON_ID,
        VUE_LNA_CALC.LNA
        + NVL(C.con_garage_rent,0)
        + (NVL(C.con_garden_rent,0) - 0.075 * NVL(C.con_garden_rent,0))
        + NVL(C.con_extra_rent,0)
        + NVL(C.con_expected_charge_cost,0)
        + NVL(C.CON_WATER_INVOICING,0)
        + NVL(C.CON_GARBAGE_INVOICING,0)
        + NVL(C.CON_HOUSING_TAX_INVOICING,0)
        - NVL(C.CON_INSURANCE_REIMBURSEMENT,0)
        - NVL(C.CON_HOUSING_TAX_REIMBURSEMENT,0)
        - NVL(C.CON_GARBAGE_REIMBURSEMENT,0)
        + NVL(C.CON_ANNUAL_CLEARANCE_CHARGES,0)
        + NVL(C.CON_OTHER_INVOICING_AMOUNT,0)
        AS WITHDRAWN_RENT
      FROM CONTRACT C
      JOIN VUE_LNA_CALC on C.CON_ID = VUE_LNA_CALC.CON_ID;

-- Modification de la vue des contrats : prise en compte des nouveaux champs de la facturation ponctuelle
CREATE OR REPLACE VIEW CONTRACT_DETAIL
	(CON_ID, CON_REFERENCE, CON_SIGNATURE, CON_START_VALIDITY_DATE, CON_END_VALIDITY_DATE, 
	 CON_MARKET_RENT_PRICE, CON_RENT_PRICE_LIMIT, CON_GARAGE_RENT, CON_GARDEN_RENT, CON_EXTRA_RENT, 
	 CON_EXPECTED_CHARGE_COST, CON_ANNUAL_CLEARANCE_CHARGES, CON_GARBAGE_INVOICING, CON_WATER_INVOICING, CON_HOUSING_TAX_INVOICING, CON_INSURANCE_REIMBURSEMENT, 
	 CON_HOUSING_TAX_REIMBURSEMENT, CON_GARBAGE_REIMBURSEMENT, CON_OTHER_INVOICING_LABEL, CON_OTHER_INVOICING_AMOUNT, 
	 CON_ADDED_WITHDRAWN_RENT, CON_LAST_WITHDRAWN_DATE, CON_TERMINATION_SAVINGS, 
	 CON_TERMINATION_SAVING_AMOUNT, CON_LAST_SAVING_DATE, CON_REAL_ESTATE_RENTAL_VALUE, HOU_ID, TEN_ID, 
	 RTP_ID, TER_ID, FIE_ID, CCE_ID, PME_ID, 
     CON_N_N_COEF, CON_NET_AGENT_RENT, CON_SHORTTERM_CONTRACT_DISCO, CON_LOP_RENT, CON_WITHDRAWN_RENT, 
	 COD_REVISED_SURFACE_AREA, COD_REVISED_SURFACE_AREA_RENT, COD_N_N_COEF, COD_NET_AGENT_RENT, COD_SHORTTERM_CONTRACT_DISCO, 
	 COD_LOP_RENT, COD_WITHDRAWN_RENT, COD_ADDED_WITHDRAWN_RENT, COD_TERMINATION_SAVING_MONTH, COD_TERMINATION_SAVING_AMOUNT,
	 COD_CALCULATED_BENEFITS )
	AS    
  SELECT 
    C.CON_ID, C.CON_REFERENCE, C.CON_SIGNATURE, C.CON_START_VALIDITY_DATE, C.CON_END_VALIDITY_DATE, 
    C.CON_MARKET_RENT_PRICE, C.CON_RENT_PRICE_LIMIT, C.CON_GARAGE_RENT, C.CON_GARDEN_RENT, C.CON_EXTRA_RENT, 
    C.CON_EXPECTED_CHARGE_COST, C.CON_ANNUAL_CLEARANCE_CHARGES, CON_GARBAGE_INVOICING, CON_WATER_INVOICING, CON_HOUSING_TAX_INVOICING, CON_INSURANCE_REIMBURSEMENT, 
	CON_HOUSING_TAX_REIMBURSEMENT, CON_GARBAGE_REIMBURSEMENT, CON_OTHER_INVOICING_LABEL, CON_OTHER_INVOICING_AMOUNT, 
    C.CON_ADDED_WITHDRAWN_RENT, C.CON_LAST_WITHDRAWN_DATE, C.CON_TERMINATION_SAVINGS, 
    C.CON_TERMINATION_SAVING_AMOUNT, C.CON_LAST_SAVING_DATE, C.CON_REAL_ESTATE_RENTAL_VALUE, C.HOU_ID, C.TEN_ID, 
    C.RTP_ID, C.TER_ID, C.FOA_ID, C.CCE_ID, C.PME_ID ,
	C.CON_N_N_COEF, C.CON_NET_AGENT_RENT, C.CON_SHORTTERM_CONTRACT_DISCO, C.CON_LOP_RENT, C.CON_WITHDRAWN_RENT,    
    H.HOU_REVISED_SURFACE_AREA AS COD_REVISED_SURFACE_AREA,
    VUE_LSC_CALC.LSC AS COD_REVISED_SURFACE_AREA_RENT,
    VUE_NN_COEF_CALC.NNCOEF AS COD_N_N_COEF,
    VUE_LNA_CALC.LNA AS COD_NET_AGENT_RENT,
    VUE_SHRTTRM_CNTRCT_DSC_CALC.STCD AS COD_SHORTTERM_CONTRACT_DISCO,
    VUE_LOP_RENT_CALC.LOP_RENT AS COD_LOP_RENT,
    VUE_WITHDRAWN_RENT_CALC.WITHDRAWN_RENT AS COD_WITHDRAWN_RENT,
    c.con_added_withdrawn_rent + VUE_WITHDRAWN_RENT_CALC.WITHDRAWN_RENT AS COD_ADDED_WITHDRAWN_RENT,
    VUE_TERM_SAVING_MONTH_CALC.TERM_SAVING_MONTH AS COD_TERMINATION_SAVING_MONTH,
    c.con_termination_saving_amount + VUE_TERM_SAVING_MONTH_CALC.TERM_SAVING_MONTH AS COD_TERMINATION_SAVING_AMOUNT,
    GET_CALCULATED_BENEFIT(t.ten_actual_salary, h.hou_room_count) AS COD_CALCULATED_BENEFITS
  FROM CONTRACT C
  JOIN HOUSING H on C.HOU_ID = H.HOU_ID
  JOIN TENANT T on C.TEN_ID = T.TEN_ID
  JOIN VUE_LSC_CALC on C.CON_ID = VUE_LSC_CALC.CON_ID
  JOIN VUE_NN_COEF_CALC on C.CON_ID = VUE_NN_COEF_CALC.CON_ID
  JOIN VUE_SHRTTRM_CNTRCT_DSC_CALC on C.CON_ID = VUE_SHRTTRM_CNTRCT_DSC_CALC.CON_ID
  JOIN VUE_LOP_RENT_CALC on C.CON_ID = VUE_LOP_RENT_CALC.CON_ID
  JOIN VUE_LNA_CALC on C.CON_ID = VUE_LNA_CALC.CON_ID
  JOIN VUE_WITHDRAWN_RENT_CALC on C.CON_ID = VUE_WITHDRAWN_RENT_CALC.CON_ID
  JOIN VUE_TERM_SAVING_MONTH_CALC on C.CON_ID = VUE_TERM_SAVING_MONTH_CALC.CON_ID;

-- Nouvelle table pour les informations de la dernière revalorisation des loyers jardin/garage                           
CREATE TABLE REVALUATION_RENTS (
    rer_id NUMBER(19,0) not null,
    rer_rate NUMBER(8,2) not null,
    rer_last_updated DATE not null,
    rer_action VARCHAR2(1 CHAR) not null,
    CONSTRAINT pk_revaluation_rents PRIMARY KEY (rer_id) USING INDEX TABLESPACE TBS_ABITA_IDX
);

-- Nouvelle séquence pour la table des revalorisations des loyers jardin/garage
CREATE SEQUENCE SEQ_REVALUATION_RENTS
START WITH 1 
INCREMENT BY 1 
NOMAXVALUE; 

-- Données pour la table des revalorisations des loyers jardin/garage
INSERT INTO REVALUATION_RENTS
VALUES (1, 1.54, TO_DATE('2014/12/23', 'yyyy/mm/dd'), 1);

-- Modification de la table des contrats tiers : nouvelle colonne concernant la prise en compte du contrat lors de la prochaine régularisation
ALTER TABLE THIRD_PARTY_CONTRACT ADD (
    TPC_YL_REGULARIZATION NUMBER(6,0) DEFAULT 0 NOT NULL
);

-- Modification de la table d’historisation des générations : ajout des colonnes contenant les montants et les dates
ALTER TABLE YL_ZN_ACC_DOC_NBR ADD (
    YL_ZN_CYCLE_DATE DATE,
    YL_ZN_CANCELLATION_DATE DATE,
    YL_ZN_MENS_RENT_AMOUNT NUMBER(10,3),
    YL_ZN_MENS_EXP_CHARGE_COST NUMBER(10,3),
    YL_ZN_RENT_AMOUNT NUMBER(10,3),
    YL_ZN_EXP_CHARGE_COST NUMBER(10,3),
    YL_ZN_FINAL_RENT_AMOUNT NUMBER(10,3),
    YL_ZN_FINAL_EXP_CHARGE_COST NUMBER(10,3)
);

ALTER TABLE YL_ZN_ACC_DOC_NBR MODIFY (
    YL_ZN_ADN_PIECE_TYPE VARCHAR(10)
);

ALTER TABLE TENANT MODIFY TEN_HOUSEHOLD_SIZE NULL;
ALTER TABLE TENANT MODIFY TEN_HOUSEHOLD_SIZE_LAST_YEAR NULL;

-- On ajoute la colonne CON_HOUSEHOLD_SIZE avec une valeur par default pour toutes les lignes : 0 car CON_HOUSEHOLD_SIZE non nullable
ALTER TABLE CONTRACT ADD (
CON_HOUSEHOLD_SIZE NUMBER(3,0) DEFAULT 0 NOT NULL
);
-- On met à jour toutes les lignes des contrats occupants avec la valeur à jour récupérée dans la table occupant
UPDATE CONTRACT CON SET CON.CON_HOUSEHOLD_SIZE = (SELECT NVL(TEN.TEN_HOUSEHOLD_SIZE, 0) FROM TENANT TEN WHERE TEN.TEN_ID = CON.TEN_ID);

  CREATE OR REPLACE VIEW VUE_NN_COEF_CALC (CON_ID, NNCOEF)
AS
  SELECT C.CON_ID,
    ROUND((C.CON_HOUSEHOLD_SIZE + DECODE(T.TEN_MANAGERIAL_EMP, '1', 1, 0)) / NULLIF(h.hou_room_count + DECODE(rt.rtp_technical_code, 'LAS', 1, 0),0),8) AS NNCOEF
  FROM CONTRACT C
  JOIN RENT_TYPOLOGY RT
  ON C.RTP_ID = RT.RTP_ID
  JOIN HOUSING H
  ON C.HOU_ID = H.HOU_ID
  JOIN TENANT T
  ON C.TEN_ID = T.TEN_ID;
  
CREATE OR REPLACE VIEW VUE_LNA_CALC (CON_ID, LNA)
AS
  SELECT C.CON_ID,
    ROUND(DECODE(RT.rtp_technical_code, 'LAS', LEAST(VUE_NN_COEF_CALC.NNCOEF * (VUE_LSC_CALC.LSC - VUE_SHRTTRM_CNTRCT_DSC_CALC.STCD), C.CON_LOP_RENT), 'LMA', NVL(C.CON_MARKET_RENT_PRICE,0), 'LBA', LEAST(VUE_LSC_CALC.LSC - NVL(VUE_SHRTTRM_CNTRCT_DSC_CALC.STCD,0), C.CON_LOP_RENT), NVL(C.CON_RENT_PRICE_LIMIT,0)),2) AS LNA
  FROM CONTRACT C
  JOIN RENT_TYPOLOGY RT
  ON C.RTP_ID = RT.RTP_ID
  JOIN VUE_NN_COEF_CALC
  ON C.CON_ID = VUE_NN_COEF_CALC.CON_ID
  JOIN VUE_LSC_CALC
  ON C.CON_ID = VUE_LSC_CALC.CON_ID
  JOIN VUE_SHRTTRM_CNTRCT_DSC_CALC
  ON C.CON_ID = VUE_SHRTTRM_CNTRCT_DSC_CALC.CON_ID;


CREATE OR REPLACE VIEW CONTRACT_DETAIL
	(CON_ID, CON_REFERENCE, CON_SIGNATURE, CON_START_VALIDITY_DATE, CON_END_VALIDITY_DATE, 
	 CON_MARKET_RENT_PRICE, CON_RENT_PRICE_LIMIT, CON_GARAGE_RENT, CON_GARDEN_RENT, CON_EXTRA_RENT, 
	 CON_EXPECTED_CHARGE_COST, CON_ANNUAL_CLEARANCE_CHARGES, CON_GARBAGE_INVOICING, CON_WATER_INVOICING, CON_HOUSING_TAX_INVOICING, CON_INSURANCE_REIMBURSEMENT, 
	 CON_HOUSING_TAX_REIMBURSEMENT, CON_GARBAGE_REIMBURSEMENT, CON_OTHER_INVOICING_LABEL, CON_OTHER_INVOICING_AMOUNT, 
	 CON_ADDED_WITHDRAWN_RENT, CON_LAST_WITHDRAWN_DATE, CON_TERMINATION_SAVINGS, 
	 CON_TERMINATION_SAVING_AMOUNT, CON_LAST_SAVING_DATE, CON_REAL_ESTATE_RENTAL_VALUE, HOU_ID, TEN_ID, 
	 RTP_ID, TER_ID, FIE_ID, CCE_ID, PME_ID, 
     CON_N_N_COEF, CON_NET_AGENT_RENT, CON_SHORTTERM_CONTRACT_DISCO, CON_LOP_RENT, CON_WITHDRAWN_RENT, CON_HOUSEHOLD_SIZE,
	 COD_REVISED_SURFACE_AREA, COD_REVISED_SURFACE_AREA_RENT, COD_N_N_COEF, COD_NET_AGENT_RENT, COD_SHORTTERM_CONTRACT_DISCO, 
	 COD_LOP_RENT, COD_WITHDRAWN_RENT, COD_ADDED_WITHDRAWN_RENT, COD_TERMINATION_SAVING_MONTH, COD_TERMINATION_SAVING_AMOUNT,
	 COD_CALCULATED_BENEFITS )
	AS    
  SELECT 
    C.CON_ID, C.CON_REFERENCE, C.CON_SIGNATURE, C.CON_START_VALIDITY_DATE, C.CON_END_VALIDITY_DATE, 
    C.CON_MARKET_RENT_PRICE, C.CON_RENT_PRICE_LIMIT, C.CON_GARAGE_RENT, C.CON_GARDEN_RENT, C.CON_EXTRA_RENT, 
    C.CON_EXPECTED_CHARGE_COST, C.CON_ANNUAL_CLEARANCE_CHARGES, CON_GARBAGE_INVOICING, CON_WATER_INVOICING, CON_HOUSING_TAX_INVOICING, CON_INSURANCE_REIMBURSEMENT, 
	CON_HOUSING_TAX_REIMBURSEMENT, CON_GARBAGE_REIMBURSEMENT, CON_OTHER_INVOICING_LABEL, CON_OTHER_INVOICING_AMOUNT, 
    C.CON_ADDED_WITHDRAWN_RENT, C.CON_LAST_WITHDRAWN_DATE, C.CON_TERMINATION_SAVINGS, 
    C.CON_TERMINATION_SAVING_AMOUNT, C.CON_LAST_SAVING_DATE, C.CON_REAL_ESTATE_RENTAL_VALUE, C.HOU_ID, C.TEN_ID, 
    C.RTP_ID, C.TER_ID, C.FOA_ID, C.CCE_ID, C.PME_ID ,
	C.CON_N_N_COEF, C.CON_NET_AGENT_RENT, C.CON_SHORTTERM_CONTRACT_DISCO, C.CON_LOP_RENT, C.CON_WITHDRAWN_RENT, CON_HOUSEHOLD_SIZE,    
    H.HOU_REVISED_SURFACE_AREA AS COD_REVISED_SURFACE_AREA,
    VUE_LSC_CALC.LSC AS COD_REVISED_SURFACE_AREA_RENT,
    VUE_NN_COEF_CALC.NNCOEF AS COD_N_N_COEF,
    VUE_LNA_CALC.LNA AS COD_NET_AGENT_RENT,
    VUE_SHRTTRM_CNTRCT_DSC_CALC.STCD AS COD_SHORTTERM_CONTRACT_DISCO,
    VUE_LOP_RENT_CALC.LOP_RENT AS COD_LOP_RENT,
    VUE_WITHDRAWN_RENT_CALC.WITHDRAWN_RENT AS COD_WITHDRAWN_RENT,
    c.con_added_withdrawn_rent + VUE_WITHDRAWN_RENT_CALC.WITHDRAWN_RENT AS COD_ADDED_WITHDRAWN_RENT,
    VUE_TERM_SAVING_MONTH_CALC.TERM_SAVING_MONTH AS COD_TERMINATION_SAVING_MONTH,
    c.con_termination_saving_amount + VUE_TERM_SAVING_MONTH_CALC.TERM_SAVING_MONTH AS COD_TERMINATION_SAVING_AMOUNT,
    GET_CALCULATED_BENEFIT(t.ten_actual_salary, h.hou_room_count) AS COD_CALCULATED_BENEFITS
  FROM CONTRACT C
  JOIN HOUSING H on C.HOU_ID = H.HOU_ID
  JOIN TENANT T on C.TEN_ID = T.TEN_ID
  JOIN VUE_LSC_CALC on C.CON_ID = VUE_LSC_CALC.CON_ID
  JOIN VUE_NN_COEF_CALC on C.CON_ID = VUE_NN_COEF_CALC.CON_ID
  JOIN VUE_SHRTTRM_CNTRCT_DSC_CALC on C.CON_ID = VUE_SHRTTRM_CNTRCT_DSC_CALC.CON_ID
  JOIN VUE_LOP_RENT_CALC on C.CON_ID = VUE_LOP_RENT_CALC.CON_ID
  JOIN VUE_LNA_CALC on C.CON_ID = VUE_LNA_CALC.CON_ID
  JOIN VUE_WITHDRAWN_RENT_CALC on C.CON_ID = VUE_WITHDRAWN_RENT_CALC.CON_ID
  JOIN VUE_TERM_SAVING_MONTH_CALC on C.CON_ID = VUE_TERM_SAVING_MONTH_CALC.CON_ID;
  
  
-- Modification de la table contrat : suppression d’un champ de facturation ponctuelle (remboursement taxe habitation)
ALTER TABLE CONTRACT DROP COLUMN CON_HOUSING_TAX_INVOICING;

-- Modification de la vue du calcul du loyer prélevé : prise en compte du champ en moins de facturation ponctuelle
CREATE OR REPLACE VIEW VUE_WITHDRAWN_RENT_CALC (CON_ID, WITHDRAWN_RENT) AS
      SELECT C.CON_ID,
        VUE_LNA_CALC.LNA
        + NVL(C.con_garage_rent,0)
        + (NVL(C.con_garden_rent,0) - 0.075 * NVL(C.con_garden_rent,0))
        + NVL(C.con_extra_rent,0)
        + NVL(C.con_expected_charge_cost,0)
        + NVL(C.CON_WATER_INVOICING,0)
        + NVL(C.CON_GARBAGE_INVOICING,0)
        - NVL(C.CON_INSURANCE_REIMBURSEMENT,0)
        - NVL(C.CON_HOUSING_TAX_REIMBURSEMENT,0)
        - NVL(C.CON_GARBAGE_REIMBURSEMENT,0)
        + NVL(C.CON_ANNUAL_CLEARANCE_CHARGES,0)
        + NVL(C.CON_OTHER_INVOICING_AMOUNT,0)
        AS WITHDRAWN_RENT
      FROM CONTRACT C
      JOIN VUE_LNA_CALC on C.CON_ID = VUE_LNA_CALC.CON_ID;

-- Modification de la vue du calcul du loyer prélevé : prise en compte du champ en moins de facturation ponctuelle
CREATE OR REPLACE VIEW CONTRACT_DETAIL
    (CON_ID, CON_REFERENCE, CON_SIGNATURE, CON_START_VALIDITY_DATE, CON_END_VALIDITY_DATE, 
     CON_MARKET_RENT_PRICE, CON_RENT_PRICE_LIMIT, CON_GARAGE_RENT, CON_GARDEN_RENT, CON_EXTRA_RENT, 
     CON_EXPECTED_CHARGE_COST, CON_ANNUAL_CLEARANCE_CHARGES, CON_GARBAGE_INVOICING, CON_WATER_INVOICING, CON_INSURANCE_REIMBURSEMENT, 
     CON_HOUSING_TAX_REIMBURSEMENT, CON_GARBAGE_REIMBURSEMENT, CON_OTHER_INVOICING_LABEL, CON_OTHER_INVOICING_AMOUNT, 
     CON_ADDED_WITHDRAWN_RENT, CON_LAST_WITHDRAWN_DATE, CON_TERMINATION_SAVINGS, 
     CON_TERMINATION_SAVING_AMOUNT, CON_LAST_SAVING_DATE, CON_REAL_ESTATE_RENTAL_VALUE, HOU_ID, TEN_ID, 
     RTP_ID, TER_ID, FIE_ID, CCE_ID, PME_ID, 
     CON_N_N_COEF, CON_NET_AGENT_RENT, CON_SHORTTERM_CONTRACT_DISCO, CON_LOP_RENT, CON_WITHDRAWN_RENT, CON_HOUSEHOLD_SIZE,
     COD_REVISED_SURFACE_AREA, COD_REVISED_SURFACE_AREA_RENT, COD_N_N_COEF, COD_NET_AGENT_RENT, COD_SHORTTERM_CONTRACT_DISCO, 
     COD_LOP_RENT, COD_WITHDRAWN_RENT, COD_ADDED_WITHDRAWN_RENT, COD_TERMINATION_SAVING_MONTH, COD_TERMINATION_SAVING_AMOUNT,
     COD_CALCULATED_BENEFITS )
    AS    
  SELECT 
    C.CON_ID, C.CON_REFERENCE, C.CON_SIGNATURE, C.CON_START_VALIDITY_DATE, C.CON_END_VALIDITY_DATE, 
    C.CON_MARKET_RENT_PRICE, C.CON_RENT_PRICE_LIMIT, C.CON_GARAGE_RENT, C.CON_GARDEN_RENT, C.CON_EXTRA_RENT, 
    C.CON_EXPECTED_CHARGE_COST, C.CON_ANNUAL_CLEARANCE_CHARGES, CON_GARBAGE_INVOICING, CON_WATER_INVOICING, CON_INSURANCE_REIMBURSEMENT, 
    CON_HOUSING_TAX_REIMBURSEMENT, CON_GARBAGE_REIMBURSEMENT, CON_OTHER_INVOICING_LABEL, CON_OTHER_INVOICING_AMOUNT, 
    C.CON_ADDED_WITHDRAWN_RENT, C.CON_LAST_WITHDRAWN_DATE, C.CON_TERMINATION_SAVINGS, 
    C.CON_TERMINATION_SAVING_AMOUNT, C.CON_LAST_SAVING_DATE, C.CON_REAL_ESTATE_RENTAL_VALUE, C.HOU_ID, C.TEN_ID, 
    C.RTP_ID, C.TER_ID, C.FOA_ID, C.CCE_ID, C.PME_ID ,
    C.CON_N_N_COEF, C.CON_NET_AGENT_RENT, C.CON_SHORTTERM_CONTRACT_DISCO, C.CON_LOP_RENT, C.CON_WITHDRAWN_RENT, CON_HOUSEHOLD_SIZE,    
    H.HOU_REVISED_SURFACE_AREA AS COD_REVISED_SURFACE_AREA,
    VUE_LSC_CALC.LSC AS COD_REVISED_SURFACE_AREA_RENT,
    VUE_NN_COEF_CALC.NNCOEF AS COD_N_N_COEF,
    VUE_LNA_CALC.LNA AS COD_NET_AGENT_RENT,
    VUE_SHRTTRM_CNTRCT_DSC_CALC.STCD AS COD_SHORTTERM_CONTRACT_DISCO,
    VUE_LOP_RENT_CALC.LOP_RENT AS COD_LOP_RENT,
    VUE_WITHDRAWN_RENT_CALC.WITHDRAWN_RENT AS COD_WITHDRAWN_RENT,
    c.con_added_withdrawn_rent + VUE_WITHDRAWN_RENT_CALC.WITHDRAWN_RENT AS COD_ADDED_WITHDRAWN_RENT,
    VUE_TERM_SAVING_MONTH_CALC.TERM_SAVING_MONTH AS COD_TERMINATION_SAVING_MONTH,
    c.con_termination_saving_amount + VUE_TERM_SAVING_MONTH_CALC.TERM_SAVING_MONTH AS COD_TERMINATION_SAVING_AMOUNT,
    GET_CALCULATED_BENEFIT(t.ten_actual_salary, h.hou_room_count) AS COD_CALCULATED_BENEFITS
  FROM CONTRACT C
  JOIN HOUSING H on C.HOU_ID = H.HOU_ID
  JOIN TENANT T on C.TEN_ID = T.TEN_ID
  JOIN VUE_LSC_CALC on C.CON_ID = VUE_LSC_CALC.CON_ID
  JOIN VUE_NN_COEF_CALC on C.CON_ID = VUE_NN_COEF_CALC.CON_ID
  JOIN VUE_SHRTTRM_CNTRCT_DSC_CALC on C.CON_ID = VUE_SHRTTRM_CNTRCT_DSC_CALC.CON_ID
  JOIN VUE_LOP_RENT_CALC on C.CON_ID = VUE_LOP_RENT_CALC.CON_ID
  JOIN VUE_LNA_CALC on C.CON_ID = VUE_LNA_CALC.CON_ID
  JOIN VUE_WITHDRAWN_RENT_CALC on C.CON_ID = VUE_WITHDRAWN_RENT_CALC.CON_ID
  JOIN VUE_TERM_SAVING_MONTH_CALC on C.CON_ID = VUE_TERM_SAVING_MONTH_CALC.CON_ID;
  
 -- On alloue de l'espace pour les tables vides pour qu'elles soient exportées
alter table RENT_TAX allocate extent;
alter table TVA_CODE allocate extent;
alter table ADJUSTMENT_RATE allocate extent;
alter table CHARGE_TAX allocate extent;
alter table FIELD allocate extent;
alter table FWK_ACTION_LOG allocate extent;
alter table FWK_ACTION_LOG_MESSAGE allocate extent;
alter table FWK_BATCH_HISTO allocate extent;
alter table FWK_BATCH_MESSAGE allocate extent;
alter table FWK_MAIL_REPORTING allocate extent;
alter table YL_ZN_ACC_DOC_NBR allocate extent;
alter table NC_NT_ACC_DOC_NBR allocate extent;