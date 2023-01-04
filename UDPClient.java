package UDP;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        final int port = 9876;
        final String serverAddress = "localhost";
        InetAddress IPAddress = InetAddress.getByName(serverAddress);
        DatagramSocket clientSocket = new DatagramSocket();
        byte[] sendData ;

        while(true) {
            System.out.println("Please enter your message");
            Scanner sc  = new Scanner(System.in);
            String message = sc.nextLine();
            sendData = message.getBytes();
            byte[] receiveData = new byte[sendData.length];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("FROM SERVER: " + modifiedSentence);
        }



    }
}
