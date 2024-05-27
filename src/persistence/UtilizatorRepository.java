package persistence;

import model.Utilizator;
import service.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class UtilizatorRepository implements GenericRepository<Utilizator> {
    private static DatabaseConnection db = null;

    public UtilizatorRepository(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public void add(Utilizator entity) {
        String sql = """
                     INSERT INTO utilizator(user_id, username, mail, password)
                     VALUES (?, ?, ?, ?)
                     """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getId());
            stmt.setString(2, entity.getUsername());
            stmt.setString(3, entity.getMail());
            stmt.setString(4, entity.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Utilizator get(int id) {
        String sql = """
                     SELECT user_id, username, mail, password
                     FROM utilizator
                     WHERE user_id = ?
                     """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Utilizator(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("mail"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public ArrayList<Utilizator> getAll() {
        String sql = """
                     SELECT user_id, username, mail, password
                     FROM utilizator
                     """;

        ArrayList<Utilizator> utilizatori = new ArrayList<>();
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Utilizator utilizator = new Utilizator(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("mail"),
                        rs.getString("password")
                );
                utilizatori.add(utilizator);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return utilizatori;
    }

    @Override
    public void update(Utilizator entity) {
        String sql = """
                     UPDATE utilizator
                     SET username = ?, mail = ?, password = ?
                     WHERE user_id = ?
                     """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getMail());
            stmt.setString(3, entity.getPassword());
            stmt.setInt(4, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Utilizator entity) {
        String sql = """
                     DELETE FROM utilizator
                     WHERE user_id = ?
                     """;

        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setInt(1, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Utilizator getByUsername(String username) {
        String sql = "SELECT * FROM utilizator WHERE username = ?";
        try {
            PreparedStatement stmt = db.connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Utilizator(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("mail"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
