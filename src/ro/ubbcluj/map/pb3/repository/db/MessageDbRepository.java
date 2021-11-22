package ro.ubbcluj.map.pb3.repository.db;

import ro.ubbcluj.map.pb3.Message.Message;
import ro.ubbcluj.map.pb3.domain.Utilizator;
import ro.ubbcluj.map.pb3.domain.validators.Validator;
import ro.ubbcluj.map.pb3.repository.Repository;

import java.sql.*;
import java.util.*;

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
        String sql = "select * from messages where id = " + aLong;

        try(Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Long idCurent = resultSet.getLong("id");
                Long from = resultSet.getLong("from1");

                String sql1 = "select * from users where id = " + from;

                PreparedStatement ps1 = connection.prepareStatement(sql1);
                ResultSet resultSet1 = ps1.executeQuery();
                Utilizator utilizator = new Utilizator("a","a");
                while (resultSet1.next()) {
                    String firstName = resultSet1.getString("first_name");
                    String lastName = resultSet1.getString("last_name");
                    utilizator.setFirstName(firstName);
                    utilizator.setLastName(lastName);
                    utilizator.setId(Long.valueOf(from));
                }

                String toString = resultSet.getString("to1");
                String toString1 = toString.strip();
                String[] listTo = toString1.split(" ");
                List<Utilizator> list = new ArrayList<>();
                for(String curent: listTo) {
                    String sql2 = "select * from users where id = " + curent;

                    PreparedStatement ps2 = connection.prepareStatement(sql2);
                    ResultSet resultSet2 = ps2.executeQuery();
                    while (resultSet2.next()) {
                        String firstName1 = resultSet2.getString("first_name");
                        String lastName1 = resultSet2.getString("last_name");
                        Utilizator utilizator1 = new Utilizator(firstName1, lastName1);
                        utilizator1.setId(Long.valueOf(curent));
                        list.add(utilizator1);
                    }
                }
                String msg = resultSet.getString("msg");
                Message message = new Message(utilizator,list,msg);
                message.setId(idCurent);
                return message;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Iterable<Message> findAll() {
        String sql = "select * from messages";

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet resultSet = ps.executeQuery();
            Set<Message> all = new HashSet<>();
            while (resultSet.next()) {
                Long idCurent = resultSet.getLong("id");
                Long from = resultSet.getLong("from1");

                String sql1 = "select * from users where id = " + from;

                PreparedStatement ps1 = connection.prepareStatement(sql1);
                ResultSet resultSet1 = ps1.executeQuery();
                Utilizator utilizator = new Utilizator("a","a");
                while (resultSet1.next()) {
                    String firstName = resultSet1.getString("first_name");
                    String lastName = resultSet1.getString("last_name");
                    utilizator.setFirstName(firstName);
                    utilizator.setLastName(lastName);
                    utilizator.setId(Long.valueOf(from));
                }

                String toString = resultSet.getString("to1");
                String toString1 = toString.strip();
                String[] listTo = toString1.split(" ");
                List<Utilizator> list = new ArrayList<>();
                for(String curent: listTo) {
                    String sql2 = "select * from users where id = " + curent;

                    PreparedStatement ps2 = connection.prepareStatement(sql2);
                    ResultSet resultSet2 = ps2.executeQuery();
                    while (resultSet2.next()) {
                        String firstName1 = resultSet2.getString("first_name");
                        String lastName1 = resultSet2.getString("last_name");
                        Utilizator utilizator1 = new Utilizator(firstName1, lastName1);
                        utilizator1.setId(Long.valueOf(curent));
                        list.add(utilizator1);
                    }
                }
                String msg = resultSet.getString("msg");
                Message message = new Message(utilizator,list,msg);
                message.setId(idCurent);
                all.add(message);
            }
            return all;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Message save(Message entity) {
        String sql = "insert into messages (from1, to1, msg, rplymsg) values (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            String to1 = String.valueOf(entity.getTo()
                    .stream()
                    .map(x -> x.getId().toString())
                    .reduce("",(u,v) -> u.concat(" " + v)));

            ps.setInt(1, Math.toIntExact(entity.getFrom().getId()));
            ps.setString(2, to1);
            ps.setString(3, entity.getMsg());
            if(entity.getReplyMsg() == null) {
                ps.setInt(4, 0);
            } else {
                ps.setInt(4, Math.toIntExact(entity.getReplyMsg().getId()));
            }

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
