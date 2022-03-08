package run;
import data.FileManager;
import data.ParserJSON;
import exceptions.AddingRepeatedCommandException;
import org.json.simple.parser.ParseException;
import utils.CommandLine;


public class App {
    //Необходимо указывать полный путь до файла!
    private static String DEFAULT_FILENAME = "collection.json";

    public static void main(String[] args) throws ParseException, NoSuchFieldException, AddingRepeatedCommandException {
        if(args.length == 1){
            DEFAULT_FILENAME = args[0];
        }

        if(FileManager.canRead(DEFAULT_FILENAME)){
            String jsonString = FileManager.read(DEFAULT_FILENAME);
            ParserJSON.parse(jsonString);

            CommandLine CMD = new CommandLine();
            CMD.run();
        }else{
            System.out.println(String.format("Unable to read file %s!", DEFAULT_FILENAME));
        }
    }
}
