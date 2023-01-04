package TCP;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        final String serverHost = "localhost";
        final int port = 7777;

        Socket clientSocket = null;
        DataInputStream input= null;
        DataOutputStream output= null;
        try {
            clientSocket = new Socket(serverHost, port);
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        }
        catch( UnknownHostException e){
            System.err.println("Don't know about host "+ serverHost);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.println("Enter the message: ");
                String message = sc.nextLine();
                output.writeUTF(message);
                System.out.println("Sent a message to the server.");
                String response = input.readUTF();
                System.out.println("Received a response from the server: " + response);
            }

        }catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
