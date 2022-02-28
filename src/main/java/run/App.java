package run;
import commands.Help;
import data.FileManager;
import data.ParserJSON;
import exceptions.AddingRepeatedCommandException;
import exceptions.FieldValueException;
import org.json.simple.parser.ParseException;
import utils.CommandLine;

import java.io.IOException;


public class App {
    private static String DEFAULT_FILENAME = "collection.json";

    public static void main(String[] args) throws IOException, ParseException, NoSuchFieldException, AddingRepeatedCommandException {
        if(args.length == 1){
            DEFAULT_FILENAME = args[0];
        }

        FileManager reader = new FileManager();
        String jsonString = reader.read(DEFAULT_FILENAME);
        ParserJSON.parse(jsonString);

        CommandLine CMD = new CommandLine();
        CMD.run();
    }
}
