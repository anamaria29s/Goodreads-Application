package persistence;

import model.Author;
import model.Book;
import model.Rating;
import model.associative.AuthorRating;
import model.associative.BookRating;
import service.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookRatingRepository implements GenericRepository<Rating> {
    private final DatabaseConnection db;
    private final BookRepository bookRepository;
    private final RatingRepository ratingRepository;

    public BookRatingRepository(DatabaseConnection db, BookRepository bookRepository, RatingRepository ratingRepository) {
        this.db = db;
        this.bookRepository = bookRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void add(Rating entity) {
        String sql = "INSERT INTO BookRating (book_id, rating_id) VALUES (?, ?)";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, ((BookRating) entity).getIdBook());
            stmt.setInt(2, entity.getIdRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rating get(int id) {
        String sql = "SELECT * FROM BookRating WHERE rating_id = ?";
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int bookId = rs.getInt("book_id");
                int ratingId = rs.getInt("rating_id");
                Book book = bookRepository.get(bookId);
                Rating rating = ratingRepository.get(ratingId);
                return new BookRating(ratingId, rating.getNota(), rating.getReview(), rating.getUser(), book);
            } else {
                return null; // Returnează null dacă nu găsești niciun rating cu ID-ul dat
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Rating> getAll() {
        String sql = "SELECT * FROM BookRating";
        ArrayList<Rating> bookRatings = new ArrayList<>();
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                int ratingId = rs.getInt("rating_id");
                Book book = bookRepository.get(bookId);
                Rating rating = ratingRepository.get(ratingId);
                BookRating bookRating = new BookRating(ratingId, rating.getNota(), rating.getReview(), rating.getUser(), book);
                bookRatings.add(bookRating);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookRatings;
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
        String sql = "DELETE FROM BookRating WHERE rating_id = ?";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getIdRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Rating> getAllRatingsForBook(int bookId) {
        String sql = "SELECT * FROM BookRating WHERE book_id = ?";
        ArrayList<Rating> bookRatings = new ArrayList<>();
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int ratingId = rs.getInt("rating_id");
                Rating rating = ratingRepository.get(ratingId);
                bookRatings.add(rating);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookRatings;
    }

}
