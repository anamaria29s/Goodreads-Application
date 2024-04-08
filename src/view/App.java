package view;

import model.associative.BookAuthor;
import model.associative.AuthorRating;
import model.associative.BookRating;
import model.associative.ShelfBook;
import model.Author;
import model.Utilizator;
import model.Rating;
import model.Book;
import model.Genre;
import model.Shelf;
import model.Status;
import persistence.*;
import service.DatabaseConnection;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class App {
    private static App instance;
    private DatabaseConnection db;
    private UtilizatorRepository utilizatorRepository;
    private BookRatingRepository bookRepository;
    private AuthorRepository authorRepository;
    private ShelfRepository shelfRepository;
    private ShelfBookRepository shelfBookRepository;
    private RatingRepository ratingRepository;
    private BookRatingRepository bookRatingRepository;
    private AuthorRatingRepository authorRatingRepository;

    public App() {
        db = DatabaseConnection.getInstance();
        utilizatorRepository = new UtilizatorRepository(db);
        bookRepository = new BookRating(db);
        authorRepository = new AuthorRepository(db);
        shelfRepository = new ShelfRepository(db);
        shelfBookRepository = new ShelfBookRepository(db);
        ratingRepository = new RatingRepository(db);
        bookRatingRepository = new BookRatingRepository(db);
        authorRatingRepository = new AuthorRatingRepository(db);
    }
    public static App getInstance() {
        if (instance == null)
            instance = new App();

        return instance;
    }
}
