create table UTILIZATOR (
                            user_id number(6) constraint pk_userid primary key ,
                            username varchar2(50) constraint username_null not null ,
                            mail varchar2(255) constraint mail_null not null ,
                            password varchar2(255) constraint parola_null not null
);

select * from UTILIZATOR;

create table AUTHOR (
                            idAuthor number(6) constraint pk_authorid primary key ,
                            nume varchar2(255) constraint nume_null not null ,
                            prenume varchar2(255) constraint prenume_null not null
);

create table BOOK (
                        idBook number(6) constraint pk_bookid primary key ,
                        title varchar2(255) constraint title_null not null
);

CREATE TABLE BOOKAUTHOR (
                             book_id NUMBER(6),
                             author_id NUMBER(6),
                             CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES BOOK(idBook),
                             CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES AUTHOR(idAuthor),
                             CONSTRAINT pk_book_author PRIMARY KEY (book_id, author_id)
);


CREATE TABLE SHELF (
                            idShelf  number(6) constraint pk_shelfid primary key,
                            status  varchar2(255) constraint status_null not null,
                            user_id number(6),
                            constraint fk_userid foreign key (user_id) references utilizator(user_id)
);

CREATE TABLE SHELFBOOK (
                            book_id NUMBER(6),
                            shelf_id NUMBER(6),
                            CONSTRAINT fk_book2 FOREIGN KEY (book_id) REFERENCES BOOK(idBook),
                            CONSTRAINT fk_shelf2 FOREIGN KEY (shelf_id) REFERENCES SHELF(idShelf),
                            CONSTRAINT pk_book_shelf PRIMARY KEY (book_id, shelf_id)
);