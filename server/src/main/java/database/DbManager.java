package database;

import collection.Dragon;

import java.sql.*;

public class DbManager {
    private final Connection connection;

    public DbManager(Connection connection) {
        this.connection = connection;
    }


    public synchronized int add(Dragon dragon) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO dragons (name, coordinate_x, coordinate_y, creation_date," +
                        " age, weight, speaking, type, dragon_head, owner_id) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement = setDragon(dragon, preparedStatement);
        if (preparedStatement.executeUpdate() == 1) {
            return getLastId();
        }
        return -1;
    }

    public synchronized Integer getLastId() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT last_value FROM dragons_id_seq");
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("last_value");
        }
        return null;
    }


    public PreparedStatement setDragon(Dragon dragon, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, dragon.getName());
        preparedStatement.setFloat(2, dragon.getCoordinates().getX().floatValue());
        preparedStatement.setFloat(3, dragon.getCoordinates().getY().floatValue());
        preparedStatement.setDate(4, Date.valueOf(dragon.getCreationDate()));
        if (dragon.getAge() == null) {
            preparedStatement.setNull(5, Types.INTEGER);
        } else {
            preparedStatement.setInt(5, dragon.getAge());
        }
        preparedStatement.setInt(6, Math.toIntExact(dragon.getWeight()));
        preparedStatement.setBoolean(7, dragon.getSpeaking());
        if (dragon.getType() == null) {
            preparedStatement.setNull(8, Types.VARCHAR);
        } else {
            preparedStatement.setString(8, dragon.getType().toString());
        }
        preparedStatement.setInt(9, (int) dragon.getHead().getSize());
        preparedStatement.setInt(10, dragon.getOwnerId());

        return preparedStatement;
    }


    public synchronized int clearCollection(int userId) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM dragons WHERE owner_id = ?");
        preparedStatement.setInt(1, userId);
        int changedLines = preparedStatement.executeUpdate();
        return changedLines;
    }


    public synchronized int removeByHead(int userId, Long dragonHead) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM dragons WHERE owner_id = ? AND dragon_head = ?");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, Math.toIntExact(dragonHead));
        return preparedStatement.executeUpdate();
    }

    public synchronized int removeById(int userId, int dragonId) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM dragons WHERE owner_id = ? AND id = ?");
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, dragonId);
        return preparedStatement.executeUpdate();
    }

    public synchronized int updateId(Dragon dragon) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE dragons" +
                        " SET name = ?, coordinate_x = ?, coordinate_y = ?, creation_date = ?, age = ?," +
                        "weight = ?, speaking = ?, type = ?, dragon_head = ?, owner_id = ?" +
                        "WHERE id = ?");
        preparedStatement = setDragon(dragon, preparedStatement);
        preparedStatement.setInt(11, dragon.getId());
        return preparedStatement.executeUpdate();
    }

    public synchronized int removeFirst(int firstId, int userId) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM dragons WHERE id = ? AND owner_id = ?");
        preparedStatement.setInt(1, firstId);
        preparedStatement.setInt(2, userId);
        return preparedStatement.executeUpdate();
    }
}
