package persistence;

import model.Shelf;
import model.Status;
import model.Utilizator;
import service.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ShelfRepository implements GenericRepository<Shelf> {
    private final DatabaseConnection db;
    private final UtilizatorRepository utilizatorRepository;

    public ShelfRepository(DatabaseConnection db, UtilizatorRepository utilizatorRepository) {
        this.db = db;
        this.utilizatorRepository = utilizatorRepository;
    }

    @Override
    public void add(Shelf entity) {
        String sql = "INSERT INTO shelf (idShelf, USER_ID) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getIdShelf());
            stmt.setInt(3, entity.getUser().getId()); // Presupunând că obținem ID-ul utilizatorului din obiectul Utilizator
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Shelf get(int id) {
        String sql = "SELECT * FROM shelf WHERE idShelf = ?";
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int shelfId = rs.getInt("idShelf");
                int userId = rs.getInt("USER_ID");
                Utilizator user = utilizatorRepository.get(userId);
                return new Shelf(shelfId, user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Shelf> getAll() {
        String sql = "SELECT * FROM shelf";

        ArrayList<Shelf> shelves = new ArrayList<>();
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idShelf = rs.getInt("idShelf");
                int userId = rs.getInt("USER_ID");
                Utilizator user = utilizatorRepository.get(userId);
                Shelf shelf = new Shelf(idShelf, user);
                shelves.add(shelf);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return shelves;
    }

    @Override
    public void update(Shelf entity) {
        String sql = "UPDATE shelf SET status = ? WHERE idShelf = ?";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(2, entity.getIdShelf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Shelf entity) {
        String sql = "DELETE FROM shelf WHERE idShelf = ?";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getIdShelf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    // Add book to a user's shelf
//    public void addBookToUserShelf(int userId, int bookId) {
//        // Find the user's shelf
//        Shelf userShelf = getUserShelf(userId);
//        if (userShelf == null) {
//            throw new RuntimeException("User with ID " + userId + " does not have a shelf.");
//        }
//
//        // Add book to the user's shelf
//        addBookToShelf(userShelf.getIdShelf(), bookId);
//    }
//
    private Shelf getUserShelf(int userId) {
        String sql = "SELECT * FROM shelf WHERE USER_ID = ?";
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int shelfId = rs.getInt("idShelf");
                Utilizator user = utilizatorRepository.get(userId);
                return new Shelf(shelfId, user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
//
//    public void addBookToShelf(int shelfId, int bookId) {
//        String sql = "INSERT INTO shelfbook (SHELF_ID, BOOK_ID) VALUES (?, ?)";
//        try {
//            PreparedStatement stmt = db.connection.prepareStatement(sql);
//            stmt.setInt(1, shelfId);
//            stmt.setInt(2, bookId);
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//


