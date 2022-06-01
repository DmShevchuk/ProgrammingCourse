package commands.command;

import collection.CollectionManager;
import commands.Command;
import database.DbManager;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

import java.sql.SQLException;

public class Clear extends Command {
    private final CollectionManager collectionManager;
    private final DbManager dbManager;

    public Clear(CollectionManager collectionManager, DbManager dbManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    @Override
    public Response execute(Request request) {
        int userId = request.getAccount().getId();
        try {
            int deletedLines = dbManager.clearCollection(userId);
            if (deletedLines > 0) {
                collectionManager.clearCollection(userId);
                return new Response(ResponseStatus.SUCCESS, "Collection cleared!", collectionManager.getCollection());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Response(ResponseStatus.FAIL, "Failed to clear collection," +
                " maybe there are no items to delete!");
    }
}

