import gui.AppWorker;
import javafx.application.Application;
import javafx.stage.Stage;
import account.Client;
import utils.AppStarter;

import java.net.InetSocketAddress;

public class App extends Application {
    private static String HOST = "127.0.0.1";
    private static int PORT = 12012;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AppStarter appStarter = new AppStarter(new InetSocketAddress(HOST, PORT));
        Client client = appStarter.run();

        AppWorker sceneConnector = new AppWorker(primaryStage, client);
        sceneConnector.setLoginWindow();
    }
}
