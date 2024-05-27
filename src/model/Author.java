package model;

import java.util.Scanner;

public class Author {
    private int idAuthor;
    private String nume;
    private String prenume;

    public Author(){}
    public Author(int idAuthor,String nume, String prenume){
        this.idAuthor = idAuthor;
        this.nume = nume;
        this.prenume = prenume;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getNume(){
        return nume;
    }
    public void setNume(String nume){
        this.nume = nume;
    }
    public String getPrenume(){
        return prenume;
    }
    public void setPrenume(String prenume){
        this.prenume = prenume;
    }

    public void read() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the author's ID: ");
        this.idAuthor = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter the author's last name: ");
        this.nume = scanner.nextLine();
        System.out.println("Enter the author's first name: ");
        this.prenume = scanner.nextLine();
    }

    public void read2() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the author's ID: ");
        this.idAuthor = scanner.nextInt();
    }
}
