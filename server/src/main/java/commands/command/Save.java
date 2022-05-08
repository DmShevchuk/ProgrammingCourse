package commands.command;

import collection.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import commands.Command;
import data.*;
import interaction.*;

import java.io.*;
import java.util.logging.Logger;

public class Save extends Command {
    private final CollectionManager collectionManager;

    public Save(CollectionManager collectionManager, Logger logger) {
        super(collectionManager);
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(Request request) {
        return null;
    }
}
