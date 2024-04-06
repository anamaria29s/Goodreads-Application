package model;

public class AuthorRating extends Rating{
    private Author author;

    public AuthorRating(int idRating, int nota, String review, Utilizator user, Author author) {
        super(idRating, nota, review, user);
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
