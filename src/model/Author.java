package model;

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

}
