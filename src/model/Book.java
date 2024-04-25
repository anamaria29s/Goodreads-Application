package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public void read() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the book's ID: ");
        idBook = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the book's title: ");
        titlu = scanner.nextLine();
        System.out.println("How many authors does the book have? ");
        int numAuthors = scanner.nextInt();
        scanner.nextLine();
        authorlist = new ArrayList<>();
        for (int i = 0; i < numAuthors; i++) {
            Author author = new Author();
            author.read();
            authorlist.add(author);
        }
    }
}
