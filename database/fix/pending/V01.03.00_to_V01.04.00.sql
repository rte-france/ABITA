UPDATE FWK_PARAMETER SET PRM_VALUE = '01.04.00' WHERE PRM_ID = 3041;

-- Insertion des 12 mois de génération
INSERT INTO DETAIL_CRON SELECT '2', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;
INSERT INTO DETAIL_CRON SELECT '3', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;
INSERT INTO DETAIL_CRON SELECT '4', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;
INSERT INTO DETAIL_CRON SELECT '5', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;
INSERT INTO DETAIL_CRON SELECT '6', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;
INSERT INTO DETAIL_CRON SELECT '7', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;
INSERT INTO DETAIL_CRON SELECT '8', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;
INSERT INTO DETAIL_CRON SELECT '9', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;
INSERT INTO DETAIL_CRON SELECT '10', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;
INSERT INTO DETAIL_CRON SELECT '11', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;
INSERT INTO DETAIL_CRON SELECT '12', det_yl, det_zn, det_nc, det_nt, det_nni, det_user_data, det_salary_retained, det_fringe_benefits FROM DETAIL_CRON WHERE DET_ID = 1;


-- Modification de la table des agences : nouvelle colonne concernant l’agence
ALTER TABLE FIELD_OF_ACTIVITY ADD (
    AGC_ID NUMBER(6,0) DEFAULT 1 NOT NULL
);
