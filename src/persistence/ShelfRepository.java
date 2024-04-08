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
        String sql = "INSERT INTO shelf (idShelf, status, USER_ID) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getIdShelf());
            stmt.setString(2, entity.getStatus().toString()); // Convertim statusul în șir de caractere
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
                String statusStr = rs.getString("status");
                Status status = switch (statusStr) {
                    case "read" -> Status.READ;
                    case "to read" -> Status.TO_READ;
                    case "reading" -> Status.READING;
                    default -> throw new IllegalArgumentException("Invalid status: " + statusStr);
                };
                Utilizator user = new Utilizator(); // Aici trebuie să obținem utilizatorul din baza de date
                return new Shelf(shelfId, status, user);
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
                String statusStr = rs.getString("status");
                Status status = Status.fromString(statusStr);
                int userId = rs.getInt("user_id");
                Utilizator user = utilizatorRepository.get(userId);
                Shelf shelf = new Shelf(idShelf, status, user);
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
            stmt.setString(1, entity.getStatus().toString());
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

}
