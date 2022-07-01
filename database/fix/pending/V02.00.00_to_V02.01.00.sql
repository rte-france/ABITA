UPDATE FWK_PARAMETER SET PRM_VALUE = '02.01.00' WHERE PRM_ID = 3041;

-- ajout des champs Reglement du pecule de fin d'occupation et date de reglement du pecule de fin d'occupation (FFT00056322)
ALTER TABLE CONTRACT ADD (
    CON_TERM_SAVINGS_PAYMENT VARCHAR2(1 CHAR) DEFAULT 0
);
COMMENT ON COLUMN CONTRACT.CON_TERM_SAVINGS_PAYMENT IS 'Règlement du pécule de fin d''occupation';

ALTER TABLE CONTRACT ADD (
    CON_TERM_SAVINGS_PAYMENT_DATE DATE
);
COMMENT ON COLUMN CONTRACT.CON_TERM_SAVINGS_PAYMENT_DATE IS 'Date du règlement du pécule de fin d''occupation';



-- FFT00055218
ALTER TABLE CONTRACT ADD (
  CON_AGC_ID_FIX NUMBER(6,0)
);
COMMENT ON COLUMN CONTRACT.CON_AGC_ID_FIX IS 'Agence si contrat clos';

ALTER TABLE CONTRACT ADD (
  CON_REVISED_SURF_AREA_FIX NUMBER(6,2)
);
COMMENT ON COLUMN CONTRACT.CON_REVISED_SURF_AREA_FIX IS 'Surface corrigée si contrat clos';

ALTER TABLE CONTRACT ADD (
  CON_REVISED_SURF_AREA_RENT_FIX NUMBER(9,2)
);
COMMENT ON COLUMN CONTRACT.CON_REVISED_SURF_AREA_RENT_FIX IS 'Loyer surface corrigée si contrat clos';

-- colonne deja existante mais inutilisée, on la renomme par soucis de cohérence
ALTER TABLE CONTRACT RENAME COLUMN CON_N_N_COEF TO CON_N_N_COEF_FIX;
COMMENT ON COLUMN CONTRACT.CON_N_N_COEF_FIX IS 'Coeficient n/N si contrat clos';

ALTER TABLE CONTRACT ADD (
  CON_LOP_RENT_FIX NUMBER(10,3)
);
COMMENT ON COLUMN CONTRACT.CON_LOP_RENT_FIX IS 'Loyer écrêté si contrat clos';

ALTER TABLE CONTRACT ADD (
  CON_BENEFITS_FIX NUMBER(10,3)
);
COMMENT ON COLUMN CONTRACT.CON_BENEFITS_FIX IS 'Avantage en nature si contrat clos';

ALTER TABLE CONTRACT ADD (
  CON_CLOSED VARCHAR2(1 CHAR) DEFAULT 0
);
COMMENT ON COLUMN CONTRACT.CON_CLOSED IS 'Indicateur de contrat clos';

CREATE OR REPLACE FORCE VIEW CONTRACT_DETAIL (
  CON_ID,
  CON_REFERENCE,
  CON_SIGNATURE,
  CON_START_VALIDITY_DATE,
  CON_END_VALIDITY_DATE,
  CON_MARKET_RENT_PRICE,
  CON_RENT_PRICE_LIMIT,
  CON_GARAGE_RENT,
  CON_GARDEN_RENT,
  CON_EXTRA_RENT,
  CON_EXPECTED_CHARGE_COST,
  CON_ANNUAL_CLEARANCE_CHARGES,
  CON_GARBAGE_INVOICING,
  CON_WATER_INVOICING,
  CON_INSURANCE_REIMBURSEMENT,
  CON_HOUSING_TAX_REIMBURSEMENT,
  CON_GARBAGE_REIMBURSEMENT,
  CON_OTHER_INVOICING_LABEL,
  CON_OTHER_INVOICING_AMOUNT,
  CON_ADDED_WITHDRAWN_RENT,
  CON_LAST_WITHDRAWN_DATE,
  CON_TERMINATION_SAVINGS,
  CON_TERMINATION_SAVING_AMOUNT,
  CON_TERM_SAVINGS_PAYMENT,
  CON_TERM_SAVINGS_PAYMENT_DATE,
  CON_LAST_SAVING_DATE,
  CON_REAL_ESTATE_RENTAL_VALUE,
  CON_RETROACTIVITYS_MONTHS,
  HOU_ID,
  TEN_ID,
  RTP_ID,
  TER_ID,
  FIE_ID,
  CCE_ID,
  PME_ID,
  CON_NET_AGENT_RENT,
  CON_SHORTTERM_CONTRACT_DISCO,
  CON_LOP_RENT,
  CON_WITHDRAWN_RENT,
  CON_HOUSEHOLD_SIZE,
  CON_AGC_ID_FIX,
  CON_REVISED_SURF_AREA_FIX,
  CON_REVISED_SURF_AREA_RENT_FIX,
  CON_N_N_COEF_FIX,
  CON_LOP_RENT_FIX,
  CON_BENEFITS_FIX,
  CON_CLOSED,
  COD_REVISED_SURFACE_AREA,
  COD_REVISED_SURFACE_AREA_RENT,
  COD_N_N_COEF,
  COD_NET_AGENT_RENT,
  COD_SHORTTERM_CONTRACT_DISCO,
  COD_LOP_RENT,
  COD_WITHDRAWN_RENT,
  COD_ADDED_WITHDRAWN_RENT,
  COD_TERMINATION_SAVING_MONTH,
  COD_TERMINATION_SAVING_AMOUNT,
  COD_CALCULATED_BENEFITS)
