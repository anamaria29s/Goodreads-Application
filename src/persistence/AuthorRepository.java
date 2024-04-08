package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Author;

public class AuthorRepository implements GenericRepository<Author> {
    private final Connection connection;

    public AuthorRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Author author) {
        String sql = "INSERT INTO AUTHOR (idAuthor, nume, prenume) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
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
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, author.getIdAuthor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
