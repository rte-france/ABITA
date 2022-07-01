-- execution des scripts de base prerequis
@base/prerequisite.sql
-------------------------------------------------------------------------
--                                                                     --
--  Execution des scripts SQL.                                         --
--  /!\ Les nouveaux scripts a integrer dans les futures livraisons    --
--  doivent toujours etre ajoutes ici !                                --
--  CONSEIL : Les scripts doivent toujours etre ajoutes les uns        --
--  a la suite des autres.                                             --
--                                                                     --
-------------------------------------------------------------------------
-- Ceci est un premier script d'exemple, utilisé pour déclarer les droits de la nouvelle application
@updates/01-access-rights-abita-1.0.0.0.sql
@updates/02-data-abita-1.0.0.0.sql
