------------------------------------------------------------------------------------
--                                                                                --
--  Positionnement de toutes les proprietes necessaires a l'execution de SQLPLUS  --
--  /!\ Ne pas modifier ce script !                                               --
--  Auteur : SQLI                                                                 --
--                                                                                --
------------------------------------------------------------------------------------
--  Pas de commit si une commande exit dans les scripts
SET exitcommit OFF;
--  Pas de commit au fur et a mesure des commandes SQL
SET autocommit OFF;
--  On ignore le caractere '&'
SET define OFF;
-- Elimination des lignes vides
SET sqlblanklines ON
-- Encodage des scripts
--SET NLS_LANG=.WE8MSWIN1252
----------------------------------------------------------------------
--                                                                  --
--  Execution de tous les scripts de création de la base de données --
--                                                                  --
----------------------------------------------------------------------
@fix/pending/V02.02.00_to_V02.03.00/fix/V02.02.00_to_V02.03.00.sql
@fix/pending/V02.02.00_to_V02.03.00/fix/V02.02.00_to_V02.03.00_reasonForExit.sql
@fix/pending/V02.02.00_to_V02.03.00/fix/V02.02.00_to_V02.03.00_sites.sql
