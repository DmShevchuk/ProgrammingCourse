package commands.command;

import collection.CollectionManager;
import commands.Command;
import database.DbManager;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

import java.sql.SQLException;

public class Add extends Command {
    private final CollectionManager collectionManager;
    private final DbManager dbManager;

    public Add(CollectionManager collectionManager, DbManager dbManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    @Override
    public Response execute(Request request) {
        try {
            int dragonId = dbManager.add(request.getDragonBuild().build());
            if (dragonId != -1) {
                collectionManager.addDragon(request.getDragonBuild().setId(dragonId).build());
                return new Response(ResponseStatus.SUCCESS, "Dragon added to collection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Response(ResponseStatus.FAIL, "The dragon is not added to the collection!");
    }

}
