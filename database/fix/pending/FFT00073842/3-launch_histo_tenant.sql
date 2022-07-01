-- FFT00073842 : Exécution de la procédure posant problème puis ROLLBACK à la fin (aucune donnée impactée)
-- L'exécution de ce script a permis de détecter une donnée corrompue dans la table HISTORY_TENANT
SET SERVEROUTPUT ON
DECLARE
  PN$MONTH NUMBER;
  PN$YEAR NUMBER;
  line_inserted NUMBER := 0;
BEGIN
  PN$MONTH := 2;
  PN$YEAR := 9999;

  HISTORIZE_TENANT (
    PN$MONTH => PN$MONTH,
    PN$YEAR => PN$YEAR
  );

  SELECT count(*) INTO line_inserted FROM HISTORY_TENANT WHERE HTE_YEAR = 9999;
  DBMS_OUTPUT.PUT_LINE('Historisation reussie. Lignes historisees : ' || TO_CHAR (line_inserted));
  ROLLBACK;
END;
/
