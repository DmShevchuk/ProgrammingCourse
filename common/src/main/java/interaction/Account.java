package interaction;

import java.io.Serializable;

public class Account implements Serializable {
    private int id;
    private final String login;
    private final String hashedPassword;

    public Account(String login, String hashedPassword) {
        this.login = login;
        this.hashedPassword = hashedPassword;
    }

    public Account(int id, String login, String hashedPassword) {
        this.id = id;
        this.login = login;
        this.hashedPassword = hashedPassword;
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
}
