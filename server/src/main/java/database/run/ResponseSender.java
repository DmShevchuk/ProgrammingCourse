package database.run;

import interaction.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/*
    Класс, отправляющий ответы пользователям
**/

public class ResponseSender {
    public ResponseSender() {
    }

    public synchronized Runnable send(Response response, Socket socket) throws IOException {
        System.out.println(response.getStatus());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        socket.getOutputStream().write(ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array());
        byteArrayOutputStream.writeTo(socket.getOutputStream());
        return null;
    }
}
