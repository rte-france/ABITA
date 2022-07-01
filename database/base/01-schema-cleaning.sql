SET serveroutput ON;
CREATE OR REPLACE Procedure SCHEMA_CLEANING
IS
	current_user varchar2(255);
BEGIN
	SELECT user INTO current_user FROM dual;
	IF current_user = 'SYS' THEN
	  DBMS_OUTPUT.put_line('/!\ Vous ne devez pas passer ce script en tant que SYSDBA. Ce script doit etre passe sur un schema adequat.');
  	ELSE
	  DBMS_OUTPUT.put_line('Execution de la purge des objets sur le schema ' || current_user || '.');
	  BEGIN
		  FOR cur_rec IN (SELECT object_name, object_type
		                  FROM   user_objects
		                  WHERE  object_type IN ('TABLE', 'VIEW', 'PACKAGE', 'PROCEDURE', 'FUNCTION', 'SEQUENCE')) LOOP
		    BEGIN
		      IF cur_rec.object_type = 'TABLE' THEN
		        EXECUTE IMMEDIATE 'DROP ' || cur_rec.object_type || ' "' || cur_rec.object_name || '" CASCADE CONSTRAINTS';
		      ELSE
		        EXECUTE IMMEDIATE 'DROP ' || cur_rec.object_type || ' "' || cur_rec.object_name || '"';
		      END IF;
		    EXCEPTION
		      WHEN OTHERS THEN
		        DBMS_OUTPUT.put_line('FAILED: DROP ' || cur_rec.object_type || ' "' || cur_rec.object_name || '"');
		    END;
		  END LOOP;
	  END;
	END IF;
END;
/
EXECUTE SCHEMA_CLEANING();
