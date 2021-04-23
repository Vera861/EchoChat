package Server;

import Client.EchoClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        Socket socket = null;
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
            Socket client = serverSocket.accept();
            System.out.println("Соединение установлено");
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            while (true) {
                while (!client.isClosed()) {
                    String str = in.readUTF();
                    System.out.println("Сообщение от клиента: " + str);
                    if (str.equals("/end")) {
                        out.writeUTF("Server reply - " + str + " - OK");
                        out.flush();
                        break;
                    }
                    out.writeUTF("Server reply - " + str + " - OK");
                }
                in.close();
                out.close();
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}