AS
  SELECT
    C.CON_ID,
    C.CON_REFERENCE,
    C.CON_SIGNATURE,
    C.CON_START_VALIDITY_DATE,
    C.CON_END_VALIDITY_DATE,
    C.CON_MARKET_RENT_PRICE,
    C.CON_RENT_PRICE_LIMIT,
    C.CON_GARAGE_RENT,
    C.CON_GARDEN_RENT,
    C.CON_EXTRA_RENT,
    C.CON_EXPECTED_CHARGE_COST,
    C.CON_ANNUAL_CLEARANCE_CHARGES,
    CON_GARBAGE_INVOICING,
    CON_WATER_INVOICING,
    CON_INSURANCE_REIMBURSEMENT,
    CON_HOUSING_TAX_REIMBURSEMENT,
    CON_GARBAGE_REIMBURSEMENT,
    CON_OTHER_INVOICING_LABEL,
    CON_OTHER_INVOICING_AMOUNT,
    C.CON_ADDED_WITHDRAWN_RENT,
    C.CON_LAST_WITHDRAWN_DATE,
    C.CON_TERMINATION_SAVINGS,
    C.CON_TERMINATION_SAVING_AMOUNT,
    C.CON_TERM_SAVINGS_PAYMENT,
    C.CON_TERM_SAVINGS_PAYMENT_DATE,
    C.CON_LAST_SAVING_DATE,
    C.CON_REAL_ESTATE_RENTAL_VALUE,
    C.CON_RETROACTIVITYS_MONTHS,
    C.HOU_ID,
    C.TEN_ID,
    C.RTP_ID,
    C.TER_ID,
    C.FOA_ID,
    C.CCE_ID,
    C.PME_ID,
    C.CON_NET_AGENT_RENT,
    C.CON_SHORTTERM_CONTRACT_DISCO,
    C.CON_LOP_RENT,
    C.CON_WITHDRAWN_RENT,
    C.CON_HOUSEHOLD_SIZE,
    C.CON_AGC_ID_FIX,
    C.CON_REVISED_SURF_AREA_FIX,
    C.CON_REVISED_SURF_AREA_RENT_FIX,
    C.CON_N_N_COEF_FIX,
    C.CON_LOP_RENT_FIX,
    C.CON_BENEFITS_FIX,
    C.CON_CLOSED,
    H.HOU_REVISED_SURFACE_AREA                                                     AS COD_REVISED_SURFACE_AREA,
    VUE_LSC_CALC.LSC                                                               AS COD_REVISED_SURFACE_AREA_RENT,
    VUE_NN_COEF_CALC.NNCOEF                                                        AS COD_N_N_COEF,
    VUE_LNA_CALC.LNA                                                               AS COD_NET_AGENT_RENT,
    VUE_SHRTTRM_CNTRCT_DSC_CALC.STCD                                               AS COD_SHORTTERM_CONTRACT_DISCO,
    VUE_LOP_RENT_CALC.LOP_RENT                                                     AS COD_LOP_RENT,
    VUE_WITHDRAWN_RENT_CALC.WITHDRAWN_RENT                                         AS COD_WITHDRAWN_RENT,
    c.con_added_withdrawn_rent + VUE_WITHDRAWN_RENT_CALC.WITHDRAWN_RENT            AS COD_ADDED_WITHDRAWN_RENT,
    VUE_TERM_SAVING_MONTH_CALC.TERM_SAVING_MONTH                                   AS COD_TERMINATION_SAVING_MONTH,
    c.con_termination_saving_amount + VUE_TERM_SAVING_MONTH_CALC.TERM_SAVING_MONTH AS COD_TERMINATION_SAVING_AMOUNT,
    GET_CALCULATED_BENEFIT(t.ten_actual_salary, h.hou_room_count)                  AS COD_CALCULATED_BENEFITS
  FROM CONTRACT C
    JOIN HOUSING H
      ON C.HOU_ID = H.HOU_ID
    JOIN TENANT T
      ON C.TEN_ID = T.TEN_ID
    JOIN VUE_LSC_CALC
      ON C.CON_ID = VUE_LSC_CALC.CON_ID
    JOIN VUE_NN_COEF_CALC
      ON C.CON_ID = VUE_NN_COEF_CALC.CON_ID
    JOIN VUE_SHRTTRM_CNTRCT_DSC_CALC
      ON C.CON_ID = VUE_SHRTTRM_CNTRCT_DSC_CALC.CON_ID
    JOIN VUE_LOP_RENT_CALC
      ON C.CON_ID = VUE_LOP_RENT_CALC.CON_ID
    JOIN VUE_LNA_CALC
      ON C.CON_ID = VUE_LNA_CALC.CON_ID
    JOIN VUE_WITHDRAWN_RENT_CALC
      ON C.CON_ID = VUE_WITHDRAWN_RENT_CALC.CON_ID
    JOIN VUE_TERM_SAVING_MONTH_CALC
      ON C.CON_ID = VUE_TERM_SAVING_MONTH_CALC.CON_ID;

