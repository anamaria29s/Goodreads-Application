create table UTILIZATOR (
                            user_id number(6) constraint pk_userid primary key ,
                            username varchar2(50) constraint username_null not null ,
                            mail varchar2(255) constraint mail_null not null ,
                            password varchar2(255) constraint parola_null not null
);

select * from UTILIZATOR;
