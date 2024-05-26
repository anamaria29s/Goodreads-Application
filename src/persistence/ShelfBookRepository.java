package persistence;

import model.Book;
import model.Shelf;
import model.Status;
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
//    public ArrayList<Book> getBooksFromUserShelf(int userId) {
//        ArrayList<Book> books = new ArrayList<>();
//        String sql = """
//            SELECT b.idBook, b.title, sb.STATUS
//            FROM book b
//            JOIN shelfbook sb ON b.idBook = sb.BOOK_ID
//            JOIN shelf s ON sb.SHELF_ID = s.idShelf
//            WHERE s.USER_ID = ?
//            """;
//
//        try {
//            PreparedStatement stmt = db.connection.prepareStatement(sql);
//            stmt.setInt(1, userId);
//            ResultSet rs = stmt.executeQuery();
//
//            // Create an instance of BookRepository
//            BookRepository bookRepository = new BookRepository(db, new AuthorRepository(db));
//
//            while (rs.next()) {
//                // Call getAuthorsForBook on the bookRepository instance
//                Book book = new Book(
//                        rs.getInt("idBook"),
//                        rs.getString("title"),
//                        bookRepository.getAuthorsForBook(rs.getInt("idBook"))
//                );
//                ShelfBook.setStatus(rs.getString("STATUS"));
//                books.add(book);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return books;
//    }

    public void update(ShelfBook shelfBook) {
        String sql = "UPDATE shelfbook SET STATUS = ? WHERE BOOK_ID = ? AND SHELF_ID = ?";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setString(1, shelfBook.getStatus().toString());
            stmt.setInt(2, shelfBook.getIdBook());
            stmt.setInt(3, shelfBook.getIdShelf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
