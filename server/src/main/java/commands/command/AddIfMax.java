package commands.command;

import collection.CollectionManager;
import collection.Dragon;
import commands.Command;
import database.DbManager;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

import java.sql.SQLException;


public class AddIfMax extends Command {
    private final CollectionManager collectionManager;
    private final DbManager dbManager;

    public AddIfMax(CollectionManager collectionManager, DbManager dbManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    @Override
    public Response execute(Request request) {
        if (compare(request.getDragonBuild())) {
            try {
                int dragonId = dbManager.add(request.getDragonBuild().build());
                collectionManager.addDragon(request.getDragonBuild().setId(dragonId).build());
                return new Response(ResponseStatus.SUCCESS, "Dragon added to collection!", collectionManager.getCollection());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new Response(ResponseStatus.FAIL, "The dragon is not superior the largest dragon!");
    }

    public boolean compare(Dragon.Builder newDragon) {
        if (collectionManager.getCollectionSize() == 0) {
            return true;
        }
        Dragon dragon = collectionManager.getMaxElement();
        return dragon.compareTo(newDragon.build()) < 0;
    }
}
