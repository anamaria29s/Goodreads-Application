import service.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        db.testConnection();
    }
}