-- Ajout de l'agence pour les contrats tiers
ALTER TABLE THIRD_PARTY_CONTRACT ADD (
  TPC_AGC_ID_FIX NUMBER(6,0)
);
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.TPC_AGC_ID_FIX IS 'Agence si contrat clos';

ALTER TABLE THIRD_PARTY_CONTRACT ADD (
  TPC_CLOSED VARCHAR2(1 CHAR) DEFAULT 0
);
COMMENT ON COLUMN THIRD_PARTY_CONTRACT.TPC_CLOSED IS 'Indicateur de contrat clos';

-- mise à jour des contrats occupant clos
UPDATE CONTRACT SET CON_CLOSED = 1 WHERE CON_ID IN (
  SELECT
    contrat.CON_ID
  FROM
    CONTRACT contrat,
    (SELECT
      -- seul la dernière génération importe
      GREATEST(
        -- on récupère ici le jour de déclenchement des 8 générations
        SUBSTR(DET_NC, 0, INSTR(DET_NC, ' ')-1),
        SUBSTR(DET_NT, 0, INSTR(DET_NT, ' ')-1),
        SUBSTR(DET_NNI, 0, INSTR(DET_NNI, ' ')-1),
        SUBSTR(DET_USER_DATA, 0, INSTR(DET_USER_DATA, ' ')-1),
        SUBSTR(DET_SALARY_RETAINED, 0, INSTR(DET_SALARY_RETAINED, ' ')-1),
        SUBSTR(DET_FRINGE_BENEFITS, 0, INSTR(DET_FRINGE_BENEFITS, ' ')-1)
      ) AS dernierJour
      FROM detail_cron
      WHERE det_id = to_char(sysdate, 'mm')) generation
  WHERE
    -- si la date de fin de validité du contrat
    CON_END_VALIDITY_DATE <
      -- si la dernière generation du mois courant n'est pas encore passée,
      -- on ne met à jour que les contrats dont la date de fin de validité précède le premier jour du mois courant
      -- sinon, si la dernière génération du mois courant est déjà passée, il faut mettre à jour tous les contrats
      -- dont la date de fin de validité est précède le premier jour du mois suivant
      CASE WHEN generation.dernierJour > to_char(sysdate, 'dd')
        THEN trunc(sysdate,'MONTH')
        ELSE add_months(trunc(sysdate,'MONTH'),1)
      END
  )
;

-- Mise à jour des champs pour les contrats occupants clos
UPDATE CONTRACT_DETAIL COD SET COD.CON_AGC_ID_FIX = (SELECT HOU.AGC_ID FROM HOUSING HOU INNER JOIN CONTRACT CON ON CON.HOU_ID = HOU.HOU_ID WHERE COD.CON_ID = CON.CON_ID), COD.CON_BENEFITS_FIX = COD.COD_CALCULATED_BENEFITS,
COD.CON_LOP_RENT_FIX = COD.CON_LOP_RENT, COD.CON_N_N_COEF_FIX = COD.COD_N_N_COEF, COD.CON_REVISED_SURF_AREA_FIX = COD.COD_REVISED_SURFACE_AREA, COD.CON_REVISED_SURF_AREA_RENT_FIX = COD.COD_REVISED_SURFACE_AREA_RENT
WHERE COD.CON_CLOSED = 1;

