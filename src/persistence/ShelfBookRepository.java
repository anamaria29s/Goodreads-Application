package persistence;

import model.associative.ShelfBook;
import service.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShelfBookRepository {
    private final DatabaseConnection db;

    public ShelfBookRepository(DatabaseConnection db) {
        this.db = db;
    }

    public void add(ShelfBook shelfBook) {
        String sql = "INSERT INTO shelfbook (BOOK_ID, SHELF_ID) VALUES (?, ?)";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, shelfBook.getIdBook());
            stmt.setInt(2, shelfBook.getIdShelf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<ShelfBook> getAll() {
        String sql = "SELECT * FROM shelfbook";

        ArrayList<ShelfBook> shelfBooks = new ArrayList<>();
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ShelfBook shelfBook = new ShelfBook();
                shelfBook.setIdBook(rs.getInt("idBook"));
                shelfBook.setIdShelf(rs.getInt("idShelf"));
                shelfBooks.add(shelfBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return shelfBooks;
    }

    public void delete(ShelfBook shelfBook) {
        String sql = "DELETE FROM shelfbook WHERE BOOK_ID = ? AND SHELF_ID = ?";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, shelfBook.getIdBook());
            stmt.setInt(2, shelfBook.getIdShelf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
