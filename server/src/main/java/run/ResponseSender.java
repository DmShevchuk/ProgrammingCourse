package run;

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

    public synchronized void send(Response response, Socket socket){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(response);
            socket.getOutputStream().write(ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array());
            byteArrayOutputStream.writeTo(socket.getOutputStream());
        } catch (IOException e) {
            try {socket.close();}catch (IOException ignored){}
        }
    }

}
