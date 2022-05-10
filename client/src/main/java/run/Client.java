package run;

import interaction.Account;
import interaction.Request;
import interaction.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private final InetSocketAddress inetSocketAddress;
    private SocketChannel socketChannel;
    private Account account;

    public Client(InetSocketAddress socketAddress) {
        this.inetSocketAddress = socketAddress;
    }

    public void connect() throws IOException {
        socketChannel = SocketChannel.open(inetSocketAddress);
        socketChannel.configureBlocking(false);
    }

    public void refuseConnection() {
        try {socketChannel.close();} catch (IOException ignored) {}
        resetSocketChannel();
    }

    public void resetSocketChannel() {
        socketChannel = null;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public void send(Request request) throws IOException {
        if (socketChannel == null) {
            connect();
        }
        ByteBuffer buffer = ByteBuffer.wrap(serialize(request));
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
    }

    public Response receive() throws IOException, ClassNotFoundException {
        Response response;
        ByteBuffer buffer = ByteBuffer.allocate(4);
        do {
            socketChannel.read(buffer);
        } while (buffer.hasRemaining());

        buffer.flip();
        buffer = ByteBuffer.allocate(buffer.getInt());

        while (buffer.hasRemaining()) {
            socketChannel.read(buffer);
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        response = (Response) objectInputStream.readObject();
        return response;
    }

    public byte[] serialize(Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(request);
        return byteArrayOutputStream.toByteArray();
    }
}