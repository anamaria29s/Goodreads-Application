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
select * from AUTHOR;

create table BOOK (
                        idBook number(6) constraint pk_bookid primary key ,
                        title varchar2(255) constraint title_null not null
);
select * from BOOK;

create table BOOKAUTHOR (
                             book_id number(6),
                             author_id number(6),
                             constraint fk_book foreign key (book_id) references BOOK(idBook),
                             constraint fk_author foreign key (author_id) references AUTHOR(idAuthor),
                             constraint pk_book_author primary key (book_id, author_id)
);

select * from BOOKAUTHOR;

create table SHELF (
                            idShelf  number(6) constraint pk_shelfid primary key,
                            user_id number(6),
                            constraint fk_userid foreign key (user_id) references utilizator(user_id)
);

create table SHELFBOOK (
                            book_id number(6),
                            shelf_id number(6),
                            status  varchar2(255) constraint status_null not null,
                            constraint fk_book2 foreign key (book_id) references BOOK(idBook),
                            constraint fk_shelf2 foreign key (shelf_id) references SHELF(idShelf),
                            constraint pk_book_shelf primary key (book_id, shelf_id)
);

drop table SHELF;
drop table SHELFBOOK;
create table RATING (
                       idRating  number(6) constraint pk_ratingid primary key,
                       nota number(6) constraint nota_null not null,
                       review  varchar2(255) constraint review_null not null,
                       user_id number(6),
                       constraint fk_userid2 foreign key (user_id) references utilizator(user_id)
);

create table AUTHORRATING (
                              author_id number(6),
                              rating_id number(6),
                              constraint fk_author3 foreign key (author_id) references AUTHOR(idAuthor),
                              constraint fk_rating2 foreign key (rating_id) references RATING(idRating),
                              constraint pk_author_rating primary key (author_id, rating_id)
);

create table BOOKRATING (
                              book_id number(6),
                              rating_id number(6),
                              constraint fk_book3 foreign key (book_id) references BOOK(idBook),
                              constraint fk_rating3 foreign key (rating_id) references RATING(idRating),
                              constraint pk_book_rating primary key (book_id, rating_id)
);