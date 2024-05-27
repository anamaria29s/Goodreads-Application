create audit policy audit_operations
    ACTIONS select on BOOK,
        insert on BOOK,
        update on BOOK,
        delete on BOOK,
        select on AUTHOR,
        insert on AUTHOR,
        update on AUTHOR,
        delete on AUTHOR,
        select on RATING,
        insert on RATING,
        update on RATING,
        delete on RATING,
        select on UTILIZATOR,
        insert on UTILIZATOR,
        update on UTILIZATOR,
        delete on UTILIZATOR;

audit policy audit_operations;

select object_schema,
       object_name,
       action_name,
       event_timestamp
from UNIFIED_AUDIT_TRAIL
where current_user = 'java' and UNIFIED_AUDIT_POLICIES = 'AUDIT_OPERATIONS';





-- PL/SQL STATEMENT TO PURGE UNIFIED_AUDIT_TRAIL
-- BEGIN
--     DBMS_AUDIT_MGMT.CLEAN_AUDIT_TRAIL(
--             AUDIT_TRAIL_TYPE => DBMS_AUDIT_MGMT.AUDIT_TRAIL_UNIFIED,
--             USE_LAST_ARCH_TIMESTAMP => FALSE,
--             CONTAINER => dbms_audit_mgmt.container_current);
-- END;
-- /