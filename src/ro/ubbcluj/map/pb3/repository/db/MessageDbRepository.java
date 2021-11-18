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
        String sql = "insert into messages (from, to, msg) values (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, entity.getFrom().getFirstName());
            ps.setString(2, String.valueOf(entity.getTo()
                    .stream()
                    .map(x -> x.getFirstName())
                    .reduce((x,y) -> x.concat(y)))
            );
            ps.setString(3, entity.getMsg());
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
        String sql = "update messages set replyMsg = ? where id = ? ";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getReplyMsg());
            ps.setInt(2, Math.toIntExact(entity.getId()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
