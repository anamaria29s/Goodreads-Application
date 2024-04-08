package model;

import java.util.List;

public class Book {
    private int idBook;
    private String titlu;
    private List<Author> authorlist;

    public Book(){}
    public Book(int idBook, String titlu, List<Author> authorlist){
        this.idBook = idBook;
        this.titlu = titlu;
        this.authorlist = authorlist;
    }

    public int getIdBook() {
        return idBook;
    }
    public String getTitlu(){
        return titlu;
    }
    public void setTitlu(String titlu){
        this.titlu = titlu;
    }
    public void addAuthor(Author author) {
        authorlist.add(author);
    }

    public List<Author> getAuthorlist() {
        return authorlist;
    }

    public void removeAuthor(Author author) {
        authorlist.remove(author);
    }
}
