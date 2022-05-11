package database;

import collection.Coordinates;
import collection.Dragon;
import collection.DragonHead;
import collection.DragonType;

import java.sql.*;
import java.util.LinkedList;

/*
Класс, осуществляющий загрузку коллекции из БД
**/
public class CollectionLoader {
    private final Connection connection;

    public CollectionLoader(Connection connection) {
        this.connection = connection;
    }

    /*
    @return список объекта класса Dragon
    **/
    public LinkedList<Dragon> loadCollection() throws SQLException {
        LinkedList<Dragon> dragonsList = new LinkedList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM dragons");
        while (resultSet.next()) {
            Dragon dragon = getDragonFromQuery(resultSet);
            dragonsList.add(dragon);
        }
        statement.close();
        return dragonsList;
    }

    /*
     Поля БД формируют объект класса Dragon
    @param resultSet
    @return объект класса Dragon
    **/

    private Dragon getDragonFromQuery(ResultSet resultSet) throws SQLException {
        Dragon.Builder builder = new Dragon.Builder();

        builder.setId(resultSet.getInt("id"));
        builder.setName(resultSet.getString("name"));
        builder.setCoordinates(new Coordinates(resultSet.getDouble("coordinate_x"),
                resultSet.getDouble("coordinate_y")));
        builder.setCreationDate(resultSet.getDate("creation_date").toLocalDate());
        builder.setAge(resultSet.getInt("age"));
        builder.setWeight((long) resultSet.getInt("weight"));
        builder.setSpeaking(resultSet.getBoolean("speaking"));
        builder.setType(DragonType.getTypeByString(resultSet.getString("type")));
        builder.setHead(new DragonHead(resultSet.getInt("dragon_head")));
        builder.setOwnerId(resultSet.getInt("owner_id"));

        return builder.build();
    }


    /*
        SQL-скрипты, инициализирующие таблицы Dragons и Users
        В случае, если таких еще нет
    **/
    public void initializeTables() throws SQLException {
        PreparedStatement createUsersTable = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Users(" +
                        " id SERIAL UNIQUE PRIMARY KEY," +
                        " name VARCHAR UNIQUE NOT NULL," +
                        " password VARCHAR NOT NULL," +
                        " salt VARCHAR NOT NULL);"
        );
        createUsersTable.executeUpdate();

        PreparedStatement createDragonTable = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS Dragons(" +
                        "    id SERIAL UNIQUE PRIMARY KEY," +
                        "    name VARCHAR NOT NULL," +
                        "    coordinate_x FLOAT NOT NULL CHECK(coordinate_x > -972)," +
                        "    coordinate_y FLOAT NOT NULL CHECK(coordinate_y > -290)," +
                        "    creation_date DATE NOT NULL," +
                        "    age INTEGER CHECK (age > 0)," +
                        "    weight BIGINT CHECK (weight > 0)," +
                        "    speaking BOOLEAN NOT NULL," +
                        "    type VARCHAR CHECK(type = 'WATER' OR type = 'UNDERGROUND' OR type = 'AIR' OR type = 'FIRE')," +
                        "    dragon_head BIGINT NOT NULL," +
                        "    owner_id INTEGER NOT NULL," +
                        "    FOREIGN KEY (owner_id) REFERENCES Users (id)" +
                        ");"
        );
        createDragonTable.executeUpdate();
    }
}
