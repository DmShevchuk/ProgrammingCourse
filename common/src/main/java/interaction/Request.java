package interaction;

import collection.Dragon;

import java.io.Serializable;

public class Request implements Serializable {
    private final RequestType requestType;
    private final String commandName;
    private final String args;
    private final Dragon.Builder dragonBuild;
    private final Account account;


    private Request(Builder builder) {
        this.requestType = builder.requestType;
        this.commandName = builder.commandName;
        this.args = builder.args;
        this.dragonBuild = builder.dragonBuild;
        this.account = builder.account;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }

    public Dragon.Builder getDragonBuild() {
        return dragonBuild;
    }

    public Account getAccount() {
        return account;
    }

    public static class Builder {
        private RequestType requestType;
        private String commandName;
        private String args;
        private Dragon.Builder dragonBuild;
        private Account account;

        public Builder() {
        }

        public Builder setRequestType(RequestType requestType) {
            this.requestType = requestType;
            return this;
        }

        public Builder setCommandName(String commandName) {
            this.commandName = commandName;
            return this;
        }

        public Builder setArgs(String args) {
            this.args = args;
            return this;
        }

        public Builder setDragonBuild(Dragon.Builder dragonBuild) {
            this.dragonBuild = dragonBuild;
            return this;
        }

        public Builder setAccount(Account account) {
            this.account = account;
            return this;
        }

        public Request build() {
            return new Request(this);
        }


    }

}
