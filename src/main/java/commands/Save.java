package commands;

public class Save extends Command {
    private final String DEFAULT_FILE_NAME = "collection-out.json";

    public Save() {
        super("save : сохранить коллекцию в файл", "save", 0);
    }

    @Override
    public void execute() {
    }
}
