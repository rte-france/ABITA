------------------------------------------------------------------------------------
--                                                                                --
--  Positionnement de toutes les proprietes necessaires a l'execution de SQLPLUS  --
--  /!\ Ne pas modifier ce script !                                               --                                                                 --
--                                                                                --
------------------------------------------------------------------------------------
--  Pas de commit si une commande exit dans les scripts
SET exitcommit OFF;
--  Pas de commit au fur et a mesure des commandes SQL
SET autocommit OFF;
--  On ignore le caractere '&'
SET define OFF;
---------------------------------------------
--                                         --
--  Execution de tous les scripts de base  --
--                                         --
---------------------------------------------
@base/01-schema-cleaning.sql
@base/02-install-framework-1.0.0.0.sql
@base/03-install-abita-1.0.0.0.sql
