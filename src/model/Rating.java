package model;

public class Rating {
    private int idRating;
    private int nota;
    private String review;
    private Utilizator user;
    public Rating(){}

    public Rating(int idRating, int nota, String review, Utilizator user) {
        this.idRating = idRating;
        this.nota = nota;
        this.review = review;
        this.user = user;
    }

    public int getIdRating() {
        return idRating;
    }

    public void setIdRating(int idRating) {
        this.idRating = idRating;
    }

    public Utilizator getUser() {
        return user;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
