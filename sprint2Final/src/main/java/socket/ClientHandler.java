package socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import controller.dto.resquest.SearchRequest;
import lombok.SneakyThrows;
import model.Mgni;
import service.TransactionService;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;
    private TransactionService transactionService = new TransactionService();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @SneakyThrows
    @Override
    public void run() {

        try {
            while (true) {
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();

                String data = new DataInputStream(inputStream).readUTF();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                SearchRequest searchRequest = new Gson().fromJson(data, SearchRequest.class);
                // socket 完整資料判斷

                switch (searchRequest.getChoice()){
                    case "findById":
                        Mgni mgni = transactionService.findById(searchRequest.getId());
                        String json = new Gson().toJson(mgni);
                        dataOutputStream.writeUTF(json);
                        dataOutputStream.flush(); //*******
                        break;
                    default:
                        break;
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
