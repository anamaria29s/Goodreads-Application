package model;
import java.util.Scanner;
public class Utilizator {

    private int user_id;
    private String username;
    private String mail;
    private String password;

    public Utilizator(){}

    public Utilizator(int user_id, String username, String mail, String password){
        this.user_id = user_id;
        this.username = username;
        this.mail = mail;
        this.password = password;
    }

    public int getId(){
        return user_id;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getMail(){
        return mail;
    }
    public void setMail(String mail){
        this.mail = mail;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public void read() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user ID:");
        user_id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter username:");
        username = scanner.nextLine();
        System.out.println("Enter email:");
        mail = scanner.nextLine();
        System.out.println("Enter password:");
        password = scanner.nextLine();
    }
}
