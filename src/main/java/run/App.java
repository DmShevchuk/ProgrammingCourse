package run;

import data.FileManager;
import data.ParserJSON;
import utils.CommandLine;

import java.io.IOException;

//** Главный класс, запускающий приложение*/
public class App {
    private static String DEFAULT_FILENAME = "collection.json";

    public static void main(String[] args) {

        if (args.length == 1) {
            DEFAULT_FILENAME = args[0];
        }

        CommandLine CMD = new CommandLine();

        FileManager fileManager = new FileManager(CMD);

        if (fileManager.canRead(DEFAULT_FILENAME)) {
            String jsonString = fileManager.read(DEFAULT_FILENAME);

            try {
                ParserJSON parser = new ParserJSON(CMD);
                parser.parse(jsonString);

                CMD.run();
            } catch (Exception e){
                System.err.println(e.getMessage());
            }

        } else {
            System.err.println(String.format("Unable to read file %s!", DEFAULT_FILENAME));
        }
    }
}
