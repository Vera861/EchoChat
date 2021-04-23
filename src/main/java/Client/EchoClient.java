package Client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

    public static void main(String[] args) throws InterruptedException {
        try(Socket socket = new Socket("localhost", 8189);
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
            DataInputStream ois = new DataInputStream(socket.getInputStream()); )
        {
            System.out.println("Подключено к сокету");
            while(!socket.isOutputShutdown()){
                if(br.ready()){
                    Thread.sleep(1000);
                    String clientCommand = br.readLine();
                    oos.writeUTF(clientCommand);
                    oos.flush();
                    Thread.sleep(1000);
                    if(clientCommand.equalsIgnoreCase("quit")){
                        Thread.sleep(2000);
                        if(ois.read() > -1)     {
                            String in = ois.readUTF();
                            System.out.println(in);
                        }
                        break;
                    }
                    Thread.sleep(2000);
                    if(ois.read() > -1)     {
                        String in = ois.readUTF();
                        System.out.println(in);
                    }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}