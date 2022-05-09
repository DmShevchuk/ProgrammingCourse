package utils;

import interaction.Account;
import interaction.Request;
import interaction.RequestType;
import interaction.Response;
import run.Client;

import java.io.Console;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authorization {
    private final Client client;
    private final Console console;

    public Authorization(Client client, Console console) {
        this.client = client;
        this.console = console;
    }

    public Account startAuth() {
        String choice = console.readLine("Enter 'v' to enter, 'r' to register:");
        if ("v".equals(choice)) {
            return signIn();
        } else {
            return signUp();
        }
    }

    private Account signIn() {
        String login = "";
        String password = "";
        while (login.length() == 0) {
            login = console.readLine("Login:");
        }
        while (password.length() == 0) {
            password = String.copyValueOf(console.readPassword("Enter password:"));
        }
        return getAuthResult(new Account(login, hashPassword(password)), RequestType.AUTH);
    }

    private Account signUp() {
        String login = "";
        String password = "";
        while (login.length() == 0) {
            login = console.readLine("Login:");
        }
        while (password.length() == 0) {
            password = getPasswordConfirm();
        }

        return getAuthResult(new Account(login, password), RequestType.REG);

    }

    private String getPasswordConfirm() {
        String password = "";
        String passwordConfirm = "";
        while (password.length() == 0) {
            password = String.copyValueOf(console.readPassword("Enter password:"));
        }

        while (passwordConfirm.length() == 0) {
            passwordConfirm = String.copyValueOf(console.readPassword("Enter password again:"));
            ;
        }

        if (password.equals(passwordConfirm)) {
            return hashPassword(password);
        }

        System.out.println("Passwords do not match!");
        return "";
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-224");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            return no.toString(16);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Account getAuthResult(Account account, RequestType requestType) {
        try {
            client.send(new Request.Builder().setRequestType(requestType).setAccount(account).build());
            Response response = client.receive();
            if (response.getAccount() != null) {
                System.out.println("Login Successful!");
                return response.getAccount();
            }
            System.out.println(response.getResult());
            client.resetSocketChannel();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Problems connecting to the server!");
        }
        return null;
    }
}
