package ro.ubbcluj.map.pb3.repository.db;

import ro.ubbcluj.map.pb3.Message.Message;
import ro.ubbcluj.map.pb3.domain.Utilizator;
import ro.ubbcluj.map.pb3.domain.validators.Validator;
import ro.ubbcluj.map.pb3.repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageDbRepository implements Repository<Long, Message> {
    private String url;
    private String username;
    private String password;
    private Validator<Message> validator;

    /**
     *
     * @param url url-ul cu care se conecteaza la baza de date
     * @param username - usernameul
     * @param password - parola
     * @param validator - validator pentru prietenie
     */
    public MessageDbRepository(String url, String username, String password, Validator<Message> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public Message findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        return null;
    }

    @Override
    public Message save(Message entity) {
        String sql = "insert into messages (from1, to1, msg) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            String to1 = String.valueOf(entity.getTo()
                    .stream()
                    .map(x -> x.getId().toString())
                    .reduce(" ",(u,v) -> u.concat(v)));

            ps.setInt(1, Math.toIntExact(entity.getFrom().getId()));
            ps.setString(2, to1);
            ps.setString(3, entity.getMsg());
            ps.executeUpdate();
//            ps.setDate(4, java.sql.Date.valueOf(entity.getData().toLocalDate()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Message delete(Long aLong) {
        return null;
    }

    @Override
    public Message update(Message entity) {
        return null;
    }
}
