package commands.command;

import collection.CollectionManager;
import commands.Command;
import database.DbManager;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

import java.sql.SQLException;

public class RemoveFirst extends Command {
    private final CollectionManager collectionManager;
    private final DbManager dbManager;

    public RemoveFirst(CollectionManager collectionManager, DbManager dbManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    @Override
    public Response execute(Request request) {
        int userId = request.getAccount().getId();

        if (collectionManager.getCollectionSize() == 0) {
            return new Response(ResponseStatus.FAIL, "Collection is empty!");
        }
        try {
            int firstId = collectionManager.getFirstId();
            int deletedLines = dbManager.removeFirst(firstId, userId);
            if(deletedLines == 1){
                collectionManager.removeFirst();
                return new Response(ResponseStatus.SUCCESS, "First element of collection has been removed!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Response(ResponseStatus.FAIL, "Cannot remove first element from collection!");
    }
}
