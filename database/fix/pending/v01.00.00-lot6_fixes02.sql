-- Mise à jour des champs fixes par les champs calculés
UPDATE CONTRACT_DETAIL CON
SET CON.CON_N_N_COEF = CON.COD_N_N_COEF
, CON.CON_LOP_RENT = CON.COD_LOP_RENT
, CON.CON_SHORTTERM_CONTRACT_DISCO = CON.COD_SHORTTERM_CONTRACT_DISCO;	