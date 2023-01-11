package social_network.repo;

import social_network.domain.Friendship;
import social_network.domain.Message;

import java.sql.*;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RepoDBMessages extends RepoDBAbstract<Long, Message> {

    public RepoDBMessages(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public void add(Message entity) {
        String sql = "INSERT INTO messages (user_id1, user_id2, sent_at, content) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getId1());
            statement.setLong(2, entity.getId2());
            java.sql.Timestamp sqlTimestamp = java.sql.Timestamp.valueOf(entity.getSent_at().atZone(ZoneId.systemDefault()).toLocalDateTime());
            statement.setTimestamp(3, sqlTimestamp);
            statement.setString(4, entity.getContent());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM messages WHERE id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM messages";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Message entity) {
        String sql = "UPDATE messages SET user_id1 = ?, user_id2 = ?, sent_at = ?, content = ? WHERE id = ?";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getId1());
            statement.setLong(2, entity.getId2());
            java.sql.Date sqlDate = java.sql.Date.valueOf(entity.getSent_at().toLocalDate());
            statement.setDate(3, sqlDate);
            statement.setString(4, entity.getContent());
            statement.setLong(5, entity.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message findOne(Long aLong) {
        String sql = "SELECT * FROM messages";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long id1 = resultSet.getLong("user_id1");
                    Long id2 = resultSet.getLong("user_id2");
                    java.sql.Date sqlDate = resultSet.getDate("sent_at");
                    java.time.LocalDate localDate = sqlDate.toLocalDate();
                    java.time.LocalDateTime sent_at = localDate.atStartOfDay();
                    String content = resultSet.getString("content");
                    Message message = new Message(id1, id2, sent_at, content);
                    message.setId(id);

                    return message;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Collection<Message> findAll() {
        Set<Message> messages = new HashSet<>();

        String sql = "SELECT * FROM messages";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long id1 = resultSet.getLong("user_id1");
                    Long id2 = resultSet.getLong("user_id2");
                    java.sql.Timestamp sqlTimestamp = resultSet.getTimestamp("sent_at");
                    java.time.LocalDateTime sent_at = sqlTimestamp.toLocalDateTime();
                    String content = resultSet.getString("content");
                    Message message = new Message(id1, id2, sent_at, content);
                    message.setId(id);
                    messages.add(message);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }
}