-- mise à jour des contrats tiers mensuels à échoir clos
UPDATE THIRD_PARTY_CONTRACT SET TPC_CLOSED = 1 WHERE TPC_ID IN (
  SELECT
    tpc.TPC_ID
  FROM
    THIRD_PARTY_CONTRACT tpc,
    (SELECT
      -- seul la dernière génération importe
      GREATEST(
        -- on récupère ici le jour de déclenchement des 2 générations
        SUBSTR(DET_YL, 0, INSTR(DET_YL, ' ')-1),
        SUBSTR(DET_ZN, 0, INSTR(DET_ZN, ' ')-1)
      ) AS dernierJour
      FROM detail_cron
      WHERE det_id = to_char(sysdate, 'mm')) generation
  WHERE
    -- si la date de résiliation du contrat mensuel échu
    tpc.TPC_CANCELLATION_DATE <
      -- si la dernière generation du mois courant n'est pas encore passée,
      -- on ne met à jour que les contrats dont la date de fin de validité précède le premier jour du mois courant
      -- sinon, si la dernière génération du mois courant est déjà passée, il faut mettre à jour tous les contrats
      -- dont la date de fin de validité est précède le premier jour du mois suivant
      CASE WHEN generation.dernierJour > to_char(sysdate, 'dd')
        THEN trunc(sysdate,'MONTH')
        ELSE add_months(trunc(sysdate,'MONTH'),1)
      END
    AND tpc.PCY_ID = 1
    AND tpc.TPC_EXPIRY_DATE = 1
  )
;

-- mise à jour des contrats tiers mensuels échu clos
UPDATE THIRD_PARTY_CONTRACT SET TPC_CLOSED = 1 WHERE TPC_ID IN (
  SELECT
    tpc.TPC_ID
  FROM
    THIRD_PARTY_CONTRACT tpc,
    (SELECT
      -- seul la dernière génération importe
      GREATEST(
        -- on récupère ici le jour de déclenchement des 2 générations
        SUBSTR(DET_YL, 0, INSTR(DET_YL, ' ')-1),
        SUBSTR(DET_ZN, 0, INSTR(DET_ZN, ' ')-1)
      ) AS dernierJour
      FROM detail_cron
      WHERE det_id = to_char(sysdate, 'mm')) generation
  WHERE
    -- si la date de résiliation du contrat mensuel à échoir
    tpc.TPC_CANCELLATION_DATE <
      -- si la dernière generation du mois courant n'est pas encore passée,
      -- on ne met à jour que les contrats dont la date de fin de validité précède le premier jour du mois suivant
      -- sinon, si la dernière génération du mois courant est déjà passée, il faut mettre à jour tous les contrats
      -- dont la date de fin de validité est précède le premier jour du mois suivant le mois suviant
      CASE WHEN generation.dernierJour > to_char(sysdate, 'dd')
        THEN add_months(trunc(sysdate,'MONTH'),1)
        ELSE add_months(trunc(sysdate,'MONTH'),2)
      END
    AND tpc.PCY_ID = 1
    AND tpc.TPC_EXPIRY_DATE = 0
  )
;

-- mise à jour des contrats tiers trimestriels clos
UPDATE THIRD_PARTY_CONTRACT SET TPC_CLOSED = 1 WHERE TPC_ID IN (
  SELECT
    tpc.TPC_ID
  FROM
    THIRD_PARTY_CONTRACT tpc,
    (SELECT
      -- seul la dernière génération importe
      GREATEST(
        -- on récupère ici le jour de déclenchement des 2 générations
        SUBSTR(DET_YL, 0, INSTR(DET_YL, ' ')-1),
        SUBSTR(DET_ZN, 0, INSTR(DET_ZN, ' ')-1)
      ) AS dernierJour
      FROM detail_cron
      WHERE det_id = to_char(sysdate, 'mm')) generation
  WHERE
    -- si la date de résiliation du contrat est avant le 01/01/17, le contrat est clos si il est trimestriel
    tpc.TPC_CANCELLATION_DATE < TO_DATE('01/01/2017', 'dd/MM/yyyy')
    AND tpc.PCY_ID = 2
  )
