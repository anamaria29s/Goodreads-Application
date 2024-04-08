package model;

public class Shelf {
    private int idShelf;
    private Status status;
    private Utilizator user;
    Shelf(){}

    public Shelf(int idShelf, Status status, Utilizator user) {
        this.idShelf = idShelf;
        this.status = status;
        this.user = user;
    }

    public int getIdShelf() {
        return idShelf;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Utilizator getUser() {
        return user;
    }
}
