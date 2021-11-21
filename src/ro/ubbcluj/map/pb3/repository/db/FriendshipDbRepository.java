package ro.ubbcluj.map.pb3.repository.db;

import ro.ubbcluj.map.pb3.domain.Prietenie;
import ro.ubbcluj.map.pb3.domain.Tuple;
import ro.ubbcluj.map.pb3.domain.validators.ValidationException;
import ro.ubbcluj.map.pb3.domain.validators.Validator;
import ro.ubbcluj.map.pb3.repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Repo care salveaza si aduce datele din baza de date "friendship"
 */
public class FriendshipDbRepository implements Repository<Tuple<Long, Long>, Prietenie> {
    private String url;
    private String username;
    private String password;
    private Validator<Prietenie> validator;

    /**
     *
     * @param url url-ul cu care se conecteaza la baza de date
     * @param username - usernameul
     * @param password - parola
     * @param validator - validator pentru prietenie
     */
    public FriendshipDbRepository(String url, String username, String password, Validator<Prietenie> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Prietenie findOne(Tuple<Long, Long> longLongTuple) {
        String sql = "select * from friendship where id1 = " + longLongTuple.getLeft() + "and id2 = "+ longLongTuple.getRight();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                Long id1 = result.getLong("id1");
                Long id2 = result.getLong("id2");
                Date date = result.getDate("dateofaccept");
                Prietenie prietenie = new Prietenie();
                Tuple<Long, Long> tuple = new Tuple(id1, id2);
                prietenie.setId(tuple);
                prietenie.setDate(date);
                return prietenie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> friends = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("select * from friendship");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                Date date = resultSet.getDate("dateofaccept");
                Prietenie prietenie = new Prietenie();
                Tuple<Long, Long> tuple = new Tuple(id1, id2);
                prietenie.setId(tuple);
                prietenie.setDate(date);
                friends.add(prietenie);
            }
            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prietenie save(Prietenie entity) throws ValidationException {
        validator.validate(entity);

        String sql = "insert into friendship (id1, id2, dateofaccept) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, Math.toIntExact(entity.getId().getLeft()));
            ps.setInt(2, Math.toIntExact(entity.getId().getRight()));
            ps.setDate(3, entity.getDate());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prietenie delete(Tuple<Long, Long> id) {

        String sql = "delete from friendship where id1 = " + id.getLeft() + " and id2 = " + id.getRight();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prietenie update(Prietenie entity) {
        return null;
    }
}

