package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.AuditEntity;
import model.Author;
import service.Audit;
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

            // Log audit action
            AuditEntity auditEntity = new AuditEntity();
            auditEntity.setSchema("JAVA");
            auditEntity.setTable("AUTHOR");
            auditEntity.setActionName("INSERT");
            auditEntity.setTimestamp(LocalDateTime.now());
            Audit.getInstance().log(auditEntity);

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

            // Log audit action
            AuditEntity auditEntity = new AuditEntity();
            auditEntity.setSchema("JAVA");
            auditEntity.setTable("AUTHOR");
            auditEntity.setActionName("UPDATE");
            auditEntity.setTimestamp(LocalDateTime.now());
            Audit.getInstance().log(auditEntity);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Author author) {
        String sqlDeleteAuthor = "DELETE FROM AUTHOR WHERE idAuthor = ?";

        try {
            db.connection.setAutoCommit(false);

            // Delete related book authors
            String sqlDeleteBookAuthors = "DELETE FROM BOOKAUTHOR WHERE author_id = ?";
            try (PreparedStatement stmt = db.connection.prepareStatement(sqlDeleteBookAuthors)) {
                stmt.setInt(1, author.getIdAuthor());
                stmt.executeUpdate();
            }

            // Delete related author ratings
            String sqlDeleteAuthorRatings = "DELETE FROM AUTHORRATING WHERE author_id = ?";
            try (PreparedStatement stmt = db.connection.prepareStatement(sqlDeleteAuthorRatings)) {
                stmt.setInt(1, author.getIdAuthor());
                stmt.executeUpdate();
            }

            String sqlDeleteRatings = "DELETE FROM RATING WHERE IDRATING IN (SELECT RATING_ID FROM JAVA.AUTHORRATING WHERE AUTHOR_ID = ?)";
            try (PreparedStatement stmt = db.connection.prepareStatement(sqlDeleteRatings)) {
                stmt.setInt(1, author.getIdAuthor());
                stmt.executeUpdate();
            }
            // Delete the author
            try (PreparedStatement stmt = db.connection.prepareStatement(sqlDeleteAuthor)) {
                stmt.setInt(1, author.getIdAuthor());
                stmt.executeUpdate();
            }

            // Log audit action
            AuditEntity auditEntity = new AuditEntity();
            auditEntity.setSchema("JAVA");
            auditEntity.setTable("AUTHOR");
            auditEntity.setActionName("DELETE");
            auditEntity.setTimestamp(LocalDateTime.now());
            Audit.getInstance().log(auditEntity);

            db.connection.commit();
        } catch (SQLException e) {
            try {
                db.connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                db.connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<Author> getAuthorsForBook(int book_id) {
        String sql = """
                 SELECT a.idAuthor, a.nume, a.prenume
                 FROM author a
                 JOIN bookauthor ba ON a.idAuthor = ba.AUTHOR_ID
                 WHERE ba.BOOK_ID = ?
                 ORDER BY a.nume asc 
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
