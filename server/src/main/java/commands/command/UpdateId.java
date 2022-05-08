package commands.command;

import collection.CollectionManager;
import collection.Dragon;
import commands.Command;
import database.DbManager;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

import java.sql.SQLException;


public class UpdateId extends Command {
    private CollectionManager collectionManager;
    private final DbManager dbManager;

    public UpdateId(CollectionManager collectionManager, DbManager dbManager) {
        super(collectionManager);
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;
    }

    @Override
    public Response execute(Request request) {
        Integer userId = request.getAccount().getId();
        int dragonId = Integer.parseInt(request.getArgs());

        Dragon dragon = collectionManager.getElementByID(dragonId);

        if(dragon == null || !dragon.getOwnerId().equals(userId))
            return new Response(ResponseStatus.FAIL, "Unable to update element!");

        Dragon updatedDragon = request.getDragonBuild().setId(dragonId).setOwnerId(userId).build();
        try {
            int changedLines = dbManager.updateId(updatedDragon);
            if (changedLines == 1) {
                collectionManager.updateElementById(dragon.getId(), request.getDragonBuild()
                        .setId(dragonId)
                        .setOwnerId(userId)
                        .build());
                return new Response(ResponseStatus.SUCCESS, "Item updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Response(ResponseStatus.FAIL, "Unable to update element!");
    }

}
