package commands;

public class Exit extends Command {
    public Exit() {
        super("exit : завершить программу (без сохранения в файл)", "exit", 0);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
