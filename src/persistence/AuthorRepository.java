package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Author;
import service.DatabaseConnection;

public class AuthorRepository implements GenericRepository<Author> {
    private final DatabaseConnection db;

    public AuthorRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public void add(Author author) {
        String sql = "INSERT INTO AUTHOR (idAuthor, nume, prenume) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = db.connection.prepareStatement(sql)) {
            stmt.setInt(1, author.getIdAuthor());
            stmt.setString(2, author.getNume());
            stmt.setString(3, author.getPrenume());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author get(int id) {
        String sql = "SELECT * FROM AUTHOR WHERE idAuthor = ?";
        try (PreparedStatement stmt = db.connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int authorId = rs.getInt("idAuthor");
                String nume = rs.getString("nume");
                String prenume = rs.getString("prenume");
                return new Author(authorId, nume, prenume);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Author> getAll() {
        ArrayList<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM AUTHOR";
        try (PreparedStatement stmt = db.connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int authorId = rs.getInt("idAuthor");
                String nume = rs.getString("nume");
                String prenume = rs.getString("prenume");
                authors.add(new Author(authorId, nume, prenume));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    @Override
    public void update(Author author) {
        String sql = "UPDATE AUTHOR SET nume = ?, prenume = ? WHERE idAuthor = ?";
        try (PreparedStatement stmt = db.connection.prepareStatement(sql)) {
            stmt.setString(1, author.getNume());
            stmt.setString(2, author.getPrenume());
            stmt.setInt(3, author.getIdAuthor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Author author) {
        String sql = "DELETE FROM AUTHOR WHERE idAuthor = ?";
        try (PreparedStatement stmt = db.connection.prepareStatement(sql)) {
            stmt.setInt(1, author.getIdAuthor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Author> getAuthorsForBook(int book_id) {
        String sql = """
                 SELECT a.idAuthor, a.nume, a.prenume
                 FROM author a
                 JOIN bookauthor ba ON a.idAuthor = ba.AUTHOR_ID
                 WHERE ba.BOOK_ID = ?
                 """;

        ArrayList<Author> authors = new ArrayList<>();
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, book_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Author author = new Author(
                        rs.getInt("idAuthor"),
                        rs.getString("nume"),
                        rs.getString("prenume")
                );
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }

}
