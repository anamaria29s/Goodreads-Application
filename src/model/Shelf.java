package model;

public class Shelf {
    private int idShelf;
    private Utilizator user;
    Shelf(){}

    public Shelf(int idShelf, Utilizator user) {
        this.idShelf = idShelf;
        this.user = user;
    }

    public int getIdShelf() {
        return idShelf;
    }


    public Utilizator getUser() {
        return user;
    }
}
