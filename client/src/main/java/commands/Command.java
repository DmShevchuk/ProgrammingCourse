package commands;

import interaction.Response;

import java.io.IOException;

public abstract class Command {
    private final String name;
    private final String info;
    private final int argQuantity;


    protected Command(String name, String info, int argQuantity) {
        this.name = name;
        this.info = info;
        this.argQuantity = argQuantity;
    }

    public abstract <T> Response execute(T args) throws IOException;
    public <T> void setAdditionalArgs(T args){};

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public int getArgQuantity() {
        return argQuantity;
    }
}
