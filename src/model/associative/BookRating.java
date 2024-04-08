package model.associative;

import model.Book;
import model.Rating;
import model.Utilizator;

public class BookRating extends Rating {
    private Book book;

    public BookRating(int idRating, int nota, String review, Utilizator user, Book book) {
        super(idRating, nota, review, user);
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getIdBook() {
        return book.getIdBook();
    }
}
