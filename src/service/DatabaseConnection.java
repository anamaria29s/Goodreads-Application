package service;


import java.sql.*;

public class DatabaseConnection {

    private static final String DB_DRIVER = "oracle.jdbc.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USER = "java";
    private static final String DB_PASS = "password";

    public static Connection connection;
    private static DatabaseConnection instance;

    public void testConnection() {
        String sql = """
                Select * from test
                """;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                System.out.println(rs.getString("nume"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private DatabaseConnection() {
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}