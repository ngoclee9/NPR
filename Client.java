package NPR;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final String serverHost = "localhost";
        final int port = 1153;

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
            System.out.println("Please enter the student ID: ");
            int studentID= sc.nextInt();
            output.writeUTF(String.valueOf(studentID));
            String response1  = input.readUTF();
            sc.nextLine();
            System.out.println(response1);
            while (true){
                System.out.println("Enter the message: ");
                String message = sc.nextLine();
                output.writeUTF(message);
                System.out.println("Sent a message to the server.");
                String response = input.readUTF();
                if(isNumeric(message) ) {
                    if (Integer.parseInt(message) >0){
                        System.out.println(studentID + " raised to the power of  " + message + " is "+ response );
                    }
                    else{
                        System.out.println("Received a response from the server: " + response);
                    }

                }
                else
                    System.out.println("Received a response from the server: " + response);
            }

        }catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public static boolean isNumeric(String str) {
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }
}
