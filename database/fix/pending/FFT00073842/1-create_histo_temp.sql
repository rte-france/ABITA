-- script de test pour FFT00073842
CREATE TABLE HISTORY_TENANT_2018 AS SELECT * FROM HISTORY_TENANT;

create or replace PROCEDURE HISTORIZE_TENANT_2018 (
    PN$MONTH IN HISTORY_TENANT.HTE_MONTH%TYPE,
    PN$YEAR IN HISTORY_TENANT.HTE_YEAR%TYPE
) IS
    CURSOR TenantData IS
        SELECT *
        FROM TENANT TEN
        INNER JOIN TYPE_TENANT TTE ON TEN.TTE_ID = TTE.TTE_ID;
BEGIN
    FOR oTenant IN TenantData LOOP
        INSERT INTO HISTORY_TENANT_2018 (
            HTE_ID,
            TEN_ID,
            HTE_MONTH,
            HTE_YEAR,
            HTE_REFERENCE,
            HTE_MANAGERIAL_EMP,
            HTE_TTE_NT_HEADER_LABEL,
            HTE_ACTUAL_SALARY,
            HTE_REFERENCE_GROSS_SALARY,
            HTE_TEMP
        )
        VALUES(
            SEQ_HISTORY_TENANT.nextVal,
            oTenant.TEN_ID,
            PN$MONTH,
            PN$YEAR,
            oTenant.TEN_REFERENCE,
            oTenant.TEN_MANAGERIAL_EMP,
            oTenant.TTE_NT_HEADER_LABEL,
            oTenant.TEN_ACTUAL_SALARY,
            oTenant.TEN_REFERENCE_GROSS_SALARY,
            0
        );
    END LOOP;
END;
/

DECLARE
  PN$MONTH NUMBER;
  PN$YEAR NUMBER;
BEGIN
  PN$MONTH := 2;
  PN$YEAR := 2018;

  HISTORIZE_TENANT_2018 (
    PN$MONTH => PN$MONTH,
    PN$YEAR => PN$YEAR
  );
END;
/

EXIT;
