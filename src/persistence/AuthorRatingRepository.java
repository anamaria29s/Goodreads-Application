package persistence;

import model.Author;
import model.Rating;
import model.Shelf;
import model.Utilizator;
import model.associative.AuthorRating;
import service.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuthorRatingRepository implements GenericRepository<Rating> {
    private final DatabaseConnection db;
    private final AuthorRepository authorRepository;
    private final RatingRepository ratingRepository;

    public AuthorRatingRepository(DatabaseConnection db, AuthorRepository authorRepository, RatingRepository ratingRepository) {
        this.db = db;
        this.authorRepository = authorRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void add(Rating entity) {
        String sql = "INSERT INTO AuthorRating (author_id, rating_id) VALUES (?, ?)";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, ((model.associative.AuthorRating) entity).getIdAuthor());
            stmt.setInt(2, entity.getIdRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rating get(int id) {
        String sql = "SELECT * FROM AuthorRating WHERE rating_id = ?";
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int authorId = rs.getInt("author_id");
                int ratingId = rs.getInt("rating_id");
                Author author = authorRepository.get(authorId);
                Rating rating = ratingRepository.get(ratingId);
                return new AuthorRating(ratingId, rating.getNota(), rating.getReview(), rating.getUser(), author);
            } else {
                return null; // Returnează null dacă nu găsești niciun rating cu ID-ul dat
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Rating> getAll() {
        String sql = "SELECT * FROM AuthorRating";
        ArrayList<Rating> authorRatings = new ArrayList<>();
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int authorId = rs.getInt("author_id");
                int ratingId = rs.getInt("rating_id");
                Author author = authorRepository.get(authorId);
                Rating rating = ratingRepository.get(ratingId);
                AuthorRating authorRating = new AuthorRating(ratingId, rating.getNota(), rating.getReview(), rating.getUser(), author);
                authorRatings.add(authorRating);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authorRatings;
    }

    public ArrayList<Rating> getAllRatingsForAuthor(int authorId) {
        String sql = "SELECT * FROM AuthorRating WHERE author_id = ?";
        ArrayList<Rating> authorRatings = new ArrayList<>();
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, authorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int ratingId = rs.getInt("rating_id");
                Rating rating = ratingRepository.get(ratingId);
                authorRatings.add(rating);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authorRatings;
    }


    @Override
    public void update(Rating entity) {
        String sql = "UPDATE Rating SET nota = ?, review = ? WHERE idRating = ?";
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getNota());
            stmt.setString(2, entity.getReview());
            stmt.setInt(3, entity.getIdRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Rating entity) {
        String sql = "DELETE FROM AuthorRating WHERE rating_id = ?";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getIdRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
