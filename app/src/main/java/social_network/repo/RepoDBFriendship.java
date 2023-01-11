package social_network.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import social_network.domain.Friendship;

public class RepoDBFriendship extends RepoDBAbstract<Long, Friendship> {

    public RepoDBFriendship(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public void add(Friendship entity) {
        String sql = "INSERT INTO friendships (user_id1, user_id2, friends_from, status) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getId1());
            statement.setLong(2, entity.getId2());
            java.sql.Date sqlDate = java.sql.Date.valueOf(entity.getFriendsFrom().toLocalDate());
            statement.setDate(3, sqlDate);
            statement.setString(4, entity.getStatus());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM friendships WHERE id = ?";
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
        String sql = "DELETE FROM friendships";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Friendship> findAll() {
        Set<Friendship> friendships = new HashSet<>();

        String sql = "SELECT * FROM friendships";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);) {

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long id1 = resultSet.getLong("user_id1");
                    Long id2 = resultSet.getLong("user_id2");
                    java.sql.Date sqlDate = resultSet.getDate("friends_from");
                    java.time.LocalDate localDate = sqlDate.toLocalDate();
                    java.time.LocalDateTime localDateTime = localDate.atStartOfDay();
                    String status = resultSet.getString("status");
                    Friendship friendship = new Friendship(id1, id2);
                    friendship.setFriendsFrom(localDateTime);
                    friendship.setId(id);
                    friendship.setStatus(status);
                    friendships.add(friendship);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friendships;
    }

    @Override
    public Friendship findOne(Long id) {
        String sql = "SELECT * FROM friendships WHERE id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);) {

            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Long id1 = resultSet.getLong("user_id1");
                    Long id2 = resultSet.getLong("user_id2");
                    java.sql.Date sqlDate = resultSet.getDate("friends_from");
                    java.time.LocalDate localDate = sqlDate.toLocalDate();
                    java.time.LocalDateTime localDateTime = localDate.atStartOfDay();
                    String status = resultSet.getString("status");
                    Friendship friendship = new Friendship(id1, id2);
                    friendship.setFriendsFrom(localDateTime);
                    friendship.setId(id);
                    friendship.setStatus(status);
                    return friendship;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(Friendship entity) {
        String sql = "UPDATE friendships SET user_id1 = ?, user_id2 = ?, friends_from = ?, status = ? WHERE id = ?";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, entity.getId1());
            statement.setLong(2, entity.getId2());
            java.sql.Date sqlDate = java.sql.Date.valueOf(entity.getFriendsFrom().toLocalDate());
            statement.setDate(3, sqlDate);
            statement.setString(4, entity.getStatus());
            statement.setLong(5, entity.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
