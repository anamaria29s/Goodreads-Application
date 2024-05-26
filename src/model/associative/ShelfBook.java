package model.associative;

import model.Status;

public class ShelfBook{
    private int idBook;
    private int idShelf;
    private Status status;

    public ShelfBook(int idBook, int idShelf, Status status) {
        this.idBook = idBook;
        this.idShelf = idShelf;
        this.status = status;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public int getIdShelf() {
        return idShelf;
    }

    public void setIdShelf(int idShelf) {
        this.idShelf = idShelf;
    }


}
