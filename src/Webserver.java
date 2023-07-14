

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Webserver {

    public static final int DEFAULT_PORT = 8001;
    public static final int THREADPOOL_SIZE = 10;
    public int myPort;
    public final ExecutorService threadpool;

    public Webserver (int port) {
        this.myPort = port;
        this.threadpool = Executors.newFixedThreadPool(THREADPOOL_SIZE);
        startWebserver();

    }

    public static void main(String[] args) {

        new Webserver(DEFAULT_PORT);

    }

    public void startWebserver() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.myPort);
            System.out.println("Server gestartet...");

            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Neue Request von " + socket.getInetAddress().getHostAddress());

                Runnable requestHandler = new RequestHandler(socket);
                this.threadpool.execute(requestHandler);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
