package database.run;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Properties;

/*
     Класс, запускающий сервер с параметрами,
     указанными в resources/connection.properties
**/

public class ServerStarter {
    private final String propFileName;

    public ServerStarter(String propFileName) {
        this.propFileName = propFileName;
    }


    public ServerSocket start() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(propFileName));

        String host = properties.getProperty("host");
        int port = Integer.parseInt(properties.getProperty("port"));

        InetAddress inetAddress;
        inetAddress = InetAddress.getByName(host);
        return new ServerSocket(port, 0, inetAddress);
    }
}
