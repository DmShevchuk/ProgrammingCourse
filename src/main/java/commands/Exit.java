package commands;

public class Exit extends Command {
    public Exit() {
        super("exit : terminate program (without saving to file)", "exit", 0);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
