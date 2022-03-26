package commands;

import utils.CommandLine;

public abstract class Command {
    private final String name;
    private final String info;
    private final int argQuantity;
    protected final CommandLine commandLine;

    protected Command(String name, String info, int argQuantity, CommandLine commandLine) {
        this.name = name;
        this.info = info;
        this.argQuantity = argQuantity;
        this.commandLine = commandLine;
    }

    public String getName() {
        return name;
    }


    public abstract void execute();

    public String getInfo() {
        return info;
    }

    public int getArgQuantity() {
        return argQuantity;
    }
}