;

-- mise à jour des contrats tiers annuels clos
UPDATE THIRD_PARTY_CONTRACT SET TPC_CLOSED = 1 WHERE TPC_ID IN (
  SELECT
    tpc.TPC_ID
  FROM
    THIRD_PARTY_CONTRACT tpc,
    (SELECT
      -- seul la dernière génération importe
      GREATEST(
        -- on récupère ici le jour de déclenchement des 2 générations
        SUBSTR(DET_YL, 0, INSTR(DET_YL, ' ')-1),
        SUBSTR(DET_ZN, 0, INSTR(DET_ZN, ' ')-1)
      ) AS dernierJour
      FROM detail_cron
      WHERE det_id = to_char(sysdate, 'mm')) generation
  WHERE
    -- si la date de résiliation du contrat est avant le 01/01/17, le contrat est clos si il est annuel
    tpc.TPC_CANCELLATION_DATE < TO_DATE('01/01/2017', 'dd/MM/yyyy')
    AND tpc.PCY_ID = 3
  )
;

-- Mise à jour des champs pour les contrats tiers clos
UPDATE THIRD_PARTY_CONTRACT TPC SET TPC.TPC_AGC_ID_FIX = (SELECT HOU.AGC_ID FROM HOUSING HOU INNER JOIN THIRD_PARTY_CONTRACT THC ON THC.HOU_ID = HOU.HOU_ID WHERE TPC.TPC_ID = THC.TPC_ID)
WHERE TPC.TPC_CLOSED = 1;

-- Mise à jour de la vue des abattements de précarité afin de se baser sur les données historisées si le contrat est clos
CREATE OR REPLACE VIEW VUE_SHRTTRM_CNTRCT_DSC_CALC (CON_ID, STCD) AS
  SELECT C.CON_ID,
  CASE WHEN C.CON_CLOSED = 1
  THEN ROUND(C.CON_REVISED_SURF_AREA_RENT_FIX * 0.075,2)
  ELSE ROUND(LSC_C.LSC * 0.075,2)
  END AS STCD
  FROM CONTRACT C
  JOIN VUE_LSC_CALC LSC_C
  ON C.CON_ID = LSC_C.CON_ID;

-- Mise à jour de la vue des loyers net agent afin de se baser sur les données historisées si le contrat est clos
CREATE OR REPLACE VIEW VUE_LNA_CALC (CON_ID, LNA) AS
  SELECT C.CON_ID,
    CASE WHEN C.CON_CLOSED = 1
    THEN ROUND(DECODE(RT.rtp_technical_code, 'LAS', LEAST(C.CON_N_N_COEF_FIX * (C.CON_REVISED_SURF_AREA_RENT_FIX - VUE_SHRTTRM_CNTRCT_DSC_CALC.STCD), C.CON_LOP_RENT), 'LMA', NVL(C.CON_MARKET_RENT_PRICE,0), 'LBA', LEAST(C.CON_REVISED_SURF_AREA_FIX - NVL(VUE_SHRTTRM_CNTRCT_DSC_CALC.STCD,0), C.CON_LOP_RENT_FIX), NVL(C.CON_RENT_PRICE_LIMIT,0)),2)
    ELSE ROUND(DECODE(RT.rtp_technical_code, 'LAS', LEAST(VUE_NN_COEF_CALC.NNCOEF * (VUE_LSC_CALC.LSC - VUE_SHRTTRM_CNTRCT_DSC_CALC.STCD), C.CON_LOP_RENT), 'LMA', NVL(C.CON_MARKET_RENT_PRICE,0), 'LBA', LEAST(VUE_LSC_CALC.LSC - NVL(VUE_SHRTTRM_CNTRCT_DSC_CALC.STCD,0), C.CON_LOP_RENT), NVL(C.CON_RENT_PRICE_LIMIT,0)),2)
    END AS LNA
  FROM CONTRACT C
  JOIN RENT_TYPOLOGY RT
  ON C.RTP_ID = RT.RTP_ID
  JOIN VUE_NN_COEF_CALC
  ON C.CON_ID = VUE_NN_COEF_CALC.CON_ID
  JOIN VUE_LSC_CALC
  ON C.CON_ID = VUE_LSC_CALC.CON_ID
  JOIN VUE_SHRTTRM_CNTRCT_DSC_CALC
  ON C.CON_ID = VUE_SHRTTRM_CNTRCT_DSC_CALC.CON_ID;

COMMIT;
