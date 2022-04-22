package run;

import collection.CollectionManager;
import commands.command.*;
import interaction.Request;
import interaction.Response;
import interaction.ResponseStatus;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Server{
    private final Socket socket;
    private final CollectionManager collectionManager;

    public Server(Socket socket, CollectionManager collectionManager) {
        this.socket = socket;
        this.collectionManager = collectionManager;

        run();
    }

    public void run() {
        try {
            while (true) {
                // Ожидание сообщения от клиента
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Request request = (Request) objectInputStream.readObject();
                System.out.println(request.getCommandName() + request.getArgs() + request.getDragonBuild());
                send(runCommand(request), socket);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Response runCommand(Request request){
        return switch (request.getCommandName()) {
            case "add" -> new Add(collectionManager).execute(request);
            case "add_if_max" -> new AddIfMax(collectionManager).execute(request);
            case "clear" -> new Clear(collectionManager).execute(request);
            case "info" -> new Info(collectionManager).execute(request);
            case "min_by_id" -> new MinByID(collectionManager).execute(request);
            case "print_field_descending_weight" -> new PrintFieldDescendingWeight(collectionManager).execute(request);
            case "remove_all_by_head" -> new RemoveAllByHead(collectionManager).execute(request);
            case "remove_by_id" -> new RemoveByID(collectionManager).execute(request);
            case "remove_first" -> new RemoveFirst(collectionManager).execute(request);
            case "show" -> new Show(collectionManager).execute(request);
            case "update" -> new UpdateId(collectionManager).execute(request);
            default -> null;
        };
    }

    public void send(Response response, Socket socket) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(response);
        socket.getOutputStream().write(ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array());
        byteArrayOutputStream.writeTo(socket.getOutputStream());
    }
}