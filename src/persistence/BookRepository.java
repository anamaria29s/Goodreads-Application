package persistence;

import model.Author;
import model.Book;
import model.associative.BookAuthor;
import service.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = """
                     INSERT INTO book (idBook, title)
                     VALUES (?, ?)
                     """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getIdBook());
            stmt.setString(2, entity.getTitlu());
            stmt.execute();

            // Adăugare autorii cărții în tabela asociativă
            for (Author author : entity.getAuthorlist()) {
                addBookAuthor(entity.getIdBook(), author.getIdAuthor());
            }
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Book book) {
        String sql = "DELETE FROM BOOK WHERE idBook = ?";
        try (PreparedStatement stmt = db.connection.prepareStatement(sql)) {
            stmt.setInt(1, book.getIdBook());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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




}


