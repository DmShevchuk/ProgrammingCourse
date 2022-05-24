package commands;

public abstract class Command {
    private final String name;
    private final String info;
    private final int argQuantity;


    protected Command(String name, String info, int argQuantity) {
        this.name = name;
        this.info = info;
        this.argQuantity = argQuantity;
    }

    public abstract void execute();

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
