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
        String sql = "INSERT INTO shelf (idShelf, USER_ID) VALUES (?, ?)";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getIdShelf());
            stmt.setInt(2, entity.getUser().getId()); // Presupunând că obținem ID-ul utilizatorului din obiectul Utilizator
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
        String sql = "UPDATE shelf SET USER_ID = ? WHERE idShelf = ?";

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

    public Shelf getShelfByUserId(int userId) {
        String sql = "SELECT * FROM shelf WHERE user_id = ?";
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

    public void deleteByUserId(int userId) {
        try {
            String query = "DELETE FROM SHELF WHERE user_id = ?";
            PreparedStatement stmt = db.connection.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



