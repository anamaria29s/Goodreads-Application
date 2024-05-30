package persistence;

import model.*;
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
        String sql = "INSERT INTO shelfbook (BOOK_ID, SHELF_ID, STATUS) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, shelfBook.getIdBook());
            stmt.setInt(2, shelfBook.getIdShelf());
            stmt.setString(3, shelfBook.getStatus().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ShelfBook get(int id) {
        String sql = "SELECT * FROM shelfbook WHERE BOOK_ID = ?";
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String statusValue = rs.getString("STATUS");
                // Utilizați valoarea direct pentru a crea un obiect Status
                Status status = new Status(statusValue);

                return new ShelfBook(
                        rs.getInt("BOOK_ID"),
                        rs.getInt("SHELF_ID"),
                        status
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public ArrayList<ShelfBook> getAll() {
        String sql = "SELECT * FROM shelfbook";

        ArrayList<ShelfBook> shelfBooks = new ArrayList<>();
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ShelfBook shelfBook = new ShelfBook(
                        rs.getInt("BOOK_ID"),
                        rs.getInt("SHELF_ID"),
                        Status.fromString(rs.getString("STATUS"))
                );
                shelfBooks.add(shelfBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return shelfBooks;
    }

    public ArrayList<ShelfBook> getBooksInShelf(int shelfId) {
        String sql = "SELECT * FROM shelfbook WHERE SHELF_ID = ?";
        ArrayList<ShelfBook> shelfBooks = new ArrayList<>();

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, shelfId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String statusValue = rs.getString("STATUS");
                // Elimină eticheta suplimentară din valoarea statusului
                statusValue = statusValue.replace("Status[value=", "").replace("]", "");
                Status status = Status.fromString(statusValue);

                ShelfBook shelfBook = new ShelfBook(
                        rs.getInt("BOOK_ID"),
                        rs.getInt("SHELF_ID"),
                        status
                );
                shelfBooks.add(shelfBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return shelfBooks;
    }


    public void updateBookStatus(int bookId, int shelfId, Status newStatus) {
        String sql = "UPDATE shelfbook SET STATUS = ? WHERE BOOK_ID = ? AND SHELF_ID = ?";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setString(1, newStatus.value());
            stmt.setInt(2, bookId);
            stmt.setInt(3, shelfId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(ShelfBook shelfBook) {
        String sql = "DELETE FROM shelfbook WHERE BOOK_ID = ? ";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, shelfBook.getIdBook());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
