package model;

public class BookRating extends Rating{
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
}
