package persistence;

import model.AuditEntity;
import model.Author;
import model.Book;
import model.associative.BookAuthor;
import service.Audit;
import service.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookRepository implements GenericRepository<Book> {
    private final DatabaseConnection db;
    private final AuthorRepository authorRepository;

    public BookRepository(DatabaseConnection db, AuthorRepository authorRepository) {
        this.db = db;
        this.authorRepository = authorRepository;
    }

    @Override
    public void add(Book entity) {
        String sql = "INSERT INTO book (idBook, title) VALUES (?, ?)";
        try (PreparedStatement stmt = db.connection.prepareStatement(sql)) {
            stmt.setInt(1, entity.getIdBook());
            stmt.setString(2, entity.getTitlu());
            stmt.execute();

            // Adăugare autorii cărții în tabela asociativă
            for (Author author : entity.getAuthorlist()) {
                // Obține detaliile autorului din baza de date folosind ID-ul
                Author existingAuthor = authorRepository.get(author.getIdAuthor());
                if (existingAuthor != null) {
                    addBookAuthor(entity.getIdBook(), existingAuthor.getIdAuthor());
                }
            }

            // Log audit action
            AuditEntity auditEntity = new AuditEntity();
            auditEntity.setSchema("JAVA");
            auditEntity.setTable("BOOK");
            auditEntity.setActionName("INSERT");
            auditEntity.setTimestamp(LocalDateTime.now());
            Audit.getInstance().log(auditEntity);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Book get(int id) {
        String sql = """
                 SELECT idBook, title
                 FROM book
                 WHERE idBook = ?
                 """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getInt("idBook"),
                        rs.getString("title"),
                        getAuthorsForBook(id)
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public ArrayList<Book> getAll() {
        ArrayList<Book> books = new ArrayList<>();
        String sql = """
                 SELECT idBook, title
                 FROM book
                 """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idBook = rs.getInt("idBook");
                String title = rs.getString("title");
                List<Author> authors = getAuthorsForBook(idBook);
                books.add(new Book(idBook, title, authors));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }


    @Override
    public void update(Book entity) {
        String sql = """
                 UPDATE book
                 SET title = ?
                 WHERE idBook = ?
                 """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setString(1, entity.getTitlu());
            stmt.setInt(2, entity.getIdBook());
            stmt.executeUpdate();

            // Log audit action
            AuditEntity auditEntity = new AuditEntity();
            auditEntity.setSchema("JAVA");
            auditEntity.setTable("BOOK");
            auditEntity.setActionName("UPDATE");
            auditEntity.setTimestamp(LocalDateTime.now());
            Audit.getInstance().log(auditEntity);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Book book) {
        String sqlDeleteBook = "DELETE FROM BOOK WHERE idBook = ?";

        try {
            db.connection.setAutoCommit(false);

            // Delete related book authors
            String sqlDeleteBookAuthors = "DELETE FROM BOOKAUTHOR WHERE book_id = ?";
            try (PreparedStatement stmt = db.connection.prepareStatement(sqlDeleteBookAuthors)) {
                stmt.setInt(1, book.getIdBook());
                stmt.executeUpdate();
            }

            // Delete related shelf books
            String sqlDeleteShelfBooks = "DELETE FROM SHELFBOOK WHERE book_id = ?";
            try (PreparedStatement stmt = db.connection.prepareStatement(sqlDeleteShelfBooks)) {
                stmt.setInt(1, book.getIdBook());
                stmt.executeUpdate();
            }

            // Delete related book ratings
            String sqlDeleteBookRatings = "DELETE FROM BOOKRATING WHERE book_id = ?";
            try (PreparedStatement stmt = db.connection.prepareStatement(sqlDeleteBookRatings)) {
                stmt.setInt(1, book.getIdBook());
                stmt.executeUpdate();
            }

            String sqlDeleteRatings = "DELETE FROM RATING WHERE IDRATING IN (SELECT RATING_ID FROM BOOKRATING WHERE book_id = ?)";
            try (PreparedStatement stmt = db.connection.prepareStatement(sqlDeleteRatings)) {
                stmt.setInt(1, book.getIdBook());
                stmt.executeUpdate();
            }

            // Delete the book
            try (PreparedStatement stmt = db.connection.prepareStatement(sqlDeleteBook)) {
                stmt.setInt(1, book.getIdBook());
                stmt.executeUpdate();
            }

            // Log audit action
            AuditEntity auditEntity = new AuditEntity();
            auditEntity.setSchema("JAVA");
            auditEntity.setTable("BOOK");
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


    // Metodă pentru adăugarea unui autor în tabela asociativă Book_Author
    private void addBookAuthor(int idBook, int idAuthor) {
        String sql = """
                     INSERT INTO bookauthor (BOOK_ID, AUTHOR_ID)
                     VALUES (?, ?)
                     """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, idBook);
            stmt.setInt(2, idAuthor);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Metoda pentru a obține lista de autori pentru o anumita carte
    public List<Author> getAuthorsForBook(int idBook) {
        List<Author> authors = new ArrayList<>();
        String sql = """
             SELECT a.idAuthor, a.nume, a.prenume
             FROM author a, bookauthor ba
             WHERE a.idAuthor = ba.AUTHOR_ID
             AND ba.BOOK_ID = ?
             """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, idBook);
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


    public List<Book> getBooksByAuthor(int authorId) {
        List<Book> books = new ArrayList<>();
        String sql = """
                SELECT b.idBook, b.title
                FROM book b
                JOIN bookauthor ba ON b.idBook = ba.BOOK_ID
                WHERE ba.AUTHOR_ID = ?
                ORDER BY b.title ASC
                """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, authorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book(rs.getInt("idBook"), rs.getString("title"), getAuthorsForBook(rs.getInt("idBook")));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    public ArrayList<String> getBookTitlesByIds(ArrayList<Integer> bookIds) {
        ArrayList<String> titles = new ArrayList<>();
        String sql = "SELECT title FROM book WHERE idBook IN (";

        // Construiește o șir de întrebări pentru fiecare ID de carte
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < bookIds.size(); i++) {
            placeholders.append("?");
            if (i < bookIds.size() - 1) {
                placeholders.append(",");
            }
        }
        sql += placeholders + ")";

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);

            // Setează valorile pentru fiecare ID de carte în interogarea SQL
            for (int i = 0; i < bookIds.size(); i++) {
                stmt.setInt(i + 1, bookIds.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                titles.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return titles;
    }



}


