package commands;

import collection.CollectionManager;
import commands.command.*;
import database.DbManager;
import interaction.Request;
import interaction.Response;

import java.util.HashMap;

public class CommandManager {
    private final CollectionManager collectionManager;
    private final DbManager dbManager;
    private final HashMap<String, Command> commandHashMap = new HashMap<>();

    public CommandManager(CollectionManager collectionManager, DbManager dbManager){
        this.collectionManager = collectionManager;
        this.dbManager = dbManager;

        commandManagerInit();
    }

    public Response runCommand(Request request){
        return commandHashMap.get(request.getCommandName()).execute(request);
    }

    private void commandManagerInit(){
        commandHashMap.put("add", new Add(collectionManager, dbManager));
        commandHashMap.put("add_if_max", new AddIfMax(collectionManager, dbManager));
        commandHashMap.put("clear", new Clear(collectionManager, dbManager));
        commandHashMap.put("info", new Info(collectionManager));
        commandHashMap.put("min_by_id", new MinByID(collectionManager));
        commandHashMap.put("print_field_descending_weight", new PrintFieldDescendingWeight(collectionManager));
        commandHashMap.put("remove_all_by_head", new RemoveAllByHead(collectionManager, dbManager));
        commandHashMap.put("remove_by_id", new RemoveByID(collectionManager, dbManager));
        commandHashMap.put("remove_first", new RemoveFirst(collectionManager, dbManager));
        commandHashMap.put("show", new Show(collectionManager));
        commandHashMap.put("update", new UpdateId(collectionManager, dbManager));
    }

}
