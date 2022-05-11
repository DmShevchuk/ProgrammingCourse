package commands.command;

import collection.CollectionManager;
import collection.DragonHead;
import commands.Command;
import database.DbManager;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

import java.sql.SQLException;

public class RemoveByID extends Command {
    private final CollectionManager collectionManager;
    private final DbManager dbManager;

    public RemoveByID(CollectionManager collectionManager, DbManager dbManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    @Override
    public Response execute(Request request) {
        int dragonId = Integer.parseInt(request.getArgs());
        int userId = request.getAccount().getId();

        if (collectionManager.getCollectionSize() == 0 || !collectionManager.checkExistingID(dragonId)) {
            return new Response(ResponseStatus.FAIL, String.format("Non-existent id=%d!", dragonId));
        }
        try {
            int deletedLines = dbManager.removeById(userId, dragonId);
            if(deletedLines > 0){
                collectionManager.deleteElementByID(dragonId);
                return new Response(ResponseStatus.SUCCESS, String.format("Element with id=%d removed!", dragonId));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return new Response(ResponseStatus.FAIL, String.format("Unable to delete dragon with id=%d!", dragonId));
    }
}
