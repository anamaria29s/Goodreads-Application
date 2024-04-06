package model;

public class Shelf {
    private String status;
    private Utilizator user;
    Shelf(){}

    public Shelf(String status, Utilizator user) {
        this.status = status;
        this.user = user;
    }

    public Utilizator getUser() {
        return user;
    }
}
