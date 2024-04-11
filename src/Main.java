import service.DatabaseConnection;
import view.App;
public class Main {
//    public static void main(String[] args) {
//        DatabaseConnection db = DatabaseConnection.getInstance();
//        db.testConnection();
//    }
    private static App menu;

    public static void main(String[] args) {
        menu = App.getInstance();
        menu.start();
    }
}