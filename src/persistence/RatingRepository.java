package persistence;

import model.Rating;
import model.Utilizator;
import service.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class RatingRepository implements GenericRepository<Rating> {
    private final DatabaseConnection db;

    public RatingRepository(DatabaseConnection db) {
        this.db = db;
    }
    public int getUserIdFromInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter user ID: ");
        return scanner.nextInt();
    }


    @Override
    public void add(Rating entity) {
        // Get user ID from input
        int userId = getUserIdFromInput();
        // Fetch user from the database
        UtilizatorRepository utilizatorRepository = new UtilizatorRepository(db);
        Utilizator user = utilizatorRepository.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }
        entity.setUser(user); // Set the user in the Rating entity

        String sql = "INSERT INTO rating (IDRATING,nota, review, user_id) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, entity.getIdRating());
            stmt.setInt(2, entity.getNota());
            stmt.setString(3, entity.getReview());
            stmt.setInt(4, entity.getUser().getId());
            stmt.executeUpdate();

//            ResultSet rs = stmt.getGeneratedKeys();
//            if (rs.next()) {
//                entity.setIdRating(rs.getInt(1));
//            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rating get(int id) {
        String sql = "SELECT * FROM rating WHERE idRating = ?";
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idRating = rs.getInt("idRating");
                int nota = rs.getInt("nota");
                String review = rs.getString("review");
                int userId = rs.getInt("user_id");

                UtilizatorRepository utilizatorRepository = new UtilizatorRepository(db);
                Utilizator user = utilizatorRepository.get(userId);

                return new Rating(idRating, nota, review, user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public ArrayList<Rating> getAll() {
        String sql = "SELECT * FROM rating";

        ArrayList<Rating> ratings = new ArrayList<>();
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idRating = rs.getInt("idRating");
                int nota = rs.getInt("nota");
                String review = rs.getString("review");
                int userId = rs.getInt("user_id");

                UtilizatorRepository utilizatorRepository = new UtilizatorRepository(db);
                Utilizator user = utilizatorRepository.get(userId);

                Rating rating = new Rating(idRating, nota, review, user);
                ratings.add(rating);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ratings;
    }

    @Override
    public void update(Rating entity) {
        String sql = "UPDATE rating SET nota = ?, review = ?, user_id = ? WHERE idRating = ?";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getNota());
            stmt.setString(2, entity.getReview());
            stmt.setInt(3, entity.getUser().getId());
            stmt.setInt(4, entity.getIdRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Rating entity) {
        String sql = "DELETE FROM rating WHERE idRating = ?";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getIdRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
