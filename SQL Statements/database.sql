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

INSERT INTO UTILIZATOR (user_id, username, mail, password) VALUES (10, 'john_doe', 'john@example.com', 'password123');
INSERT INTO UTILIZATOR (user_id, username, mail, password) VALUES (11, 'jane_smith', 'jane@example.com', 'securepass');
INSERT INTO UTILIZATOR (user_id, username, mail, password) VALUES (12, 'alice_jones', 'alice@example.com', 'mypassword');
INSERT INTO UTILIZATOR (user_id, username, mail, password) VALUES (14, 'bob_brown', 'bob@example.com', 'passbob');
INSERT INTO UTILIZATOR (user_id, username, mail, password) VALUES (15, 'charlie_black', 'charlie@example.com', 'blackcharlie');
commit;

INSERT INTO AUTHOR (idAuthor, nume, prenume) VALUES (103, 'Rowling', 'J.K.');
INSERT INTO AUTHOR (idAuthor, nume, prenume) VALUES (104, 'Tolkien', 'J.R.R.');
INSERT INTO AUTHOR (idAuthor, nume, prenume) VALUES (105, 'Martin', 'George R.R.');
INSERT INTO AUTHOR (idAuthor, nume, prenume) VALUES (106, 'Hemingway', 'Ernest');


INSERT INTO BOOK (idBook, title) VALUES (1002, 'Harry Potter and the Sorcerer Stone');
INSERT INTO BOOK (idBook, title) VALUES (1003, 'The Hobbit');
INSERT INTO BOOK (idBook, title) VALUES (1005, 'A Game of Thrones');
INSERT INTO BOOK (idBook, title) VALUES (1006, 'The Old Man and the Sea');

INSERT INTO RATING (idRating, nota, review, user_id) VALUES (93, 5, 'Excellent!', 12);
INSERT INTO RATING (idRating, nota, review, user_id) VALUES (94, 4, 'Very good', 15);
INSERT INTO RATING (idRating, nota, review, user_id) VALUES (95, 3, 'Average', 13);
INSERT INTO RATING (idRating, nota, review, user_id) VALUES (96, 2, 'Not great', 14);
INSERT INTO RATING (idRating, nota, review, user_id) VALUES (97, 1, 'Terrible', 11);


INSERT INTO AUTHORRATING (author_id, rating_id) VALUES (103, 93);
INSERT INTO AUTHORRATING (author_id, rating_id) VALUES (105, 96);
INSERT INTO BOOKRATING (book_id, rating_id) VALUES (1007, 97);
INSERT INTO BOOKRATING (book_id, rating_id) VALUES (1004, 95);
INSERT INTO BOOKRATING (book_id, rating_id) VALUES (1006, 94);


INSERT INTO SHELF (idShelf, user_id) VALUES (90, 10);
INSERT INTO SHELF (idShelf, user_id) VALUES (91, 11);
INSERT INTO SHELF (idShelf, user_id) VALUES (92, 12);
INSERT INTO SHELF (idShelf, user_id) VALUES (94, 14);
INSERT INTO SHELF (idShelf, user_id) VALUES (95, 15);
