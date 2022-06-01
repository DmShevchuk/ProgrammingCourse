package interaction;

import collection.Dragon;

import java.io.Serializable;
import java.util.LinkedList;

public class Response implements Serializable {
    private final ResponseStatus status;
    private String result;
    private Dragon dragon;
    private LinkedList<Dragon> dragonList;
    private Account account;

    public Response(ResponseStatus status, String result) {
        this.status = status;
        this.result = result;
    }

    public Response(ResponseStatus status, Dragon dragon) {
        this.status = status;
        this.dragon = dragon;
    }

    public Response(ResponseStatus status, LinkedList<Dragon> dragonList) {
        this.status = status;
        this.dragonList = dragonList;
    }

    public Response(ResponseStatus status, String result, LinkedList<Dragon> dragonList) {
        this.status = status;
        this.result = result;
        this.dragonList = dragonList;
    }

    public Response(ResponseStatus status, Account account, LinkedList<Dragon> dragonList) {
        this.status = status;
        this.account = account;
        this.dragonList = dragonList;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    public LinkedList<Dragon> getDragonList() {
        return dragonList;
    }

    public Dragon getDragon() {
        return dragon;
    }

    public Account getAccount() {
        return account;
    }
}