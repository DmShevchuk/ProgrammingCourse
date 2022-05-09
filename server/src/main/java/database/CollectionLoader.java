package database;

import collection.Coordinates;
import collection.Dragon;
import collection.DragonHead;
import collection.DragonType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
