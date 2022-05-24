package account;

import interaction.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Authorization {
    private final Client client;

    public Authorization(Client client) {
        this.client = client;
    }


    public Response signIn(String login, String password) {
        String hashedPassword = hashPassword(password);
        return getAuthResult(new Account(login, hashPassword(password)), RequestType.AUTH);
    }

    public Response signUp(String login, String password) {
        String hashedPassword = hashPassword(password);
        return getAuthResult(new Account(login, password), RequestType.REG);

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

    private Response getAuthResult(Account account, RequestType requestType) {
        try {
            client.send(new Request.Builder().setRequestType(requestType).setAccount(account).build());
            return client.receive();
        } catch (IOException | ClassNotFoundException e) {
            return new Response(ResponseStatus.FAIL, "Проблемы с подключением к серверу!");
        }
    }
}
