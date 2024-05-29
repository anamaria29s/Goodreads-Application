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
where current_user = 'java' and lower(UNIFIED_AUDIT_POLICIES) = 'audit_operations';

commit;

select * from AUTHOR;
select * from AUDIT_UNIFIED_POLICIES where lower(POLICY_NAME)= 'audit_operations';