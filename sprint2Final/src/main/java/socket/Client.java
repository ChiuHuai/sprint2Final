package socket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            System.out.println("Client socket 連接成功： " + socket.getClass());

            BufferedReader buf = new BufferedReader(
                    new InputStreamReader(System.in));

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream =new DataInputStream(socket.getInputStream());

            String data = "";
            String temp = "";
            while(!data.equals("stop")) {
                 data = buf.readLine();
                dataOutputStream.writeUTF(data);
                dataOutputStream.flush();
                temp = dataInputStream.readUTF();
                System.out.println(temp);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
