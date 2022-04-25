package interaction;

import collection.Dragon;
import java.io.Serializable;

public class Request implements Serializable {
    private String commandName;
    private String args;
    private Dragon.Builder dragonBuild;

    private Request(Builder builder) {
        this.commandName = builder.commandName;
        this.args = builder.args;
        this.dragonBuild = builder.dragonBuild;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgs(){
        return args;
    }

    public Dragon.Builder getDragonBuild() {
        return dragonBuild;
    }

    public static class Builder{
        private String commandName;
        private String args;
        private Dragon.Builder dragonBuild;

        public Builder(){}

        public Builder setCommandName(String commandName){
            this.commandName = commandName;
            return this;
        }

        public Builder setArgs(String args){
            this.args = args;
            return this;
        }

        public Builder setDragonBuild(Dragon.Builder dragonBuild){
            this.dragonBuild = dragonBuild;
            return this;
        }

        public Request build(){
            return new Request(this);
        }


    }

}
