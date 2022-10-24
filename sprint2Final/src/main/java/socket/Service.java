package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Service {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Server is waiting for connection...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Server socket 連接成功，ip： " + socket.getRemoteSocketAddress());
                executorService.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
