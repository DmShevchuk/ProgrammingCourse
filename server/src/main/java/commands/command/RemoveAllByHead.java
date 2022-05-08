package commands.command;

import collection.CollectionManager;
import collection.DragonHead;
import commands.Command;
import database.DbManager;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

import java.sql.SQLException;

public class RemoveAllByHead extends Command {
    private final CollectionManager collectionManager;
    private final DbManager dbManager;

    public RemoveAllByHead(CollectionManager collectionManager, DbManager dbManager) {
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
            int deletedLines = dbManager.removeByHead(userId, Long.parseLong(request.getArgs()));
            if (deletedLines > 0){
                collectionManager.removeByHead(userId, new DragonHead(Long.parseLong(request.getArgs())));
                return new Response(ResponseStatus.SUCCESS, String.format("Dragons with head size=%s removed!", request.getArgs()));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Response(ResponseStatus.FAIL, String.format("Unable to remove dragons with head=%s!", request.getArgs()));
    }
}
