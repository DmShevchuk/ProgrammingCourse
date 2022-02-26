package commands;

public abstract class Command {
    private final String name;
    private final String INFO;
    private final int argQuantity;

    protected Command(String info, String name, int argQuantity) {
        this.INFO = info;
        this.name = name;
        this.argQuantity = argQuantity;
    }

    public String getName() {
        return name;
    }


    public abstract void execute();

    public String getInfo() {
        return INFO;
    }

    public int getArgQuantity(){
        return argQuantity;
    }
}
