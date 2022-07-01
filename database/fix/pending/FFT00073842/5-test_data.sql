SELECT
    hco.*
FROM
    history_tenant hte
    INNER JOIN history_contract hco ON hte.hte_id = hco.hte_id
WHERE
    hte.hte_month = 1
AND
    hte.hte_year = 2017;

---------------------- delete des tenants (17 tenants à supprimer pour février 2017)-------------------------------
DELETE FROM history_tenant hte WHERE
    hte.hte_month < 2
AND
    hte.hte_year <= 2017
OR
    hte.hte_year < 2017
OR
    hte.hte_temp = 1;

SELECT
    hte.*,
    con.con_id,
    con.con_reference,
    con.con_end_validity_date,
    con.CON_START_VALIDITY_DATE,
    con.CON_RETROACTIVITYS_MONTHS
FROM
    history_tenant hte
    JOIN history_contract hco ON hco.hte_id = hte.hte_id
    JOIN contract con ON hco.con_id = con.con_id
WHERE
    hte.hte_month < 2
AND
    hte.hte_year <= 2017
OR
    hte.hte_year < 2017
OR
    hte.hte_temp = 1;
---------------------------------------------------------------------------------------------------

---------------- delete des contrat (aucun contrat à supprimer pour février 2017) -----------------
DELETE FROM history_contract hco WHERE
    hco.hco_month < 2
AND
    hco.hco_year <= 2017
OR
    hco.hco_year < 2017
OR
    hco.hco_temp = 1;

SELECT
    *
FROM
    history_contract hco
WHERE
    hco.hco_month < 2
AND
    hco.hco_year <= 2017
OR
    hco.hco_year < 2017
OR
    hco.hco_temp = 1;
-----------------------------------------------------------------------------------------------------
-------------------- Requête permettant la détection d'incohérence d'historisation ------------------
-- Un occupant temporaire ne peut pas être rattaché à un contrat définitif, fixe
SELECT
    hte.hte_id,
    hte.ten_id,
    hco.hco_id,
    con.con_id,
    DECODE(hco.hco_temp,1,'Contrat temp.',
                        0, 'Fixe') as hco_temp,
    DECODE(hte.hte_temp,1,'Occupant temp.',
                        0, 'Fixe') as hte_temp,
    hco.hco_year,
    hco.hco_month,
    con.con_reference,
    con.CON_START_VALIDITY_DATE,
    con.con_end_validity_date,
    con.CON_RETROACTIVITYS_MONTHS,
    CASE WHEN hco.hco_temp = 0 and hte.hte_temp = 1 THEN 'A corriger'
  END
FROM
    history_tenant hte
    JOIN history_contract hco ON hco.hte_id = hte.hte_id
    JOIN contract con ON hco.con_id = con.con_id
WHERE
    (hte.hte_temp = 1)
ORDER BY
    hte.ten_id;
---------------------------------------------------------------------------------------------------

----- Requête permettant la détection de contrats à cloturer car échec des histos de février -------
SELECT
    janvier.con_id,
    con.con_reference,
    janvier.date_fin_contrat_jan,
    fevrier.date_fin_contrat_fev
FROM
    (
        SELECT
            hco.con_id,
            hco.hco_month,
            hco.hco_end_validity_date AS date_fin_contrat_jan
        FROM
            history_contract hco
        WHERE
            hco.hco_year = 2018
        AND
            hco.hco_month = 1
        AND
            hco.hco_end_validity_date IS NULL
    ) janvier
    JOIN (
            SELECT
                hco.con_id,
                hco.hco_month,
                hco.hco_end_validity_date AS date_fin_contrat_fev
            FROM
                history_contract hco
            WHERE
                hco.hco_year = 2018
            AND
                hco.hco_month = 2
            AND
                hco.hco_end_validity_date IS NOT NULL
            AND
                hco.hco_end_validity_date < TO_DATE('2018-02-01 00:00:00','YYYY-MM-DD HH24:MI:SS')
        ) fevrier ON janvier.con_id = fevrier.con_id
    JOIN contract con ON janvier.con_id = con.con_id;
---------------------------------------------------------------------------------------------------
