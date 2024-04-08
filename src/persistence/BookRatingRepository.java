package persistence;

import model.Rating;
import model.Utilizator;
import service.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookRatingRepository implements GenericRepository<Rating> {
    private final DatabaseConnection db;

    public BookRatingRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public void add(Rating entity) {
        String sql = "INSERT INTO BookRating (book_id, rating_id) VALUES (?, ?)";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, ((model.associative.BookRating) entity).getIdBook());
            stmt.setInt(2, entity.getIdRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rating get(int id) {
        return null;
    }

    @Override
    public ArrayList<Rating> getAll() {  return null;
    }

    @Override
    public void update(Rating entity) {

    }

    @Override
    public void delete(Rating entity) {
        String sql = "DELETE FROM BookRating WHERE rating_id = ?";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getIdRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
