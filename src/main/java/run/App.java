package run;

import data.FileManager;
import data.ParserJSON;
import utils.CommandLine;

//** Главный класс, запускающий приложение*/
public class App {
    private static String FILENAME = "collection.json";

    public static void main(String[] args) {
        if (args.length == 1) {
            FILENAME = args[0];
        }

        CommandLine CMD = new CommandLine();
        FileManager fileManager = new FileManager(CMD);


        try {
            fileManager.canRead(FILENAME);
            String jsonString = fileManager.read(FILENAME);
            ParserJSON parser = new ParserJSON(CMD);
            parser.parse(jsonString);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            CMD.run();
        }
    }
}
